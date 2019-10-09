<template>
    <div class="personalCenterContent">
        <div class="personalCenterContent_main">
            <div class="prompt">
                <img src="@/assets/image/admin/notice_img.png" alt="">
                <span>为避免产生不必要的运营问题，建议关闭不使用的展示配置项</span>
            </div>
            <div class="cententLeft">
                <div class="left_title"></div>
                <div class="left_info">
                    <div class="left_info_head">
                        <div style="width: 100%; height: 148px; background: -webkit-linear-gradient(left, rgb(80, 160, 160), rgb(64, 128, 128));"></div>
                        <img class="center_set" src="@/assets/image/admin/center_set.png" alt="">
                        <img class="center_head" src="@/assets/image/admin/user_touxiang.png" alt="">
                        <div class="center_name">用户昵称</div>
                        <div class="center_sign">
                            <img src="@/assets/image/admin/sign_icon.png" alt="">
                            <span>积分签到：今日可领10积分</span>
                        </div> 
                    </div>
                    <div class="my_order indoor_area_raidus">
                        <div class="orderTitle">
                            <div class="titleLeft">我的订单</div>
                            <div class="titleRight">
                                <span>查看全部订单 </span>
                                <img src="@/assets/image/admin/right_into.png" alt="">
                            </div>
                        </div>
                        <div class="orderContent">
                            <div class="each_item">
                                <div class="item_img">
                                    <img src="@/assets/image/admin/uc_order_icon1.png" alt="">
                                </div>
                                <div class="item_word">待付款</div>
                            </div>
                            <div class="each_item">
                                <div class="item_img">
                                    <img src="@/assets/image/admin/uc_order_icon2.png" alt="">
                                </div>
                                <div class="item_word">待发货</div>
                            </div>
                            <div class="each_item">
                                <div class="item_img">
                                    <img src="@/assets/image/admin/uc_order_icon3.png" alt="">
                                </div>
                                <div class="item_word">待收货</div>
                            </div>
                            <div class="each_item">
                                <div class="item_img">
                                    <img src="@/assets/image/admin/uc_order_icon4.png" alt="">
                                </div>
                                <div class="item_word">评价</div>
                            </div>
                            <div class="each_item">
                                <div class="item_img">
                                    <img src="@/assets/image/admin/uc_order_icon5.png" alt="">
                                </div>
                                <div class="item_word">退款中</div>
                            </div>
                        </div>
                        <div class="orderOperation">
                            <img class="up_img" src="@/assets/image/admin/add_up_use.png" alt="">
                            <img class="down_img" src="@/assets/image/admin/add_down.png" alt="">
                        </div>
                    </div>
                    <div class="my_account indoor_area_raidus">
                        <div class="orderTitle">
                            <div class="titleLeft">我的资产</div>
                        </div>
                        <div class="orderContent">
                            <div class="each_item">
                                <div class="item_num">
                                    <span>1000.99</span>
                                </div>
                                <div class="item_word">我的余额</div>
                            </div>
                            <div class="each_item">
                                <div class="item_num">
                                    <span>1000</span>
                                </div>
                                <div class="item_word">我的积分</div>
                            </div>
                            <div class="each_item">
                                <div class="item_num">
                                    <span>122</span>张
                                </div>
                                <div class="item_word">优惠券</div>
                            </div>
                            <div class="each_item">
                                <div class="item_num">
                                    <span>90</span>张
                                </div>
                                <div class="item_word">优惠卡</div>
                            </div>
                        </div>
                        <div class="orderOperation">
                            <img class="up_img" src="@/assets/image/admin/add_up_use.png" alt="">
                            <img class="down_img" src="@/assets/image/admin/add_down.png" alt="">
                        </div>
                    </div>
                    <div class="use_history indoor_area_raidus">
                        <div class="orderTitle">
                            <div class="titleLeft">使用记录</div>
                        </div>
                        <div class="orderContent">
                            <div class="each_item">
                                <div class="item_his">100</div>
                                <div class="item_word">我的收藏</div>
                            </div>
                            <div class="each_item">
                                <div class="item_his">100</div>
                                <div class="item_word">历史购买</div>
                            </div>
                            <div class="each_item">
                                <div class="item_his">100</div>
                                <div class="item_word">我的足迹</div>
                            </div>
                        </div>
                        <div class="orderOperation">
                            <img class="up_img" src="@/assets/image/admin/add_up_use.png" alt="">
                            <img class="down_img" src="@/assets/image/admin/add_down.png" alt="">
                        </div>
                    </div>
                    <div class="my_appointment indoor_area_raidus">
                        <div class="orderTitle">
                            <div class="titleLeft">我的预约</div>
                            <div class="titleRight">
                                <span>查看全部预约 </span>
                                <img src="@/assets/image/admin/right_into.png" alt="">
                            </div>
                        </div>
                        <div class="orderContent">
                            <div class="app_img" style="margin-left: 10px; border: 1px solid #ccc;
                                width: 40px; height: 40px; overflow: hidden;flex: 0.2;margin-right: 10px;text-align: left;">
                                <img src="@/assets/image/admin/img1.jpg" alt="">
                            </div>
                            <div class="app_info">
                                <p>单人璀璨睫毛嫁接套餐1次</p>
                                <p>预约到店时间：2018-03-05 12:30</p>
                            </div>
                        </div>
                        <div class="orderOperation">
                            <img class="up_img" src="@/assets/image/admin/add_up_use.png" alt="">
                            <img class="down_img" src="@/assets/image/admin/add_down.png" alt="">
                        </div>
                    </div>
                    <div class="my_service indoor_area_raidus">
                        <div class="orderTitle">
                            <div class="titleLeft">我的服务</div>
                        </div>
                        <div class="serveContent clearfix">
                            <!-- <div class="each_serve" style="display: flex;">
                                <div class="serve_img" >
                                    <img src="@/assets/image/admin/icon_dis.png" alt="">
                                </div>
                                <div class="item_word">分销中心</div>
                            </div> -->
                            <!-- <div class="each_serve">
                                <div class="serve_img">
                                    <img src="@/assets/image/admin/BpUFvnYjTCWYSJvB.png" alt="">
                                </div>
                                <div class="item_word">我的砍价</div>
                            </div>
                            <div class="each_serve">
                                <div class="serve_img">
                                    <img src="@/assets/image/admin/I6eh09rewaciTaji.png" alt="">
                                </div>
                                <div class="item_word">我的奖品</div>
                            </div>
                            <div class="each_serve">
                                <div class="serve_img">
                                    <img src="@/assets/image/admin/icon_comment.png" alt="">
                                </div>
                                <div class="item_word">我的评价</div>
                            </div>
                            <div class="each_serve">
                                <div class="serve_img">
                                    <img src="@/assets/image/admin/icon_store.png" alt="">
                                </div>
                                <div class="item_word">门店列表</div>
                            </div>
                            <div class="each_serve">
                                <div class="serve_img">
                                    <img src="@/assets/image/admin/icon_member.png" alt="">
                                </div>
                                <div class="item_word">会员激活</div>
                            </div>
                            <div class="each_serve">
                                <div class="serve_img">
                                    <img src="@/assets/image/admin/icon_scan.png" alt="">
                                </div>
                                <div class="item_word">扫码核销</div>
                            </div>
                            <div class="each_serve">
                                <div class="serve_img">
                                    <img src="@/assets/image/admin/crop_mgsjwtZBwX9XkCts.jpeg" alt="">
                                </div>
                                <div class="item_word">礼物记录</div>
                            </div>
                            <div class="each_serve">
                                <div class="serve_img">
                                    <img src="@/assets/image/admin/icon_ownset.png" alt="">
                                </div>
                                <div class="item_word">代理专区</div>
                            </div> -->
                        </div>
                        <div class="orderOperation">
                            <img class="up_img" src="@/assets/image/admin/add_up_use.png" alt="">
                            <img class="down_img" src="@/assets/image/admin/add_down.png" alt="">
                        </div>
                    </div>
                </div>
            </div>
            <div class="cententRight">
                <div class="cententRight_title">
                    页面布局样式：
                    <el-radio-group v-model="styleChoose">
                        <el-radio label="1">平铺式</el-radio>
                        <el-radio label="2">卡片式</el-radio>
                    </el-radio-group>
                </div>
                <div class="cententRight_container">
                    <el-collapse v-model="activeNames" @change="changeHandler">
                        <el-collapse-item title="个人信息背景色配置" name="1">
                            <el-radio-group v-model="userInfoColor">
                                <el-radio label="1" style="display: block;margin-bottom: 20px;">同步店铺配色</el-radio>
                                <el-radio label="2">自定义图片</el-radio>
                            </el-radio-group>
                        </el-collapse-item>
                        <el-collapse-item title="账户资产信息展示项配置" name="2">
                            <el-form ref="propertyForm" :model="propertyFormInfo" label-width="100px">
                                <el-form-item label="资产：">
                                    <el-switch v-model="propertyForm.switch" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
                                    <span>开关开启，则展示账户资产信息</span>
                                </el-form-item>
                                <el-form-item label="标题：" prop="title">
                                    <el-input style="width: 120px;" v-model="propertyForm.title"></el-input>
                                </el-form-item>
                                <el-form-item label="我的余额：" prop="balance">
                                    <el-radio-group v-model="propertyForm.balance">
                                        <el-radio label="1">展示</el-radio>
                                        <el-radio label="0">不展示</el-radio>
                                    </el-radio-group>
                                </el-form-item>
                                <el-form-item label="我的积分：" prop="points">
                                    <el-radio-group v-model="propertyForm.points">
                                        <el-radio label="1">展示</el-radio>
                                        <el-radio label="0">不展示</el-radio>
                                    </el-radio-group>
                                </el-form-item>
                                <el-form-item label="优惠券：" prop="preferenceTicket">
                                    <el-radio-group v-model="propertyForm.preferenceTicket">
                                        <el-radio label="1">展示</el-radio>
                                        <el-radio label="0">不展示</el-radio>
                                    </el-radio-group>
                                </el-form-item>
                                <el-form-item label="优惠卡：" prop="preferenceCard">
                                    <el-radio-group v-model="propertyForm.preferenceCard">
                                        <el-radio label="1">展示</el-radio>
                                        <el-radio label="0">不展示</el-radio>
                                    </el-radio-group>
                                </el-form-item>
        
                            </el-form>
                        </el-collapse-item>
                        <el-collapse-item title="订单信息展示项配置" name="3">
                            
                        </el-collapse-item>
                        <el-collapse-item title="预约信息展示项配置" name="4">
                            
                        </el-collapse-item>
                        <el-collapse-item title="店铺使用相关数据展示项配置" name="5">
                        
                        </el-collapse-item>
                        <el-collapse-item title="服务展示项配置" name="6">
                            
                        </el-collapse-item>
                    </el-collapse>
                </div>




                



                
            </div>
            <div class="footer">
                <el-button type="primary" size="small" @click="saveClickHandler">保存</el-button>
            </div>
        </div>
    </div>
