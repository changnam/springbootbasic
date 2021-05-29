package com.honsoft.web.repository.mysql;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.honsoft.web.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

  List<User> findByUserName(String userName);

  User findById(long id);
}