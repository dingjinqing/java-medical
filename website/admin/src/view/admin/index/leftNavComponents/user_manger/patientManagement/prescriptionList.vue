<template>
  <div class="content">
    <div class="main">
      <div class="navBox">
        <div class="filters">
          <div class="filters_item">
            <span>手机号：</span>
            <el-input
              v-model="queryParams.mobile"
              size="small"
              style="width:190px;"
              placeholder="请输入患者手机号"
            >
            </el-input>
          </div>
          <div class="filters_item ">
            <span class="fil_span">医师姓名：</span>
            <el-select
              v-model="queryParams.doctorName"
              placeholder="请输入医师姓名"
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
                :key="item.name"
                :label="item.name"
                :value="item.name"
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
              @click="initDataList"
            >搜索</el-button>
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
            prop='prescriptionCode'
            label='处方号'
          ></el-table-column>
          <el-table-column
            prop='departmentName'
            label='科室名称'
          ></el-table-column>
          <el-table-column
            prop='doctorName'
            label='医师名称'
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
import { getPrescriptionList } from '@/api/admin/memberManage/patientManage.js'
import { getDate } from '@/api/admin/firstWebManage/goodsStatistics/goodsStatistics.js'
import { getDoctorList } from '@/api/admin/doctorManage/advistoryTotal/advistory.js'
export default {
  components: { pagination },
  watch: {
    lang () {
      this.timeRange = this.$t('tradesStatistics.timeRange')
    }
  },
  data () {
    return {
      loading: false,
      langDefaultFlag: false,
      timeValue: [],
      timeSelect: 1,
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
      pageParams: {
        currentPage: 1,
        pageRows: 20
      },
      tableData: [],
      storeGroup: [],
      queryParams: {
        mobile: null,
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
      getPrescriptionList(Object.assign(this.queryParams, this.pageParams)).then((res) => {
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
      if (time !== 0) {
        this.getDateValue(time)
        this.initDataList()
      }
    },
    // 自定义时间
    changeDate () {
      this.queryParams.diagnoseStartTime = this.timeValue[0].substring(0, 4) + '-' + this.timeValue[0].substring(4, 6) + '-' + this.timeValue[0].substring(6, 8) + ' 00:00:00'
      this.queryParams.diagnoseEndTime = this.timeValue[1].substring(0, 4) + '-' + this.timeValue[1].substring(4, 6) + '-' + this.timeValue[1].substring(6, 8) + ' 00:00:00'
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
          this.queryParams.diagnoseStartTime = res.content.startTime + ' 00:00:00'
          this.queryParams.diagnoseEndTime = res.content.endTime + ' 00:00:00'
          this.initData()
        }
      }).catch(err => console.log(err))
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
          console.log(res)
          this.doctorList = res.content
        }
      })
    }
  },
  // watch: {
  //   lang () {
  //     if (this.langDefaultFlag) {
  //       // 重新渲染表格数据
  //       let originalData = JSON.parse(JSON.stringify(this.originalData))
  //       this.handleData(originalData)
  //     }
  //   }
  // },
  mounted () {
    this.id = this.$route.query.id ? this.$route.query.id : 0
    this.getDateValue(1)
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
  padding: 10px;
    .navBox {
    display: flex;
    background-color: #fff;
    padding: 0px 15px 10px;
    margin: 0px 10px 0;
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
    margin-top: 10px;
    a {
      color: #5a8bff;
      cursor: pointer;
    }
  }
}
</style>
