package com.example.touragentapidemo.repositories;

import com.example.touragentapidemo.models.UsersRequests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersRequestsRepo extends JpaRepository<UsersRequests, Long> {
    UsersRequests getUsersRequestsByRequestIdAndUserId(String requestId, int userId);
//    @Query(value = "select r.id, r.request_context, ur.request_status, o.additional_info, o.date_range, o.price from requests r JOIN users_requests ur ON r.id = ur.request_id JOIN offers o ON ur.id = o.users_requests_id where ur.user_id = :userId AND ur.is_archived = FALSE",nativeQuery = true)
//    List<UsersRequests> getAllRequestsThatOfferMade(int userId);
    @Query(value = "select * from users_requests ur where ur.user_id = :userId AND ur.is_archived = false AND ur.request_status = 'NEW_REQUEST'",nativeQuery = true)
    List<UsersRequests> getAllUsersRequests(int userId);
    @Query(value = "select * from users_requests ur where ur.user_id = :userId AND ur.is_archived = true",nativeQuery = true)
    List<UsersRequests> getAllArchivedUsersRequests(int userId);

    List<UsersRequests> getUsersRequestsByRequestId(String requestId);
    UsersRequests getUsersRequestsById(long id);
}
