<template>
  <div class="content">
    <div class="main">
      <div class="item_box">
        <h2 class="h2_title">仅退款申请</h2>
        <div class="refund_status_desc">
          <div class="refund_description">
            <h3>
              等待商家处理退款申请
            </h3>
            <div class="tips">如果您同意，需要您退款给买家。</div>
            <div class="tips">如果您拒绝，将需要您填写拒绝理由。</div>
          </div>
          <div class="btn_box">
            <el-button
              type="primary"
              size="small"
            >同意买家仅退款</el-button>
            <el-button
              type="default"
              size="small"
            >拒绝仅退款申请</el-button>
          </div>
        </div>
      </div>
      <div class="item_box">
        <h2 class="h2_title">仅退款申请详情</h2>
        <table class="refund_info_table">
          <tbody>
            <tr>
              <td>订单编号</td>
              <td></td>
              <td>退款编号</td>
              <td></td>
            </tr>
            <tr>
              <td>退货商品</td>
              <td colspan="3">
                <div class="goods_info">
                  <img
                    :src="$imageHost+'/image/admin/icon_jia.png'"
                    alt=""
                  >
                  <div class="goods_info_right">
                    <div class="goods_name">春季复古格纹连衣裙--意杰店转卖，勿改</div>
                    <div class="goods_spec">尺码:L</div>
                    <div class="goods_price_box"><span class="goods_price">￥400.00</span>X5</div>
                  </div>
                </div>
                <div class="goods_info">
                  <img
                    :src="$imageHost+'/image/admin/icon_jia.png'"
                    alt=""
                  >
                  <div class="goods_info_right">
                    <div class="goods_name">春季复古格纹连衣裙--意杰店转卖，勿改</div>
                    <div class="goods_spec">尺码:L</div>
                    <div class="goods_price_box"><span class="goods_price">￥100.00</span>X20</div>
                  </div>
                </div>
              </td>
            </tr>
            <tr>
              <td>退款类型</td>
              <td colspan="3">仅退款</td>
            </tr>
            <tr>
              <td>退款状态</td>
              <td colspan="3">退款成功</td>
            </tr>
            <tr>
              <td>退款金额</td>
              <td
                colspan="3"
                class="refund_info_td"
              >
                <template v-if="1">
                  <p class="refund_info"> 退商品金额：<el-input-number
                      v-model="refundGoodsMoney"
                      :precision="2"
                      :controls="false"
                      :min="0"
                      size="small"
                    ></el-input-number> 元，退运费金额：<el-input-number
                      v-model="refundFreight"
                      size="small"
                      :precision="2"
                      :controls="false"
                      :min="0"
                    ></el-input-number> 元，可退最大运费xx元</p>
                  <p>
                    总退款金额：￥<span class="text-warning refund-money">0.00</span> =
                    <span>退尾款：￥<span class="text-warning refund-bk-order-money">0.00</span> + </span>
                    退会员卡余额：￥<span class="text-warning refund-member-card-money">0.00</span>
                    + 退余额：￥<span class="text-warning refund-balance-money">0.00</span>
                    + 退积分抵扣：￥<span class="text-warning refund-score-money">0.00</span>
                    + 退支付金额：￥<span class="text-warning refund-pay-money">0.00</span>
                  </p>
                  <p class="tips">注：总退款金额 = 退商品金额+退运费金额，扣款优先级：会员卡余额，余额，积分，支付金额</p>
                </template>
                <template v-else>
                  <p class="refund_info">退商品金额：<span class="text-warning">￥100.00</span></p>
                  <p class="refund_info">退运费金额：<span class="text-warning">￥200.00</span></p>
                  <p class="refund_info">退款详情：
                    <span>退尾款：<span class="text-warning">￥100.00</span></span>
                    <span>退会员卡余额：<span class="text-warning">￥100.00</span></span>
                    <span>退余额：<span class="text-warning">￥100.00</span></span>
                    <span>退积分抵扣：<span class="text-warning">￥100.00</span></span>
                    <span>退支付金额：<span class="text-warning">￥100.00</span></span>
                  </p>
                </template>
              </td>
            </tr>
            <tr>
              <td>退款原因</td>
              <td colspan="3">
                协商一致退款
              </td>
            </tr>
            <tr>
              <td><span class="w2">说明</span></td>
              <td colspan="3">
                xxxxx
              </td>
            </tr>
            <tr>
              <td>退款图片</td>
              <td colspan="3">
                <div class="image_box">
                  <img
                    :src="$imageHost+'/image/admin/icon_jia.png'"
                    alt=""
                  >
                  <img
                    :src="$imageHost+'/image/admin/icon_jia.png'"
                    alt=""
                  >
                  <img
                    :src="$imageHost+'/image/admin/icon_jia.png'"
                    alt=""
                  >
                </div>
              </td>
            </tr>
            <tr>
              <td>申请时间</td>
              <td colspan="3">
                XXXXXXXXXXXXXXXXXXXX
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="item_box">
        <h2 class="h2_title">协商记录</h2>
        <div class="recode">
          <div class="top">
            <ul>
              <li>买家</li>
              <li>2019-10-11 11:11:11</li>
              <li>商家同意退款，退款成功</li>
            </ul>
          </div>
          <div class="bottom">
            <ul>
              <li>商家同意退款，退款成功</li>
              <li>注：退款将在3个工作日内到达买家账户，本次维权结束。</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      refundGoodsMoney: null,
      refundFreight: null
    }
  }
}
</script>

