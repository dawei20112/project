package com.example.demo.supplier;

import com.example.demo.customer.CustomerRegistrationRequest;
import com.example.demo.customer.CustomerService;
import com.example.demo.customer.CustomerUpdateRequest;
import com.example.demo.customer.dto.CustomerDTO;
import com.example.demo.scurityAndJwt.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/supplier")

//数值实例化
//控制器文件
public class SupplierController {
//    private final CustomerService customerService;
    private final SupplierService supplierService;
    private final JWTUtil jwtUtil;


    @Autowired
    public SupplierController(CustomerService customerService,
                              SupplierService supplierService, JWTUtil jwtUtil) {
//        this.customerService = customerService;
        this.supplierService = supplierService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping/*("api/v1/customers")*/

    public List<Supplier> getSupplier() {
        return supplierService.getAllSupplier();
    }

    /*@GetMapping("api/v1/customers/{customersId}")*///不同的客户实现 {}查询数据的根据需求
    @GetMapping("{id}")
    public Supplier getSupplier(
            @PathVariable("id") Integer customersId) {
        return supplierService.getSupplierById(customersId);
    }

    /*建立一个注册的接口信息
     * 与注册表中的数据进行比对*/
    @PostMapping
    public void registerSupplier(
            @RequestBody SuppRegistrationRequest request) {
        supplierService.addSupplier(request);
//        String token = jwtUtil.issueToken(request.email(), "ROLE_USER");
//        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token)
//                .build();

    }
    //todo

    /*从数据表删除数据的方法的控制类 调用接口*/
    @DeleteMapping("{id}")
    public void deletedSupplier(
            @PathVariable("id") Integer customerId) {
        supplierService.deletedSupplierById(customerId);
    }

    /*更新数据请求*/
    @PutMapping("{id}")
    public void updateSupplier(
            @PathVariable("id") Integer customerId,
            @RequestBody SupplierUpdateRequesr customerUpdateRequest) {
        supplierService.updateSupplierById(customerId);

        supplierService.updateSupplierById(customerId, customerUpdateRequest);
    }


}
