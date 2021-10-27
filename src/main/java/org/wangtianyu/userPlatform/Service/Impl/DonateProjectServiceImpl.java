package org.wangtianyu.userPlatform.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.wangtianyu.userPlatform.Mapper.DonateProjectMapper;
import org.wangtianyu.userPlatform.Model.DonateDTO;
import org.wangtianyu.userPlatform.Model.DonateProject;
import org.springframework.stereotype.Service;
import org.wangtianyu.userPlatform.Service.DonateProjectService;
import org.wangtianyu.userPlatform.Service.DonateService;

import java.math.BigDecimal;
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

    @Override
    public List<DonateProject> selectedExhibitProjects() {
        List<DonateProject> projects =  getBaseMapper().selectedExhibitProjects();
        mergeDonateToProjectCalculate(projects);
        return projects;
    }

    @Override
    public DonateProject selectProjectJoinUser(String projectId) {
        return getBaseMapper().selectProjectJoinWithUser(projectId);
    }

    @Override
    public Page<DonateProject> selectProjectPageable(Page<DonateProject> page, Wrapper<DonateProject> wrapper) {
        Page<DonateProject> resultPage = getBaseMapper().selectProjectPageable(page,wrapper);
        mergeDonateToProjectCalculate(resultPage.getRecords());
        return resultPage;
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
