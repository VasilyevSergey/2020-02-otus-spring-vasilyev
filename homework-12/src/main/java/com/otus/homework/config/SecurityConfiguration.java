package com.otus.homework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    public void configure( WebSecurity web ) {
        /*web.ignoring().antMatchers( "/" );*/
    }

    @Override
    public void configure( HttpSecurity http ) throws Exception {
        http.csrf().disable()
                // По умолчанию SecurityContext хранится в сессии
                // Это необходимо, чтобы он нигде не хранился
                // и данные приходили каждый раз с запросом
                //.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS )
//                .and()
//                .authorizeRequests().antMatchers( "/public" ).anonymous()
                //.and()
                .authorizeRequests().antMatchers( "/" /*, "/success"*/ ).authenticated()
                //.and().anonymous().authorities( "ROLE_ANONYMOUS" ).principal( "ya" )
                .and()
                // Включает Form-based аутентификацию
//
                .formLogin()
                //.defaultSuccessUrl("/")

                .and().rememberMe()

        ;

        http.rememberMe()
                .key("MyRememberMeKey")
                .tokenValiditySeconds(86400)
        ;
    }

    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    public void configure( AuthenticationManagerBuilder auth ) throws Exception {
        auth.inMemoryAuthentication()
                .withUser( "admin" ).password( "password" ).roles( "ADMIN" );
    }


    /*
    @Bean
    public UserDetailsService daoUserDetailsService(DataSource dataSource) {
        JdbcDaoImpl dao = new JdbcDaoImpl();
        dao.setDataSource(dataSource);
        return dao;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        dataSource.setUrl("jdbc:hsqldb:hsql://localhost:9001");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }*/



}
