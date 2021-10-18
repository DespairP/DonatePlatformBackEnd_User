package org.wangtianyu.userPlatform.Apis;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.wangtianyu.userPlatform.Filter.UserPasswordLoginFilter;
import org.wangtianyu.userPlatform.Model.MessageWrapper;
import org.wangtianyu.userPlatform.Model.Platformuser;
import org.wangtianyu.userPlatform.Model.PlatformuserDTO;
import org.wangtianyu.userPlatform.Security.PlatformUserDetail;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/login")
public class UserLoginController {

    @GetMapping("/test")
    public MessageWrapper<String> test(){
        return new MessageWrapper(MessageWrapper.BasicStatus.SUCCESS,"test","test");
    }

    @GetMapping("/user")
    public MessageWrapper<Platformuser> getLoggerUser(){
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"authentication required");
        PlatformUserDetail userDetails = (PlatformUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new MessageWrapper<Platformuser>(MessageWrapper.BasicStatus.SUCCESS,userDetails.getUser(),"");
    }

    @PostMapping ("/register")
    public MessageWrapper<Platformuser> registerUser(@RequestBody PlatformuserDTO platformuserDTO){

        return null;
    }


}
