<template>
  <div class="content">
    <div class="main">
      <div class="titleEdit"><span>处方列表</span></div>
      <div class="navBox">
        <div class="filters">
          <div class="filters_item">
            <span>医师姓名：</span>
            <el-input
              v-model="queryParams.doctorName"
              size="small"
              style="width: 150px"
              placeholder="请输入药品名称"
            >
            </el-input>
          </div>
          <div class="filters_item">
            <span class="fil_span">科室：</span>
            <el-input
              v-model="queryParams.departmentName"
              size="small"
              style="width: 150px"
              placeholder="请输入批准文号"
            >
            </el-input>
          </div>
          <div class="filters_item">
            <span class="fil_span">就诊类型：</span>
            <el-select
              v-model="queryParams.prescriptionType"
              size="small"
              class="default_input"
              style="width:150px"
            >
              <el-option
                v-for="item in prescriptionTypes"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
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
          <el-table-column label="处方号">
            <template slot-scope="scope">
              <div>
                <span
                  @click="handlePrescriptionInfo(scope.row.prescriptionCode)"
                  style="color: #5a8bff; cursor: pointer"
                >
                  {{ scope.row.prescriptionCode }}
                </span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label='就诊类型'>
            <template v-slot='scope'>
              {{getLabelValue(prescriptionTypes,scope.row.auditType)}}
            </template>
          </el-table-column>
          <el-table-column label="医师姓名">
            <template slot-scope="scope">
              <div class="linkStyle">
                <a @click="
                    handleDoctorMessage(
                      scope.row.id,
                      scope.row.doctorCode
                    )
                  ">{{ scope.row.doctorName }}</a>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="departmentName"
            label="科室"
          ></el-table-column>
          <el-table-column label="订单号">
            <template slot-scope="scope">
              <div class="operation">
                <a @click="handleSeeOrder(scope.row.orderSnByOrderInfo)">{{scope.row.orderSnByOrderInfo}}</a>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="totalPrice"
            label="处方金额"
          ></el-table-column>
          <el-table-column label="处方药品">
            <template v-slot="scope">
              <span
                v-for="(item,index) in scope.row.goodsList"
                :key='index'
                style="margin-right:5px;"
              >
                {{item.goodsName}}
              </span>
            </template>
          </el-table-column>
          <el-table-column
            prop="createTime"
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
      prescriptionTypes: [
        { value: null, label: '全部' },
        { value: 0, label: '不审核' },
        { value: 1, label: '续方' },
        { value: 2, label: '开方' },
        { value: 3, label: '会话开方' }
      ],
      tableData: [],
      queryParams: {
        startTime: '',
        endTime: '',
        doctorName: '',
        departmentName: '',
        prescriptionType: null
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
    },
    handleSeeOrder (code) {
      console.log(this.$router)
      let newpage = this.$router.resolve({
        name: 'orderInfo'
      })
      newpage.href = newpage.href + '?orderSn=' + code
      console.log(newpage.href)
      window.open(newpage.href, '_blank')
    },
    getLabelValue (map, value) {
      let label = ''
      map.forEach(item => {
        if (item.value === value) {
          label = item.label
        }
      })
      return label
    },
    handleDoctorMessage (id, code) {
      const { href } = this.$router.resolve({
        path: '/admin/home/main/doctor/detail',
        query: {
          id: id,
          code: code
        }
      })
      window.open(href, '_blank')
    },
    handlePrescriptionInfo (prescriptionCode) {
      let newpage = this.$router.resolve({
        name: 'prescription_message',
        query: {
          prescriptionCode
        }
      })
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
