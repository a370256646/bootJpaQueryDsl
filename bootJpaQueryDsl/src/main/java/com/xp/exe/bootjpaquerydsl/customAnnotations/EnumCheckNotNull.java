package com.xp.exe.bootjpaquerydsl.customAnnotations;

import com.xp.exe.bootjpaquerydsl.customAnnotations.validates.EnumCheckValidateNotNull;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @description: 自定义性别枚举validate注解验证, 此自定义注解主要是针对非空值进行判断和跳过处理
 * @return: 校验结果
 * @author: coderXp
 * @date: 2022/12/1
 */
// target范围为方法属性以及请求参数上
@Target({ElementType.FIELD, ElementType.PARAMETER})
// 运行时有效
@Retention(RetentionPolicy.RUNTIME)
// 校验器指定
@Constraint(validatedBy = {EnumCheckValidateNotNull.class})
@Documented
// 子类继承
@Inherited
public @interface EnumCheckNotNull {
    // 验证不通过时输出的信息
    String message() default "枚举验证失败";

    // 注解的有效负载(严重程度)
    Class<? extends Payload>[] payload() default {};

    // 注解所在的组信息
    Class<?>[] groups() default {};

    // 目标枚举类
    Class<? extends Enum<?>> target();

    // 默认要校验的字段以及对应的get方法
    String field() default "getCode";

    // 最小值 默认0
    int min() default 0;

    // 最大值 默认99999
    int max() default Integer.MAX_VALUE;

    boolean checkRange() default false;

}
