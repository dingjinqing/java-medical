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
      粒度：
      <!--     选择粒度-->
      <el-select
        v-model="gradeSelect"
        size="small"
        clearable
        @change="gradeChangeHandler"
        class="gradeSelect"
      >
        <el-option
          v-for="item in gradeRange"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
      <!--自定义时间-->
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
      <el-button type="primary" size="small">数据导出</el-button>
    </div>
    <div v-show="this.param.action===1"><span>筛选时间范围内总打开次数:</span>{{this.totalNum}}</div>
    <div v-show="this.param.action===2"><span>筛选时间范围内总访问次数:</span>{{this.totalNum}}</div>
    <div v-show="this.param.action===4"><span>筛选时间范围内总新用户数:</span>{{this.totalNum}}</div>
    <div v-show="this.param.action===6"><span>筛选时间范围内次均停留时长:</span>{{this.averageNum}}</div>
    <div v-show="this.param.action===7"><span>筛选时间范围内平均访问深度:</span>{{this.averageNum}}</div>
    <!--    折线图数据-->
    <div v-show="this.totalNum" id="allAnalysisCharts"></div>
    <div class="noData" v-show="this.totalNum===0"><span>暂无相关数据</span></div>
  </div>
</template>

<script>
import echarts from 'echarts'
import {amountAnalysis} from '@/api/admin/firstWebManage/visitAnalysis/visitAnalysis.js'

export default {
  components: {},

  data () {
    return {
      custom: true,
      timeValue: [],
      actionSelect: 1,
      actionRange: [
        { value: 1, label: '打开次数' },
        { value: 2, label: '访问次数' },
        { value: 3, label: '访问人数' },
        { value: 4, label: '新用户数' },
        { value: 5, label: '人均停留时长' },
        { value: 6, label: '次均停留时长' },
        { value: 7, label: '平均访问深度' }
      ],
      timeSelect: 7,
      timeRange: [
        { value: 7, label: '最近7天' },
        { value: 30, label: '最近30天' },
        { value: 0, label: '自定义' }
      ],
      gradeSelect: 1,
      gradeRange: [
        { value: 1, label: '日' },
        { value: 7, label: '周' },
        { value: 30, label: '月' }
      ],
      param: {
        action: 1,
        type: '7',
        grading: '1',
        startDate: '',
        endDate: ''
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
      originalData: [],
      totalNum: '',
      averageNum: ''
    }
  },
  created () {
    this.loadData()
  },
  mounted () {
    this.myChart = echarts.init(document.getElementById('allAnalysisCharts'))
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
    // grade下拉框变化
    gradeChangeHandler (grade) {
      this.chartChange = {
        date: [],
        number: []
      }
      this.param.grading = grade
      this.loadData()
    },
    // 自定义时间
    customDate () {
      this.chartChange = {
        date: [],
        number: []
      }
      console.log('选择器的时间：', this.timeValue)
      this.param.startDate = this.timeValue[0]
      this.param.endDate = this.timeValue[1]
      this.loadData()
    },
    // 页面初始化数据
    loadData () {
      amountAnalysis(this.param).then(res => {
        console.log('访问分析', res)
        if (res.error === 0) {
          this.handleData(res.content)
        }
      }).catch(err => console.log(err))
    },
    // 数据处理
    handleData (content) {
      this.totalNum = content.totalNum
      this.averageNum = content.averageNum
      this.startDate.year = content.startDate.substring(0, 4)
      this.startDate.month = content.startDate.substring(4, 6)
      this.startDate.day = content.startDate.substring(6, 8)

      this.endDate.year = content.endDate.substring(0, 4)
      this.endDate.month = content.endDate.substring(4, 6)
      this.endDate.day = content.endDate.substring(6, 8)
      // content.dailyData.map(item => {
      //   this.chartChange.date.push(item.date)
      //   this.chartChange.number.push(item.number)
      // })
      this.chartChange.date = content.date
      this.chartChange.number = content.list
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
      .gradeSelect {
        width: 140px;
        margin: 0 10px 0 2px;
      }
    }
    .noData{
      height: 300px;
      text-align: center;
      padding-top: 150px;
    }
  }

  #allAnalysisCharts {
    width: 90%;
    height: 300px;
  }
</style>
