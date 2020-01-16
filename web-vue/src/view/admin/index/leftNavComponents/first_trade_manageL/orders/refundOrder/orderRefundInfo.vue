<template>
  <div class="content">
    <div class="main">
      <div class="item_box">
        <h2 class="h2_title">{{returnTypeMap.get(returnInfo.returnType) + $t('order.applyText')}}</h2>
        <div class="refund_status_desc">
          <template v-if="returnInfo.returnType == 1">
            <template v-if="returnInfo.refundStatus == 1">
              <div class="refund_description">
                <h3>
                  {{$t('order.refundNote_1_1_1')}}
                </h3>
                <div class="tips">{{$t('order.refundNote_1_1_2')}}</div>
                <div class="tips">{{$t('order.refundNote_1_1_3')}}</div>
                <div
                  v-if="autoTime != null"
                  class="tips"
                  v-html="$t('order.refundNote_1_1_4',[showCountdown])"
                >
                </div>
              </div>
              <div class="btn_box">
                <el-button
                  type="primary"
                  size="small"
                  @click="showRefund"
                >{{$t('order.refundBtn_1_1_1')}}</el-button>
                <el-button
                  type="default"
                  size="small"
                  @click="showRefusal"
                >{{$t('order.refundBtn_1_1_2')}}</el-button>
              </div>
            </template>
            <template v-else-if="returnInfo.refundStatus == 2">
              <div class="refund_description">
                <h3>
                  {{$t('order.refundNote_1_2_1')}}
                </h3>
                <div class="tips">{{$t('order.refundNote_1_2_2')}}</div>
                <div
                  v-if="autoTime != null"
                  class="tips"
                  v-html="$t('order.refundNote_1_2_3',[showCountdown])"
                >
                </div>
              </div>
              <div class="btn_box">
                <el-button
                  type="primary"
                  size="small"
                  @click="showRefund"
                >{{$t('order.refundBtn_1_2_1')}}</el-button>
                <el-button
                  type="default"
                  size="small"
                  @click="showRefusal"
                >{{$t('order.refundBtn_1_2_2')}}</el-button>
              </div>
            </template>
            <template v-else-if="returnInfo.refundStatus == 3">
              <div class="refund_description">
                <h3>
                  {{$t('order.refundNote_1_3_1')}}
                </h3>
              </div>
            </template>
            <template v-else-if="returnInfo.refundStatus == 4">
              <div class="refund_description">
                <h3>
                  {{$t('order.refundNote_1_4_1')}}
                </h3>
                <div class="tips">{{$t('order.refundNote_1_4_2',[todo])}}</div>
                <div
                  v-if="autoTime != null"
                  class="tips"
                  v-html="$t('order.refundNote_1_2_3',[showCountdown])"
                >
                </div>
              </div>
              <div class="btn_box">
                <el-button
                  type="primary"
                  size="small"
                  @click="showRefund"
                >{{$t('order.refundBtn_1_4_1')}}</el-button>
                <el-button
                  type="default"
                  size="small"
                  @click="showRefusal"
                >{{$t('order.refundBtn_1_4_2')}}</el-button>
              </div>
            </template>
            <template v-else-if="returnInfo.refundStatus == 5">
              <div class="refund_description">
                <h3>
                  {{$t('order.refundNote_1_5_1')}}
                </h3>
              </div>
            </template>
            <template v-else-if="returnInfo.refundStatus == 6">
              <div class="refund_description">
                <h3>
                  {{$t('order.refundNote_1_6_1')}}
                </h3>
              </div>
            </template>
            <template v-else-if="returnInfo.refundStatus == 7 && returnInfo.operatorLastType == 1">
              <div class="refund_description">
                <h3>
                  {{$t('order.refundNote_1_7_1')}}
                </h3>
              </div>
            </template>
            <template v-else-if="returnInfo.refundStatus == 7 && returnInfo.operatorLastType == 2">
              <div class="refund_description">
                <h3>
                  {{$t('order.refundNote_1_8_1')}}
                </h3>
                <div class="tips">{{$t('order.refundNote_1_8_2')}}</div>
              </div>
            </template>
          </template>
          <template v-else>
            <template v-if="returnInfo.refundStatus == 4">
              <div class="refund_description">
                <h3>
                  {{$t('order.refundNote_4_1')}}
                </h3>
                <div class="tips">{{$t('order.refundNote_4_2')}}</div>
                <div class="tips">{{$t('order.refundNote_4_3')}}</div>
                <div
                  v-if="autoTime != null"
                  class="tips"
                  v-html="$t('order.refundNote_4_4',[showCountdown])"
                >
                </div>
              </div>
              <div class="btn_box">
                <el-button
                  type="primary"
                  size="small"
                  @click="showRefund"
                >{{$t('order.refundBtn_4_1',[returnTypeMap.get(returnInfo.returnType)])}}</el-button>
                <el-button
                  type="default"
                  size="small"
                  @click="showRefusal"
                >{{$t('order.refundBtn_4_2',[returnTypeMap.get(returnInfo.returnType)])}}</el-button>
              </div>
            </template>
            <template v-else-if="returnInfo.refundStatus == 5">
              <div class="refund_description">
                <h3>
                  {{$t('order.refundNote_5_1',[returnTypeMap.get(returnInfo.returnType)])}}
                </h3>
              </div>
            </template>
            <template v-else-if="returnInfo.refundStatus == 6">
              <div class="refund_description">
                <h3>
                  {{$t('order.refundNote_6_1',[returnTypeMap.get(returnInfo.returnType)])}}
                </h3>
              </div>
            </template>
            <template v-else-if="returnInfo.refundStatus == 7 && returnInfo.operatorLastType == 1">
              <div class="refund_description">
                <h3>
                  {{$t('order.refundNote_7_1',[returnTypeMap.get(returnInfo.returnType)])}}
                </h3>
              </div>
            </template>
            <template v-else-if="returnInfo.refundStatus == 7 && returnInfo.operatorLastType == 2">
              <div class="refund_description">
                <h3>
                  {{$t('order.refundNote_8_1')}}
                </h3>
                <div class="tips">{{$t('order.refundNote_8_2',[returnTypeMap.get(returnInfo.returnType)])}}</div>
              </div>
            </template>
          </template>
        </div>

      </div>
      <div class="item_box">
        <h2 class="h2_title">{{returnTypeMap.get(returnInfo.returnType)}}{{$t('order.applicationDetails')}}</h2>
        <table class="refund_info_table">
          <tbody>
            <tr>
              <td>{{$t('order.orderSn')}}</td>
              <td>{{returnInfo.orderSn}}</td>
              <td>{{$t('order.returnSn')}}</td>
              <td>{{returnInfo.returnOrderSn}}</td>
            </tr>
            <tr>
              <td>{{$t('order.returnOrderGoods')}}</td>
              <td colspan="3">
                <template v-for="goods in returnInfo.returnGoods">
                  <div
                    class="goods_info"
                    :key="goods.recId"
                  >
                    <img
                      :src="$imageHost + '/' + goods.goodsImg"
                      alt=""
                    >
                    <div class="goods_info_right">
                      <div class="goods_name">{{goods.goodsName}}</div>
                      <div class="goods_spec">{{goods.goodsAttr}}</div>
                      <div class="goods_price_box"><span class="goods_price">
                          {{getCurrencyPool_1 + goods.goodsPrice.toFixed(2)}}
                        </span>X{{goods.goodsNumber}}</div>
                    </div>
                  </div>
                </template>

              </td>
            </tr>
            <tr>
              <td>{{$t('order.returnType')}}</td>
              <td colspan="3">{{returnTypeMap.get(returnInfo.returnType)}}</td>
            </tr>
            <tr>
              <td>{{$t('order.returnStatus')}}</td>
              <td colspan="3">{{
                 returnStatusToShowMapping[returnInfo.refundStatus + ''] == null ?
                 returnStatusToShowMapping[returnInfo.refundStatus + '-' + returnInfo.returnType] :
                 returnStatusToShowMapping[returnInfo.refundStatus + '']
                }}</td>
            </tr>
            <tr>
              <td>{{$t('order.returnMoney')}}</td>
              <td
                colspan="3"
                class="refund_info_td"
              >
                <template v-if="returnInfo.refundStatus == 2 || returnInfo.refundStatus == 4">
                  <p class="refund_info"> {{$t('order.refundGoodsPrice')}}：
                    <el-input-number
                      v-model="returnMoney"
                      :precision="2"
                      :controls="false"
                      :min="0"
                      :max="returnInfo.money"
                      size="small"
                      controls-position="right"
                    ></el-input-number>
                    {{
                         getCurrencyPool_0 + '，' +
                         $t('order.returnShippingFee')
                      }}：
                    <el-input-number
                      v-model="shippingFee"
                      size="small"
                      controls-position="right"
                      :precision="2"
                      :controls="false"
                      :min="0"
                      :max="returnInfo.canReturnShippingFee"
                    ></el-input-number>
                    {{
                        getCurrencyPool_0 + '，' + $t('order.maxRefundShippingFee') +
                        (returnInfo.canReturnShippingFee == null ? 0.00 : returnInfo.canReturnShippingFee.toFixed(2) +
                        getCurrencyPool_0)
                      }}
                  </p>
                  <p>
                    {{$t('order.totalRefundPrice') + '：' + getCurrencyPool_1}}
                    <span class="text-warning refund-money">{{refundtotalPrice.toFixed(2)}}</span>
                    =
                    <template v-if="returnInfo.orderInfo.bkOrderMoney > 0">
                      <span>{{$t('order.bk_order_money') + '：' + getCurrencyPool_1}}
                        <span class="text-warning refund-bk-order-money">{{bk_order_money.toFixed(2)}}</span> +
                      </span>
                    </template>
                    {{$t('order.member_card_balance') + '：' + getCurrencyPool_1}}
                    <span class="text-warning refund-member-card-money">{{member_card_balance.toFixed(2)}}</span> +
                    {{$t('order.use_account') + '：' + getCurrencyPool_1}}
                    <span class="text-warning refund-balance-money">{{refund_balance_money.toFixed(2)}}</span> +
                    {{$t('order.score_discount') + '：' + getCurrencyPool_1}}
                    <span class="text-warning refund-score-money">{{refund_score_money.toFixed(2)}}</span> +
                    {{$t('order.money_paid') + '：' + getCurrencyPool_1}}
                    <span class="text-warning refund-pay-money">{{refund_pay_money.toFixed(2)}}</span>
                  </p>
                  <p class="tips">{{$t('order.refundTips')}}</p>
                </template>
                <template v-else>
                  <p class="refund_info">{{$t('order.refundGoodsPrice')}}：<span class="text-warning"> {{getCurrencyPool_1 + returnInfo.money}}</span></p>
                  <p class="refund_info">{{$t('order.shippingFee')}}：<span class="text-warning">{{getCurrencyPool_1 + returnInfo.shippingFee}}</span></p>

                  <span
                    v-if="returnInfo.refundStatus == 5"
                    class="refund_info"
                  >
                    {{$t('order.returnInfo')}}：
                    <span
                      v-for="(value,key,index) in returnInfo.calculateMoney"
                      class="refund_info"
                      :key="index"
                    >
                      <template v-if="value > 0">
                        <span class="refund_info">
                          {{$t('order')[key]}}:
                        </span>
                        <span class="text-warning">
                          {{getCurrencyPool_1 + value.toFixed(2)}}
                        </span>
                      </template>
                    </span>
                  </span>
                </template>
              </td>
            </tr>
            <template v-if="returnInfo.refundStatus == 4 && returnInfo.returnType == 1">
              <tr>
                <td>{{$t('order.customerPhone')}}</td>
                <td colspan="3">
                  {{returnInfo.phone}}
                </td>
              </tr>
              <tr>
                <td>{{$t('order.shippingCompany')}}</td>
                <td colspan="3">
                  {{$t('expressList.company')[returnInfo.shippingType]}}
                </td>
              </tr>
              <tr>
                <td>{{$t('order.shippingNo')}}</td>
                <td colspan="3">
                  {{returnInfo.shippingNo}}
                  <a
                    class="text-info"
                    target="blank"
                    :href=toShippingView
                  >{{$t('order.select')}}</a>
                </td>
              </tr>
            </template>
            <tr>
              <td>{{$t('order.refundReason')}}</td>
              <td colspan="3">
                {{$t('order.reasonTypeList')[returnInfo.reasonType]}}
              </td>
            </tr>
            <tr>
              <td><span class="w2">{{$t('order.description')}}</span></td>
              <td colspan="3">
                {{returnInfo.reasonDesc}}
              </td>
            </tr>
            <tr>
              <td>{{$t('order.returnImage')}}</td>
              <td colspan="3">
                <div class="image_box">
                  <template>
                    <img
                      v-for="(image , index) in getReturnImageArray"
                      :src="$imageHost + '/' + image"
                      alt=""
                      :key="index"
                    />
                  </template>
                </div>
              </td>
            </tr>
            <tr>
              <td>{{$t('order.applyTime')}}</td>
              <td colspan="3">
                {{
                  returnInfo.returnType != 1 ?
                  returnInfo.shippingOrRefundTime:
                  returnInfo.applyTime
                }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div
        class="item_box"
        v-if=" 1 != 1"
      >
        <h2 class="h2_title">{{$t('order.helpBuyInfo')}}</h2>
        <template>
          <el-table
            :data="returnInfo.helpBuyInfo"
            style="width: 100%"
          >
            <el-table-column
              prop="helpBuyPayNumbeer"
              :label="$t('order.helpBuyPayNumbeer')"
              width="180"
            >
            </el-table-column>
            <el-table-column
              prop="payTime"
              :label="$t('order.payTime')"
              width="180"
            >
            </el-table-column>
            <el-table-column
              prop="helpBuyUserName"
              :label="$t('order.helpBuyUserName')"
            ></el-table-column>
            <el-table-column
              prop="helpBuyMoney"
              :label="$t('order.helpBuyMoney')"
              width="180"
            >
            </el-table-column>
            <el-table-column
              prop="returnMoney"
              :label="$t('order.returnMoney')"
              width="180"
            >
            </el-table-column>
            <el-table-column
              prop="finishTime"
              :label="$t('order.finishTime')"
            >
            </el-table-column>
          </el-table>
        </template>
      </div>
      <div class="item_box">
        <h2 class="h2_title">{{$t('order.negotiationRecord')}}</h2>
        <template v-for="(record , index ) in returnInfo.operatorRecord">
          <div
            class="recode"
            :key="index"
          >
            <div class="top">
              <ul>
                <li>{{$t('order.recordTypeObj')[record.type]}}</li>
                <li>{{record.createTime}}</li>
                <li>
                  {{recordLogicStatus[record.logicStatus]}}
                </li>
              </ul>
            </div>
            <div class="bottom">
              <ul>
                <template v-if="record.logicStatus == 1">
                  <li>{{$t('order.returntype')}}：{{returnTypeMap.get(returnInfo.returnType)}}</li>
                  <li>{{$t('order.returnReasonText')}}：{{$t('order.reasonTypeList')[returnInfo.reasonType]}}</li>
                  <li>{{$t('order.returnMoney')}}：{{(returnInfo.money + returnInfo.shippingFee).toFixed(2)}}</li>
                  <li>{{$t('order.returnDescription')}}：{{returnInfo.reasonDesc}}</li>
                </template>
                <template v-else-if="record.logicStatus == 2">
                  <li>{{recordLogicStatus[record.logicStatus]}}</li>
                  <li>{{$t('order.refusedReason') + '：' + returnInfo.refundRefuseReason}}</li>
                </template>
                <template v-else-if="record.logicStatus == 3">
                  <li>{{recordLogicStatus[record.logicStatus]}}</li>
                  <li>{{$t('order.returnSuccessNote')}}</li>
                </template>
                <template v-else-if="record.logicStatus == 4">
                  <li>{{$t('order.passReturnApplyNote')}}</li>
                  <li>{{$t('order.passReturnApplyNote1',{ returnAddress: returnInfo.returnAddress, consignee:returnInfo.consignee,merchantTelephone:returnInfo.merchantTelephone})}}</li>
                </template>
                <template v-else-if="record.logicStatus == 5">
                  <li>{{$t('order.refusedReturngoodsApply')}}</li>
                </template>
                <template v-else-if="record.logicStatus == 6">
                  <li>{{$t('order.autoCancelNote')}}</li>
                </template>
                <template v-else-if="record.logicStatus == 7">
                  <li>{{$t('order.buyersCancel')}}</li>
                </template>
                <template v-else-if="record.logicStatus == 8">
                  <li>{{$t('order.submitLogisticsNote')}}</li>
                  <li>{{$t('order.shippingCompany') + '：' + $t('expressList.company')[returnInfo.shippingType]}}</li>
                  <li>
                    {{$t('order.shippingNo') + '：' + returnInfo.shippingNo}}
                    <a
                      class="text-info"
                      target="blank"
                      :href=toShippingView
                    >{{$t('order.select')}}
                    </a>
                  </li>
                  <li>{{$t('order.phone') + '：' + returnInfo.phone}}</li>
                  <li>{{$t('order.logisticsVoucherImages') + '：'}}
                    <a
                      v-for="(image , index) in getVoucherImages"
                      :href="$imageHost + '/' + image"
                      :title="$t('order.showBigImage')"
                      target="_blank"
                      :key="index"
                    >
                      <img
                        :src="$imageHost + '/' + image"
                        alt=""
                        style='max-width: 50px;max-height: 50px'
                      />
                    </a>
                  </li>
                </template>
                <template v-else-if="record.logicStatus == 9">
                  <li>{{recordLogicStatus[record.logicStatus]}}</li>
                </template>
              </ul>
            </div>
          </div>
        </template>

      </div>
    </div>
    <el-dialog
      title="请输入拒绝理由"
      :visible.sync="refusal"
      custom-class="custom"
      width="30%"
    >
      <el-input
        type="textarea"
        v-model="refusalInfo"
        placeholder="请输入内容"
        resize="none"
        rows="5"
      ></el-input>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="refusal = false">取 消</el-button>
        <el-button
          type="primary"
          @click="refusalConfirm"
        >确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="确认退货完成"
      :visible.sync="refundDialog"
      custom-class="custom"
      width="30%"
    >
      <div>
        <template v-if="returnInfo.returnType == 1 && returnInfo.refundStatus == 1">
          <el-form
            label-position="right"
            label-width="80px"
            :model="returnAddressInfo"
            ref="returnAddressInfoForm"
          >
            <el-form-item
              :label="$t('order.consignee')"
              prop="consignee"
            >
              <el-input
                v-model="returnAddressInfo.consignee"
                size="small"
              ></el-input>
            </el-form-item>
            <el-form-item
              :label="$t('order.shippingAddress')"
              prop="returnAddress"
            >
              <el-input
                v-model="returnAddressInfo.returnAddress"
                size="small"
              ></el-input>
            </el-form-item>
            <el-form-item
              :label="$t('order.phone')"
              prop="merchantTelephone"
            >
              <el-input
                v-model="returnAddressInfo.merchantTelephone"
                size="small"
              ></el-input>
            </el-form-item>
            <el-form-item
              label="邮编"
              prop="zipCode"
            >
              <el-input
                v-model="returnAddressInfo.zipCode"
                size="small"
              ></el-input>
            </el-form-item>
          </el-form>
        </template>
        <template v-else-if="returnInfo.refundStatus == 4 || returnInfo.refundStatus == 2">
          <span style="display:inline-block;width: 100%;">
            提醒：
            <span>请在 <span style="color: #f66;">确认已收到退货实物或者协商通过后再操作退货完成，本操作会同步退款给顾客(货款退回到顾客支付的账户)，请谨慎操作</span></span>
          </span>
          <span>
            由于支付渠道（如跨行退款）的延迟，会在 <span style="color: #f66;">3个工作日内到账，</span>
            <P>您确定要对订单操作退货完成吗？</P>
          </span>
        </template>
        <template v-else>
          <span style="display:inline-block;width: 100%;">
            提醒：
            <span style="color: #f66;">退款操作会把订单金额全部退回给顾客付款账户，</span>
          </span>-
          <span>
            由于支付渠道（如跨行退款）的延迟，会在 <span style="color: #f66;">3个工作日内到账，</span>
            <p>您确定要对订单退款吗？</p>
          </span>
        </template>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="refundDialog = false">取 消</el-button>
        <el-button
          type="primary"
          @click="refundConfirm"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  returnInfo, handleReturnInfo
} from '@/api/admin/orderManage/order.js'
export default {
  data () {
    return {
      returnTypeMap: null,
      searchParam: {
        returnOrderSn: null
      },
      returnInfo: null,
      returnStatusToShowMapping: null,
      recordLogicStatus: null,
      negotiationMap: null,
      autoTime: null,
      showCountdown: null,
      refundGoodsMoney: null,
      refundFreight: null,
      countDownStr: '',
      start: 1571554186,
      refusalInfo: null,
      refusal: false,
      refundDialog: false,
      returnAddressInfo: {
        consignee: null,
        returnAddress: null,
        merchantTelephone: null,
        zipCode: null
      },
      returnMoney: 0,
      shippingFee: 0,
      returnAmountMap: {
        bk_order_money: 0,
        member_card_balance: 0,
        use_account: 0,
        score_discount: 0,
        money_paid: 0
      },
      member_card_balance: 0.00,
      refund_balance_money: 0.00,
      refund_score_money: 0.00,
      refund_pay_money: 0.00,
      bk_order_money: 0.00
    }
  },
  created () {
    this.search(this.$route.query.returnOrderSn)
  },
  mounted: function () {
    this.langDefault()
  },
  destroyed () {
    clearInterval(this.timer)
  },
  methods: {
    countdown: function (interval) {
      this.timer = setInterval(() => {
        interval -= 1000
        if (interval <= 0) interval = 0
        let day = Math.floor(interval / (24 * 3600 * 1000))
        let hoursInterval = interval % (24 * 3600 * 1000)
        let hours = Math.floor(hoursInterval / (3600 * 1000))
        let minutesInterval = hoursInterval % (3600 * 1000)
        let minutes = Math.floor(minutesInterval / (60 * 1000))
        let secondsInterval = minutesInterval % (60 * 1000)
        let seconds = Math.round(secondsInterval / 1000)
        this.showCountdown = `${day}${this.$t('order.day')}${hours}${this.$t('order.hours')}${minutes}${this.$t('order.minutes')}${seconds}${this.$t('order.seconds')}`
        console.log(interval)
        console.log(seconds)
        console.log(this.showCountdown)
      }, 1000)
    },
    showRefund () {
      this.refundDialog = true
      this.$refs.returnAddressInfoForm.resetFields()
    },
    showRefusal () {
      this.refusal = true
      this.refusalInfo = null
    },
    refundConfirm () {
      this.handleReturn()
    },
    refusalConfirm () {
      this.handleReturn('refusal')
    },
    handleReturn (target = null) {
      let returnOperate = null
      if ((this.returnInfo.refundStatus === 4 || this.returnInfo.refundStatus === 2) && target) {
        returnOperate = 2
      } else if (this.returnInfo.returnType === 1 && this.returnInfo.refundStatus === 1 && !target) {
        returnOperate = 3
      } else if (this.returnInfo.returnType === 1 && this.returnInfo.refundStatus === 1 && target) {
        returnOperate = 4
      }
      if (this.returnInfo.refundStatus === 1 && !(this.returnAddressInfo.consignee && this.returnAddressInfo.returnAddress && this.returnAddressInfo.merchantTelephone && this.returnAddressInfo.zipCode)) {
        this.$message.error('请输入卖家收货所需内容')
        return
      }
      let obj = {
        orderId: this.returnInfo.orderId,
        orderSn: this.returnInfo.orderSn,
        action: 1,
        returnOperate: returnOperate,
        returnMoney: this.returnMoney,
        shippingFee: this.shippingFee,
        reasonDesc: this.refusalInfo,
        returnType: this.returnInfo.returnType,
        retId: this.returnInfo.retId,
        ...this.returnAddressInfo
      }
      handleReturnInfo(obj).then(res => {
        console.log(res)
        this.search(this.$route.query.returnOrderSn)
        this.refundDialog = false
        this.refusal = false
      })
    },
    search (returnOrderSn) {
      this.searchParam.returnOrderSn = returnOrderSn
      returnInfo(this.searchParam).then(res => {
        this.returnInfo = res.content
        this.returnMoney = this.returnInfo.money
        this.shippingFee = this.returnInfo.shippingFee
        this.returnAmountMap = res.content.calculateMoney
        this.setRecordLogicStatus(this.returnInfo.operatorRecord)
        this.setAutoTime()
      }).catch(() => {
      })
    },
    setRecordLogicStatus (operatorRecord) {
      operatorRecord.forEach(record => {
        if (record.status === 4 || (returnInfo.returnType === 0 && record.status === 1)) {
          // 发起申请
          record.logicStatus = 1
        } else if (record.status === 6 && returnInfo.returnType === 0) {
          // 商家拒绝退款申请
          record.logicStatus = 2
        } else if (record.status === 5) {
          // 退款成功
          record.logicStatus = 3
        } else if (record.status === 2) {
          // 商家同意退货退款
          record.logicStatus = 4
        } else if (record.status === 2) {
          // 商家审核不通过
          record.logicStatus = 5
        } else if (record.status === 7 && record.type === 2) {
          // 系统自动撤销退款
          record.logicStatus = 6
        } else if (record.status === 7 && record.type === 1) {
          // 买家撤销
          record.logicStatus = 7
        } else if (record.status === 4 && this.returnInfo.returnType === 1) {
          // 提交物流
          record.logicStatus = 8
        } else if (record.status === 6 && this.returnInfo.returnType === 1) {
          // 提交物流
          record.logicStatus = 9
        }
      })
    },
    setAutoTime () {
      if (this.returnInfo.returnMoneyDays != null) {
        this.autoTime = this.returnInfo.returnMoneyDays
      }
      if (this.returnInfo.returnAddressDays != null) {
        this.autoTime = this.returnInfo.returnAddressDays
      }
      if (this.returnInfo.returnShoppingDays != null) {
        this.autoTime = this.returnInfo.returnShoppingDays
      }
      if (this.returnInfo.returnAuditPassNotShoppingDays != null) {
        this.autoTime = this.returnInfo.returnAuditPassNotShoppingDays
      }
      this.countdown(this.autoTime)
    },
    submit () {

    },
    arrayToMap () {
      this.returnTypeMap = new Map(this.$t('order.returnTypeList'))
      this.returnStatusToShowMapping = {
        '1': this.$t('order.returnStatusMapping_1'),
        '2': this.$t('order.returnStatusList')[3][1],
        '3': this.$t('order.returnStatusList')[2][1],
        '4-0': this.$t('order.returnStatusMapping_4_0'),
        '4-1': this.$t('order.returnStatusList')[4][1],
        '6-0': this.$t('order.returnStatusList')[6][1],
        '6-1': this.$t('order.returnStatusList')[5][1],
        '7': this.$t('order.returnStatusList')[7][1],
        '5': this.$t('order.returnStatusList')[8][1]
      }
      this.recordLogicStatus = {
        1: this.$t('order.apply'),
        2: this.$t('order.refusedApply'),
        3: this.$t('order.returnStatusList')[8][1],
        4: this.$t('order.passReturnApply'),
        5: this.$t('order.returnStatusList')[2][1],
        6: this.$t('order.autoCancel'),
        7: this.$t('order.buyersCancel'),
        8: this.$t('order.submitLogistics'),
        9: this.$t('order.refusedReturnGoods')
      }
    }
  },
  watch: {
    lang () {
      this.langDefault()
      this.arrayToMap()
    },
    refundtotalPrice (newVal) {
      let totalPrice = newVal
      this.bk_order_money = totalPrice > this.returnAmountMap.bk_order_money ? this.returnAmountMap.bk_order_money : totalPrice
      totalPrice -= this.bk_order_money

      this.member_card_balance = totalPrice > this.returnAmountMap.member_card_balance ? this.returnAmountMap.member_card_balance : totalPrice
      totalPrice -= this.member_card_balance

      this.refund_balance_money = totalPrice > this.returnAmountMap.use_account ? this.returnAmountMap.use_account : totalPrice
      totalPrice -= this.refund_balance_money

      this.refund_score_money = totalPrice > this.returnAmountMap.score_discount ? this.returnAmountMap.score_discount : totalPrice
      totalPrice -= this.refund_score_money

      this.refund_pay_money = totalPrice > this.returnAmountMap.money_paid ? this.returnAmountMap.money_paid : totalPrice
      totalPrice -= this.refund_pay_money
    }
  },
  computed: {
    getCurrencyPool_0 () {
      // eg:元
      return this.currencyPool[this.returnInfo.currency][this.lang][0]
    },
    getCurrencyPool_1 () {
      // eg:￥
      return this.currencyPool[this.returnInfo.currency][this.lang][1]
    },
    getReturnImageArray () {
      return JSON.parse(this.returnInfo.goodsImages)
    },
    getVoucherImages () {
      return this.returnInfo.voucherImages
    },
    toShippingView () {
      return 'https://www.kuaidi100.com/chaxun?com=' + this.returnInfo.shippingCode + '&nu=' + this.returnInfo.shippingNo
    },
    refundtotalPrice () {
      return this.returnMoney + this.shippingFee
    }
  }
}
</script>

