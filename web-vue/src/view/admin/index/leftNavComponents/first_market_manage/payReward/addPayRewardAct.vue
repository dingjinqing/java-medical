<template>
  <div class="addPayRewardAct">
    <div class="addPayRewardAct_main">

      <!-- 左侧内容区域 -->
      <div class="leftContent">
        <!-- 标题图片部分 -->
        <div class="titles">
          <img
            :src="$imageHost+'/image/admin/shop_beautify/phone_tops.png'"
            alt=""
          >
        </div>
        <div class="carousel">
          <el-carousel
            trigger="click"
            height="573px"
            arrow="never"
          >
            <el-carousel-item
              v-for="(item,index) in carouselList"
              :key="index"
            >
              <img
                :src="item.src"
                alt=""
                style="height: 100%; width: 100%;"
              >
            </el-carousel-item>
          </el-carousel>
        </div>
      </div>

      <!-- 右侧内容区域 -->
      <div class="rightContent">
        <!-- 活动配置区域 -->
        <section class="container">
          <div class="title">{{$t('payReward.actSetting')}}</div>
          <el-form
            label-position="right"
            label-width="113px"
            ref="payRewardForm"
            :rules="rules"
            :model="params"
          >
            <el-form-item
              :label="$t('payReward.actName')"
              style="margin-top:15px"
              prop="activityNames"
            >
              <el-input
                v-model="params.activityNames"
                size="small"
                style="width: 170px"
                :placeholder="$t('payReward.maxWords')"
                maxlength="10"
                show-word-limit
              ></el-input>
            </el-form-item>

            <el-form-item
              :label="$t('payReward.validDate')"
              prop="timeType"
            >
              <div>
                <el-radio
                  v-model="params.timeType"
                  :label=0
                  style="margin-right: 20px;"
                >{{$t('payReward.fixedTime')}}</el-radio>
                <el-date-picker
                  v-model="dateInterval"
                  type="datetimerange"
                  style="width:400px;"
                  size="small"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  :range-separator="$t('payReward.to')"
                  :start-placeholder="$t('payReward.effectTime')"
                  :end-placeholder="$t('payReward.expirationTime')"
                  :disabled="params.timeType === 1"
                  :default-time="['00:00:00','23:59:59']"
                ></el-date-picker>
                <div>
                  <el-radio
                    v-model="params.timeType"
                    :label=1
                  >{{$t('payReward.foreverTime')}}</el-radio>
                </div>
              </div>
            </el-form-item>

            <el-form-item
              :label="$t('payReward.priority') + '：'"
              prop="actFirst"
            >
              <el-input
                v-model="params.actFirst"
                size="small"
                style="width:170px"
              ></el-input>
              <div class="tips">{{$t('payReward.levelTips')}}</div>
            </el-form-item>

            <el-form-item
              :label="$t('payReward.triggerCondition')+ '：'"
              class="triggerCondition"
              prop="goodsAreaType"
            >
              <span style="color: #999">{{$t('payReward.relationship')}}</span>
              <div>
                <span>{{$t('payReward.goodsCondition')}}</span>
                <el-radio-group v-model="params.goodsAreaType">
                  <el-radio :label=1>{{$t('payReward.allGoods')}}</el-radio>
                  <el-radio :label=2>{{$t('payReward.partOfGoods')}}</el-radio>
                </el-radio-group>
                <div
                  class="noneBlock"
                  v-if="params.goodsAreaType === 2"
                >
                  <div
                    class="noneBlockList"
                    v-for="(item,index) in noneBlockDiscArr"
                    :key="index"
                  >
                    <div class="noneBlockLeft"
                         @click="hanldeToAddGoodS(index)">
                      <img :src="$imageHost+'/image/admin/icon_jia.png'">
                      {{item.name}}
                    </div>
                    <div
                      v-if="item.num"
                      class="noneBlockRight"
                      @click="hanldeToShowGoodS(index)"
                    >{{$t('payReward.selectedClassfication')[index]}} {{item.num}}{{$t('payReward.selectedNumber')[index]}}</div>
                  </div>
                </div>
              </div>
              <div style="display: flex">
                <span>{{$t('payReward.payCondition')}}</span>
                <span>{{$t('payReward.everyOrder')}}</span>
                <el-form-item
                  prop="minPayMoney"
                  style="margin:0 5px"
                  :rules="[
                    { validator: (rule, value, callback) => {validateMoney(rule, value, callback )}, trigger: ['blur', 'change']}
                  ]"
                >
                  <el-input
                    v-model="params.minPayMoney"
                    size="small"
                    style="width: 100px"
                  ></el-input>
                </el-form-item>
                <span>{{$t('payReward.everyOrderTips')}}</span>
              </div>
            </el-form-item>

            <el-form-item :label="$t('payReward.joinConstraint')">
              <div style="display:flex">
                <span>{{$t('payReward.everyJoin')}}</span>
                <el-form-item prop="limitTimes">
                  <el-input
                    v-model="params.limitTimes"
                    size="small"
                    style="width: 100px;margin:0 5px"
                  ></el-input>
                </el-form-item>
                <span>{{$t('payReward.everyJoinTime')}}</span>
              </div>
            </el-form-item>
          </el-form>
        </section>

        <!-- 支付奖励区域 -->
        <section class="container">
          <div class="pay_rewards">
            <div class="name">{{$t('payReward.payAward')}}</div>
            <div>
              <span style="color: #999">{{$t('payReward.maxPayTimes')}}</span>
              <div
                class="addReward"
                style="display:flex"
                @click="addPayRewardItem()"
              >
                <div style="margin-top:-1px">+</div>{{$t('payReward.addReward')}}
              </div>
            </div>
          </div>
          <el-form
            label-width="113px"
            label-position="right"
            v-for="(item,index) in params.awardList"
            :key="index"
            class="order_form"
          >
            <!-- 表头部分 -->
            <el-form-item
              :label="'第'+(index+1)+'次支付奖励'"
              class="order"
              label-width="112px"
            >
              <div class="delIcon">
                <i
                  v-if="index>0"
                  class="el-icon-delete "
                  style="color:#409eff;cursor:pointer"
                  @click="deletePayRewardItem(index)"
                ></i>
              </div>
            </el-form-item>

            <!-- 支付奖励 -->
            <el-form-item
              :label="$t('payReward.payAward')+'：'"
              required
            >
              <el-radio-group
                v-model="params.awardList[index].giftType"
                class="itemOptions"
              >
                <div style="margin-top:13px">
                  <el-radio :label="0">{{$t('payReward.noPrize')}}</el-radio>
                  <el-radio :label="1">{{$t('payReward.ordinaryCoupon')}}</el-radio>
                  <el-radio :label="2">{{$t('payReward.splitCoupon')}}</el-radio>
                </div>
                <div style="margin-top:10px">
                  <el-radio :label="3">{{$t('payReward.luckyDraw')}}</el-radio>
                  <el-radio :label="4">{{$t('payReward.leftMoney')}}</el-radio>
                  <el-radio :label="5">{{$t('payReward.goods')}}</el-radio>
                </div>
                <div style="margin-top:10px">
                  <el-radio :label="6">{{$t('payReward.integral')}}</el-radio>
                  <el-radio :label="7">{{$t('payReward.custome')}}</el-radio>
                </div>
              </el-radio-group>
            </el-form-item>

            <!-- 普通优惠券 -->
            <!-- prop="awardList[index].ordinaryCoupon" -->
            <el-form-item
              v-if="item.giftType===1"
              :label="$t('payReward.ordinaryCoupon')+'：'"
              prop="ordinaryCouponCheck"
              :rules="[
                { required: true, message: '请选择普通优惠券', trigger: 'blur'},
                { validator: (rule, value, callback) => {validateOrdinaryCoupon(rule, value, callback, awardList[index].ordinaryCoupon)}, trigger:['blur']}
              ]"
            >
              <div class="middleContainer">
                <div
                  v-for="(itemC,indexC) in params.awardList[index].ordinaryCoupon"
                  :key="indexC"
                  class="addInfo clear"
                >
                  <section
                    class="couponImgWrapper"
                    style="line-height: normal"
                  >
                    <div
                      class="coupon_list_top"
                      v-if="itemC.actCode==='voucher'"
                    >
                      <span>￥</span>
                      <span>{{itemC.denomination}}</span>
                    </div>
                    <div
                      class="coupon_list_top"
                      v-if="itemC.actCode==='discount'"
                    >
                      <span style="font-size: 20px">{{itemC.denomination}}</span>
                      <span style="font-size: 14px">{{$t('payReward.discount')}}</span>
                    </div>
                    <div class="coupon_center_limit">{{itemC.useConsumeRestrict | formatLeastConsume(itemC.leastConsume)}}</div>
                    <div class="coupon_center_number">剩余{{itemC.surplus}}张</div>
                    <div
                      class="coupon_list_bottom"
                      style="font-size:12px"
                    >
                      <span v-if="itemC.scoreNumber === 0">领取</span>
                      <div v-if="itemC.scoreNumber !== 0">
                        <span>{{itemC.scoreNumber}}</span>积分 兑换
                      </div>
                    </div>
                  </section>
                  <span
                    @click="deleteCouponImg(itemC,indexC,index)"
                    class="deleteIcon"
                  >×</span>
                </div>

                <div
                  class="addInfo"
                  @click="handleToCallDialog1(item,index)"
                >
                  <el-image
                    fit="scale-down"
                    :src="imgHost+'/image/admin/shop_beautify/add_decorete.png'"
                    style="width: 78px;height:78px;cursor:pointer"
                  ></el-image>
                  <p>{{$t('payReward.addCoupon')}}</p>
                </div>
              </div>
              <div class="textTips">{{$t('payReward.maxCouponNumber')}}</div>
            </el-form-item>

            <!-- 分裂优惠券 -->
            <el-form-item
              v-if="params.awardList[index].giftType === 2 "
              :label="$t('payReward.splitCoupon')+ '：'"
              prop="awardList[index].splitCoupon"
              :rules="{
                required: true, message:'请选择分裂优惠券'

              }"
            >
              <div class="middleContainer">
                <div
                  v-for="(itemD,indexD) in params.awardList[index].splitCoupon"
                  :key="indexD"
                  class="addInfo"
                >
                  <section
                    class="couponImgWrapper"
                    style="line-height: normal"
                  >
                    <div
                      class="coupon_list_top"
                      v-if="itemD.actCode==='voucher'"
                    >
                      <span>￥</span>
                      <span>{{itemD.denomination}}</span>
                    </div>
                    <div
                      class="coupon_list_top"
                      v-else-if="itemD.actCode=='random'"
                    >
                      <span>￥</span>
                      <span>{{itemD.randomMax}}</span>
                      <span>最高</span>
                    </div>
                    <div
                      class="coupon_list_top"
                      v-else
                    >
                      <span style="font-size: 20px">{{itemD.denomination}}</span>
                      <span style="font-size: 14px">折</span>
                    </div>
                    <div class="coupon_center_limit">{{itemD.useConsumeRestrict | formatLeastConsume(itemD.leastConsume)}}</div>
                    <div class="coupon_center_number">剩余{{itemD.surplus}}张</div>
                    <div
                      class="coupon_list_bottom"
                      style="font-size:12px"
                    >
                      <span v-if="itemD.scoreNumber === 0">领取</span>
                      <div v-if="itemD.scoreNumber !== 0">
                        <span>{{itemD.scoreNumber}}</span>积分 兑换
                      </div>
                    </div>
                  </section>
                  <span
                    @click="deleteCouponImg2(itemD, indexD, index)"
                    class="deleteIcon"
                  >×</span>
                </div>

                <div
                  class="addInfo"
                  @click="handleToCallDialog2(item, index)"
                  style="line-height: normal"
                >
                  <!-- v-if="params.awardList[index].splitCoupon.length < 1" -->

                  <el-image
                    fit="scale-down"
                    :src="imgHost+'/image/admin/shop_beautify/add_decorete.png'"
                    style="width: 78px;height:78px;cursor:pointer"
                  ></el-image>
                  <p>{{$t('addBargainAct.addCoupon')}}</p>
                </div>
              </div>
              <div class="textTips">最多可以添加1张优惠券，已过期和已停用的优惠券不能添加</div>
            </el-form-item>

            <!-- 幸运大抽奖 -->
            <el-form-item
              v-if="item.giftType === 3"
              :label="$t('payReward.luckyDraw')+'：'"
              class="luckyDraw"
              :prop="`awardList[${index}].lotteryId`"
              :rules="[
                {required: true, validator:(rule, value, callback) => { validateLottery(rule, value, callback, item.lotteryId)}, trigger: ['change']}
              ]"
            >
              <el-select
                size="small"
                style="width: 120px"
                v-model="params.awardList[index].lotteryId"
                :placeholder="$t('payReward.selectDrawAct')"
              >
                <el-option
                  v-for="item in options"
                  :key="item.id"
                  :label="item.lotteryName"
                  :value="item.id"
                ></el-option>
              </el-select>&nbsp;
              <span>{{$t('payReward.refresh')}}</span> | <span @click="create">{{$t('payReward.new')}}</span> | <span @click="manage">{{$t('payReward.manage')}}</span>
            </el-form-item>

            <!-- 余额 -->
            <el-form-item
              v-if="params.awardList[index].giftType === 4"
              :label="$t('payReward.leftMoney')+'：'"
              :prop="`awardList[${index}].accountNumber`"
              :rules="[
                { required: true, validator: (rule, value, callback) => {validateAccountNumber(rule, value, callback, item.accountNumber)}, trigger: 'blur' }
              ]"
            >
              <el-input
                v-model="params.awardList[index].accountNumber"
                size="small"
                style="width:120px"
                :placeholder="$t('payReward.inputLeftMoney')"
              ></el-input>
            </el-form-item>

            <el-form-item
              v-if="item.giftType === 5"
              :label="$t('payReward.award')+ '：'"
              required
            >
              <div
                class="addGoodsWrapper"
                @click="addGoods(index)"
              >+&nbsp;{{$t('payReward.addGift')}}</div>
              <div
                v-if="true"
                class="goods_modal"
              >
                <table>
                  <thead>
                    <tr style="background: #F8F8F8;">
                      <th width="50%">{{$t('payReward.goodsName')}}</th>
                      <th width="10%">{{$t('payReward.goodsPrice')}}</th>
                      <th width="20%">{{$t('payReward.goodsNumber')}}</th>
                      <th width="20%">{{$t('payReward.goodsStatus')}}</th>
                    </tr>
                  </thead>
                  <tbody class="tbody">
                    <tr>
                      <td>
                        <section style="overflow:hidden">
                          <div
                            class="goods_img"
                            style="width:40px;height:40px; float: left"
                          >
                            <el-image
                              :src="params.awardList[index].goodsImg"
                              fit="contain"
                              style="width:100%; height: 100%;"
                            ></el-image>
                          </div>
                          <span
                            class="goods_name"
                            style="float: right;;"
                          >
                            {{ params.awardList[index].goodsName}}
                          </span>
                        </section>
                      </td>
                      <td>
                        ￥{{params.awardList[index].goodsPrice}}
                      </td>
                      <td>
                        {{ params.awardList[index].goodsNumber}}
                      </td>
                      <td>
                        上架
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </el-form-item>

            <el-form-item
              v-if="item.giftType === 5"
              :label="$t('payReward.giftValidity')+'：'"
              :prop="`awardList[${index}].keepDays`"
              :rules="[
                { required: true, validator: (rule,value, callback)=>(validateKeepDays(rule, value, callback, item.keepDays)), trigger:['blur', 'change']}
              ]"
            >
              <div>
                <el-input
                  v-model="params.awardList[index].keepDays"
                  size="small"
                  style="width:100px"
                ></el-input>
                <span>天</span>
                <div>中奖用户需在有效期内领取，过期后将无法领取</div>
              </div>
            </el-form-item>

            <el-form-item
              v-if="item.giftType === 6"
              :label="$t('payReward.integral')+'：'"
              :prop="`awardList[${index}].scoreNumber`"
              :rules="[
                { required: true, validator: (rule,value, callback)=>(validateIntegral(rule, value, callback, item.scoreNumber)), trigger:['blur', 'change']}
              ]"
            >
              <el-input
                v-model="params.awardList[index].scoreNumber"
                size="small"
                style="width:120px"
                :placeholder="$t('payReward.inputIntegral')"
              ></el-input>
            </el-form-item>

            <el-form-item
              v-if="item.giftType === 7"
              :label="$t('payReward.actImg')+ '：'"
              :prop="`awardList[${index}].customImage`"
              :rules="[
                { required: true, validator: (rule,value, callback)=>(validateImage(rule, value, callback, item.customImage)), trigger:['blur', 'change']}
              ]"
            >
              <div style="display: flex">
                <div
                  class="size"
                  @click="handleImage(index)"
                >
                  <el-image
                    :src="$imageHost + '/' + params.awardList[index].customImage"
                    fit="contain"
                    style=" width:100%; height: 100%;"
                  ></el-image>
                </div>
                <div style="margin-top:10px">{{$t('payReward.imgSize')}}</div>
              </div>
            </el-form-item>
            <el-form-item
              v-if="item.giftType === 7"
              :label="$t('payReward.settingLink')+'：'"
              :prop="`awardList[${index}].customLink`"
              :rules="[
                { required: true, validator: (rule,value, callback)=>(validateCustomLink(rule, value, callback, item.customLink)), trigger:['blur', 'change']}
              ]"
            >
              <el-input
                v-model="params.awardList[index].customLink"
                size="small"
                style="width:200px"
              ></el-input>
              <span
                @click="chooseSelect(index)"
                class="selectLink"
              >{{$t('payReward.chooseLink')}}</span>
            </el-form-item>

            <el-form-item
              v-if="item.giftType !== 0"
              :label="$t('payReward.giftNumber')+'：'"
              :prop="`awardList[${index}].awardNumber`"
              :rules="[
                { required: true, validator: (rule, value, callback)=>{validateGiftNmuber(rule, value, callback, item.awardNumber)}, trigger: ['blur', 'change']}
              ]"
            >
              <div>
                <el-input
                  v-model="params.awardList[index].awardNumber"
                  size="small"
                  style="width:100px"
                ></el-input>
                <span>已发{{params.awardList[index].sendNum}}份</span>
                <span>{{$t('payReward.giftTips2')}}</span>
                <div class="tips">{{$t('payReward.giftTips3')}}</div>
              </div>
            </el-form-item>
          </el-form>
        </section>
      </div>

      <!-- 保存按钮 -->
      <div class="save">
        <el-button
          size="small"
          type="primary"
          @click="activitySave"
        >{{$t('payReward.save')}}
        </el-button>
      </div>
    </div>

    <!--选择商家分类弹窗-->
    <AddingBusClassDialog
      :dialogVisible.sync="isShowBusinessPlatformDialog"
      :classFlag="classFlag"
      :backDataArr="shopAndPlatformBackDataArr"
      @BusClassTrueArr="getBusinessnPlatformSortData"
    />

    <!-- 活动配置 - 添加普通优惠券 -->
    <AddCouponDialog
      @handleToCheck="addCouponHandle"
      :tuneUpCoupon="addCouponVisible"
      :couponBack.sync="emptySelect"
      :type=0
    />

    <!--活动配置 - 添加分裂优惠券-->
    <AddCouponDialog
      @handleToCheck="addDisCouponHandle"
      :tuneUpCoupon="showCouponDialog2"
      :couponBack="disCouponIdList"
      :singleElection="true"
      :type="1"
    />

    <!-- 活动配置 - 触发条件 - 选择商品弹窗 -->
    <ChoosingGoods
      @result='getGoodsIdFromChoosingGoods'
      :tuneUpChooseGoods='controlChoosingGoodsDialog'
      :chooseGoodsBack='choosingGoodsDateTmpContainer'
      :onlyShowChooseGoods="isOnlyShowChooseGoods"
    />

    <!-- 选择支付奖励 - 自定义 - 链接弹窗 -->
    <SelectLinks
      :tuneUpSelectLink="tuneUpSelectLinkDialog"
      @selectLinkPath='handleToSelectLinkPath'
    />

    <!--支付奖励 - 自定义 - 选择图片弹窗 -->
    <ImageDalog
      pageIndex='pictureSpace'
      :tuneUp="tuneUpImageDialog"
      :imageSize="[560,780]"
      @handleSelectImg='avatarSelectHandle'
    />

    <!--支付奖励 - 奖品 - 添加奖品弹窗-->
    <ChoosingGoods
      @resultGoodsRow="addGiftDialog"
      :tuneUpChooseGoods="isShowChoosingGoodsDialog"
      :singleElection="true"
      :loadProduct="true"
      :chooseGoodsBack="chooseGoodsIdList"
    />
  </div>
