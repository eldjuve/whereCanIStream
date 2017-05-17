package com.wherecanistream.frontend.auth;

/**
 * Created by djuve on 10.05.2017.
 */

import com.wherecanistream.frontend.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    User user;

    @Autowired
    RestHandler restHandler;

    @Override
    public UserDetails loadUserByUsername(String loginID) throws UsernameNotFoundException {
        this.user = restHandler.getUserByLoginID(loginID);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));

        return new org.springframework.security.core.userdetails.User(user.getLoginID(), user.getPassword(), grantedAuthorities);
    }

    public User getLoggedInUser(){
        return this.user;
    }

    public void updateLoggedInUser(String loginID){
        this.user = restHandler.getUserByLoginID(loginID);
    }

    public void clearLoggedInUser(){
        this.user = null;
    }
}