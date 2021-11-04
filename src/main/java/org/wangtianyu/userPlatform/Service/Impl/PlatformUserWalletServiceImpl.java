package org.wangtianyu.userPlatform.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.wangtianyu.userPlatform.Mapper.PlatformUserWalletMapper;
import org.wangtianyu.userPlatform.Model.PlatformUserWallet;
import org.springframework.stereotype.Service;
import org.wangtianyu.userPlatform.Model.Platformuser;
import org.wangtianyu.userPlatform.Service.PlatformUserWalletService;
import org.wangtianyu.userPlatform.Service.UserLoginService;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 */
@Service
public class PlatformUserWalletServiceImpl extends ServiceImpl<PlatformUserWalletMapper, PlatformUserWallet> implements PlatformUserWalletService {
    @Autowired
    UserLoginService loginService;

    @Override
    public PlatformUserWallet getOrCreateUserWallet(String id) {
        PlatformUserWallet wallet = getOne(new QueryWrapper<PlatformUserWallet>().eq("user_id",id));
        if(wallet == null){
            boolean isUserExits = loginService.count(new QueryWrapper<Platformuser>().eq("user_id",id)) > 0;
            if(!isUserExits) return null;
            wallet = new PlatformUserWallet().setUserId(id.toString()).setWalletMoney(new BigDecimal("0.00"));
            save(wallet);
        }
        return wallet;
    }
}




