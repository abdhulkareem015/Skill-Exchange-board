package com.skillexchange.skill_exchange.controller;

import com.skillexchange.skill_exchange.model.User;
import com.skillexchange.skill_exchange.model.ExchangeRequest;
import com.skillexchange.skill_exchange.model.Skill;
import com.skillexchange.skill_exchange.service.UserService;
import com.skillexchange.skill_exchange.service.ExchangeRequestService;
import com.skillexchange.skill_exchange.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExchangeRequestService exchangeRequestService;

    @Autowired
    private SkillService skillService;

    @GetMapping("/home")
    public String homePage(@RequestParam String email, Model model) {
        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isEmpty()) {
            model.addAttribute("error", "User not found!");
            return "error";
        }

        User user = userOpt.get();
        List<ExchangeRequest> sentRequests = exchangeRequestService.getRequestsByRequester(user);
        List<ExchangeRequest> receivedRequests = exchangeRequestService.getRequestsByProvider(user);
        List<Skill> userSkills = skillService.getSkillsByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("sentRequests", sentRequests);
        model.addAttribute("receivedRequests", receivedRequests);
        model.addAttribute("userSkills", userSkills);

        return "home";
    }
}
