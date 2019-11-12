<template>
  <div>
    <section class="label">
      <div class="labelItem">成交用户分析</div>
      <el-select
        v-model="timeSelect"
        size="small"
        clearable
        @change="dateChangeHandler"
        class="timeSelect"
      >
        <el-option
          v-for="item in timeRange"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
      <span>2019年10月02日 - 2019年11月01日</span>

      <!-- 表格数据部分 -->
      <table class="table_list">
        <thead class="table_heard">
          <tr>
            <th>
              客户类型
            </th>
            <th>
              <section class="table_icon">
                <div>客户数</div>
                <el-tooltip
                  class="item"
                  effect="light"
                  content="客户数"
                  placement="top"
                >
                  <i class="el-icon-warning-outline icons"></i>
                </el-tooltip>
              </section>
            </th>
            <th>
              <section class="table_icon">
                <div>客户数占比</div>
                <el-tooltip
                  class="item"
                  effect="light"
                  content="客户数占比"
                  placement="top"
                >
                  <i class="el-icon-warning-outline icons"></i>
                </el-tooltip>
              </section>
            </th>
            <th>
              <section class="table_icon">
                <div>客单价</div>
                <el-tooltip
                  class="item"
                  effect="light"
                  content="客单价"
                  placement="top"
                >
                  <i class="el-icon-warning-outline icons"></i>
                </el-tooltip>
              </section>
            </th>
            <th>付款金额</th>
            <th>
              <section class="table_icon">
                <div>访问-付款转化率</div>
                <el-tooltip
                  class="item"
                  effect="light"
                  content="访问-付款转化率"
                  placement="top"
                >
                  <i class="el-icon-warning-outline icons"></i>
                </el-tooltip>
              </section>
            </th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>全部成交客户</td>
            <td>
              <span>{{this.table_allUser.orderUserData}}</span>
              <span>{{this.table_allUser.orderUserDataTrend}}</span>
            </td>
            <td>
              <span>{{this.table_allUser.orderUserDataRate}}</span>
              <span>{{this.table_allUser.orderUserDataRateTrend}}</span>
            </td>
            <td>
              <span>{{this.table_allUser.unitPrice}}</span>
              <span>{{this.table_allUser.unitPriceTrend}}</span>
            </td>
            <td>
              <span>{{this.table_allUser.totalPaidMoney}}</span>
              <span>{{this.table_allUser.totalPaidMoneyTrend}}</span>
            </td>
            <td>
              <span>{{this.table_allUser.transRate}}</span>
              <span>{{this.table_allUser.transRateTrend}}</span>
            </td>
          </tr>
          <tr>
            <td>新成交客户</td>
            <td>
              <span>{{this.table_newUser.newOrderUserData}}</span>
              <span>{{this.table_newUser.newOrderUserDataTrend}}</span>
            </td>
            <td>
              <span>{{this.table_newUser.newOrderUserDataRate}}</span>
              <span>{{this.table_newUser.newOrderUserDataRateTrend}}</span>
            </td>
            <td>
              <span>{{this.table_newUser.newUnitPrice}}</span>
              <span>{{this.table_newUser.newUnitPriceTrend}}</span>
            </td>
            <td>
              <span>{{this.table_newUser.newPaidMoney}}</span>
              <span>{{this.table_newUser.newPaidMoneyTrend}}</span>
            </td>
            <td>
              <span>{{this.table_newUser.newTransRate}}</span>
              <span>{{this.table_newUser.newTransRateTrend}}</span>
            </td>
          </tr>
          <tr>
            <td>老成交用户</td>
            <td>
              <span>{{this.table_oldUser.oldOrderUserData}}</span>
              <span>{{this.table_oldUser.oldOrderUserDataTrend}}</span>
            </td>
            <td>
              <span>{{this.table_oldUser.oldOrderUserDataRate}}</span>
              <span>{{this.table_oldUser.oldOrderUserDataRateTrend}}</span>
            </td>
            <td>
              <span>{{this.table_oldUser.oldUnitPrice}}</span>
              <span>{{this.table_oldUser.oldUnitPriceTrend}}</span>
            </td>
            <td>
              <span>{{this.table_oldUser.oldPaidMoney}}</span>
              <span>{{this.table_oldUser.oldPaidMoneyTrend}}</span>
            </td>
            <td>
              <span>{{this.table_oldUser.oldTransRate}}</span>
              <span>{{this.table_oldUser.oldTransRateTrend}}</span>
            </td>
          </tr>
          <tr>
            <td>趋势指标选择</td>
            <td colspan="5">
              <el-radio-group v-model="trendIndicator">
                <el-radio :label="1">客户数</el-radio>
                <el-radio :label="2">客单价</el-radio>
                <el-radio :label="3">付款金额</el-radio>
                <el-radio :label="4">访问付款转化率</el-radio>
              </el-radio-group>

            </td>
          </tr>
        </tbody>
      </table>
    </section>

    <section class="line">
      <!-- echarts图表部分 -->
      <div id="userAnalysisCharts"></div>
    </section>
  </div>
