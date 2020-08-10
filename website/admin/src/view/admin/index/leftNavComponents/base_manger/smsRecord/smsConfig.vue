<template>
  <div class="container">
    <div class="content">
      <el-tabs v-model="activeTab">
        <el-tab-pane
          label="短信记录"
          name="first"
        >
          <smsRecord />
        </el-tab-pane>
        <el-tab-pane
          label="充值记录"
          name="second"
        >
          <inpayRecord />
        </el-tab-pane>
        <el-tab-pane
          label="充值记录"
          name="third"
        >
          <smsSetting />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
export default {
  components: {
    smsRecord: () => import('./smsRecord'),
    inpayRecord: () => import('./inpayRecord'),
    smsSetting: () => import('./smsSetting')
  },
  data () {
    return {
      activeTab: 'first'
    }
  }
}
</script>
<style lang="scss">
.base_manger {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;

  .base_mangerMain {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 0 20px;
  }
}
.content .el-tabs__header {
  margin: 10px;
  padding: 10px;
  margin-bottom: 0;
  background: #fff;
}
</style>
