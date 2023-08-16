package com.xp.exe.bootjpaquerydsl.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * @description: TODO
 * @author: Administrator
 * @date: 2022/7/5 15:32
 * @version: 1.0
 */
@Getter
@AllArgsConstructor
public enum BaseReturnCodeEnum {
    SUCCESS(200,"请求成功"),
    FAIL(-200,"请求失败"),
    BAD_REQUEST(400,"请求失败"),
    INTERNAL_FAIL(500,"服务器请求错误");

    private final int RETURN_CODE;
    private final String REMARK;

    /**
    * @description: 根据传递的code值判断是否在枚举支持列表中
    * @param: [codeValue 枚举值]
    * @return: 若不支持返回null,支持则返回支持的枚举enum值,使用此枚举一定要注意null判断
    * @author: Administrator
    * @date: 2022/7/5
    */
    public static BaseReturnCodeEnum getByCode(int codeValue){
        return Stream.of(values())
                .filter(BaseReturnCodeEnum -> BaseReturnCodeEnum.getRETURN_CODE()==codeValue)
                .findFirst()
                .orElse(null);
    }


}
