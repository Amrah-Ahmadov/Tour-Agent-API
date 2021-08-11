package com.example.touragentapidemo.models;

import com.example.touragentapidemo.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @Column(nullable = false, unique = true)
    private String id;
    @Column(columnDefinition="TEXT")
    private String requestContext;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "request")
    private List<UsersRequests> usersRequests;

}
