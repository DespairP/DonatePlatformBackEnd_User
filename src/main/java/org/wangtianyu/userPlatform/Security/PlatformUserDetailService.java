package org.wangtianyu.userPlatform.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.wangtianyu.userPlatform.Mapper.PlatformuserMapper;
import org.wangtianyu.userPlatform.Model.Platformuser;


@Component
public class PlatformUserDetailService implements UserDetailsService {
    @Autowired
    private PlatformuserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Platformuser user = mapper.userLogin(username);
        if(user == null) throw new UsernameNotFoundException("用户名不存在或密码错误");
        return new PlatformUserDetail(user);
    }
}
