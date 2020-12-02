package com.wmp.demo.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


// // Injection through constructor
// public class GreetingServiceTest {

//     private GreetingService _greetingservice;

//     public void GreetingServiceTest(ConsoleGreetingService service) {
//         _greetingservice = service;
//     }

//     @Test
//     public void testGreeting(CapturedOutput output) {
//         consoleGreetingService.greet("hello");
//         Assertions.assertThat(output.getAll()).contains("hello!");    }
// }


@ExtendWith(OutputCaptureExtension.class)
@ExtendWith(SpringExtension.class)
public class GreetingServiceTest {
    
    @Configuration
    @ComponentScan("com.wmp.demo.service")
    public static class UserServiceTestConfig { }

    @Autowired
    // @Qualifier("ConsoleGreetingService")
    // @Autowired can disinguish the beans according to field name
    // for example, if this field is named `greetingService` aurowired would fail
    private GreetingService consoleGreetingService;
    
    @Test
    public void testGreeting(CapturedOutput output) {
        consoleGreetingService.greet("hello");
        Assertions.assertThat(output.getAll()).contains("hello!");    }
}
