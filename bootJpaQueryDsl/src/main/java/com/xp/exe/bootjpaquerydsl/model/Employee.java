package com.xp.exe.bootjpaquerydsl.model;

import com.xp.exe.bootjpaquerydsl.common.BaseEntity;
import com.xp.exe.bootjpaquerydsl.common.groups.AddGroup;
import com.xp.exe.bootjpaquerydsl.common.groups.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 员工实体类
 * @author: coderXp
 * @date: 2023年06月5日
 * @version: 1.0.0
 */

@Entity
@Table(name = "emp_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends BaseEntity {

    @Column(length = 20)
    @Schema(description = "员工名称")
    @NotBlank(message = "请输入员工名称", groups = {AddGroup.class, UpdateGroup.class})
    private String empName;

    @Column(columnDefinition = "tinyint(3) default 0")
    @Schema(description = "工龄")
    private Integer workYear;

    @Column(length = 20)
    @Schema(description = "关联的用户id信息")
    @NotNull(message = "请输入关联的用户信息", groups = {AddGroup.class})
    @Null(message = "更新时不能传入用户关联id信息", groups = {UpdateGroup.class})
    private Long userId;

    @Column(length = 20)
    @Schema(description = "关联的公司id信息")
    @NotNull(message = "请输入关联的公司信息", groups = {AddGroup.class})
    @Null(message = "更新时不能传入公司关联id信息", groups = {UpdateGroup.class})
    private Long companyId;

    @Column(length = 200)
    @Schema(description = "员工描述")
    private String empDesc;
}
