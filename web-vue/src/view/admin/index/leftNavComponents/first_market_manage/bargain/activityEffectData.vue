<!--
* 砍价 - 效果数据页面
*
* @author:赵鑫
-->
<template>
  <div>
    <wrapper
      class="dataContent"
      v-loading="loading"
    >
      <!-- 日期筛选部分 -->
      <div style="display:flex">
        <div style="height:32px;line-height:32px">筛选日期：</div>
        <div class="selectTime">
          <el-date-picker
            v-model="selectDate"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd HH:mm:ss"
            size="small"
          >
          </el-date-picker>
        </div>
        <el-button
          @click="initDataList"
          style="margin-left: 10px;"
          type="primary"
          size="mini"
        >筛选</el-button>
      </div>

      <!-- 表格数据部分 -->
      <section>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="title">发起砍价用户数</div>
            <el-tooltip
              class="item"
              effect="light"
              content="发起砍价用户数"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >{{totalNumbers.recordTotal}}</div>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="title">帮砍人次</div>
            <el-tooltip
              class="item"
              effect="light"
              content="活动帮助砍价的总人次"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #fc6181;"
          >{{totalNumbers.userTotal}}</div>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="title">活动订单数</div>
            <el-tooltip
              class="item"
              effect="light"
              content="活动已付款订单数量（包括退款/货）"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #fdb64a;"
          >{{totalNumbers.orderTotal}}</div>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="title">拉新用户数</div>
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
            style="color: #3dcf9a;"
          >{{totalNumbers.sourceTotal}}</div>
        </div>
      </section>

      <!-- echarts图表部分 -->
      <div id="charts"></div>

    </wrapper>
  </div>
</template>

<script>
import wrapper from '@/components/admin/wrapper/wrapper'
import echarts from 'echarts'
import { getRecordAnalysisData } from '@/api/admin/marketManage/bargain.js'

export default {
  components: { wrapper },
  data () {
    return {
      loading: false,
      actId: null,
      requestParams: {},
      selectDate: '',
      bargainAnalysisTotalVo: {},
      echartsData: {},
      totalNumbers: {},
      originalData: {},
      myChart: {}
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.requestParams.bargainId = this.actId
      this.requestParams.startTime = this.selectDate[0]
      this.requestParams.endTime = this.selectDate[1]
      getRecordAnalysisData(this.requestParams).then((res) => {
        if (res.error === 0) {
          this.originalData = res.content
          console.log(this.originalData)
          let originalData = JSON.parse(JSON.stringify(this.originalData))
          this.handleData(originalData)
          this.loading = false
        }
      })
    },

    handleData (data) {
      // 顶部数据部分
      this.totalNumbers = data.total

      // echarts数据渲染
      this.echartsData.tooltip = { trigger: 'axis' }
      this.echartsData.legend = { name: ['发起砍价用户数 ', '帮砍人次 ', '活动订单数 ', '拉新用户数 '] }
      this.echartsData.grid = { left: '3%', right: '4%', bottom: '3%', containLabel: true }
      this.echartsData.xAxis = [
        {
          type: 'category',
          data: data.dateList,
          boundaryGap: false
        }
      ]
      this.echartsData.yAxis = [
        {
          type: 'value'
        }
      ]
      this.echartsData.series = [
        {
          name: '发起砍价用户数',
          type: 'line',
          stack: '总量',
          data: data.recordNumber
        },
        {
          name: '帮砍人次',
          type: 'line',
          stack: '总量',
          data: data.userNumber
        },
        {
          name: '活动订单数',
          type: 'line',
          stack: '总量',
          data: data.orderNumber
        },
        {
          name: '拉新用户数',
          type: 'line',
          stack: '总量',
          data: data.sourceNumber
        }
      ]

      this.myChart.setOption(this.echartsData)
    }

  },
  mounted () {
    if (this.$route.query.id > 0) {
      this.actId = this.$route.query.id
      this.selectDate = []
      this.selectDate.push(this.$route.query.startTime)
      this.selectDate.push(this.$route.query.endTime)
      this.initDataList()
    }
    this.myChart = echarts.init(document.getElementById('charts'))

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
