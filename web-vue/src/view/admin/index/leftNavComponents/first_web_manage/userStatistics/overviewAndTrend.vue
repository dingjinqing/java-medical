<template>
  <!-- <div class="userStatistics">
    <div class="userContainer"> -->
  <section class="label overviewAndTrend">
    <div class="labelItem">客户概况及趋势</div>
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
    <span>2019年10月02日 - 2019年11月01日</span>

    <!-- 表格数据部分 -->
    <div class="fromWrapper">
      <div class="fromItem">
        <div
          class="fromInfo"
          style="display: flex;"
        >
          <div class="title">访客数</div>
          <el-tooltip
            class="item"
            effect="light"
            content="访客数"
            placement="top"
          >
            <i class="el-icon-warning-outline icons"></i>
          </el-tooltip>
        </div>
        <div
          class="num"
          style="color: #5A8BFF"
        >{{this.originalData.visitorsNumber}}</div>
        <div>较前一月 {{this.originalData.visitorsNumberRate}}</div>
      </div>
      <div class="fromItem">
        <div
          class="fromInfo"
          style="display: flex;"
        >
          <div class="title">累积用户数</div>
          <el-tooltip
            class="item"
            effect="light"
            content="累积用户数"
            placement="top"
          >
            <i class="el-icon-warning-outline icons"></i>
          </el-tooltip>
        </div>
        <div
          class="num"
          style="color: #fc6181;"
        >{{this.originalData.userNumber}}</div>
        <div>较前一月{{this.originalData.userNumberRate}}</div>
      </div>
      <div
        class="fromItem"
        style="display: flex;"
      >
        <div
          class="fromInfo"
          style="display: flex;"
        >
          <div class="title">用户成交数</div>
          <el-tooltip
            class="item"
            effect="light"
            content="用户成交数"
            placement="top"
          >
            <i class="el-icon-warning-outline icons"></i>
          </el-tooltip>
        </div>
        <div
          class="num"
          style="color: #fdb64a;"
        >{{this.originalData.tradeNumber}}</div>
        <div>较前一月{{this.originalData.tradeNumberRate}}</div>
      </div>
    </div>

    <!-- echarts图表部分 -->
    <div id="charts"></div>

  </section>
</template>

<script>
import echarts from 'echarts'
import { customerTrend } from '@/api/admin/firstWebManage/userStatistics/userStatistics.js'

export default {
  created () {
    this.initData()
  },

  mounted () {
    this.langDefault()
    this.myChart = echarts.init(document.getElementById('charts'))
  },

  data () {
    return {
      timeSelect: '',
      timeRange: [
        { value: 1, label: '最新1天' },
        { value: 7, label: '最新7天' },
        { value: 30, label: '最新30天' }
      ],
      params: '1',
      originalData: {
        visitorsNumber: '', // 访客数
        visitorsNumberRate: '',
        userNumber: '', // 累积用户访客数
        userNumberRate: '',
        tradeNumber: '', // 成交用户数
        tradeNumberRate: ''
      },
      chartDateList: [],
      chartVisitorsNumber: [], // 图表访客数
      chartUserNumber: [], // 图表累积用户访客数
      chartTradeNumber: [], // 图表成交用户数
      myChart: {}
    }
  },

  methods: {
    dateChangeHandler (time) {
      this.params = time
      this.initData()
    },

    initData () {
      customerTrend({ 'type': this.params }).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.originalData = res.content
          this.handleData(this.originalData)
        }
      }).catch(err => console.log(err))
    },

    handleData (data) {
      console.log(data)
      // 访客数
      this.originalData.visitorsNumber = data.loginDataTotal
      if (data.loginDataRate > 0) {
        this.originalData.visitorsNumberRate = '  ↑ ' + (data.loginDataRate * 100).toFixed(2) + '%'
      } else {
        this.originalData.visitorsNumberRate = '↓' + Math.abs((data.loginDataRate * 100)).toFixed(2) + '%'
      }
      // 累积用户数
      this.originalData.userNumber = data.userDataTotal
      if (data.userDataRate > 0) {
        this.originalData.userNumberRate = ' ↑' + (data.userDataRate * 100).toFixed(2) + '%'
      } else {
        this.originalData.userNumberRate = '↓' + Math.abs((data.userDataRate * 100)).toFixed(2) + '%'
      }
      // 用户成交数
      this.originalData.tradeNumber = data.orderUserDataTotal
      if (data.orderUserDataRate > 0) {
        this.originalData.tradeNumberRate = ' ↑' + (data.orderUserDataRate * 100).toFixed(2) + '%'
      } else {
        this.originalData.tradeNumberRate = '↓' + Math.abs((data.orderUserDataRate * 100)).toFixed(2) + '%'
      }

      data.trendDailyVo.map(item => {
        this.chartDateList.push(item.refDate)
        this.chartVisitorsNumber.push(item.loginData)
        this.chartUserNumber.push(item.userData)
        this.chartTradeNumber.push(item.orderUserData)
        // console.log(this.chartDateList)
        // console.log(this.chartVisitorsNumber)
        // console.log(this.chartUserNumber)
        // console.log(this.chartTradeNumber)
      })

      // // 折线图数据部分
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
          data: this.chartDateList
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '访客数',
            type: 'line',
            stack: '总量',
            data: this.chartVisitorsNumber
            // data: [34, 27, 33, 31, 42, 19, 11]
          },
          {
            name: '累积用户数',
            type: 'line',
            stack: '总量',
            data: this.chartUserNumber
            // data: [3404, 3410, 3423, 3435, 3441, 3450, 3456]
          },
          {
            name: '成交用户数',
            type: 'line',
            stack: '总量',
            data: this.chartTradeNumber
            // data: [4, 3, 5, 4, 11, 0, 0]
          }
        ]
      }

      this.myChart.setOption(this.echartsData)
    }

  }
}

</script>
<style lang="scss" scoped>
// .userStatistics {
//   padding: 10px;
//   font-size: 14px;
//   .userContainer {
//     padding: 10px;
//     position: relative;
//     background: #fff;
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
    // .fomrInfo {
    // display: flex;
    // .item {
    .icons {
      margin-left: 10px;
      position: relative;
    }
    // }
    .num {
      margin-top: 15px;
      font-size: 30px;
    }
    :nth-of-type(3) {
      margin-top: 10px;
    }
    // }
  }
}

#charts {
  width: 90%;
  height: 500px;
}
//   }
// }
</style>
