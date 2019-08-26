<template>
  <div class="content">
    <div class="main">
      <div class="coupon_settings">
        <div class="left_preview">
          <div class="left_top">
            优惠券礼包
          </div>
          <div class="left_content">
            <div class="content_bg">
              <img src="../../../../../../assets/adminimg/cou_package_bg.png">
            </div>
            <div class="content_info">
              <div class="text_title">{{pack_name?pack_name:'优惠券礼包'}}</div>
              <div class="package_info">
                <div class="package_title">
                  ——— <span>优惠券礼包</span> ———
                </div>
                <div class="coupon_info">
                  <div
                    class="coupon_item"
                    v-for="(item, index) in coupon_info"
                    :key="index"
                  >
                    <div class="coupon_left">
                      <div class="coupon_price">￥<span>{{item.coupon_price}}</span></div>
                      <div class="coupon_rule">{{item.coupon_rule}}</div>
                    </div>
                    <div class="coupon_middle">
                      <img
                        src="../../../../../../assets/adminimg/cou_midd_icon.png"
                        alt=""
                      >
                    </div>
                    <div class="coupon_right">
                      <div class="coupon_name">{{item.coupon_name}}</div>
                      <div class="coupon_limits">{{item.coupon_limits}}></div>
                      <div class="coupon_time">{{item.coupon_time}}</div>
                      <div class="coupon_icon">{{item.coupon_icon}}张</div>
                    </div>
                  </div>
                </div>
                <div class="btn_get">立即领取</div>
              </div>
              <div class="package_rule">
                <div class="rule_title">活动规则</div>
                <div class="rule_info">{{act_rule}}</div>
              </div>
            </div>
          </div>
        </div>
        <div class="right_settings">
          <div class="set_box">
            <p class="set_title">基础设置</p>
            <div class="set_item">
              <div class="item_title">
                <em>*</em> 活动名称：
              </div>
              <div class="item_right">
                <el-input
                  v-model="act_name"
                  placeholder="最多支持10个字"
                  size="small"
                  class="default_input"
                  maxlength="10"
                ></el-input>
                <span class="item_tips">只作为商家记录使用，用户不会看到这个名称</span>
              </div>
            </div>
            <div class="set_item">
              <div class="item_title"><em>*</em> 有效期：</div>
              <div class="item_right">
                <el-date-picker
                  v-model="value1"
                  type="datetimerange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  format="yyyy-MM-dd HH:mm:ss"
                  size="small"
                >
                </el-date-picker>
              </div>
            </div>
            <div class="set_item">
              <div class="item_title"><em>*</em> 礼包名称：</div>
              <div class="item_right">
                <el-input
                  v-model="pack_name"
                  placeholder="最多支持8个字"
                  size="small"
                  class="default_input"
                  maxlength="8"
                ></el-input>
                <span class="item_tips">展示在小程序活动页，最多可填写8个汉字</span>
              </div>
            </div>
            <div class="set_item">
              <div class="item_title"><em>*</em> 礼包内容：</div>
              <div class="item_right">
                <a style="color:#409eff;font-size:12px;cursor: pointer;">添加优惠券</a>
                <span class="item_tips">最多可添加10种优惠券，每种优惠券最多送6张</span>
              </div>
            </div>
            <!-- <div class="coupon_set_box">
              <div class="bottom_tips_box">
                <span>注：</span>
                <div class="tips_content">
                  <p>1.优惠券包发放的优惠券不占用原优惠券库存；</p>
                  <p>2.任意一张优惠券过期或失效，则不会展示在券礼包中，用户可以设置的价格购买券包中其他优惠券；</p>
                  <p>3.若所有优惠券全部过期或失效，则该礼包失效，用户不可领取。</p>
                </div>
              </div>
            </div> -->
            <div class="set_item">
              <div class="item_title"><em>*</em> 每人限领礼包数量：</div>
              <div class="item_right">
                <el-input
                  v-model="limit_get_times"
                  placeholder=""
                  size="small"
                  class="small_input"
                ></el-input>
                <span class="item_tips">单个用户可以领取该礼包的数量，填写0表示不限制</span>
              </div>
            </div>
            <div class="set_item">
              <div class="item_title"><em>*</em> 礼包发放数量：</div>
              <div class="item_right">
                <el-input
                  v-model="total_amount"
                  placeholder=""
                  size="small"
                  class="small_input"
                ></el-input>
                <span class="item_tips">优惠券包发放的总数量</span>
              </div>
            </div>
            <div class="set_item">
              <div class="item_title"><em>*</em> 礼包领取方式：</div>
              <div class="item_right">
                <p>
                  <el-radio
                    v-model="access_mode"
                    label="0"
                  >现金购买</el-radio>
                  <el-radio
                    v-model="access_mode"
                    label="1"
                  >积分购买</el-radio>
                  <el-radio
                    v-model="access_mode"
                    label="2"
                  >直接领取</el-radio>
                </p>
                <p class="package_get_choose">
                  <span v-if="access_mode==='0'">
                    需支付：<el-input
                      v-model="access_cost"
                      class="small_input"
                      size="small"
                    ></el-input> 元，
                  </span>
                  <span v-if="access_mode==='1'">
                    需支付：<el-input
                      v-model="access_cost"
                      class="small_input"
                      size="small"
                    ></el-input> 积分，
                  </span>
                  <span v-if="access_mode!='2'">
                    当前已选优惠券可优惠金额总和为 0.00元
                  </span>
                </p>
              </div>
            </div>
            <div class="set_item">
              <div class="item_title"><em>*</em> 活动规则：</div>
              <div class="item_right">
                <el-input
                  v-model="act_rule"
                  placeholder="请输入活动规则"
                  type="textarea"
                  :rows="5"
                  resize="none"
                ></el-input>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="footer">
      <el-button
        type="primary"
        size="small"
      >保存</el-button>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      act_name: '',
      pack_name: '',
      limit_get_times: '',
      total_amount: '',
      act_rule: '',
      access_mode: '0',
      coupon_info: [
        { coupon_price: '88', coupon_rule: '不限制', coupon_name: '优惠券233', coupon_limits: '全部商品可用', coupon_time: '2019-08-21-2019-08-31', coupon_icon: '10' },
        { coupon_price: '88', coupon_rule: '不限制', coupon_name: '优惠券233', coupon_limits: '全部商品可用', coupon_time: '2019-08-21-2019-08-31', coupon_icon: '10' },
        { coupon_price: '88', coupon_rule: '不限制', coupon_name: '优惠券233', coupon_limits: '全部商品可用', coupon_time: '2019-08-21-2019-08-31', coupon_icon: '10' },
        { coupon_price: '88', coupon_rule: '不限制', coupon_name: '优惠券233', coupon_limits: '全部商品可用', coupon_time: '2019-08-21-2019-08-31', coupon_icon: '10' },
        { coupon_price: '88', coupon_rule: '不限制', coupon_name: '优惠券233', coupon_limits: '全部商品可用', coupon_time: '2019-08-21-2019-08-31', coupon_icon: '10' }
      ]
    }
  },
  methods () {

  }
}
</script>

