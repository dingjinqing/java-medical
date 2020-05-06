<template>
  <div class="CRM-authorization-config-page">
    <div class="content">
      <el-form
        :model="formData"
        label-width="30%"
        label-position="left"
        label-suffix="："
        size="small"
      >
        <el-form-item label="服务名称">
          <p class="label-con">CRM服务</p>
        </el-form-item>
        <el-form-item label="SessionKey">
          <span class="label-con">w20190604140528M49sbDZFCs4748160</span>
          <span>(<el-button type="text">重置</el-button>)</span>
        </el-form-item>
        <el-form-item label="AppKey">
          <el-input class="form-input"></el-input>
        </el-form-item>
        <el-form-item label="AppSecret">
          <el-input class="form-input"></el-input>
          <el-button type="primary">提交</el-button>
        </el-form-item>
        <el-form-item label="是否已授权">
          <p class="label-con">未授权</p>
        </el-form-item>
        <el-form-item label="操作">
          <template>
            <el-button type="text">授权</el-button>
          </template>
          <template>
            <el-button type="text">删除授权</el-button>
            <el-button type="text">同步CRM门店</el-button>
            <span class="label-con">注：仅同步未对接门店</span>
            <el-button type="text">同步CRM商品到系统商品</el-button>
          </template>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      formData: {}
    }
  }
}
</script>

<style lang="scss" scoped>
.CRM-authorization-config-page {
  .el-form {
    width: 840px;
    padding: 0px 30px;
    margin-bottom: 20px;
    border: 1px solid #ededed;
  }
  .el-form-item {
    line-height: 50px;
    border-bottom: 1px solid #eee;
  }
  /deep/ .el-form-item__label {
    line-height: 50px;
  }
  /deep/ .el-form-item__content {
    line-height: 50px;
  }
  .label-con {
    color: #999999;
    font-size: 12px;
  }
  .form-input {
    width: 215px;
  }
}
</style>
