<template>
  <div class="content">
    <div class="main">
      <div class="titleEdit"><span>咨询记录</span></div>
      <div class="navBox">
        <div class="filters">
          <div class="filters_item">
            <span>患者姓名：</span>
            <el-input
              v-model="queryParams.patientName"
              size="small"
              style="width: 150px"
              placeholder="请输入患者姓名"
            >
            </el-input>
          </div>
          <div class="filters_item">
            <span class="fil_span">时间筛选：</span>
            <el-date-picker
              v-model="timeValue"
              type="daterange"
              size="small"
              value-format="yyyy-MM-dd HH:mm:ss"
              :default-time="['00:00:00', '23:59:59']"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            >
            </el-date-picker>
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
          <el-table-column label="患者姓名">
            <template slot-scope="scope">
              <span
                class="linkStyle"
                @click="handleSeePatient(scope.row.patientId)"
              >{{scope.row.patientName}}
              </span>
            </template>
          </el-table-column>
          <el-table-column label='咨询单号'>
            <template slot-scope="scope">
              <span
                class="linkStyle"
                @click="orderHandler(scope.row.orderSn)"
              >{{scope.row.orderSn}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="inqTime"
            label="问诊日期"
          ></el-table-column>
          <el-table-column
            prop="inquiryCost"
            label="咨询费用"
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
import { doctorInquiry } from '@/api/admin/doctorManage/doctorInfo/doctor'
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
      pageParams: {
        currentPage: 1,
        pageRows: 5
      },
      tableData: [],
      queryParams: {
        startTime: '',
        endTime: '',
        patientName: ''
      },
      // 表格原始数据
      originalData: [],
      doctorList: []
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.queryParams.doctorId = this.id
      this.queryParams.currentPage = this.pageParams.currentPage
      this.queryParams.pageRows = this.pageParams.pageRows
      let params = {
        ...this.queryParams
      }
      doctorInquiry(params).then((res) => {
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
    // handleSeeOrder (code) {
    //   console.log(this.$router)
    //   let newpage = this.$router.resolve({
    //     name: 'orderInfo'
    //   })
    //   newpage.href = newpage.href + '?orderSn=' + code
    //   console.log(newpage.href)
    //   window.open(newpage.href, '_blank')
    // },
    handleData (data) {
      this.tableData = data
      this.langDefaultFlag = true
    },
    // 跳转订单详情
    orderHandler (orderSn) {
      this.$router.push({
        name: 'orderInfo',
        query: {
          orderSn: orderSn
        }
      })
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
    .linkStyle {
      // color: #5a8bff;
    }
  }
}
</style>
