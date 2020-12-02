package com.wmp.demo;

import com.wmp.demo.service.GreetingService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoApplicationConfig {

    @Bean
    // here again spring can use the variable name to decide which implementation to inject
    public CommandLineRunner greetingCommandLine(GreetingService consoleGreetingService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception{
                consoleGreetingService.greet("hello spring!");
            }
        };
    }
    
}
