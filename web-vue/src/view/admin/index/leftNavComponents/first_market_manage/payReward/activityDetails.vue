<template>
  <div class="actDetails">
    <!-- 筛选条件部分 -->
    <div class="mainContent">
      <div
        style="display: flex"
        class="item"
      >
        <span>领取时间</span>
        <div :style="{'display': 'flex'}">
          <el-date-picker
            v-model="value"
            type="datetime"
            size="small"
            placeholder="选择开始时间"
            style="width:150px"
          >
          </el-date-picker>
          <span class="to">至</span>
          <el-date-picker
            v-model="value1"
            type="datetime"
            size="small"
            placeholder="选择结束时间"
            style="width:150px"
          >
          </el-date-picker>
        </div>
      </div>
      <div class="item">
        <span>手机号</span>
        <el-input
          v-model="input11"
          style="width:150px"
          size="small"
          placeholder="请输入手机号"
        ></el-input>
      </div>
      <div class="item">
        <span>用户昵称</span>
        <el-input
          style="width:150px"
          size="small"
          placeholder="请输入用户昵称"
        ></el-input>
      </div>
      <div class="item">
        <span>活动奖励</span>
        <el-select
          v-model="value3"
          placeholder="请选择"
          size="small"
          style="width:150px"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </div>
      <div class="item">
        <el-button
          size="small"
          type="primary"
        >查询</el-button>
      </div>
    </div>

    <!-- tab表格内容部分 -->
    <div class="table_list">
      <el-table
        header-row-class-name="tableClss"
        border
        style="width: 100%"
      >
        <el-table-column
          prop=""
          label="用户ID"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="用户昵称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="手机号"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="订单号"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="活动奖励"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="领取时间"
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
      value: '',
      value1: '',
      input11: '',
      value3: '',
      options: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }]
    }
  }
}

</script>
<style lang="scss" scoped>
.actDetails {
  padding: 10px;
  min-height: 100%;
  height: 100%;
  font-size: 14px;
  .mainContent {
    background: #fff;
    padding: 25px 30px;
    display: flex;
    .item {
      margin-right: 30px;
      span {
        margin-right: 8px;
      }
    }
    :nth-of-type(1) {
      span {
        line-height: 28px;
      }
      .to {
        margin: 0 5px;
      }
    }
  }
  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    color: #000;
    padding: 8px 10px;
  }
  .table_list {
    position: relative;
    margin-top: 10px;
    background-color: #fff;
    padding: 10px 20px 20px;
  }
}
</style>
