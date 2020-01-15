package com.vpu.sql.processor;

import com.vpu.sql.config.DataConfigSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SqlExecuteProcessor  implements ApplicationListener<ContextRefreshedEvent>, Ordered {

    final DataConfigSource dataConfigSource;




    public SqlExecuteProcessor(DataConfigSource dataConfigSource) {
        this.dataConfigSource = dataConfigSource;

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        dataConfigSource.execute();
    }





    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE+4;
    }
}
