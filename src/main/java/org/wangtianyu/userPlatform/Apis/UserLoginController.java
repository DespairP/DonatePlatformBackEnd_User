package org.wangtianyu.userPlatform.Apis;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.wangtianyu.userPlatform.Filter.UserPasswordLoginFilter;
import org.wangtianyu.userPlatform.Mapper.PlatformuserMapper;
import org.wangtianyu.userPlatform.Model.MessageWrapper;
import org.wangtianyu.userPlatform.Model.Platformuser;
import org.wangtianyu.userPlatform.Model.PlatformuserDTO;
import org.wangtianyu.userPlatform.Security.PlatformUserDetail;
import org.wangtianyu.userPlatform.Service.UserLoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/login")
public class UserLoginController {
    private final UserLoginService service;

    public UserLoginController(UserLoginService service) {
        this.service = service;
    }

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
    public MessageWrapper<Platformuser> registerUser(HttpServletRequest request, @RequestBody PlatformuserDTO platformuserDTO) throws NullPointerException{
        try{
            Optional<PlatformuserDTO> optionalPlatformuserDTO = Optional.ofNullable(platformuserDTO);
            Platformuser platformuser = Platformuser.createUserByDto(optionalPlatformuserDTO.orElseThrow(()->new NullPointerException("收到的用户为空")));
            if(platformuserDTO.getUsername() == null || platformuserDTO.getPassword() == null) throw new NullPointerException("用户或账户名非法");
            if(platformuserDTO.getPassword().length() < 5 || platformuserDTO.getUsername().length() < 5) throw new IllegalArgumentException("用户或账户名非法");
            return new MessageWrapper(MessageWrapper.BasicStatus.SUCCESS,service.registerUser(request,platformuser),"register success!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new MessageWrapper(MessageWrapper.BasicStatus.FAILED,ex.getMessage(),ex.getMessage());
        }
    }



}
