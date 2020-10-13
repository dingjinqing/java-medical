<template>
  <div class="content">
    <div class="main">
      <div class="titleEdit"><span>业绩统计</span></div>
      <div class="navBox">
        <div class="filters">
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
          <el-table-column
            prop="lastTime"
            label="最近登录时间"
          ></el-table-column>
          <el-table-column>
            <template slot="header">
              <span>出勤率</span>
              <el-tooltip
                class="item"
                effect="light"
                content="出勤率 = 查询时间段内登录天数/查询天数"
                placement="top"
              >
                <img
                  class="icon_img"
                  :src="$imageHost + '/image/admin/system_icon.png'"
                >
              </el-tooltip>

            </template>
            <template v-slot="scope">
              <span>{{scope.row.loginRate}}</span>
            </template>

          </el-table-column>
          <el-table-column
            prop="consultationNumber"
            label="累计接诊人数"
          ></el-table-column>
          <el-table-column
            prop="prescriptionNum"
            label="累计处方数"
          ></el-table-column>
          <el-table-column
            prop="prescriptionMoney"
            label="累计处方金额"
          ></el-table-column>
          <el-table-column
            prop="inquiryNumber"
            label="累计咨询单数"
          ></el-table-column>
          <el-table-column
            prop="inquiryMoney"
            label="累计咨询金额"
          ></el-table-column>
          <el-table-column
            prop="createTime"
            label="累计消费金额"
          ></el-table-column>
        </el-table>
        <!-- <pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        /> -->
      </div>
    </div>
  </div>
</template>

<script>
import pagination from '@/components/admin/pagination/pagination'
import { doctorPerfomance } from '@/api/admin/doctorManage/doctorInfo/doctor'
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
      // pageParams: {
      //   currentPage: 1,
      //   pageRows: 5
      // },
      tableData: [],
      queryParams: {
        startTime: '',
        endTime: ''
      },
      // 表格原始数据
      originalData: [],
      doctorList: []
    }
  },
  methods: {
    initDataList () {
      // this.loading = true
      this.queryParams.doctorId = this.id
      // this.queryParams.currentPage = this.pageParams.currentPage
      // this.queryParams.pageRows = this.pageParams.pageRows
      if (this.queryParams.startTime === '' || this.queryParams.endTime === '') {
        this.$message.success({
          message: '请选择时间范围',
          duration: '2000'
        })
      }
      let params = {
        ...this.queryParams
      }
      doctorPerfomance(params).then((res) => {
        if (res.error !== 0) {
          this.$message.error({ message: res.message })
          return
        }
        console.log(res)
        this.originalData = res.content
        let originalData = JSON.parse(JSON.stringify(this.originalData))
        this.handleData(originalData)
        // this.loading = false
      }).catch(error => {
        console.log(error)
      })
    },
    handleData (data) {
      this.tableData = [data]
      console.log(this.tableData)
      this.langDefaultFlag = true
    },
    getNowTime () {
      var now = new Date()
      var year = now.getFullYear()
      var month = now.getMonth()
      var date = now.getDate()
      month = month + 1
      month = month.toString().padStart(2, '0')
      date = date.toString().padStart(2, '0')
      var defaultDate = `${year}-${month}-${date}`
      this.timeValue = [defaultDate + ' 00:00:00', defaultDate + ' 23:59:59']
      this.queryParams.startTime = this.timeValue[0]
      this.queryParams.endTime = this.timeValue[1]
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
    this.getNowTime()
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
    .icon_img {
      position: relative;
      top: 2px;
    }
  }
}
</style>
