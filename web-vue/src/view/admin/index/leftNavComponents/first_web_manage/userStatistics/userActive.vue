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
              style="width: 400px;height: 90px;line-height: 30px;font-size: 14px;"
            >
              <section style="display: flex">
                <div style="width: 30%;color:#999">{{item.title}}</div>
                <div style="width: 70%;color: #353535">{{item.content}}</div>
              </section>
              <section style="display: flex">
                <div style="width: 30%;color:#999">{{item.title1}}</div>
                <div style="width: 70%;color: #353535">{{item.content1}}</div>
              </section>
            </div>
            <i class="el-icon-warning-outline icons"></i>
          </el-tooltip>
        </div>
        <div
          class="num"
          style="color: #5A8BFF"
        >{{item.number}}</div>
        <div>占比 {{item.rate}}</div>
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
      this.originalData.accessNumberRate = this.numberChange(data.loginDataRate)
      // 领券会员数
      this.originalData.getCouponNumber = data.couponData
      this.originalData.getCouponNumberRate = this.numberChange(data.couponDataRate)
      // 加购会员数
      this.originalData.addBuyNumber = data.cartData
      this.originalData.orderUserDataRate = this.numberChange(data.orderUserDataRate)
      // 成交会员数
      this.originalData.successNumber = data.orderUserData
      this.originalData.successNumberRate = this.numberChange(data.orderUserDataRate)

      data.activeDailyVo.map(item => {
        this.chartDateList.push(item.refDate)
        this.chartAccessNumber.push(item.loginData)
        this.chartGetCouponNumber.push(item.couponData)
        this.chartAddBuyNumber.push(item.cartData)
        this.chartSuccessNumber.push(item.orderUserData)
      })

      this.table = [
        {
          name: '访问会员数',
          title: '访问会员数',
          content: '筛选时间内，访问过店铺的用户数量,一人多次访问记为一人',
          title1: '访问会员数占比',
          content1: '筛选时间内，访问用户数 / 累积用户数',
          number: this.originalData.accessNumber,
          rate: this.originalData.accessNumberRate
        },
        {
          name: '领券会员数',
          title: '领券会员数',
          content: '筛选时间内，领取了优惠券的用户数，一人多次领券记为一人',
          title1: '领券会员数占比',
          content1: '筛选时间内，领券用户数/ 访问用户数',
          number: this.originalData.getCouponNumber,
          rate: this.originalData.getCouponNumberRate
        },
        {
          name: '加购会员数',
          title: '加购会员数',
          content: '筛选时间内，将商品添加购物车的用户数，一人多次添加购物车记为一人',
          title1: '加购会员数占比',
          content1: '筛选时间内，加购用户数/ 访问用户数',
          number: this.originalData.addBuyNumber,
          rate: this.originalData.orderUserDataRate
        },
        {
          name: '成交会员数',
          title: '成交会员数',
          content: '筛选时间内，付款成功的用户数，一人多次付款成功记为一人',
          title1: '成交会员数占比',
          content1: '筛选时间内，成交用户数/ 访问用户数',
          number: this.originalData.successNumber,
          rate: this.originalData.successNumberRate
        }
      ]

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
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '访问会员数 ',
            type: 'line',
            data: this.chartAccessNumber
          },
          {
            name: '领券会员数',
            type: 'line',
            data: this.chartGetCouponNumber
          },
          {
            name: '加购会员数',
            type: 'line',
            data: this.chartAddBuyNumber
          },
          {
            name: '成交会员数',
            type: 'line',
            data: this.chartSuccessNumber
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
