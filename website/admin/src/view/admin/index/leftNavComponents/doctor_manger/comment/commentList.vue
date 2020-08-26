<template>
  <div class="main">
    <div class="nav_box">
      <div class="filters">
        <div class="filters_item ">
          <span class="fil_span">医师姓名：</span>
          <el-select
            v-model="param.doctorId"
            placeholder="请输入医生姓名"
            size="small"
            class="default_input"
            filterable
            clearable
          >
            <el-option
              label="全部"
              value=" "
            ></el-option>
            <el-option
              v-for="item in doctorList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>

        </div>
        <div class="filters_item ">
          <span class="fil_span">评价星级：</span>
          <el-select
            v-model="param.commstar"
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
            v-model="param.flag"
            size="small"
            class="mini_select"
            style="width: 170px;"
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
          ></el-switch>
          <span style="margin-left:10px">{{autoReview ? '已开启':'已关闭'}}</span>
        </div>
        <div class="btn_wrap">
          <el-button
            type='primary'
            size='small'
            @click="initData"
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
          prop='doctorName'
          label='医生姓名'
        ></el-table-column>
        <el-table-column
          prop='doctorName'
          label='用户昵称'
        ></el-table-column>
        <el-table-column
          prop='amount'
          label='咨询订单号'
        ></el-table-column>
        <el-table-column
          label="评价内容"
          align="center"
          width="200px"
        >
          <template slot-scope="scope">
            <div class="evaluation-info">
              <div class="evaluation-info_item">
                <span class="evaluation-info_title">评分：</span><span><i
                    class="el-icon-star-on"
                    v-for="index in scope.row.commstar"
                    :key="index"
                  ></i></span>
              </div>
              <div class="evaluation-info_item">
                <span class="evaluation-info_title">评价：</span><span>{{
                  scope.row.commNote || '此用户没有评价'
                }}</span>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="评价回复"
          align="center"
          width="200px"
        >
          <template slot-scope="scope">
            <div class="evaluation-info">
              <div class="evaluation-info_item">
                <span class="evaluation-info_title">回复：</span><span>{{
                  scope.row.commNote || '暂无回复'
                }}</span>
              </div>
              <div class="evaluation_response">
                <el-button
                  type="primary"
                  v-if="!scope.row.content"
                  size="mini"
                  @click="writeReply(scope.row.id)"
                >删除回复</el-button>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label='评价时间'>
          <template v-slot='scope'>
            <span>{{scope.row.createTime | timeDate}}</span>
          </template>
        </el-table-column>

        <el-table-column
          prop='departmentName'
          label='匿名评价'
        ></el-table-column>

        <el-table-column
          prop='oncePrice'
          label='审核状态'
        ></el-table-column>
        <el-table-column
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <el-tooltip
              content="删除"
              placement="top"
            >
              <span
                class="el-icon-delete operateSpan"
                @click="delEvaluation(scope.row.id)"
              ></span>
            </el-tooltip>
            <el-tooltip
              content="通过"
              placement="top"
              v-if="
                target === 'Record' &&
                  (scope.row.flag === 0 || scope.row.flag === 2)
              "
            >
              <span
                class="el-icon-success operateSpan"
                @click="evaluationPass(scope.row.id)"
              ></span>
            </el-tooltip>
            <el-tooltip
              content="拒绝"
              placement="top"
              v-if="
                target === 'Record' &&
                  (scope.row.flag === 0 || scope.row.flag === 1)
              "
            >
              <span
                class="el-icon-error operateSpan"
                @click="evaluationRefuse(scope.row.id)"
              ></span>
            </el-tooltip>
            <el-tooltip
              content="置顶"
              placement="top"
              v-if="scope.row.isTop === 0"
            >
              <span
                class="el-icon-top operateSpan"
                @click="evaluationTop(scope.row.id)"
              ></span>
            </el-tooltip>
            <!-- <el-tooltip
              :content="$t('evaluation.down')"
              placement="top"
              v-if="scope.row.isTop === 1"
            >
              <span
                class="el-icon-bottom operateSpan"
                @click="evaluationCancelTop(scope.row.id)"
              ></span>
            </el-tooltip> -->
          </template>
        </el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initData"
      />

    </div>

  </div>
