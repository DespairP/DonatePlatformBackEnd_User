package org.wangtianyu.userPlatform.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.wangtianyu.userPlatform.Mapper.DonateProjectInformationMapper;
import org.wangtianyu.userPlatform.Model.DonateDTO;
import org.wangtianyu.userPlatform.Model.DonateProject;
import org.wangtianyu.userPlatform.Model.DonateProjectInformation;
import org.springframework.stereotype.Service;
import org.wangtianyu.userPlatform.Service.DonateProjectInformationService;
import org.wangtianyu.userPlatform.Service.DonateProjectService;
import org.wangtianyu.userPlatform.Service.DonateService;

import java.io.Serializable;
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
    public DonateProject getByProjectId(String projectId) {
        DonateProject project = projectService.selectProjectJoinUser(projectId);
        if(project == null) return null;
        DonateDTO dto = donateService.selectDonateCountInformation(project);
        if(dto == null || dto.getTotalProgress() == null) dto = new DonateDTO(projectId,new BigDecimal(0),0);
        project.setProjectDonateStatics(dto);
        return project;
    }


}




