<template>
  <div class="content">
    <div class="main">
      <div class="filters">
        <div class="filters_item">
          <span>{{$t('marketCommon.orderSn')}}：</span>
          <el-input
            v-model="pageParams.orderSn"
            :placeholder="$t('marketCommon.orderSnPlaceholder')"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>{{$t('marketCommon.userInfo')}}：</span>
          <el-input
            v-model="pageParams.userInfo"
            :placeholder="$t('marketCommon.userInfoPlaceholder')"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>{{$t('marketCommon.orderTime')}}</span>
          <el-date-picker
              v-model="pageParams.startTime"
              type="datetime"
              :placeholder="$t('membershipIntroduction.Starttime')"
              value-format="yyyy-MM-dd HH:mm:ss"
              class="time_input"
              @change="datePickerChange(true,pageParams)"
              size="small"
            />
            至
           <el-date-picker
              v-model="pageParams.endTime"
              type="datetime"
              :placeholder="$t('membershipIntroduction.Endtime')"
              value-format="yyyy-MM-dd HH:mm:ss"
              @change="datePickerChange(false,pageParams)"
              :picker-options="endTime"
              default-time="23:59:59"
              class="time_input"
              size="small"
            />
        </div>
        <div class="filters_item">
          <el-button
            @click="initDataList"
            type="primary"
            size="small"
          >{{$t('marketCommon.filter')}}</el-button>
          <el-button
            @click="exportDataList"
            type="primary"
            size="small"
          >{{$t('marketCommon.export')}}</el-button>
        </div>
      </div>
      <div class="table_box">
        <el-table
          v-loading="loading"
          :data="tableData"
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
            prop="orderSn"
            :label="$t('marketCommon.orderSn')"
          ></el-table-column>
          <el-table-column
            prop="realPay"
            :label="$t('marketCommon.price')"
          ></el-table-column>
          <el-table-column
            prop="username"
            :label="$t('marketCommon.userInfo')"
          ></el-table-column>
          <el-table-column
            prop="createTime"
            :label="$t('marketCommon.orderTime')"
          ></el-table-column>
          <el-table-column
            prop="orderStatusFormat"
            :label="$t('marketCommon.orderStatus')"
          ></el-table-column>
          <el-table-column
            prop="moneyPaid"
            :label="$t('marketCommon.moneyPaid')"
          ></el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { download } from '@/util/excelUtil.js'
import { getCouponPackOrderPageList, exportCouponPackOrderList } from '@/api/admin/marketManage/couponPackage.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      actId: null,
      pageParams: {
        id: '',
        orderSn: '',
        userInfo: '',
        startTime: null,
        endTime: null
      },
      effectiveDate: '',
      tableData: [],
      loading: false,
      endTime: {
        disabledDate: time => {
          return time.getTime() < new Date(this.pageParams.startTime).getTime()
        }
      },

      // 表格原始数据
      originalData: []
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.pageParams.id = this.actId
      getCouponPackOrderPageList(this.pageParams).then((res) => {
        if (res.error === 0) {
          this.originalData = res.content.dataList
          let originalData = JSON.parse(JSON.stringify(this.originalData))
          this.handleData(originalData)
          this.pageParams = {
            ...this.pageParams,
            ...res.content.page
          }
          this.loading = false
        }
      })
    },
    // 列表导出
    exportDataList () {
      this.loading = true
      this.pageParams.id = this.actId
      exportCouponPackOrderList(this.pageParams).then((res) => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName.split(';')[1].split('=')[1]
        download(res, fileName)
        this.loading = false
      })
    },
    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        item.orderStatusFormat = item.orderStatus === 1 ? this.$t('couponPackage.orderStatusFinished') : this.$t('couponPackage.orderStatusWaitPay')
        item.realPay = this.getRealPrice(item.moneyPaid, item.useAccount, item.useScore, item.memberCardBalance)
      })
      this.tableData = data
    },
    getRealPrice (moneyPaid, useAccount, useScore, memberCardBalance) {
      let payStr = ''
      if (useScore > 0) {
        payStr += `${useScore}积分`
      } else {
        let price = 0.0
        if (moneyPaid > 0) {
          price += moneyPaid
        };
        if (useAccount > 0) {
          price += useAccount
        };
        if (memberCardBalance > 0) {
          price += memberCardBalance
        };
        payStr += `${price}元`
      }

      return payStr
    },
    /* 验证输入的时间范围是否合法 */
    datePickerChange (isStart, target) {
      console.log(target)
      if (target.startTime === null || target.endTime === null) {
        return
      }
      if (new Date(target.startTime).getTime() <= new Date(target.endTime).getTime()) {
        return
      }
      if (isStart) {
        target.startTime = null
      } else {
        target.endTime = null
      }
    }
  },
  watch: {
    // data内变量国际化
    lang () {
      // 重新渲染表格数据
      let originalData = JSON.parse(JSON.stringify(this.originalData))
      this.handleData(originalData)
    }
  },
  mounted () {
    this.langDefault()
    if (this.$route.query.id > 0) {
      this.actId = this.$route.query.id
    }
    this.initDataList()
  }
}
</script>

<style lang="scss" scoped>
.content {
  padding: 10px;
  .main {
    .filters {
      display: flex;
      line-height: 32px;
      margin-bottom: 10px;
      background-color: #fff;
      padding: 10px 10px 0 0;
      flex-wrap: wrap;
      .filters_item {
        display: flex;
        max-width: 480px;
        margin-left: 15px;
        margin-bottom: 10px;
        > span {
          min-width: 80px;
          font-size: 14px;
        }
      }
    }
    .navBox {
      background-color: #fff;
      padding: 10px;
      margin-bottom: 10px;
    }
    .table_box {
      background-color: #fff;
      padding: 15px;
      .operation {
        display: flex;
        justify-content: space-around;
        > .item {
          font-size: 22px;
          color: #66b1ff;
          cursor: pointer;
        }
      }
    }
    .default_input {
      width: 150px;
    }
    .time_input{
      width: 185px;
    }
  }
}
</style>
