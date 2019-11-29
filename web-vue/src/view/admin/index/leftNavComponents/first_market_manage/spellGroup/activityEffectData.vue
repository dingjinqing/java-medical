<!--
* 多人拼团 - 效果数据页面
*
* @author:赵鑫
-->
<template>
  <div>
    <wrapper class="dataContent">
      <!-- 日期筛选部分 -->
      <div style="display:flex">
        <div style="height:32px;line-height:32px">{{$t('groupBuy.screeningDates')}}：</div>
        <div class="selectTime">
          <el-date-picker
            size="small"
            v-model="starDate"
            type="datetime"
            :placeholder="$t('groupBuy.startDate')"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
          <span>{{$t('marketCommon.to')}}</span>
          <el-date-picker
            size="small"
            v-model="endDate"
            type="datetime"
            :placeholder="$t('groupBuy.endDate')"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </div>
        <el-button
          style="margin-left: 10px;"
          type="primary"
          size="mini"
          @click="initEcharts"
        >{{$t('marketCommon.filter')}}</el-button>
      </div>

      <!-- 表格数据部分 -->
      <section>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="title">{{$t('groupBuy.totalAmountPaid',[this.currency[0]])}}</div>
            <el-tooltip
              class="item"
              effect="light"
              :content="$t('groupBuy.totalAmountPaidComment')"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >{{this.totalAmountPaid,[this.currency[0]]}}</div>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="title">{{$t('groupBuy.totalDiscountAmount',[this.currency[0]])}}</div>
            <el-tooltip
              class="item"
              effect="light"
              :content="$t('groupBuy.totalDiscountAmountComment')"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #fc6181;"
          >{{this.totalDiscountAmount,[this.currency[0]]}}</div>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="title">{{$t('groupBuy.costBenefitRatio')}}</div>
            <el-tooltip
              class="item"
              effect="light"
              :content="$t('groupBuy.costBenefitRatioComment')"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #fdb64a;"
          >{{this.costBenefitRatio}}%</div>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="title">{{$t('groupBuy.numberNewTransactions')}}</div>
            <el-tooltip
              class="item"
              effect="light"
              :content="$t('groupBuy.numberNewTransactions')"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #3dcf9a;"
          >{{this.numberNewTransactions}}</div>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="title">{{$t('groupBuy.oldNumberUsers')}}</div>
            <el-tooltip
              class="item"
              effect="light"
              :content="$t('groupBuy.oldNumberUsersComment')"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #8379f7;"
          >{{this.oldNumberUsers}}</div>
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
        dateList: [],
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
        console.log('aaaaaaaaaaaaaaaaaaaaa', this.echartData.marketPriceList)
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
            data: this.echartData.goodsPriceList
          },
          {
            name: this.echartInit.legendData[1],
            yAxisIndex: 0,
            color: this.echartInit.colors[1],
            type: 'line',
            data: this.echartData.marketPriceList
          },
          {
            name: this.echartInit.legendData[2],
            type: 'line',
            color: this.echartInit.colors[2],
            yAxisIndex: 1,
            data: this.echartData.ratioList
          },
          {
            name: this.echartInit.legendData[3],
            type: 'line',
            color: this.echartInit.colors[3],
            yAxisIndex: 0,
            stack: '总量',
            data: this.echartData.oldUserList
          },
          {
            name: this.echartInit.legendData[4],
            type: 'line',
            color: this.echartInit.colors[4],
            yAxisIndex: 0,
            stack: '总量',
            data: this.echartData.newUserList
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
      this.totalAmountPaid = data.totalPrice
      this.totalDiscountAmount = data.totalMarketPrice
      this.costBenefitRatio = data.totalRatio
      this.numberNewTransactions = data.totalNewUseer
      this.oldNumberUsers = data.totalOldUser
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
