<template>
  <div class="transactionData">
    <div class="top">
      <div>查询时间：</div>
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
      <el-date-picker
        v-if="timeSelect===0"
        v-model="timeValue"
        type="daterange"
        size="small"
        @change="customDate"
        value-format="yyyy-MM-dd"
        range-separator="-"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        class="custom"
      >
      </el-date-picker>

      <div class="tips">{{this.showStartTime}} - {{this.showEndTime}}</div>
    </div>
    <!--中部-->
    <div class="middle">
      <div
        class="each_eara_item"
        v-for="(item,index) in middleData"
        :key="index"
      >
        <div class="each_ares_title">
          {{item.title}}
        </div>
        <div
          class="each_ares_num"
          :style="index === 0?'color:#5A8BFF':index === 1?'color:#fc6181':index === 2?'color:#fdb64a':'color:#ff9f7f'"
        >{{item.num}}</div>
        <img
          :src="$imageHost+'/image/admin/any_coner/any_coner_blue.png'"
          class="each_area_bg"
        >
      </div>
    </div>
    <!--    折线图数据-->
    <div class="charts">
      <ve-line
        :data="chartData"
        :colors='appcolor'
      ></ve-line>
    </div>

  </div>
</template>
<script>
import { getMemberSardPurchase, getCouponpackPurchase, getRenewPurchase, getRechargePurchase } from '@/api/admin/memberManage/memberValueAdd/memberValueAdd.js'
export default {
  props: {
    activeName: {
      Type: String,
      default: ''
    }
  },
  data () {
    this.chartSettings = {
      xAxisType: 'time'
    }
    return {
      timeValue: [],
      timeSelect: 1,
      timeRange: [
        { value: 1, label: '今日' },
        { value: 2, label: '昨日' },
        { value: 7, label: '最近7天' },
        { value: 30, label: '最近30天' },
        { value: 0, label: '自定义' }
      ],
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
      param: {},
      chartChange: {
        date: [],
        number: []
      },
      chartData: {
        columns: ['日期', '成功支付单数', '成功支付人数', '支付金额', '退款金额'],
        rows: []
      },
      appcolor: ['#5A8BFF', '#fc6181', '#fdb64a', '#ff9f7f'],
      middleData: [
        {
          title: '成功支付单数',
          num: 0
        },
        {
          title: '成功支付人数',
          num: 0
        },
        {
          title: '支付金额',
          num: 0.00
        },
        {
          title: '退款金额',
          num: 0
        }
      ],
      startTime: '',
      endTime: '',
      showStartTime: '',
      showEndTime: ''
    }
  },
  mounted () {
    console.log(this.activeName)
    this.handleToQueryData(this.activeName)
  },
  methods: {
    // 自定义时间
    customDate () {
      // this.chartChange = {
      //   date: [],
      //   number: []
      // }
      console.log('选择器的时间：', this.timeValue)
      this.startTime = this.timeValue[0]
      this.endTime = this.timeValue[1]
      this.handleToQueryData(this.activeName)
    },
    // time下拉框变化
    timeChangeHandler (time) {
      console.log(time)
      if (time !== 0) {
        this.chartChange = {
          date: [],
          number: []
        }
        this.handleToQueryData(this.activeName)
      }
    },
    // 数据请求
    handleToQueryData (newData) {
      console.log(newData)
      console.log(this.getDay(0))
      let obj = {
        'startTime': null,
        'endTime': null
      }
      console.log(this.timeSelect)
      switch (this.timeSelect) {
        case 1:
          obj.startTime = this.getDay(0)
          obj.endTime = this.getDay(0)
          break
        case 2:
          obj.startTime = this.getDay(-1)
          obj.endTime = this.getDay(0)
          break
        case 7:
          obj.startTime = this.getDay(-7)
          obj.endTime = this.getDay(0)
          break
        case 30:
          obj.startTime = this.getDay(-30)
          obj.endTime = this.getDay(0)
          break
      }
      if (obj.startTime) {
        this.startTime = obj.startTime
        this.endTime = obj.endTime
      } else {
        obj.startTime = this.startTime
        obj.endTime = this.endTime
      }
      this.showStartTime = this.startTime.split('-')[0] + '年' + this.startTime.split('-')[1] + '月' + this.startTime.split('-')[2] + '日'
      this.showEndTime = this.endTime.split('-')[0] + '年' + this.endTime.split('-')[1] + '月' + this.endTime.split('-')[2] + '日'
      obj.startTime = obj.startTime + ' 00:00:00'
      // obj.endTime = obj.endTime + ' 23:59:59'
      switch (newData) {
        case 'first':
          if (this.timeSelect === 2) {
            obj.endTime = obj.endTime + ' 00:00:00'
          } else {
            obj.endTime = obj.endTime + ' 23:59:59'
          }
          getMemberSardPurchase(obj).then(res => {
            console.log(res)
            if (res.error === 0) {
              this.handleData(res.content)
            }
          })
          break
        case 'second':
          if (this.timeSelect === 2) {
            obj.endTime = obj.endTime + ' 00:00:00'
          } else {
            obj.endTime = obj.endTime + ' 23:59:59'
          }
          getRenewPurchase(obj).then(res => {
            console.log(res)
            if (res.error === 0) {
              this.handleData(res.content)
            }
          })
          break
        case 'third':
          if (this.timeSelect === 2) {
            obj.endTime = obj.endTime + ' 00:00:00'
          } else {
            obj.endTime = obj.endTime + ' 23:59:59'
          }
          getRechargePurchase(obj).then(res => {
            console.log(res)
            if (res.error === 0) {
              this.handleData(res.content)
            }
          })
          break
        case 'fourth':
          if (this.timeSelect === 2) {
            obj.endTime = obj.endTime + ' 00:00:00'
          } else {
            obj.endTime = obj.endTime + ' 23:59:59'
          }
          getCouponpackPurchase(obj).then(res => {
            console.log(res)
            if (res.error === 0) {
              this.handleData(res.content)
            }
          })
          break
      }
    },
    // 数据处理
    handleData (content) {
      this.middleData[0].num = content.total.totalPaidOrderNumber
      this.middleData[1].num = content.total.totalPaidUserNumber
      this.middleData[2].num = content.total.totalPaymentAmount
      this.middleData[3].num = content.total.totalReturnAmount
      let arr = []
      content.dateList.forEach((item, index) => {
        let obj = {}
        obj['日期'] = item
        arr.push(obj)
      })
      content.paidOrderNumber.forEach((item, index) => {
        arr[index]['成功支付单数'] = item
      })
      content.paidUserNumber.forEach((item, index) => {
        arr[index]['成功支付人数'] = item
      })
      content.paymentAmount.forEach((item, index) => {
        arr[index]['支付金额'] = item
      })
      content.returnAmount.forEach((item, index) => {
        arr[index]['退款金额'] = item
      })
      console.log(arr)
      this.chartData.rows = arr
      //   this.startDate.year = content.startTime.substring(0, 4)
      //   this.startDate.month = content.startTime.substring(4, 6)
      //   this.startDate.day = content.startTime.substring(6, 8)

      //   this.endDate.year = content.endTime.substring(0, 4)
      //   this.endDate.month = content.endTime.substring(4, 6)
      //   this.endDate.day = content.endTime.substring(6, 8)
      //   content.dailyData.map(item => {
      //     this.chartChange.date.push(item.date)
      //     this.chartChange.number.push(item.number)
      //   })
      //   // 折线图数据部分
    },
    // 处理时间
    getDay (day) {
      let today = new Date()
      let targetday = today.getTime() + 1000 * 60 * 60 * 24 * day
      today.setTime(targetday) // 注意，这行是关键代码

      let tYear = today.getFullYear()
      let tMonth = today.getMonth()
      let tDate = today.getDate()
      tMonth = this.doHandleMonth(tMonth + 1)
      tDate = this.doHandleMonth(tDate)
      return tYear + '-' + tMonth + '-' + tDate
    },
    doHandleMonth (month) {
      let m = month
      if (month.toString().length === 1) {
        m = '0' + month
      }
      return m
    }
  }
}
</script>
<style lang="scss" scoped>
.transactionData {
  background: #fff;
  .top {
    display: flex;
    align-items: center;
    padding-left: 50px;
    /deep/ .el-input {
      width: 100px;
    }
    .tips {
      margin-left: 10px;
      color: #999;
      font-size: 14px;
    }
  }
  .middle {
    display: flex;
    margin: 10px 0;
    padding: 0 50px;
    .each_eara_item {
      border: 1px solid #eee;
      border-right: none;
      flex: 1;
      height: 130px;
      position: relative;
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
      &:last-of-type {
        border-right: 1px solid #eee;
      }
      .each_ares_num {
        margin-top: 15px;
        font-size: 30px;
      }
      .each_area_bg {
        position: absolute;
        bottom: 2px;
        left: 0;
      }
    }
  }
  .charts {
    padding: 0 50px;
  }
  /deep/ .el-date-editor {
    margin-left: 10px;
  }
}
</style>
