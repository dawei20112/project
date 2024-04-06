package com.example.demo;

import com.example.demo.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*实现数据库的数据交互*/
//@Repository
public interface CustomerRepository//设置实现的对应实体类 若不设定就是通用启动实体类
        extends JpaRepository<Customer, Integer> {

    /*比对数据库通过email进行数据比对*/
    boolean existsCustomerByEmail(String email);

    /*配置数据库的删除请求通过两种方式*/
    boolean existsCustomerById(Integer id);

    Optional<Customer> findCustomerByEmail(String email);
}
