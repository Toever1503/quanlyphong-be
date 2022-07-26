package com.services.impl;

import com.config.jwt.JwtLoginResponse;
import com.config.jwt.JwtProvider;
import com.config.jwt.JwtUserLoginModel;
import com.dtos.UserDto;
import com.entities.RoleEntity;
import com.entities.UserEntity;
import com.models.RegisterModel;
import com.models.UserModel;
import com.repositories.IRoleRepository;
import com.repositories.IUserRepository;
import com.services.CustomUserDetail;
import com.services.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public UserServiceImpl(IUserRepository userRepository, IRoleRepository roleRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;

        RoleEntity adminRole = RoleEntity.builder()
                .id(1l)
                .roleName(RoleEntity.ADMIN_ROLE)
                .build();

        RoleEntity userRole = RoleEntity.builder()
                .id(2l)
                .roleName(RoleEntity.USER_ROLE)
                .build();
        this.roleRepository.saveAllAndFlush(List.of(adminRole, userRole));
        this.userRepository.saveAllAndFlush(
                List.of(
                        UserEntity.builder()
                                .id(1l)
                                .username("admin")
                                .password(passwordEncoder.encode("123456"))
                                .email("admin@admin.com")
                                .roleSet(Set.of(adminRole, userRole))
                                .build(),
                        UserEntity.builder()
                                .id(2l)
                                .username("user")
                                .password(passwordEncoder.encode("123456"))
                                .email("user@admin.com")
                                .roleSet(Set.of(userRole))
                                .build()
                )
        );
    }

    @Override
    public List<UserDto> findAll() {
        return null;
    }

    @Override
    public Page<UserDto> findAll(Pageable page) {
        return null;
    }

    @Override
    public List<UserDto> findAll(Specification<UserEntity> specs) {
        return null;
    }

    @Override
    public UserDto findOne(Specification<UserEntity> specs) {
        return null;
    }

    @Override
    public Page<UserEntity> findAll(Pageable page, Specification<UserEntity> specs) {
        return null;
    }

    @Override
    public UserDto findById(Long id) {
        return null;
    }

    @Override
    public UserDto add(UserModel model) {
        return null;
    }

    @Override
    public List<UserDto> adds(List<UserModel> model) {
        return null;
    }

    @Override
    public UserDto update(UserModel model) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public boolean deleteByIds(List<Long> ids) {
        return false;
    }


    private UserEntity findByUsername(String username) {
        return userRepository.findByUsernameOrEmail(username, username).orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng có tên: " + username));
    }

    @Override
    public boolean signUp(RegisterModel registerModel) {
        if (this.userRepository.findByUsername(registerModel.getUsername()).isPresent()) {
            throw new RuntimeException("Tên đăng nhập đã được sử dụng!");
        }
        if (this.userRepository.findByEmail(registerModel.getEmail()) != null)
            throw new RuntimeException("Email đã được đăng kí bởi tài khoản khác!");

        Set<RoleEntity> roleEntitySet = new HashSet<>();
        roleEntitySet.add(this.roleRepository.findByRoleName(RoleEntity.USER_ROLE));
        UserEntity user = this.userRepository.saveAndFlush(UserEntity
                .builder()
                .username(registerModel.getUsername())
                .fullName(registerModel.getFullName())
                .password(this.passwordEncoder.encode(registerModel.getPassword()))
                .email(registerModel.getEmail())
                .roleSet(roleEntitySet)
                .build());

        return this.userRepository.saveAndFlush(user) != null;
    }

    @Override
    public JwtLoginResponse logIn(JwtUserLoginModel userLogin) {
        UserEntity user = this.findByUsername(userLogin.getUsername());
        UserDetails userDetail = new CustomUserDetail(user);
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetail, userLogin.getPassword(), userDetail.getAuthorities()));

        long timeValid = userLogin.isRemember() ? 86400 * 7 : 1800l;
        return JwtLoginResponse.builder()
                .token(this.jwtProvider.generateToken(userDetail.getUsername(), timeValid))
                .type("Bearer").authorities(userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .timeValid(timeValid)
                .build();
    }

    @Override
    public boolean tokenFilter(String token, HttpServletRequest req, HttpServletResponse res) {
        try {
            String username = this.jwtProvider.getUsernameFromToken(token);
            CustomUserDetail userDetail = new CustomUserDetail(this.findByUsername(username));
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            req.getSession().setAttribute("object", usernamePasswordAuthenticationToken.getPrincipal());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
