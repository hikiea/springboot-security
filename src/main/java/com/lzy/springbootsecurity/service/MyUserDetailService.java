package com.lzy.springbootsecurity.service;

import com.lzy.springbootsecurity.mapper.UserMapper;
import com.lzy.springbootsecurity.pojo.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserBean userBean = userMapper.selectByUsername(username);
        if (userBean == null){
            throw new UsernameNotFoundException("数据库查无此人");
        }
        return userBean;
    }
}
