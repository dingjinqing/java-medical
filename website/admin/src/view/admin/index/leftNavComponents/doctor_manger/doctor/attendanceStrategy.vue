<template>
  <div class="main-container">
    <div class="card-content">
      <div class="main-title">
        <span>出勤统计</span>
      </div>
      <div class="panel-content">
        <div class="canvas-content">
          <ve-pie
            :data="chartData"
            :colors="chartColor"
            width
            height="390px"
            :extend="chartEntend"
          />
        </div>
        <div class="table-content">
          <div>
            <div style="float: right; margin-bottom: 20px">
              <el-select v-model="filterType">
                <el-option label="本月" :value="1"></el-option>
                <el-option label="最近30天" :value="2"></el-option>
              </el-select>
            </div>
            <el-table
              :data="docterAttendanceTable"
              border
              header-row-class-name="tableClss"
            >
              <el-table-column
                label="医师姓名"
                prop="name"
                align="center"
              ></el-table-column>
              <el-table-column
                label="最近登录时间"
                prop="lastTime"
                align="center"
              ></el-table-column>
              <el-table-column
                label="出勤率"
                prop="loginRate"
                align="center"
              ></el-table-column>
              <el-table-column label="院内排名" prop="rank" align="center">
                <template slot-scope="scope">
                  <div>
                    {{
                      (docterAttendancePageParams.currentPage - 1) *
                        docterAttendancePageParams.pageRows +
                      scope.$index +
                      1
                    }}
                  </div>
                </template>
              </el-table-column>
            </el-table>
            <pagination
              :page-params.sync="docterAttendancePageParams"
              @pagination="getAttendanceSummary"
            />
          </div>
        </div>
      </div>
    </div>
    <div class="card-content">
      <div class="main-title">
        <span>业绩统计</span>
      </div>
      <div class="filters">
        <div class="filters_item">
          <span>医师姓名：</span>
          <el-input
            v-model="docterPerformanceParams.doctorName"
            placeholder="请输入医师姓名"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>科室：</span>
          <el-select
            v-model="docterPerformanceParams.departmentId"
            filterable
            size="small"
          >
            <el-option
              v-for="item in departmentList"
              :key="item.id"
              :value="item.id"
              :label="item.name"
            ></el-option>
          </el-select>
        </div>
        <div class="filters_item">
          <span>时间筛选：</span>
          <el-date-picker
            v-model="docterPerformanceParams.startTime"
            type="datetime"
            placeholder="开始时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            class="middle_input"
            size="small"
          />
          至
          <el-date-picker
            v-model="docterPerformanceParams.endTime"
            type="datetime"
            placeholder="结束时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            class="middle_input"
            default-time="23:59:59"
            size="small"
          />
        </div>
        <div class="filters_item">
          <el-button
            type="primary"
            @click="filterTable"
            size="small"
            style="margin-left: 20px"
            >搜索</el-button
          >
        </div>
      </div>
      <div style="margin-top: 20px">
        <el-table
          :data="docterPerformanceTable"
          border
          header-row-class-name="tableClss"
          @sort-change="sortChange"
        >
          <el-table-column
            label="医师姓名"
            prop="name"
            align="center"
          ></el-table-column>
          <el-table-column
            label="科室"
            prop="departmentNames"
            align="center"
          ></el-table-column>
          <el-table-column
            label="累计处方数"
            prop="prescriptionNum"
            align="center"
            sortable="custom"
          ></el-table-column>
          <el-table-column
            label="累计处方金额"
            prop="prescriptionMoney"
            align="center"
            sortable="custom"
          ></el-table-column>
          <el-table-column
            label="累计咨询单数"
            prop="inquiryNumber"
            align="center"
            sortable="custom"
          ></el-table-column>
          <el-table-column
            label="累计咨询金额"
            prop="inquiryMoney"
            align="center"
            sortable="custom"
          ></el-table-column>
          <el-table-column
            label="累计消费金额"
            prop="consumeMoney"
            align="center"
            sortable="custom"
          ></el-table-column>
        </el-table>
        <pagination
          :page-params.sync="docterPerformancePageParams"
          @pagination="getDoctorSummary"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { getAttendanceSummaryList, getDoctorSummaryList, getDepartment, getAttendanceDivide } from '@/api/admin/doctorManage/doctorAttendance/doctorAttendance'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination'),
    've-pie': () => import('v-charts/lib/pie.common')
  },
  data () {
    return {
      docterAttendanceTable: [],
      docterPerformanceTable: [],
      docterAttendancePageParams: {},
      filterType: 1,
      docterPerformancePageParams: {},
      docterPerformanceParams: {
        startTime: (() => {
          let start = new Date()
          start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
          return `${start.getFullYear()}-${start.getMonth() + 1}-${start.getDate() + 1} 00:00:00`
        })(),
        endTime: (() => {
          let end = new Date()
          return `${end.getFullYear()}-${end.getMonth() + 1}-${end.getDate() + 1} 23:59:59`
        })(),
        doctorName: null,
        departmentId: 0,
        orderField: null,
        orderDirection: null
      },
      departmentList: [],
      chartData: {
        columns: ['出勤率', '人数'],
        rows: [
          { '出勤率': '50%以下' },
          { '出勤率': '50%~75%' },
          { '出勤率': '75%以上' }
        ]
      },
      chartSetting: {
        metrics: ['出勤率']
      },
      chartEntend: {
        legend: {
          // 可查看官方配置文档，left：'center'的意思是居中
          left: 'right',
          // 垂直排列
          orient: 'vertical',
          // 设置高度
          height: '100px',
          bottom: 100
        }
      },
      chartColor: ['rgb(254,67,101)', 'rgb(249,205,173)', 'rgb(131,175,155)']
    }
  },
  mounted () {
    this.getDepartmentList()
    this.getAttendanceSummary()
    this.getDoctorSummary()
    this.getDivide()
  },
  methods: {
    getAttendanceSummary () {
      getAttendanceSummaryList({ type: this.filterType, ...this.docterAttendancePageParams }).then(res => {
        if (res.error === 0) {
          this.docterAttendanceTable = res.content.dataList
          this.docterAttendancePageParams = res.content.page
        } else {
          this.$message.error({
            message: res.message
          })
        }
      })
    },
    getDoctorSummary () {
      getDoctorSummaryList({ ...this.docterPerformanceParams, ...this.docterPerformancePageParams, departmentId: !this.docterPerformanceParams.departmentId ? null : this.docterPerformanceParams.departmentId }).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.docterPerformanceTable = res.content.dataList
          this.docterPerformancePageParams = res.content.page
        } else {
          this.$message.error({
            message: res.message
          })
        }
      })
    },
    getDepartmentList () {
      getDepartment().then(res => {
        if (res.error === 0) {
          this.departmentList = [{ id: 0, name: '全部' }, ...res.content]
        } else {
          this.$message.error({
            message: res.message
          })
        }
      })
    },
    getDivide () {
      getAttendanceDivide({ type: this.filterType }).then(res => {
        if (res.error === 0) {
          this.chartData.rows = [
            { '出勤率': '50%以下', '人数': res.content.halfNum },
            { '出勤率': '50%~75%', '人数': res.content.thirdQuarterNum },
            { '出勤率': '75%以上', '人数': res.content.fourthQuarterNum }
          ]
        } else {
          this.$message.error({
            message: res.message
          })
        }
      })
    },
    filterTable () {
      this.docterPerformancePageParams.currentPage = 1
      this.getDoctorSummary()
    },
    sortChange ({ column, prop, order }) {
      console.log(column, prop, order)
      let orderFieldName = {
        prescriptionNum: 'prescription_num',
        prescriptionMoney: 'prescription_money',
        inquiryNumber: 'inquiry_number',
        inquiryMoney: 'inquiry_money',
        consumeMoney: 'consume_money'
      }
      if (['descending', 'ascending'].includes(order)) {
        this.docterPerformanceParams.orderField = orderFieldName[prop]
        this.docterPerformanceParams.orderDirection = order === 'descending' ? 'desc' : 'asc'
      } else {
        this.docterPerformanceParams.orderField = null
        this.docterPerformanceParams.orderDirection = null
      }
      this.docterPerformancePageParams.currentPage = 1
      this.getDoctorSummary()
    }
  },
  watch: {
    filterType () {
      this.docterAttendancePageParams.currentPage = 1
      this.getAttendanceSummary()
      this.getDivide()
    }
  }
}
</script>

