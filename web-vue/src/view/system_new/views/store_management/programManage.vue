<template>
  <el-tabs v-model="tabActive" type="border-card" class="tab" >
    <el-tab-pane label="小程序模板版本管理" name="first">
      <versionManage />
    </el-tab-pane>
    <el-tab-pane label="小程序版本操作日志" name="second">
      <versionLog />
    </el-tab-pane>
    <el-tab-pane label="小程序授权列表" name="third">
      <authList />
    </el-tab-pane>
    <el-tab-pane label="小程序版本统计" name="fourth">定时任务补偿</el-tab-pane>
  </el-tabs>
</template>

<script>
import versionManage from './versionManage'
import versionLog from './versionLog'
import authList from './authList'

export default {
  name: 'programManage',
  components: {
    versionManage,
    versionLog,
    authList
  },
  data () {
    return {
      tabActive: 'first'
    }
  }
}
</script>

<style scoped lang="scss">
.tab {
  margin-top: 18px;
  /deep/ .el-tabs__header .el-tabs__item.is-active {
    border-top: 3px solid #57889c;
    color: #333;
    font-size: 14px;
    font-weight: 700;
  }
  /deep/ .el-tabs__item{
    height: 46px;
    padding: 0 10px 3px 10px!important;
    border-top: 2px solid transparent;
    font-weight: 400;
  }
  /deep/ .el-tabs__content {
    padding: 10px 0 0 0;
    background: #e0e3ec;
  }
}
</style>
