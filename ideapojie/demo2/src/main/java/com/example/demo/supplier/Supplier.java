package com.example.demo.supplier;

import jakarta.persistence.*;

import java.util.Objects;

//todo 多表链接还没有进行 先进行单表业务实现
@Entity
@Table(name = "supplier",
        uniqueConstraints = {
                @UniqueConstraint(name = "supplier_name_unqiue",
                        columnNames = "name")
        }
)
public class Supplier {
    @Id
    @SequenceGenerator(
            name = "supplier_id_seq",
            sequenceName = "supplier_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "supplier_id_seq"
    )
    private int id;
    private String name;
    private String address;
    private String contact;
//  private Set<Medicine> medicines;

    public Supplier() {
    }

    public Supplier(String name, String address, String contact) {
        this.name = name;
        this.address = address;
        this.contact = contact;
    }

    public Supplier(int id, String name, String address, String contact) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String sname) {
        this.name = sname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return id == supplier.id && Objects.equals(name, supplier.name) && Objects.equals(address, supplier.address) && Objects.equals(contact, supplier.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, contact);
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
