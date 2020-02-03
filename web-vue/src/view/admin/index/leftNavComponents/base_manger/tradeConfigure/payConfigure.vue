<template>
  <div class="payConfigure">
    <!-- 四种支付配置内容区域 -->
    <el-card class="payCard">
      <div
        v-for="item in payConfigure"
        :key="item.payName"
        class="payContent"
      >
        <span>{{item.payName}}</span>
        <el-switch
          v-model="item.enabled"
          active-color="#f7931e"
          style="margin: 0 10px;"
        ></el-switch>
        <span>{{item.enabled?$t('payConfiguration.activated'):$t('payConfiguration.inactived')}}</span>
        <span>{{item.title}}</span>
        <span
          v-if="item.payName==='微信支付'"
          class="setting"
          @click="handleSetting"
        >{{$t('payConfiguration.config')}}</span>
      </div>
    </el-card>

    <!-- 默认支付配置内容区域 -->
    <div class="defaultPayCongigure">
      <div class="title">
        <span></span>
        {{$t('payConfiguration.defaultConfig')}}
      </div>
      <div class="payMethod">
        <el-checkbox v-model="card_first">{{$t('payConfiguration.cardpay')}}</el-checkbox>
        <el-checkbox v-model="balance_first">{{$t('payConfiguration.balancepay')}}</el-checkbox>
        <el-checkbox v-model="score_first">{{$t('payConfiguration.scorepay')}}</el-checkbox>
      </div>
      <div class="tips">
        <span>{{$t('payConfiguration.Note1')}}</span>
        <br>
        <span class="secondTips">{{$t('payConfiguration.Note2')}}</span>
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
            >{{$t('payConfiguration.example')}}</el-button>
          </el-popover>
        </span>
      </div>
    </div>
    <div
      class="btn"
      @click="updateConfig"
    >{{$t('payConfiguration.save')}}</div>

    <!-- 微信支付配置弹窗 -->
    <el-dialog
      :title="$t('payConfiguration.wechatpayconf')"
      :visible.sync="showSettingDialog"
      :close-on-click-modal='false'
      width=25%
    >
      <ul class="settingContent">
        <li>
          <span>appid：</span>
          <el-input
            size="small"
            v-model="wechatpayconf.appId"
          ></el-input>
        </li>
        <li>
          <span>{{$t('payConfiguration.payMchId')}}</span>
          <el-input
            size="small"
            v-model="wechatpayconf.payMchId"
          ></el-input>
        </li>
        <li>
          <span>{{$t('payConfiguration.payKey')}}</span>
          <el-input
            size="small"
            v-model="wechatpayconf.payKey"
          ></el-input>
        </li>
        <li class="specialHeight">
          <span>{{$t('payConfiguration.payCertContent')}}</span>
          <el-input
            size="small"
            class="textarea"
            type="textarea"
            :rows="5"
            v-model="wechatpayconf.payCertContent"
          ></el-input>
        </li>
        <li class="specialHeight">
          <span>{{$t('payConfiguration.payKeyContent')}}</span>
          <el-input
            size="small"
            class="textarea"
            type="textarea"
            :rows="5"
            v-model="wechatpayconf.payKeyContent"
          ></el-input>
        </li>
      </ul>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          size="small"
          @click="save"
        >{{$t('payConfiguration.save')}}</el-button>
        <el-button
          size="small"
          @click="cancle"
        >{{$t('payConfiguration.cancel')}}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { paySelect, payUpdate, wechatPaySelect, wechatPayUpdate } from '@/api/admin/basicConfiguration/tradeConfiguration.js'
