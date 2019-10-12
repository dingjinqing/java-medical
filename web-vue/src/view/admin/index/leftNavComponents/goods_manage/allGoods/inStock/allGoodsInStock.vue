<template>
  <div>
    <div class="headerTab">
      <div
        class="tabItem"
        @click="tabItemClicked('goodsForSale')"
      >{{$t('allGoods.allGoodsRouterHeader.saleOn')}}</div>
      <div
        class="tabItem"
        @click="tabItemClicked('goodsForSaleOut')"
      >{{$t('allGoods.allGoodsRouterHeader.saleOut')}}</div>
      <div class="tabItem tabItemActive">{{$t('allGoods.allGoodsRouterHeader.inStock')}}</div>
    </div>
    <allGoodsHeader
      ref="allGoodsHeaderCmp"
      :initSortCatParams="initFilterData"
    />
    <div class="btnWrap">
      <el-button
        type="primary"
        size="small"
        @click="searchGoodsData"
      >{{$t('allGoods.allGoodsRouterHeader.searchBtn')}}</el-button>
      <el-button
        type="primary"
        size="small"
        @click="resetFormData"
      >{{$t('allGoods.allGoodsRouterHeader.resetBtn')}}</el-button>
      <el-button
        type="primary"
        size="small"
      >{{$t('allGoods.allGoodsRouterHeader.exportGoods')}}</el-button>
      <el-button
        type="primary"
        size="small"
      >{{$t('allGoods.allGoodsRouterHeader.addGoods')}}</el-button>
    </div>
    <saleOnAndInStockContent ref="saleOnAndInStockContentCmp" />
  </div>
</template>

<script>
/* 组件引入 */
import allGoodsHeader from '../allGoodsHeader'
import saleOnAndInStockContent from '../saleOnAndInStockContent'

export default {
  name: 'allGoodsInStock',
  components: {
    allGoodsHeader,
    saleOnAndInStockContent
  },
  data () {
    return {
      initFilterData: {
        isOnSale: 0,
        selectType: 1
      }
    }
  },
  methods: {
    tabItemClicked (routerName) {
      this.$router.push({ name: routerName })
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
    this.langDefault()
    this.searchGoodsData()
  }
}
</script>

<style scoped>
.headerTab {
  border-bottom: 1px solid #eeeeee;
  color: #666;
  display: flex;
}
.tabItem {
  min-width: 50px;
  line-height: 50px;
  margin: 0px 5px;
  cursor: pointer;
}
.tabItemActive {
  border-bottom: 2px solid #5a8bff;
}
.btnWrap {
  padding-left: 50px;
  margin-bottom: 15px;
}
</style>
