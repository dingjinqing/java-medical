<template>
  <div class="content">
    <div class="main">
      <el-form :inline="true">
        <el-form-item :label="$t('groupIntegration.groupId')">
          <el-input
            size="small"
            :placeholder="$t('groupIntegration.pleaseGroupId')"
            v-model="condition.groupId"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('groupIntegration.status')">
          <el-select
            v-model="condition.status"
            size="small"
          >
            <el-option
              value=""
              :label="$t('groupIntegration.pleaseSelect')"
            >
            </el-option>
            <el-option
              value="1"
              :label="$t('groupIntegration.yes')"
            >
            </el-option>
            <el-option
              value="0"
             :label="$t('groupIntegration.no')"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item  :label="$t('groupIntegration.endTime')">
          <el-date-picker
            style="width: 300px"
            size="small"
            type="datetimerange"
            value-format="yyyy-MM-dd HH:mm:ss"
            v-model="timeRange"
          ></el-date-picker>
        </el-form-item>
        <el-button
          size="small"
          type="primary"
          @click="onSubmit"
        >{{$t('groupIntegration.find')}}</el-button>
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
          :label="$t('groupIntegration.groupId')"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('groupIntegration.groupNum')"
          align="center"
        >
          <template slot-scope="scope">
            <el-link
              type="primary"
              @click="loadDailog(scope.row.groupId)"
            >{{scope.row.participantNum}}</el-link>
          </template>
        </el-table-column>
        <el-table-column
          prop="useIntegration"
          :label="$t('groupIntegration.useIntegration')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="startTime"
         :label="$t('groupIntegration.startTime')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="endTime"
         :label="$t('groupIntegration.endTime')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="grouperName"
          :label="$t('groupIntegration.grouperName')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="mobile"
          :label="$t('groupIntegration.grouperMobile')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="statusName"
         :label="$t('groupIntegration.status')"
          align="center"
        ></el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageInfo"
        @pagination="loadTable"
      />
    </div>

    <!-- 点击参团人数链接弹窗 -->
    <el-dialog
      :title="$t('groupIntegration.participantUserDetails')"
      :visible.sync="dialogVisible"
      center
      width="60%"
    >
      <div class="table_list">
        <el-table
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="dialogTableData"
          border
          style="width: 100%"
        >
          <el-table-column
            prop="userId"
            :label="$t('groupIntegration.userId')"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="username"
            :label="$t('groupIntegration.username')"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="mobile"
            :label="$t('groupIntegration.mobile')"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="isNew"
            :label="$t('groupIntegration.isNew')"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="startTime"
            :label="$t('groupIntegration.joinTime')"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="groupId"
           :label="$t('groupIntegration.groupId')"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="inviteNum"
            :label="$t('groupIntegration.inviteNum')"
            align="center"
          ></el-table-column>

          <el-table-column
            prop="integration"
           :label="$t('groupIntegration.useIntegration')"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="isGrouper"
            :label="$t('groupIntegration.isGrouper')"
            align="center"
          ></el-table-column>
        </el-table>
        <pagination
          :page-params.sync="dialogTablePageInfo"
          @pagination="getDetail"
        />
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          @click="dialogVisible = false"
        >{{$t('groupIntegration.ok')}}</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script>
import { successGroupIntegration, detailGroupIntegration } from '@/api/admin/marketManage/groupIntegrationList.js'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: {
    pagination
  },
  data: function () {
    return {
      timeRange: [],
      dialogVisible: false,
      dialogCondition: {
        actId: null,
        groupId: null
      },
      condition: {
        actId: null,
        groupId: null,
        status: null,
        startTime: null,
        endTime: null
      },
      tableData: [],
      pageInfo: {
      },
      dialogTableData: [],
      dialogTablePageInfo: {
      }
    }
  },
  methods: {
    onSubmit () {
      if (!this.timeRange) {
        this.condition.startTime = ''
        this.condition.endTime = ''
      } else {
        this.condition.startTime = this.timeRange[0]
        this.condition.endTime = this.timeRange[1]
      }
      this.pageInfo.currentPage = 1
      this.loadTable()
    },
    loadTable () {
      this.pageInfo.actId = this.condition.actId
      this.pageInfo.groupId = this.condition.groupId
      this.pageInfo.status = this.condition.status
      this.pageInfo.startTime = this.condition.startTime
      this.pageInfo.endTime = this.condition.endTime
      successGroupIntegration(this.pageInfo).then(res => {
        console.log(res)
        this.handData(res.content.dataList)
        this.pageInfo = res.content.page
      })
    },
    loadDailog (groupId) {
      this.dialogCondition.actId = this.condition.actId
      this.dialogCondition.groupId = groupId
      this.dialogTablePageInfo.currentPage = 1
      this.getDetail()
    },
    getDetail () {
      this.dialogTablePageInfo.actId = this.dialogCondition.actId
      this.dialogTablePageInfo.groupId = this.dialogCondition.groupId
      detailGroupIntegration(this.dialogTablePageInfo).then(res => {
        console.log(res)
        this.handDialog(res.content.dataList)
        this.dialogTablePageInfo = res.content.page
        this.dialogVisible = true
      })
    },
    handData (data) {
      data.map((item, index) => {
        switch (item.status) {
          case 0: item.statusName = this.$t('groupIntegration.inGroup')
            break
          case 1: item.statusName = this.$t('groupIntegration.groupSuccess')
            break
          case 2: item.statusName = this.$t('groupIntegration.groupFail')
            break
        }
      })
      this.tableData = data
    },
    handDialog (data) {
      data.map((item, index) => {
        if (item.isNew === 1) {
          item.isNew = this.$t('groupIntegration.yes')
        } else {
          item.isNew = this.$t('groupIntegration.no')
        }
        if (item.isGrouper === 1) {
          item.isGrouper = this.$t('groupIntegration.yes')
        } else {
          item.isGrouper = this.$t('groupIntegration.no')
        }
      })
      this.dialogTableData = data
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
    padding: 10px 20px 0px 20px;
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
