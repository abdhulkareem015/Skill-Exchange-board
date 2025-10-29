package com.skillexchange.skill_exchange.repository;

import com.skillexchange.skill_exchange.model.Review;
import com.skillexchange.skill_exchange.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByReviewedUser(User reviewedUser);
}
