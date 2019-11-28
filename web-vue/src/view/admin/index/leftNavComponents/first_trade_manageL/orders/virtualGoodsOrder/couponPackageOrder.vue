<template>
  <div>
    <div class="search_box">
      <div class="filters">
        <div class="filters_item">
          <span>{{$t('orderCommon.orderUserInfo')}}：</span>
          <el-input
            v-model="searchParams.userInfo"
            :placeholder="$t('orderCommon.orderUserInfoPlaceholder')"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>{{$t('orderCommon.orderSn')}}：</span>
          <el-input
            v-model="searchParams.orderSn"
            :placeholder="$t('orderCommon.orderSnPlaceholder')"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>{{$t('couponPackageOrder.couponPackage')}}：</span>
          <el-input
            v-model="searchParams.packName"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>{{$t('orderCommon.orderTime')}}：</span>
          <el-date-picker
            v-model="applicationTime"
            type="datetimerange"
            :range-separator="$t('orderCommon.to')"
            value-format="yyyy-MM-dd HH:mm:ss"
            :start-placeholder="$t('orderCommon.startTime')"
            :end-placeholder="$t('orderCommon.endTime')"
            size="small"
          >
          </el-date-picker>
        </div>
        <div class="filters_item">
          <el-button
            @click="initDataList"
            type="primary"
            size="small"
          >{{$t('orderCommon.filter')}}</el-button>
          <el-button
            type="default"
            size="small"
          >{{$t('orderCommon.exportTable')}}</el-button>
        </div>
      </div>
    </div>
    <el-tabs
      v-model="searchParams.refundType"
      @tab-click="handleClick"
    >
      <el-tab-pane
        :label="$t('orderCommon.all')"
        name="0"
      >
      </el-tab-pane>
      <el-tab-pane
        :label="$t('orderCommon.refundOrder')"
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
          :label="$t('couponPackageOrder.couponPackage')"
        ></el-table-column>
        <el-table-column
          prop="orderSn"
          :label="$t('orderCommon.orderSn')"
          width="200"
        ></el-table-column>
        <el-table-column
          prop="price"
          :label="$t('orderCommon.price')"
        ></el-table-column>
        <el-table-column :label="$t('orderCommon.orderUserInfo')">
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
          :label="$t('orderCommon.orderTime')"
        ></el-table-column>
        <el-table-column :label="$t('orderCommon.orderStatus')">
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
          :label="$t('orderCommon.moneyPaid')"
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
    this.langDefault()
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
        // 优惠券礼包的价格可以更改，所以单个订单价格以实际支付金额为准
        if (item.useScore > 0) {
          item.price = item.useScore + this.$t('orderCommon.integral')
        } else {
          item.price = item.orderAmount + this.currencyPool[item.currency][this.lang][0]
        }

        item.moneyPaid += this.currencyPool[item.currency][this.lang][0]
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
        return `<div>${this.$t('orderCommon.orderFinished')}<br/><a class="refund" >${this.$t('orderCommon.manualRefund')}</a></div>`
      } else if (orderInfo.returnFlag === 0) {
        return `<div>${this.$t('orderCommon.orderFinished')}<div/>`
      } else if (orderInfo.returnFlag === 1 && (orderInfo.moneyPaid + orderInfo.useAccount + orderInfo.useScore > orderInfo.returnScore + orderInfo.returnAccount + orderInfo.returnMoney)) {
        return `<div><a class="refund">${this.$t('orderCommon.manualRefund')}</a><br/><a class="view">${this.$t('orderCommon.checkRefund')}</a></div>`
      } else if (orderInfo.returnFlag === 1) {
        return `<div>${this.$t('orderCommon.refundFailed')}</div>`
      } else {
        return `<div>${this.$t('orderCommon.refundCompleted')}<br/> <a class="view">${this.$t('orderCommon.checkRefund')}</a></div>`
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
