<template>
  <div class="content">
    <div class="main">
      <el-tabs
        v-model="tabSwitch"
        @tab-click="handleClick"
      >
        <el-tab-pane
          v-for="item in tabInfo"
          :key="item.name"
          :label="item.title"
          :name="item.name"
        >
          <el-button
            type="primary"
            class="barginBtn"
          >添加返利策略活动</el-button>
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
          label="返利策略名称"
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
          label="返利比列"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="优先级"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="创建时间"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="状态"
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
      <div class="footer_right">
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
      item: '',
      value: '',
      tabSwitch: '2',
      tabInfo: [{
        title: '全部策略',
        name: '1'
      }, {
        title: '进行中',
        name: '2'
      }, {
        title: '未开始',
        name: '3'
      }, {
        title: '已过期',
        name: '4'
      }, {
        title: '已停用',
        name: '5'
      }],
      tabIndex: 2,
      currentPage: 1,
      tableData: null
    }
  },
  methods: {
    handleClick (tab) {
      // console.log(tab.index)
    },
    handleCurrentChange () {
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
  padding: 10px 0px 10px;
  .footer_right {
    padding: 20px 0 20px 20px;
    display: flex;
    justify-content: flex-end;
    span {
      display: block;
      height: 32px;
      line-height: 32px;
    }
  }
}
</style>
