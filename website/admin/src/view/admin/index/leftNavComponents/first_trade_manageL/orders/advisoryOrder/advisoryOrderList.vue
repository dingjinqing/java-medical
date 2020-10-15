<template>
  <div class="main1">
    <div class="nav_box">
      <div class="filters">
        <div class="filters_item">
          <span class="fil_span">医生：</span>
          <el-input
            v-model="queryParams.doctorName"
            size="small"
            style="width: 150px"
            placeholder="请输入医生姓名"
          ></el-input>
        </div>
        <div class="filters_item">
          <span class="fil_span">患者：</span>
          <el-input
            v-model="queryParams.patientName"
            size="small"
            style="width: 150px"
            placeholder="请输入患者姓名"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>提交时间：</span>
          <el-date-picker
            v-model="queryParams.startTime"
            type="datetime"
            placeholder="开始时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            class="default_input"
            @change="datePickerChange(true, applyTime)"
            size="small"
          />
          <span style="width: auto; margin: 0 5px">至</span>
          <el-date-picker
            v-model="queryParams.endTime"
            type="datetime"
            placeholder="结束时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            class="default_input"
            @change="datePickerChange(false, applyTime)"
            :picker-options="applyEndTime"
            default-time="23:59:59"
            size="small"
          />
        </div>
        <div class="filters_item">
          <span class="fil_span">订单状态：</span>
          <el-select
            size="small"
            clearable
            class="timeSelect"
            v-model="queryParams.orderStatus"
          >
            <el-option
              v-for="item in orderStatusInfo"
              :key="item.id"
              :label="item.text"
              :value="item.id"
            >
            </el-option>
          </el-select>
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
          prop="doctorName"
          label="医生"
        >
          <template slot-scope="scope">
            <div class="linkStyle">
              <a @click="
                    handleDoctorMessage(
                      scope.row.doctorId,
                      scope.row.doctorCode
                    )
                  ">{{ scope.row.doctorName }}</a>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="patientName"
          label="患者"
        ></el-table-column>
        <!-- <el-table-column
          prop='departmentName'
          label='科室'
        ></el-table-column> -->
        <el-table-column
          prop="orderAmount"
          label="咨询费"
        ></el-table-column>
        <el-table-column
          prop="orderStatusName"
          label="订单状态"
        ></el-table-column>
        <el-table-column
          prop="orderSn"
          label="咨询单号"
        >
          <template slot-scope="scope">
            <span
              class="linkStyle"
              @click="orderHandler(scope.row.orderSn)"
            >{{ scope.row.orderSn }}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="totalRebateMoney"
          label="返利金额"
        ></el-table-column>
        <el-table-column
          prop="settlementName"
          label="返利状态"
        ></el-table-column>
        <el-table-column
          prop="createTime"
          label="提交时间"
        ></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <div class="operation">
              <a
                v-if="
                  scope.row.orderAmount - scope.row.refundMoney > 0 &&
                  scope.row.orderStatus !== 0 &&
                  scope.row.orderStatus !== 5
                "
                href="javaScript:void(0);"
                @click="returnOrder(scope.row)"
              >退款</a>
              <a
                href="javaScript:void(0);"
                @click="toDetail(scope.row.orderSn)"
              >详情</a>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />
    </div>
    <ManualRefund
      :dataInfo="refundInfo"
      :show.sync="showRefund"
      @complete="initDataList"
    />
  </div>
</template>

