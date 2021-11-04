package org.wangtianyu.userPlatform.Service;

import org.wangtianyu.userPlatform.Model.DonateProject;
import org.wangtianyu.userPlatform.Model.DonateProjectInformation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface DonateProjectInformationService extends IService<DonateProjectInformation> {
    public DonateProjectInformation getInfoByProjectId(String projectId);

    /**获取项目全部可能的信息*/
    public DonateProject getInfoAndProjectByProjectId(String projectId);
}
