package com.lzy.springbootsecurity.config;

import com.lzy.springbootsecurity.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



// 继承 WebSecurityConfigurerAdapter工具类，重写里面两个方法 认证、鉴权
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyUserDetailService myUserDetailService;

    // 鉴权
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                // 匹配 "/","/index" 路径，不需要权限即可访问
                .antMatchers("/","/index","/error").permitAll()
                // 匹配 "/user" 及其以下所有路径，都需要 "USER" 权限
                .antMatchers("/user/**").hasRole("USER")
                // 匹配 "/admin" 及其以下所有路径，都需要 "ADMIN" 权限
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                // 指定登录地址 /login , 成功后 跳转到 /user
                .formLogin().loginPage("/login").defaultSuccessUrl("/")
                .and()
                // 退出登录的地址为 "/logout"，退出成功后跳转到页面 "/login"
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");
        /* 开启记住我功能，相当于 cookie，默认保存两周 */
        http.rememberMe().rememberMeParameter("remember");
        /*这个是用来防范CSRF跨站请求伪造攻击的，在学习阶段关掉*/
        http.csrf().disable();
    }


    // 认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        /**
         * 在内存中创建一个名为 "user" 的用户，密码为 "pwd"，拥有 "USER" 权限，密码使用BCryptPasswordEncoder加密
         */
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("user").password(new BCryptPasswordEncoder().encode("pwd")).roles("USER");
        /**
         * 在内存中创建一个名为 "admin" 的用户，密码为 "pwd"，拥有 "USER" 和"ADMIN"权限
         */
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("admin").password(new BCryptPasswordEncoder().encode("pwd")).roles("USER","ADMIN");
        //加入数据库验证类，下面的语句实际上在验证链中加入了一个DaoAuthenticationProvider
        auth.userDetailsService(myUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
