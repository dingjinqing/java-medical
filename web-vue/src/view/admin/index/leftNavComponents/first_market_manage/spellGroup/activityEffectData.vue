<!--
* 效果数据页面
*
* @author:赵鑫
-->
<template>
  <div>
    <wrapper class="dataContent">
      <div style="display:flex">
        <div style="height:32px;line-height:32px">筛选日期：</div>
        <div class="selectTime">
          <el-date-picker
            size="small"
            v-model="value1"
            type="datetime"
            placeholder="选择开始时间"
            default-time="12:00:00"
          >
          </el-date-picker>
          <span>至</span>
          <el-date-picker
            size="small"
            v-model="value1"
            type="datetime"
            placeholder="选择接受时间"
            default-time="12:00:00"
          >
          </el-date-picker>
        </div>
        <el-button
          style="margin-left: 10px;"
          type="primary"
          size="mini"
        >筛选</el-button>
      </div>

      <section>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="title">活动实付总金额(元)</div>
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
            <div class="title">活动优惠总金额(元)</div>
            <el-tooltip
              class="item"
              effect="light"
              content="活动优惠的总金额"
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
            <div class="title">费效比</div>
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
            <div class="title">新成交用户数</div>
            <el-tooltip
              class="item"
              effect="light"
              content="活动带来的首次在店铺下单的用户数"
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
            <div class="title">老成交用户数</div>
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
import wrapper from '@/components/admin/wrapper/wrapper'
import echarts from 'echarts'

export default {
  components: { wrapper },
  data () {
    return {
      value1: ''
    }
  },
  mounted () {
    // let this_ = this
    let myChart = echarts.init(document.getElementById('charts'))
    let option = {
      tooltip: {
        trigger: 'axis'
      },
      legend: {
        data: ['活动实付总金额', '活动优惠总金额', '费效比', '新成交用户数', '老成家用户数']
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      // toolbox: {
      //   feature: {
      //     saveAsImage: {}
      //   }
      // },
      xAxis: [
        {
          type: 'category',
          data: ['2019-09-01', '2019-09-02', '2019-09-03', '2019-09-04', '2019-09-05', '2019-09-06', '2019-09-07', '2019-09-08', '2019-09-09', '2019-09-10'],
          boundaryGap: false
        }
      ],
      yAxis: [
        {
          type: 'value'
        }
      ],
      series: [
        {
          name: '活动实付总金额',
          type: 'line',
          stack: '总量',
          data: [120, 132, 101, 134, 90, 230, 210, 220, 240, 220]
        },
        {
          name: '活动优惠总金额',
          type: 'line',
          stack: '总量',
          data: [220, 182, 191, 234, 290, 330, 310, 300, 320, 323]
        },
        {
          name: '费效比',
          type: 'line',
          stack: '总量',
          data: [150, 232, 201, 154, 190, 330, 410, 440, 430, 424]
        },
        {
          name: '新成交用户数',
          type: 'line',
          stack: '总量',
          data: [320, 332, 301, 334, 390, 330, 320, 313, 329, 321]
        },
        {
          name: '老成交用户数',
          type: 'line',
          stack: '总量',
          data: [820, 932, 901, 934, 1290, 1330, 1320, 1235, 1335, 1285]
        }
      ]
    }
    myChart.setOption(option)

    // 建议加上以下这一行代码，不加的效果图如下（当浏览器窗口缩小的时候）。超过了div的界限（红色边框）
    // window.addEventListener('resize', function () { myChart.resize() })
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
