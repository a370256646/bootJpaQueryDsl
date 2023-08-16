package com.xp.exe.bootjpaquerydsl.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: TODO
 * @author: Administrator
 * @date: 2022/7/5 15:32
 * @version: 1.0
 */
@Getter
@AllArgsConstructor
public enum BaseMessageEnum {
    SUCCESS("请求成功!"),
    FAIL("请求失败!");

    private final String MESSAGE;
}
