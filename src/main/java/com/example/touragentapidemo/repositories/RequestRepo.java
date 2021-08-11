package com.example.touragentapidemo.repositories;

import com.example.touragentapidemo.dtos.RequestDto;
import com.example.touragentapidemo.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RequestRepo extends JpaRepository<Request, String> {
    @Query(value = "select r.id, r.request_context from requests r JOIN users_requests ur ON r.id = ur.request_id where ur.user_id = :userId AND ur.is_archived = false",nativeQuery = true)
    List<Request> getRequestByUserId(int userId);
    @Query(value = "select r.id, r.request_context from requests r JOIN users_requests ur ON r.id = ur.request_id where ur.user_id = :userId AND ur.is_archived = true",nativeQuery = true)
    List<Request> getAllArchivedRequestsOfUser(int userId);
}
