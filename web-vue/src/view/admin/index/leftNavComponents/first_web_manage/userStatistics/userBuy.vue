<template>
  <section class="label">
    <div class="labelItem">用户复购趋势</div>
    <el-select
      v-model="timeSelect"
      size="small"
      clearable
      class="timeSelect"
    >
      <el-option
        v-for="item in timeRange"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      ></el-option>
    </el-select>
    {{defaultWeek}}
    <el-date-picker
      ref='time'
      v-model="defaultWeek"
      type="week"
      format="yyyy 第 WW 周"
      value-format="yyyy-MM-dd"
      placeholder="选择周"
      @change="handleWeek"
      :picker-options="{firstDayOfWeek: 1}"
    >
    </el-date-picker>
    <span>{{this.startDate.year}}年{{this.startDate.month}}月{{this.startDate.day}}日 - {{this.endDate.year}}年{{this.endDate.month}}月{{this.endDate.day}}日</span>

    <!-- echarts图表部分 -->
    <div id="userBuyCharts"></div>

  </section>
</template>

<script>
import echarts from 'echarts'
import { userReBuy } from '@/api/admin/firstWebManage/userStatistics/userStatistics.js'

export default {
  created () {
    this.initData()
  },

  mounted () {
    this.langDefault()
    this.initData()
    this.getDefaultWeek()
    this.myChart = echarts.init(document.getElementById('userBuyCharts'))
  },
  computed: {

  },
  data () {
    return {
      timeSelect: '自然周',
      Monday: '',
      timeRange: [
        { value: 1, label: '自然周' }
      ],
      defaultWeek: new Date(),
      paramsObject: {
        weekNum: '',
        sunday: ''
      },
      myChart: {},
      weekNumList: [],
      againBuyRate: [],
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
  watch: {
    defaultWeek (newData) {
      this.$nextTick(() => {
        this.paramsObject.weekNum = Number(this.$refs['time'].$el.children[0].value.split(' ')[2])
        console.log(this.$refs['time'].$el.children[0].value)
      })
    }
  },
  methods: {

    // 获取日期
    getDates (str) {
      const oDate = new Date(str)
      const oYear = oDate.getFullYear()
      const oMonth = oDate.getMonth() + 1
      const oDay = oDate.getDate()
      const oTime = oYear + '-' + this.addZero(oMonth) + '-' + this.addZero(oDay)
      return oTime
    },

    // 补零操作
    addZero (num) {
      if (parseInt(num) < 10) {
        num = '0' + num
      }
      return num
    },

    getDefaultWeek () {
      var date = new Date()
      console.log(date)
      console.log(date.getTime())
    },

    handleWeek (val) {
      console.log(val)
      const TuesdayDate = new Date(val).getTime()
      // this.paramsObject.monday = this.getDates(new Date(TuesdayDate - 86400000))
      this.paramsObject.sunday = this.getDates(new Date(TuesdayDate + 5 * 86400000))
      this.initData()
    },

    initData () {
      // this.$nextTick(()=>{

      // })
      // console.log(this.$refs['time'].$el.children[0].value)

      console.log(this.paramsObject)
      userReBuy(
        this.paramsObject
      ).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.originalData = res.content
          this.handleData(this.originalData)
        }
      }).catch(err => console.log(err))
    },

    // 处理接口返回来的数据
    handleData (data) {
      // this.startDate.year = data.startTime.split('-')[0]
      // this.startDate.month = data.startTime.split('-')[1]
      // this.startDate.day = data.startTime.split('-')[2]

      // this.endDate.year = data.endTime.split('-')[0]
      // this.endDate.month = data.endTime.split('-')[1]
      // this.endDate.day = data.endTime.split('-')[2]
      console.log(data)
      data.rebuyWeekVo.map(item => {
        this.weekNumList.push(item.weekNum)
        console.log(this.weekNumList)
        this.againBuyRate.push(item.rebuyRate)
        console.log(this.again)
      })

      this.echartsData = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['访客数', '累积用户数', '成交用户数']
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
          // data: this.userDate
          data: ['访客数', '累积用户数', '成交用户数']
        },
        yAxis: {
          type: 'value'
        },
        // series
        series: [
          {
            name: '访客数',
            type: 'line',
            stack: '总量',
            // data: this.chartVisitorsNumber
            data: [34, 27, 33, 31, 42, 19, 11]
          },
          {
            name: '累积用户数',
            type: 'line',
            stack: '总量',
            // data: this.chartUserNumber
            data: [3404, 3410, 3423, 3435, 3441, 3450, 3456]
          },
          {
            name: '成交用户数',
            type: 'line',
            stack: '总量',
            // data: this.chartTradeNumber
            data: [4, 3, 5, 4, 11, 0, 0]
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
  padding: 10px;
  background: #fff;
  .labelItem {
    height: 50px;
    line-height: 50px;
    color: #333;
  }
  .timeSelect {
    width: 140px;
    margin: 0 10px 0 2px;
  }
  #userBuyCharts {
    width: 90%;
    padding-top: 20px;
    height: 500px;
  }
}
</style>
