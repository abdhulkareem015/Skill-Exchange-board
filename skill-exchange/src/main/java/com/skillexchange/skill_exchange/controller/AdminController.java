package com.skillexchange.skill_exchange.controller;

import com.skillexchange.skill_exchange.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private ExchangeRequestService exchangeRequestService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("skills", skillService.getAllSkills());
        model.addAttribute("requests", exchangeRequestService.getAllRequests());
        model.addAttribute("reviews", reviewService.getAllReviews());
        return "admin-dashboard";
    }
}
