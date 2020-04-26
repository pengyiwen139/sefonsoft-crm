package com.sefonsoft.oa.system.emun;


/**
 * @author xielf
 */

public enum GenderEnum {

    /** */
    MAN(1,"男"),
    WOMAN(2,"女"),;




    private int code;
    private String name;

    GenderEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static GenderEnum convertName(String name){
        for (GenderEnum value : values()) {
            if(value.name.equals(name)){
                return value;
            }
        }
        return null;
    }

    public static GenderEnum convertCode(int code){
        for (GenderEnum value : values()) {
            if(value.code == code){
                return value;
            }
        }
        return null;
    }
}