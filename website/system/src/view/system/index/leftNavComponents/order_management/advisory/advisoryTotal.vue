<template>
  <div class="main">
    <div class="nav_box">
      <div class="filters">
        <div class="filters_item ">
          <span class="fil_span">医师姓名：</span>
          <el-input
            v-model="param.doctorName"
            size="small"
            style="width:190px;"
            placeholder="请输入医师姓名"
          >
          </el-input>

        </div>
        <div class="filters_item">
          <span class="fil_span">时间筛选：</span>
          <el-select
            v-model="timeSelect"
            size="small"
            clearable
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
            @click='exportData'
          >导出</el-button>
        </div>
      </div>
    </div>
    <div
      class="total_amount"
      v-if='total.amountTotal > 0'
    >
      <div>
        <span>总计:</span>咨询单数:<span>{{total.amountTotal}};</span>咨询单次价格:<span>{{total.oncePriceTotal}};</span>咨询总金额:<span>{{total.amountPriceTotal}}</span>
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

        <el-table-column label='日期'>
          <template v-slot='scope'>
            <span>{{scope.row.createTime | timeDate}}</span>
          </template>
        </el-table-column>

        <el-table-column
          prop='doctorName'
          label='医生姓名'
        ></el-table-column>
        <el-table-column
          prop='shopName'
          label='医院'
        ></el-table-column>
        <el-table-column
          prop='amount'
          label='咨询单数'
        ></el-table-column>
        <el-table-column
          prop='oncePrice'
          label='咨询单次价格'
        ></el-table-column>
        <el-table-column
          prop='amountPrice'
          label='咨询总金额'
        ></el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initData"
      />

    </div>

  </div>
</template>

<script>
import { getAdvistoryReportList, getReportExport, getDoctorTotal } from '@/api/admin/orderManage/advisory.js'
import { download } from '@/util/excelUtil.js'
import { dateChange } from '@/util/date.js'
import pagination from '@/components/system/pagination/pagination'
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
    // this.getDoctor({})
    this.initData()
  },
  data () {
    return {
      loading: false,
      timeValue: [],
      timeSelect: -1,
      pageParams: {},
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
        doctorName: ''
      },
      doctorList: [],
      total: {}
    }
  },
  methods: {
    // 导出
    exportData () {
      getReportExport(this.param).then(res => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : '咨询报表.xlsx'
        download(res, decodeURIComponent(fileName))
      }).catch(err => console.log(err))
    },
    // 选择时间段
    dateChangeHandler (time) {
      if (time !== 0) {
        this.getDateValue(time)
      }
    },
    // 自定义时间
    changeDate () {
      this.param.startTime = this.timeValue[0].substring(0, 4) + '-' + this.timeValue[0].substring(4, 6) + '-' + this.timeValue[0].substring(6, 8) + ' 00:00:00'
      this.param.endTime = this.timeValue[1].substring(0, 4) + '-' + this.timeValue[1].substring(4, 6) + '-' + this.timeValue[1].substring(6, 8) + ' 00:00:00'
      this.startDate.year = this.timeValue[0].substring(0, 4)
      this.startDate.month = this.timeValue[0].substring(4, 6)
      this.startDate.day = this.timeValue[0].substring(6, 8)
      this.endDate.year = this.timeValue[1].substring(0, 4)
      this.endDate.month = this.timeValue[1].substring(4, 6)
      this.endDate.day = this.timeValue[1].substring(6, 8)
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
      this.param.endTime = date[0] + ' 00:00:00'
      this.initData()
    },
    initData () {
      let params = Object.assign({}, this.param, this.pageParams)
      getAdvistoryReportList(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
        }
      }).catch(err => console.log(err))
      this.getTotal({ doctorName: this.param.doctorName, startTime: this.param.startTime, endTime: this.param.endTime })
    },
    getTotal (doctor) {
      getDoctorTotal(doctor).then(res => {
        if (res.error === 0) {
          console.log(res)
          this.total = res.content
        }
      })
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
    background-color: #fff;
    padding: 20px 15px 10px;
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
  .default_input {
    width: 150px;
  }
  .doctor_input {
    width: 150px;
  }
  .total_amount {
    background: #fff;
    padding: 10px 0;
    margin: 0 10px;
    div {
      text-align: center;
      font-size: 15px;
      color: #333;
      span {
        margin-right: 20px;
      }
    }
  }
}
</style>
