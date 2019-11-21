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
            prop="userID"
            align="center"
            label="会员ID"
            width="120"
          >
          </el-table-column>
          <el-table-column
            label="昵称"
            align="center"
          >
            <template slot-scope="scope">
              <span
                @click="handleToUserDetail(scope.row)"
                style="cursor:pointer;color:#5a8bff"
              >
                {{scope.row.sickName}}
              </span>
            </template>
          </el-table-column>
          <el-table-column
            prop="phoneNum"
            label="手机号"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="inviter"
            label="邀请人"
            width="120"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="date"
            label="领卡时间"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="cardNum"
            label="会员卡号"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="status"
            label="卡状态"
            width="120"
            align="center"
          >
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
          >
            <template slot-scope="scope">
              <div class="operation">
                <span
                  v-for="(item,index) in operation"
                  :key="index"
                  @click="handleToOperation(scope.row,index)"
                >{{item}}</span>
              </div>
            </template>
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
export default {
  components: { Pagination: () => import('@/components/admin/pagination/pagination') },
  data () {
    return {
      MemberInforInput: '',
      date: '',
      labelInput: '',
      tableData: [
        {
          userID: '51',
          phoneNum: '18811309193',
          sickName: '啦啦啦',
          inviter: '帅飞',
          date: '20190828 14:40:44',
          cardNum: '2342342334235',
          status: '正常'
        },
        {
          userID: '12',
          sickName: '啦啦啦',
          phoneNum: '18811309193',
          inviter: '帅飞',
          date: '20190828 14:40:44',
          cardNum: '2342342334235',
          status: '正常'
        },
        {
          userID: '43',
          sickName: '啦啦啦',
          phoneNum: '18811309193',
          inviter: '帅飞',
          date: '20190828 14:40:44',
          cardNum: '2342342334235',
          status: '正常'
        }
      ],
      pageParams: {
        totalRows: 10,
        currentPage: 1,
        pageRows: 10
      }
    }
  },
  methods: {
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
