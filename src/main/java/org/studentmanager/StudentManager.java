package org.studentmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
//import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
public class StudentManager {

    public static void main(String[] args){
        //SpringApplication application = new SpringApplication(StudentManager.class);
        //application.setApplicationStartup(new BufferingApplicationStartup(2048));
        SpringApplication.run(StudentManager.class, args);
    }
}
