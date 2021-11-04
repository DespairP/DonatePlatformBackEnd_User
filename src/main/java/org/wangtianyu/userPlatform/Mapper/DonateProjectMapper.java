package org.wangtianyu.userPlatform.Mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.wangtianyu.userPlatform.Model.DonateProject;

import java.util.List;

/**
* @Entity org.wangtianyu.userPlatform.Model.DonateProject
*/
public interface DonateProjectMapper extends BaseMapper<DonateProject> {
    public List<DonateProject> selectedExhibitProjects();

    public DonateProject selectProjectJoinWithUser(@Param("projectId") String projectId);

    public Page<DonateProject> selectProjectPageable(Page<DonateProject> page,@Param("ew")  Wrapper<DonateProject> projectWrapper);
}
