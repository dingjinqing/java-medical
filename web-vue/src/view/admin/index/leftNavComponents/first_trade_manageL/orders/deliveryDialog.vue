<template>
  <el-dialog
    title="填入快递信息发货"
    :visible.sync="showDelivery"
    custom-class="custom"
    width="640px"
  >
    <div
      class="delivery-info"
      v-loading="!deliveryInfo"
    >
      <div class="delivery-info_userinfo">
        <span class="title">配送信息</span>
        <div class="userinfo">
          <p>收货人：{{deliveryInfo.consignee}} {{deliveryInfo.mobile}}</p>
          <p>收货地址：{{deliveryInfo.completeAddress}}</p>
        </div>
      </div>
      <div class="delivery-info_shipinfo">
        <div>
          快递列表：
          <el-select
            v-model="shippingId"
            size="small"
            class="default_input"
            filterable
          >
            <el-option
              v-for="(item, key) in $t('expressList.company')"
              :key="key"
              :label="item"
              :value="key"
            ></el-option>
          </el-select>
        </div>
        <div>
          快递单号：
          <el-input
            v-model="shippingNo"
            placeholder="请输入快递单号"
            size="small"
            class="default_input"
          ></el-input>
        </div>
      </div>
      <div class="delivery-info_goodslist">
        <el-table
          ref="multipleTable"
          :data="deliveryInfo.orderGoodsVo"
          style="width:100%;"
          border
          :header-cell-style="{
            'background-color':'#f5f5f5',
            'text-align':'center',
            'border':'none'
          }"
          :cell-style="{
            'text-align':'center'
          }"
        >
          <el-table-column
            type="selection"
            width="55"
          >
          </el-table-column>
          <el-table-column
            prop=""
            label="商品名称"
            width="200"
          >
            <template slot-scope="scope">
              <div class="goods_info">
                <img
                  :src="$imageHost+'/'+scope.row.goodsImg"
                  alt=""
                >
                <div class="right_info">
                  <div class="goods_name">{{scope.row.goodsName}}</div>
                </div>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="goodsAttr"
            label="规格"
          ></el-table-column>
          <el-table-column
            prop="goodsPrice"
            label="单价"
          ></el-table-column>
          <el-table-column
            prop="goodsNumber"
            label="数量"
          ></el-table-column>
          <el-table-column
            prop="goodsNumber"
            label="发货数量"
          ></el-table-column>
        </el-table>
      </div>
    </div>
    <span
      slot="footer"
      class="dialog-footer"
    >
      <el-button @click="showDelivery = false">取 消</el-button>
      <el-button
        type="primary"
        @click="goodsDelivery"
      >确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { deliveryInfo, delivery } from '@/api/admin/orderManage/order.js'
export default {
  data () {
    return {
      showDelivery: false,
      deliveryInfo: {},
      shippingId: '1',
      shippingNo: ''
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
      let obj = {
        orderId: this.orderData.orderId,
        orderSn: this.orderData.orderSn,
        action: 0
      }
      deliveryInfo(obj).then(res => {
        if (res.error === 0) {
          this.deliveryInfo = res.content
        }
      })
    },
    goodsDelivery () {
      let obj = {
        orderId: this.orderData.orderId,
        orderSn: this.orderData.orderSn,
        action: 0,
        shippingNo: this.shippingNo,
        shippingId: this.shippingId,
        shipGoods: []
      }
      obj.shipGoods = this.$refs.multipleTable.selection.map(item => {
        return { recId: item.recId, sendNumber: item.goodsNumber }
      })
      console.log(obj)
      delivery(obj).then(res => {
        console.log(res)
        this.showDelivery = false
        this.$emit('handlerResetData')
      })
    }

  },
  watch: {
    showDelivery (newval) {
      if (newval === false) {
        this.closeDialog()
      }
    },
    show: {
      handler (newVal) {
        if (newVal === true) {
          this.showDelivery = true
          this.initData()
        }
      },
      immediate: true
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
  > div {
    & + div {
      margin-top: 10px;
    }
  }
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
  .delivery-info_shipinfo {
    display: flex;
    > div {
      & + div {
        margin-left: 72px;
      }
    }
  }
  .delivery-info_goodslist {
    .goods_info {
      display: flex;
      > img {
        width: 60px;
        height: 60px;
        margin-right: 5px;
      }
      > .right_info {
        flex: 1;
        display: flex;
        flex-direction: column;
        text-align: left;
        justify-content: space-between;
        .goods_name {
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 3;
          overflow: hidden;
          /*! autoprefixer: off */
          -webkit-box-orient: vertical;
          text-align: left;
          line-height: 1.1;
        }
      }
    }
  }
  .default_input {
    width: 180px;
  }
}
</style>
