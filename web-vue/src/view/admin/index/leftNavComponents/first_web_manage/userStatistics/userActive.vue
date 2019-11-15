<template>
  <section class="label">
    <div class="labelItem">用户活跃</div>
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
    <span>{{this.startDate.year}}年{{this.startDate.month}}月{{this.startDate.day}}日 - {{this.endDate.year}}年{{this.endDate.month}}月{{this.endDate.day}}日</span>

    <!-- 表格数据部分 -->
    <div class="fromWrapper">
      <div class="fromItem">
        <div
          class="fromInfo"
          style="display: flex;"
        >
          <div class="title">访问会员数</div>
          <el-tooltip
            class="item"
            effect="light"
            content="访问会员数"
            placement="top"
          >
            <i class="el-icon-warning-outline icons"></i>
          </el-tooltip>
        </div>
        <div
          class="num"
          style="color: #5A8BFF"
        >{{this.originalData.accessNumber}}</div>
        <div>占比{{this.originalData.accessNumberRate}}</div>
      </div>

      <div class="fromItem">
        <div
          class="fromInfo"
          style="display: flex;"
        >
          <div class="title">领券会员数</div>
          <el-tooltip
            class="item"
            effect="light"
            content="领券会员数"
            placement="top"
          >
            <i class="el-icon-warning-outline icons"></i>
          </el-tooltip>
        </div>
        <div
          class="num"
          style="color: #fc6181;"
        >{{this.originalData.getCouponNumber}}</div>
        <div>占比{{this.originalData.getCouponNumberRate}}</div>
      </div>

      <div
        class="fromItem"
        style="display: flex;"
      >
        <div
          class="fromInfo"
          style="display: flex;"
        >
          <div class="title">加购会员数</div>
          <el-tooltip
            class="item"
            effect="light"
            content="加购会员数"
            placement="top"
          >
            <i class="el-icon-warning-outline icons"></i>
          </el-tooltip>
        </div>
        <div
          class="num"
          style="color: #fdb64a;"
        >{{this.originalData.addBuyNumber}}</div>
        <div>占比{{this.originalData.addBuyNumberRate}}</div>
      </div>

      <div
        class="fromItem"
        style="display: flex;"
      >
        <div
          class="fromInfo"
          style="display: flex;"
        >
          <div class="title">成交会员数</div>
          <el-tooltip
            class="item"
            effect="light"
            content="成交会员数"
            placement="top"
          >
            <i class="el-icon-warning-outline icons"></i>
          </el-tooltip>
        </div>
        <div
          class="num"
          style="color: #fdb64a;"
        >{{this.originalData.successNumber}}</div>
        <div>占比{{this.originalData.successNumberRate}}</div>
      </div>
    </div>

    <!-- echarts图表部分 -->
    <div id="userActiveCharts"></div>

  </section>
</template>

<script>
import echarts from 'echarts'
import { userActive } from '@/api/admin/firstWebManage/userStatistics/userStatistics.js'

export default {
  created () {
    this.initData()
  },

  mounted () {
    this.langDefault()
    this.myUserChart = echarts.init(document.getElementById('userActiveCharts'))
  },

  data () {
    return {
      timeSelect: 1,
      timeRange: [
        { value: 1, label: '最新1天' },
        { value: 7, label: '最新7天' },
        { value: 30, label: '最新30天' }
      ],
      params: 1,
      originalData: {
        accessNumber: '', // 访问会员数
        accessNumberRate: '',
        getCouponNumber: '', // 领券会员数
        getCouponNumberRate: '',
        addBuyNumber: '', // 加购会员数
        addBuyNumberRate: '',
        successNumber: '', // 成交会员数
        successNumberRate: ''
      },
      chartDateList: [],
      chartAccessNumber: [], // 图表访问会员数
      chartGetCouponNumber: [], // 图表领券会员数
      chartAddBuyNumber: [], // 图表加购会员数
      chartSuccessNumber: [], // 图表成交会员数
      myUserChart: {},
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
    dateChangeHandler (time) {
      this.params = time
      // console.log(this.params)
      this.initData()
    },

    initData () {
      userActive({ 'type': this.params }).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.originalData = res.content
          this.handleData(this.originalData)
        }
      }).catch(err => console.log(err))
    },

    // 处理返回来的数据
    handleData (data) {
      this.startDate.year = data.startTime.split('-')[0]
      this.startDate.month = data.startTime.split('-')[1]
      this.startDate.day = data.startTime.split('-')[2]

      this.endDate.year = data.endTime.split('-')[0]
      this.endDate.month = data.endTime.split('-')[1]
      this.endDate.day = data.endTime.split('-')[2]

      console.log(data)
      // 访问会员数
      this.originalData.accessNumber = data.loginData
      if (data.loginDataRate > 0) {
        this.originalData.accessNumberRate = '↑' + (data.loginDataRate * 100).toFixed(2) + '%'
      } else {
        this.originalData.accessNumberRate = '↓ ' + Math.abs((data.loginDataRate * 100)).toFixed(2) + '%'
      }
      // 领券会员数
      this.originalData.getCouponNumber = data.couponData
      if (data.couponDataRate > 0) {
        this.originalData.getCouponNumberRate = '↑' + (data.couponDataRate * 100).toFixed(2) + '%'
      } else {
        this.originalData.getCouponNumberRate = '↓ ' + Math.abs((data.couponDataRate * 100)).toFixed(2) + '%'
      }
      // 加购会员数
      this.originalData.addBuyNumber = data.cartData
      if (data.orderUserDataRate > 0) {
        this.originalData.addBuyNumberRate = '↑' + (data.cartDataRate * 100).toFixed(2) + '%'
      } else {
        this.originalData.addBuyNumberRate = '↓ ' + Math.abs((data.cartDataRate * 100)).toFixed(2) + '%'
      }
      // 成交会员数
      this.originalData.successNumber = data.orderUserData
      if (data.orderUserDataRate > 0) {
        this.originalData.successNumberRate = '↑' + (data.orderUserDataRate * 100).toFixed(2) + '%'
      } else {
        this.originalData.successNumberRate = '↓ ' + Math.abs((data.orderUserDataRate * 100)).toFixed(2) + '%'
      }

      data.activeDailyVo.map(item => {
        this.chartDateList.push(item.refDate)
        this.chartAccessNumber.push(item.loginData)
        this.chartGetCouponNumber.push(item.couponData)
        this.chartAddBuyNumber.push(item.cartData)
        this.chartSuccessNumber.push(item.orderUserData)
      })

      // // 折线图数据部分
      this.echartsData = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['访问会员数 ', '领券会员数', '加购会员数', '成交会员数']
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
          // data: ['周一', '周2', '周3', '周4', '周5', '周6', '周7']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '访问会员数 ',
            type: 'line',
            stack: '总量',
            data: this.chartAccessNumber
            // data: [34, 27, 33, 31, 42, 19, 11]
          },
          {
            name: '领券会员数',
            type: 'line',
            stack: '总量',
            data: this.chartGetCouponNumber
            // data: [3404, 3410, 3423, 3435, 3441, 3450, 3456]
          },
          {
            name: '加购会员数',
            type: 'line',
            stack: '总量',
            data: this.chartAddBuyNumber
            // data: [4, 3, 5, 4, 11, 0, 0]
          },
          {
            name: '成交会员数',
            type: 'line',
            stack: '总量',
            data: this.chartSuccessNumber
            // data: [4, 3, 5, 44, 113, 45, 89]
          }
        ]
      }

      this.myUserChart.setOption(this.echartsData)
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

#userActiveCharts {
  width: 90%;
  height: 500px;
}
</style>
