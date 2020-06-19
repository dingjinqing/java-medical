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
      >
        <template slot-scope="scope">
          <span
            @click="handleToClickUser(scope.row)"
            class="userName"
          >{{scope.row.username}}</span>
        </template>
      </el-table-column>
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
      >
        <template slot-scope="scope">
          {{scope.row.renewMoney}}{{scope.row.renewType===0?'元':scope.row.renewType===1?'积分':''}}
        </template>
      </el-table-column>
      <el-table-column
        prop="renewTime"
        label="续费时长"
        align="center"
      >
        <template slot-scope="scope">
          {{scope.row.renewTime}}{{scope.row.renewDateType===0?'日':scope.row.renewDateType===1?'周':'月'}}
        </template>
      </el-table-column>
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
      v-loading="loading"
    >

      <el-table-column
        prop="orderSn"
        label="充值单号"
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
      >
        <template slot-scope="scope">
          <span
            @click="handleToClickUser(scope.row)"
            class="userName"
          >{{scope.row.username}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="mobile"
        label="手机号"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="createTime"
        label="充值时间"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="charge"
        label="充值金额(元)"
        align="center"
      >
        <template slot-scope="scope">
          {{scope.row.charge}}
        </template>
      </el-table-column>
      <el-table-column
        prop="afterChargeMoney"
        label="当次充值后卡余额(元)"
        align="center"
        width="160"
      >
        <template slot-scope="scope">
          {{scope.row.afterChargeMoney}}
        </template>
      </el-table-column>
      <el-table-column
        prop="reason"
        label="备注"
        align="center"
        width="160"
      ></el-table-column>
    </el-table>
    <div
      class="pagination"
      v-if="pageParams.totalRows>0"
    >
      <div>当前页面{{pageParams.currentPage}}/{{pageParams.pageCount}} 共 {{pageParams.totalRows}}条</div>
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
import { getRenewalDetails, memberpCarRecharge } from '@/api/admin/memberManage/memberValueAdd/memberValueAdd.js'
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
    },
    userId: {
      Type: Number
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
      deep: true,
      immediate: true
    },
    userId () {
      this.handleToQueryData()
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
            pageRows: 20,
            userId: this.userId
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
        case 'third':
          let params2 = {
            orderSn: this.propsData.rechargeNo,
            userInfo: this.propsData.memberInfo,
            cardName: this.propsData.cardName,
            cardId: this.propsData.cardId,
            createTimeMin: this.propsData.rechargeStartTime,
            createTimeMax: this.propsData.rechargeEndTime,
            chargeMin: this.propsData.rechargeAmountmin,
            chargeMax: this.propsData.rechargeAmountmax,
            changeType: this.propsData.rechargeMethod,
            afterChargeMoneyMin: this.propsData.afterRechargeStartTime,
            afterChargeMoneyMax: this.propsData.afterRechargeEndTime,
            currentPage: this.pageParams.currentPage,
            pageRows: 20,
            userId: this.userId
          }

          memberpCarRecharge(params2).then(res => {
            console.log(res)
            this.tableData1 = []
            if (res.error === 0) {
              this.tableData1 = []
              this.tableData1.push(...res.content.dataList)
              console.log(this.tableData1, this.tableData1.length)
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
    },
    handleToClickUser (row) {
      console.log(row)
      this.$router.push({
        path: '/admin/home/main/membershipInformation',
        query: {
          userId: row.userId
        }
      })
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
  .userName {
    color: #5a8bff;
    cursor: pointer;
  }
}
</style>
