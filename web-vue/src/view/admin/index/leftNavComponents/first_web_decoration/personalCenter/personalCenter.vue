<template>
  <div class="personalCenterContent">
    <div class="personalCenterContent_main">
      <div class="prompt">
        <img
          :src="imgHost + '/image/admin/notice_img.png'"
          alt=""
        >
        <span>{{ $t('personalCenter.warningTip') }}</span>
      </div>
      <div class="bottomContent">

        <div class="cententLeft">
          <!-- 左侧头部 -->
          <div class="left_info_head">
            <div
              class="left_info_headBg"
              v-if="leftData[1].bg_type=='0'"
              style="background: -webkit-linear-gradient(left,rgb(80, 160, 160),rgb(64, 128, 128));"
            ></div>
            <div
              class="left_info_headBg"
              v-if="leftData[1].bg_type!='0'"
              :style="{'backgroundImage': bgImg=='' ? '#eee' : 'url(' + bgImage + ')', 'backgroundSize': 'cover'}"
            ></div>
            <img
              class="center_set"
              :src="imgHost + '/image/admin/center_set.png'"
              alt=""
            >
            <img
              class="center_head"
              :src="imgHost + '/image/admin/user_touxiang.png'"
              alt=""
            >
            <div class="center_name">{{ $t('personalCenter.userNickname') }}</div>
            <div class="center_sign">
              <img
                :src="imgHost + '/image/admin/sign_icon.png'"
                alt=""
              >
              <span>{{ $t('personalCenter.integralTip') }}</span>
            </div>
          </div>
          <!-- 左侧结构 -->
          <div
            v-for="(item, index) in leftData"
            :key="index"
          >
            <div
              class="indoor_area_raidus"
              :class="styleChoose=='1'? 'widthActive' : ''"
              v-if="item.module_name=='account_money' && item.is_show=='1'"
            >
              <div class="orderTitle">
                <div class="titleLeft">{{ item.title }}</div>
              </div>
              <div class="orderContent">
                <div
                  class="each_item"
                  v-for="(val, key) in item.content"
                  :key="key"
                  v-if="val.icon_name=='account' && val.is_show=='1'"
                >
                  <div class="item_num">
                    <span>1000.99</span>
                  </div>
                  <div class="item_word">{{ $t('personalCenter.account') }}</div>
                </div>
                <div
                  class="each_item"
                  v-for="(val, key) in item.content"
                  :key="key"
                  v-if="val.icon_name=='score' && val.is_show=='1'"
                >
                  <div class="item_num">
                    <span>1000</span>
                  </div>
                  <div class="item_word">{{ $t('personalCenter.score') }}</div>
                </div>
                <div
                  class="each_item"
                  v-for="(val, key) in item.content"
                  :key="key"
                  v-if="val.icon_name=='coupon' && val.is_show=='1'"
                >
                  <div class="item_num">
                    <span>122</span>{{ $t('personalCenter.sheets') }}
                  </div>
                  <div class="item_word">{{ $t('personalCenter.coupon') }}</div>
                </div>
                <div
                  class="each_item"
                  v-for="(val, key) in item.content"
                  :key="key"
                  v-if="val.icon_name=='card' && val.is_show=='1'"
                >
                  <div class="item_num">
                    <span>90</span>{{ $t('personalCenter.sheets') }}
                  </div>
                  <div class="item_word">{{ $t('personalCenter.card') }}</div>
                </div>
              </div>
              <div class="orderOperation">
                <img
                  class="up_img"
                  :src="imgHost + '/image/admin/add_up_use.png'"
                  alt=""
                  @click="upClickHandler(leftData, index)"
                >
                <img
                  class="down_img"
                  :src="imgHost + '/image/admin/add_down.png'"
                  alt=""
                  @click="downClickHandler(leftData, index)"
                >
              </div>
            </div>
            <div
              class="indoor_area_raidus"
              :class="styleChoose=='1'? 'widthActive' : ''"
              v-if="item.module_name=='order' && item.is_show=='1'"
            >
              <div class="orderTitle">
                <div class="titleLeft">{{ item.title }}</div>
                <div
                  class="titleRight"
                  v-if="isShowOrder=='1'"
                >
                  <span>{{ $t('personalCenter.viewAllOrder') }} </span>
                  <img
                    :src="imgHost + '/image/admin/right_into.png'"
                    alt=""
                  >
                </div>
              </div>
              <div class="orderContent">
                <div
                  class="each_item"
                  v-for="(val, key) in item.content"
                  :key="key"
                  v-if="val.icon_name=='wait_pay'"
                >
                  <div class="item_img">
                    <img
                      :src="imgHost + val.icon"
                      alt=""
                    >
                  </div>
                  <div class="item_word">{{ $t('personalCenter.wait1') }}</div>
                </div>
                <div
                  class="each_item"
                  v-for="(val, key) in item.content"
                  :key="key"
                  v-if="val.icon_name=='wait_deliver'"
                >
                  <div class="item_img">
                    <img
                      :src="imgHost + val.icon"
                      alt=""
                    >
                  </div>
                  <div class="item_word">{{ $t('personalCenter.wait2') }}</div>
                </div>
                <div
                  class="each_item"
                  v-for="(val, key) in item.content"
                  :key="key"
                  v-if="val.icon_name=='wait_receive'"
                >
                  <div class="item_img">
                    <img
                      :src="imgHost + val.icon"
                      alt=""
                    >
                  </div>
                  <div class="item_word">{{ $t('personalCenter.wait3') }}</div>
                </div>
                <div
                  class="each_item"
                  v-for="(val, key) in item.content"
                  :key="key"
                  v-if="val.icon_name=='wait_comment' && isShowOrder=='1'"
                >
                  <div class="item_img">
                    <img
                      :src="imgHost + val.icon"
                      alt=""
                    >
                  </div>
                  <div class="item_word">{{ $t('personalCenter.wait4') }}</div>
                </div>
                <div
                  class="each_item"
                  v-for="(val, key) in item.content"
                  :key="key"
                  v-if="val.icon_name=='refund'"
                >
                  <div class="item_img">
                    <img
                      :src="imgHost + val.icon"
                      alt=""
                    >
                  </div>
                  <div class="item_word">{{ $t('personalCenter.wait5') }}</div>
                </div>
                <div
                  class="each_item_special"
                  v-if="isShowOrder!='1'"
                >
                  <img
                    :src="imgHost + '/image/admin/icon_jiantou.png'"
                    alt=""
                  >

                  <div
                    class="each_item"
                    v-if="isShowOrder!='1'"
                  >
                    <div class="item_img">
                      <img
                        :src="imgHost + '/image/admin/uc_order_icon6.png'"
                        alt=""
                      >
                    </div>
                    <div class="item_word">{{ $t('personalCenter.allOrder') }}</div>
                  </div>

                </div>
              </div>
              <div class="orderOperation">
                <img
                  class="up_img"
                  :src="imgHost + '/image/admin/add_up_use.png'"
                  alt=""
                  @click="upClickHandler(leftData, index)"
                >
                <img
                  class="down_img"
                  :src="imgHost + '/image/admin/add_down.png'"
                  alt=""
                  @click="downClickHandler(leftData, index)"
                >
              </div>
            </div>
            <div
              class="indoor_area_raidus"
              :class="styleChoose=='1'? 'widthActive' : ''"
              v-if="item.module_name=='use_record' && item.is_show=='1'"
            >
              <div class="orderTitle">
                <div class="titleLeft">{{ item.title }}</div>
              </div>
              <div class="orderContent">
                <div
                  class="each_item"
                  v-if="item.is_show_collect=='1'"
                >
                  <div class="item_his">100</div>
                  <div class="item_word">{{ $t('personalCenter.collect') }}</div>
                </div>
                <div
                  class="each_item"
                  v-if="item.is_show_buy_history=='1'"
                >
                  <div class="item_his">100</div>
                  <div class="item_word">{{ $t('personalCenter.buyHistory') }}</div>
                </div>
                <div
                  class="each_item"
                  v-if="item.is_show_footprint=='1'"
                >
                  <div class="item_his">100</div>
                  <div class="item_word">{{ $t('personalCenter.footprint') }}</div>
                </div>
              </div>
              <div class="orderOperation">
                <img
                  class="up_img"
                  :src="imgHost + '/image/admin/add_up_use.png'"
                  alt=""
                  @click="upClickHandler(leftData, index)"
                >
                <img
                  class="down_img"
                  :src="imgHost + '/image/admin/add_down.png'"
                  alt=""
                  @click="downClickHandler(leftData, index)"
                >
              </div>
            </div>
            <div
              class="indoor_area_raidus"
              :class="styleChoose=='1'? 'widthActive' : ''"
              v-if="item.module_name=='appointment' && item.is_show=='1'"
            >
              <div class="orderTitle">
                <div class="titleLeft">{{ item.title }}</div>
                <div class="titleRight">
                  <span>{{ $t('personalCenter.viewAllAppointments') }} </span>
                  <img
                    :src="imgHost + '/image/admin/right_into.png'"
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
                    :src="imgHost + '/image/admin/img1.jpg'"
                    alt=""
                  >
                </div>
                <div class="app_info">
                  <p>{{ $t('personalCenter.tip1') }}</p>
                  <p>{{ $t('personalCenter.tip2') }}</p>
                </div>
              </div>
              <div class="orderOperation">
                <img
                  class="up_img"
                  :src="imgHost + '/image/admin/add_up_use.png'"
                  alt=""
                  @click="upClickHandler(leftData, index)"
                >
                <img
                  class="down_img"
                  :src="imgHost + '/image/admin/add_down.png'"
                  alt=""
                  @click="downClickHandler(leftData, index)"
                >
              </div>
            </div>
            <div
              class="indoor_area_raidus"
              :class="styleChoose=='1'? 'widthActive' : ''"
              v-if="item.module_name=='service' && item.is_show=='1'"
            >
              <div class="orderTitle">
                <div class="titleLeft">{{ item.title }}</div>
              </div>
              <div class="serveContent">
                <div
                  class="each_serve"
                  style="display: flex;"
                  v-for="(val, key) in item.content"
                  :key="key"
                  v-if="val.icon_name==='distribution' && val.is_show=='1'"
                >
                  <div class="serve_img">
                    <img
                      :src="imgHost + val.icon"
                      alt=""
                    >
                  </div>
                  <div class="serve_word">{{ $t('personalCenter.distribution') }}</div>
                </div>
                <div
                  class="each_serve"
                  style="display: flex;"
                  v-for="(val, key) in item.content"
                  :key="key"
                  v-if="val.icon_name==='bargain' && val.is_show=='1'"
                >
                  <div class="serve_img">
                    <img
                      :src="imgHost + val.icon"
                      alt=""
                    >
                  </div>
                  <div class="serve_word">{{ $t('personalCenter.bargain') }}</div>
                </div>
                <div
                  class="each_serve"
                  style="display: flex;"
                  v-for="(val, key) in item.content"
                  :key="key"
                  v-if="val.icon_name==='award' && val.is_show=='1'"
                >
                  <div class="serve_img">
                    <img
                      :src="imgHost + val.icon"
                      alt=""
                    >
                  </div>
                  <div class="serve_word">{{ $t('personalCenter.award') }}</div>
                </div>
                <div
                  class="each_serve"
                  style="display: flex;"
                  v-for="(val, key) in item.content"
                  :key="key"
                  v-if="val.icon_name==='comment_list' && val.is_show=='1'"
                >
                  <div class="serve_img">
                    <img
                      :src="imgHost + val.icon"
                      alt=""
                    >
                  </div>
                  <div class="serve_word">{{ $t('personalCenter.commentList') }}</div>
                </div>
                <div
                  class="each_serve"
                  style="display: flex;"
                  v-for="(val, key) in item.content"
                  :key="key"
                  v-if="val.icon_name==='store_list' && val.is_show=='1'"
                >
                  <div class="serve_img">
                    <img
                      :src="imgHost + val.icon"
                      alt=""
                    >
                  </div>
                  <div class="serve_word">{{ $t('personalCenter.storeList') }}</div>
                </div>
                <div
                  class="each_serve"
                  style="display: flex;"
                  v-for="(val, key) in item.content"
                  :key="key"
                  v-if="val.icon_name==='user_activate' && val.is_show=='1'"
                >
                  <div class="serve_img">
                    <img
                      :src="imgHost + val.icon"
                      alt=""
                    >
                  </div>
                  <div class="serve_word">{{ $t('personalCenter.userActivate') }}</div>
                </div>
                <div
                  class="each_serve"
                  style="display: flex;"
                  v-for="(val, key) in item.content"
                  :key="key"
                  v-if="val.icon_name==='order_verify' && val.is_show=='1'"
                >
                  <div class="serve_img">
                    <img
                      :src="imgHost + val.icon"
                      alt=""
                    >
                  </div>
                  <div class="serve_word">{{ $t('personalCenter.orderVerify') }}</div>
                </div>
                <div
                  class="each_serve"
                  style="display: flex;"
                  v-for="(val, key) in item.content"
                  :key="key"
                  v-if="val.icon_name==='present_list' && val.is_show=='1'"
                >
                  <div class="serve_img">
                    <img
                      :src="imgHost + val.icon"
                      alt=""
                    >
                  </div>
                  <div class="serve_word">{{ $t('personalCenter.presentList') }}</div>
                </div>
                <div
                  class="each_serve"
                  style="display: flex;"
                  v-for="(val, key) in item.content"
                  :key="key"
                  v-if="key>7 && val.is_show=='1'"
                >
                  <div class="serve_img">
                    <img
                      :src="imgHost + val.icon"
                      alt=""
                    >
                  </div>
                  <div class="serve_word">{{ val.title }}</div>
                </div>

              </div>
              <div class="orderOperation">
                <img
                  class="up_img"
                  :src="imgHost + '/image/admin/add_up_use.png'"
                  alt=""
                  @click="upClickHandler(leftData, index)"
                >
                <img
                  class="down_img"
                  :src="imgHost + '/image/admin/add_down.png'"
                  alt=""
                  @click="downClickHandler(leftData, index)"
                >
              </div>
            </div>
          </div>
        </div>

        <div class="cententRight">
          <!-- 右侧头部 -->
          <div class="cententRight_title">
            {{ $t('personalCenter.layoutStyle') }}：
            <el-radio-group
              v-model="rightData[0].page_style"
              @change="changepageStyle"
            >
              <el-radio label="1">{{ $t('personalCenter.flatStyle') }}</el-radio>
              <el-radio label="2">{{ $t('personalCenter.cardStyle') }}</el-radio>
            </el-radio-group>
          </div>
          <!-- 右侧结构 -->
          <div class="cententRight_container">
            <el-collapse v-model="activeNames">
              <el-collapse-item
                :title="$t('personalCenter.userInfoColor')"
                name="1"
                style="margin-bottom: 20px;"
                v-for="(item, index) in rightData"
                :key="index"
                v-if="index===1"
              >
                <el-radio-group v-model="item.bg_type">
                  <el-radio
                    label="0"
                    style="display: block;margin-bottom: 20px;"
                  >{{ $t('personalCenter.storeColor') }}</el-radio>
                  <el-radio label="1">{{ $t('personalCenter.customPicture') }}</el-radio>
                  <div
                    class="customizeImgWrap"
                    v-if="bgImg!=''"
                    @click="changeImgHandler(item.module_name, '')"
                  >
                    <el-image
                      fit="cover"
                      :src="imgHost + bgImg"
                      style="width: 100%; height: 100%"
                    ></el-image>
                  </div>
                  <div
                    class="customizeImgWrap"
                    v-if="bgImg==''"
                    @click="changeImgHandler(item.module_name, '')"
                  >

                    <el-image
                      fit="scale-down"
                      :src="imgHost + '/image/admin/add_img_bg.png'"
                      style="width: 100%; height: 100%"
                    />
                  </div>
                </el-radio-group>
              </el-collapse-item>
              <el-collapse-item
                :title="$t('personalCenter.propertyInfo')"
                name="2"
                v-for="(item, index) in rightData"
                :key="index"
                v-if="index===2"
              >
                <el-form label-width="120px">
                  <el-form-item :label="$t('personalCenter.assetsLabel')">
                    <el-switch
                      v-model="item.is_show"
                      active-value="1"
                      inactive-value="0"
                      @change="changeSwitch(item.module_name, item.is_show)"
                      active-color="#f7931e"
                    ></el-switch>
                    <span>{{ $t('personalCenter.assetsLabelTip') }}</span>
                  </el-form-item>
                  <el-form-item :label="$t('personalCenter.title')">
                    <el-input
                      :placeholder="$t('personalCenter.inputTip')"
                      maxlength="10"
                      show-word-limit
                      style="width: 170px;"
                      size="small"
                      @blur="changeTitle(item.module_name, item.title)"
                      v-model="item.title"
                    ></el-input>
                    <p>{{ $t('personalCenter.assetsTitle') }}</p>
                  </el-form-item>
                  <el-form-item :label="$t('personalCenter.account') + '：'">
                    <el-radio-group
                      v-for="(val,key) in item.content"
                      :key="key"
                      v-if="val.icon_name=='account'"
                      v-model="val.is_show"
                      @change="changeAccountItem(item.module_name, val.icon_name, val.is_show)"
                    >
                      <el-radio label="1">{{ $t('personalCenter.show') }}</el-radio>
                      <el-radio label="0">{{ $t('personalCenter.noShow') }}</el-radio>
                    </el-radio-group>
                  </el-form-item>
                  <el-form-item :label="$t('personalCenter.score') + '：'">
                    <el-radio-group
                      v-for="(val,key) in item.content"
                      :key="key"
                      v-if="val.icon_name=='score'"
                      v-model="val.is_show"
                      @change="changeAccountItem(item.module_name, val.icon_name, val.is_show)"
                    >
                      <el-radio label="1">{{ $t('personalCenter.show') }}</el-radio>
                      <el-radio label="0">{{ $t('personalCenter.noShow') }}</el-radio>
                    </el-radio-group>
                  </el-form-item>
                  <el-form-item :label="$t('personalCenter.coupon') + '：'">
                    <el-radio-group
                      v-for="(val,key) in item.content"
                      :key="key"
                      v-if="val.icon_name=='coupon'"
                      v-model="val.is_show"
                      @change="changeAccountItem(item.module_name, val.icon_name, val.is_show)"
                    >
                      <el-radio label="1">{{ $t('personalCenter.show') }}</el-radio>
                      <el-radio label="0">{{ $t('personalCenter.noShow') }}</el-radio>
                    </el-radio-group>
                  </el-form-item>
                  <el-form-item :label="$t('personalCenter.card') + '：'">
                    <el-radio-group
                      v-for="(val,key) in item.content"
                      :key="key"
                      v-if="val.icon_name=='card'"
                      v-model="val.is_show"
                      @change="changeAccountItem(item.module_name, val.icon_name, val.is_show)"
                    >
                      <el-radio label="1">{{ $t('personalCenter.show') }}</el-radio>
                      <el-radio label="0">{{ $t('personalCenter.noShow') }}</el-radio>
                    </el-radio-group>
                  </el-form-item>
                </el-form>
              </el-collapse-item>
              <el-collapse-item
                :title="$t('personalCenter.orderInfo')"
                name="3"
                v-for="(item, index) in rightData"
                :key="index"
                v-if="index===3"
              >
                <el-form label-width="120px">
                  <el-form-item :label="$t('personalCenter.orderLabel')">
                    <el-switch
                      v-model="item.is_show"
                      active-value="1"
                      inactive-value="0"
                      @change="changeSwitch(item.module_name, item.is_show)"
                      active-color="#f7931e"
                    ></el-switch>
                    <span>{{ $t('personalCenter.orderLabelTip') }}</span>
                  </el-form-item>
                  <el-form-item :label="$t('personalCenter.title')">
                    <el-input
                      :placeholder="$t('personalCenter.inputTip')"
                      maxlength="10"
                      show-word-limit
                      style="width: 170px;"
                      size="small"
                      @blur="changeTitle(item.module_name, item.title)"
                      v-model="item.title"
                    ></el-input>
                    <p>{{ $t('personalCenter.orderTitle') }}</p>
                  </el-form-item>
                  <el-form-item :label="$t('personalCenter.orderStyle')">
                    <el-radio-group
                      v-model="item.module_style"
                      @change="changeModuleStyle"
                    >
                      <el-radio label="1">{{ $t('personalCenter.styleRadio1') }}</el-radio>
                      <el-radio label="2">{{ $t('personalCenter.styleRadio2') }}</el-radio>
                    </el-radio-group>
                  </el-form-item>
                  <el-form-item :label="$t('personalCenter.waitPay')">
                    <div
                      style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;position: relative"
                      v-for="(val, key) in item.content"
                      :key="key"
                      v-if="key==0"
                    >
                      <div
                        class="imgContainter"
                        @click="changeImgHandler(item.module_name, val.icon_name)"
                      >
                        <el-image
                          fit="scale-down"
                          :src="imgHost + val.icon"
                        />
                        <div class="selectIcon">{{ $t('personalCenter.changeIcon') }}</div>
                      </div>
                      <div style="margin-left: 10px;">
                        <p style="color: #999;">{{ $t('personalCenter.iconTip') }}</p>
                        <el-button
                          type="text"
                          @click="resetIconHandler(item.module_name, val.icon_name, '/image/admin/uc_config/uc_order_icon1.png')"
                        >{{ $t('personalCenter.resetIcon') }}</el-button>
                      </div>
                    </div>
                  </el-form-item>
                  <el-form-item :label="$t('personalCenter.waitDeliver')">
                    <div
                      style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;position: relative"
                      v-for="(val, key) in item.content"
                      :key="key"
                      v-if="key==1"
                    >
                      <div
                        class="imgContainter"
                        @click="changeImgHandler(item.module_name, val.icon_name)"
                      >
                        <el-image
                          fit="scale-down"
                          :src="imgHost + val.icon"
                        />
                        <div class="selectIcon">{{ $t('personalCenter.changeIcon') }}</div>
                      </div>
                      <div style="margin-left: 10px;">
                        <p style="color: #999;">{{ $t('personalCenter.iconTip') }}</p>
                        <el-button
                          type="text"
                          @click="resetIconHandler(item.module_name, val.icon_name, '/image/admin/uc_config/uc_order_icon2.png')"
                        >{{ $t('personalCenter.resetIcon') }}</el-button>
                      </div>
                    </div>
                  </el-form-item>
                  <el-form-item :label="$t('personalCenter.waitReceive')">
                    <div
                      style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;position: relative"
                      v-for="(val, key) in item.content"
                      :key="key"
                      v-if="key==2"
                    >
                      <div
                        class="imgContainter"
                        @click="changeImgHandler(item.module_name, val.icon_name)"
                      >
                        <el-image
                          fit="scale-down"
                          :src="imgHost + val.icon"
                        />
                        <div class="selectIcon">{{ $t('personalCenter.changeIcon') }}</div>
                      </div>
                      <div style="margin-left: 10px;">
                        <p style="color: #999;">{{ $t('personalCenter.iconTip') }}</p>
                        <el-button
                          type="text"
                          @click="resetIconHandler(item.module_name, val.icon_name, '/image/admin/uc_config/uc_order_icon3.png')"
                        >{{ $t('personalCenter.resetIcon') }}</el-button>
                      </div>
                    </div>
                  </el-form-item>
                  <el-form-item :label="$t('personalCenter.waitComment')">
                    <div
                      style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;position: relative"
                      v-for="(val, key) in item.content"
                      :key="key"
                      v-if="key==3"
                    >
                      <div
                        class="imgContainter"
                        @click="changeImgHandler(item.module_name, val.icon_name)"
                      >
                        <el-image
                          fit="scale-down"
                          :src="imgHost + val.icon"
                        />
                        <div class="selectIcon">{{ $t('personalCenter.changeIcon') }}</div>
                      </div>
                      <div style="margin-left: 10px;">
                        <p style="color: #999;">{{ $t('personalCenter.iconTip') }}</p>
                        <el-button
                          type="text"
                          @click="resetIconHandler(item.module_name, val.icon_name, '/image/admin/uc_config/uc_order_icon4.png')"
                        >{{ $t('personalCenter.resetIcon') }}</el-button>
                      </div>
                    </div>
                  </el-form-item>
                  <el-form-item :label="$t('personalCenter.refund')">
                    <div
                      style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;position: relative"
                      v-for="(val, key) in item.content"
                      :key="key"
                      v-if="key==4"
                    >
                      <div
                        class="imgContainter"
                        @click="changeImgHandler(item.module_name, val.icon_name)"
                      >
                        <el-image
                          fit="scale-down"
                          :src="imgHost + val.icon"
                        />
                        <div class="selectIcon">{{ $t('personalCenter.changeIcon') }}</div>
                      </div>
                      <div style="margin-left: 10px;">
                        <p style="color: #999;">{{ $t('personalCenter.iconTip') }}</p>
                        <el-button
                          type="text"
                          @click="resetIconHandler(item.module_name, val.icon_name, '/image/admin/uc_config/uc_order_icon5.png')"
                        >{{ $t('personalCenter.resetIcon') }}</el-button>
                      </div>
                    </div>
                  </el-form-item>

                </el-form>

              </el-collapse-item>
              <el-collapse-item
                :title="$t('personalCenter.appointmentInfo')"
                name="4"
                v-for="(item, index) in rightData"
                :key="index"
                v-if="index===4"
              >
                <el-form label-width="120px">
                  <el-form-item :label="$t('personalCenter.appointmentLabel')">
                    <el-switch
                      v-model="item.is_show"
                      active-value="1"
                      inactive-value="0"
                      @change="changeSwitch(item.module_name, item.is_show)"
                      active-color="#f7931e"
                    ></el-switch>
                    <span>{{ $t('personalCenter.appointmentLabelTip') }}</span>
                  </el-form-item>
                  <el-form-item :label="$t('personalCenter.title')">
                    <el-input
                      :placeholder="$t('personalCenter.inputTip')"
                      maxlength="10"
                      show-word-limit
                      style="width: 170px;"
                      size="small"
                      @blur="changeTitle(item.module_name, item.title)"
                      v-model="item.title"
                    ></el-input>
                    <p>{{ $t('personalCenter.appointmentTitle') }}</p>
                  </el-form-item>
                </el-form>

              </el-collapse-item>
              <el-collapse-item
                :title="$t('personalCenter.shopUseInfo')"
                name="5"
                v-for="(item, index) in rightData"
                :key="index"
                v-if="index===5"
              >
                <el-form label-width="120px">
                  <el-form-item :label="$t('personalCenter.shopLabel')">
                    <el-switch
                      v-model="item.is_show"
                      active-value="1"
                      inactive-value="0"
                      @change="changeSwitch(item.module_name, item.is_show)"
                      active-color="#f7931e"
                    ></el-switch>
                    <span>{{ $t('personalCenter.shopLabelTip') }}</span>
                  </el-form-item>
                  <el-form-item :label="$t('personalCenter.title')">
                    <el-input
                      :placeholder="$t('personalCenter.inputTip')"
                      maxlength="10"
                      show-word-limit
                      style="width: 170px;"
                      size="small"
                      @blur="changeTitle(item.module_name, item.title)"
                      v-model="item.title"
                    ></el-input>
                    <p>{{ $t('personalCenter.shopTitle') }}</p>
                  </el-form-item>
                  <el-form-item :label="$t('personalCenter.collect') + '：'">
                    <el-radio-group
                      v-model="item.is_show_collect"
                      @change="changeItem(item.module_name, 'is_show_collect', item.is_show_collect)"
                    >
                      <el-radio label="1">{{ $t('personalCenter.show') }}</el-radio>
                      <el-radio label="0">{{ $t('personalCenter.noShow') }}</el-radio>
                    </el-radio-group>
                    <span>{{ $t('personalCenter.collectTip') }}</span>
                  </el-form-item>
                  <el-form-item :label="$t('personalCenter.buyHistory') + '：'">
                    <el-radio-group
                      v-model="item.is_show_buy_history"
                      @change="changeItem(item.module_name, 'is_show_buy_history', item.is_show_buy_history)"
                    >
                      <el-radio label="1">{{ $t('personalCenter.show') }}</el-radio>
                      <el-radio label="0">{{ $t('personalCenter.noShow') }}</el-radio>
                    </el-radio-group>
                    <span>{{ $t('personalCenter.buyHistoryTip') }}</span>
                  </el-form-item>
                  <el-form-item :label="$t('personalCenter.footprint') + '：'">
                    <el-radio-group
                      v-model="item.is_show_footprint"
                      @change="changeItem(item.module_name, 'is_show_footprint', item.is_show_footprint)"
                    >
                      <el-radio label="1">{{ $t('personalCenter.show') }}</el-radio>
                      <el-radio label="0">{{ $t('personalCenter.noShow') }}</el-radio>
                    </el-radio-group>
                    <span>{{ $t('personalCenter.footprintTip') }}</span>
                  </el-form-item>
                </el-form>

              </el-collapse-item>
              <el-collapse-item
                :title="$t('personalCenter.serveShowInfo')"
                name="6"
                v-for="(item, index) in rightData"
                :key="index"
                v-if="index===6"
              >
                <el-form label-width="100px">
                  <el-form-item :label="$t('personalCenter.serveLabel')">
                    <el-switch
                      v-model="item.is_show"
                      active-value="1"
                      inactive-value="0"
                      @change="changeSwitch(item.module_name, item.is_show)"
                      active-color="#f7931e"
                    ></el-switch>
                    <span>{{ $t('personalCenter.serveLabelTip') }}</span>
                  </el-form-item>
                  <el-form-item :label="$t('personalCenter.title')">
                    <el-input
                      :placeholder="$t('personalCenter.inputTip')"
                      maxlength="10"
                      show-word-limit
                      style="width: 170px;"
                      size="small"
                      @blur="changeTitle(item.module_name, item.title)"
                      v-model="item.title"
                    ></el-input>
                    <p>{{ $t('personalCenter.serveTitle') }}</p>
                  </el-form-item>
                  <el-form-item
                    :label="$t('personalCenter.distribution') + '：'"
                    v-for="(val, key) in item.content"
                    :key="key"
                    v-if="key==0"
                  >
                    <el-radio-group
                      v-model="val.is_show"
                      @change="changeAccountItem(item.module_name, val.icon_name, val.is_show)"
                    >
                      <el-radio label="1">{{ $t('personalCenter.show') }}</el-radio>
                      <el-radio label="0">{{ $t('personalCenter.noShow') }}</el-radio>
                    </el-radio-group>
                    <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
                      <div
                        class="imgContainter"
                        @click="changeImgHandler(item.module_name, val.icon_name)"
                      >
                        <el-image
                          fit="scale-down"
                          :src="imgHost + val.icon"
                        />
                        <div class="selectIcon">{{ $t('personalCenter.changeIcon') }}</div>
                      </div>
                      <div style="margin-left: 10px;">
                        <p style="color: #999;">{{ $t('personalCenter.iconTip') }}</p>
                        <el-button
                          type="text"
                          @click="resetIconHandler(item.module_name, val.icon_name, '/image/admin/uc_config/icon_dis.png')"
                        >{{ $t('personalCenter.resetIcon') }}</el-button>
                      </div>
                    </div>
                  </el-form-item>
                  <el-form-item
                    :label="$t('personalCenter.bargain') + '：'"
                    v-for="(val, key) in item.content"
                    :key="key"
                    v-if="key==1"
                  >
                    <el-radio-group
                      v-model="val.is_show"
                      @change="changeAccountItem(item.module_name, val.icon_name, val.is_show)"
                    >
                      <el-radio label="1">{{ $t('personalCenter.show') }}</el-radio>
                      <el-radio label="0">{{ $t('personalCenter.noShow') }}</el-radio>
                    </el-radio-group>
                    <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
                      <div
                        class="imgContainter"
                        @click="changeImgHandler(item.module_name, val.icon_name)"
                      >
                        <el-image
                          fit="scale-down"
                          :src="imgHost + val.icon"
                        />
                        <div class="selectIcon">{{ $t('personalCenter.changeIcon') }}</div>
                      </div>
                      <div style="margin-left: 10px;">
                        <p style="color: #999;">{{ $t('personalCenter.iconTip') }}</p>
                        <el-button
                          type="text"
                          @click="resetIconHandler(item.module_name, val.icon_name, '/image/admin/uc_config/icon_bargain.png')"
                        >{{ $t('personalCenter.resetIcon') }}</el-button>
                      </div>
                    </div>
                  </el-form-item>
                  <el-form-item
                    :label="$t('personalCenter.award') + '：'"
                    v-for="(val, key) in item.content"
                    :key="key"
                    v-if="key==2"
                  >
                    <el-radio-group
                      v-model="val.is_show"
                      @change="changeAccountItem(item.module_name, val.icon_name, val.is_show)"
                    >
                      <el-radio label="1">{{ $t('personalCenter.show') }}</el-radio>
                      <el-radio label="0">{{ $t('personalCenter.noShow') }}</el-radio>
                    </el-radio-group>
                    <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
                      <div
                        class="imgContainter"
                        @click="changeImgHandler(item.module_name, val.icon_name)"
                      >
                        <el-image
                          fit="scale-down"
                          :src="imgHost + val.icon"
                        />
                        <div class="selectIcon">{{ $t('personalCenter.changeIcon') }}</div>
                      </div>
                      <div style="margin-left: 10px;">
                        <p style="color: #999;">{{ $t('personalCenter.iconTip') }}</p>
                        <el-button
                          type="text"
                          @click="resetIconHandler(item.module_name, val.icon_name, '/image/admin/uc_config/icon_award.png')"
                        >{{ $t('personalCenter.resetIcon') }}</el-button>
                      </div>
                    </div>
                  </el-form-item>

                  <el-form-item
                    :label="$t('personalCenter.commentList') + '：'"
                    v-for="(val, key) in item.content"
                    :key="key"
                    v-if="key==3"
                  >
                    <el-radio-group
                      v-model="val.is_show"
                      @change="changeAccountItem(item.module_name, val.icon_name, val.is_show)"
                    >
                      <el-radio label="1">{{ $t('personalCenter.show') }}</el-radio>
                      <el-radio label="0">{{ $t('personalCenter.noShow') }}</el-radio>
                    </el-radio-group>
                    <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
                      <div
                        class="imgContainter"
                        @click="changeImgHandler(item.module_name, val.icon_name)"
                      >
                        <el-image
                          fit="scale-down"
                          :src="imgHost + val.icon"
                        />
                        <div class="selectIcon">{{ $t('personalCenter.changeIcon') }}</div>
                      </div>
                      <div style="margin-left: 10px;">
                        <p style="color: #999;">{{ $t('personalCenter.iconTip') }}</p>
                        <el-button
                          type="text"
                          @click="resetIconHandler(item.module_name, val.icon_name, '/image/admin/uc_config/icon_comment.png')"
                        >{{ $t('personalCenter.resetIcon') }}</el-button>
                      </div>
                    </div>
                  </el-form-item>
                  <el-form-item
                    :label="$t('personalCenter.storeList') + '：'"
                    v-for="(val, key) in item.content"
                    :key="key"
                    v-if="key==4"
                  >
                    <el-radio-group
                      v-model="val.is_show"
                      @change="changeAccountItem(item.module_name, val.icon_name, val.is_show)"
                    >
                      <el-radio label="1">{{ $t('personalCenter.show') }}</el-radio>
                      <el-radio label="0">{{ $t('personalCenter.noShow') }}</el-radio>
                    </el-radio-group>
                    <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
                      <div
                        class="imgContainter"
                        @click="changeImgHandler(item.module_name, val.icon_name)"
                      >
                        <el-image
                          fit="scale-down"
                          :src="imgHost + val.icon"
                        />
                        <div class="selectIcon">{{ $t('personalCenter.changeIcon') }}</div>
                      </div>
                      <div style="margin-left: 10px;">
                        <p style="color: #999;">{{ $t('personalCenter.iconTip') }}</p>
                        <el-button
                          type="text"
                          @click="resetIconHandler(item.module_name, val.icon_name, '/image/admin/uc_config/icon_store.png')"
                        >{{ $t('personalCenter.resetIcon') }}</el-button>
                      </div>
                    </div>
                  </el-form-item>
                  <el-form-item
                    :label="$t('personalCenter.userActivate') + '：'"
                    v-for="(val, key) in item.content"
                    :key="key"
                    v-if="key==5"
                  >
                    <el-radio-group
                      v-model="val.is_show"
                      @change="changeAccountItem(item.module_name, val.icon_name, val.is_show)"
                    >
                      <el-radio label="1">{{ $t('personalCenter.show') }}</el-radio>
                      <el-radio label="0">{{ $t('personalCenter.noShow') }}</el-radio>
                    </el-radio-group>
                    <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
                      <div
                        class="imgContainter"
                        @click="changeImgHandler(item.module_name, val.icon_name)"
                      >
                        <el-image
                          fit="scale-down"
                          :src="imgHost + val.icon"
                        />
                        <div class="selectIcon">{{ $t('personalCenter.changeIcon') }}</div>
                      </div>
                      <div style="margin-left: 10px;">
                        <p style="color: #999;">{{ $t('personalCenter.iconTip') }}</p>
                        <el-button
                          type="text"
                          @click="resetIconHandler(item.module_name, val.icon_name, '/image/admin/uc_config/icon_member.png')"
                        >{{ $t('personalCenter.resetIcon') }}</el-button>
                      </div>
                    </div>
                  </el-form-item>
                  <el-form-item
                    :label="$t('personalCenter.orderVerify') + '：'"
                    v-for="(val, key) in item.content"
                    :key="key"
                    v-if="key==6"
                  >
                    <el-radio-group
                      v-model="val.is_show"
                      @change="changeAccountItem(item.module_name, val.icon_name, val.is_show)"
                    >
                      <el-radio label="1">{{ $t('personalCenter.show') }}</el-radio>
                      <el-radio label="0">{{ $t('personalCenter.noShow') }}</el-radio>
                    </el-radio-group>
                    <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
                      <div
                        class="imgContainter"
                        @click="changeImgHandler(item.module_name, val.icon_name)"
                      >
                        <el-image
                          fit="scale-down"
                          :src="imgHost + val.icon"
                        />
                        <div class="selectIcon">{{ $t('personalCenter.changeIcon') }}</div>
                      </div>
                      <div style="margin-left: 10px;">
                        <p style="color: #999;">{{ $t('personalCenter.iconTip') }}</p>
                        <el-button
                          type="text"
                          @click="resetIconHandler(item.module_name, val.icon_name, '/image/admin/uc_config/icon_scan.png')"
                        >{{ $t('personalCenter.resetIcon') }}</el-button>
                      </div>
                    </div>
                  </el-form-item>
                  <el-form-item
                    :label="$t('personalCenter.presentList') + '：'"
                    v-for="(val, key) in item.content"
                    :key="key"
                    v-if="key==7"
                  >
                    <el-radio-group
                      v-model="val.is_show"
                      @change="changeAccountItem(item.module_name, val.icon_name, val.is_show)"
                    >
                      <el-radio label="1">{{ $t('personalCenter.show') }}</el-radio>
                      <el-radio label="0">{{ $t('personalCenter.noShow') }}</el-radio>
                    </el-radio-group>
                    <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
                      <div
                        class="imgContainter"
                        @click="changeImgHandler(item.module_name, val.icon_name)"
                      >
                        <el-image
                          fit="scale-down"
                          :src="imgHost + val.icon"
                        />
                        <div class="selectIcon">{{ $t('personalCenter.changeIcon') }}</div>
                      </div>
                      <div style="margin-left: 10px;">
                        <p style="color: #999;">{{ $t('personalCenter.iconTip') }}</p>
                        <el-button
                          type="text"
                          @click="resetIconHandler(item.module_name, val.icon_name, '/image/admin/uc_config/icon_pre.png')"
                        >{{ $t('personalCenter.resetIcon') }}</el-button>
                      </div>
                    </div>
                  </el-form-item>

                  <el-form-item
                    :label="$t('personalCenter.customIcon') + '：'"
                    v-for="(val, key) in item.content"
                    :key="key"
                    v-if="key>7"
                  >
                    <el-radio-group
                      v-model="val.is_show"
                      @change="changeAccountItem(item.module_name, val.icon_name, val.is_show, key)"
                    >
                      <el-radio label="1">{{ $t('personalCenter.show') }}</el-radio>
                      <el-radio label="0">{{ $t('personalCenter.noShow') }}</el-radio>
                    </el-radio-group>
                    <el-button
                      type="text"
                      style="margin-left: 10px;"
                      @click="delTemplate(item.content, key)"
                    >{{ $t('personalCenter.deleteTemplate') }}</el-button>
                    <div style="margin: 10px 0;">
                      {{ $t('personalCenter.title') }}&nbsp;&nbsp;<el-input
                        :placeholder="$t('personalCenter.inputTip')"
                        maxlength="10"
                        show-word-limit
                        v-model="val.title"
                        size="small"
                        @blur="changeTemplateTitle(item.module_name, key, val.title)"
                        style="width: 170px;"
                      ></el-input>
                    </div>
                    <div style="display: flex;align-items: center;flex-wrap: wrap;overflow: hidden;">
                      <div
                        class="imgContainter"
                        @click="changeImgHandler(item.module_name, val.icon_name)"
                      >
                        <el-image
                          fit="scale-down"
                          :src="imgHost + val.icon"
                        />
                        <div class="selectIcon">{{ $t('personalCenter.changeIcon') }}</div>
                      </div>
                      <div style="margin-left: 10px;">
                        <p style="color: #999;">{{ $t('personalCenter.iconTip') }}</p>
                      </div>
                      <div>{{ $t('personalCenter.jumpPage') }}<el-button
                          type="text"
                          @click="selectLink(key)"
                        >{{ $t('personalCenter.selectLink') }}</el-button>
                      </div>
                      <div
                        style="margin-left: 20px;"
                        v-if="val.link!=''"
                      >{{ val.link }} {{ val.link_name }}</div>
                    </div>
                  </el-form-item>
                  <div style="text-align: center;">
                    <el-button
                      type="primary"
                      size="small"
                      style="text-align: center;"
                      @click="addTemplate"
                    >{{ $t('personalCenter.addTemplate') }}</el-button>
                  </div>
                </el-form>

              </el-collapse-item>
            </el-collapse>
          </div>
        </div>

      </div>
      <div class="footer">
        <el-button
          type="primary"
          size="small"
          @click="saveClickHandler"
        >{{ $t('personalCenter.save') }}</el-button>
      </div>
    </div>
    <!--图片dialog-->
    <!-- pageIndex='pictureSpace' -->
    <ImageDalog
      :tuneUp="selfImgDialogShow"
      pageIndex="pictureSpace"
      :imageSize="imageSize"
      :isDraggable='isDraggable'
      @handleSelectImg='imgDialogSelectedCallback'
    />
    <!-- 选择链接弹窗 -->
    <selectLinks
      @selectLinkPath="getLinkPath"
      :tuneUpSelectLink="tuneUpSelectLink"
    />
  </div>
