<template>
  <!-- 全部商品组件 -->
  <div class="allGoods">
    <el-button @click="lookEdit">商品编辑测试</el-button>
    <!-- 外部容器卡片 -->
    <el-card class="container">
      <!-- 头部标签页 -->
      <allGoodsHeader />
      <!-- 出售中|已售罄|仓库中路由出口 -->
      <router-view></router-view>
    </el-card>

  </div>
</template>
<script>
import allGoodsHeader from './allGoodsHeader'

export default {
  name: 'saleOn',
  // 组件注册
  components: { allGoodsHeader },
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
    lookEdit () {
      this.$router.push({
        name: 'goods_update',
        params: {goodsId: 46}
      })
    }
  }
}
</script>

<style lang="css" scoped>
/* 样式文件 要加scoped作用于当前的组件，方式样式污染 */
.allGoods {
  padding: 10px;
}
</style>
