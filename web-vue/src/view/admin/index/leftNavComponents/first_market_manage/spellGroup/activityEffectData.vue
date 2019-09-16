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
            default-time="12:00:00"
          >
          </el-date-picker>
          <span>{{$t('marketCommon.to')}}</span>
          <el-date-picker
            size="small"
            v-model="endDate"
            type="datetime"
            :placeholder="$t('groupBuy.endDate')"
            default-time="12:00:00"
          >
          </el-date-picker>
        </div>
        <el-button
          style="margin-left: 10px;"
          type="primary"
          size="mini"
        >{{$t('marketCommon.filter')}}</el-button>
      </div>

      <!-- 表格数据部分 -->
      <section>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="title">{{$t('groupBuy.totalAmountPaid')}}</div>
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
          >{{this.totalAmountPaid}}</div>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="title">{{$t('groupBuy.totalDiscountAmount')}}</div>
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
          >{{this.totalDiscountAmount}}</div>
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
          <el-button @click="updateEcharts">aaaaaaa
          </el-button>
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
      starDate: 0,
      endDate: 0,
      totalAmountPaid: 0,
      totalDiscountAmount: 0,
      costBenefitRatio: 0,
      numberNewTransactions: 0,
      oldNumberUsers: 0,
      colors: ['#5A8BFF', '#fc6181', '#fdb64a', '#3dcf9a', '#8379f7'],
      legendData: this.$t('groupBuy.legendData'),
      dateDataList: ['2019-09-01', '2019-09-02', '2019-09-03', '2019-09-04', '2019-09-05', '2019-09-06', '2019-09-07', '2019-09-08', '2019-09-09', '2019-09-10'],
      option: {},
      myChart: {}
    }
  },
  watch: {
    lang () {
      this.legendData = this.$t('groupBuy.legendData')
    }
  },
  mounted () {
    this.initEcharts()
  },
  methods: {
    initEcharts () {
      this.myChart = echarts.init(document.getElementById('charts'))
      let obj = {}
      groupBuyAnalysis(obj).then(res => {
        console.log(res)
      })
      this.option = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: this.legendData
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
            data: this.dateDataList,
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
            name: this.legendData[0],
            type: 'line',
            color: this.colors[0],
            yAxisIndex: 0,
            stack: '总量',
            data: [120, 132, 101, 134, 90, 230, 210, 220, 240, 220]
          },
          {
            name: this.legendData[1],
            yAxisIndex: 0,
            color: this.colors[1],

            type: 'line',
            stack: '总量',
            data: [220, 182, 191, 234, 290, 330, 310, 300, 320, 323]
          },
          {
            name: this.legendData[2],
            type: 'line',
            color: this.colors[2],

            yAxisIndex: 1,
            stack: '总量',
            data: [150, 232, 201, 154, 190, 330, 410, 440, 430, 424]
          },
          {
            name: this.legendData[3],
            type: 'line',
            color: this.colors[3],

            yAxisIndex: 0,
            stack: '总量',
            data: [320, 332, 301, 334, 390, 330, 320, 313, 329, 321]
          },
          {
            name: this.legendData[4],
            type: 'line',
            color: this.colors[4],
            yAxisIndex: 0,
            stack: '总量',
            data: [820, 932, 901, 934, 1290, 1330, 1320, 1235, 1335, 1285]
          }
        ]
      }
      this.myChart.setOption(this.option)
      // window.addEventListener('resize', function () { myChart.resize() })
    },
    updateEcharts () {
      console.log('国际化', this.legendData)
      console.log('国际化', this.$t('groupBuy.legendData'))
      this.legendData = this.$t('groupBuy.legendData')
      this.initEcharts()
      // this.myChart.setOption({
      //   legend: {
      //     data: this.$t('groupBuy.legendData')
      //   },
      //   yAxis: [
      //     {
      //       name: this.$t('groupBuy.number'),
      //       type: 'value'
      //     },
      //     {
      //       name: this.$t('groupBuy.costBenefitRatio'),
      //       type: 'value'
      //     }
      //   ]
      // })
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
