package org.wangtianyu.userPlatform.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.wangtianyu.userPlatform.Mapper.DonateMapper;
import org.wangtianyu.userPlatform.Model.CaculateModel.CalculateTierModel;
import org.wangtianyu.userPlatform.Model.Donate;
import org.wangtianyu.userPlatform.Model.Dto.DonateDTO;
import org.wangtianyu.userPlatform.Model.DonateProject;
import org.wangtianyu.userPlatform.Model.DonateProjectTier;
import org.wangtianyu.userPlatform.Model.Dto.DonateTierDTO;
import org.wangtianyu.userPlatform.Service.DonateProjectTierService;
import org.wangtianyu.userPlatform.Service.DonateService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class DonateServiceImpl extends ServiceImpl<DonateMapper, Donate> implements DonateService{
    /**获取一组项目的统计信息，包括捐助的人数，捐助的总额*/
    @Override
    public List<DonateDTO> selectDonatesCountInformation(List<DonateProject> donateProjects) {
        return baseMapper.selectDonatesCountInformation(donateProjects);
    }

    /**获取单个项目的统计信息，包括捐助的人数，捐助的总额*/
    @Override
    public DonateDTO selectDonateCountInformation(DonateProject donateProject) {
        return baseMapper.selectDonateCountInformation(donateProject);
    }

    /**获取用户在某项捐助的Tier*/
    @Override
    public DonateProjectTier selectUserDonateTierByProjectId(Donate donate) {
        return baseMapper.selectUserDonateTierByProjectId(donate);
    }

    /**专门为creator的统计*/
    @Autowired
    DonateProjectTierService projectTierService;
    @Override
    public List<CalculateTierModel> calculateProjectDonateCount(String projectId) {
        List<DonateProjectTier> donateProjectTiers = projectTierService.list(new QueryWrapper<DonateProjectTier>().eq("project_id",projectId));
        if(donateProjectTiers == null || donateProjectTiers.size() == 0) return null;
        List<DonateTierDTO> donateTierDTOS = getBaseMapper().selectUserDonateTierGroupByDonate(donateProjectTiers);
        Map<String,DonateTierDTO> donateTierDTOMap = donateTierDTOS.stream().collect(Collectors.toMap(DonateTierDTO::getProjectTierId,value->value));
        List<CalculateTierModel> calculateTierModels = new LinkedList<>();
        donateProjectTiers.forEach(donateProjectTier -> {
            DonateTierDTO dto = donateTierDTOMap.getOrDefault(donateProjectTier.getProjectDonateTierId(),new DonateTierDTO(donateProjectTier.getProjectDonateTierId(),new BigDecimal(0),0));
            calculateTierModels.add(new CalculateTierModel(donateProjectTier.getProjectDonateTierName(),dto.getDonatedUserNumber(),dto.getTotalProgress()));
        });
        return calculateTierModels;
    }


}




