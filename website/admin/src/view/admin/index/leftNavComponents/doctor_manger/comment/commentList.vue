<template>
  <div class="main">
    <div class="nav_box">
      <div class="filters">
        <div class="filters_item">
          <span class="fil_span">医师姓名：</span>
          <el-select
            v-model="param.doctorName"
            placeholder="请输入医生姓名"
            size="small"
            class="default_input"
            filterable
            clearable
          >
            <el-option label="全部" value=" "></el-option>
            <el-option
              v-for="item in doctorList"
              :key="item.id"
              :label="item.name"
              :value="item.name"
            ></el-option>
          </el-select>
        </div>
        <div class="filters_item">
          <span class="fil_span">评分星级：</span>
          <el-select
            v-model="param.stars"
            size="small"
            class="default_input"
            filterable
          >
            <el-option
              v-for="item in starLevel"
              :key="item.key"
              :label="item.value"
              :value="item.key"
            ></el-option>
          </el-select>
        </div>
        <div class="filters_item">
          <span>审核状态：</span>
          <el-select
            v-model="param.auditStatus"
            size="small"
            class="mini_select"
            style="width: 170px"
          >
            <el-option
              v-for="item in auditFlag"
              :key="item.key"
              :label="item.value"
              :value="item.key"
            ></el-option>
          </el-select>
        </div>
        <div class="filters_item">
          <span>自动审核：</span>
          <el-switch
            v-model="autoReview"
            active-color="#f7931e"
            @change="changeAudit"
          ></el-switch>
          <span style="margin-left: 10px">{{
            autoReview ? '已开启' : '已关闭'
          }}</span>
        </div>
        <div class="btn_wrap">
          <el-button type="primary" size="small" @click="initData"
            >查询</el-button
          >
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
        <el-table-column prop="name" label="医生姓名">
          <template slot-scope="scope">
            <div class="operation">
              <a @click="handleSeeDoctor(scope.row.doctorId)">{{scope.row.doctorName}}({{scope.row.doctorCode}})</a>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="patientName" label="患者">
          <template slot-scope="scope">
            <div class="operation">
              <a @click="handleSeePatient(scope.row.patientId)">{{scope.row.patientName}}({{scope.row.patientId}})</a>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="userName" label="用户昵称">
          <template slot-scope="scope">
            <div class="operation">
              <a @click="handleSeeUserInfo(scope.row.userId)">{{scope.row.userName}}({{scope.row.userId}})</a>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="orderSn" label="咨询订单号">
          <template slot-scope="scope">
            <div class="operation">
              <a @click="handleSeeAdvisoryOrderInfo(scope.row.orderId)">{{scope.row.orderId}}({{scope.row.orderSn}})</a>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="评价内容" align="center" width="200px">
          <template slot-scope="scope">
            <div class="evaluation-info">
              <div class="evaluation-info_item">
                <span class="evaluation-info_title">评分：</span
                ><span
                  ><i
                    class="el-icon-star-on"
                    v-for="index in scope.row.stars"
                    :key="index"></i>
              </span>
              </div>
              <div class="evaluation-info_item">
                <span class="evaluation-info_title">评价：</span
                ><span>{{ scope.row.commNote || '此用户没有评价' }}</span>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="评价回复" align="center" width="200px">
          <template slot-scope="scope">
            <div class="evaluation-info">
              <div class="evaluation-info_item">
                <span class="evaluation-info_title"></span
                ><span style="text-align: center">{{
                  scope.row.replylist
                    ? '回复：' + scope.row.replylist[0].replyNote
                    : '暂无回复'
                }}</span>
              </div>
              <div class="evaluation_response">
                <el-button
                  type="primary"
                  v-if="scope.row.replylist"
                  size="mini"
                  @click="delDoctorComment(scope.row.replylist[0].id)"
                  >删除回复</el-button
                >
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="评价时间">
          <template v-slot="scope">
            <span>{{ scope.row.createTime | timeDate }}</span>
          </template>
        </el-table-column>
        <el-table-column label="匿名评价">
          <template v-slot="scope">
            <span>{{ scope.row.isAnonymou == 0 ? '否' : '是' }}</span>
          </template>
        </el-table-column>

        <el-table-column label="审核状态">
          <template v-slot="scope">
            <span>{{ scope.row.auditStatus | status }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-tooltip
              content="通过"
              placement="top"
              v-if="scope.row.auditStatus == 0||scope.row.auditStatus == 2">
              <span
                class="el-icon-success operateSpan"
                @click="passComment(scope.row.id, 1)"
              ></span>
            </el-tooltip>
            <el-tooltip
              content="拒绝"
              placement="top"
              v-if="scope.row.auditStatus == 0||scope.row.auditStatus == 1">
              <span
                class="el-icon-error operateSpan"
                @click="passComment(scope.row.id, 2)"
              ></span>
            </el-tooltip>
            <el-tooltip
              content="置顶"
              placement="top"
              v-if="scope.row.top === 0">
              <span
                class="el-icon-top operateSpan"
                @click="evaluationTop(scope.row.id, 1)"
              ></span>
            </el-tooltip>
            <el-tooltip
              content="取消置顶"
              placement="top"
              v-if="scope.row.top > 0">
              <span
                class="el-icon-bottom operateSpan"
                @click="evaluationTop(scope.row.id, 0)"
              ></span>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <pagination :page-params.sync="pageParams" @pagination="initData" />
    </div>
  </div>
</template>

<script>
import { getDoctorList } from '@/api/admin/doctorManage/advistoryTotal/advistory.js'
import { getCommentList, ifAuditAuto, getAuditAuto, commentUntop, deleteComment, deleteDoctorComment, aduitComment } from '@/api/admin/doctorManage/comment/comment.js'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: {
    pagination
  },
  watch: {

  },
  mounted () {
    this.param.doctorName = this.$route.query.name ? this.$route.query.name : ''
    this.getDoctor({})
    this.auditAuto()
    this.initData()
  },
  data () {
    return {
      loading: false,
      pageParams: {
        currentPage: 1,
        pageRows: 20
      },
      tableData: [],
      starLevel: [
        { key: 0, value: '全部' },
        { key: 1, value: '一星' },
        { key: 2, value: '二星' },
        { key: 3, value: '三星' },
        { key: 4, value: '四星' },
        { key: 5, value: '五星' }
      ],
      auditFlag: [
        { key: null, value: '全部' },
        { key: 0, value: '待审核' },
        { key: 1, value: '已通过' },
        { key: 2, value: '未通过' }
      ],
      param: {
        stars: 0,
        flag: -1,
        sort: 'createTime',
        doctorName: ''
      },
      autoReview: false,
      doctorList: [],
      total: {}
    }
  },
  methods: {

    initData () {
      let params = Object.assign({}, this.param, this.pageParams)
      getCommentList(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
        }
      }).catch(err => console.log(err))
    },
    getDoctor (doctor) {
      getDoctorList(doctor).then(res => {
        if (res.error === 0) {
          console.log(res)
          this.doctorList = res.content
        }
      })
    },
    auditAuto () {
      getAuditAuto().then(res => {
        if (res.error === 0) {
          this.autoReview = res.content === 1
        }
      })
    },
    changeAudit () {
      let status = this.autoReview === true ? 1 : 0
      let params = { status: status }
      ifAuditAuto(params).then(res => {
        console.log(res)
      })
    },
    delDoctorComment (id) {
      deleteDoctorComment({ id: id }).then(res => {
        if (res.error === 0) {
          this.$message.success({
            message: '删除成功',
            duration: '2000'
          })
          console.log(this.tableData)
          let targetData = this.tableData.find(item => { if (item.replylist) return item.replylist[0].id === id })
          targetData.replylist = null
        }
      })
    },
    delComment (id) {
      this.$confirm(this.$t('pageClassification.deleteConfirm'), {
        confirmButtonText: this.$t('pageClassification.confirm'),
        cancelButtonText: this.$t('pageClassification.cancel'),
        type: 'warning'
      }).then(() => {
        deleteComment({ id: id }).then(res => {
          if (res.error === 0) {
            this.$message.success({
              message: '删除成功',
              duration: '2000'
            })
            let targetData = this.tableData.find(item => item.id === id)
            targetData.isDelete = 1
          }
        })
      }).catch(() => {
      })
    },
    evaluationTop (id, status) {
      commentUntop({ id: id, status: status }).then(res => {
        console.log(res)
        if (res.error === 0) {
          if (status === 1) {
            this.$message.success({
              message: '置顶成功',
              duration: '2000'
            })
            let targetData = this.tableData.find(item => item.id === id)
            targetData.top = 1
          } else {
            this.$message.success({
              message: '取消成功',
              duration: '2000'
            })
            let targetData = this.tableData.find(item => item.id === id)
            targetData.top = 0
          }
        }
      })
    },
    // 跳转患者详情
    handleSeeAdvisoryOrderInfo (code) {
      console.log(this.$router)
      let newpage = this.$router.resolve({
        name: 'advisory_order_info'
      })
      newpage.href = newpage.href + '?orderId=' + code
      console.log(newpage.href)
      window.open(newpage.href, '_blank')
    },
    // 跳转患者详情
    handleSeeUserInfo (code) {
      console.log(this.$router)
      let newpage = this.$router.resolve({
        name: 'membershipInformation'
      })
      newpage.href = newpage.href + '?userId=' + code
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
    passComment (id, status) {
      aduitComment({ id: id, status: status }).then(res => {
        if (res.error === 0) {
          if (status === 1) {
            this.$message.success({
              message: '审核通过',
              duration: '2000'
            })
            let targetData = this.tableData.find(item => item.id === id)
            targetData.auditStatus = 1
          } else {
            this.$message.success({
              message: '审核未通过',
              duration: '2000'
            })
            let targetData = this.tableData.find(item => item.id === id)
            targetData.auditStatus = 2
          }
        }
      })
    }
  },
  filters: {
    timeDate: function (val) {
      if (!val) return
      val = val.split(' ')
      return val[0]
    },
    status: function (val) {
      let status = ['待审核', '审核通过', '审核未通过']
      return status[val]
    }
  }
}
</script>

<style lang='scss' scoped>
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
        align-items: center;
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
    margin: 0 10px 10px;
    a {
      color: #5a8bff;
      cursor: pointer;
    }
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
    margin: 0 10px;
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
.operateSpan {
  font-size: 22px;
  color: #5a8bff;
  cursor: pointer !important;
}
.evaluation_response {
  display: flex;
  flex-direction: column;
  align-items: center;
  > .el-button {
    width: 90px;
  }
}
.evaluation-info {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  text-align: left;
  > .evaluation-info_item {
    display: flex;
    > span {
      flex: 1;
      &.evaluation-info_title {
        flex: 0 1 auto;
        width: auto;
      }
      > .el-icon-star-on {
        color: #ff6666;
        font-size: 20px;
      }
    }
  }
}
</style>
