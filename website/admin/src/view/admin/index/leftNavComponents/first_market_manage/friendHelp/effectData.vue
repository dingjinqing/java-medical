<template>
  <div class="effectData">
    <div class="top">
      <section style="display: flex">
        <div style="padding-top: 32px;padding-left: 60px ">筛选日期：</div>
        <div style="padding-top: 25px">
        <el-date-picker
            v-model="startTime"
            type="datetime"
            :placeholder="$t('promoteList.startTime')"
            class="morelength"
            size="small"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        <span style="margin: 0 5px">{{$t('promoteList.to')}}</span>
          <el-date-picker
            v-model="endTime"
            type="datetime"
            :placeholder="$t('promoteList.endTime')"
            class="morelength"
            size="small"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </div>
        <div style="padding-top: 26px; padding-left: 15px"><el-button
          type="primary"
          size="small"
          @click="selectData"
        >{{$t('promoteList.filter')}}</el-button></div>
      </section>
    </div>
    <div class="mid">
      <table border="1" class="table">
        <tr>
          <td style="display:table-cell; vertical-align:middle">
            发起用户数
            <div style="padding-top: 15px;padding-left: 25px;font-size:25px;color: #2766f6 ">{{this.launchTotal}}</div>
          </td>
          <td style="display:table-cell; vertical-align:middle">
            帮助力用户数
            <div style="padding-top: 15px;padding-left: 25px;font-size:25px;color: #e666a9 ">{{this.promoteTotal}}</div>
          </td>
          <td style="display:table-cell; vertical-align:middle">
            助力成功用户数
            <div style="padding-top: 15px;padding-left: 25px;font-size:25px;color: #ff7a24 ">{{this.successTotal}}</div>
          </td>
          <td style="display:table-cell; vertical-align:middle">
            拉新用户数
            <div style="padding-top: 15px;padding-left: 25px;font-size:25px;color: #39c840 ">{{this.newUserTotal}}</div>
          </td>
        </tr>
      </table>
    </div>
    <div class="bottom">

      <div id="myCharts" v-show="this.controlShow"></div>
      <div v-show="!this.controlShow"><span>暂无数据</span></div>
    </div>

    <div></div>
  </div>
</template>

<script>
import echarts from 'echarts'
import { effectData } from '@/api/admin/marketManage/friendHelp.js'

export default {
  data () {
    return {
      param: {
        id: '',
        startTime: '',
        endTime: ''
      },
      launchTotal: '',
      promoteTotal: '',
      successTotal: '',
      newUserTotal: '',
      launch: [],
      promote: [],
      success: [],
      newUser: [],
      date: [],
      startTime: '',
      endTime: '',
      myCharts: {},
      chartChange: {
        payUserNum: [],
        uv: [],
        pv: [],
        date: []
      },
      controlShow: true
    }
  },
  created () {
    console.log('加载页面：', this.param)
    this.param.id = this.$route.params.id
    this.loadData()
  },
  mounted () {
    this.myCharts = echarts.init(document.getElementById('myCharts'))
    this.param.id = this.$route.params.id
    this.loadData()
  },
  methods: {
    loadData () {
      effectData(this.param).then(res => {
        console.log('res???', res)
        this.launchTotal = res.content.launchTotal
        this.promoteTotal = res.content.promoteTotal
        this.successTotal = res.content.successTotal
        this.newUserTotal = res.content.newUserTotal
        this.launch = res.content.launch
        this.promote = res.content.promote
        this.success = res.content.success
        this.newUser = res.content.newUser
        this.date = res.content.date
        this.startTime = res.content.startTime
        this.endTime = res.content.endTime

        // 折线图数据部分
        this.echartsData = {
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['发起用户数', '帮助力用户数', '助力成功用户数', '拉新用户数']
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
            data: this.date
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              name: '发起用户数',
              type: 'line',
              data: this.launch,
              itemStyle: {
                normal: {
                  color: '#2766f6', // 折线点的颜色
                  lineStyle: {
                    color: '#2766f6'// 折线的颜色
                  }
                }
              }
            },
            {
              name: '帮助力用户数',
              type: 'line',
              data: this.promote,
              itemStyle: {
                normal: {
                  color: '#e666a9', // 折线点的颜色
                  lineStyle: {
                    color: '#e666a9'// 折线的颜色
                  }
                }
              }
            },
            {
              name: '助力成功用户数',
              type: 'line',
              data: this.success,
              itemStyle: {
                normal: {
                  color: '#ff7a24', // 折线点的颜色
                  lineStyle: {
                    color: '#ff7a24'// 折线的颜色
                  }
                }
              }
            },
            {
              name: '拉新用户数',
              type: 'line',
              data: this.newUser,
              itemStyle: {
                normal: {
                  color: '#39c840', // 折线点的颜色
                  lineStyle: {
                    color: '#39c840'// 折线的颜色
                  }
                }
              }
            }
          ]
        }
        this.myCharts.setOption(this.echartsData)
      })
    },
    selectData () {
      this.param.startTime = this.startTime
      this.param.endTime = this.endTime
      effectData(this.param).then(res => {
        console.log('res???', res)
        this.launchTotal = res.content.launchTotal
        this.promoteTotal = res.content.promoteTotal
        this.successTotal = res.content.successTotal
        this.newUserTotal = res.content.newUserTotal
        this.startTime = res.content.startTime
        this.endTime = res.content.endTime
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .effectData {
    padding: 10px;
    min-width: 100%;
    font-size: 14px;
    height: 100%;
  .top{
    background-color: white;
    height: 100px;
  }
  .mid{
    background-color: white;
    height: 250px;
  .table{
    margin-left: 45px;
    border: 1px solid#45c3ec;
    border-spacing: 0px;
    border-collapse: collapse;
    width: 1200px;
    height: 160px;
    table-layout: fixed;
  }
  .table td{
    border: 1px gray solid;
    padding-left: 85px;
  }
  }
  .bottom{
    background-color: white;
    height: 450px;

  }
  }
  #myCharts {
    width: 90%;
    height: 330px;
  }
</style>
