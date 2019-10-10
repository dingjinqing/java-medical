<template>
  <div class="personalCenterContent">
    <div class="personalCenterContent_main">
      <div class="prompt">
        <img
          src="@/assets/image/admin/notice_img.png"
          alt=""
        >
        <span>{{ $t('personalCenter.warningTip') }}</span>
      </div>
      <div class="cententLeft">
        <div class="_title"></div>
        <div class="left_info">
          <div class="left_info_head">
            <div
              class="left_info_headBg"
              v-if="this.userInfoColor=='1'"
              style="background: -webkit-linear-gradient(left,rgb(80, 160, 160),rgb(64, 128, 128));"
            ></div>
            <div
              class="left_info_headBg"
              v-if="this.userInfoColor=='2'"
              :style="{'backgroundImage': this.customizeImg.length==0 ? '' : 'url(' + this.customizeImg[0].imgUrl + ')'}"
            ></div>
            <img
              class="center_set"
              src="@/assets/image/admin/center_set.png"
              alt=""
            >
            <img
              class="center_head"
              src="@/assets/image/admin/user_touxiang.png"
              alt=""
            >
            <div class="center_name">{{ $t('personalCenter.userNickname') }}</div>
            <div class="center_sign">
              <img
                src="@/assets/image/admin/sign_icon.png"
                alt=""
              >
              <span>{{ $t('personalCenter.integralTip') }}</span>
            </div>
          </div>
          <div>
            <!-- v-for="(item, index) in leftList" :key="index" -->
            <div
              class="my_order indoor_area_raidus"
              v-if="this.orderFormInfo.switch == true"
              :class="this.styleChoose=='1'? 'widthActive' : ''"
            >
              <div class="orderTitle">
                <div
                  class="titleLeft"
                  v-if="this.orderFormInfo.moduleTitle!=''"
                >{{ orderFormInfo.moduleTitle }}</div>
                <div
                  class="titleRight"
                  v-if="this.orderFormInfo.moduleStyle=='1'"
                >
                  <span>查看全部订单 </span>
                  <img
                    src="@/assets/image/admin/right_into.png"
                    alt=""
                  >
                </div>
              </div>
              <div class="orderContent">
                <div class="each_item">
                  <div class="item_img">
                    <img
                      src="@/assets/image/admin/uc_order_icon1.png"
                      alt=""
                    >
                  </div>
                  <div class="item_word">待付款</div>
                </div>
                <div class="each_item">
                  <div class="item_img">
                    <img
                      src="@/assets/image/admin/uc_order_icon2.png"
                      alt=""
                    >
                  </div>
                  <div class="item_word">待发货</div>
                </div>
                <div class="each_item">
                  <div class="item_img">
                    <img
                      src="@/assets/image/admin/uc_order_icon3.png"
                      alt=""
                    >
                  </div>
                  <div class="item_word">待收货</div>
                </div>
                <div
                  class="each_item"
                  v-if="this.orderFormInfo.moduleStyle=='1'"
                >
                  <div class="item_img">
                    <img
                      src="@/assets/image/admin/uc_order_icon4.png"
                      alt=""
                    >
                  </div>
                  <div class="item_word">评价</div>
                </div>
                <div class="each_item">
                  <div class="item_img">
                    <img
                      src="@/assets/image/admin/uc_order_icon5.png"
                      alt=""
                    >
                  </div>
                  <div class="item_word">退款中</div>
                </div>
                <div
                  class="each_item_special"
                  v-if="this.orderFormInfo.moduleStyle=='0'"
                >
                  <img
                    src="@/assets/image/admin/icon_jiantou.png"
                    alt=""
                  >

                  <div class="each_item">
                    <div class="item_img">
                      <img
                        src="@/assets/image/admin/uc_order_icon6.png"
                        alt=""
                      >
                    </div>
                    <div class="item_word">全部订单</div>
                  </div>

                </div>

              </div>
              <div class="orderOperation">
                <img
                  class="up_img"
                  src="@/assets/image/admin/add_up_use.png"
                  alt=""
                  @click="upClickHandler(0)"
                >
                <img
                  class="down_img"
                  src="@/assets/image/admin/add_down.png"
                  alt=""
                  @click="downClickHandler(0)"
                >
              </div>
            </div>
            <div
              class="my_account indoor_area_raidus"
              v-if="this.propertyFormInfo.switch == true"
              :class="this.styleChoose=='1'? 'widthActive' : ''"
            >
              <div class="orderTitle">
                <div
                  class="titleLeft"
                  v-if="this.propertyFormInfo.title!=''"
                >{{ propertyFormInfo.title }}</div>
              </div>
              <div class="orderContent">
                <div
                  class="each_item"
                  v-if="this.propertyFormInfo.balance=='1'"
                >
                  <div class="item_num">
                    <span>1000.99</span>
                  </div>
                  <div class="item_word">我的余额</div>
                </div>
                <div
                  class="each_item"
                  v-if="this.propertyFormInfo.points=='1'"
                >
                  <div class="item_num">
                    <span>1000</span>
                  </div>
                  <div class="item_word">我的积分</div>
                </div>
                <div
                  class="each_item"
                  v-if="this.propertyFormInfo.preferenceTicket=='1'"
                >
                  <div class="item_num">
                    <span>122</span>张
                  </div>
                  <div class="item_word">优惠券</div>
                </div>
                <div
                  class="each_item"
                  v-if="this.propertyFormInfo.preferenceCard=='1'"
                >
                  <div class="item_num">
                    <span>90</span>张
                  </div>
                  <div class="item_word">优惠卡</div>
                </div>
              </div>
              <div class="orderOperation">
                <img
                  class="up_img"
                  src="@/assets/image/admin/add_up_use.png"
                  alt=""
                  @click="upClickHandler(1)"
                >
                <img
                  class="down_img"
                  src="@/assets/image/admin/add_down.png"
                  alt=""
                  @click="downClickHandler(1)"
                >
              </div>
            </div>
            <div
              class="use_history indoor_area_raidus"
              v-if="this.shopUseFormInfo.switch == true"
              :class="this.styleChoose=='1'? 'widthActive' : ''"
            >
              <div class="orderTitle">
                <div
                  class="titleLeft"
                  v-if="this.shopUseFormInfo.shopUseTitle!=''"
                >{{ shopUseFormInfo.shopUseTitle }}</div>
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
                <img
                  class="up_img"
                  src="@/assets/image/admin/add_up_use.png"
                  alt=""
                  @click="upClickHandler(2)"
                >
                <img
                  class="down_img"
                  src="@/assets/image/admin/add_down.png"
                  alt=""
                  @click="downClickHandler(2)"
                >
              </div>
            </div>
            <div
              class="my_appointment indoor_area_raidus"
              v-if="this.appointmentFormInfo.switch == true"
              :class="this.styleChoose=='1'? 'widthActive' : ''"
            >
              <div class="orderTitle">
                <div
                  class="titleLeft"
                  v-if="this.appointmentFormInfo.appointmentTitle!=''"
                >{{ appointmentFormInfo.appointmentTitle }}</div>
                <div class="titleRight">
                  <span>查看全部预约 </span>
                  <img
                    src="@/assets/image/admin/right_into.png"
                    alt=""
                  >
                </div>
              </div>
              <div class="orderContent">
                <div
                  class="app_img"
                  style="margin-left: 10px; border: 1px solid #ccc;
                                        width: 40px; height: 40px; overflow: hidden;flex: 0.2;margin-right: 10px;text-align: left;"
                >
                  <img
                    src="@/assets/image/admin/img1.jpg"
                    alt=""
                  >
                </div>
                <div class="app_info">
                  <p>单人璀璨睫毛嫁接套餐1次</p>
                  <p>预约到店时间：2018-03-05 12:30</p>
                </div>
              </div>
              <div class="orderOperation">
                <img
                  class="up_img"
                  src="@/assets/image/admin/add_up_use.png"
                  alt=""
                  @click="upClickHandler(3)"
                >
                <img
                  class="down_img"
                  src="@/assets/image/admin/add_down.png"
                  alt=""
                  @click="downClickHandler(3)"
                >
              </div>
            </div>
            <div
              class="my_service indoor_area_raidus"
              v-if="this.serveShowFormInfo.switch == true"
              :class="this.styleChoose=='1'? 'widthActive' : ''"
            >
              <div class="orderTitle">
                <div
                  class="titleLeft"
                  v-if="this.serveShowFormInfo.serveShowTitle!=''"
                >{{ serveShowFormInfo.serveShowTitle }}</div>
              </div>
              <div
                class="serveContent clearfix"
                style="flex-wrap: wrap"
              >
                <div
                  class="each_serve"
                  style="display: flex;"
                  v-if="this.serveShowFormInfo.retailCenter=='1'"
                >
                  <div class="serve_img">
                    <img
                      src="@/assets/image/admin/icon_dis.png"
                      alt=""
                    >
                  </div>
                  <div class="serve_word">分销中心</div>
                </div>
                <div
                  class="each_serve"
                  style="display: flex;"
                  v-if="this.serveShowFormInfo.myDiscount=='1'"
                >
                  <div class="serve_img">
                    <img
                      src="@/assets/image/admin/BpUFvnYjTCWYSJvB.png"
                      alt=""
                    >
                  </div>
                  <div class="serve_word">我的砍价</div>
                </div>
                <div
                  class="each_serve"
                  style="display: flex;"
                  v-if="this.serveShowFormInfo.myPrize=='1'"
                >
                  <div class="serve_img">
                    <img
                      src="@/assets/image/admin/I6eh09rewaciTaji.png"
                      alt=""
                    >
                  </div>
                  <div class="serve_word">我的奖品</div>
                </div>
                <div
                  class="each_serve"
                  style="display: flex;"
                  v-if="this.serveShowFormInfo.myAppraise=='1'"
                >
                  <div class="serve_img">
                    <img
                      src="@/assets/image/admin/icon_comment.png"
                      alt=""
                    >
                  </div>
                  <div class="serve_word">我的评价</div>
                </div>
                <div
                  class="each_serve"
                  style="display: flex;"
                  v-if="this.serveShowFormInfo.shopList=='1'"
                >
                  <div class="serve_img">
                    <img
                      src="@/assets/image/admin/icon_store.png"
                      alt=""
                    >
                  </div>
                  <div class="serve_word">门店列表</div>
                </div>
                <div
                  class="each_serve"
                  style="display: flex;"
                  v-if="this.serveShowFormInfo.activation=='1'"
                >
                  <div class="serve_img">
                    <img
                      src="@/assets/image/admin/icon_member.png"
                      alt=""
                    >
                  </div>
                  <div class="serve_word">会员激活</div>
                </div>
                <div
                  class="each_serve"
                  style="display: flex;"
                  v-if="this.serveShowFormInfo.cancellation=='1'"
                >
                  <div class="serve_img">
                    <img
                      src="@/assets/image/admin/icon_scan.png"
                      alt=""
                    >
                  </div>
                  <div class="serve_word">扫码核销</div>
                </div>
                <div
                  class="each_serve"
                  style="display: flex;"
                  v-if="this.serveShowFormInfo.giftRecord=='1'"
                >
                  <div class="serve_img">
                    <img
                      src="@/assets/image/admin/crop_mgsjwtZBwX9XkCts.jpeg"
                      alt=""
                    >
                  </div>
                  <div class="serve_word">礼物记录</div>
                </div>
                <div
                  class="each_serve"
                  style="display: flex;"
                  v-if="this.serveShowFormInfo.retailCenter=='1'"
                >
                  <div class="serve_img">
                    <img
                      src="@/assets/image/admin/icon_ownset.png"
                      alt=""
                    >
                  </div>
                  <div class="serve_word">代理专区</div>
                </div>
              </div>
              <div class="orderOperation">
                <img
                  class="up_img"
                  src="@/assets/image/admin/add_up_use.png"
                  alt=""
                  @click="upClickHandler(4)"
                >
                <img
                  class="down_img"
                  src="@/assets/image/admin/add_down.png"
                  alt=""
                  @click="downClickHandler(4)"
                >
              </div>
            </div>
          </div>

        </div>
      </div>
      <div class="cententRight">
        <div class="cententRight_title">
          {{ $t('personalCenter.layoutStyle') }}：
          <el-radio-group v-model="styleChoose">
            <el-radio label="1">{{ $t('personalCenter.flatStyle') }}</el-radio>
            <el-radio label="2">{{ $t('personalCenter.cardStyle') }}</el-radio>
          </el-radio-group>
        </div>
        <div class="cententRight_container">
          <el-collapse
            v-model="activeNames"
            @change="changeHandler"
          >
            <el-collapse-item
              title="个人信息背景色配置"
              name="1"
              style="margin-bottom: 20px;"
            >
              <el-radio-group v-model="userInfoColor">
                <el-radio
                  label="1"
                  style="display: block;margin-bottom: 20px;"
                >同步店铺配色</el-radio>
                <el-radio label="2">自定义图片</el-radio>
                <div
                  class="customizeImgWrap"
                  @click="changeCustomizeImg"
                  v-for="(item,index) in customizeImg"
                  :key="index"
                >
                  <el-image
                    fit="cover"
                    :src="item.imgUrl"
                    style="width: 100%; height: 100%"
                  ></el-image>
                </div>
                <div
                  class="customizeImgWrap"
                  @click="changeCustomizeImg"
                  v-if="this.customizeImg.length==0"
                >
                  <el-image
                    fit="scale-down"
                    :src="imgHost+'/image/admin/add_img_bg.png'"
                    style="width: 100%; height: 100%"
                  />
                </div>
              </el-radio-group>
            </el-collapse-item>
            <el-collapse-item
              title="账户资产信息展示项配置"
              name="2"
            >
              <el-form
                :model="propertyFormInfo"
                label-width="100px"
              >
                <el-form-item label="资产：">
                  <el-switch
                    v-model="propertyFormInfo.switch"
                    active-color="#13ce66"
                    inactive-color="#ff4949"
                  ></el-switch>
                  <span>开关开启，则展示账户资产信息</span>
                </el-form-item>
                <el-form-item
                  label="标题："
                  prop="title"
                >
                  <el-input
                    style="width: 120px;"
                    v-model="propertyFormInfo.title"
                  ></el-input>
                  <span>用户资产模块标题，不填写则不显示</span>
                </el-form-item>
                <el-form-item
                  label="我的余额："
                  prop="balance"
                >
                  <el-radio-group v-model="propertyFormInfo.balance">
                    <el-radio label="1">展示</el-radio>
                    <el-radio label="0">不展示</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item
                  label="我的积分："
                  prop="points"
                >
                  <el-radio-group v-model="propertyFormInfo.points">
                    <el-radio label="1">展示</el-radio>
                    <el-radio label="0">不展示</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item
                  label="优惠券："
                  prop="preferenceTicket"
                >
                  <el-radio-group v-model="propertyFormInfo.preferenceTicket">
                    <el-radio label="1">展示</el-radio>
                    <el-radio label="0">不展示</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item
                  label="优惠卡："
                  prop="preferenceCard"
                >
                  <el-radio-group v-model="propertyFormInfo.preferenceCard">
                    <el-radio label="1">展示</el-radio>
                    <el-radio label="0">不展示</el-radio>
                  </el-radio-group>
                </el-form-item>

              </el-form>
            </el-collapse-item>
            <el-collapse-item
              title="订单信息展示项配置"
              name="3"
            >
              <el-form
                :model="orderFormInfo"
                label-width="100px"
              >
                <el-form-item label="订单：">
                  <el-switch
                    v-model="orderFormInfo.switch"
                    active-color="#13ce66"
                    inactive-color="#ff4949"
                  ></el-switch>
                  <span>开关开启，则展示订单信息</span>
                </el-form-item>
                <el-form-item
                  label="标题："
                  prop="title"
                >
                  <el-input
                    style="width: 120px;"
                    v-model="orderFormInfo.moduleTitle"
                  ></el-input>
                  <span>订单模块标题，不填写则不显示</span>
                </el-form-item>
                <el-form-item
                  label="模块样式："
                  prop="style"
                >
                  <el-radio-group v-model="orderFormInfo.moduleStyle">
                    <el-radio label="1">样式1</el-radio>
                    <el-radio label="0">样式2</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item
                  label="待付款订单："
                  prop="payOrder"
                >
                  <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;position: relative">
                    <div class="imgContainter">
                      <el-image
                        fit="scale-down"
                        :src="imgHost+'/image/admin/add_img.png'"
                      />
                      <div
                        class="selectIcon"
                        @click="updateImgIcon"
                      >更换图标</div>
                    </div>
                    <div style="margin-left: 10px;">
                      <p style="color: #999;">建议尺寸：50px*50px</p>
                      <el-button type="text">重置图标</el-button>
                    </div>
                  </div>
                </el-form-item>
                <el-form-item
                  label="待发货订单："
                  prop="sendOrder"
                >
                  <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
                    <div class="imgContainter">
                      <el-image
                        fit="scale-down"
                        :src="imgHost+'/image/admin/add_img.png'"
                      />
                      <div
                        class="selectIcon"
                        @click="updateImgIcon"
                      >更换图标</div>
                    </div>
                    <div style="margin-left: 10px;">
                      <p style="color: #999;">建议尺寸：50px*50px</p>
                      <el-button type="text">重置图标</el-button>
                    </div>
                  </div>
                </el-form-item>
                <el-form-item
                  label="待收货订单："
                  prop="receiveOrder"
                >
                  <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
                    <div class="imgContainter">
                      <el-image
                        fit="scale-down"
                        :src="imgHost+'/image/admin/add_img.png'"
                      />
                      <div
                        class="selectIcon"
                        @click="updateImgIcon"
                      >更换图标</div>
                    </div>
                    <div style="margin-left: 10px;">
                      <p style="color: #999;">建议尺寸：50px*50px</p>
                      <el-button type="text">重置图标</el-button>
                    </div>
                  </div>
                </el-form-item>
                <el-form-item
                  label="评价订单："
                  prop="appraiseOrder"
                >
                  <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
                    <div class="imgContainter">
                      <el-image
                        fit="scale-down"
                        :src="imgHost+'/image/admin/add_img.png'"
                      />
                      <div
                        class="selectIcon"
                        @click="updateImgIcon"
                      >更换图标</div>
                    </div>
                    <div style="margin-left: 10px;">
                      <p style="color: #999;">建议尺寸：50px*50px</p>
                      <el-button type="text">重置图标</el-button>
                    </div>
                  </div>
                </el-form-item>
                <el-form-item
                  label="退款中订单："
                  prop="refundingOrder"
                >
                  <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
                    <div class="imgContainter">
                      <el-image
                        fit="scale-down"
                        :src="imgHost+'/image/admin/add_img.png'"
                      />
                      <div
                        class="selectIcon"
                        @click="updateImgIcon"
                      >更换图标</div>
                    </div>
                    <div style="margin-left: 10px;">
                      <p style="color: #999;">建议尺寸：50px*50px</p>
                      <el-button type="text">重置图标</el-button>
                    </div>
                  </div>
                </el-form-item>
              </el-form>

            </el-collapse-item>
            <el-collapse-item
              title="预约信息展示项配置"
              name="4"
            >
              <el-form
                :model="appointmentFormInfo"
                label-width="100px"
              >
                <el-form-item label="预约：">
                  <el-switch
                    v-model="appointmentFormInfo.switch"
                    active-color="#13ce66"
                    inactive-color="#ff4949"
                  ></el-switch>
                  <span>开关开启，则展示预约信息</span>
                </el-form-item>
                <el-form-item
                  label="标题："
                  prop="title"
                >
                  <el-input
                    style="width: 120px;"
                    v-model="appointmentFormInfo.appointmentTitle"
                  ></el-input>
                  <span>预约模块标题，不填写则不显示</span>
                </el-form-item>
              </el-form>

            </el-collapse-item>
            <el-collapse-item
              title="店铺使用相关数据展示项配置"
              name="5"
            >
              <el-form
                :model="shopUseFormInfo"
                label-width="100px"
              >
                <el-form-item label="店铺使用：">
                  <el-switch
                    v-model="shopUseFormInfo.switch"
                    active-color="#13ce66"
                    inactive-color="#ff4949"
                  ></el-switch>
                  <span>开关开启，则展示店铺使用信息</span>
                </el-form-item>
                <el-form-item
                  label="标题："
                  prop="title"
                >
                  <el-input
                    style="width: 120px;"
                    v-model="shopUseFormInfo.shopUseTitle"
                  ></el-input>
                  <span>店铺使用模块标题，不填写则不显示</span>
                </el-form-item>
                <el-form-item
                  label="我的收藏："
                  prop="collect"
                >
                  <el-radio-group v-model="shopUseFormInfo.collect">
                    <el-radio label="1">展示</el-radio>
                    <el-radio label="0">不展示</el-radio>
                  </el-radio-group>
                  <span>用户收藏的商品</span>
                </el-form-item>
                <el-form-item
                  label="历史购买："
                  prop="histroy"
                >
                  <el-radio-group v-model="shopUseFormInfo.histroy">
                    <el-radio label="1">展示</el-radio>
                    <el-radio label="0">不展示</el-radio>
                  </el-radio-group>
                  <span>用户收藏的商品</span>
                </el-form-item>
                <el-form-item
                  label="我的足迹："
                  prop="footprint"
                >
                  <el-radio-group v-model="shopUseFormInfo.footprint">
                    <el-radio label="1">展示</el-radio>
                    <el-radio label="0">不展示</el-radio>
                  </el-radio-group>
                  <span>用户收藏的商品</span>
                </el-form-item>

              </el-form>

            </el-collapse-item>
            <el-collapse-item
              title="服务展示项配置"
              name="6"
            >
              <el-form
                :model="serveShowFormInfo"
                label-width="100px"
              >
                <el-form-item label="服务：">
                  <el-switch
                    v-model="serveShowFormInfo.switch"
                    active-color="#13ce66"
                    inactive-color="#ff4949"
                  ></el-switch>
                  <span>开关开启，则展示店铺服务信息</span>
                </el-form-item>
                <el-form-item
                  label="标题："
                  prop="title"
                >
                  <el-input
                    style="width: 120px;"
                    v-model="serveShowFormInfo.serveShowTitle"
                  ></el-input>
                  <span>开关开启，则展示店铺服务信息</span>
                </el-form-item>
                <el-form-item
                  label="分销中心："
                  prop="retailCenter"
                >
                  <el-radio-group v-model="serveShowFormInfo.retailCenter">
                    <el-radio label="1">展示</el-radio>
                    <el-radio label="0">不展示</el-radio>
                  </el-radio-group>
                  <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
                    <div class="imgContainter">
                      <el-image
                        fit="scale-down"
                        :src="imgHost+'/image/admin/add_img.png'"
                      />
                      <div
                        class="selectIcon"
                        @click="updateImgIcon"
                      >更换图标</div>
                    </div>
                    <div style="margin-left: 10px;">
                      <p style="color: #999;">建议尺寸：50px*50px</p>
                      <el-button type="text">重置图标</el-button>
                    </div>
                  </div>
                </el-form-item>
                <el-form-item
                  label="我的砍价："
                  prop="myDiscount"
                >
                  <el-radio-group v-model="serveShowFormInfo.myDiscount">
                    <el-radio label="1">展示</el-radio>
                    <el-radio label="0">不展示</el-radio>
                  </el-radio-group>
                  <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
                    <div class="imgContainter">
                      <el-image
                        fit="scale-down"
                        :src="imgHost+'/image/admin/add_img.png'"
                      />
                      <div
                        class="selectIcon"
                        @click="updateImgIcon"
                      >更换图标</div>
                    </div>
                    <div style="margin-left: 10px;">
                      <p style="color: #999;">建议尺寸：50px*50px</p>
                      <el-button type="text">重置图标</el-button>
                    </div>
                  </div>
                </el-form-item>
                <el-form-item
                  label="我的奖品："
                  prop="myPrize"
                >
                  <el-radio-group v-model="serveShowFormInfo.myPrize">
                    <el-radio label="1">展示</el-radio>
                    <el-radio label="0">不展示</el-radio>
                  </el-radio-group>
                  <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
                    <div class="imgContainter">
                      <el-image
                        fit="scale-down"
                        :src="imgHost+'/image/admin/add_img.png'"
                      />
                      <div
                        class="selectIcon"
                        @click="updateImgIcon"
                      >更换图标</div>
                    </div>
                    <div style="margin-left: 10px;">
                      <p style="color: #999;">建议尺寸：50px*50px</p>
                      <el-button type="text">重置图标</el-button>
                    </div>
                  </div>
                </el-form-item>
                <el-form-item
                  label="我的评价："
                  prop="myAppraise"
                >
                  <el-radio-group v-model="serveShowFormInfo.myAppraise">
                    <el-radio label="1">展示</el-radio>
                    <el-radio label="0">不展示</el-radio>
                  </el-radio-group>
                  <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
                    <div
                      class="imgContainter"
                      @click="updateImgIcon"
                    >
                      <el-image
                        fit="scale-down"
                        :src="imgHost+'/image/admin/add_img.png'"
                      />
                      <div
                        class="selectIcon"
                        @click="updateImgIcon"
                      >更换图标</div>
                    </div>
                    <div style="margin-left: 10px;">
                      <p style="color: #999;">建议尺寸：50px*50px</p>
                      <el-button type="text">重置图标</el-button>
                    </div>
                  </div>
                </el-form-item>
                <el-form-item
                  label="门店列表："
                  prop="shopList"
                >
                  <el-radio-group v-model="serveShowFormInfo.shopList">
                    <el-radio label="1">展示</el-radio>
                    <el-radio label="0">不展示</el-radio>
                  </el-radio-group>
                  <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
                    <div class="imgContainter">
                      <el-image
                        fit="scale-down"
                        :src="imgHost+'/image/admin/add_img.png'"
                      />
                      <div
                        class="selectIcon"
                        @click="updateImgIcon"
                      >更换图标</div>
                    </div>
                    <div style="margin-left: 10px;">
                      <p style="color: #999;">建议尺寸：50px*50px</p>
                      <el-button type="text">重置图标</el-button>
                    </div>
                  </div>
                </el-form-item>
                <el-form-item
                  label="会员激活："
                  prop="activation"
                >
                  <el-radio-group v-model="serveShowFormInfo.activation">
                    <el-radio label="1">展示</el-radio>
                    <el-radio label="0">不展示</el-radio>
                  </el-radio-group>
                  <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
                    <div class="imgContainter">
                      <el-image
                        fit="scale-down"
                        :src="imgHost+'/image/admin/add_img.png'"
                      />
                      <div
                        class="selectIcon"
                        @click="updateImgIcon"
                      >更换图标</div>
                    </div>
                    <div style="margin-left: 10px;">
                      <p style="color: #999;">建议尺寸：50px*50px</p>
                      <el-button type="text">重置图标</el-button>
                    </div>
                  </div>
                </el-form-item>
                <el-form-item
                  label="扫码核销："
                  prop="cancellation"
                >
                  <el-radio-group v-model="serveShowFormInfo.cancellation">
                    <el-radio label="1">展示</el-radio>
                    <el-radio label="0">不展示</el-radio>
                  </el-radio-group>
                  <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
                    <div class="imgContainter">
                      <el-image
                        fit="scale-down"
                        :src="imgHost+'/image/admin/add_img.png'"
                      />
                      <div
                        class="selectIcon"
                        @click="updateImgIcon"
                      >更换图标</div>
                    </div>
                    <div style="margin-left: 10px;">
                      <p style="color: #999;">建议尺寸：50px*50px</p>
                      <el-button type="text">重置图标</el-button>
                    </div>
                  </div>
                </el-form-item>
                <el-form-item
                  label="礼物记录："
                  prop="giftRecord"
                >
                  <el-radio-group v-model="serveShowFormInfo.giftRecord">
                    <el-radio label="1">展示</el-radio>
                    <el-radio label="0">不展示</el-radio>
                  </el-radio-group>
                  <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
                    <div class="imgContainter">
                      <el-image
                        fit="scale-down"
                        :src="imgHost+'/image/admin/add_img.png'"
                      />
                      <div
                        class="selectIcon"
                        @click="updateImgIcon"
                      >更换图标</div>
                    </div>
                    <div style="margin-left: 10px;">
                      <p style="color: #999;">建议尺寸：50px*50px</p>
                      <el-button type="text">重置图标</el-button>
                    </div>
                  </div>
                </el-form-item>
                <!-- 自定义模板 -->
                <el-form-item
                  label="自定义模板："
                  prop="customTemplate"
                >
                  <el-radio-group v-model="templateFormInfo.customTemplate">
                    <el-radio label="1">展示</el-radio>
                    <el-radio label="0">不展示</el-radio>
                  </el-radio-group>
                  <el-button
                    type="text"
                    style="margin-left: 10px;"
                    @click="delTemplate"
                  >删除模板</el-button>
                  <div style="margin: 10px 0;">
                    标题：&nbsp;&nbsp;<el-input
                      placeholder="帮助中心"
                      style="width: 120px;"
                      v-model="templateFormInfo.templateTitle"
                    ></el-input>
                  </div>
                  <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
                    <div class="imgContainter">
                      <el-image
                        fit="scale-down"
                        :src="imgHost+'/image/admin/add_img.png'"
                      />
                      <div
                        class="selectIcon"
                        @click="updateImgIcon"
                      >更换图标</div>
                    </div>
                    <div style="margin-left: 10px;">
                      <p style="color: #999;">建议尺寸：50px*50px</p>
                    </div>
                    <div>跳转到的页面：<el-button
                        type="text"
                        @click="selectLink"
                      >选择链接</el-button>
                    </div>
                    <div
                      v-if="this.pageLink"
                      style="margin-left: 20px;"
                    >{{ this.pageLink }}</div>
                  </div>
                </el-form-item>
                <div style="text-align: center;">
                  <el-button
                    type="primary"
                    style="text-align: center;"
                    @click="addTemplate"
                  >添加自定义模板</el-button>
                </div>
              </el-form>

            </el-collapse-item>
          </el-collapse>
        </div>

      </div>
      <div class="footer">
        <el-button
          type="primary"
          size="small"
          @click="saveClickHandler"
        >保存</el-button>
      </div>
    </div>
    <!--图片dialog-->
    <ImageDalog
      :tuneUp="selfImgDialogShow"
      pageIndex='pictureSpace'
      @handleSelectImg='imgDialogSelectedCallback'
    />
    <!-- 选择链接弹窗 -->
    <selectLinks
      @selectLinkPath="getPath"
      :tuneUpSelectLink="tuneUpSelectLink"
    />
  </div>
