package com.skillexchange.skill_exchange.controller;

import com.skillexchange.skill_exchange.model.*;
import com.skillexchange.skill_exchange.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ExchangeRequestService exchangeRequestService;

    @Autowired
    private UserService userService;

    // Show Review Form
    @GetMapping("/add/{requestId}")
    public String showReviewForm(@PathVariable Long requestId, Model model) {
        Optional<ExchangeRequest> reqOpt = exchangeRequestService.getRequestById(requestId);
        if (reqOpt.isPresent()) {
            model.addAttribute("exchangeRequest", reqOpt.get());
            return "add-review";
        } else {
            model.addAttribute("error", "Exchange request not found!");
            return "error";
        }
    }

    // Handle Review Submission
    @PostMapping("/add")
    public String addReview(@RequestParam Long requestId,
                            @RequestParam String reviewerEmail,
                            @RequestParam int rating,
                            @RequestParam String comment,
                            Model model) {
        Optional<ExchangeRequest> reqOpt = exchangeRequestService.getRequestById(requestId);
        Optional<User> reviewerOpt = userService.findByEmail(reviewerEmail);

        if (reqOpt.isPresent() && reviewerOpt.isPresent()) {
            ExchangeRequest request = reqOpt.get();
            User reviewer = reviewerOpt.get();
            User reviewedUser = request.getProvider(); // provider is being rated

            Review review = Review.builder()
                    .exchangeRequest(request)
                    .reviewer(reviewer)
                    .reviewedUser(reviewedUser)
                    .rating(rating)
                    .comment(comment)
                    .build();

            reviewService.addReview(review);

            model.addAttribute("message", "Review added successfully!");
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid data!");
            return "error";
        }
    }

    // View All Reviews for a User
    @GetMapping("/user/{email}")
    public String viewUserReviews(@PathVariable String email, Model model) {
        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isPresent()) {
            model.addAttribute("reviews", reviewService.getReviewsByUser(userOpt.get()));
            model.addAttribute("reviewedUser", userOpt.get());
            return "view-reviews";
        } else {
            model.addAttribute("error", "User not found!");
            return "error";
        }
    }
}
