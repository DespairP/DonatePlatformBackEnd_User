package org.wangtianyu.userPlatform.Model.Dto;

import lombok.Data;
import org.wangtianyu.userPlatform.Model.DonateProject;
import org.wangtianyu.userPlatform.Model.DonateProjectInformation;
import org.wangtianyu.userPlatform.Model.DonateProjectTier;

import java.util.List;

/**用于接收项目发布时的数据*/
@Data
public class DonateProjectPostingDTO {

    DonateProject project;

    DonateProjectInformation info;

    List<DonateProjectTier> tiers;
}
