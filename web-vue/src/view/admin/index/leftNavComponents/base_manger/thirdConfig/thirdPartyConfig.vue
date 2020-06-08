<template>
  <div class="container">
    <div class="content">
      <el-tabs v-model="activeTab">
        <el-tab-pane
          :label="$t('thirdPartyConfig.erpAc')"
          name="first"
        >
          <erpAuthorizationConfig />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('thirdPartyConfig.posAc')"
          name="second"
        >
          <posAuthorizationConfig />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('thirdPartyConfig.crmAc')"
          name="third"
        >
          <crmAuthorizationConfig />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
export default {
  components: {
    erpAuthorizationConfig: () => import('./erpAuthorizationConfig'),
    crmAuthorizationConfig: () => import('./crmAuthorizationConfig'),
    posAuthorizationConfig: () => import('./posAuthorizationConfig')
  },
  data () {
    return {
      activeTab: 'first'
    }
  }
}
</script>

<style>
.confirmCls {
  width: 260px;
}
</style>
<style lang="scss" scoped>
.container {
  padding: 10px;
  min-width: 100%;
  height: 100%;
  font-size: 14px;
  .content {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 0 20px;
  }
}
</style>
