package com.example.touragentapidemo.services;

import com.example.touragentapidemo.dtos.OfferDTO;
import com.example.touragentapidemo.mappers.MapperModel;
import com.example.touragentapidemo.models.Offer;
import com.example.touragentapidemo.models.UsersRequests;
import com.example.touragentapidemo.repositories.OfferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferService {
    private OfferRepo offerRepo;
    @Autowired
    MapperModel mapperModel;

    public OfferService(OfferRepo offerRepo) {
        this.offerRepo = offerRepo;
    }
    public void saveOffer(Offer offer){
        offerRepo.saveAndFlush(offer);
    }
    public Offer convertOfferDtoToOffer(Offer offer, OfferDTO offerDTO, UsersRequests usersRequests){
        return mapperModel.convertOfferDtoToOffer(offer, offerDTO, usersRequests);
    }
}
