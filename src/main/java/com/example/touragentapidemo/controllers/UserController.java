package com.example.touragentapidemo.controllers;

import com.example.touragentapidemo.dtos.*;
import com.example.touragentapidemo.exceptions.RepeatPasswordIncorrectException;
import com.example.touragentapidemo.exceptions.UserNotFoundException;
import com.example.touragentapidemo.exceptions.WrongPasswordException;
import com.example.touragentapidemo.models.ConfirmationToken;
import com.example.touragentapidemo.models.User;
import com.example.touragentapidemo.repositories.ConfirmationTokenRepository;
import com.example.touragentapidemo.repositories.UserRepo;
import com.example.touragentapidemo.services.EmailService;
import com.example.touragentapidemo.services.UserService;
import com.example.touragentapidemo.utils.JwtTokenUtil;
import javassist.tools.web.BadHttpRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserService service;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private UserRepo userRepo;

    public UserController(UserService service) {
        this.service = service;
    }


    @PostMapping("/register")
    public ResponseEntity<UserGetDto> insertUser(@RequestBody UserRegistrationDTO registration){
        if(!registration.getRepeatPassword().equals(registration.getPassword())){
            throw new RepeatPasswordIncorrectException();
        }
        User user = service.register(registration);
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        emailService.sendMail(user.getEmail(), "Complete Registration!", "To confirm your account, please click here : " + "http://localhost:8080/api/confirm-account?token=" + confirmationToken.getConfirmationToken());
        confirmationTokenRepository.save(confirmationToken);
        UserGetDto userGetDto = new UserGetDto(user);
        return new ResponseEntity<>(userGetDto, HttpStatus.OK);
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity confirmUserAccount(@RequestParam("token")String confirmationToken){
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if(token != null)
        {
            User user = userRepo.getUserByEmail(token.getUser().getEmail());
            user.setActive(true);
            userRepo.save(user);
            emailService.sendMail(user.getEmail(), "Congratulations","You signed up successfully");
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/forgotPassword")
    public ResponseEntity resetPassword(@RequestParam("email") String userEmail) {
        User user = userRepo.getUserByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException();
        }
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        emailService.sendMail(user.getEmail(), "Set password", "For to set password, please click here : " + "http://localhost:8080/api/forgot-password-confirm?token=" + confirmationToken.getConfirmationToken());
        confirmationTokenRepository.save(confirmationToken);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value="/forgot-password-confirm", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity confirmForgotPassword(@RequestParam("token")String confirmationToken, @RequestBody ForgotPasswordDto dto){
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if(!dto.getPassword().equals(dto.getRepeatPassword())){
            throw new RepeatPasswordIncorrectException();
        }
        if(token != null)
        {
            User user = userRepo.getUserByEmail(token.getUser().getEmail());
            user.setPassword( new BCryptPasswordEncoder().encode(dto.getPassword()));
            userRepo.save(user);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/resetPassword")
    public ResponseEntity setNewPassword(HttpServletRequest request, @RequestBody ResetPasswordDto dto) throws BadHttpRequest {
        User user = jwtTokenUtil.getUserFromToken(request.getHeader("Authorization").replace("Bearer ", ""));

        if (user == null){
            throw new BadHttpRequest();
        }
        if(!dto.getNewPassword().equals(dto.getRepeatNewPassword())){
            throw new RepeatPasswordIncorrectException();
        }else if(user.getPassword().equals(dto.getOldPassword())){
            throw new WrongPasswordException();
        }
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getNewPassword()));
        userRepo.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping("/user")
    public ResponseEntity<UserDto> getUser(HttpServletRequest request) throws BadHttpRequest {
        User user = jwtTokenUtil.getUserFromToken(request.getHeader("Authorization").replace("Bearer ", ""));
        if (user == null){
            throw new BadHttpRequest();
        }
        return new ResponseEntity(new UserDto(user), HttpStatus.OK);
    }
}
