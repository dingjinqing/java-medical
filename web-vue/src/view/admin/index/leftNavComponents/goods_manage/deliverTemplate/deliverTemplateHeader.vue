<template>
  <!-- 运费模板头部内容 -->
  <div class="deliverTemplateHeader">
    <el-tabs
      v-model="activeName"
      @tab-click="handleClick"
    >
      <!-- 运费模板列表 -->
      <el-tab-pane
        label="运费模板列表"
        name="first"
      >
      </el-tab-pane>
      <!-- 重量运费模板列表 -->
      <el-tab-pane
        label="重量运费模板列表"
        name="second"
      >
      </el-tab-pane>
      <!-- 添加运费模板 -->
      <el-tab-pane
        label="添加运费模板"
        name="third"
      >
      </el-tab-pane>
      <!-- 添加重量运费模板-->
      <el-tab-pane
        label="添加重量运费模板"
        name="fourth"
      >
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
export default {
  name: 'deliverTemplateHeader',
  data () {
    return {
      activeName: 'first'
    }
  },
  methods: {
    // 点击头部跳转路由
    handleClick (tab, event) {
      switch (tab.name) {
        case 'first': this.$router.push({ name: `deliverTemplateList` }); break
        case 'second': this.$router.push({ name: `deliverTemplateWeightList` }); break
        case 'third': this.$router.push({ name: `deliverTemplateAdd` }); break
        case 'fourth': this.$router.push({ name: `deliverTemplateWeightAdd` }); break
        default: break
      }
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
