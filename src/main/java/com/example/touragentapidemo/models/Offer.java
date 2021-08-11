package com.example.touragentapidemo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String price;
    private String dateRange;
    private String additionalInfo;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "users_requests_id", referencedColumnName = "id")
    private UsersRequests usersRequests;
}
