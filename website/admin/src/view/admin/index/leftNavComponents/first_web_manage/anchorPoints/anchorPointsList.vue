<!--
埋点列表 -kdc
 -->
<template>
    <div class="main">
        <div class="nav_box">
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
                    <span class="fil_span">时间筛选：</span>
                    <el-select
                            v-model="timeSelect"
                            size="small"
                            @change="dateChangeHandler"
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
                            v-if="timeSelect===0"
                            v-model="timeValue"
                            type="daterange"
                            size="small"
                            value-format="yyyyMMdd"
                            @change="changeDate"
                            range-separator="-"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期"
                    >
                    </el-date-picker>
                    <span class="choosed_time">{{this.startDate.year}}年{{this.startDate.month}}月{{this.startDate.day}}日 - {{this.endDate.year}}年{{this.endDate.month}}月{{this.endDate.day}}日</span>
                </div>
                <div class="btn_wrap">
                    <el-button
                      type='primary'
                      size='small'
                      @click="initData"
                    >查询</el-button>
                    <el-button
                            type="primary"
                            size="small"
                            @click='initData'
                    >导出</el-button>
                </div>
            </div>
        </div>
        <div class="table_box">
            <el-table
                    v-loading='loading'
                    :data='tableData'
                    style="width:100%"
                    border
                    :header-cell-style="{
            'background-color':'#f5f5f5',
            'text-align':'center',
            'border':'none',
            'color': '#000'
          }"
                    :cell-style="{
            'text-align':'center'
          }"
            >
                <el-table-column
                        prop='createTime'
                        label='日期'
                ></el-table-column>
                <el-table-column
                        prop='device'
                        label='设备'
                ></el-table-column>
                <el-table-column
                        prop='eventName'
                        label='事件'
                ></el-table-column>
                <el-table-column
                        prop='key'
                        label='关键字'
                ></el-table-column>
                <el-table-column
                        prop='value'
                        label='值'
                ></el-table-column>
                <el-table-column
                        prop='page'
                        label='页面'>
                </el-table-column>
                <el-table-column
                        prop='platform'
                        label='平台'>
                </el-table-column>
                <el-table-column
                        prop='userId'
                        label='用户id'>
                </el-table-column>
            </el-table>
            <pagination
                    :page-params.sync="pageParams"
                    @pagination="initData"
            />
        </div>
    </div>
</template>

<script>
import { getEventKeyMap, getAnchorPointsList } from '@/api/admin/basicConfiguration/anchorPoints.js'
import pagination from '@/components/admin/pagination/pagination'
import '@/util/date.js'
export default {
  components: {
    pagination
  },
  watch: {
    lang () {
    }
  },
  mounted () {
    this.getDateValue(-1)
    this.initEvent()
  },
  data () {
    return {
      loading: false,
      timeValue: [],
      timeSelect: 7,
      pageParams: {
        currentPage: 1,
        pageRows: 20
      },
      // 事件
      eventList: [],
      keyList: [],
      tableData: [],
      timeRange: [
        { value: 0, label: '今天' },
        { value: 1, label: '昨天' },
        { value: 7, label: '最新7天' },
        { value: 30, label: '最新30天' },
        { value: -1, label: '自定义' }
      ],
      startDate: {
        year: '',
        month: '',
        day: ''
      },
      endDate: {
        year: '',
        month: '',
        day: ''
      },
      param: {
        startTime: '',
        endTime: '',
        event: null,
        key: null
      },
      originalData: [],
      analyRange: [
        { value: 1, label: '每天' },
        { value: 2, label: '每周' },
        { value: 3, label: '每月' },
        { value: 4, label: '每季度' },
        { value: 5, label: '每年' }
      ]

    }
  },
  methods: {
    initEvent () {
      getEventKeyMap().then(res => {
        this.eventList = res.content
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
          }
        })
      }
    },
    keyChangeHandler (key) {
      console.log(key)
      this.param.key = key
    },
    // 选择时间段
    dateChangeHandler (time) {
      if (time !== -1) {
        this.createGetDateValue(time)
        this.initDataList()
      } else {
        this.startDate.year = ''
        this.startDate.month = ''
        this.startDate.day = ''
        this.endDate.year = ''
        this.month = ''
        this.endDate.day = ''
        this.createTimeStartTime = ''
        this.createTimeEndTime = ''
      }
    },
    // 自定义时间
    changeDate () {
      this.param.startTime = this.timeValue[0].substring(0, 4) + '-' + this.timeValue[0].substring(4, 6) + '-' + this.timeValue[0].substring(6, 8) + ' 00:00:00'
      this.param.endTime = this.timeValue[1].substring(0, 4) + '-' + this.timeValue[1].substring(4, 6) + '-' + this.timeValue[1].substring(6, 8) + ' 23:59:59'
      this.startDate.year = this.timeValue[0].substring(0, 4)
      this.startDate.month = this.timeValue[0].substring(4, 6)
      this.startDate.day = this.timeValue[0].substring(6, 8)
      this.endDate.year = this.timeValue[1].substring(0, 4)
      this.endDate.month = this.timeValue[1].substring(4, 6)
      this.endDate.day = this.timeValue[1].substring(6, 8)
      this.initData()
    },
    initData () {
      let params = Object.assign({}, this.param, this.pageParams)
      getAnchorPointsList(params).then(res => {
        console.log(res)
        if (res.error !== 0) {
          this.$message.error({ message: res.message })
          return
        }
        this.originalData = res.content.dataList
        this.pageParams = res.content.page
        let originalData = JSON.parse(JSON.stringify(this.originalData))
        this.handleData(originalData)
      }).catch(err => console.log(err))
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
      this.startDate.year = startTimeStr.split('-')[0]
      this.startDate.month = startTimeStr.split('-')[1]
      this.startDate.day = startTimeStr.split('-')[2]
      this.endDate.year = endTimeStr.split('-')[0]
      this.endDate.month = endTimeStr.split('-')[1]
      this.endDate.day = endTimeStr.split('-')[2]
      this.initData()
    },
    handleData (data) {
      this.tableData = data
    }
  },
  filters: {
    timeDate: function (val) {
      if (!val) return
      val = val.split(' ')
      return val[0]
    }
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
            margin: 10px 10px 0;
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
    }
</style>
