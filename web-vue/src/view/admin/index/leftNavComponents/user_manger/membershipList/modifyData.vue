<template>
    <div class='modifyDataDialog'>
        <el-dialog
            :title="model.title"
            :width="width"
            :visible.sync="model.visiable"
            :modal-append-to-body="true"
            @close="close">
            <div class="top">
                <div class="show-item">
                    <span>{{model.presentText}}:</span>
                    <span>{{model.persentMoney}}</span>
                </div>
                <div class="show-item">
                    <span>{{model.addText}}</span>
                    <el-input-number
                        v-model.lazy.number="inputValue"
                        :precision="2"
                        :controls="false"
                        size="small"
                        :max="999999999"
                        :placeholder="$t('membershipIntroduction.Pleasecontent')"
                        :class="{errorClass: inputError}"
                        @blur="inputCheckValid"
                    >
                    </el-input-number>
                    <span>{{model.tips}}</span>
                </div>
                <div v-if="inputError" class="show-item">
                    <span class="error-tips">请输入数额</span>
                </div>
                <div class="show-item">
                    <span>{{model.bzText}}</span>
                    <el-input
                        v-model="desc"
                        size="small"
                    >
                    </el-input>
                </div>
            </div>
            <div
                slot="footer"
                class="dialog-footer"
            >
                <el-button
                    size="small"
                    type="primary"
                    @click="model.visiable=false"
                >
                    {{ $t('membershipIntroduction.cancel') }}
                </el-button>
                <el-button
                    size="small"
                    type="primary"
                    @click="submitData(model.index)"
                >
                    {{ certain() }}
                </el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
import { accountAddRequest, scoreUpdateRequest } from '@/api/admin/membershipList.js'
import { chargeConsume } from '@/api/admin/memberManage/memberCard.js'
export default {
  props: {
    model: {
      type: Object,
      required: true
    },
    userId: {
      type: Number
    }
  },
  data () {
    return {
      width: '40%',
      desc: null,
      inputValue: void 0,
      inputError: false
    }
  },
  methods: {
    certain () {
      console.log('->', this.model.index)
      let index = this.model.index

      // 余额,卡余额
      if ([0, 2].includes(index)) {
        return this.$t('membershipIntroduction.accountCertain')
      } else if (index === 1) {
        // 积分
        return this.$t('membershipIntroduction.scoreCertain')
      }
    },
    submitData (index) {
      if (this.inputCheckValid()) {
        if (index === 0) {
        // 余额
          this.submitAccount()
        } else if (index === 1) {
        // 积分
          this.submitScore()
        } else if (index === 2) {
          // 卡余额，门店兑换次数，商品兑换次数
          this.submitCardChargeConsume(this.model.type)
        }
      }
    },
    submitAccount () {
      // 余额更新
      accountAddRequest(
        {
          'userId': this.userId,
          'account': this.model.persentMoney,
          'remark': this.desc,
          'amount': this.inputValue
        }
      ).then(res => {
        if (res.error === 0) {
          this.success()
        } else {
          this.fail()
        }
      })
    },
    submitScore () {
      // 积分更新
      scoreUpdateRequest({
        'userId': [this.userId],
        'score': this.inputValue,
        'remark': this.desc,
        'scoreDis': this.model.persentMoney
      }).then(res => {
        if (res.error === 0) {
          this.success()
        } else {
          this.fail()
        }
      })
    },
    submitCardChargeConsume (type) {
      let obj = null
      console.log(typeof type, type)
      switch (type) {
        case 0:
          // 卡余额
          obj = {
            moneyDis: this.model.presentText,
            reduce: this.inputValue,
            userId: this.model.userId,
            cardId: this.model.cardId,
            cardNo: this.model.cardNo,
            message: this.desc,
            cardType: this.model.cardType
          }
          break
        case 1:
          // 兑换商品次数

          break
        case 2:
          // 兑换门店次数

          break
        default:
          break
      }
      // 会员卡余额，兑换次数
      chargeConsume(obj).then(res => {

      })
    },
    success () {
      this.$message.success(this.$t('membershipIntroduction.success'))
      this.$emit('submitRes', true)
    },
    fail () {
      this.$message.error(this.$t('membershipIntroduction.error'))
      this.$emit('submitRes', false)
    },
    inputCheckValid () {
      console.log('check input info')
      // 检查输入
      this.inputError = !this.inputValue && this.inputValue !== 0
      return !this.inputError
    },
    cleanVariable () {
      // 清空数据
      this.inputValue = void 0
      this.desc = null
      this.inputError = false
    },
    close () {
      console.log('close')
      this.cleanVariable()
    }

  }
}
</script>

<style scoped lang="scss">
.modifyDataDialog{
    .top{
        .show-item{
            margin-bottom: 10px;
            display: flex;
            span{
                white-space: nowrap;
                height: 32px;
                line-height: 32px;
                margin-left: 10px;
                margin-right: 10px;
            }
            .errorClass{
               /deep/ .el-input__inner{
                    border-color: red !important;
                }
            }
            .error-tips{
                color: red;
                font-size: 13px;
            }
        }
    }
}
</style>
