package com.kongbig.cloud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author kongbig
 * @date 2019/12/8 9:30
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Configure HttpSecurity as needed
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        http.csrf().disable();
        // 注意：为了可以使用 http://${user}:${password}@${host}:${port}/eureka/ 这种方式登录，所以必须是httpBasic
        // 如果是form方式，不能使用url格式登录
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }

}
