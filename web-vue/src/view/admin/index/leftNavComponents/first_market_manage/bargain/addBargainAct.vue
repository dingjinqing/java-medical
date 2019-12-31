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
          label-width="150px"
          labelPosition='right'
          :rules="formRules"
          ref="form"
        >
          <el-form-item
            :label="$t('addBargainAct.bargainType')+':'"
            prop=""
          >
            <el-radio-group
              :disabled="isEditFlag"
              v-model="param.bargainType"
              size="medium"
            >
              <el-radio :label='0'>{{$t('addBargainAct.bargainType1Tip')}}</el-radio>
              <el-radio :label='1'>{{$t('addBargainAct.bargainType2Tip')}}</el-radio>
            </el-radio-group>
            <span style="margin-left: 15px;">{{$t('addBargainAct.sttlementAmountTip')}}</span>
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
            :label="$t('addBargainAct.actGoods')+':'"
            prop=""
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
              <p v-if="this.goodsRow.length == 0">{{$t('addBargainAct.selectGoods')}}</p>
              <p v-else>{{$t('addBargainAct.reselect')}}</p>
            </div>
            <div class="fontColor">{{$t('addBargainAct.actGoodsTip')}}</div>
            <el-table
              :data="this.goodsRow"
              :hidden="this.goodsRow.length == 0?true:false"
              border
              header-row-class-name="tableClss"
            >
              <el-table-column
                prop="goodsName"
                :label="$t('addBargainAct.goodsName')"
                align="center"
                class="tableHeaderHeight"
                width="300"
              >
                <template slot-scope="scope">
                  <img
                    :src="scope.row.goodsImg"
                    class="goodsName_img"
                  >
                  <span>{{scope.row.goodsName}}</span>
                </template>

              </el-table-column>
              <el-table-column
                prop="goodsNumber"
                :label="$t('addBargainAct.goodsOriginalStock')"
                align="center"
                class="tableHeaderHeight"
                width="100"
              ></el-table-column>
              <el-table-column
                :label="$t('addBargainAct.bargainStock')"
                align="center"
                class="tableHeaderHeight"
                width="200"
              >
                <template slot-scope="scope">
                  <el-input-number
                    v-model="param.stock"
                    size="mini"
                    controls-position="right"
                    :min="1"
                    :max="scope.row.goodsNumber"
                  >
                  </el-input-number>
                </template>
              </el-table-column>
              <el-table-column
                prop="shopPrice"
                :label="$t('addBargainAct.goodsOriginalPrice')"
                align="center"
                class="tableHeaderHeight"
                width="100"
              ></el-table-column>
              <el-table-column
                v-if="param.bargainType == 0"
                prop="shopPrice"
                :label="$t('addBargainAct.bargainReservePrice')"
                align="center"
                class="tableHeaderHeight"
              >
                <template slot-scope="scope">
                  <el-input-number
                    v-model="param.expectationPrice"
                    size="mini"
                    controls-position="right"
                    :min="0"
                    :max="scope.row.shopPrice"
                  >
                  </el-input-number>
                  ({{$t('addBargainAct.default0')}})
                </template>
              </el-table-column>
              <el-table-column
                v-else
                prop="shopPrice"
                :label="$t('addBargainAct.sttlementAmount')"
                align="center"
                class="tableHeaderHeight"
              >
                <template slot-scope="scope">
                  <div style="display: flex">
                    <el-input-number
                      :disabled="isEditFlag"
                      v-model="param.floorPrice"
                      size="mini"
                      controls-position="right"
                      :min="0"
                      :max="scope.row.shopPrice"
                    >
                    </el-input-number>
                    <span style="margin: 3px 6px;">{{$t('marketCommon.to')}}</span>
                    <el-input-number
                      :disabled="isEditFlag"
                      v-model="param.expectationPrice"
                      size="small"
                      controls-position="right"
                      :min="0"
                      :max="scope.row.shopPrice"
                    >
                    </el-input-number>
                    <div style="margin: 4px 6px">({{$t('addBargainAct.default0')}})
                      <span style="color: #999;">{{$t('addBargainAct.sttlementAmountTip')}}</span>
                    </div>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>

          <!-- 砍到指定金额计算部分内容区域 -->
          <div v-if="this.param.bargainType==0">

            <el-form-item
              :label="$t('marketCommon.shippingSetting')+':'"
              prop=""
            >
              <el-radio-group v-model="param.freeFreight">
                <el-radio :label="1">{{$t('marketCommon.freeShipping')}}</el-radio>
                <el-radio :label="0">{{$t('marketCommon.useOriginalProductShippingTemplate')}}</el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item :label="$t('addBargainAct.expectToParticipateInBargaining')+':'">
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

            <el-form-item :label="$t('addBargainAct.goodsFirstBargainProportion')">
              <div style="display: flex">
                <el-input-number
                  v-model="param.bargainMin"
                  controls-position="right"
                  size="small"
                  style="width:150px"
                  :min="0"
                  :max="50"
                ></el-input-number>&nbsp;%&nbsp;{{$t('marketCommon.to')}}&nbsp;
                <el-input-number
                  v-model="param.bargainMax"
                  size="small"
                  controls-position="right"
                  style="width:150px"
                  :min="0"
                  :max="50"
                ></el-input-number>&nbsp;%
                <span style="margin-left:10px">({{$t('addBargainAct.proportionIntervalTip')}})</span>
              </div>
              <div
                class="fontColor"
                style="line-height:24px;margin-top:10px"
              >{{$t('addBargainAct.proportionInterval')}}</div>
            </el-form-item>
          </div>

          <!-- 砍到任意金额计算部分 -->
          <div v-if="this.param.bargainType==1">
            <el-form-item
              :label="$t('addBargainAct.singleBargainMoney')"
              prop=""
            >
              <el-radio-group v-model="param.bargainMoneyType">
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
              :label="$t('marketCommon.shippingSetting')+':'"
              prop=""
            >
              <el-radio-group v-model="param.freeFreight">
                <el-radio :label="1">{{$t('marketCommon.freeShipping')}}</el-radio>
                <el-radio :label="0">{{$t('marketCommon.useOriginalProductShippingTemplate')}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </div>

          <!-- 收起、展开更多配置 -->
          <div
            @click="handleToChangeArror()"
            style="margin: 0 0 10px 90px"
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
                      style="margin-right: 5px;"
                    >
                      <section
                        class="couponImgWrapper"
                        style="line-height:normal"
                      >

                        <div class="coupon_list_top">
                          <span>￥</span>
                          <span class="number">{{item.denomination}}</span>
                        </div>
                        <div class="coupon_center_limit">{{item.useConsumeRestrict | formatLeastConsume(item.leastConsume)}}</div>
                        <div class="coupon_center_number">剩余{{item.surplus}}张</div>
                        <div
                          class="coupon_list_bottom"
                          style="font-size:12px"
                        >领取</div>
                      </section>
                      <span
                        @click="deleteCouponImg(index)"
                        class="deleteIcon"
                      >×</span>
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
                    style="margin-right: 5px;"
                  >
                    <section
                      class="couponImgWrapper"
                      style="line-height: normal"
                    >
                      <div class="coupon_list_top">
                        <span>￥</span>
                        <span class="number">{{item.denomination}}</span>
                      </div>
                      <div class="coupon_center_limit">{{item.useConsumeRestrict | formatLeastConsume(item.leastConsume)}}</div>
                      <div class="coupon_center_number">剩余{{item.surplus}}张</div>
                      <div
                        class="coupon_list_bottom"
                        style="font-size:12px"
                      >领取</div>
                    </section>
                    <span
                      @click="deleteCouponImg2(index)"
                      class="deleteIcon"
                    >×</span>
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
    />
    <!--商品选择-->
    <choosingGoods
      @resultGoodsRow="choosingGoodsResult"
      :tuneUpChooseGoods="tuneUpChooseGoods"
      :chooseGoodsBack="goodsIdList"
      :singleElection="true"
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
      // 点击编辑按钮进来，初始化页面数据
      let SimpleBargainParam = {
        'id': this.$route.query.id
      }
      getBargainByIsd(SimpleBargainParam).then((res) => {
        console.log(res)
        if (res.error === 0) {
          console.log(res, 'res--')
          this.param = res.content
          this.param.effectiveDate = []
          this.param.effectiveDate.push(res.content.startTime)
          this.param.effectiveDate.push(res.content.endTime)
          this.mrkingVoucherObjs = res.content.mrkingVoucherList
          this.rewardCouponObjs = res.content.rewardCouponList
          this.goodsRow.push(res.content.goods)
          let resultConfig = JSON.parse(res.content.shareConfig)
          console.log(resultConfig)
          this.params.shareConfig = resultConfig
          console.log(this.params.shareConfig)
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
    return {
      // 向帮忙砍价的用户赠送优惠券
      mrkingVoucherObjs: [],
      // 砍价失败后向买家赠送优惠券
      rewardCouponObjs: [],
      // 优惠券弹窗区分，1鼓励奖，0好友砍价优惠券
      dialogFlag: 1,
      goodsRow: [],
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
        effectiveDate: '',
        goodsId: 0,
        expectationPrice: 0,
        shareConfig: {
          'shareAction': 1,
          'shareDoc': '',
          'shareImgAction': 1,
          'shareImg': ''
        }
      },
      shareConfig: {
        'shareAction': 1,
        'shareDoc': '',
        'shareImgAction': 1,
        'shareImg': ''
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
      console.log(data)
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
      this.param.goodsId = row.goodsId
      this.goodsRow = []
      this.goodsRow.push(row)
      this.goodsIdList = []
      this.goodsIdList.push(row.goodsId)
      this.param.stock = this.goodsRow[0].goodsNumber
    },
    addSubmit () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.param.shareConfig = this.shareConfig
          this.param.startTime = this.param.effectiveDate[0]
          this.param.endTime = this.param.effectiveDate[1]
          this.param.mrkingVoucherId = this.getCouponIdsString(this.mrkingVoucherObjs)
          this.param.rewardCouponId = this.getCouponIdsString(this.rewardCouponObjs)

          if (this.validParam()) {
            addBargain(this.param).then((res) => {
              if (res.error === 0) {
                this.$message.success(this.$t('marketCommon.successfulOperation'))
                this.$router.push({
                  name: 'bargain'
                })
              } else {
                this.$message.error(res.message)
              }
            })
          }
        }
      })
    },
    updateSubmit () {
      // 更新活动
      this.param.id = this.actId
      this.param.shareConfig = this.shareConfig
      this.param.startTime = this.param.effectiveDate[0]
      this.param.endTime = this.param.effectiveDate[1]
      this.param.mrkingVoucherId = this.getCouponIdsString(this.mrkingVoucherObjs)
      this.param.rewardCouponId = this.getCouponIdsString(this.rewardCouponObjs)
      updateBargain(this.param).then((res) => {
        if (res.error === 0) {
          this.$message.success(this.$t('marketCommon.successfulOperation'))
          this.$router.push({
            name: 'bargain'
          })
        } else {
          this.$message.error(this.$t('marketCommon.failureOperation'))
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
      if (!this.param.goodsId) {
        this.$message.warning(this.$t('addBargainAct.vaildGoodsSelect'))
        return false
      }
      if (!this.param.stock) {
        this.$message.warning(this.$t('addBargainAct.vaildStock'))
        return false
      }
      if (this.param.bargainType === 0) {
        // 砍到指定金额结算：期望参与砍价人次必填；商品首次砍价可砍价百分比区间必填；砍价底价必填
        if (this.param.expectationPrice === '') {
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
        if (this.param.bargainMin === '' || !this.param.bargainMax) {
          this.$message.warning(this.$t('addBargainAct.vaildProportionalinterval1'))
          return false
        }
        if (this.param.bargainMin > this.param.bargainMax) {
          this.$message.warning(this.$t('addBargainAct.vaildProportionalinterval2'))
          return false
        }
      } else {
        // 砍到任意金额结算
        if (this.param.bargainMoneyType === 0) {
          if (!this.param.expectationPrice || this.param.floorPrice === '') {
            this.$message.warning(this.$t('addBargainAct.vaildCalculatedAmount1'))
            return false
          }
          if (this.param.expectationPrice < this.param.floorPrice) {
            this.$message.warning(this.$t('addBargainAct.vaildCalculatedAmount2'))
            return false
          }

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
      .box-card {
        width: 630px;
        background-color: #f5f5f5;
        .middleContainer {
          display: flex;
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
        }
        .addInfo {
          display: inline-block;
          position: relative;
          width: 100px;
          height: 101px;
          margin-bottom: 10px;
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
</style>
