<template>
  <div>
    <div class="searchContent">
      <div class="headerTab">
        <div
          class="tabItem"
          @click="tabItemClicked('goodsForSale')"
        >{{$t('allGoods.allGoodsRouterHeader.saleOn')}}</div>
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
        <el-button
                type="primary"
                size="small"
                @click="batchUpOrDownGoods(1)"
        >批量上架his独有数据</el-button>
        <el-button
                type="primary"
                size="small"
                @click="batchUpOrDownGoods(2)"
        >批量上架store独有数据</el-button>
        <el-button
          type="primary"
          size="small"
          @click="batchUpOrDownGoods(3)"
        >批量上架已匹配数据</el-button>
      </div>
    </div>

    <saleOnAndInStockContent
      ref="saleOnAndInStockContentCmp"
      :initFilterData="initFilterData"
      @sortChange="sortChange"
    />
  </div>
</template>

<script>
/* 组件引入 */
import allGoodsHeader from '../allGoodsHeader'
import saleOnAndInStockContent from '../saleOnAndInStockContent'

// api导入
import { batchOperateGoods } from '@/api/admin/goodsManage/allGoods/allGoods'

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
        // 是否在售 否
        isOnSale: 0,
        // 查询分类信息展示其商品数量是以商品为统计对象还是以规格为统计对象 1 以商品为对象
        selectType: 1
      },
      sortData: {
        orderField: null,
        orderDirection: null
      }
    }
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
      let formFilterData = this.$refs.allGoodsHeaderCmp.getFormData()
      let formFilterDataString = this.$refs.allGoodsHeaderCmp.getFormDataString()
      console.log(formFilterDataString)
      let params = {
        ...formFilterData,
        ...this.initFilterData,
        ...this.sortData
      }
      this.$refs.saleOnAndInStockContentCmp.fetchGoodsData(params, formFilterDataString)
    },
    /* 触发商品导出弹窗 */
    exportGoodsData () {
      let formFilterData = this.$refs.allGoodsHeaderCmp.getFormData()
      let formFilterDataString = this.$refs.allGoodsHeaderCmp.getFormDataString()
      let params = {
        ...formFilterData,
        ...this.initFilterData,
        ...this.sortData
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
    },
    /* 批量上架双方已经匹配的数据 */
    batchUpOrDownGoods (type) {
      let param = {}
      switch (type) {
        case 1:
          param.batchUpOrDownHisGoods = 1
          break
        case 2:
          param.batchUpOrDownStoreGoods = 1
          break
        case 3:
          param.batchUpOrDownBothInGoods = 1
          break
        default:
          break
      }

      batchOperateGoods(param).then(res => {
        if (res.error !== 0) {
          this.$message.warning({message: '操作失败'})
        } else {
          this.$message.success({message: '操作成功'})
          this.searchGoodsData()
        }
      })
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
