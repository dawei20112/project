package com.example.demo.customer;

import com.example.demo.customer.dto.CustomerDTO;
import com.example.demo.customer.dto.CustomerDTOMapper;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.RequestValidationException;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
//初始化组件 增加服务配置

/*实现customer服务类*/

@Service   //制定实现方式("要实现的函数名")
//@Component
public class CustomerService {
    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;

    private final CustomerDTOMapper customerDTOMapper;

    /*public CustomerService(@Qualifier("制定要实现的接口的名字")为jpa实现的就是jpa的接口 CustomerDao customerDao) 这个名字在对应的AccessService的@Repository("这里就是要的名字")*/
    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao,
                           CustomerDTOMapper customerDTOMapper, PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.customerDTOMapper = customerDTOMapper;
        this.passwordEncoder = passwordEncoder;
    }


    //todo 一开始能用的 放开就是最基础的
//    public List<Customer> getAllCustomers() {
//        return customerDao.selectAllCustomers();
//    }

    /**
     * DTO版本的信息
     */
    public List<CustomerDTO> getAllCustomers() {
        return customerDao.selectAllCustomers()
                .stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());
    }

    //服务类抛出异常 错误 从而传递出错误的信息  接收来自controller的查询请求 若没有就调用ResourceNotFound这个函数前往错误处理class文件进行错误处理
//    public Customer getCustomersById(Integer id) {
//        return customerDao.selectCustomersById(id)
//                .orElseThrow(() -> new ResourceNotFoundException(
//                        "customer with id [%s] not find".formatted(id)
//                ));
//    }

    /**
     * DTO版本的信息
     */
    public CustomerDTO getCustomersById(Integer id) {
        return customerDao.selectCustomersById(id)
                .map(customerDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not find".formatted(id)
                ));
    }


    /*实现向数据库添加数据 创建一个函数方法实现*/
    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        /*检查有了就退出*/
        String email = customerRegistrationRequest.email();
        if (customerDao.existsPersonWithEmail(email)) {
            throw new DuplicateResourceException(
                    "email already taken"
            );
        }
        /*没有就直接添加*/
        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                passwordEncoder.encode(customerRegistrationRequest.password()),
                customerRegistrationRequest.age(),
                customerRegistrationRequest.gender()
        );
        customerDao.insertCustomer(
                customer
        );

    }

    /*实现从数据库删除数据的函数方法*/
    public void deletedCustomerById(Integer customerId) {
        if (!customerDao.existsPersonWithId(customerId)) {
            throw new ResourceNotFoundException(
                    "customer with id [%s] not found".formatted(customerId)
            );
        }
        /*如果查到了具体的customerId就进行删除*/
        customerDao.deleteCustomerById(customerId);


    }

    /*数据更新函数方法 两个实现方法 */
    public void updateCustomerById(Integer costomerId) {
    }

    public void updateCustomerById(Integer customerId
            , CustomerUpdateRequest customerUpdateRequest) {
        /**逻辑实现还完善**/
//        Customer customer = getCustomersById(customerId);
        Customer customer = customerDao.selectCustomersById(customerId)
//                .map(customerDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not find".formatted(customerId)
                ));
        boolean changes = false;

        if (customerUpdateRequest.name() != null && !customerUpdateRequest.name().equals(customer.getName())) {
            customer.setName(customerUpdateRequest.name());
//            customerDao.insertCustomer(customer);
            changes = true;
        }
        if (customerUpdateRequest.age() != null && !customerUpdateRequest.age().equals(customer.getAge())) {
            customer.setAge(customerUpdateRequest.age());
//            customerDao.insertCustomer();
            changes = true;
        }
        if (customerUpdateRequest.email() != null && !customerUpdateRequest.email().equals(customer.getEmail())) {
            if (customerDao.existsPersonWithEmail(customerUpdateRequest.email())) {
                throw new DuplicateResourceException("email already taken");
            }
            customer.setEmail(customerUpdateRequest.email());
//            customerDao.insertCustomer();
            changes = true;
        }
        if (!changes) {
            throw new RequestValidationException("no date changes found");
        }
        customerDao.updateCusromerById(customer);

    }
}
