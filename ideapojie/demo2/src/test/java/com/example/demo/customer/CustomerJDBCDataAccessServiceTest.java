package com.example.demo.customer;

import com.example.demo.AbstractTestcontainerUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainerUnitTest {

    private CustomerJDBCDataAccessService customerJDBCDataAccessService;
    private final CustomerMapper customerMapper = new CustomerMapper();

    @BeforeEach
    void setUp() {
        customerJDBCDataAccessService = new CustomerJDBCDataAccessService(
                getJdbcTemplate(),
                customerMapper
        );
    }

    @Test
    void selectAllCustomers() {
        //Given
        Customer customer = new Customer(
                FAKER.name().fullName(),
                FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                "password", 20,
                Gender.MALE);
        customerJDBCDataAccessService.insertCustomer(customer);
        //When
        List<Customer> actual = customerJDBCDataAccessService.selectAllCustomers();

        //Then
        assertThat(actual).isNotEmpty();
    }

    @Test
    void selectCustomersById() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                "password", 20,
                Gender.MALE);
        customerJDBCDataAccessService.insertCustomer(customer);
        var id = customerJDBCDataAccessService.selectAllCustomers()
                .stream().filter(c -> c.getEmail().equals(email)).map(Customer::getId)
                .findFirst().orElseThrow();
        //Given
        //When
        Optional<Customer> actual = customerJDBCDataAccessService.selectCustomersById(id);
        //Then
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void willReturnEmptyWhenselectCustomerById() {
        int id = 0;
        var customer = customerJDBCDataAccessService.selectCustomersById(id);
        assertThat(customer).isEmpty();

    }

    @Test
    void
    insertCustomer() {
        //Given
        //When
        //Then
    }

    @Test
    void existsPersonWithEmail() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(
                name,
                email,
                "password", 20,
                Gender.MALE);
        customerJDBCDataAccessService.insertCustomer(customer);
        boolean actual = customerJDBCDataAccessService.existsPersonWithEmail(email);
        assertThat(actual).isTrue();
        //When
        //Then
    }

    @Test
    void existsPerSonWithEamilReturnsFalesWhenDoesNotExists() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        boolean actualWithEmail = customerJDBCDataAccessService.existsPersonWithEmail(email);
        assertThat(actualWithEmail).isFalse();
    }

    @Test
    void existsPersonWithId() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(
                name,
                email,
                "password", 20,
                Gender.MALE);
        customerJDBCDataAccessService.insertCustomer(customer);

        var actualId = customerJDBCDataAccessService.selectAllCustomers().stream()
                .filter(c -> c.getEmail()
                        .equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        var actual = customerJDBCDataAccessService.existsPersonWithId(actualId);
        assertThat(actual).isTrue();

    }

    @Test
    void existsPersonWithWillReturnFalseWhenIdNotPresent() {
        int id = -1;
        var actual = customerJDBCDataAccessService.existsPersonWithId(id);
        assertThat(actual).isFalse();
    }

    @Test
    void deleteCustomerById() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(
                name,
                email,
                "password", 20,
                Gender.MALE);
        customerJDBCDataAccessService.insertCustomer(customer);
        //Given
        var actualId = customerJDBCDataAccessService.selectAllCustomers().stream()
                .filter(c -> c.getEmail()
                        .equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        customerJDBCDataAccessService.deleteCustomerById(actualId);
        //When
        Optional<Customer> actual = customerJDBCDataAccessService.selectCustomersById(actualId);
        assertThat(actual).isNotPresent();
        //Then
    }

    @Test
    void updateCusromerName() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                "password", 20,
                Gender.MALE);
        customerJDBCDataAccessService.insertCustomer(customer);
        var id = customerJDBCDataAccessService.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        var newName = "foo";
        //when age is name
        Customer update = new Customer();
        update.setId(id);
        update.setName(newName);
        customerJDBCDataAccessService.updateCusromerById(update);
        Optional<Customer> actual = customerJDBCDataAccessService.selectCustomersById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(newName);
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
        //Given
        //When
        //Then
    }

    @Test
    void updateCusromerEamil() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(
                name,
                email,
                "password", 20,
                Gender.MALE);
        customerJDBCDataAccessService.insertCustomer(customer);
        var actualId = customerJDBCDataAccessService.selectAllCustomers().stream()
                .filter(c -> c.getEmail()
                        .equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        var newEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        //when age is name
        Customer update = new Customer();
        update.setId(actualId);
        update.setEmail(newEmail);
        customerJDBCDataAccessService.updateCusromerById(update);
        Optional<Customer> actual = customerJDBCDataAccessService.selectCustomersById(actualId);
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(actualId);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(newEmail);
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
        //Given
        //When
        //Then
    }

    @Test
    void updateCusromerAge() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(
                name,
                email,
                "password", 20,
                Gender.MALE);
        customerJDBCDataAccessService.insertCustomer(customer);
        var actualId = customerJDBCDataAccessService.selectAllCustomers().stream()
                .filter(c -> c.getEmail()
                        .equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
//        var newEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        var newAge = 50;
        //when age is name
        Customer update = new Customer();
        update.setId(actualId);
        update.setAge(newAge);
        customerJDBCDataAccessService.updateCusromerById(update);
        Optional<Customer> actual = customerJDBCDataAccessService.selectCustomersById(actualId);
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(actualId);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(newAge);
        });
        //Given
        //When
        //Then
    }

    @Test
    void willUpdateAllPropertiesCustomer() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(
                name,
                email,
                "password", 20,
                Gender.MALE);
        customerJDBCDataAccessService.insertCustomer(customer);
        var actualId = customerJDBCDataAccessService.selectAllCustomers().stream()
                .filter(c -> c.getEmail()
                        .equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        Customer update = new Customer();
        update.setId(actualId);
        update.setName("foo");
        String newEmail = UUID.randomUUID().toString();
        update.setEmail(newEmail);
        update.setAge(50);
//        update.setGender(Gender.MALE);
        customerJDBCDataAccessService.updateCusromerById(update);
        Optional<Customer> actual = customerJDBCDataAccessService.selectCustomersById(actualId);
//        assertThat(actual).isPresent().hasValue(update);
        assertThat(actual).isPresent().hasValueSatisfying(updated->{
            assertThat(updated.getId()).isEqualTo(actualId);
            assertThat(updated.getGender()).isEqualTo(Gender.MALE);
            assertThat(updated.getName()).isEqualTo("foo");
            assertThat(updated.getEmail()).isEqualTo(newEmail);
            assertThat(updated.getAge()).isEqualTo(50);
        });
    }


    @Test
    void willNotUpdateWhenNothingToUpdate() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(
                name,
                email,
                "password", 20,
                Gender.MALE);
        customerJDBCDataAccessService.insertCustomer(customer);
        var actualId = customerJDBCDataAccessService.selectAllCustomers().stream()
                .filter(c -> c.getEmail()
                        .equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        Customer update = new Customer();
        update.setId(actualId);
        customerJDBCDataAccessService.updateCusromerById(update);

        Optional<Customer> actual = customerJDBCDataAccessService.selectCustomersById(actualId);
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(actualId);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });

    }
}