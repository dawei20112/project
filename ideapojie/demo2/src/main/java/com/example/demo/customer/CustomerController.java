package com.example.demo.customer;

import com.example.demo.customer.dto.CustomerDTO;
import com.example.demo.scurityAndJwt.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")

//数值实例化
//控制器文件
public class CustomerController {
    private final CustomerService customerService;
    private final JWTUtil jwtUtil;


    @Autowired
    public CustomerController(CustomerService customerService,
                              JWTUtil jwtUtil) {
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
    }

    //主函数之外构建
   /* @RequestMapping(path = "api/v1/customer",
            method = RequestMethod.GET)//第一个参数映射请求的路径地址 可以改变联机的方式  第二个参数是请求的链接方式*/
    @GetMapping/*("api/v1/customers")*///更短的请求的方式路径配置方发 实现用户的Get请求 实现的还是输出的具体的类型
//    public List<Customer> getCustomers() {
//        return customerService.getAllCustomers();
//    }//原始可用不是DTO的
    public List<CustomerDTO> getCustomers() {
        return customerService.getAllCustomers();
    }

    /*@GetMapping("api/v1/customers/{customersId}")*///不同的客户实现 {}查询数据的根据需求
    @GetMapping("{customersId}")
    //实现错误处理等 客户给一个ID查找若没有就返回一个错误提示
//    public Customer getCustomer(
//            @PathVariable("customersId") Integer customersId) {
//        return customerService.getCustomersById(customersId);

    public CustomerDTO getCustomer(
            @PathVariable("customersId") Integer customersId) {
        return customerService.getCustomersById(customersId);

        //放置的与上面{}内的参数一致
//        Customer customer = customers.stream().filter(
//                        c -> c.id.equals(customersId))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("customer with id [%s] not find".formatted(customersId)));
//        return customer;

    }

    /*建立一个注册的接口信息
     * 与注册表中的数据进行比对*/
    @PostMapping
    public ResponseEntity<?> registerCustomer(
            @RequestBody CustomerRegistrationRequest request) {
        customerService.addCustomer(request);
        String token = jwtUtil.issueToken(request.email(), "ROLE_USER");
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token)
                .build();

    }
    //todo

    /*从数据表删除数据的方法的控制类 调用接口*/
    @DeleteMapping("{customerId}")
    public void deletedCustomer(
            @PathVariable("customerId") Integer customerId) {
        customerService.deletedCustomerById(customerId);
    }

    /*更新数据请求*/
    @PutMapping("{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") Integer customerId,
            @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        customerService.updateCustomerById(customerId);

        customerService.updateCustomerById(customerId, customerUpdateRequest);
    }


}
