package com.example.demo.supplier;

import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.RequestValidationException;

import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;

@Service
public class SupplierService {
    private final SupplierDao supplierDao;

    public SupplierService(@Qualifier("sjdbc") SupplierDao supplierDao) {

        this.supplierDao = supplierDao;
    }

    public List<Supplier> getAllSupplier() {
        return supplierDao.selectAllSupplier();
    }

    //todo 根据id查询企业信息
    public Supplier getSupplierById(Integer id) {
        return supplierDao.selectAllSupplierById(id)
                .orElseThrow(() -> new ResolutionException("supplier [%s]is not found".formatted(id))
                );
    }

    //todo 修改企业信息
    public void updateSupplierById(int id) {

    }

    public void updateSupplierById(Integer id, SupplierUpdateRequesr supplierUpdateRequesr) {
        //todo 根据名字进行修改
        Supplier supplier = getSupplierById(id);
        boolean change = false;
        if (supplierUpdateRequesr.name() != null && !supplierUpdateRequesr.name().equals(supplier.getName())) {
            if (supplierDao.existsPersonWithName(supplierUpdateRequesr.name())) {
                throw new DuplicateResourceException("name already taken");
            }
            supplier.setName(supplierUpdateRequesr.name());
            change = true;
        }
        if (supplierUpdateRequesr.address() != null && !supplierUpdateRequesr.address().equals(supplier.getAddress())) {
            supplier.setAddress(supplierUpdateRequesr.address());
            change = true;
        }
        if (supplierUpdateRequesr.contact() != null && !supplierUpdateRequesr.contact().equals(supplier.getContact())) {
            supplier.setContact(supplierUpdateRequesr.contact());
            change = true;
        }
        if (!change) {
            throw new RequestValidationException("no date change");
        }
        supplierDao.updateSupplierById(supplier);
    }

    //todo 添加企业信息
    public void addSupplier(SuppRegistrationRequest suppRegistrationRequest) {
        String name = suppRegistrationRequest.name();
        if (supplierDao.existsPersonWithName(name)){
            throw new DuplicateResourceException("name already taken");
        }
        Supplier supplier = new Supplier(
                suppRegistrationRequest.name(),
                suppRegistrationRequest.address(),
                suppRegistrationRequest.contact()
        );
        supplierDao.insertSupplier(supplier);

    }

    //todo 删除企业信息

    public void deletedSupplierById(Integer id) {
        if (!supplierDao.existsPersonWithId(id)) {
            throw new ResourceNotFoundException("supplier with id [%s] not found".formatted(id));
        }
        supplierDao.deletedSupplierById(id);
    }
    public void deletedSupplierByName(String name){
        if (!supplierDao.existsPersonWithName(name)){
            throw new ResourceNotFoundException("supplier with name[%s] not found".formatted(name));
        }
        supplierDao.daletedSupplierByName(name);
    }
}
