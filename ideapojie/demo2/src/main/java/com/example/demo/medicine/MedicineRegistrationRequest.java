package com.example.demo.medicine;


import com.example.demo.supplier.Supplier;


public record MedicineRegistrationRequest(
        String name,
        String mid,
        String date,
//        BigDecimal price,
        int price,
        Integer stock,
        Supplier supplier

        //todo 后续再说
) {
}
