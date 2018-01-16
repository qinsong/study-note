package com.qs.mapper;

import com.qs.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Administrator on 2017/5/17.
 */
@Mapper
public interface UserMapper {
    @Select("SELECT id , username from user_db.user where id=1")
    User queryUsers(User user);

    @Insert("insert into user_db.user(username) values (#{username})")
    void insertUser(User user);
}
