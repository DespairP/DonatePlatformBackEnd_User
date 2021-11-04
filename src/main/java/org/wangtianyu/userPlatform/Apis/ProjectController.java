package org.wangtianyu.userPlatform.Apis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wangtianyu.userPlatform.Model.DonateProject;
import org.wangtianyu.userPlatform.Model.DonateProjectInformation;
import org.wangtianyu.userPlatform.Model.DonateProjectTier;
import org.wangtianyu.userPlatform.Model.MessageWrapper;
import org.wangtianyu.userPlatform.Service.DonateProjectInformationService;
import org.wangtianyu.userPlatform.Service.DonateProjectService;
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
    @Autowired
    private DonateProjectService projectService;

    /**获取项目的详细信息
     * 注意：项目的详细信息不包含项目的统计信息，须通过项目的大致信息的model进行获取*/
    @GetMapping("/info/{id}")
    public MessageWrapper<DonateProjectInformation> getProjectInfo(@PathVariable("id") String projectId) {
        DonateProjectInformation information = projectInformationService.getInfoByProjectId(projectId);
        if (information == null)
            return new MessageWrapper<DonateProjectInformation>(MessageWrapper.BasicStatus.FAILED, null, "no information get");
        return new MessageWrapper<DonateProjectInformation>(MessageWrapper.BasicStatus.SUCCESS, information, "information get");
    }

    /**
     * 获取项目的全部（不含统计信息）信息，
     * 包含项目的详细信息和大致信息
     * */
    @GetMapping("/{id}")
    public MessageWrapper<DonateProject> getProject(@PathVariable("id") String projectId) {
        DonateProject project = projectInformationService.getInfoAndProjectByProjectId(projectId);
        return project != null ? new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS, project, "project get") : new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED, null, "get failed");
    }

    /**
     * 获取项目的捐助档位
     * */
    @GetMapping("/{id}/tiers")
    public MessageWrapper<List<DonateProjectTier>> getProjectTiers(@PathVariable("id") String projectId){
        if(projectId == null) return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"Project Id is NULL");
        List<DonateProjectTier> tiers = projectTierService.list(new QueryWrapper<DonateProjectTier>().eq("project_id",projectId));
        return tiers != null ? new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,tiers,"tiers get") : new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"tier get failed");
    }

    /**
     * 获得具体项目的具体tier
     * */
    @GetMapping("/{id}/tier/{tierId}")
    public MessageWrapper<DonateProjectTier> getProjectTierInfomation(@PathVariable("id") String projectId,@PathVariable("tierId") String tierId){
        DonateProjectTier tier = projectTierService.getOne(new QueryWrapper<DonateProjectTier>().eq("project_id", projectId)
                                                                                                                        .eq("project_donate_tier_id", tierId));
        return tier != null ? new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,tier,"tier getted")
                :new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"tier not available");

    }




}
