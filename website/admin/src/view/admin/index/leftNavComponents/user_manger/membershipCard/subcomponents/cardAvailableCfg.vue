<template>
  <div class="card-available-div">
    <el-form
      status-icon
      ref="ruleForm"
      label-width="100px"
    >
      <el-form-item
        :label="$t('memberCard.cardStartUse')"
        class="available-item"
      >
        <el-radio
          v-model="data.flag"
          label="1"
        >
          {{$t('memberCard.startUse')}}
        </el-radio>
        <el-radio
          v-model="data.flag"
          label="2"
        >
          {{$t('memberCard.stopUse')}}
        </el-radio>
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
    data: {
      get () {
        return this.val
      },
      set () {
        this.$emit('input', this.data)
      }
    }
  },
  mounted () {
    this.langDefault()
  },
  watch: {
    'data.flag': {
      handler (newName, oldName) {
        this.val.flag = newName
        this.data = this.val
      },
      immediate: true
    }
  }
}
</script>
<style scoped lang="scss">
*,/deep/ .el-form-item__label,
/deep/ .el-radio__label,
/deep/ .el-checkbox__label{
  font-size: 13px;
}
.card-available-div {
  .available-item {
     padding-left: 75px;
  }
}
</style>
