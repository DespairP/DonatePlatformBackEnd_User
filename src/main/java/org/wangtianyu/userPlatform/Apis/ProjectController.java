package org.wangtianyu.userPlatform.Apis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.wangtianyu.userPlatform.Model.*;
import org.wangtianyu.userPlatform.Security.PlatformUserDetail;
import org.wangtianyu.userPlatform.Service.DonateProjectInformationService;
import org.wangtianyu.userPlatform.Service.DonateProjectService;
import org.wangtianyu.userPlatform.Service.DonateProjectTierService;
import org.wangtianyu.userPlatform.Service.DonateProjectUpdateService;

import java.util.List;
import java.util.Optional;

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
    @Autowired
    private DonateProjectUpdateService projectUpdateService;
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
     * 获取项目的全部（包含统计信息）信息，
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

    @GetMapping("/{id}/updates")
    public MessageWrapper<List<DonateProjectUpdate>> getProjectUpdates(@PathVariable("id") String projectId){
        String userId = getUserId();
        if(projectService.getById(projectId) == null)
            return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"项目不存在！");
        return new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,projectUpdateService.getProjectUpdatesByUserAuthority(userId,projectId),"updates get");
    }

    @GetMapping("/{id}/updates/page")
    public MessageWrapper<Page<DonateProjectUpdate>> getProjectUpdates(@PathVariable("id") String projectId, @RequestParam(value = "page",defaultValue = "1")Integer currentPage){
        String userId = getUserId();
        if(projectService.getById(projectId) == null)
            return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"项目不存在！");
        return new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,projectUpdateService.getProjectUpdatesByUserAuthorityPageable(userId,projectId,currentPage),"updates get");
    }


    private String getUserId(){
        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof PlatformUserDetail))
            return "";
        PlatformUserDetail detail = (PlatformUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (detail.getUser() != null) return Optional.ofNullable(detail.getUser().getUserId()).orElse("");
        return "";
    }

}
