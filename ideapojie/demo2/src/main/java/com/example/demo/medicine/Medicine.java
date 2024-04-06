package com.example.demo.medicine;

import com.example.demo.supplier.Supplier;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(
        name = "medicine",
        uniqueConstraints = {
                @UniqueConstraint(name = "medicine_name_unqiye",
                columnNames = "name")
        }
)
public class Medicine {
    @Id
    @SequenceGenerator(
            name = "medicine_id_seq",
            sequenceName = "medicine_id_seq" ,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "medicine_id_seq"
    )
    private int id;
    @Column(
            nullable = false
    )
    private String name;
    @Column(
            nullable = false
    )
    private String mid;
    @Column(
            nullable = false
    )
    private String date;
    @Column(
            nullable = false
    )
    private int stock;
    @Column(
            nullable = false
    )
    private int price;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    public Medicine() {
    }

    public Medicine(int id, String name, String mid, String date, int stock, int price, Supplier supplier) {
        this.id = id;
        this.name = name;
        this.mid = mid;
        this.date = date;
        this.stock = stock;
        this.price = price;
        this.supplier = supplier;
    }

    public Medicine(String name, String mid, String date, int stock, int price, Supplier supplier) {
        this.name = name;
        this.mid = mid;
        this.date = date;
        this.stock = stock;
        this.supplier = supplier;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicine medicine = (Medicine) o;
        return id == medicine.id && stock == medicine.stock && price == medicine.price && Objects.equals(name, medicine.name) && Objects.equals(mid, medicine.mid) && Objects.equals(date, medicine.date) && Objects.equals(supplier, medicine.supplier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, mid, date, stock, price, supplier);
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mid='" + mid + '\'' +
                ", date='" + date + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                ", supplier=" + supplier +
                '}';
    }
}