</template>

<script>
import { addPayRewardAct, updatePayReward, getPayRewardInfo, isGoingAct } from '@/api/admin/marketManage/payReward.js'
import('@/util/date')

export default {
  components: {
    ChoosingGoods: () => import('@/components/admin/choosingGoods'),
    AddingBusClassDialog: () => import('@/components/admin/addingBusClassDialog'),
    SelectLinks: () => import('@/components/admin/selectLinks'),
    ImageDalog: () => import('@/components/admin/imageDalog'),
    AddCouponDialog: () => import('@/components/admin/addCouponDialog')
  },
  mounted () {
    this.getIsGonigLotteryActivity()

    if (this.$route.query.id > 0) {
      this.params.id = this.$route.query.id
      this.fetchCurrentActivityData()
    } else {
      this.addPayRewardItem()
    }
  },
  filters: {
    formatLeastConsume (useConsumeRestrict, leastConsume) {
      if (useConsumeRestrict === 0) {
        return `不限制`
      } else {
        return `满${leastConsume}元可用`
      }
    }
  },
  data () {
    var that = this
    var validateSiForever = (rule, value, callback) => {
      console.log(value, 'value data--')
      if (value === 0 && (this.dateInterval === null || this.dateInterval.length === 0)) {
        return callback(new Error('请选择活动生效时间'))
      } else {
        callback()
      }
    }
    var validatelevel = (rule, value, callback) => {
      var re = /^([1-9][0-9]{0,1}|100)$/
      if (!value) {
        callback(new Error('请填写优先级'))
      } else if (!re.test(value)) {
        callback(new Error('请填写1~100之间的正整数'))
      } else {
        callback()
      }
    }

    // 验证弹窗是否选择
    var validateGoodsAreaType = (rule, value, callback) => {
      console.log(value)
      console.log(this.noneBlockDiscArr)
      if (value === 2 && (that.noneBlockDiscArr[0].num === 0 && that.noneBlockDiscArr[1].num === 0 && that.noneBlockDiscArr[2].num === 0)) {
        return callback(new Error('请选择参加活动的商品'))
      } else {
        return callback()
      }
    }
    var limitTimesValidator = (rule, value, callback) => {
      var re = /^(0|\+?[1-9][0-9]*)$/
      if (!value) {
        callback(new Error('请输入参与限制'))
      } else if (!re.test(value)) {
        callback(new Error('请填写0或者正整数'))
      } else {
        callback()
      }
    }
    // var ordinaryCouponValidator = (rule, value, callback) => {
    //   if (this.params.awardList[this.currentModelIndex].ordinaryCoupon.length === 0) {
    //     callback(new Error('请选择普通优惠券'))
    //   } else {
    //     callback()
    //   }
    // }
    return {
      emptySelect: [],
      shopAndPlatformBackDataArr: null,
      choosingGoodsDateTmpContainer: null,
      shopCategoryIds: null, // 指定商品-商家分类
      platformCategoryIds: null, // 指定商品-平台分类
      AtreeType: null,
      idInfo: null,
      carouselList: [
        { src: 'http://mpdevimg2.weipubao.cn/image/admin/pay_gift1.jpg' },
        { src: 'http://mpdevimg2.weipubao.cn/image/admin/pay_gift2.jpg' },
        { src: 'http://mpdevimg2.weipubao.cn/image/admin/pay_gift3.jpg' }
      ],
      options: [],
      noneBlockDiscArr: [
        { name: '选择商品', num: 0 },
        { name: '选择商家分类', num: 0 },
        { name: '选择平台分类', num: 0 }
      ],
      controlChoosingGoodsDialog: false,
      userDialogFlag: null,
      classFlag: null, // 区分商家分类和平台分类flag
      isShowBusinessPlatformDialog: false, // 商家分类和平台分类调起
      choosingGoodsDateFlag1: null, // 指定商品-选择商品选中数据,
      // ownGoodsId: null, // 会员专享商品: 添加的商品Id
      tuneUpSelectLinkDialog: false,
      tuneUpImageDialog: false,
      isShowChoosingGoodsDialog: false,
      isOnlyShowChooseGoods: false,
      showCouponDialog2: false,
      disCouponIdList: [],
      addCouponVisible: false, // 优惠券
      currentModelIndex: 0, // 优惠券索引
      imgHost: `${this.$imageHost}`,
      dateInterval: [], // 时间范围
      chooseGoodsIdList: [], // 选择商品

      params: {
        id: '',
        activityNames: '',
        timeType: 0, // 时间类型
        startTime: '',
        endTime: '',
        actFirst: '', //  优先级
        goodsAreaType: 1, // 商品范围类型
        goodsIds: '', // 商品id
        goodsCatIds: '', // 商品平台分类
        goodsSortIds: '', // 商品商家分类
        minPayMoney: '', // 最少支付金额
        limitTimes: '', // 每个用户参与次数
        awardList: []
      },
      rules: {
        activityNames: [{ required: true, message: '请输入活动名称', trigger: 'blur' }],
        timeType: [{ required: true, validator: validateSiForever, trigger: ['blur', 'change'] }],
        actFirst: { required: true, validator: validatelevel, trigger: 'blur' },
        goodsAreaType: { required: true, validator: validateGoodsAreaType, trigger: ['blur', 'change'] },
        limitTimes: { required: true, validator: limitTimesValidator, trigger: 'blur' }
      }
    }
  },
  watch: {
    'params.timeType': function (value) {
      if (value) {
        this.$refs.payRewardForm.validateField('timeType')
      }
    },
    'params.goodsAreaType': function (value) {
      if (value) {
        this.$refs.payRewardForm.validateField('goodsAreaType')
      }
    }
  },
  methods: {
    // 添加奖励 - 增加对应的支付奖励次数
    addPayRewardItem () {
      let obj = {
        'giftType': 0,
        'productId': '',
        'keepDays': '',
        'accountNumber': '',
        'scoreNumber': '',
        'awardNumber': '',
        'ordinaryCoupon': [],
        'splitCoupon': [],
        'splitCouponIdList': [],
        'ordinaryCouponIdList': [],
        'customImage': 'image/admin/btn_add.png',
        'customLink': '',
        'goodsId': this.choosingGoodsDateFlag1,
        'shopCategoryIds': this.shopCategoryIds,
        'platformCategoryIds': this.platformCategoryIds,
        'couponIds': [],
        'couponList': [],
        'sendNum': '',
        'id': ''
      }
      if (this.params.awardList.length < 5) {
        this.params.awardList.push(obj)
        console.log(this.params.awardList)
        return obj
      } else {
        this.$message.warning('最多可添加5个规则！')
      }
    },

    // 删除奖励
    deletePayRewardItem (index) {
      console.log(this.params.awardList)
      this.params.awardList.splice(index, 1)
      console.log(index)
    },

    // 普通优惠券弹窗调起 currentIndex为当前的添加的Item的索引值
    handleToCallDialog1 (item, currentIndex) {
      console.log(currentIndex, 'currentIndex')
      // 改变对应的当前模块的index
      this.currentModelIndex = currentIndex
      console.log(item, 'item--')
      console.log(this.params, 'itemParams--')
      let arr = []
      item.ordinaryCoupon.forEach(item => {
        console.log(item, 'what is item')
        arr.push(item.id)
        console.log(arr, 'item coupon id')
      })
      this.emptySelect = arr
      this.$nextTick(() => {
        this.addCouponVisible = !this.addCouponVisible
      })
    },
    // 普通优惠券数据处理回显处理
    addCouponHandle (data) {
      console.log(data, 'coupon data')
      console.log(this.currentModelIndex, 'first--')
      this.params.awardList[this.currentModelIndex].couponIds = data.map(item => item.id)
      this.params.awardList[this.currentModelIndex].ordinaryCoupon = data

      if (this.params.awardList[this.currentModelIndex].giftType === 1) {
        this.params.awardList[this.currentModelIndex].couponList = []
        this.params.awardList[this.currentModelIndex].ordinaryCoupon.map((item, index) => {
          this.params.awardList[this.currentModelIndex].couponList.push(Object.assign({}, item, {
            actCode: item.actCode,
            denomination: item.denomination,
            id: item.id,
            randomMin: item.randomMin,
            randomMax: item.randomMax,
            couponCenterLimit: item.leastConsume,
            couponCenterNumber: item.surplus
          }))
        })
      }
    },
    // 删除普通优惠券
    deleteCouponImg (item, index, currentIndex) {
      console.log(currentIndex)
      console.log(this.params.awardList[currentIndex])
      let added = this.params.awardList[currentIndex].ordinaryCouponIdList.map(item => item.id)
      this.emptySelect = added
      this.params.awardList[currentIndex].ordinaryCoupon.splice(index, 1)
    },

    // 分裂优惠券弹窗调起
    handleToCallDialog2 (item, receiveCurrentDisCouponIndex) {
      console.log(item)
      let disCouponArr = []
      item.splitCoupon.forEach(item => {
        disCouponArr.push(item.id)
      })
      this.disCouponIdList = disCouponArr
      this.showCouponDialog2 = !this.showCouponDialog2
      this.currentModelIndex = receiveCurrentDisCouponIndex
    },
    // 分裂优惠券处理
    addDisCouponHandle (disCouponData) {
      this.params.awardList[this.currentModelIndex].couponIds = disCouponData.map(item => item.id)
      this.params.awardList[this.currentModelIndex].splitCoupon = disCouponData

      if (this.params.awardList[this.currentModelIndex].giftType === 2) {
        this.params.awardList[this.currentModelIndex].couponList = []
        this.params.awardList[this.currentModelIndex].splitCoupon.forEach((item, index) => {
          this.params.awardList[this.currentModelIndex].couponList.push(Object.assign({}, item, {
            actCode: item.actCode,
            denomination: item.denomination,
            id: item.id,
            randomMin: item.randomMin,
            randomMax: item.randomMax,
            couponCenterLimit: item.leastConsume,
            couponCenterNumber: item.surplus
          }))
        })
      }
    },
    // 删除分裂优惠券
    deleteCouponImg2 (item, index, receiveCurrentDisCouponIndex) {
      let disCoupons = this.params.awardList[receiveCurrentDisCouponIndex].splitCouponIdList.map(item => item.id)
      this.disCouponIdList = disCoupons
      this.params.awardList[receiveCurrentDisCouponIndex].splitCoupon.splice(index, 1)
    },

    // 跳转到幸运大抽奖创建页面
    create () {
      this.$router.push({
        path: '/admin/home/main/luckyDraw'
      })
    },

    // 跳转到商品管理页面
    manage () {
      this.$router.push({
        path: '/admin/home/main/luckyDraw'
      })
    },

    // 点击会员专享商品出现的添加类弹窗汇总
    hanldeToAddGoodS (index) {
      console.log('指定商品')
      this.userDialogFlag = '1'
      console.log(index)
      switch (index) {
        case 0:
          // 商品弹窗显示
          this.controlChoosingGoodsDialog = !this.controlChoosingGoodsDialog
          this.choosingGoodsDateTmpContainer = this.choosingGoodsDateFlag1
          break
        case 1:
          this.AtreeType = 1
          console.log('商家分类')
          this.isShowBusinessPlatformDialog = !this.isShowBusinessPlatformDialog
          // this.businessDialogVisible = true
          this.classFlag = 1
          this.shopAndPlatformBackDataArr = this.shopCategoryIds
          break
        case 2:
          this.AtreeType = 2
          console.log('平台分类')
          this.isShowBusinessPlatformDialog = !this.isShowBusinessPlatformDialog
          // this.businessDialogVisible = true
          this.classFlag = 2
          this.shopAndPlatformBackDataArr = this.platformCategoryIds
          break
      }
    },

    // 点击会员专享商品出现的添加类弹窗汇总
    hanldeToShowGoodS (index) {
      console.log('回显')
      this.userDialogFlag = '1'
      console.log(index)
      switch (index) {
        case 0:
          // 商品弹窗显示
          this.isOnlyShowChooseGoods = true
          this.controlChoosingGoodsDialog = !this.controlChoosingGoodsDialog
          this.choosingGoodsDateTmpContainer = this.choosingGoodsDateFlag1
          break
        case 1:
          this.AtreeType = 1
          console.log('商家分类')
          this.isShowBusinessPlatformDialog = !this.isShowBusinessPlatformDialog
          // this.businessDialogVisible = true
          this.classFlag = 1
          this.shopAndPlatformBackDataArr = this.shopCategoryIds
          break
        case 2:
          this.AtreeType = 2
          console.log('平台分类')
          this.isShowBusinessPlatformDialog = !this.isShowBusinessPlatformDialog
          // this.businessDialogVisible = true
          this.classFlag = 2
          this.shopAndPlatformBackDataArr = this.platformCategoryIds
          break
      }
    },

    // 获取会员权益选择商品弹窗的商品id
    getGoodsIdFromChoosingGoods (data) {
      console.log(data, 'get data')
      // 添加商品id
      if (this.userDialogFlag === '1') {
        this.choosingGoodsDateFlag1 = data
        this.noneBlockDiscArr[0].num = data.length
        console.log(this.noneBlockDiscArr[0].num, 'num length')
      }
    },

    // 调起链接弹窗
    chooseSelect (index) {
      this.tuneUpSelectLinkDialog = !this.tuneUpSelectLinkDialog
      console.log(this.currentModelIndex)
      console.log(index, 'current')
      this.currentModelIndex = index
    },

    // 链接数据选中回传
    handleToSelectLinkPath (link) {
      this.params.awardList[this.currentModelIndex].customLink = link
      console.log(this.params.awardList[this.currentModelIndex].customLink)
    },

    // 活动图片弹窗调起
    handleImage (index) {
      this.currentModelIndex = index
      this.tuneUpImageDialog = !this.tuneUpImageDialog
    },

    // 活动图片替换
    avatarSelectHandle (val) {
      this.params.awardList[this.currentModelIndex].customImage = val.imgPath
      console.log(val.imgPath, this.params.awardList[this.currentModelIndex], 'current awardList item')
    },

    // 商品分类和平台分类弹窗选中回传数据
    getBusinessnPlatformSortData (data) {
      console.log(data, 'bussiness data--')
      if (this.userDialogFlag === '1') {
        // 折扣
        if (this.AtreeType === 1) {
          // 商家分类
          this.shopCategoryIds = data
          this.noneBlockDiscArr[1].num = data.length
          console.log(this.noneBlockDiscArr)
        }
        if (this.AtreeType === 2) {
          // 平台分类
          this.platformCategoryIds = data
          this.noneBlockDiscArr[2].num = data.length
        }
      }
    },

    // 调起选择商品弹窗
    addGoods (index) {
      console.log(index)
      this.currentModelIndex = index
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },

    // 返回商品信息 选状态选中的商品全部信息
    addGiftDialog (res) {
      console.log(res, 'goodInfo')
      this.params.awardList[this.currentModelIndex].productId = res.prdId
      this.params.awardList[this.currentModelIndex].goodsShow = true
      this.params.awardList[this.currentModelIndex].goodsName = res.goodsName
      this.params.awardList[this.currentModelIndex].goodsImg = res.goodsImg
      this.params.awardList[this.currentModelIndex].goodsPrice = res.prdPrice
      this.params.awardList[this.currentModelIndex].goodsNumber = res.prdNumber
      console.log(this.params.awardList, this.params.awardList[this.currentModelIndex], 'console log awardiList value===')
      this.$forceUpdate()
    },

    // 点击编辑按钮跳转过来获取对应的信息
    fetchCurrentActivityData () {
      getPayRewardInfo({ id: this.params.id }).then(res => {
        console.log(res.content, 'get jump data')
        if (res.error === 0) {
          this.handleData(res.content)
          console.log(this.params, 'this.params')
          let index = 0
          // this.params = res.content
          res.content.awardContentList.forEach(awad => {
            this.addPayRewardItem()
            this.params.awardList[index].giftType = awad.giftType
            this.params.awardList[index].productId = awad.productId
            this.params.awardList[index].keepDays = awad.keepDays
            this.params.awardList[index].accountNumber = awad.accountNumber
            this.params.awardList[index].scoreNumber = awad.scoreNumber
            this.params.awardList[index].awardNumber = awad.awardNumber
            this.params.awardList[index].customImage = awad.customImage
            this.params.awardList[index].customLink = awad.customLink
            this.params.awardList[index].lotteryId = awad.lotteryId
            this.params.awardList[index].sendNum = awad.sendNum
            this.params.awardList[index].id = awad.id
            console.log(this.params.awardList[index].id, 'data--')
            console.log(awad.sendNum)

            if (awad.giftType === 1) {
              this.params.awardList[index].ordinaryCoupon = awad.couponView
              this.params.awardList[index].ordinaryCouponIdList = awad.couponIds
              this.$forceUpdate()
            }
            if (awad.giftType === 2) {
              this.params.awardList[index].splitCoupon = awad.couponView
              this.params.awardList[index].splitCouponIdList = awad.couponIds
              this.$forceUpdate()
            }
            if (awad.giftType === 5) {
              // this.params.awardList[index].productId = awad.productId
              this.params.awardList[index].goodsShow = true
              this.params.awardList[index].goodsName = awad.product.goodsName + awad.product.prdDesc
              this.params.awardList[index].goodsImg = this.$imageHost + '/' + awad.product.goodsImg
              console.log(this.params.awardList[index])
              console.log(this.params.awardList[index].goodsImg)
              this.params.awardList[index].goodsPrice = awad.product.prdPrice
              this.params.awardList[index].goodsNumber = awad.product.prdNumber
            }
            index++
          })
        }
      }).catch(err => console.log(err))
    },

    // 对获取到的信息进行处理
    handleData (data) {
      console.log(data, 'receiveData')
      this.params.activityNames = data.activityNames
      this.params.timeType = data.timeType
      this.params.actFirst = data.actFirst
      this.params.goodsAreaType = data.goodsAreaType
      this.params.goodsIds = data.goodsIds // 商品id
      this.params.goodsCatIds = data.goodsCatIds // 商家平台分类
      this.params.goodsSortIds = data.goodsSortIds // 商家商家分类
      this.params.minPayMoney = data.minPayMoney
      this.params.limitTimes = data.limitTimes
      if (data.startTime && data.endTime) {
        if (this.params.timeType === 0) {
          this.dateInterval = [data.startTime, data.endTime]
          this.params.startTime = data.startTime
          this.params.endTime = data.endTime
        }
      }
    },

    // 添加支付有礼活动接口调用
    activitySave () {
      this.$refs.payRewardForm.validate(valid => {
        console.log(valid, 'valid--')
        if (valid) {
          if (this.params.timeType === 0 && this.dateInterval) {
            this.params.startTime = this.dateInterval[0]
            this.params.endTime = this.dateInterval[1]
          }
          let obj = {
            goodsIds: this.goodsIds === null ? null : String(this.choosingGoodsDateFlag1), // 商品ID
            goodsCatIds: this.goodsCatIds === null ? null : String(this.platformCategoryIds), // 商家平台分类
            goodsSortIds: String(this.shopCategoryIds) // 商品商家分类
          }
          let requestParams = Object.assign(this.params, obj)
          console.log(this.params.id, 'this.params.id', this.requestParams, 'this.requestParams', this.params, 'this.params')
          if (this.params.id === '') {
            this.addActivity(requestParams)
          } else {
            this.updateActivity(requestParams)
          }
        } else {
          console.log('submit error')
          return false
        }
      })
    },

    // 新增支付有礼活动
    addActivity (requestParams) {
      addPayRewardAct(requestParams).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success('保存成功')
        } else {
          this.$message.warning('保存失败')
        }
      }).catch(err => console.log(err))
    },

    // 支付奖励-幸运大抽奖-下拉框选项
    getIsGonigLotteryActivity () {
      isGoingAct().then(res => {
        if (res.error === 0) {
          this.options = res.content.dataList
        }
      }).catch(err => console.log(err))
    },

    // 更新支付有礼活动接口调用
    updateActivity (requestParams) {
      requestParams.id = this.params.id
      updatePayReward(requestParams).then(res => {
        if (res.error === 0) {
          this.$message.success('更新成功')
        } else {
          this.$message.warning('更新失败')
        }
      }).catch(err => console.log(err))
    },

    // 验证奖品份数
    validateGiftNmuber (rule, value, callback, awardNumber) {
      var re = /^(0|\+?[1-9][0-9]*)$/
      if (awardNumber === '') {
        callback(new Error('请输入奖品份数'))
      } else if (!re.test(awardNumber)) {
        callback(new Error('请填写0或者正整数'))
      } else {
        callback()
      }
    },

    // 验证积分
    validateIntegral (rule, value, callback, scoreNumber) {
      var re = /^(0|\+?[1-9][0-9]*)$/
      if (scoreNumber === '') {
        callback(new Error('请输入积分'))
      } else if (!re.test(scoreNumber)) {
        callback(new Error('请填写0或者正整数'))
      } else {
        callback()
      }
    },

    // 验证自定义链接
    validateCustomLink (rule, value, callback, customLink) {
      if (!customLink) {
        callback(new Error('请选择链接'))
      } else {
        callback()
      }
    },

    // 验证幸运大抽奖
    validateLottery (rule, value, callback, lotteryId) {
      console.log(lotteryId, 'lotteryId value--')
      console.log(value, 'value')
      console.log(rule, 'rule')
      console.log(callback, 'callback')
      if (!lotteryId) {
        callback(new Error('请选择幸运大抽奖活动'))
      } else {
        callback()
      }
    },

    // 验证自定义图片
    validateImage (rule, value, callback, customImage) {
      console.log(customImage, 'customImage')
      console.log(value, 'custom value--')
      console.log(rule)
      console.log(callback)
      if (!customImage) {
        callback(new Error('请选择定义图片'))
      } else {
        callback()
      }
    },

    // 验证赠品有效期
    validateKeepDays (rule, value, callback, keepDays) {
      var re = /^(0|\+?[1-9][0-9]*)$/
      if (!keepDays) {
        callback(new Error('请输入保质日期'))
      } else if (!re.test(keepDays)) {
        callback(new Error('请输入0或者正数'))
      } else {
        callback()
      }
    },

    // 验证支付条件
    validateMoney (rule, value, callback) {
      var re = /^(?!(0[0-9]{0,}$))[0-9]{1,}[.]{0,}[0-9]{0,}$/
      if (value === '') {
        callback(new Error('请输入支付条件'))
      } else if (!re.test(value)) {
        callback(new Error('请输入正整数'))
      } else {
        callback()
      }
    },

    // 验证余额
    validateAccountNumber (rule, value, callback, accountNumber) {
      console.log(value, 'account value--')
      var re = /^(?!(0[0-9]{0,}$))[0-9]{1,}[.]{0,}[0-9]{0,}$/
      if (!accountNumber) {
        callback(new Error('请选择余额'))
      } else if (!re.test(accountNumber)) {
        callback(new Error('请输入正整数'))
      } else {
        callback()
      }
    }

  }
}
</script>

