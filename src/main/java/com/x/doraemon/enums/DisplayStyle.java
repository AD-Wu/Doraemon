package com.x.doraemon.enums;

import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Desc toString方法的展示类型枚举
 * @Date 2019-11-08 12:34
 * @Author AD
 */
public enum DisplayStyle {

    // ---------------------- 枚举对象 ----------------------

    /**
     * 默认，全限定类名+内存地址+单行显示<br/>
     * 如：com.x.commons.collection.NameValue@27fa135a[name=name,value=AD]
     */
    DEFAULT(ToStringStyle.DEFAULT_STYLE),

    /**
     * 只显示属性值<br/>
     * 如：name,AD
     */
    SIMPLE(ToStringStyle.SIMPLE_STYLE),

    /**
     * 全限定类名+内存地址+多行显示<br/>
     * 如：<br/>
     * com.x.commons.collection.NameValue@27fa135a[   <br/>
     * name=name                                      <br/>
     * value=AD                                       <br/>
     * ]
     */
    MULTI_LINE(ToStringStyle.MULTI_LINE_STYLE),

    /**
     * 显示成JSON字符串<br/>
     * 如：{"name":"name","value":"AD"}
     */
    JSON(ToStringStyle.JSON_STYLE),

    /**
     * 简短类名+单行显示<br/>
     * 如：NameValue[name=name,value=AD]
     */
    SHORT_PREFIX(ToStringStyle.SHORT_PREFIX_STYLE),

    /**
     * 全限定类名+内存地址+属性值<br/>
     * 如：com.x.commons.collection.NameValue@27fa135a[name,AD]
     */
    NO_FIELD_NAMES(ToStringStyle.NO_FIELD_NAMES_STYLE),

    /**
     * 只显示属性名+属性值<br/>
     * 如：[name=name,value=AD]
     */
    NO_CLASS_NAME(ToStringStyle.NO_CLASS_NAME_STYLE);

    // ---------------------- 成员变量 ----------------------

    private final ToStringStyle style;

    // ---------------------- 构造方法 ----------------------

    private DisplayStyle(ToStringStyle style) {
        this.style = style;
    }

    // ---------------------- 成员方法 ----------------------

    /**
     * 获取当前要显示的toString样式
     *
     * @return
     */
    public ToStringStyle get() {
        return this.style;
    }

}
