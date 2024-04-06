package com.example.demo.customer;

import com.example.demo.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class CustomerJPADataAccessServiceTest {
    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    @Mock//Spring自带  mock 测试
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        //When
        underTest.selectAllCustomers();
        //Then
        verify(customerRepository).findAll();
    }

    @Test
    void selectCustomersById() {
        //Given
        int id = 1;
        underTest.selectCustomersById(id);
        verify(customerRepository)
                .findById(id);
    }

    @Test
    void insertCustomer() {
        //Given
        Customer alex = new Customer(
                1,
                "alex",
                "alex@outlook.com",
                "password", 20, Gender.MALE);
        underTest.insertCustomer(
                alex);
        //When
        verify(customerRepository).save(alex);
        //Then
    }

    @Test
    void existsPersonWithEmail() {
        //Given
        var email="alex@outlook.com";
        //When
        underTest.existsPersonWithEmail(email);
        //Then
        verify(customerRepository).existsCustomerByEmail(email);
    }

    @Test
    void existsPersonWithId() {
        //Given
        var id = 1;
        underTest.existsPersonWithId(id);
        verify(customerRepository).existsCustomerById(id);
        //When
        //Then
    }

    @Test
    void deleteCustomerById() {
        //Given
        var id =1;
        //When
        underTest.deleteCustomerById(id);
        //Then
        verify(customerRepository).deleteById(id);
    }

    @Test
    void updateCusromerById() {
        Customer customer = new Customer(
                1,
                "Foo",
                "Foo@outlook.com",
                "password", 25,

                Gender.MALE);
        underTest.updateCusromerById(customer);
        verify(customerRepository).save(customer);
        //Given
        //When
        //Then
    }
}