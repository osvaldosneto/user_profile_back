package com.user.userprofile.service;

import com.user.userprofile.entity.Profile;
import com.user.userprofile.entity.User;
import com.user.userprofile.repository.UserRepository;
import com.user.userprofile.security.UserSS;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserSS loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found: " + username));
        Set< Profile > setProfile = new HashSet<>();
        setProfile.add(user.getProfile());
        return new UserSS(user.getId(), user.getUsername(), user.getPassword(), setProfile);
    }
}
