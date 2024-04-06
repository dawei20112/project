package com.example.demo.customer;

import com.example.demo.customer.dto.CustomerDTO;
import com.example.demo.customer.dto.CustomerDTOMapper;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.RequestValidationException;
import com.example.demo.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private CustomerDao customerDao;
    @Mock
    private PasswordEncoder passwordEncoder;
    //todo 暂时加入

    private CustomerDTOMapper customerDTOMapper =new CustomerDTOMapper();
    private CustomerService underTest;

    @BeforeEach
    void setUp() {
//        AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerService(customerDao, customerDTOMapper, passwordEncoder);
    }

    @Test
    void getAllCustomers() {
        //Given
        //When
        underTest.getAllCustomers();
        //Then
        verify(customerDao).selectAllCustomers();
    }


    @Test
    void canGetCustomersById() {
        var id = 10;
        Customer customer = new Customer(
                id,
                "alex",
                "alex@outlook.com",
                "password", 20,
                Gender.MALE);
        when(customerDao.selectCustomersById(id))
                .thenReturn(Optional.of(customer));
        //Given
//        Customer actual = underTest.getCustomersById(10);
        CustomerDTO expected= customerDTOMapper.apply(customer);
        CustomerDTO actual =underTest.getCustomersById(id);
        //When
        assertThat(actual).isEqualTo(expected);
        //Then
//        verify(customerDao).selectCustomersById(id);
    }

    @Test
    void canNotGetCustomersById() {
        int id = 10;
        when(customerDao.selectCustomersById(id))
                .thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> underTest.getCustomersById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(
                        "customer with id [%s]".formatted(id));
//        verify(customerDao).selectCustomersById(id);
    }

    /*添加客户的测试*/
    @Test
    void addCustomer() {
        String email = "alex@outlook.com";
        //Given
        //When
        when(customerDao.existsPersonWithEmail(email)).thenReturn(false);
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "alex", email, "password", 25,Gender.FEMALE
        );
        String passwordHash="¢5554ml;f;lsd";
        when(passwordEncoder.encode(request.password())).thenReturn(passwordHash);
        underTest.addCustomer(request);
        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor
                .forClass(Customer.class);
        verify(customerDao).insertCustomer(customerArgumentCaptor.capture());

        Customer argumentCaptorValue = customerArgumentCaptor.getValue();
        assertThat(argumentCaptorValue.getId()).isNull();
        assertThat(argumentCaptorValue.getName()).isEqualTo(request.name());
        assertThat(argumentCaptorValue.getEmail()).isEqualTo(request.email());
        assertThat(argumentCaptorValue.getAge()).isEqualTo(request.age());
        assertThat(argumentCaptorValue.getPassword()).isEqualTo(passwordHash);

    }

    @Test
    void canNotAddCustomer() {
        String email = "alex@outlook.com";
        //Given
        //When
        when(customerDao.existsPersonWithEmail(email)).thenReturn(true);
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "alex", email, "password", 25,Gender.FEMALE
        );
        assertThatThrownBy(() -> underTest.addCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("email already taken");
        //Then

        verify(customerDao, never()).insertCustomer(any());
    }

    @Test
    void deletedCustomerById() {
        //Given
        var id = 10;
        when(customerDao.existsPersonWithId(id))
                .thenReturn(true);
        underTest.deletedCustomerById(id);
        verify(customerDao).deleteCustomerById(id);
        //When
        //Then
    }

    @Test
    void willThroeDeletedCustomerByIdNotFound() {
        //Given
        var id = 10;
        when(customerDao.existsPersonWithId(id))
                .thenReturn(false);
        assertThatThrownBy(() -> underTest.deletedCustomerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer with id [%s] not found".formatted(id)
                );
        verify(customerDao, never()).deleteCustomerById(id);
        //When
        //Then
    }

    @Test
    void updateCustomerById() {
        //Given
        var id = 10;
        Customer alex = new Customer(
                id,
                "alex",
                "alex@outlook.com",
                "password", 20,
                Gender.MALE);
        when(customerDao.selectCustomersById(id)).thenReturn(Optional.of(alex));
        //Whennew
        String email = "Alexandro@github.com";
        CustomerUpdateRequest alexandro = new CustomerUpdateRequest("Alexandro",
                email,
                35);
        when(customerDao.existsPersonWithEmail(email)).thenReturn(false);
        underTest.updateCustomerById(id, alexandro);

        //Then
        ArgumentCaptor<Customer> argumentCaptor =
                ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCusromerById(argumentCaptor.capture());
        Customer captorValue = argumentCaptor.getValue();
        assertThat(captorValue.getName()).isEqualTo(alexandro.name());
        assertThat(captorValue.getEmail()).isEqualTo(alexandro.email());
        assertThat(captorValue.getAge()).isEqualTo(alexandro.age());
    }

    @Test
    void canUpdateOnlyCustomerNameById() {
        //Given
        var id = 10;
        Customer alex = new Customer(
                id,
                "alex",
                "alex@outlook.com",
                "password", 20,
                Gender.MALE);
        when(customerDao.selectCustomersById(id)).thenReturn(Optional.of(alex));
        //Whennew
//        String email = "Alexandro@github.com";
        CustomerUpdateRequest alexandro = new CustomerUpdateRequest("Alexandro",
                null,
                null);
//        when(customerDao.existsPersonWithEmail(email)).thenReturn(false);
        underTest.updateCustomerById(id, alexandro);

        //Then
        ArgumentCaptor<Customer> argumentCaptor =
                ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCusromerById(argumentCaptor.capture());
        Customer captorValue = argumentCaptor.getValue();

        assertThat(captorValue.getName()).isEqualTo(alexandro.name());
        assertThat(captorValue.getEmail()).isEqualTo(alex.getEmail());
        assertThat(captorValue.getAge()).isEqualTo(alex.getAge());
    }

    @Test
    void canUpdateOnlyCustomerEmailById() {
        //Given
        var id = 10;
        Customer alex = new Customer(
                id,
                "alex",
                "alex@outlook.com",
                "password", 20,
                Gender.MALE);
        when(customerDao.selectCustomersById(id)).thenReturn(Optional.of(alex));
        //Whennew
        String email = "Alexandro@github.com";
        CustomerUpdateRequest alexandro = new CustomerUpdateRequest(null,
                email,
                null);
        when(customerDao.existsPersonWithEmail(email)).thenReturn(false);
        underTest.updateCustomerById(id, alexandro);

        //Then
        ArgumentCaptor<Customer> argumentCaptor =
                ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCusromerById(argumentCaptor.capture());
        Customer captorValue = argumentCaptor.getValue();

        assertThat(captorValue.getName()).isEqualTo(alex.getName());
        assertThat(captorValue.getEmail()).isEqualTo(alexandro.email());
        assertThat(captorValue.getAge()).isEqualTo(alex.getAge());
    }

    @Test
    void canUpdateOnlyCustomerAgeById() {
        //Given
        var id = 10;
        Customer alex = new Customer(
                id,
                "alex",
                "alex@outlook.com",
                "password", 20,
                Gender.MALE);
        when(customerDao.selectCustomersById(id)).thenReturn(Optional.of(alex));
        //Whennew
//        String email = "Alexandro@github.com";
        CustomerUpdateRequest alexandro = new CustomerUpdateRequest(null,
                null,
                50);
//        when(customerDao.existsPersonWithEmail(email)).thenReturn(false);
        underTest.updateCustomerById(id, alexandro);

        //Then
        ArgumentCaptor<Customer> argumentCaptor =
                ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCusromerById(argumentCaptor.capture());
        Customer captorValue = argumentCaptor.getValue();

        assertThat(captorValue.getName()).isEqualTo(alex.getName());
        assertThat(captorValue.getEmail()).isEqualTo(alex.getEmail());
        assertThat(captorValue.getAge()).isEqualTo(alexandro.age());
    }

    @Test
    void willThrowTryingUpdateCustomerByIdWhenEmailAlreadyTaken() {
        //Given
        var id = 10;
        Customer alex = new Customer(
                id,
                "alex",
                "alex@outlook.com",
                "password", 20,
                Gender.MALE);
        when(customerDao.selectCustomersById(id)).thenReturn(Optional.of(alex));
        //Whennew
        String email = "Alexandro@github.com";
        CustomerUpdateRequest alexandro = new CustomerUpdateRequest("Alexandro",
                email,
                35);
        when(customerDao.existsPersonWithEmail(email)).thenReturn(true);
        assertThatThrownBy(() -> underTest.updateCustomerById(id, alexandro))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("email already taken");
        verify(customerDao, never()).updateCusromerById(any());
        //Then
//        ArgumentCaptor<Customer> argumentCaptor =
//                ArgumentCaptor.forClass(Customer.class);
//        verify(customerDao).updateCusromerById(argumentCaptor.capture());

//        Customer captorValue = argumentCaptor.getValue();
//        assertThat(captorValue.getName()).isEqualTo(alexandro.name());
//        assertThat(captorValue.getEmail()).isEqualTo(alexandro.email());
//        assertThat(captorValue.getAge()).isEqualTo(alexandro.age());
    }

    @Test
    void willThrowWhenCustomerUpdateHasNOChanges() {
        //Given
        var id = 10;
        Customer alex = new Customer(
                id,
                "alex",
                "alex@outlook.com",
                "password", 20,
                Gender.MALE);
        when(customerDao.selectCustomersById(id)).thenReturn(Optional.of(alex));
        //Whennew
        CustomerUpdateRequest alexandro = new CustomerUpdateRequest(alex.getName(),
                alex.getEmail(), alex.getAge());
        assertThatThrownBy(
                () -> underTest.updateCustomerById(id, alexandro)
        ).isInstanceOf(RequestValidationException.class)
                .hasMessage("no date changes found");
        //Then
        verify(customerDao, never()).updateCusromerById(any());

    }
}