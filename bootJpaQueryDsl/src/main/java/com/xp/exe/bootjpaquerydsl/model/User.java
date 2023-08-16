package com.xp.exe.bootjpaquerydsl.model;

import com.xp.exe.bootjpaquerydsl.common.BaseEntity;
import com.xp.exe.bootjpaquerydsl.common.groups.AddGroup;
import com.xp.exe.bootjpaquerydsl.common.groups.UpdateGroup;
import com.xp.exe.bootjpaquerydsl.customAnnotations.EnumCheckNotNull;
import com.xp.exe.bootjpaquerydsl.enums.SexEnum;
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
 * @description: 用户实体类
 * @author: coderXp
 * @date: 2023年06月5日
 * @version: 1.0.0
 */

@Entity
@Table(name = "user_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(length = 20)
    @Schema(description = "用户名")
    @NotBlank(message = "请输入用户名", groups = {AddGroup.class, UpdateGroup.class})
    private String userName;

    @Column(columnDefinition = "tinyint(1)")
    @Schema(description = "性别")
    @EnumCheckNotNull(target = SexEnum.class, checkRange = true, min = 1, max = 2, message = "性别只能是男/女 1/2",
            groups = {AddGroup.class, UpdateGroup.class})
    private Integer sex;

    @Column(columnDefinition = "tinyint(3)")
    @Schema(description = "年龄")
    @NotNull(message = "请传入用户年龄", groups = {AddGroup.class, UpdateGroup.class})
    private Integer age;

    @Column(length = 200)
    @Schema(description = "描述")
    private String userDesc;
}
