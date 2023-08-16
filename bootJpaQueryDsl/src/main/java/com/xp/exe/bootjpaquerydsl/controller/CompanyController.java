package com.xp.exe.bootjpaquerydsl.controller;

import com.xp.exe.bootjpaquerydsl.common.BasePageRequest;
import com.xp.exe.bootjpaquerydsl.common.BaseResultData;
import com.xp.exe.bootjpaquerydsl.common.groups.AddGroup;
import com.xp.exe.bootjpaquerydsl.common.groups.UpdateGroup;
import com.xp.exe.bootjpaquerydsl.model.Company;
import com.xp.exe.bootjpaquerydsl.model.PageObject;
import com.xp.exe.bootjpaquerydsl.service.impl.CompanyServiceImpl;
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
 * @description: 公司控制层
 * @author: coderXp
 * @date: 2023年08月01日
 * @version: 1.0.0
 */
@RestController
@RequestMapping("company")
@Tag(name = "公司controller")
public class CompanyController {

    private final CompanyServiceImpl companyService;

    public CompanyController(CompanyServiceImpl companyService) {
        this.companyService = companyService;
    }

    @PostMapping("getAll")
    @Operation(summary = "获取全体可用的公司数据", description = "获取全体可用的公司数据")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "pageNum", description = "页码"),
            @Parameter(in = ParameterIn.QUERY, name = "pageSize", description = "每页展示数据量")
    })
    public BaseResultData<PageObject<Company>> getAllUser(@RequestParam(value = "pageNum", defaultValue = "1") Long pageNum,
                                                          @RequestParam(value = "pageSize", defaultValue = "15") Long pageSize) {
        return BaseResultData.success(companyService.getAll(new BasePageRequest(pageNum, pageSize)));
    }

    @PostMapping("getOne")
    @Operation(summary = "获取单个公司数据", description = "获取单个公司数据")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "cmpId", description = "公司身份标识id")
    })
    public BaseResultData<Company> getOne(@NotBlank @RequestParam("cmpId") Long cmpId) {
        return BaseResultData.success(companyService.getOneById(cmpId));
    }

    @PostMapping("addOne")
    @Operation(summary = "新增公司", description = "新增公司")
    @Parameters({
            @Parameter(name = "company", description = "要新增的公司jsonBody体内容")
    })
    public BaseResultData<Company> addOne(@RequestBody @Validated({AddGroup.class}) Company company) {
        return BaseResultData.success(companyService.addOne(company));
    }

    @PostMapping("delOne")
    @Transactional
    @Modifying
    @Operation(summary = "删除公司", description = "删除公司")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "cmpId", description = "要删除的公司身份标识id")
    })
    public BaseResultData<String> delOne(@NotBlank @RequestParam("cmpId") Long cmpId) {
        companyService.delOne(cmpId);
        return BaseResultData.success();
    }

    @PostMapping("updateOne")
    @Transactional
    @Modifying
    @Operation(summary = "编辑公司信息", description = "编辑公司信息")
    @Parameters({
            @Parameter(in = ParameterIn.DEFAULT, name = "company", description = "要编辑修改的公司jsonBody体内容")
    })
    public BaseResultData<String> updateOne(@RequestBody @Validated({UpdateGroup.class}) Company company) {
        companyService.updateOne(company);
        return BaseResultData.success();
    }
}
