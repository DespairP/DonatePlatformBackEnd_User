package org.wangtianyu.userPlatform.Mapper;

import org.apache.ibatis.annotations.Param;
import org.wangtianyu.userPlatform.Model.Donate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.wangtianyu.userPlatform.Model.DonateDTO;
import org.wangtianyu.userPlatform.Model.DonateProject;

import java.util.List;

/**
 * @Entity org.wangtianyu.userPlatform.Model.Donate
 */
public interface DonateMapper extends BaseMapper<Donate> {

    public List<DonateDTO> selectDonatesCountInformation(List<DonateProject> donateProjects);

    public DonateDTO selectDonateCountInformation(@Param("project") DonateProject donateProject);
}




