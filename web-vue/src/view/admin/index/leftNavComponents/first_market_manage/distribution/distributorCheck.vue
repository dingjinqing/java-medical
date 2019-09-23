<template>
  <div class="content">
    <div class="table_list">
      <div class="select_info">
        <div class="leftarea">
          <span>手机号</span>
          <el-input size="small"></el-input>
        </div>
        <div class="leftarea">
          <span>昵称</span>
          <el-input size="small"></el-input>
        </div>
        <div class="midarea">
          <span>申请时间</span>
          <el-date-picker
            v-model="value1"
            type="date"
            placeholder="选择日期"
            size="small"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
          <span>至</span>
          <el-date-picker
            v-model="value1"
            type="date"
            placeholder="选择日期"
            size="small"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </div>
        <div class="rightarea">
          <el-button
            type="primary"
            size="small"
          >查询</el-button>
        </div>
      </div>
      <!-- <el-table
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
        </el-table-column>
      </el-table> -->

      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          label="待审核分销员"
          name="first"
        >用户管理</el-tab-pane>
        <el-tab-pane
          label="审核通过"
          name="second"
        >配置管理</el-tab-pane>
        <el-tab-pane
          label="未通过"
          name="third"
        >角色管理</el-tab-pane>
      </el-tabs>
      <div>
        <table class="checkList">
          <tr class='title'>
            <td colspan="6">ID:</td>
            <td>11</td>
            <td>11</td>
            <td>11</td>
          </tr>
          <tr>
            <td>真实姓名</td>
            <td>常乐</td>
            <td>手机号</td>
            <td>13716024041</td>
            <td>身份证号</td>
            <td>214423452232561</td>
            <td rowspan="5">22</td>
            <td rowspan="5">22</td>
            <td rowspan="5">22</td>
          </tr>
          <tr>
            <td>性别</td>
            <td>男</td>
            <td>生日</td>
            <td>1987.03.21</td>
            <td>婚姻状况</td>
            <td>未婚</td>

          </tr>
          <tr>
            <td>教育程度</td>
            <td>小学</td>
            <td>所在行业</td>
            <td>互联网</td>
            <td>所在地</td>
            <td>朔州</td>

          </tr>
          <tr>
            <td>备注</td>
            <td colspan="5">7个工作日没审核通过，联系110</td>
          </tr>
          <tr>
            <td>图片</td>
            <td colspan="5">6</td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  data () {
    return {
      value1: '',
      activeName: ''
    }
  },
  methods: {
    // handleCurrentChange (tab) {

    // }
    handleClick (tab, event) {
      console.log(tab, event)
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
      :first-child {
        margin-right: 10px;
      }
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
      :first-child {
        margin-right: 10px;
      }
      :nth-of-type(1) {
        margin-right: 10px;
      }
      :nth-of-type(2) {
        margin: 0 10px 0 0;
      }
      /deep/ .el-input {
        width: 200px !important;
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
  tr {
    height: 35px;
    line-height: 35px;
    border: 1px solid lightgray;
  }
  td {
    padding-left: 10px;
    text-align: center;
    border: 1px solid lightgray;
  }
  .checkList {
    width: 100%;
  }
  .title {
    background-color: #eee;
  }
}
</style>
