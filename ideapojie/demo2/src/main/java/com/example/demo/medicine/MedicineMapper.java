package com.example.demo.medicine;

//import com.pharmacy_management.supplier.Supplier;
//import com.pharmacy_management.supplier.SupplierMapper;
import com.example.demo.supplier.Supplier;
import com.example.demo.supplier.SupplierMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MedicineMapper implements RowMapper<Medicine> {
    @Override
    public Medicine mapRow(ResultSet rs, int rowNum) throws SQLException {
        Medicine medicine = new Medicine(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("date"),
                rs.getString("mid"),
                rs.getInt("stock"),
                rs.getInt("price"),
//                new SupplierMapper().mapRow(rs,rowNum)
                new Supplier(
                        rs.getInt("supplier_id"),
                        rs.getString("supplier_name"),
                        rs.getString("address"),
                        rs.getString("contact")
                )
                );
        return medicine;

//        return medicine;
    }
}
