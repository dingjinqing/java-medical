<template>
  <div class="content">
    <el-row>
      <el-col :span="4"></el-col>
      <el-col :span="16">
        <el-row>
          <el-col :span="24">
            <div class="warnThing">
              <el-alert
                :title="$t('groupIntegration.warnThing')"
                type="warning"
                show-icon
                center
                :closable='false'
              >
              </el-alert>
            </div>
          </el-col>
        </el-row>
        <el-row style="margin-top: 15px;">
          <el-col :span="7">
            <div>
              <img
                style="width: 100%;"
                :src="$imageHost+'/image/admin/shop_beautify/phone_tops.png'"
              >
            </div>
            <div
              class="showMsg"
              v-html="editMsg"
            >
            </div>
          </el-col>
          <el-col :span="17">
            <div class="right">
              <el-form
                ref="form"
                label-width="80px"
              >
                <el-form-item :label="$t('groupIntegration.pageInfo')+':'">
                  <el-radio-group
                    v-model="isUseDefault"
                    @change="changeRadio"
                  >
                    <el-radio :label="1">{{$t('groupIntegration.title1')}}</el-radio>
                    <el-radio :label="0">{{$t('groupIntegration.title0')}}</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-form>
              <div class="innerRight">
                <editor
                  v-model="editMsg"
                  :disabled="disabled"
                  ref="editor"
                />
              </div>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <div class="footer">
              <el-button
                type="primary"
                size="small"
                @click="saveMsg"
              >{{$t('groupIntegration.save')}}</el-button>
            </div>
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="4"></el-col>
    </el-row>
  </div>
</template>
<script>
import editor from '@/components/admin/tinymceEditor/tinymceEditor'
export default {
  components: {
    editor
  },
  props: ['sendMsg'],
  mounted () {
    // 初始化数据
    this.langDefault()
    this.setValue()
    this.defalutValue()
  },
  data () {
    return {
      editMsg: null,
      isUseDefault: 0,
      disabled: false,
      // msg: this.$t('lotteryDraw.templateData')
      msg: `
        <div style="line-height: 1.5;">
          <p>参与步骤</p>
          <p>1.在低价抽奖商品列表页，点击商品进入商品详情页，通过下单开团入口进入订单结算页，付款成功后，按页面提示分享给微信好友；</p>
          <p>2.好友通过小程序落地页查看活动现状，完成支付，参与拼团；</p>
          <p>3.支付人数在有效期内达到门槛值，则团内所有用户都获得抽奖资格，等待公布中奖结果；</p>
          <p>4.中奖结果在活动结束时公布，所有中奖订单进入发货流程，未中奖用户及未成团用户将全额退款至原支付账户。</p>
          <p>参与规则</p>
          <p>1.活动期间，同一账户每个拼团商品仅可购买一单；</p>
          <p>2.拼团抽奖商品库存有限，如因库存不足导致抢购失败或发货失败，订单将全额退款至原支付账户。</p>
        </div>
      `
    }
  },
  methods: {
    setValue () {
      if (!this.isEmpty(this.sendMsg)) {
        this.isUseDefault = Number(this.sendMsg.isUseDefault)
        this.editMsg = this.sendMsg.document
      }
    },
    defalutValue () {
      let value = this.isUseDefault
      if (value === 1) {
        this.editMsg = this.msg
        this.disabled = true
      }
      if (value === 0) {
        this.$refs.editor.clear()
        this.disabled = false
      }
    },
    changeRadio () {
      this.defalutValue()
    },
    isEmpty (obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    },
    checkInfo () {
      if (this.isEmpty(this.isUseDefault)) {
        this.$message.warning(this.$t('groupIntegration.pleasePageInfo'))
        return false
      }
      if (this.isEmpty(this.editMsg)) {
        this.$message.warning(this.$t('groupIntegration.pleaseWritePageInfo'))
        return false
      }
      return true
    },
    saveMsg () {
      if (!this.checkInfo()) {
        return
      }
      let msg = {
        'document': this.editMsg,
        'isUseDefault': String(this.isUseDefault)
      }
      this.$emit('ActivityMsg', msg)
    }
  }
}
</script>
<style lang="scss" scoped>
.content {
  .el-col {
    min-height: 1px;
  }
}
.showMsg {
  border: 1px solid #ededed;
  border-top: 1px solid #f2f2f2;
  p {
    line-height: 20px;
  }
  padding: 0 8px;
  overflow-y: auto;
  height: 540px;
  .headline {
    font-weight: bold;
  }
}
.footer {
  text-align: center;
  margin-top: 15px;
}
.right {
  margin-left: 22px;
  height: 595px;
  border: 1px solid #ededed;
  background-color: #f8f8fa;
}
.innerRight {
  margin: 9px;
}
.warnThing {
  border: 1px solid #ffd5a3;
}
</style>
