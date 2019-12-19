<template>
  <div class="tradesStatistics">

    <!-- 地域分布 -->
    <div class="tradesContainer">
      <section class="label">
        <div class="labelName">
          <div class="labelItem">{{$t('tradesStatistics.areaDistribute')}}</div>
          <div class="labelInfo">
            <el-tooltip
              effect="light"
              placement="right-start"
            >
              <div
                slot="content"
                style="width: 500px;line-height: 30px;font-size: 14px;padding:10px 5px 10px 10px"
              >
                <section
                  v-for="item in areaTipsList"
                  :key="item.title"
                  style="display: flex"
                >
                  <div style="width: 30%;color:#999">{{item.title}}</div>
                  <div style="width: 70%;color: #353535">{{item.content}}</div>
                </section>
              </div>
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
        </div>
        <el-date-picker
          v-model="value2"
          type="month"
          size="small"
          @change="selectMonthHandler"
          value-format="yyyy-MM"
          :placeholder="$t('tradesStatistics.selectMonth')"
          class="selectMonth"
        >
        </el-date-picker>
        <div class='wrapperCharts'>
          <div class="mapArea">
            <div
              class="map"
              id="charts"
              ref="myEchart"
            ></div>
          </div>
          <div class="tableWrapper">
            <div class="table_lists">
              <el-table
                header-row-class-name="areaTable"
                :default-sort="{prop: 'province', order: 'ascending'}"
                :data="areaData"
                border
              >
                <el-table-column
                  prop="province"
                  :label="$t('tradesStatistics.province')"
                  sortable
                  align="center"
                ></el-table-column>
                <el-table-column
                  prop="totalDealMoney"
                  :label="$t('tradesStatistics.paymentAmount')"
                  sortable
                  align="center"
                ></el-table-column>
                <el-table-column
                  prop="orderUserNum"
                  :label="$t('tradesStatistics.paymentNumber')"
                  sortable
                  align="center"
                ></el-table-column>
                <el-table-column
                  prop="uv"
                  :label="$t('tradesStatistics.visitorNumber')"
                  sortable
                  align="center"
                ></el-table-column>
                <el-table-column
                  prop="uv2paid"
                  :label="$t('tradesStatistics.visitToPayRate')"
                  sortable
                  align="center"
                ></el-table-column>
                <el-table-column
                  prop="orderNum"
                  :label="$t('tradesStatistics.orderNumber')"
                  sortable
                  align="center"
                ></el-table-column>
              </el-table>
            </div>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import echarts from 'echarts'
import '../../../../../../../node_modules/echarts/map/js/china.js'

import { tradeAreaApi } from '@/api/admin/firstWebManage/tradesStatistics/tradesStatistics.js'

