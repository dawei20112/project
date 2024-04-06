package com.example.demo.journey;

import com.example.demo.auth.AuthenticationRequest;
import com.example.demo.auth.AuthenticationResponse;
import com.example.demo.customer.CustomerRegistrationRequest;
import com.example.demo.customer.Gender;
import com.example.demo.customer.dto.CustomerDTO;
import com.example.demo.scurityAndJwt.security.jwt.JWTUtil;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = RANDOM_PORT)

public class AuthenticationIT {
    private static final Random RANDOM = new Random();
    private static final String AUTHENTICATION_PATH = "/api/v1/auth";
    private static final String CUSTOMERS_PATH = "/api/v1/customers";
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private JWTUtil jwtUtil;

    @Test
    void canLogin() {

        //Given
        Faker faker = new Faker();
        var name = faker.name();
        String fullName = name.fullName();
        String email = name.lastName() + "-" + UUID.randomUUID() + "@github.com";
        var age = RANDOM.nextInt(1, 100);
        Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;
        String password = "password";
        String username = email;
        //建立一个用户信息
        CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest(
                fullName, email, password, age, gender
        );
        //send a post request

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                username,
                password
        );
//无用户登录
        webTestClient.post()
                .uri(AUTHENTICATION_PATH + "/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .exchange()
                .expectStatus()
                .isUnauthorized();
//登记用户
        webTestClient.post()
                .uri(CUSTOMERS_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono
                                .just(customerRegistrationRequest),
                        CustomerRegistrationRequest.class
                )
                .exchange()
                .expectStatus()
                .isOk();
//尝试再次登录
        EntityExchangeResult<AuthenticationResponse> result = webTestClient.post()
                .uri(AUTHENTICATION_PATH + "/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(
                        new ParameterizedTypeReference<AuthenticationResponse>() {
                        })
                .returnResult();
        String token = result
                .getResponseHeaders()
                .get(HttpHeaders.AUTHORIZATION)
                .get(0);
        AuthenticationResponse responseBody = result.getResponseBody();
        CustomerDTO customerDTO = responseBody.customerDTO();
//        assertThat(jwtUtil.isTokenValid(
//                token,
//                customerDTO.username())).isTrue();
        assertThat(jwtUtil.isTokenValid(
                token,
                customerDTO.username())).isTrue();

        assertThat(customerDTO.email()).isEqualTo(email);
        assertThat(customerDTO.age()).isEqualTo(age);
        assertThat(customerDTO.name()).isEqualTo(fullName);
        assertThat(customerDTO.username()).isEqualTo(email);
        assertThat(customerDTO.gender()).isEqualTo(gender);
        assertThat(customerDTO.roles()).isEqualTo(List.of("ROLE_USER"));
        //When
        //Then
    }
}
