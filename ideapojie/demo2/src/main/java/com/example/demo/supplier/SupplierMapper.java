package com.example.demo.supplier;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SupplierMapper implements RowMapper<Supplier> {
    @Override
    public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Supplier(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("contact"),
                rs.getString("address")
        );
    }
}
