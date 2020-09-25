package com.example.conversion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * CurrencyConvention
 *
 * @author: Xugang Song
 * @create: 2020/9/25
 */
@SpringBootApplication
@EnableFeignClients("com.example.conversion")
@EnableDiscoveryClient
public class CurrencyConventionApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyConventionApplication.class, args);
    }

}
