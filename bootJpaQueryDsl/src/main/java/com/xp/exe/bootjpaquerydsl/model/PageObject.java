package com.xp.exe.bootjpaquerydsl.model;

import com.xp.exe.bootjpaquerydsl.common.BasePageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description: 分页封装对象
 * @author: coderXp
 * @date: 2023年08月01日
 * @version: 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageObject<T> {
    /**
     * @description: 总数据量
     */
    private Long totalCount;

    /**
     * @description: 总页数
     */
    private Long totalPage;

    /**
     * @description: 当前页
     */
    private Long currentPage;

    private Long pageSize;

    /**
     * @description: 分页后的这一页的数据
     */
    private List<T> dataInfo;

    public void buildPageData(List<T> dataInfo, Long totalCount, BasePageRequest basePageRequest) {
        this.dataInfo = dataInfo;
        this.totalCount = totalCount;
        this.pageSize = basePageRequest.getPageSize();
        this.currentPage = basePageRequest.getPageNum();
        this.totalPage = totalCount % pageSize == 0
                ? (totalCount / pageSize) : (totalCount / pageSize) + 1;
    }
}
