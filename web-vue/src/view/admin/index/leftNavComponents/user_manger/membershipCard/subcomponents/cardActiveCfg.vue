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
          {{ $t('memberCard.chooseUserInfo') }}
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
          activation: '1',
          activationCfgBox: [],
          examine: '0'
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
