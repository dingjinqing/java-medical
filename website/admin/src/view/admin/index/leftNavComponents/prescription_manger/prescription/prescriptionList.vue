<template>
  <div class="content">
    <div class="main">
      <div class="nav_box">
        <div class="filters">
          <div class="filters_item ">
            <span class="fil_span">医师姓名：</span>
            <el-select
                    v-model="queryParams.doctorName"
                    placeholder="全部"
                    size="small"
                    class="default_input"
                    filterable
                    clearable
                    @change="initDataList">
              <el-option label="全部" value=""></el-option>
              <el-option
                      v-for="item in doctorList"
                      :key="item.id"
                      :label="item.name"
                      :value="item.name"></el-option>
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
            <span class="fil_span">开方类型：</span>
            <el-select
                    v-model="queryParams.auditType"
                    placeholder="全部"
                    size="small"
                    class="default_input"
                    @change="initDataList">
              <el-option label="全部" value=null></el-option>
              <el-option
                      v-for="item in auditTypes"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <span class="fil_span">是否使用：</span>
            <el-select
                    v-model="queryParams.isUsed"
                    placeholder="全部"
                    size="small"
                    class="default_input"
                    @change="initDataList">
              <el-option label="全部" value=null></el-option>
              <el-option
                      v-for="item in isUsed"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <span class="fil_span">过期类型：</span>
            <el-select
                    v-model="queryParams.expireType"
                    placeholder="全部"
                    size="small"
                    class="default_input"
                    @change="initDataList">
              <el-option label="全部" value=null></el-option>
              <el-option
                      v-for="item in expireType"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <span class="fil_span">是否有效：</span>
            <el-select
                    v-model="queryParams.isValid"
                    placeholder="全部"
                    size="small"
                    class="default_input"
                    @change="initDataList">
              <el-option label="全部" value=null></el-option>
              <el-option
                      v-for="item in isValid"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <span class="fil_span">是否结算：</span>
            <el-select
                    v-model="queryParams.settlementFlag"
                    placeholder="全部"
                    size="small"
                    class="default_input"
                    @change="initDataList">
              <el-option label="全部" value=null></el-option>
              <el-option
                      v-for="item in settlementFlag"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"></el-option>
            </el-select>
          </div>
        </div>
      </div>
      <div class="nav_box" style='margin-top:0;padding-top:0'>
        <div class="filters">
          <div class="filters_item">
            <span class="fil_span">开方时间：</span>
            <el-select
                    v-model="createTimeSelect"
                    size="small"
                    @change="createDateChangeHandler"
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
                    v-if="createTimeSelect===-1"
                    v-model="createTimeValue"
                    type="daterange"
                    size="small"
                    value-format="yyyyMMdd"
                    @change="createChangeDate"
                    range-separator="-"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期">
            </el-date-picker>
            <span class="choosed_time">{{this.createTime.startDate.year}}年{{this.createTime.startDate.month}}月{{this.createTime.startDate.day}}日 - {{this.createTime.endDate.year}}年{{this.createTime.month}}月{{this.createTime.endDate.day}}日</span>
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
                  prop='name'
                  label='患者姓名'
          >
            <template slot-scope="scope">
              <div class="operation">
                <a @click="handleSeePatient(scope.row.id)">{{scope.row.name}}({{scope.row.patientId}})</a>
              </div>
            </template>
          </el-table-column>
          <el-table-column
                  prop='prescriptionCode'
                  label='处方号'
          >
            <template slot-scope="scope">
              <div class="operation">
                <a @click="handleSeeOrder(scope.row.prescriptionCode)">{{scope.row.prescriptionCode}}</a>
              </div>
            </template>
          </el-table-column>
          <el-table-column
                  prop='doctorName'
                  label='医师名称'
          >
            <template slot-scope="scope">
              <div class="operation">
                <a @click="handleSeeDoctor(scope.row.doctorCode)">{{scope.row.doctorName}}({{scope.row.doctorCode}})</a>
              </div>
            </template>
          </el-table-column>
          <el-table-column
                  prop='departmentName'
                  label='科室名称'
          ></el-table-column>
          <el-table-column
                  prop='diagnosisName'
                  label='疾病名称'
          ></el-table-column>
          <el-table-column
                  prop='createTime'
                  label='开方时间'
          >
            <template slot-scope="scope">
              {{scope.row.createTime.substring(0, 10)}}
              <br />
              {{scope.row.createTime.substring(11, 20)}}
            </template>
          </el-table-column>
          <el-table-column
                  prop='auditType'
                  label='处方类型'>
            <template slot-scope="scope">
              {{getLabelValue(auditTypes,scope.row.auditType)}}
            </template>
          </el-table-column>
          <el-table-column
                  prop='totalPrice'
                  label='处方金额'
          >
          </el-table-column>
          <el-table-column
                  prop='isUsed'
                  label='是否使用'>
            <template slot-scope="scope">
              {{getLabelValue(isUsed,scope.row.isUsed)}}
            </template>
          </el-table-column>
          <el-table-column
                  prop='expireType'
                  label='过期类型'>
            <template slot-scope="scope">
              {{getLabelValue(expireType,scope.row.expireType)}}
              <br>{{expireTypeTime(scope.row)}}
            </template>
          </el-table-column>
          <el-table-column
                  prop='isValid'
                  label='是否有效'>
            <template slot-scope="scope">
              {{getLabelValue(isValid,scope.row.isValid)}}
            </template>
          </el-table-column>
          <el-table-column
                  prop='settlementFlag'
                  label='是否结算'>
            <template slot-scope="scope">
              {{getLabelValue(settlementFlag,scope.row.settlementFlag)}}
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
      auditTypes: [
        {value: 0, label: '不审核'},
        {value: 1, label: '续方'},
        {value: 2, label: '开方'},
        {value: 3, label: '会话开方'}
      ],
      isValid: [
        {value: 0, label: '无效'},
        {value: 1, label: '有效'}
      ],
      settlementFlag: [
        {value: 0, label: '未结算'},
        {value: 1, label: '已结算'}
      ],
      isUsed: [
        {value: 0, label: '未使用'},
        {value: 1, label: '已使用'}
      ],
      expireType: [
        {value: 0, label: '未知'},
        {value: 1, label: '永久有效'},
        {value: 2, label: '时间段内有效'}
      ],
      createTimeValue: [],
      createTimeSelect: 30,
      timeRange: [
        { value: 0, label: '今日' },
        { value: 1, label: '昨日' },
        { value: 7, label: '最近7天' },
        { value: 30, label: '最近30天' },
        { value: -1, label: '自定义' }
      ],
      diagnoseTime: {
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
      },
      createTime: {
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
        doctorName: '',
        auditType: null,
        settlementFlag: null,
        isUsed: null,
        expireType: null,
        isValid: null
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
    createDateChangeHandler (time) {
      if (time !== -1) {
        this.createGetDateValue(time)
        this.initDataList()
      } else {
        this.createTime.startDate.year = ''
        this.createTime.startDate.month = ''
        this.createTime.startDate.day = ''
        this.createTime.endDate.year = ''
        this.createTime.month = ''
        this.createTime.endDate.day = ''
        this.queryParams.createTimeStartTime = ''
        this.queryParams.createTimeEndTime = ''
      }
    },
    // 自定义时间
    createChangeDate () {
      this.queryParams.diagnoseStartTime = this.createTimeValue[0].substring(0, 4) + '-' + this.createTimeValue[0].substring(4, 6) + '-' + this.createTimeValue[0].substring(6, 8) + ' 00:00:00'
      this.queryParams.diagnoseEndTime = this.createTimeValue[1].substring(0, 4) + '-' + this.createTimeValue[1].substring(4, 6) + '-' + this.createTimeValue[1].substring(6, 8) + ' 23:59:59'
      this.createTime.startDate.year = this.createTimeValue[0].substring(0, 4)
      this.createTime.startDate.month = this.createTimeValue[0].substring(4, 6)
      this.createTime.startDate.day = this.createTimeValue[0].substring(6, 8)
      this.createTime.endDate.year = this.createTimeValue[1].substring(0, 4)
      this.createTime.endDate.month = this.createTimeValue[1].substring(4, 6)
      this.createTime.endDate.day = this.createTimeValue[1].substring(6, 8)
    },
    createGetDateValue (unit) {
      var startTime = new Date()
      var endTime = new Date()
      if (unit !== 0) {
        endTime.setDate(endTime.getDate() - 1)
        startTime.setDate(endTime.getDate() - unit + 1)
      }
      var startTimeStr = startTime.format('yyyy-MM-dd')
      var endTimeStr = endTime.format('yyyy-MM-dd')
      this.queryParams.createStartTime = startTimeStr + ' 00:00:00'
      this.queryParams.createEndTime = endTimeStr + ' 23:59:59'
      this.createTime.startDate.year = startTimeStr.split('-')[0]
      this.createTime.startDate.month = startTimeStr.split('-')[1]
      this.createTime.startDate.day = startTimeStr.split('-')[2]
      this.createTime.endDate.year = endTimeStr.split('-')[0]
      this.createTime.endDate.month = endTimeStr.split('-')[1]
      this.createTime.endDate.day = endTimeStr.split('-')[2]
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
    // 跳转患者详情
    handleSeePatient (code) {
      console.log(this.$router)
      let newpage = this.$router.resolve({
        name: 'patient_message'
      })
      newpage.href = newpage.href + '?id=' + code
      console.log(newpage.href)
      window.open(newpage.href, '_blank')
    },
    // 跳转医师列表
    handleSeeDoctor (code) {
      console.log(this.$router)
      let newpage = this.$router.resolve({
        name: 'addDoctor'
      })
      newpage.href = newpage.href + '?id=' + code
      console.log(newpage.href)
      window.open(newpage.href, '_blank')
    },
    // 处方列表
    handleSeeOrder (code) {
      console.log(this.$router)
      let newpage = this.$router.resolve({
        name: 'orderInfo'
      })
      newpage.href = newpage.href + '?orderSn=' + code
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
          this.doctorList = res.content
        }
      })
    },
    getLabelValue (map, value) {
      let label = '无'
      map.forEach(item => {
        if (item.value === value) {
          label = item.label
        }
      })
      return label
    },
    // 过期时间和类型
    expireTypeTime (row) {
      if (row.expireType === 2) {
        // 有过期时间
        return row.prescriptionExpireTime
      }
      return ''
    }
  },
  mounted () {
    this.id = this.$route.query.id ? this.$route.query.id : 0
    this.createGetDateValue(30)
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
            margin: 0 0px 0 0px;
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
