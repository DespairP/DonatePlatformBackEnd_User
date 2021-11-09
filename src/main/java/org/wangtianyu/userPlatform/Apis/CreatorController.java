package org.wangtianyu.userPlatform.Apis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.wangtianyu.userPlatform.Model.*;
import org.wangtianyu.userPlatform.Model.CaculateModel.CalculateTierModel;
import org.wangtianyu.userPlatform.Model.Dto.DonateProjectPostingDTO;
import org.wangtianyu.userPlatform.Security.PlatformUserDetail;
import org.wangtianyu.userPlatform.Service.DonateProjectPermissionService;
import org.wangtianyu.userPlatform.Service.DonateProjectService;
import org.wangtianyu.userPlatform.Service.DonateProjectUpdateService;
import org.wangtianyu.userPlatform.Service.DonateService;
import org.wangtianyu.userPlatform.Utils.FileOSSUploadUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/creator")
@CrossOrigin("*")
public class CreatorController {
    @Autowired
    DonateProjectService donateProjectService;
    @Autowired
    DonateProjectPermissionService donateProjectPermissionService;
    @Autowired
    DonateService donateService;
    @Autowired
    FileOSSUploadUtil uploadUtil;
    @Autowired
    DonateProjectUpdateService updateService;

    /**获取所有自己发布的众筹项目的大致信息*/
    @GetMapping("/projects/own")
    public MessageWrapper<List<DonateProject>> getOwnedDonateProject(){
        String userId = getUserId(); // 获取现在正在登录的用户Id
        if("".equals(userId)) return MessageWrapper.createUnauthorizedMessage(); // 如果用户Id为空，说明未登录
        //获取用户现在拥有的项目，并且统计出项目的进度
        List<DonateProject> donateProjects = donateProjectService.selectProjectsWithStatics(new QueryWrapper<DonateProject>().eq("user_id", userId));
        return new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,donateProjects,"donateProject get");
    }

    /**获取用户在这个项目上所拥有的权限*/
    @GetMapping("/project/{projectId}/permission")
    public MessageWrapper<DonateProjectPermission> getProjectPermission(@PathVariable("projectId") String projectId){
        String userId = getUserId(); // 获取现在正在登录的用户Id
        if("".equals(userId)) return MessageWrapper.createUnauthorizedMessage(); // 如果用户Id为空，说明未登录
        DonateProject project = donateProjectService.getById(projectId);
        if(project == null) return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"project not found!");
        if(userId.equals(project.getUserId()))
            return new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,new DonateProjectPermission(projectId,userId,"OWNER"),"permission getted");
        DonateProjectPermission permission = donateProjectPermissionService.getOne(new QueryWrapper<DonateProjectPermission>().eq("user_id", userId)
                                                                                                                              .eq("project_id", projectId));
        if(permission == null) return new MessageWrapper<>(MessageWrapper.BasicStatus.DENY,null,"not permission available");
        return new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,permission,"permission getted");
    }

    /**计算项目中的所有档位，每个档位的捐助数量和捐助总额*/
    @GetMapping("/project/{projectId}/tiers/caculate")
    public MessageWrapper<List<CalculateTierModel>> caculateDonateProjectTierCountsAndSum(@PathVariable String projectId){
        List<String> permissionList = ImmutableList.of("OWNER","MANAGER","WATCHER");
        if(!checkPermissionIsAvailable(getUserId(),projectId,permissionList))
            return new MessageWrapper<>(MessageWrapper.BasicStatus.DENY,null,"not permission available");
        List<CalculateTierModel> calculateTierModels = donateService.calculateProjectDonateCount(projectId);
        return new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,calculateTierModels,"calculateComplete");
    }

    /**上传图片到OSS上*/
    @PostMapping("/upload")
    public MessageWrapper<String> uploadUserIcon(MultipartFile file) throws UnsupportedEncodingException {
        uploadUtil.uploadFile(file);
        return new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,"https://gench-donate-platform.oss-cn-shanghai.aliyuncs.com/img/" + URLEncoder.encode(Objects.requireNonNull(file.getOriginalFilename()),"UTF-8"),"upload Success");
    }

    /**创建项目，包括项目的信息，项目的tiers*/
    @PostMapping("/create/project")
    public MessageWrapper<String> uploadUserIcon(@RequestBody DonateProjectPostingDTO dto) throws Exception {
        String userId = getUserId();
        if("".equals(userId)) return MessageWrapper.createUnauthorizedMessage();
        if(dto == null) return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"no data received");
        return new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,donateProjectService.createProject(userId,dto),"project created!");
    }

    /*创建项目更新信息*/
    @PostMapping("/create/update")
    public MessageWrapper<DonateProjectUpdate> createProjectUpdate(@RequestBody DonateProjectUpdate update){
        String userId = getUserId();
        if(update.getProjectId() == null) return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"received data is null");
        List<String> permissionList = ImmutableList.of("OWNER","MANAGER");
        if(!checkPermissionIsAvailable(userId,update.getProjectId(),permissionList))
            return new MessageWrapper<>(MessageWrapper.BasicStatus.DENY,null,"No Permission Available!");
        update.setProjectUpdateUser(userId);
        boolean result = updateService.save(update);
        if(!result) return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"Insert Failed");
        return new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,update,"Insert Successfully");
    }

    @PostMapping("/delete/update")
    public MessageWrapper<String> deleteProjectUpdate(@RequestBody DonateProjectUpdate update){
        String userId = getUserId();
        if(update.getProjectId() == null) return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"received data is null");
        List<String> permissionList = ImmutableList.of("OWNER","MANAGER");
        if(!checkPermissionIsAvailable(userId,update.getProjectId(),permissionList))
            return new MessageWrapper<>(MessageWrapper.BasicStatus.DENY,null,"No Permission Available!");
        update.setProjectUpdateIsDisposed(true);
        boolean result = updateService.updateById(update);
        if(!result) return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"Delete Failed");
        return new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,"删除成功！","Delete Successfully");
    }


    private String getUserId(){
        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof PlatformUserDetail))
            return "";
        PlatformUserDetail detail = (PlatformUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (detail.getUser() != null) return Optional.ofNullable(detail.getUser().getUserId()).orElseGet(() -> "");
        return "";
    }

    private Platformuser getUserPrincipal(){
        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof PlatformUserDetail))
            return null;
        PlatformUserDetail detail = (PlatformUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (detail.getUser() != null) return detail.getUser();
        return null;
    }

    /**
     * 检验是否有权限访问
     * @param permissions 可接受的permission
     * @return 是否有权限
     * */
    private boolean checkPermissionIsAvailable(String userId,String projectId,List<String> permissions){
        assert permissions != null;
        if("".equals(userId)) return false;
        DonateProject project = donateProjectService.getById(projectId);
        if(project == null) return false;
        if(userId.equals(project.getUserId()))
            return permissions.contains("OWNER");
        DonateProjectPermission permission = donateProjectPermissionService.getOne(new QueryWrapper<DonateProjectPermission>().eq("user_id", userId)
                .eq("project_id", projectId));
        if(permission == null || permission.getUserPermission() == null) return false;
        return permissions.contains(permission.getUserPermission().toUpperCase());
    }

    /**
     * 检验是否有权限访问,并且返回当前的权限
     * @param permissions 可接受的permission
     * @return 能被接受的权限，否则返回null
     * */
    private DonateProjectPermission checkAndGetPermissionIsAvailable(String userId,String projectId,List<String> permissions){
        assert permissions != null;
        if("".equals(userId)) return null;
        DonateProject project = donateProjectService.getById(projectId);
        if(project == null) return null;
        if(userId.equals(project.getUserId()))
            return permissions.contains("OWNER") ? new DonateProjectPermission(projectId,userId,DonateProjectPermission.PERMISSION_OWNER) : null;
        DonateProjectPermission permission = donateProjectPermissionService.getOne(new QueryWrapper<DonateProjectPermission>().eq("user_id", userId)
                .eq("project_id", projectId));
        if(permission == null || permission.getUserPermission() == null) return null;
        return permissions.contains(permission.getUserPermission().toUpperCase()) ? permission : null;
    }
}
