package com.example.demo.medicine;

public record MedicineUpdateRrquest(
        String name,
        String mid,
        String date,
        Integer price,
        Integer stock
) {
}
