<template>
  <div>
    <wrapper class="dataContent">

      <!-- 日期筛选部分 -->
      <div style="display:flex">
        <div style="height:32px;line-height:32px">{{ $t('seckill.screen') }}：</div>
        <div class="selectTime">
          <el-date-picker
            size="small"
            v-model="starDate"
            type="datetime"
            :placeholder="$t('seckill.startTime')"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
          <span>{{ $t('seckill.to') }}</span>
          <el-date-picker
            size="small"
            v-model="endDate"
            type="datetime"
            :placeholder="$t('seckill.endTime')"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </div>
        <el-button
          style="margin-left: 10px;"
          type="primary"
          size="mini"
          @click="initEcharts"
        >{{ $t('seckill.screen') }}</el-button>
      </div>

      <!-- 表格数据部分 -->
      <section>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="titless">付款订单数</div>
            <el-tooltip
              class="item"
              effect="light"
              content="活动带来的付款订单数（包括退款部分）"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >{{ totalOrderNumber }}</div>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="titless">参与用户数</div>
            <el-tooltip
              class="item"
              effect="light"
              content="参与活动的用户数（包括开团及参团用户）"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >{{ totalJoinNum }}</div>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="titless">成团用户数</div>
            <el-tooltip
              class="item"
              effect="light"
              content="活动已成团用户数"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >{{ totalSuccessUserNum }}</div>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="titless">拉新用户数</div>
            <el-tooltip
              class="item"
              effect="light"
              content="在店铺没有过访问记录，通过活动首次访问店铺的用户数"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >{{ totalNewUser }}</div>
        </div>
      </section>

      <div id="charts"></div>

    </wrapper>
  </div>
</template>

<script>
import { effactLotteryList } from '@/api/admin/marketManage/lotteryDraw.js'
import wrapper from '@/components/admin/wrapper/wrapper'
import echarts from 'echarts'

export default {
  components: { wrapper },
  data () {
    return {
      starDate: this.moment().startOf('month').format('YYYY-MM-DD HH:mm:ss'),
      endDate: this.moment().format('YYYY-MM-DD HH:mm:ss'),
      totalOrderNumber: 0, // 付款订单数
      totalJoinNum: 0, // 参与用户数
      totalSuccessUserNum: 0, // 成团用户数
      totalNewUser: 0, // 拉新用户数
      echartInit: {
        colors: ['#5A8BFF', '#fc6181', '#fdb64a', '#3dcf9a', '#8379f7'],
        legendData: this.$t('groupBuy.legendData')
      },
      echartData: {
        dataList: [] // 日期时间
      },
      option: {},
      myChart: {}
    }
  },
  watch: {
    lang () {
      this.updateEcharts()
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    this.initEcharts()
  },
  methods: {
    initEcharts () {
      this.myChart = echarts.init(document.getElementById('charts'))
      let params = {
        groupDrawId: this.$route.query.id,
        startTime: this.starDate,
        endTime: this.endDate
      }
      this.handleEcharts()
      effactLotteryList(params).then((res) => {
        if (res.error === 0) {
          this.totalOrderNumber = res.content.totalOrderNumber
          this.totalJoinNum = res.content.totalJoinNum
          this.totalSuccessUserNum = res.content.totalSuccessUserNum
          this.totalNewUser = res.content.totalNewUser
          // this.echartData = res.content
          var orderObj = res.content.orderNumber
          var newObj = res.content.newUser
          var joinObj = res.content.joinNum
          var successObj = res.content.successUserNum
          var dataList = []
          var orderNumber = []
          var newUser = []
          var joinNum = []
          var successUserNum = []
          Object.keys(orderObj).forEach(function (key) {
            dataList.push(key)
            orderNumber.push(orderObj[key])
          })
          Object.keys(newObj).forEach(function (key) {
            newUser.push(newObj[key])
          })
          Object.keys(joinObj).forEach(function (key) {
            joinNum.push(joinObj[key])
          })
          Object.keys(successObj).forEach(function (key) {
            successUserNum.push(successObj[key])
          })
          this.echartData.dataList = dataList
          this.echartData.orderNumber = orderNumber
          this.echartData.newUser = newUser
          this.echartData.joinNum = joinNum
          this.echartData.successUserNum = successUserNum
          this.handleEcharts()
          this.myChart.hideLoading()
          this.myChart.dispatchAction({
            type: 'restore'
          })
        }
      })
    },
    handleEcharts () {
      this.option = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: this.echartInit.legendData
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: this.echartData.dateList,
            boundaryGap: false
          }
        ],
        yAxis: [
          {
            name: this.$t('seckill.effactNum'),
            type: 'value'
          },
          {
            name: this.$t('seckill.costEffect'),
            type: 'value'
          }
        ],
        series: [
          {
            name: this.echartInit.legendData[0],
            type: 'line',
            color: this.echartInit.colors[0],
            yAxisIndex: 0,
            stack: '总量',
            data: this.echartData.orderNumber
          },
          {
            name: this.echartInit.legendData[1],
            yAxisIndex: 0,
            color: this.echartInit.colors[1],

            type: 'line',
            stack: '总量',
            data: this.echartData.newUser
          },
          {
            name: this.echartInit.legendData[2],
            type: 'line',
            color: this.echartInit.colors[2],

            yAxisIndex: 1,
            stack: '总量',
            data: this.echartData.joinNum
          },
          {
            name: this.echartInit.legendData[3],
            type: 'line',
            color: this.echartInit.colors[3],

            yAxisIndex: 0,
            stack: '总量',
            data: this.echartData.successUserNum
          }
        ]
      }
      this.myChart.setOption(this.option)
      this.myChart.showLoading({
        text: 'loading',
        color: '#4cbbff',
        textColor: '#4cbbff'
        // maskColor: 'rgba(0, 0, 0, 0.9)'
      })
    },
    updateEcharts () {
      this.echartInit.legendData = this.$t('groupBuy.legendData')
      this.initEcharts()
      this.myChart.setOption({
        legend: {
          data: this.$t('groupBuy.legendData')
        },
        yAxis: [
          {
            name: this.$t('groupBuy.number'),
            type: 'value'
          },
          {
            name: this.$t('groupBuy.costBenefitRatio'),
            type: 'value'
          }
        ]
      })
    }
  }
}

</script>
<style lang="scss" scoped>
.dataContent {
  padding: 10px 50px;
  font-size: 14px;
  section {
    border: 1px solid #eee;
    height: 130px;
    width: 85%;
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 30px 0 50px;
    .fromInfo {
      flex: 1;
      height: 130px;
      position: relative;
      border-right: 1px solid #eee;
      -webkit-box-sizing: border-box;
      -moz-box-sizing: border-box;
      box-sizing: border-box;
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
    }
  }
  #charts {
    width: 100%;
    height: 500px;
    left: -30px;
  }
}
</style>
