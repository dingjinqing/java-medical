<template>
  <!-- 头部组件 -->
  <div class="allGoodsHeader">
    <div class="headerTab">
      <div class="tabItem" :class="{tabItemActive:tabItemActiveIndex === 0}" @click="tabItemClicked('goodsForSale')">出售中</div>
      <div class="tabItem" :class="{tabItemActive:tabItemActiveIndex === 1}" @click="tabItemClicked('goodsForSaleOut')">已售罄</div>
      <div class="tabItem" :class="{tabItemActive:tabItemActiveIndex === 2}" @click="tabItemClicked('goodsForInStock')">仓库中</div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'allGoodsHeader',
  // 数据data
  data () {
    return {
      tabItemActiveIndex: 0
    }
  },
  watch: {
    /* 用来单独处理在 */
    $route (to) {
      if (to.name === 'goodsForSaleOut') {
        this.tabItemActiveIndex = 1
      } else if (to.name === 'goodsForInStock') {
        this.tabItemActiveIndex = 2
      } else {
        // goodsForSale
        this.tabItemActiveIndex = 0
      }
    }
  },
  methods: {
    tabItemClicked (routerName) {
      this.$router.push({name: routerName})
    }
  },
  mounted () {
    // 初始化国际语言
    this.langDefault()
  }
}
</script>

<style scoped>
  .headerTab{
    border-bottom: 1px solid #EEEEEE;
    color: #666;
    display: flex;
  }
  .tabItem{
    width: 50px;
    line-height: 50px;
    margin:0px 5px;
    cursor: pointer;
  }
  .tabItemActive{
    border-bottom: 2px solid #5a8bff;
  }
</style>
