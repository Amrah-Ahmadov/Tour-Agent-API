package com.example.touragentapidemo.controllers;

import com.example.touragentapidemo.dtos.OfferDTO;
import com.example.touragentapidemo.dtos.OfferGetDto;
import com.example.touragentapidemo.dtos.RequestDto;
import com.example.touragentapidemo.dtos.UsersRequestsDto;
import com.example.touragentapidemo.enums.RequestStatus;
import com.example.touragentapidemo.exceptions.OfferAlreadyMadeException;
import com.example.touragentapidemo.exceptions.RequestExpiredException;
import com.example.touragentapidemo.mappers.MapperModel;
import com.example.touragentapidemo.models.Offer;
import com.example.touragentapidemo.models.User;
import com.example.touragentapidemo.models.UsersRequests;
import com.example.touragentapidemo.services.MainService;
import com.example.touragentapidemo.services.OfferService;
import com.example.touragentapidemo.services.RequestsService;
import com.example.touragentapidemo.utils.JwtTokenUtil;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {
    private final JwtTokenUtil jwtTokenUtil;
    private final RequestsService requestsService;
    private final MainService service;
    private final OfferService offerService;
    private final MapperModel mapperModel;

    public RequestController(JwtTokenUtil jwtTokenUtil, RequestsService requestsService, MainService service, OfferService offerService, MapperModel mapperModel) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.requestsService = requestsService;
        this.service = service;
        this.offerService = offerService;
        this.mapperModel = mapperModel;
    }

    @GetMapping
    public ResponseEntity<List<UsersRequestsDto>> getAllRequestsForUser(HttpServletRequest request) throws BadHttpRequest {
        User user = jwtTokenUtil.getUserFromToken(request.getHeader("Authorization").replace("Bearer ", ""));
        if (user == null){
            throw new BadHttpRequest();
        }
        return new ResponseEntity<>(requestsService.getAllUsersRequests(user.getId()), HttpStatus.OK);
    }
    @PostMapping("/makeOffer")
    public ResponseEntity<OfferGetDto> sendOffer(HttpServletRequest request, @RequestBody OfferDTO offerDTO) throws BadHttpRequest, OfferAlreadyMadeException, RequestExpiredException {
        User user = jwtTokenUtil.getUserFromToken(request.getHeader("Authorization").replace("Bearer ", ""));
        if (user == null){
            throw new BadHttpRequest();
        }
        UsersRequests usersRequests = requestsService.getUsersRequestsByRequestId(offerDTO.getRequest(), user.getId());
        if(usersRequests.getRequestStatus().equals("OFFER_MADE") || usersRequests.getRequestStatus().equals("ACCEPTED")){
            throw new OfferAlreadyMadeException();
        }else if(usersRequests.getRequestStatus().equals("EXPIRED")){
            throw new RequestExpiredException();
        }
        requestsService.ifItsWorkingHour(LocalTime.now());
        usersRequests.setRequestStatus(RequestStatus.OFFER_MADE.name());
        System.out.println(usersRequests.getId() + " " + usersRequests.getRequest().getRequestContext() + " " + usersRequests.getRequest().getId() + " " + usersRequests.getUser().getName() + " !!!!!!!!!!!!11!!!!!!!1");
        Offer offer = new Offer();
        offer = offerService.convertOfferDtoToOffer(offer, offerDTO, usersRequests);
        offerService.saveOffer(offer);
        System.out.println(offer.getId() + " -id" + offer.getPrice() + " -price"  + offer.getUsersRequests().getId() + " -requests id" + offer.getUsersRequests().getRequest().getId() + " request id"+ " SSSSSSSSSSSss");
        OfferGetDto offerGetDto = mapperModel.entityToDTO(offer, OfferGetDto.class);
        service.putOffersToQueue(offerDTO, usersRequests.getId());
        return new ResponseEntity<>(offerGetDto, HttpStatus.CREATED);
    }
    @PutMapping("/archive/{id}")
    public ResponseEntity<UsersRequestsDto> makeRequestArchived(HttpServletRequest request, @PathVariable String id) throws BadHttpRequest {
        User user = jwtTokenUtil.getUserFromToken(request.getHeader("Authorization").replace("Bearer ", ""));
        if (user == null){
            throw new BadHttpRequest();
        }
        UsersRequests usersRequests = requestsService.getUsersRequestsByRequestId(id, user.getId());
        usersRequests.setArchived(true);
        requestsService.saveUsersRequests(usersRequests);
        return new ResponseEntity<>(mapperModel.entityToDTO(usersRequests, UsersRequestsDto.class), HttpStatus.OK);
    }
    @PutMapping("/unArchive/{id}")
    public ResponseEntity<UsersRequestsDto> unarchiveRequest(HttpServletRequest request, @PathVariable String id) throws BadHttpRequest {
        User user = jwtTokenUtil.getUserFromToken(request.getHeader("Authorization").replace("Bearer ", ""));
        if (user == null){
            throw new BadHttpRequest();
        }
        UsersRequests usersRequests = requestsService.getUsersRequestsByRequestId(id, user.getId());
        usersRequests.setArchived(false);
        requestsService.saveUsersRequests(usersRequests);
        return new ResponseEntity<>(mapperModel.entityToDTO(usersRequests, UsersRequestsDto.class), HttpStatus.OK);
    }
    @GetMapping("/archived")
    public ResponseEntity<List<UsersRequestsDto>> getAllArchivedRequestsForUser(HttpServletRequest request) throws BadHttpRequest {
        User user = jwtTokenUtil.getUserFromToken(request.getHeader("Authorization").replace("Bearer ", ""));
        if (user == null){
            throw new BadHttpRequest();
        }
        return new ResponseEntity<>(requestsService.getAllArchivedUsersRequests(user.getId()), HttpStatus.OK);
    }
    @GetMapping("/madeOffer")
    public ResponseEntity<List<OfferGetDto>> getRequestsThatOfferMade(HttpServletRequest request) throws BadHttpRequest {
        User user = jwtTokenUtil.getUserFromToken(request.getHeader("Authorization").replace("Bearer ", ""));
        if (user == null){
            throw new BadHttpRequest();
        }
        return new ResponseEntity<>(requestsService.getRequestsThatOfferMade(user.getId()), HttpStatus.OK);
    }
    @GetMapping("/showAccepted")
    public ResponseEntity<List<OfferGetDto>> getAcceptedOffer(HttpServletRequest request) throws BadHttpRequest {
        User user = jwtTokenUtil.getUserFromToken(request.getHeader("Authorization").replace("Bearer ", ""));
        if (user == null){
            throw new BadHttpRequest();
        }
        return new ResponseEntity<>(requestsService.getOfferAccepted(user.getId()), HttpStatus.OK);
    }

}
