package org.wangtianyu.userPlatform.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.wangtianyu.userPlatform.Model.DonateProject;

import java.util.List;


public interface DonateProjectService extends IService<DonateProject> {
    public List<DonateProject> selectedExhibitProjects();

    public DonateProject selectProjectJoinUser(String projectId);

    public Page<DonateProject> selectProjectPageable(Page<DonateProject> page,Wrapper<DonateProject> wrapper);
}
