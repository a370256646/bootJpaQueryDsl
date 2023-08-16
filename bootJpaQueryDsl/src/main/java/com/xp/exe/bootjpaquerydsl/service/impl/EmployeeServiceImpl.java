package com.xp.exe.bootjpaquerydsl.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.xp.exe.bootjpaquerydsl.common.BaseEntity;
import com.xp.exe.bootjpaquerydsl.common.BasePageRequest;
import com.xp.exe.bootjpaquerydsl.model.Employee;
import com.xp.exe.bootjpaquerydsl.model.PageObject;
import com.xp.exe.bootjpaquerydsl.model.QEmployee;
import com.xp.exe.bootjpaquerydsl.repository.EmployeeRepository;
import com.xp.exe.bootjpaquerydsl.service.IEmployeeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: employee业务逻辑提供层
 * @author: coderXp
 * @date: 2023年08月16日
 * @version: 1.0.0
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmployeeRepository employeeRepository;

    private final JPAQueryFactory queryFactory;

    private final QEmployee Q_EMPLOYEE = QEmployee.employee;

    public EmployeeServiceImpl(JPAQueryFactory queryFactory,
                               EmployeeRepository employeeRepository) {
        this.queryFactory = queryFactory;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public PageObject<Employee> getAll(BasePageRequest basePageRequest) {
        // 统计逻辑
        Long totalCount = queryFactory.select(Q_EMPLOYEE.count()).from(Q_EMPLOYEE)
                .where(Q_EMPLOYEE.isDeleted.eq(BaseEntity.DeletedEnum.EFFECTIVE.getCode()))
                .fetchOne();
        //分页逻辑
        List<Employee> employeeList = queryFactory.selectFrom(Q_EMPLOYEE)
                .where(Q_EMPLOYEE.isDeleted.eq(BaseEntity.DeletedEnum.EFFECTIVE.getCode()))
                .offset(basePageRequest.getStartOffSet())
                .limit(basePageRequest.getPageSize())
                .fetch();
        PageObject<Employee> pageObject = new PageObject<>();
        pageObject.buildPageData(employeeList, totalCount, basePageRequest);
        return pageObject;
    }

    @Override
    public Employee getOneById(Long empId) {
        return queryFactory.selectFrom(Q_EMPLOYEE)
                .where(Q_EMPLOYEE.id.eq(empId))
                .fetchOne();
    }

    @Override
    public Employee addOne(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public int delOne(Long empId) {
        return (int) queryFactory.delete(Q_EMPLOYEE)
                .where(Q_EMPLOYEE.id.eq(empId))
                .execute();
    }

    @Override
    public int updateOne(Employee employee) {
        return (int) queryFactory.update(Q_EMPLOYEE)
                .set(Q_EMPLOYEE.empName, employee.getEmpName())
                .set(Q_EMPLOYEE.empDesc, employee.getEmpDesc())
                .set(Q_EMPLOYEE.workYear, employee.getWorkYear())
                // 更新日期维护
                .set(Q_EMPLOYEE.updatedAt, LocalDateTime.now())
                .where(Q_EMPLOYEE.id.eq(employee.getId()))
                .execute();
    }
}
