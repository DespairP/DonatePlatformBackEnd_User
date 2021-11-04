package org.wangtianyu.userPlatform.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.wangtianyu.userPlatform.Mapper.DonateProjectMapper;
import org.wangtianyu.userPlatform.Model.DonateProjectInformation;
import org.wangtianyu.userPlatform.Model.DonateProjectTier;
import org.wangtianyu.userPlatform.Model.Dto.DonateDTO;
import org.wangtianyu.userPlatform.Model.DonateProject;
import org.springframework.stereotype.Service;
import org.wangtianyu.userPlatform.Model.Dto.DonateProjectPostingDTO;
import org.wangtianyu.userPlatform.Service.DonateProjectInformationService;
import org.wangtianyu.userPlatform.Service.DonateProjectService;
import org.wangtianyu.userPlatform.Service.DonateProjectTierService;
import org.wangtianyu.userPlatform.Service.DonateService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
*
*/
@Service
public class DonateProjectServiceImpl extends ServiceImpl<DonateProjectMapper, DonateProject> implements DonateProjectService {
    @Autowired
    private DonateService donateService;
    @Autowired
    private DonateProjectInformationService informationService;
    @Autowired
    private DonateProjectTierService projectTierService;

    /**获取项目前8位的项目，并连接发布作者，通过merge加入项目的统计信息（包括众筹人数等等）*/
    @Override
    public List<DonateProject> selectedExhibitProjects() {
        List<DonateProject> projects =  getBaseMapper().selectedExhibitProjects();
        mergeDonateToProjectCalculate(projects);
        return projects;
    }

    /**获取项目的大致信息，和项目连接的发布作者*/
    @Override
    public DonateProject selectProjectJoinUser(String projectId) {
        return getBaseMapper().selectProjectJoinWithUser(projectId);
    }

    /**分页获取项目，并连接发布的作者，通过merge加入项目的统计信息（包括众筹人数等等）*/
    @Override
    public Page<DonateProject> selectProjectPageable(Page<DonateProject> page, Wrapper<DonateProject> wrapper) {
        Page<DonateProject> resultPage = getBaseMapper().selectProjectPageable(page,wrapper);
        mergeDonateToProjectCalculate(resultPage.getRecords());
        return resultPage;
    }

    /**获取符合条件的所有项目，*项目不含user*，通过merge加入项目的统计信息（包括众筹人数等等）*/
    @Override
    public List<DonateProject> selectProjectsWithStatics(Wrapper<DonateProject> wrapper) {
        List<DonateProject> donateProjects = list(wrapper);
        mergeDonateToProjectCalculate(donateProjects);
        return donateProjects;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public String createProject(String userId ,DonateProjectPostingDTO dto) throws Exception{
        assert dto != null || dto.getProject() != null || dto.getInfo() != null || dto.getTiers() != null;
        //注入UserId
        DonateProject project = dto.getProject().setUserId(userId).setProjectStatus(DonateProject.EnumProjectStatus.ONREVIEW);
        boolean result = save(project);
        if(!result) throw new RuntimeException("project Save failed");
        DonateProjectInformation information = dto.getInfo().setProjectId(project.getProjectId()).setProjectUpdateDate(new Date());
        result = informationService.save(information);
        if(!result) throw new RuntimeException("information Save failed");
        List<DonateProjectTier> tiers = dto.getTiers();
        tiers.forEach(tier -> {tier.setProjectId(project.getProjectId());});
        result = projectTierService.saveBatch(tiers);
        if(!result) throw new RuntimeException("tiers saved failed");
        return project.getProjectId();
    }


    private void mergeDonateToProjectCalculate(List<DonateProject> projects){
        assert projects != null;
        if(projects.size() == 0) return;
        List<DonateDTO> donateDTOs = donateService.selectDonatesCountInformation(projects);
        Map<String, DonateDTO> donateDTOMap = donateDTOs.stream().collect(Collectors.toMap(DonateDTO::getProjectId,value->value,(k1,k2)->k1));
        projects.forEach(donateProject -> {
            donateProject.setProjectDonateStatics(donateDTOMap.getOrDefault(donateProject.getProjectId(),new DonateDTO(donateProject.getProjectId(),new BigDecimal(0),0)));
        });
    }
}
