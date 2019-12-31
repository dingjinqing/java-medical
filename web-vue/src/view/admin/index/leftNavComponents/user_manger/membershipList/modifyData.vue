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
                        :placeholder="$t('membershipIntroduction.Pleasecontent')"
                        :class="{errorClass: inputError}"
                        @blur="inputCheckValid"
                    >
                    </el-input-number>
                    <span>{{model.tips}}</span>
                </div>
                <div v-if="inputError" class="show-item">
                    <span style="color: red">请输入数额</span>
                </div>
                <div class="show-item">
                    <span>{{model.bzText}}</span>
                    <el-input
                        v-model="desc"
                        :placeholder="$t('membershipIntroduction.Pleasecontent')"
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
                    {{ certain(model.index) }}
                </el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
import { accountAddRequest } from '@/api/admin/membershipList.js'
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
    certain (index) {
      // 余额
      if (index === 0) {
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
        }
      }
    },
    submitAccount () {
      accountAddRequest(
        {
          'userId': this.userId,
          'account': this.model.persentMoney,
          'remark': this.desc,
          'amount': this.inputValue
        }
      ).then(res => {
        if (res.error === 0) {
          this.$message.success(this.$t('membershipIntroduction.success'))
          this.$emit('submitRes', true)
        } else {
          this.$message.error(this.$t('membershipIntroduction.error'))
          this.$emit('submitRes', false)
        }
      })
    },
    submitScore () {

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
        }
    }
}
</style>
