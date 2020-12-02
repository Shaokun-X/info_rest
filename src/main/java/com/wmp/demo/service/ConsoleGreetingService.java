package com.wmp.demo.service;

import org.springframework.stereotype.Service;

// @Service("ConsoleGreetingService")
@Service
// @Primary // this annotaion gives the component the priority to inject when beans duplication occurs
public class ConsoleGreetingService implements GreetingService{
    @Override
    public void greet(String name) {
        System.out.println(name + '!');
    }
}
