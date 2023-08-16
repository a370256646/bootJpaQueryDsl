package com.xp.exe.bootjpaquerydsl.service;

import com.xp.exe.bootjpaquerydsl.common.BasePageRequest;
import com.xp.exe.bootjpaquerydsl.model.Company;
import com.xp.exe.bootjpaquerydsl.model.Employee;
import com.xp.exe.bootjpaquerydsl.model.PageObject;

/**
 * @description: TODO
 * @author: coderXp
 * @date: 2023年08月16日
 * @version: 1.0.0
 */
public interface ICompanyService {
    PageObject<Company> getAll(BasePageRequest basePageRequest);

    Company getOneById(Long cmpId);

    int addOne(Company company);

    int delOne(Long cmpId);

    int updateOne(Company company);
}
