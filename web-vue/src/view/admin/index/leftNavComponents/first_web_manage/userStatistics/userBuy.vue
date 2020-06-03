<template>
  <section class="label">
    <div class="labelItem">{{$t('userStatistics.userBuyAgainTrend')}}</div>
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
    周
    <el-date-picker
      ref='time'
      v-model="defaultWeek"
      type="week"
      format="yyyy 第 WW 周"
      value-format="yyyy-MM-dd"
      :placeholder="$t('userStatistics.selectWeek')"
      @change="handleWeek"
      :picker-options="{firstDayOfWeek: 1}"
      size="small"
    >
    </el-date-picker>

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
    this.handleWeek()
    this.myChart = echarts.init(document.getElementById('userBuyCharts'))
  },
  computed: {},
  data () {
    return {
      timeSelect: this.$t('userStatistics.naturalWeek'),
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
      startTime: [],
      endTime: [],
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
      weekDate: []
    }
  },
  watch: {
    defaultWeek (newData) {
      this.$nextTick(() => {
        this.paramsObject.weekNum = Number(this.$refs['time'].$el.children[0].value.split(' ')[2])
        console.log(this.$refs['time'].$el.children[0].value, 'default week---')
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

    handleWeek (val) {
      console.log(val)
      const TuesdayDate = new Date(val).getTime()
      this.paramsObject.sunday = this.getDates(new Date(TuesdayDate + 5 * 86400000))
      this.initData()
    },

    initData () {
      console.log(this.paramsObject)
      userReBuy(this.paramsObject).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.originalData = res.content
          this.handleData(res.content)
        }
      }).catch(err => console.log(err))
    },

    // 处理接口返回来的数据
    handleData (data) {
      console.log(data)
      this.weekDate = []
      this.weekNumList = []
      this.againBuyRate = []
      this.endTime = []
      data.rebuyWeekVo.map(item => {
        this.weekNumList.push(item.weekNum)
        this.againBuyRate.push(item.rebuyRate)
        this.startTime.push(item.startTime)
        this.endTime.push(item.endTime)
        this.weekDate.push(item.xaxis)
      })
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
          data: this.weekDate
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '客户复购率',
            type: 'line',
            data: this.againBuyRate
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
