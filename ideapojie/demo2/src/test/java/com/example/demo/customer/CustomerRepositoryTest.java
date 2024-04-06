package com.example.demo.customer;

import com.example.demo.AbstractTestcontainerUnitTest;
import com.example.demo.CustomerRepository;
import com.example.demo.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest //测试springboot的接口
@DataJpaTest
@Import({TestConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestcontainerUnitTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ApplicationContext applicationContext;
//
//    public CustomerRepositoryTest(CustomerRepository customerRepository) {
//        this.customerRepository = customerRepository;
//    }

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
        System.out.println(applicationContext.getBeanDefinitionCount());
    }

    @Test
    void existsCustomerByEmail() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                "password",
                20,
                Gender.MALE);
        customerRepository.save(customer);
        //Given
        //When
        var actual = customerRepository.existsCustomerByEmail(email);
        //Then
        assertThat(actual).isTrue(); //Given
        //When
        //Then
    }

    @Test
    void existsCustomerByEmailWhenEmailNotPersent() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        var actual = customerRepository.existsCustomerByEmail(email);
        //Then
        assertThat(actual).isFalse(); //Given
        //When
        //Then
    }


    @Test
    void existsCustomerById() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                "password", 20,
                Gender.MALE);
        customerRepository.save(customer);
        var id = customerRepository.findAll()
                .stream().filter(c -> c.getEmail().equals(email)).map(c -> c.getId())
                .findFirst().orElseThrow();
        //Given
        //When
        var actual = customerRepository.existsCustomerById(id);
        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerByIdWhenIdNotPresent() {
        var id = -1;
        //Given
        //When
        var actual = customerRepository.existsCustomerById(id);
        //Then
        assertThat(actual).isFalse();
    }
}