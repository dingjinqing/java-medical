<template>
  <div class="card-grade-div">
    <el-form
      :model="ruleForm"
      status-icon
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
      :hide-required-asterisk="false"
    >
      <el-form-item
        :label="$t('memberCard.gradeSetting')"
        class="grade-item"
        :rules="[{required: true}]"
      >
        <span class="grade-condition-tip">
          {{$t('memberCard.gradeTip')}}
        </span>
        <div class="grade-condition">
          <el-form-item class="grade-score">
            <span>{{$t('memberCard.gradeScore')}}</span>
            <el-input-number
              v-model="ruleForm.gradeScore"
              size="small"
              :min="0"
              :max="999999999"
              :controls="false"
            >
            </el-input-number>
            <span>{{$t('memberCard.unitM')}}</span>
          </el-form-item>
          <div>{{$t('memberCard.or')}}</div>
          <el-form-item class="grade-amount">
            <span>{{$t('memberCard.gradeAmountCon')}}</span>
            <el-input-number
              v-model="ruleForm.gradeCrash"
              size="small"
              :min="0"
              :max="999999999"
              :controls="false"
            >
            </el-input-number>
            <span>{{$t('memberCard.yuan')}}</span>
            <span class="amount-tip">
              {{$t('memberCard.amountTip')}}
            </span>
          </el-form-item>
        </div>

      </el-form-item>
      <el-form-item
        :label="$t('memberCard.cardGrade')"
        class="grade-item"
        :rules="[{required: true}]"
      >
        <div class="grade-choose">
          <el-select
            v-model="ruleForm.gradeValue"
            :placeholder="$t('memberCard.cardGrade')"
            size="small"
          >
            <el-option
              v-for="(item,index) in gradeOptions"
              :key="index"
              :label="item.label"
              :value="item.value"
              :disabled="item.disabled"
            >
            </el-option>
          </el-select>
          <span v-if="valid" style="color: red;">请选择会员卡等级</span>
          <div class="grade-condition-tip">
            {{$t('memberCard.gradeConditionTip')}}
          </div>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import {getAllNoDeleteGradeCard} from '@/api/admin/memberManage/memberCard.js'
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
    'ruleForm.gradeScore': {
      handler (newName, oldName) {
        this.val.gradeScore = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.gradeCrash': {
      handler (newName, oldName) {
        this.val.gradeCrash = newName
        this.ruleForm = this.val
      },
      immediate: true
    },
    'ruleForm.gradeValue': {
      handler (newName, oldName) {
        console.log(this.recordGrade)
        // 存储等级
        if (this.recordGrade && oldName) {
          this.recordGrade = false
          this.gradeOptions.forEach(item => {
            if (item.value === oldName) {
              console.log(oldName, item.value)
              item.disabled = false
            }
          })
          console.log(this.gradeOptions)
        }
        this.val.gradeValue = newName
        this.ruleForm = this.val
        this.valid = false
      },
      immediate: true
    }
  },
  mounted () {
    this.initGradeOptions()
    this.$on('checkRule', () => {
      if (this.ruleForm.gradeValue) {
        this.valid = false
        this.ruleForm.valid = true
      } else {
        this.valid = true
        this.$message.warning('请输入等级')
      }
    })
  },
  data () {
    return {
      recordGrade: true,
      valid: false,
      gradeOptions: [
        { label: '请选择', value: null, disabled: false },
        { label: 'v1', value: 'v1', disabled: false },
        { label: 'v2', value: 'v2', disabled: false },
        { label: 'v3', value: 'v3', disabled: false },
        { label: 'v4', value: 'v4', disabled: false },
        { label: 'v5', value: 'v5', disabled: false },
        { label: 'v6', value: 'v6', disabled: false },
        { label: 'v7', value: 'v7', disabled: false },
        { label: 'v8', value: 'v8', disabled: false },
        { label: 'v9', value: 'v9', disabled: false }
      ],
      rules: {

      }
    }
  },
  methods: {
    initGradeOptions () {
      getAllNoDeleteGradeCard().then(res => {
        console.log(this.ruleForm.gradeValue)
        if (res.error === 0) {
          console.log(res.content)
          this.gradeOptions.forEach(item => {
            if (res.content.includes(item.value)) {
              item.disabled = true
            }
          })
        }
      })
    }
  }
}
</script>
<style scoped lang="scss">
.card-grade-div {
  .grade-item {
    padding-left: 100px;
    .grade-condition-tip {
      color: #999;
      font-size: 13px;
      line-height: 20px;
    }
    .grade-condition {
      .grade-score {
        span {
          margin-right: 10px;
        }
        /deep/ .el-input {
          width: 90%;
        }
      }
      .grade-amount {
        span {
          margin-right: 10px;
        }
        /deep/ .el-input {
          width: 90%;
        }
        .amount-tip {
          color: #999;
          font-size: 13px;
        }
      }
    }
    .grade-choose {
      /deep/ .el-select {
        width: 18%;
      }
    }
  }
}
</style>
