<!--
埋点报表 -kdc
 -->
<template>
    <div class="main">

        <div class="nav_box">
            <div class="text-title">
                事件
            </div>
            <div class="filters">
                <div class="filters_item">
                    <span class="fil_span">事件：</span>
                    <el-select
                            v-model="param.event"
                            size="small"
                            @change="eventChangeHandler"
                            class="timeSelect">
                        <el-option label="全部" value="null"></el-option>
                        <el-option
                                v-for="item in eventList"
                                :key="item.event"
                                :label="item.eventName"
                                :value="item.event">
                        </el-option>
                    </el-select>
                </div>
                <div class="filters_item">
                    <span class="fil_span">关键词：</span>
                    <el-select
                            v-model="param.key"
                            size="small"
                            @change="keyChangeHandler"
                            class="timeSelect">
                        <el-option
                                v-for="item in keyList"
                                :key="item.key"
                                :label="item.key"
                                :value="item.key"></el-option>
                    </el-select>
                </div>
                <div class="filters_item">
                    <span class="fil_span">时间：</span>
                    <el-select
                            v-model="timeSelect"
                            size="small"
                            @change="selectTimeChangeHandler"
                            class="timeSelect"
                    >
                        <el-option
                                v-for="item in timeRange"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        ></el-option>
                    </el-select>
                    <el-date-picker
                            v-if="timeSelect===-1"
                            v-model="timeValue"
                            type="daterange"
                            size="small"
                            value-format="yyyyMMdd"
                            @change="changeTimeDate"
                            range-separator="-"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期">
                    </el-date-picker>
                    <span class="choosed_time">{{this.time.startDate.year}}年{{this.time.startDate.month}}月{{this.time.startDate.day}}日 - {{this.time.endDate.year}}年{{this.time.endDate.month}}月{{this.time.endDate.day}}日</span>
                </div>

                <div class="btn_wrap">
                    <el-button
                            type='primary'
                            size='small'
                            @click="initEcharts">查询</el-button>
                </div>
            </div>
        </div>
        <div class="line">
            <div id="moneyCharts"></div>
        </div>
    </div>
</template>

