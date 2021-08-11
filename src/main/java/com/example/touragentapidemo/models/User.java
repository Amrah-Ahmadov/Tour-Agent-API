package com.example.touragentapidemo.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@ToString(exclude = "usersRequests")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String companyName;
    private String name;
    private String surname;
    private String voen;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(columnDefinition = "boolean default false")
    private boolean isActive;
    private LocalDateTime creationDate;

    public String getFullName(){
        return this.name + " "+this.surname;
    }
}
