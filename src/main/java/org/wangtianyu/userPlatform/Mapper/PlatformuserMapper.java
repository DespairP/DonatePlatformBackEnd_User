package org.wangtianyu.userPlatform.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.wangtianyu.userPlatform.Model.Platformuser;

/**
* @Entity org.wangtianyu.userPlatform.domain.Platformuser
*/
public interface PlatformuserMapper extends BaseMapper<Platformuser> {

    public Platformuser userLogin(@Param("account") String account);


}
