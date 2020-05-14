<template>
  <div class="container">
    <div class="main">
      <div class="content">
        <div class="header">
          <span>{{$t('fullCuti18n.filterDate')}}：</span>
          <el-date-picker
            v-model="startTime"
            type="datetime"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
            size="small"
            default-time="00:00:00"
          ></el-date-picker>
          <span>{{$t('fullCuti18n.to')}}</span>
          <el-date-picker
            v-model="endTime"
            type="datetime"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
            size="small"
            default-time="23:59:59"
          ></el-date-picker>
          <el-button
            type="primary"
            size="small"
            @click="initData"
          >{{$t('fullCuti18n.filter')}}</el-button>
        </div>
        <div class="num-list">
          <ul class="fc-statics-list">
            <li
              class="fc-statics-item"
              :style="'background-image: url('+$imageHost+'/image/admin/any_coner/any_coner_blue.png);'"
            >
              <div class="fc-statics-title">
                <span>{{$t('fullCuti18n.totalPaid')}}（元）</span>
                <img
                  class="fc-statics-search-icon"
                  :src="$imageHost+'/image/admin/analysis_tishi.png'"
                >
              </div>
              <div
                class="fc-statics-num"
                style="color: #5A8BFF;"
              >{{(performanceData.totalPayment||0).toFixed(2)}}</div>
            </li>
            <li
              class="fc-statics-item"
              :style="'background-image: url('+$imageHost+'/image/admin/any_coner/any_coner_pink.png);'"
            >
              <div class="fc-statics-title">
                <span>{{$t('fullCuti18n.totalDiscount')}}（元）</span>
                <img
                  class="fc-statics-search-icon"
                  :src="$imageHost+'/image/admin/analysis_tishi.png'"
                  alt=""
                >
              </div>
              <div
                class="fc-statics-num"
                style="color: #fc6181;"
              >{{(performanceData.totalDiscount||0).toFixed(2)}}</div>
            </li>
            <li
              class="fc-statics-item"
              :style="'background-image: url('+$imageHost+'/image/admin/any_coner/any_coner_orange.png);'"
            >
              <div class="fc-statics-title">
                <span>{{$t('fullCuti18n.costEffect')}}</span>
                <img
                  class="fc-statics-search-icon"
                  :src="$imageHost+'/image/admin/analysis_tishi.png'"
                  alt=""
                >
              </div>
              <div
                class="fc-statics-num"
                style="color: #fdb64a;"
              >{{(performanceData.totalCostEffectivenessRatio||0).toFixed(2)}}%</div>
            </li>
            <li
              class="fc-statics-item"
              :style="'background-image: url('+$imageHost+'/image/admin/any_coner/any_coner_yellow.png);'"
            >
              <div class="fc-statics-title">
                <span>{{$t('fullCuti18n.payOrderNum')}}</span>
                <img
                  class="fc-statics-search-icon"
                  :src="$imageHost+'/image/admin/analysis_tishi.png'"
                  alt=""
                >
              </div>
              <div
                class="fc-statics-num"
                style="color: #ff9f7f;"
              >{{(performanceData.totalPaidOrderNumber||0)}}</div>
            </li>
            <li
              class="fc-statics-item"
              :style="'background-image: url('+$imageHost+'/image/admin/any_coner/any_coner_aqua.png);'"
            >
              <div class="fc-statics-title">
                <span>{{$t('fullCuti18n.payGoodsNum')}}</span>
                <img
                  class="fc-statics-search-icon"
                  :src="$imageHost+'/image/admin/analysis_tishi.png'"
                  alt=""
                >
              </div>
              <div
                class="fc-statics-num"
                style="color: #32c5e9;"
              >{{(performanceData.totalPaidGoodsNumber||0)}}</div>
            </li>
            <li
              class="fc-statics-item"
              :style="'background-image: url('+$imageHost+'/image/admin/any_coner/any_coner_green.png);'"
            >
              <div class="fc-statics-title">
                <span>{{$t('fullCuti18n.newDealsUsers')}}</span>
                <img
                  class="fc-statics-search-icon"
                  :src="$imageHost+'/image/admin/analysis_tishi.png'"
                  alt=""
                >
              </div>
              <div
                class="fc-statics-num"
                style="color: #3dcf9a;"
              >{{(performanceData.totalNewUserNumber||0)}}</div>
            </li>
            <li
              class="fc-statics-item"
              :style="'background-image: url('+$imageHost+'/image/admin/any_coner/any_coner_purple.png);'"
            >
              <div class="fc-statics-title">
                <span>{{$t('fullCuti18n.oldDealsUsers')}}</span>
                <img
                  class="fc-statics-search-icon"
                  :src="$imageHost+'/image/admin/analysis_tishi.png'"
                  alt=""
                >
              </div>
              <div
                class="fc-statics-num"
                style="color: #8379f7;"
              >{{(performanceData.totalOldUserNumber||0)}}</div>
            </li>
          </ul>
        </div>
      </div>
      <div class="echarts">
        <div
          id="fullCutcharts"
          style="width:100%; max-width:100%; height:400px;"
        ></div>
      </div>
    </div>
  </div>
