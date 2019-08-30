<template>
  <div class="content">
    <div class="main">
      <div class="filters">
        <div class="filters_item">
          <span>订单号：</span>
          <el-input
            v-model="paramsData.orderSn"
            placeholder="请输入订单号"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>下单用户信息：</span>
          <el-input
            v-model="paramsData.userInfo"
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
            @click="initDataList"
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
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {

      pageParams: {},
      effectiveDate: '',
      paramsData: {
        id: '',
        orderSn: '',
        userInfo: '',
        startTime: '',
        endTime: '',
        currentPage: 1
      },
      tableData: [
        {
          'orderSn': 'qweqwew',
          'moneyPaid': 0,
          'useAccount': 1.3,
          'useScore': 50,
          'memberCardBalance': 0,
          'userId': 1,
          'username': 'sdsa',
          'mobile': '13533333333',
          'createTime': '2019-08-21 17:10:22',
          'orderStatus': 1
        },
        {
          'orderSn': 'qweqwew',
          'moneyPaid': 0,
          'useAccount': 1.3,
          'useScore': 50,
          'memberCardBalance': 0,
          'userId': 1,
          'username': 'sdsa',
          'mobile': '13533333333',
          'createTime': '2019-08-21 17:10:22',
          'orderStatus': 1
        },
        {
          'orderSn': 'qweqwew',
          'moneyPaid': 0,
          'useAccount': 1.3,
          'useScore': 50,
          'memberCardBalance': 0,
          'userId': 1,
          'username': 'sdsa',
          'mobile': '13533333333',
          'createTime': '2019-08-21 17:10:22',
          'orderStatus': 1
        },
        {
          'orderSn': 'qweqwew',
          'moneyPaid': 0,
          'useAccount': 1.3,
          'useScore': 50,
          'memberCardBalance': 0,
          'userId': 1,
          'username': 'sdsa',
          'mobile': '13533333333',
          'createTime': '2019-08-21 17:10:22',
          'orderStatus': 1
        },
        {
          'orderSn': 'qweqwew',
          'moneyPaid': 0,
          'useAccount': 1.3,
          'useScore': 50,
          'memberCardBalance': 0,
          'userId': 1,
          'username': 'sdsa',
          'mobile': '13533333333',
          'createTime': '2019-08-21 17:10:22',
          'orderStatus': 1
        }
      ],
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
      this.loading = false
      this.pageParams.startTime = this.effectiveDate[0] ? this.effectiveDate[0] : ''
      this.pageParams.endTime = this.effectiveDate[1] ? this.effectiveDate[1] : ''
      this.handleData(this.tableData)
    },
    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        // TODO: 国际化
        item.orderStatusFormat = item.orderStatus === 1 ? '订单完成' : '订单未完成'
        item.realPay = this.getRealPay(item.useAccount, item.useScore, item.memberCardBalance)
      })
      this.tableData = data
    },
    getRealPay (useAccount, useScore, memberCardBalance) {
      let payStr = ''
      if (useAccount > 0) {
        payStr += `${useAccount}元余额`
      };
      if (useScore > 0) {
        payStr += `${useAccount}积分`
      };
      if (memberCardBalance > 0) {
        payStr += `${useAccount}元会员卡余额`
      };
      return payStr
    }
  },
  mounted () {
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
