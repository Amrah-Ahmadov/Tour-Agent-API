package com.example.touragentapidemo.repositories;

import com.example.touragentapidemo.models.Offer;
import com.example.touragentapidemo.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferRepo extends JpaRepository<Offer, Long> {
//    @Query(value = "select r.id, r.request_context, ur.request_status, o.additional_info, o.date_range, o.price from requests r JOIN users_requests ur ON r.id = ur.request_id JOIN offers o ON ur.id = o.users_requests_id where ur.user_id = :userId AND ur.is_archived = FALSE",nativeQuery = true)
    @Query(value = "select o.id, o.additional_info, o.date_range, o.price, o.users_requests_id from offers o JOIN users_requests ur ON o.users_requests_id = ur.id where ur.request_status = 'OFFER_MADE' AND ur.user_id = :userId AND ur.is_archived = false",nativeQuery = true)
    List<Offer> getAllRequestsThatOfferMade(int userId);
    @Query(value = "select o.id, o.additional_info, o.date_range, o.price, o.users_requests_id from offers o JOIN users_requests ur ON o.users_requests_id = ur.id where ur.request_status = 'ACCEPTED' AND ur.user_id = :userId AND ur.is_archived = false",nativeQuery = true)
    List<Offer> getAllAcceptedOffersWithRequests(int userId);
}
