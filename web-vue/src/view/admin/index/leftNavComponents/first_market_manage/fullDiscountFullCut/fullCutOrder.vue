<template>
  <div class="container">
    <div class="top">
      <el-form
        size="small"
        :inline="true"
        label-suffix="："
        label-width="110px"
      >
        <el-form-item label="订单号">
          <el-input
            class="form-input"
            placeholder="请输入订单号"
          ></el-input>
        </el-form-item>
        <el-form-item label="商品信息">
          <el-input
            class="form-input"
            placeholder="请输入商品名称"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="下单人">
          <el-input
            class="form-input"
            placeholder="请输入下单人昵称/手机号"
          ></el-input>
        </el-form-item>
        <el-form-item label="订单状态">
          <el-select
            v-model="queryParams.state"
            class="form-input"
          >
            <el-option
              label="全部"
              value=""
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary">查询</el-button>
          <el-button>数据导出</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="content">
      <el-table
        :data="tableData"
        header-row-class-name="tableClss"
        border
      >
        <el-table-column
          align="center"
          label="订单号"
        ></el-table-column>
        <el-table-column
          align="center"
          label="商品"
        ></el-table-column>
        <el-table-column
          align="center"
          label="单价"
        ></el-table-column>
        <el-table-column
          align="center"
          label="活动优惠金额"
        ></el-table-column>
        <el-table-column
          align="center"
          label="实付金额"
        ></el-table-column>
        <el-table-column
          align="center"
          label="数量"
        ></el-table-column>
        <el-table-column
          align="center"
          label="下单人"
        ></el-table-column>
        <el-table-column
          align="center"
          label="订单状态"
        ></el-table-column>
      </el-table>
      <!-- 翻页 -->
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />
    </div>
  </div>
</template>

<script>
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      tableData: [],
      pageParams: {},
      queryParams: {}
    }
  },
  mounted () { },
  methods: {
    initDataList () {
      let params = Object.assign({}, this.queryParams, this.pageParams)
      console.log(params)
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .top {
    background-color: #fff;
    padding: 15px;
  }
  .content {
    background-color: #fff;
    margin-top: 10px;
    padding: 15px;
  }
  .form-input {
    width: 190px;
  }
  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    font-weight: bold;
    color: #000;
    padding: 8px 10px;
  }
}
</style>
