<template>
  <div>
    <!--上半部分-->
    <div class="div_style">
      <div class="labelItem">{{$t('assetsManage.sourceAnalysis')}}</div>
      <!--下拉-->
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
      <!--自定义时间-->
      <el-date-picker
        v-if="timeSelect===0"
        v-model="timeValue"
        type="daterange"
        size="small"
        @change="customDate"
        value-format="yyyyMMdd"
        range-separator="-"
        :start-placeholder="$t('tradesStatistics.startTime')"
        :end-placeholder="$t('tradesStatistics.endTime')"
        class="custom"
      >
      </el-date-picker>
      <span>{{this.startDate.year}}{{$t('userStatistics.year')}}{{this.startDate.month}}{{$t('userStatistics.month')}}{{this.startDate.day}}{{$t('userStatistics.day')}} - {{this.endDate.year}}{{$t('userStatistics.year')}}{{this.endDate.month}}{{$t('userStatistics.month')}}{{this.endDate.day}}{{$t('userStatistics.day')}}</span>
      <!--来源标签-->
      <div style="display:flex">
        <div
          class="label_style"
          v-for="(item, index) in this.span_data"
          :key="index"
        >
          <div
            :id="item.name"
            :class="spanStyle"
            @click="changeData(item.name, index)"
          >{{item.name}}</div>
        </div>
      </div>
      <!-- 柱状图部分 -->
      <div class="charts">
        <ve-histogram
          :data="chartData"
          :legend-visible="false"
        >
          <div
            class="data-empty"
            v-if="emptyChar"
          >{{$t('userportrait.noData')}}</div>
        </ve-histogram>
      </div>

    </div>
    <!--下半部分-->
    <div class="div_style">
      <!--来源下拉-->
      <el-select
        v-model="param1.sourceId"
        size="small"
        clearable
        @change="sourceChangeHandler"
        class="timeSelect"
      >
        <el-option
          v-for="item in selectData"
          :key="item.key"
          :label="item.name"
          :value="item.key"
        ></el-option>
      </el-select>
      <!--时间下拉-->
      <el-select
        v-model="timeSelect1"
        size="small"
        clearable
        @change="dateChangeHandler1"
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
        v-if="timeSelect1===0"
        v-model="timeValue1"
        type="daterange"
        size="small"
        @change="customDate1"
        value-format="yyyyMMdd"
        range-separator="-"
        :start-placeholder="$t('tradesStatistics.startTime')"
        :end-placeholder="$t('tradesStatistics.endTime')"
        class="custom"
      >
      </el-date-picker>
      <span>{{this.startDate1.year}}{{$t('userStatistics.year')}}{{this.startDate1.month}}{{$t('userStatistics.month')}}{{this.startDate1.day}}{{$t('userStatistics.day')}} - {{this.endDate1.year}}{{$t('userStatistics.year')}}{{this.endDate1.month}}{{$t('userStatistics.month')}}{{this.endDate1.day}}{{$t('userStatistics.day')}}</span>
      <!-- 折线图部分 -->
      <div class="charts">
        <ve-line
          :data="lineData"
          :settings="chartSettings"
          :legend-visible="false"
        >
          <div
            class="data-empty"
            v-if="emptyLine"
          >{{$t('userportrait.noData')}}</div>
        </ve-line>
      </div>

    </div>
  </div>
</template>

<script>
import { distributionAnalysis, sourceSelect, sourceAnalysis } from '@/api/admin/firstWebManage/visitAnalysis/visitAnalysis.js'