</template>

<script>
import { getAdvistoryReportList, getReportExport, getDoctorList, getDoctorTotal } from '@/api/admin/doctorManage/advistoryTotal/advistory.js'
import { getDate } from '@/api/admin/firstWebManage/goodsStatistics/goodsStatistics.js'
import { download } from '@/util/excelUtil.js'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: {
    pagination
  },
  watch: {
    lang () {
      this.timeRange = this.$t('tradesStatistics.timeRange')
    }
  },
  mounted () {
    this.getDateValue(1)
    this.getDoctor({})
    this.initData()
  },
  data () {
    return {
      loading: false,
      pageParams: {},
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
        { key: -1, value: '全部' },
        { key: 0, value: '待审核' },
        { key: 1, value: '已通过' },
        { key: 2, value: '未通过' }
      ],
      param: {
        commstar: 0,
        flag: 0
      },
      autoReview: false,
      doctorList: [],
      total: {}
    }
  },
  methods: {
    // 导出
    exportData () {
      getReportExport(this.param).then(res => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : '咨询报表.xlsx'
        download(res, decodeURIComponent(fileName))
      }).catch(err => console.log(err))
    },
    // 选择时间段
    dateChangeHandler (time) {
      if (time !== 0) {
        this.getDateValue(time)
        this.initData()
      }
    },
    // 自定义时间
    changeDate () {
      this.param.startTime = this.timeValue[0].substring(0, 4) + '-' + this.timeValue[0].substring(4, 6) + '-' + this.timeValue[0].substring(6, 8) + ' 00:00:00'
      this.param.endTime = this.timeValue[1].substring(0, 4) + '-' + this.timeValue[1].substring(4, 6) + '-' + this.timeValue[1].substring(6, 8) + ' 00:00:00'
      this.startDate.year = this.timeValue[0].substring(0, 4)
      this.startDate.month = this.timeValue[0].substring(4, 6)
      this.startDate.day = this.timeValue[0].substring(6, 8)
      this.endDate.year = this.timeValue[1].substring(0, 4)
      this.endDate.month = this.timeValue[1].substring(4, 6)
      this.endDate.day = this.timeValue[1].substring(6, 8)
      this.initData()
    },
    getDateValue (unit) {
      getDate(unit).then(res => {
        if (res.error === 0) {
          this.startDate.year = res.content.startTime.split('-')[0]
          this.startDate.month = res.content.startTime.split('-')[1]
          this.startDate.day = res.content.startTime.split('-')[2]
          this.endDate.year = res.content.endTime.split('-')[0]
          this.endDate.month = res.content.endTime.split('-')[1]
          this.endDate.day = res.content.endTime.split('-')[2]
          this.param.startTime = res.content.startTime + ' 00:00:00'
          this.param.endTime = res.content.endTime + ' 00:00:00'
          this.initData()
        }
      }).catch(err => console.log(err))
    },
    initData () {
      let params = Object.assign({}, this.param, this.pageParams)
      getAdvistoryReportList(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
        }
      }).catch(err => console.log(err))
      this.getTotal({ doctorId: this.param.doctorId, startTime: this.param.startTime, endTime: this.param.endTime })
    },
    getDoctor (doctor) {
      getDoctorList(doctor).then(res => {
        if (res.error === 0) {
          console.log(res)
          this.doctorList = res.content
        }
      })
    },
    getTotal (doctor) {
      getDoctorTotal(doctor).then(res => {
        if (res.error === 0) {
          console.log(res)
          this.total = res.content
        }
      })
    }
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
