package com.example.demo.auth;

import com.example.demo.customer.dto.CustomerDTO;

public record AuthenticationResponse(
        String token,
        CustomerDTO customerDTO) {

}
