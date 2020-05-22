<template>
  <div class="main-container">
    <div class="content friend-pay-page">
      <div class="fp-top">
        <h1>好友代付</h1>
        <ul>
          <li>单人代付：用户邀请一个好友代付全部款项。</li>
          <li>多人代付：用户可将代付信息发到微信群或多个微信好友，请TA们帮忙代付。</li>
          <li>温馨提示：代付订单将在下单后24小时内有效，逾期后未完成支付，所有款项将自动退回到付款人账户。</li>
        </ul>
        <div class="fpc-switch">
          <el-switch
            v-model="insteadPayInfo.status"
            active-color="rgb(247, 147, 30)"
            inactive-color="rgb(204, 204, 204)"
          ></el-switch>
          <span class="fpc-switch-text">{{insteadPayInfo.status?'已开启':'已关闭'}}</span>
        </div>
      </div>
      <div
        class="fp-center"
        v-show="insteadPayInfo.status"
      >
        <el-form
          ref="fpForm"
          size="small"
          :model="insteadPayInfo"
          :rules="insteadPayRules"
        >
          <el-form-item prop="singlePay">
            <div class="fpc-title">
              <el-checkbox v-model="insteadPayInfo.singlePay">单人代付</el-checkbox>
            </div>
            <div class="fpc-con">
              <el-form-item
                label="发起人默认留言："
                prop="orderUserMessageSingle"
              >
                <el-input
                  v-model="insteadPayInfo.orderUserMessageSingle"
                  :disabled="!insteadPayInfo.singlePay"
                  placeholder=""
                ></el-input>
              </el-form-item>
              <el-form-item
                label="代付人默认留言："
                prop="insteadPayMessageSingle"
              >
                <el-input
                  v-model="insteadPayInfo.insteadPayMessageSingle"
                  :disabled="!insteadPayInfo.singlePay"
                  placeholder=""
                ></el-input>
              </el-form-item>
            </div>
            <div class="fpc-title">
              <el-checkbox
                v-model="insteadPayInfo.multiplePay"
                @change="multiplePayChange"
              >多人代付</el-checkbox>
            </div>
            <div class="fpc-con">
              <el-form-item
                label="发起人默认留言："
                prop="orderUserMessageMultiple"
              >
                <el-input
                  v-model="insteadPayInfo.orderUserMessageMultiple"
                  :disabled="!insteadPayInfo.multiplePay"
                ></el-input>
              </el-form-item>
              <el-form-item
                label="代付人默认留言："
                prop="insteadPayMessageMultiple"
              >
                <el-input
                  v-model="insteadPayInfo.insteadPayMessageMultiple"
                  :disabled="!insteadPayInfo.multiplePay"
                ></el-input>
              </el-form-item>
              <el-form-item
                label="代付金额比例："
                prop="tableData"
              >
                <el-popover
                  placement="right"
                  trigger="hover"
                >
                  <div>
                    <el-image
                      style="width:200px;"
                      :src="srcList[0]"
                      :preview-src-list="srcList"
                    ></el-image>
                  </div>
                  <el-button
                    slot="reference"
                    type="text"
                  >查看示例</el-button>
                </el-popover>
                <el-table
                  header-row-class-name="tableClss"
                  class="fpc-table"
                  :data="insteadPayInfo.tableData"
                  border
                >
                  <el-table-column label="选项描述">
                    <template slot-scope="scope">
                      <div>
                        <el-form-item :prop="'tableData.'+ scope.$index +'.name'">
                          <el-input
                            v-model="scope.row.name"
                            :disabled="!insteadPayInfo.multiplePay"
                          ></el-input>
                        </el-form-item>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column>
                    <template slot="header">
                      <span>代付金额比例</span>
                      <el-popover
                        placement="top"
                        trigger="hover"
                        width="450"
                      >
                        <p>好友可选择商家设定的代付金额比例支付。</p>
                        <p>例：订单金额为100元，用户选择多人代付，分享给好友A，好友A可选择代付金额比例10%，即10元；代付金额比例30%，即30元；代付订单剩余金额，即100元。好友A也可以手动填写代付金额帮助好友付款。</p>
                        <img
                          class="tishi-icon"
                          slot="reference"
                          :src="$imageHost + '/image/admin/analysis_tishi.png'"
                        >
                      </el-popover>
                    </template>
                    <template slot-scope="scope">
                      <div v-if="scope.$index < 2">
                        <el-form-item :prop="'tableData.'+ scope.$index +'.percent'">
                          <el-input
                            v-model="scope.row.percent"
                            :disabled="!insteadPayInfo.multiplePay"
                          ></el-input>
                        </el-form-item>
                      </div>
                      <div v-else-if="scope.$index === 2">
                        <span>订单剩余金额</span>
                      </div>
                    </template>
                  </el-table-column>
                </el-table>
              </el-form-item>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <div class="footer">
      <el-button
        type="primary"
        size="small"
        @click="updateFriendPay"
      >保存</el-button>
    </div>
  </div>
</template>

