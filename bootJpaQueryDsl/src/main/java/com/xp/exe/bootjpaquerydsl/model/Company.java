package com.xp.exe.bootjpaquerydsl.model;

import com.xp.exe.bootjpaquerydsl.common.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "company_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company extends BaseEntity {

    @Column(length = 20)
    @Schema(description = "公司名称")
    @NotBlank(message = "请输入公司名称")
    private String companyName;

    @Column(length = 200)
    @Schema(description = "公司简介")
    @NotBlank(message = "请输入公司简介信息")
    private String empIntroduce;

    @Column(length = 200)
    @Schema(description = "公司描述")
    private String empDesc;
}
