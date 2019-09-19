<template>
  <div>
    <div class="headerTab">
      <div class="tabItem tabItemActive">出售中</div>
      <div class="tabItem" @click="tabItemClicked('goodsForSaleOut')">已售罄</div>
      <div class="tabItem" @click="tabItemClicked('goodsForInStock')">仓库中</div>
    </div>
    <allGoodsHeader :initSortCatParams="initFilterData"/>
  </div>
</template>

<script>

import allGoodsHeader from '../allGoodsHeader'
export default {
  name: 'allGoodsSaleOff',
  data () {
    return {
      initFilterData: {
        isOnSale: 1,
        isSaleOut: null
      }
    }
  },
  components: {
    allGoodsHeader
  },
  methods: {
    tabItemClicked (routerName) {
      this.$router.push({name: routerName})
    }
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
