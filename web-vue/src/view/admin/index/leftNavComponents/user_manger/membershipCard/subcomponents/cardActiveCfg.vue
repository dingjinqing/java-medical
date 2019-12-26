<template>
  <div class="card-active-div">
    <el-form
      :model="ruleForm"
      ref="ruleForm"
      label-width="100px"
    >
      <el-form-item
        class="active-item"
        :label="$t('memberCard.isActiveted')"
        :rules="[{required: true}]"
      >
        <div>
          <el-radio
            v-model="ruleForm.activation"
            label="0"
          >
            {{ $t('memberCard.no') }}
          </el-radio>
          <el-radio
            v-model="ruleForm.activation"
            label="1"
          >
            {{ $t('memberCard.yes') }}
          </el-radio>
        </div>
        <div class="active-tip">
          <span>{{ $t('memberCard.chooseUserInfo') }}</span>
          <span v-if="activeError" class="active-error">请选择激活配置信息</span>
        </div>
        <div
          v-if="ruleForm.activation==='1'"
          class="active-choose"
        >
          <div class="active-top">
            <el-checkbox-group v-model="ruleForm.activationCfgBox">
              <el-checkbox label="realName">{{ $t('memberCard.realName') }}</el-checkbox>
              <el-checkbox label="mobile">{{ $t('memberCard.mobile') }}</el-checkbox>
              <el-checkbox label="cid">{{ $t('memberCard.cid') }}</el-checkbox>
              <el-checkbox label="sex">{{ $t('memberCard.sex') }}</el-checkbox>
              <el-checkbox label="birthday">{{ $t('memberCard.birthday') }}</el-checkbox>
              <el-checkbox label="maritalStatus">{{ $t('memberCard.maritalStatus') }}</el-checkbox>
              <el-checkbox label="education">{{ $t('memberCard.educationInfo') }}</el-checkbox>
              <el-checkbox label="industryInfo">{{ $t('memberCard.industry') }}</el-checkbox>
              <el-checkbox label="address">{{ $t('memberCard.address') }}</el-checkbox>
            </el-checkbox-group>
          </div>
          <div class="active-bottom">
            <div class="exmine-tip">{{ $t('memberCard.isactiveInfo') }}</div>
            <el-radio
              v-model="ruleForm.examine"
              label="0"
            >{{ $t('memberCard.noExmine') }}</el-radio>
            <el-radio
              v-model="ruleForm.examine"
              label="1"
            >{{ $t('memberCard.yesExmine') }}</el-radio>
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
          activation: '0',
          activationCfgBox: [],
          examine: '0'
        }
      }
    }
  },
  data () {
    return {
      activeError: false
    }
  },
  computed: {
    ruleForm: {
      get () {
        return this.val
      },
      set () {
        if (this.ruleForm.activationCfgBox.length > 0 || this.ruleForm.activation === '0') {
          this.activeError = false
        }
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
  mounted () {
    this.$on('checkRule', () => {
      if (this.ruleForm.activation === '1') {
        if (this.ruleForm.activationCfgBox.length > 0) {
          this.ruleForm.valid = true
          this.activeError = false
        } else {
          this.ruleForm.valid = false
          this.activeError = true
          this.$message.warning('请选择激活配置信息')
        }
      } else {
        this.ruleForm.valid = true
        this.activeError = false
      }
    })
  }
}
</script>
<style scoped lang="scss">
.card-active-div {
  .active-item {
    padding-left: 80px;
    /deep/ .el-form-item__label {
      width: 122px !important;
    }
    .active-tip {
      color: #9d9d9d;
      font-size: 13px;
      padding-left: 50px;
      .active-error{
        color: #F56C6C;
        font-size: 12px;
        padding-left: 10px;
      }
    }
    .active-choose {
      padding-left: 50px;
      .active-bottom {
        .exmine-tip {
          color: #9d9d9d;
          font-size: 13px;
        }
      }
    }
  }
}
</style>
