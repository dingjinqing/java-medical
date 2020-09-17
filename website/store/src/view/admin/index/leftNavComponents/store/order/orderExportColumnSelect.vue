<template>
  <el-dialog
    :title="$t('order.export')"
    :visible.sync="showNodes"
    custom-class="custom"
    width="741"
  >
    <el-checkbox-group v-model="columns">
      <table class="columns-table" v-loading="loading">
        <tbody>
          <tr>
            <td style="width: 25%">
              {{ $t('order.baseInfo') }}
            </td>
            <td>
              <el-checkbox
                class="required"
                checked="checked"
                disabled="disabled"
                label="orderSn"
                >{{ $t('order.orderSn') }}</el-checkbox
              >
              <el-checkbox label="orderStatusName">{{
                $t('order.orderStatusText')
              }}</el-checkbox>
              <el-checkbox label="payNames">{{
                $t('order.paymentType')
              }}</el-checkbox>
              <el-checkbox label="createTime">{{
                $t('order.orderSubmitTime')
              }}</el-checkbox>
              <el-checkbox label="payTime">{{
                $t('order.payTime')
              }}</el-checkbox>
              <el-checkbox label="closedTime">{{
                $t('order.orderCloseTime')
              }}</el-checkbox>
              <el-checkbox label="cancelledTime">{{
                $t('order.orderCancelTime')
              }}</el-checkbox>
              <el-checkbox label="finishedTime">{{
                $t('order.orderFinishTime')
              }}</el-checkbox>
              <el-checkbox label="isCodString">{{
                $t('order.isCod')
              }}</el-checkbox>
            </td>
          </tr>
          <tr>
            <td>{{ $t('order.consigneeInfo') }}</td>
            <td>
              <el-checkbox label="consignee">{{
                $t('order.consigneeName')
              }}</el-checkbox>
              <el-checkbox label="mobile">{{ $t('order.mobile') }}</el-checkbox>
              <el-checkbox label="completeAddress">{{
                $t('order.shippingAddress')
              }}</el-checkbox>
              <el-checkbox label="provinceName">{{
                $t('order.shippingProvince')
              }}</el-checkbox>
              <el-checkbox label="cityName">{{
                $t('order.shippingCity')
              }}</el-checkbox>
              <el-checkbox label="districtName">{{
                $t('order.shippingDistrict')
              }}</el-checkbox>
              <el-checkbox label="zipcode">{{
                $t('order.zipcode')
              }}</el-checkbox>
            </td>
          </tr>
          <tr>
            <td>{{ $t('order.orderUserInfo') }}</td>
            <td>
              <el-checkbox label="userName">{{
                $t('order.userNameText')
              }}</el-checkbox>
              <el-checkbox label="userMobile">{{
                $t('order.userMobileText')
              }}</el-checkbox>
              <el-checkbox label="isNew">{{
                $t('order.isNewUser')
              }}</el-checkbox>
              <el-checkbox label="userSourceString">{{
                $t('order.orderUserSource')
              }}</el-checkbox>
              <el-checkbox label="userTag">{{
                $t('order.orderUserTag')
              }}</el-checkbox>
              <el-checkbox label="addMessage">{{
                $t('order.orderUserMassage')
              }}</el-checkbox>
            </td>
          </tr>
          <tr>
            <td>{{ $t('order.shippingInfo') }}</td>
            <td>
              <el-checkbox label="shippingTime">{{
                $t('order.shippingTimeText')
              }}</el-checkbox>
              <el-checkbox label="shippingName">{{
                $t('order.shippingName')
              }}</el-checkbox>
              <el-checkbox label="shippingNo">{{
                $t('order.shippingNo')
              }}</el-checkbox>
              <el-checkbox label="deliverTypeName">{{
                $t('order.deliverTypeName')
              }}</el-checkbox>
              <el-checkbox label="confirmTime">{{
                $t('order.confirmTimeText')
              }}</el-checkbox>
              <el-checkbox label="storeId">{{
                $t('order.storeId')
              }}</el-checkbox>
              <el-checkbox label="storeName">{{
                $t('order.storeNameString')
              }}</el-checkbox>
              <el-checkbox label="pickupdateTime">{{
                $t('order.pickupdateTime')
              }}</el-checkbox>
            </td>
          </tr>
          <tr>
            <td>{{ $t('order.goodsInfo') }}</td>
            <td>
              <el-checkbox
                class="required"
                label="goodsName"
                checked="checked"
                disabled="disabled"
                >{{ $t('order.goodsName') }}</el-checkbox
              >
              <el-checkbox
                class="required"
                checked="checked"
                disabled="disabled"
                label="productSn"
                >{{ $t('order.productSn') }}</el-checkbox
              >
              <el-checkbox label="goodsNumber">{{
                $t('order.goodsNum')
              }}</el-checkbox>
              <el-checkbox label="discountedGoodsPrice">{{
                $t('order.discountedGoodsPrice')
              }}</el-checkbox>
              <el-checkbox label="goodsAttr">{{
                $t('order.goodsAttr')
              }}</el-checkbox>
              <el-checkbox label="goodsPrice">{{
                $t('order.goodsPrice')
              }}</el-checkbox>
              <el-checkbox label="marketPrice">{{
                $t('order.marketPrice')
              }}</el-checkbox>
              <el-checkbox label="goodsSn">{{
                $t('order.goodsSn')
              }}</el-checkbox>
              <el-checkbox label="goodsId">{{
                $t('order.goodsId')
              }}</el-checkbox>
              <el-checkbox label="sendNumber">{{
                $t('order.sendNumber')
              }}</el-checkbox>
              <el-checkbox label="returnNumber">{{
                $t('order.returnNumText')
              }}</el-checkbox>
              <el-checkbox label="source">{{
                $t('order.goodsSource')
              }}</el-checkbox>
              <el-checkbox label="prdCostPrice">{{
                $t('order.prdCostPrice')
              }}</el-checkbox>
              <el-checkbox label="prdWeight">{{
                $t('order.prdWeight')
              }}</el-checkbox>
            </td>
          </tr>
          <tr>
            <td>{{ $t('order.tradeInfo') }}</td>
            <td>
              <el-checkbox label="orderAmount">{{
                $t('order.orderAmount')
              }}</el-checkbox>
              <el-checkbox label="discount">{{
                $t('order.discount')
              }}</el-checkbox>
              <el-checkbox label="shippingFee">{{
                $t('order.shippingFee')
              }}</el-checkbox>
              <el-checkbox label="scoreDiscount">{{
                $t('order.scoreDiscount')
              }}</el-checkbox>
              <el-checkbox label="useAccount">{{
                $t('order.useAccount')
              }}</el-checkbox>
              <el-checkbox label="moneyPaid">{{
                $t('order.wxMoneyPaid')
              }}</el-checkbox>
              <el-checkbox label="memberCardBalance">{{
                $t('order.memberCardBalance')
              }}</el-checkbox>
              <el-checkbox label="memberCardReduce">{{
                $t('order.memberCardReduce')
              }}</el-checkbox>
              <el-checkbox label="promotionReduce">{{
                $t('order.promotionReduce')
              }}</el-checkbox>
              <el-checkbox label="rebate">{{ $t('order.rebate') }}</el-checkbox>
              <el-checkbox label="rebateUserName">{{
                $t('order.rebateUserName')
              }}</el-checkbox>
              <el-checkbox label="rebateFlag">{{
                $t('order.rebateFlag')
              }}</el-checkbox>
              <el-checkbox label="communityRebateInfo">{{
                $t('order.communityRebateInfo')
              }}</el-checkbox>
            </td>
          </tr>
          <tr>
            <td>{{ $t('order.afetrSaleInfo') }}</td>
            <td>
              <el-checkbox label="returnTime">{{
                $t('order.returnTime')
              }}</el-checkbox>
              <el-checkbox label="returnFinishTime">{{
                $t('order.returnFinishTime')
              }}</el-checkbox>
              <el-checkbox label="returnOrderMoney">{{
                $t('order.returnMoney')
              }}</el-checkbox>
              <el-checkbox label="returnShippingFee">{{
                $t('order.returnShippingFee')
              }}</el-checkbox>
            </td>
          </tr>
          <tr>
            <td>{{ $t('order.otherInfo') }}</td>
            <td>
              <el-checkbox label="sellerRemark">{{
                $t('order.sellerMessage')
              }}</el-checkbox>
              <el-checkbox label="orderRealName">{{
                $t('order.orderRealName')
              }}</el-checkbox>
              <el-checkbox label="orderCid">{{
                $t('order.orderCid')
              }}</el-checkbox>
              <el-checkbox label="orderInvoice">{{
                $t('order.orderInvoice')
              }}</el-checkbox>
              <el-checkbox label="custom">{{ $t('order.custom') }}</el-checkbox>
            </td>
          </tr>
        </tbody>
      </table>
    </el-checkbox-group>

    <span slot="footer" class="dialog-footer">
      <el-button @click="showNodes = false">{{
        $t('orderCommon.cancel')
      }}</el-button>
      <el-button type="primary" @click="confirm">{{
        $t('orderCommon.ok')
      }}</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { getExportColumns, setExportColumns } from '@/api/store/order.js'
