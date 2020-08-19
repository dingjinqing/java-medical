<template>
  <el-dialog
    :title="$t('orderCommon.manualRefund')"
    :visible.sync="dialogShow"
    custom-class="custom"
    width="300px"
  >
    <div>
      <p style="margin-bottom:10px">退款金额：<el-input-number
          controls-position="right"
          :min="0"
          :max="dataInfo.orderAmount - dataInfo.refundMoney"
          size="small"
          v-model="returnInfo.refundMoney"
        ></el-input-number>
      </p>
      <div>
        <p style="margin-bottom:10px">退款原因</p>
        <el-input
          type="textarea"
          :rows="2"
          v-model="returnInfo.refundReason"
        ></el-input>
      </div>
    </div>
    <span
      slot="footer"
      class="dialog-footer"
    >
      <el-button @click="dialogShow = false">取消</el-button>
      <el-button
        type="primary"
        @click="refundConfirm"
      >确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { returnAdvisoryOrder } from '@/api/admin/orderManage/order.js'
export default {
  data () {
    return {
      dialogShow: false,
      returnInfo: {
        refundMoney: 0,
        refundReason: null
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
      let { orderSn } = this.dataInfo
      returnAdvisoryOrder({ orderSn, ...this.returnInfo }).then(res => {
        if (res.error === 0) {
          this.$emit('complete')
        } else {
          this.$message.error({ message: res.message })
        }
        this.dialogShow = false
      })
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
    p {
      & + p {
        margin-top: 10px;
      }
    }
  }
  .coupon_refund_top {
    border-bottom: 1px solid #eee;
  }
  .coupon_refund_bottom {
    padding-top: 10px;
  }
}
</style>
