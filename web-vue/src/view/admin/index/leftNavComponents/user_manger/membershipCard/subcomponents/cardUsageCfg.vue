<template>
  <div class="card-usage-div">
    <el-form
      :model="ruleForm"
      ref="ruleForm"
      label-width="100px"
    >
      <el-form-item
        :label="$t('memberCard.useNeedKnow')"
        class="usage-item"
      >
        <el-input
          type="textarea"
          :rows="2"
          v-model="ruleForm.desc"
        >
        </el-input>
      </el-form-item>
      <el-form-item
        :label="$t('memberCard.contactPhone')"
        class="usage-item phone-num"
      >
        <el-input
          size="small"
          v-model="ruleForm.mobile"
          onkeyup="value=value.replace(/[^\d\-]/g,'')"
          :placeholder="$t('memberCard.pleaseInput')"
        >
        </el-input>
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
    'ruleForm': {
      handler (newName, oldName) {
        this.val = newName
        this.ruleForm = this.val
      },
      deep: true
    }
  }
}
</script>
<style scoped lang="scss">
.card-usage-div {
  .usage-item {
    padding-left: 100px;
    /deep/ .el-textarea {
      width: 80%;
    }
  }
  .phone-num {
    /deep/ .el-input {
      width: 30%;
    }
  }
}
</style>
