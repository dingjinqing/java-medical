/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Cache implements Serializable {

    private static final long serialVersionUID = 1106628297;

    private Integer   id;
    private String    k;
    private String    v;
    private Timestamp created;

    public Cache() {}

    public Cache(Cache value) {
        this.id = value.id;
        this.k = value.k;
        this.v = value.v;
        this.created = value.created;
    }

    public Cache(
        Integer   id,
        String    k,
        String    v,
        Timestamp created
    ) {
        this.id = id;
        this.k = k;
        this.v = v;
        this.created = created;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getK() {
        return this.k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getV() {
        return this.v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public Timestamp getCreated() {
        return this.created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Cache (");

        sb.append(id);
        sb.append(", ").append(k);
        sb.append(", ").append(v);
        sb.append(", ").append(created);

        sb.append(")");
        return sb.toString();
    }
}
