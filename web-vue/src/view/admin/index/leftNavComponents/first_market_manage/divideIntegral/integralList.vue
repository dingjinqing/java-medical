<template>
  <div class="content">
    <div class="main">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          label="全部瓜分活动"
          name="first"
        >
          <el-button
            type="primary"
            @click="addActivity"
          >添加瓜分积分活动</el-button>
        </el-tab-pane>
        <el-tab-pane
          label="进行中"
          name="second"
        >
          <el-button
            type="primary"
            @click="addActivity"
          >添加瓜分积分活动</el-button>
        </el-tab-pane>
        <el-tab-pane
          label="未开始"
          name="third"
        >
          <el-button
            type="primary"
            @click="addActivity"
          >添加瓜分积分活动</el-button>
        </el-tab-pane>
        <el-tab-pane
          label="已过期"
          name="fourth"
        >
          <el-button
            type="primary"
            @click="addActivity"
          >添加瓜分积分活动</el-button>
        </el-tab-pane>
        <el-tab-pane
          label="已停用"
          name="fifth"
        >
          <el-button
            type="primary"
            @click="addActivity"
          >添加瓜分积分活动</el-button>
        </el-tab-pane>
      </el-tabs>
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
          prop=""
          label="活动名称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="活动内容"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="活动积分总量"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="有效期"
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
          label="参与人数"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="团数量"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="消耗积分"
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
      <div class="footer">
        <span>当前页面1/1，总记录4条</span>
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :page-size="20"
          layout="prev, pager, next, jumper"
          :total="4"
        >
        </el-pagination>
      </div>
    </div>
  </div>

</template>
<script>

export default {
  data () {
    return {
      tableData: [],
      activeName: 'second',
      currentPage: 1
    }
  },
  mounted () {
  },
  methods: {
    // 当前页发生变化
    handleCurrentChange () {
      console.log(this.currentPage)
    },
    handleClick (tab) {
      console.log(tab)
    },
    addActivity () {
      console.log(111)
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
.footer {
  padding: 20px 0 20px 20px;
  display: flex;
  justify-content: flex-end;
  span {
    display: block;
    height: 32px;
    line-height: 32px;
  }
}
</style>
