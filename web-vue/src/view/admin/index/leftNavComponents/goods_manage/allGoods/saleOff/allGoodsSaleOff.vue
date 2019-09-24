<template>
  <div>
    <div class="headerTab">
      <div class="tabItem" @click="tabItemClicked('goodsForSale')">出售中</div>
      <div class="tabItem tabItemActive">已售罄</div>
      <div class="tabItem" @click="tabItemClicked('goodsForInStock')">仓库中</div>
    </div>
    <allGoodsHeader ref="goodsHeaderFormCmp" :initSortCatParams="initFilterData"/>
    <div class="btnWrap">
      <el-button type="primary" @click="searchGoodsData">查询</el-button>
      <el-button type="primary" @click="resetFormData">重置</el-button>
      <el-button type="primary">导出商品</el-button>
      <el-button type="primary">添加商品</el-button>
    </div>
    <saleOut ref="saleOutCmp"/>
  </div>
</template>

<script>

import allGoodsHeader from '../allGoodsHeader'
import saleOut from '../saleOut'
export default {
  name: 'allGoodsSaleOff',
  data () {
    return {
      initFilterData: {
        isOnSale: 1,
        isSaleOut: true,
        selectType: 2
      }
    }
  },
  components: {
    allGoodsHeader,
    saleOut
  },
  methods: {
    tabItemClicked (routerName) {
      this.$router.push({name: routerName})
    },
    /* 触发商品分页查询 */
    searchGoodsData () {
      let formFilterData = this.$refs.goodsHeaderFormCmp.getFormData()
      let params = {
        ...formFilterData,
        ...this.initFilterData
      }
      this.$refs.saleOutCmp.fetchGoodsData(params)
    },
    /* 重置过滤数据 */
    resetFormData () {
      this.$refs.goodsHeaderFormCmp.resetFormData()
    }
  },
  mounted () {
    this.searchGoodsData()
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
  .btnWrap{
    padding-left: 50px;
    margin-bottom: 15px;
  }
</style>
