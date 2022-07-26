package com.resources;

import com.config.jwt.JwtLoginResponse;
import com.config.jwt.JwtUserLoginModel;
import com.dtos.ResponseDto;
import com.models.RegisterModel;
import com.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/users")
public class UserResources {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final IUserService userService;

    public UserResources(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("signup")
    public ResponseDto signUpUser(@RequestBody @Valid RegisterModel model) {
        log.info("{Anonymous} is signing up");
        return ResponseDto.of(this.userService.signUp(model), "Signup");
    }

    @PostMapping("login")
    public ResponseDto loginUser(@RequestBody @Valid JwtUserLoginModel model) {
        log.info("{} is logging in system", model.getUsername());
        return ResponseDto.of(this.userService.logIn(model), "Login");
    }

}
