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
          <el-button type="primary">添加好友助力活动</el-button>
        </el-tab-pane>
        <el-tab-pane
          label="进行中"
          name="second"
        >
          <el-button type="primary">添加好友助力活动</el-button>
        </el-tab-pane>
        <el-tab-pane
          label="未开始"
          name="third"
        >
          <el-button type="primary">添加好友助力活动</el-button>
        </el-tab-pane>
        <el-tab-pane
          label="已过期"
          name="fourth"
        >
          <el-button type="primary">添加好友助力活动</el-button>
        </el-tab-pane>
        <el-tab-pane
          label="已停用"
          name="fifth"
        >
          <el-button type="primary">添加好友助力活动</el-button>
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
          <span>活动时间：</span>
          <el-input size="small"></el-input>
          <span>至</span>
          <el-input size="small"></el-input>
        </div>
        <div class="rightarea">
          <span>奖励类型：</span>
          <el-select
            v-model="options.value"
            size="small"
            placeholder="请选择"
          >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
          <el-button
            type="primary"
            size="small"
          >筛选</el-button>
        </div>
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
// import pagination from '@/components/admin/pagination/pagination'
export default {
  components: {
    // pagination
  },
  data () {
    return {
      tableData: [],
      activeName: 'second',
      currentPage: null,
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
  mounted () {
  },

  methods: {
    // 当前页发生变化
    handleCurrentChange () {
      console.log(this.currentPage)
    },
    handleClick (tab) {
      console.log(tab)
      // 请求参数已经定义好了
      // let obj = {
      //   'type': parseInt(tab.index) + 1,
      //   'currentPage': 1,
      //   'pageRows': 1
      // }
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
