package org.kerago.keragobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KeragoBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeragoBackendApplication.class, args);
        System.out.println("working");
    }

}
