package com.honsoft.web.dao.h2;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.honsoft.web.entity.City;

//@Component
public class CityDao {

  private final SqlSession sqlSession;

  public CityDao(@Qualifier("h2SqlSession") SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  public City selectCityById(long id) {
    return this.sqlSession.selectOne("selectCityById", id);
  }

}