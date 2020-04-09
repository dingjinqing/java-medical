<template>
<div class="coreIndicator">
  <div class="top">
    <div class="left" style="font-size: 18px">核心指标</div>
    <div class="right">
      时间筛选:
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
    </div>
  </div>
  <div class="mid">
    <table border="1" class="table">
      <tr>
        <td style="display:table-cell; vertical-align:middle">
          <div style="padding-bottom: 10px">付款金额</div>
          <div style="padding-bottom: 10px;font-size:22px ">{{this.vo.totalPaidMoney}}</div>
          <div v-show="this.param.screeningTime === 1 && this.vo.totalPaidMoneyIncr >= 0" style="color: gray;font-size: 13px">
            较前一日 <span style="color: red">↑{{this.vo.totalPaidMoneyIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 1 && this.vo.totalPaidMoneyIncr < 0" style="color: gray;font-size: 13px">
            较前一日 <span style="color: #1bb129">↓{{this.vo.totalPaidMoneyIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 7 && this.vo.totalPaidMoneyIncr >= 0" style="color: gray;font-size: 13px">
            较前七日 <span style="color: red">↑{{this.vo.totalPaidMoneyIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 7 && this.vo.totalPaidMoneyIncr < 0" style="color: gray;font-size: 13px">
            较前七日 <span style="color: #1bb129">↓{{this.vo.totalPaidMoneyIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 30 && this.vo.totalPaidMoneyIncr >= 0" style="color: gray;font-size: 13px">
            较前三十日 <span style="color: red">↑{{this.vo.totalPaidMoneyIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 30 && this.vo.totalPaidMoneyIncr < 0" style="color: gray;font-size: 13px">
            较前三十日 <span style="color: #1bb129">↓{{this.vo.totalPaidMoneyIncr*100}}%</span>
          </div>
        </td>
        <td style="display:table-cell; vertical-align:middle">
          <div style="padding-bottom: 10px">访问-付款转化率</div>
          <div style="padding-bottom: 10px;font-size:22px ">{{this.vo.uv2Paid}}</div>
          <div v-show="this.param.screeningTime === 1 && this.vo.uv2PaidIncr >= 0" style="color: gray;font-size: 13px">
            较前一日 <span style="color: red">↑{{this.vo.uv2PaidIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 1 && this.vo.uv2PaidIncr < 0" style="color: gray;font-size: 13px">
            较前一日 <span style="color: #1bb129">↓{{this.vo.uv2PaidIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 7 && this.vo.uv2PaidIncr >= 0" style="color: gray;font-size: 13px">
            较前七日 <span style="color: red">↑{{this.vo.uv2PaidIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 7 && this.vo.uv2PaidIncr < 0" style="color: gray;font-size: 13px">
            较前七日 <span style="color: #1bb129">↓{{this.vo.uv2PaidIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 30 && this.vo.uv2PaidIncr >= 0" style="color: gray;font-size: 13px">
            较前三十日 <span style="color: red">↑{{this.vo.uv2PaidIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 30 && this.vo.uv2PaidIncr < 0" style="color: gray;font-size: 13px">
            较前三十日 <span style="color: #1bb129">↓{{this.vo.uv2PaidIncr*100}}%</span>
          </div>
        </td>
        <td style="display:table-cell; vertical-align:middle">
          <div style="padding-bottom: 10px">客单价</div>
          <div style="padding-bottom: 10px;font-size:22px ">{{this.vo.pct}}</div>
          <div v-show="this.param.screeningTime === 1 && this.vo.pctIncr >= 0" style="color: gray;font-size: 13px">
            较前一日 <span style="color: red">↑{{this.vo.pctIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 1 && this.vo.pctIncr < 0" style="color: gray;font-size: 13px">
            较前一日 <span style="color: #1bb129">↓{{this.vo.pctIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 7 && this.vo.pctIncr >= 0" style="color: gray;font-size: 13px">
            较前七日 <span style="color: red">↑{{this.vo.pctIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 7 && this.vo.pctIncr < 0" style="color: gray;font-size: 13px">
            较前七日 <span style="color: #1bb129">↓{{this.vo.pctIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 30 && this.vo.pctIncr >= 0" style="color: gray;font-size: 13px">
            较前三十日 <span style="color: red">↑{{this.vo.pctIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 30 && this.vo.pctIncr < 0" style="color: gray;font-size: 13px">
            较前三十日 <span style="color: #1bb129">↓{{this.vo.pctIncr*100}}%</span>
          </div>
        </td>
        <td style="display:table-cell; vertical-align:middle">
          <div style="padding-bottom: 10px">付款订单数</div>
          <div style="padding-bottom: 10px;font-size:22px ">{{this.vo.payOrderNum}}</div>
          <div v-show="this.param.screeningTime === 1 && this.vo.payOrderNumIncr >= 0" style="color: gray;font-size: 13px">
            较前一日 <span style="color: red">↑{{this.vo.payOrderNumIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 1 && this.vo.payOrderNumIncr < 0" style="color: gray;font-size: 13px">
            较前一日 <span style="color: #1bb129">↓{{this.vo.payOrderNumIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 7 && this.vo.payOrderNumIncr >= 0" style="color: gray;font-size: 13px">
            较前七日 <span style="color: red">↑{{this.vo.payOrderNumIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 7 && this.vo.payOrderNumIncr < 0" style="color: gray;font-size: 13px">
            较前七日 <span style="color: #1bb129">↓{{this.vo.payOrderNumIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 30 && this.vo.payOrderNumIncr >= 0" style="color: gray;font-size: 13px">
            较前三十日 <span style="color: red">↑{{this.vo.payOrderNumIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 30 && this.vo.payOrderNumIncr < 0" style="color: gray;font-size: 13px">
            较前三十日 <span style="color: #1bb129">↓{{this.vo.payOrderNumIncr*100}}%</span>
          </div>
        </td>
      </tr>
    </table>
<!--    第一个折线图-->
    <div id="firstCharts" v-show="this.controlShow"></div>
    <div v-show="!this.controlShow"><span>暂无数据</span></div>
  </div>
  <div class="bottom">
    <table border="1" class="table">
      <tr>
        <td style="display:table-cell; vertical-align:middle">
          <div style="padding-bottom: 10px">付款人数</div>
          <div style="padding-bottom: 10px;font-size:22px ">{{this.vo.payUserNum}}</div>
          <div v-show="this.param.screeningTime === 1 && this.vo.payUserNumIncr >= 0" style="color: gray;font-size: 13px">
            较前一日 <span style="color: red">↑{{this.vo.payUserNumIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 1 && this.vo.payUserNumIncr < 0" style="color: gray;font-size: 13px">
            较前一日 <span style="color: #1bb129">↓{{this.vo.payUserNumIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 7 && this.vo.payUserNumIncr >= 0" style="color: gray;font-size: 13px">
            较前七日 <span style="color: red">↑{{this.vo.payUserNumIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 7 && this.vo.payUserNumIncr < 0" style="color: gray;font-size: 13px">
            较前七日 <span style="color: #1bb129">↓{{this.vo.payUserNumIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 30 && this.vo.payUserNumIncr >= 0" style="color: gray;font-size: 13px">
            较前三十日 <span style="color: red">↑{{this.vo.payUserNumIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 30 && this.vo.payUserNumIncr < 0" style="color: gray;font-size: 13px">
            较前三十日 <span style="color: #1bb129">↓{{this.vo.payUserNumIncr*100}}%</span>
          </div>
        </td>
        <td style="display:table-cell; vertical-align:middle">
          <div style="padding-bottom: 10px">访客数</div>
          <div style="padding-bottom: 10px;font-size:22px ">{{this.vo.uv}}</div>
          <div v-show="this.param.screeningTime === 1 && this.vo.uvIncr >= 0" style="color: gray;font-size: 13px">
            较前一日 <span style="color: red">↑{{this.vo.uvIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 1 && this.vo.uvIncr < 0" style="color: gray;font-size: 13px">
            较前一日 <span style="color: #1bb129">↓{{this.vo.uvIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 7 && this.vo.uvIncr >= 0" style="color: gray;font-size: 13px">
            较前七日 <span style="color: red">↑{{this.vo.uvIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 7 && this.vo.uvIncr < 0" style="color: gray;font-size: 13px">
            较前七日 <span style="color: #1bb129">↓{{this.vo.uvIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 30 && this.vo.uvIncr >= 0" style="color: gray;font-size: 13px">
            较前三十日 <span style="color: red">↑{{this.vo.uvIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 30 && this.vo.uvIncr < 0" style="color: gray;font-size: 13px">
            较前三十日 <span style="color: #1bb129">↓{{this.vo.uvIncr*100}}%</span>
          </div>
        </td>
        <td style="display:table-cell; vertical-align:middle">
          <div style="padding-bottom: 10px">浏览量</div>
          <div style="padding-bottom: 10px;font-size:22px ">{{this.vo.pv}}</div>
          <div v-show="this.param.screeningTime === 1 && this.vo.pvIncr >= 0" style="color: gray;font-size: 13px">
            较前一日 <span style="color: red">↑{{this.vo.pvIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 1 && this.vo.pvIncr < 0" style="color: gray;font-size: 13px">
            较前一日 <span style="color: #1bb129">↓{{this.vo.pvIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 7 && this.vo.pvIncr >= 0" style="color: gray;font-size: 13px">
            较前七日 <span style="color: red">↑{{this.vo.pvIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 7 && this.vo.pvIncr < 0" style="color: gray;font-size: 13px">
            较前七日 <span style="color: #1bb129">↓{{this.vo.pvIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 30 && this.vo.pvIncr >= 0" style="color: gray;font-size: 13px">
            较前三十日 <span style="color: red">↑{{this.vo.pvIncr*100}}%</span>
          </div>
          <div v-show="this.param.screeningTime === 30 && this.vo.pvIncr < 0" style="color: gray;font-size: 13px">
            较前三十日 <span style="color: #1bb129">↓{{this.vo.pvIncr*100}}%</span>
          </div>
        </td>
      </tr>
    </table>
    <!--    第二个折线图-->
    <div id="secondCharts" v-show="this.controlShow"></div>
    <div v-show="!this.controlShow"><span>暂无数据</span></div>
  </div>

  <div></div>
</div>
</template>

<script>
import echarts from 'echarts'
import {coreIndicator} from '@/api/admin/firstWebManage/realTimeOverview/realTimeOverview'

export default {
  data () {
    return {
      param: {
        screeningTime: 1
      },
      timeSelect: 1,
      timeRange: [
        { value: 1, label: '昨日' },
        { value: 7, label: '最近7天' },
        { value: 30, label: '最近30天' }
      ],
      vo: {},
      myFirstChart: {},
      firstChartChange: {
        totalPaidMoney: [],
        uv2Paid: [],
        pct: [],
        payOrderNum: [],
        date: []
      },
      secondChartChange: {
        payUserNum: [],
        uv: [],
        pv: [],
        date: []
      },
      controlShow: true

    }
  },
  created () {
    this.loadData()
  },
  mounted () {
    this.myFirstChart = echarts.init(document.getElementById('firstCharts'))
    this.mySecondChart = echarts.init(document.getElementById('secondCharts'))
  },
  methods: {
    // 页面初始化数据
    loadData () {
      coreIndicator(this.param).then(res => {
        console.log('coreIndicator:', res)
        if (res.error === 0) {
          this.handleData(res.content)
        }
      }).catch(err => console.log(err))
    },
    handleData (content) {
      this.vo = content
      console.log('coreIndicatorVo:', this.vo)

      // 处理折线图数据
      if (content.lineChartVos.length === 0) {
        this.controlShow = false
      }
      content.lineChartVos.map(item => {
        this.firstChartChange.totalPaidMoney.push(item.totalPaidMoney)
      })
      content.lineChartVos.map(item => {
        this.firstChartChange.uv2Paid.push(item.uv2Paid)
      })
      content.lineChartVos.map(item => {
        this.firstChartChange.pct.push(item.pct)
      })
      content.lineChartVos.map(item => {
        this.firstChartChange.payOrderNum.push(item.payOrderNum)
      })
      content.lineChartVos.map(item => {
        this.firstChartChange.date.push(item.date)
        this.secondChartChange.date.push(item.date)
      })
      content.lineChartVos.map(item => {
        this.secondChartChange.payUserNum.push(item.payUserNum)
      })
      content.lineChartVos.map(item => {
        this.secondChartChange.uv.push(item.uv)
      })
      content.lineChartVos.map(item => {
        this.secondChartChange.pv.push(item.pv)
      })
      // 折线图数据部分
      this.firstEchartsData = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['付款金额', '访问-付款转化率', '客单价', '付款订单数']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: this.firstChartChange.date
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '付款金额',
            type: 'line',
            data: this.firstChartChange.totalPaidMoney
          },
          {
            name: '访问-付款转化率',
            type: 'line',
            data: this.firstChartChange.uv2Paid
          },
          {
            name: '客单价',
            type: 'line',
            data: this.firstChartChange.pct
          },
          {
            name: '付款订单数',
            type: 'line',
            data: this.firstChartChange.payOrderNum
          }
        ]
      }
      this.myFirstChart.setOption(this.firstEchartsData)
      // 第二个折线图
      this.secondEchartsData = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['付款人数', '访客数', '浏览量']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: this.secondChartChange.date
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '付款人数',
            type: 'line',
            data: this.secondChartChange.payUserNum
          },
          {
            name: '访客数',
            type: 'line',
            data: this.secondChartChange.uv
          },
          {
            name: '浏览量',
            type: 'line',
            data: this.secondChartChange.pv
          }
        ]
      }
      this.mySecondChart.setOption(this.secondEchartsData)
    },
    // 时间选择
    timeChangeHandler (time) {
      console.log('time is:', time)
      this.param.screeningTime = time
      this.loadData()
    }
  }
}
</script>

<style lang="scss" scoped>
  .coreIndicator {
    padding: 10px;
    background: #fff;
    .top{
      height: 50px;
      .left{
        float: left;
      }
      .right{
        padding-right: 20px;
        float: right;
      }
    }
    .mid{
      height: 550px;
      .table{
        margin: 40px;
        border: 1px solid#45c3ec;
        border-spacing: 0px;
        border-collapse: collapse;
        width: 1240px;
        height: 180px;
        table-layout: fixed;
      }
      .table td{
        border: 1px gray solid;
        padding-left: 80px;
      }
    }
    .bottom{
      height: 550px;
      .table{
        margin: 40px;
        border: 1px gray solid;
        border-spacing: 0px;
        border-collapse: collapse;
        width: 1240px;
        height: 180px;
        table-layout: fixed;
      }
      .table td{
        border: 1px gray solid;
        padding-left: 80px;
      }
    }
  }
  #firstCharts {
    width: 90%;
    height: 330px;
  }
  #secondCharts {
    width: 90%;
    height: 330px;
  }
</style>
