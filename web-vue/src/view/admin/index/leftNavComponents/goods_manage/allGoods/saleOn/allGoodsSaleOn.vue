<template>
  <div>
    <div class="searchContent">
      <div class="headerTab">
        <div class="tabItem tabItemActive">{{$t('allGoods.allGoodsRouterHeader.saleOn')}}</div>
        <div
          class="tabItem"
          @click="tabItemClicked('goodsForSaleOut')"
        >{{$t('allGoods.allGoodsRouterHeader.saleOut')}}</div>
        <div
          class="tabItem"
          @click="tabItemClicked('goodsForInStock')"
        >{{$t('allGoods.allGoodsRouterHeader.inStock')}}</div>
      </div>
      <!-- 查询 -->
      <allGoodsHeader
        ref="allGoodsHeaderCmp"
        :initSortCatParams="initFilterData"
      />
      <!-- 按钮 -->
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

    <!-- 表格 -->
    <saleOnAndInStockContent ref="saleOnAndInStockContentCmp" />
  </div>
</template>

<script>

/* 组件引入 */
import allGoodsHeader from '../allGoodsHeader'
import saleOnAndInStockContent from '../saleOnAndInStockContent'

export default {
  name: 'allGoodsSaleOn',
  components: {
    allGoodsHeader,
    saleOnAndInStockContent
  },
  data () {
    return {
      // 在售未售罄状态
      initFilterData: {
        // 分类信息是否需要展示商品数量信息
        needGoodsNum: true,
        // 是否在售 是
        isOnSale: 1,
        // 是否查询售罄商品
        isSaleOut: false,
        // 查询分类信息展示其商品数量是以商品为统计对象还是以规格为统计对象 1 以商品为对象
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
