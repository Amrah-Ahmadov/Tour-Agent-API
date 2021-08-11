package com.example.touragentapidemo.services;

import com.example.touragentapidemo.enums.RequestStatus;
//import com.example.touragentapidemo.models.Request;
//import com.example.touragentapidemo.repositories.RequestRepo;
//import com.example.touragentapidemo.models.Request;
import com.example.touragentapidemo.models.Request;
import com.example.touragentapidemo.models.User;
//import com.example.touragentapidemo.models.UsersRequests;
import com.example.touragentapidemo.models.UsersRequests;
import com.example.touragentapidemo.repositories.RequestRepo;
import com.example.touragentapidemo.repositories.UserRepo;
import com.example.touragentapidemo.repositories.UsersRequestsRepo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReceiveRequestService {
    private final UserRepo userRepo;
    private final RequestRepo requestRepo;
    private final UsersRequestsRepo usersRequestsRepo;
    @Value("${request.duration}")
    private long duration;
    @Value("#{ T(java.time.LocalTime).parse('${work-hours.start-time}')}")
    private LocalTime startTime;
    @Value("#{ T(java.time.LocalTime).parse('${work-hours.end-time}')}")
    private LocalTime endTime;

    public ReceiveRequestService(UserRepo userRepo, RequestRepo requestRepo, UsersRequestsRepo usersRequestsRepo) {
        this.userRepo = userRepo;
        this.requestRepo = requestRepo;
        this.usersRequestsRepo = usersRequestsRepo;
    }

    @RabbitListener(queues = "telegram_bot_queue")
    public void listen(Map<String, String> requestMap) {
        String requestId = requestMap.get("Request");
        requestMap.remove("Request");
        String requestContent = convertMapToString(requestMap);
        LocalDateTime creationDate = LocalDateTime.now();
        LocalDateTime expirationDate = creationDate.plusHours(duration);
        if(!(expirationDate.toLocalTime().isAfter(startTime) && expirationDate.toLocalTime().isBefore(endTime))){
            long hours = ChronoUnit.HOURS.between(startTime, endTime);
            expirationDate = expirationDate.plusHours(hours);
        }
        Request request = Request.builder().id(requestId).requestContext(requestContent).creationDate(creationDate).expirationDate(expirationDate).build();
        requestRepo.saveAndFlush(request);
        List<User> users = userRepo.findAll();
        for(User user : users){
            UsersRequests usersRequests = UsersRequests.builder().user(user).request(request).requestStatus(RequestStatus.NEW_REQUEST.name()).build();
            usersRequestsRepo.saveAndFlush(usersRequests);
        }

    }

    public String convertMapToString(Map<String, String> map) {
        String mapAsString = map.keySet().stream()
                .map(key -> key + " = " + map.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
        return mapAsString;
    }
}
