package com.skillexchange.skill_exchange.repository;

import com.skillexchange.skill_exchange.model.ExchangeRequest;
import com.skillexchange.skill_exchange.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeRequestRepository extends JpaRepository<ExchangeRequest, Long> {
    List<ExchangeRequest> findByRequester(User requester);
    List<ExchangeRequest> findByProvider(User provider);
}
