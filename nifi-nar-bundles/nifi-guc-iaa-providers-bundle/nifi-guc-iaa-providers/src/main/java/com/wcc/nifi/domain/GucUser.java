package com.wcc.nifi.domain;


/**
 * Guc 用户信息
 * wcc 2022/10/24
 */
public class GucUser {

    private String account;

    private Long id;

    private String name;

    private Long tenantId;

    public Long getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}
