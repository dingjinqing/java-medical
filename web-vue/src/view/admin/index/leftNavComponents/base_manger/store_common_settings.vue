<template>
  <!-- 店铺通用设置 -->
  <div class="container store_common_setting_page">
    <div class="content">
      <ul>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>销量展示设置</span>
          </div>
          <div class="text-set clearfix">
            <el-switch
              v-model="value"
              active-color="#F7931E"
              inactive-color="#ccc"
              :active-value="1"
              :inactive-value="0"
            >
            </el-switch>
            <span
              class="con4-hide"
              v-show="value===0"
            >已关闭</span>
            <span
              class="con4-hide"
              v-show="value===1"
            >已开启</span>
            <span class="tip">开关开启，商品详情页会展示商品的销量</span>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>购买按钮展示设置</span>
          </div>
          <div class="text-set">
            <div class="top">
              <el-switch
                v-model="value"
                active-color="#F7931E"
                inactive-color="#ccc"
                :active-value="1"
                :inactive-value="0"
              >
              </el-switch>
              <span
                class="con4-hide"
                v-show="value===0"
              >已关闭</span>
              <span
                class="con4-hide"
                v-show="value===1"
              >已开启</span>
              <span class="tip">开关开启，店铺商品页以及推荐商品列表中会显示购买按钮</span>
            </div>
            <div class="btn_cart">
              <el-radio-group>
                <el-radio>
                  <i class="iconfont icontianjia icon_font_size new_class"></i>
                </el-radio>
                <el-radio>
                  <i class="iconfont icongouwuche1 icon_font_size new_class"></i>
                </el-radio>
                <el-radio>
                  <el-button
                    type="primary"
                    round
                    size="small"
                  >马上抢</el-button>
                </el-radio>
                <el-radio>
                  <el-button
                    size="small"
                    round
                  >购买</el-button>
                </el-radio>
              </el-radio-group>
            </div>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>划线价(市场价/销量/评论)展示设置</span>
          </div>
          <div class="text-set">
            <div class="top">
              <el-switch
                v-model="value"
                active-color="#F7931E"
                inactive-color="#ccc"
                :active-value="1"
                :inactive-value="0"
              >
              </el-switch>
              <span
                class="con4-hide"
                v-show="value===0"
              >已关闭</span>
              <span
                class="con4-hide"
                v-show="value===1"
              >已开启</span>
              <span class="tip">开关开启，店铺商品页以及推荐商品列表中会显示商品划线价（市场价/销量/评论）。注：为保证页面展示效果，若开启购买按钮，则小程序前端不会显示划线价</span>
            </div>
            <div class="bottom">
              <el-radio-group>
                <el-radio>
                  市场价
                </el-radio>
                <el-radio>
                  销量
                </el-radio>
                <el-radio>
                  评价数
                </el-radio>
              </el-radio-group>
            </div>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>售罄商品展示设置</span>
          </div>
          <div class="text-set clearfix">
            <el-switch
              v-model="value"
              active-color="#F7931E"
              inactive-color="#ccc"
              :active-value="1"
              :inactive-value="0"
            >
            </el-switch>
            <span
              class="con4-hide"
              v-show="value===0"
            >已关闭</span>
            <span
              class="con4-hide"
              v-show="value===1"
            >已开启</span>
            <span class="tip">开关开启，前端装修商品模块、店铺商品页、推荐商品列表、活动商品列表会展示已售罄商品</span>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>商品默认排序</span>
          </div>
          <div class="text-set clearfix">
            <p>请选择小程序端"店铺商品页"默认排序规则：</p>
            <el-radio-group>
              <el-radio>
                按照商品上新时间倒序排列
                <span>最新添加的商品将排在商品列表最上方</span>
              </el-radio>
              <el-radio>
                按照商品销量倒序排列
                <span>销量最高的商品将排在商品列表最上方</span>
              </el-radio>
              <el-radio>
                按照商品评价数量倒序排列
                <span>评价数最多的商品将排在商品列表最上方</span>
              </el-radio>
              <el-radio>
                按照商品访问次数倒序排列
                <span>7天内访问次数最多的商品将排在商品列表最上方</span>
              </el-radio>
            </el-radio-group>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>售罄商品展示设置</span>
          </div>
          <div class="text-set clearfix">
            <el-switch
              v-model="value"
              active-color="#F7931E"
              inactive-color="#ccc"
              :active-value="1"
              :inactive-value="0"
            >
            </el-switch>
            <span
              class="con4-hide"
              v-show="value===0"
            >已关闭</span>
            <span
              class="con4-hide"
              v-show="value===1"
            >已开启</span>
            <span class="tip">开关开启，会在商品详情页滚动展示最近的5条购买记录 </span>
            <el-tooltip>查看示例</el-tooltip>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>商品默认平台分类</span>
          </div>
          <div class="text-set clearfix">
            <span>商品默认平台分类： </span>
            <el-select>
              <el-optoon>请选择平台分类</el-optoon>
            </el-select>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>店铺分享</span>
          </div>
          <div class="text-set clearfix">
            <el-radio-group>
              <el-radio>默认样式 <el-tooltip>查看示例</el-tooltip>
              </el-radio>
              <el-radio>自定义样式</el-radio>
            </el-radio-group>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>手机号授权配置</span>
          </div>
          <div class="text-set clearfix">
            <div class="top">
              <span>手机号授权</span>
              <el-switch
                v-model="value"
                active-color="#F7931E"
                inactive-color="#ccc"
                :active-value="1"
                :inactive-value="0"
              >
              </el-switch>
              <span
                class="con4-hide"
                v-show="value===0"
              >已关闭</span>
              <span
                class="con4-hide"
                v-show="value===1"
              >已开启</span>
              <span class="tip">开关开启，用户在购买、预约以及申请成为分销员时需绑定手机号 </span>
            </div>
            <div class="bottom">
              <span>地理位置授权</span>
              <el-switch
                v-model="value"
                active-color="#F7931E"
                inactive-color="#ccc"
                :active-value="1"
                :inactive-value="0"
              >
              </el-switch>
              <span
                class="con4-hide"
                v-show="value===0"
              >已关闭</span>
              <span
                class="con4-hide"
                v-show="value===1"
              >已开启</span>
              <span class="tip">开关开启，用户进入小程序时会弹出地理位置授权申请。用户在小程序中触发需要
                授权地理位置的操作时，默认会调起授权提示，不受此开关控制 </span>
            </div>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>手机号授权配置</span>
          </div>
          <div class="text-set">
            <p>
              当前充值账号是： 微铺宝电商运营， 剩余金额： ￥0.0000， 剩余短信条数： 0 前往充值
            </p>
          </div>
        </li>
      </ul>
    </div>
    <div class="footer">
      <el-button
        size="small"
        type="primary"
        class="footer-btn"
        @click="saveServiceHandle"
      >保存</el-button>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      value: 1
    }
  }
}
</script>

