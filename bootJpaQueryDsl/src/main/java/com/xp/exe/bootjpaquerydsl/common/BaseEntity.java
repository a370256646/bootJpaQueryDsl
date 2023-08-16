package com.xp.exe.bootjpaquerydsl.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xp.exe.bootjpaquerydsl.common.groups.AddGroup;
import com.xp.exe.bootjpaquerydsl.common.groups.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @description: 基础实体类, 包含所有实体应该拥有的基础属性
 * @author: coderXp
 * @date: 2023年06月05日
 * @version: 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {
    /**
     * @description: 主键, 默认配置自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20)
    @Schema(description = "id,主键", hidden = true)
    @Null(message = "新增时不能指定有id信息",groups = {AddGroup.class})
    @NotNull(message = "修改时请传入id信息",groups = {UpdateGroup.class})
    private Long id;

    /**
     * @description: 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间", hidden = true)
    private LocalDateTime createdAt;

    /**
     * @description: 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "修改时间", hidden = true)
    private LocalDateTime updatedAt;

    /**
     * @description: 逻辑删除标识
     */
    @JsonIgnore
    @Column(columnDefinition = "tinyint(1) default 0")
    @Schema(description = "是否删除字段", hidden = true)
    private Integer isDeleted;

    @Getter
    @AllArgsConstructor
    public enum DeletedEnum {
        EFFECTIVE(0, "有效"),
        HAS_DELETED(1, "删除");

        private final int code;
        private final String desc;
    }

    /**
     * @description: 持久化操作(新增)前做的事
     */
    @PrePersist
    public void prePersist() {
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        this.createdAt = localDateTimeNow;
        this.updatedAt = localDateTimeNow;
        this.isDeleted = DeletedEnum.EFFECTIVE.code;
    }

    /**
     * @description: 更新操作前做的事
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
