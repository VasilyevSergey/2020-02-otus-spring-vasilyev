package com.otus.homework.service;

import com.otus.homework.domain.UserDB;
import com.otus.homework.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MongoUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    public MongoUserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDB user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("user"));
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
