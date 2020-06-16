<template>
  <div>
    <div class="search_box">
      <div class="filters">
        <div class="filters_item">
          <span>{{$t('couponPackageOrder.couponPackage')}}：</span>
          <el-input
            v-model="searchParams.packName"
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
          <span>{{$t('orderCommon.orderUserInfo')}}：</span>
          <el-input
            v-model="searchParams.userInfo"
            :placeholder="$t('orderCommon.orderUserInfoPlaceholder')"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>支付方式：</span>
          <el-select
            v-model="searchParams.payCode"
            :placeholder="$t('orderCommon.selectPlaceholder')"
            size="small"
            class="default_input"
          >
            <el-option
              label="全部"
              :value="null"
            ></el-option>
            <el-option
              label="微信支付"
              value="wxpay"
            ></el-option>
            <el-option
              label="余额支付"
              value="balance"
            ></el-option>
            <el-option
              label="积分支付"
              value="score"
            ></el-option>
          </el-select>
        </div>
        <div
          class="filters_item"
          style="max-width: 500px;"
        >
          <span>{{$t('orderCommon.orderTime')}}：</span>
          <el-date-picker
            v-model="applicationTime0"
            type="date"
            size="small"
            value-format="yyyy-MM-dd 00:00:00"
            :placeholder="$t('actionRecord.startTime')"
          >
          </el-date-picker>
          &nbsp;至&nbsp;
          <el-date-picker
            v-model="applicationTime1"
            type="date"
            size="small"
            value-format="yyyy-MM-dd 23:59:59"
            :placeholder="$t('actionRecord.endTime')"
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
            @click="exportDataList"
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
            'border':'none',
            'font-weight': 'bold',
            'color': '#000'
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
    <!-- 导出数据确认弹窗 -->
    <exportForm
      :show.sync="showExportConfirm"
      :param="this.searchParams"
    />
  </div>
</template>

<script>
import { getCouponPackageOrderList } from '@/api/admin/orderManage/virtualGoodsOrder.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination'),
    ManualRefund: () => import('./refundDialog'),
    exportForm: () => import('./couponPackageOrderExportDialog.vue')
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
      applicationTime0: '',
      applicationTime1: '',
      showExportConfirm: false, // 是否展示导出数据弹窗

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
      this.searchParams.startTime = this.applicationTime0
      this.searchParams.endTime = this.applicationTime1
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
      if (orderInfo.returnFlag === 0 || orderInfo.returnFlag === 1) {
        let statusStr = `<div>${this.$t('orderCommon.orderFinished')}<br/>`
        if ((orderInfo.useAccount > 0 || orderInfo.memberCardBalance > 0 || orderInfo.moneyPaid > 0 || orderInfo.useScore > 0 || orderInfo.surplusAmount > 0) && orderInfo.canReturn === 1) statusStr += `<a class="refund">${this.$t('orderCommon.manualRefund')}</a>`
        statusStr += `</div>`
        return statusStr
      } else if (orderInfo.returnFlag === 3) {
        let statusStr = `<div>${this.$t('couponPackageOrder.partialRefund')}<br/> <a class="view">${this.$t('orderCommon.checkRefund')}</a></div>`
        if ((orderInfo.useAccount > 0 || orderInfo.memberCardBalance > 0 || orderInfo.moneyPaid > 0 || orderInfo.useScore > 0 || orderInfo.surplusAmount > 0) && orderInfo.canReturn === 1) statusStr += `<div><a class="refund">${this.$t('orderCommon.manualRefund')}</a></div>`
        return statusStr
      } else if (orderInfo.returnFlag === 2) {
        return `<div>${this.$t('orderCommon.refundCompleted')}<br/> <a class="view">${this.$t('orderCommon.checkRefund')}</a></div>`
      }
    },
    processRefunds (orderSn, event) {
      this.refundInfo = this.originalData.find(item => {
        return item.orderSn === orderSn
      })
      console.log(this.refundInfo)
      this.$set(this.refundInfo, 'viewOrderType', 'couponPackage')
      if (event.target.className === 'view') {
        this.$set(this.refundInfo, 'action', 'view')
        this.showRefund = true
      } else if (event.target.className === 'refund') {
        this.$set(this.refundInfo, 'action', 'refund')
        this.showRefund = true
      }
    },
    exportDataList () {
      this.searchParams.startTime = this.applicationTime0
      this.searchParams.endTime = this.applicationTime1
      this.showExportConfirm = true
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
.search_box {
  background-color: #fff;
  .filters {
    display: flex;
    line-height: 32px;
    flex-wrap: wrap;
    margin-left: -15px;
    // max-width: 1226px;
    .filters_item {
      display: flex;
      max-width: 480px;
      min-width: 280px;
      margin-left: 15px;
      margin-bottom: 10px;
      > span {
        min-width: 100px;
        font-size: 14px;
        text-align: right;
      }
    }
  }
  .default_input {
    width: 180px;
  }
}
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
