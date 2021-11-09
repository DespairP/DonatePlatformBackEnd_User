package org.wangtianyu.userPlatform.Service;

import org.wangtianyu.userPlatform.Model.DonateOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface DonateOrderService extends IService<DonateOrder> {
    /**
     * 用户捐助项目，会创建用户的订单order，用户捐助记录donate
     * @param order 捐助订单
     * @return 是否创建成功
     * */
    public boolean donateProject(DonateOrder order) throws Exception;
}
