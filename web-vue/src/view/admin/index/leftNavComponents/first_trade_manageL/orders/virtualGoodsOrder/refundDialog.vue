<template>
  <el-dialog
    :title="$t('orderCommon.manualRefund')"
    :visible.sync="dialogShow"
    custom-class="custom"
    width="30%"
  >
    <div
      class="card_refund"
      v-if="refundInfo.viewOrderType === 'card' && refundInfo.action === 'refund'"
    >
      <p v-if="refundInfo.useScore > 0">{{$t('refundDialog.integral')}}:<el-input-number
          v-model="refundData.score"
          size="small"
          controls-position="right"
        ></el-input-number> {{$t('refundDialog.integral')}}</p>
      <p v-if="refundInfo.useAccount > 0">{{$t('refundDialog.balance')}}:<el-input-number
          v-model="refundData.account"
          size="small"
          controls-position="right"
        ></el-input-number> {{$t('refundDialog.yuan')}}</p>
      <p v-if="refundInfo.moneyPaid > 0">{{$t('refundDialog.cash')}}:<el-input-number
          v-model="refundData.money"
          size="small"
          controls-position="right"
        ></el-input-number> {{$t('refundDialog.yuan')}}</p>
    </div>
    <div
      class="coupon_refund"
      v-if="refundInfo.viewOrderType === 'couponPackage' && refundInfo.action === 'refund'"
    >
      <div class="coupon_refund_top">
        <p>{{$t('refundDialog.refundTip')}}:</p>
        <p v-if="refundInfo.useAccount > 0">{{$t('refundDialog.refund')}}{{$t('refundDialog.balance')}}:<el-input-number
            v-model="refundData.account"
            size="small"
            controls-position="right"
          ></el-input-number> {{$t('refundDialog.yuan')}}</p>
        <p v-if="refundInfo.memberCardBalance > 0">{{$t('refundDialog.refund')}}{{$t('refundDialog.memberCardBalance')}}:<el-input-number
            v-model="refundData.memberCardBalance"
            size="small"
            controls-position="right"
          ></el-input-number> {{$t('refundDialog.yuan')}}</p>
        <p v-if="refundInfo.moneyPaid > 0">{{$t('refundDialog.refund')}}{{$t('refundDialog.cash')}}:<el-input-number
            v-model="refundData.money"
            size="small"
            controls-position="right"
          ></el-input-number> {{$t('refundDialog.yuan')}}</p>
        <p v-if="refundInfo.useScore > 0">{{$t('refundDialog.refund')}}{{$t('refundDialog.integral')}}:<el-input-number
            v-model="refundData.score"
            size="small"
            controls-position="right"
          ></el-input-number> {{$t('refundDialog.integral')}}</p>
      </div>
      <div class="coupon_refund_bottom">
        <p>{{$t('refundDialog.remainingCouponTip')}}:{{refundInfo.surplusAmount ?  refundInfo.surplusAmount : 0}},{{$t('refundDialog.whetherToContinueToIssue')}}</p>
        <p>
          <el-radio
            v-model="refundData.stillSendFlag"
            label="0"
          >{{$t('refundDialog.stopIssuing')}}</el-radio>
          <el-radio
            v-model="refundData.stillSendFlag"
            label="1"
          >{{$t('refundDialog.continueToIssue')}}</el-radio>
        </p>
      </div>
    </div>
    <div
      class="view_refund"
      v-if="refundInfo.action === 'view'"
    >
      <p>{{$t('refundDialog.refundTime')}}:{{refundInfo.returnTime}}</p>
      <p v-if="refundInfo.returnAccount > 0">{{$t('refundDialog.refunded')}}{{$t('refundDialog.balance')}}:{{refundInfo.returnAccount}}</p>
      <p v-if="refundInfo.returnCardBalance > 0">{{$t('refundDialog.refunded')}}{{$t('refundDialog.memberCardBalance')}}:{{refundInfo.returnCardBalance}}</p>
      <p v-if="refundInfo.returnMoney > 0">{{$t('refundDialog.refunded')}}{{$t('refundDialog.cash')}}:{{refundInfo.returnMoney}}</p>
      <p v-if="refundInfo.returnScore > 0">{{$t('refundDialog.refunded')}}{{$t('refundDialog.integral')}}:{{refundInfo.returnScore}}</p>
    </div>
    <span
      slot="footer"
      class="dialog-footer"
    >
      <el-button
        @click="dialogShow = false"
        v-if="refundInfo.action === 'refund'"
      >{{$t('refundDialog.cancel')}}</el-button>
      <el-button
        type="primary"
        @click="refundConfirm"
      >{{$t('refundDialog.confirm')}}</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { refundMemberCardOrder, refundCouponPackageOrder } from '@/api/admin/orderManage/virtualGoodsOrder.js'

