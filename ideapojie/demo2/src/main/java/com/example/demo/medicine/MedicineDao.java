package com.example.demo.medicine;

import java.util.List;
import java.util.Optional;

public interface MedicineDao {
    /*查询药品全部信息*/
    List<Medicine> selectAllMedicine();

    Optional<Medicine> selectMedicineById(Integer id);

    boolean existsMedicineWithName(String name);

    boolean existsMedicineWithId(Integer id);
    void deleteMedicineById(Integer medicineId);
    void  updateMedicineById(Medicine updateId);

    void insertMedicine(Medicine medicine);
}
