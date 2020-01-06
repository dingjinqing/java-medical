<template>
  <section class="label">
    <div class="labelItem">营收概况及趋势 查看明细</div>
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
    <el-date-picker
      v-if="timeSelect===0"
      v-model="timeValue"
      type="daterange"
      size="small"
      @change="customDate"
      value-format="yyyyMMdd"
      range-separator="-"
      start-placeholder="开始日期"
      end-placeholder="结束日期"
      class="custom"
    >
    </el-date-picker>
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
        >{{item.number}}  ￥</div>
        <div>较前一周期 {{item.rate}}</div>
      </div>
    </div>

    <!-- echarts图表部分 -->
    <div id="charts"></div>

  </section>
</template>

<script>
import echarts from 'echarts'
import { cashManage } from '@/api/admin/firstWebManage/assetsManage/assetsManage.js'

export default {
  watch: {
    lang () {
      this.timeRange = this.$t('tradesStatistics.timeRange')
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
      timeValue: [],
      timeRange: this.$t('tradesStatistics.timeRange'),
      value: 1,
      screeningTime: '1',
      originalData: {
        incomeRealMoney: '', // 净收入
        incomeRealMoneyPer: '',
        incomeTotalMoney: '', // 总收入
        incomeTotalMoneyPer: '',
        outgoMoney: '', // 总支出
        outgoMoneyPer: '',
        revenueDates: []
      },
      // 请求入参
      param: {
        screeningTime: 1,
        startTime: '',
        endTime: '',
        tradeContent: 0
      },
      chartChange: {
        date: [],
        incomeRealMoney: [], // 图表净收入
        incomeTotalMoney: [], // 图表总收入
        outgoMoney: [] // 图表总支出
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
    // 自定义时间
    customDate () {
      this.chartChange = {
        date: [],
        incomeRealMoney: [],
        incomeTotalMoney: [],
        outgoMoney: []
      }
      this.screeningTime = 0
      this.param.startTime = this.timeValue[0].substring(0, 4) + '-' + this.timeValue[0].substring(4, 6) + '-' + this.timeValue[0].substring(6, 8)
      this.param.endTime = this.timeValue[1].substring(0, 4) + '-' + this.timeValue[1].substring(4, 6) + '-' + this.timeValue[1].substring(6, 8)
      console.log('选择器的时间：', this.param)
      this.initData()
    },
    // 指定时间段
    dateChangeHandler (time) {
      if (time !== 0) {
        this.chartChange = {
          date: [],
          incomeRealMoney: [],
          incomeTotalMoney: [],
          outgoMoney: []
        }
        this.screeningTime = time
        this.initData()
      }
    },

    initData () {
      this.param.screeningTime = this.screeningTime
      cashManage(this.param).then(res => {
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
      console.log('起始时间' + this.startDate)
      this.endDate.year = data.endTime.split('-')[0]
      this.endDate.month = data.endTime.split('-')[1]
      this.endDate.day = data.endTime.split('-')[2]
      console.log('结束时间' + this.endDate)

      // 净收入
      // this.originalData.incomeRealMoney = data.incomeRealMoney === null ? 0 : data.incomeRealMoney
      this.originalData.incomeRealMoney = data.incomeRealMoney
      this.originalData.incomeRealMoneyPer = this.numberChange(data.incomeRealMoneyPer)
      // 总收入
      // this.originalData.incomeTotalMoney = data.incomeTotalMoney === null ? 0 : data.incomeTotalMoney
      this.originalData.incomeTotalMoney = data.incomeTotalMoney
      this.originalData.incomeTotalMoneyPer = this.numberChange(data.incomeTotalMoneyPer)
      // 总支出
      // this.originalData.outgoMoney = data.outgoMoney === null ? 0 : data.outgoMoney
      this.originalData.outgoMoney = data.outgoMoney
      this.originalData.outgoMoneyPer = this.numberChange(data.outgoMoneyPer)

      data.revenueDates.map(item => {
        this.chartChange.date.push(item.refDate)
        this.chartChange.incomeRealMoney.push(item.incomeRealMoney)
        this.chartChange.incomeTotalMoney.push(item.incomeTotalMoney)
        this.chartChange.outgoMoney.push(item.outgoMoney)
      })

      this.table = [
        {
          name: '净收入',
          content: this.$t('userStatistics.content1'),
          number: this.originalData.incomeRealMoney,
          rate: this.originalData.incomeRealMoneyPer
        },
        {
          name: '总收入',
          content: this.$t('userStatistics.content2'),
          number: this.originalData.incomeTotalMoney,
          rate: this.originalData.incomeTotalMoneyPer
        },
        {
          name: '总支出',
          content: this.$t('userStatistics.content3'),
          number: this.originalData.outgoMoney,
          rate: this.originalData.outgoMoneyPer
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
            name: '净收入',
            type: 'line',
            data: this.chartChange.incomeRealMoney
          },
          {
            name: '总收入',
            type: 'line',
            data: this.chartChange.incomeTotalMoney
          },
          {
            name: '总支出',
            type: 'line',
            data: this.chartChange.outgoMoney
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
