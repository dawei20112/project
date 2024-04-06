package com.example.demo.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerMapper customerMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerMapper customerMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                select id, name ,email, age,gender,password
                from customer
                """;
        /**
         * 具体的实现方法在customerMapper这个文件中 这里只是进行了静态注入和使用**/
        List<Customer> customers = jdbcTemplate.query(sql, customerMapper);
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomersById(Integer id) {
        var sql = """
                select id, name ,email,age,gender,password
                from customer
                where id = ? 
                """;
        /**简化版本的通过id进行查询**/
        return jdbcTemplate.query(sql, customerMapper, id)
                .stream().findFirst();

        /**原始版本的通过ID进行身份查询**/
        /*
        RowMapper<Customer> customerRowMapper = (rs, rowNum) -> {
            Customer customer = new Customer(
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("age")
            );
            return customer;
        };
        List<Customer> customersSelectById = jdbcTemplate.query(sql, customerRowMapper,id);
        return customersSelectById.stream().findFirst();*/
    }

    /*向数据库中添加数据*/
    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                insert into customer(name,email,password,age ,gender)
                values(?,?,?,?,?)
                """;
        int result = jdbcTemplate.update(sql,
                customer.getName(),
                customer.getEmail(),
                customer.getPassword(),
                customer.getAge(),
                customer.getGender().name()
        );

        System.out.println("jdbcTemplate.update = " + result);

    }

    @Override
/**查询存在的用户通过email进行查找**/
    public boolean existsPersonWithEmail(String email) {
        var sql = """
                select  count(id)
                from customer
                where email = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public boolean existsPersonWithId(Integer id) {
        var sql = """
                select  count(id)
                from customer
                where id = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteCustomerById(Integer customerId) {
        var sql = """
                delete from customer where id = ?
                """;
        int delete = jdbcTemplate.update(sql, customerId);
        System.out.println("deleteCustomerById result = " + delete);

    }

    @Override
    public void updateCusromerById(Customer updateId) {
        if (updateId.getName() != null) {
            var sql = """
                    update   customer 
                    set name =?
                    where id = ?
                     """;
            int update = jdbcTemplate.update(sql,
                    updateId.getName(),
                    updateId.getId()
            );
            System.out.println("update customer name result = " + update);
        }
        if (updateId.getAge() != null) {
            var sql = """
                    update   customer 
                    set age =?
                    where id = ?
                     """;
            int update = jdbcTemplate.update(sql,
                    updateId.getAge(),
                    updateId.getId()
            );
            System.out.println("update customer age result = " + update);

        }
        if (updateId.getEmail() != null) {
            String sql = "update   customer set email = ? where id = ?";
            int update = jdbcTemplate.update(
                    sql,
                    updateId.getEmail(),
                    updateId.getId()
            );
            System.out.println("update customer email result = " + update);

        }

    }

    @Override
    public Optional<Customer> selectUserByEmail(String email) {
//        var sql = """
//                select *
//                from customer
//                where email = ?
//                """;
        String sql="select id,name,password,email,age,gender from customer where email=?";
        /**简化版本的通过id进行查询**/
        return jdbcTemplate.query(sql, customerMapper, email)
                .stream()
                .findFirst();

    }

}