</template>

<script>
import { fullcutAnalysisApi } from '@/api/admin/marketManage/fullDiscountFullCut.js'
import echarts from 'echarts'
import '@/util/date.js'
export default {
  data () {
    return {
      activityId: '',
      startTime: '',
      endTime: '',
      tableData: [],
      fullCutcharts: null,
      colors: ['#5A8BFF', '#fc6181', '#fdb64a', '#ff9f7f', '#32c5e9', '#3dcf9a', '#8379f7'],
      performanceData: {} // 汇总数据
    }
  },
  mounted () {
    let query = this.$route.query
    this.startTime = query.startTime || ''
    this.endTime = query.endTime || ''
    if (new Date(this.endTime) > new Date()) {
      this.endTime = new Date().format('yyyy-MM-dd hh:mm:ss')
    }
    this.activityId = query.id
    this.initEcharts()
    this.initData()
  },
  methods: {
    initEcharts () {
      let that = this
      this.fullCutcharts = echarts.init(document.getElementById('fullCutcharts'))
      let xAxisData = []
      let amount = []
      let discount = []
      let ratio = []
      let orderNumber = []
      let orderGoodsNumber = []
      let newUser = []
      let oldUser = []
      let fullCutchartsOptions = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: [that.$t('fullCuti18n.totalPaid'), that.$t('fullCuti18n.totalDiscount'), that.$t('fullCuti18n.totalDiscount'), that.$t('fullCuti18n.payOrderNum'), that.$t('fullCuti18n.payGoodsNum'), that.$t('fullCuti18n.newDealsUsers'), that.$t('fullCuti18n.oldDealsUsers')]
        },
        grid: {
          left: '3%',
          right: '4%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: xAxisData
        },
        yAxis: [
          {
            name: '数量',
            type: 'value'
          },
          {
            name: that.$t('fullCuti18n.totalDiscount'),
            type: 'value'
          }
        ],
        series: [
          {
            name: that.$t('fullCuti18n.totalPaid'),
            type: 'line',
            color: that.colors[0],
            yAxisIndex: 0,
            data: amount || []
          },
          {
            name: that.$t('fullCuti18n.totalDiscount'),
            type: 'line',
            yAxisIndex: 0,
            data: discount || []
          },
          {
            name: that.$t('fullCuti18n.totalDiscount'),
            type: 'line',
            color: that.colors[2],
            yAxisIndex: 1,
            data: ratio || []
          },
          {
            name: that.$t('fullCuti18n.payOrderNum'),
            type: 'line',
            color: that.colors[3],
            yAxisIndex: 0,
            data: orderNumber || []
          },
          {
            name: that.$t('fullCuti18n.payGoodsNum'),
            type: 'line',
            color: that.colors[4],
            yAxisIndex: 0,
            data: orderGoodsNumber || []
          },
          {
            name: that.$t('fullCuti18n.newDealsUsers'),
            type: 'line',
            color: that.colors[5],
            yAxisIndex: 0,
            data: newUser || []
          },
          {
            name: that.$t('fullCuti18n.oldDealsUsers'),
            type: 'line',
            color: that.colors[6],
            yAxisIndex: 0,
            data: oldUser || []
          }
        ]
      }
      that.fullCutcharts.setOption(fullCutchartsOptions)
      setTimeout(() => {
        that.fullCutcharts.resize()
      }, 0)
      window.addEventListener('resize', function () {
        that.fullCutcharts.resize()
      })
    },
    initData () {
      let that = this
      let params = {
        id: this.activityId,
        startTime: this.startTime,
        endTime: this.endTime
      }
      if (!params.startTime || !params.endTime) {
        this.$message.warning(that.$t('fullCuti18n.psFDate'))
        return false
      }
      if (new Date(params.startTime) > new Date(params.endTime)) {
        this.$message.warning(that.$t('fullCuti18n.cnotGreaterEnd'))
        return false
      }
      fullcutAnalysisApi(params).then(res => {
        if (res.error === 0) {
          let content = res.content
          that.performanceData = content.total
          let xAxisData = content.dateList
          let amount = content.paymentAmount // 活动实付总金额数组
          let discount = content.discountAmount // 活动优惠总金额 数组
          let ratio = content.costEffectivenessRatio // 费效比数组
          let orderNumber = content.paidOrderNumber // 付款订单数数组
          let orderGoodsNumber = content.paidGoodsNumber // 付款商品件数
          let oldUser = content.oldUserNumber
          let newUser = content.newUserNumber
          let fullCutchartsOptions = {
            xAxis: {
              type: 'category',
              boundaryGap: false,
              data: xAxisData
            },
            series: [
              {
                name: that.$t('fullCuti18n.totalPaid'),
                type: 'line',
                color: that.colors[0],
                yAxisIndex: 0,
                data: amount || []
              },
              {
                name: that.$t('fullCuti18n.totalDiscount'),
                type: 'line',
                yAxisIndex: 0,
                data: discount || []
              },
              {
                name: that.$t('fullCuti18n.totalDiscount'),
                type: 'line',
                color: that.colors[2],
                yAxisIndex: 1,
                data: ratio || []
              },
              {
                name: that.$t('fullCuti18n.payOrderNum'),
                type: 'line',
                color: that.colors[3],
                yAxisIndex: 0,
                data: orderNumber || []
              },
              {
                name: that.$t('fullCuti18n.payGoodsNum'),
                type: 'line',
                color: that.colors[4],
                yAxisIndex: 0,
                data: orderGoodsNumber || []
              },
              {
                name: that.$t('fullCuti18n.newDealsUsers'),
                type: 'line',
                color: that.colors[5],
                yAxisIndex: 0,
                data: newUser || []
              },
              {
                name: that.$t('fullCuti18n.oldDealsUsers'),
                type: 'line',
                color: that.colors[6],
                yAxisIndex: 0,
                data: oldUser || []
              }
            ]
          }
          that.fullCutcharts.setOption(fullCutchartsOptions)
        } else {
          that.$message.error(res.message)
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  color: #333;
  font: 14px Helvetica Neue, Helvetica, PingFang SC, \5fae\8f6f\96c5\9ed1,
    Tahoma, Arial, sans-serif;
}
.main {
  width: 100%;
  padding: 30px 25px;
  background: #fff;
}
.content {
  width: 85%;
  margin: 0 auto;
}
.header {
  margin-bottom: 30px;
}
.fc-statics-list {
  width: 100%;
  display: flex;
  overflow: hidden;
  flex-wrap: wrap;
}
.fc-statics-item {
  flex: 0 0 195px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  height: 130px;
  border: 1px solid #eee;
  background-size: 39px 44px;
  background-position: left bottom;
  background-repeat: no-repeat;
}
.fc-statics-num {
  margin-top: 15px;
  font-size: 30px;
}
.fc-statics-search-icon {
  position: relative;
  top: 3px;
  cursor: pointer;
}
.echarts {
  width: 100%;
  max-width: 100%;
  margin-top: 50px;
  padding: 0 20px;
}
</style>
