<template>
  <div class="payConfigure">
    <el-card class="payCard">
      <div
        v-for="item in payConfigure"
        :key="item.name"
        class="payContent"
      >
        <span>{{item.name}}</span>
        <el-switch
          v-model="item.value"
          active-color="#13ce66"
          inactive-color="#ff4949"
          style="margin: 0 10px;"
        ></el-switch>
        <span>{{item.value?'已开启':'已关闭'}}</span>
        <span>{{item.title}}</span>
        <span
          v-if="item.name==='微信支付'"
          class="setting"
          @click="handleSetting"
        >配置</span>
      </div>
    </el-card>

    <div class="defaultPayCongigure">
      <div class="title">
        <span></span>
        默认支付配置
      </div>
      <div class="payMethod">
        <el-checkbox v-model="checked1">会员卡余额支付</el-checkbox>
        <el-checkbox v-model="checked2">余额支付</el-checkbox>
        <el-checkbox v-model="checked3">积分支付</el-checkbox>
      </div>
      <div class="tips">
        <span>注：</span>
        <span>1、用户在结算时将会默认优先使用以上勾选的支付方式</span>
        <br>
        <span class="secondTips">2、如果勾选以上全部支付方式，计算优先级为会员卡余额>余额支付>积分支付</span>
        <span style="margin-left: 10px;">
          <el-popover
            placement="right-start"
            width="220"
            trigger="hover"
          >
            <el-image :src="src"></el-image>
            <el-button
              slot="reference"
              type="text"
            >查看示例</el-button>
          </el-popover>
        </span>
      </div>
    </div>
    <div class="btn">保存</div>
  </div>
</template>

<script>

export default {
  data () {
    return {
      payConfigure: [
        { name: '微信支付', title: '开关开启，用户可以通过微信支付完成订单支付 ', value: '' },
        { name: '积分支付', title: '开关开启，用户购买商品时可以用积分抵扣一定金额', value: '' },
        { name: '余额支付', title: '开关开启，用户可以使用余额进行支付', value: '' },
        { name: '货到付款', title: '开关开启，用户在提交订单时可以使用货到付款方式支付；开关关闭则不显示货到付款支付方式', value: '' }
      ],
      checked1: '',
      checked2: '',
      checked3: '',
      src: `${this.$imageHost}/image/admin/share/pay_config_share.jpg`
    }
  },
  methods: {
    handleSetting () {
      console.log('配置')
    }
  }
}

</script>
<style lang="scss" scoped>
.payConfigure {
  padding-bottom: 20px;
  .payCard {
    position: relative;
    width: 890px;
    padding-top: 0 !important;
    .payContent {
      height: 50px;
      line-height: 50px;
      border-bottom: 1px solid #eee;
      :nth-of-type(3) {
        margin-left: 15px;
        color: #999;
        font-size: 12px;
      }
      .setting {
        position: absolute;
        color: #5a8bff;
        right: 90px;
        cursor: pointer;
      }
    }
  }
  .defaultPayCongigure {
    margin-top: 20px;
    .title {
      height: 40px;
      line-height: 40px;
      background: #eef1f6;
      padding-left: 16px;
      font-size: 13px;
      span {
        display: inline-block;
        border-left: 2px solid #5a8bff;
        height: 14px;
        width: 8px;
        margin-bottom: -1px;
      }
    }
    .payMethod {
      height: 60px;
      line-height: 60px;
      padding-left: 16px;
      color: #999;
      font-size: 13px;
    }
    .tips {
      height: 60px;
      line-height: 30px;
      padding-left: 16px;
      color: #999;
      .secondTips {
        padding-left: 34px;
      }
    }
  }
  .btn {
    width: 90px;
    height: 30px;
    line-height: 30px;
    margin-left: 16px;
    margin-top: 5px;
    color: #fff;
    text-align: center;
    border: 1px solid #5a8bff;
    background: #5a8bff;
  }
}
</style>
