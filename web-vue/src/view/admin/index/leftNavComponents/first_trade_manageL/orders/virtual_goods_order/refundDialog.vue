<template>
  <el-dialog
    title="手动退款"
    :visible.sync="dialogShow"
    custom-class="custom"
    width="30%"
  >
    <div
      class="card_refund"
      v-if="refundInfo.viewOrderType === 'card' && refundInfo.action === 'refund'"
    >
      <p v-if="refundInfo.useScore > 0">积分：<el-input-number
          v-model="refundData.score"
          size="small"
        ></el-input-number> 积分</p>
      <p v-if="refundInfo.useAccount > 0">余额：<el-input-number
          v-model="refundData.account"
          size="small"
        ></el-input-number> 元</p>
      <p v-if="refundInfo.moneyPaid > 0">现金：<el-input-number
          v-model="refundData.money"
          size="small"
        ></el-input-number> 元</p>
    </div>
    <div
      class="coupon_refund"
      v-if="refundInfo.viewOrderType === 'couponPackage' && refundInfo.action === 'refund'"
    >
      <div class="coupon_refund_top">
        <p>请输入退款金额：</p>
        <p v-if="refundInfo.useAccount > 0">退余额：<el-input-number
            v-model="refundData.account"
            size="small"
          ></el-input-number> 元</p>
        <p v-if="refundInfo.memberCardBalance > 0">退会员卡余额：<el-input-number
            v-model="refundData.memberCardBalance"
            size="small"
          ></el-input-number> 元</p>
        <p v-if="refundInfo.moneyPaid > 0">退现金：<el-input-number
            v-model="refundData.money"
            size="small"
          ></el-input-number> 元</p>
        <p v-if="refundInfo.useScore > 0">退积分：<el-input-number
            v-model="refundData.score"
            size="small"
          ></el-input-number> 分</p>
      </div>
      <div class="coupon_refund_bottom">
        <p>剩余{{refundInfo.surplusAmount ?  refundInfo.surplusAmount : 0}}张优惠券未发放是否继续发放</p>
        <p>
          <el-radio
            v-model="refundData.stillSendFlag"
            label="0"
          >停止发放</el-radio>
          <el-radio
            v-model="refundData.stillSendFlag"
            label="1"
          >继续发放</el-radio>
        </p>
      </div>
    </div>
    <div
      class="view_refund"
      v-if="refundInfo.action === 'view'"
    >
      <p>退款时间：{{refundInfo.returnTime}}</p>
      <p v-if="refundInfo.returnAccount > 0">已退余额：{{refundInfo.returnAccount}}</p>
      <p v-if="refundInfo.returnCardBalance > 0">已退会员卡余额：{{refundInfo.returnCardBalance}}</p>
      <p v-if="refundInfo.returnMoney > 0">已退现金：{{refundInfo.returnMoney}}</p>
      <p v-if="refundInfo.returnScore > 0">已退积分：{{refundInfo.returnScore}}</p>
    </div>
    <span
      slot="footer"
      class="dialog-footer"
    >
      <el-button
        @click="dialogShow = false"
        v-if="refundInfo.action === 'refund'"
      >取 消</el-button>
      <el-button
        type="primary"
        @click="refundConfirm"
      >确 定</el-button>
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
        console.log(this.refundInfo)
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
