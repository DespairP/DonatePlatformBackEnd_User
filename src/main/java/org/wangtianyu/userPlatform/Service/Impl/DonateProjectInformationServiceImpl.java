package org.wangtianyu.userPlatform.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.wangtianyu.userPlatform.Mapper.DonateProjectInformationMapper;
import org.wangtianyu.userPlatform.Model.Dto.DonateDTO;
import org.wangtianyu.userPlatform.Model.DonateProject;
import org.wangtianyu.userPlatform.Model.DonateProjectInformation;
import org.springframework.stereotype.Service;
import org.wangtianyu.userPlatform.Service.DonateProjectInformationService;
import org.wangtianyu.userPlatform.Service.DonateProjectService;
import org.wangtianyu.userPlatform.Service.DonateService;

import java.math.BigDecimal;

/**
 *
 */
@Service
public class DonateProjectInformationServiceImpl extends ServiceImpl<DonateProjectInformationMapper, DonateProjectInformation> implements DonateProjectInformationService {
    @Autowired
    DonateProjectService projectService;
    @Autowired
    DonateService donateService;

    @Override
    public DonateProjectInformation getInfoByProjectId(String projectId) {
        return getBaseMapper().selectByProjectId(projectId);
    }

    @Override
    public DonateProject getInfoAndProjectByProjectId(String projectId) {
        DonateProject project = projectService.selectProjectJoinUser(projectId);
        if(project == null) return null;
        DonateDTO dto = donateService.selectDonateCountInformation(project);
        if(dto == null || dto.getTotalProgress() == null) dto = new DonateDTO(projectId,new BigDecimal(0),0);
        project.setProjectDonateStatics(dto);
        return project;
    }


}




