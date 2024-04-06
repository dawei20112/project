package com.example.demo.medicine;

//import com.pharmacy_management.jwt.JWTUtil;

import com.example.demo.scurityAndJwt.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/medicine")
public class MedicineController {

    private final MedicineService medicineService;

    @Autowired
    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    /*查找全部*/
    @GetMapping
    public List<Medicine> getMedicine() {
        List<Medicine> allMedicine = medicineService.getALLMedicine();
        return allMedicine;
    }

    /*id查找*/
    @GetMapping("{medicibeId}")
    public Medicine getMedicineById(@PathVariable("medicibeId") Integer medicibeId) {
        return medicineService.getMedicineById(medicibeId);
    }

    /*添加数据*/
    /*建立一个注册的接口信息
     * 与注册表中的数据进行比对*/
    //todo 添加药品信息
//    @PostMapping
//    public ResponseEntity<?> addMedicine(
//            @RequestBody MedicineRegistrationRequest request
//    ) {
//        medicineService.addMedicine(request);
//        String token = JWTUtil.issueToken(request.name(), "ROLE_USER");
//        return ResponseEntity.ok()
//                .header(HttpHeaders.AUTHORIZATION, token)
//                .build();
//        return null;
//    }
    @PostMapping
    public void addedMicine(MedicineRegistrationRequest request) {
        medicineService.addMedicine(request);
    }

    /*删除*/
    @DeleteMapping("{medicineId}")
    public void deleteMedicine(
            @PathVariable("medicineId") Integer medicine
    ) {
        medicineService.deletedMedicineById(medicine);
    }

    //todo/*该数据还是有问题*/
    @PutMapping("{Id}")
    public void updateMedicineById(
            @PathVariable("Id") Integer medicineId,
            @RequestBody MedicineUpdateRrquest medicineUpdateRrquest) {
        medicineService.updateMedicineById(medicineId);
        medicineService.updateMedicineById(medicineId, medicineUpdateRrquest);
    }


}
