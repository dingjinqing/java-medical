<template>
  <div class="main">
    <div class="since-info">
      <div class="since-info-top">
        <span>订单号：P201909051016068533</span>
      </div>
      <div class="since-info-detail">
        <div class="order_info">
          <div class="title">订单信息</div>
          <div class="item_box">
            <div class="item"><span class="item_title">订单金额：</span>0.00元</div>
            <div class="item"><span class="item_title">买单时间：</span>2019-09-05 10:16:06</div>
            <div class="item"><span class="item_title">支付方式：</span>微信支付</div>
            <div class="item"><span class="item_title">订单状态：</span>已支付</div>
            <div class="item"><span class="item_title">买单门店：</span>牡丹园门店</div>

          </div>

        </div>
        <div class="user_info">
          <div class="title">买家信息</div>
          <div class="item_box">
            <div class="item"> <span class="item_title"> 买单客户：</span>XXXXXX</div>
            <div class="item"> <span class="item_title">客户留言：</span> XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</div>
            <div class="item"> <span class="item_title">发票类型：</span> 企业发票</div>
            <div class="item"> <span class="item_title">发票抬头：</span> XXXX</div>
            <div class="item"> <span class="item_title">公司税号：</span> XXXXX</div>
            <div class="item"> <span class="item_title">公司地址：</span> XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</div>
          </div>
        </div>
      </div>
      <div class="order-remark">
        <i class="icon el-icon-edit-outline"></i>
        <span>买家备注：</span>
      </div>
      <div class="pay_detail">
        <div class="pd_title">
          <span>支付明细</span>
          <span class="refund">手动退款</span>
        </div>
        <div class="list_item">
          <span>优惠额度：</span>
          <div class="preference_list">
            <div><span>会员卡折扣</span>-XXX元</div>
            <div><span>会员卡抵扣</span>-XXX元</div>
            <div><span class="w2">积分</span>-XXX元</div>
            <div><span class="w2">余额</span>-XXX元</div>
          </div>
        </div>
        <div class="list_item">
          <span>总价：</span>
          <div>XXX元</div>
        </div>
        <div class="list_item">
          <span>实收款：</span>
          <div class="m_color">XXX元</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {

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
