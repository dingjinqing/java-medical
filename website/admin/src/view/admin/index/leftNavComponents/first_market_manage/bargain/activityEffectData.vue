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
        <div style="height:32px;line-height:32px">{{$t('marketCommon.filterDate')}}:</div>
        <div class="selectTime">
          <el-date-picker
            v-model="selectDate"
            type="datetimerange"
            :range-separator="$t('marketCommon.to')"
            :start-placeholder="$t('marketCommon.startTime')"
            :end-placeholder="$t('marketCommon.endTime')"
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
        >{{$t('marketCommon.filter')}}</el-button>
      </div>

      <!-- 表格数据部分 -->
      <section>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="title">{{$t('bargainList.bargainRecordNumber')}}</div>
            <el-tooltip
              class="item"
              effect="light"
              :content="$t('bargainList.bargainRecordNumber')"
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
            <div class="title">{{$t('bargainList.helpCutPeople')}}</div>
            <el-tooltip
              class="item"
              effect="light"
              :content="$t('bargainList.helpCutPeopleTip')"
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
            <div class="title">{{$t('bargainList.actOrderNumber')}}</div>
            <el-tooltip
              class="item"
              effect="light"
              :content="$t('bargainList.actOrderNumberTip')"
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
            <div class="title">{{$t('bargainList.sourceUserNumber')}}</div>
            <el-tooltip
              class="item"
              effect="light"
              :content="$t('bargainList.sourceUserNumberTip')"
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
      myChart: {},
      langFlag: '',
      flag: true
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
      this.echartsData.legend = { name: [this.$t('bargainList.bargainRecordNumber'), this.$t('bargainList.helpCutPeople'), this.$t('bargainList.actOrderNumber'), this.$t('bargainList.sourceUserNumber')] }
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
          name: this.$t('bargainList.bargainRecordNumber'),
          type: 'line',
          stack: this.$t('marketCommon.totalAmount'),
          data: data.recordNumber
        },
        {
          name: this.$t('bargainList.helpCutPeople'),
          type: 'line',
          stack: this.$t('marketCommon.totalAmount'),
          data: data.userNumber
        },
        {
          name: this.$t('bargainList.actOrderNumber'),
          type: 'line',
          stack: this.$t('marketCommon.totalAmount'),
          data: data.orderNumber
        },
        {
          name: this.$t('bargainList.sourceUserNumber'),
          type: 'line',
          stack: this.$t('marketCommon.totalAmount'),
          data: data.sourceNumber
        }
      ]

      this.myChart.setOption(this.echartsData)
    }

  },
  watch: {
    // data内变量国际化
    lang () {
      if (this.langFlag === this.lang && this.flag) return
      this.flag = false
      // 重新渲染表格数据
      let originalData = JSON.parse(JSON.stringify(this.originalData))
      this.handleData(originalData)
    }
  },
  mounted () {
    this.langFlag = localStorage.getItem('WEPUBAO_LANGUAGE')
    if (this.$route.query.id > 0) {
      this.actId = this.$route.query.id
      this.selectDate = []
      this.selectDate.push(this.$route.query.startTime)
      this.selectDate.push(this.$route.query.endTime)
      this.initDataList()
    }
    this.myChart = echarts.init(document.getElementById('charts'))
    this.langDefault()
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
