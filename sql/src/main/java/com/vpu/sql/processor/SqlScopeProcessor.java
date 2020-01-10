package com.vpu.sql.processor;

import com.vpu.sql.config.DataConfigSource;
import com.vpu.sql.constant.Scope;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class SqlScopeProcessor implements ApplicationListener<ContextRefreshedEvent>, Ordered {

    private static final String MAIN = "main";

    private static final String SHOP = "shop";

    private static final String SHOP_ID = "shopId";

    private final DataConfigSource dataConfigSource;

    public SqlScopeProcessor(DataConfigSource dataConfigSource){
        this.dataConfigSource = dataConfigSource;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        String main = System.getProperty(MAIN);
        String shop = System.getProperty(SHOP);
        String shopId = System.getProperty(SHOP_ID);
        try {
            if( shop != null ){
                /*如果shopId是null，默认执行全部shop库*/
                if( StringUtils.isEmpty(shopId) ){
                    dataConfigSource.initDataSource(Scope.shop,null);
                }else{
                    String[] id = shopId.split(",");
                    dataConfigSource.initDataSource(Scope.appoint_shop,Arrays.stream(id).map(Integer::valueOf).collect(Collectors.toList()));
                }
            }else if( main != null ){
                dataConfigSource.initDataSource(Scope.main,null);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE+1;
    }
}
