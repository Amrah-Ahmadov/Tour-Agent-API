package com.example.touragentapidemo.services;

import com.example.touragentapidemo.dtos.OfferGetDto;
import com.example.touragentapidemo.dtos.UsersRequestsDto;
import com.example.touragentapidemo.exceptions.NotWorkingHourException;
import com.example.touragentapidemo.mappers.MapperModel;
import com.example.touragentapidemo.models.Offer;
import com.example.touragentapidemo.models.UsersRequests;
import com.example.touragentapidemo.repositories.OfferRepo;
import com.example.touragentapidemo.repositories.RequestRepo;
import com.example.touragentapidemo.repositories.UsersRequestsRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestsService {
    private final RequestRepo requestRepo;
    private final UsersRequestsRepo usersRequestsRepo;
    private final MapperModel mapperModel;
    private final OfferRepo offerRepo;

    @Value("#{ T(java.time.LocalTime).parse('${work-hours.start-time}')}")
    private LocalTime startTime;
    @Value("#{ T(java.time.LocalTime).parse('${work-hours.end-time}')}")
    private LocalTime endTime;

    public RequestsService(RequestRepo requestRepo, UsersRequestsRepo usersRequestsRepo, MapperModel mapperModel, OfferRepo offerRepo) {
        this.requestRepo = requestRepo;
        this.usersRequestsRepo = usersRequestsRepo;
        this.mapperModel = mapperModel;
        this.offerRepo = offerRepo;
    }

    public void ifItsWorkingHour(LocalTime now){
        if(!(now.isAfter(startTime) && now.isBefore(endTime))){
            throw new NotWorkingHourException();
        }
    }
    public List<UsersRequestsDto> getAllUsersRequests(int userId){
        List<UsersRequests> usersRequests = usersRequestsRepo.getAllUsersRequests(userId);
        return usersRequests.stream().map(r -> mapperModel.entityToDTO(r, UsersRequestsDto.class)).collect(Collectors.toList());
    }
    public UsersRequests getUsersRequestsByRequestId(String requestId, int userId){
        return usersRequestsRepo.getUsersRequestsByRequestIdAndUserId(requestId, userId);
    }
    public void saveUsersRequests(UsersRequests usersRequests){
        usersRequestsRepo.saveAndFlush(usersRequests);
    }

    public List<UsersRequestsDto> getAllArchivedUsersRequests(int userId){
        List<UsersRequests> requests = usersRequestsRepo.getAllArchivedUsersRequests(userId);
        return requests.stream().map(r -> mapperModel.entityToDTO(r, UsersRequestsDto.class)).collect(Collectors.toList());
    }
    public List<OfferGetDto> getRequestsThatOfferMade(int userId){
        List<Offer> offers = offerRepo.getAllRequestsThatOfferMade(userId);
        return offers.stream().map(r -> mapperModel.entityToDTO(r, OfferGetDto.class)).collect(Collectors.toList());
    }
    public List<OfferGetDto> getOfferAccepted(int userId){
        List<Offer> offers = offerRepo.getAllAcceptedOffersWithRequests(userId);
        return offers.stream().map(r -> mapperModel.entityToDTO(r, OfferGetDto.class)).collect(Collectors.toList());
    }
}
