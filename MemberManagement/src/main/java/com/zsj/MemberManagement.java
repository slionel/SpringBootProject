package com.zsj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author zsj55
 */
@SpringBootApplication
public class MemberManagement {
    public static void main(String[] args) {
        SpringApplication sa = new SpringApplication(MemberManagement.class);
        sa.run(args);
    }
}