<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
.store_common_setting_page {
  .content {
    min-height: 760px;
    background: #fff;
    padding: 0 20px;
  }
  .text-prompt {
    width: 100%;
    height: 50px;
    line-height: 50px;
    padding-left: 25px;
    background: #eef1f6;
    font-size: 14px;
    .blue_border {
      border-left: 2px solid #5a8bff;
      height: 14px;
      width: 8px;
      display: inline-block;
      margin-bottom: -1px;
    }
  }
  .text-set {
    line-height: 60px;
    color: #666;
    padding: 0 32px;
    font-size: 13px;
  }
  .con4-hide {
    margin: 22px 15px 15px 0px;
    color: #333;
    font-size: 14px;
    line-height: 1;
  }
  .btn_cart {
    height: 55px;
    display: flex;
    align-items: center;
    justify-content: flex-start;
  }
  .new_class {
    color: rgb(91, 158, 164);
    vertical-align: middle;
    font-size: 32px;
  }
  .footer {
    position: fixed;
    z-index: 100;
    bottom: 0;
    display: flex;
    justify-content: center;
    width: calc(100% - 186px);
    padding: 10px;
    background: #f8f8fa;
    border-top: 1px solid #f2f2f2;
    text-align: center;
    .footer-btn {
      width: 105px;
      margin: 0 10px;
    }
  }
}
</style>
