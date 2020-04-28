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
              >{{accountStatisticsInfo.allShopNum}}</div>
              <div class="account-title">店铺总数</div>
            </li>
            <li>
              <div
                class="account"
                style="color: #fe6c6c;"
              >{{accountStatisticsInfo.effectiveShopNum}}</div>
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
          <h2>用户统计 <span>用户总数：</span><span v-text="userStatisticsInfo.allNum"></span></h2>
        </div>
        <div class="module-con clearfix">
          <div class="module-con-left">
            <div id="userStatistics"></div>
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
          <h2>订单统计 <span>订单总数：</span><span v-text="orderStatisticsInfo.allOrderNum"></span></h2>
        </div>
        <div class="module-con clearfix">
          <div class="module-con-left">
            <div id="orderStatistics"></div>
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
                    <p style="height:20px;line-height:20px;">{{orderNum(data)}}</p>
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
            <li>微信：<span>{{orderStatisticsInfo.wxPayed||0}},</span></li>
            <li>余额：<span>{{orderStatisticsInfo.balancePayed||0}},</span></li>
            <li>卡余额：<span>{{orderStatisticsInfo.cardBalancePayed||0}},</span></li>
            <li>积分：<span>{{orderStatisticsInfo.integralPayed||0}}</span></li>
          </ul>
        </div>
        <div class="module-con clearfix">
          <div class="module-con-left">
            <div id="OrderPaymentStatistics"></div>
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
                    <p style="height:20px;line-height:20px;">{{payMoney(data)}}</p>
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
      orderStatisticsInfo: {}, // 订单支付统计
      userEcharts: {},
      orderEcharts: {},
      orderPaymentEcharts: {}
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
      // 用户统计
      if (userStatisticsInfo.userNumsInfo && userStatisticsInfo.userNumsInfo.length > 0) {
        let userXdata = []
        let userYdata = []
        userStatisticsInfo.userNumsInfo.forEach(item => {
          userXdata.push(item.date.split('-').slice(1).join('-'))
          userYdata.push(item.num)
        })
        this.userEcharts.setOption({
          title: {
            text: '注册用户变化趋势',
            subtext: '不是虚构'
          },
          tooltip: {
            trigger: 'axis'
          },
          toolbox: {
            show: true,
            feature: {
              dataZoom: {
                yAxisIndex: 'none'
              },
              dataView: { readOnly: false },
              magicType: { type: ['line', 'bar'] },
              restore: {},
              saveAsImage: {}
            }
          },
          xAxis: {
            type: 'category',
            data: userXdata
          },
          yAxis: {
            type: 'value'
          },
          legend: {
            data: ['注册量']
          },
          series: [{
            name: '新增用户',
            type: 'line',
            data: userYdata,
            markPoint: {
              data: [
                { type: 'max', name: '最大值' },
                { type: 'min', name: '最小值' }
              ]
            },
            markLine: {
              data: [
                { type: 'average', name: '平均值' }
              ]
            },
            label: {
              normal: {
                show: true,
                position: 'top'
              }
            }
          }]
        })
      }
      // 订单统计
      if (orderStatisticsInfo.orderNumInfos && orderStatisticsInfo.orderNumInfos.length > 0) {
        let orderXdata = []
        let orderYdata = []
        orderStatisticsInfo.orderNumInfos.forEach(item => {
          orderXdata.push(item.date.split('-').slice(1).join('-'))
          orderYdata.push(item.num)
        })
        this.orderEcharts.setOption({
          title: {
            text: '订单变化趋势',
            subtext: '不是虚构'
          },
          xAxis: {
            type: 'category',
            data: orderXdata
          },
          yAxis: {
            type: 'value'
          },
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['订单量']
          },
          toolbox: {
            show: true,
            feature: {
              dataZoom: {
                yAxisIndex: 'none'
              },
              dataView: { readOnly: false },
              magicType: { type: ['line', 'bar'] },
              restore: {},
              saveAsImage: {}
            }
          },
          series: [
            {
              name: '支付订单',
              type: 'line',
              data: orderYdata,
              markPoint: {
                data: [
                  { type: 'max', name: '最大值' },
                  { type: 'min', name: '最小值' }
                ]
              },
              markLine: {
                data: [
                  { type: 'average', name: '平均值' }
                ]
              }
            }
          ]
        })
      }
      // 支付金额统计
      if (orderStatisticsInfo.orderMoneyInfos && orderStatisticsInfo.orderMoneyInfos.length > 0) {
        let payXdata = []
        let payYdata = []
        let accountMoney = []
        let cardMoney = []
        let scoreMoney = []
        orderStatisticsInfo.orderMoneyInfos.forEach(item => {
          payXdata.push(item.date.split('-').slice(1).join('-'))
          payYdata.push(item.money)
        })
        this.orderPaymentEcharts.setOption({
          title: {
            text: '支付金额变化趋势',
            subtext: '不是虚构'
          },
          xAxis: {
            type: 'category',
            data: payXdata
          },
          yAxis: {
            type: 'value'
          },
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['支付金额']
          },
          toolbox: {
            show: true,
            feature: {
              dataZoom: {
                yAxisIndex: 'none'
              },
              dataView: { readOnly: false },
              magicType: { type: ['line', 'bar'] },
              restore: {},
              saveAsImage: {}
            }
          },
          series: [
            {
              name: '微信',
              type: 'line',
              data: payYdata,
              areaStyle: { normal: {} },
              markPoint: {
                data: [
                  { type: 'max', name: '最大值' },
                  { type: 'min', name: '最小值' }
                ]
              },
              markLine: {
                data: [
                  { type: 'average', name: '平均值' }
                ]
              }
            },
            {
              name: '账户余额',
              type: 'line',
              stack: '总量',
              areaStyle: { normal: {} },
              data: accountMoney
            },
            {
              name: '卡余额',
              type: 'line',
              stack: '总量',
              areaStyle: { normal: {} },
              data: cardMoney
            },
            {
              name: '积分支付',
              type: 'line',
              stack: '总量',
              areaStyle: { normal: {} },
              data: scoreMoney
            }
          ]
        })
      }
    },
    initData () {
      let param = Object.assign({}, this.queryParams)
      if (!param.startTime || !param.endTime) {
        this.$message.warning('请选择检索时间')
        return false
      }
      shopViewApi(param).then(res => {
        if (res) {
          console.log(res)
          this.accountStatisticsInfo = res.content.accountStatisticsInfo // 账户统计
          this.userStatisticsInfo = res.content.userStatisticsInfo // 用户统计
          this.orderStatisticsInfo = res.content.orderStatisticsInfo // 订单支付统计
          this.echartsLoadData(res.content)
        }
      })
    },
    userNum (data) {
      let day = data.day
      let userNumsInfo = this.userStatisticsInfo.userNumsInfo || []
      let info = userNumsInfo.find(item => item.date === day)
      if (info) {
        return info.num
      }
      return ''
    },
    orderNum (data) {
      let day = data.day
      let orderNumInfos = this.orderStatisticsInfo.orderNumInfos || []
      let info = orderNumInfos.find(item => item.date === day)
      if (info) {
        return info.num
      }
      return ''
    },
    payMoney (data) {
      let day = data.day
      let orderMoneyInfos = this.orderStatisticsInfo.orderMoneyInfos || []
      let info = orderMoneyInfos.find(item => item.date === day)
      if (info) {
        return info.money
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
      & > div {
        width: 100%;
        height: 350px;
        margin-top: 10px;
      }
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
