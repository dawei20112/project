package com.example.demo.customer;
/*实现公开记录*/
public record CustomerRegistrationRequest(/*存放的是添加数据的类型数量可多可少*/
        String name,
        String email,
        String password,
        Integer age,
        Gender gender
) {
}
