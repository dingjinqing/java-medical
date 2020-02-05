<template>
  <div class="allAnalysis">
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
      {{$t('visitAnalysis.grading')}}：
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
        :start-placeholder="$t('visitAnalysis.startDate')"
        :end-placeholder="$t('visitAnalysis.endDate')"
        class="custom"
      >
      </el-date-picker>
      <span>{{this.startDate.year}}{{$t('visitAnalysis.years')}}{{this.startDate.month}}{{$t('visitAnalysis.months')}}{{this.startDate.day}}{{$t('visitAnalysis.days')}} - {{this.endDate.year}}{{$t('visitAnalysis.years')}}{{this.endDate.month}}{{$t('visitAnalysis.months')}}{{this.endDate.day}}{{$t('visitAnalysis.days')}}</span>
      <el-button type="primary" size="small" @click="exportData()">{{$t('visitAnalysis.export')}}</el-button>
    </div>
    <div v-show="this.param.action===1"><span>{{$t('visitAnalysis.totalSessionCount')}}:</span>{{this.totalNum}}</div>
    <div v-show="this.param.action===2"><span>{{$t('visitAnalysis.totalPv')}}:</span>{{this.totalNum}}</div>
    <div v-show="this.param.action===4"><span>{{$t('visitAnalysis.totalUvNew')}}:</span>{{this.totalNum}}</div>
    <div v-show="this.param.action===6"><span>{{$t('visitAnalysis.aveStayTime')}}:</span>{{this.averageNum}}</div>
    <div v-show="this.param.action===7"><span>{{$t('visitAnalysis.aveVisitDepth')}}:</span>{{this.averageNum}}</div>
    <!--    折线图数据-->
    <div v-show="this.controlShow" id="allAnalysisCharts"></div>
    <div class="noData" v-show="this.totalNum===0"><span>{{$t('visitAnalysis.noData')}}</span></div>
  </div>
</template>

<script>
import echarts from 'echarts'
import { download } from '@/util/excelUtil.js'
import {amountAnalysis, excelExport} from '@/api/admin/firstWebManage/visitAnalysis/visitAnalysis.js'

export default {
  components: {},

  data () {
    return {
      custom: true,
      timeValue: [],
      actionSelect: 1,
      actionRange: [
        { value: 1, label: this.$t('visitAnalysis.sessionCount') },
        { value: 2, label: this.$t('visitAnalysis.pv') },
        { value: 3, label: this.$t('visitAnalysis.uv') },
        { value: 4, label: this.$t('visitAnalysis.uvNew') },
        { value: 5, label: this.$t('visitAnalysis.stayTimeUv') },
        { value: 6, label: this.$t('visitAnalysis.stayTimePv') },
        { value: 7, label: this.$t('visitAnalysis.visitDepth') }
      ],
      timeSelect: 7,
      timeRange: [
        { value: 7, label: this.$t('visitAnalysis.lastSeven') },
        { value: 30, label: this.$t('visitAnalysis.lastThirty') },
        { value: 0, label: this.$t('visitAnalysis.custom') }
      ],
      gradeSelect: 1,
      gradeRange: [
        { value: 1, label: this.$t('visitAnalysis.day') },
        { value: 7, label: this.$t('visitAnalysis.week') },
        { value: 30, label: this.$t('visitAnalysis.month') }
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
      averageNum: '',
      controlShow: true
    }
  },
  created () {
    this.loadData()
  },
  mounted () {
    this.myChart = echarts.init(document.getElementById('allAnalysisCharts'))
  },
  methods: {
    // 数据导出
    exportData () {
      excelExport(this.param).then(res => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName.split(';')[1].split('=')[1]
        download(res, decodeURIComponent(fileName))
      }).catch(err => console.log(err))
    },

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
      console.log('datePicker：', this.timeValue)
      this.param.startDate = this.timeValue[0]
      this.param.endDate = this.timeValue[1]
      this.loadData()
    },
    // 页面初始化数据
    loadData () {
      amountAnalysis(this.param).then(res => {
        console.log('visitAnalysis', res)
        if (res.error === 0) {
          this.handleData(res.content)
        }
      }).catch(err => console.log(err))
    },
    // 数据处理
    handleData (content) {
      this.totalNum = content.totalNum
      if (this.totalNum === 0) {
        this.controlShow = false
      } else {
        this.controlShow = true
      }
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
  .allAnalysis {
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
