package com.sefonsoft.oa.system.emun;

/**
 * 菜单角色枚举
 * @author Aron
 */
public enum MenuRole {
    GLY("0001","管理员"),
    DQLD("0003","大区领导"),
    PTXS("0014","普通销售"),
    LXZY("0015","立项专员"),
    LD("0032","销售管理部及以上领导");




    private String code;
    private String name;

    MenuRole(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}