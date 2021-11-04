package org.wangtianyu.userPlatform.Service;

import org.wangtianyu.userPlatform.Model.DonateOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface DonateOrderService extends IService<DonateOrder> {
    /**用户捐助项目*/
    public boolean donateProject(DonateOrder order) throws Exception;
}
