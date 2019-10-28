<template>
  <div>
    <wrapper class="dataContent">

      <!-- 日期筛选部分 -->
      <div style="display:flex">
        <div style="height:32px;line-height:32px">筛选：</div>
        <div class="selectTime">
          <el-date-picker
            size="small"
            v-model="starDate"
            type="datetime"
            placeholder="开始时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
          <span>至</span>
          <el-date-picker
            size="small"
            v-model="endDate"
            type="datetime"
            placeholder="结束时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </div>
        <el-button
          style="margin-left: 10px;"
          type="primary"
          size="mini"
          @click="initEcharts"
        >筛选</el-button>
      </div>

      <!-- 表格数据部分 -->
      <section>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="titless">活动实付总金额(元)</div>
            <el-tooltip
              class="item"
              effect="light"
              content="活动订单实际付款总金额(包括账户余额、会员卡余额及微信支付，不包含退款部分)"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >0</div>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="titless">活动优惠总金额(元)</div>
            <el-tooltip
              class="item"
              effect="light"
              content="活动优惠总金额"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #fc6181;"
          >0</div>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="titless">费效比</div>
            <el-tooltip
              class="item"
              effect="light"
              content="活动优惠总金额/活动实付总金额"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #fdb64a;"
          >0%</div>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="titless">新成交用户数</div>
            <el-tooltip
              class="item"
              effect="light"
              content="新成交用户数"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #3dcf9a;"
          >0</div>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="titles">老成交用户数</div>
            <el-tooltip
              class="item"
              effect="light"
              content="在店铺有过付款订单，参与该活动的用户数"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #8379f7;"
          >0</div>
        </div>
      </section>

      <div id="charts"></div>

    </wrapper>
  </div>
</template>

<script>
import { groupBuyAnalysis } from '@/api/admin/marketManage/spellGroup.js'
import wrapper from '@/components/admin/wrapper/wrapper'
import echarts from 'echarts'

export default {
  components: { wrapper },
  data () {
    return {
      starDate: this.moment().startOf('month').format('YYYY-MM-DD HH:mm:ss'),
      endDate: this.moment().format('YYYY-MM-DD HH:mm:ss'),
      totalAmountPaid: 0,
      totalDiscountAmount: 0,
      costBenefitRatio: 0,
      numberNewTransactions: 0,
      oldNumberUsers: 0,
      echartInit: {
        colors: ['#5A8BFF', '#fc6181', '#fdb64a', '#3dcf9a', '#8379f7'],
        legendData: this.$t('groupBuy.legendData')
      },
      echartData: {
        dateList: ['2019-09-01', '2019-09-02', '2019-09-03', '2019-09-04', '2019-09-05', '2019-09-06', '2019-09-07', '2019-09-08', '2019-09-09', '2019-09-10'],
        marketPriceList: [],
        goodsPriceList: [],
        ratioList: [],
        oldUserList: [],
        newUserList: []
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
      let obj = {
        id: this.$route.query.id,
        startTime: this.starDate,
        endTime: this.endDate
      }
      this.handleEcharts()
      groupBuyAnalysis(obj).then(res => {
        this.handleData(res.content)
        this.myChart.hideLoading()
        this.myChart.dispatchAction({
          type: 'restore'
        })
        console.log(res)
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
            name: this.$t('groupBuy.number'),
            type: 'value'
          },
          {
            name: this.$t('groupBuy.costBenefitRatio'),
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
            data: [120, 132, 101, 134, 90, 230, 210, 220, 240, 220]
          },
          {
            name: this.echartInit.legendData[1],
            yAxisIndex: 0,
            color: this.echartInit.colors[1],

            type: 'line',
            stack: '总量',
            data: [220, 182, 191, 234, 290, 330, 310, 300, 320, 323]
          },
          {
            name: this.echartInit.legendData[2],
            type: 'line',
            color: this.echartInit.colors[2],

            yAxisIndex: 1,
            stack: '总量',
            data: [150, 232, 201, 154, 190, 330, 410, 440, 430, 424]
          },
          {
            name: this.echartInit.legendData[3],
            type: 'line',
            color: this.echartInit.colors[3],

            yAxisIndex: 0,
            stack: '总量',
            data: [320, 332, 301, 334, 390, 330, 320, 313, 329, 321]
          },
          {
            name: this.echartInit.legendData[4],
            type: 'line',
            color: this.echartInit.colors[4],
            yAxisIndex: 0,
            stack: '总量',
            data: [820, 932, 901, 934, 1290, 1330, 1320, 1235, 1335, 1285]
          }
        ]
      }
      this.myChart.setOption(this.option)
      this.myChart.showLoading({
        text: 'loading',
        color: '#4cbbff',
        textColor: '#4cbbff',
        maskColor: 'rgba(0, 0, 0, 0.9'
      })
      // window.addEventListener('resize', function () { myChart.resize() })
    },
    handleData (data) {
      this.echartData = data
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
    margin: 30px 0 50px 0;
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
