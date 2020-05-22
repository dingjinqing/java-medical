<template>
  <div class="hotWordsStatistics">
    <div class="text-title">
      搜索热词统计
    </div>
    <!--    下拉框-->
    <div class="top">
      <!--     选择时间-->
      <el-select
        v-model="timeSelect"
        size="small"
        clearable
        @change="timeChangeHandler"
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
      <span>{{this.startDate.year}}年{{this.startDate.month}}月{{this.startDate.day}}日 - {{this.endDate.year}}年{{this.endDate.month}}月{{this.endDate.day}}日</span>
    </div>
    <!--    柱状图数据-->
    <div id="hotWordsCharts" ></div>
    <div class="text">
      <span>热词统计</span>
    </div>
  </div>
</template>

<script>
import echarts from 'echarts'
import {getHot} from '@/api/admin/firstWebManage/searchStatistics/searchStatistics.js'

export default {
  data () {
    return {
      custom: true,
      timeValue: [],
      timeSelect: 7,
      timeRange: [
        { value: 7, label: '最近7天' },
        { value: 30, label: '最近30天' },
        { value: 0, label: '自定义' }
      ],
      param: {
        type: '7',
        startTime: '',
        endTime: ''
      },
      chartChange: {
        date: [],
        number: []
      },
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
      myChart: {},
      tableData: [],
      isOpenHotWords: ''
    }
  },
  created () {
    this.loadData()
  },
  mounted () {
    this.myChart = echarts.init(document.getElementById('hotWordsCharts'))
  },
  methods: {
    // time下拉框变化
    timeChangeHandler (time) {
      console.log(time)
      if (time === 7 || time === 30) {
        this.chartChange = {
          date: [],
          number: []
        }
        this.param.type = time
        this.loadData()
      } else {
        this.param.type = time
      }
    },
    // 自定义时间
    customDate () {
      this.chartChange = {
        date: [],
        number: []
      }
      this.param.startTime = this.timeValue[0].substring(0, 4) + '-' + this.timeValue[0].substring(4, 6) + '-' + this.timeValue[0].substring(6, 8) + ' 00:00:00'
      this.param.endTime = this.timeValue[1].substring(0, 4) + '-' + this.timeValue[1].substring(4, 6) + '-' + this.timeValue[1].substring(6, 8) + ' 23:59:59'
      console.log('选择器的时间：', this.param)
      this.loadData()
    },
    // 页面初始化数据
    loadData () {
      getHot(this.param).then(res => {
        console.log('搜搜热词统计：', res)
        if (res.error === 0) {
          this.isOpenHotWords = res.content.isOpenHotWords
          this.handleData(res.content)
        }
      }).catch(err => console.log(err))
    },
    // 数据处理
    handleData (content) {
      this.startDate.year = content.startTime.substring(0, 4)
      this.startDate.month = content.startTime.substring(5, 7)
      this.startDate.day = content.startTime.substring(8, 10)

      this.endDate.year = content.endTime.substring(0, 4)
      this.endDate.month = content.endTime.substring(5, 7)
      this.endDate.day = content.endTime.substring(8, 10)
      this.chartChange = {
        hotWords: [],
        count: []
      }
      content.data.map(item => {
        this.chartChange.hotWords.push(item.hotWords)
        this.chartChange.count.push(item.count)
      })
      console.log('柱状图数据：', this.chartChange)
      // 柱状图数据部分
      this.echartsData = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
        },
        grid: {
          left: '8%',
          right: '4%',
          bottom: '4%',
          containLabel: true
        },
        xAxis: {
          axisTick: {
            alignWithLabel: true
          },
          type: 'category',
          // boundaryGap: false,
          // data: this.chartChange.hotWords
          data: []
        },
        yAxis: {
          type: 'value'
        },
        series: [
          // {
          //   name: '搜索次数',
          //   type: 'bar',
          //   barWidth: '60%',
          //   // type: 'line',
          //   data: this.chartChange.count
          // }
          {
            name: this.chartChange.hotWords[0],
            type: 'bar',
            barWidth: '7%',
            data: [this.chartChange.count[0]]
          },
          {
            name: this.chartChange.hotWords[1],
            type: 'bar',
            barWidth: '7%',
            data: [this.chartChange.count[1]]
          },
          {
            name: this.chartChange.hotWords[2],
            type: 'bar',
            barWidth: '7%',
            data: [this.chartChange.count[2]]
          },
          {
            name: this.chartChange.hotWords[3],
            type: 'bar',
            barWidth: '7%',
            data: [this.chartChange.count[3]]
          },
          {
            name: this.chartChange.hotWords[4],
            type: 'bar',
            barWidth: '7%',
            data: [this.chartChange.count[4]]
          },
          {
            name: this.chartChange.hotWords[5],
            type: 'bar',
            barWidth: '7%',
            data: [this.chartChange.count[5]]
          },
          {
            name: this.chartChange.hotWords[6],
            type: 'bar',
            barWidth: '7%',
            data: [this.chartChange.count[6]]
          },
          {
            name: this.chartChange.hotWords[7],
            type: 'bar',
            barWidth: '7%',
            data: [this.chartChange.count[7]]
          },
          {
            name: this.chartChange.hotWords[8],
            type: 'bar',
            barWidth: '7%',
            data: [this.chartChange.count[8]]
          },
          {
            name: this.chartChange.hotWords[9],
            type: 'bar',
            barWidth: '7%',
            data: [this.chartChange.count[9]]
          }
        ],
        color: '#3398DB'
      }
      this.myChart.setOption(this.echartsData)
    }
  }
}

</script>
<style lang="scss" scoped>
  .hotWordsStatistics {
    padding: 10px;
    background: #fff;
    .text-title {
      height: 50px;
      line-height: 50px;
      color: #333;
    }
    .top {
      height: 50px;
      line-height: 50px;
      color: #333;

      .timeSelect {
        width: 140px;
        margin: 0 10px 0 2px;
      }
    }
    .text{
      color: #9a9a9a;
      text-align: center;
    }
  }
  #hotWordsCharts {
    width: 90%;
    height: 300px;
  }
</style>
