<template>
  <el-dialog
    title="导出表格"
    :visible.sync="showNodes"
    custom-class="custom"
    width="30%"
  >
    <span>{{columns}}</span>
    <el-checkbox-group
      v-model="columns"
      class="checkbox-group"
    >
      <div height="50">基础信息</div>
      <div>
        <el-checkbox
          class="required"
          checked="checked"
          disabled="disabled"
          label="orderSn"
        >订单号</el-checkbox>
        <el-checkbox label="orderStatusName">订单状态</el-checkbox>
        <el-checkbox label="payNames">支付方式</el-checkbox>
        <el-checkbox label="addTime">订单提交时间</el-checkbox>
        <el-checkbox label="payTime">支付时间</el-checkbox>
        <el-checkbox label="closedTime">订单关闭时间</el-checkbox>
        <el-checkbox label="cancelledTime">订单取消时间</el-checkbox>
        <el-checkbox label="finishedTime">订单完成时间</el-checkbox>
        <el-checkbox label="isCod">是否货到付款</el-checkbox>
        <el-checkbox label="consignee">收货人姓名</el-checkbox>
        <el-checkbox label="mobile">手机号</el-checkbox>
        <el-checkbox label="completeAddress">收货地址</el-checkbox>
        <el-checkbox label="provinceName">收货省份</el-checkbox>
        <el-checkbox label="cityName">收货城市</el-checkbox>
        <el-checkbox label="districtName">收货地区</el-checkbox>
        <el-checkbox label="zipcode">邮政编码</el-checkbox>
        <el-checkbox label="userName">下单人姓名</el-checkbox>
        <el-checkbox label="userMobile">下单人手机号</el-checkbox>
        <el-checkbox label="isNew">新老用户</el-checkbox>
        <el-checkbox label="userSource">下单人来源</el-checkbox>
        <el-checkbox label="userTag">下单人标签</el-checkbox>
        <el-checkbox label="addMessage">下单人留言</el-checkbox>
        <el-checkbox label="shippingTime">发货时间</el-checkbox>
        <el-checkbox label="shippingName">货运名称</el-checkbox>
        <el-checkbox label="shippingNo">物流单号</el-checkbox>
        <el-checkbox label="deliverTypeName">配送类型</el-checkbox>
        <el-checkbox label="confirmTime">确认收货时间</el-checkbox>
        <el-checkbox label="storeId">门店ID</el-checkbox>
        <el-checkbox label="storeName">门店名称</el-checkbox>
        <el-checkbox
          class="required"
          label="goodsName"
          checked="checked"
          disabled="disabled"
        >商品名称</el-checkbox>
        <el-checkbox
          class="required"
          checked="checked"
          disabled="disabled"
          label="productSn"
        >商家编码</el-checkbox>
        <el-checkbox label="goodsNumber">商品数量</el-checkbox>
        <el-checkbox label="discountedGoodsPrice">实际售价</el-checkbox>
        <el-checkbox label="goodsAttr">SKU属性</el-checkbox>
        <el-checkbox label="goodsPrice">商品售价</el-checkbox>
        <el-checkbox label="marketPrice">商品市场价</el-checkbox>
        <el-checkbox label="goodsSn">商品货号</el-checkbox>
        <el-checkbox label="goodsId">商品ID</el-checkbox>
        <el-checkbox label="sendNumber">已发货数量</el-checkbox>
        <el-checkbox label="returnNumber">退货数量</el-checkbox>
        <el-checkbox label="source">商品来源</el-checkbox>
        <el-checkbox label="prdCostPrice">成本价</el-checkbox>
        <el-checkbox label="prdWeight">SKU重量</el-checkbox>
        <el-checkbox label="orderAmount">订单总金额</el-checkbox>
        <el-checkbox label="discount">优惠券优惠金额</el-checkbox>
        <el-checkbox label="shippingFee">邮费</el-checkbox>
        <el-checkbox label="scoreDiscount">积分抵扣金额</el-checkbox>
        <el-checkbox label="useAccount">使用账户余额</el-checkbox>
        <el-checkbox label="moneyPaid">微信支付金额</el-checkbox>
        <el-checkbox label="memberCardBalance">使用会员卡余额</el-checkbox>
        <el-checkbox label="memberCardReduce">会员卡抵扣金额</el-checkbox>
        <el-checkbox label="promotionReduce">满折满减优惠金额</el-checkbox>
        <el-checkbox label="returnTime">申请退货时间</el-checkbox>
        <el-checkbox label="returnFinishTime">退货完成时间</el-checkbox>
        <el-checkbox label="returnOrderMoney">退款金额</el-checkbox>
        <el-checkbox label="returnShippingFee">退运费金额</el-checkbox>
        <el-checkbox label="sellerRemark">卖家备注</el-checkbox>
        <el-checkbox name="orderRealName">真实姓名</el-checkbox>
        <el-checkbox label="orderCid">身份证号</el-checkbox>
        <el-checkbox label="custom">自定义下单必填信息</el-checkbox>

      </div>

    </el-checkbox-group>

    <span
      slot="footer"
      class="dialog-footer"
    >
      <el-button @click="showNodes = false">取 消</el-button>
      <el-button
        type="primary"
        @click="confirm"
      >确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { getExportColumns, setExportColumns } from '@/api/admin/orderManage/order.js'
export default {
  data () {
    return {
      columns: [],
      storedColumns: [], // 默认的或上次导出设置的列
      showNodes: false,
      type: 0,
      checked: true
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
      getExportColumns(obj).then(res => {
        if (res.error === 0) {
          this.storedColumns = res.content
          if (res.content !== null) {
            this.columns = res.content
          }
        }
      }).catch(() => {
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
</style>