<script>
import { getFriendPayApi, updateFriendPayApi } from '@/api/admin/marketManage/friendPay.js'
export default {
  components: {
  },
  data () {
    let that = this
    function validPayFor (rules, value, callback) {
      let single = that.insteadPayInfo.singlePay
      let multiple = that.insteadPayInfo.multiplePay
      if (that.insteadPayInfo.status && !single && !multiple) {
        callback(new Error('请选择代付类型'))
      }
      callback()
    }
    function validos (rules, value, callback) {
      if (that.insteadPayInfo.status && that.insteadPayInfo.singlePay && !value) {
        callback(new Error('请填写单人代付留言'))
      }
      callback()
    }
    function validis (rules, value, callback) {
      if (that.insteadPayInfo.status && that.insteadPayInfo.singlePay && !value) {
        callback(new Error('请填写单人代付留言'))
      }
      callback()
    }
    function validom (rules, value, callback) {
      if (that.insteadPayInfo.status && that.insteadPayInfo.singlePay && !value) {
        callback(new Error('请填写多人代付留言'))
      }
      callback()
    }
    function validim (rules, value, callback) {
      if (that.insteadPayInfo.status && that.insteadPayInfo.singlePay && !value) {
        callback(new Error('请填写多人代付留言'))
      }
      callback()
    }
    function validTable (rules, value, callback) {
      let table = value
      if (that.insteadPayInfo.status && that.insteadPayInfo.multiplePay) {
        for (let i = 0; i < table.length; i++) {
          let item = table[i]
          if (!item.name) {
            callback(new Error('选项描述需设置三个'))
            break
          }
          if (!item.percent && i < 2) {
            callback(new Error('金额比例需设置两个'))
            break
          }
        }
      }
      callback()
    }
    return {

      srcList: [this.$imageHost + '/image/admin/new_preview_image/friend_help.jpg'],
      insteadPayInfo: {
        status: true,
        tableData: [
          { name: '意思意思', percent: '' },
          { name: '情比金坚', percent: '' },
          { name: '一掷千金', percent: '' }
        ]
      },
      insteadPayRules: {
        singlePay: [{ validator: validPayFor, trigger: 'change' }],
        orderUserMessageSingle: [{ validator: validos, trigger: 'change' }],
        insteadPayMessageSingle: [{ validator: validis, trigger: 'change' }],
        orderUserMessageMultiple: [{ validator: validom, trigger: 'change' }],
        insteadPayMessageMultiple: [{ validator: validim, trigger: 'change' }],
        tableData: [{ validator: validTable, trigger: 'change' }]
      }
    }
  },
  mounted (o) {
    console.log(o)
    this.initData()
  },
  methods: {
    initData () {
      let that = this
      let params = {}
      getFriendPayApi(params).then(res => {
        if (res.error === 0) {
          let content = res.content
          console.log(content)
          let datas = []
          for (let i = 0; i < content.payRatioText.length; i++) {
            datas[i] = {}
            datas[i]['name'] = content.payRatioText[i]
            datas[i]['percent'] = content.payRatioNumber[i] ? content.payRatioNumber[i] : ''
          }
          content.tableData = datas
          this.insteadPayInfo = content
        } else {
          that.$message.error(res.message)
        }
      })
    },
    multiplePayChange (val) {
      if (!val) {
        this.$refs.fpForm.validateField('singlePay')
      }
    },
    updateFriendPay () {
      let that = this
      let params = Object.assign({}, this.insteadPayInfo)
      let payRatioText = []
      let payRatioNumber = []
      this.insteadPayInfo.tableData.forEach(item => {
        payRatioText.push(item.name)
        if (item.percent) {
          payRatioNumber.push(item.percent)
        }
      })
      params.payRatioText = payRatioText
      params.payRatioNumber = payRatioNumber
      this.$refs.fpForm.validate((valid) => {
        if (valid) {
          updateFriendPayApi(params).then(res => {
            if (res.error === 0) {
              that.$message.success(res.message)
            } else {
              that.$message.error(res.message)
            }
          })
        }
      })
    }
  }
}

</script>
<style lang="scss" scoped>
.main-container {
  position: relative;
  padding: 10px;
  margin-bottom: 50px;
  .content {
    background: #fff;
    padding: 10px;
    .fp-top {
      position: relative;
      padding: 10px 15px;
      background: #f5f5f5;
      margin-bottom: 15px;
      font-size: 13px;
      line-height: 24px;
      h1 {
        display: block;
        font-size: 16px;
        color: #333;
        font-weight: bold;
        margin-bottom: 10px;
      }
      .fpc-switch {
        position: absolute;
        top: 10px;
        right: 15px;
      }
      .fpc-switch-text {
        font-size: 14px;
        line-height: 20px;
        margin-left: 10px;
      }
    }
    .fpc-title {
      background: #f5f5f5;
      padding: 10px 15px;
      margin-bottom: 15px;
    }
    .tishi-icon {
      position: relative;
      top: 4px;
    }
    .fp-center {
      .el-input {
        width: 250px;
      }
    }
    .fpc-con {
      padding: 0 10px;
    }
    .fpc-table {
      width: 500px;
      margin-left: 110px;
      .el-input {
        width: 100%;
      }
    }
    /deep/ .tableClss th {
      background-color: #f5f5f5;
      border: none;
      height: 36px;
      font-weight: bold;
      color: #000;
      padding: 8px 10px;
      text-align: center;
    }
  }
  .footer {
    position: fixed;
    bottom: 0;
    z-index: 999;
    left: 150px;
    width: calc(100% - 150px);
    padding: 10px 0;
    background: #fff;
    text-align: center;
  }
}
</style>