</template>
<script>
// import { mapGetters } from 'vuex'
/* 组件导入 */
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
      //   左侧数据
      leftList: [{
        switch: true,
        listTitle: {
          titleLeft: '',
          titleRight: ''
        },
        listContent: {
          item_img: '',
          item_num: '',
          item_his: '',
          item_word: ''
        }
      }, {
        switch: true,
        listTitle: {
          titleLeft: '',
          titleRight: ''
        },
        listContent: {
          item_img: '',
          item_num: '',
          item_his: '',
          item_word: ''
        }
      }, {
        switch: true,
        listTitle: {
          titleLeft: '',
          titleRight: ''
        },
        listContent: {
          item_img: '',
          item_num: '',
          item_his: '',
          item_word: ''
        }
      }, {
        switch: true,
        listTitle: {
          titleLeft: '',
          titleRight: ''
        },
        listContent: {
          item_img: '',
          item_num: '',
          item_his: '',
          item_word: ''
        }
      }, {
        switch: true,
        listTitle: {
          titleLeft: '',
          titleRight: ''
        },
        listContent: {
          item_img: '',
          item_num: '',
          item_his: '',
          item_word: ''
        }
      }],
      customizeImg: [],
      //   资产
      propertyFormInfo: {
        switch: true,
        title: '我的资产',
        balance: '1',
        points: '1',
        preferenceTicket: '1',
        preferenceCard: '1'
      },
      //   订单
      orderFormInfo: {
        switch: true,
        moduleTitle: '我的订单',
        moduleStyle: '1',
        payOrder: '',
        sendOrder: '',
        receiveOrder: '',
        appraiseOrder: '',
        refundingOrder: ''
      },
      //   预约
      appointmentFormInfo: {
        switch: true,
        appointmentTitle: '我的预约'
      },
      //   店铺使用
      shopUseFormInfo: {
        switch: true,
        shopUseTitle: '使用记录',
        collect: '1',
        histroy: '1',
        footprint: '1'
      },
      //   服务展示
      serveShowFormInfo: {
        switch: true,
        serveShowTitle: '我的服务',
        retailCenter: '1',
        myDiscount: '1',
        myPrize: '1',
        shopList: '1',
        activation: '1',
        myAppraise: '1',
        cancellation: '1',
        giftRecord: '1'
      },
      //   自定义模板
      templateFormInfo: {
        customTemplate: '1',
        templateTitle: ''
      },
      selfImgDialogShow: false,
      tuneUpSelectLink: false,
      pageLink: '',
      imgHost: `${this.$imageHost}`
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
    this.langDefault()
  },
  methods: {
    // 保存
    saveClickHandler () {

    },
    changeHandler (val) {
      console.log(val)
    },
    // 左侧模块上移
    upClickHandler (index) {
      if (index === 0) {
        this.$message.warning('当前模块不能移动')
      } else {

      }
    },
    // 左侧模块下移
    downClickHandler (index) {
      if (index === 4) {
        this.$message.warning('当前模块不能移动')
      } else {

      }
    },
    // 自定义图片背景
    changeCustomizeImg () {
      this.selfImgDialogShow = !this.selfImgDialogShow
      this.$nextTick(() => this.$http.$emit('dtVisible'))
    },
    // 商品图片点击回调函数
    imgDialogSelectedCallback (imgObj) {
      this.customizeImg = []
      this.customizeImg.push({ imgPath: imgObj.imgPath, imgUrl: imgObj.imgUrl })
    },
    //  修改订单图标
    updateImgIcon () {
      this.selfImgDialogShow = !this.selfImgDialogShow
      this.$nextTick(() => this.$http.$emit('dtVisible'))
    },
    //  自定义模板跳转链接
    selectLink () {
      this.tuneUpSelectLink = !this.tuneUpSelectLink
    },
    // 获取选中的path
    getPath (res) {
      this.pageLink = res
    },
    // 删除自定义模板
    delTemplate () {

    },
    // 添加自定义模板
    addTemplate () { }
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
  border: 1px solid #f2e1c8;
  background: #fff7ec;
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
  background: rgba(0, 0, 0, 0.1);
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
  position: relative;
  width: 96% !important;
  margin: 0 auto !important;
  margin-bottom: 10px !important;
  border: 2px solid #fff;

  padding: 10px 0;
  margin-bottom: 10px;
  background: #fff;
  padding-left: 10px;
}

