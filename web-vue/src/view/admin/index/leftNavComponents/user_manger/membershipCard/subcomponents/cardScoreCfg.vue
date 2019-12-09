<template>
  <div class="scoreReceiveDiv">
    <el-form
      :model="ruleForm"
      status-icon
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
    >
      <div class="sendScoreTop">
        <el-form-item prop="score">
          <div class="scoreReceiveItem">
            <el-checkbox v-model="ruleForm.powerScore">
              {{ $t('memberCard.getScore') }}
            </el-checkbox>
            <span class="sendInfo">
              {{$t('memberCard.openCardSend')}}
            </span>
            <el-input-number
              v-model="ruleForm.score"
              size="small"
              :controls="false"
              :min="0"
              :max="999999999"
            >
            </el-input-number>
            <span class="scoreInfo">{{ $t('memberCard.score') }}</span>
          </div>
        </el-form-item>
      </div>
      <div class="sendScoreMiddle">
        <el-form-item>
          <div class="scoreReceiveSubItem">
            <el-radio
              v-model="ruleForm.offSet"
              label='0'
            >
              {{ $t('memberCard.shopFull') }}
            </el-radio>
            <el-input
              v-model='ruleForm.shopingInputLeft'
              size="small"
              type="number"
            >
            </el-input>
            <span class="sendInfo">
              {{ $t('memberCard.send') }}
            </span>
            <el-input
              size="small"
              type="number"
              v-model="ruleForm.shopingInputRight"
            >
            </el-input>
            <span class="scoreInfo">{{ $t('memberCard.score') }}</span>
            <img
              style="cursor:pointer"
              :src="$imageHost +'/image/admin/sign_jia.png' "
              @click="handleToAddIntegral()"
            >
          </div>
        </el-form-item>
        <el-form-item v-if="ruleForm.offSet==='0'">
          <div
            v-for="(item,index) in ruleForm.addIntegralArr"
            :key="index"
            class="scoreReceiveAddSubItem"
          >
            <span class="shopFullInfo">{{ $t('memberCard.shopFull') }}</span>
            <el-input
              size="small"
              type="number"
              v-model="ruleForm.addIntegralArr[index].leftInput"
            ></el-input>
            <span class="sendInfo">{{ $t('memberCard.send') }}</span>
            <el-input
              size="small"
              type="number"
              v-model="ruleForm.addIntegralArr[index].rightInput"
            >
            </el-input>
            <span class="scoreInfo">{{ $t('memberCard.score') }}</span>
            <img
              style="cursor:pointer"
              :src="$imageHost +'/image/admin/sign_del.png' "
              @click="handleToDelIntegral(index)"
            >
          </div>
        </el-form-item>
      </div>
      <div class="sendScoreBottom">
        <el-form-item>
          <div class="scoreReceiveSubItem">
            <el-radio
              v-model="ruleForm.offSet"
              label='1'
            >
              {{ $t('memberCard.shopEachFull') }}
            </el-radio>
            <el-input
              size="small"
              type="number"
              v-model="ruleForm.shopingInputLeftM"
            >
            </el-input>
            <span class="sendInfo">
              {{ $t('memberCard.send') }}
            </span>
            <el-input
              size="small"
              type="number"
              v-model="ruleForm.shopingInputRightM"
            >
            </el-input>
            <span class="scoreInfo">{{ $t('memberCard.score') }}</span>
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
    'ruleForm.powerScore': {
      handler (newName, oldName) {
        this.val.powerScore = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.score': {
      handler (newName, oldName) {
        this.val.score = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.offSet': {
      handler (newName, oldName) {
        this.val.offSet = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.shopingInputLeft': {
      handler (newName, oldName) {
        this.val.shopingInputLeft = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.shopingInputRight': {
      handler (newName, oldName) {
        this.val.shopingInputRight = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.shopingInputLeftM': {
      handler (newName, oldName) {
        this.val.shopingInputLeftM = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.shopingInputRightM': {
      handler (newName, oldName) {
        this.val.shopingInputRightM = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.addIntegralArr': {
      handler (newName, oldName) {
        this.val.addIntegralArr = newName
        this.ruleForm = this.val
      },
      deep: true
    }
  },
  data () {
    let validateScore = (rule, value, callback) => {
      if (this.ruleForm.powerScore) {
        if (this.checkScoreError(value)) {
          callback(new Error('请输入赠送积分'))
        }
      }
      callback()
    }
    return {
      rules: {
        score: [
          { validator: validateScore, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    checkScoreError (val) {
      if (val === 0) {
        return false
      }
      return this.isBlank(val)
    },
    isBlank (val) {
      return (!val || /^\s*$/.test(val))
    },
    handleToAddIntegral () {
      console.log('添加积分')
      this.ruleForm.addIntegralArr.push({
        leftInput: 0,
        rightInput: 0
      })
    },
    handleToDelIntegral (index) {
      this.ruleForm.addIntegralArr.splice(index, 1)
    }
  }
}
</script>
<style scoped lang="scss">
.scoreReceiveDiv {
  /deep/ .el-checkbox__label {
    color: black;
  }
  .sendScoreTop {
    /deep/ .el-form-item__error {
      padding-left: 200px;
    }

    .scoreReceiveItem {
      padding-left: 100px;
      display: flex;
      align-items: center;
      /deep/ .el-checkbox {
        margin-right: 20px;
      }
      .sendInfo {
        margin-right: 20px;
      }
      .scoreInfo {
        margin-left: 20px;
      }
      /deep/ .el-input {
        width: 100%;
        .el-input__inner {
          width: 100%;
        }
      }
    }
  }
  .sendScoreMiddle {
    /deep/ .el-form-item {
      margin-bottom: 2px;
    }
    .scoreReceiveSubItem {
      padding-left: 170px;
      margin-right: 32px;
      display: flex;
      align-items: center;
      /deep/ .el-radio {
        margin-right: 32px;
      }
      /deep/ .el-input {
        width: 20%;
        .el-input__inner {
          text-align: center;
          width: 100%;
        }
      }
      .scoreInfo {
        margin: 0 5px 0 20px;
      }
      .sendInfo {
        margin: 0 10px;
      }
    }
    .scoreReceiveAddSubItem {
      padding-left: 198px;
      display: flex;
      align-items: center;
      .shopFullInfo {
        margin-right: 31px;
      }
      /deep/ .el-input {
        width: 20%;
        .el-input__inner {
          text-align: center;
          width: 100%;
        }
      }
      .sendInfo {
        margin: 0 10px;
      }
      .scoreInfo {
        margin: 0 5px 0 20px;
      }
    }
  }
  .sendScoreBottom {
    .scoreReceiveSubItem {
      padding-left: 170px;
      display: flex;
      align-items: center;
      /deep/ .el-radio {
        margin-right: 16px;
      }
      /deep/ .el-input {
        width: 19.3%;
        .el-input__inner {
          text-align: center;
          width: 100%;
        }
      }
      .scoreInfo {
        margin-left: 20px;
      }
      .sendInfo {
        margin: 0 10px;
      }
    }
  }
}
</style>
