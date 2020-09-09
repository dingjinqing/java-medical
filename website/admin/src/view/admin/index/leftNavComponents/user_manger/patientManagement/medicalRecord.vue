<template>
  <div class="content">
    <div class="main">
      <div class="titleEdit"><span>购药记录</span></div>
      <div class="navBox">
        <div class="filters">
          <div class="filters_item">
            <span>处方号：</span>
            <el-input
              v-model="queryParams.prescriptionNos"
              size="small"
              style="width: 190px"
              placeholder="请输入患者处方号"
            >
            </el-input>
          </div>
          <div class="filters_item">
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
              v-if="timeSelect === 4"
              v-model="timeValue"
              type="datetimerange"
              size="small"
              value-format="yyyy-MM-dd HH:mm:ss"
              :default-time="['00:00:00', '23:59:59']"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            >
            </el-date-picker>
            <span
              class="choosed_time"
              v-if="timeSelect !== -1 && timeSelect !== 4"
            >{{ this.startDate.year }}年{{ this.startDate.month }}月{{
                this.startDate.day
              }}日 - {{ this.endDate.year }}年{{ this.endDate.month }}月{{
                this.endDate.day
              }}日</span>
          </div>
          <div class="btn_wrap">
            <el-button
              type="primary"
              size="small"
              @click="initDataList"
            >搜索</el-button>
          </div>
        </div>
      </div>
      <div class="table_box">
        <el-table
          v-loading="loading"
          :data="tableData"
          style="width: 100%"
          border
          :header-cell-style="{
            'background-color': '#f5f5f5',
            'text-align': 'center',
            border: 'none',
            color: '#000',
          }"
          :cell-style="{
            'text-align': 'center',
          }"
        >
          <el-table-column
            prop="prescriptionCode"
            label="处方号"
          ></el-table-column>
          <el-table-column
            prop='doctorCode'
            label='医师Code'
          ></el-table-column>
          <el-table-column
            prop="departmentName"
            label="科室名称"
          ></el-table-column>
          <el-table-column
            prop="doctorName"
            label="医师名称"
          ></el-table-column>
          <el-table-column
            prop="diagnosisName"
            label="疾病名称"
          ></el-table-column>
          <el-table-column
            prop="diagnoseTime"
            label="就诊时间"
          ></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <div class="operation">
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="查看详情"
                  placement="top"
                >
                  <a @click="handleSeeMessage(scope.row.prescriptionCode)">查看详情</a>
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
import { getDoctorList } from '@/api/admin/doctorManage/advistoryTotal/advistory.js'
export default {
  components: { pagination },
  watch: {
    lang () {
      // this.timeRange = this.$t('tradesStatistics.timeRange')
    },
    timeSelect (val) {
      let end = new Date()
      let start = new Date()
      switch (val) {
        case -1:
          start = ''
          end = ''
          break
        case 1:
          start.setTime(start.getTime() - 3600 * 1000 * 24 * 1)
          break
        case 2:
          start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
          break
        case 3:
          start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
          break
        case 4:
          start = this.timeValue[0] || ''
          end = this.timeValue[1] || ''
          break
      }
      this.queryParams.diagnoseStartTime = `${start.getFullYear()}-${start.getMonth() + 1}-${start.getDate()} 00:00:00`
      this.queryParams.diagnoseEndTime = `${end.getFullYear()}-${end.getMonth() + 1}-${end.getDate()} 23:59:59`
      this.startDate.year = start.getFullYear()
      this.startDate.month = start.getMonth() + 1
      this.startDate.day = start.getDate()
      this.endDate.year = end.getFullYear()
      this.endDate.month = end.getMonth() + 1
      this.endDate.day = end.getDate()
    },
    timeValue (val) {
      if (this.timeSelect === 4) {
        this.queryParams.diagnoseStartTime = val[0]
        this.queryParams.diagnoseEndTime = val[1]
      }
    }
  },
  data () {
    return {
      loading: false,
      langDefaultFlag: false,
      timeValue: [],
      timeSelect: -1,
      timeRange: [
        { value: -1, label: '全部' },
        { value: 1, label: '最新1天' },
        { value: 2, label: '最新7天' },
        { value: 3, label: '最新30天' },
        { value: 4, label: '自定义' }
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
        pageRows: 5
      },
      tableData: [],
      storeGroup: [],
      queryParams: {
        prescriptionNos: null,
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
      let params = {
        ...this.queryParams
      }
      params.prescriptionNos = this.queryParams.prescriptionNos ? [this.queryParams.prescriptionNos] : null
      getPrescriptionList(params).then((res) => {
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
    // this.getDateValue(1)
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
.content {
  margin-top: 10px;
  background: #fff;
  .main {
    .titleEdit{
      padding: 0 20px;
      height: 50px;
      display: -webkit-box;
      display: -ms-flexbox;
      display: flex;
      -webkit-box-align: center;
      -ms-flex-align: center;
      align-items: center;
      border-bottom: 1px solid #e6e9f0;
      margin-bottom: 10px;
    }
    .navBox {
      display: flex;
      background-color: #fff;
      padding: 0px 20px;
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
      a {
        color: #5a8bff;
        cursor: pointer;
      }
    }
  }
}
</style>
