package com.xp.exe.bootjpaquerydsl.service;

import com.xp.exe.bootjpaquerydsl.common.BasePageRequest;
import com.xp.exe.bootjpaquerydsl.model.PageObject;
import com.xp.exe.bootjpaquerydsl.model.User;

/**
 * @description: TODO
 * @author: coderXp
 * @date: 2023年06月06日
 * @version: 1.0.0
 */
public interface UserService {
    PageObject<User> getAllUser(BasePageRequest basePageRequest);

    User getOneById(Long userId);

    User addOne(User user);

    int delOne(Long userId);

    int updateOne(User user);

    PageObject<User> getByCondition(BasePageRequest basePageRequest);
}
