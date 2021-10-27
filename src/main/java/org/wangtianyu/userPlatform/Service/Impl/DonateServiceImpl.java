package org.wangtianyu.userPlatform.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.wangtianyu.userPlatform.Mapper.DonateMapper;
import org.wangtianyu.userPlatform.Model.Donate;
import org.wangtianyu.userPlatform.Model.DonateDTO;
import org.wangtianyu.userPlatform.Model.DonateProject;
import org.wangtianyu.userPlatform.Service.DonateService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class DonateServiceImpl extends ServiceImpl<DonateMapper, Donate> implements DonateService{
    @Override
    public List<DonateDTO> selectDonatesCountInformation(List<DonateProject> donateProjects) {
        return baseMapper.selectDonatesCountInformation(donateProjects);
    }

    @Override
    public DonateDTO selectDonateCountInformation(DonateProject donateProject) {
        return baseMapper.selectDonateCountInformation(donateProject);
    }
}




