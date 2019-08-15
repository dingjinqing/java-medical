<template>
  <div class="brandManagementContent">
    <div class="brandManagementContent_main">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          label="评价记录"
          name="first"
        >评价记录</el-tab-pane>
        <el-tab-pane
          label="评价审核"
          name="second"
        >评价审核</el-tab-pane>
        <el-tab-pane
          label="商品列表"
          name="third"
        >商品列表</el-tab-pane>

      </el-tabs>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      activeName: 'first'
    }
  },
  methods: {
    // tap切换
    handleClick (tab, event) {
      console.log(tab, event)
    }
  }
}
</script>
<style scoped>
.brandManagementContent {
  padding: 10px;
  padding-right: 23px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
}
.brandManagementContent_main {
  position: relative;
  background-color: #fff;
  /* height: 100%; */
  overflow: hidden;
  overflow-y: auto;
  padding-bottom: 96px;
  padding: 10px;
}
</style>
