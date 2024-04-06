package com.example.demo.auth;

import com.example.demo.customer.Customer;
import com.example.demo.customer.dto.CustomerDTO;
import com.example.demo.customer.dto.CustomerDTOMapper;
import com.example.demo.scurityAndJwt.security.jwt.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    //    设置数据过滤
    private final CustomerDTOMapper customerDTOMapper;

    private final JWTUtil jwtUtil;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 CustomerDTOMapper customerDTOMapper, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.customerDTOMapper = customerDTOMapper;
        this.jwtUtil = jwtUtil;
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
//        开始身份验证
        Customer principal = (Customer) authentication.getPrincipal();
//        创建了一个Token 也发送了一个token
        CustomerDTO customerDTO = customerDTOMapper.apply(principal);
//生成令牌和customerDto
        String token = jwtUtil.issueToken(customerDTO.username(), customerDTO.roles());
        return new AuthenticationResponse(token,customerDTO);
    }
}
