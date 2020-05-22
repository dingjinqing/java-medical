<template>
  <section class="label">
    <div class="labelItem">{{$t('userStatistics.userOverviewAndTrend')}}</div>
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
    <span>{{this.startDate.year}}{{$t('userStatistics.year')}}{{this.startDate.month}}{{$t('userStatistics.month')}}{{this.startDate.day}}{{$t('userStatistics.day')}} - {{this.endDate.year}}{{$t('userStatistics.year')}}{{this.endDate.month}}{{$t('userStatistics.month')}}{{this.endDate.day}}{{$t('userStatistics.day')}}</span>

    <!-- 表格数据部分 -->
    <div class="fromWrapper">
      <div
        class="fromItem"
        v-for="item in table"
        :key="item.number"
      >
        <div
          class="fromInfo"
          style="display: flex;"
        >
          <div>{{item.name}}</div>
          <el-tooltip
            effect="light"
            placement="top"
          >
            <div
              slot="content"
              style="width: 250px;line-height: 30px;font-size: 14px"
            >
              {{item.content}}
            </div>
            <i class="el-icon-warning-outline icons"></i>
          </el-tooltip>
        </div>
        <div
          class="num"
          style="color: #5A8BFF"
        >{{item.number}}</div>
        <div>{{$t('userStatistics.compareLastMonth')}} {{item.rate}}</div>
      </div>
    </div>

    <!-- echarts图表部分 -->
    <div id="charts"></div>

  </section>
</template>

<script>
import echarts from 'echarts'
import { customerTrend } from '@/api/admin/firstWebManage/userStatistics/userStatistics.js'

export default {
  watch: {
    lang () {
      this.timeRange = this.$t('userStatistics.timeRange')
    }
  },
  created () {
    this.initData()
  },

  mounted () {
    this.langDefault()
    this.myChart = echarts.init(document.getElementById('charts'))
  },

  data () {
    return {
      timeSelect: 1,
      timeRange: this.$t('userStatistics.timeRange'),
      value: 1,
      params: '1',
      originalData: {
        visitorsNumber: '', // 访客数
        visitorsNumberRate: '',
        userNumber: '', // 累积用户访客数
        userNumberRate: '',
        tradeNumber: '', // 成交用户数
        tradeNumberRate: ''
      },
      chartChange: {
        date: [],
        visitorsNumber: [], // 图表访客数
        userNumber: [], // 图表累积用户访客数
        tradeNumber: [] // 图表成交用户数
      },
      myChart: {},
      table: [],
      startDate: {
        year: '',
        month: '',
        day: ''
      },
      endDate: {
        year: '',
        month: '',
        day: ''
      }
    }
  },

  methods: {
    dateChangeHandler (time) {
      this.chartChange = {
        date: [],
        visitorsNumber: [],
        userNumber: [],
        tradeNumber: []
      }
      this.params = time
      this.initData()
    },

    initData () {
      customerTrend({ 'type': this.params }).then(res => {
        if (res.error === 0) {
          this.originalData = res.content
          this.handleData(this.originalData)
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
    handleData (data) {
      this.startDate.year = data.startTime.split('-')[0]
      this.startDate.month = data.startTime.split('-')[1]
      this.startDate.day = data.startTime.split('-')[2]

      this.endDate.year = data.endTime.split('-')[0]
      this.endDate.month = data.endTime.split('-')[1]
      this.endDate.day = data.endTime.split('-')[2]

      // 访客数
      this.originalData.visitorsNumber = data.loginDataTotal
      this.originalData.visitorsNumberRate = this.numberChange(data.loginDataRate)
      // 累积用户数
      this.originalData.userNumber = data.userDataTotal
      this.originalData.userNumberRate = this.numberChange(data.userDataRate)
      // 用户成交数
      this.originalData.tradeNumber = data.orderUserDataTotal
      this.originalData.tradeNumberRate = this.numberChange(data.orderUserDataRate)

      data.trendDailyVo.map(item => {
        this.chartChange.date.push(item.refDate)
        this.chartChange.visitorsNumber.push(item.loginData)
        this.chartChange.userNumber.push(item.userData)
        this.chartChange.tradeNumber.push(item.orderUserData)
      })

      this.table = [
        {
          name: this.$t('userStatistics.visitNumber'),
          content: this.$t('userStatistics.content1'),
          number: this.originalData.visitorsNumber,
          rate: this.originalData.visitorsNumberRate
        },
        {
          name: this.$t('userStatistics.userNumber'),
          content: this.$t('userStatistics.content2'),
          number: this.originalData.userNumber,
          rate: this.originalData.userNumberRate
        },
        {
          name: this.$t('userStatistics.userTradeNumber'),
          content: this.$t('userStatistics.content3'),
          number: this.originalData.tradeNumber,
          rate: this.originalData.tradeNumberRate
        }
      ]

      console.log(this.chartChange, 'char change value==')

      // 折线图数据部分
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
          data: this.chartChange.date
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: this.$t('userStatistics.visitNumber'),
            type: 'line',
            data: this.chartChange.visitorsNumber
          },
          {
            name: this.$t('userStatistics.userNumber'),
            type: 'line',
            data: this.chartChange.userNumber
          },
          {
            name: this.$t('userStatistics.userTradeNumber'),
            type: 'line',
            data: this.chartChange.tradeNumber
          }
        ]
      }

      this.myChart.setOption(this.echartsData)
    }

  }
}

</script>
<style lang="scss" scoped>
.label {
  background: #fff;
  padding: 10px;
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
.fromWrapper {
  border: 1px solid #eee;
  height: 130px;
  width: 85%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 30px auto 50px;
  .fromItem {
    flex: 1;
    height: 130px;
    position: relative;
    border-right: 1px solid #eee;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    .icons {
      margin-left: 10px;
      position: relative;
    }
    .num {
      margin-top: 15px;
      font-size: 30px;
    }
    :nth-of-type(3) {
      margin-top: 10px;
    }
  }
}

#charts {
  width: 90%;
  height: 500px;
}
</style>
