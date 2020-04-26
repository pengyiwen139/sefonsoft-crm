package com.sefonsoft.oa.service.trans.enums;

/**
 * @author Aron
 * @version 0.0.1
 */
public enum TransEnum {
    /**
     * 1,男
     */
    MAN("1", "男");

    private String type;
    private String name;

    TransEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
