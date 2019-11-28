<template>
  <div>
    <div class="searchContent">
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
          @click="exportGoodsData"
        >{{$t('allGoods.allGoodsRouterHeader.exportGoods')}}</el-button>
        <el-button
          type="primary"
          size="small"
          @click="addGoodsData"
        >{{$t('allGoods.allGoodsRouterHeader.addGoods')}}</el-button>
      </div>
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
        needGoodsNum: true,
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
    /* 触发商品导出弹窗 */
    exportGoodsData () {
      let formFilterData = this.$refs.allGoodsHeaderCmp.getFormData()
      let formFilterDataString = this.$refs.allGoodsHeaderCmp.getFormDataString()
      let params = {
        ...formFilterData,
        ...this.initFilterData
      }
      this.$refs.saleOnAndInStockContentCmp.showExportDialog(params, formFilterDataString)
    },
    /* 重置过滤数据 */
    resetFormData () {
      this.$refs.allGoodsHeaderCmp.resetFormData()
    },
    /* 添加商品跳转 */
    addGoodsData () {
      this.$router.push({ name: 'goods_add' })
    }
  },
  mounted () {
    this.langDefault()
    this.searchGoodsData()
  }
}
</script>

<style scoped>
.searchContent {
  width: 100%;
  padding: 10px;
  background: #fff;
  margin-bottom: 10px;
}
.headerTab {
  border-bottom: 1px solid #eeeeee;
  color: #666;
  display: flex;
}
.tabItem {
  font-size: 14px;
  min-width: 50px;
  line-height: 40px;
  /* margin: 0px 5px; */
  margin-right: 20px;
  cursor: pointer;
}
.tabItemActive {
  border-bottom: 1.5px solid #5a8bff;
}
.btnWrap {
  padding-left: 50px;
  margin-bottom: 15px;
}
</style>
