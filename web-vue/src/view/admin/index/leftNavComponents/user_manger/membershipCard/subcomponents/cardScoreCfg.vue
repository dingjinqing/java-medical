<template>
  <div class="scoreReceiveDiv">
    <el-form
      :model="ruleForm"
      status-icon
      :rules="rules"
      ref="ruleFormScore"
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
      <el-form
        :model="ruleForm"
        status-icon
        :rules="rules"
        :inline-message="true"
        ref="ruleForm"
        label-width="100px"
      >
      <div class="sendScoreMiddle">
          <el-form-item prop="scoreSendFullFix" class="scoreReceiveSubItem" >
            <!-- <div class="scoreReceiveSubItem"> -->
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
            <!-- </div> -->
          </el-form-item>
          <div v-if="ruleForm.offSet==='0'">
            <el-form-item  v-for="(item,index) in ruleForm.addIntegralArr"
              :key="index"
              :prop="'addIntegralArr.'+index+'.leftInput'"
              :rules="rules.scoreSendEach"
              class="scoreReceiveAddSubItem">

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
            </el-form-item>

          </div>
          <el-form-item prop="scoreSendEachFix" class="scoreReceiveSubItemBottom" >

              <el-radio
                v-model="ruleForm.offSet"
                label='1'
              >
                {{ $t('memberCard.shopEachFull') }}
              </el-radio>
              <el-input
                v-model='ruleForm.shopingInputLeftM'
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
                v-model="ruleForm.shopingInputRightM"
              >
              </el-input>
              <span class="scoreInfo">{{ $t('memberCard.score') }}</span>
          </el-form-item>
      </div>
      </el-form>
      <div class="sendScoreBottom"></div>
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
        if (oldName === '0') {
          this.$refs.ruleForm.validateField('scoreSendFullFix')
        } else if (oldName === '1') {
          this.$refs.ruleForm.validateField('scoreSendEachFix')
        }
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
  mounted () {
    this.$on('checkRule', () => {
      this.$refs.ruleFormScore.validate((valid) => {
        if (!valid) {
          this.ruleForm.valid = false
          this.$message.warning('请输入赠送积分')
        } else {
          this.$refs.ruleForm.validate((valid) => {
            if (!valid) {
              this.ruleForm.valid = false
              this.$message.warning('请输入积分')
            } else {
              this.ruleForm.valid = true
            }
          })
        }
      })
    })
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
    let validateScoreSendFullFix = (rule, value, callback) => {
      if (this.ruleForm.offSet === '0') {
        if (this.checkScoreSendFull()) {
          callback(new Error('请输入积分'))
          this.sendFullErrorFix = true
        } else {
          this.sendFullErrorFix = false
          callback()
        }
      } else {
        callback()
      }
    }

    let validateScoreSendEachFix = (rule, value, callback) => {
      if (this.ruleForm.offSet === '1') {
        if (this.checkScoreSendEach()) {
          callback(new Error('请输入积分'))
        } else {
          callback()
        }
      } else {
        callback()
      }
    }

    let validateScoreSendEach = (rule, value, callback) => {
      // 校验动态数组
      let index = Number(rule.fullField.split('.')[1])
      if (value && this.ruleForm.addIntegralArr[index].rightInput) {
        callback()
      } else {
        callback(new Error('请输入积分'))
      }
    }

    return {
      sendFullErrorFix: false,
      sendEachError: [],
      rules: {
        score: [
          { validator: validateScore, trigger: 'blur' }
        ],
        scoreSendFullFix: [
          { validator: validateScoreSendFullFix, trigger: 'blur' }
        ],
        scoreSendEach: [
          { validator: validateScoreSendEach, trigger: 'blur' }
        ],
        scoreSendEachFix: [
          { validator: validateScoreSendEachFix, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    checkScoreError (val) {
      if (val === 0) {
        return false
      }
      console.log(val, this.isBlank(val))
      return this.isBlank(val)
    },
    isBlank (val) {
      return (!val || /^\s*$/.test(val))
    },
    checkScoreSendFull () {
      if (this.ruleForm.shopingInputLeft && this.ruleForm.shopingInputRight) {
        return false
      }
      return true
    },
    checkScoreSendEach () {
      if (this.ruleForm.shopingInputLeftM && this.ruleForm.shopingInputRightM) {
        return false
      }
      return true
    },
    handleToAddIntegral () {
      console.log('添加积分')
      this.ruleForm.addIntegralArr.push({
        leftInput: undefined,
        rightInput: undefined
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
    /deep/ .el-form-item{
      margin-bottom: 20px;
    }
    /deep/ .el-form-item__error{
      margin-left: 100px;
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
      /deep/ .el-input {
        width: 20%;
        .el-input__inner {
          text-align: center;
          width: 100%;
        }
      }
      padding-left: 170px;
      margin-right: 32px;
      display: flex;
      align-items: center;
      .send-full-error{
        margin-left: -83px;
        margin-right: 15px;
        color: #F56C6C;
        font-size: 12px;
      }
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

    .scoreReceiveSubItemBottom {
        padding-left: 170px;
        display: flex;
        align-items: center;
        /deep/ .el-radio {
          margin-right: 18px;
        }
        /deep/ .el-input {
          width: 21%;
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
  .sendScoreBottom {
    margin-bottom: 20px;
    .scoreReceiveSubItem {
      padding-left: 170px;
      display: flex;
      align-items: center;
      /deep/ .el-radio {
        margin-right: 23px;
      }
      /deep/ .el-input {
        width: 14%;
        .el-input__inner {
          text-align: center;
          width: 100%;
        }
      }
      .scoreInfo {
        margin-left: 20px;
        color: red;
      }
      .sendInfo {
        margin: 0 10px;
      }
    }
  }
}
</style>
