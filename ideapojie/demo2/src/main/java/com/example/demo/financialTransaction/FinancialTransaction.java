package com.example.demo.financialTransaction;

import com.example.demo.employee.Employee;
import com.example.demo.medicine.Medicine;
;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

//todo 财政收支表（FinancialTransaction）
@Entity
@Table(name = "financial_transactions")
public class FinancialTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private Double amount;
    private String transactionDate;
    private String transactionType;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    // Other relationships, getters, setters


    public FinancialTransaction() {
    }

    public FinancialTransaction(Long transactionId, Double amount, String transactionDate, String transactionType, Medicine medicine, Employee employee) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.medicine = medicine;
        this.employee = employee;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinancialTransaction that = (FinancialTransaction) o;
        return Objects.equals(transactionId, that.transactionId) && Objects.equals(amount, that.amount) && Objects.equals(transactionDate, that.transactionDate) && Objects.equals(transactionType, that.transactionType) && Objects.equals(medicine, that.medicine) && Objects.equals(employee, that.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, amount, transactionDate, transactionType, medicine, employee);
    }

    @Override
    public String toString() {
        return "FinancialTransaction{" +
                "transactionId=" + transactionId +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                ", transactionType='" + transactionType + '\'' +
                ", medicine=" + medicine +
                ", employee=" + employee +
                '}';
    }
}