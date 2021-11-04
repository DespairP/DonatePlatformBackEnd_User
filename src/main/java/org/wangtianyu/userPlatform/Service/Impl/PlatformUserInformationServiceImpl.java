package org.wangtianyu.userPlatform.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.wangtianyu.userPlatform.Mapper.PlatformUserInformationMapper;
import org.wangtianyu.userPlatform.Model.PlatformUserInformation;
import org.wangtianyu.userPlatform.Model.PlatformUserLocation;
import org.wangtianyu.userPlatform.Model.Platformuser;
import org.wangtianyu.userPlatform.Service.PlatformUserInformationService;
import org.springframework.stereotype.Service;
import org.wangtianyu.userPlatform.Service.UserLoginService;

/**
 *
 */
@Service
public class PlatformUserInformationServiceImpl extends ServiceImpl<PlatformUserInformationMapper, PlatformUserInformation> implements PlatformUserInformationService {
    @Autowired
    UserLoginService service;

    @Override
    public PlatformUserInformation getOrCreateUserInformation(String userId) {
        assert !"".equals(userId);
        PlatformUserInformation information = getOne(new QueryWrapper<PlatformUserInformation>().eq("user_id",userId));
        if(information == null){
            boolean userExists = service.count(new QueryWrapper<Platformuser>().eq("user_id",userId)) > 0;
            if(!userExists) throw new RuntimeException("annoymous is not enabled");
            information = new PlatformUserInformation().setUserId(userId).setUserSex("其他");
            save(information);
        }
        return information;
    }

    @Override
    public PlatformUserLocation getUserMainLocation(String userId) {
        return getBaseMapper().selectUserMainLocation(userId);
    }
}




