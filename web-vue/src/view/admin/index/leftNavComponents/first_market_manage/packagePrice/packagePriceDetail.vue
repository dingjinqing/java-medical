<!-- 打包一口价参与明细 -->
<template>
  <div>
    <wrapper>
      <el-form :inline="true">
        <el-form-item label="用户昵称">
          <el-input
            placeholder="请输入用户昵称"
            v-model="userName"
          ></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input
            placeholder="请输入手机号"
            v-model="mobile"
          ></el-input>
        </el-form-item>
        <el-form-item label="订单号">
          <el-input
            placeholder="请输入订单号"
            v-model="orderSn"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="onSubmit"
          >筛选</el-button>
        </el-form-item>
      </el-form>
    </wrapper>
    <wrapper>
      <div class="table_list">
        <el-table
          :data="dataTable"
          header-row-class-name="tableClss"
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
    </wrapper>
  </div>
</template>
<script>
import pagination from '@/components/admin/pagination/pagination'
import wrapper from '@/components/admin/wrapper/wrapper'
import { activityDetail } from '@/api/admin/marketManage/packagePrice.js'

export default {
  components: {
    pagination,
    wrapper
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
.table_list {
  position: relative;
  margin-top: 10px;
  background-color: #fff;
  padding: 10px 20px 0 20px;
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
