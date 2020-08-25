package com.vpu.sql.entity;

import com.vpu.sql.constant.Scope;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author luguangyao
 */
public class DbSource {

    private DataSource dataSource;


    private List<String> dataBases;

    private Scope scope;



    public DbSource(DataSource dataSource, Scope scope) {
        this.dataSource = dataSource;
        this.scope = scope;
    }

    public DbSource(DataSource dataSource, List<String> dataBases, Scope scope) {
        this.dataSource = dataSource;
        this.dataBases = dataBases;
        this.scope = scope;
    }
    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<String> getDataBases() {
        return dataBases;
    }

    public void setDataBases(List<String> dataBases) {
        this.dataBases = dataBases;
    }

}
