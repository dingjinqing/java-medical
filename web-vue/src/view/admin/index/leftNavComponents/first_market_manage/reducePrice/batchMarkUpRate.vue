<template>
  <div class="container">

    <!-- 搜索 -->
    <div>
      <el-button
        type="primary"
        size="small"
      >查询</el-button>
    </div>

    <!-- 表格 -->
    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop=""
          label="商家分类"
          align="center"
        ></el-table-column>
        <el-table-column
          prop=""
          label="商家品牌"
          align="center"
        ></el-table-column>
        <el-table-column
          prop=""
          label="商家毛利"
          align="center"
        ></el-table-column>
        <el-table-column
          prop=""
          label="活动名称"
          align="center"
        ></el-table-column>
        <el-table-column
          prop=""
          label="有效期"
          align="center"
        ></el-table-column>
        <el-table-column
          prop=""
          label="活动状态"
          align="center"
        ></el-table-column>
        <el-table-column
          prop=""
          label="添加时间"
          align="center"
        ></el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />
    </div>

  </div>
</template>

<script>
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: {
    pagination
  },
  data () {
    return {
      tableData: [], // 表格数据
      pageParams: {}, // 分页
      requestParams: {}
    }
  },
  watch: {
    lang () {
    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    this.initDataList()
  },
  methods: {
    initDataList () {

    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  margin-top: 10px;
  background-color: #fff;
  padding: 15px;
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
  //   padding: 15px;
}
</style>
