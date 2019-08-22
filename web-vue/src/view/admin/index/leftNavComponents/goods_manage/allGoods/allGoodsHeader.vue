<template>
  <!-- 头部组件 -->
  <div class="allGoodsHeader">
    <el-tabs
      v-model="activeName"
      @tab-click="handleClick"
    >
      <el-tab-pane
        label="出售中"
        name="saleOn"
      ></el-tab-pane>
      <el-tab-pane
        label="已售罄"
        name="saleEnd"
      ></el-tab-pane>
      <el-tab-pane
        label="仓库中"
        name="saleOff"
      ></el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
export default {
  name: 'allGoodsHeader',
  // 数据data
  data () {
    return {
      activeName: 'saleOn'
    }
  },
  // Vue生命周期钩子函数
  mounted () {

  },
  // methods 方法
  methods: {
    handleClick (tab, event) {
      console.log(tab.name)
      // 点击进行路由跳转
      switch (tab.name) {
        case 'saleOn': this.$router.push({
          name: 'sale_on'
        })
          break
        case 'saleEnd': this.$router.push({
          name: 'sale_end'
        })
          break
        case 'saleOff': this.$router.push({
          name: 'sale_off'
        })
          break
      }
    }
  }
}
</script>

<style lang="css" scoped>
/* 样式文件 要加scoped作用于当前的组件，方式样式污染 */
</style>
