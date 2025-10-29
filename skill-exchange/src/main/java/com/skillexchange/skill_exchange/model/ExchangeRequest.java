package com.skillexchange.skill_exchange.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "exchange_requests")
public class ExchangeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;   // The one who requests to learn

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private User provider;    // The one who teaches

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;

    @PrePersist
    public void onCreate() {
        this.requestedAt = LocalDateTime.now();
        this.status = Status.PENDING;
    }

    public enum Status {
        PENDING, ACCEPTED, COMPLETED, REJECTED
    }
}
