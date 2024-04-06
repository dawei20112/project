package com.example.demo.financialTransaction;

import com.example.demo.employee.Employee;
import com.example.demo.employee.EmployeeMapper;
import com.example.demo.medicine.Medicine;
import com.example.demo.medicine.MedicineMapper;
import com.example.demo.supplier.Supplier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FinacialMapper implements RowMapper<FinancialTransaction> {
    @Override
    public FinancialTransaction mapRow(ResultSet rs, int rowNum) throws SQLException {
//        return new Supplier(
//                rs.getInt("id"),
//                rs.getString("name"),
//                rs.getString("contact"),
//                rs.getString("address")
//        );
        return new FinancialTransaction(
                rs.getLong("transaction_id"),
                rs.getDouble("amount"),
                rs.getString("transaction_date"),
                rs.getString("transaction_type"),
                new MedicineMapper().mapRow(rs,rowNum),
                new EmployeeMapper().mapRow(rs, rowNum)

        );
    }
}