export default {
  data () {
    return {
      columns: [],
      storedColumns: [], // 默认的或上次导出设置的列
      showNodes: false,
      type: 0,
      checked: true,
      loading: false,
      jichu: ''
    }
  },
  props: {
    show: Boolean,
    orderSn: String
  },
  methods: {
    closeDialog () {
      this.$emit('update:show', false)
    },
    initData () {
      let obj = {
      }
      this.loading = true
      getExportColumns(obj).then(res => {
        if (res.error === 0) {
          this.storedColumns = res.content
          if (res.content !== null) {
            this.columns = res.content
          }
        }
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    confirm () {
      if (this.columns !== this.storedColumns) {
        setExportColumns(this.columns).then(res => {
          if (res.error === 0) {
            this.showNodes = false
            this.$emit('exportColumnSelectConfirm')
          } else {
            this.$message.error('error')
          }
        }).catch(() => {
          this.$message.error('error')
        })
      } else {
        this.showNodes = false
        this.$emit('exportColumnSelectConfirm')
      }
    }
  },
  watch: {
    showNodes (newval) {
      this.closeDialog()
    },
    show (newVal) {
      if (newVal === true) {
        this.showNodes = true
        this.initData()
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

/deep/ .required .el-checkbox__label {
  color: #ff0000 !important;
}
/deep/ .required .el-checkbox__inner {
  background-color: #409eff !important;
  border-color: #409eff !important;
  border-radius: 2px !important;
}
/deep/ .required .el-checkbox__inner::after {
  border-color: #fff !important;
}
/deep/ .columns-table {
  border: 1px solid #ddd;
  width: 100%;
  max-width: 100%;
  margin-bottom: 18px;
  font-size: 14px;
  tbody {
    > tr {
      > td:first-of-type {
        text-align: center;
        display: table-cell;
        vertical-align: middle;
      }
      > td:last-of-type {
        padding: 10px;
        /deep/ .el-checkbox {
          width: 160px;
          margin-right: 0;
        }
      }
      > td {
        border: 1px solid #ddd;
      }
    }
  }
}
</style>
