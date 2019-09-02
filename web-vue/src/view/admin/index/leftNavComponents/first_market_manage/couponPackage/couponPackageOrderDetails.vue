<template>
  <div class="content">
    <div class="main">
      <div class="filters">
        <div class="filters_item">
          <span>订单号：</span>
          <el-input
            v-model="pageParams.orderSn"
            placeholder="请输入订单号"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>下单用户信息：</span>
          <el-input
            v-model="pageParams.userInfo"
            placeholder="请输入下单用户昵称/手机号"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>下单时间</span>
          <el-date-picker
            v-model="effectiveDate"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd HH:mm:ss"
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
            @click="exportDataList"
            type="primary"
            size="small"
          >导出数据</el-button>
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
          <template v-for="(item,index) in tableItem">
            <el-table-column
              :prop="item.prop"
              :label="item.label"
              :key="index"
            >
            </el-table-column>
          </template>
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
        startTime: '',
        endTime: ''
      },
      effectiveDate: '',
      tableData: [],
      loading: false,
      tableItem: [
        { prop: 'orderSn', label: '订单号' },
        { prop: 'realPay', label: '单价' },
        { prop: 'username', label: '下单用户信息' },
        { prop: 'createTime', label: '下单时间' },
        { prop: 'orderStatusFormat', label: '订单状态' },
        { prop: 'moneyPaid', label: '支付金额' }
      ]
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.pageParams.id = this.actId
      this.pageParams.startTime = this.effectiveDate[0] ? this.effectiveDate[0] : null
      this.pageParams.endTime = this.effectiveDate[1] ? this.effectiveDate[1] : null
      getCouponPackOrderPageList(this.pageParams).then((res) => {
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          this.pageParams = res.content.page
          this.loading = false
        }
      })
    },
    // 列表导出
    exportDataList () {
      this.loading = true
      this.pageParams.id = this.actId
      this.pageParams.startTime = this.effectiveDate[0] ? this.effectiveDate[0] : null
      this.pageParams.endTime = this.effectiveDate[1] ? this.effectiveDate[1] : null
      exportCouponPackOrderList(this.pageParams).then((res) => {
        this.download(res)
        this.loading = false
      })
    },
    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        // TODO: 国际化
        item.orderStatusFormat = item.orderStatus === 1 ? '订单完成' : '待付款'
        item.realPay = this.getRealPrice(item.moneyPaid, item.useAccount, item.useScore, item.memberCardBalance)
      })
      this.tableData = data
    },
    getRealPrice (moneyPaid, useAccount, useScore, memberCardBalance) {
      let payStr = ''
      if (useScore > 0) {
        payStr += `${useAccount}积分`
      } else {
        let price = 0.0
        if (moneyPaid > 0) {
          price += moneyPaid
        };
        if (useAccount > 0) {
          price += useAccount
        };
        if (memberCardBalance > 0) {
          price += useAccount
        };
        payStr += `${price}元`
      }

      return payStr
    },
    // 下载文件
    download (data) {
      if (!data) {
        return
      }
      let url = window.URL.createObjectURL(new Blob([data]))
      let link = document.createElement('a')
      link.style.display = 'none'
      link.href = url
      link.setAttribute('download', 'excel.xlsx')

      document.body.appendChild(link)
      link.click()
    }
  },
  mounted () {
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
  }
}
</style>
