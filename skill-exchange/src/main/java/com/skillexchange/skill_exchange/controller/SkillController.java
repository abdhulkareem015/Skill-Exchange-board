package com.skillexchange.skill_exchange.controller;

import com.skillexchange.skill_exchange.model.Skill;
import com.skillexchange.skill_exchange.model.User;
import com.skillexchange.skill_exchange.service.SkillService;
import com.skillexchange.skill_exchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @Autowired
    private UserService userService;

    // -------------------------------
    // SHOW ADD SKILL FORM
    // -------------------------------
    @GetMapping("/add")
    public String showAddSkillForm(@RequestParam String email, Model model) {
        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isEmpty()) {
            model.addAttribute("error", "User not found!");
            return "error";
        }

        model.addAttribute("user", userOpt.get());
        model.addAttribute("skill", new Skill());
        return "add-skill";
    }

    // -------------------------------
    // HANDLE ADD SKILL SUBMISSION
    // -------------------------------
    @PostMapping("/add")
    public String addSkill(@ModelAttribute Skill skill,
                           @RequestParam String email,
                           Model model) {
        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isEmpty()) {
            model.addAttribute("error", "User not found!");
            return "error";
        }

        User user = userOpt.get();
        skill.setUser(user);
        skillService.addSkill(skill);

        return "redirect:/home?email=" + email;
    }
}
