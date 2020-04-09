package com.vpu.sql.config;

import com.google.common.collect.Lists;
import com.vpu.sql.config.source.MainDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MainDataSourceImp implements MainDataSource {
    final DataSource dataSource;

    static String DB_CONFIG_SQL = "select db_config from b2c_shop";

    public MainDataSourceImp(@Qualifier("mysqlDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void executeMain(String sql) throws SQLException {
        try(Connection con = dataSource.getConnection();
            PreparedStatement pst = con.prepareStatement( sql );
            ResultSet rs = pst.executeQuery()){
            while (rs.next()){
                System.out.println(rs.getInt("shop_id"));
            }
        }

    }

    @Override
    public DataSource getMainDataSource(){
        return dataSource;
    }


    @Override
    public void execute(String sql) {

    }

    @Override
    public void execute(Path path) {

    }

    @Override
    public List<String> getShopDbConfig(List<Integer> shopIds) throws SQLException {
        if( !CollectionUtils.isEmpty(shopIds) ){
            String sql = shopIds.stream().map(x->"shop_id ="+x).collect(Collectors.joining(" or "));

            DB_CONFIG_SQL = DB_CONFIG_SQL +" where 1=1 and "+sql;
        }
        List<String> result = Lists.newArrayList();
        try(Connection con = dataSource.getConnection();
            PreparedStatement pst = con.prepareStatement( DB_CONFIG_SQL );
        ){

            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                result.add(rs.getString("db_config"));
            }
        }
        return result;
    }
}
