<template>
  <div class="content">
    <div class="main">
      <el-form
        :inline="true"
        label-width="110px"
      >
        <section style="display: flex">
          <el-form-item :label="$t('groupIntegration.mobile')">
            <el-input
              size="small"
              :placeholder="$t('groupIntegration.mobileTip')"
              v-model="queryForm.mobile"
              style="width: 150px"
            />
          </el-form-item>
          <el-form-item :label="$t('groupIntegration.username')">
            <el-input
              size="small"
              :placeholder="$t('groupIntegration.usernameTip')"
              v-model="queryForm.username"
              style="width: 150px"
            />
          </el-form-item>
          <el-form-item :label="$t('groupIntegration.joinTime')">
            <el-date-picker
              style="width: 320px"
              size="small"
              type="datetimerange"
              value-format="yyyy-MM-dd HH:mm:ss"
              v-model="timeRange"
            ></el-date-picker>
          </el-form-item>
        </section>

        <section style="display:flex;">
          <el-form-item :label="$t('groupIntegration.isGrouper')">
            <el-select
              v-model="queryForm.isGrouper"
              size="small"
              style="width: 150px"
            >
              <el-option
                :label="$t('groupIntegration.pleaseSelect')"
                value=""
              ></el-option>
              <el-option
                 :label="$t('groupIntegration.yes')"
                value="1"
              ></el-option>
              <el-option
                :label="$t('groupIntegration.no')"
                value="0"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item :label="$t('groupIntegration.inviteNum')">
            <el-input
              v-model="queryForm.inviteNum"
              size="small"
              style="width: 150px"
            ></el-input>
          </el-form-item>
          <el-form-item :label="$t('groupIntegration.minIntegration')">
            <el-col :span="10">
              <el-form-item>
                <el-input
                  v-model="queryForm.minIntegration"
                  size="small"
                  style="width: 150px"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col
              :span="1"
              style="margin: 0 5px"
            >
              <el-form-item>
                <div>{{$t('groupIntegration.to')}}</div>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item>
                <el-input
                  v-model="queryForm.maxIntegration"
                  size="small"
                  style="width: 150px"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-form-item>
        </section>

        <section style="display: flex">
          <el-form-item :label="$t('groupIntegration.isNew')">
            <el-select
              size="small"
              v-model="queryForm.isNew"
              :placeholder="$t('groupIntegration.pleaseSelect')"
              style="width: 150px"
            >
              <el-option
                :label="$t('groupIntegration.pleaseSelect')"
                value=""
              ></el-option>
              <el-option
               :label="$t('groupIntegration.yes')"
                value="1"
              ></el-option>
              <el-option
               :label="$t('groupIntegration.no')"
                value="0"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item :label="$t('groupIntegration.groupId')">
            <el-input
              v-model="queryForm.groupId"
              size="small"
              style="width: 150px"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
              style="margin-left: 30px;"
              type="primary"
              size="small"
              @click="onSubmit"
            >{{$t('groupIntegration.find')}}</el-button>
          </el-form-item>
        </section>
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
        console.log(res.content.dataList)
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
    padding: 20px 0px;
  }
}
.el-form-item {
  margin-bottom: 5px !important;
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
  padding: 10px 20px;
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
