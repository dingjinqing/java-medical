<template>
  <div class="main">
    <div class="nav_box">
      <div class="filters">
        <div class="filters_item">
          <span class="fil_span">分析时段：</span>
          <el-select
            v-model="param.analyzeType"
            size="small"
            @change="dateChangeHandler(time,1)"
            class="timeSelect"
          >
            <el-option
              v-for="item in analyRange"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
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
          <!-- <el-button
            type='primary'
            size='small'
            @click="initData"
          >查询</el-button> -->
          <el-button
            type="primary"
            size="small"
            @click='exportData'
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
          prop='time'
          label='日期'
        ></el-table-column>
        <el-table-column>
          <template slot="header">
            <span>销售金额</span>
            <el-tooltip
              class="item"
              effect="light"
              content="订单成交总金额"
              placement="top"
            >
              <img
                class="icon_img"
                :src="$imageHost + '/image/admin/system_icon.png'"
              >
            </el-tooltip>

          </template>
          <template v-slot="scope">
            <span>{{scope.row.orderAmount}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop='shippingFee'
          label='运费'
        ></el-table-column>
        <el-table-column
          prop='useAccount'
          label='余额支付'
        ></el-table-column>
        <el-table-column
          prop='moneyPaid'
          label='实付金额'
        ></el-table-column>
        <el-table-column
          prop='returnAmount'
          label='退款金额'
        ></el-table-column>
        <el-table-column
          prop='returnNumber'
          label='退款单数'
        ></el-table-column>
        <el-table-column>
          <template slot="header">
            <span>净销售额</span>
            <el-tooltip
              class="item"
              effect="light"
              content="销售金额-退款金额"
              placement="top"
            >
              <img
                class="icon_img"
                :src="$imageHost + '/image/admin/system_icon.png'"
              >
            </el-tooltip>

          </template>
          <template v-slot="scope">
            <span>{{scope.row.netSales}}</span>
          </template></el-table-column>
        <el-table-column>
          <template slot="header">
            <span>笔单价</span>
            <el-tooltip
              class="item"
              effect="light"
              content="净销售额/销售单数"
              placement="top"
            >
              <img
                class="icon_img"
                :src="$imageHost + '/image/admin/system_icon.png'"
              >
            </el-tooltip>

          </template>
          <template v-slot="scope">
            <span>{{scope.row.orderAvg}}</span>
          </template></el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initData"
      />
    </div>
  </div>
</template>

<script>
import { getSalesReportList, getSalesReportExport } from '@/api/admin/basicConfiguration/salesreport.js'
import { dateChange } from '@/util/date.js'
import { download } from '@/util/excelUtil.js'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: {
    pagination
  },
  watch: {
    lang () {
      this.timeRange = [
        { value: -1, label: '最新1天' },
        { value: -7, label: '最新7天' },
        { value: -30, label: '最新30天' },
        { value: 0, label: '自定义' }
      ]
    }
  },
  mounted () {
    this.getDateValue(-1)
  },
  data () {
    return {
      loading: false,
      langDefaultFlag: false,
      timeValue: [],
      timeSelect: -1,
      pageParams: {
        currentPage: 1,
        pageRows: 20
      },
      tableData: [],
      timeRange: [
        { value: -1, label: '最新1天' },
        { value: -7, label: '最新7天' },
        { value: -30, label: '最新30天' },
        { value: 0, label: '自定义' }
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
        analyzeType: 1
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
    // 导出
    exportData () {
      getSalesReportExport(this.param).then(res => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : '销售报表.xlsx'
        download(res, decodeURIComponent(fileName))
      }).catch(err => console.log(err))
    },
    // 选择时间段
    dateChangeHandler (time, type = 0) {
      if (time !== 0) {
        if (type === 0) this.getDateValue(time)
        this.initData()
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
      getSalesReportList(params).then(res => {
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
      let date = dateChange(unit)
      this.startDate.year = date[1].split('-')[0]
      this.startDate.month = date[1].split('-')[1]
      this.startDate.day = date[1].split('-')[2]
      this.endDate.year = date[0].split('-')[0]
      this.endDate.month = date[0].split('-')[1]
      this.endDate.day = date[0].split('-')[2]
      this.param.startTime = date[1] + ' 00:00:00'
      this.param.endTime = date[0] + ' 23:59:59'
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
