package com.vpu.sql.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public class DBConfig {

    private String host;

    private Integer port;

    private String userName;

    private String password;

    private String dataBase;

    private String dataBaseKey;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
    @JsonProperty("username")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    @JsonProperty("database")
    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }
    @JsonProperty("datasourceKey")
    public String getDataBaseKey() {
        return dataBaseKey;
    }

    public void setDataBaseKey(String dataBaseKey) {
        this.dataBaseKey = dataBaseKey;
    }

    @Override
    public String toString() {
        return "DBConfig{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", dataBase='" + dataBase + '\'' +
                ", dataBaseKey='" + dataBaseKey + '\'' +
                '}';
    }
}
