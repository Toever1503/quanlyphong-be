package com.services;

import com.config.jwt.JwtLoginResponse;
import com.config.jwt.JwtUserLoginModel;
import com.dtos.UserDto;
import com.entities.UserEntity;
import com.models.RegisterModel;
import com.models.UserModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IUserService extends IBaseService<UserEntity, UserModel, Long, UserDto>{
    boolean signUp(RegisterModel model);

    JwtLoginResponse logIn(JwtUserLoginModel model);

    boolean tokenFilter(String substring, HttpServletRequest req, HttpServletResponse res);
}
