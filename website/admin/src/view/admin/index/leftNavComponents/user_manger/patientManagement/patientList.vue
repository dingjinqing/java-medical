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
              style="width: 190px"
              placeholder="请输入患者手机号"
            >
            </el-input>
          </div>
          <div class="filters_item">
            <span style="width: 60px">姓名：</span>
            <el-input
              v-model="queryParams.name"
              size="small"
              style="width: 190px"
              placeholder="请输入姓名"
            >
            </el-input>
          </div>
          <div
            class="filters_item"
            style="width: 350px"
          >
            <span
              class="fil_span"
              style="width: 150px"
            >时间筛选：</span>
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
            prop="id"
            label="患者编号"
          ></el-table-column>
          <el-table-column
            prop="wxNickName"
            label="微信昵称"
          >
            <template v-slot="scope">
              <span
                style="color: #5a8bff; cursor: pointer"
                @click="hanldeToDetail(item.userIdNew)"
                v-for="item in scope.row.userParamList"
                :key='item.userIdNew'
              >{{ item.wxNickName }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="name"
            label="姓名"
          ></el-table-column>
          <el-table-column
            prop="mobile"
            label="手机号"
          ></el-table-column>
          <el-table-column
            prop="treatmentNo"
            label="就诊卡号"
          ></el-table-column>
          <el-table-column label="性别">
            <template v-slot="scope">
              <span>{{
                scope.row.sex == 0 ? '男' : scope.row.sex == 1 ? '女' : '未知'
              }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="diseaseHistoryNameStr"
            label="疾病史"
          ></el-table-column>
          <el-table-column
            prop="allergyHistory"
            label="过敏史"
          ></el-table-column>
          <el-table-column
            prop="countPrescription"
            label="处方数量"
          ></el-table-column>
          <el-table-column
            prop="createTime"
            label="注册时间"
          ></el-table-column>
          <el-table-column label="操作">
            <template v-slot="scope">
              <div class="operation">
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="查看详情"
                  placement="top"
                >
                  <a @click="handleSeeMessage(scope.row.id)">查看详情</a>
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
// 导入api
import { getPatientList } from '@/api/admin/memberManage/patientManage.js'
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
        pageRows: 20
      },
      tableData: [],
      queryParams: {
        name: null,
        mobile: null,
        startTime: '',
        endTime: ''
      },
      // 表格原始数据
      originalData: []
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.queryParams.currentPage = this.pageParams.currentPage
      this.queryParams.pageRows = this.pageParams.pageRows
      getPatientList(Object.assign(this.queryParams, this.pageParams)).then((res) => {
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
      })
    },
    handleSeeMessage (userId) {
      console.log(this.$router)
      this.$router.push({
        name: 'patient_message',
        query: {
          id: userId
        }
      })
    },
    hanldeToDetail (userId) {
      this.$router.push({
        name: 'membershipInformation',
        query: {
          userId: userId
        }
      })
    },
    handleData (data) {
      this.tableData = data
      this.langDefaultFlag = true
    }
  },

  mounted () {
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
    padding: 15px;
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
          width: 120px;
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
