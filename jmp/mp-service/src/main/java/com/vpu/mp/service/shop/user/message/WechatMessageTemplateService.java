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
import com.vpu.mp.service.shop.user.user.UserService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
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

    public void sendMessage(RabbitMessageParam param,WxUserInfo info) throws WxErrorException {
        String pageUrl = param.getPage();
        if( param.getMaTemplateData() != null ){
            String formId = getFormId(info.getUserId());
            sendMaMessage(param,info,pageUrl,formId);
        }else if( param.getMpTemplateData() != null ){
            sendMpMessage(param,info,pageUrl);
        }
    }
    private String getFormId(Integer userId){
        String formId = db().select()
            .from(MP_TEMPLATE_FORM_ID)
            .where(MP_TEMPLATE_FORM_ID.USER_ID.eq(userId))
            .and(MP_TEMPLATE_FORM_ID.STATUS.eq((byte)1))
            .orderBy(MP_TEMPLATE_FORM_ID.CREATE_TIME.desc())
            .fetchAny(MP_TEMPLATE_FORM_ID.FORM_ID);
        db().update(MP_TEMPLATE_FORM_ID)
            .set(MP_TEMPLATE_FORM_ID.USE_STATE,(byte)2)
            .where(MP_TEMPLATE_FORM_ID.FORM_ID.eq(formId))
            .returning(MP_TEMPLATE_FORM_ID.FORM_ID,MP_TEMPLATE_FORM_ID.OPEN_ID)
            .fetch();
        return formId;
    }
    public void sendMaMessage(RabbitMessageParam param,WxUserInfo info,String pageUrl,String formId) throws WxErrorException {
        MaTemplateData data = param.getMaTemplateData();
        MaTemplateConfig config =data.getConfig();
        List<String> names = RegexUtil.getSubStrList("{{",".",config.getContent());
        List<WxMaTemplateData> wxDatalist = new ArrayList<>();
        for (int i = 0,len = data.getData().length; i < len; i++) {
            String[] values = data.getData()[i];
            int values_len = values.length;
            WxMaTemplateData wxData = new WxMaTemplateData();
            wxData.setName(names.get(i));
            wxData.setValue(values[0]);
            if( values_len == 2 ){
                wxData.setColor(values[1]);
            }
            wxData.setColor(config.getColors().get(names.get(i)));
            wxDatalist.add(wxData);
        }
        maTemplateService.sendMpTemplateMessage(info.getMaAppId(),info.getMaOpenId(),wxDatalist,config,param.getEmphasisKeyword(),pageUrl,formId);
    }

    public void sendMpMessage(RabbitMessageParam param,WxUserInfo info,String pageUrl) throws WxErrorException {
        List<WxMpTemplateData> wxDatalist = new ArrayList<>();
        MpTemplateData data = param.getMpTemplateData();
        MpTemplateConfig config =data.getConfig();
        List<String> names = RegexUtil.getSubStrList("{{",".",config.getContent());
        for (int i = 0,len = data.getData().length; i < len; i++) {
            String[] values = data.getData()[i];
            int values_len = values.length;
            WxMpTemplateData wxData = new WxMpTemplateData();
            wxData.setName(names.get(i));
            wxData.setValue(values[0]);
            if( values_len == 2 ){
                wxData.setColor(values[1]);
            }
            wxData.setColor(config.getColors().get(names.get(i)));
            wxDatalist.add(wxData);
        }
        accountMessageService.sendMpTemplateMessage(
            info.getMpAppId(),info.getMpOpenId(),wxDatalist,config,info.getMpAppId(),pageUrl,"");
    }

    public List<WxUserInfo> getUserInfoList(List<Integer> userIdList) {
        List<WxUserInfo> resultList = new ArrayList<>(userIdList.size());
        String appId = mpAuthShopService.getAuthShopByShopId(getShopId()).get(MP_AUTH_SHOP.APP_ID);
        List<UserRecord> userList = userService.getUserRecordByIds(userIdList);
        Map<Integer,UserRecord> userMap = userList.stream()
            .collect(Collectors.toMap(x->x.getUserId(),x->x));
        List<MpOfficialAccountUserRecord> accountUserList =
            accountUserService.getAccountUserListByUnionIds(
                userList.stream().map(x->x.get(USER.WX_UNION_ID)).collect(Collectors.toList())
            );
        Map<String,MpOfficialAccountUserRecord> accountUserAccountMap = accountUserList.stream()
            .collect(Collectors.toMap(x->x.getUnionid(),x->x));
        userIdList.stream()
            .filter(x->userMap.containsKey(x))
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
