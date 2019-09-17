<template>
  <el-dialog
    title="填入快递信息发货"
    :visible.sync="showDelivery"
    v-if="showDelivery === true"
    custom-class="custom"
    width="40%"
  >
    <div class="delivery-info">
      <div class="delivery-info_userinfo">
        <span class="title">配送信息</span>
        <div class="userinfo">
          <p>收货人：{{deliveryInfo.consignee}} {{deliveryInfo.mobile}}</p>
          <p>收货地址：{{deliveryInfo.completeAddress}}</p>
        </div>
      </div>
      <div class="delivery-info_shipinfo">

      </div>
      <div class="delivery-info_goodslist"></div>
    </div>
    <span
      slot="footer"
      class="dialog-footer"
    >
      <el-button @click="showDelivery = false">取 消</el-button>
      <el-button
        type="primary"
        @click="showDelivery = fasle"
      >确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      showDelivery: false,
      deliveryInfo: {},
      shippingId: 1
    }
  },
  props: {
    show: Boolean,
    orderData: Object
  },
  methods: {
    closeDialog () {
      this.$emit('update:show', false)
    },
    initData () {
      this.deliveryInfo = {
        'consignee': '北京',
        'mobile': '11111111111',
        'completeAddress': '北京紫禁城',
        'orderGoodsVo': [
          {
            'recId': 2,
            'orderId': 4,
            'orderSn': '3',
            'goodsId': 0,
            'goodsSn': '2',
            'goodsName': 'good名称1',
            'goodsNumber': 1,
            'goodsPrice': 200,
            'goodsAttr': '黑',
            'productId': 2,
            'goodsImg': '',
            'sendNumber': 1
          },
          {
            'recId': 5,
            'orderId': 4,
            'orderSn': '3',
            'goodsId': 0,
            'goodsSn': '4',
            'goodsName': 'good名称3',
            'goodsNumber': 3,
            'goodsPrice': 100,
            'goodsAttr': '白',
            'productId': 4,
            'goodsImg': '',
            'sendNumber': 1
          }
        ]
      }
    }
  },
  watch: {
    showDelivery (newval) {
      if (newval === false) {
        this.closeDialog()
      }
    },
    show (newVal) {
      if (newVal === true) {
        this.showDelivery = true
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
}
.delivery-info {
  display: flex;
  flex-direction: column;
  .delivery-info_userinfo {
    padding: 8px;
    background-color: #f5f5f5;
    line-height: 30px;
    display: flex;
    > .title {
      width: 65px;
    }
    > .userinfo {
      flex: 1;
      display: flex;
      flex-direction: column;
    }
  }
}
</style>
