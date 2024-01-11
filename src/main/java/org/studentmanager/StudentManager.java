package org.studentmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentManager {

    /*
     * SpringApplication.run -> is the method to launch an application
     */
    public static void main(String[] args){
        SpringApplication.run(StudentManager.class, args);
    }
}
