<template>
  <div class="card-grade-div">
    <el-form
      :model="ruleForm"
      status-icon
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
          <div class="grade-score">
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
          </div>
          <div>{{$t('memberCard.or')}}</div>
          <div class="grade-amount">
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
          </div>
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
          <div class="grade-condition-tip">
            {{$t('memberCard.gradeConditionTip')}}
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
          gradeScore: null,
          gradeCrash: null,
          gradeValue: null
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
  watch: {
    'ruleForm': {
      handler (newName, oldName) {
        this.val = newName
        this.ruleForm = this.val
      },
      deep: true
    }
  },
  data () {
    return {
      gradeOptions: [
        { label: 'v1', value: 'v1', disabled: false },
        { label: 'v2', value: 'v2', disabled: false },
        { label: 'v3', value: 'v3', disabled: false },
        { label: 'v4', value: 'v4', disabled: false },
        { label: 'v5', value: 'v5', disabled: false },
        { label: 'v6', value: 'v6', disabled: false },
        { label: 'v7', value: 'v7', disabled: false },
        { label: 'v8', value: 'v8', disabled: false },
        { label: 'v9', value: 'v9', disabled: false }
      ]
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
