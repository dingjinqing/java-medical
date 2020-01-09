package com.vpu.sql;

import com.vpu.sql.config.DataManager;
import com.vpu.sql.util.DBUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import java.sql.SQLException;

@SpringBootApplication
@Slf4j
public class SqlApplication implements ExitCodeGenerator {

    public static void main(String[] args) {

//        ConfigurableApplicationContext ctx = new
//                SpringApplicationBuilder(SqlApplication.class).web(WebApplicationType.NONE).run();
//        ctx.close();
//        SpringApplication.exit(new
//                SpringApplicationBuilder(SqlApplication.class).web(WebApplicationType.NONE).run());
//        log.info( System.getProperty("sqlPath"));
//        SpringApplication.start;
//
        System.exit(SpringApplication.exit(new
                SpringApplicationBuilder(SqlApplication.class).web(WebApplicationType.NONE).run()));

    }

    @Override
    public int getExitCode() {
        if(DBUtil.errorNumbers.intValue() >0 ){
            return 1;
        }else{
            return 0;
        }

    }
}
