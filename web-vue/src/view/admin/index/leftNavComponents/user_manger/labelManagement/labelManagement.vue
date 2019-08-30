<template>
  <div class="labelManagement">
    <div class="labelManagementMain">
      <div class="top">
        <el-button
          type="primary"
          size="small"
        >新建标签</el-button>
        <el-input
          v-model="labelInput"
          placeholder="请输入标签查询"
          size="small"
          suffix-icon="el-icon-search"
        ></el-input>
        <el-button
          type="primary"
          size="small"
        >查询</el-button>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      labelInput: ''
    }
  }
}
</script>
<style lang="scss" scoped>
.labelManagement {
  padding: 10px;
  padding-bottom: 68px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .labelManagementMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px;
    .top {
      /deep/ .el-button {
        width: 100px;
      }
      /deep/ .el-input {
        width: 150px;
        margin: 0 20px;
      }
      display: flex;
    }
  }
}
</style>
