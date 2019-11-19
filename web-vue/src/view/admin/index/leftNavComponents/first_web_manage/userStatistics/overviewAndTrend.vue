<template>
  <section class="label">
    <div class="labelItem">客户概况及趋势</div>
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
        <div>较前一月 {{item.rate}}</div>
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
      timeRange: [
        { value: 1, label: '最新1天' },
        { value: 7, label: '最新7天' },
        { value: 30, label: '最新30天' }
      ],
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
          name: '访客数',
          content: '筛选时间内，店铺所有页面被访问的去重人数，一个人在筛选时间范围内访问多次只记为一个',
          number: this.originalData.visitorsNumber,
          rate: this.originalData.visitorsNumberRate
        },
        {
          name: '累积用户数',
          content: '截至到筛选时间的最后一天，店铺的会员累积人数',
          number: this.originalData.userNumber,
          rate: this.originalData.userNumberRate
        },
        {
          name: '用户成交数',
          content: '筛选时间内，在店铺中付款成功的去重客户数，一个人在筛选时间范围内付款多次只记为一个',
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
            name: '访客数',
            type: 'line',
            data: this.chartChange.visitorsNumber
          },
          {
            name: '累积用户数',
            type: 'line',
            data: this.chartChange.userNumber
          },
          {
            name: '成交用户数',
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
