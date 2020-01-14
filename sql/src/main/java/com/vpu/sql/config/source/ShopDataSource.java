package com.vpu.sql.config.source;

import com.vpu.sql.entity.DBConfig;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface ShopDataSource {
    void execute(String sql);


    void execute(Path path);


    Map<String,DataSource> getShopDataSource(List<DBConfig> configs);
}
