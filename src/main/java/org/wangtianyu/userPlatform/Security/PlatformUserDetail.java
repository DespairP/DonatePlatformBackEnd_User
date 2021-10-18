package org.wangtianyu.userPlatform.Security;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.wangtianyu.userPlatform.Model.Platformuser;

import java.util.Collection;

public class PlatformUserDetail implements UserDetails {
    private Platformuser user;

    public PlatformUserDetail(Platformuser user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ImmutableList.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !user.isUserIsDisposed();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isUserIsDisposed();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !user.isUserIsDisposed();
    }

    @Override
    public boolean isEnabled() {
        return !user.isUserIsDisposed();
    }

    public Platformuser getUser() {
        return user;
    }
}
