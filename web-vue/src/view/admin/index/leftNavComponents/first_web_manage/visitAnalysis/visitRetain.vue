<template>
  <!-- 用户留存 -->
  <div class="visitRetain">
    <!-- 下拉框 -->
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
        :start-placeholder="$t('visitAnalysis.startDate')"
        :end-placeholder="$t('visitAnalysis.endDate')"
        class="custom"
      >
      </el-date-picker>
      <span>{{this.startDate.year}}{{$t('visitAnalysis.years')}}{{this.startDate.month}}{{$t('visitAnalysis.months')}}{{this.startDate.day}}{{$t('visitAnalysis.days')}} - {{this.endDate.year}}{{$t('visitAnalysis.years')}}{{this.endDate.month}}{{$t('visitAnalysis.months')}}{{this.endDate.day}}{{$t('visitAnalysis.days')}}</span>
    </div>
    <!-- 表格 -->
    <table class="visitretain-table">
      <thead class="visitretain-thead">
        <tr>
<!--          时间-->
          <th>{{$t('visitAnalysis.time')}}</th>
<!--          新增用户数-->
          <th>{{$t('visitAnalysis.uvNew')}}</th>
<!--          一天后-->
          <th>1{{$t('visitAnalysis.dayLater')}}</th>
          <th>2{{$t('visitAnalysis.dayLater')}}</th>
          <th>3{{$t('visitAnalysis.dayLater')}}</th>
          <th>4{{$t('visitAnalysis.dayLater')}}</th>
          <th>5{{$t('visitAnalysis.dayLater')}}</th>
<!--          六天后-->
          <th>6{{$t('visitAnalysis.dayLater')}}</th>
        </tr>
      </thead>
      <tbody class="visitretain-tbody">
        <tr
          v-for="(item, index) in tableData"
          :key="index"
        >
          <td style="text-align:left;">{{item.refDate}}</td>
          <td>{{item.data[0]}}</td>
          <td
            v-for="(percent, percentIndex) in item.percents"
            :key="percentIndex"
            class="noborder"
            :class="{green: percent !== '', 'deep-green': percent > 0}"
          >{{percent}}%</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { retainAnalysis } from '@/api/admin/firstWebManage/visitAnalysis/visitAnalysis.js'

export default {
  components: {},

  data () {
    return {
      custom: true,
      timeValue: [],
      actionSelect: 2,
      actionRange: [
        { value: 1, label: this.$t('visitAnalysis.newRetention') },
        { value: 2, label: this.$t('visitAnalysis.activeRetention') }
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
        action: 2,
        type: '7',
        grading: '1',
        startDate: '',
        endDate: ''
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
      tableData: []
    }
  },
  created () {
    this.loadData()
  },
  mounted () {
  },
  methods: {
    // action下拉框变化
    actionChangeHandler (action) {
      this.param.action = action
      this.loadData()
    },
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
    // grade下拉框变化
    gradeChangeHandler (grade) {
      this.param.grading = grade
      this.loadData()
    },
    // 自定义时间
    customDate () {
      console.log('date picker：', this.timeValue)
      this.param.startDate = this.timeValue[0]
      this.param.endDate = this.timeValue[1]
      this.loadData()
    },
    // 页面初始化数据
    loadData () {
      retainAnalysis(this.param).then(res => {
        console.log('retain data:', res)
        if (res.error === 0) {
          this.handleData(res.content)
        }
      }).catch(err => console.log(err))
    },
    // 数据处理
    handleData (content) {
      this.startDate.year = content.startDate.substring(0, 4)
      this.startDate.month = content.startDate.substring(4, 6)
      this.startDate.day = content.startDate.substring(6, 8)

      this.endDate.year = content.endDate.substring(0, 4)
      this.endDate.month = content.endDate.substring(4, 6)
      this.endDate.day = content.endDate.substring(6, 8)

      console.log('content', content)
      this.tableData = content.data
      let datas = content.data
      datas.forEach((item, index) => {
        let percents = []
        let data = item.data
        for (const key in data) {
          if (data.hasOwnProperty(key) && key !== '0') {
            let first = data[0]
            const value = data[key]
            let percent = ''
            if (first === 0 || first === '0') {
              percent = parseFloat(0).toFixed(2)
            } else {
              percent = parseFloat(value / first * 100).toFixed(2)
            }
            percents.push(percent)
          }
        }
        item.percents = percents
      })
    }
  }
}

</script>
<style lang="scss" scoped>
.visitRetain {
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
  .noData {
    height: 300px;
    text-align: center;
    padding-top: 150px;
  }
}
.visitretain-table {
  width: 100%;
  margin: 0 5px;
  background-color: #fff;
  color: #666;
  border: 1px solid #e6e6e6;
  .visitretain-thead {
    background-color: #f2f2f2;
    th {
      position: relative;
      padding: 9px 15px;
      min-height: 20px;
      line-height: 20px;
      font-size: 14px;
      border: 1px solid #e6e6e6;
    }
  }
  .visitretain-tbody {
    tr {
      td {
        position: relative;
        padding: 9px 15px;
        min-height: 20px;
        line-height: 20px;
        font-size: 14px;
        border: 1px solid #e6e6e6;
        text-align: center;
      }
      .noborder {
        border: none;
      }
      .green {
        background-color: #d1efd1;
      }
      .deep-green {
        background-color: #76ce75 !important;
      }
    }
  }
}
</style>
