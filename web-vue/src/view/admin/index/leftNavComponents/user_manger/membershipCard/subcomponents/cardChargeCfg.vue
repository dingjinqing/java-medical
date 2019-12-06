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
        <el-form-item class='charge-full'>
          <div class="charge-sub-item">
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
          </div>
          <div v-if="ruleForm.offset==='0'">
            <div
              v-for="(item,index) in ruleForm.addChargeArr"
              :key="index"
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
            </div>
          </div>
        </el-form-item>
        <el-form-item class="charge-full-each">
          <div class="charge-sub-item">
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
          </div>
        </el-form-item>
      </div>
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
    return {
      rules: {
        sendMoney: [
          { validator: validateSendMoney, trigger: 'blur' }
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
        leftInput: '',
        rightInput: ''
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
    .charge-item {
      padding-left: 100px;
      display: flex;
      align-items: center;
      .send-info {
        margin-right: 20px;
      }
      .yuan-info {
        margin-left: 20px;
      }
      .inputWidth {
        width: 170px;
        background: red;
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
    .charge-full {
      .charge-sub-item {
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
    }
    .charge-full-each {
      .charge-sub-item {
        padding-left: 170px;
        margin-right: 32px;
        display: flex;
        align-items: center;
        /deep/ .el-radio {
          margin-right: 17px;
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
    }
  }
}
</style>
