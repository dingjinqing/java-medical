<template>
  <div class="container">
    <el-card>
      <!-- Tabs 标签页 -->
      <el-tabs
        v-model="activeName"
        @tab-click="tabsHandleClick"
      >
        <el-tab-pane
          label="分类列表"
          name="sort"
        >
        </el-tab-pane>
        <el-tab-pane
          label="推荐列表"
          name="recommend"
        >
        </el-tab-pane>
      </el-tabs>
      <!-- 主要内容路由出口 -->
      <router-view></router-view>
    </el-card>
  </div>
</template>
<script>
export default {
  // 组件名
  name: '',
  // data 数据
  data () {
    return {
      activeName: 'sort'
    }
  },
  // 生命周期钩子
  create () {

  },
  mounted () {

  },
  // 方法
  methods: {
    // 点击头部的标签页
    tabsHandleClick () {

    }
  }
}
</script>
<style lang="scss" scoped>
.container {
  padding: 10px;
}
</style>
