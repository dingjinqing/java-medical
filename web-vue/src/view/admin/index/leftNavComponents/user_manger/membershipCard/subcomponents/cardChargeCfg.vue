<template>
  <div class="card-charge">
    <el-form
      :model="ruleForm"
      status-icon
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
    >
      <div class="card-charge-top">
        <el-form-item prop="sendMoney">
          <div class="charge-item">
            <el-checkbox v-model="ruleForm.powerCard">
              {{$t('memberCard.powerCard')}}
            </el-checkbox>
            <span class="send-info">
              {{$t('memberCard.openCardSend')}}
            </span>
            <el-input-number
              v-model="ruleForm.sendMoney"
              size="small"
              :precision="2"
              :controls="false"
              :min="0"
              :max="999999999"
              class="inputWidth"
            >
            </el-input-number>
            <span class='yuan-info'>{{ $t('memberCard.yuan') }}</span>
          </div>
        </el-form-item>
      </div>
      <el-form :model="ruleForm" :rules="rules"  ref="ruleFormCharge"  :inline-message="true" label-width="100px">
      <div class="card-charge-middle">

          <el-form-item class='only-charge'>
            <div class="charge-sub-item">
              <el-radio
                v-model="ruleForm.offset"
                label="2"
              >
                {{ $t('memberCard.justCharge') }}
              </el-radio>
            </div>
          </el-form-item>

            <div class="charge-sub-item">
              <el-form-item class='charge-full' prop="chargeFull">
              <el-radio
                v-model="ruleForm.offset"
                label="0"
              >
                {{ $t('memberCard.chargeFull') }}
              </el-radio>
              <el-input-number
                v-model="ruleForm.chargeInputLeft"
                size="small"
                :precision="2"
                :controls="false"
                :min="0"
                :max="999999999"
              >
              </el-input-number>
              <span class='send-info'>{{ $t('memberCard.send') }}</span>
              <el-input-number
                v-model="ruleForm.chargeInputRight"
                size="small"
                :precision="2"
                :controls="false"
                :min="0"
                :max="999999999"
              >
              </el-input-number>
              <span class='yuan-info'>{{ $t('memberCard.yuan') }}</span>
              <img
                style="cursor:pointer"
                :src="$imageHost +'/image/admin/sign_jia.png' "
                @click="handleToAddChargeArr()"
              >
              </el-form-item>
            </div>
            <div v-if="ruleForm.offset==='0'">

              <el-form-item
                v-for="(item,index) in ruleForm.addChargeArr"
                :key="index"
                :prop="'addChargeArr.'+index+'.leftInput'"
                :rules="rules.chargeEach"
                class="charge-add-sub-item"
              >
                <span class="charge-full">{{ $t('memberCard.chargeFull') }}</span>
                <el-input-number
                  v-model="ruleForm.addChargeArr[index].leftInput"
                  size="small"
                  :precision="2"
                  :controls="false"
                  :min="0"
                  :max="999999999"
                >
                </el-input-number>
                <span class='send-info'>{{ $t('memberCard.send') }}</span>
                <el-input-number
                  v-model="ruleForm.addChargeArr[index].rightInput"
                  size="small"
                  :precision="2"
                  :controls="false"
                  :min="0"
                  :max="999999999"
                >
                </el-input-number>
                <span class='yuan-info'>{{ $t('memberCard.yuan') }}</span>
                <img
                  style="cursor:pointer"
                  :src="$imageHost +'/image/admin/sign_del.png' "
                  @click="handleToDelChargeArr(index)"
                >
              </el-form-item>
            </div>
          <div class="charge-full-each" >
            <el-form-item class="charge-sub-item" prop="chargeBottom">
              <el-radio
                v-model="ruleForm.offset"
                label="1"
              >
                {{ $t('memberCard.chargeEachFull') }}
              </el-radio>
              <el-input-number
                v-model="ruleForm.chargeInputLeftM"
                size="small"
                :precision="2"
                :controls="false"
                :min="0"
                :max="999999999"
              >
              </el-input-number>
              <span class='send-info'>{{ $t('memberCard.send') }}</span>
              <el-input-number
                v-model="ruleForm.chargeInputRightM"
                size="small"
                :precision="2"
                :controls="false"
                :min="0"
                :max="999999999"
              >
              </el-input-number>
              <span class='yuan-info'>{{ $t('memberCard.yuan') }}</span>
            </el-form-item>
          </div>
      </div>
    </el-form>
    </el-form>
  </div>
