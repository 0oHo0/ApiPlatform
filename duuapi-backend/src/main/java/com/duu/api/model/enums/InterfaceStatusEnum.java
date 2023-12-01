package com.duu.api.model.enums;

/**
 * @author : duu
 * @data : 2023/11/23
 * @from ：https://github.com/0oHo0
 **/
public enum InterfaceStatusEnum {
    ONLINE("已发布",1),

    OFFLINE("下线",0);

    private String text;
    private Integer value;

    InterfaceStatusEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
