<template>
  <!-- 推送统计 -->
  <div class="pushStatistics">
    <div class="inline">
      <div class="time">
        <span>{{$t(`messagePush.filterDate`)}}</span>
        <el-select
          v-model="value"
          :placeholder="$t(`messagePush.pleaseSelectAFilterDate`)"
          size="small"
          @change="timeChange"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>

      </div>
      <div
        class="timeAndBtn"
        v-if="isShowTimePicker"
      >
        <div class="timePicker">
          <dateTimePicker
            :showPicker=1
            @time="getChooseTime"
          />
        </div>
        <div>
          <el-button
            type="primary"
            size="small"
            @click="handleFilter"
          >{{$t(`messagePush.btnFilter`)}}</el-button>
        </div>
      </div>

    </div>
    <!-- 比率展示 -->
    <div class="ulListContent">
      <ul class="ulList">
        <li>
          <div>{{$t(`messagePush.messagePushNumber`)}}</div>
          <div
            class="number1"
            v-loading="loading"
          >{{sendNumber}}</div>
          <el-image
            class="left_image"
            style="width:44px;height:40px"
            :src="urls.url4"
          ></el-image>
        </li>
        <li>
          <div>{{$t(`messagePush.messageSentNumber`)}}</div>
          <div
            class="number2"
            v-loading="loading"
          >{{sendSuccessNumber}}</div>
          <el-image
            class="left_image"
            style="width:44px;height:40px"
            :src="urls.url1"
          ></el-image>
        </li>
        <li>
          <div>{{$t(`messagePush.numberOfReturnVisits`)}}</div>
          <div
            class="number3"
            v-loading="loading"
          >{{visitNumber}}</div>
          <el-image
            class="left_image"
            style="width:44px;height:40px"
            :src="urls.url2"
          ></el-image>
        </li>
        <li>
          <div>{{$t(`messagePush.averageReturnVisitRate`)}}</div>
          <div
            class="number4"
            v-loading="loading"
          >{{visitPercentage}}</div>
          <el-image
            class="left_image"
            style="width:44px;height:40px"
            :src="urls.url3"
          ></el-image>
        </li>
      </ul>
    </div>

    <!-- 图表 -->
    <div>
      <div id="charts">
        <ve-line
          v-loading="loading"
          :data="chartData"
          :settings="chartSettings"
        ></ve-line>
      </div>
    </div>
  </div>
