package com.easyshop;

import com.easyshop.model.Sequence;
import com.easyshop.repository.SequenceRepository;
import com.easyshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 22/10/16.
 */
@SpringBootApplication
@EnableTransactionManagement
public class Application {

    @Autowired
    SequenceRepository sequenceRepository;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}