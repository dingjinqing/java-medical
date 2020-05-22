<template>
  <section class="label">
    <div class="labelItem">{{$t('assetsManage.overviewTrends')}} <el-button
        type="text"
        @click="toDetail()"
      >{{$t('assetsManage.details')}}</el-button>
    </div>
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

    <!-- 表格数据部分 -->
    <div class="fromWrapper">
      <div
        class="fromItem"
        v-for="item in table"
        :key="item.number"
      >
        <div
          class="fromInfo"
          style="display: flex;"
        >
          <div>{{item.name}}</div>
          <el-tooltip
            effect="light"
            placement="top"
          >
            <div
              slot="content"
              style="width: 250px;line-height: 30px;font-size: 14px"
            >
              {{item.content}}
            </div>
            <i class="el-icon-warning-outline icons"></i>
          </el-tooltip>
        </div>
        <div
          class="num"
          style="color: #5A8BFF"
        >{{item.number}} {{$t('assetsManage.integral')}}</div>
        <div>{{$t('assetsManage.previous')}} {{item.rate}}</div>
      </div>
    </div>
    <!-- echarts图表部分 -->
    <ve-line
      :data="chartData"
      :settings="chartSettings"
      style="width:90%;margin-left:20px"
    ></ve-line>
  </section>
</template>

<script>
import { scoreManage } from '@/api/admin/firstWebManage/assetsManage/assetsManage.js'

export default {
  components: {},

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
    this.chartSettings = {
      metrics: ['净收入', '总收入', '总支出'],
      dimension: ['refDate']
    }
    return {
      chartData: {
        columns: ['refDate', '净收入', '总收入', '总支出'],
        rows: [
          { 'refDate': '1970-01-01', '净收入': 0, '总收入': 0, '总支出': 0 }
        ]
      },
      timeSelect: 1,
      timeValue: [],
      timeRange: this.$t('tradesStatistics.timeRange'),
      value: 1,
      screeningTime: '1',
      originalData: {
        incomeRealScore: '', // 净积分收入
        incomeRealScorePer: '',
        incomeTotalScore: '', // 总积分收入
        incomeTotalScorePer: '',
        outgoScore: '', // 总积分支出
        outgoScorePer: '',
        revenueDates: []
      },
      // 请求入参
      param: {
        screeningTime: 1,
        startTime: '',
        endTime: '',
        tradeContent: 1
      },
      table: [],
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

  methods: {
    // 跳转明细页面
    toDetail () {
      this.$router.push({
        name: 'asset_summary_detail',
        params: {
          flag: 1
        }
      })
    },
    // 自定义时间
    customDate () {
      this.screeningTime = 0
      this.param.startTime = this.timeValue[0].substring(0, 4) + '-' + this.timeValue[0].substring(4, 6) + '-' + this.timeValue[0].substring(6, 8)
      this.param.endTime = this.timeValue[1].substring(0, 4) + '-' + this.timeValue[1].substring(4, 6) + '-' + this.timeValue[1].substring(6, 8)
      console.log('选择器的时间：', this.param)
      this.initData()
    },
    // 指定时间段
    dateChangeHandler (time) {
      if (time !== 0) {
        this.screeningTime = time
        this.initData()
      }
    },

    initData () {
      this.param.screeningTime = this.screeningTime
      scoreManage(this.param).then(res => {
        if (res.error === 0) {
          this.originalData = res.content
          this.handleData(this.originalData)
        }
      }).catch(err => console.log(err))
    },

    numberChange (number) {
      let str
      if (number > 0) {
        str = '↑' + Number(number * 100).toFixed(2) + '%'
      } else if (number < 0) {
        str = '↓' + Math.abs(Number(number * 100)).toFixed(2) + '%'
      } else {
        str = '--'
      }
      return str
    },
    handleData (data) {
      this.startDate.year = data.startTime.split('-')[0]
      this.startDate.month = data.startTime.split('-')[1]
      this.startDate.day = data.startTime.split('-')[2]
      console.log('起始时间' + this.startDate)
      this.endDate.year = data.endTime.split('-')[0]
      this.endDate.month = data.endTime.split('-')[1]
      this.endDate.day = data.endTime.split('-')[2]
      console.log('结束时间' + this.endDate)

      // 净收入
      // this.originalData.incomeRealScore = data.incomeRealScore === null ? 0 : data.incomeRealScore
      this.originalData.incomeRealScore = data.incomeRealScore
      this.originalData.incomeRealScorePer = this.numberChange(data.incomeRealScorePer)
      // 总收入
      // this.originalData.incomeTotalScore = data.incomeTotalScore === null ? 0 : data.incomeTotalScore
      this.originalData.incomeTotalScore = data.incomeTotalScore
      this.originalData.incomeTotalScorePer = this.numberChange(data.incomeTotalScorePer)
      // 总支出
      // this.originalData.outgoScore = data.outgoScore === null ? 0 : data.outgoScore
      this.originalData.outgoScore = data.outgoScore
      this.originalData.outgoScorePer = this.numberChange(data.outgoScorePer)

      this.table = [
        {
          name: this.$t('assetsManage.income'),
          content: this.$t('userStatistics.content1'),
          number: this.originalData.incomeRealScore,
          rate: this.originalData.incomeRealScorePer
        },
        {
          name: this.$t('assetsManage.revenue'),
          content: this.$t('userStatistics.content2'),
          number: this.originalData.incomeTotalScore,
          rate: this.originalData.incomeTotalScorePer
        },
        {
          name: this.$t('assetsManage.expenses'),
          content: this.$t('userStatistics.content3'),
          number: this.originalData.outgoScore,
          rate: this.originalData.outgoScorePer
        }
      ]

      // 折线图数据部分
      if (data.revenueDates === []) {
        this.chartData.rows = [
          { 'refDate': '2020-01-01', '净收入': 0, '总收入': 0, '总支出': 0 }
        ]
      } else {
        this.chartData.rows = data.revenueDates
        this.chartData.rows.map(item => {
          item.净收入 = item.incomeRealScore
          item.总收入 = item.incomeTotalScore
          item.总支出 = item.outgoScore
        })
      }
    }

  }
}
</script>
<style lang="scss" scoped>
.label {
  background: #fff;
  padding: 10px;
  .labelItem {
    height: 50px;
    line-height: 50px;
    color: #333;
  }
  .timeSelect {
    width: 140px;
    margin: 0 10px 0 2px;
  }
}
.fromWrapper {
  border: 1px solid #eee;
  height: 130px;
  width: 85%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 30px auto 50px;
  .fromItem {
    flex: 1;
    height: 130px;
    position: relative;
    border-right: 1px solid #eee;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    .icons {
      margin-left: 10px;
      position: relative;
    }
    .num {
      margin-top: 15px;
      font-size: 30px;
    }
    :nth-of-type(3) {
      margin-top: 10px;
    }
  }
}

#charts1 {
  width: 90%;
  height: 500px;
}
</style>
