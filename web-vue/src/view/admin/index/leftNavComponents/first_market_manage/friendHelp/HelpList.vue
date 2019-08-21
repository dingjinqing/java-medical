<template>
  <div class="content">
    <div class="main">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          label="全部好友助力活动"
          name="first"
        >
          <el-button
            type="primary"
            @click="addActivity"
          >添加好友助力活动</el-button>
        </el-tab-pane>
        <el-tab-pane
          label="进行中"
          name="second"
        >
          <el-button
            type="primary"
            @click="addActivity"
          >添加好友助力活动</el-button>
        </el-tab-pane>
        <el-tab-pane
          label="未开始"
          name="third"
        >
          <el-button
            type="primary"
            @click="addActivity"
          >添加好友助力活动</el-button>
        </el-tab-pane>
        <el-tab-pane
          label="已过期"
          name="fourth"
        >
          <el-button
            type="primary"
            @click="addActivity"
          >添加好友助力活动</el-button>
        </el-tab-pane>
        <el-tab-pane
          label="已停用"
          name="fifth"
        >
          <el-button
            type="primary"
            @click="addActivity"
          >添加好友助力活动</el-button>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div class="table_list">
      <div class="select_info">
        <span>活动名称：</span>
        <el-input></el-input>
        <span>活动时间：</span>
        <el-input></el-input>
        <span>至</span>
        <el-input></el-input>
        <span>奖励类型</span>
        <el-select></el-select>
        <el-button type="primary">筛选</el-button>
      </div>
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop=""
          label="活动名称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="活动有效期"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="奖励类型"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="奖励库存"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="已领取奖励数量"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="活动状态"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="操作"
          align="center"
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
      tableData: [],
      activeName: 'second'
    }
  },
  mounted () {
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
  padding: 10px 20px 10px 20px;
  .select_info {
    display: flex;
    span {
      width: 100%;
    }
    /deep/ .el-iniput__inner {
      width: 100px;
    }
  }
}
.opt {
  text-align: left;
  color: #5a8bff;
  span {
    cursor: pointer;
  }
}
.balanceDialo .el-dialog__body {
  padding-bottom: 0 !important;
}
.balanceDialo .el-dialog__footer {
  border-top: 1px solid #eee;
}
.setUpDialog .el-dialog__body {
  padding-top: 10px !important;
}
.add_coupon {
  float: left;
  margin-left: 65%;
}
</style>
