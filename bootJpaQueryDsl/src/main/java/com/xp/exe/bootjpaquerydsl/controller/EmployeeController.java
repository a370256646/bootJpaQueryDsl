package com.xp.exe.bootjpaquerydsl.controller;

import com.xp.exe.bootjpaquerydsl.common.BasePageRequest;
import com.xp.exe.bootjpaquerydsl.common.BaseResultData;
import com.xp.exe.bootjpaquerydsl.common.groups.AddGroup;
import com.xp.exe.bootjpaquerydsl.common.groups.UpdateGroup;
import com.xp.exe.bootjpaquerydsl.model.Employee;
import com.xp.exe.bootjpaquerydsl.model.PageObject;
import com.xp.exe.bootjpaquerydsl.service.impl.EmployeeServiceImpl;
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

/**
 * @description: 员工控制层
 * @author: coderXp
 * @date: 2023年08月01日
 * @version: 1.0.0
 */
@RestController
@RequestMapping("employee")
@Tag(name = "员工controller")
public class EmployeeController {

    private EmployeeServiceImpl employeeService;

    /**
     * @description: 构造器注入
     */
    public EmployeeController(EmployeeServiceImpl employeeServiceImpl) {
        this.employeeService = employeeServiceImpl;
    }

    @PostMapping("getAll")
    @Operation(summary = "获取全体可用的员工数据", description = "获取全体可用的员工数据")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "pageNum", description = "页码"),
            @Parameter(in = ParameterIn.QUERY, name = "pageSize", description = "每页展示数据量")
    })
    public BaseResultData<PageObject<Employee>> getAllUser(@RequestParam(value = "pageNum", defaultValue = "1") Long pageNum,
                                                           @RequestParam(value = "pageSize", defaultValue = "15") Long pageSize) {
        return BaseResultData.success(employeeService.getAll(new BasePageRequest(pageNum, pageSize)));
    }

    @PostMapping("getOne")
    @Operation(summary = "获取单个员工数据", description = "获取单个员工数据")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "empId", description = "员工身份标识id")
    })
    public BaseResultData<Employee> getOne(@NotBlank @RequestParam("empId") Long empId) {
        return BaseResultData.success(employeeService.getOneById(empId));
    }

    @PostMapping("addOne")
    @Operation(summary = "新增员工", description = "新增员工")
    @Parameters({
            @Parameter(name = "employee", description = "要新增的员工jsonBody体内容")
    })
    public BaseResultData<Employee> addOne(@RequestBody @Validated({AddGroup.class}) Employee employee) {
        return BaseResultData.success(employeeService.addOne(employee));
    }

    @PostMapping("delOne")
    @Transactional
    @Modifying
    @Operation(summary = "删除员工", description = "删除员工")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "empId", description = "要删除的员工身份标识id")
    })
    public BaseResultData<String> delOne(@NotBlank @RequestParam("empId") Long empId) {
        employeeService.delOne(empId);
        return BaseResultData.success();
    }

    @PostMapping("updateOne")
    @Transactional
    @Modifying
    @Operation(summary = "编辑员工信息", description = "编辑员工信息")
    @Parameters({
            @Parameter(in = ParameterIn.DEFAULT, name = "employee", description = "要编辑修改的员工jsonBody体内容")
    })
    public BaseResultData<String> updateOne(@RequestBody @Validated({UpdateGroup.class}) Employee employee) {
        employeeService.updateOne(employee);
        return BaseResultData.success();
    }

}
