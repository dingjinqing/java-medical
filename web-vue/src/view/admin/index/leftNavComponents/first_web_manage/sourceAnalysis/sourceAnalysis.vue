<template>
  <div class="div_style">
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
      start-placeholder="开始日期"
      end-placeholder="结束日期"
      class="custom"
    >
    </el-date-picker>
    <span>{{this.startDate.year}}{{$t('userStatistics.year')}}{{this.startDate.month}}{{$t('userStatistics.month')}}{{this.startDate.day}}{{$t('userStatistics.day')}} - {{this.endDate.year}}{{$t('userStatistics.year')}}{{this.endDate.month}}{{$t('userStatistics.month')}}{{this.endDate.day}}{{$t('userStatistics.day')}}</span>
    <!--来源标签-->
    <div class="label_style"
         v-for="(item, index) in this.span_data"
         :key="index"
    >
      <span :id="item.name" :class="spanStyle" @click="changeData(item.name, index)">{{item.name}}</span>
    </div>
    <!-- 柱状图部分 -->
    <div class="charts"><ve-histogram :data="chartData"></ve-histogram></div>

  </div>
</template>

<script>
import {distributionAnalysis} from '@/api/admin/firstWebManage/visitAnalysis/visitAnalysis.js'

export default {
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
  },

  data () {
    return {
      arrayData: -1,
      isChange: true,
      timeSelect: 1,
      timeValue: [],
      timeRange: this.$t('tradesStatistics.timeRange'),
      value: 1,
      screeningTime: '1',
      spanStyle: 'span_normal',
      // 请求入参
      param: {
        type: 7,
        startDate: '',
        endDate: '',
        // 需要忽略的访问来源id
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
      span_data: [],
      histogram_data: [{'name': '小程序', 'value': 0}],
      histogram: Object,
      // 柱状图数据
      chartData: {
        columns: ['name', 'value'],
        rows: [
          {'name': '2020-01-16', 'value': 1393},
          {'name': '2020-01-17', 'value': 3530}
        ]
      }
    }
  },

  methods: {
    // 自定义时间
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
    cancelStyle () {
      this.span_data.map((item, index) => {
        document.getElementById(item.name).setAttribute('class', 'span_normal')
      })
    },
    // 指定时间段
    dateChangeHandler (time) {
      if (time !== 0) {
        this.screeningTime = time
        this.isChange = true
        this.param.cancelBtn = []
        this.cancelStyle()
        this.initData()
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
          console.log(res.content.accessSourceSessionCnt)
          this.histogram_data = res.content.accessSourceSessionCnt
          this.histogram_data.map((item, index) => {
            this.histogram[index] = item.key
            delete item.key
            delete item.isShow
          })
          this.chartData.rows = this.histogram_data
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
  .div_style {
    background: #fff;
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
  .label_style{
    margin-left: 20px;
    margin-top: 30px;
    margin-bottom: 30px;
  }
  .span_normal {
    padding: 7px 15px;
    margin-right: 10px;
    border: 1px solid #5A8BFF;
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

  .charts {
    margin-left: 5%;
    width: 80%;
    height: 500px;
  }
</style>
