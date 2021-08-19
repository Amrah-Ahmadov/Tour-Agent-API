package com.example.touragentapidemo;

import com.example.touragentapidemo.dtos.OfferGetDto;
import com.example.touragentapidemo.dtos.UsersRequestsDto;
import com.example.touragentapidemo.models.Offer;
import com.example.touragentapidemo.models.Request;
import com.example.touragentapidemo.models.User;
import com.example.touragentapidemo.models.UsersRequests;
import com.example.touragentapidemo.repositories.OfferRepo;
import com.example.touragentapidemo.repositories.RequestRepo;
import com.example.touragentapidemo.repositories.UsersRequestsRepo;
import com.example.touragentapidemo.services.RequestsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class RequestServicesTest {
    @Autowired
    private RequestsService requestsService;
    @MockBean
    private OfferRepo offerRepo;
    @MockBean
    private UsersRequestsRepo usersRequestsRepo;

    @Test
    @DisplayName("Test getUsersRequestsByRequestId Success")
    void getUsersRequestsByRequestId(){
        User user = new User(1, "Company555", "Anar", "Ahmadov", "123456789101112", "anar@gmail.com", "123456", true, LocalDateTime.of(2021, Month.APRIL, 3,6,30,40,50000));
        Request request = new Request();
        request.setId("11ae11c1-d1e1-111d-1111-f111f1dbedd1");
        UsersRequests usersRequests = new UsersRequests();
        usersRequests.setUser(user);
        usersRequests.setRequest(request);
        doReturn(usersRequests).when(usersRequestsRepo).getUsersRequestsByRequestIdAndUserId("11ae11c1-d1e1-111d-1111-f111f1dbedd1", 1);
        UsersRequests returnedUsersRequests = requestsService.getUsersRequestsByRequestId("11ae11c1-d1e1-111d-1111-f111f1dbedd1", 1);

        Assertions.assertSame(returnedUsersRequests, usersRequests, "The usersRequests returned was not the same as the mock");
    }
    @Test
    @DisplayName("Test GetAllUsersRequests")
    void getAllUsersRequests(){
        User user2 = new User(2, "Company666", "Yusif", "Ahmadov", "583928459101111", "yusif@gmail.com", "123456", true, LocalDateTime.of(2021, Month.APRIL, 3,6,30,40,50000));
        Request request1 = new Request();
        Request request2 = new Request();
        request1.setId("22ae22a2-d2e2-222d-1111-f111f1dbedd1");
        request1.setId("33ae33a3-d3e3-333d-33333-f111f1dbedd3");
        UsersRequests usersRequests1 = new UsersRequests();
        UsersRequests usersRequests2 = new UsersRequests();
        usersRequests1.setUser(user2);
        usersRequests1.setRequest(request1);
        usersRequests2.setUser(user2);
        usersRequests2.setRequest(request2);

        doReturn(Arrays.asList(usersRequests1, usersRequests2)).when(usersRequestsRepo).getAllUsersRequests(2);
        List<UsersRequestsDto> usersRequestsList = requestsService.getAllUsersRequests(2);
        Assertions.assertEquals(2, usersRequestsList.size(), "getAllUsersRequests should return 2 usersRequests");
    }
    @Test
    @DisplayName("Test getAllArchivedUsersRequests")
    void getAllArchivedUsersRequests(){
        User user = new User(2, "Company666", "Yusif", "Ahmadov", "583928459101111", "yusif@gmail.com", "123456", true, LocalDateTime.of(2021, Month.APRIL, 3,6,30,40,50000));
        Request request = new Request();
        request.setId("55ae55a5-d5e5-555d-1111-f111f1dbedd1");
        UsersRequests usersRequests = new UsersRequests();
        usersRequests.setUser(user);
        usersRequests.setRequest(request);

        doReturn(Arrays.asList(usersRequests)).when(usersRequestsRepo).getAllArchivedUsersRequests(2);
        List<UsersRequestsDto> usersRequestsList = requestsService.getAllArchivedUsersRequests(2);
        Assertions.assertEquals(1, usersRequestsList.size(), "getAllArchivedUsersRequests should return 1 usersRequests");

    }
    @Test
    @DisplayName("Test getRequestsThatOfferMade")
    void getRequestsThatOfferMade(){
        User user2 = new User(2, "Company666", "Yusif", "Ahmadov", "583928459101111", "yusif@gmail.com", "123456", true, LocalDateTime.of(2021, Month.APRIL, 3,6,30,40,50000));
        Request request1 = new Request();
        Request request2 = new Request();
        request1.setId("22ae22a2-d2e2-222d-1111-f111f1dbedd1");
        request1.setId("33ae33a3-d3e3-333d-33333-f111f1dbedd3");
        UsersRequests usersRequests1 = new UsersRequests();
        UsersRequests usersRequests2 = new UsersRequests();
        usersRequests1.setUser(user2);
        usersRequests1.setRequest(request1);
        usersRequests2.setUser(user2);
        usersRequests2.setRequest(request2);
        Offer offer1 = new Offer(1L, "200 manat", "03/09/2021 ve 03/10/2021 tarixleri araliginda olacaq", "seher ve gunorta yemeyi daxildir", usersRequests1);
        Offer offer2 = new Offer(2L, "300 manat", "15/09/2021 ve 15/10/2021 tarixleri araliginda olacaq", "seher ve gunorta yemeyi daxildir", usersRequests2);

        doReturn(Arrays.asList(offer1, offer2)).when(offerRepo).getAllRequestsThatOfferMade(2);
        List<OfferGetDto> offers = requestsService.getRequestsThatOfferMade(2);
        Assertions.assertEquals(2, offers.size(), "getRequestsThatOfferMade should return 2 offers");
    }
    @Test
    @DisplayName("Test getOfferAccepted")
    void getOfferAccepted(){
        User user2 = new User(3, "Company666", "Etibar", "Ahmadov", "583928459101111", "etibar@gmail.com", "123456", true, LocalDateTime.of(2021, Month.JULY, 3,6,30,40,50000));
        Request request1 = new Request();
        Request request2 = new Request();
        request1.setId("55ae55a5-a5e5-555d-5555-a555f5aaaaa5");
        request1.setId("66ae66a6-a6a6-333d-33333-f111f1aaaa3");
        UsersRequests usersRequests1 = new UsersRequests();
        UsersRequests usersRequests2 = new UsersRequests();
        usersRequests1.setUser(user2);
        usersRequests1.setRequest(request1);
        usersRequests2.setUser(user2);
        usersRequests2.setRequest(request2);
        Offer offer1 = new Offer(1L, "500 manat", "03/09/2021 ve 14/10/2021 tarixleri araliginda olacaq", "seher ve gunorta yemeyi daxildir", usersRequests1);
        Offer offer2 = new Offer(2L, "600 manat", "15/09/2021 ve 15/10/2021 tarixleri araliginda olacaq", "seher ve gunorta yemeyi daxildir", usersRequests2);

        doReturn(Arrays.asList(offer1, offer2)).when(offerRepo).getAllAcceptedOffersWithRequests(3);
        List<OfferGetDto> offers = requestsService.getOfferAccepted(3);
        Assertions.assertEquals(2, offers.size(), "getOfferAccepted should return 2 offers");
    }

}
