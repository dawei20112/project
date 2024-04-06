package com.example.demo;

import org.springframework.stereotype.Service;

@Service  //构建了一个自我定义的bean组件
public class Fooservice {
    private final Main.Foo foo;

    public Fooservice(Main.Foo foo) {
        this.foo = foo;
        System.out.println();
    }

    String getFooName(){
        return foo.name();
    }
}
