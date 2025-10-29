package com.skillexchange.skill_exchange.service;

import com.skillexchange.skill_exchange.model.Review;
import com.skillexchange.skill_exchange.model.User;
import com.skillexchange.skill_exchange.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByUser(User user) {
        return reviewRepository.findByReviewedUser(user);
    }

    public List<Review> getAllReviews() {
    return reviewRepository.findAll();
}

}
