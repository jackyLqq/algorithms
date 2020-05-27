package com.liuqiqi.tree;

/**
 * 颜色枚举类
 *
 * @author liuqiqi
 * @date 2020/5/24 21:18
 */
public enum ColorEnum {

    BLACK(1),

    RED(0);

    private Integer color;

    ColorEnum(Integer color) {
        this.color = color;
    }

    public Integer getColor() {
        return color;
    }

}
