package com.user.userprofile.security;

import com.user.userprofile.entity.Profile;
import com.user.userprofile.enumeration.ProfileType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserSS implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSS() {
    }

    public UserSS(Long id, String username, String password, Set<Profile> profiles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = profiles
                .stream()
                .map(x -> new SimpleGrantedAuthority(x.getProfileType().getDescription()))
                .collect(Collectors.toList());
    }

    public Long getId(){
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasRole(ProfileType profileType){
        return getAuthorities()
                .contains(new SimpleGrantedAuthority(profileType.getDescription()));
    }

}
