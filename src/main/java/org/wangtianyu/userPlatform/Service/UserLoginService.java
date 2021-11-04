package org.wangtianyu.userPlatform.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.wangtianyu.userPlatform.Mapper.PlatformuserMapper;
import org.wangtianyu.userPlatform.Model.Platformuser;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Service
public class UserLoginService {
    @Autowired
    private PlatformuserMapper mapper;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    @Qualifier("userDetailService")
    private UserDetailsService detailsService;

    /**注册用户*/
    public Platformuser registerUser(HttpServletRequest request,Platformuser user)throws Exception{
        int rows = mapper.insert(user);
        if(rows <= 0) throw new IllegalArgumentException("user is already exists!");
        UserDetails userDetails = detailsService.loadUserByUsername(user.getUserAccount());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(),userDetails.getAuthorities());
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        Authentication authentication = manager.authenticate(token);
        if(!authentication.isAuthenticated()) throw new RuntimeException("注册成功,但是登录失败");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return user;
    }

    public long count(Wrapper<Platformuser> wrapper){
        return mapper.selectCount(wrapper);
    }

    public boolean update(UpdateWrapper<Platformuser> wrapper){return mapper.update(null,wrapper) > 0;}

    public Platformuser selectById(Serializable id){return mapper.selectById(id);}
}