export default {
  watch: {
    lang () {
      this.timeRange = this.$t('tradesStatistics.timeRange')
    }
  },
  created () {
    this.initPageData()
    this.getSourceSelect()
  },

  mounted () {
    this.langDefault()
  },

  data () {
    this.chartSettings = {
      xAxisType: 'time'
    }
    return {
      selectData: [],
      chartSettings: {},
      arrayData: -1,
      isChange: true,
      timeSelect: 1,
      timeSelect1: 1,
      timeValue: [],
      timeValue1: [],
      timeRange: this.$t('tradesStatistics.timeRange'),
      value: 1,
      screeningTime: '1',
      spanStyle: 'span_normal',
      // 请求入参
      param: {
        type: 1,
        startDate: '',
        endDate: '',
        // 需要忽略的访问来源id
        cancelBtn: []
      },
      param1: {
        type: 1,
        startDate: '',
        endDate: '',
        sourceId: 1
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
      startDate1: {
        year: '',
        month: '',
        day: ''
      },
      endDate1: {
        year: '',
        month: '',
        day: ''
      },
      span_data: [],
      histogram_data: [],
      histogram: Object,
      // 柱状图数据
      chartData: {
        columns: ['name', 'value'],
        rows: []
      },
      emptyChar: true,
      lineData: {
        columns: ['refDate', 'openTimes'],
        rows: []
      },
      emptyLine: true
    }
  },

  methods: {
    // 初始化上下数据
    initPageData () {
      this.initData()
      this.getSourceAnalysis()
    },
    // 获取折线图数据
    getSourceAnalysis () {
      this.param1.type = this.screeningTime
      sourceAnalysis(this.param1).then(res => {
        if (res.error === 0) {
          this.startDate1.year = res.content.startDate.substring(0, 4)
          this.startDate1.month = res.content.startDate.substring(4, 6)
          this.startDate1.day = res.content.startDate.substring(6, 8)
          this.endDate1.year = res.content.endDate.substring(0, 4)
          this.endDate1.month = res.content.endDate.substring(4, 6)
          this.endDate1.day = res.content.endDate.substring(6, 8)
          this.lineData.rows = res.content.lineChart
          if (this.lineData.rows.length === 0) {
            this.emptyLine = true
          } else {
            this.emptyLine = false
          }
        }
      }).catch(err => console.log(err))
    },
    // 来源下拉更改
    sourceChangeHandler () {
      this.getSourceAnalysis()
    },
    // 来源下拉
    getSourceSelect () {
      sourceSelect(this.param).then(res => {
        if (res.error === 0) {
          this.selectData = res.content
        }
      }).catch(err => console.log(err))
    },
    // 自定义时间-上
    customDate () {
      this.screeningTime = 0
      this.param.startDate = this.timeValue[0].substring(0, 4) + this.timeValue[0].substring(4, 6) + this.timeValue[0].substring(6, 8)
      this.param.endDate = this.timeValue[1].substring(0, 4) + this.timeValue[1].substring(4, 6) + this.timeValue[1].substring(6, 8)
      console.log('选择器的时间：', this.param)
      this.isChange = true
      this.param.cancelBtn = []
      this.cancelStyle()
      this.initData()
    },
    // 自定义时间-下
    customDate1 () {
      this.screeningTime = 0
      this.param1.startDate = this.timeValue1[0].substring(0, 4) + this.timeValue1[0].substring(4, 6) + this.timeValue1[0].substring(6, 8)
      this.param1.endDate = this.timeValue1[1].substring(0, 4) + this.timeValue1[1].substring(4, 6) + this.timeValue1[1].substring(6, 8)
      console.log('选择器的时间：', this.param)
      this.getSourceAnalysis()
    },
    cancelStyle () {
      this.span_data.map((item, index) => {
        document.getElementById(item.name).setAttribute('class', 'span_normal')
      })
    },
    // 指定时间段-上
    dateChangeHandler (time) {
      if (time !== 0) {
        this.screeningTime = time
        this.isChange = true
        this.param.cancelBtn = []
        this.cancelStyle()
        this.initData()
      }
    },
    // 指定时间段-下
    dateChangeHandler1 (time) {
      if (time !== 0) {
        this.screeningTime = time
        this.getSourceAnalysis()
      }
    },

    initData () {
      this.param.type = this.screeningTime
      distributionAnalysis(this.param).then(res => {
        if (res.error === 0) {
          this.startDate.year = res.content.startDate.substring(0, 4)
          this.startDate.month = res.content.startDate.substring(4, 6)
          this.startDate.day = res.content.startDate.substring(6, 8)
          this.endDate.year = res.content.endDate.substring(0, 4)
          this.endDate.month = res.content.endDate.substring(4, 6)
          this.endDate.day = res.content.endDate.substring(6, 8)
          this.histogram_data = res.content.accessSourceSessionCnt
          this.histogram_data.map((item, index) => {
            this.histogram[index] = item.key
            delete item.key
            delete item.isShow
          })
          this.chartData.rows = this.histogram_data
          if (this.chartData.rows.length === 0) {
            this.emptyChar = true
          } else {
            this.emptyChar = false
          }
          if (this.isChange === true) {
            this.span_data = this.histogram_data
            this.isChange = false
          }
        }
      }).catch(err => console.log(err))
    },
    changeData (name, index) {
      // 动态点击改变柱状图数据
      console.log(name)
      if (document.getElementById(name).className === 'span_cancel') {
        this.param.cancelBtn.map((item, index1) => {
          if (item === this.histogram[index]) {
            this.arrayData = index1
          }
        })
        this.param.cancelBtn.splice(this.arrayData, 1)
        document.getElementById(name).setAttribute('class', 'span_normal')
        this.initData()
      } else if (document.getElementById(name).className === 'span_normal') {
        this.param.cancelBtn.push(this.histogram[index])
        document.getElementById(name).setAttribute('class', 'span_cancel')
        this.initData()
      }
    }
  }
}

</script>
<style lang="scss" scoped>
.labelItem {
  height: 50px;
  line-height: 50px;
  color: #333;
  margin-left: 20px;
}
.div_style {
  margin-left: 10px;
  margin-top: 10px;
  background: #fff;
  border: #9f9f9f;
  padding: 10px;
  .labelItem {
    height: 50px;
    line-height: 50px;
    color: #333;
  }
  .timeSelect {
    width: 140px;
    margin: 0 10px 0 30px;
  }
}
.label_style {
  margin-left: 20px;
  margin-top: 30px;
  margin-bottom: 30px;
}
.span_normal {
  padding: 7px 15px;
  margin-right: 10px;
  border: 1px solid #5a8bff;
  border-radius: 30px;
  cursor: pointer;
  margin-bottom: 10px;
}
.span_cancel {
  padding: 7px 15px;
  margin-right: 10px;
  color: #9a9a9a;
  border: 1px solid #afafb2;
  border-radius: 30px;
}

.data-empty {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(255, 255, 255, 0.7);
  color: #888;
  font-size: 14px;
}

.charts {
  margin-left: 5%;
  width: 80%;
  height: 500px;
}
</style>
