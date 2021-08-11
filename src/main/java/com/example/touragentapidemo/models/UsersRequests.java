package com.example.touragentapidemo.models;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_requests")
public class UsersRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;
    @OneToOne(mappedBy = "usersRequests")
    private Offer offer;
    private String requestStatus;
    @Column(columnDefinition = "boolean default false")
    private boolean isArchived;
    private String clientContactInfo;
}
