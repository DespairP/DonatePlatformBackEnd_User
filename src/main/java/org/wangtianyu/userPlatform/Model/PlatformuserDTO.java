package org.wangtianyu.userPlatform.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PlatformuserDTO implements Serializable {
    private String username;
    private String password;

}
