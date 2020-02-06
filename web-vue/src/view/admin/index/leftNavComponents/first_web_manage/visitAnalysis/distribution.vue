<template>
  <div class="distribution">
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
    </div>
    <!--图表数据-->
    <div class="image_chart">
      <div class="left"  id="leftCharts">
        <div>{{$t('visitAnalysis.visitSource')}}</div>
      </div>
      <div  class="mid" id="midCharts">
        <div>{{$t('visitAnalysis.visitTime')}}</div>
      </div>
      <div class="right" id="rightCharts">
        <div>{{$t('visitAnalysis.visitDepth')}}</div>
      </div>
    </div>
  </div>
</template>

<script>
import echarts from 'echarts'
import {distributionAnalysis} from '@/api/admin/firstWebManage/visitAnalysis/visitAnalysis.js'

export default {
  components: {},

  data () {
    return {
      custom: true,
      timeValue: [],
      timeSelect: 7,
      timeRange: [
        { value: 7, label: this.$t('visitAnalysis.lastSeven') },
        { value: 30, label: this.$t('visitAnalysis.lastThirty') },
        { value: 0, label: this.$t('visitAnalysis.custom') }
      ],
      param: {
        type: '7',
        startDate: '',
        endDate: '',
        cancelBtn: []
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
      leftChart: {},
      midChart: {},
      rightChart: {},
      controlMid: false
    }
  },
  created () {
    this.loadData()
  },
  mounted () {
    this.leftChart = echarts.init(document.getElementById('leftCharts'))
    this.midChart = echarts.init(document.getElementById('midCharts'))
    this.rightChart = echarts.init(document.getElementById('rightCharts'))
  },
  methods: {
    // time下拉框变化
    timeChangeHandler (time) {
      console.log(time)
      if (time === 7 || time === 30) {
        this.param.type = time
        this.loadData()
      } else {
        this.param.type = time
      }
    },
    // 自定义时间
    customDate () {
      console.log('datePicker：', this.timeValue)
      this.param.startDate = this.timeValue[0]
      this.param.endDate = this.timeValue[1]
      this.loadData()
    },
    // 页面初始化数据
    loadData () {
      distributionAnalysis(this.param).then(res => {
        console.log('distribution data:', res)
        if (res.error === 0) {
          this.handleData(res.content)
        }
      }).catch(err => console.log(err))
    },
    // 数据处理
    handleData (content) {
      console.log(content, 'content data')
      if (content.visitStayTime.max === 0) {
        this.controlMid = false
      } else {
        this.controlMid = true
      }
      console.log(this.controlMid)
      console.log(content.visitStayTime.max)
      this.averageNum = content.averageNum
      this.startDate.year = content.startDate.substring(0, 4)
      this.startDate.month = content.startDate.substring(4, 6)
      this.startDate.day = content.startDate.substring(6, 8)

      this.endDate.year = content.endDate.substring(0, 4)
      this.endDate.month = content.endDate.substring(4, 6)
      this.endDate.day = content.endDate.substring(6, 8)
      let circleData = content.accessSourceSessionCnt
      // 饼状图数据部分
      this.leftEchartsData = {
        title: {
          text: this.$t('visitAnalysis.visitSource'),
          left: 'center',
          textStyle: {
            color: '#a4a4a4',
            fontStyle: 'normal',
            fontWeight: 'normal',
            fontSize: 16
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        color: ['#7EC0EE', '#FF9F7F', '#FFD700', '#C9C9C9', '#E066FF', '#C0FF3E'],
        series: [
          {
            name: this.$t('visitAnalysis.visitSource'),
            type: 'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
              normal: {
                show: false,
                position: 'center'
              },
              emphasis: {
                show: true,
                textStyle: {
                  fontSize: '30',
                  fontWeight: 'bold'
                }
              }
            },
            labelLine: {
              normal: {
                show: false
              }
            },
            data: circleData
          }
        ]
      }
      this.leftChart.setOption(this.leftEchartsData)
      this.midEchartsData = {
        title: {
          text: this.$t('visitAnalysis.visitTime'),
          left: 'center',
          textStyle: {
            color: '#a4a4a4',
            fontStyle: 'normal',
            fontWeight: 'normal',
            fontSize: 16
          }
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        color: ['#7EC0EE'],
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01]
        },
        yAxis: {
          type: 'category',
          data: content.visitStayTime.yaxis
        },
        series: [
          {
            name: this.$t('visitAnalysis.sessionCount'),
            type: 'bar',
            data: content.visitStayTime.xaxis
          }
        ]
      }
      this.midChart.setOption(this.midEchartsData)
      this.rightEchartsData = {
        title: {
          text: this.$t('visitAnalysis.visitDepth'),
          left: 'center',
          textStyle: {
            color: '#a4a4a4',
            fontStyle: 'normal',
            fontWeight: 'normal',
            fontSize: 16
          }
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        color: ['#7EC0EE'],
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01]
        },
        yAxis: {
          type: 'category',
          data: content.visitDepth.yaxis
        },
        series: [
          {
            name: '打开次数',
            type: 'bar',
            data: content.visitDepth.xaxis
          }
        ]
      }
      this.rightChart.setOption(this.rightEchartsData)
    }
  }
}

</script>
<style lang="scss" scoped>
  .distribution {
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
    .image_chart{
      display:flex;
      height: 400px;
    }
    .noData{
      height: 300px;
      text-align: center;
      padding-top: 150px;
    }
  }
  #leftCharts {
    width: 30%;
    height: 380px;
  }
  #midCharts {
    width: 30%;
    height: 380px;
    margin-right: 50px;
  }
  #rightCharts {
    width: 30%;
    height: 380px;
  }
</style>
