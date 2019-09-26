<template>
  <div class="main">
    <div class="since-info">
      <div class="since-info-top">
        <span>{{$t('order.orderSn')}}：{{order.orderSn }}</span>
      </div>
      <div class="since-info-detail">
        <div class="order_info">
          <div class="title">{{$t('order.orderInfo')}}</div>
          <div class="item_box">
            <div class="item"><span class="item_title">{{$t('order.orderAmount')}}：</span>{{order.moneyPaid.toFixed(2)}}</div>
            <div class="item"><span class="item_title">{{$t('order.storeTime')}}：</span>{{order.payTime}}</div>
            <div class="item"><span class="item_title">{{$t('order.paymentType')}}：</span>{{$t('order.payTypeObj')[order.payCode]}}</div>
            <div class="item"><span class="item_title">{{$t('order.orderStatusText')}}：</span>{{$t('order.storeStatusList')[order.orderStatus][1]}}</div>
            <div class="item"><span class="item_title">{{$t('order.storeNameText')}}：</span>{{order.storeName}}</div>

          </div>

        </div>
        <div class="user_info">
          <div class="title">{{$t('order.storeUserInfo')}}</div>
          <div class="item_box">
            <div class="item"> <span class="item_title">{{$t('order.storeUsertext')}}：</span>{{order.username}}</div>
            <div class="item"> <span class="item_title">{{$t('order.clientMessages')}}：</span>{{order.addMessage}}</div>
            <template v-if="order.type != null">
              <div class="item"> <span class="item_title">{{$t('order.invoiceType')}}：</span>{{$t('order.invoiceTypeObj')[order.type]}}</div>
              <div class="item"> <span class="item_title">{{$t('order.InvoiceTitle')}}：</span>{{order.title}}</div>
              <template v-if="order.type == 0">
                <div
                  v-if="order.taxNumber != null"
                  class="item"
                > <span class="item_title">{{$t('order.CompanyTaxNumber')}}：</span>{{order.taxNumber}}</div>
                <div
                  v-if="order.taxNumber != null"
                  class="item"
                > <span class="item_title">{{$t('order.companyAddress')}}：</span>{{order.companyAddress}}</div>
              </template>
            </template>

          </div>
        </div>
      </div>
      <div class="order-remark">
        <i
          class="icon el-icon-edit-outline"
          @click="addNodes"
        ></i>
        <span>买家备注：{{order.addMessage}}</span>
      </div>
      <div class="pay_detail">
        <div class="pd_title">
          <span>{{$t('order.payInfo')}}</span>
          <span class="refund">手动退款</span>
        </div>
        <div class="list_item">
          <span>{{$t('order.discountInfo')}}：</span>
          <div class="preference_list">
            <div><span>{{$t('order.memberCardReduce')}}</span> - {{order.memberCardReduce.toFixed(2)}}元</div>
            <div><span>{{$t('order.memberCardDiscount')}}</span> - {{order.memberCardBalance.toFixed(2)}}元</div>
            <div><span class="w2">{{$t('order.score')}}</span> - {{order.scoreDiscount.toFixed(2)}}元</div>
            <div><span class="w2">{{$t('order.balance')}}</span> - {{order.useAccount.toFixed(2)}}元</div>
          </div>
        </div>
        <div class="list_item">
          <span>{{$t('order.totalPrice')}}：</span>
          <div>{{order.orderAmount.toFixed(2)}}元</div>
        </div>
        <div class="list_item">
          <span>{{$t('order.amountSum')}}：</span>
          <div class="m_color">{{order.moneyPaid.toFixed(2)}}元</div>
        </div>
      </div>
    </div>
    <nodesDialog
      :show.sync="showNodes"
      :orderSn="notesOrderSn"
    />
  </div>
</template>

<script>
import { storeInfo } from '@/api/admin/orderManage/order.js'
export default {
  components: {
    nodesDialog: () => import('../addNotes')
  },
  data () {
    return {
      showNodes: false,
      searchParam: {
        orderSn: null
      },
      order: null,
      notesOrderSn: null
    }
  },
  created () {
    this.search(this.$route.query.orderSn)
  },
  methods: {
    addNodes () {
      this.showNodes = true
      this.notesOrderSn = this.$route.query.orderSn
    },
    search (orderSn) {
      this.searchParam.orderSn = orderSn
      storeInfo(this.searchParam).then(res => {
        this.order = res.content
      }).catch(() => {
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.main {
  padding: 10px;
  > div {
    margin-bottom: 10px;
  }
  .since-info {
    background-color: #fff;
    padding: 0 25px;
    font-size: 14px;
    overflow: hidden;
    .since-info-top {
      display: flex;
      line-height: 44px;
      color: #333;
      > span {
        margin-right: 60px;
      }
    }
    .since-info-path {
      margin-bottom: 10px;
    }
    .since-info-detail {
      display: flex;
      margin-left: -30px;
      margin-bottom: 10px;
      > div {
        margin-left: 30px;
        border: 1px solid #cfd6ff;
        flex: 1;
        padding: 0 30px;
        > .title {
          margin-top: 10px;
        }
        > .item_box {
          display: flex;
          justify-content: space-between;
          flex-wrap: wrap;
          line-height: 40px;
          color: #666;
          flex-direction: column;
          > .item {
            display: flex;
            word-break: break-all;
            > .item_title {
              min-width: 70px;
            }
          }
        }
      }
    }
    .order-remark {
      font-size: 14px;
      color: #333;
      line-height: 50px;
      > .icon {
        font-size: 20px;
        color: #f8a039;
        vertical-align: middle;
        cursor: pointer;
      }
    }
    .pay_detail {
      display: flex;
      flex-direction: column;
      color: #333;
      .pd_title {
        display: flex;
        justify-content: space-between;
        line-height: 40px;
        background: #f9f9f9;
        padding: 0 10px;
        .refund {
          cursor: pointer;
        }
      }
      .list_item {
        display: flex;
        line-height: 40px;
        padding-left: 10px;
        > span {
          width: 70px;
          text-align: right;
        }
        > div {
          flex: 1;
          &.preference_list {
            display: flex;
            flex-direction: column;
            .w2 {
              letter-spacing: 43px;
              margin-right: -43px;
            }
          }
        }
      }
    }
  }
  .m_color {
    color: #f66;
  }
}
</style>
