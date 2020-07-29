package com.otus.homework.config;

import com.otus.homework.service.MongoUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final MongoUserDetailsServiceImpl userDetailsService;

    public SecurityConfig(MongoUserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure( HttpSecurity http ) throws Exception {
        http.csrf().disable()
                //.authorizeRequests().anyRequest().authenticated()
                //.and()
                .authorizeRequests().antMatchers("/").hasAnyRole("ADMIN", "USER")
                //.authorizeRequests().antMatchers("/").permitAll()
                .and()
                .authorizeRequests().antMatchers("/author/**").hasAnyRole("ADMIN", "USER")
                .and()
                .authorizeRequests().antMatchers("/book/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .and()
                .rememberMe()
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .exceptionHandling();

        http.rememberMe()
                .key("MyRememberMeKey")
                .tokenValiditySeconds(86400);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configure( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
