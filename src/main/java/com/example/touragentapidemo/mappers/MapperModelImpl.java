package com.example.touragentapidemo.mappers;

import com.example.touragentapidemo.dtos.OfferDTO;
import com.example.touragentapidemo.models.Offer;
import com.example.touragentapidemo.models.UsersRequests;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;


@Component
public class MapperModelImpl implements MapperModel {
    private final ModelMapper modelMapper;

    public MapperModelImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public <T, Y> T entityToDTO(Y data, Class<T> entityClass) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(data, entityClass);
    }

    public Offer convertOfferDtoToOffer(Offer offer, OfferDTO offerDTO, UsersRequests usersRequests){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.map(offerDTO, offer);
        offer.setUsersRequests(usersRequests);
//        entity = Offer.builder().
        return offer;
    }

}
