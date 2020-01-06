<template>
  <section class="label">
    <div class="labelItem">资产管理1111</div>
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
        >{{item.number}} 积分</div>
        <div>较前一周期 {{item.rate}}</div>
      </div>
    </div>

    <!-- echarts图表部分 -->
    <div id="score_charts"></div>
  </section>
</template>

<script>
import echarts from 'echarts'
import { scoreManage } from '@/api/admin/firstWebManage/assetsManage/assetsManage.js'

export default {
  components: {},

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
    this.myChart = echarts.init(document.getElementById('score_charts'))
  },

  data () {
    return {
      timeSelect: 1,
      timeValue: [],
      timeRange: this.$t('tradesStatistics.timeRange'),
      value: 1,
      screeningTime: '1',
      originalData: {
        incomeRealScore: '', // 净积分收入
        incomeRealScorePer: '',
        incomeTotalScore: '', // 总积分收入
        incomeTotalScorePer: '',
        outgoScore: '', // 总积分支出
        outgoScorePer: '',
        revenueDates: []
      },
      // 请求入参
      param: {
        screeningTime: 1,
        startTime: '',
        endTime: '',
        tradeContent: 1
      },
      chartChange: {
        date: [],
        incomeRealScore: [], // 图表净收入
        incomeTotalScore: [], // 图表总收入
        outgoScore: [] // 图表总支出
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
        incomeRealScore: [],
        incomeTotalScore: [],
        outgoScore: []
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
          incomeRealScore: [],
          incomeTotalScore: [],
          outgoScore: []
        }
        this.screeningTime = time
        this.initData()
      }
    },

    initData () {
      this.param.screeningTime = this.screeningTime
      scoreManage(this.param).then(res => {
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
      // this.originalData.incomeRealScore = data.incomeRealScore === null ? 0 : data.incomeRealScore
      this.originalData.incomeRealScore = data.incomeRealScore
      this.originalData.incomeRealScorePer = this.numberChange(data.incomeRealScorePer)
      // 总收入
      // this.originalData.incomeTotalScore = data.incomeTotalScore === null ? 0 : data.incomeTotalScore
      this.originalData.incomeTotalScore = data.incomeTotalScore
      this.originalData.incomeTotalScorePer = this.numberChange(data.incomeTotalScorePer)
      // 总支出
      // this.originalData.outgoScore = data.outgoScore === null ? 0 : data.outgoScore
      this.originalData.outgoScore = data.outgoScore
      this.originalData.outgoScorePer = this.numberChange(data.outgoScorePer)

      data.revenueDates.map(item => {
        this.chartChange.date.push(item.refDate)
        this.chartChange.incomeRealScore.push(item.incomeRealScore)
        this.chartChange.incomeTotalScore.push(item.incomeTotalScore)
        this.chartChange.outgoScore.push(item.outgoScore)
      })

      this.table = [
        {
          name: '净收入',
          content: this.$t('userStatistics.content1'),
          number: this.originalData.incomeRealScore,
          rate: this.originalData.incomeRealScorePer
        },
        {
          name: '总收入',
          content: this.$t('userStatistics.content2'),
          number: this.originalData.incomeTotalScore,
          rate: this.originalData.incomeTotalScorePer
        },
        {
          name: '总支出',
          content: this.$t('userStatistics.content3'),
          number: this.originalData.outgoScore,
          rate: this.originalData.outgoScorePer
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
            data: this.chartChange.incomeRealScore
            // data: [120, 132, 101, 134, 90, 230, 210]
          },
          {
            name: '总收入',
            type: 'line',
            data: this.chartChange.incomeTotalScore
            // data: [220, 182, 191, 234, 290, 330, 310]
          },
          {
            name: '总支出',
            type: 'line',
            data: this.chartChange.outgoScore
            // data: [150, 232, 201, 154, 190, 330, 410]
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

  #score_charts {
    width: 90%;
    height: 500px;
    // border: 1px solid #000;
  }
}
</style>
