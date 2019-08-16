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
      <div>
        <el-table
          :data="tableData"
          style="width: 100%"
        >
          <e-table-column
            prop="name"
            label="优惠券名称"
          >

          </e-table-column>
          <e-table-column
            prop="name"
            label="积分兑换"
          >

          </e-table-column>
          <e-table-column
            prop="name"
            label="价值"
          >

          </e-table-column>
          <e-table-column
            prop="name"
            label="最低消费"
          >

          </e-table-column>

        </el-table>
      </div>
    </div>
  </div>

</template>
<script>
export default {
  data () {
    return {
      tableData: []
    }
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
    padding: 10px 20px 0 20px;
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
</style>
