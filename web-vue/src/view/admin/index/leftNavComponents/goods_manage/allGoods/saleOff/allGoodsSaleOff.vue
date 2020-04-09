<template>
  <div>
    <div class="searchContent">
      <div class="headerTab">
        <div
          class="tabItem"
          @click="tabItemClicked('goodsForSale')"
        >{{$t('allGoods.allGoodsRouterHeader.saleOn')}}</div>
        <div class="tabItem tabItemActive">{{$t('allGoods.allGoodsRouterHeader.saleOut')}}</div>
        <div
          class="tabItem"
          @click="tabItemClicked('goodsForInStock')"
        >{{$t('allGoods.allGoodsRouterHeader.inStock')}}</div>
      </div>
      <allGoodsHeader
        ref="goodsHeaderFormCmp"
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

    <saleOut ref="saleOutCmp"  @sortChange="sortChange"/>
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
        needGoodsNum: true,
        isOnSale: 1,
        isSaleOut: true,
        selectType: 2
      },
      sortData: {
        orderField: null,
        orderDirection: null
      }
    }
  },
  components: {
    allGoodsHeader,
    saleOut
  },
  methods: {
    tabItemClicked (routerName) {
      this.$router.push({ name: routerName })
    },
    /* 更新排序字段 */
    sortChange (prop, order) {
      console.log(prop)
      console.log(order)
      if (order === null) {
        this.sortData.orderField = null
        this.sortData.orderDirection = null
      } else {
        this.sortData.orderField = prop
        this.sortData.orderDirection = (order === 'ascending' ? 'asc' : 'desc')
      }
      this.searchGoodsData()
    },
    /* 触发商品分页查询 */
    searchGoodsData () {
      let formFilterData = this.$refs.goodsHeaderFormCmp.getFormData()
      let params = {
        ...formFilterData,
        ...this.initFilterData,
        ...this.initFilterData,
        ...this.sortData
      }
      this.$refs.saleOutCmp.fetchGoodsData(params)
    },
    /* 触发商品导出弹窗 */
    exportGoodsData () {
      let formFilterData = this.$refs.goodsHeaderFormCmp.getFormData()
      let formFilterDataString = this.$refs.goodsHeaderFormCmp.getFormDataString()
      let params = {
        ...formFilterData,
        ...this.initFilterData
      }
      this.$refs.saleOutCmp.showExportDialog(params, formFilterDataString)
    },
    /* 重置过滤数据 */
    resetFormData () {
      this.$refs.goodsHeaderFormCmp.resetFormData()
    },
    /* 添加商品跳转 */
    addGoodsData () {
      this.$router.push({ name: 'goods_add' })
    }
  },
  mounted () {
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
