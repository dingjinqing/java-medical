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
}
</style>
