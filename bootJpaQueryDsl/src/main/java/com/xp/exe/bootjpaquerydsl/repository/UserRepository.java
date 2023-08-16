package com.xp.exe.bootjpaquerydsl.repository;

import com.xp.exe.bootjpaquerydsl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @description: TODO
 * @author: coderXp
 * @date: 2023年06月07日
 * @version: 1.0.0
 */
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

}
