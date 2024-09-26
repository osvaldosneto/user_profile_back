package com.user.userprofile.controller;

import com.user.userprofile.DTO.UserLoginDTO;
import com.user.userprofile.security.JwtTokenService;
import com.user.userprofile.security.UserSS;
import com.user.userprofile.service.UserDetailServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(HttpServletResponse response, @RequestBody UserLoginDTO userLoginDTO) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword())
        );
        final UserSS userSS = userDetailsService.loadUserByUsername(userLoginDTO.getUsername());
        final String token = jwtTokenService.generateToken(userSS);
        userLoginDTO.setProfileType(userSS.getAuthorities().iterator().next().toString());
        userLoginDTO.setPassword("");
        userLoginDTO.setToken(token);
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.ok(userLoginDTO);
    }

}
