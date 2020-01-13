<template>
  <div class="content">
    <div class="main">

      <div class="add_coupon_content">
        <div class="coupon_content">
          <div class="fl content_left">
            <div class="fl_title">
              <div>{{ $t('ordinaryCoupon.coupon') }}</div>
            </div>
            <div class="info">
              <div class="info_top">
                <div
                  class="vip_exclusive"
                  style="display:none;"
                >{{ $t('ordinaryCoupon.member') }}</div>
                <div class="coupon_name">{{param.actName ? param.actName : $t('ordinaryCoupon.couponName') }}</div>
                <div class="coupon_vou">
                  <span v-if="param.preferentialType === 2">￥{{param.randomMin && param.randomMax ? param.randomMin + '-' + param.randomMax : '0.00 - 0.00'}}</span>
                  <span v-if="param.preferentialType === 0">￥{{param.denomination ? param.denomination : '0.00'}}</span>
                  <span v-if="param.preferentialType === 1">{{param.denomination2?param.denomination2:'0'}} {{ $t('ordinaryCoupon.typeTip5') }}</span>
                </div>
                <div class="coupon_dis"></div>
              </div>
            </div>
            <div class="info_mid">
              <div class="clearfix">
                <span class="sub_title">{{ $t('ordinaryCoupon.validityType') }}</span>
                <span class="date">
                  <span v-if="param.validityType === 0 && param.couponDate === ''">xxxx-xx-xx xx:xx:xx-xxxx-xx-xx xx:xx:xx</span>
                  <span v-if="param.validityType === 0 && param.couponDate !== ''">{{param.couponDate[0]}} - {{param.couponDate[1]}}</span>
                  <span v-if="param.validityType === 1">{{ $t('ordinaryCoupon.appoint') }} {{param.validity}}<span v-if="param.validity === ''">X</span> {{ $t('ordinaryCoupon.appointDay') }} {{param.validityHour}}<span v-if="param.validityHour === ''">X</span> {{ $t('ordinaryCoupon.appointHour') }} {{param.validityMinute}}<span v-if="param.validityMinute === ''">X</span> {{ $t('ordinaryCoupon.appointMinute') }}</span>
                </span>
              </div>
              <div>
                <span class="sub_title">{{ $t('ordinaryCoupon.restrict2') }}</span>
                <span
                  class="all"
                  v-if="param.useConsumeRestrict===0"
                >{{ $t('ordinaryCoupon.restrictRadio1') }}</span>
                <span v-else>{{ $t('ordinaryCoupon.restrictRadio2') }} {{param.leastConsume?param.leastConsume:'0'}}<span v-if="param.leastConsume === ''">X</span> {{ $t('ordinaryCoupon.restrictTip') }}</span>
                <span
                  class="part"
                  v-if="param.availableGoods === 1"
                >{{ $t('ordinaryCoupon.leftTip1') }}</span>
              </div>
            </div>
            <div class="info_bot">
              <div
                class="code"
                v-if="param.validationCode != ''"
              >{{ $t('ordinaryCoupon.leftTip2') }}</div>
              <div
                class="use"
                v-if="this.param.type==0"
              >{{ $t('ordinaryCoupon.leftTip3') }}</div>
              <div
                class="use"
                v-if="this.param.type==1"
              >{{ $t('ordinaryCoupon.leftTip4') }}</div>
              <div>
                <span class="sub_title">{{ $t('ordinaryCoupon.useExplain') }}</span>
                <div
                  class="instruction"
                  v-html="crlfFormat ? crlfFormat : $t('ordinaryCoupon.useExplainTip2')"
                ></div>
              </div>
            </div>
          </div>
          <div class="content_right">
            <el-form
              :rules="paramRules"
              ref="param"
              :model="param"
              label-width="180px"
              style="margin-top: 20px;"
            >
              <div class="coupon_info">
                <div class="coupon_info_title">{{ $t('ordinaryCoupon.couponType') }}</div>
                <el-form-item
                  :label="$t('ordinaryCoupon.couponType') + '：'"
                  prop="type"
                >
                  <div>
                    <el-radio
                      v-model="param.type"
                      :label=0
                      :disabled="editType"
                    >{{ $t('ordinaryCoupon.generalCoupons') }}</el-radio>
                    <el-radio
                      v-model="param.type"
                      :label=1
                      :disabled="editType"
                    >{{ $t('ordinaryCoupon.splitCoupon') }} <el-tooltip
                        effect="dark"
                        :content="$t('ordinaryCoupon.splitTip')"
                        placement="top"
                      >
                        <i class="el-icon-warning-outline"></i>
                      </el-tooltip>
                    </el-radio>
                  </div>
                </el-form-item>
              </div>

              <div class="coupon_info">
                <div
                  class="coupon_info_title"
                  v-if="param.type===0"
                >{{ $t('ordinaryCoupon.couponInfo') }}</div>
                <div
                  class="coupon_info_title"
                  v-if="param.type===1"
                >{{ $t('ordinaryCoupon.splitInfo') }} <span style="color: #999;margin-left: 15px;">{{ $t('ordinaryCoupon.splitInfoTip') }}</span></div>

                <el-form-item
                  :label="$t('ordinaryCoupon.couponName') + '：'"
                  prop="actName"
                >
                  <el-input
                    size="small"
                    class="coupon_name_input"
                    :placeholder="$t('ordinaryCoupon.nameTip')"
                    v-model="param.actName"
                    clearable
                    :disabled="editType"
                  ></el-input>
                </el-form-item>
                <el-form-item
                  :label="$t('ordinaryCoupon.validityType') + '：'"
                  prop="validityType"
                >
                  <el-radio
                    v-model="param.validityType"
                    :label='0'
                    :disabled="editType"
                    @change="validityTypeChange"
                  >{{ $t('ordinaryCoupon.fixedDate') }}</el-radio>
                  <el-date-picker
                    v-model="param.couponDate"
                    :disabled="param.validityType === 0 && !editType ? false : true"
                    type="datetimerange"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    format="yyyy-MM-dd HH:mm:ss"
                    :range-separator="$t('seckill.to')"
                    :start-placeholder="$t('ordinaryCoupon.startTime')"
                    :end-placeholder="$t('ordinaryCoupon.endTime')"
                    :default-time="['00:00:00','23:59:59']"
                    size="small"
                  >
                  </el-date-picker>
                </el-form-item>
                <el-form-item prop="validityType1">
                  <el-radio
                    v-model="param.validityType"
                    :label='1'
                    :disabled="editType"
                    @change="validityTypeChange"
                    style="margin-right: 25px;"
                  >{{ $t('ordinaryCoupon.appoint') }}</el-radio>
                  <span>
                    <el-input
                      :disabled="param.validityType===1 && !editType ?false:true"
                      v-model="param.validity"
                      size="small"
                      class="small_input"
                    ></el-input> {{ $t('ordinaryCoupon.appointDay') }}
                    <el-input
                      :disabled="param.validityType===1 && !editType ?false:true"
                      v-model="param.validityHour"
                      size="small"
                      class="small_input"
                    ></el-input> {{ $t('ordinaryCoupon.appointHour') }}
                    <el-input
                      :disabled="param.validityType===1 && !editType ?false:true"
                      v-model="param.validityMinute"
                      size="small"
                      class="small_input"
                    ></el-input> {{ $t('ordinaryCoupon.appointMinute') }}
                  </span>
                </el-form-item>

                <el-form-item
                  :label="$t('ordinaryCoupon.surplus') + '：'"
                  prop="limitSurplusFlag"
                >
                  <div>
                    <div>
                      <el-radio
                        v-model="param.limitSurplusFlag"
                        :label='0'
                        :disabled="editType"
                        @change="limitSurplusFlagChange"
                      >{{ $t('ordinaryCoupon.surplusRadio1') }}</el-radio>
                      <span>
                        <el-input
                          :disabled="param.limitSurplusFlag===0 && !editType ?false:true"
                          v-model="param.totalAmount"
                          size="small"
                          class="small_input"
                        ></el-input> {{ $t('ordinaryCoupon.surplusTip1') }}
                        <span style="color: #999;">{{ $t('ordinaryCoupon.surplusTip2') }}</span>
                      </span>
                    </div>
                    <div>
                      <el-radio
                        v-model="param.limitSurplusFlag"
                        :label='1'
                        :disabled="editType"
                        @change="limitSurplusFlagChange"
                      >{{ $t('ordinaryCoupon.surplusRadio2') }}</el-radio>
                    </div>

                  </div>
                </el-form-item>
                <el-form-item
                  :label="$t('ordinaryCoupon.preferentialType') + '：'"
                  prop="preferentialType"
                >
                  <div>
                    <p v-if="param.type==1">
                      <el-radio
                        v-model="param.preferentialType"
                        :label='2'
                        v-if="param.type==1"
                        :disabled="editType"
                        @change="preferentialTypeChange"
                      >{{ $t('ordinaryCoupon.typeRadio1') }}</el-radio>
                      <el-input
                        :disabled="param.preferentialType==2 && !editType ?false:true"
                        v-model="param.randomMin"
                        size="small"
                        class="small_input"
                      ></el-input>
                      {{ $t('ordinaryCoupon.typeTip1') }}
                      <el-input
                        :disabled="param.preferentialType==2 && !editType  ?false:true"
                        v-model="param.randomMax"
                        size="small"
                        class="small_input"
                      ></el-input>
                      <span style="color: #999;display: block;">{{ $t('ordinaryCoupon.typeTip2') }}</span>
                    </p>

                    <p>
                      <el-radio
                        v-model="param.preferentialType"
                        :label=0
                        :disabled="editType"
                        @change="preferentialTypeChange"
                      >{{ $t('ordinaryCoupon.typeRadio2') }}</el-radio>
                      <span>
                        {{ $t('ordinaryCoupon.typeTip3') }}
                        <el-input
                          :disabled="param.preferentialType==0 && !editType ?false:true"
                          v-model="param.denomination"
                          size="small"
                          class="small_input"
                        ></el-input> {{ $t('ordinaryCoupon.typeTip4') }}
                      </span>
                    </p>

                    <p>
                      <el-radio
                        v-model="param.preferentialType"
                        :label=1
                        :disabled="editType"
                        @change="preferentialTypeChange"
                      >{{ $t('ordinaryCoupon.typeRadio3') }}</el-radio>
                      <el-input
                        :disabled="param.preferentialType==1 && !editType ?false:true"
                        v-model="param.denomination2"
                        size="small"
                        class="small_input"
                      ></el-input> {{ $t('ordinaryCoupon.typeTip5') }}
                    </p>

                  </div>
                </el-form-item>
                <el-form-item
                  :label="$t('ordinaryCoupon.isRandom') + '：'"
                  prop="useScore"
                  v-if="param.type==0"
                  :style="{height: param.useScore === 1 ? '100px' : ''}"
                >
                  <div>
                    <el-radio
                      v-model="param.useScore"
                      :label='0'
                      :disabled="editType"
                      @change="useScoreChange"
                    >{{ $t('ordinaryCoupon.randomRadio1') }}</el-radio>
                    <el-radio
                      v-model="param.useScore"
                      :label='1'
                      :disabled="editType"
                      @change="useScoreChange"
                    >{{ $t('ordinaryCoupon.randomRadio2') }}</el-radio>
                    <p v-if="param.useScore== 1">
                      <el-input
                        v-model="param.scoreNumber"
                        class="small_input"
                        size="small"
                        :disabled="editType"
                      ></el-input>
                      {{ $t('ordinaryCoupon.randomTip') }}
                    </p>
                  </div>
                </el-form-item>
              </div>

              <div class="coupon_info">
                <div class="coupon_info_title">{{ $t('ordinaryCoupon.couponRule') }}</div>

                <el-form-item
                  :label="$t('ordinaryCoupon.receivePerPerson') + '：'"
                  prop="receivePerPerson"
                  v-if="param.type===0"
                >
                  <div class="ft">
                    <el-select
                      v-model="param.receivePerPerson"
                      size="small"
                      class="coupon_name_input"
                    >
                      <el-option
                        v-for="(item, index) in getLimit"
                        :key="index"
                        :value="item.value"
                        :label="item.label"
                      ></el-option>
                    </el-select>
                  </div>
                </el-form-item>
                <el-form-item
                  :label="$t('ordinaryCoupon.member') + '：'"
                  v-if="param.type===0"
                  prop="isExclusive"
                >
                  <div>
                    <p>
                      <el-checkbox
                        v-model="param.isExclusive"
                        :label="$t('ordinaryCoupon.memberTip1')"
                        :disabled="editType"
                      ></el-checkbox>
                    </p>
                    <div v-if="param.isExclusive">
                      <el-select
                        v-model="param.cardId"
                        :label="$t('ordinaryCoupon.memberTip2')"
                        multiple
                        size="small"
                        :disabled="editType"
                        class="coupon_name_input"
                      >
                        <el-option
                          v-for="item in cardList"
                          :key="item.id"
                          :label="item.cardName"
                          :value="item.id"
                        ></el-option>
                      </el-select>
                      <span class="card_links">
                        <span
                          class="member"
                          @click="editType ? handler() : refresh()"
                        >{{ $t('ordinaryCoupon.memberTip3') }}</span><span> | </span>
                        <span
                          class="member"
                          @click="editType ? handler() : addMemberCard()"
                        >{{ $t('ordinaryCoupon.memberTip4') }}</span><span> | </span>
                        <span
                          class="member"
                          @click="editType ? handler() : manageMemberCard()"
                        >{{ $t('ordinaryCoupon.memberTip5') }}</span>
                      </span>
                    </div>
                  </div>
                </el-form-item>
                <el-form-item
                  :label="$t('ordinaryCoupon.validationCode') + '：'"
                  v-if="param.type===0"
                >
                  <el-input
                    v-model="param.validationCode"
                    size="small"
                    :disabled="editType"
                    class="coupon_name_input"
                  ></el-input>
                </el-form-item>
                <el-form-item
                  :label="$t('ordinaryCoupon.couponNum') + '：'"
                  prop="couponNum"
                  v-if="param.type===1"
                >
                  <div>
                    <el-radio
                      v-model="param.couponNum"
                      :label="0"
                      :disabled="editType"
                    >{{ $t('ordinaryCoupon.numRadio1') }}</el-radio>
                    <el-radio
                      v-model="param.couponNum"
                      :label="1"
                      :disabled="editType"
                    >{{ $t('ordinaryCoupon.numRadio2') }}</el-radio>
                    <el-input
                      :disabled="param.couponNum==1 && !editType ?false:true"
                      v-model="param.receiveNum"
                      size="small"
                      class="small_input"
                      style="margin-left: -30px;"
                    ></el-input>{{ $t('ordinaryCoupon.numTip') }}
                  </div>
                </el-form-item>
                <el-form-item :label="$t('ordinaryCoupon.enabled') + '：'">
                  <div style="display:flex">
                    <div>
                      <el-radio
                        v-model="param.suitGoods"
                        :label="1"
                        :disabled="editType"
                      >{{ $t('ordinaryCoupon.enabledRadio1') }}</el-radio>
                      <el-radio
                        v-model="param.suitGoods"
                        :label="0"
                        :disabled="editType"
                      >{{ $t('ordinaryCoupon.enabledRadio2') }}</el-radio>
                    </div>
                    <span style="flex:1;padding-left:15px;color:red;">
                      {{ $t('ordinaryCoupon.enabledTip') }}
                    </span>
                  </div>
                </el-form-item>
                <el-form-item
                  :label="$t('ordinaryCoupon.restrict') + '：'"
                  prop="useConsumeRestrict"
                >
                  <div>
                    <p>
                      <el-radio
                        v-model="param.useConsumeRestrict"
                        :label='0'
                        :disabled="editType"
                        @change="useConsumeRestrictChange"
                      >{{ $t('ordinaryCoupon.restrictRadio1') }}</el-radio>
                    </p>
                    <p>
                      <el-radio
                        v-model="param.useConsumeRestrict"
                        :label='1'
                        :disabled="editType"
                        @change="useConsumeRestrictChange"
                      >{{ $t('ordinaryCoupon.restrictRadio2') }}&nbsp;<el-input
                          :disabled="param.useConsumeRestrict === 1 && !editType ? false : true"
                          size="small"
                          v-model.number="param.leastConsume"
                          class="small_input"
                        ></el-input>
                        {{ $t('ordinaryCoupon.restrictTip') }}
                      </el-radio>
                    </p>
                  </div>
                </el-form-item>
                <el-form-item
                  :label="$t('ordinaryCoupon.suitGoods') + '：'"
                  prop="availableGoods"
                  :style="{height:param.availableGoods === 1 ? '240px':''}"
                >
                  <div>
                    <p>
                      <el-radio
                        v-model="param.availableGoods"
                        :label='0'
                        :disabled="editType"
                        @change="availableGoodsChange"
                      >{{ $t('ordinaryCoupon.suitGoodsRadio1') }}</el-radio>
                    </p>
                    <p>
                      <el-radio
                        v-model="param.availableGoods"
                        :label='1'
                        :disabled="editType"
                        @change="availableGoodsChange"
                      >{{ $t('ordinaryCoupon.suitGoodsRadio2') }}</el-radio>
                    </p>
                    <div v-if="param.availableGoods === 1">
                      <div
                        class="noneBlockList"
                        v-for="(item,index) in noneBlockDiscArr"
                        :key="index"
                      >
                        <!-- :disabled="editType" -->
                        <el-button
                          size="small"
                          plain
                          @click="hanldeToAddGoodS(index)"
                          style="margin-right: 20px;"
                        >
                          <i class="el-icon-plus"></i> {{item.name}}
                        </el-button>
                        <div v-if="index === 0">{{ $t('ordinaryCoupon.suitGoodsTip1') }} {{ goodsInfo.length > 0 ? goodsInfo.length : 0 }} {{ $t('ordinaryCoupon.suitGoodsTip2') }}</div>
                        <div v-if="index === 1">{{ $t('ordinaryCoupon.suitGoodsTip1') }} {{ busClass.length > 0 ? busClass.length : 0 }} {{ $t('ordinaryCoupon.suitGoodsTip3') }}</div>
                        <div v-if="index === 2">{{ $t('ordinaryCoupon.suitGoodsTip1') }} {{ platClass.length > 0 ? platClass.length : 0 }} {{ $t('ordinaryCoupon.suitGoodsTip4') }}</div>
                      </div>
                    </div>
                  </div>
                </el-form-item>
                <el-form-item :label="$t('ordinaryCoupon.useExplain') + '：'">
                  <el-input
                    type="textarea"
                    :rows="5"
                    v-model="param.useExplain"
                    :placeholder="$t('ordinaryCoupon.useExplainTip')"
                    resize="none"
                  ></el-input>
                </el-form-item>
              </div>
            </el-form>

          </div>
        </div>
      </div>
    </div>
    <div class="footer">
      <el-button
        type="primary"
        size="small"
        @click="saveCoupon()"
      >{{ $t('ordinaryCoupon.save') }}</el-button>
    </div>
    <!--选择商品弹窗-->
    <ChoosingGoods
      :tuneUpChooseGoods="tuneUpChooseGoods"
      @resultGoodsDatas="choosingGoodsResult"
      :chooseGoodsBack="goodsInfo"
    />
    <!-- 选择 1商家分类;2平台分类弹窗 -->
    <AddingBusClassDialog
      :dialogVisible.sync="tuneUpBusClassDialog"
      :classFlag="classFlag"
      @BusClassTrueDetailData="busClassDialogResult"
      @backDataArr="commInfo"
    />
  </div>
