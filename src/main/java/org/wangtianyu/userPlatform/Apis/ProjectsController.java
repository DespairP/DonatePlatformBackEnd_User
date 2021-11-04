package org.wangtianyu.userPlatform.Apis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wangtianyu.userPlatform.Model.MessageWrapper;
import org.wangtianyu.userPlatform.Model.DonateProject;
import org.wangtianyu.userPlatform.Service.DonateProjectService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/projects")
public class ProjectsController {

    @Autowired
    DonateProjectService service;

    private final static int PAGE_SIZE = 8;

    /**获取展示的项目组*/
    @GetMapping("/exhibit")
    public MessageWrapper<List<DonateProject>> getExhibitProjects() {
        return new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS, service.selectedExhibitProjects(), "data fetched!");
    }

    /**分页获取过滤后的项目组*/
    @GetMapping("/list")
    public MessageWrapper<Page<DonateProject>> getPageableProject(@RequestParam(value = "page", required = false, defaultValue = "1") Integer currentPage,
                                                                  @RequestParam(value = "time", required = false) String timeDto,
                                                                  @RequestParam(value = "status", required = false) String statusDto) {
        DonateProject.EnumProjectStatus status = DonateProject.EnumProjectStatus.APPROVED;
        //当statusDto为none时不进行过滤
        try {
            if (statusDto != null && !"".equals(statusDto) && !"none".equals(statusDto)) status = DonateProject.EnumProjectStatus.valueOf(statusDto);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        Page<DonateProject> page = new Page<>(Optional.ofNullable(currentPage).orElse(1), PAGE_SIZE);
        page = service.selectProjectPageable(page, new QueryWrapper<DonateProject>().orderBy(timeDto != null && !timeDto.equals("") && !timeDto.equals("none"), "asc".equals(timeDto), "project_publish_public_date")
                                                                                    .eq(!"none".equals(statusDto) && statusDto != null,"project_status",status.getMySqlIndex()));
        return new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS, page, "page get");
    }

    /**分页搜索项目组*/
    @GetMapping("/search")
    public MessageWrapper<Page<DonateProject>> searchProject(@RequestParam(value = "key",required = false,defaultValue = "") String key,
                                                             @RequestParam(value = "page", required = false, defaultValue = "1") Integer currentPage
                                                             ){
        if(key == null || "".equals(key)) return new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,null,"key contains nothing");
        Page<DonateProject> page = new Page<>(Optional.ofNullable(currentPage).orElse(1), 6);
        page = service.selectProjectPageable(page,new QueryWrapper<DonateProject>().like("project_name",key));
        return new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,page,"page get");
    }
}
