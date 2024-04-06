package com.example.demo.financialTransaction;

import com.example.demo.supplier.Supplier;

import java.util.List;
import java.util.Optional;

public interface FiancialDao {
    List<Supplier> selectAllSupplier();

    Optional<Supplier> selectAllSupplierById(Integer id);

    boolean existsPersonWithName(String name);

    void updateSupplierById(Supplier supplier);

    boolean existsPersonWithId(Integer id);

    void deletedSupplierById(Integer id);

    void daletedSupplierByName(String name);

    void insertSupplier(Supplier supplier);
}