</template>
<script>
import { saveCoupon, updateCoupon, updateSaveCoupon } from '@/api/admin/marketManage/couponList.js'
import { allCardApi } from '@/api/admin/marketManage/messagePush'
export default {
  components: {
    ChoosingGoods: () => import('@/components/admin/choosingGoods'),
    AddingBusClassDialog: () => import('@/components/admin/addingBusClassDialog')
  },
  data () {
    // 自定义校验有效期
    var validateTime = (rule, value, callback) => {
      if (value === 0 && this.param.couponDate === '') {
        callback(new Error(this.$t('ordinaryCoupon.validateTime1')))
      } else {
        callback()
      }
    }
    var validateTime1 = (rule, value, callback) => {
      var re = /^(0|\+?[1-9][0-9]*)$/
      if (this.param.validityType === 1 && (this.param.validity === '' || this.param.validityHour === '' || this.param.validityMinute === '')) {
        callback(new Error(this.$t('ordinaryCoupon.validateTime2')))
      } else if (this.param.validityType === 1 && (!re.test(this.param.validity) || !re.test(this.param.validityHour) || !re.test(this.param.validityMinute))) {
        callback(new Error(this.$t('ordinaryCoupon.validateNum')))
      } else {
        callback()
      }
    }
    // 自定义校验初始库存
    var validateSurplus = (rule, value, callback) => {
      var re = /^(\+?[1-9][0-9]*)$/
      if (value === 0 && this.param.totalAmount === null) {
        callback(new Error(this.$t('ordinaryCoupon.validateSurplus')))
      } else if (value === 0 && !re.test(this.param.totalAmount)) {
        callback(new Error(this.$t('ordinaryCoupon.validateNum1')))
      } else {
        callback()
      }
    }
    // 自定义校验优惠类型
    var validatePreferentialType = (rule, value, callback) => {
      // 金额 (正数,保留两位小数)
      var re = /^[1-9]\d*(\.\d{1,2})?$/
      var re1 = /^0\.\d{1,2}$/
      var re2 = /^((0\.[1-9]{1})|(([1-9]{1})(\.\d{1})?))$/
      if (value === 0 && this.param.denomination === null) {
        callback(new Error(this.$t('ordinaryCoupon.validatePreferentialType1')))
      } else if (value === 0 && !re.test(this.param.denomination) && !re1.test(this.param.denomination)) {
        callback(new Error(this.$t('ordinaryCoupon.validateNum')))
      } else if (value === 1 && this.param.denomination2 === null) {
        callback(new Error(this.$t('ordinaryCoupon.validatePreferentialType2')))
      } else if (value === 1 && !re2.test(this.param.denomination2)) {
        callback(new Error(this.$t('ordinaryCoupon.validateDiscount')))
      } else if (value === 2 && (this.param.randomMin === null || this.param.randomMax === null)) {
        callback(new Error('请填写随机金额'))
      } else if (value === 2 && ((!re.test(this.param.randomMin) && !re1.test(this.param.randomMin)) || (!re.test(this.param.randomMax) && !re1.test(this.param.randomMax)))) {
        callback(new Error(this.$t('ordinaryCoupon.validateNum')))
      } else if (this.param.randomMin > this.param.randomMax) {
        callback(new Error('最大金额不能比最小金额小'))
      } else {
        callback()
      }
    }
    // 自定义校验积分兑换
    var validateisRandom = (rule, value, callback) => {
      var re = /^(0|\+?[1-9][0-9]*)$/
      if (value === 1 && this.param.scoreNumber === null) {
        callback(new Error(this.$t('ordinaryCoupon.validateisRandom')))
      } else if (value === 1 && !re.test(this.param.scoreNumber)) {
        callback(new Error(this.$t('ordinaryCoupon.validateNum')))
      } else {
        callback()
      }
    }
    // 自定义领券人数
    var validateCouponNum = (rule, value, callback) => {
      var re = /^(0|\+?[1-9][0-9]*)$/
      if (value === 1 && this.param.receiveNum === null) {
        callback(new Error('请填写领券人数'))
      } else if (value === 1 && !re.test(this.param.receiveNum)) {
        callback(new Error(this.$t('ordinaryCoupon.validateNum')))
      } else {
        callback()
      }
    }
    // 自定义会员专享
    var validateIsExclusive = (rule, value, callback) => {
      if (value === true && this.param.cardId.length === 0) {
        callback(new Error('请选择会员卡'))
      } else {
        callback()
      }
    }
    // 自定义校验使用门槛
    var validateuseConsumeRestrict = (rule, value, callback) => {
      var re = /^[1-9]\d*(\.\d{1,2})?$/
      var re1 = /^0\.\d{1,2}$/
      if (value === 1 && this.param.leastConsume === null) {
        callback(new Error(this.$t('ordinaryCoupon.validateuseConsumeRestrict')))
      } else if (value === 1 && !re.test(this.param.leastConsume) && !re1.test(this.param.leastConsume)) {
        callback(new Error(this.$t('ordinaryCoupon.validateNum')))
      } else {
        callback()
      }
    }
    // 自定义校验可使用商品
    var validateAvailableGoods = (rule, value, callback) => {
      if (value === 1 && (this.goodsInfo.length === 0 && this.busClass.length === 0 && this.platClass.length === 0)) {
        callback(new Error(this.$t('ordinaryCoupon.validatesuitGoods1')))
      } else {
        callback()
      }
    }

    return {
      editType: false, // 编辑保存状态
      couponInfo: {},
      id: '',
      example: false,
      param: {
        type: 0, // 优惠券类型
        actCode: '',
        actName: '', // 优惠券名
        // aliasCode: '', // 唯一活动代码
        preferentialType: 0,
        useConsumeRestrict: 0, // 使用门槛
        useScore: 0, // 是否需要积分
        receivePerPerson: 0, // 每人限领
        cardId: '',
        validityType: 0, // 有效期
        couponDate: '', // 总时间
        startTime: '', // 生效时间
        endTime: '', // 到期时间
        validity: '',
        validityHour: '',
        validityMinute: '',
        limitSurplusFlag: 1, // 初始库存
        totalAmount: null, // num发行量
        validationCode: '',
        recommendGoodsId: '', // 指定商品
        recommendCatId: '', // 指定平台分类
        recommendSortId: '', // 指定商家分类
        randomMin: null, // 随机金额1
        randomMax: null, // 随机金额2
        couponNum: 0, // 领券人数
        receiveNum: null, // 人数
        // enabled: 1, // 隐藏
        suitGoods: 0, // 隐藏
        availableGoods: 0, // 适用商品
        useExplain: '',
        denomination: null, // num面额
        denomination2: null,
        leastConsume: null,
        scoreNumber: null, // num积分数
        AtreeType: null,
        isExclusive: false
      },
      paramRules: {
        type: { required: true, message: this.$t('ordinaryCoupon.validateType'), trigger: 'change' },
        actName: [
          { required: true, message: this.$t('ordinaryCoupon.validateactName1'), trigger: 'blur' },
          { max: 10, message: this.$t('ordinaryCoupon.validateactName2'), trigger: 'blur' }
        ],
        validityType: { required: true, validator: validateTime, trigger: 'change' },
        validityType1: { validator: validateTime1, trigger: 'change' },
        limitSurplusFlag: { required: true, validator: validateSurplus, trigger: 'change' },
        preferentialType: { required: true, validator: validatePreferentialType, trigger: 'change' },
        useScore: { required: true, validator: validateisRandom, trigger: 'change' },
        receivePerPerson: { required: true, message: this.$t('ordinaryCoupon.validatereceivePerPerson'), trigger: 'change' },
        couponNum: { required: true, validator: validateCouponNum, trigger: 'change' },
        isExclusive: { validator: validateIsExclusive, trigger: 'change' },
        useConsumeRestrict: { required: true, validator: validateuseConsumeRestrict, trigger: 'change' },
        availableGoods: { required: true, validator: validateAvailableGoods, trigger: 'change' }
      },
      couponId: '',
      AtreeType: null,
      activeName: 'second',
      currentPage: 1,
      getLimit: [
        {
          label: '不限制',
          value: 0
        }, {
          label: '1',
          value: 1
        }, {
          label: '2',
          value: 2
        }, {
          label: '3',
          value: 3
        }, {
          label: '4',
          value: 4
        }, {
          label: '5',
          value: 5
        }, {
          label: '10',
          value: 10
        }, {
          label: '20',
          value: 20
        }],

      cardList: [], // 会员卡列表

      noneBlockDiscArr: [], // 指定商品
      tuneUpChooseGoods: false, // 商品弹窗
      tuneUpBusClassDialog: false, // 商家/平台弹窗
      classFlag: 0, // 商家/平台类型
      // 弹窗结果区分标识 1商家分类;2平台分类
      flag: 0,
      // 商品弹窗回调数据
      goodsInfo: [],
      goodsInfoRow: [],
      // 商家分类弹窗回调数据
      busClass: [],
      busClassRow: [],
      // 平台分类弹窗回调数据
      platClass: [],
      platClassRow: [],
      // 平台分类/商家分类共享变量
      commInfo: []
    }
  },
  mounted () {
    this.couponId = this.$route.query.id

    this.dataDefalut()
    this.getCardList()
    if (this.couponId) {
      this.editType = true
      this.getOneInfo()
    }
    this.noneBlockDiscArr = this.$t('ordinaryCoupon.noneBlockDiscArr')
  },
  methods: {
    dataDefalut () {
      this.$http.$on('result', res => {
        this.param.recommendGoodsId = res.join()
        this.noneBlockDiscArr[0].num = res.length
      })
      this.$http.$on('BusClassTrueArr', res => {
        if (this.AtreeType === 1) {
          this.param.recommendSortId = res.join()
          this.noneBlockDiscArr[1].num = res.length
        } else {
          this.param.recommendCatId = res.join()
          this.noneBlockDiscArr[2].num = res.length
        }
      })
    },

    getCardList () {
      // 会员卡数据
      allCardApi().then((res) => {
        if (res.error === 0) {
          this.cardList = res.content
        }
      })
    },

    // 编辑回显
    getOneInfo () {
      updateCoupon(this.couponId).then(res => {
        if (res.error === 0) {
          console.log(res.content[0])
          var data = res.content[0]
          this.id = data.id
          this.param.type = data.type
          this.param.actCode = data.actCode
          this.param.actName = data.actName
          this.param.aliasCode = data.aliasCode
          // 有效期
          this.param.validityType = data.validityType
          this.param.couponDate = [data.startTime, data.endTime]
          this.param.startTime = data.startTime
          this.param.endTime = data.endTime
          this.param.validity = data.validity
          this.param.validityHour = data.validityHour
          this.param.validityMinute = data.validityMinute
          // 初始库存
          this.param.totalAmount = data.totalAmount
          this.param.limitSurplusFlag = data.limitSurplusFlag
          // 优惠类型
          this.param.actCode = data.actCode
          if (this.param.actCode === 'voucher') {
            // 面额
            this.param.preferentialType = 0
            this.param.denomination = data.denomination
            this.param.denomination2 = null
          } else if (this.param.actCode === 'discount') {
            // 折扣
            this.param.preferentialType = 1
            this.param.denomination2 = data.denomination
            this.param.denomination = null
          } else if (this.param.actCode === 'random') {
            // 随机金额
            this.param.preferentialType = 2
          }
          this.param.randomMin = data.randomMin
          this.param.randomMax = data.randomMax
          // 积分兑换
          this.param.scoreNumber = data.scoreNumber
          if (this.param.scoreNumber > 0) {
            this.param.useScore = 1
          } else {
            this.param.useScore = 0
          }
          // 每人限领
          this.param.receivePerPerson = data.receivePerPerson
          // 领券人数
          this.param.receiveNum = data.receiveNum
          if (this.param.receiveNum === 0) {
            this.param.couponNum = 0
          } else {
            this.param.couponNum = 1
          }
          // 会员专享
          this.param.cardId = data.cardId
          if (this.param.cardId === '') {
            this.param.isExclusive = false
          } else {
            this.param.isExclusive = true
            this.param.cardId = this.param.cardId.split(',')
            this.param.cardId = this.param.cardId.map(Number)
          }
          // 领取码
          this.param.validationCode = data.validationCode
          // 是否隐藏
          // this.param.enabled = data.enabled
          this.param.suitGoods = data.suitGoods
          // 使用门槛
          this.param.leastConsume = data.leastConsume
          if (this.param.leastConsume === 0) {
            this.param.useConsumeRestrict = 0
          } else {
            this.param.useConsumeRestrict = 1
          }
          // 可使用商品
          if (data.recommendGoodsId !== '' || data.recommendCatId !== '' || data.recommendSortId !== '') {
            // 指定商品
            this.param.availableGoods = 1
            this.goodsInfo = data.recommendGoodsId.split(',')
            this.goodsInfo = this.goodsInfo.map(Number)

            this.busClass = data.recommendCatId.split(',')
            this.busClass = this.busClass.map(Number)

            this.platClass = data.recommendSortId.split(',')
            this.platClass = this.platClass.map(Number)
          } else {
            // 全部商品
            this.param.availableGoods = 0
          }

          // 说明
          this.param.useExplain = data.useExplain
        }
      })
    },

    // 保存优惠券
    saveCoupon () {
      this.$refs['param'].validate((valid) => {
        if (valid) {
          // 面额/折
          if (this.param.preferentialType === 1) {
            this.param.actCode = 'discount'
            this.param.denomination = this.param.denomination2
          } else if (this.param.preferentialType === 0) {
            this.param.actCode = 'voucher'
            this.param.denomination = this.param.denomination
          } else if (this.param.preferentialType === 2) {
            this.param.actCode = 'random'
          }
          // 领券人数
          if (this.param.couponNum === 0) {
            this.param.receiveNum = 0
          }
          // 发放的总数量
          if (this.param.limitSurplusFlag === 1) {
            this.param.totalAmount = 0
          } else {
            // this.param.limitSurplusFlag = this.param.totalAmount
          }
          // 使用门槛
          if (this.param.useConsumeRestrict === 0) {
            this.param.leastConsume = 0
          }

          // 可使用商品
          if (this.param.availableGoods === 0) {
            this.param.recommendGoodsId = ''
            this.param.recommendCatId = ''
            this.param.recommendSortId = ''
          } else {
            this.param.recommendGoodsId = this.goodsInfo.toString()
            this.param.recommendCatId = this.busClass.toString()
            this.param.recommendSortId = this.platClass.toString()
          }

          this.param.scoreNumber = Number(this.param.scoreNumber)

          // 有效期
          if (this.param.validityType === 0) {
            this.param.startTime = this.param.couponDate[0]
            this.param.endTime = this.param.couponDate[1]
          } else {
            this.param.startTime = ''
            this.param.endTime = ''
          }

          // 会员专享
          if (this.param.isExclusive === true) {
            this.param.cardId = this.param.cardId.toString()
          } else {
            this.param.cardId = ''
          }

          if (this.editType === false) {
            // 添加保存
            saveCoupon(this.param).then((res) => {
              if (res.error === 0) {
                this.$message.success({ message: this.$t('ordinaryCoupon.addSuccess') })
                this.$router.push({ 'name': 'ordinary_coupon' })
              } else {
                this.$message.warning({ message: this.$t('ordinaryCoupon.addDefault') })
              }
            })
          } else {
            // 编辑保存
            var obj = this.param
            obj.id = this.id
            updateSaveCoupon(obj).then((res) => {
              if (res.error === 0) {
                this.$message.success({ message: this.$t('ordinaryCoupon.editSuccess') })
                this.$router.push({ 'name': 'ordinary_coupon' })
              } else {
                this.$message.warning({ message: this.$t('ordinaryCoupon.editDefault') })
              }
            })
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },

    // 点击指定商品出现的添加类弹窗汇总
    hanldeToAddGoodS (index) {
      console.log(index)
      switch (index) {
        case 0:
          this.tuneUpChooseGoods = !this.tuneUpChooseGoods
          break
        case 1:
          this.tuneUpBusClassDialog = true
          this.classFlag = 1
          this.flag = 1
          this.commInfo = this.busClass
          break
        case 2:
          this.tuneUpBusClassDialog = true
          this.classFlag = 2
          this.flag = 2
          this.commInfo = this.platClass
          break
      }
    },

    // 选择商品弹窗回调显示
    choosingGoodsResult (row) {
      this.goodsInfoRow = row
      this.goodsInfo = []
      this.goodsInfoRow.map((item, index) => {
        this.goodsInfo.push(item.goodsId)
      })
    },

    // 选择商家分类/平台分类弹窗回调显示
    busClassDialogResult (row) {
      if (this.flag === 1) {
        // 商家分类
        this.busClassRow = row
        this.busClass = []
        this.busClassRow.map((item, index) => {
          this.busClass.push(item.sortId)
        })
      } else {
        // 平台分类
        this.platClassRow = row
        this.platClass = []
        this.platClassRow.map((item, index) => {
          this.platClass.push(item.catId)
        })
      }
    },

    // 刷新
    refresh () {
      this.getCardList()
      this.$nextTick(() => {
        this.$message.success('刷新成功')
      })
    },

    handler () {

    },

    addMemberCard () {
      window.open('/admin/home/main/normalCardDetail')
    },

    manageMemberCard () {
      window.open('/admin/home/main/user_card')
    },

    // 切换触发校验
    validityTypeChange (value) {
      this.$refs['param'].validateField('validityType')
      this.$refs['param'].validateField('validityType1')
    },
    limitSurplusFlagChange (e) {
      this.$refs['param'].validateField('limitSurplusFlag')
    },
    preferentialTypeChange (e) {
      this.$refs['param'].validateField('preferentialType')
    },
    useScoreChange (e) {
      this.$refs['param'].validateField('useScore')
    },
    useConsumeRestrictChange (e) {
      this.$refs['param'].validateField('useConsumeRestrict')
    },
    availableGoodsChange (e) {
      if (e === 0) {
        this.goodsInfo = []
        this.busClass = []
        this.platClass = []
      }
      this.$refs['param'].validateField('availableGoods')
    }
  },
  computed: {
    coupon_date_info () {
      if (this.param.validity || this.param.validityHour || this.param.validityMinute) {
        return `领券日开始${this.param.validity ? this.param.validity + '天' : ''}${this.param.validityHour ? this.param.validityHour + '时' : ''}${this.param.validityMinute ? this.param.validityMinute + '分' : ''}内有效`
      } else {
        return `领券日开始X天X时X分内有效`
      }
    },
    coupon_date_datetimerange () {
      if (this.param.couponDate) {
        let dateStr = ''
        this.param.couponDate.map(item => {
          let date = new Date(item)
          let year = date.getFullYear()
          let month = (date.getMonth() + 1).toString().padStart(2, '0')
          let day = date.getDate().toString().padStart(2, '0')
          let hour = date.getHours().toString().padStart(2, '0')
          let minute = date.getMinutes().toString().padStart(2, '0')
          let second = date.getSeconds().toString().padStart(2, '0')
          dateStr += `${year}-${month}-${day} ${hour}:${minute}:${second} `
        })
        return dateStr
      } else {
        return `xxxx-xx-xx xx:xx:xx xxxx-xx-xx xx:xx:xx`
      }
    },
    crlfFormat () {
      return this.param.useExplain.replace(/\n/g, '<br />')
    }
  }
}
</script>
<style lang="scss" scoped>
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 10px 20px;
    margin-bottom: 52px;
  }
  .footer {
    position: absolute;
    bottom: 0;
    right: 27px;
    left: 160px;
    height: 52px;
    padding: 10px 0;
    background-color: #fff;
    text-align: center;
    z-index: 99;
  }
}
.el-form-item {
  margin: 15px 0 !important;
}
.main_coupon {
  padding: 0 20px 20px;
  background: #fff;
  width: 100%;
}
.coupon_content {
  display: flex;
  width: 950px;
  margin: 0 auto;
}
.add_coupon_content .coupon_content .content_left {
  width: 323px;
  height: 510px;
  border: 1px solid #ccc;
  background: #eee;
  margin-top: 20px;
}
.fl_title {
  height: 55px;
  background: url(../../../../../../assets/adminImg/phone_tops.png) no-repeat;
  color: white;
  text-align: center;
}
.fl_title div {
  padding-top: 25px;
  font-size: 15px;
}
.info {
  width: 95%;
  margin: 10px auto 0px auto;
  background: white;
}
.info_mid,
.info_bot {
  background: white;
  margin: 0px 10px;
  color: #666;
  font-size: 13px;
}
.info_bot {
  padding: 10px;
}
.vip_exclusive {
  position: absolute;
  top: 10px;
  left: 10px;
  width: 70px;
  line-height: 5px;
  color: #fff;
  border-radius: 15px;
  background-color: rgba(0, 0, 0, 0.1);
  font-size: 12px;
}
.info_top {
  padding: 10px;
  color: white;
  background: url(../../../../../../assets/adminImg/coupon_bg.png) left top
    no-repeat;
  height: 100px;
  background-size: 100% 100%;
  text-align: center;
  font-size: 15px;
}
.info_top div {
  padding: 10px;
}
.info_mid,
.info_bot {
  background: white;
  margin: 0px 10px;
  color: #666;
  font-size: 13px;
}
.info_mid div {
  border-bottom: 1px solid #ddd;
  padding: 15px;
}
.sub_title {
  color: #aaa;
}
.date {
  width: 75%;
  float: right;
}
.small_input {
  width: 70px;
}
.info_bot {
  padding: 10px;
}
.code {
  width: 60%;
  margin: 0 auto;
  text-align: center;
  background: #f6f6f6;
  border-radius: 5px;
  padding: 6px;
  color: #aaa;
}
.use {
  border: 1px solid #ff6666;
  border-radius: 5px;
  padding: 5px;
  width: 30%;
  text-align: center;
  margin: 15px auto;
  color: #ff6666;
}
.instruction {
  background-color: #f6f6f6;
  min-height: 50px;
  margin: 10px auto;
  border-radius: 5px;
  padding: 5px;
  font-size: 12px;
}
.add_coupon_content .coupon_content .content_right {
  flex: 1;
  margin-left: 15px;
}
.coupon_info_title {
  border-bottom: 1px solid #e5e5e5;
  line-height: 40px;
}
.coupon_info {
  border: 1px solid #e5e5e5;
  background: #f8f8f8;
  // padding: 0 12px 22px;
  padding: 0 12px;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  border-radius: 3px;
  margin-bottom: 10px;
  > ul {
    display: flex;
    flex-direction: column;
    > li {
      display: flex;
      line-height: 32px;
      > .content_left_title {
        width: 130px;
        text-align: right;
        line-height: 32px;
      }
    }
  }
}
.card_links > .member {
  color: #409eff;
  cursor: pointer;
}
.content_right_li {
  padding: 10px 0 0 0;
}
.content_right_li > div:nth-child(2) {
  flex: 1;
}
.content_right_li em {
  color: #f66;
}
.content_right_li .fl:first-child {
  width: 110px;
  text-align: right;
}
.ft {
  width: 110px;
  float: left;
  line-height: 30px;
  margin-right: 15px;
}
.coupon_name_input {
  width: 170px;
}
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
</style>
