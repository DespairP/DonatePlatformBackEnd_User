package org.wangtianyu.userPlatform.Service;

import org.wangtianyu.userPlatform.Model.*;
import com.baomidou.mybatisplus.extension.service.IService;
import org.wangtianyu.userPlatform.Model.CaculateModel.CalculateTierModel;
import org.wangtianyu.userPlatform.Model.Dto.DonateDTO;

import java.util.List;

/**
 *
 */
public interface DonateService extends IService<Donate> {
    public List<DonateDTO> selectDonatesCountInformation(List<DonateProject> donateProjects);

    public DonateDTO selectDonateCountInformation(DonateProject donateProject);

    public DonateProjectTier selectUserDonateTierByProjectId(Donate donate);

    /**获取每个tier的捐助数量*/
    public List<CalculateTierModel> calculateProjectDonateCount(String projectId);
}
