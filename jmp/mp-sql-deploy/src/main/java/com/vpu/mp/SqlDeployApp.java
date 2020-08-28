package com.vpu.mp;

import com.vpu.mp.db.deploy.DeployDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author lixinguo
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SqlDeployApp implements ApplicationRunner {

    @Autowired
    protected DeployDb deployDb;

    public static void main(String[] args) {
        SpringApplication.run(SqlDeployApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        deployDb.deploy();
    }
}
