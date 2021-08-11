package com.example.touragentapidemo.services;

import com.example.touragentapidemo.enums.RequestStatus;
import com.example.touragentapidemo.models.UsersRequests;
import com.example.touragentapidemo.repositories.UsersRequestsRepo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ReceivedOfferService {
    private final UsersRequestsRepo usersRequestsRepo;

    public ReceivedOfferService(UsersRequestsRepo usersRequestsRepo) {
        this.usersRequestsRepo = usersRequestsRepo;
    }

    @RabbitListener(queues = "accepted_offer_queue")
    public void listen(Map<String, String> acceptedMap) {
        String usersRequestsId = acceptedMap.get("UsersRequestId");
        String contactInfo = acceptedMap.get("UsersInfo");
        UsersRequests usersRequests = usersRequestsRepo.getUsersRequestsById(Long.valueOf(usersRequestsId));
        usersRequests.setRequestStatus(RequestStatus.ACCEPTED.name());
        usersRequests.setClientContactInfo(contactInfo);
        usersRequestsRepo.saveAndFlush(usersRequests);
    }
}
