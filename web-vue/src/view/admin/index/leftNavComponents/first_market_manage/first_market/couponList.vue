<template>
  <div class="content">
    <div class="main">
      <div class="p_top_right">
        <div class="topRightDiv">
          <span>优惠券名称</span>
          <el-autocomplete
            placeholder="请输入优惠券名称"
            size='small'
          >
          </el-autocomplete>
        </div>
        <div class="topRightDiv s_btn">
          <el-button
            type="primary"
            size="small"
            @click="handleQuery()"
          >查询</el-button>
        </div>
      </div>
    </div>
    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          label="优惠券名称"
          align="center"
        >

        </el-table-column>
        <el-table-column
          prop="pageName"
          label="积分兑换"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="creatTime"
          label="价值"
          align="center"
          width="80"
        >
        </el-table-column>
        <el-table-column
          prop="isFirstPage"
          label="最低消费"
          align="center"
        >

        </el-table-column>
        <el-table-column
          prop="pageClass"
          label="库存"
          align="center"
          width="80"
        >

        </el-table-column>
        <el-table-column
          label="领取限制"
          align="center"
        >

        </el-table-column>
        <el-table-column
          label="有效期"
          align="center"
        >

        </el-table-column>
        <el-table-column
          label="领取人/次"
          align="center"
        >

        </el-table-column>
        <el-table-column
          label="发放人/次"
          align="center"
        >

        </el-table-column>
        <el-table-column
          label="已使用"
          align="center"
        >

        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          width="130"
        >

        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      tableData: []
    }
  },
  methods: {

  }
}
</script>

<style lang="scss" scoped>
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 10px 20px;
  }
}
.condition {
  position: relative;
  background-color: #fff;
  padding: 10px 20px 0 20px;
}
.p_top_right {
  display: flex;
  /deep/ .el-button {
    padding: none;
    height: 32px;
  }
  span {
    white-space: nowrap;
    height: 32px;
    line-height: 32px;
    margin-right: 10px;
  }
  .topRightDiv {
    &:nth-of-type(2) {
      margin: 0 10px 0 30px;
    }
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
  padding: 10px 20px 0 20px;
}
</style>
