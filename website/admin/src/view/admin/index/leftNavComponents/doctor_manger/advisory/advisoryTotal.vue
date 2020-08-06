<template>
  <div class="main">
    <div class="nav_box">
      <div class="filters">
        <div class="filters_item ">
          <span class="fil_span">医师姓名：</span>
          <el-select
            v-model="param.doctorId"
            placeholder="请输入医生姓名"
            size="small"
            class="default_input"
            filterable
          >
            <el-option
              label="全部"
              value=" "
            ></el-option>
            <el-option
              v-for="item in doctorList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>

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
          prop='departmentName'
          label='科室'
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
      <div
        class="total_amount"
        v-if='total.amountTotal > 0'
      >
        <div>
          <span>总计:</span>咨询单数<span>{{total.amountTotal}}</span>咨询单次价格<span>{{total.oncePriceTotal}}</span>咨询总金额<span>{{total.amountPriceTotal}}</span>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import { getAdvistoryReportList, getReportExport, getDoctorList, getDoctorTotal } from '@/api/admin/doctorManage/advistoryTotal/advistory.js'
import { getDate } from '@/api/admin/firstWebManage/goodsStatistics/goodsStatistics.js'
import { download } from '@/util/excelUtil.js'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: {
    pagination
  },
  watch: {
    lang () {
      this.timeRange = this.$t('tradesStatistics.timeRange')
    }
  },
  mounted () {
    this.getDateValue(1)
    this.getDoctor({})
    this.initData()
  },
  data () {
    return {
      loading: false,
      timeValue: [],
      timeSelect: 1,
      pageParams: {},
      tableData: [],
      timeRange: this.$t('tradesStatistics.timeRange'),
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
        doctorId: ''
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
        this.initData()
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
      this.initData()
    },
    getDateValue (unit) {
      getDate(unit).then(res => {
        if (res.error === 0) {
          this.startDate.year = res.content.startTime.split('-')[0]
          this.startDate.month = res.content.startTime.split('-')[1]
          this.startDate.day = res.content.startTime.split('-')[2]
          this.endDate.year = res.content.endTime.split('-')[0]
          this.endDate.month = res.content.endTime.split('-')[1]
          this.endDate.day = res.content.endTime.split('-')[2]
          this.param.startTime = res.content.startTime + ' 00:00:00'
          this.param.endTime = res.content.endTime + ' 00:00:00'
          this.initData()
        }
      }).catch(err => console.log(err))
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
      this.getTotal({ doctorId: this.param.doctorId })
    },
    getDoctor (doctor) {
      getDoctorList(doctor).then(res => {
        if (res.error === 0) {
          console.log(res)
          this.doctorList = res.content
        }
      })
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
    width: 100%;
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
  .doctor_input {
    width: 150px;
  }
  .total_amount {
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
