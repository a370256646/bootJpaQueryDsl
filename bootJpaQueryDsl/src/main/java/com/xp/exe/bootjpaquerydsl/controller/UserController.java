package com.xp.exe.bootjpaquerydsl.controller;

import com.xp.exe.bootjpaquerydsl.common.BasePageRequest;
import com.xp.exe.bootjpaquerydsl.common.BaseResultData;
import com.xp.exe.bootjpaquerydsl.common.groups.AddGroup;
import com.xp.exe.bootjpaquerydsl.common.groups.UpdateGroup;
import com.xp.exe.bootjpaquerydsl.model.PageObject;
import com.xp.exe.bootjpaquerydsl.model.User;
import com.xp.exe.bootjpaquerydsl.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @description: 用户controller控制层
 * @author: coderXp
 * @date: 2023年06月06日
 * @version: 1.0.0
 */
@RestController
@RequestMapping("user")
@Tag(name = "用户controller")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("getAllUser")
    @Operation(summary = "获取全体可用的用户数据", description = "获取全体可用的用户数据")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "pageNum", description = "页码"),
            @Parameter(in = ParameterIn.QUERY, name = "pageSize", description = "每页展示数据量")
    })
    public BaseResultData<PageObject<User>> getAllUser(@RequestParam(value = "pageNum", defaultValue = "1") Long pageNum,
                                                       @RequestParam(value = "pageSize", defaultValue = "15") Long pageSize) {
        return BaseResultData.success(userService.getAllUser(new BasePageRequest(pageNum, pageSize)));
    }

    @PostMapping("getUser")
    @Operation(summary = "获取单个用户数据", description = "获取单个用户数据")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "userId", description = "用户身份标识id")
    })
    public BaseResultData<User> getUser(@NotBlank @RequestParam("userId") Long userId) {
        return BaseResultData.success(userService.getOneById(userId));
    }

    @PostMapping("getUserByCondition")
    @Operation(summary = "根据条件获取单个用户数据", description = "根据条件获取单个用户数据")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "pageNum", description = "页码"),
            @Parameter(in = ParameterIn.QUERY, name = "pageSize", description = "每页展示数据量"),
            @Parameter(in = ParameterIn.QUERY, name = "userId", description = "用户身份标识id,可选"),
            @Parameter(in = ParameterIn.QUERY, name = "userName", description = "用户名称,可选"),
            @Parameter(in = ParameterIn.QUERY, name = "userDesc", description = "用户描述,可选"),
            @Parameter(in = ParameterIn.QUERY, name = "userAge", description = "用户年龄,可选"),
            @Parameter(in = ParameterIn.QUERY, name = "userSex", description = "用户性别,可选"),
    })
    public BaseResultData<PageObject<User>> getUserByCondition(@RequestParam(value = "pageSize", defaultValue = "15") Long pageSize,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Long pageNum,
                                                   @RequestParam(value = "userId", required = false) Long userId,
                                                   @RequestParam(value = "userName", required = false) String userName,
                                                   @RequestParam(value = "userDesc", required = false) String userDesc,
                                                   @RequestParam(value = "userAge", required = false) Long userAge,
                                                   @RequestParam(value = "userSex", required = false) Long userSex) {
        BasePageRequest basePageRequest = new BasePageRequest(pageNum, pageSize);
        HashMap<String, Object> reqParamMap = basePageRequest.getRequestParamsMap();
        reqParamMap.put("userId", userId);
        reqParamMap.put("userName", userName);
        reqParamMap.put("userDesc", userDesc);
        reqParamMap.put("userAge", userAge);
        reqParamMap.put("userSex", userSex);
        return BaseResultData.success(userService.getByCondition(basePageRequest));
    }

    @PostMapping("addUser")
    @Operation(summary = "新增用户", description = "新增用户")
    @Parameters({
            @Parameter(name = "user", description = "要新增的用户jsonBody体内容")
    })
    public BaseResultData<String> addUser(@RequestBody @Validated({AddGroup.class}) User user) {
        userService.addOne(user);
        return BaseResultData.success();
    }

    @PostMapping("delUser")
    @Transactional
    @Modifying
    @Operation(summary = "删除用户", description = "删除用户")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "userId", description = "要删除的用户身份标识id")
    })
    public BaseResultData<String> delUser(@NotBlank @RequestParam("userId") Long userId) {
        userService.delOne(userId);
        return BaseResultData.success();
    }

    @PostMapping("updateUser")
    @Transactional
    @Modifying
    @Operation(summary = "编辑用户信息", description = "编辑用户信息")
    @Parameters({
            @Parameter(in = ParameterIn.DEFAULT, name = "user", description = "要编辑修改的用户jsonBody体内容")
    })
    public BaseResultData<String> delUser(@RequestBody @Validated({UpdateGroup.class}) User user) {
        userService.updateOne(user);
        return BaseResultData.success();
    }

}
