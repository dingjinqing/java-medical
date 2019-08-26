<template>
  <div class="content">
    <div class="table_list">
      <div class="select_info">
        <div class="leftarea">
          <span>手机号：</span>
          <el-input size="small"></el-input>
        </div>
        <div class="leftarea">
          <span>昵称：</span>
          <el-input size="small"></el-input>
        </div>
        <div class="midarea">
          <span>申请时间：</span>
          <el-input size="small"></el-input>
          <span>至</span>
          <el-input size="small"></el-input>
        </div>
        <div class="rightarea">
          <el-button
            type="primary"
            size="small"
          >查询</el-button>
        </div>
      </div>
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="actName"
          label="活动名称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="validDate"
          label="活动有效期"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="rewardType"
          label="奖励类型"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="marketStore"
          label="奖励库存"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="recNum"
          label="已领取奖励数量"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="actState"
          label="活动状态"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <span>编辑</span>
              <span>分享</span>
              <span>领取明细</span>
              <span>发起明细</span>
              <span>参与明细</span>
              <span @click="startOrBlock(scope)">启用</span>
              <span @click="startOrBlock(scope)">停用</span>
              <span @click="delAct(scope)">删除</span>
            </div>
          </template>
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
      currentPage: 1
    }
  },
  methods: {
    handleCurrentChange (tab) {

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
  background-color: #fff;
  padding: 0px 20px 10px 20px;
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
}
</style>
