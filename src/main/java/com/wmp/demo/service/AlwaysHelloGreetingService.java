package com.wmp.demo.service;

import org.springframework.stereotype.Service;

// @Service("AlwaysHelloGreetingService") // specify an identifier
@Service
public class AlwaysHelloGreetingService implements GreetingService{
    @Override
    public void greet(String name) {
        System.out.println("hello");
    }
}