<style lang="scss" scoped>
.content {
  padding: 10px;
  .main {
    background-color: #fff;
    padding: 10px;
    .item_box {
      .h2_title {
        font-size: 14px;
        padding-left: 12px;
        background: #f5f5f5;
        line-height: 36px;
        border: 1px solid #ddd;
        border-bottom: 0;
        color: #333;
        font-weight: 600;
      }
      .refund_status_desc {
        border: 1px solid #ddd;
        padding: 16px 30px;
        position: relative;
        display: flex;
        justify-content: space-between;
        align-items: center;
        .refund_description {
          line-height: 24px;
          > h3 {
            font-weight: 600;
          }
          > .tips {
            font-size: 14px;
            color: #666;
          }
        }
        .btn_box {
          display: flex;
        }
      }
      .refund_info_table {
        width: 100%;
        font-size: 14px;
        > tbody {
          > tr {
            &:first-child {
              > td {
                &:nth-child(odd) {
                  width: 150px;
                  color: #666;
                  text-align: center;
                }
              }
            }
            > td {
              color: #666;
              padding: 8px 10px;
              border: 1px solid #ddd;
              vertical-align: middle;
              &:first-child {
                width: 150px;
                color: #666;
                text-align: center;
              }
              .w2 {
                letter-spacing: 28px;
                margin-right: -28px;
              }
              .goods_info {
                display: flex;
                & + .goods_info {
                  margin-top: 10px;
                }
                > img {
                  width: 90px;
                  height: 90px;
                  margin-right: 10px;
                }
                > .goods_info_right {
                  flex: 1;
                  display: flex;
                  flex-direction: column;
                  justify-content: space-between;
                  > div {
                    overflow: hidden;
                    text-overflow: ellipsis;
                    display: -webkit-box;
                    -webkit-line-clamp: 2;
                    overflow: hidden;
                    /*! autoprefixer: off */
                    -webkit-box-orient: vertical;
                    text-align: left;
                    .goods_price {
                      color: #ff6600;
                      margin-right: 10px;
                    }
                  }
                }
              }
              &.refund_info_td {
                p {
                  line-height: 32px;
                }
              }
              .refund_info {
                color: #333;
              }
              .image_box {
                img {
                  width: 80px;
                  height: 80px;
                  & + img {
                    margin-left: 10px;
                  }
                }
              }
              .text-warning {
                color: #ff6600;
              }
              .tips {
                color: #666;
              }
            }
          }
        }
      }
      .recode {
        border: 1px solid #ccc;
        padding: 10px;
        border-top: none;
        color: #333;
        font-size: 14px;
        .top {
          border-bottom: 1px dashed #ccc;
          padding-bottom: 10px;
          > ul {
            > li {
              display: inline-block;
              width: 200px;
              text-align: left;
            }
          }
        }
        .bottom {
          padding: 10px;
        }
      }
    }
    .item_box + .item_box {
      margin-top: 10px;
    }
  }
  /deep/ .custom {
    .el-dialog__header {
      background: #f3f3f3;
      padding-top: 10px;
      .el-dialog__title {
        font-size: 14px;
      }
      .el-dialog__headerbtn {
        top: 10px;
      }
      .el-form-item {
        margin-bottom: 0;
      }
    }
    .el-checkbox-button.is-disabled .el-checkbox-button__inner {
      background-color: #f5f7fa;
    }
  }
}
</style>
