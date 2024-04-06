package com.example.demo.medicine;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
    boolean existsMedicineByName(String name);
    boolean existsMedicineById(Integer id);
}
