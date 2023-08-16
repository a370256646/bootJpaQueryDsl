package com.xp.exe.bootjpaquerydsl.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * @description: 基础请求参数对象, 不带分页参数
 * @author: coderXp
 * @date: 2023年08月01日
 * @version: 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseRequest {
    private HashMap<String, Object> requestParamsMap = new HashMap<>();
}
