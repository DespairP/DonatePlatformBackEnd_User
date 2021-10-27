package org.wangtianyu.userPlatform.Service;

import org.wangtianyu.userPlatform.Model.Donate;
import com.baomidou.mybatisplus.extension.service.IService;
import org.wangtianyu.userPlatform.Model.DonateDTO;
import org.wangtianyu.userPlatform.Model.DonateProject;

import java.util.List;

/**
 *
 */
public interface DonateService extends IService<Donate> {
    public List<DonateDTO> selectDonatesCountInformation(List<DonateProject> donateProjects);

    public DonateDTO selectDonateCountInformation(DonateProject donateProject);
}
