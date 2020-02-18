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
      <section style="margin-top: 30px;">
        <div class="fromInfo topLine">
          <div style="display:flex">
            <div class="titless">{{ this.$t('seckill.payment') }}</div>
            <el-tooltip
              class="item"
              effect="light"
              :content="this.$t('seckill.paymentTip')"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >{{ total.totalPayment }}</div>
        </div>
        <div class="fromInfo topLine">
          <div style="display:flex">
            <div class="titless">{{ this.$t('seckill.discount') }}</div>
            <el-tooltip
              class="item"
              effect="light"
              :content="this.$t('seckill.discountTip')"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #fc6181;"
          >{{ total.totalDiscount }}</div>
        </div>
        <div class="fromInfo topLine">
          <div style="display:flex">
            <div class="titless">{{ this.$t('seckill.costEffect') }}</div>
            <el-tooltip
              class="item"
              effect="light"
              :content="this.$t('seckill.costEffectTip')"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #fdb64a;"
          >{{ total.totalCostEffectivenessRatio === 0 ? 0 : ((total.totalDiscount / total.totalPayment) * 100).toFixed(2) }}%</div>
        </div>
        <div class="fromInfo topLine">
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
            style="color: #3dcf9a;"
          >{{ total.totalNewUserNumber }}</div>
        </div>
        <div class="fromInfo topLine">
          <div style="display:flex">
            <div class="titles">付款商品件数</div>
            <el-tooltip
              class="item"
              effect="light"
              content="活动付款的商品件数"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #8379f7;"
          >{{ total.totalOldUserNumber }}</div>
        </div>
      </section>

      <section style="margin-bottom: 30px;">
        <div class="fromInfo">
          <div style="display:flex">
            <div class="titless">{{ this.$t('seckill.newUser') }}</div>
            <el-tooltip
              class="item"
              effect="light"
              :content="this.$t('seckill.newUser')"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #3dcf9a;"
          >{{ total.totalNewUserNumber }}</div>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="titles">{{ this.$t('seckill.oldUser') }}</div>
            <el-tooltip
              class="item"
              effect="light"
              :content="this.$t('seckill.oldUserTip')"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #8379f7;"
          >{{ total.totalOldUserNumber }}</div>
        </div>
      </section>

      <div id="charts"></div>

    </wrapper>
  </div>
</template>

<script>
import { effactSeckillList } from '@/api/admin/marketManage/seckill.js'
import wrapper from '@/components/admin/wrapper/wrapper'
import echarts from 'echarts'

export default {
  components: { wrapper },
  data () {
    return {
      starDate: this.moment().startOf('month').format('YYYY-MM-DD HH:mm:ss'),
      endDate: this.moment().format('YYYY-MM-DD HH:mm:ss'),
      total: {}, // 表格
      echartInit: {
        colors: ['#5A8BFF', '#fc6181', '#fdb64a', '#3dcf9a', '#8379f7', '#5A8BFF', '#fc6181'],
        legendData: this.$t('groupBuy.legendData')
      },
      echartData: {},
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
        skId: this.$route.query.id,
        startTime: this.starDate,
        endTime: this.endDate
      }
      this.handleEcharts()
      effactSeckillList(params).then((res) => {
        if (res.error === 0) {
          this.total = res.content.total
          this.echartData = res.content
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
            data: this.echartData.paymentAmount
          },
          {
            name: this.echartInit.legendData[1],
            yAxisIndex: 0,
            color: this.echartInit.colors[1],

            type: 'line',
            stack: '总量',
            data: this.echartData.discountAmount
          },
          {
            name: this.echartInit.legendData[2],
            type: 'line',
            color: this.echartInit.colors[2],

            yAxisIndex: 1,
            stack: '总量',
            data: this.echartData.costEffectivenessRatio
          },
          {
            name: '付款订单数',
            type: 'line',
            color: this.echartInit.colors[3],

            yAxisIndex: 0,
            stack: '总量',
            data: this.echartData.ordersNumber
          },
          {
            name: '付款商品件数',
            type: 'line',
            color: this.echartInit.colors[4],
            yAxisIndex: 0,
            stack: '总量',
            data: this.echartData.goodsNumber
          },
          {
            name: this.echartInit.legendData[3],
            type: 'line',
            color: this.echartInit.colors[5],

            yAxisIndex: 0,
            stack: '总量',
            data: this.echartData.oldUserNumber
          },
          {
            name: this.echartInit.legendData[4],
            type: 'line',
            color: this.echartInit.colors[6],
            yAxisIndex: 0,
            stack: '总量',
            data: this.echartData.newUserNumber
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
    height: 130px;
    width: 100%;
    display: flex;
    flex-flow: row wrap;
    .topLine {
      border-top: 1px solid #eee;
    }
    .fromInfo:first-child {
      border-left: 1px solid #eee;
    }
    .fromInfo {
      flex: 0 1 19%;
      height: 130px;
      position: relative;
      border-right: 1px solid #eee;
      border-bottom: 1px solid #eee;
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
