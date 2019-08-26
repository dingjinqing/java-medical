<template>
  <div class="content">
    <div class="main">
      <el-form :inline="true">
        <el-form-item label="团ID">
          <el-input
            placeholder="请输入团ID"
            v-model="condition.groupId"
          ></el-input>
        </el-form-item>
        <el-form-item label="成团状态">
          <el-select v-model="condition.status">
            <el-option
              value=""
              label="请选择"
            >
            </el-option>
            <el-option
              value="1"
              label="是"
            >
            </el-option>
            <el-option
              value="0"
              label="否"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker
            type="datetimerange"
            value-format="yyyy-MM-dd HH:mm:ss"
            v-model="timeRange"
          ></el-date-picker>
        </el-form-item>
        <el-button
          type="primary"
          @click="onSubmit"
        >查询</el-button>
      </el-form>
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
          prop="groupId"
          label="团ID"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="participantNum"
          label="团人数"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="useIntegration"
          label="消耗积分"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="startTime"
          label="开团时间"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="endTime"
          label="结束时间"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="grouperName"
          label="团长昵称"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="mobile"
          label="团长手机号"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="statusName"
          label="成团状态"
          align="center"
        ></el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
import { successGroupIntegration } from '@/api/admin/marketManage/groupIntegrationList.js'
export default {
  data: function () {
    return {
      timeRange: [],
      condition: {
        actId: null,
        groupId: null,
        status: null,
        startTime: null,
        endTime: null,
        currentPage: 1,
        pageRows: 20
      },
      tableData: []
    }
  },
  methods: {
    onSubmit () {
      this.condition.startTime = this.timeRange[0]
      this.condition.endTime = this.timeRange[1]
      successGroupIntegration(this.condition).then(res => {
        console.log(res)
        this.handData(res.content.dataList)
      })
    },
    handData (data) {
      data.map((item, index) => {
        switch (item.status) {
          case 0: item.statusName = '拼团中'
            break
          case 1: item.statusName = '拼团成功'
            break
          case 2: item.statusName = '拼团失败'
            break
        }
      })
      this.tableData = data
    }
  },
  mounted () {
    this.condition.actId = this.$route.params.id
    this.onSubmit()
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
  padding: 10px 20px 0 20px;
}
</style>
