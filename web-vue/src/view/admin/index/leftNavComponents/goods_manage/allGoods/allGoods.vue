<template>
  <!-- 全部商品组件 -->
  <div class="contentWrap">
      <div class="content">
        <allGoodsHeader/>
        <!-- 出售中|已售罄|仓库中路由出口 -->
        <router-view></router-view>
      </div>
  </div>
</template>
<script>
import allGoodsHeader from './allGoodsHeader'

export default {
  name: 'allGoods',
  components: { allGoodsHeader },

  // 数据data
  data () {
    return {
      tabItemActiveIndex: 0
    }
  },
  // methods 方法
  methods: {

  },
  // Vue生命周期钩子函数
  mounted () {

  }
}
</script>

<style scoped>
  .contentWrap {
    padding: 10px 10px;
  }
  .content {
    background-color: white;
    padding: 10px 10px 10px 10px;
  }
</style>
