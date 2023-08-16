package com.xp.exe.bootjpaquerydsl.service;

import com.xp.exe.bootjpaquerydsl.common.BasePageRequest;
import com.xp.exe.bootjpaquerydsl.model.Employee;
import com.xp.exe.bootjpaquerydsl.model.PageObject;

/**
 * @description: TODO
 * @author: coderXp
 * @date: 2023年08月16日
 * @version: 1.0.0
 */
public interface IEmployeeService {
    PageObject<Employee> getAll(BasePageRequest basePageRequest);

    Employee getOneById(Long empId);

    Employee addOne(Employee employee);

    int delOne(Long empId);

    int updateOne(Employee employee);
}