export default {
  data () {
    return {
      dialogShow: false,
      refundInfo: {},
      refundData: {
        money: '',
        score: '',
        account: '',
        memberCardBalance: '',
        stillSendFlag: '0'
      }
    }
  },
  props: {
    show: Boolean,
    dataInfo: Object
  },
  methods: {
    closeDialog () {
      this.$emit('update:show', false)
    },
    refundConfirm () {
      if (this.refundInfo.viewOrderType === 'couponPackage' && this.refundInfo.action === 'refund') {
        // 优惠券礼包订单确认退款
        let postData = {}
        postData.orderId = this.refundData.orderId
        postData.orderSn = this.refundData.orderSn
        postData.stillSendFlag = this.refundData.stillSendFlag
        postData.virtualOrderRefundParam = this.refundData
        refundCouponPackageOrder(postData).then((res) => {
          if (res.error === 0) {
            this.$message({
              type: 'success',
              message: this.$t('marketCommon.successfulOperation')
            })
            this.dialogShow = false
          } else {
            this.$message({
              type: 'error',
              message: res.message
            })
          }
        })
      } else if (this.refundInfo.viewOrderType === 'card' && this.refundInfo.action === 'refund') {
        // 会员卡订单确认退款
        refundMemberCardOrder(this.refundData).then((res) => {
          if (res.error === 0) {
            this.$message({
              type: 'success',
              message: this.$t('marketCommon.successfulOperation')
            })
            this.dialogShow = false
          } else {
            this.$message({
              type: 'error',
              message: res.message
            })
          }
        })
      }
    }
  },
  watch: {
    dialogShow (newVal) {
      if (newVal === false) {
        this.closeDialog()
      }
    },
    show (newVal) {
      if (newVal === true) {
        this.dialogShow = true
        this.refundInfo = this.dataInfo
        this.refundData.money = this.refundInfo.moneyPaid
        this.refundData.score = this.refundInfo.useScore
        this.refundData.account = this.refundInfo.useAccount
        this.refundData.memberCardBalance = this.refundInfo.memberCardBalance ? this.refundInfo.memberCardBalance : 0
        this.refundData.orderId = this.refundInfo.orderId
        this.refundData.orderSn = this.refundInfo.orderSn
      }
    }
  }
}
</script>

<style lang="scss" scoped>
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
  }
  .el-checkbox-button.is-disabled .el-checkbox-button__inner {
    background-color: #f5f7fa;
  }
}
.card_refund {
  display: flex;
  flex-direction: column;
  p {
    text-align: center;
    & + p {
      margin-top: 10px;
    }
  }
}
.view_refund {
  display: flex;
  flex-direction: column;
  p {
    & + p {
      margin-top: 10px;
    }
  }
}
.coupon_refund {
  display: flex;
  flex-direction: column;
  .coupon_refund_top,
  .coupon_refund_bottom {
    display: flex;
    flex-direction: column;
    padding-bottom: 10px;
    border-bottom: 1px solid #eee;
    p {
      & + p {
        margin-top: 10px;
      }
    }
  }
  .coupon_refund_bottom {
    padding-top: 10px;
  }
}
</style>
