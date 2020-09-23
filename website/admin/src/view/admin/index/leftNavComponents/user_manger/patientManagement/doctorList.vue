<template>
  <div class="content">
    <div class="main">
      <div class="titleEdit"><span>医师列表</span></div>
      <div class="navBox">
        <div class="filters">
          <div class="filters_item">
            <span>医师姓名：</span>
            <el-input
              v-model="queryParams.doctorName"
              size="small"
              style="width: 150px"
              placeholder="请输入医师姓名"
            >
            </el-input>
          </div>
          <div class="filters_item">
            <span class="fil_span">科室：</span>
            <el-input
              v-model="queryParams.departmentName"
              size="small"
              style="width: 150px"
              placeholder="请输入科室名称"
            >
            </el-input>
          </div>
          <div class="btn_wrap">
            <el-button
              type="primary"
              size="small"
              @click="initDataList"
            >查询</el-button>
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
            prop="doctorName"
            label="医师姓名"
          ></el-table-column>
          <el-table-column
            prop='departmentName'
            label='科室'
          ></el-table-column>
          <el-table-column
            prop="prescriptionNumber"
            label="处方数"
          ></el-table-column>
          <el-table-column
            prop="prescriptionConsumptionAmount"
            label="处方金额"
          ></el-table-column>
          <el-table-column
            prop="inquiryNumber"
            label="咨询单数"
          ></el-table-column>
          <el-table-column
            prop="inquiryConsumptionAmount"
            label="咨询总金额"
          ></el-table-column>
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
import { getDoctor } from '@/api/admin/memberManage/patientManage.js'
import { getDoctorList } from '@/api/admin/doctorManage/advistoryTotal/advistory.js'
export default {
  components: { pagination },
  watch: {
    timeValue (val) {
      if (val !== null) {
        this.queryParams.startTime = val[0]
        this.queryParams.endTime = val[1]
      } else {
        this.queryParams.startTime = ''
        this.queryParams.endTime = ''
      }
    }
  },
  data () {
    return {
      loading: false,
      langDefaultFlag: false,
      timeValue: [],
      timeSelect: -1,
      pageParams: {
        currentPage: 1,
        pageRows: 5
      },
      tableData: [],
      queryParams: {
        doctorName: '',
        departmentName: ''
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
      getDoctor(params).then((res) => {
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
    .titleEdit {
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
    .el-button + .el-button {
      margin-left: 10px !important;
    }
  }
}
</style>
