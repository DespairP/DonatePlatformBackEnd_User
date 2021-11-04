package org.wangtianyu.userPlatform.Mapper;

import org.apache.ibatis.annotations.Param;
import org.wangtianyu.userPlatform.Model.Donate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.wangtianyu.userPlatform.Model.Dto.DonateDTO;
import org.wangtianyu.userPlatform.Model.DonateProject;
import org.wangtianyu.userPlatform.Model.DonateProjectTier;
import org.wangtianyu.userPlatform.Model.Dto.DonateTierDTO;

import java.util.List;

/**
 * @Entity org.wangtianyu.userPlatform.Model.Donate
 */
public interface DonateMapper extends BaseMapper<Donate> {

    public List<DonateDTO> selectDonatesCountInformation(List<DonateProject> donateProjects);

    public DonateDTO selectDonateCountInformation(@Param("project") DonateProject donateProject);

    public DonateProjectTier selectUserDonateTierByProjectId(@Param("donate") Donate donate);

    public List<DonateTierDTO> selectUserDonateTierGroupByDonate(List<DonateProjectTier> donateTierId);
}




