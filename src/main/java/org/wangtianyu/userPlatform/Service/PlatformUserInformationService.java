package org.wangtianyu.userPlatform.Service;

import org.wangtianyu.userPlatform.Model.PlatformUserInformation;
import com.baomidou.mybatisplus.extension.service.IService;
import org.wangtianyu.userPlatform.Model.PlatformUserLocation;

/**
 *
 */
public interface PlatformUserInformationService extends IService<PlatformUserInformation> {
    /**获取用户的详细信息，如果不存在则创建一个*/
    public PlatformUserInformation getOrCreateUserInformation(String userId);

    /**获取用户的详细地址*/
    public PlatformUserLocation getUserMainLocation(String userId);
}
