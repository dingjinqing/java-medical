<template>
  <div class="container overview-page">
    <div class="header">
      <div class="h-l">system <span class="title-type">管理员</span></div>
      <div class="h-r"></div>
    </div>
    <div class="content">
      <div class="module">
        <div class="module-header">
          <h2>账户统计</h2>
        </div>
        <div class="module-con">
          <ul class="account-list">
            <li>
              <div
                class="account"
                style="color:#2eb8e6;"
              >{{accountStatisticsInfo.allAccountNum}}</div>
              <div class="account-title">账户总数</div>
            </li>
            <li>
              <div
                class="account"
                style="color: #f3bd51;"
              >{{accountStatisticsInfo.effectiveShopNum}}</div>
              <div class="account-title">店铺总数</div>
            </li>
            <li>
              <div
                class="account"
                style="color: #fe6c6c;"
              >40</div>
              <div class="account-title">有效店铺</div>
            </li>
            <li>
              <div class="account">{{accountStatisticsInfo.usedShopNum}}</div>
              <div class="account-title">使用中</div>
            </li>
            <li>
              <div class="account">{{accountStatisticsInfo.toExpireShopNum}}</div>
              <div class="account-title">将过期</div>
            </li>
            <li>
              <div class="account">{{accountStatisticsInfo.expiredShopNum}}</div>
              <div class="account-title">已过期</div>
            </li>
            <li>
              <div class="account">{{accountStatisticsInfo.disabledShopNum}}</div>
              <div class="account-title">已禁用</div>
            </li>
            <li>
              <div class="account">{{accountStatisticsInfo.authorizedShopNum}}</div>
              <div class="account-title">授权成功</div>
            </li>
            <li>
              <div class="account">{{accountStatisticsInfo.unauthorizedShopNum}}</div>
              <div class="account-title">未授权成功</div>
            </li>
            <li>
              <div class="account">{{accountStatisticsInfo.openedPaymentShopNum}}</div>
              <div class="account-title">开通支付</div>
            </li>
            <li>
              <div class="account">{{accountStatisticsInfo.notOpenPaymentShopNum}}</div>
              <div class="account-title">未开通支付</div>
            </li>
          </ul>
        </div>
        <div>
          <span>时间：</span>
          <el-date-picker
            v-model="queryParams.startTime"
            type="datetime"
            size="small"
            style="width:190px;"
            default-time="00:00:00"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd hh:mm:ss"
          ></el-date-picker>
          <span>-</span>
          <el-date-picker
            v-model="queryParams.endTime"
            type="datetime"
            size="small"
            style="width:190px;"
            default-time="23:59:59"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd hh:mm:ss"
          ></el-date-picker>
          <el-button
            type="primary"
            size="small"
            @click="initData"
          >搜索</el-button>
        </div>
      </div>
      <div class="module">
        <div class="module-header">
          <h2>用户统计 <span>用户总数：</span><span>14321</span></h2>
        </div>
        <div class="module-con clearfix">
          <div class="module-con-left">
            <div
              id="userStatistics"
              style="width:782px;height:350px;"
            ></div>
          </div>
          <div class="module-con-right">
            <el-calendar :range="dateRange">
              <template
                slot="dateCell"
                slot-scope="{date, data}"
              >
                <div>
                  <div
                    :class="data.isSelected ? 'is-selected' : ''"
                    style="text-align:center; font-size:16px;"
                  >
                    <p style="height:20px;line-height:20px;">{{userNum(data)}}</p>
                    <p style="font-size: 12px;color:  gray;">{{ data.day.split('-').slice(1).join('-') }}</p>
                  </div>
                </div>
              </template>
            </el-calendar>
          </div>
        </div>
      </div>
      <div class="module">
        <div class="module-header">
          <h2>订单统计 <span>订单总数：</span><span>9402</span></h2>
        </div>
        <div class="module-con clearfix">
          <div class="module-con-left">
            <div
              id="orderStatistics"
              style="width:782px;height:350px;"
            ></div>
          </div>
          <div class="module-con-right">
            <el-calendar :range="dateRange">
              <template
                slot="dateCell"
                slot-scope="{date, data}"
              >
                <div>
                  <div
                    :class="data.isSelected ? 'is-selected' : ''"
                    style="text-align:center; font-size:16px;"
                  >
                    <p style="height:20px;line-height:20px;">{{userNum(data)}}</p>
                    <p style="font-size: 12px;color:  gray;">{{ data.day.split('-').slice(1).join('-') }}</p>
                  </div>
                </div>
              </template>
            </el-calendar>
          </div>
        </div>
      </div>
      <div class="module">
        <div class="module-header clearfix">
          <h2>订单支付统计</h2>
          <ul class="pay-ul clearfix">
            <li>微信：<span>233</span></li>
            <li>余额：<span>666</span></li>
            <li>卡余额：<span>222</span></li>
            <li>积分：<span>233</span></li>
          </ul>
        </div>
        <div class="module-con clearfix">
          <div class="module-con-left">
            <div
              id="OrderPaymentStatistics"
              style="width:782px;height:350px;"
            ></div>
          </div>
          <div class="module-con-right">
            <el-calendar :range="dateRange">
              <template
                slot="dateCell"
                slot-scope="{date, data}"
              >
                <div>
                  <div
                    :class="data.isSelected ? 'is-selected' : ''"
                    style="text-align:center; font-size:16px;"
                  >
                    <p style="height:20px;line-height:20px;">{{userNum(data)}}</p>
                    <p style="font-size: 12px;color:  gray;">{{ data.day.split('-').slice(1).join('-') }}</p>
                  </div>
                </div>
              </template>
            </el-calendar>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { shopViewApi } from '@/api/system/overView/overViewIndex'
