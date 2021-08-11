package com.example.touragentapidemo.services;

import com.example.touragentapidemo.dtos.OfferDTO;
import com.example.touragentapidemo.mappers.MapperModel;
import com.example.touragentapidemo.models.Offer;
import com.example.touragentapidemo.models.UsersRequests;
import com.example.touragentapidemo.repositories.OfferRepo;
import org.springframework.stereotype.Service;

@Service
public class OfferService {
    private final OfferRepo offerRepo;
    private final MapperModel mapperModel;

    public OfferService(OfferRepo offerRepo, MapperModel mapperModel) {
        this.offerRepo = offerRepo;
        this.mapperModel = mapperModel;
    }
    public void saveOffer(Offer offer){
        offerRepo.saveAndFlush(offer);
    }
    public Offer convertOfferDtoToOffer(Offer offer, OfferDTO offerDTO, UsersRequests usersRequests){
        return mapperModel.convertOfferDtoToOffer(offer, offerDTO, usersRequests);
    }
}
