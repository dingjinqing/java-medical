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
          label-width="150px"
          labelPosition='right'
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
            prop=""
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
            prop=""
          >
            <el-date-picker
              v-model="effectiveDate"
              type="datetimerange"
              :range-separator="$t('marketCommon.to')"
              :start-placeholder="$t('marketCommon.startTime')"
              :end-placeholder="$t('marketCommon.endTime')"
              value-format="yyyy-MM-dd HH:mm:ss"
              size="small"
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
            >
              <el-table-column
                prop="goodsName"
                :label="$t('addBargainAct.goodsName')"
                align="center"
              >
                <template slot-scope="scope">
                  <img :src="scope.row.goodsImg">
                  <span>{{scope.row.goodsName}}</span>
                </template>

              </el-table-column>
              <el-table-column
                prop="goodsNumber"
                :label="$t('addBargainAct.goodsOriginalStock')"
                align="center"
              ></el-table-column>
              <el-table-column
                :label="$t('addBargainAct.bargainStock')"
                align="center"
              >
                <template slot-scope="scope">
                  <el-input-number
                    v-model="param.stock"
                    size="small"
                    style="width:120px"
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
              ></el-table-column>
              <el-table-column
                v-if="param.bargainType == 0"
                prop="shopPrice"
                :label="$t('addBargainAct.bargainReservePrice')"
                align="center"
              >
                <template slot-scope="scope">
                  <el-input-number
                    v-model="param.expectationPrice"
                    size="small"
                    style="width:120px"
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
              >
                <template slot-scope="scope">
                  <el-input-number
                    :disabled="isEditFlag"
                    v-model="param.floorPrice"
                    size="small"
                    style="width:120px"
                    :min="0"
                    :max="scope.row.shopPrice"
                  >
                  </el-input-number>
                  {{$t('marketCommon.to')}}
                  <el-input-number
                    :disabled="isEditFlag"
                    v-model="param.expectationPrice"
                    size="small"
                    style="width:120px"
                    :min="0"
                    :max="scope.row.shopPrice"
                  >
                  </el-input-number>
                  ({{$t('addBargainAct.default0')}}){{$t('addBargainAct.sttlementAmountTip')}}
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>

          <!-- 砍到指定金额计算部分内容区域 -->
          <div v-if="this.param.bargainType==0">
            <!-- <el-form-item
              label="帮砍设置："
              prop=""
            >
              <el-checkbox v-model="checked">帮砍好友需要授权手机号才可以参与砍价</el-checkbox>
            </el-form-item> -->

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
                  size="small"
                  style="width:150px"
                  :min="0"
                  :max="50"
                ></el-input-number>&nbsp;%&nbsp;{{$t('marketCommon.to')}}&nbsp;
                <el-input-number
                  v-model="param.bargainMax"
                  size="small"
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
                    size="small"
                    style="width:150px"
                  ></el-input-number>{{$t('marketCommon.yuan')}}
                </el-radio>
                <br>
                <el-radio :label='1'>{{$t('addBargainAct.randomMoney')}}
                  <el-input-number
                    v-model="param.bargainMinMoney"
                    size="small"
                    style="width:150px"
                  ></el-input-number>{{$t('marketCommon.yuan')}}
                  <span>{{$t('marketCommon.to')}}</span>
                  <el-input-number
                    v-model="param.bargainMaxMoney"
                    size="small"
                    style="width:150px"
                  ></el-input-number>{{$t('marketCommon.yuan')}}{{$t('addBargainAct.getRandomMoneyBetween')}}
                </el-radio>
              </el-radio-group>
            </el-form-item>

            <!-- <el-form-item
              label="帮砍设置："
              prop=""
            >
              <el-checkbox v-model="checked">帮砍好友需要授权手机号才可以参与砍价</el-checkbox>
            </el-form-item> -->

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

          <!-- 公共更多配置模块部分 -->
          <el-form-item
            :label="$t('addBargainAct.friendsBargainCoupon')+':'"
            prop=""
          >
            <el-card class="box-card">
              <div class="fontColor">{{$t('addBargainAct.friendsBargainCouponTip')}}</div>
              <span>{{mrkingVoucherObjs}}</span>
              <div
                @click="handleToCallDialog1()"
                class="addInfo"
              >
                <img
                  :src="srcList.src3"
                  alt=""
                >
                <p class="fontColor">{{$t('addBargainAct.addCoupon')}}</p>
              </div>
              <div class="fontColor">{{$t('addBargainAct.couponLimitTip')}}</div>
            </el-card>
          </el-form-item>

          <el-form-item
            :label="$t('addBargainAct.encouragementAward')+':'"
            prop=""
          >
            <el-card class="box-card">
              <div class="fontColor">{{$t('addBargainAct.encouragementAwardTip')}}</div>
              <span>{{rewardCouponObjs}}</span>
              <div
                @click="handleToCallDialog2()"
                class="addInfo"
              >
                <img
                  :src="srcList.src3"
                  alt=""
                >
                <p class="fontColor">{{$t('addBargainAct.addCoupon')}}</p>
              </div>
              <!-- <addCoupon /> -->
              <div class="fontColor">{{$t('addBargainAct.couponLimitTip')}}</div>
            </el-card>
          </el-form-item>

          <!-- 引入活动分享模块 -->
          <actShare :shareConfig="shareConfig" />

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
    <!--添加优惠卷-->
    <AddCouponDialog
      @handleToCheck="handleToCheck"
      :tuneUpCoupon="showCouponDialog"
      :couponBack="couponIdList"
    />
    <!--商品选择-->
    <choosingGoods @resultGoodsRow="choosingGoodsResult" />
  </div>
