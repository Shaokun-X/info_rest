package com.wmp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DummyUserService {

    @Autowired
    // @Qualifier("ConsoleGreetingService")
    private GreetingService consoleGreetingService;
    
    // public DummyUserService(GreetingService greetingService) {
    //     this.consoleGreetingService = greetingService;
    // }

    public void greetAll() {
        String[] users = {"Elodie", "Charles"};
        for (String user : users) {
            consoleGreetingService.greet(user);
        }
    }
}
