package com.skillexchange.skill_exchange.controller;

import com.skillexchange.skill_exchange.model.*;
import com.skillexchange.skill_exchange.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/requests")
public class ExchangeRequestController {

    @Autowired
    private ExchangeRequestService exchangeRequestService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private UserService userService;

    // -------------------------------
    // CREATE REQUEST
    // -------------------------------
    @GetMapping("/create")
    public String showCreateRequestForm(@RequestParam String email, Model model) {
        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isEmpty()) {
            model.addAttribute("error", "User not found!");
            return "error";
        }

        model.addAttribute("user", userOpt.get());
        model.addAttribute("skills", skillService.getAllSkills());
        return "create-request";
    }

    @PostMapping("/create")
    public String createRequest(@RequestParam Long skillId,
                                @RequestParam String requesterEmail,
                                Model model) {
        Optional<Skill> skillOpt = skillService.getAllSkills()
                .stream().filter(s -> s.getSkillId().equals(skillId)).findFirst();
        Optional<User> requesterOpt = userService.findByEmail(requesterEmail);

        if (skillOpt.isPresent() && requesterOpt.isPresent()) {
            Skill skill = skillOpt.get();
            User requester = requesterOpt.get();

            ExchangeRequest request = ExchangeRequest.builder()
                    .skill(skill)
                    .provider(skill.getUser())
                    .requester(requester)
                    .status(ExchangeRequest.Status.PENDING)
                    .build();

            exchangeRequestService.createRequest(request);

            return "redirect:/home?email=" + requester.getEmail();
        } else {
            model.addAttribute("error", "Invalid skill or user!");
            return "create-request";
        }
    }

    // -------------------------------
    // VIEW SENT REQUESTS (USER SIDE)
    // -------------------------------
    @GetMapping("/user")
    public String viewUserRequests(@RequestParam String email, Model model) {
        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isEmpty()) {
            model.addAttribute("error", "User not found!");
            return "error";
        }

        User user = userOpt.get();
        model.addAttribute("user", user);
        model.addAttribute("requests", exchangeRequestService.getRequestsByRequester(user));
        model.addAttribute("viewType", "sent");
        return "view-requests";
    }

    // -------------------------------
    // VIEW RECEIVED REQUESTS (PROVIDER SIDE)
    // -------------------------------
    @GetMapping("/provider")
    public String viewProviderRequests(@RequestParam String email, Model model) {
        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isEmpty()) {
            model.addAttribute("error", "User not found!");
            return "error";
        }

        User user = userOpt.get();
        model.addAttribute("user", user);
        model.addAttribute("requests", exchangeRequestService.getRequestsByProvider(user));
        model.addAttribute("viewType", "received");
        return "view-requests";
    }

    // -------------------------------
    // UPDATE STATUS (Accept / Reject / Complete)
    // -------------------------------
    @PostMapping("/updateStatus")
    public String updateRequestStatus(@RequestParam Long requestId,
                                      @RequestParam String action,
                                      @RequestParam String userEmail,
                                      Model model) {
        ExchangeRequest.Status newStatus;

        switch (action.toLowerCase()) {
            case "accept":
                newStatus = ExchangeRequest.Status.ACCEPTED;
                break;
            case "reject":
                newStatus = ExchangeRequest.Status.REJECTED;
                break;
            case "complete":
                newStatus = ExchangeRequest.Status.COMPLETED;
                break;
            default:
                newStatus = ExchangeRequest.Status.PENDING;
        }

        exchangeRequestService.updateStatus(requestId, newStatus);

        return "redirect:/requests/provider?email=" + userEmail;
    }
}
