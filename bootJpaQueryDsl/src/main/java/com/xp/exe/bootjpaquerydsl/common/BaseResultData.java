package com.xp.exe.bootjpaquerydsl.common;

import com.xp.exe.bootjpaquerydsl.enums.BaseReturnCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResultData<T> implements Serializable {

    /**
     * @description: 状态码
     * @see com.xp.exe.bootjpaquerydsl.enums.BaseReturnCodeEnum
     */
    private int status;

    /**
     * @description: 状态码关联的提示内容信息
     */
    private String remark;

    /**
     * @description: 消息提示
     * @see com.xp.exe.bootjpaquerydsl.enums.BaseMessageEnum
     */
    private String message;

    /**
     * @description: 返回数据
     */
    private T data;

    /**
     * @description: 操作时间
     */
    private long timestamp;

    /**
     * @description: 请求成功后的返回内容
     * @param: [data 要返回的对象数据,可以是单个数据也可以是集合对象]
     * @return: BaseResultData 标准定义的返回对象
     * @author: Administrator
     * @date: 2022/7/5
     */
    public static <T> BaseResultData<T> success(T data) {
        return buildInstance(data, null, null);
    }

    /**
     * @description: 请求成功后的返回内容(空数据返回)
     * @return: BaseResultData 标准定义的返回对象,不抱愧obj数据
     * @author: coderXp
     * @date: 2022/9/30
     */
    public static <T> BaseResultData<T> success() {
        return buildInstance(null, null, null);
    }

    /**
     * @description: 请求失败后的返回内容 默认请求失败情况
     * @param: [message 要返回的错误提示信息]
     * @return: BaseResultData 标准定义的返回对象
     * @author: Administrator
     * @date: 2022/7/5
     */
    public static <T> BaseResultData<T> failWithMessage(String message) {
        return buildInstance(null, BaseReturnCodeEnum.FAIL, message);
    }

    /**
     * @description: 请求失败返回提示内容 一般用于特殊场景下使用,提供自定义请求失败状态码
     * @param: [baseReturnCodeEnum 自定义请求失败枚举内容, message 提示性文本]
     * @return: com.xp.exe.bootjpaquerydsl.common.BaseResultData<T>
     * @author: coderXp
     * @date: 2023/6/12
     */
    public static <T> BaseResultData<T> failWithCodeAndMessage(BaseReturnCodeEnum baseReturnCodeEnum, String message) {
        return buildInstance(null, baseReturnCodeEnum, message);
    }

    /**
     * @description: 请求失败后的返回内容
     * @return: BaseResultData 无提示信息的标准定义的返回对象
     * @author: coderXp
     * @date: 2022/9/30
     */
    public static <T> BaseResultData<T> fail() {
        return buildInstance(null, BaseReturnCodeEnum.FAIL, null);
    }

    /**
     * @description: 根据入参动态构建全局对象
     * @param: [data data数据(可能有), baseReturnCodeEnum 返回状态枚举值(传入即意味着请求失败), message 提示性文本(传入即意味着请求失败)]
     * @return: com.xp.exe.bootjpaquerydsl.common.BaseResultData<T>
     * @author: coderXp
     * @date: 2023/6/12
     */
    private static <T> BaseResultData<T> buildInstance(T data, BaseReturnCodeEnum baseReturnCodeEnum, String message) {
        // 待返回的对象instance
        BaseResultData<T> baseResultData = new BaseResultData<>();
        // 默认属性设置
        baseResultData.setTimestamp(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        // 存在有data数据则塞入
        if (null != data)
            baseResultData.setData(data);
        if (null == baseReturnCodeEnum) {
            // 默认构建请求状态为成功的对象
            baseResultData.setStatus(BaseReturnCodeEnum.SUCCESS.getRETURN_CODE());
            baseResultData.setRemark(BaseReturnCodeEnum.SUCCESS.getREMARK());
            baseResultData.setMessage(BaseReturnCodeEnum.SUCCESS.getREMARK());
        } else {
            // 一般传入该枚举则意味着请求失败了
            baseResultData.setStatus(BaseReturnCodeEnum.FAIL.getRETURN_CODE());
            baseResultData.setRemark(BaseReturnCodeEnum.FAIL.getREMARK());
            // 一般传入message则意味着请求失败且含有提示性message
            if (message != null)
                baseResultData.setMessage(message);
            else
                baseResultData.setMessage(BaseReturnCodeEnum.FAIL.getREMARK());
        }
        return baseResultData;
    }
}
