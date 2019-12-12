<template>
  <div class="card-receive-div">
    <el-form
      :model="ruleForm"
      ref="ruleForm"
      label-width="100px"
    >

      <el-form-item
        :label="$t('memberCard.isNeedtoBuy')"
        class="receive-item"
        :rules="[{required: true}]"
      >
        <div class="receive-top">
          <el-radio
            v-model="ruleForm.isPay"
            label="0"
          >
            {{ $t('memberCard.receiveDirect') }}
          </el-radio>
          <el-radio
            v-model="ruleForm.isPay"
            label="1"
          >{{ $t('memberCard.needBuy') }}</el-radio>
          <el-radio
            v-model="ruleForm.isPay"
            label="2"
          >{{ $t('memberCard.needReceiveCode') }}</el-radio>
        </div>
        <div class="receive-bottom">
          <div
            v-if="ruleForm.isPay==='1'"
            class="receive-buy"
          >
            <div>
              <el-radio
                v-model="ruleForm.payType"
                label='0'
              >
                {{ $t('memberCard.crashBuy') }}
              </el-radio>
              <el-input-number
                :controls="false"
                :precision="2"
                :min="0"
                :max="999999999"
                v-model="ruleForm.payMoney"
                :placeholder="$t('memberCard.pleaseInput')"
                size="small"
              >
              </el-input-number>
              <span>{{ $t('memberCard.yuan') }}</span>
            </div>
            <div>
              <el-radio
                v-model="ruleForm.payType"
                label='1'
              >
                {{ $t('memberCard.scoreBuy') }}
              </el-radio>
              <el-input-number
                :controls="false"
                :min="0"
                :max="999999999"
                v-model="ruleForm.payScore"
                :placeholder="$t('memberCard.pleaseInput')"
                size="small"
              >
              </el-input-number>
              <span>{{ $t('memberCard.unitM') }}</span>
            </div>
          </div>
          <div
            v-if="ruleForm.isPay==='2'"
            class="receive-code"
          >
            <div class="receive-code-one">

              <el-radio
                v-model="ruleForm.receiveAction"
                label="1"
              >
                {{ $t('memberCard.receiveCodeSetting') }}
              </el-radio>

              <div v-if="ruleForm.receiveAction==='1'">
                <div
                  v-for="(item,index) in ruleForm.codeAddDivArr"
                  :key="index"
                >
                  <div>
                    <span>{{ $t('memberCard.batchOne') }}</span>
                    <span>{{ $t('memberCard.batchName') }}</span>
                    <el-input
                      v-model="item.batchName"
                      size="small"
                    ></el-input>
                    <span
                      v-for="(codeItem,codeIndex) in codeArr"
                      :key="codeIndex"
                      @click="handleCallCodeDialog(index,codeIndex)"
                    >{{codeItem}}</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="receive-code-two">

              <el-radio
                v-model="ruleForm.receiveAction"
                label="2"
              >
                {{ $t('memberCard.cardPassAndNo') }}
              </el-radio>
              <div v-if="ruleForm.receiveAction==='2'">
                <div
                  v-for="(item,index) in ruleForm.codeAddDivArrBottom"
                  :key="index"
                >
                  <div>
                    <span>{{ $t('memberCard.batchOne') }}</span>
                    <span>{{ $t('memberCard.batchName') }}</span>
                    <el-input
                      v-model="item.pwdName"
                      size="small"
                    ></el-input>
                    <span
                      v-for="(codeItem,codeIndex) in codeArr"
                      :key="codeIndex"
                      @click="handleCallCodeDialogBottom(index,codeIndex)"
                    >{{codeItem}}</span>
                  </div>
                </div>
              </div>
            </div>

          </div>

          <div
            v-if="ruleForm.cardType===1"
            class="limit-card-send"
          >
            <div
              v-if="ruleForm.isPay !== '0'"
              class="split-line"
            ></div>
            <div class="send-limit">
              <div class="send-num">
                <span>发送总量：</span>
                <el-input-number
                  v-model="ruleForm.stock"
                  :controls="false"
                  :min="0"
                  :max="999999999"
                  size="small"
                >
                </el-input-number>
                <span>张</span>
                <span class="send-tip">填0时为不限制</span>
                <span>当前已方法： 1张</span>
              </div>
              <div class="person-receive-num">
                <span>领取限制：每人限领</span>
                <el-input-number
                  v-model="ruleForm.limits"
                  :controls="false"
                  :min="0"
                  :max="999999999"
                  size="small"
                >
                </el-input-number>
                <span>张</span>
                <span class="send-tip">填0时为不限制</span>
              </div>
            </div>
          </div>
        </div>

      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  props: {
    val: {
      type: Object,
      default: () => {
        return {
          cardType: 1,
          isPay: '2',
          payType: '0',
          payMoney: '',
          payScore: '',
          receiveAction: '2',
          codeAddDivArr: [{ batchName: null, batchId: null }],
          codeAddDivArrBottom: [{ pwdName: null, pwdId: null }]
        }
      }
    }

  },
  computed: {
    ruleForm: {
      get () {
        return this.val
      },
      set () {
        this.$emit('input', this.ruleForm)
      }
    }
  },
  data () {
    return {
      codeArr: null
    }
  },
  created () {
    this.codeArr = this.$t('memberCard.codeArr')
  },
  watch: {
    lang () {
      this.codeArr = this.$t('memberCard.codeArr')
    }
  },
  methods: {
    handleCallCodeDialog (index, codeIndex) {

    }
  }
}
</script>
<style scoped lang="scss">
.card-receive-div {
  .receive-item {
    padding-left: 80px;
    /deep/ .el-form-item__label {
      width: 122px !important;
    }
    .receive-top {
    }
    .receive-bottom {
      width: 100%;
      padding: 0px 20px;
      background-color: #fff;
      border: 1px solid #ddd;
      margin: 20px 0 0 -100px;
      border-radius: 4px;
      .receive-buy {
        padding: 5px 20px;
      }
      .receive-code {
        .receive-code-one {
          padding: 5px 20px;
          display: flex;
          /deep/ .el-radio__label {
            height: 41px;
            line-height: 41px;
          }
          /deep/ .el-input {
            width: 60px;
            margin-right: 10px;
          }
          span {
            display: inline-block;
            margin-right: 10px;
            &:nth-of-type(3),
            &:nth-of-type(4),
            &:nth-of-type(5),
            &:nth-of-type(6) {
              color: #5a83f9;
              cursor: pointer;
            }
          }
        }
        .receive-code-two {
          padding: 5px 20px;
          display: flex;
          /deep/ .el-radio__label {
            height: 41px;
            line-height: 41px;
          }
          /deep/ .el-input {
            width: 60px;
            margin-right: 10px;
          }
          span {
            display: inline-block;
            margin-right: 8px;
            &:nth-of-type(3),
            &:nth-of-type(4),
            &:nth-of-type(5),
            &:nth-of-type(6) {
              color: #5a83f9;
              cursor: pointer;
            }
          }
        }
      }
      .limit-card-send {
        .split-line {
          border-bottom: 1px solid #e6e6e6;
        }
        .send-limit {
          margin-top: 10px;
          margin-left: 20px;
          .send-num {
            span {
              &:nth-of-type(1),
              &:nth-of-type(2),
              &:nth-of-type(3) {
                margin-right: 10px;
              }
              &:nth-of-type(3) {
                color: #999;
              }
              &:nth-of-type(4) {
                color: #ff6666;
                font-size: 13px;
              }
            }
            /deep/ .el-input {
              width: 90%;
            }
          }
          .person-receive-num {
            span {
              &:nth-of-type(1),
              &:nth-of-type(2) {
                margin-right: 10px;
              }
              &:nth-of-type(3) {
                color: #999;
              }
            }
            /deep/ .el-input {
              width: 90%;
            }
          }
        }
      }
    }
  }
}
</style>
