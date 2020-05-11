<template>
  <div class="container">
    <div class="main">
      <div class="content">
        <div class="header">
          <span>筛选日期：</span>
          <el-date-picker
            type="datetime"
            format="yyyy-MM-dd hh:mm:ss"
            size="small"
          ></el-date-picker>
          <span>至</span>
          <el-date-picker
            type="datetime"
            format="yyyy-MM-dd hh:mm:ss"
            size="small"
          ></el-date-picker>
          <el-button
            type="primary"
            size="small"
          >筛选</el-button>
        </div>
        <div class="num-list">
          <ul class="fc-statics-list">
            <li
              class="fc-statics-item"
              :style="'background-image: url('+$imageHost+'/image/admin/any_coner/any_coner_blue.png);'"
            >
              <div class="fc-statics-title">
                <span>活动实付总金额（元）</span>
                <img
                  class="fc-statics-search-icon"
                  :src="$imageHost+'/image/admin/analysis_tishi.png'"
                  alt=""
                >
              </div>
              <div
                class="fc-statics-num"
                style="color: #5A8BFF;"
              >316.00</div>
            </li>
            <li
              class="fc-statics-item"
              :style="'background-image: url('+$imageHost+'/image/admin/any_coner/any_coner_pink.png);'"
            >
              <div class="fc-statics-title">
                <span>活动优惠总金额(元)</span>
                <img
                  class="fc-statics-search-icon"
                  :src="$imageHost+'/image/admin/analysis_tishi.png'"
                  alt=""
                >
              </div>
              <div
                class="fc-statics-num"
                style="color: #fc6181;"
              >20.00</div>
            </li>
            <li
              class="fc-statics-item"
              :style="'background-image: url('+$imageHost+'/image/admin/any_coner/any_coner_orange.png);'"
            >
              <div class="fc-statics-title">
                <span>费效比</span>
                <img
                  class="fc-statics-search-icon"
                  :src="$imageHost+'/image/admin/analysis_tishi.png'"
                  alt=""
                >
              </div>
              <div
                class="fc-statics-num"
                style="color: #fdb64a;"
              >6.33%</div>
            </li>
            <li
              class="fc-statics-item"
              :style="'background-image: url('+$imageHost+'/image/admin/any_coner/any_coner_yellow.png);'"
            >
              <div class="fc-statics-title">
                <span>付款订单数</span>
                <img
                  class="fc-statics-search-icon"
                  :src="$imageHost+'/image/admin/analysis_tishi.png'"
                  alt=""
                >
              </div>
              <div
                class="fc-statics-num"
                style="color: #ff9f7f;"
              >2</div>
            </li>
            <li
              class="fc-statics-item"
              :style="'background-image: url('+$imageHost+'/image/admin/any_coner/any_coner_aqua.png);'"
            >
              <div class="fc-statics-title">
                <span>付款商品件数</span>
                <img
                  class="fc-statics-search-icon"
                  :src="$imageHost+'/image/admin/analysis_tishi.png'"
                  alt=""
                >
              </div>
              <div
                class="fc-statics-num"
                style="color: #32c5e9;"
              >2</div>
            </li>
            <li
              class="fc-statics-item"
              :style="'background-image: url('+$imageHost+'/image/admin/any_coner/any_coner_green.png);'"
            >
              <div class="fc-statics-title">
                <span>新成交用户数</span>
                <img
                  class="fc-statics-search-icon"
                  :src="$imageHost+'/image/admin/analysis_tishi.png'"
                  alt=""
                >
              </div>
              <div
                class="fc-statics-num"
                style="color: #3dcf9a;"
              >0</div>
            </li>
            <li
              class="fc-statics-item"
              :style="'background-image: url('+$imageHost+'/image/admin/any_coner/any_coner_purple.png);'"
            >
              <div class="fc-statics-title">
                <span>老成交用户数</span>
                <img
                  class="fc-statics-search-icon"
                  :src="$imageHost+'/image/admin/analysis_tishi.png'"
                  alt=""
                >
              </div>
              <div
                class="fc-statics-num"
                style="color: #8379f7;"
              >2</div>
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
import echarts from 'echarts'
import '@/util/date.js'
export default {
  data () {
    return {
      tableData: [],
      fullCutcharts: null,
      colors: ['#5A8BFF', '#fc6181', '#fdb64a', '#ff9f7f', '#32c5e9', '#3dcf9a', '#8379f7']
    }
  },
  mounted () {
    this.initEcharts()
    this.initData()
  },
  methods: {
    initEcharts () {
      this.fullCutcharts = echarts.init(document.getElementById('fullCutcharts'))
    },
    initData () {
      let that = this
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
          data: ['活动实付总金额', '活动优惠总金额', '费效比', '付款订单数', '付款商品件数', '新成交用户数', '老成交用户数']
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
            name: '费效比',
            type: 'value'
          }
        ],
        series: [
          {
            name: '活动实付总金额',
            type: 'line',
            color: that.colors[0],
            yAxisIndex: 0,
            data: amount
          },
          {
            name: '活动优惠总金额',
            type: 'line',
            yAxisIndex: 0,
            data: discount || []
          },
          {
            name: '费效比',
            type: 'line',
            color: that.colors[2],
            yAxisIndex: 1,
            data: ratio || []
          },
          {
            name: '付款订单数',
            type: 'line',
            color: that.colors[3],
            yAxisIndex: 0,
            data: orderNumber || []
          },
          {
            name: '付款商品件数',
            type: 'line',
            color: that.colors[4],
            yAxisIndex: 0,
            data: orderGoodsNumber || []
          },
          {
            name: '新成交用户数',
            type: 'line',
            color: that.colors[5],
            yAxisIndex: 0,
            data: newUser || []
          },
          {
            name: '老成交用户数',
            type: 'line',
            color: that.colors[6],
            yAxisIndex: 0,
            data: oldUser || []
          }
        ]
      }
      setTimeout(() => {
        that.fullCutcharts.resize()
      }, 0)
      that.fullCutcharts.setOption(fullCutchartsOptions)
      window.addEventListener('resize', function () {
        that.fullCutcharts.resize()
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