<style lang="scss" scoped>
.main-container {
  padding: 10px;
  display: flex;
  flex-direction: column;
  .card-content {
    background-color: #fff;
    padding: 10px;
    & + .card-content {
      margin-top: 10px;
    }
    .main-title {
      height: 40px;
      background-color: #eef1f6;
      line-height: 40px;
      span {
        color: #333;
        font-size: 14px;
        font-weight: 600;
        margin-left: 28px;
        position: relative;
        &::before {
          content: ' ';
          position: absolute;
          height: 100%;
          width: 2px;
          background-color: #5a8bff;
          top: 0;
          left: -14px;
        }
      }
    }
    .panel-content {
      display: flex;
      margin-top: 20px;
      height: 390px;
      > div {
        flex: 1;
        min-width: 0;
        overflow: hidden;
        position: relative;
        &.table-content {
          > div {
            position: absolute;
            width: 100%;
          }
        }
        &.canvas-content {
          /deep/ canvas {
            left: -130px !important;
          }
        }
      }
    }
  }
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.filters {
  display: flex;
  line-height: 32px;
  flex-wrap: wrap;
  margin-top: 20px;
  // max-width: 1226px;
  .filters_item {
    display: flex;
    max-width: 480px;
    min-width: 320px;
    /deep/ .areaLinkage {
      .el-select {
        margin-left: 10px;
        &:first-of-type {
          margin-left: 0;
        }
      }
    }
    > span {
      min-width: 100px;
      font-size: 14px;
      text-align: right;
    }
  }
}
.default_input {
  width: 180px;
}
.middle_input {
  width: 185px;
}
</style>