</template>
<script>
/* 组件导入 */
// import { mapGetters } from 'vuex'
import ImageDalog from '@/components/admin/imageDalog'
import SelectLinks from '@/components/admin/selectLinks'
import { personalGetRequest, personalSaveRequest } from '@/api/admin/personalCenter'
export default {
  components: { SelectLinks, ImageDalog },
  data () {
    return {
      activeNames: ['1', '2', '3', '4', '5', '6'],
      styleChoose: '2', // 页面布局样式
      imgHost: `${this.$imageHost}`,
      bgImg: '', // 自定义背景图
      bgImage: '', // 自定义背景图完整路径
      selfImgDialogShow: false, // 图片dialog
      moduleTitle: '',
      module_name: '',
      tuneUpSelectLink: false, // 链接dialog
      linkIndex: -1, // 链接
      isShowOrder: '1', // 模块样式
      isShowCustomize: '1', // 自定义模板内容
      // customValue: 1, // 自定义模板个数
      templateTitle: '', // 自定义模板标题
      imageSize: [], // 图片大小
      isDraggable: false, // 是否支持多选

      leftData: [{
        module_name: 'global',
        page_style: '2'
      }, {
        module_name: 'center_header',
        is_show: '1',
        bg_type: '0',
        bg_img: ''
      }, {
        module_name: 'account_money',
        is_show: 1,
        title: '我的资产',
        content: [
          {
            icon_name: 'account',
            is_show: '1'
          },
          {
            icon_name: 'score',
            is_show: '1'
          },
          {
            icon_name: 'coupon',
            is_show: '1'
          },
          {
            icon_name: 'card',
            is_show: '1'
          }
        ]
      }, {
        module_name: 'order',
        is_show: '1',
        title: '我的订单',
        module_style: '1',
        content: [
          {
            icon_name: 'wait_pay',
            icon: '/image/admin/uc_config/uc_order_icon1.png',
            is_show: '1'
          },
          {
            icon_name: 'wait_deliver',
            icon: '/image/admin/uc_config/uc_order_icon2.png',
            is_show: '1'
          },
          {
            icon_name: 'wait_receive',
            icon: '/image/admin/uc_config/uc_order_icon3.png',
            is_show: '1'
          },
          {
            icon_name: 'wait_comment',
            icon: '/image/admin/uc_config/uc_order_icon4.png',
            is_show: '1'
          },
          {
            icon_name: 'refund',
            icon: '/image/admin/uc_config/uc_order_icon5.png',
            is_show: '1'
          }
        ]
      }, {
        module_name: 'appointment',
        is_show: '1',
        title: '我的预约'
      }, {
        module_name: 'use_record',
        is_show: '1',
        title: '使用记录',
        is_show_collect: '1',
        is_show_buy_history: '1',
        is_show_footprint: '1'
      }, {
        module_name: 'service',
        title: '我的服务',
        is_show: '1',
        content: [
          {
            is_show: '1',
            icon_name: 'distribution',
            icon: '/image/admin/uc_config/icon_dis.png',
            link: '',
            link_name: ''
          },
          {
            is_show: '1',
            icon_name: 'bargain',
            icon: '/image/admin/uc_config/icon_bargain.png',
            link: '',
            link_name: ''
          },
          {
            is_show: '1',
            icon_name: 'award',
            icon: '/image/admin/uc_config/icon_award.png',
            link: '',
            link_name: ''
          },
          {
            is_show: '1',
            icon_name: 'comment_list',
            icon: '/image/admin/uc_config/icon_comment.png',
            link: '',
            link_name: ''
          },
          {
            is_show: '1',
            icon_name: 'store_list',
            icon: '/image/admin/uc_config/icon_store.png',
            link: '',
            link_name: ''
          },
          {
            is_show: '1',
            icon_name: 'user_activate',
            icon: '/image/admin/uc_config/icon_member.png',
            link: '',
            link_name: ''
          },
          {
            is_show: '1',
            icon_name: 'order_verify',
            icon: '/image/admin/uc_config/icon_scan.png',
            link: '',
            link_name: ''
          },
          {
            is_show: '1',
            icon_name: 'present_list',
            icon: '/image/admin/uc_config/icon_pre.png',
            link: '',
            link_name: ''
          },
          {
            is_show: '1',
            icon_name: 'custom_icon',
            icon: '/image/admin/uc_config/icon_ownset.png',
            title: '帮助中心',
            link: '',
            link_name: ''
          }
        ]
      }],

      rightData: [{
        module_name: 'global',
        page_style: '2'
      }, {
        module_name: 'center_header',
        is_show: '1',
        bg_type: '0',
        bg_img: ''
      }, {
        module_name: 'account_money',
        is_show: '1',
        title: '我的资产',
        content: [
          {
            icon_name: 'account',
            is_show: '1'
          },
          {
            icon_name: 'score',
            is_show: '1'
          },
          {
            icon_name: 'coupon',
            is_show: '1'
          },
          {
            icon_name: 'card',
            is_show: '1'
          }
        ]
      }, {
        module_name: 'order',
        is_show: '1',
        title: '我的订单',
        module_style: '1',
        content: [
          {
            icon_name: 'wait_pay',
            icon: '/image/admin/uc_config/uc_order_icon1.png',
            is_show: '1'
          },
          {
            icon_name: 'wait_deliver',
            icon: '/image/admin/uc_config/uc_order_icon2.png',
            is_show: '1'
          },
          {
            icon_name: 'wait_receive',
            icon: '/image/admin/uc_config/uc_order_icon3.png',
            is_show: '1'
          },
          {
            icon_name: 'wait_comment',
            icon: '/image/admin/uc_config/uc_order_icon4.png',
            is_show: '1'
          },
          {
            icon_name: 'refund',
            icon: '/image/admin/uc_config/uc_order_icon5.png',
            is_show: '1'
          }
        ]
      }, {
        module_name: 'appointment',
        is_show: '1',
        title: '我的预约'
      }, {
        module_name: 'use_record',
        is_show: '1',
        title: '使用记录',
        is_show_collect: '1',
        is_show_buy_history: '1',
        is_show_footprint: '1'
      }, {
        module_name: 'service',
        title: '我的服务',
        is_show: '1',
        content: [
          {
            is_show: '1',
            icon_name: 'distribution',
            icon: '/image/admin/uc_config/icon_dis.png',
            link: '',
            link_name: ''
          },
          {
            is_show: '1',
            icon_name: 'bargain',
            icon: '/image/admin/uc_config/icon_bargain.png',
            link: '',
            link_name: ''
          },
          {
            is_show: '1',
            icon_name: 'award',
            icon: '/image/admin/uc_config/icon_award.png',
            link: '',
            link_name: ''
          },
          {
            is_show: '1',
            icon_name: 'comment_list',
            icon: '/image/admin/uc_config/icon_comment.png',
            link: '',
            link_name: ''
          },
          {
            is_show: '1',
            icon_name: 'store_list',
            icon: '/image/admin/uc_config/icon_store.png',
            link: '',
            link_name: ''
          },
          {
            is_show: '1',
            icon_name: 'user_activate',
            icon: '/image/admin/uc_config/icon_member.png',
            link: '',
            link_name: ''
          },
          {
            is_show: '1',
            icon_name: 'order_verify',
            icon: '/image/admin/uc_config/icon_scan.png',
            link: '',
            link_name: ''
          },
          {
            is_show: '1',
            icon_name: 'present_list',
            icon: '/image/admin/uc_config/icon_pre.png',
            link: '',
            link_name: ''
          },
          {
            is_show: '1',
            icon_name: 'custom_icon',
            icon: '/image/admin/uc_config/icon_ownset.png',
            title: '帮助中心',
            link: '',
            link_name: ''
          }
        ]
      }]
    }
  },
  computed: {

  },
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  created () {
    // 初始化查询
    this.getPersonal()
  },
  methods: {
    // 获取个人中心数据
    getPersonal () {
      personalGetRequest().then((res) => {
        if (res.error === 0) {
          this.rightData = res.content
          this.leftData = res.content
          this.bgImg = this.rightData[1].bg_img
          this.bgImage = this.imgHost + this.bgImg
        }
      })
    },

    // 保存
    saveClickHandler () {
      if (this.rightData[1].bg_Type === '0') {
        this.rightData[1].bg_img = ''
      } else {
        this.rightData[1].bg_img = this.bgImg
      }

      let obj = this.rightData
      personalSaveRequest(obj).then((res) => {
        if (res.error === 0) {
          this.$message.success({
            message: '保存成功'
          })
        } else {
          this.$message.warning(res.message)
        }
      })
    },

    // 切换布局样式
    changepageStyle (val) {
      this.styleChoose = val
    },
    // 切换模块样式
    changeModuleStyle (val) {
      this.isShowOrder = val
    },

    // 切换模块显示隐藏
    changeSwitch (name, value) {
      for (let i = 0; i < this.leftData.length; i++) {
        if (this.leftData[i].module_name === name) {
          this.leftData[i].is_show = value
        }
      }
    },

    // 切换标题数据响应
    changeTitle (name, value) {
      for (let i = 0; i < this.leftData.length; i++) {
        if (this.leftData[i].module_name === name) {
          this.leftData[i].title = value
        }
      }
    },

    changeTemplateTitle (name, index, value) {
      for (let i = 0; i < this.leftData.length; i++) {
        if (this.leftData[i].module_name === name) {
          for (let j = 0; j < this.leftData[i].content.length; j++) {
            this.leftData[i].content[index].title = value
          }
        }
      }
    },

    // 切换列表项显示隐藏
    changeAccountItem (title, name, value, key) {
      var itemNum = 0
      for (let i = 0; i < this.leftData.length; i++) {
        if (this.leftData[i].module_name === title) {
          for (let j = 0; j < this.leftData[i].content.length; j++) {
            if (this.leftData[i].content[j].icon_name === name) {
              if (key) {
                // 自定义模板
                this.leftData[i].content[key].is_show = value
              } else {
                this.leftData[i].content[j].is_show = value
              }
            }
            // 判断是否全部关闭
            if (this.leftData[i].content[j].is_show === '1') {
              itemNum++
            }
          }
          if (itemNum === 0) {
            this.leftData[i].is_show = '0'
          } else {
            this.leftData[i].is_show = '1'
          }
        }
      }
    },

    changeItem (name, type, value) {
      for (let i = 0; i < this.leftData.length; i++) {
        if (this.leftData[i].module_name === name) {
          for (let key in this.leftData[i]) {
            if (key === type) {
              let data = this.leftData[i]
              data[key] = value
            }
          }
          // 判断是否全部关闭
          if (this.leftData[i].is_show_collect === '0' && this.leftData[i].is_show_buy_history === '0' && this.leftData[i].is_show_footprint === '0') {
            this.leftData[i].is_show = '0'
          } else {
            this.leftData[i].is_show = '1'
          }
        }
      }
    },

    // 左侧模块排序
    swapItems (arr, index1, index2) {
      arr[index1] = arr.splice(index2, 1, arr[index1])[0]
      return arr
    },
    // 左侧模块上移
    upClickHandler (arr, index) {
      if (index === 2) {
        this.$message.warning('当前模块不能移动')
      } else {
        this.swapItems(arr, index, index - 1)
      }
    },
    // 左侧模块下移
    downClickHandler (arr, index) {
      if (index === this.leftData.length - 1) {
        this.$message.warning('当前模块不能移动')
      } else {
        this.swapItems(arr, index, index + 1)
      }
    },
    // 切换图片
    changeImgHandler (title, name) {
      this.moduleTitle = title
      this.module_name = name
      this.selfImgDialogShow = !this.selfImgDialogShow
      if (name !== '') {
        this.imageSize = [50, 50]
      } else {
        this.imageSize = [750, 300]
      }
    },
    // 重置图标
    resetIconHandler (title, name, value) {
      for (var i = 0; i < this.rightData.length; i++) {
        if (this.rightData[i].module_name === title) {
          for (var j = 0; j < this.rightData[i].content.length; j++) {
            if (this.rightData[i].content[j].icon_name === name) {
              this.rightData[i].content[j].icon = value
            }
          }
        }
      }
      // for (var m = 0; m < this.leftData.length; m++) {
      //   if (this.leftData[m].module_name === title) {
      //     for (var n = 0; n < this.leftData[m].content.length; j++) {
      //       if (this.leftData[m].content[n].icon_name === name) {
      //         this.leftData[m].content[n].icon = value
      //       }
      //     }
      //   }
      // }
    },

    // 商品图片点击回调函数
    imgDialogSelectedCallback (imgObj) {
      // 右侧显示
      for (let i = 0; i < this.rightData.length; i++) {
        if (this.moduleTitle === 'center_header') {
          // this.rightData[i].bg_img = '/' + imgObj.imgPath
          this.bgImg = '/' + imgObj.imgPath
          this.bgImage = this.imgHost + this.bgImg
        } else {
          if (this.rightData[i].module_name === this.moduleTitle) {
            for (let j = 0; j < this.rightData[i].content.length; j++) {
              if (this.rightData[i].content[j].icon_name === this.module_name) {
                this.rightData[i].content[j].icon = '/' + imgObj.imgPath
                return
              }
            }
          }
        }
      }
      // 左侧显示
      for (let i = 0; i < this.leftData.length; i++) {
        if (this.leftData[i].module_name === this.moduleTitle) {
          for (let j = 0; j < this.leftData[i].content.length; j++) {
            if (this.leftData[i].content[j].icon_name === this.module_name) {
              this.leftData[i].content[j].icon = imgObj.imgPath
              return
            }
          }
        }
      }
    },

    //  自定义模板跳转链接
    selectLink (index) {
      this.linkIndex = index
      this.tuneUpSelectLink = !this.tuneUpSelectLink
    },
    // 获取选中的path
    getLinkPath (res) {
      for (let i = 0; i < this.rightData.length; i++) {
        if (this.rightData[i].module_name === 'service') {
          for (let j = 0; j < this.rightData[i].content.length; j++) {
            if (j === this.linkIndex) {
              this.rightData[i].content[this.linkIndex].link = res
            }
          }
        }
      }
    },
    // 删除自定义模板
    delTemplate (arr, index) {
      // 删除右侧
      for (let i = 0; i < this.rightData.length; i++) {
        if (this.rightData[i].module_name === 'service') {
          this.rightData[i].content.splice(index, 1)
        }
      }
      // 删除左侧
      // for (let i = 0; i < this.leftData.length; i++) {
      //   if (this.leftData[i].module_name === 'service') {
      //     this.leftData[i].content.splice(index, 1)
      //   }
      // }
    },
    // 添加自定义模板
    addTemplate () {
      // 右侧添加
      // icon_name: 'custom_icon' + this.customValue,
      for (let i = 0; i < this.rightData.length; i++) {
        if (this.rightData[i].module_name === 'service') {
          this.rightData[i].content.push({
            is_show: '1',
            icon_name: 'custom_icon',
            icon: '/image/admin/uc_config/icon_ownset.png',
            title: '我的服务',
            link: '',
            link_name: ''
          })
        }
      }
      // for (let i = 0; i < this.leftData.length; i++) {
      //   if (this.leftData[i].module_name === 'service') {
      //     this.leftData[i].content.push({
      //       is_show: '1',
      //       icon_name: 'custom_icon',
      //       icon: '/image/admin/uc_config/icon_ownset.png',
      //       title: '我的服务',
      //       link: '',
      //       link_name: ''
      //     })
      //   }
      // }
      // this.customValue++
    }
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
  position: relative;
  background-color: #fff;
  height: 100%;
  overflow: hidden;
  overflow-y: auto;
  /* padding-bottom: 96px; */
}

.prompt {
  width: 845px;
  height: 40px;
  line-height: 40px;
  border: 1px solid #f2e1c8;
  background: #fff7ec;
  color: #666;
  padding-left: 12px;
  margin: 20px auto;
}

.prompt img {
  margin: -3px 5px 0 0;
}

.bottomContent {
  width: 845px;
  overflow: hidden;
  margin: 0 auto;
  margin-bottom: 70px;
}

.cententLeft {
  /* width: 323px; */
  width: 350px;
  border: 1px solid #ccc;
  background: #eee;
  position: relative;
  float: left;
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
  width: 105px;
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
  text-align: center;
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

.each_item .item_app {
  width: 100%;
  display: flex;
}

.each_item .orderContent .app_img {
  /* margin-left: 10px;
  border: 1px solid #ccc;
  width: 40px;
  height: 40px;
  overflow: hidden;
  flex: 0.2;
  margin-right: 10px;
  text-align: left; */
}

.app_img img {
  border: none;
  width: 100%;
  height: 100%;
}

.each_serve {
  width: 105px;
  margin-bottom: 20px;
  float: left;
  flex: 30% !important;

  justify-content: center;
  flex-direction: column;
  align-items: center;
  display: flex;
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
  text-align: left;
  flex: 1.5;
  flex-direction: column;
  justify-content: space-around;
  height: 40px;
}

.app_info p {
  font-size: 14px;
  color: #333;
}

.app_info p:last-child {
  margin-top: 10px;
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
  margin-left: 15px;
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
  margin-left: 15px;
}
.cententRight_container span,
.cententRight_container p {
  color: #999;
  font-size: 12px;
}
.cententRight_container .el-collapse-item {
  padding: 10px;
  background: #fff;
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
  position: fixed;
  bottom: 0;
  right: 27px;
  left: 160px;
  height: 52px;
  padding: 10px 0;
  background-color: #fff;
  text-align: center;
  border-top: 1px solid #eee;
  z-index: 9;
}
</style>