<style lang="scss" scoped>
.addPayRewardAct {
  padding: 10px;
  min-width: 100%;
  height: 100%;
  font-size: 14px;
  .addPayRewardAct_main {
    position: relative;
    display: flex;
    justify-content: center;
    background: #fff;
    padding: 40px 0 96px;
    .leftContent {
      width: 325px;
      height: 573px;
      background: #f5f5f5;
      .titles {
        height: 55px;
        img {
          width: 324px;
        }
      }
      .carousel {
        width: 324px;
        height: 55px;
      }
    }
    .rightContent {
      margin-left: 20px;
      .container {
        border: 1px solid #e5e5e5;
        background: #f8f8f8;
        width: 550px;
        padding: 0 10px;
        border-radius: 4px;
        margin-bottom: 10px;
        .title {
          height: 40px;
          line-height: 40px;
          border-bottom: 1px solid #eee;
        }

        /deep/ .el-form-item {
          margin-bottom: 16px;
        }

        .pay_rewards {
          display: flex;
          justify-content: space-between;
          height: 40px;
          line-height: 40px;
          border-bottom: 1px solid #eee;
          :nth-of-type(2) {
            display: flex;
            .addReward {
              margin-left: 10px;
              padding: 0 5px;
              height: 25px;
              line-height: 25px;
              border: 1px solid #5a8bff;
              color: #5a8bff;
              cursor: pointer;
              border-radius: 4px;
              margin-right: 10px;
              margin-top: 8px;
            }
          }
        }
        .order_form {
          .order {
            position: relative;
            border-bottom: 1px dashed #ccc;
            border-top: 1px dashed #ccc;

            font-weight: bold;
            .delIcon {
              font-size: 20px;
              position: relative;
              .el-icon-delete {
                position: absolute;
                right: 20px;
                margin-top: 10px;
              }
            }
          }

          .order_form:first-child {
            border: 1px solid red;
          }
        }
        .topOrder {
          border-top: 1px dashed #eee;
        }
        .triggerCondition {
          .noneBlock {
            margin-left: 80px;
            .noneBlockList {
              margin-bottom: 10px;
              display: flex;
              .noneBlockLeft {
                line-height: 30px;
                height: 30px;
                width: 120px;
                text-align: left;
                color: #5a8bff;
                border: 1px solid #ccc;
                background: #fff;
                cursor: pointer;
                padding-left: 5px;
                margin-right: 20px;
              }
              .noneBlockRight {
                color: #5a8bff;
                cursor: pointer;
                height: 30px;
                line-height: 30px;
              }
            }
          }
        }
        .itemOptions {
          div {
            .el-radio {
              width: 100px;
            }
          }
        }
        .coupon {
          background: #fff;
          border: 1px solid #e4e4e4;
          text-align: center;
          padding: 13px 0;
          cursor: pointer;
          float: none;
          width: 100px;
          height: 90px;
          img {
            margin-top: 10px;
          }
          p {
            color: #999;
            font-size: 12px;
            margin-top: -10px;
          }
        }
        .textTips {
          color: #999;
          margin-top: 10px;
          font-size: 12px;
          clear: both;
          display: block;
          content: "";
        }
        .addGoodsWrapper {
          width: 120px;
          height: 30px;
          line-height: 30px;
          text-align: center;
          color: #5a8bff;
          border: 1px solid #ccc;
          background: #fff;
          cursor: pointer;
        }
        // 添加表格奖品样式
        .goods_modal {
          display: block;
          margin-top: 10px;
          th {
            padding: 10px 0;
            border: 1px solid #eee;
          }
          td {
            border: 1px solid #ddd;
            background: #fff;
            padding: 8px 10px;
          }
        }
        .tips {
          margin-top: 10px;
          line-height: 1.4;
          font-size: 12px;
          color: #999;
        }
        .middleContainer {
          .addInfo {
            float: left;
            width: 100px;
            height: 101px;
            margin-bottom: 10px;
            background: #fff;
            border: 1px solid #e4e4e4;
            margin-right: 20px;
            cursor: pointer;
            text-align: center;
            img {
              margin-top: 10px;
            }
            p {
              line-height: normal;
              margin-top: -30px;
              color: #999;
            }
            .deleteIcon {
              position: relative;
              width: 17px !important;
              height: 17px;
              line-height: 17px;
              top: -118px;
              left: 45px;
              cursor: pointer;
              opacity: 0.8;
              color: #fff;
              background: #ccc;
              border: 1px solid #ccc;
              border-radius: 50%;
              text-align: center;
            }
            .couponImgWrapper {
              width: 100%;
              height: 100%;
              border: 1px solid #fbb;
              border-radius: 10px;
              .coupon_list_top {
                margin-top: 10px;
                color: #f60;
                span {
                  font-weight: bold;
                }
                :nth-of-type(2) {
                  font-size: 20px;
                  font-weight: bold;
                }
              }
              .coupon_center_limit {
                height: 20px;
                color: #f60;
                font-size: 12px !important;
              }
              .coupon_center_number {
                height: 20px;
                color: #fbb;
              }
              .coupon_list_bottom {
                height: 24px;
                line-height: 30px;
                border-bottom-left-radius: 8px;
                border-bottom-right-radius: 8px;
                color: #fff;
                background: #f66;
                background-image: url("http://mpdevimg2.weipubao.cn/image/admin/coupon_border.png");
                background-repeat: repeat-x;
              }
            }
          }
        }

        .selectLink {
          background-color: #fff;
          width: 80px;
          height: 30px;
          line-height: 30px;
          padding: 6px 12px;
          border: 1px solid #dbdbdb;
          border-radius: 4px;
        }

        .size {
          width: 70px;
          height: 70px;
          box-shadow: 0 0 0 #fff;
          color: #9a9a9a;
          border: none;
          margin-right: 10px;
        }
        .luckyDraw {
          span {
            cursor: pointer;
          }
        }
      }
    }
    .save {
      border-top: 1px solid #f2f2f2;
      display: flex;
      justify-content: center;
      align-items: center;
      position: fixed;
      bottom: 0;
      z-index: 2;
      right: 20px;
      left: 160px;
      height: 50px;
      background: #f8f8fa;
    }
  }
}
</style>