import echarts from 'echarts'
import '@/util/date.js'
let aDay = 24 * 60 * 60 * 1000
export default {
  name: 'overview',
  data () {
    return {
      queryParams: {
        startTime: '',
        endTime: ''
      },
      accountStatisticsInfo: {}, // 账户统计
      userStatisticsInfo: {}, // 用户统计
      userNumsInfo: [],
      orderStatisticsInfo: {}, // 订单支付统计
      userStatisticsData: {
        columns: ['日期', '新增用户'],
        rows: [
          { '日期': '1月1日', '新增用户': 123 },
          { '日期': '1月2日', '新增用户': 1223 },
          { '日期': '1月3日', '新增用户': 2123 },
          { '日期': '1月4日', '新增用户': 4123 },
          { '日期': '1月5日', '新增用户': 3123 },
          { '日期': '1月6日', '新增用户': 7123 }
        ]
      },
      userEcharts: {},
      orderEcharts: {}
    }
  },
  computed: {
    dateRange () {
      if (this.queryParams.startTime && this.queryParams.endTime) {
        let startTime = this.queryParams.startTime
        let endTime = this.queryParams.endTime
        let startDay = new Date(startTime).getDay()
        let endDay = new Date(endTime).getDay() || 7
        console.log(startDay, endDay)
        let startMonday = new Date(new Date(startTime).getTime() - (startDay - 1) * aDay)
        console.log(startMonday.format('yyyy-MM-dd'))
        let endSunday = new Date((7 - endDay) * aDay + new Date(endTime).getTime())
        console.log(endSunday.format('yyyy-MM-dd'))
        let range = [new Date(startMonday).format('yyyy-MM-dd'), new Date(endSunday).format('yyyy-MM-dd')]
        console.log(range)
        return range
      } else {
        return null
      }
    }
  },
  mounted () {
    this.initDate()
    this.initEcharts()
  },
  methods: {
    initDate () {
      let now = new Date()
      console.log(now)
      let yesterday = new Date(now - 24 * 60 * 60 * 1000)
      let prevMonth = new Date(now.getTime() - (31 * 24 * 60 * 60 * 1000))
      console.log(yesterday.format('yyyy-MM-dd hh:mm:ss'), prevMonth.format('yyyy-MM-dd hh:mm:ss'))
      let queryParams = {
        startTime: new Date(prevMonth).format('yyyy-MM-dd') + ' 00:00:00',
        endTime: new Date(yesterday).format('yyyy-MM-dd') + ' 23:59:59'
      }
      this.queryParams = queryParams
      this.initData()
    },
    initEcharts () {
      this.orderEcharts = echarts.init(document.getElementById('orderStatistics'))
      this.userEcharts = echarts.init(document.getElementById('userStatistics'))
      this.orderPaymentEcharts = echarts.init(document.getElementById('OrderPaymentStatistics'))
    },
    echartsLoadData (content) {
      let { userStatisticsInfo, orderStatisticsInfo } = content
      console.log('content:', userStatisticsInfo, orderStatisticsInfo)
      if (userStatisticsInfo.userNumsInfo && userStatisticsInfo.userNumsInfo.length > 0) {
        let userXdata = []
        let userYdata = []
        userStatisticsInfo.userNumsInfo.forEach(item => {
          userXdata.push(item.date.split('-').slice(1).join('-'))
          userYdata.push(item.num)
        })
        this.userEcharts.setOption({
          title: {
            text: '注册用户变化趋势'
          },
          tooltip: {},
          xAxis: {
            data: userXdata
          },
          yAxis: {},
          series: [{
            name: '新增用户',
            type: 'line',
            data: userYdata
          }]
        })
      }
      this.orderEcharts.setOption({
        title: {
          text: '注册用户变化趋势'
        },
        tooltip: {},
        xAxis: {
          data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']
        },
        yAxis: {},
        series: [{
          name: '销量',
          type: 'line',
          data: [5, 20, 36, 10, 10, 20]
        }]
      })
      this.orderPaymentEcharts.setOption({
        title: {
          text: '注册用户变化趋势'
        },
        tooltip: {},
        xAxis: {
          data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']
        },
        yAxis: {},
        series: [{
          name: '销量',
          type: 'line',
          data: [5, 20, 36, 10, 10, 20]
        }]
      })
    },
    initData () {
      let param = Object.assign({}, this.queryParams)
      shopViewApi(param).then(res => {
        if (res) {
          console.log(res)
          this.accountStatisticsInfo = res.content.accountStatisticsInfo // 账户统计
          this.orderStatisticsInfo = res.content.orderStatisticsInfo // 订单支付统计
          this.userStatisticsInfo = res.content.userStatisticsInfo // 用户统计
          this.userNumsInfo = res.content.userStatisticsInfo.userNumsInfo
          this.echartsLoadData(res.content)
        }
      })
    },
    userNum (data) {
      console.log(data)
      let day = data.day
      let info = this.userNumsInfo.find(item => item.date === day)
      if (info) {
        return info.num
      }
      return ''
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  position: relative;
  margin-top: 10px;
  background: #f5f5f5;
  color: #333;
  font: 14px Helvetica Neue, Helvetica, PingFang SC, \5fae\8f6f\96c5\9ed1,
    Tahoma, Arial, sans-serif;
  .header {
    display: flex;
    justify-content: space-between;
    padding: 10px;
  }
  .content {
    padding: 0 10px;
    background: #fff;
  }
  .clearfix {
    &:after {
      content: ".";
      display: block;
      height: 0;
      clear: both;
      visibility: hidden;
    }
  }
}
.overview-page {
  .title-type {
    display: inline-block;
    margin-left: 20px;
    padding: 4px 10px;
    border-radius: 12px;
    cursor: pointer;
    background: #457bf9;
    font-size: 14px;
    color: #fff;
  }
  .module {
    width: 100%;
    .module-header {
      height: 50px;
      line-height: 50px;
      border-bottom: 1px solid #e6e9f0;
    }
    h2 {
      font-size: 20px;
      font-weight: bold;
      float: left;
    }
    .pay-ul {
      float: left;
      margin-left: 20px;
      font-size: 20px;
      font-weight: bold;
      li {
        float: left;
      }
    }
    .account-list {
      width: 100%;
      display: flex;
      padding: 43px 0;
      li {
        flex: 1;
        text-align: center;
        cursor: pointer;
        &:not(:last-child) {
          border-right: 1px solid #e6e9f0;
        }
      }
      .account {
        margin: 10px 0 15px;
        font-size: 36px;
      }
      .account-title {
        font-size: 18px;
      }
    }
    .module-con {
      width: 100%;
    }
    .module-con-left {
      width: 60%;
      float: left;
    }
    .module-con-right {
      width: 40%;
      float: right;
    }
  }
  /deep/ .el-calendar-day {
    height: auto !important;
  }
}
</style>
