package com.vpu.sql.entity;

import com.vpu.sql.constant.Scope;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.List;

public class DBSource {

    private DataSource dataSource;


    private List<String> dataBases;

    private Scope scope;



    public DBSource(DataSource dataSource,Scope scope) {
        this.dataSource = dataSource;
        this.scope = scope;
    }

    public DBSource(DataSource dataSource, List<String> dataBases,Scope scope) {
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
