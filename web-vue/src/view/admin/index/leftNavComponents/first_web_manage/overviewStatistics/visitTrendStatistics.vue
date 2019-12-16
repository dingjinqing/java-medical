<template>
  <div class="visitTrendStatistics">
    <!--    下拉框-->
    <div class="top">
    <!--      选择活动-->
      <el-select
        v-model="actionSelect"
        size="small"
        clearable
        @change="actionChangeHandler"
        class="actionSelect"
      >
        <el-option
          v-for="item in actionRange"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
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
    <!--    折线图数据-->
    <div id="charts"></div>
  </div>
</template>

<script>
import echarts from 'echarts'
import {getSelect} from '@/api/admin/firstWebManage/overviewStatistics/overviewStatistics.js'

export default {
  data () {
    return {
      custom: true,
      timeValue: [],
      actionSelect: 1,
      actionRange: [
        { value: 1, label: '累计访问用户数' },
        { value: 2, label: '打开次数' },
        { value: 3, label: '访问次数' },
        { value: 4, label: '访问人数' },
        { value: 5, label: '新访问用户数' },
        { value: 6, label: '分享次数' },
        { value: 7, label: '分享人数' },
        { value: 8, label: '人均停留时长' },
        { value: 9, label: '次均停留时长' }
      ],
      timeSelect: 7,
      timeRange: [
        { value: 7, label: '最近7天' },
        { value: 30, label: '最近30天' },
        { value: 0, label: '自定义' }
      ],
      param: {
        action: '1',
        type: '7',
        startTime: '',
        endTime: ''
      },
      myChart: {},
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
      table: [],
      originalData: []
    }
  },
  created () {
    this.loadData()
  },
  mounted () {
    this.myChart = echarts.init(document.getElementById('charts'))
  },
  methods: {
    // action下拉框变化
    actionChangeHandler (action) {
      this.chartChange = {
        date: [],
        number: []
      }
      this.param.action = action
      this.loadData()
    },
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
      console.log('选择器的时间：', this.timeValue)
      this.param.startTime = this.timeValue[0]
      this.param.endTime = this.timeValue[1]
      this.loadData()
    },
    // 页面初始化数据
    loadData () {
      getSelect(this.param).then(res => {
        console.log('访问趋势', res)
        if (res.error === 0) {
          this.handleData(res.content)
        }
      }).catch(err => console.log(err))
    },
    // 数据处理
    handleData (content) {
      this.startDate.year = content.startTime.substring(0, 4)
      this.startDate.month = content.startTime.substring(4, 6)
      this.startDate.day = content.startTime.substring(6, 8)

      this.endDate.year = content.endTime.substring(0, 4)
      this.endDate.month = content.endTime.substring(4, 6)
      this.endDate.day = content.endTime.substring(6, 8)
      content.dailyData.map(item => {
        this.chartChange.date.push(item.date)
        this.chartChange.number.push(item.number)
      })
      // 折线图数据部分
      this.echartsData = {
        tooltip: {
          trigger: 'axis'
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
            name: this.actionRange[this.param.action - 1].label,
            type: 'line',
            data: this.chartChange.number
          }
        ],
        color: '#91c7ae'
      }
      this.myChart.setOption(this.echartsData)
    }
  }
}

</script>
<style lang="scss" scoped>
  .visitTrendStatistics {
    padding: 10px;
    background: #fff;

    .top {
      height: 50px;
      line-height: 50px;
      color: #333;
      .actionSelect {
        width: 140px;
        margin: 0 10px 0 2px;
      }
      .timeSelect {
        width: 140px;
        margin: 0 10px 0 2px;
      }
    }
  }

  #charts {
    width: 90%;
    height: 300px;
  }
</style>
