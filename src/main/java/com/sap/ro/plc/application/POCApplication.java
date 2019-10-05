package com.sap.ro.plc.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.sap.ro.plc.config",
        "com.sap.ro.plc.controller",
        "com.sap.ro.plc.service",
})
public class POCApplication {

    public static void main(String[] args) {
        SpringApplication.run(POCApplication.class, args);
    }

}
