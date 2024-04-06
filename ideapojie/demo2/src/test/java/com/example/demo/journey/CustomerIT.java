package com.example.demo.journey;

import com.example.demo.customer.CustomerRegistrationRequest;
import com.example.demo.customer.CustomerUpdateRequest;
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
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)

public class CustomerIT {
    private static final Random RANDOM = new Random();
    private static final String CUSTOMERS_PATH = "/api/v1/customers";
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private JWTUtil jwtUtil;

    @Test
    void canRegisterACustomer() {
        //create registration request
        Faker faker = new Faker();
        var name = faker.name();
        String fullName = name.fullName();
        String email = name.lastName() + "-" + UUID.randomUUID() + "@github.com";
        var age = RANDOM.nextInt(1, 100);
        Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                fullName, email, "password", age, gender
        );
        //send a post request

        String jwtToken = webTestClient.post()
                .uri(CUSTOMERS_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Void.class)
                .getResponseHeaders()
                .get(HttpHeaders.AUTHORIZATION)
                .get(0);

        ;
        // get all customers
        List<CustomerDTO> allCustomers = webTestClient.get()
                .uri(CUSTOMERS_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<CustomerDTO>() {
                })
                .returnResult()
                .getResponseBody();
        // make sure that customer is present
//        Customer expectedCustomer = new Customer(
//                fullName, email, "password", age,
//                gender);


        //get customer by id
        var id = allCustomers.stream()
                .filter(customer -> customer
                        .email()
                        .equals(email))
                .map(CustomerDTO::id)
                .findFirst().orElseThrow();
//        expectedCustomer.setId(id);
        CustomerDTO expectedCustomer = new CustomerDTO(
                id,
                fullName,
                email,
                gender,
                age,
                List.of("ROLE_USER"),
                email
        );
        assertThat(allCustomers)
//                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expectedCustomer);
        //todo
        webTestClient.get()
                .uri(CUSTOMERS_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<CustomerDTO>() {
                }).isEqualTo(expectedCustomer);

    }

    @Test
    void canDeleteCustomer() {
        //crate
        Faker faker = new Faker();
        var name = faker.name();
        String fullName = name.fullName();
        String email = name.lastName() + "-" + UUID.randomUUID() + "@github.com";
        var age = RANDOM.nextInt(1, 100);
        Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                fullName, email, "password", age, gender
        );

        CustomerRegistrationRequest request2 = new CustomerRegistrationRequest(
                fullName, email+".cn", "password", age, gender
        );
        //send a post request to creat customer 1

        webTestClient.post()
                .uri(CUSTOMERS_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

//send a post request to creat customer 2
        String jwtToken = webTestClient.post()
                .uri(CUSTOMERS_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request2), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Void.class)
                .getResponseHeaders()
                .get(HttpHeaders.AUTHORIZATION)
                .get(0);


        // get all customers
        List<CustomerDTO> allCustomers = webTestClient.get()
                .uri(CUSTOMERS_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<CustomerDTO>() {
                })
                .returnResult()
                .getResponseBody();
        // make sure that customer is present


        //get customer by id
        var id = allCustomers.stream()
                .filter(customer -> customer
                        .email()
                        .equals(email))
                .map(CustomerDTO::id)
                .findFirst().orElseThrow();

        //todo+ customer 2 delete customer 1
        webTestClient.delete()
                .uri(CUSTOMERS_PATH + "/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        //todo customer 2 get customer 1 by id
        webTestClient.get()
                .uri(CUSTOMERS_PATH + "/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void canUpdateCustomer() {
        Faker faker = new Faker();
        var name = faker.name();
        String fullName = name.fullName();
        String email = name.lastName() + "-" + UUID.randomUUID() + "@github.com";
        var age = RANDOM.nextInt(1, 100);
        Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                fullName, email, "password", age, gender
        );
        //send a post request

        String s = webTestClient.post()
                .uri(CUSTOMERS_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Void.class)
                .getResponseHeaders()
                .get(HttpHeaders.AUTHORIZATION)
                .get(0);
        // get all customers
        List<CustomerDTO> allCustomers = webTestClient.get()
                .uri(CUSTOMERS_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,String.format("Bearer %s",s))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<CustomerDTO>() {
                })
                .returnResult()
                .getResponseBody();
        // make sure that customer is present
        //get customer by id
        var id = allCustomers.stream()
                .filter(customer -> customer
                        .email()
                        .equals(email))
                .map(CustomerDTO::id)
                .findFirst()
                .orElseThrow();

        //update customer by id
        String newName = "Alex";
        CustomerUpdateRequest updateCustomer = new CustomerUpdateRequest(
                newName
                , null
                , null);
        webTestClient.put().uri(CUSTOMERS_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,String.format("Bearer %s",s))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateCustomer), CustomerUpdateRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        //todo
        CustomerDTO updateCustomerInformation = webTestClient.get()
                .uri(CUSTOMERS_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,String.format("Bearer %s",s))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CustomerDTO.class)
                .returnResult()
                .getResponseBody();
        CustomerDTO expected = new CustomerDTO(
                id, newName, email,  gender,age,List.of("ROLE_USER"),email);
        assertThat(updateCustomerInformation).isEqualTo(expected);
    }
}
