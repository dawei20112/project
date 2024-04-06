package com.example.demo.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository("list")
//接口的具体实现类
public class CustomerListDataAccessService implements CustomerDao {
    //创建模型 创建实体映射类 数据对象模型
    //实现构造方法 有参数和无参数的
    //构建生成equals hash get set 还有 todoString 函数
    private static List<Customer> customers;

    static {
        customers = new ArrayList<>();  //向数据库中添加数据 下面添加的数据个数与对象都与创建的实体类一致  如果添加多个数据对象可以多次在代码上进行重复的对象添加
        Customer alex = new Customer(
                1,
                "Alex",
                "alex@outlook.com",
                "password", 21, Gender.MALE);
        customers.add(alex);

        Customer Jaime = new Customer(
                2,
                "Jaime",
                "Jaime@outlook.com",
                "password", 19, Gender.MALE);
        customers.add(Jaime);
    }
    //        初试数据库


    //方法复写 返回customers
    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomersById(Integer id) {
        return customers.stream().filter(
                        c -> c.getId().equals(id))
                .findFirst();
        //一般这里不会出现异常问题 服务类抛出异常
//                .orElseThrow(() -> new IllegalArgumentException("customer with id [%s] not find".formatted(id)));

    }

    /*实现一个从列表添加数据向数据的静态实现方法*/
    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    /*实现的是若是用户存在那就直接退出*/
    @Override
    public boolean existsPersonWithEmail(String email) {
        return customers.stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }



    /*实现从Dao创建的接口实现方式*/

    @Override
    public boolean existsPersonWithId(Integer id) {
        return customers.stream().anyMatch(c -> c.getId().equals(id));
    }

    /*数据的具体删除实现*/
    @Override
    public void deleteCustomerById(Integer customerId) {
        customers.stream()
                .filter(
                        c -> c.getId().equals(customerId)
                ).findFirst()
                .ifPresent(customers::remove);
    }

    /**
     * list的数据实现方式就是把数据指定覆盖更新
     **/
    @Override
    public void updateCusromerById(Customer updateId) {
        customers.add(updateId);
    }
//登录查询
    @Override
    public Optional<Customer> selectUserByEmail(String email) {
        return customers.stream().filter(c -> c.getUsername().equals(email)).findFirst();
    }
}
