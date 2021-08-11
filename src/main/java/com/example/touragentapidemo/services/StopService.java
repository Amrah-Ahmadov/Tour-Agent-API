package com.example.touragentapidemo.services;

import com.example.touragentapidemo.enums.RequestStatus;
import com.example.touragentapidemo.models.UsersRequests;
import com.example.touragentapidemo.repositories.UsersRequestsRepo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StopService {
    private final UsersRequestsRepo usersRequestsRepo;

    public StopService(UsersRequestsRepo usersRequestsRepo) {
        this.usersRequestsRepo = usersRequestsRepo;
    }

    @RabbitListener(queues = "stop_queue")
    public void listen(String requestId) {
        List<UsersRequests> usersRequests = usersRequestsRepo.getUsersRequestsByRequestId(requestId);
        for(UsersRequests ur : usersRequests){
            ur.setRequestStatus(RequestStatus.EXPIRED.name());
            usersRequestsRepo.saveAndFlush(ur);
        }
    }
}
