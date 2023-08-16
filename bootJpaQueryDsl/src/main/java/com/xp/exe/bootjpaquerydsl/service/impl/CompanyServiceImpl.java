package com.xp.exe.bootjpaquerydsl.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.xp.exe.bootjpaquerydsl.common.BaseEntity;
import com.xp.exe.bootjpaquerydsl.common.BasePageRequest;
import com.xp.exe.bootjpaquerydsl.model.Company;
import com.xp.exe.bootjpaquerydsl.model.PageObject;
import com.xp.exe.bootjpaquerydsl.model.QCompany;
import com.xp.exe.bootjpaquerydsl.repository.CompanyRepository;
import com.xp.exe.bootjpaquerydsl.service.ICompanyService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: company业务逻辑提供层
 * @author: coderXp
 * @date: 2023年08月16日
 * @version: 1.0.0
 */
@Service
public class CompanyServiceImpl implements ICompanyService {

    private final CompanyRepository companyRepository;

    private final JPAQueryFactory queryFactory;

    private final QCompany Q_COMPANY = QCompany.company;

    public CompanyServiceImpl(JPAQueryFactory queryFactory,
                              CompanyRepository companyRepository) {
        this.queryFactory = queryFactory;
        this.companyRepository = companyRepository;
    }


    @Override
    public PageObject<Company> getAll(BasePageRequest basePageRequest) {
        // 统计逻辑
        Long totalCount = queryFactory.select(Q_COMPANY.count()).from(Q_COMPANY)
                .where(Q_COMPANY.isDeleted.eq(BaseEntity.DeletedEnum.EFFECTIVE.getCode()))
                .fetchOne();
        //分页逻辑
        List<Company> companyList = queryFactory.selectFrom(Q_COMPANY)
                .where(Q_COMPANY.isDeleted.eq(BaseEntity.DeletedEnum.EFFECTIVE.getCode()))
                .offset(basePageRequest.getStartOffSet())
                .limit(basePageRequest.getPageSize())
                .fetch();
        PageObject<Company> pageObject = new PageObject<>();
        pageObject.buildPageData(companyList, totalCount, basePageRequest);
        return pageObject;
    }

    @Override
    public Company getOneById(Long cmpId) {
        return queryFactory.selectFrom(Q_COMPANY)
                .where(Q_COMPANY.id.eq(cmpId))
                .fetchOne();
    }

    @Override
    public Company addOne(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public int delOne(Long cmpId) {
        return (int) queryFactory.delete(Q_COMPANY)
                .where(Q_COMPANY.id.eq(cmpId))
                .execute();
    }

    @Override
    public int updateOne(Company company) {
        return (int) queryFactory.update(Q_COMPANY)
                .set(Q_COMPANY.cmpName, company.getCmpName())
                .set(Q_COMPANY.cmpIntroduce, company.getCmpIntroduce())
                .set(Q_COMPANY.cmpDesc, company.getCmpDesc())
                // 更新日期维护
                .set(Q_COMPANY.updatedAt, LocalDateTime.now())
                .where(Q_COMPANY.id.eq(company.getId()))
                .execute();
    }
}
