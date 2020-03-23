<template>
  <div class="viewSigninMembers">
    <div class="viewSigninMembersMain">
      <div class="top">
        <div>
          <span>会员信息：</span>
          <el-input
            v-model="MemberInforInput"
            style="width:170px;"
            placeholder="请输入会员昵称/手机号"
            size="small"
          ></el-input>
        </div>
        <div>
          <span>签到时间：</span>
          <el-date-picker
            size="small"
            v-model="date"
            type="daterange"
            range-separator="至"
            start-placeholder="签到时间"
            end-placeholder="签到时间"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </div>
      </div>
      <div
        class="top"
        style="margin-top:15px"
      >
        <div>
          <span>标签：</span>
          <el-input
            v-model="labelInput"
            placeholder="请输入会员昵称/手机号"
            size="small"
            style="width:170px;"
          ></el-input>
        </div>
        <div class="btn">
          <el-button
            type="primary"
            size="small"
          >筛选</el-button>
        </div>
      </div>
    </div>
    <div
      class="viewSigninMembersMain"
      style="margin-top:10px"
    >
      <div class="tableMain">
        <el-table
          class="version-manage-table"
          :data="tableData"
          header-row-class-name="tableClss"
          border
          style="width: 100%"
        >
          <el-table-column
            align="center"
            label="会员昵称"
            width="200px"
          >
          <template slot-scope="scope">
              <span
                @click="handleToUserDetail(scope.row)"
                style="cursor:pointer;color:#5a8bff"
              >
                {{scope.row.username}}
              </span>
            </template>
          </el-table-column>
          <el-table-column
            prop="mobile"
            label="手机号"
            align="center"
            width="200px"
          >

          </el-table-column>
          <el-table-column
            prop="userShowTag"
            align="center"
            width="300"
            icon="el-icon-delete"
          >
          <template slot="header">
            会员标签
          </template>
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="签到时间"
            align="center"
            width="250"
          >
          </el-table-column>
          <el-table-column
            prop="usableScore"
            label="获得积分数"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="continueDays"
            label="连续签到天数"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="totalScore"
            label="本次签到累计获得积分数"
            align="center"
          >
          </el-table-column>
        </el-table>
        <Pagination
          :page-params.sync="pageParams"
          @pagination="search"
        />
      </div>
    </div>
  </div>
</template>
<script>
import { userScoreSign } from '@/api/admin/memberManage/scoreManage/scoreCfg.js'
export default {
  components: { Pagination: () => import('@/components/admin/pagination/pagination') },
  mounted () {
    this.initTableData()
  },
  data () {
    return {
      MemberInforInput: '',
      date: '',
      labelInput: '',
      tableData: [],
      pageParams: {
        totalRows: 10,
        currentPage: 1,
        pageRows: 10
      }
    }
  },
  methods: {
    // 初始化数据
    initTableData () {
      this.loadData({})
    },
    loadData (obj) {
      userScoreSign(obj).then(res => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
        }
      })
    },
    search (data) {
      console.log(data)
    },
    // 跳转会员信息编辑页面
    handleToUserDetail (row) {
      this.$router.push({
        name: 'membershipInformation'
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.viewSigninMembers {
  padding: 10px;
  padding-bottom: 68px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .viewSigninMembersMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px;
    .top {
      display: flex;
      div {
        display: flex;
        align-items: center;
        span {
          display: block;
          width: 100px;
          text-align: right;
          margin-right: 20px;
        }
      }
      .btn {
        margin-left: 44px;
        /deep/ .el-button {
          width: 85px;
        }
      }
    }
    .tableMain {
      position: relative;
      background-color: #fff;
      overflow: hidden;
      overflow-y: auto;
      margin-top: 10px;
      /deep/ .tableClss th {
        background-color: #f5f5f5;
        border: none;
        height: 36px;
        font-weight: bold;
        color: #000;
        padding: 8px 10px;
        .el-checkbox {
          margin-left: -4px;
        }
      }
      .operation {
        display: flex;
        justify-content: space-around;
        span {
          cursor: pointer;
          color: #5a8bff;
        }
      }
    }
  }
}
</style>
