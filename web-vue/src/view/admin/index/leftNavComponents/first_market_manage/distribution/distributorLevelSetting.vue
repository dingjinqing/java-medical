<template>
  <div class="content">
    <div class="main">
      <div class="mainInfo"> 提示：每次修改分销员等级，将会有大量分销员受到影响，请谨慎操作</div>
    </div>

    <el-button
      type="text"
      @click="centerDialogVisible = true"
    >升级规则</el-button>

    <el-dialog
      title="提醒"
      :visible.sync="centerDialogVisible"
      width="16%"
      center
    >
      <div class="textInfo">累计邀请用户数：分销员累积邀请的用户数。</div>
      <div class="textInfo">累积推广金：分销员推广商品的订单累计金额。</div>
      <div class="textInfo">累积消费金：分销员在店铺累积消费金额。</div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="centerDialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="centerDialogVisible = false"
        >确 定</el-button>
      </span>
    </el-dialog>

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
          label="等级"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="等级名称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="升级规则"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="分销员数量"
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
      tableData: null,
      centerDialogVisible: false
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
    .mainInfo {
      width: 100%;
      margin-top: 16px;
      height: 40px;
      line-height: 37px;
      border: 1px solid #f2e1c8;
      background: #fff7ec;
      color: #666;
      margin-bottom: 16px;
      padding-left: 12px;
    }
  }
  .textInfo {
    margin-bottom: 20px;
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
