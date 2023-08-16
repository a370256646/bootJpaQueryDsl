package com.xp.exe.bootjpaquerydsl.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @description: 性别枚举类
 * @author: coderXp
 * @date: 2022年11月2022/11/24日
 * @version: 1.0.0
 */
@Getter
@AllArgsConstructor
public enum SexEnum {
    MAN(1, "男"),
    WOMEN(2, "女");

    private final Integer code;

    private final String desc;
}
