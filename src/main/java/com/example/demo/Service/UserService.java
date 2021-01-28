package com.example.demo.Service;

import com.example.demo.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepo up;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return up.findByUsername(s);
    }
}
