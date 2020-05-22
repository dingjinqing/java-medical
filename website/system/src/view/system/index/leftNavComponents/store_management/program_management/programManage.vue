<template>
  <el-tabs
    v-model="tabActive"
    type="border-card"
    class="tab"
  >
    <el-tab-pane
      :label="$t('programVersion.versionManage')"
      name="versionManage"
    >
      <versionManage />
    </el-tab-pane>
    <el-tab-pane
      :label="$t('programVersion.versionLog')"
      name="versionLog"
    >
      <versionLog />
    </el-tab-pane>
    <el-tab-pane
      :label="$t('programVersion.authList')"
      name="authList"
    >
      <authList />
    </el-tab-pane>
    <el-tab-pane
      :label="$t('programVersion.versionStatistics')"
      name="versionStatistics"
    >
      <versionStatistics />
    </el-tab-pane>
    <el-tab-pane
      v-if="$route.params.page === 'authMsg'"
      :label="$t('programVersion.authMsg')"
      name="authMsg"
    >
      <authMsg />
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import versionManage from './versionManage' // 首页组件直接加载，剩下的页面都异步加载

export default {
  name: 'programManage',
  components: {
    versionManage: versionManage,
    versionLog: () => import('./versionLog'),
    authList: () => import('./authList'),
    versionStatistics: () => import('./versionStatistics'),
    authMsg: () => import('./authMsg')
  },
  data () {
    return {
      tabActive: this.$route.params.page
    }
  },
  watch: {
    $route: { // 带选项watch写法。兼容国际化刷新立即显示
      immediate: true,
      handler (to) {
        this.tabActive = to.params.page
        this.$store.commit('UPDATE_BREADCRUMB_TITLE', this.$t(`programVersion.${this.$route.params.page}`))
      }
    },
    tabActive (val) {
      this.$router.push({
        name: this.$route.name,
        params: {
          page: val
        }
      })
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
  /deep/ .el-tabs__item {
    height: 46px;
    padding: 0 10px 3px 10px !important;
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
