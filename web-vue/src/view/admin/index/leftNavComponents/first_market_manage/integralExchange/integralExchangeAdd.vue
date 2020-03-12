<template>
  <div class="integralExchangeAdd">
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
      class="demo-ruleForm"
    >
      <el-form-item
        label="活动名称"
        prop="name"
      >
        <el-input
          size="small"
          v-model="ruleForm.name"
        ></el-input>
        <span class="tips">只作为商家记录使用，用户不会看到这个名称</span>
      </el-form-item>

    </el-form>
  </div>
</template>
<script>
export default {
  data () {
    return {
      ruleForm: {
        name: ''

      },
      rules: {
        name: [
          { required: true, message: '请输入活动名称', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {

  }
}
</script>
<style lang="scss" scoped>
.integralExchangeAdd {
  padding: 8px 23px;
  background: #ffffff;
  min-height: 500px !important;
  padding-top: 20px;
  /deep/ .el-input {
    width: auto;
  }
  .tips {
    font-size: 12px;
    color: #999;
  }
}
</style>
