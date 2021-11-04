package org.wangtianyu.userPlatform.Service;

import org.wangtianyu.userPlatform.Model.PlatformUserWallet;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface PlatformUserWalletService extends IService<PlatformUserWallet> {
    /**获取用户的钱包，如果没有获取到则创建一个*/
    public PlatformUserWallet getOrCreateUserWallet(String id);
}
