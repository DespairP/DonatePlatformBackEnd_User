package org.wangtianyu.userPlatform.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.wangtianyu.userPlatform.Model.DonateProject;
import org.wangtianyu.userPlatform.Model.Dto.DonateProjectPostingDTO;

import java.util.List;


public interface DonateProjectService extends IService<DonateProject> {
    /**获取项目前8位的项目，并连接发布作者，通过merge加入项目的统计信息（包括众筹人数等等）*/
    public List<DonateProject> selectedExhibitProjects();

    /**获取项目的大致信息，和项目连接的发布作者*/
    public DonateProject selectProjectJoinUser(String projectId);

    /**分页获取项目，并连接发布的作者，通过merge加入项目的统计信息（包括众筹人数等等）*/
    public Page<DonateProject> selectProjectPageable(Page<DonateProject> page,Wrapper<DonateProject> wrapper);

    /**获取符合条件的所有项目，*项目不含user*，通过merge加入项目的统计信息（包括众筹人数等等）*/
    public List<DonateProject> selectProjectsWithStatics(Wrapper<DonateProject> wrapper);

    /**创建一个众筹项目*/
    public String createProject(String userId,DonateProjectPostingDTO dto) throws Exception;
}
