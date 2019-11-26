<template>
  <div class="content">
    <div class="main">
      <el-tabs
        v-model="tabSwitch"
        @tab-click="handleClick"
      >
        <el-tab-pane
          v-for="(item) in tabInfo"
          :key="item.name"
          :label="item.title"
          :name="item.name"
        >
          <el-button
            type="primary"
            size="small"
            class="barginBtn"
          >添加拼团抽奖活动</el-button>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div class="table_list">
      <div class="select_info">
        <div class="leftarea">
          <span>活动名称：</span>
          <el-input size="small"></el-input>
        </div>
        <div class="midarea">
          <span>活动有效期：</span>
          <el-input size="small"></el-input>
          <span>至</span>
          <el-input size="small"></el-input>
        </div>
        <div class="rightarea">
          <el-button
            type="primary"
            size="small"
          >筛选</el-button>
        </div>
      </div>
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
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
          label="有效期"
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
          label="商品数"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="最少成团人数"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="开奖所需最少人数"
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
          label="成团人数"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="开团数"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop=""
          label="中奖用户"
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
  cerate () {

  },
  data () {
    return {
      tabSwitch: '2',
      tabInfo: [{
        title: '全部砍价活动',
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
      options: [{
        value: '1',
        label: '黄金糕'
      }, {
        value: '2',
        label: '双皮奶'
      }, {
        value: '3',
        label: '蚵仔煎'
      }, {
        value: '4',
        label: '龙须面'
      }, {
        value: '5',
        label: '北京烤鸭'
      }]
    }
  },
  methods: {
    // 当前页发生变化
    handleCurrentChange () {
      console.log(this.currentPage)
    },
    handleClick (tab) {
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
    margin: 10px 0px;
    .leftarea {
      display: flex;
      margin-right: 30px;
    }
    .rightarea {
      display: flex;
      :nth-of-type(1) {
        margin-right: 5px;
      }
    }
    .midarea {
      display: flex;
      margin-right: 30px;
      :nth-of-type(2) {
        margin: 0 0 0 10px;
      }
    }
    span {
      white-space: nowrap;
      height: 32px;
      line-height: 32px;
    }
    /deep/ .el-input__inner {
      width: 200px;
      display: inline-block;
    }
  }
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
