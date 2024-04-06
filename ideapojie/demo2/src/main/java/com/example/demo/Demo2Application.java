package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//@SpringBootApplication
//@RestController //添置的api的声明
public class Demo2Application {

    //第一种api数据呈现方式
//    public static void main(String[] args) {
//        SpringApplication.run(Demo2Application.class, args);
//    }
    @GetMapping("/") //添置api所需要的过程 是新的是网页输出hello 通过端口路由
    public String greet(){
        return "hello";
//        添置路由路径
    }

//第二种实现网页数据json单独的数据传递方式 单一的json类型饿数据传递
    @GetMapping("/singlejsonrenderingResponse")
    public SinglejsonrenderingResponse singlejsonrendering (){
        return new SinglejsonrenderingResponse("hello");
    }
    record SinglejsonrenderingResponse(String greet){}
    //第二种实现网页输出的方法 这种方式获取的是键值对的呈现方式 返回的是json的数据呈现样式
    @GetMapping("/greetResponse") //带参数的api传递
    public GreetResponse greetResponse(@RequestParam(value ="name",required = false )String name){//上下两个要相同
        String greetMessage = name == null || name.isBlank() ? "Hello":"Hello " +name; //
        return new GreetResponse(greetMessage,
        List.of("java","Golang","Javascript"),
                new Person("Alex",28,30_000)//填方的是第二部分放置的具体的对象数据
        );

    }
    record Person(String name,int age,double savings){ } //第二部分传递的后面具体的一些数据类型 包括可用mongodb进行存储的数据类型 可以包含对象的多种属性数据
    record GreetResponse(String greet, //两部分组成 第一部分 传递的事数据可以用那些语言进行解析
                         List<String> favProgrammingLAnguages,//形成json数据的语言形式
                         Person person //这里存放多个数据 可以用键值对的方式进行扩充
                        ){
    }
//    record GreetResponse(String greet){
//    }这一行的复杂的实现方式  下面的代码是具体的实现方式 实现的就是上述的一行的代码 效果是一样的
//    class GreetResponseTrueFunction{
//        private final String greetresponsetruefunction;
//
//    GreetResponseTrueFunction(String greetresponsetruefunction) {
//        this.greetresponsetruefunction = greetresponsetruefunction;
//    }
//
//    public String getGreetresponsetruefunction() {  //删除get方法会使得返回一个code：406状态码 get是非常装要的
//        return greetresponsetruefunction;
//    }
//
//    @Override
//    public String toString() {
//        return "GreetResponseTrueFunction{" +
//                "greetresponsetruefunction='" + greetresponsetruefunction + '\'' +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        GreetResponseTrueFunction that = (GreetResponseTrueFunction) o;
//        return Objects.equals(greetresponsetruefunction, that.greetresponsetruefunction);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(greetresponsetruefunction);
//    }
//}



}
