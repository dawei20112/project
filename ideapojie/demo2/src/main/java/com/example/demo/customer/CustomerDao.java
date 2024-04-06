package com.example.demo.customer;

import java.util.List;
import java.util.Optional;

//实现调用方法的接口类
/*每添加一个接口类就要添加对应的服务 可以不实现对应的具体的实现方法过程但一定要去服务类中去添加实现的方法*/
public interface CustomerDao {
    List<Customer> selectAllCustomers();
    //调用方法接口
    Optional<Customer> selectCustomersById(Integer id);
    /*调用传递向数据库插入数据的服务接口 出现错误在其他文件进行错误处理*/
    void insertCustomer(Customer customer);
    boolean existsPersonWithEmail(String email);
    /*删除数据的两个接口实现 第一个通过id进行
    * 第二个通过customerId进行数据删除*/
    boolean existsPersonWithId(Integer id);
    void deleteCustomerById(Integer customerId);
    void  updateCusromerById(Customer updateId);

    //用户登录查询信息
    Optional<Customer> selectUserByEmail(String email);
}
