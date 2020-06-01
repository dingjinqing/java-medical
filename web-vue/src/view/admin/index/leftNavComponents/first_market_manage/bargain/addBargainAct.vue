<!--
* 添加砍价活动页面
*
* @author:赵鑫
-->

<template>
  <div class="bargainAct">
    <div class="bargainContent">
      <div class="bargainActMain">
        <!-- 公共部分 -->
        <el-form
          :model="param"
          label-width="200px"
          labelPosition='right'
          :rules="formRules"
          ref="form"
        >
          <el-form-item
            :label="$t('addBargainAct.bargainType')+':'"
            prop=""
            required
          >
            <el-radio-group
              :disabled="isEditFlag"
              v-model="param.bargainType"
              size="medium"
            >
              <el-radio :label='0'>{{$t('addBargainAct.bargainType1Tip')}}</el-radio>
              <el-radio :label='1'>{{$t('addBargainAct.bargainType2Tip')}}</el-radio>
            </el-radio-group>
            <span class="act-type-tips">{{$t('addBargainAct.sttlementAmountTip')}}</span>
          </el-form-item>

          <el-form-item
            :label="$t('marketCommon.actName')+':'"
            prop="bargainName"
          >
            <el-input
              v-model="param.bargainName"
              size="small"
              style="width:200px;"
              :placeholder="$t('marketCommon.actNamePlaceholder')"
            ></el-input>
          </el-form-item>

          <el-form-item
            :label="$t('marketCommon.validDate')+':'"
            prop="effectiveDate"
          >
            <el-date-picker
              v-model="param.effectiveDate"
              type="datetimerange"
              :range-separator="$t('marketCommon.to')"
              :start-placeholder="$t('marketCommon.startTime')"
              :end-placeholder="$t('marketCommon.endTime')"
              value-format="yyyy-MM-dd HH:mm:ss"
              size="small"
              :default-time="['00:00:00','23:59:59']"
            >
            </el-date-picker>
          </el-form-item>

          <el-form-item
            label="优先级："
            prop="first"
          >
            <el-input
              v-model="param.first"
              size="small"
              placeholder="请输入优先级"
              style="width: 200px"
            ></el-input>
          </el-form-item>

          <el-form-item
            :label="$t('addBargainAct.actGoods')+':'"
            prop=""
            style="padding-top:5px;"
          >
            <div
              v-if="!isEditFlag"
              @click="showChoosingGoods"
              class="choose"
            >
              <img
                :src="srcList.src3"
                alt=""
              >
              <p v-if="this.param.bargainGoods.length == 0">{{$t('addBargainAct.selectGoods')}}</p>
              <p v-else>{{$t('addBargainAct.reselect')}}</p>
            </div>
            <div class="fontColor">{{$t('addBargainAct.actGoodsTip')}}</div>
            <el-table
              :data="param.bargainGoods"
              :hidden="param.bargainGoods.length == 0?true:false"
              border
              header-row-class-name="tableClss"
            >
              <!-- 商品名称 -->
              <el-table-column
                prop="goodsName"
                :label="$t('addBargainAct.goodsName')"
                align="center"
                class="tableHeaderHeight"
                width="200px"
              >
                <template slot-scope="scope">
                  <img
                    :src="scope.row.goodsImg"
                    class="goodsName_img"
                  >
                  <span>{{scope.row.goodsName}}</span>
                </template>
              </el-table-column>

              <!-- 砍价原库存 -->
              <el-table-column
                prop="goodsNumber"
                :label="$t('addBargainAct.goodsOriginalStock')"
                align="center"
                class="tableHeaderHeight"
                width="90px"
              >
                <template slot-scope="scope">
                  <span>{{scope.row.goodsNumber}}</span>
                </template>
              </el-table-column>

              <!-- 砍价库存 -->
              <el-table-column
                :label="$t('addBargainAct.bargainStock')"
                align="center"
                class="tableHeaderHeight"
                width="160"
              >
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'bargainGoods.' + scope.$index+ '.stock'"
                    :rules="[{ validator: (rule, value, callback)=>{validateStock(rule, value, callback, scope.row.goodsNumber, scope.row, scope.$index)}, trigger: ['blur', 'change'] }]"
                  >
                    <el-input
                      v-model="scope.row.stock"
                      size="mini"
                    ></el-input>
                  </el-form-item>
                </template>
              </el-table-column>

              <el-table-column
                prop="saleNum"
                label="已售数量"
                align="center"
                width="90"
                v-if="isEditFlag"
                class="tableHeaderHeight"
              >
                <template slot-scope="scope">
                  <span>{{scope.row.saleNum}}</span>
                </template>
              </el-table-column>

              <!-- 商品原价 -->
              <el-table-column
                prop="shopPrice"
                :label="$t('addBargainAct.goodsOriginalPrice')"
                align="center"
                width="120"
              >
                <template slot-scope="scope">
                  <span>{{scope.row.shopPrice}}</span>
                </template>
              </el-table-column>

              <!-- 指定金额 - 砍价底价 -->
              <el-table-column
                v-if="param.bargainType === 0"
                :label="$t('addBargainAct.bargainReservePrice')"
                align="center"
                class="tableHeaderHeight"
              >
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'bargainGoods.' + scope.$index+ '.expectationPrice'"
                    :rules="[{ validator: (rule, value, callback)=>{validateExpectationPrice(rule, value, callback, scope.row.shopPrice, scope.row.floorPrice, scope.row)}, trigger: ['blur', 'change'] }]"
                    class="input_error"
                  >
                    <el-input
                      v-model.number="scope.row.expectationPrice"
                      size="mini"
                      style="width:50%"
                    >
                    </el-input>
                    ({{$t('addBargainAct.default0')}})
                  </el-form-item>
                </template>
              </el-table-column>

              <!-- 任意金额 - 结算金额 -->
              <el-table-column
                v-if="param.bargainType === 1"
                :label="$t('addBargainAct.sttlementAmount')"
                class="tableHeaderHeight"
                align="center"
              >
                <template slot-scope="scope">
                  <div>
                    <div style="display: flex;justify-content: center;">
                      <el-form-item
                        :prop="'bargainGoods.' + scope.$index+ '.floorPrice'"
                        :rules="[{ validator: (rule, value, callback)=>{validateFloorPrice(rule, value, callback, scope.row.shopPrice, scope.row.expectationPrice, scope.row)}, trigger: ['blur', 'change'] }]"
                      >
                        <el-input
                          :disabled="isEditFlag"
                          v-model="scope.row.floorPrice"
                          size="mini"
                        >
                        </el-input>
                      </el-form-item>
                      <span style="margin: 10px 6px;">{{$t('marketCommon.to')}}</span>
                      <el-form-item
                        :prop="'bargainGoods.' + scope.$index+ '.expectationPrice'"
                        :rules="[{ validator: (rule, value, callback)=>{validateExpectationPrice(rule, value, callback, scope.row.shopPrice, scope.row)}, trigger: ['blur', 'change'] }]"
                      >
                        <el-input
                          :disabled="isEditFlag"
                          v-model="scope.row.expectationPrice"
                          size="mini"
                        >
                        </el-input>
                      </el-form-item>
                    </div>
                    <div style="margin-top:5px;">
                      <span>({{$t('addBargainAct.default0')}})</span>
                      <span style="color: #999;">{{$t('addBargainAct.sttlementAmountTip')}}</span>
                    </div>
                  </div>
                </template>
              </el-table-column>

              <!-- 操作 -->
              <el-table-column
                label="操作"
                align="center"
                v-if="addFlag"
                width="90"
              >
                <template slot-scope="scope">
                  <div
                    v-if="scope.row.goodsId"
                    :disabled="isEditFlag"
                    @click="deleteGoods(scope.row, scope.row.goodsId)"
                    style="cursor:pointer;color:#409eff"
                  >删除</div>
                </template>
              </el-table-column>

              <div
                slot="append"
                class="moreSetUp"
              >
                <span style="color: #5a8bff">批量设置：</span>
                <a
                  :class="activeIndex === 1 ? '' : 'settings'"
                  @click="setCurrent(1)"
                >砍价库存</a>
                <a
                  :class="activeIndex === 2 ? '' : 'settings'"
                  @click="setCurrent(2)"
                  v-if="param.bargainType===1"
                >最小结算金额</a>
                <a
                  :class="activeIndex === 3 ? '' : 'settings'"
                  @click="setCurrent(3)"
                >结算金额</a>
              </div>
            </el-table>
          </el-form-item>

          <!-- 砍到指定金额计算部分内容区域 -->
          <div v-if="this.param.bargainType==0">

            <el-form-item
              label="帮砍设置:"
              prop=""
            >
              <el-checkbox
                label="帮砍好友需要授权手机号才可以参与砍价"
                v-model="param.needBindMobile"
              ></el-checkbox>
            </el-form-item>
            <el-form-item
              :label="$t('marketCommon.shippingSetting')+':'"
              prop=""
              required
            >
              <el-radio-group v-model="param.freeFreight">
                <el-radio :label="1">{{$t('marketCommon.freeShipping')}}</el-radio>
                <el-radio :label="0">{{$t('marketCommon.useOriginalProductShippingTemplate')}}</el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item
              :label="$t('addBargainAct.expectToParticipateInBargaining')+':'"
              required
            >
              <el-input-number
                :disabled="isEditFlag"
                v-model="param.expectationNumber"
                size="small"
                controls-position="right"
                style="width:150px"
                :min="3"
              >
              </el-input-number>&nbsp;{{$t('addBargainAct.people')}}
              <span style="margin-left:10px">({{$t('addBargainAct.expectPeopleMin')}})</span>
              <div class="fontColor">{{$t('addBargainAct.expectPeopleTip')}}</div>
            </el-form-item>

            <el-form-item
              :label="$t('addBargainAct.goodsFirstBargainProportion')+':'"
              prop="bargainMin"
            >
              <div>
                <el-input
                  v-model="param.bargainMin"
                  size="small"
                  style="width:150px"
                ></el-input>&nbsp;%&nbsp;{{$t('marketCommon.to')}}&nbsp;
                <el-input
                  v-model="param.bargainMax"
                  size="small"
                  style="width:150px"
                  ref="bargainMax"
                ></el-input>&nbsp;%
                <span style="margin-left:10px">({{$t('addBargainAct.proportionIntervalTip')}})</span>
              </div>
              <div
                class="fontColor"
                style="line-height:24px;margin-top:10px;width: 880px;"
              >{{$t('addBargainAct.proportionInterval')}}</div>
            </el-form-item>

            <el-form-item label="活动初始参与砍价人次：">
              <el-input-number
                :disabled="isEditFlag"
                v-model="param.initialSales"
                size="small"
                controls-position="right"
                style="width:150px"
                :min="0"
              >
              </el-input-number>
              <div class="fontColor">活动商品初始参与砍价人次，将展示在小程序端。参与砍价人次=活动初始参与砍价人次+实际砍价人次，不填写则表示0</div>
            </el-form-item>
          </div>

          <!-- 砍到任意金额计算部分 -->
          <div v-if="this.param.bargainType==1">
            <el-form-item
              :label="$t('addBargainAct.singleBargainMoney')+':'"
              prop=""
            >
              <el-radio-group
                v-model="param.bargainMoneyType"
                style="margin-top:7px"
              >
                <el-radio :label='0'>{{$t('addBargainAct.fixedMoney')}}
                  <el-input-number
                    v-model="param.bargainFixedMoney"
                    controls-position="right"
                    size="mini"
                    style="width:150px"
                  ></el-input-number>&nbsp;{{$t('marketCommon.yuan')}}
                </el-radio>
                <br>
                <el-radio :label='1'>{{$t('addBargainAct.randomMoney')}}
                  <el-input-number
                    v-model="param.bargainMinMoney"
                    size="mini"
                    controls-position="right"
                    style="width:150px"
                    :min="0"
                  ></el-input-number>&nbsp;{{$t('marketCommon.yuan')}}
                  <span>{{$t('marketCommon.to')}}</span>
                  <el-input-number
                    v-model="param.bargainMaxMoney"
                    size="mini"
                    controls-position="right"
                    style="width:150px;margin-top: 10px"
                    :min="0"
                  ></el-input-number>{{$t('marketCommon.yuan')}}{{$t('addBargainAct.getRandomMoneyBetween')}}
                </el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item
              label="帮砍设置:"
              prop=""
            >
              <el-checkbox
                label="帮砍好友需要授权手机号才可以参与砍价"
                v-model="param.needBindMobile"
              ></el-checkbox>
            </el-form-item>
            <el-form-item
              :label="$t('marketCommon.shippingSetting')+':'"
              prop=""
            >
              <el-radio-group v-model="param.freeFreight">
                <el-radio :label="1">{{$t('marketCommon.freeShipping')}}</el-radio>
                <el-radio :label="0">{{$t('marketCommon.useOriginalProductShippingTemplate')}}</el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item label="活动初始参与砍价人次：">
              <el-input-number
                :disabled="isEditFlag"
                v-model="param.initialSales"
                size="small"
                controls-position="right"
                style="width:150px"
                :min="0"
              >
              </el-input-number>
              <div class="fontColor">活动商品初始参与砍价人次，将展示在小程序端。参与砍价人次=活动初始参与砍价人次+实际砍价人次，不填写则表示0</div>
            </el-form-item>
          </div>

          <!-- 收起、展开更多配置 -->
          <div
            @click="handleToChangeArror()"
            style="margin: 0 0 10px 140px"
          >
            <div
              v-if="arrorFlag"
              style="color:rgb(90, 139, 255);cursor:pointer"
            >{{$t('membershipIntroduction.more')}}&nbsp;<img :src="ArrowArr[0].img_1"></div>
            <div
              v-if="!arrorFlag"
              style="color:rgb(90, 139, 255);cursor:pointer"
            >{{$t('membershipIntroduction.retract')}}&nbsp;<img :src="ArrowArr[1].img_2"></div>
          </div>

          <!-- 公共更多配置模块部分 -->
          <div v-if='!arrorFlag'>
            <el-form-item
              :label="$t('addBargainAct.friendsBargainCoupon')+':'"
              prop=""
            >
              <!-- 好友砍价优惠券部分 -->
              <el-card class="box-card">
                <div class="fontColor">{{$t('addBargainAct.friendsBargainCouponTip')}}</div>
                <div class="middleContainer">
                  <div>
                    <div
                      v-for="(item,index) in mrkingVoucherObjs"
                      :key="index"
                      class="addInfo"
                      style="margin-right: 15px;"
                    >
                      <section
                        class="couponImgWrapper"
                        style="line-height:normal"
                      >

                        <div
                          class="coupon_list_top"
                          v-if="item.actCode==='voucher'"
                        >
                          <span>￥</span>
                          <span class="number">{{item.denomination}}</span>
                        </div>
                        <div
                          class="coupon_list_top"
                          v-if="item.actCode==='discount'"
                        >
                          <span style="font-size: 20px">{{item.denomination}}</span>
                          <span style="font-size: 14px">{{$t('payReward.discount')}}</span>
                        </div>
                        <div
                          class="coupon_list_top"
                          v-if="item.actCode === 'random'"
                        >
                          ￥<span class="number_heightest">{{item.randomMax}}</span>
                          <span class="hightest">最高</span>
                        </div>
                        <div class="coupon_center_limit">{{item.useConsumeRestrict | formatLeastConsume(item.leastConsume)}}</div>
                        <div
                          class="coupon_center_number"
                          v-if="item.surplus !==0"
                        >剩余{{item.surplus}}张</div>
                        <div
                          class="coupon_center_number"
                          v-if="item.surplus ===0"
                        >库存不限制</div>
                        <div
                          class="coupon_list_bottom"
                          :style="`background-image: url(${$imageHost}/image/admin/coupon_border.png)`"
                        >
                          <!-- <span v-if="item.scoreNumber === 0">领取</span>
                          <div v-if="item.scoreNumber !== 0">
                            <span>{{item.scoreNumber}}</span>积分 兑换
                          </div> -->
                          <span>领取</span>
                        </div>
                      </section>
                      <!-- <span
                        @click="deleteCouponImg(index)"
                        class="deleteIcon"
                      >×</span> -->
                      <div
                        @click="deleteCouponImg(index)"
                        class="deleteIcon"
                      >
                        <img
                          :src="imgHost+'/image/admin/sign_del.png'"
                          alt=""
                        >
                      </div>
                    </div>
                  </div>

                  <div
                    class="addInfo"
                    @click="handleToCallDialog1()"
                    v-if="mrkingVoucherObjs.length < 5"
                  >
                    <el-image
                      fit="scale-down"
                      :src="imgHost+'/image/admin/shop_beautify/add_decorete.png'"
                      style="width: 78px;height:78px;cursor:pointer"
                    ></el-image>
                    <p>{{$t('addBargainAct.addCoupon')}}</p>
                  </div>
                </div>

                <div class="fontColor">{{$t('addBargainAct.couponLimitTip')}}</div>
              </el-card>
            </el-form-item>

            <el-form-item
              :label="$t('addBargainAct.encouragementAward')+':'"
              prop=""
            >
              <!-- 鼓励奖优惠券部分 -->
              <el-card class="box-card">
                <div class="fontColor">{{$t('addBargainAct.encouragementAwardTip')}}</div>
                <div class="middleContainer">
                  <div
                    v-for="(item,index) in rewardCouponObjs"
                    :key="index"
                    class="addInfo"
                    style="margin-right: 15px;"
                  >
                    <section
                      class="couponImgWrapper"
                      style="line-height: normal"
                    >
                      <div
                        class="coupon_list_top"
                        v-if="item.actCode==='voucher'"
                      >
                        <span>￥</span>
                        <span class="number">{{item.denomination}}</span>
                      </div>
                      <div
                        class="coupon_list_top"
                        v-if="item.actCode==='discount'"
                      >
                        <span style="font-size: 20px">{{item.denomination}}</span>
                        <span style="font-size: 14px">{{$t('payReward.discount')}}</span>
                      </div>
                      <div
                        class="coupon_list_top"
                        v-if="item.actCode === 'random'"
                      >
                        ￥<span class="number_heightest">{{item.randomMax}}</span>
                        <span class="hightest">最高</span>
                      </div>
                      <div class="coupon_center_limit">{{item.useConsumeRestrict | formatLeastConsume(item.leastConsume)}}</div>
                      <div
                        class="coupon_center_number"
                        v-if="item.surplus !==0"
                      >剩余{{item.surplus}}张</div>
                      <div
                        class="coupon_center_number"
                        v-if="item.surplus ===0"
                      >库存不限制</div>
                      <div
                        class="coupon_list_bottom"
                        :style="`background-image: url(${$imageHost}/image/admin/coupon_border.png`"
                      >
                        <!-- <span v-if="item.scoreNumber === 0">领取</span>
                        <div v-if="item.scoreNumber !== 0">
                          <span>{{item.scoreNumber}}</span>积分 兑换
                        </div> -->
                        <span>领取</span>
                      </div>
                    </section>
                    <div
                      @click="deleteCouponImg2(index)"
                      class="deleteIcon"
                    >
                      <img
                        :src="imgHost+'/image/admin/sign_del.png'"
                        alt=""
                      >
                    </div>
                  </div>

                  <div
                    class="addInfo"
                    @click="handleToCallDialog2()"
                    v-if="rewardCouponObjs.length < 5"
                    style="line-height: normal"
                  >
                    <el-image
                      fit="scale-down"
                      :src="imgHost+'/image/admin/shop_beautify/add_decorete.png'"
                      style="width: 78px;height:78px;cursor:pointer"
                    ></el-image>
                    <p>{{$t('addBargainAct.addCoupon')}}</p>
                  </div>
                </div>
                <div class="fontColor">{{$t('addBargainAct.couponLimitTip')}}</div>
              </el-card>
            </el-form-item>
            <!-- 引入活动分享模块 -->
            <el-form-item label="活动分享:">
              <actShare :shareConfig="shareConfig" />
            </el-form-item>
          </div>

        </el-form>

      </div>
    </div>
    <div class="footer">
      <el-button
        @click="isEditFlag?updateSubmit():addSubmit()"
        type="primary"
        size="small"
      >{{$t('marketCommon.save')}}</el-button>
    </div>
    <!--添加优惠券-->
    <AddCouponDialog
      @handleToCheck="handleToCheck"
      :tuneUpCoupon="showCouponDialog"
      :couponBack="couponIdList"
      :type="-1"
    />
    <!--商品选择-->
    <choosingGoods
      @resultGoodsDatas="choosingGoodsResult"
      :tuneUpChooseGoods="tuneUpChooseGoods"
      :chooseGoodsBack="goodsIdList"
      :singleElection="false"
    />
  </div>
