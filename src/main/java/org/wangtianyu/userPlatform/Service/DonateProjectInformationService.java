package org.wangtianyu.userPlatform.Service;

import org.wangtianyu.userPlatform.Model.DonateProject;
import org.wangtianyu.userPlatform.Model.DonateProjectInformation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface DonateProjectInformationService extends IService<DonateProjectInformation> {
    public DonateProjectInformation getInfoByProjectId(String projectId);


    public DonateProject getByProjectId(String projectId);
}