<style lang="scss" scoped>
.content {
  padding: 10px;
  .main {
    background-color: #fff;
    padding: 10px;
    .item_box {
      .h2_title {
        font-size: 14px;
        padding-left: 12px;
        background: #f5f5f5;
        line-height: 36px;
        border: 1px solid #ddd;
        border-bottom: 0;
        color: #333;
        font-weight: 600;
      }
      .refund_status_desc {
        border: 1px solid #ddd;
        padding: 16px 30px;
        position: relative;
        display: flex;
        justify-content: space-between;
        align-items: center;
        .refund_description {
          line-height: 24px;
          > h3 {
            font-weight: 600;
          }
          > .tips {
            font-size: 14px;
            color: #666;
          }
        }
        .btn_box {
          display: flex;
        }
      }
      .refund_info_table {
        width: 100%;
        font-size: 14px;
        > tbody {
          > tr {
            &:first-child {
              > td {
                &:nth-child(odd) {
                  width: 150px;
                  color: #666;
                  text-align: center;
                }
              }
            }
            > td {
              color: #666;
              padding: 8px 10px;
              border: 1px solid #ddd;
              vertical-align: middle;
              &:first-child {
                width: 150px;
                color: #666;
                text-align: center;
              }
              .w2 {
                letter-spacing: 28px;
                margin-right: -28px;
              }
              .goods_info {
                display: flex;
                & + .goods_info {
                  margin-top: 10px;
                }
                > img {
                  width: 90px;
                  height: 90px;
                  margin-right: 10px;
                }
                > .goods_info_right {
                  flex: 1;
                  display: flex;
                  flex-direction: column;
                  justify-content: space-between;
                  > div {
                    overflow: hidden;
                    text-overflow: ellipsis;
                    display: -webkit-box;
                    -webkit-line-clamp: 2;
                    overflow: hidden;
                    /*! autoprefixer: off */
                    -webkit-box-orient: vertical;
                    text-align: left;
                    .goods_price {
                      color: #ff6600;
                      margin-right: 10px;
                    }
                  }
                }
              }
              &.refund_info_td {
                p {
                  line-height: 32px;
                }
              }
              .refund_info {
                color: #333;
              }
              .image_box {
                img {
                  width: 80px;
                  height: 80px;
                  & + img {
                    margin-left: 10px;
                  }
                }
              }
              .text-warning {
                color: #ff6600;
              }
              .tips {
                color: #666;
              }
            }
          }
        }
      }
      .recode {
        border: 1px solid #ccc;
        padding: 10px;
        border-top: none;
        color: #333;
        font-size: 14px;
        .top {
          border-bottom: 1px dashed #ccc;
          padding-bottom: 10px;
          > ul {
            > li {
              display: inline-block;
              width: 200px;
              text-align: left;
            }
          }
        }
        .bottom {
          padding: 10px;
        }
      }
    }
    .item_box + .item_box {
      margin-top: 10px;
    }
  }
}
</style>
