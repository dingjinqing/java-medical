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
      this.chartChange = {
        date: [],
        number: []
      }
      this.param.type = time
      this.loadData()
    },
    // 页面初始化数据
    loadData () {
      getSelect(this.param).then(res => {
        console.log('访问趋势', res)
        if (res.error === 0) {
          console.log('数组数据', res.content.dailyData)
          this.handleData(res.content.dailyData)
        }
      }).catch(err => console.log(err))
    },
    // 数据处理
    handleData (dailyData) {
      dailyData.map(item => {
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
