package edu.tcu.cs.kayscollectiononline.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;


public class MyUserPrincipal implements UserDetails {

    private AppUser appUser;
    public MyUserPrincipal(AppUser appUser) {
        this.appUser = appUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(StringUtils.tokenizeToStringArray(appUser.getRoles(), " "))
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role))
                .toList();
    }

    @Override
    public String getPassword() {
        return appUser.getPassword();
    }

    @Override
    public String getUsername() {
        return appUser.getUsername();
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
        return appUser.isEnabled();
    }

    public AppUser getAppUser() {
        return appUser;
    }
}
