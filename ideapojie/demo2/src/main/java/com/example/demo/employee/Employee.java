package com.example.demo.employee;

//import com.pharmacy_management.manager.Manager;
import jakarta.persistence.*;
import org.apache.catalina.Manager;

import java.util.Objects;

//todo 员工表
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String employeeName;
    private String contactNumber;
//    @ManyToOne
//    @JoinColumn(name = "manager_id")
//    private Manager manager;


    public Employee() {
    }

    public Employee(long id, String employeeName, String contactNumber) {
        this.id = id;
        this.employeeName = employeeName;
        this.contactNumber = contactNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && Objects.equals(employeeName, employee.employeeName) && Objects.equals(contactNumber, employee.contactNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeeName, contactNumber);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", employeeName='" + employeeName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
    }
}
