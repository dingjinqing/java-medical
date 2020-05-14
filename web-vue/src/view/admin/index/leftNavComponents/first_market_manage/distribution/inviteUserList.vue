<template>
  <div class="content">
    <div class="main">
      <el-form
        :model="searchParam"
        label-width="100px"
        :label-position="right"
        :inline="true"
      >
        <el-form-item label="手机号：">
          <el-input
            v-model="searchParam.mobile"
            size="small"
            class="inputWidth"
            placeholder="请输入内容"
          ></el-input>
        </el-form-item>
        <el-form-item label="微信昵称：">
          <el-input
            v-model="searchParam.username"
            size="small"
            class="inputWidth"
            placeholder="请输入内容"
          ></el-input>
        </el-form-item>
        <el-form-item label="真实姓名：">
          <el-input
            v-model="searchParam.realName"
            size="small"
            class="inputWidth"
            placeholder="请输入内容"
          ></el-input>
        </el-form-item>
        <el-form-item label="注册时间：">
          <el-date-picker
            v-model="searchParam.startCreateTime"
            type="datetime"
            size="small"
            align="right"
            class="selectWidth"
            placeholder="选择日期时间"
          ></el-date-picker>
          至
          <el-date-picker
            v-model="searchParam.endCreateTime"
            type="datetime"
            size="small"
            align="right"
            class="selectWidth"
            default-time="23:59:59"
            placeholder="选择日期时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="邀请时间：">
          <el-date-picker
            v-model="searchParam.startInviteTime"
            type="datetime"
            size="small"
            align="right"
            class="selectWidth"
            placeholder="选择日期时间"
          ></el-date-picker>
          至
          <el-date-picker
            v-model="searchParam.endInviteTime"
            type="datetime"
            size="small"
            align="right"
            class="selectWidth"
            default-time="23:59:59"
            placeholder="选择日期时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button
            @click="inviteList"
            type="primary"
            size="small"
          >筛选</el-button>
          <el-button size="small">导出</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="main list_content">
      <div class="title_content">
        累计获得佣金数：<span style="color: red;">{{totalGetFanliMoney}}</span>
      </div>
    </div>

    <div class="main list_content">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          label="用户昵称"
          align="center"
        >
          <template slot-scope="scope">
            <span
              class="nameStyle"
              @click="userNameHandler(scope.row.userId)"
            >{{scope.row.username}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="mobile"
          label="用户手机号"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop=""
          label="真实姓名"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="注册时间"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="orderNumber"
          label="累计返利订单数"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="totalCanFanliMoney"
          label="累计订单返利商品总金额"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="totalFanliMoney"
          label="累计返利佣金金额"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="inviteTime"
          label="邀请时间"
          align="center"
        >
        </el-table-column>
        <el-table-column
          label="返利有效日期"
          align="center"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.inviteExpiryDate ? scope.row.inviteExpiryDate : '永久'}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="inviteProtectDate"
          label="邀请保护日期"
          align="center"
        >
        </el-table-column>
      </el-table>
    </div>
    <pagination
      :page-params.sync="pageParams"
      @pagination="inviteList"
    />
  </div>
</template>
<script>
import { inviteUserList } from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      // 搜索
      searchParam: {
        mobile: '',
        username: '',
        realName: '',
        startCreateTime: '',
        endCreateTime: '',
        startInviteTime: '',
        endInviteTime: ''
      },
      totalGetFanliMoney: 0, // 累计获得佣金数
      requestParam: {},
      tableData: [], // 表格
      pageParams: {}, // 分页
      userId: '' // 用户id
    }
  },
  mounted () {
    if (this.$route.query.userId > 0) {
      this.userId = this.$route.query.userId
      this.inviteList()
    }
  },
  methods: {
    // 邀请用户列表
    inviteList () {
      this.requestParam = this.searchParam
      this.requestParam.userId = this.userId
      // this.requestParams.currentPage = this.pageParams.currentPage
      // this.requestParams.pageRows = this.pageParams.pageRows
      inviteUserList(this.requestParam).then(res => {
        if (res.error === 0) {
          this.totalGetFanliMoney = res.content.totalGetFanliMoney
          this.tableData = res.content.inviteUserInfo.dataList
          this.pageParams = res.content.inviteUserInfo.page
        }
      })
    },

    // 用户昵称跳转
    userNameHandler (id) {
      this.$router.push({
        path: '/admin/home/main/membershipInformation',
        query: {
          userId: id
        }
      })
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
.title_content {
  height: 30px;
  line-height: 30px;
  text-align: center;
}
.list_content {
  margin-top: 10px;
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.inputWidth {
  width: 170px;
}
.selectWidth {
  width: 200px;
}
.nameStyle {
  color: #5a8bff;
  cursor: pointer;
}
</style>
