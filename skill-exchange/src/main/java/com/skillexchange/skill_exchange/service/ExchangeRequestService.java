package com.skillexchange.skill_exchange.service;

import com.skillexchange.skill_exchange.model.ExchangeRequest;
import com.skillexchange.skill_exchange.model.User;
import com.skillexchange.skill_exchange.repository.ExchangeRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangeRequestService {

    @Autowired
    private ExchangeRequestRepository exchangeRequestRepository;

    // Create a new exchange request
    public ExchangeRequest createRequest(ExchangeRequest request) {
        return exchangeRequestRepository.save(request);
    }

    // Get all requests made by a user (as requester)
    public List<ExchangeRequest> getRequestsByRequester(User user) {
        return exchangeRequestRepository.findByRequester(user);
    }

    // Get all requests received by a user (as provider)
    public List<ExchangeRequest> getRequestsByProvider(User user) {
        return exchangeRequestRepository.findByProvider(user);
    }

    // Get request by ID
    public Optional<ExchangeRequest> getRequestById(Long id) {
        return exchangeRequestRepository.findById(id);
    }

    // Update full request object
    public ExchangeRequest updateRequest(ExchangeRequest request) {
        return exchangeRequestRepository.save(request);
    }

    // Delete request by ID
    public void deleteRequest(Long id) {
        exchangeRequestRepository.deleteById(id);
    }

    // ✅ Update request status (Accept / Reject / Complete)
    public ExchangeRequest updateStatus(Long requestId, ExchangeRequest.Status newStatus) {
        ExchangeRequest request = exchangeRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found with id: " + requestId));

        request.setStatus(newStatus);

        // If marked completed, update completedAt timestamp
        if (newStatus == ExchangeRequest.Status.COMPLETED) {
            request.setCompletedAt(LocalDateTime.now());
        }

        return exchangeRequestRepository.save(request);
    }

    public List<ExchangeRequest> getAllRequests() {
    return exchangeRequestRepository.findAll();
}

}