</template>

<script>
import echarts from 'echarts'
import { userAnalysis } from '@/api/admin/firstWebManage/userStatistics/userStatistics.js'

export default {
  created () {
    this.initData()
  },

  mounted () {
    this.langDefault()
    this.myUserChart = echarts.init(document.getElementById('userAnalysisCharts'))
  },

  data () {
    return {
      timeSelect: '',
      timeRange: [
        { value: 1, label: '最新1天' },
        { value: 7, label: '最新7天' },
        { value: 30, label: '最新30天' }
      ],
      params: 7,
      table_allUser: {},
      table_newUser: {},
      table_oldUser: {},
      trendIndicator: 1,
      myUserChart: {},
      // 新用户
      newUserNumber: {},
      newUserPrice: {},
      newUserPay: {},
      newUserTransform: {},
      // 老用户
      oldUserNumber: {},
      oldUserPrice: {},
      oldUserPay: {},
      oldUserTransform: {}
    }
  },

  methods: {
    dateChangeHandler (time) {
      this.params = time
      // console.log(this.params)
      this.initData()
    },

    initData () {
      userAnalysis({ 'type': this.params }).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.originalData = res.content
          this.handleData(this.originalData)
        }
      }).catch(err => console.log(err))
    },

    numberChange (point) {
      let str
      if (point > 0) {
        str = '↑' + Number(point * 100).toFixed(2) + '%'
      } else if (point < 0) {
        str = '↓' + Math.abs(Number(point * 100)).toFixed(2) + '%'
      } else {
        str = '--'
      }
      return str
    },

    // 处理返回来的数据
    handleData (data) {
      console.log(data)
      console.log(data.dataVo)
      console.log(data.changeRateVo)

      let allUser = {
        // 整数
        orderUserData: data.dataVo.orderUserData,
        orderUserDataRate: this.numberChange(data.dataVo.orderUserDataRate),
        unitPrice: data.dataVo.unitPrice,
        totalPaidMoney: data.dataVo.totalPaidMoney,
        transRate: this.numberChange(data.dataVo.transRate),
        // 变换率
        orderUserDataTrend: this.numberChange(data.changeRateVo.orderUserDataTrend),
        orderUserDataRateTrend: this.numberChange(data.changeRateVo.orderUserDataRateTrend),
        unitPriceTrend: this.numberChange(data.changeRateVo.unitPriceTrend),
        totalPaidMoneyTrend: this.numberChange(data.changeRateVo.totalPaidMoneyTrend),
        transRateTrend: this.numberChange(data.changeRateVo.transRateTrend)
      }
      this.table_allUser = allUser
      console.log(allUser)

      let newUser = {
        // 整数
        newOrderUserData: data.dataVo.newOrderUserData,
        newOrderUserDataRate: this.numberChange(data.dataVo.newOrderUserDataRate),
        newUnitPrice: data.dataVo.newUnitPrice,
        newPaidMoney: data.dataVo.newPaidMoney,
        newTransRate: this.numberChange(data.dataVo.newTransRate),
        // 变换率
        newOrderUserDataTrend: this.numberChange(data.changeRateVo.newOrderUserDataTrend),
        newOrderUserDataRateTrend: this.numberChange(data.changeRateVo.newOrderUserDataRateTrend),
        newUnitPriceTrend: this.numberChange(data.changeRateVo.newUnitPriceTrend),
        newPaidMoneyTrend: this.numberChange(data.changeRateVo.newPaidMoneyTrend),
        newTransRateTrend: this.numberChange(data.changeRateVo.newTransRateTrend)
      }
      this.table_newUser = newUser

      let oldUser = {
        // 整数
        oldOrderUserData: data.dataVo.oldOrderUserData,
        oldOrderUserDataRate: this.numberChange(data.dataVo.oldOrderUserDataRate),
        oldUnitPrice: data.dataVo.oldUnitPrice,
        oldPaidMoney: data.dataVo.oldPaidMoney,
        oldTransRate: this.numberChange(data.dataVo.oldTransRate),
        // 变换率
        oldOrderUserDataTrend: this.numberChange(data.changeRateVo.oldOrderUserDataTrend),
        oldOrderUserDataRateTrend: this.numberChange(data.changeRateVo.oldOrderUserDataRateTrend),
        oldUnitPriceTrend: this.numberChange(data.changeRateVo.oldUnitPriceTrend),
        oldPaidMoneyTrend: this.numberChange(data.changeRateVo.oldPaidMoneyTrend),
        oldTransRateTrend: this.numberChange(data.changeRateVo.oldTransRateTrend)
      }
      this.table_oldUser = oldUser

      // 新成交
      data.dailyNewVo.map(item => {
        // 日期
        // this.newUserNumber.push(item.refDate)
        // 客户数
        this.newUserNumber.push(item.orderUserData)
        // 客单价
        this.newUserPrice.push(item.unitPrice)
        // 付款金额
        this.newUserPay.push(item.paidMoney)
        // 转化率
        this.newUserTransform.push(item.transRate)
      })

      // 新成交
      data.dailyNewVo.map(item => {
        // 日期
        // this.newUserNumber.push(item.refDate)
        // 客户数
        this.newUserNumber.push(item.orderUserData)
        // 客单价
        this.newUserNumber.push(item.unitPrice)
        // 付款金额
        this.newUserNumber.push(item.paidMoney)
        // 转化率
        this.newUserNumber.push(item.transRate)
      })

      // 老成交
      data.dailyOldVo.map(item => {
        // 日期
        // this.oldUserNumber.push(item.refDate)
        // 客户数
        this.oldUserNumber.push(item.orderUserData)
        // 客单价
        this.oldUserPrice.push(item.unitPrice)
        // 付款金额
        this.oldUserPay.push(item.paidMoney)
        // 转化率
        this.oldUserTransform.push(item.transRate)
      })

      // handleTrendIndicator() {
      //   switch (trendIndicator) {
      //     case 1:
      //       let arr = ['新成交客户数', '老成交客户数']
      //   }
      // }

      // // 折线图数据部分
      this.echartsData = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['访问会员数 ', '领券会员数', '加购会员数', '成交会员数']
        },
        grid: {
          left: '7%',
          right: '4%',
          bottom: '4%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          // data: this.chartDateList
          data: ['周一', '周2', '周3', '周4', '周5', '周6', '周7']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '访问会员数 ',
            type: 'line',
            stack: '总量',
            // data: this.chartAccessNumber
            data: [34, 27, 33, 31, 42, 19, 11]
          },
          {
            name: '领券会员数',
            type: 'line',
            stack: '总量',
            // data: this.chartGetCouponNumber
            data: [3404, 3410, 3423, 3435, 3441, 3450, 3456]
          },
          {
            name: '加购会员数',
            type: 'line',
            stack: '总量',
            // data: this.chartAddBuyNumber
            data: [4, 3, 5, 4, 11, 0, 0]
          },
          {
            name: '成交会员数',
            type: 'line',
            stack: '总量',
            // data: this.chartSuccessNumber
            data: [4, 3, 5, 44, 113, 45, 89]
          }
        ]
      }

      this.myUserChart.setOption(this.echartsData)
    }

  }
}

</script>
<style lang="scss" scoped>
.label {
  padding: 10px;
  background: #fff;
  .labelItem {
    height: 50px;
    line-height: 50px;
    color: #333;
  }
  .timeSelect {
    width: 140px;
    margin: 0 10px 0 2px;
  }
}
.table_list {
  width: 98%;
  height: 200px;
  margin: 20px 2% 0 0;
  border: 1px solid #000;
  .table_heard {
    .table_icon {
      display: flex;
    }
    tr {
      th {
        padding: 9px 15px;
        min-height: 20px;
        line-height: 20px;
        border: 1px solid #e6e6e6;
      }
    }
  }
  td {
    padding: 9px 15px;
    min-height: 20px;
    line-height: 20px;
    border: 1px solid #e6e6e6;
  }
}

.line {
  width: 100%;
  margin-top: 10px;
  background: #fff;
  #userAnalysisCharts {
    width: 90%;
    padding-top: 20px;
    height: 500px;
  }
}
</style>
