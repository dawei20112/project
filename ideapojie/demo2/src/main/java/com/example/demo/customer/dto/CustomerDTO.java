package com.example.demo.customer.dto;

import com.example.demo.customer.Gender;

import java.util.List;

public record CustomerDTO(
        Integer id,
        String name,
        String email,
        Gender gender,
        Integer age,
        List<String> roles,
        String username
) {

}
