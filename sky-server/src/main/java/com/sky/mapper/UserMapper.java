package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    void insert(User user);

    /**
     * 根据openid来查询用户
     * @param openid
     * @return
     */
    @Select("select * from User where openid = #{openid}")
    User getByOpenid(String openid);

    @Select("select * from user where id=#{id}")
    User getById(Long userId);
}
