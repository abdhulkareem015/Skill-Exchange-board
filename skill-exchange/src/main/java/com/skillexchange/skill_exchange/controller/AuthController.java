package com.skillexchange.skill_exchange.controller;

import com.skillexchange.skill_exchange.model.User;
import com.skillexchange.skill_exchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Email already registered!");
            return "register";
        }
        userService.registerUser(user);
        model.addAttribute("success", "Registration successful! Please login.");
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {

        Optional<User> userOpt = userService.findByEmail(email);

        if (userOpt.isPresent() && userService.validateLogin(email, password)) {
            User user = userOpt.get();

            // ✅ Redirect to home page with email as parameter
            return "redirect:/home?email=" + user.getEmail();
        } else {
            model.addAttribute("error", "Invalid email or password!");
            return "login";
        }
    }

    @GetMapping("/logout")
public String logout() {
    // You can later clear session attributes here if you use session-based login
    return "redirect:/";
}

}