</template>
<script>
import { mapGetters } from 'vuex'
import ImageDalog from '@/components/admin/imageDalog'
import SelectLinks from '@/components/admin/selectLinks'
// import { bottomGetRequest, bottomUpdateRequest } from '@/api/admin/bottomNavigation'
export default {
  components: { SelectLinks, ImageDalog },
  data () {
    return {
        activeNames: ['1', '2', '3', '4', '5', '6'],
        styleChoose: '2',
        userInfoColor: '1',
        propertyForm: {
            switch: true,
            title: '',
            balance: '1',
            points: '1',
            preferenceTicket: '1',
            preferenceCard:'1'
        },
    }
  },
  computed: {
    // ...mapGetters(['afferentPath']),
    // afferentPath_ () {
    //   return this.afferentPath
    // }
  },
  watch: {
    
  },
  mounted () {
    // 初始化语言
    this.langDefault();
    // 初始化查询
    // this.queryBottom();
  },
  methods: {
    // 保存
    saveClickHandler() {

    },
    changeHandler(val) {
        console.log(val);
    },
  }
}
</script>
<style scoped>
.personalCenterContent {
    padding: 10px;
    min-width: 100%;
    font-size: 14px;
    height: 100%;
    position: relative;
}

.personalCenterContent_main {
    background-color: #fff;
    height: 100%;
    overflow: hidden;
    overflow-y: auto;
    padding-bottom: 96px;
}