</template>
<script>
// 引入推送统计api
import { analysisApi } from '@/api/admin/marketManage/messagePush.js'
// 引入选择时间组件
import dateTimePicker from '@/components/admin/dateTimePicker/dateTimePicker'
export default {
  name: `pushStatistics`,
  components: { dateTimePicker },
  data () {
    this.chartSettings = {
      axisSite: { right: ['回访率'] },
      yAxisType: ['KMB', 'KMB'],
      yAxisName: ['数量', '百分比']
    }
    return {
      /**
       * 图表的数据
       */
      chartData: {
        columns: [
          '日期',
          '消息推送数量',
          '消息送达数量',
          '回访数量',
          '回访率'
        ],
        rows: [

        ]
      },
      /**
       * options
       */
      options: [
        {
          label: this.$t(`messagePush.lastDay`),
          value: this.$t(`messagePush.lastDay`)
        },
        {
          label: this.$t(`messagePush.lastSevenDays`),
          value: this.$t(`messagePush.lastSevenDays`)
        },
        {
          label: this.$t(`messagePush.lastMonth`),
          value: this.$t(`messagePush.lastMonth`)
        },
        {
          label: this.$t(`messagePush.customize`),
          value: this.$t(`messagePush.customize`)
        }
      ],
      value: this.$t(`messagePush.lastDay`),
      urls: {
        url1: `${this.$imageHost}/image/admin/any_coner/any_coner_blue.png`,
        url2: `${this.$imageHost}/image/admin/any_coner/any_coner_pink.png`,
        url3: `${this.$imageHost}/image/admin/any_coner/any_coner_orange.png`,
        url4: `${this.$imageHost}/image/admin/any_coner/any_coner_green.png`
      },
      sendNumber: null,
      sendSuccessNumber: null,
      visitNumber: null,
      visitPercentage: null,
      dataList: [{
        date: `"2019-09-18"`,
        sendNumber: 0, // 消息推送数量
        sendSuccessNumber: 0, // 消息送达数量
        visitNumber: 0, // 回访数量
        visitPercentage: 0 // 平均回访率
      }, {
        date: `"2019-09-18"`,
        sendNumber: 0, // 消息推送数量
        sendSuccessNumber: 0, // 消息送达数量
        visitNumber: 0, // 回访数量
        visitPercentage: 0 // 平均回访率
      }, {
        date: `"2019-09-18"`,
        sendNumber: 0, // 消息推送数量
        sendSuccessNumber: 0, // 消息送达数量
        visitNumber: 0, // 回访数量
        visitPercentage: 0 // 平均回访率
      }],
      loading: false,
      isShowTimePicker: false

    }
  },

  created () {
    this.fetchData({
      'startTime': this.getTime().lastDay,
      'endTime': this.getTime().now
    })
  },
  watch: {
    // data内变量国际化

  },
  mounted () {
    this.langDefault()
  },
  methods: {
    // 获取数据
    fetchData (params) {
      this.loading = true
      analysisApi(params).then(res => {
        console.log(res)
        const { error, content } = res
        if (error === 0) {
          console.log(content)
          const { sendNumber, sendSuccessNumber, visitNumber, visitPercentage } = content
          this.sendNumber = sendNumber
          this.sendSuccessNumber = sendSuccessNumber
          this.visitNumber = visitNumber
          this.visitPercentage = visitPercentage
          this.formatAllStatistics(content.allStatistics)
          this.loading = false
        }
      }
      ).catch(err => console.log(err))
    },
    formatAllStatistics (data) {
      console.log(data)
      let arr = []
      data.forEach((item, index) => {
        arr.push({
          '日期': item.date,
          '消息推送数量': item.sendNumber,
          '消息送达数量': item.sendSuccessNumber,
          '回访数量': item.visitNumber,
          '回访率': item.visitPercentage
        })
      })
      this.chartData.rows = arr
    },
    // timeChange 当选择的时间发生变化的时候
    timeChange (val) {
      console.log(val)
      const now = this.getTime().now
      const last7 = this.getTime().last7
      const last30 = this.getTime().last30
      const lastDay = this.getTime().lastDay
      console.log(lastDay)
      switch (val) {
        case this.$t(`messagePush.lastDay`):
          this.fetchData({
            'startTime': lastDay,
            'endTime': now
          })
          break
        case this.$t(`messagePush.lastSevenDays`):
          this.fetchData({
            'startTime': last7,
            'endTime': now
          })
          break
        case this.$t(`messagePush.lastMonth`):
          this.fetchData({
            'startTime': last30,
            'endTime': now
          })
          break
        case this.$t(`messagePush.customize`):
          this.isShowTimePicker = true
          this.loading = true
          break
        default:
          break
      }
    },
    getChooseTime (val) {
      console.log(val)
      this.fetchData(val)
    },

    // 获取时间
    getTime () {
      let now = this.moment().format('YYYY-MM-DD 00:00:00')
      let lastDay = this.moment().subtract(1, 'days').format('YYYY-MM-DD 00:00:00')
      var last7 = this.moment().subtract(6, 'days').format('YYYY-MM-DD 00:00:00') // 7天前
      var last30 = this.moment().subtract(29, 'days').format('YYYY-MM-DD 00:00:00')// 30天前
      return { now, lastDay, last7, last30 }
    },
    // 筛选
    handleFilter () {

    }
  }
}
</script>

<style lang="scss" scoped>
.pushStatistics {
  padding: 10px;
  .inline {
    display: flex;
    .time {
      margin-bottom: 10px;
      margin-right: 10px;
    }
    .timeAndBtn {
      display: flex;
      .timePicker {
        margin-right: 10px;
      }
    }
  }

  .ulListContent {
    display: flex;
    justify-content: center;
    .ulList {
      width: 1000px;
      height: 130px;
      display: flex;
      border: 1px solid #eee;
      li {
        position: relative;
        width: 25%;
        height: 130px;
        border-right: 1px solid #eee;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        .number1 {
          color: #3dcf9a;

          font-size: 30px;
          margin-top: 10px;
        }
        .number2 {
          color: #5a8bff;

          font-size: 30px;
          margin-top: 10px;
        }
        .number3 {
          color: #fc6181;

          font-size: 30px;
          margin-top: 10px;
        }
        .number4 {
          color: #fdb64a;
          font-size: 30px;
          margin-top: 10px;
        }
        .left_image {
          position: absolute;
          bottom: 1px;
          left: 0;
        }
      }
    }
  }
  #charts {
    width: 90%;
    margin-top: 20px;
  }
}
</style>