export default {
  mounted () {
    this.langDefault()
  },
  watch: {
    lang () {
      this.payConfigure = [
        { payName: '微信支付', payCode: 'wxpay', title: this.$t('payConfiguration.wedesc'), enabled: false },
        { payName: '积分支付', payCode: 'score', title: this.$t('payConfiguration.scoredesc'), enabled: false },
        { payName: '余额支付', payCode: 'balance', title: this.$t('payConfiguration.balancedesc'), enabled: false },
        { payName: '货到付款', payCode: 'cod', title: this.$t('payConfiguration.coddesc'), enabled: false }
      ]
    }
  },
  created () {
    this.initData()
  },
  data () {
    return {
      // 微信支付配置
      wechatpayconf: {
        appId: '',
        payMchId: '',
        payKey: '',
        payCertContent: '',
        payKeyContent: ''
      },
      // 支付配置
      payConfigure: [
        { payName: '微信支付', payCode: 'wxpay', title: this.$t('payConfiguration.wedesc'), enabled: false },
        { payName: '积分支付', payCode: 'score', title: this.$t('payConfiguration.scoredesc'), enabled: false },
        { payName: '余额支付', payCode: 'balance', title: this.$t('payConfiguration.balancedesc'), enabled: false },
        { payName: '货到付款', payCode: 'cod', title: this.$t('payConfiguration.coddesc'), enabled: false }
      ],
      // 默认支付配置
      card_first: 0,
      balance_first: 0,
      score_first: 0,
      src: `${this.$imageHost}/image/admin/share/pay_config_share.jpg`,
      showSettingDialog: false,
      text1: '',
      text2: 'bao'
    }
  },
  methods: {
    // 初始化配置信息
    initData () {
      paySelect().then(res => {
        console.log(res)
        if (res.error === 0) {
          this.handlerData(res.content)
        } else {
          this.$message.error('数据拉取失败')
        }
      })
    },
    handlerData (data) {
      console.log(data)
      this.$nextTick(() => {
        this.payConfigure.map((item, index) => {
          data.v1.map((item1, index1) => {
            if (item.payCode === item1.payCode) {
              if (item1.enabled === 1) {
                item.enabled = true
              } else {
                item.enabled = false
              }
            }
          })
        })
        if (data.v2.card_first === 1) {
          this.card_first = true
        } else {
          this.card_first = false
        }
        if (data.v2.balance_first === 1) {
          this.balance_first = true
        } else {
          this.balance_first = false
        }
        if (data.v2.score_first === 1) {
          this.score_first = true
        } else {
          this.score_first = false
        }
      })

      console.log(this.payConfigure)
    },
    // 更新支付配置
    updateConfig () {
      let basicconf = {
        wxpay: null,
        score: null,
        balance: null,
        cod: null
      }
      let param = {
        basicConfig: null,
        card_first: null,
        balance_first: null,
        score_first: null
      }
      this.payConfigure.map((item, index) => {
        switch (item.payCode) {
          case 'wxpay':
            if (item.enabled === true) {
              basicconf.wxpay = 1
            } else {
              basicconf.wxpay = 0
            }
            break
          case 'score':
            if (item.enabled === true) {
              basicconf.score = 1
            } else {
              basicconf.score = 0
            }
            break
          case 'balance':
            basicconf.balance = item.enabled
            if (item.enabled === true) {
              basicconf.balance = 1
            } else {
              basicconf.balance = 0
            }
            break
          case 'cod':
            basicconf.cod = item.enabled
            if (item.enabled === true) {
              basicconf.cod = 1
            } else {
              basicconf.cod = 0
            }
            break
        }
      })
      param.basicConfig = basicconf
      if (this.card_first === true) {
        param.card_first = 1
      } else {
        param.card_first = 0
      }
      if (this.balance_first === true) {
        param.balance_first = 1
      } else {
        param.balance_first = 0
      }
      if (this.score_first === true) {
        param.score_first = 1
      } else {
        param.score_first = 0
      }
      console.log(param)
      payUpdate(param).then(res => {
        if (res.error === 0) {
          this.$message.success('更新成功！')
          this.initData()
        } else {
          this.$message.error('更新失败！')
        }
      })
    },
    // 微信支付配置事件
    handleSetting () {
      wechatPaySelect().then(res => {
        console.log(res)
        if (res.error === 0) {
          this.wechatpayconf = res.content
        } else {
          this.$message.error(res.message)
        }
      })
      this.showSettingDialog = true
    },
    // 配置弹出保存、取消事件
    cancle () {
      this.showSettingDialog = false
    },
    save () {
      wechatPayUpdate(this.wechatpayconf).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success('保存成功！')
          this.showSettingDialog = false
        } else {
          this.$message.error(res.message)
        }
      })
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
    cursor: pointer;
  }

  // 微信支付配置弹窗样式
  .settingContent {
    li {
      height: 40px;
      line-height: 40px;
      margin-bottom: 10px;
      padding-left: 10px;
      display: flex;
      span {
        vertical-align: middle;
        margin-right: 10px;
        width: 100px;
      }
      .el-input {
        width: 240px !important;
        height: 22px !important;
      }
    }
    li:nth-of-type(even) {
      background: #f3f3f3;
    }
    .specialHeight {
      height: 110px;
      line-height: 110px;
      .textarea {
        width: 240px;
        // line-height: 100px;
        // height: 100px !important;
        // overflow-y: auto;
      }
      .textarea > .el-input__inner {
        height: 100px !important;
      }
    }
  }
  .el-dialog__header {
    text-align: center !important;
  }
  .el-dialog__body {
    padding: 0 20px !important;
  }
}
</style>
