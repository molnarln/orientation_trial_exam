package com.greenfoxacademy.url_aliasing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Alias {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String url;
    private String aliasName;
    private Integer code;
    //@JsonIgnore
    private Long hitCount;

    public Alias() {
        this.code = (int) (Math.random() * 10000);
        this.hitCount = 0l;
    }

    public Alias(String url, String aliasName) {
        this.url = url;
        this.aliasName = aliasName;
        this.code = 1 + (int) (Math.random() * 10000);
        this.hitCount = 0L;
    }

    public Long getHitCount() {
        return hitCount;
    }

    public void incrementHitcount() {
        this.hitCount++;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        code = code;
    }
}
