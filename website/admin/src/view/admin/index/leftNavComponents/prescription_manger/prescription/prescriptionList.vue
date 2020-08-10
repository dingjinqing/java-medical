<template>
  <div class="content">
    <div class="main">
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
          ></el-table-column>
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
import { getAllPrescriptionList } from '@/api/admin/memberManage/patientManage.js'
export default {
  components: { pagination },
  data () {
    return {
      loading: false,
      langDefaultFlag: false,
      pageParams: {
        currentPage: 1,
        pageRows: 20
      },
      tableData: [],
      storeGroup: [],
      queryParams: {
        mobile: null,
        currentPage: 1,
        pageRows: 20
      },
      // 表格原始数据
      originalData: []
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
    this.initDataList()
  }
}
</script>

<style scoped lang='scss'>
.main {
  padding: 10px;
  .navBox {
    display: flex;
    width: 100%;
    background-color: #fff;
    .filters {
      flex: 2;
      display: flex;
      flex-wrap: wrap;
      line-height: 32px;
      margin-left: -15px;
      .filters_item {
        width: 250px;
        display: flex;
        justify-content: flex-end;
        margin-left: 15px;
        > span {
          width: 70px;
          font-size: 14px;
          text-align: right;
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
