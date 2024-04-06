package com.example.demo.customer;
/**
 * 对数据库数据的更新请求实现
 * **/
public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age

) {
}
