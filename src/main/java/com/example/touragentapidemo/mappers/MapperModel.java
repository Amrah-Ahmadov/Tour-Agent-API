package com.example.touragentapidemo.mappers;


import com.example.touragentapidemo.dtos.OfferDTO;
import com.example.touragentapidemo.models.Offer;
import com.example.touragentapidemo.models.UsersRequests;

public interface MapperModel {
    <T, Y> T entityToDTO(Y data, Class<T> tClass);
    Offer convertOfferDtoToOffer(Offer offer, OfferDTO offerDTO, UsersRequests usersRequests);

}
