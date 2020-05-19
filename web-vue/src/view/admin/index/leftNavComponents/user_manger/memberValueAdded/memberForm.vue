<template>
  <div class="memberForm">
    <el-table
      ref="tableData1"
      :data="tableData1"
      border
      highlight-current-row
      @current-change="handleCurrentChange"
      style="width: 100%;"
      header-row-class-name="tableClss"
      v-if="activeName==='second'"
      v-loading="loading"
    >
      <el-table-column
        prop="renewOrderSn"
        label="续费单号"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="cardName"
        label="会员卡"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="cardId"
        label="卡ID"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="username"
        label="会员昵称"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="mobile"
        label="手机号"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="addTime"
        label="续费时间"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="renewMoney"
        label="续费金额"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="renewTime"
        label="续费时长"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="renewExpireTime"
        label="当次续费后有效期"
        align="center"
        width="160"
      ></el-table-column>
    </el-table>
    <el-table
      ref="tableData1"
      :data="tableData1"
      border
      highlight-current-row
      @current-change="handleCurrentChange"
      style="width: 100%;"
      header-row-class-name="tableClss"
      v-if="activeName==='third'"
    >

      <el-table-column
        prop="pageName"
        label="充值单号"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="createTime"
        label="会员卡"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="typeText"
        label="卡ID"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="typeText"
        label="会员昵称"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="typeText"
        label="手机号"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="typeText"
        label="充值时间"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="typeText"
        label="充值金额(元)"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="typeText"
        label="当次充值后卡余额(元)"
        align="center"
        width="160"
      ></el-table-column>
      <el-table-column
        prop="type"
        label="备注"
        align="center"
        width="160"
      ></el-table-column>
    </el-table>
    <div
      class="pagination"
      v-if="pageParams.totalRows>0"
    >
      <div>{{$t('programVersion.currentPage')}}：{{pageParams.currentPage}}，{{$t('programVersion.totalPage')}}：{{pageParams.pageCount}}，{{$t('programVersion.totalRecord')}}：{{pageParams.totalRows}}</div>
      <el-pagination
        @current-change="handleToQueryData"
        :current-page.sync="pageParams.currentPage"
        :page-size="20"
        layout="prev, pager, next, jumper"
        :total="pageParams.totalRows"
      >
      </el-pagination>
    </div>
  </div>
</template>
<script>
import { getRenewalDetails } from '@/api/admin/memberManage/memberValueAdd/memberValueAdd.js'
export default {
  components: {
    Pagination: () => ('@/components/admin/pagination/pagination') // 分页组件
  },
  props: {
    activeName: {
      Type: String,
      default: ''
    },
    bottomFormData: {
      Type: Object,
      default: {}
    }
  },
  data () {
    return {
      pageParams: {
        currentPage: 1
      }, // 分页
      tableData1: [], // 表格
      propsData: {},
      loading: false
    }
  },
  watch: {
    bottomFormData: {
      handler (newData) {
        this.tableData1 = []
        this.pageParams = {
          currentPage: 1
        }
        this.propsData = newData
        this.handleToQueryData()
      },
      deep: true
    }
  },
  methods: {
    // 初始请求数据
    handleToQueryData () {
      this.loading = true
      console.log(this.activeName, this.propsData)
      switch (this.activeName) {
        case 'second':
          let params = {
            renewOrderSn: this.propsData.renewalNo,
            userInfo: this.propsData.memberInfo,
            cardName: this.propsData.cardName,
            cardId: this.propsData.cardId,
            startTime: this.propsData.renewStartTime,
            endTime: this.propsData.renewEndTime,
            renewMoneyMin: this.propsData.renewalAmountmin,
            renewMoneyMax: this.propsData.renewalAmountmax,
            renewType: this.propsData.company,
            renewTimeMin: this.propsData.afterRenewalStartTime,
            renewTimeMax: this.propsData.afterRenewalEndTime,
            currentPage: this.pageParams.currentPage,
            pageRows: 20
          }
          getRenewalDetails(params).then(res => {
            console.log(res)
            if (res.error === 0) {
              this.tableData1 = res.content.dataList
              this.pageParams = res.content.page
              this.loading = false
              console.log(this.pageParams)
            }
          })
          break
      }
    },
    // 选中数据
    handleCurrentChange (row) {
      console.log(row)
    }
  }
}
</script>
<style lang="scss" scoped>
.memberForm {
  margin-top: 10px;
  padding: 8px 23px;
  background-color: #fff;
  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    font-weight: bold;
    color: #000;
    // padding: 8px 10px;
    .el-checkbox {
      margin-left: -4px;
    }
  }
  .pagination {
    background-color: #fff;
    height: 50px;
    line-height: 50px;
    color: #333;
    font-size: 14px;
    display: flex;
    justify-content: flex-end;
    padding-right: 10px;
    /deep/ .el-pagination {
      display: flex;
      align-items: center;
      .el-pager {
        display: flex;
        align-items: center;
      }
    }
  }
}
</style>
