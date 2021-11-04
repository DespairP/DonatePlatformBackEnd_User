package org.wangtianyu.userPlatform.Mapper;

import org.apache.ibatis.annotations.Param;
import org.wangtianyu.userPlatform.Model.PlatformUserInformation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.wangtianyu.userPlatform.Model.PlatformUserLocation;

/**
 * @Entity org.wangtianyu.userPlatform.Model.PlatformUserInformation
 */
public interface PlatformUserInformationMapper extends BaseMapper<PlatformUserInformation> {
    public PlatformUserLocation selectUserMainLocation(@Param("user_id")String userId);
}




