<!--
*****
*** 打包一口价 - 参与明细页面
*****
-->
<template>
  <div class="container">
    <div class="main">
      <el-form :inline="true">
        <el-form-item label="用户昵称：">
          <el-input
            placeholder="请输入用户昵称"
            size="small"
            v-model="userName"
          ></el-input>
        </el-form-item>
        <el-form-item label="手机号：">
          <el-input
            placeholder="请输入手机号"
            size="small"
            v-model="mobile"
          ></el-input>
        </el-form-item>
        <el-form-item label="订单号：">
          <el-input
            placeholder="请输入订单号"
            size="small"
            v-model="orderSn"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            size="small"
            @click="onSubmit"
          >筛选</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="table_list">
      <el-table
        :data="dataTable"
        header-row-class-name="tableClss"
        style="100%"
        border
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
          label="手机号"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="orderSn"
          label="一口价订单号"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="createTime"
          label="下单时间"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="moneyPaid"
          label="下单金额"
          align="center"
        ></el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageInfo"
        @pagination="loadTable"
      ></pagination>
    </div>
  </div>
</template>
<script>
import pagination from '@/components/admin/pagination/pagination'
import { activityDetail } from '@/api/admin/marketManage/packagePrice.js'

export default {
  components: {
    pagination
  },
  data () {
    return {
      activityId: '',
      userName: '',
      mobile: '',
      orderSn: '',
      pageInfo: {},
      dataTable: []
    }
  },
  methods: {
    onSubmit () {
      this.pageInfo.currentPage = 1
      this.loadTable()
    },
    loadTable () {
      this.pageInfo.userName = this.userName
      this.pageInfo.mobile = this.mobile
      this.pageInfo.orderSn = this.orderSn
      this.pageInfo.activityId = this.activityId
      activityDetail(this.pageInfo).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.dataTable = res.content.dataList
          this.pageInfo = res.content.page
        }
      })
    }
  },
  mounted () {
    this.activityId = this.$route.params.id
    this.onSubmit()
  }
}
</script>
<style lang="scss" scoped>
.container {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    padding: 15px;
    background: #fff;
    /deep/ .el-form--inline .el-form-item {
      margin-left: 10px;
      margin-right: 20px;
      margin-bottom: 0;
      .el-form-item__label {
        padding: 0 5px 0 0;
      }
      .el-input {
        width: 175px;
      }
    }
  }
}
.table_list {
  position: relative;
  margin-top: 10px;
  background-color: #fff;
  padding: 15px;
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
