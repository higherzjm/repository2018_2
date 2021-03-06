package com.j2ee.spring.Spring_mybatis.mapper;

import com.j2ee.spring.Spring_mybatis.beans.Test;
import com.j2ee.spring.Spring_mybatis.beans.TestExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author dengbin
 * @date 2018/4/18
 */
public interface TestMapper {
    int countByExample(TestExample example);

    int deleteByExample(TestExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Test record);

    int insertSelective(Test record);

    List<Test> selectByExample(TestExample example);

    Test selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Test record, @Param("example") TestExample example);

    int updateByExample(@Param("record") Test record, @Param("example") TestExample example);

    int updateByPrimaryKeySelective(Test record);

    int updateByPrimaryKey(Test record);

}
