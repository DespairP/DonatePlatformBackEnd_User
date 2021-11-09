package org.wangtianyu.userPlatform.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.wangtianyu.userPlatform.Mapper.DonateProjectUpdateMapper;
import org.wangtianyu.userPlatform.Model.*;
import org.springframework.stereotype.Service;
import org.wangtianyu.userPlatform.Service.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.wangtianyu.userPlatform.Model.DonateProjectPermission.PERMISSION_OWNER;

/**
 *
 */
@Service
public class DonateProjectUpdateServiceImpl extends ServiceImpl<DonateProjectUpdateMapper, DonateProjectUpdate> implements DonateProjectUpdateService {
    @Autowired
    DonateProjectService donateProjectService;
    @Autowired
    DonateProjectPermissionService donateProjectPermissionService;
    @Autowired
    DonateService donateService;
    @Autowired
    DonateProjectTierService donateProjectTierService;

    @Override
    public List<DonateProjectUpdate> getProjectUpdates(String projectId) {
        return list(new QueryWrapper<DonateProjectUpdate>().eq("project_id", projectId).eq("project_update_is_disposed",false));
    }

    @Override
    public List<DonateProjectUpdate> getProjectUpdatesByUserAuthority(String userId,String projectId) {
        assert userId != null;
        List<DonateProjectUpdate> updates = getProjectUpdates(projectId);
        DonateProjectPermission permission = getUserPermissionByProject(userId,projectId); //获取用户管理权限
        //过滤掉管理者/没有动态的项目
        if(updates == null || updates.size() == 0 || permission!= null) return Optional.ofNullable(updates).orElseGet(LinkedList::new);
        final Donate userDonate = donateService.getOne(new QueryWrapper<Donate>().eq("user_id", userId).eq("project_id", projectId));
        DonateProjectTier donateTier = null;
        if(userDonate != null && userDonate.getDonateTierId() != null) donateTier = donateService.selectUserDonateTierByProjectId(userDonate);
        final DonateProjectTier finalDonateTier = donateTier;
        //如果update中有
        //update需求权限 > 0
        //捐助能力 < 需求权限 的更新
        //就隐藏update
        updates.forEach(update->{
            if(update.getProjectUpdateTier() > 0 &&
                    (userDonate == null ||
                    finalDonateTier == null ||
                    finalDonateTier.getProjectDonateTierLevel() < update.getProjectUpdateTier()))
                update.setProjectUpdateInvisible();
        });
        return updates;
    }

    @Override
    public Page<DonateProjectUpdate> getProjectUpdatesByUserAuthorityPageable(String userId,String projectId,Integer currentPage) {
        assert userId != null && currentPage != null;
        if(currentPage <= 0) currentPage = 1;
        Page<DonateProjectUpdate> donateProjectUpdatePage = page(new Page<DonateProjectUpdate>(currentPage,6),
                new QueryWrapper<DonateProjectUpdate>().eq("project_id", projectId).eq("project_update_is_disposed",false).orderByDesc( "project_update_time"));
        DonateProjectPermission permission = getUserPermissionByProject(userId,projectId); //获取用户管理权限
        if(donateProjectUpdatePage == null || donateProjectUpdatePage.getRecords() == null || donateProjectUpdatePage.getTotal() == 0)
            return donateProjectUpdatePage;
        if(permission != null){
            donateProjectUpdatePage.getRecords().forEach(update->{
                update.setIsProjectUpdateVisible(true);
            });
            return donateProjectUpdatePage;
        }
        final Donate userDonate = donateService.getOne(new QueryWrapper<Donate>().eq("user_id", userId).eq("project_id", projectId));
        DonateProjectTier donateTier = null;
        if(userDonate != null && userDonate.getDonateTierId() != null) donateTier = donateService.selectUserDonateTierByProjectId(userDonate);
        final DonateProjectTier finalDonateTier = donateTier;
        donateProjectUpdatePage.getRecords().forEach(update->{
            if(update.getProjectUpdateTier() > 0 &&
                    (userDonate == null ||
                            finalDonateTier == null ||
                            finalDonateTier.getProjectDonateTierLevel() < update.getProjectUpdateTier())) {
                update.setProjectUpdateInvisible();
                return;
            }
            update.setIsProjectUpdateVisible(true);
        });
        return donateProjectUpdatePage;
    }

    private DonateProjectPermission getUserPermissionByProject(String userId,String projectId){
        DonateProject project = donateProjectService.getById(projectId);
        if(project == null) return null;
        if(userId.equals(project.getUserId())) return new DonateProjectPermission(projectId,userId,PERMISSION_OWNER);
        DonateProjectPermission permission = donateProjectPermissionService.getOne(new QueryWrapper<DonateProjectPermission>().eq("user_id", userId).eq("project_id", projectId));
        if(permission != null && permission.getUserPermission() != null) return permission.setUserPermission(permission.getUserPermission().toUpperCase());
        return null;
    }
}




