// 瓜分积分活动--参与人数明细
<template>
  <div class="content">
    <div class="main">
      <el-form :inline="true">
        <el-form-item label="用户手机号">
          <el-input
            placeholder="请输入手机号"
            v-model="queryForm.mobile"
          />
        </el-form-item>
        <el-form-item label="用户昵称">
          <el-input
            placeholder="请输入用户昵称"
            v-model="queryForm.username"
          />
        </el-form-item>
        <el-form-item label="参团时间">
          <el-date-picker
            type="datetimerange"
            value-format="yyyy-MM-dd HH:mm:ss"
            v-model="timeRange"
            range-separator="至"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="是否团长">
          <el-select v-model="queryForm.isGrouper">
            <el-option
              label="请选择"
              value=""
            ></el-option>
            <el-option
              label="是"
              value="1"
            ></el-option>
            <el-option
              label="否"
              value="0"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="邀请用户数量">
          <el-input v-model="queryForm.inviteNum"></el-input>
        </el-form-item>
        <el-form-item label="瓜分积分">
          <el-col :span="11">
            <el-input v-model="queryForm.minIntegration"></el-input>
          </el-col>
          <el-col :span="1">至</el-col>
          <el-col :span="11">
            <el-input v-model="queryForm.maxIntegration"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="是否新用户">
          <el-select
            v-model="queryForm.isNew"
            placeholder="请选择"
          >
            <el-option
              label="请选择"
              value=""
            ></el-option>
            <el-option
              label="是"
              value="1"
            ></el-option>
            <el-option
              label="否"
              value="0"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="团ID">
          <el-input v-model="queryForm.groupId"></el-input>
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
          prop="userId"
          label="用户ID"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="username"
          label="用户昵称"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="mobile"
          label="手机号码"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="isNew"
          label="是否新用户"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="startTime"
          label="参团时间"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="groupId"
          label="团ID"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="inviteNum"
          label="邀请用户数量"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="integration"
          label="消耗积分"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="isGrouper"
          label="是否团长"
          align="center"
        ></el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="loadData"
      />
    </div>
  </div>
</template>
<script>
import { detailGroupIntegration } from '@/api/admin/marketManage/groupIntegrationList.js'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: {
    pagination
  },
  data: function () {
    return {
      timeRange: [],
      queryForm: {
        actId: null,
        mobile: null,
        username: null,
        startTime: null,
        endTime: null,
        isGrouper: null,
        inviteNum: null,
        minIntegration: null,
        maxIntegration: null,
        isNew: null,
        groupId: null
      },
      tableData: [],
      pageParams: {
      }
    }
  },
  methods: {
    loadData () {
      if (this.timeRange === null) {
        this.queryForm.startTime = ''
        this.queryForm.endTime = ''
      } else {
        this.queryForm.startTime = this.timeRange[0]
        this.queryForm.endTime = this.timeRange[1]
      }
      this.pageParams.actId = this.queryForm.actId
      this.pageParams.mobile = this.queryForm.mobile
      this.pageParams.username = this.queryForm.username
      this.pageParams.startTime = this.queryForm.startTime
      this.pageParams.endTime = this.queryForm.endTime
      this.pageParams.isGrouper = this.queryForm.isGrouper
      this.pageParams.inviteNum = this.queryForm.inviteNum
      this.pageParams.minIntegration = this.queryForm.minIntegration
      this.pageParams.maxIntegration = this.queryForm.maxIntegration
      this.pageParams.isNew = this.queryForm.isNew
      this.pageParams.groupId = this.queryForm.groupId
      detailGroupIntegration(this.pageParams).then(res => {
        console.log(res)
        this.handData(res.content.dataList)
        this.pageParams = res.content.page
      })
    },
    onSubmit () {
      this.pageParams.currentPage = 1
      this.loadData()
    },
    handData (data) {
      data.map((item, index) => {
        if (item.isNew === 1) {
          item.isNew = '是'
        } else {
          item.isNew = '否'
        }
        if (item.isGrouper === 1) {
          item.isGrouper = '是'
        } else {
          item.isGrouper = '否'
        }
      })
      this.tableData = data
    }
  },
  mounted () {
    this.queryForm.actId = this.$route.params.id
    this.loadData()
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
.paginationfooter {
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
