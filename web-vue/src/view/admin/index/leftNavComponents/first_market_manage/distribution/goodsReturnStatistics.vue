<template>
  <div>
    <div class="searchInfo">
      <el-select
        v-model="goodsValue"
        placeholder="请选择"
        size="small"
        class="inputStyle"
      >
        <el-option
          v-for="item in goodsClassify"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <el-input
        size="small"
        placeholder="请输入内容"
        prefix-icon="el-icon-search"
        v-model="goodsName"
        class="inputStyle"
      ></el-input>
      <el-button
        type="primary"
        size="small"
        @click="initData"
      >筛选</el-button>
      <el-button size="small">导出</el-button>
    </div>

    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        border
        style="width: 100%"
        :data="tableData"
      >
        <el-table-column
          prop="goodsName"
          label="商品名称"
          align="center"
        >
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
            <div class="opt">
              <p @click="showDetail(scope.row.id)">查看明细</p>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination
      :page-params.sync="pageParams"
      @pagination="initData"
    />
  </div>

</template>

<script>
import { goodsReturnStatistics } from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      goodsValue: '',
      goodsName: '',
      goodsClassify: [],
      tableData: [],
      pageParams: {}
    }
  },
  mounted () {
    this.initData()
  },
  methods: {
    initData () {
      let requestParams = {}
      requestParams.goodsValue = this.goodsValue
      requestParams.goodsName = this.goodsName
      requestParams.currentPage = this.pageParams.currentPage
      requestParams.pageRows = this.pageParams.pageRows
      goodsReturnStatistics(requestParams).then(res => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
        }
      })
    },
    // 分销员邀请用户列表
    showDetail (id) {
      this.$router.push({
        path: '/admin/home/main/distribution/goodsReturnStaticticsDetail',
        query: {
          prdId: id
        }
      })
    }
  }

}

</script>
<style lang="scss" scoped>
.searchInfo {
  display: flex;
  .inputStyle {
    width: 170px;
    margin-right: 20px;
  }
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.table_list {
  position: relative;
  margin-top: 10px;
  background-color: #fff;
  padding: 10px 20px 10px 20px;
}
</style>
