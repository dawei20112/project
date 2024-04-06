package com.example.demo.medicine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("medicinejdbc")
public class MedicineJDBCAccessService implements MedicineDao {

    private final JdbcTemplate jdbcTemplate;
    private final MedicineMapper medicineMapper;
    private static final Logger log = LoggerFactory.getLogger(MedicineJDBCAccessService.class);


    public MedicineJDBCAccessService(JdbcTemplate jdbcTemplate, MedicineMapper medicineMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.medicineMapper = medicineMapper;
    }

    @Override
    public List<Medicine> selectAllMedicine() {
        var sql = "select medicine.id as id, medicine.name as name, mid, date, stock, price, supplier.id as supplier_id, supplier.name as supplier_name, supplier.address as address, supplier.contact as contact\n" +
                "from medicine\n" +
                "left join supplier\n" +
                "on supplier.id = medicine.supplier_id\n" +
                "order by medicine.id";

        List<Medicine> query;
        try {
            query = jdbcTemplate.query(sql, medicineMapper);
        } catch (Exception e) {
            log.info("Sql error", e);
            throw e;
        }

        System.out.println(query);
        return query;
    }

    @Override
    public Optional<Medicine> selectMedicineById(Integer id) {
        var sql = """
select medicine.id as id, medicine.name as name, mid, date, stock, price, supplier.id as supplier_id, supplier.name as supplier_name, supplier.address as address, supplier.contact as contact
from medicine
left join supplier
on supplier.id = medicine.supplier_id
where id = ?
order by medicine.id
""";
        return jdbcTemplate.query(sql, medicineMapper, id).stream().findFirst();
    }

    @Override
    public void insertMedicine(Medicine medicine) {
        var sql = "insert into medicine(price,stock,date,mid,name,supplier_id)values(?,?,?,?,?,?);";
        int update = jdbcTemplate.update(sql,
                medicine.getPrice(),
                medicine.getStock(),
                medicine.getDate(),
                medicine.getMid(),
                medicine.getName(),
                medicine.getSupplier().getId()
        );
        System.out.println("jdbcTemplate.update =  " + update);
    }

    @Override
    public boolean existsMedicineWithName(String name) {
        var sql = """
                select count(id)
                from medicine
                where name=?;
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name);
        return count != null && count > 0;
    }

    @Override
    public boolean existsMedicineWithId(Integer id) {
        var sql = """
                select count(*)
                from medicine
                where id=?;
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);

        return count != null && count > 0;
    }

    @Override
    public void deleteMedicineById(Integer medicineId) {
        var sql = """
                delete from medicine where id =?;
                """;
        int delete = jdbcTemplate.update(sql, medicineId);
        System.out.println("deleteMedicineByDI result = " + delete);

    }

    // 这个你自己重新写写吧，假设你要更新很多信息的话你自己算算要执行多少条SQL
    // 这个昂，你就自己化身拼SQL小王子吧
    // 所以你猜为什么会有MyBatis这种本质上就是拼SQL的工具
    @Override
    public void updateMedicineById(Medicine updateId) {
        if (updateId.getName() != null) {
            var sql = """
                    update medicine
                    set name =?
                    where id =?;
                    """;
            int update = jdbcTemplate.update(sql
                    , updateId.getName(), updateId.getId());
            System.out.println("update medicine name result = " + update);
        }
        if (updateId.getDate() != null) {
            var sql = """
                    update medicine
                    set date =?
                    where id =?;
                    """;
            int update = jdbcTemplate.update(sql
                    , updateId.getDate(), updateId.getId());
            System.out.println("update medicine date result = " + update);
        }
        if (updateId.getMid() != null) {
            var sql = """
                    update medicine
                    set mid =?
                    where id =?;
                    """;
            int update = jdbcTemplate.update(sql
                    , updateId.getMid(), updateId.getId());
            System.out.println("update medicine mid result = " + update);
        }
        if (updateId.getStock() != 0 ) {
            var sql = """
                    update medicine
                    set stock =?
                    where id =?;
                    """;
            int update = jdbcTemplate.update(sql
                    , updateId.getStock(), updateId.getId());
            System.out.println("update medicine stock result = " + update);
        }
        if (updateId.getPrice() != 0 ) {
            var sql = """
                    update medicine
                    set price =?
                    where id =?;
                    """;
            int update = jdbcTemplate.update(sql
                    , updateId.getPrice(), updateId.getId());
            System.out.println("update medicine price result = " + update);
        }

    }
}
