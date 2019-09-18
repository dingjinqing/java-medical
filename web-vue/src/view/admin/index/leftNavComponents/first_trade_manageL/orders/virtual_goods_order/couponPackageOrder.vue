<template>
  <div>
    <div class="search_box">
      <div class="filters">
        <div class="filters_item">
          <span>下单用户信息：</span>
          <el-input
            v-model="searchParams.userInfo"
            placeholder="请输入姓名/手机号"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>订单号：</span>
          <el-input
            v-model="searchParams.orderSn"
            placeholder="请输入订单号"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>优惠券包：</span>
          <el-input
            v-model="searchParams.packName"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>下单时间：</span>
          <el-date-picker
            v-model="applicationTime"
            type="datetimerange"
            range-separator="至"
            value-format="yyyy-MM-dd HH:mm:ss"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            size="small"
          >
          </el-date-picker>
        </div>
        <div class="filters_item">
          <el-button
            @click="initDataList"
            type="primary"
            size="small"
          >筛选</el-button>
          <el-button
            type="default"
            size="small"
          >导出表格</el-button>
        </div>
      </div>
    </div>
    <el-tabs
      v-model="searchParams.refundType"
      @tab-click="handleClick"
    >
      <el-tab-pane
        label="全部"
        name="0"
      >
      </el-tab-pane>
      <el-tab-pane
        label="退款订单"
        name="1"
      ></el-tab-pane>
    </el-tabs>
    <div class="table_box">
      <el-table
        v-loading="loading"
        :data="couponPackageOrderList"
        style="width:100%;"
        border
        :header-cell-style="{
            'background-color':'#f5f5f5',
            'text-align':'center',
            'border':'none'
          }"
        :cell-style="{
            'text-align':'center'
          }"
      >
        <el-table-column
          prop="packName"
          label="优惠券包"
        ></el-table-column>
        <el-table-column
          prop="orderSn"
          label="订单号"
          width="200"
        ></el-table-column>
        <el-table-column
          prop="moneyPaid"
          label="单价"
        ></el-table-column>
        <el-table-column label="下单用户信息">
          <template slot-scope="scope">
            <a
              class="user_info"
              @click="viewUserDetail(scope.row.userId)"
            >
              {{scope.row.username}}
              <br />
              {{scope.row.mobile}}
            </a>
          </template>
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="下单时间"
        ></el-table-column>
        <el-table-column label="订单状态">
          <template slot-scope="scope">
            <div
              v-html="returnFlagType(scope.row.orderSn)"
              class="refund_status"
              @click="processRefunds(scope.row.orderSn,$event)"
            >
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="moneyPaid"
          label="支付金额"
        ></el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />
    </div>
    <ManualRefund
      :dataInfo="refundInfo"
      :show.sync="showRefund"
    />
  </div>
</template>

<script>
import { getCouponPackageOrderList } from '@/api/admin/orderManage/virtualGoodsOrder.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination'),
    ManualRefund: () => import('./refundDialog')
  },
  data () {
    return {
      loading: false,
      pageParams: {},
      searchParams: {},
      couponPackageOrderList: [],
      showRefund: false,
      refundInfo: null,
      applicationTime: '',

      // 原始表格数据
      originalData: []
    }
  },
  mounted () {
    this.initDataList()
  },
  methods: {
    handleClick (el) {
      if (el.name === '0') {
        this.searchParams.refund = false
      } else {
        this.searchParams.refund = true
      }
      this.initDataList()
    },
    initDataList () {
      this.loading = true
      this.searchParams.startTime = this.applicationTime[0]
      this.searchParams.endTime = this.applicationTime[1]
      this.searchParams.currentPage = this.pageParams.currentPage
      this.searchParams.pageRows = this.pageParams.pageRows
      getCouponPackageOrderList(this.searchParams).then((res) => {
        if (res.error === 0) {
          this.originalData = res.content.dataList
          let originalData = JSON.parse(JSON.stringify(this.originalData))
          this.handleData(originalData)
          this.pageParams = res.content.page
          this.loading = false
        }
      })
    },
    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        // this.$set(this.memberCardOrderList, index, item)
      })
      this.couponPackageOrderList = data
    },
    viewUserDetail (userId) {
      this.$router.push({
        name: 'membershipInformation',
        query: {
          userId: userId
        }
      })
    },
    returnFlagType (orderSn) {
      let orderInfo = this.couponPackageOrderList.find(item => {
        return item.orderSn === orderSn
      })
      if (orderInfo.returnFlag === 0 && (orderInfo.moneyPaid + orderInfo.useAccount + orderInfo.useScore > 0)) {
        return `<div>订单完成<br/><a class="refund" >手动退款</a></div>`
      } else if (orderInfo.returnFlag === 0) {
        return `<div>订单完成<div/>`
      } else if (orderInfo.returnFlag === 1 && (orderInfo.moneyPaid + orderInfo.useAccount + orderInfo.useScore > orderInfo.returnScore + orderInfo.returnAccount + orderInfo.returnMoney)) {
        return `<div>部分退款<br/><a class="refund">手动退款</a><br/><a class="view">查看退款</a></div>`
      } else if (orderInfo.returnFlag === 1) {
        return `<div>退款完成<br/> <a class="view">查看退款</a></div>`
      } else {
        return `<div>退款失败</div>`
      }
    },
    processRefunds (orderSn, event) {
      this.refundInfo = this.couponPackageOrderList.find(item => {
        return item.orderSn === orderSn
      })
      this.$set(this.refundInfo, 'viewOrderType', 'couponPackage')
      if (event.target.className === 'view') {
        this.$set(this.refundInfo, 'action', 'view')
        this.showRefund = true
      } else if (event.target.className === 'refund') {
        this.$set(this.refundInfo, 'action', 'refund')
        this.showRefund = true
      }
    }
  },
  filters: {
    returnFlagType (data) {
      console.log(data)
    }
  },
  watch: {
    showRefund (n, o) {
      if (n === false) {
        this.initDataList()
      }
    }
  }

}
</script>

<style lang="scss" scoped>
.user_info {
  color: #409eff;
  cursor: pointer;
}
/deep/ .refund_status {
  .view,
  .refund {
    color: #409eff;
    cursor: pointer;
  }
}
</style>