<style lang="scss" scoped>
.main {
  height: 1000px;
  padding: 10px;
  background-color: #fff;
  margin: 10px;
  margin-bottom: 62px;
  .coupon_settings {
    max-width: 960px;
    margin: 0 auto;
    display: flex;
    .left_preview {
      width: 310px;
      max-height: 600px;
      overflow-y: auto;
      &::-webkit-scrollbar {
        width: 9px;
        height: 9px;
      }
      &::-webkit-scrollbar-track {
        border-radius: 8px;
        background-color: rgba(0, 0, 0, 0);
      }

      &::-webkit-scrollbar-track:hover {
        background-color: rgba(0, 0, 0, 0.06);
        -webkit-box-shadow: -2px 0 0 #fff inset,
          1px 0 0 rgba(255, 255, 255, 0.9) inset,
          0 -1px 0 rgba(255, 255, 255, 0.9) inset,
          0 1px 0 rgba(255, 255, 255, 0.9) inset;
      }

      &::-webkit-scrollbar-track:active {
        background-color: rgba(0, 0, 0, 0.1);
      }

      &::-webkit-scrollbar-thumb {
        border-radius: 8px;
        background-color: rgba(0, 0, 0, 0.1);
        -webkit-box-shadow: -2px 0 0 #fff inset, 1px 0 0 #fff inset,
          0 -1px 0 rgba(255, 255, 255, 0.9) inset,
          0 1px 0 rgba(255, 255, 255, 0.9) inset;
      }

      &::-webkit-scrollbar-thumb:hover {
        background-color: rgba(0, 0, 0, 0.4);
      }

      &::-webkit-scrollbar-thumb:active {
        background: rgba(0, 0, 0, 0.6);
      }
      .left_top {
        width: 100%;
        height: 55px;
        color: #000;
        background: url("../../../../../../assets/adminimg/cou_top_bg.png")
          no-repeat 100%/100%;
        text-align: center;
        line-height: 75px;
      }
      .left_content {
        width: 100%;
        min-height: 450px;
        background: linear-gradient(left, #ff5666, #e6558e);
        position: relative;
        padding-bottom: 20px;
        .content_bg {
          width: 100%;
          position: absolute;
          > img {
            width: 100%;
          }
        }
        .content_info {
          position: relative;
          z-index: 2;
          .text_title {
            font-size: 35px;
            color: #fff;
            text-align: center;
            padding-top: 60px;
            font-weight: bold;
            font-style: italic;
          }
          .package_info {
            background: #f0a6b5;
            margin: 0 auto;
            width: 97%;
            margin-top: 200px;
            overflow: hidden;
            .package_title {
              line-height: 44px;
              color: #fff;
              font-size: 14px;
              text-align: center;
              > span {
                display: inline-block;
                margin: 0 10px;
                position: relative;
                &::before {
                  content: "";
                  width: 4px;
                  height: 4px;
                  background-color: #fff;
                  border-radius: 50%;
                  position: absolute;
                  top: 22px;
                  left: -14px;
                }
                &::after {
                  content: "";
                  width: 4px;
                  height: 4px;
                  background-color: #fff;
                  border-radius: 50%;
                  position: absolute;
                  top: 22px;
                  right: -14px;
                }
              }
            }
            .coupon_info {
              display: flex;
              flex-direction: column;
              width: 97%;
              margin: 0 auto;
              margin-bottom: -10px;
              .coupon_item {
                margin-bottom: 10px;
                height: 100px;
                display: flex;
                > div {
                  background-color: #fff;
                  height: 100px;
                }
                .coupon_left {
                  width: 100px;
                  text-align: center;
                  display: flex;
                  flex-direction: column;
                  justify-content: center;
                  align-items: center;
                  .coupon_price {
                    color: #f66;
                    margin-bottom: 10px;
                    > span {
                      font-size: 18px;
                      font-weight: 600;
                    }
                  }
                  .coupon_rule {
                    color: #999;
                    font-size: 14px;
                  }
                }
                .coupon_middle {
                  width: 25px;
                  background: none;
                  > img {
                    width: 100%;
                    height: 100%;
                  }
                }
                .coupon_right {
                  flex: 1;
                  display: flex;
                  flex-direction: column;
                  justify-content: center;
                  align-items: flex-start;
                  padding-left: 10px;
                  position: relative;
                  overflow: hidden;
                  .coupon_name {
                    font-weight: bold;
                    font-size: 14px;
                    margin-bottom: 5px;
                    width: 100%;
                    text-overflow: ellipsis;
                    overflow: hidden;
                    white-space: nowrap;
                  }
                  .coupon_limits {
                    margin-bottom: 15px;
                    font-size: 13px;
                  }
                  .coupon_time {
                    color: #999;
                    font-size: 12px;
                  }
                  .coupon_icon {
                    position: absolute;
                    right: -15px;
                    top: 8px;
                    background: #fead2d;
                    width: 64px;
                    font-size: 12px;
                    color: #fff;
                    transform: rotate(40deg);
                    text-align: center;
                  }
                }
              }
            }
            .btn_get {
              width: 280px;
              line-height: 34px;
              text-align: center;
              color: #c93f0e;
              background: linear-gradient(top, #ffd47b, #fcc02a);
              border-radius: 30px;
              font-weight: bold;
              font-size: 13px;
              margin: 10px auto;
            }
          }
          .package_rule {
            background: #f0a6b5;
            margin: 0 auto;
            width: 97%;
            margin-top: 20px;
            overflow: hidden;
            padding: 10px;
            > .rule_title {
              color: #fff;
              font-size: 14px;
              font-weight: 600;
            }
            > .rule_info {
              word-break: break-all;
              color: #fff;
              font-size: 14px;
              font-weight: 600;
            }
          }
        }
      }
    }
    .right_settings {
      flex: 1;
      margin-left: 15px;
      .set_box {
        border: 1px solid #e5e5e5;
        background: #f8f8f8;
        padding: 0 12px 22px;
        border-radius: 3px;
        .set_title {
          border-bottom: 1px solid #e5e5e5;
          line-height: 40px;
          font-size: 14px;
        }
        .set_item {
          display: flex;
          margin-top: 15px;
          > .item_title {
            width: 150px;
            font-size: 14px;
            text-align: right;
            line-height: 32px;
            color: #333;
            > em {
              color: red;
            }
          }
          > .item_right {
            flex: 1;
            line-height: 32px;
            > .item_tips {
              color: #999;
              font-size: 12px;
            }
            > .package_get_choose {
              font-size: 14px;
              color: #333;
            }
          }
        }
        > .coupon_set_box {
          padding-left: 80px;
          > .bottom_tips_box {
            font-size: 12px;
            color: #999;
            padding-left: 20px;
            display: flex;
            line-height: 32px;
            > .tips_content {
              flex: 1;
            }
          }
        }
      }
    }
  }
}

.default_input {
  width: 150px;
}
.small_input {
  width: 80px;
}
.footer {
  position: fixed;
  bottom: 0;
  left: 160px;
  right: 10px;
  height: 52px;
  padding: 10px 0;
  background-color: #fff;
  text-align: center;
}
</style>
