<template>
  <!-- 全部商品组件 -->
  <div class="allGoods">
    <!-- 外部容器卡片 -->
    <el-card class="container">
      <!-- 头部标签页 -->
      <allGoodsHeader />
      <!-- 头部操作 -->
      <headOperation />
      <!-- 出售中|已售罄|仓库中路由出口 -->
      <router-view></router-view>
      <allGoodsFooter />
    </el-card>

  </div>
</template>
<script>
import allGoodsHeader from './allGoodsHeader'
import headOperation from './headOperation'
import allGoodsFooter from './allGoodsFooter'
export default {
  name: 'saleOn',
  // 组件注册
  components: { allGoodsHeader, headOperation, allGoodsFooter },
  // 数据data
  data () {
    return {

    }
  },
  // Vue生命周期钩子函数
  mounted () {

  },
  // methods 方法
  methods: {

  }
}
</script>

<style lang="css" scoped>
/* 样式文件 要加scoped作用于当前的组件，方式样式污染 */
.allGoods {
  padding: 10px;
}
</style>
