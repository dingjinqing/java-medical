<template>
  <div class="content">
    <div class="main">
      <div class="liNav">
        <span>手机号：</span>
        <el-input
          v-model="mobile"
          size="small"
          class="ipt"
          placeholder="请输入内容"
        ></el-input>
        <span>微信号：</span>
        <el-input
          v-model="username"
          size="small"
          class="ipt"
          placeholder="请输入内容"
        ></el-input>
      </div>
      <div class="liNav">
        <span>注册时间：</span>
        <el-date-picker
          v-model="startCreateTime"
          type="datetime"
          size="small"
          placeholder="选择日期时间"
        >
        </el-date-picker>
        至
        <el-date-picker
          v-model="endCreateTime"
          type="datetime"
          size="small"
          placeholder="选择日期时间"
        >
        </el-date-picker>
        <el-button
          @click="inviteList"
          type="primary"
          size="small"
        >筛选</el-button>
        <el-button size="small">导出</el-button>
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
          prop="username"
          label="用户昵称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="mobile"
          label="用户手机号"
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
          prop="inviteExpiryDate"
          label="返利有效日期"
          align="center"
        >
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
// 引入分页
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination },
  data () {
    return {
      tableData: [],
      pageParams: {},
      userId: '',
      mobile: null,
      username: null,
      startCreateTime: null,
      endCreateTime: null
    }
  },
  mounted () {
    if (this.$route.query.userId > 0) {
      this.userId = this.$route.query.userId
      this.inviteList()
    }
  },
  methods: {
    inviteList () {
      this.pageParams.userId = this.userId
      this.pageParams.mobile = this.mobile
      this.pageParams.username = this.username
      this.pageParams.startCreateTime = this.startCreateTime
      this.pageParams.endCreateTime = this.endCreateTime
      inviteUserList(this.pageParams).then(res => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
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
    .liNav {
      margin-top: 5px;
      margin-bottom: 15px;
    }

    .ipt {
      width: 200px;
      margin-right: 30px;
    }
  }
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
</style>
