package org.wangtianyu.userPlatform.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.wangtianyu.userPlatform.Model.DonateProjectInformation;


public interface DonateProjectInformationMapper extends BaseMapper<DonateProjectInformation> {
    public DonateProjectInformation selectByProjectId(@Param("projectId") String projectId);
}




