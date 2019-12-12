<template>
  <div class="effective-time">
    <el-form
      :model="ruleForm"
      status-icon
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
    >
      <el-form-item
        :label="$t('memberCard.memberEffectiveTime')"
        prop="fixedDate"
        class="date-item"
      >
        <div class='fixdate'>
          <el-radio
            v-model="ruleForm.expiredType"
            label="0"
          >
            {{ $t('memberCard.fixData') }}
          </el-radio>
          <el-date-picker
            v-model="ruleForm.fixedDate"
            type="daterange"
            :range-separator="$t('memberCard.to')"
            :start-placeholder="$t('memberCard.startDate')"
            :end-placeholder="$t('memberCard.overDate')"
            value-format="yyyy-MM-dd HH:mm:ss"
            :default-time="['00:00:00','23:59:59']"
            size="small"
          >
          </el-date-picker>
        </div>
      </el-form-item>

      <div class="date-tip">
        {{ $t('memberCard.exampleInfo') }}
      </div>

      <el-form-item
        prop="receiveDay"
        class="date-item"
      >
        <div class="receive-day">
          <el-radio
            v-model="ruleForm.expiredType"
            label="1"
          >
            {{ $t('memberCard.fromDate') }}
          </el-radio>
          <el-input-number
            size="small"
            :controls="false"
            :max="999999999"
            v-model="ruleForm.receiveDay"
          >
          </el-input-number>
          <el-select
            v-model="ruleForm.dateType"
            :placeholder="$t('memberCard.pleaseChoose')"
            size="small"
          >
            <el-option
              v-for="item in dateSelectOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
          <span>
            {{ $t('memberCard.inEffective') }}
          </span>
        </div>
      </el-form-item>
      <el-form-item class="date-item">
        <div class="forever">
          <el-radio
            v-model="ruleForm.expiredType"
            label="2"
          >
            {{ $t('memberCard.ForeverEffective') }}
          </el-radio>
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
    'ruleForm.expiredType': {
      handler (newName, oldName) {
        this.val.expiredType = newName
        this.ruleForm = this.val
        if (oldName) {
          this.validateExpiredType()
        }
      },
      immediate: true
    },
    'ruleForm.fixedDate': {
      handler (newName, oldName) {
        console.log(newName, oldName)
        if (oldName !== undefined) {
          this.val.fixedDate = newName
          this.ruleForm = this.val
        }
      },
      immediate: true
    },
    'ruleForm.receiveDay': {
      handler (newName, oldName) {
        this.val.receiveDay = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.dateType': {
      handler (newName, oldName) {
        this.val.dateType = newName
        this.ruleForm = this.val
      },
      immediate: true
    }
  },
  data () {
    let validateFixDate = (rule, value, callback) => {
      if (this.ruleForm.expiredType === '0') {
        console.log(this.ruleForm.fixedDate)
        if (this.ruleForm.fixedDate) {
          callback()
        } else {
          callback(new Error('请选择日期'))
        }
      }
      callback()
    }

    let validateReceiveDay = (rule, value, callback) => {
      if (this.ruleForm.expiredType === '1') {
        if (this.checkReceiveError(value)) {
          callback(new Error('请输入有效期'))
        } else {
          callback()
        }
      }
      callback()
    }
    return {
      dateSelectOptions: null,
      rules: {
        fixedDate: [{ validator: validateFixDate, trigger: 'blur' }],
        receiveDay: [{ validator: validateReceiveDay, trigger: 'blur' }]
      }
    }
  },
  mounted () {
    this.dateSelectOptions = this.$t('memberCard.dateSelectOptions')
  },
  methods: {
    checkReceiveError (val) {
      if (val === 0) {
        return false
      }
      return this.isBlank(val)
    },
    isBlank (val) {
      return (!val || /^\s*$/.test(val))
    },
    validateExpiredType () {
      if (this.ruleForm.expiredType === '0') {
        this.$refs.ruleForm.validateField('receiveDay')
      } else if (this.ruleForm.expiredType === '1') {
        this.$refs.ruleForm.validateField('fixedDate')
      } else {
        this.$refs.ruleForm.validate(valid => {
          console.log(valid)
        })
      }
    }
  }
}
</script>
<style scoped lang="scss">
.effective-time {
  .date-item {
    padding-left: 100px;
    /deep/ .el-form-item__label:before {
      content: "*";
      color: #f56c6c;
      margin-right: 4px;
    }
    /deep/ .el-select {
      width: 150px;
      margin-right: 2px;
    }
  }
  .date-tip {
    color: #9d9d9d;
    padding-left: 224px;
    margin-bottom: 20px;
    font-size: 13px;
  }
}
</style>
