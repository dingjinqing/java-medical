<template>
  <div class="list-con">
    <div class="top">
      <el-form
        size="small"
        :inline="true"
        class="store-permission-form"
      >
        <el-form-item label="门店账户：">
          <el-input class="form-input"></el-input>
        </el-form-item>
        <el-form-item label="手机号：">
          <el-input class="form-input"></el-input>
        </el-form-item>
        <el-form-item label="角色：">
        </el-form-item>
        <el-form-item label="账户状态："></el-form-item>
        <el-button
          type="primary"
          size="small"
        >筛选</el-button>
      </el-form>
    </div>
    <div class="center">

    </div>
  </div>
</template>

<style lang="scss" scoped>
.list-con {
}
.store-permission-form {
}
</style>
