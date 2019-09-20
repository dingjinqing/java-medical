<template>
  <div>
    <div class="headerTab">
      <div class="tabItem tabItemActive">出售中</div>
      <div class="tabItem" @click="tabItemClicked('goodsForSaleOut')">已售罄</div>
      <div class="tabItem" @click="tabItemClicked('goodsForInStock')">仓库中</div>
    </div>
    <allGoodsHeader ref="allGoodsHeaderCmp" :initSortCatParams="initFilterData"/>
    <div class="btnWrap">
      <el-button type="primary" @click="searchGoodsData">查询</el-button>
      <el-button type="primary" @click="resetFormData">重置</el-button>
      <el-button type="primary">导出商品</el-button>
      <el-button type="primary">添加商品</el-button>
    </div>
    <saleOnAndInStockContent ref="saleOnAndInStockContentCmp"/>
  </div>
</template>

<script>

/* 组件引入 */
import allGoodsHeader from '../allGoodsHeader'
import saleOnAndInStockContent from '../saleOnAndInStockContent'

export default {
  name: 'allGoodsSaleOff',
  components: {
    allGoodsHeader,
    saleOnAndInStockContent
  },
  data () {
    return {
      initFilterData: {
        isOnSale: 1,
        isSaleOut: null
      }
    }
  },
  methods: {
    tabItemClicked (routerName) {
      this.$router.push({name: routerName})
    },
    /* 触发商品分页查询 */
    searchGoodsData () {
      let formFilterData = this.$refs.allGoodsHeaderCmp.getFormData()
      let params = {
        ...formFilterData,
        ...this.initFilterData
      }
      this.$refs.saleOnAndInStockContentCmp.fetchGoodsData(params)
    },
    /* 重置过滤数据 */
    resetFormData () {
      this.$refs.allGoodsHeaderCmp.resetFormData()
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
