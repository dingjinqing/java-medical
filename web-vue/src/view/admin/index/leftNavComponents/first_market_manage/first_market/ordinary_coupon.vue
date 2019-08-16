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
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="tableData"
          border
          style="width: 100%"
        >
          <el-table-column
            label=""
            align="center"
          >

          </el-table-column>
          <el-table-column
            prop="pageName"
            label="页面名称"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="creatTime"
            label="创建时间"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="isFirstPage"
            label="是否首页"
            align="center"
          >

          </el-table-column>
          <el-table-column
            prop="pageClass"
            label="页面分类"
            align="center"
          >

          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="150"
          >

          </el-table-column>
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
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
</style>
