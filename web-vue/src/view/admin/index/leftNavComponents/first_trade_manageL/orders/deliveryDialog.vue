<template>
  <el-dialog
    title="填入快递信息发货"
    :visible.sync="showDelivery"
    custom-class="custom"
    width="600px"
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
        <div>
          快递列表：
          <el-select
            v-model="shippingId"
            size="small"
            class="default_input"
            filterable
          >
            <el-option
              v-for="item in $t('expressList.company')"
              :key="item[0]"
              :label="item[1]"
              :value="item[0]"
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
            prop=""
            label="商品名称"
            width="200"
          >
            <template slot-scope="scope">
              <div class="goods_info">
                <img
                  :src="$imageHost+'/image/admin/icon_jia.png'"
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
            prop="sendNumber"
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
      shippingId: 1,
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
        margin-left: 52px;
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