.prompt {
    width: 820px;
    height: 40px;
    line-height: 40px;
    border: 1px solid #F2E1C8;
    background: #FFF7EC;
    color: #666;
    padding-left: 12px;
    /* margin: 0 auto; */
    margin-left: 224px;
    margin-top: 80px;
}

.prompt img {
    margin: -3px 5px 0 0;
}

.cententLeft {
    width: 323px;
    border: 1px solid #ccc;
    background: #eee;
    position: relative;
    float: left;
    margin: 20px 0 0 224px;
}

.left_title {
  height: 55px;
  background: url(../../../../../../assets/adminImg/phone_tops.png) no-repeat;
  line-height: 55px;
  text-align: center;
  padding-top: 9px;
  color: #fff;
}

.left_info_head {
    position: relative;
    margin-bottom: 10px;
}

.center_set {
    position: absolute;
    top: 15px;
    right: 10px;
    width: 21px;
    z-index: 2;
}

.center_head {
    position: absolute;
    width: 65px;
    display: block;
    top: 35px;
    left: 10px;
    z-index: 9;
    border-radius: 50%;
}

.center_name {
    width: 100px;
    position: absolute;
    top: 40px;
    left: 90px;
    z-index: 9;
    color: white;
    font-size: 14px;
}

.center_sign {
    background: rgba(0,0,0,0.1);
    color: #fff;
    padding: 4px 10px;
    border-radius: 15px;
    position: absolute;
    top: 70px;
    left: 90px;
    z-index: 9;
    font-size: 12px;
}

