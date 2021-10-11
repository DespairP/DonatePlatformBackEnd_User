package org.wangtianyu.userPlatform.Apis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public class UserLoginController {

    @GetMapping("/login")
    public String login(){
        return "success";
    }
}