.widthActive {
  width: 100% !important;
}

.indoor_area_raidus:hover {
  border: 2px dashed #5a8bff;
  /* position: relative; */
}

.indoor_area_raidus:hover .orderOperation {
  display: block;
}

.orderTitle {
  min-height: 20px;
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
  position: absolute;
  top: 7px;
  right: 10px;
  font-weight: normal;
  color: #999;
  font-size: 12px;
  padding-bottom: 10px;
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
  padding: 15px 0;
  padding-bottom: 0;
  overflow: hidden;
}

.each_item {
  flex: 1;
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
}

.each_item_special {
  flex: 1;
  display: flex;
  position: relative;
}

.each_item_special > img {
  height: 40px;
  width: 10px;
  position: absolute;
  top: 0;
  left: -7px;
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

.each_item .item_num span {
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
  width: 98px;
  margin-bottom: 20px;
  float: left;
  flex: 30%;

  justify-content: center;
  flex-direction: column;
  align-items: center;
  /* display: flex; */
}

.serve_img {
  margin-bottom: 10px;
}

.serve_img img {
  width: 25px;
  height: 25px;
}

.serve_word {
  font-size: 12px;
  color: #666;
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

.app_info p:last-child {
  font-size: 12px;
  color: #999;
}

.each_item .item_word {
  font-size: 12px;
  color: #666;
}

.clearfix {
  zoom: 1;
}

.orderOperation {
  position: absolute;
  right: 8px;
  top: 2px;
  display: none;
  z-index: 999;
}

.orderOperation img {
  padding-right: 5px;
  cursor: pointer;
}

.cententRight {
  width: 480px;
  float: left;
  margin: 20px 0 0 15px;
  /* border: 1px solid #e5e5e5; */
  background: #f8f8f8;
  padding: 15px 12px 22px;
  border-radius: 3px;
}

.el-collapse {
  border: none;
}

.el-collapse-item {
  margin-bottom: 20px;
}

.cententRight_title {
  margin-bottom: 20px;
}

.el-collapse-item__header {
  padding-left: 15px !important;
  font-size: 18px !important;
}

.el-collapse-item__wrap {
  border-bottom: none !important;
}

/* .el-collapse-item .el-collapse-item__header {
  font-size: 14px;
  background-color: #F8F8F8 !important;;
  border: 1px solid #ccc;
  border-bottom: none;
}

.el-collapse-item__content {
    border: 1px solid #ccc;
    margin-bottom: 20px;
}

.el-collapse-item__wrap {
    border-bottom: none !important;
} */

.cententRight_container span {
  color: #999;
  font-size: 12px;
  margin-left: 15px;
}

.customizeImgWrap {
  width: 175px;
  height: 70px;
  border: 1px solid #ccc;
  box-sizing: border-box;
  margin-top: 10px;
  margin-left: 10px;
  cursor: pointer;
  background: url(this.imgHost+"/image/admin/add_img_bg.png") no-repeat;
}

.left_info_headBg {
  width: 100%;
  height: 148px;
}

.imgContainter {
  width: 78px;
  height: 78px;
  position: relative;
  cursor: pointer;
}

.imgContainter .el-image {
  width: 100%;
  height: 100%;
  border: 1px solid #ccc;
}

.imgContainter .selectIcon {
  width: 100%;
  line-height: 2;
  position: absolute;
  bottom: 0;
  left: 0;
  background: rgba(0, 0, 0, 0.5);
  color: #fff;
  font-size: 12px;
  text-align: center;
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