<script>
import pagination from '@/components/admin/pagination/pagination'
import ManualRefund from './returnAdvisoryDialog'
import { getBelongParts } from '@/api/admin/doctorManage/doctorInfo/doctor'
import { advisoryOrderList } from '@/api/admin/orderManage/order.js'
export default {
  components: {
    pagination,
    ManualRefund
  },
  mounted () {
    this.queryParams.doctorName = this.$route.query.name ? this.$route.query.name : ''
    this.queryParams.doctorId = this.$route.query.doctorId ? this.$route.query.doctorId : null
    this.initDoctorPart()
    this.initDataList()
  },
  data () {
    return {
      loading: false,
      queryParams: {
        doctorName: '',
        patientName: '',
        departmentId: '',
        orderStatus: '',
        doctorId: null,
        startTime: null,
        endTime: null
      },
      pageParams: {},
      tableData: [],
      belongParts: {},
      orderStatusInfo: [
        {
          id: 0,
          text: '待付款'
        },
        {
          id: 1,
          text: '待接诊'
        },
        {
          id: 2,
          text: '接诊中'
        },
        {
          id: 3,
          text: '已完成'
        },
        {
          id: 4,
          text: '已退款'
        },
        {
          id: 5,
          text: '已取消'
        },
        {
          id: 6,
          text: '待退款'
        },
        {
          id: 7,
          text: '部分退款'
        }
      ],
      showRefund: false,
      refundInfo: null
    }
  },
  methods: {
    toDetail (orderSn) {
      const { href } = this.$router.resolve({
        path: '/admin/home/main/orders/advisoryOrder/info',
        query: {
          orderSn: orderSn
        }
      })
      window.open(href, '_blank')
    },
    // 获取科室
    initDoctorPart () {
      let params = {}
      getBelongParts(params).then(res => {
        this.belongParts = res.content
      })
    },
    // 数据
    initDataList () {
      this.loading = true
      advisoryOrderList(Object.assign(this.queryParams, this.pageParams)).then((res) => {
        console.log(res)
        let oriData = JSON.parse(JSON.stringify(res.content.dataList))
        for (let i in oriData) {
          oriData[i].orderStatusName = this.getStatusName(oriData[i].orderStatus)
          oriData[i].settlementName = this.getSettlementStatus(oriData[i].settlementFlag)
          oriData[i].totalRebateMoney = oriData[i].totalRebateMoney.toFixed(2)
        }
        this.tableData = oriData
        this.pageParams = res.content.page
        this.loading = false
      })
    },
    // 订单状态
    getStatusName (data) {
      switch (data) {
        case 0:
          return '待付款'
        case 1:
          return '待接诊'
        case 2:
          return '接诊中'
        case 3:
          return '已完成'
        case 4:
          return '已退款'
        case 5:
          return '已取消'
        case 6:
          return '待退款'
        case 7:
          return '部分退款'
      }
    },
    // 返利状态
    getSettlementStatus (data) {
      switch (data) {
        case 0:
          return '待返利'
        case 1:
          return '已返利'
        case 2:
          return '未返利'
      }
    },
    returnOrder ({ refundMoney, orderAmount, orderSn }) {
      this.refundInfo = { orderAmount, refundMoney, orderSn }
      this.showRefund = true
    },
    datePickerChange (isStart, target) {
      if (target.startTime === null || target.endTime === null) {
        return
      }
      if (new Date(target.startTime).getTime() <= new Date(target.endTime).getTime()) {
        return
      }
      if (isStart) {
        target.startTime = null
      } else {
        target.endTime = null
      }
    },
    orderHandler (orderSn) {
      const { href } = this.$router.resolve({
        path: '/admin/home/main/orders/advisoryOrder/info',
        query: {
          orderSn: orderSn
        }
      })
      window.open(href, '_blank')
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
    }
  }
}
</script>

<style scoped lang='scss'>
.main1 {
  margin: 10px;
  min-width: auto !important;
  .nav_box {
    display: flex;
    width: 100%;
    background-color: #fff;
    padding: 10px 15px;
    .filters {
      flex: 2;
      display: flex;
      flex-wrap: wrap;
      line-height: 32px;
      margin-left: -15px;
      .filters_item {
        display: flex;
        justify-content: flex-end;
        margin: 10px 0;
        margin-left: 15px;
        > span {
          width: 80px;
          font-size: 14px;
          text-align: right;
        }
        .timeSelect {
          width: 140px;
          margin: 0 10px 0 10px;
        }
      }
    }
  }
  .table_box {
    padding: 10px;
    background: #fff;
    .operation a {
      color: #5a8bff;
      text-decoration: none;
    }
  }
  .btn_wrap .el-button {
    margin: 10px 40px;
  }
  .linkStyle {
    color: #5a8bff;
    cursor: pointer;
  }
}
</style>
