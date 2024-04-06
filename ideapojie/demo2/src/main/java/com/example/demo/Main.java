package com.example.demo;


import com.example.demo.customer.Customer;
import com.example.demo.customer.Gender;
import com.example.demo.medicine.Medicine;
import com.example.demo.medicine.MedicineRepository;
import com.example.demo.supplier.Supplier;
import com.github.javafaker.DateAndTime;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import io.micrometer.core.instrument.util.TimeUtils;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


@SpringBootApplication
//@RestController  //处理http请求  主动启动文件
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    /*命令行运行器*/
    @Bean
    /*参数向数据库中添加实体类数据但还是静态注入没有实现接受发送注入数据*/
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository,
                                        MedicineRepository medicineRepository
            , PasswordEncoder passwordEncoder) {
        /*customerRepository.内部有很多jpa的方法调用*/
        return args -> {
            /*用fake创建假的数据加入库中*/
            var faker = new Faker();
            Random random = new Random();
            //药品身份建立
//            Random random = new Random();
            Name name = faker.name();
            String fullName = name.fullName();
            int stock = random.nextInt(0, 1000);
            int price = random.nextInt(0, 99);
            String mid = faker.code().ean13();
            //假的生产日期
            Date past = faker.date().birthday();
//            Date past = faker.date().past(365,TimeUnit.DAYS);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = format.format(past);
            //假的厂家信息
            String supplierName = faker.company().name();
            String supplierAddress = faker.address().toString();
            String supplierContact = faker.phoneNumber().toString();
            Supplier supplier = new Supplier(
                    supplierName,
                    supplierAddress,
                    supplierContact

            );

//todo 药品信息注入
            Medicine medicine = new Medicine(
                    fullName,
                    mid,
                    dateString,
                    stock,
                    price,
                    supplier

            );

            //顾客身份信息建立
//            Name name = faker.name();
            String firstName = name.firstName();
            String lastName = name.lastName();
            int age = random.nextInt(16, 99);
            Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;
            String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@outlook.com";
            Customer customerFaker = new Customer(
                    firstName + " " + lastName,
                    email,
//                    faker.internet().safeEmailAddress(),
                    //设置随机的账户密码 第二个制定账户密码
//                    passwordEncoder.encode(UUID.randomUUID().toString())
                    passwordEncoder.encode("password")
                    , age,
                    gender);

            /**暂时关闭 调用的faker信息注入数据库**/
            //添加药物信息
//            medicineRepository.save(medicine);
//            System.out.println("medicine insert over");
//            customerRepository.save(customerFaker);
//            System.out.println(email);
        };


    }

    /*初始自己写的逻辑组件实现*/
    @Bean //组件管理引入用户定义 @Bean(“”）可以进行重命名的bean操作 bean的存在是为了创建实例之前进行逻辑操作
    public Foo getFoo() {
        return new Foo("bar");
    }

    record Foo(String name) {
    }

    private static void pritBeans(ConfigurableApplicationContext ctx) {

        String[] beanDefinitionNames =
                ctx.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
            //customerController 我们定义的类bean 实现名字
            // customerDataAccessService
            // customerService
        }
    }

}