export default {
  name: 'echarts',
  watch: {
    lang () {
      this.areaTipsList = this.$t('tradesStatistics.areaTipsList')
    }
  },
  created () {
    this.initDataList()
  },
  mounted () {
    this.langDefault()
    this.chinaConfigure()
    // this.myChart = echarts.init(document.getElementById('charts'))
  },
  beforeDestroy () {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  data () {
    return {
      value1: '',
      value2: '',
      chart: null,
      areaParams: {
        'screeningTime': '2019-08',
        'orderByField': 'total_deal_money',
        'orderByType': 'asc',
        'limitNum': 5
      },
      myChart: {},
      areaData: [],
      maxTradeMoney: '',
      minTradeMoney: '',
      timeSelect: '',
      // areaTipsList: [
      //   { title: '付款金额', content: '统计时间内，该地域访问用户的所有付款订单金额之和（拼团在成团时计入付款金额；货到付款在发货时计入付款金额，不剔除退款金额）' },
      //   { title: '付款人数', content: '统计时间内，该地域访问用户中下单并且付款成功的客户数，一人多次付款记为一人（不剔除退款订单）' },
      //   { title: '访客数', content: '统计时间内，该地域访问用户的所有访客数' },
      //   { title: '访问-付款转化率', content: '统计时间内，该地域付款人数/该地域访客数' },
      //   { title: '订单数', content: '统计时间内，该地区收货订单数 ' }
      // ]
      areaTipsList: this.$t('tradesStatistics.areaTipsList')
    }
  },
  methods: {
    // 地域分布 - 选择月份
    selectMonthHandler (val) {
      this.areaParams.screeningTime = val
      console.log(val)
      this.initDataList()
    },
    // 初始化加载数据
    initDataList () {
      // 交易地域分布
      tradeAreaApi(this.areaParams).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.areaData = res.content.vos
          console.log(this.areaData)
          this.maxTradeMoney = res.content.maxTotalDealMoney
          this.minTradeMoney = res.content.minTotalDealMoney
        }
      }).catch(err => console.log(err))
    },

    chinaConfigure () {
      let myChart = echarts.init(this.$refs.myEchart) // 这里是为了获得容器所在位置
      window.onresize = myChart.resize
      myChart.setOption({ // 进行相关配置
        tooltip: {}, // 鼠标移到图里面的浮动提示框
        dataRange: {
          show: true,
          orient: 'horizontal',
          x: 'center',
          bottom: '10px',
          itemGap: '20px',
          itemHeight: '300px',
          min: this.minTradeMoney,
          max: this.maxTradeMoney,
          // min: 1111,
          // max: 1112,
          text: ['北京', '新疆'],
          // splitNumber: '10',
          realtime: true,
          calculable: true,
          color: ['orangered', 'lightskyblue']
        },
        geo: { // 这个是重点配置区
          map: 'china', // 表示中国地图
          roam: false, // 地图是否可以进行缩放
          label: {
            normal: {
              show: false, // 是否显示对应地名
              textStyle: {
                color: 'rgba(0,0,0,0.4)'
              }
            }
          },
          itemStyle: { // 地图外观定制，其中normal表示正常显示的样式，emphasis表示鼠标悬浮下样式
            normal: {
              borderColor: 'rgba(51, 51, 51)',
              backgroundColor: 'rgba(50,50,50,0.7)',
              borderRadius: '4px'
            },
            emphasis: {
              areaColor: null, // 图标选择区域颜色
              shadowOffsetX: 0,
              shadowOffsetY: 0,
              shadowBlur: 20,
              borderWidth: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        },
        series: [{ // 整体配置 其中type很关键 表示该例是地图，data:图表所用数据
          type: 'scatter',
          coordinateSystem: 'geo' // 对应上方配置
        },
        {
          name: '启动次数', // 浮动框的标题
          type: 'map',
          geoIndex: 0,
          data: []
        }]
      })
    }
  }
}

</script>
<style lang="scss" scoped>
.tradesStatistics {
  padding: 10px;
  font-size: 14px;
  .tradesContainer {
    position: relative;
    padding: 0 10px 10px;
    background: #fff;
    .label {
      .selectMonth {
        width: 150px;
      }
      .labelName {
        display: flex;
        height: 50px;
        line-height: 50px;
        color: #333;
        .labelInfo {
          margin-left: 5px;
        }
      }
      .time {
        margin-top: -10px;
        .timeSelect {
          width: 140px;
          margin: 0 10px 0 2px;
        }
        .custom {
          width: 225px;
          margin-right: 10px;
        }
      }
      .wrapperCharts {
        display: flex;
        justify-content: space-between;
        width: 100%;
        height: 500px;
        // border: 1px solid #000;
        .mapArea {
          width: 45%;
          #charts {
            height: 500px;
            width: 100%;
            position: relative;
            // border: 10px solid #f40;
          }
        }
        .tableWrapper {
          width: 50%;
          margin-left: 2%;
          margin-right: 2%;
          margin-top: 50px;
          height: 500px;
          /deep/ .areaTable th {
            background-color: #f5f5f5;
            border: none;
            height: 36px;
            color: #000;
            font-size: 14px;
          }
          .table_lists {
            position: relative;
            background-color: #fff;
            padding: 10px 0 10px;
          }
        }
      }

      /deep/ .tableClss th {
        background-color: #f5f5f5;
        border: none;
        height: 36px;
        color: #000;
        padding: 8px 10px;
        font-size: 14px;
      }
      .table_list {
        position: relative;
        background-color: #fff;
        padding: 10px 0 10px;
      }
    }
  }
}
</style>
