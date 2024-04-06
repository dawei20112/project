package com.example.demo.medicine;

import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.RequestValidationException;
import com.example.demo.exception.ResourceNotFoundException;
//import com.pharmacy_management.execptiom.DuplicateResourceException;
//import com.pharmacy_management.execptiom.RequestValidationException;
//import com.pharmacy_management.execptiom.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {
    private final MedicineDao medicineDao;

    /*先用jpa*/
    public MedicineService(@Qualifier("medicinejdbc") MedicineDao medicineDao) {
        this.medicineDao = medicineDao;
    }

    public List<Medicine> getALLMedicine() {
        List<Medicine> medicines = medicineDao.selectAllMedicine();
        return medicines;
    }

    public Medicine getMedicineById(Integer id) {
        return medicineDao.selectMedicineById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Medicine with id [%s] not find ".formatted(id)
                ));
    }
//todo 添加数据
    public void addMedicine(MedicineRegistrationRequest medicineRegistrationRequest) {
        String name = medicineRegistrationRequest.name();
        if (medicineDao.existsMedicineWithName(name)) {
            throw new DuplicateResourceException(
                    "name already taken"
            );
        }
        Medicine medicine = new Medicine(
                medicineRegistrationRequest.name(),
                medicineRegistrationRequest.mid(),
                medicineRegistrationRequest.date(),
                medicineRegistrationRequest.stock(),
                medicineRegistrationRequest.price(),
                medicineRegistrationRequest.supplier()

        );
        medicineDao.insertMedicine(medicine);
    }

    public void deletedMedicineById(Integer id) {
        if (!medicineDao.existsMedicineWithId(id)) {
            throw new ResourceNotFoundException(
                    "Medicine with id [%s] not found".formatted(id)
            );
        }
        medicineDao.deleteMedicineById(id);
    }

    public void updateMedicineById(Integer id) {
    }

    public void updateMedicineById(Integer id, MedicineUpdateRrquest medicineUpdateRrquest) {
        Medicine medicine = getMedicineById(id);
        boolean changes = false;
        if (medicineUpdateRrquest.name() != null && !medicineUpdateRrquest.name().equals(medicine.getName())) {
            if (medicineDao.existsMedicineWithName(medicineUpdateRrquest.name())) {
                throw new DuplicateResourceException("name already taken");
            }
            medicine.setName(medicineUpdateRrquest.name());
            changes = true;
        }
        if (medicineUpdateRrquest.price() != null && !medicineUpdateRrquest.price().equals(medicine.getPrice())) {
//            medicine.setPrice(medicineUpdateRrquest.price());
            changes = true;
        }
        if (medicineUpdateRrquest.stock() != null && !medicineUpdateRrquest.stock().equals(medicine.getStock())) {
            medicine.setStock(medicineUpdateRrquest.stock());
            changes = true;

        }
        if (medicineUpdateRrquest.mid() != null && !medicineUpdateRrquest.mid().equals(medicine.getMid())) {
            medicine.setMid(medicineUpdateRrquest.mid());
            changes = true;
        }
        if (medicineUpdateRrquest.date() != null && !medicineUpdateRrquest.date().equals(medicine.getDate())) {
//            medicine.setDate(medicineUpdateRrquest.date());
            changes = true;
        }
        if (!changes) {
            throw new RequestValidationException("no date changes found");
        }


        medicineDao.updateMedicineById(medicine);
    }
}
