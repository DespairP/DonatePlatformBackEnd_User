package org.wangtianyu.userPlatform.Apis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.wangtianyu.userPlatform.Model.*;
import org.wangtianyu.userPlatform.Model.PlatformUserLocation;
import org.wangtianyu.userPlatform.Security.PlatformUserDetail;
import org.wangtianyu.userPlatform.Service.*;
import org.wangtianyu.userPlatform.Service.Impl.DonateProjectByUserService;
import org.wangtianyu.userPlatform.Utils.FileOSSUploadUtil;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    PlatformUserInformationService informationService;
    @Autowired
    DonateProjectByUserService donateProjectByUserService;
    @Autowired
    FileOSSUploadUtil uploadUtil;
    @Autowired
    PlatformUserLocationService locationService;
    @Autowired
    UserLoginService userService;
    @Autowired
    PlatformUserWalletService walletService;
    @Autowired
    DonateService donateService;
    @Autowired
    DonateOrderService donateOrderService;

    private static final Map<String,String> SEX_MAP = ImmutableMap.of(
            "male","男",
            "female","女",
            "others","其他"
    );

    private static final Map<String,String> SEX_MAP_REV = ImmutableMap.of(
            "男","male",
            "女","female",
            "其他","others"
    );

    /**获取个人信息*/
    @GetMapping("/me")
    public MessageWrapper<PlatformUserInformation> me() {
        String userId = getUserId();
        if ("".equals(userId))
            return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED, null, "authentication required");
        PlatformUserInformation information = informationService.getOrCreateUserInformation(userId);
        information.setUserSex(SEX_MAP_REV.getOrDefault(information.getUserSex(),"others"));
        return new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS, information, "information get");
    }

    /**获取个人捐助的项目*/
    @GetMapping("/me/donates")
    public MessageWrapper<List<DonateProjectByUser>> meDonates() {
        String userId = getUserId();
        if ("".equals(userId))
            return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED, null, "authentication required");
        return new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS, donateProjectByUserService.getDonateProjectsByUser(userId), "donate get");
    }



    /**上传用户图标*/
    @PostMapping("/upload")
    public MessageWrapper<String> uploadUserIcon(MultipartFile file) throws UnsupportedEncodingException {
        uploadUtil.uploadFile(file);
        return new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,"https://gench-donate-platform.oss-cn-shanghai.aliyuncs.com/img/" + URLEncoder.encode(file.getOriginalFilename(),"UTF-8"),"upload Success");
    }

    /**保存用户上传的图标地址*/
    @PostMapping("/me/saveIcon")
    public MessageWrapper<String> saveUserIcon(@RequestBody Map<String,String> iconPath){
        String userId = getUserId();
        if("".equals(userId)) return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"authentication required");
        boolean result = informationService.update(new UpdateWrapper<PlatformUserInformation>().set("user_icon_path_location",iconPath.getOrDefault("iconPath", "")).eq("user_id",userId));
        return result?new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,"update:1","save Success"):new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"save failed");
    }

    /**获取用户所保存的常驻地址*/
    @GetMapping("/me/locations")
    public MessageWrapper<List<PlatformUserLocation>> getUserLocations(){
        String userId = getUserId();
        if("".equals(userId)) return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"authentication required");
        return new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,locationService.list(new QueryWrapper<PlatformUserLocation>().eq("user_id",userId)),"location get");
    }

    /**加入用户的常驻地址*/
    @PostMapping("/me/addLocation")
    public MessageWrapper<Integer> addUserLocation(@RequestBody PlatformUserLocation location){
        String userId = getUserId();
        if("".equals(userId)) return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"authentication required");
        if(locationService.count(new QueryWrapper<PlatformUserLocation>().eq("user_id",userId)) > 10)
            return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"location over limit:10");
        location.setUserId(userId);
        location.setLocationAreaOrCountry("中国");
        return locationService.save(location) ? new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,1,"location inserted")
                :new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,0,"insert failed");
    }

    /**设置用户的地址为主要常驻地址*/
    @PostMapping("/me/setMainLocation")
    public MessageWrapper<Integer> setUserMainLocation(@RequestBody PlatformUserLocation location){
        String userId = getUserId();
        if("".equals(userId)) return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"authentication required");
        //查询user是否有information存在
        //TODO
        boolean result = false;
        if(locationService.count(new QueryWrapper<PlatformUserLocation>().eq("location_id",location.getLocationId())) > 0)
            result = informationService.update(new UpdateWrapper<PlatformUserInformation>().set("user_primary_location_id",location.getLocationId()).eq("user_id", userId));
        return result?new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,1,"updated")
                :new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,0,"update failed");

    }

    /**获得用户的主要常驻地址*/
    @GetMapping("/me/mainLocation")
    public MessageWrapper<PlatformUserLocation> getMainLocation(){
        String userId = getUserId();
        if("".equals(userId)) return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"authentication required");
        boolean result = false;
        PlatformUserLocation location = informationService.getUserMainLocation(userId);
        return location != null ? new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,location,"mainLocation getted")
                :new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,null,"no mainlocation available");
    }


    /**保存用户的信息*/
    @PostMapping("/me/setInfo")
    public MessageWrapper<Integer> saveUserInfo(@RequestBody Map<String,String> settingMap){
        Platformuser user = getUserPrincipal();
        if(user == null) return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"authentication required");
        if(settingMap.size() <= 0) return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,0,"no information getted");
        String userNickName = settingMap.getOrDefault("userNickName",user.getUserNickName());
        String userId = user.getUserId();
        //查询user是否有information存在
        //TODO
        //开始更新
        boolean result;
        //首先更新user
        result = userService.update(new UpdateWrapper<Platformuser>().set("user_nick_name",userNickName).eq("user_id", userId));
        //然后更新userInfo
        String userSex = SEX_MAP.get(settingMap.getOrDefault("userSex","others"));
        String telephone = settingMap.get("userTelephone");
        String description = settingMap.getOrDefault("userDescription","");
        result = informationService.update(new UpdateWrapper<PlatformUserInformation>().set(!"".equals(userSex),"user_sex", userSex)
                                                                                            .set(!"".equals(telephone),"user_telephone",telephone)
                                                                                            .set("user_description",description)
                                                                                            .eq("user_id",userId)) || result;
        return result?new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,1,"updated")
                :new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,0,"no things to update");
    }

    /**获取用户的钱包信息*/
    @GetMapping("/me/wallet")
    public MessageWrapper<PlatformUserWallet> getUserWallet(){
        String userId = getUserId();
        if("".equals(userId)) return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"authentication required");
        PlatformUserWallet wallet = walletService.getOrCreateUserWallet(userId);
        return wallet != null ? new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,wallet,"wallet getted")
                :new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"wallet not created or getted");
    }

    /**添加用户金钱*/
    @PostMapping ("/me/wallet/add")
    public MessageWrapper<Integer> addUserWalletMoney(@RequestBody Float value){
        String userId = getUserId();
        if("".equals(userId)) return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"authentication required");
        PlatformUserWallet wallet = walletService.getOrCreateUserWallet(userId);
        wallet.setWalletMoney(wallet.getWalletMoney().add(new BigDecimal(value).setScale(2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP));
        return walletService.updateById(wallet)?new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,1,"updated")
                :new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,0,"update failed");
    }

    /**设置用户金钱*/
    @PostMapping ("/me/wallet/set")
    public MessageWrapper<Integer> setUserWalletMoney(@RequestBody Float value){
        String userId = getUserId();
        if("".equals(userId)) return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"authentication required");
        PlatformUserWallet wallet = walletService.getOrCreateUserWallet(userId);
        wallet.setWalletMoney(new BigDecimal(value).setScale(2, RoundingMode.HALF_UP));
        return walletService.updateById(wallet)?new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,1,"updated")
                :new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,0,"update failed");
    }

    /**检查用户是否捐助过某项目*/
    @GetMapping("/me/project/isDonated/{projectId}")
    public MessageWrapper<Boolean> isUserDonated(@PathVariable("projectId") String projectId){
        String userId = getUserId();
        if("".equals(userId)) return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"authentication required");
        boolean result = donateService.count(new QueryWrapper<Donate>().eq("user_id", userId).eq("project_id", projectId)) > 0;
        return new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,result,"result getted");
    }

    /**获取用户在某项目的捐助记录*/
    @GetMapping("/me/project/{projectId}/donatedTier")
    public MessageWrapper<DonateProjectTier> getUserDonatedTierByProjectId(@PathVariable("projectId") String projectId){
        String userId = getUserId();
        if("".equals(userId)) return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"authentication required");
        DonateProjectTier tier = donateService.selectUserDonateTierByProjectId(new Donate().setProjectId(projectId).setUserId(userId));
        return new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,tier,"result getted");
    }

    /**获取用户捐助过的所有捐助项目*/
    @PostMapping("/donate")
    public MessageWrapper<String> donateProject(@RequestBody DonateOrder order) throws Exception {
        String userId = getUserId();
        if("".equals(userId)) return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"authentication required");
        if(order == null || order.getProjectId() == null || order.getOrderLocation() == null || order.getOrderPostalCode() == null || order.getOrderDeliverName() == null)
            return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"未提供任何数据！");
        //检查之前有没有捐助过
        Long counts = donateService.count(new QueryWrapper<Donate>().eq("user_id", userId).eq("project_id", order.getProjectId()));
        if(counts > 0)
            return new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,"已经捐助过该项目！");
        order.setUserId(userId);
        return donateOrderService.donateProject(order)?new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS,"捐助成功","order created sucessfully")
                :new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,"order created failed","order created failed");
    }


    private String getUserId(){
        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof PlatformUserDetail))
            return "";
        PlatformUserDetail detail = (PlatformUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (detail.getUser() != null) return Optional.ofNullable(detail.getUser().getUserId()).orElseGet(() -> "");
        return "";
    }

    private Platformuser getUserPrincipal(){
        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof PlatformUserDetail))
            return null;
        PlatformUserDetail detail = (PlatformUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (detail.getUser() != null) return detail.getUser();
        return null;
    }

}
