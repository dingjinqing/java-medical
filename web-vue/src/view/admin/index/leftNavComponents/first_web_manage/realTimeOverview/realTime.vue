<template>
<div class="realTime">
  <div class="top">
    实时概况
  </div>
  <div class="down">
    <div class="left">
      <div style="padding-bottom: 10px">付款金额</div>
      <div style="padding-bottom: 10px;font-size:22px ">{{this.vo.yesterday}}</div>
      <div style="color: gray;font-size: 13px">昨日全天</div>
<!--      折线图-->
      <div id="realTimeCharts" v-show="this.controlShow"></div>
      <div v-show="!this.controlShow"><span>暂无数据</span></div>
    </div>
    <div class="right">
      <table border="1" class="table">
        <tr>
          <td style="display:table-cell; vertical-align:middle">
            <div style="padding-bottom: 10px">访客数</div>
            <div style="padding-bottom: 10px;font-size:22px ">{{this.vo.visitUsers1}}</div>
            <div style="color: gray;font-size: 13px">昨日全天: {{this.vo.visitUsers2}}</div>
          </td>
          <td style="display:table-cell; vertical-align:middle">
            <div style="padding-bottom: 10px">浏览量</div>
            <div style="padding-bottom: 10px;font-size:22px">{{this.vo.pageViews1}}</div>
            <div style="color: gray;font-size: 13px">昨日全天: {{this.vo.pageViews2}}</div>
          </td>
        </tr>
        <tr>
          <td style="display:table-cell; vertical-align:middle">
            <div style="padding-bottom: 10px">付款订单数</div>
            <div style="padding-bottom: 10px;font-size:22px">{{this.vo.payOrderNum1}}</div>
            <div style="color: gray;font-size: 13px">昨日全天: {{this.vo.payOrderNum2}}</div>
          </td>
          <td style="display:table-cell; vertical-align:middle">
            <div style="padding-bottom: 10px">付款人数</div>
            <div style="padding-bottom: 10px;font-size:22px">{{this.vo.payUserNum1}}</div>
            <div style="color: gray;font-size: 13px">昨日全天: {{this.vo.payUserNum2}}</div>
          </td>
        </tr>
      </table>
    </div>
  </div>
</div>
</template>

<script>
import echarts from 'echarts'
import {realTime} from '@/api/admin/firstWebManage/realTimeOverview/realTimeOverview'

export default {
  data () {
    return {
      param: {
        screeningTime: 7
      },
      vo: {
        visitUsers1: '',
        visitUsers2: '',
        pageViews1: '',
        pageViews2: '',
        payOrderNum1: '',
        payOrderNum2: '',
        payUserNum1: '',
        payUserNum2: '',
        yesterday: ''
      },
      myChart: {},
      chartChange: {
        yesterday: [],
        today: []
      },
      controlShow: true
    }
  },
  created () {
    this.loadData()
  },
  mounted () {
    this.myChart = echarts.init(document.getElementById('realTimeCharts'))
  },
  methods: {
    // 页面初始化数据
    loadData () {
      console.log('echart-dom:', document.getElementById('realTimeCharts'))
      realTime(this.param).then(res => {
        console.log('realTime:', res)
        if (res.error === 0) {
          this.handleData(res.content)
        }
      }).catch(err => console.log(err))
    },
    handleData (content) {
      this.vo.visitUsers1 = content.e1.visitUsers.e1
      this.vo.visitUsers2 = content.e1.visitUsers.e2
      this.vo.pageViews1 = content.e1.pageViews.e1
      this.vo.pageViews2 = content.e1.pageViews.e2
      this.vo.payOrderNum1 = content.e1.payOrderNum.e1
      this.vo.payOrderNum2 = content.e1.payOrderNum.e2
      this.vo.payUserNum1 = content.e1.payUserNum.e1
      this.vo.payUserNum2 = content.e1.payUserNum.e2
      this.vo.yesterday = content.e1.yesterdayPaidMoney[23].e2

      console.log('vo:', this.vo)
      // 处理折线图数据
      if (content.e1.yesterdayPaidMoney.length === 0) {
        this.controlShow = false
      }
      content.e1.yesterdayPaidMoney.map(item => {
        this.chartChange.yesterday.push(item.e2)
      })
      content.e1.todayPaidMoney.map(item => {
        this.chartChange.today.push(item.e2)
      })
      // 折线图数据部分
      this.echartsData = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['昨日', '今日']
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
          data: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '昨日',
            type: 'line',
            data: this.chartChange.yesterday,
            itemStyle: {
              normal: {
                lineStyle: {
                  color: '#ff7a24'
                }
              }
            }
          },
          {
            name: '今日',
            type: 'line',
            data: this.chartChange.today,
            itemStyle: {
              normal: {
                lineStyle: {
                  color: '#45c3ec'
                }
              }
            }
          }
        ]
      }
      console.log('yesterday:', this.chartChange.yesterday)
      console.log('today:', this.chartChange.today)
      this.myChart.setOption(this.echartsData)
    }
  }
}

</script>

<style lang="scss" scoped>
  .realTime {
    padding: 10px;
    background: #fff;
    .top{
      height: 24px;
      font-size: 18px;
    }
    .down{
      height: 300px;
      .left{
        padding-left: 20px;
        padding-top: 10px;
        float: left;
        height: 500px;
        width: 50%;
      }
      .right{
        float: left;
        height: 500px;
        width: 50%;
        .table{
          border: 1px gray solid;
          border-spacing: 0px;
          border-collapse: collapse;
          width: 650px;
          height: 280px;
          table-layout: fixed;
          /*text-align: center;*/
        }
        .table td{
          border: 1px gray solid;
          padding-left: 80px;
        }

        .table th{
          border: 1px gray solid;
        }
      }
    }
  }
  #realTimeCharts {
    width: 80%;
    height: 200px;
  }
</style>
