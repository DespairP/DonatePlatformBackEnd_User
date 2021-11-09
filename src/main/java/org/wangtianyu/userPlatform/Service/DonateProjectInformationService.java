package org.wangtianyu.userPlatform.Service;

import org.wangtianyu.userPlatform.Model.DonateProject;
import org.wangtianyu.userPlatform.Model.DonateProjectInformation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface DonateProjectInformationService extends IService<DonateProjectInformation> {
    /**通过ProjectId获取项目的详细信息*/
    public DonateProjectInformation getInfoByProjectId(String projectId);

    /**获取具体项目全部的信息，包括项目的统计信息*/
    public DonateProject getInfoAndProjectByProjectId(String projectId);
}
