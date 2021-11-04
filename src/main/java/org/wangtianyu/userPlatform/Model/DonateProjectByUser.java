package org.wangtianyu.userPlatform.Model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DonateProjectByUser {
    DonateProject project;
    Donate userDonateInformation;
}
