package com.renyigesai.bakeries.api.annotation;


import java.lang.annotation.*;

/**
 * 自定义注解：ItemData
 * 用于标记物品的属性信息，包括中英文名称、物品类型等
 * 该注解只能用于字段上（ElementType.FIELD）
 * 注解会在运行时保留（RetentionPolicy.RUNTIME）
 * 注解会被包含在JavaDoc中（@Documented）
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ItemType {
    /**
     * 获取物品的中文名称
     * @return 物品的中文名称字符串
     */
    String zhCn();
    /**
     * 获取物品的英文名称
     * @return 物品的英文名称字符串
     */
    String enUs() default "";
    /**
     * 获取物品的类型
     * @return 物品类型枚举值
     */
    Class itemClass() default Class.ITEM;
    /**
     * 获取物品所属的分组
     * 默认值为 not
     * @return 物品分组枚举值
     */
    String group() default "not";

    enum Class {
        ITEM,
        BLOCK,
        CUSTOM_ITEM,
        CUSTOM_BLOCK,
    }

    enum ModelType {
        GENERAL,
        CUSTOM
    }
}