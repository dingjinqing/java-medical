<template>
  <div class="integralManagement">
    <div class="integralManagementMain">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          label="积分规则设置"
          name="first"
        >
          <IntegralRule />
        </el-tab-pane>
        <el-tab-pane
          label="前端展示设置"
          name="second"
        >前端展示设置</el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
export default {
  components: {
    IntegralRule: () => import('./integralRule')
  },
  data () {
    return {
      activeName: 'first'
    }
  },
  methods: {
    handleClick (tab, event) {
      console.log(tab, event)
    }
  }
}
</script>
<style lang="scss" scoped>
.integralManagement {
  padding: 10px;
  padding-bottom: 68px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .integralManagementMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px;
  }
}
</style>