<script>
import { getEventKeyMap, getAnchorPointsReport } from '@/api/admin/basicConfiguration/anchorPoints.js'
import echarts from 'echarts'
import '@/util/date.js'
export default {
  components: {

  },
  watch: {
    lang () {
    }
  },
  mounted () {
    this.getDateValue(30)
    this.initEvent()
  },
  data () {
    return {
      loading: false,
      // 参数
      param: {
        event: null,
        key: null,
        startTime: null,
        endTime: null
      },
      // 事件
      eventList: [],
      keyList: [],
      option: {},
      timeSelect: 30,
      timeRange: [
        { value: 0, label: '今日' },
        { value: 1, label: '昨日' },
        { value: 7, label: '最近7天' },
        { value: 30, label: '最近30天' },
        { value: -1, label: '自定义' }
      ],
      timeValue: [],
      time: {
        startDate: {
          year: '',
          month: '',
          day: ''
        },
        endDate: {
          year: '',
          month: '',
          day: ''
        }
      }

    }
  },
  methods: {
    initEvent () {
      getEventKeyMap().then(res => {
        this.eventList = res.content
        this.param.event = this.eventList[0].event
        this.param.key = this.eventList[0].keys[0].key
        this.initEcharts()
      }).catch(err => console.log(err))
    },
    initEcharts () {
      this.myChart = echarts.init(document.getElementById('moneyCharts'))
      getAnchorPointsReport(this.param).then(res => {
        console.log(res)
        if (res.error !== 0) {
          this.$message.error({ message: res.message })
          return
        }
        res.content.seriesList.forEach(item => {
          item.type = 'bar'
          item.label = {
            show: true,
            position: 'insideRight'
          }
          item.data = item.dataList
          item.tyoe = 'bar'
          item.dataList = []
          item.dataMap = {}
        })
        this.handleEcharts(res.content)
      }).catch(err => console.log(err))
    },
    eventChangeHandler (event) {
      if (event === 'null') {
        this.keyList = []
        this.param.key = null
      } else {
        this.eventList.forEach(item => {
          if (item.event === event) {
            this.keyList = item.keys
            this.keyChangeHandler(item.keys[0].key)
          }
        })
      }
    },
    keyChangeHandler (key) {
      console.log(key)
      this.param.key = key
    },
    // 自定义时间
    changeTimeDate () {
      this.queryParams.diagnoseStartTime = this.timeValue[0].substring(0, 4) + '-' + this.timeValue[0].substring(4, 6) + '-' + this.timeValue[0].substring(6, 8) + ' 00:00:00'
      this.queryParams.diagnoseEndTime = this.timeValue[1].substring(0, 4) + '-' + this.timeValue[1].substring(4, 6) + '-' + this.timeValue[1].substring(6, 8) + ' 23:59:59'
      this.time.startDate.year = this.timeValue[0].substring(0, 4)
      this.time.startDate.month = this.timeValue[0].substring(4, 6)
      this.time.startDate.day = this.timeValue[0].substring(6, 8)
      this.time.endDate.year = this.timeValue[1].substring(0, 4)
      this.time.endDate.month = this.timeValue[1].substring(4, 6)
      this.time.endDate.day = this.timeValue[1].substring(6, 8)
    },
    selectTimeChangeHandler (time) {
      if (time !== -1) {
        this.getDateValue(time)
        this.initEcharts()
      } else {
        this.time.startDate.year = ''
        this.time.startDate.month = ''
        this.time.startDate.day = ''
        this.time.endDate.year = ''
        this.time.month = ''
        this.time.endDate.day = ''
        this.param.startTime = ''
        this.param.endTime = ''
      }
    },
    getDateValue (unit) {
      var startTime = new Date()
      var endTime = new Date()
      if (unit !== 0) {
        endTime.setDate(endTime.getDate() - 1)
        startTime.setDate(endTime.getDate() - unit + 1)
      }
      var startTimeStr = startTime.format('yyyy-MM-dd')
      var endTimeStr = endTime.format('yyyy-MM-dd')
      this.param.startTime = startTimeStr + ' 00:00:00'
      this.param.endTime = endTimeStr + ' 23:59:59'
      this.time.startDate.year = startTimeStr.split('-')[0]
      this.time.startDate.month = startTimeStr.split('-')[1]
      this.time.startDate.day = startTimeStr.split('-')[2]
      this.time.endDate.year = endTimeStr.split('-')[0]
      this.time.endDate.month = endTimeStr.split('-')[1]
      this.time.endDate.day = endTimeStr.split('-')[2]
    },
    handleEcharts (dataList) {
      this.option = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 10,
          data: ['直达', '营销广告', '搜索引擎', '邮件营销', '联盟广告', '视频广告', '百度', '谷歌', '必应', '其他']
        },
        series: [
          {
            name: '访问来源',
            type: 'pie',
            selectedMode: 'single',
            radius: [0, '30%'],

            label: {
              position: 'inner'
            },
            labelLine: {
              show: false
            },
            data: [
              {value: 335, name: '直达', selected: true},
              {value: 679, name: '营销广告'},
              {value: 1548, name: '搜索引擎'}
            ]
          },
          {
            name: '访问来源',
            type: 'pie',
            radius: ['40%', '55%'],
            label: {
              formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
              backgroundColor: '#eee',
              borderColor: '#aaa',
              borderWidth: 1,
              borderRadius: 4,
              // shadowBlur:3,
              // shadowOffsetX: 2,
              // shadowOffsetY: 2,
              // shadowColor: '#999',
              // padding: [0, 7],
              rich: {
                a: {
                  color: '#999',
                  lineHeight: 22,
                  align: 'center'
                },
                // abg: {
                //     backgroundColor: '#333',
                //     width: '100%',
                //     align: 'right',
                //     height: 22,
                //     borderRadius: [4, 4, 0, 0]
                // },
                hr: {
                  borderColor: '#aaa',
                  width: '100%',
                  borderWidth: 0.5,
                  height: 0
                },
                b: {
                  fontSize: 16,
                  lineHeight: 33
                },
                per: {
                  color: '#eee',
                  backgroundColor: '#334455',
                  padding: [2, 4],
                  borderRadius: 2
                }
              }
            },
            data: [
              {value: 335, name: '直达'},
              {value: 310, name: '邮件营销'},
              {value: 234, name: '联盟广告'},
              {value: 135, name: '视频广告'},
              {value: 1048, name: '百度'},
              {value: 251, name: '谷歌'},
              {value: 147, name: '必应'},
              {value: 102, name: '其他'}
            ]
          }
        ]
      }
      this.myChart.setOption(this.option)
    }
  },
  filters: {
  }
}
</script>

<style lang='scss' scoped>
    .main {
        .nav_box {
            display: flex;
            width: 100%;
            background-color: #fff;
            padding: 10px 15px;
            margin: 0px 0px 0;
            .filters {
                flex: 2;
                display: flex;
                flex-wrap: wrap;
                line-height: 32px;
                margin-left: -15px;
                .filters_item {
                    display: flex;
                    justify-content: flex-end;
                    margin-left: 15px;
                    .fil_span {
                        width: 100px;
                        font-size: 14px;
                        text-align: right;
                    }
                    .timeSelect {
                        width: 140px;
                        margin: 0 10px 0 10px;
                    }
                    .choosed_time {
                        margin-left: 20px;
                    }
                }
                .btn_wrap {
                    margin-left: 20px;
                }
            }
        }
        .table_box {
            padding: 10px;
            background: #fff;
            margin: 0 10px 10px;
        }
        .icon_img {
            position: relative;
            top: 2px;
        }
        .text-title {
            height: 50px;
            line-height: 50px;
            color: #333;
        }
        .line {
            width: 100%;
            margin-top: 0px;
            background: #fff;
            #moneyCharts {
                width: 90%;
                padding-top: 20px;
                height: 500px;
            }
        }
    }

</style>
