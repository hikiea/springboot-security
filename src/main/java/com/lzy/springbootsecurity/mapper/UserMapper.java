package com.lzy.springbootsecurity.mapper;


import com.lzy.springbootsecurity.pojo.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {
    @Select("select * from users where username = #{username}")
    UserBean selectByUsername(@Param("username") String username);
}
