package com.example.demo.supplier;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository("sjdbc")
public class SupplierJdbcAccessService implements SupplierDao {


    private final JdbcTemplate jdbcTemplate;
    private final SupplierMapper supplierMapper;

    public SupplierJdbcAccessService(JdbcTemplate jdbcTemplate, SupplierMapper supplierMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.supplierMapper = supplierMapper;
    }

    @Override
    public List<Supplier> selectAllSupplier() {
        var sql = """ 
                select id ,name,contact,address from supplier
                """;
        return jdbcTemplate.query(sql, supplierMapper);
    }

    @Override
    public Optional<Supplier> selectAllSupplierById(Integer id) {
        String sql = "select  name,contact,address from supplier where id=?";
        return jdbcTemplate.query(sql, supplierMapper, id).stream().findFirst();
    }

    @Override
    public boolean existsPersonWithName(String name) {
        String sql = "select count(id) from supplier where name =?";
        Integer integer = jdbcTemplate.queryForObject(sql, Integer.class, name);
        return integer != null && integer > 0;
    }

    @Override
    public boolean existsPersonWithId(Integer id) {
        String sql = "select  count(id) from supplier where id=?";
        Integer integer = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return integer != null && integer > 0;
    }


    //todo deleted informatin function
    @Override
    public void deletedSupplierById(Integer id) {
        String sql = "delete  from  supplier where  id =?";
        int deleted = jdbcTemplate.update(sql, id);
        System.out.println("deletedSupplier result=" + deleted);

    }

    @Override
    public void daletedSupplierByName(String name) {
        String sql = "delete from supplier where name=?";
        int deleted = jdbcTemplate.update(sql, name);
        System.out.println("deletedSupplier result=" + deleted);
    }

    //todo 添加
    @Override
    public void insertSupplier(Supplier supplier) {
        String sql = "insert  into supplier( address, contact, name) VALUES (?,?,?)";
        int update = jdbcTemplate.update(sql,
                supplier.getName(),
                supplier.getAddress(),
                supplier.getContact()
        );
        System.out.println("jdbcTemplate.update=" + update);

    }

    //todo 更新数据 通过id去修改数据
    @Override
    public void updateSupplierById(Supplier supplier) {
        if (supplier.getName() != null) {
            String sql = "update  supplier set name=?where id=?";
            int update = jdbcTemplate.update(sql, supplier.getId(), supplier.getName());
            System.out.println("update supplier name result = " +update);
        }
        if (supplier.getAddress() != null) {
            String sql = "update  supplier set address=?where id=?";
            int update = jdbcTemplate.update(sql, supplier.getId(), supplier.getAddress());
            System.out.println("update supplier address result = " +update);
        }
        if (supplier.getContact() != null) {
            String sql = "update  supplier set contact=?where id=?";
            int update = jdbcTemplate.update(sql, supplier.getId(), supplier.getContact());
            System.out.println("update supplier contact result = " +update);
        }

    }
}
