package com.vpu.sql;

import com.vpu.sql.util.DbUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author luguangyao
 */
@SpringBootApplication
@Slf4j
public class SqlApplication implements ExitCodeGenerator {

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(new
                SpringApplicationBuilder(SqlApplication.class).web(WebApplicationType.NONE).run()));

    }

    @Override
    public int getExitCode() {
        if(DbUtil.errorNumbers.intValue() >0 ){
            return 1;
        }else{
            return 0;
        }

    }
}
