<template>
  <div class="content">
    <div class="main">
      <div class="mainInfo">
        <i
          class="el-icon-warning"
          style="color: #E6A23C;margin-right: 5px;"
        ></i>提示：每次修改分销员等级，将会有大量分销员受到影响，请谨慎操作</div>
    </div>

    <el-button
      type="text"
      @click="centerDialogVisible = true"
    >升级规则</el-button>

    <el-dialog
      title="提醒"
      :visible.sync="centerDialogVisible"
      width="25%"
      center
      :close-on-click-modal="false"
    >
      <div class="textInfo">累计邀请用户数：分销员累积邀请的用户数。</div>
      <div class="textInfo">累积推广金：分销员推广商品的订单累计金额。</div>
      <div class="textInfo">累积消费金：分销员在店铺累积消费金额。</div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          @click="centerDialogVisible = false"
        >确 定</el-button>
      </span>
    </el-dialog>

    <div class="table_list">
      <el-table
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="level"
          label="等级"
          align="center"
        >
        </el-table-column>

        <el-table-column
          label="等级名称"
          align="center"
        >
          <template slot-scope="scope">
            <el-input v-model="scope.row.levelName"></el-input>
          </template>
        </el-table-column>

        <el-table-column
          label="升级规则"
          align="center"
          width="300px"
        >
          <template slot-scope="scope">
            <div v-if="scope.row.level === '1'">成为分销员后，默认即是该等级</div>
            <el-radio-group
              v-model="scope.row.rule"
              v-if="scope.row.level !== '1'"
            >
              <el-radio :label="1">自动升级</el-radio>
              <el-radio :label="2">手动升级</el-radio>
            </el-radio-group>
            <div
              v-if="scope.row.rule === 1 && scope.row.level !== '1'"
              style="margin: 15px 0;"
            >
              <div>累计邀请用户数达 <el-input
                  size="mini"
                  style="width: 50px;"
                ></el-input> 个</div>
              <div>或</div>
              <div>累计推广金达 <el-input
                  size="mini"
                  style="width: 50px;"
                ></el-input> 元</div>
              <div>或</div>
              <div>累积推广金与消费金总和达 <el-input
                  size="mini"
                  style="width: 50px;"
                ></el-input> 元</div>
            </div>
            <div
              v-if="scope.row.rule === 2 && (scope.row.level === '2' || scope.row.level === '3')"
              style="margin: 15px 0;"
            >
              <el-button size="mini"><i class="el-icon-plus"></i> 添加分销员</el-button>
            </div>
          </template>
        </el-table-column>

        <el-table-column
          label="分销员数量"
          align="center"
        >
          <template slot-scope="scope">
            <a href="javascript:void(0);">{{ scope.row.num }}</a>
          </template>
        </el-table-column>

        <el-table-column
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <p v-if="scope.row.status === 1">已启用</p>
            <p v-if="scope.row.status === 0">已停用</p>
            <el-button
              type="primary"
              size="mini"
              v-if="scope.row.status === 0"
            >启用</el-button>
            <el-button
              type="primary"
              plain
              size="mini"
              v-if="scope.row.status === 1"
            >停用</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>

export default {
  data () {
    return {
      tableData: [{
        level: '1',
        levelName: '分销员测试',
        rule: 1,
        num: '36',
        status: 0
      }, {
        level: '2',
        levelName: 'v2',
        rule: 1,
        num: '22',
        status: 0
      }, {
        level: '3',
        levelName: '分销员组3',
        rule: 2,
        num: '2',
        status: 1
      }, {
        level: '4',
        levelName: '分销员组4',
        rule: 2,
        num: '2',
        status: 1
      }, {
        level: '5',
        levelName: '分销员组5',
        rule: 2,
        num: '2',
        status: 0
      }], // 表格数据
      centerDialogVisible: false // dialog
    }
  },
  methods: {

  }

}

</script>
<style lang="scss" scoped>
a {
  text-decoration: none;
  color: #5a8bff;
}
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
      height: 40px;
      line-height: 37px;
      border: 1px solid #f2e1c8;
      background: #fff7ec;
      color: #666;
      margin-bottom: 10px;
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
  background-color: #fff;
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
