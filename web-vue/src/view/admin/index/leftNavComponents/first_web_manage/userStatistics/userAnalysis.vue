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
      <span>{{this.startDate.year}}年{{this.startDate.month}}月{{this.startDate.day}}日 - {{this.endDate.year}}年{{this.endDate.month}}月{{this.endDate.day}}日</span>

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
                  slot="content"
                  effect="light"
                  placement="top"
                >
                  <div
                    slot="content"
                    style="width: 400px;height: 90px;line-height: 30px;font-size: 14px;"
                  >
                    <!-- <section style="display: flex">
                      <div style="width: 30%;color:#999">{{item.title}}</div>
                      <div style="width: 70%;color: #353535">{{item.content}}</div>
                    </section> -->
                    <i class="el-icon-warning-outline icons"></i>
                  </div>
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
              <el-radio-group
                v-model="trendIndicator"
                @change="handleSelect"
              >
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
      timeSelect: 1,
      timeRange: [
        { value: 1, label: '最新1天' },
        { value: 7, label: '最新7天' },
        { value: 30, label: '最新30天' }
      ],
      params: 1,
      tipsList: [
        {
          title: '',
          content: ''
        }
      ],
      startDate: {
        year: '',
        month: '',
        day: ''
      },
      endDate: {
        year: '',
        month: '',
        day: ''
      },
      table_allUser: {},
      table_newUser: {},
      table_oldUser: {},
      trendIndicator: 1,
      myUserChart: {},
      userDate: [],
      series: [],
      newUserPriceRenderList: [],
      newUserNumberRenderList: [],
      newUserPayRenderList: [],
      newUserTransformRenderList: [],
      oldUserPriceRenderList: [],
      oldUserNumberRenderList: [],
      oldUserPayRenderList: [],
      oldUserTransformRenderList: []
    }
  },

  methods: {
    dateChangeHandler (time) {
      this.userDate = []
      this.params = time
      this.initData()
    },

    initData () {
      userAnalysis({ 'type': this.params }).then(res => {
        if (res.error === 0) {
          this.originalData = res.content
          this.handleData(this.originalData)
          this.handleSelect(this.trendIndicator)
        }
      }).catch(err => console.log(err))
    },

    numberChange (number) {
      let str
      if (number > 0) {
        str = '↑' + Number(number * 100).toFixed(2) + '%'
      } else if (number < 0) {
        str = '↓' + Math.abs(Number(number * 100)).toFixed(2) + '%'
      } else {
        str = '--'
      }
      return str
    },

    handleSelect (index) {
      let series = []
      switch (index) {
        case 1:
          series = [{
            name: '新成交客户数',
            type: 'line',
            stack: '总量',
            data: this.newUserNumberRenderList
          }, {
            name: '老成交客户数',
            type: 'line',
            stack: '总量',
            data: this.oldUserNumberRenderList
          }]
          break
        case 2:
          series = [{
            name: '新成交客单价',
            type: 'line',
            stack: '总量',
            data: this.newUserPriceRenderList
          }, {
            name: '老成交客单价',
            type: 'line',
            stack: '总量',
            data: this.oldUserPriceRenderList
          }]
          break
        case 3:
          series = [{
            name: '新成交付款金额',
            type: 'line',
            stack: '总量',
            data: this.newUserPayRenderList
          }, {
            name: '老成交付款金额',
            type: 'line',
            stack: '总量',
            data: this.oldUserPayRenderList
          }]
          break
        case 4:
          series = [{
            name: '新成交访问-付款转化率',
            type: 'line',
            stack: '总量',
            data: this.newUserTransformRenderList
          }, {
            name: '老成交访问-付款转化率',
            type: 'line',
            stack: '总量',
            data: this.oldUserTransformRenderList
          }]
      }
      this.drawEcharts(series)
    },

    drawEcharts (series) {
      // // 折线图数据部分
      this.echartsData = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
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
          data: this.userDate
        },
        yAxis: {
          type: 'value'
        },
        series
      }

      this.myUserChart.setOption(this.echartsData)
    },

    // 处理表格数据
    handleData (data) {
      this.startDate.year = data.startTime.split('-')[0]
      this.startDate.month = data.startTime.split('-')[1]
      this.startDate.day = data.startTime.split('-')[2]

      this.endDate.year = data.endTime.split('-')[0]
      this.endDate.month = data.endTime.split('-')[1]
      this.endDate.day = data.endTime.split('-')[2]

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
        // 整数部分数据
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
      data.dailyNewVo.forEach(item => {
        // 日期
        this.userDate.push(item.refDate)
        // 新客户数据
        this.newUserPriceRenderList.push(item.unitPrice)
        this.newUserNumberRenderList.push(item.orderUserData)
        this.newUserPayRenderList.push(item.paidMoney)
        this.newUserTransformRenderList.push(item.transRate)
      })

      // 老成交
      data.dailyOldVo.forEach(item => {
        // 老客户数据
        this.oldUserPriceRenderList.push(item.unitPrice)
        this.oldUserNumberRenderList.push(item.orderUserData)
        this.oldUserPayRenderList.push(item.paidMoney)
        this.oldUserTransformRenderList.push(item.transRate)
      })

      this.userPrice = []
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
