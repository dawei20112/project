package com.example.demo.customer;

import com.example.demo.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class CustomerJPADataAccessService implements CustomerDao {
    private final CustomerRepository customerRepository;

    public CustomerJPADataAccessService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        /*
        //访问全部数据练习老黑教的 但是没有实现具体实现内容看后面
        * */
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> selectCustomersById(Integer id) {

        return customerRepository.findById(id);
    }


    /*实现jpa的数据插入方式函数   实现dao服务接口类
    jpa的语法事项是。Save（）注意注意 jpa的实现逻辑
    */
    @Override
    public void insertCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    /*实现通过email来确定数据表中是都有对应的数据项*/
    @Override
    public boolean existsPersonWithEmail(String email) {
        return customerRepository.existsCustomerByEmail(email);

    }

    /*实现删除的两个jpa方法一个是ID一个客户ID*/
    @Override
    public boolean existsPersonWithId(Integer id) {
        return customerRepository.existsCustomerById(id);
    }

    /*存在数据唯一性的直接删除方式*/
    @Override
    public void deleteCustomerById(Integer customerId) {
        customerRepository.deleteById(customerId);
    }

    /*实现数据更新jpa*/
    @Override
    public void updateCusromerById(Customer updateId) {
        customerRepository.save(updateId);
    }

    @Override
    public Optional<Customer> selectUserByEmail(String email) {
        return customerRepository.findCustomerByEmail(email);
    }
}
