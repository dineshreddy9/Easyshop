package com.easyshop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by pavan on 4/14/17.
 */
@Configuration
@ComponentScan(basePackages = "com.easyshop")
@EnableScheduling
public class AppSchedule {
    public static void doTask(){
        System.out.println("We are in App Schedule");
    }
}
