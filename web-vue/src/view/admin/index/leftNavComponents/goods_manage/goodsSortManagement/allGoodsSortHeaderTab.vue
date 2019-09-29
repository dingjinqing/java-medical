<template>
  <div class="allGoodsSortHeaderTab">
    <div class="tabItem" @click="itemClicked('allGoodsSort')" :class="{tabItemActive: tabIndex === 0}">分类列表</div>
    <div class="tabItem" @click="itemClicked('allGoodsRecommendSort')" :class="{tabItemActive: tabIndex === 1}">推荐分类</div>
    <div class="tabItem tabItemActive" v-if="tabIndex === 2">添加分类</div>
    <div class="tabItem tabItemActive" v-if="tabIndex === 3">添加推荐分类</div>
    <div class="tabItem tabItemActive" v-if="tabIndex === 4">编辑分类</div>
    <div class="tabItem tabItemActive" v-if="tabIndex === 5">编辑推荐分类</div>
  </div>
</template>

<script>
export default {
  name: 'allGoodsSortHeaderTab',
  props: {
    tabIndex: Number
  },
  methods: {
    itemClicked (routerName) {
      this.$router.push({name: routerName})
    }
  }
}
</script>

<style scoped>
  .allGoodsSortHeaderTab{
    border-bottom: 1px solid #EEEEEE;
    color: #666;
    display: flex;
  }
  .tabItem{
    min-width: 50px;
    line-height: 50px;
    margin:0px 5px;
    cursor: pointer;
  }
  .tabItemActive{
    border-bottom: 2px solid #5a8bff;
  }
</style>