</template>

<script>
import addCoupon from './addCoupon'
import actShare from '@/components/admin/marketManage/marketActivityShareSetting'
import AddCouponDialog from '@/components/admin/addCouponDialog'
import choosingGoods from '@/components/admin/choosingGoods'
import { addBargain, getBargainByIsd, updateBargain } from '@/api/admin/marketManage/bargain.js'

export default {
  components: { addCoupon, actShare, AddCouponDialog, choosingGoods },
  mounted () {
    this.langDefault()
    if (this.$route.query.id > 0) {
      // 编辑砍价活动
      this.actId = this.$route.query.id
      // 编辑时部分信息不可修改
      this.isEditFlag = true
      this.addFlag = false
      // 点击编辑按钮进来，初始化页面数据
      let SimpleBargainParam = {
        'id': this.$route.query.id
      }
      getBargainByIsd(SimpleBargainParam).then((res) => {
        console.log(res)
        if (res.error === 0) {
          console.log(res, 'res--')
          this.param = res.content
          console.log(this.param)
          let date = [res.content.startTime, res.content.endTime]
          this.$set(this.param, 'effectiveDate', date)
          this.mrkingVoucherObjs = res.content.mrkingVoucherList
          this.rewardCouponObjs = res.content.rewardCouponList
          this.param.bargainGoods = res.content.bargainGoods
          console.log(this.param.bargainGoods)
          let resultConfig = res.content.shopShareConfig
          this.shareConfig = resultConfig
          this.shareConfig.shareImg = resultConfig.shareImgFullUrl
          this.param.needBindMobile = Boolean(res.content.needBindMobile)
          if (res.content.bargainMin === null && res.content.bargainMax === null) {
            this.param.bargainMin = ''
            this.param.bargainMax = ''
          } else {
            this.param.bargainMin = res.content.bargainMin
            this.param.bargainMax = res.content.bargainMax
          }
        }
      })
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
    var validMin = (rule, value, callback) => {
      let validMax = this.$refs.bargainMax.value
      if (!value && !validMax) {
        callback()
      }
      var reg = /^(0|[1-9][0-9]*)$/
      if (!reg.test(value) || !reg.test(validMax)) {
        callback(new Error('请输入0和正整数'))
      } else {
        if (value > 50 || validMax > 50) {
          callback(new Error('数值不能大于50'))
        } else {
          callback()
        }
      }
    }
    var validLevel = (rule, value, callback) => {
      var reg = /^(0|[1-9][0-9]*)$/
      if (value === '' || !value) {
        callback(new Error('请输入优先级'))
      } else if (!reg.test(value)) {
        callback(new Error('请输入0和正整数'))
      } else {
        callback()
      }
    }
    return {
      // 向帮忙砍价的用户赠送优惠券
      mrkingVoucherObjs: [],
      // 砍价失败后向买家赠送优惠券
      rewardCouponObjs: [],
      // 优惠券弹窗区分，1鼓励奖，0好友砍价优惠券
      dialogFlag: 1,
      // param.bargainGoods: [],
      srcList: {
        src1: `${this.$imageHost}/image/admin/share/bargain_share.jpg`,
        src2: `${this.$imageHost}/image/admin/share/bagain_pictorial.jpg`,
        src3: `${this.$imageHost}/image/admin/shop_beautify/add_decorete.png`,
        imageUrl: ``
      },
      param: {
        // 默认值
        bargainType: 0,
        freeFreight: 1,
        expectationNumber: 100,
        bargainMoneyType: 0,
        stock: 0,
        floorPrice: 0,
        effectiveDate: [],
        goodsId: 0,
        expectationPrice: '',
        needBindMobile: false,
        initialSales: 0,
        bargainMin: '',
        bargainMax: '',
        first: '',
        startTime: '',
        endTime: '',
        shareConfig: {
          shareAction: 1,
          shareDoc: '',
          shareImgAction: 1,
          shareImg: null
        },
        bargainGoods: []
      },
      shareConfig: {
        shareAction: 1,
        shareDoc: '',
        shareImgAction: 1,
        shareImg: null
      },
      isEditFlag: false,
      actId: null,
      couponImg: [],
      imgHost: `${this.$imageHost}`,
      couponIdList: [],
      showCouponDialog: false,
      tuneUpChooseGoods: false,
      goodsIdList: [],
      arrorFlag: true,
      activeIndex: 0,
      addFlag: true,
      ArrowArr: [
        {
          img_1: this.$imageHost + '/image/admin/show_more.png'
        },
        {
          img_2: this.$imageHost + '/image/admin/hid_some.png'
        }
      ],
      // 表单约束
      formRules: {
        bargainName: [
          { required: true, message: this.$t('promoteList.check'), trigger: 'blur' }
        ],
        effectiveDate: [
          { required: true, message: this.$t('promoteList.check'), trigger: 'change' }
        ],
        bargainMin: [
          { validator: validMin, trigger: ['change', 'blur'] }
        ],
        first: [
          { required: true, validator: validLevel, trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  methods: {
    // 选择优惠券弹窗-帮忙砍价的用户
    handleToCallDialog1 () {
      this.dialogFlag = 0
      this.couponIdList = this.getCouponIdsArray(this.mrkingVoucherObjs)
      this.showCouponDialog = !this.showCouponDialog
    },
    // 选择优惠券弹窗-砍价失败后向买家赠送
    handleToCallDialog2 () {
      this.dialogFlag = 1
      this.couponIdList = this.getCouponIdsArray(this.rewardCouponObjs)
      this.showCouponDialog = !this.showCouponDialog
    },
    // 确认选择优惠券-新增-删除
    handleToCheck (data, index) {
      console.log(data, 'coupon-data')
      console.log(this.rewardCouponObjs)
      if (this.dialogFlag === 1) {
        // console.log(this.rewardCouponObjs)
        if (this.rewardCouponObjs.length >= 5) {
          return
        }
        this.rewardCouponObjs = data
      } else {
        if (this.mrkingVoucherObjs.length >= 5) {
          return
        }
        this.mrkingVoucherObjs = data
      }
    },
    // 删除好友砍价优惠券图片
    deleteCouponImg (index) {
      this.mrkingVoucherObjs.splice(index, 1)
    },
    // 删除鼓励奖优惠券图片
    deleteCouponImg2 (index) {
      this.rewardCouponObjs.splice(index, 1)
    },
    // 选择商品弹窗
    showChoosingGoods () {
      this.tuneUpChooseGoods = !this.tuneUpChooseGoods
    },
    //  选择商品弹窗确认
    choosingGoodsResult (row) {
      console.log(row, 'get row')
      this.param.goodsId = row.map(item => { return item.goodsId })
      console.log(this.param.goodsId)
      this.param.bargainGoods = row
      this.goodsIdList = []
      this.goodsIdList = row.map(item => { return item.goodsId })
    },
    deleteGoods (data, id) {
      let index = this.goodsIdList.findIndex(item => {
        return item === id
      })
      this.goodsIdList.splice(index, 1)
      let goodsTarget = this.param.bargainGoods.findIndex(item => {
        return id === item.goodsId
      })
      this.param.bargainGoods.splice(goodsTarget, 1)
    },
    // 设置数据
    setCurrent (index) {
      // 拷贝一份数据
      let price = JSON.parse(JSON.stringify(this.param.bargainGoods))
      console.log(price)
      switch (index) {
        case 1:
          price.forEach(row => {
            row.stock = price[0].stock
            console.log(row)
          })
          this.activeIndex = 1
          break
        case 2:
          price.forEach(row => {
            row.floorPrice = price[0].floorPrice
          })
          this.activeIndex = 2
          break
        case 3:
          price.forEach(row => {
            row.expectationPrice = price[0].expectationPrice
          })
          this.activeIndex = 3
          break
      }
      this.param.bargainGoods = price
    },

    // 校验输入库存
    validateStock (rule, value, callback, goodsNumber, row) {
      var re = /^[1-9]\d*$/
      if (!value) {
        callback(new Error('砍价库存不能为空'))
      } else if (value < 0) {
        callback(new Error('砍价库存不能为负数'))
      } else if (!re.test(value)) {
        callback(new Error('请填写正整数'))
      } else if (Number(value) > Number(goodsNumber)) {
        callback(new Error('砍价库存不能大于商品原库存'))
      } else {
        callback()
      }
    },

    validateExpectationPrice (rule, value, callback, shopPrice, floorPrice, row) {
      var re = /^(0|\+?[1-9][0-9]*)$/
      if (value === '' || value === undefined) {
        callback(new Error('砍价底价不能为空'))
      } else if (value < 0) {
        callback(new Error('砍价底价不能为负数'))
      } else if (!re.test(Number(value))) {
        callback(new Error('请填写0或者正整数'))
      } else if (Number(value) > Number(shopPrice)) {
        callback(new Error('砍价底价不能大于商品原价'))
      } else if (this.bargainType === 1 && (Number(value) < Number(floorPrice))) {
        callback(new Error('砍价底价区间设置不合理'))
      } else {
        callback()
      }
    },

    validateFloorPrice (rule, value, callback, shopPrice, expectationPrice, row) {
      var re = /^(0|\+?[1-9][0-9]*)$/
      if (!value) {
        callback(new Error('砍价底价不能为空'))
      } else if (value < 0) {
        callback(new Error('砍价底价不能为负数'))
      } else if (!re.test(value)) {
        callback(new Error('请填写0或者正整数'))
      } else if (Number(value) > Number(shopPrice)) {
        callback(new Error('砍价底价不能大于商品原价'))
      } else if (Number(value) > Number(expectationPrice)) {
        callback(new Error('砍价底价区间设置不合理'))
      } else {
        callback()
      }
    },
    addSubmit () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.param.shareConfig = this.shareConfig
          this.param.startTime = this.param.effectiveDate[0]
          this.param.endTime = this.param.effectiveDate[1]
          this.param.mrkingVoucherId = this.getCouponIdsString(this.mrkingVoucherObjs)
          this.param.rewardCouponId = this.getCouponIdsString(this.rewardCouponObjs)
          this.param.needBindMobile = this.param.needBindMobile ? 1 : 0
          console.log(this.param, 'param')

          this.param.first = Number(this.param.first)
          this.param.stock = 0
          let bargainGoods = []
          this.param.bargainGoods.forEach(item => {
            let { goodsId, expectationPrice, floorPrice, stock } = item
            bargainGoods.push({ goodsId, expectationPrice, floorPrice, stock })
            this.param.stock += stock
          })
          console.log(bargainGoods)
          this.param.bargainGoods = bargainGoods

          if (this.validParam()) {
            addBargain(this.param).then((res) => {
              if (res.error === 0) {
                this.$message.success(this.$t('marketCommon.successfulOperation'))
                this.$router.push({
                  name: 'kanjia'
                })
              } else {
                this.$message.error(res.message)
              }
            })
          }
        } else {
          this.$message.error('请正确填写表单！')
        }
      })
    },
    updateSubmit () {
      // 更新活动
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.param.id = this.actId
          this.param.shareConfig = this.shareConfig
          this.param.startTime = this.param.effectiveDate[0]
          this.param.endTime = this.param.effectiveDate[1]
          this.param.mrkingVoucherId = this.getCouponIdsString(this.mrkingVoucherObjs)
          this.param.rewardCouponId = this.getCouponIdsString(this.rewardCouponObjs)
          this.param.needBindMobile = this.param.needBindMobile ? 1 : 0
          if (this.validParam()) {
            updateBargain(this.param).then((res) => {
              if (res.error === 0) {
                this.$message.success(this.$t('marketCommon.successfulOperation'))
                this.$router.push({
                  name: 'kanjia'
                })
              } else {
                this.$message.error(this.$t('marketCommon.failureOperation'))
              }
            })
          }
        } else {
          this.$message.error('请正确填写表单！')
        }
      })
    },
    getCouponIdsString (data) {
      let res = ''
      data.forEach((item, index) => {
        if (index === 0) {
          res += item.id
        } else {
          res += ',' + item.id
        }
      })
      return res
    },
    getCouponIdsArray (data) {
      let res = []
      data.forEach((item, index) => {
        res.push(item.id)
      })
      return res
    },
    // 改变箭头事件
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    },
    // 提交前校验
    validParam () {
      /** 校验是否选择了商品 **/
      if (this.param.bargainGoods.length === 0) {
        this.$message.warning(this.$t('addBargainAct.vaildGoodsSelect'))
        return false
      }
      if (this.param.bargainType === 0) {
        // 砍到指定金额结算：期望参与砍价人次必填；商品首次砍价可砍价百分比区间必填；砍价底价必填
        if (this.param.bargainGoods.expectationPrice === '') {
          this.$message.warning(this.$t('addBargainAct.vaildExpectationPrice'))
          return false
        }
        if (!this.param.expectationNumber) {
          this.$message.warning(this.$t('addBargainAct.vaildExpectationNumber'))
          return false
        }
        if (this.param.expectationNumber < 3) {
          this.$message.warning(this.$t('addBargainAct.vaildExpectationNumberMin'))
          return false
        }
        // if (this.param.bargainMin === '' || !this.param.bargainMax) {
        //   this.$message.warning(this.$t('addBargainAct.vaildProportionalinterval1'))
        //   return false
        // }
        if (this.param.bargainMin > this.param.bargainMax) {
          this.$message.warning(this.$t('addBargainAct.vaildProportionalinterval2'))
          return false
        }
      } else {
        // 砍到任意金额结算
        if (this.param.bargainMoneyType === 0) {
          // if (!this.param.expectationPrice || this.param.floorPrice === '') {
          //   this.$message.warning(this.$t('addBargainAct.vaildCalculatedAmount1'))
          //   return false
          // }
          // if (this.param.expectationPrice < this.param.floorPrice) {
          //   this.$message.warning(this.$t('addBargainAct.vaildCalculatedAmount2'))
          //   return false
          // }

          // 固定金额模式
          if (!this.param.bargainFixedMoney) {
            this.$message.warning(this.$t('addBargainAct.vaildFixedMoney'))
            return false
          }
        } else {
          if (this.param.bargainMinMoney === '' || !this.param.bargainMaxMoney) {
            this.$message.warning(this.$t('addBargainAct.vaildRandomAmount1'))
            return false
          }
          if (this.param.bargainMinMoney > this.param.bargainMaxMoney) {
            this.$message.warning(this.$t('addBargainAct.vaildRandomAmount2'))
            return false
          }
        }
      }
      if (this.shareConfig.shareAction === 2 && !this.param.shareConfig.shareDoc) {
        this.$message.warning('请填写对应的分享文案')
        return false
      }
      if (this.shareConfig.shareImgAction === 2 && this.param.shareConfig.shareImg === null) {
        this.$message.warning('请选择自定义图片')
        return false
      }
      return true
    }
  }
}
</script>
<style lang="scss" scoped>
.bargainAct {
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .bargainContent {
    position: relative;
    padding: 10px;
    margin-bottom: 52px;
    .bargainActMain {
      position: relative;
      background-color: #fff;
      padding: 10px 20px;
      .act-type-tips {
        margin-left: 15px;
        color: #999;
      }
      .box-card {
        width: 630px;
        background-color: #f5f5f5;
        .middleContainer {
          display: flex;
          .deleteIcon {
            position: relative;
            width: 17px;
            height: 17px;
            top: -117px;
            left: 87px;
            img {
              width: 100%;
              height: 100%;
            }
          }
        }
        .addInfo {
          display: inline-block;
          position: relative;
          width: 100px;
          height: 101px;
          margin-bottom: 10px;
          border-radius: 10px;
          background: #fff;
          border: 1px solid #e4e4e4;
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
          .couponImgWrapper {
            width: 100%;
            height: 100%;
            border: 1px solid #fbb;
            border-radius: 10px;
            .coupon_list_top {
              margin-top: 10px;
              color: #f60;
              :nth-of-type(2) {
                font-size: 20px;
                font-weight: bold;
              }
              .number_heightest {
                font-size: 20px;
                font-weight: bold;
              }
              .hightest {
                font-size: 12px;
                font-weight: bold;
              }
            }
            .coupon_center_limit {
              height: 20px;
              color: #f60;
              font-size: 12px !important;
            }
            .coupon_center_number {
              height: 18px;
              font-size: 12px;
              color: #fbb;
            }
            .coupon_list_bottom {
              height: 24px;
              line-height: 30px;
              border-bottom-left-radius: 8px;
              border-bottom-right-radius: 8px;
              color: #fff;
              background: #f66;
              background-repeat: repeat-x;
            }
          }
        }
      }
      .choose {
        display: inline-block;
        width: 70px;
        height: 70px;
        margin-bottom: 10px;
        background: #fff;
        border: 1px solid #e4e4e4;
        cursor: pointer;
        text-align: center;
        img {
          margin-top: 12px;
        }
        p {
          margin-top: -18px;
          font-size: 12px;
          color: #999;
        }
      }
      .selectPic {
        // display: flex;
        width: 70px;
        height: 70px;
        line-height: 70px;
        text-align: center;
        border: 1px solid #e4e4e4;
        .recPic {
          width: 80px;
          height: 80px;
          background-repeat: no-repeat;
        }
      }
    }
    .fontColor {
      color: #999;
    }
  }
  .el-form-item {
    margin-bottom: 15px;
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
  }
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  height: 20px;
  line-height: 0;
}
.goodsName_img {
  width: 28px;
  height: 28px;
}
.settings {
  color: #5a8bff;
}
/deep/ .el-form-item__error {
  position: relative;
  text-align: left;
}
.input_error {
  /deep/ .el-form-item__error {
    margin-left: 25%;
  }
}
</style>
