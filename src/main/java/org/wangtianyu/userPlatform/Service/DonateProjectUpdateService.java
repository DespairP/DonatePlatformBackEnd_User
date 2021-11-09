package org.wangtianyu.userPlatform.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.wangtianyu.userPlatform.Model.DonateProjectUpdate;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface DonateProjectUpdateService extends IService<DonateProjectUpdate> {
    public List<DonateProjectUpdate> getProjectUpdates(String projectId);

    public List<DonateProjectUpdate> getProjectUpdatesByUserAuthority(String userId,String projectId);

    public Page<DonateProjectUpdate> getProjectUpdatesByUserAuthorityPageable(String userId,String projectId,Integer currentPage);
}
