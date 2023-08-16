package com.xp.exe.bootjpaquerydsl.common;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * @description: 基础请求参数对象, 带分页参数和请求条件map数据
 * @author: coderXp
 * @date: 2023年08月01日
 * @version: 1.0.0
 */
@Data
public class BasePageRequest {
    /**
    * @description: 页码
    */
    private Long pageNum;

    /**
    * @description: 每页大小
    */
    private Long pageSize;

    /**
     * @description: 分页起始偏移量
     */
    private Long startOffSet;

    /**
     * @description: 分页截止偏移量
     */
    private Long EndOffset;

    private HashMap<String,Object> requestParamsMap = new HashMap<>();

    public BasePageRequest(Long pageNum,Long pageSize){
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.startOffSet = (pageNum - 1) * pageSize;
        this.EndOffset = pageNum * pageSize;
    }
}
