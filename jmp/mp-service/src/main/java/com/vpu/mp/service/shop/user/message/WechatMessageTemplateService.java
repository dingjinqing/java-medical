package com.vpu.mp.service.shop.user.message;

import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import com.vpu.mp.db.main.tables.records.MpOfficialAccountUserRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.RegexUtil;
import com.vpu.mp.service.pojo.shop.market.message.RabbitMessageParam;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateConfig;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateData;
import com.vpu.mp.service.pojo.shop.user.message.MaTemplateConfig;
import com.vpu.mp.service.pojo.shop.user.message.MaTemplateData;
import com.vpu.mp.service.pojo.shop.user.user.WxUserInfo;
import com.vpu.mp.service.saas.shop.MpAuthShopService;
import com.vpu.mp.service.saas.shop.official.MpOfficialAccountUserService;
import com.vpu.mp.service.saas.shop.official.message.MpOfficialAccountMessageService;
import com.vpu.mp.service.shop.market.message.MessageTemplateService;
import com.vpu.mp.service.shop.user.user.UserService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.main.tables.MpAuthShop.MP_AUTH_SHOP;
import static com.vpu.mp.db.shop.tables.MpTemplateFormId.MP_TEMPLATE_FORM_ID;
import static com.vpu.mp.db.shop.tables.User.USER;


/**
 * 微信模版消息
 * @author 卢光耀
 * @date 2019-08-29 15:26
 *
*/
@Service
public class WechatMessageTemplateService extends ShopBaseService {

    @Autowired
    private MaTemplateService maTemplateService;

    @Autowired
    private UserService userService;

    private MpOfficialAccountMessageService accountMessageService;

    private MpOfficialAccountUserService accountUserService;

    private MpAuthShopService mpAuthShopService;

    @PostConstruct
    public void init(){
        accountMessageService = saas().shop.mpOfficialAccountMessageService;
        accountUserService = saas().shop.mpOfficialAccountUserService;
        mpAuthShopService = saas().shop.mp;
    }

    /**
     * 小程序和公众号发送其中一个（优先小程序）
     * @param param MQ传参 封装参照{@link MessageTemplateService}的assemblyRabbitMessageParam
     * @param info 所需信息（openID，appID）
     * @return 是否发送成功
     */
    public Boolean sendMessage(RabbitMessageParam param,WxUserInfo info) {
        String formId = getFormId(info.getUserId());
        Boolean success = Boolean.TRUE;
        if( param.getMaTemplateData() != null && StringUtils.isNotBlank(formId) ){
            success = sendMaMessage(param,info,formId);
        }

        if( !success && param.getMpTemplateData() != null && info.getIsSubscribe() ){
            success = sendMpMessage(param,info);
        }
        if( success ){
            //TODO 成功后的处理逻辑
        }
        return success;
    }

    /**
     * 获得formId，并置为已使用
     * @param userId 用户id
     * @return formId
     */
    private String getFormId(Integer userId){
        String formId = db().select()
            .from(MP_TEMPLATE_FORM_ID)
            .where(MP_TEMPLATE_FORM_ID.USER_ID.eq(userId))
            .and(MP_TEMPLATE_FORM_ID.USE_STATE.eq((byte)0))
            .orderBy(MP_TEMPLATE_FORM_ID.CREATE_TIME.desc())
            .fetchAny(MP_TEMPLATE_FORM_ID.FORM_ID);
        db().update(MP_TEMPLATE_FORM_ID)
            .set(MP_TEMPLATE_FORM_ID.USE_STATE,(byte)2)
            .where(MP_TEMPLATE_FORM_ID.FORM_ID.eq(formId))
            .returning(MP_TEMPLATE_FORM_ID.FORM_ID,MP_TEMPLATE_FORM_ID.OPEN_ID)
            .fetch();
        return formId;
    }

    /**
     * 发送小程序模版消息
     * @param param MQ传参
     * @param info  所需信息（openID，appID）
     * @param formId 发消息必须
     * @return 是否发送成功
     */
    public Boolean sendMaMessage(RabbitMessageParam param,WxUserInfo info,String formId) {
        MaTemplateData data = param.getMaTemplateData();
        MaTemplateConfig config =data.getConfig();
        List<String> names = RegexUtil.getSubStrList("{{",".",config.getContent());
        List<WxMaTemplateData> wxDatalist = new ArrayList<>();
        for (int i = 0,len = data.getData().length; i < len; i++) {
            String[] values = data.getData()[i];
            wxDatalist.add(new WxMaTemplateData(
                names.get(i),
                values[0],
                values.length==2?values[1]:config.getColors().get(names.get(i))
            ));
        }
        try {
            maTemplateService.sendMaTemplateMessage(info.getMaAppId(),info.getMaOpenId(),wxDatalist,config,param.getEmphasisKeyword(),param.getPage(),formId);
        } catch (WxErrorException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 发送公众号模版消息
     * @param param MQ传参
     * @param info  所需信息（openID，appID）
     * @return 是否发送成功
     */
    public Boolean sendMpMessage(RabbitMessageParam param,WxUserInfo info) {
        List<WxMpTemplateData> wxDatalist = new ArrayList<>();
        MpTemplateData data = param.getMpTemplateData();
        MpTemplateConfig config =data.getConfig();
        List<String> names = RegexUtil.getSubStrList("{{",".",config.getContent());
        for (int i = 0,len = data.getData().length; i < len; i++) {
            String[] values = data.getData()[i];
            wxDatalist.add(new WxMpTemplateData(
                names.get(i),
                values[0],
                values.length==2?values[1]:config.getColors().get(names.get(i))
            ));
        }
        try{
            accountMessageService.sendMpTemplateMessage(
                info.getMpAppId(),info.getMpOpenId(),wxDatalist,config,info.getMaAppId(),param.getPage(),"");
        } catch (WxErrorException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
            return Boolean.TRUE;
    }

    /**
     * 封装发送模版消息所需要的用户相关信息
     * @param userIdList
     * @return 相关信息
     */
    public List<WxUserInfo> getUserInfoList(List<Integer> userIdList) {
        List<WxUserInfo> resultList = new ArrayList<>(userIdList.size());
        String appId = mpAuthShopService.getAuthShopByShopId(getShopId()).get(MP_AUTH_SHOP.APP_ID);
        List<UserRecord> userList = userService.getUserRecordByIds(userIdList);
        Map<Integer,UserRecord> userMap = userList.stream()
            .collect(Collectors.toMap(UserRecord::getUserId, x->x));
        List<MpOfficialAccountUserRecord> accountUserList =
            accountUserService.getAccountUserListByUnionIds(
                userList.stream().map(x->x.get(USER.WX_UNION_ID)).collect(Collectors.toList())
            );
        Map<String,MpOfficialAccountUserRecord> accountUserAccountMap = accountUserList.stream()
            .collect(Collectors.toMap(MpOfficialAccountUserRecord::getUnionid, x->x));
        userIdList.stream()
            .filter(userMap::containsKey)
            .forEach(x->{
                UserRecord user= userMap.get(x);
                WxUserInfo.WxUserInfoBuilder builder = WxUserInfo.builder()
                    .userId(x)
                    .maAppId(appId)
                    .maOpenId(user.getWxOpenid());
                if( accountUserAccountMap.containsKey(user.getWxUnionId()) ){
                    MpOfficialAccountUserRecord record = accountUserAccountMap.get(user.getWxUnionId());
                    builder.isSubscribe(Boolean.TRUE)
                        .mpAppId(record.getAppId())
                        .mpOpenId(record.getOpenid());
                }
                resultList.add(builder.build());
        });
        return resultList;
    }
}
