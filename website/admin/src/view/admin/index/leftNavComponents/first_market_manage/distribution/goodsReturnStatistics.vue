<template>
  <div>
    <el-form
      :inline="true"
      :model="searchParam"
    >
      <el-form-item>
        <sortCatTreeSelect
          ref="sortTree"
          :filterGoodsInfo="{needGoodsNum: false}"
          treeType="sort"
          title="请选择商品分类"
          :selectedId.sync="searchParam.goodsSort"
        />
      </el-form-item>
      <el-form-item>
        <el-input
          size="small"
          placeholder="请输入商品名称"
          prefix-icon="el-icon-search"
          v-model="searchParam.goodsName"
          class="inputStyle"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          size="small"
          @click="initData"
        >筛选</el-button>
        <el-button
          size="small"
          @click="exportDataList"
        >导出</el-button>
      </el-form-item>
    </el-form>

    <el-table
      class="version-manage-table"
      header-row-class-name="tableClss"
      border
      style="width: 100%"
      :data="tableData"
    >
      <template slot="empty">
        <tableEmpty />
      </template>
      <el-table-column
        label="商品名称"
        align="center"
      >
        <template slot-scope="scope">
          <span
            @click="goodsHandler(scope.row.goodsId)"
            class="highStyle"
          >{{scope.row.goodsName}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="shopPrice"
        label="商品价格"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="catName"
        label="商品所属分类"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="goodsSaleNum"
        label="商品总销量"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="saleNumber"
        label="已返利总数量"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="prdTotalFanli"
        label="已返利总佣金"
        align="center"
      >
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
      >
        <template slot-scope="scope">
          <span
            @click="showDetail(scope.row.goodsId)"
            class="highStyle"
          >查看明细</span>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      :page-params.sync="pageParams"
      @pagination="initData"
    />

    <!-- 导出数据弹窗 -->
    <exportDialog
      :tuneUp="exportDialog"
      :param="this.searchParam"
      :totalRows="totalRows"
      :type="3"
      @export="exportHandler"
    />
  </div>
</template>

<script>
import { download } from '@/util/excelUtil.js'
import { goodsReturnStatistics, rebateGoodsExport } from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination'),
    exportDialog: () => import('./moneyExportDialog'),
    sortCatTreeSelect: () => import('@/components/admin/sortCatTreeSelect')
  },
  data () {
    return {
      goodsClassify: [], // 商品分类列表
      searchParam: {
        goodsSort: null,
        goodsName: ''
      },
      tableData: [],
      pageParams: {},

      exportDialog: false, // 导出数据弹窗
      totalRows: 0 // 筛选个数
    }
  },
  computed: {
    initSortCatParams: function () {
      return {
        needGoodsNum: true,
        isOnSale: 1,
        isSaleOut: 0,
        selectType: 1
      }
    }
  },
  mounted () {
    this.initData()
  },
  methods: {
    initData () {
      return new Promise((resolve, reject) => {
        let requestParams = {}
        requestParams.goodsSort = this.searchParam.goodsSort
        requestParams.goodsName = this.searchParam.goodsName
        requestParams.currentPage = this.pageParams.currentPage
        requestParams.pageRows = this.pageParams.pageRows
        goodsReturnStatistics(requestParams).then(res => {
          if (res.error === 0) {
            this.tableData = res.content.dataList
            this.pageParams = res.content.page
            resolve(this.pageParams)
          }
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 查看商品返利详情
    showDetail (goodsId) {
      this.$router.push({
        name: 'distribution_info_goodsStatictics',
        query: {
          goodsId: goodsId
        }
      })
    },

    // 导出数据
    exportDataList () {
      this.initData().then(() => {
        this.searchParam = {
          goodsSort: this.searchParam.goodsSort,
          goodsName: this.searchParam.goodsName,
          sortName: this.$refs['sortTree'].getSelectedText()
        }
        this.totalRows = this.pageParams.totalRows
        this.exportDialog = !this.exportDialog
      })
    },

    // 导出数据弹窗回调函数
    exportHandler (data) {
      // 搜索条件
      var obj = {}
      for (var i in data) {
        if (i === 'startNum' || i === 'endNum') {
          obj[i] = data[i]
        } else if (data[i]) {
          obj[i] = data[i]
        }
      }
      rebateGoodsExport(obj).then(res => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : '商品返利列表导出.xlsx'
        download(res, decodeURIComponent(fileName))
      })
    },

    // 跳转商品详情
    goodsHandler (goodsId) {
      this.$router.push({
        name: 'goods_update',
        params: {
          goodsId: goodsId
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.inputStyle {
  width: 170px;
  margin-right: 20px;
}
.highStyle {
  color: #5a8bff;
  cursor: pointer;
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
/deep/ .el-form--inline .el-form-item__label {
  display: none;
}
</style>