</template>

<script>
import addCoupon from './addCoupon'
import actShare from '@/components/admin/marketActivityShareSetting'
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
          this.param = res.content
          this.effectiveDate = []
          this.effectiveDate.push(res.content.startTime)
          this.effectiveDate.push(res.content.endTime)
          this.mrkingVoucherObjs = res.content.mrkingVoucherList
          this.rewardCouponObjs = res.content.rewardCouponList
          this.goodsRow.push(res.content.goods)
        }
      })
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
      effectiveDate: '',
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
        floorPrice: 0
      },
      shareConfig: {
        'share_action': 1,
        'share_doc': '',
        'share_img_action': 1,
        'share_img': ''
      },
      isEditFlag: false,
      actId: null,

      showCouponDialog1: false,
      showCouponDialog2: false,
      couponIdList: [],
      showCouponDialog: false
    }
  },
  methods: {
    // 选择优惠券弹窗-帮忙砍价的用户
    handleToCallDialog1 () {
      this.dialogFlag = 0
      this.couponIdList = this.getCouponIdsArray(this.mrkingVoucherObjs)
      console.log(this.couponIdList)
      this.showCouponDialog = !this.showCouponDialog
    },
    // 选择优惠券弹窗-砍价失败后向买家赠送
    handleToCallDialog2 () {
      this.dialogFlag = 1
      this.couponIdList = this.getCouponIdsArray(this.rewardCouponObjs)
      console.log(this.couponIdList)
      this.showCouponDialog = !this.showCouponDialog
    },
    // 确认选择优惠券-新增-删除
    handleToCheck (data) {
      console.log(data)
      if (this.dialogFlag === 1) {
        this.rewardCouponObjs = data
      } else {
        this.mrkingVoucherObjs = data
      }
    },
    // 选择商品弹窗
    showChoosingGoods () {
      this.$http.$emit('choosingGoodsFlag', true, 'choiseOne')
    },
    //  选择商品弹窗确认
    choosingGoodsResult (row) {
      this.param.goodsId = row.goodsId
      this.goodsRow = []
      this.goodsRow.push(row)
      this.param.stock = this.goodsRow[0].goodsNumber
    },
    addSubmit () {
      this.param.shareConfig = this.shareConfig
      this.param.startTime = this.effectiveDate[0]
      this.param.endTime = this.effectiveDate[1]
      this.param.mrkingVoucherId = this.getCouponIdsString(this.mrkingVoucherObjs)
      this.param.rewardCouponId = this.getCouponIdsString(this.rewardCouponObjs)
      addBargain(this.param).then((res) => {
        if (res.error === 0) {
          this.$message({
            type: 'success',
            message: this.$t('marketCommon.successfulOperation')
          })
          this.$router.push({
            name: 'bargain'
          })
        } else {
          this.$message({
            type: 'fail',
            message: this.$t('marketCommon.failureOperation')
          })
        }
      })
    },
    updateSubmit () {
      // 更新活动
      this.param.id = this.actId
      this.param.shareConfig = this.shareConfig
      this.param.startTime = this.effectiveDate[0]
      this.param.endTime = this.effectiveDate[1]
      this.param.mrkingVoucherId = this.getCouponIdsString(this.mrkingVoucherObjs)
      this.param.rewardCouponId = this.getCouponIdsString(this.rewardCouponObjs)
      updateBargain(this.param).then((res) => {
        if (res.error === 0) {
          this.$message({
            type: 'success',
            message: this.$t('marketCommon.successfulOperation')
          })
          this.$router.push({
            name: 'bargain'
          })
        } else {
          this.$message({
            type: 'fail',
            message: this.$t('marketCommon.failureOperation')
          })
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
    }
  }
}

</script>
<style lang="scss" scoped>
.bargainAct {
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  .bargainContent {
    padding: 10px;
    .bargainActMain {
      position: relative;
      background-color: #fff;
      padding: 10px 20px;
      .box-card {
        width: 630px;
        background-color: #f5f5f5;
        .addInfo {
          display: inline-block;
          width: 100px;
          height: 98px;
          margin-bottom: 10px;
          background: #fff;
          border: 1px solid #e4e4e4;
          padding: 13px 0;
          cursor: pointer;
          text-align: center;
          img {
            margin-top: 10px;
          }
          p {
            margin-top: -15px;
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
    width: 100%;
    height: 50px;
    padding: 10px 0;
    background: #f8f8f8;
    text-align: center;
  }
}
</style>
