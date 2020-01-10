package com.vpu.sql.config.source;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

public interface MainDataSource {

    void execute(String sql);


    void execute(Path path);

    List<String> getShopDbConfig(List<Integer> shopIds) throws SQLException;


    DataSource getMainDataSource();
}
