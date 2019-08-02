<template>
  <el-tabs v-model="tabActive" type="border-card" class="tab" >
    <el-tab-pane :label="$t('programVersion.versionManage')" name="first">
      <versionManage />
    </el-tab-pane>
    <el-tab-pane :label="$t('programVersion.versionLog')" name="second">
      <versionLog />
    </el-tab-pane>
    <el-tab-pane :label="$t('programVersion.authList')" name="third">
      <authList />
    </el-tab-pane>
    <el-tab-pane :label="$t('programVersion.versionStatistics')" name="fourth">
      <versionStatistics />
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import versionManage from './versionManage'
import versionLog from './versionLog'
import authList from './authList'
import versionStatistics from './versionStatistics'

export default {
  name: 'programManage',
  components: {
    versionManage,
    versionLog,
    authList,
    versionStatistics
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
    text-align: center;
  }
  /deep/ .el-tabs__content {
    padding: 10px 0 0 0;
    background: #e0e3ec;
  }
}
</style>