.center_sign img {
    width: 12px;
    margin-right: 3px;
}

.indoor_area_raidus {
    width: 96% !important;
    margin: 0 auto !important;
    margin-bottom: 10px !important;

    padding: 10px 0;
    margin-bottom: 10px;
    background: #fff;
    padding-left: 10px;
}

.orderTitle {
    font-weight: bold;

    display: flex;
    justify-content: space-around;
    align-items: center;

    margin-left: 10px;
    font-size: 14px;
    color: #333;
    padding-bottom: 10px;
    border-bottom: 1px solid #eee;
}

.titleLeft {
    flex: 1;
}

.orderTitle .titleRight {
    font-weight: normal;
    color: #999;
    font-size: 12px;
    text-align: right;
    padding-right: 10px;
}

.orderTitle .titleRight img {
    width: 7px;
    position: relative;
    top: 1px;
}

.orderContent {
    padding: 15px 0;
    padding-bottom: 0;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.serveContent {
    flex-wrap: wrap;
    display: flex;
}

.each_item {
    flex: 1;
    display: flex;
    justify-content: center;
    flex-direction: column;
    align-items: center;
    cursor: pointer;

    border: 1px solid #fff;
}

.each_item .item_img {
    margin-bottom: 10px;
}

.each_item .item_img img {
    width: 25px;
    height: 25px;
}

.each_item .item_num {
    color: #408080;
    font-size: 12px;
    margin-bottom: 10px;
}

.each_item .item_num span{
    font-size: 15px;
    font-weight: bold;
}

.each_item .item_his {
    color: #333;
    margin-bottom: 10px;
    font-weight: bold;
}

.each_item .orderContent .app_img {
    margin-left: 10px;
    border: 1px solid #ccc;
    width: 40px;
    height: 40px;
    overflow: hidden;
    flex: 0.2;
    margin-right: 10px;
    text-align: left;
}

.app_img img {
    border: none;
    width: 100%;
    height: 100%;
}

.each_serve {
    display: flex;
    margin-bottom: 20px;
    /* float: left; */
    flex: 30%;
}

.serve_img {
    margin-bottom: 10px;
}

.serve_img img {
    width: 25px;
    height: 25px;
}

.app_info {
    flex: 1.5;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    height: 40px;
}

.app_info p {
    font-size: 14px;
    color: #333;
}

.each_item .item_word  {
    font-size: 12px;
    color: #666;
}

.orderOperation {
    position: absolute;
    right: 8px;
    top: 2px;
    display: none;
    z-index: 8;
}

.orderOperation img {
    padding-right: 5px;
}

.cententRight {
    width: 480px;
    float: left;
    margin: 20px 0 0 15px;
    /* border: 1px solid #e5e5e5; */
    /* background: #f8f8f8; */
    padding: 15px 12px 22px;
    border-radius: 3px;
}

.cententRight_title {
    margin-bottom: 20px;
}

.el-collapse {
    border: none;
}

.el-collapse-item {
    margin-bottom: 20px;
    background: #ccc;
}

.el-collapse-item .el-collapse-item__header {
    font-size: 14px;
    background: #ccc !important;
}

.footer {
   background: #f8f8fa;
    text-align: center;
    box-sizing: border-box;
    height: 50px;
    padding-top: 10px;
    position: fixed;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 2;
}
</style>
