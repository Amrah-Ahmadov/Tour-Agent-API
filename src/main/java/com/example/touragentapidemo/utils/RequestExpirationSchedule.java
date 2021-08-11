package com.example.touragentapidemo.utils;

import com.example.touragentapidemo.enums.RequestStatus;
import com.example.touragentapidemo.models.Request;
import com.example.touragentapidemo.models.UsersRequests;
import com.example.touragentapidemo.repositories.RequestRepo;
import com.example.touragentapidemo.repositories.UsersRequestsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class RequestExpirationSchedule {
    @Autowired
    RequestRepo requestRepo;
    @Autowired
    UsersRequestsRepo usersRequestsRepo;


    @Scheduled(cron = "0 0/1 * * * *", zone = "Asia/Baku")
    public void requestExpiration(){
        List<Request> allRequests = requestRepo.findAll();
        for(Request r : allRequests){
            if(r.getExpirationDate().isAfter(LocalDateTime.now().minusMinutes(1)) && r.getExpirationDate().isBefore(LocalDateTime.now().plusMinutes(1))){
                List<UsersRequests> allUsersRequests = usersRequestsRepo.getUsersRequestsByRequestId(r.getId());
                for(UsersRequests ur : allUsersRequests){
                    ur.setRequestStatus(RequestStatus.EXPIRED.name());
                    ur.setArchived(true);
                    usersRequestsRepo.saveAndFlush(ur);
                }
            }
        }
    }
}
