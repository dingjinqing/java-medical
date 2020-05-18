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
        value-format="yyyyMMdd"
        range-separator="-"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        class="custom"
      >
      </el-date-picker>

      <div class="tips">{{this.startDate.year}}年{{this.startDate.month}}月{{this.startDate.day}}日 - {{this.endDate.year}}年{{this.endDate.month}}月{{this.endDate.day}}日</div>
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
          src="http://mpdevimg2.weipubao.cn/image/admin/any_coner/any_coner_blue.png"
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
export default {
  data () {
    this.chartSettings = {
      xAxisType: 'time'
    }
    return {
      timeValue: [],
      timeSelect: 7,
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
        rows: [
          { '日期': '2018-01-01', '成功支付单数': 1393, '成功支付人数': 1093, '支付金额': 1001, '退款金额': 2330 },
          { '日期': '2018-01-02', '成功支付单数': 3530, '成功支付人数': 3230, '支付金额': 222, '退款金额': 1320 },
          { '日期': '2018-01-03', '成功支付单数': 2923, '成功支付人数': 2623, '支付金额': 344, '退款金额': 1310 },
          { '日期': '2018-01-05', '成功支付单数': 1723, '成功支付人数': 1423, '支付金额': 322, '退款金额': 205 },
          { '日期': '2018-01-10', '成功支付单数': 3792, '成功支付人数': 3492, '支付金额': 5644, '退款金额': 260 },
          { '日期': '2018-01-20', '成功支付单数': 4593, '成功支付人数': 4293, '支付金额': 3454, '退款金额': 290 }
        ]

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
      ]
    }
  },
  mounted () {
    this.loadData()
  },
  methods: {
    // 自定义时间
    customDate () {
      this.chartChange = {
        date: [],
        number: []
      }
      console.log('选择器的时间：', this.timeValue)
      //   this.param.startTime = this.timeValue[0]
      //   this.param.endTime = this.timeValue[1]
      this.loadData()
    },
    // time下拉框变化
    timeChangeHandler (time) {
      console.log(time)
      if (time === 7 || time === 30) {
        this.chartChange = {
          date: [],
          number: []
        }
        this.param.type = time
        this.loadData()
      } else {
        this.param.type = time
      }
    },
    // 页面初始化数据
    loadData () {
      //     getSelect(this.param).then(res => {
      //     console.log('访问趋势', res)
      //     if (res.error === 0) {
      //       this.handleData(res.content)
      //     }
      //   }).catch(err => console.log(err))
    },
    // 数据处理
    handleData (content) {
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
}
</style>
