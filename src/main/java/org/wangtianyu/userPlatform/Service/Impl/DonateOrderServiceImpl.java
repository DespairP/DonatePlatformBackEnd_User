package org.wangtianyu.userPlatform.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.wangtianyu.userPlatform.Mapper.DonateOrderMapper;
import org.wangtianyu.userPlatform.Model.Donate;
import org.wangtianyu.userPlatform.Model.DonateOrder;
import org.wangtianyu.userPlatform.Model.MessageWrapper;
import org.wangtianyu.userPlatform.Service.DonateOrderService;
import org.springframework.stereotype.Service;
import org.wangtianyu.userPlatform.Service.DonateService;

/**
 *
 */
@Service
public class DonateOrderServiceImpl extends ServiceImpl<DonateOrderMapper, DonateOrder> implements DonateOrderService {
    @Autowired
    DonateService donateService;


    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean donateProject(DonateOrder order) throws Exception{
        //首先先插入到donate表中
        Donate donate = new Donate().setUserId(order.getUserId()).setDonatePrice(order.getDonatePrice()).setDonateTierId(order.getProjectDonateTierId()).setProjectId(order.getProjectId());
        boolean result = donateService.save(donate);
        if(!result) throw new Exception("服务器异常");
        //再插入到订单表中
        result = save(order);
        if(!result) throw new Exception("服务器异常");
        return result;
    }
}




