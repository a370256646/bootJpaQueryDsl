package com.xp.exe.bootjpaquerydsl.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.xp.exe.bootjpaquerydsl.common.BaseEntity;
import com.xp.exe.bootjpaquerydsl.common.BasePageRequest;
import com.xp.exe.bootjpaquerydsl.model.PageObject;
import com.xp.exe.bootjpaquerydsl.model.QUser;
import com.xp.exe.bootjpaquerydsl.model.User;
import com.xp.exe.bootjpaquerydsl.repository.UserRepository;
import com.xp.exe.bootjpaquerydsl.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @description: TODO
 * @author: coderXp
 * @date: 2023年06月06日
 * @version: 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JPAQueryFactory queryFactory;

    private final QUser Q_USER = QUser.user;

    public UserServiceImpl(JPAQueryFactory queryFactory,
                           UserRepository userRepository) {
        this.queryFactory = queryFactory;
        this.userRepository = userRepository;
    }

    @Override
    public PageObject<User> getAllUser(BasePageRequest basePageRequest) {
        // 统计逻辑
        Long totalCount = queryFactory.select(Q_USER.count()).from(Q_USER)
                .where(Q_USER.isDeleted.eq(BaseEntity.DeletedEnum.EFFECTIVE.getCode()))
                .fetchOne();
        //分页逻辑
        List<User> userList = queryFactory.selectFrom(Q_USER)
                .where(Q_USER.isDeleted.eq(BaseEntity.DeletedEnum.EFFECTIVE.getCode()))
                .offset(basePageRequest.getStartOffSet())
                .limit(basePageRequest.getPageSize())
                .fetch();
        PageObject<User> pageObject = new PageObject<>();
        pageObject.buildPageData(userList, totalCount, basePageRequest);
        return pageObject;
    }

    @Override
    public PageObject<User> getByCondition(BasePageRequest basePageRequest) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        HashMap<String, Object> requestParamsMap = basePageRequest.getRequestParamsMap();
        if (!requestParamsMap.isEmpty()) {
            //根据传过来的user对象动态构建查询where条件部分
            String userIdStr = null == requestParamsMap.get("userId") ?
                    null : requestParamsMap.get("userId").toString();
            String userNameStr = null == requestParamsMap.get("userName") ?
                    null : requestParamsMap.get("userName").toString();
            String userDescStr = null == requestParamsMap.get("userDesc") ?
                    null : requestParamsMap.get("userDesc").toString();
            String userAgeStr = null == requestParamsMap.get("userAge") ?
                    null : requestParamsMap.get("userAge").toString();
            String userSexStr = null == requestParamsMap.get("userSex") ?
                    null : requestParamsMap.get("userSex").toString();
            if (StringUtils.isNotEmpty(userIdStr))
                booleanBuilder.and(Q_USER.id.eq(Long.parseLong(userIdStr)));
            if (StringUtils.isNotEmpty(userNameStr))
                booleanBuilder.and(Q_USER.userName.eq(userNameStr));
            if (StringUtils.isNotEmpty(userDescStr))
                booleanBuilder.and(Q_USER.userDesc.eq(userDescStr));
            if (StringUtils.isNotEmpty(userAgeStr))
                booleanBuilder.and(Q_USER.sex.eq(Integer.parseInt(userAgeStr)));
            if (StringUtils.isNotEmpty(userSexStr))
                booleanBuilder.and(Q_USER.age.eq(Integer.parseInt(userSexStr)));
        }
        Long totalCount = queryFactory.select(Q_USER.count()).from(Q_USER)
                .where(booleanBuilder)
                .fetchOne();
        List<User> userList = queryFactory.selectFrom(Q_USER)
                .where(booleanBuilder)
                .fetch();
        PageObject<User> pageObject = new PageObject<>();
        pageObject.buildPageData(userList, totalCount, basePageRequest);
        return pageObject;
    }

    @Override
    public User getOneById(Long userId) {
        return queryFactory.selectFrom(Q_USER)
                .where(Q_USER.id.eq(userId))
                .fetchOne();
    }

    @Override
    public int addOne(User user) {
        // 针对新增方法来说jpa的新增更具有优势
        System.out.println(userRepository.save(user));
        return 1;
    }

    @Override
    public int delOne(Long userId) {
        return (int) queryFactory.delete(Q_USER)
                .where(Q_USER.id.eq(userId))
                .execute();
    }

    @Override
    public int updateOne(User user) {
        return (int) queryFactory.update(Q_USER)
                .set(Q_USER.age, user.getAge())
                .set(Q_USER.sex, user.getSex())
                .set(Q_USER.userDesc, user.getUserDesc())
                .where(Q_USER.id.eq(user.getId()))
                .execute();
    }
}
