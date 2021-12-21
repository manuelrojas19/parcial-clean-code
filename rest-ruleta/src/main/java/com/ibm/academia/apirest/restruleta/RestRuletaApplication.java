package com.ibm.academia.apirest.restruleta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RestRuletaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestRuletaApplication.class, args);
    }

}
