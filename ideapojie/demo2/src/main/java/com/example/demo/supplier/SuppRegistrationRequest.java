package com.example.demo.supplier;

public record SuppRegistrationRequest(
        String name,
        String contact,
        String address
) {
}
