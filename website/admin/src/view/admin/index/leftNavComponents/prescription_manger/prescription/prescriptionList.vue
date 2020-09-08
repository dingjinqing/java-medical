<template>
  <div class="content">
    <div class="main">
      <div class="nav_box">
        <div class="filters">
          <div class="filters_item ">
            <span class="fil_span">医师姓名：</span>
            <el-select
              v-model="queryParams.doctorName"
              placeholder="请输入医师姓名"
              size="small"
              class="default_input"
              filterable
              clearable
              @change="initDataList"
            >
              <el-option
                label="全部"
                value=""
              ></el-option>
              <el-option
                v-for="item in doctorList"
                :key="item.id"
                :label="item.name"
                :value="item.name"
              ></el-option>
            </el-select>

          </div>
          <div class="filters_item">
            <span>患者姓名：</span>
            <el-input
              v-model="queryParams.patientName"
              size="small"
              style="width:190px;"
              placeholder="请输入姓名"
            >
            </el-input>
          </div>
        </div>
      </div>
      <div class="nav_box" style='margin-top:0;padding-top:0'>
        <div class="filters">
          <div class="filters_item">
            <span class="fil_span">就诊时间：</span>
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
              v-if="timeSelect===-1"
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
              @click="initDataList"
            >查询</el-button>
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
            prop='id'
            label='患者ID'
          ></el-table-column>
          <el-table-column
            prop='name'
            label='患者姓名'
          ></el-table-column>
          <el-table-column
            prop='prescriptionCode'
            label='处方号'
          ></el-table-column>
          <el-table-column
            prop='doctorCode'
            label='医师码'
          ></el-table-column>
          <el-table-column
            prop='doctorName'
            label='医师名称'
          ></el-table-column>
          <el-table-column
            prop='departmentName'
            label='科室名称'
          ></el-table-column>
          <el-table-column
            prop='diagnosisName'
            label='疾病名称'
          ></el-table-column>
          <el-table-column
            prop='diagnoseTime'
            label='就诊时间'
          ></el-table-column>
          <el-table-column label='操作'>
            <template slot-scope="scope">
              <div class="operation">
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="查看详情"
                  placement="top"
                >
                  <a @click='handleSeeMessage(scope.row.prescriptionCode)'>查看详情</a>
                </el-tooltip>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        />
      </div>
    </div>
  </div>
</template>

<script>
import pagination from '@/components/admin/pagination/pagination'
import { getAllPrescriptionList } from '@/api/admin/memberManage/patientManage.js'
import { getDoctorList } from '@/api/admin/doctorManage/advistoryTotal/advistory.js'
import '@/util/date.js'
export default {
  components: { pagination },
  watch: {
    lang () {

    }
  },
  data () {
    return {
      loading: false,
      langDefaultFlag: false,
      timeValue: [],
      timeSelect: 0,
      timeRange: [
        { value: 0, label: '今日' },
        { value: 1, label: '昨日' },
        { value: 7, label: '最近7天' },
        { value: 30, label: '最近30天' },
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
      pageParams: {
        currentPage: 1,
        pageRows: 20
      },
      tableData: [],
      storeGroup: [],
      queryParams: {
        patientName: '',
        diagnoseStartTime: '',
        diagnoseEndTime: '',
        doctorName: ''
      },
      // 表格原始数据
      originalData: [],
      doctorList: []
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.queryParams.patientId = this.id
      this.queryParams.currentPage = this.pageParams.currentPage
      this.queryParams.pageRows = this.pageParams.pageRows
      getAllPrescriptionList(Object.assign(this.queryParams, this.pageParams)).then((res) => {
        if (res.error !== 0) {
          this.$message.error({ message: res.message })
          return
        }
        console.log(res)
        this.originalData = res.content.dataList
        this.pageParams = res.content.page
        let originalData = JSON.parse(JSON.stringify(this.originalData))
        this.handleData(originalData)
        this.loading = false
      }).catch(error => {
        console.log(error)
      })
    },
    // 选择时间段
    dateChangeHandler (time) {
      if (time !== -1) {
        this.getDateValue(time)
        this.initDataList()
      } else {
        this.startDate.year = ''
        this.startDate.month = ''
        this.startDate.day = ''
        this.endDate.year = ''
        this.endDate.month = ''
        this.endDate.day = ''
        this.queryParams.diagnoseStartTime = ''
        this.queryParams.diagnoseEndTime = ''
      }
    },
    // 自定义时间
    changeDate () {
      this.queryParams.diagnoseStartTime = this.timeValue[0].substring(0, 4) + '-' + this.timeValue[0].substring(4, 6) + '-' + this.timeValue[0].substring(6, 8) + ' 00:00:00'
      this.queryParams.diagnoseEndTime = this.timeValue[1].substring(0, 4) + '-' + this.timeValue[1].substring(4, 6) + '-' + this.timeValue[1].substring(6, 8) + ' 23:59:59'
      this.startDate.year = this.timeValue[0].substring(0, 4)
      this.startDate.month = this.timeValue[0].substring(4, 6)
      this.startDate.day = this.timeValue[0].substring(6, 8)
      this.endDate.year = this.timeValue[1].substring(0, 4)
      this.endDate.month = this.timeValue[1].substring(4, 6)
      this.endDate.day = this.timeValue[1].substring(6, 8)
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
      this.queryParams.diagnoseStartTime = startTimeStr + ' 00:00:00'
      this.queryParams.diagnoseEndTime = endTimeStr + ' 23:59:59'
      this.startDate.year = startTimeStr.split('-')[0]
      this.startDate.month = startTimeStr.split('-')[1]
      this.startDate.day = startTimeStr.split('-')[2]
      this.endDate.year = endTimeStr.split('-')[0]
      this.endDate.month = endTimeStr.split('-')[1]
      this.endDate.day = endTimeStr.split('-')[2]
    },
    handleSeeMessage (code) {
      console.log(this.$router)
      let newpage = this.$router.resolve({
        name: 'prescription_message'
      })
      newpage.href = newpage.href + '?prescriptionCode=' + code
      console.log(newpage.href)
      window.open(newpage.href, '_blank')
    },
    handleData (data) {
      this.tableData = data
      this.langDefaultFlag = true
    },
    getDoctor (doctor) {
      getDoctorList(doctor).then(res => {
        if (res.error === 0) {
          // console.log(res)
          this.doctorList = res.content
        }
      })
    }
  },
  mounted () {
    this.id = this.$route.query.id ? this.$route.query.id : 0
    this.getDateValue(0)
    this.getDoctor({})
    this.initDataList()
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

<style scoped lang='scss'>
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
  .operation a {
    color: #5a8bff;
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
