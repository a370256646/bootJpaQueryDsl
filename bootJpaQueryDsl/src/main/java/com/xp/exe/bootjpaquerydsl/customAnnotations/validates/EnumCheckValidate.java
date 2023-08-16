package com.xp.exe.bootjpaquerydsl.customAnnotations.validates;


import com.xp.exe.bootjpaquerydsl.customAnnotations.EnumCheck;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @description: 枚举校验器(历史版本不再使用)
 * @author: coderXp
 * @date: 2022年12月2022/12/1日
 * @version: 1.0.0
 */
public class EnumCheckValidate implements ConstraintValidator<EnumCheck, Object> {
    /**
    * @description: 最小值
    */
    private Integer min;

    /**
    * @description: 最大值
    */
    private Integer max;

    /**
    * @description: 是否进行范围检查(只有该项启用才会验证最小最大值范围有效性)
    */
    private boolean checkRange;

    /**
    * @description: 枚举类定义下所有枚举项list
    */
    private List<Object> enumValuesList;

    /**
    * @description: 待校验属性名称
    */
    private Method method;

    @Override
    public void initialize(EnumCheck enumCheck) {
        String field = enumCheck.field();
        Class<? extends Enum<?>> enumCLass = enumCheck.target();
        min = enumCheck.min();
        max = enumCheck.max();
        checkRange = enumCheck.checkRange();
        // 初始化容器对象
        enumValuesList = new ArrayList<>();
        // 判断对象是否为枚举对象,循环得到该对象下所有的枚举值并提前预封装到enumValuesList中
        Optional.of(enumCLass).filter(Class::isEnum).ifPresent(enumCls -> {
            try {
                // 获取对应校验字段的内容值(一般在枚举类中为code字段)
                method = enumCls.getMethod(field);
                // 获取该类定义下的所有枚举常量内容,迭代获取到对应
                Arrays.stream(enumCls.getEnumConstants()).forEach(enumConstant -> {
                    try {
                        enumValuesList.add(method.invoke(enumConstant));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
    * @description: 校验某个枚举值的有效性
    * @param: [value 传进来的需要校验的值, constraintValidatorContext]
    * @return: boolean
    * @author: coderXp
    * @date: 2023/6/16
    */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        // 前端有可能传过来的数据里并不包含有该校验的key会导致空指针问题,直接返回false
        if(null == value){
            return false;
        }
        // 上述初始化方法已经预处理放入数据到enumValuesList中了,该方法只需要比对以及判断是否需要进行rangeCheck即可
        try {
            // 如果是枚举类型,通过反射获取对应value对象的getCode方法的值
            // 如果是非枚举类型,通过常规方法获取它的值
            int valueInt = value.getClass().isEnum() ?
                    Integer.parseInt(method.invoke(value).toString()) : Integer.parseInt(value.toString());
            if (checkRange)
                // 优先做范围校验
                return valueInt >= min && valueInt <= max;
            // 判断值是否在所属范围中
            return enumValuesList.contains(value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