</template>
<script>
export default {
  props: {
    val: {
      type: Object,
      required: true
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
  watch: {
    'ruleForm.powerCard': {
      handler (newName, oldName) {
        this.val.powerCard = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.sendMoney': {
      handler (newName, oldName) {
        this.val.sendMoney = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.offset': {
      handler (newName, oldName) {
        this.val.offset = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.chargeInputLeft': {
      handler (newName, oldName) {
        this.val.chargeInputLeft = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.chargeInputRight': {
      handler (newName, oldName) {
        this.val.chargeInputRight = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.chargeInputLeftM': {
      handler (newName, oldName) {
        this.val.chargeInputLeftM = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.chargeInputRightM': {
      handler (newName, oldName) {
        this.val.chargeInputRightM = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.addChargeArr': {
      handler (newName, oldName) {
        this.val.addChargeArr = newName
        this.ruleForm = this.val
      },
      deep: true
    }
  },
  mounted () {
    this.$on('checkRule', () => {
      if (this.ruleForm.powerCard) {
        this.$refs.ruleForm.validate((valid) => {
          if (!valid) {
            this.$message.warning('请输入赠送金额')
            this.ruleForm.valid = false
          } else {
            this.$refs.ruleFormCharge.validate((valid) => {
              if (!valid) {
                this.$message.warning('请输入金额')
                this.ruleForm.valid = false
              } else {
                this.ruleForm.valid = true
              }
            })
          }
        })
      }
    })
  },
  data () {
    let validateSendMoney = (rule, value, callback) => {
      if (this.ruleForm.powerCard) {
        if (this.checkSendMoneyError(value)) {
          callback(new Error('请输入赠送金额'))
        } else {
          callback()
        }
      }
      callback()
    }

    let validateChargeEach = (rule, value, callback) => {
      if (this.ruleForm.offset === '0') {
        let index = Number(rule.fullField.split('.')[1])
        if (value && this.ruleForm.addChargeArr[index].rightInput) {
          callback()
        } else {
          callback(new Error('请输入金额'))
        }
      } else {
        callback()
      }
    }

    let validateChargeFull = (rule, value, callback) => {
      if (this.ruleForm.offset === '0') {
        if (this.ruleForm.chargeInputLeft && this.ruleForm.chargeInputRight) {
          callback()
        } else {
          callback(new Error('请输入金额'))
        }
      } else {
        callback()
      }
    }

    let validateChargeBottom = (rule, value, callback) => {
      if (this.ruleForm.offset === '1') {
        if (this.ruleForm.chargeInputLeftM && this.ruleForm.chargeInputRightM) {
          callback()
        } else {
          callback(new Error('请输入金额'))
        }
      } else {
        callback()
      }
    }
    return {
      rules: {
        sendMoney: [
          { validator: validateSendMoney, trigger: 'blur' }
        ],
        chargeFull: [
          { validator: validateChargeFull, trigger: 'blur' }
        ],
        chargeEach: [
          { validator: validateChargeEach, trigger: 'blur' }
        ],
        chargeBottom: [
          { validator: validateChargeBottom, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    checkSendMoneyError (val) {
      if (val === 0) {
        return false
      }
      return this.isBlank(val)
    },
    isBlank (val) {
      return (!val || /^\s*$/.test(val))
    },
    handleToAddChargeArr () {
      this.ruleForm.addChargeArr.push({
        leftInput: undefined,
        rightInput: undefined
      })
    },
    handleToDelChargeArr (index) {
      this.ruleForm.addChargeArr.splice(index, 1)
    }
  }
}
</script>
<style scoped lang="scss">
.card-charge {
  .card-charge-top {
    // /deep/ .el-form-item__error {
    //   padding-left: 200px;
    // }
    /deep/ .el-form-item__error {
      padding-left: 100px;
    }
    .charge-item {
      padding-left: 100px;
      display: flex;
      align-items: center;
      .send-info {
        margin-left: 30px;
        margin-right: 20px;
      }
      .yuan-info {
        margin-left: 20px;
      }
    }
  }
  .card-charge-middle {
    /deep/ .el-form-item {
      margin-bottom: 2px;
    }
    .only-charge {
      .charge-sub-item {
        padding-left: 170px;
        margin-right: 32px;
      }
    }
    .charge-sub-item {
      .charge-full {
        padding-left: 170px;
        margin-right: 32px;
        display: flex;
        align-items: center;
        /deep/ .el-input-number {
          width: 120px;
        }
        .send-info {
          margin: 0 10px;
        }
        .yuan-info {
          margin: 0 5px 0 20px;
        }
      }
    }

    .charge-add-sub-item {
      padding-left: 198px;
      display: flex;
      align-items: center;
      .charge-full {
        margin-right: 31px;
      }
      /deep/ .el-input-number {
        width: 120px;
      }
      .send-info {
        margin: 0 10px;
      }
      .yuan-info {
        margin: 0 5px 0 20px;
      }
    }

    .charge-full-each {
      margin-bottom: 20px;
      .charge-sub-item {
        padding-left: 170px;
        margin-right: 32px;
        display: flex;
        align-items: center;
        /deep/ .el-radio {
          margin-right: 19px;
        }
        /deep/ .el-input-number {
          width: 120px;
        }
        .send-info {
          margin: 0 15px;
        }
        .yuan-info {
          margin: 0 5px 0 20px;
        }
      }
    }
  }
}
</style>
