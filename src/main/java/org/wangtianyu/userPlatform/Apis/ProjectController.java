package org.wangtianyu.userPlatform.Apis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wangtianyu.userPlatform.Model.DonateProject;
import org.wangtianyu.userPlatform.Model.DonateProjectInformation;
import org.wangtianyu.userPlatform.Model.DonateProjectTier;
import org.wangtianyu.userPlatform.Model.MessageWrapper;
import org.wangtianyu.userPlatform.Service.DonateProjectInformationService;
import org.wangtianyu.userPlatform.Service.DonateProjectTierService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    private DonateProjectInformationService projectInformationService;
    @Autowired
    private DonateProjectTierService projectTierService;

    @GetMapping("/info/{id}")
    public MessageWrapper<DonateProjectInformation> getProjectInfo(@PathVariable("id") String projectId) {
        DonateProjectInformation information = projectInformationService.getInfoByProjectId(projectId);
        if (information == null)
            return new MessageWrapper<DonateProjectInformation>(MessageWrapper.BasicStatus.FAILED, null, "no information get");
        return new MessageWrapper<DonateProjectInformation>(MessageWrapper.BasicStatus.SUCCESS, information, "information get");
    }

    @GetMapping("/{id}")
    public MessageWrapper<DonateProject> getProject(@PathVariable("id") String projectId) {
        DonateProject project = projectInformationService.getByProjectId(projectId);
        return project != null ? new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS, project, "project get") : new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED, null, "get failed");
    }
    
    @GetMapping("/{id}/tiers")
    public MessageWrapper<List<DonateProjectTier>> getProjectTiers(@PathVariable("id") String projectId){
        if(projectId == null) return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"Project Id is NULL");
        List<DonateProjectTier> tiers = projectTierService.list(new QueryWrapper<DonateProjectTier>().eq("project_id",projectId));
        return tiers != null ? new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,tiers,"tiers get") : new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"tier get failed");
    }
}
