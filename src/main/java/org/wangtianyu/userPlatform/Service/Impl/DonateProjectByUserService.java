package org.wangtianyu.userPlatform.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wangtianyu.userPlatform.Model.Donate;
import org.wangtianyu.userPlatform.Model.DonateProject;
import org.wangtianyu.userPlatform.Model.DonateProjectByUser;
import org.wangtianyu.userPlatform.Service.DonateProjectService;
import org.wangtianyu.userPlatform.Service.DonateService;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DonateProjectByUserService {
    @Autowired
    DonateService donateService;
    @Autowired
    DonateProjectService donateProjectService;

    /**获取用户所有捐助过的项目和信息*/
    public List<DonateProjectByUser> getDonateProjectsByUser(String userId){
        List<Donate> donates = donateService.list(new QueryWrapper<Donate>().eq("user_id",userId));
        if(donates == null || donates.size() <= 0) return null;
        Set<String> projectsIds = donates.stream().map(Donate::getProjectId).collect(Collectors.toSet());
        List<DonateProject> projects = donateProjectService.list(new QueryWrapper<DonateProject>().in("project_id", projectsIds));
        Map<String,DonateProject> projectMap = projects.stream().collect(Collectors.toMap(DonateProject::getProjectId,value->value));
        List<DonateProjectByUser> donateProjectByUsers = new LinkedList<>();
        donates.forEach(donate -> {
            donateProjectByUsers.add(new DonateProjectByUser().setUserDonateInformation(donate).setProject(projectMap.getOrDefault(donate.getProjectId(),new DonateProject())));
        });
        return donateProjectByUsers;
    }

}
