package com.example.demo.customer;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerMapperTest {

    @Test
    void mapRow() throws SQLException {
        //Given
        CustomerMapper customerMapper = new CustomerMapper();

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("age")).thenReturn(19);
        when(resultSet.getString("name")).thenReturn("Jamila");
        when(resultSet.getString("email")).thenReturn("jamila@outlook.com");
        when(resultSet.getString("gender")).thenReturn("FEMALE");

        Customer actual = customerMapper.mapRow(resultSet, 1);
        //When
        //Then
        Customer exprcted = new Customer(1,
                "Jamila",
                "jamila@outlook.com",
                "password", 19,
                Gender.FEMALE);
        assertThat(actual).isEqualTo(exprcted);
    }
}