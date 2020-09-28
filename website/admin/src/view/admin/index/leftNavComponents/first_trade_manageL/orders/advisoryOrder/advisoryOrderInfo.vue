<template>
  <div class="main1">
    <div class="since-info">
      <div class="since-info-top">
        <div class="order_mes">
          <span>订单号：{{ orderContent.orderSn }}</span>
          <span>订单状态：{{ orderContent.orderStatusName }}</span>
        </div>
        <el-button
          type="primary"
          size="small"
          v-if="orderContent.orderStatus >= 3"
          @click="viewRecord"
          >咨询详情</el-button
        >
        <el-button
          type="primary"
          size="small"
          v-if="
            orderContent.orderAmount - orderContent.refundMoney > 0 &&
            orderContent.orderStatus !== 0 &&
            orderContent.orderStatus !== 5
          "
          @click="returnOrder"
          >手动退款</el-button
        >
      </div>
      <div class="since-info-detail">
        <div class="order_info">
          <div class="title">订单信息</div>
          <div class="item_box">
            <div class="item">订单状态：{{ orderContent.orderStatusName }}</div>
            <div class="item">订单金额：{{ orderContent.orderAmount }}</div>
            <div class="item">下单时间：{{ orderContent.createTime }}</div>
            <div class="item">用户：{{ orderContent.userName }}</div>
            <div class="item">订单号：{{ orderContent.orderSn }}</div>
            <div class="item">下单人手机：{{ orderContent.userMobile }}</div>
          </div>
        </div>
        <div class="user_info">
          <div class="title">患者信息</div>
          <div class="item_box">
            <div class="item">姓名：{{ orderContent.patientName }}</div>
            <div class="item">性别：{{ orderContent.patientSexName }}</div>
            <div class="item">生日：{{ orderContent.patientBirthday }}</div>
            <div class="item">
              证件类型：{{ orderContent.patientIdentityName }}
            </div>
            <div class="item">
              证件号码：{{ orderContent.patientIdentityCode }}
            </div>
            <div class="item">手机号：{{ orderContent.patientMobile }}</div>
          </div>
        </div>
      </div>
      <div
        class="order_mes"
        v-if="orderContent.refundList && orderContent.refundList.length"
      >
        <span>退款详情</span>
      </div>
      <el-table
        v-if="orderContent.refundList && orderContent.refundList.length"
        :data="orderContent.refundList"
        header-row-class-name="tableClss"
        border
        style="width: 100%"
      >
        <el-table-column prop="moneyAmount" label="已退金额" align="center">
        </el-table-column>
        <el-table-column prop="refundReason" label="退款原因" align="center">
        </el-table-column>
        <el-table-column prop="refundTime" label="退款时间" align="center">
        </el-table-column>
      </el-table>
      <div class="order_mes" v-if="orderContent.rebate">
        <span>返利详情</span>
      </div>
      <el-table
        v-if="orderContent.rebate"
        :data="[orderContent.rebate]"
        header-row-class-name="tableClss"
        border
        style="width: 100%"
      >
        <el-table-column prop="totalMoney" label="咨询费总额" align="center">
        </el-table-column>
        <el-table-column
          prop="totalRebateMoney"
          label="返利金额"
          align="center"
        >
        </el-table-column>
        <el-table-column prop="status" label="返利状态" align="center">
          <template slot-scope="scope">
            <div>
              {{ scope.row.status | getStatus }}
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="rebateTime" label="返利日期" align="center">
        </el-table-column>
      </el-table>
    </div>
    <ManualRefund
      :dataInfo="refundInfo"
      :show.sync="showRefund"
      @complete="initOrderInfo(id)"
    />
  </div>
</template>

<script>
import { advisoryOrderInfo } from '@/api/admin/orderManage/order.js'
import ManualRefund from './returnAdvisoryDialog'
export default {
  components: {
    ManualRefund
  },
  mounted () {
    if (this.$route.query.orderId) {
      this.id = this.$route.query.orderId
      this.initOrderInfo(this.id)
    }
  },
  watch: {
    '$route.query.orderId': function (newVal) {
      if (newVal) {
        this.id = this.$route.query.orderId
        this.initOrderInfo(this.id)
      }
    }
  },
  data () {
    return {
      orderContent: {},
      showRefund: false,
      refundInfo: null
    }
  },
  methods: {
    initOrderInfo (id) {
      advisoryOrderInfo({ orderId: id }).then(res => {
        if (res.error === 0) {
          res.content.orderStatusName = this.getStatusName(res.content.orderStatus)
          res.content.patientSexName = this.getPatientSex(res.content.patientSex)
          res.content.patientIdentityName = this.getIdentityName(res.content.patientIdentityType)
          this.orderContent = res.content
          console.log(this.orderContent)
        } else {
          this.$message.error({
            message: '获取失败',
            showClose: true
          })
        }
      })
    },
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
    getPatientSex (data) {
      switch (data) {
        case 0:
          return '男'
        case 1:
          return '女'
        case 2:
          return '未知'
      }
    },
    getIdentityName (data) {
      switch (data) {
        case 1:
          return '身份证'
        case 2:
          return '军人证'
        case 3:
          return '护照'
        case 4:
          return '社保卡'
      }
    },
    returnOrder () {
      let { orderAmount, refundMoney, orderSn } = this.orderContent
      this.refundInfo = { orderAmount, refundMoney, orderSn }
      this.showRefund = true
    },
    viewRecord () {
      const { href } = this.$router.resolve({
        name: 'advisoryRecord',
        query: {
          orderSn: this.orderContent.orderSn
        }
      })
      window.open(href, '_blank')
    }
  },
  filters: {
    getStatus (status) {
      let statusName = {
        0: '待返利',
        1: '已返利',
        2: '未返利'
      }
      return statusName[status]
    }
  }
}
</script>

<style lang='scss' scoped>
.main1 {
  margin: 10px;
  min-width: auto !important;
  .since-info {
    background-color: #fff;
    padding: 0 25px;
    font-size: 14px;
    overflow: hidden;
    .since-info-top {
      display: flex;
      align-items: center;
      height: 60px;
      color: #333;
      .order_mes {
        margin-right: auto;
        span {
          margin-right: 60px;
        }
      }
    }
    .since-info-detail {
      display: flex;
      margin-left: -30px;
      margin-bottom: 10px;
      > div {
        margin-left: 30px;
        border: 1px solid #cfd6ff;
        flex: 1;
        padding: 0 30px;
        > .title {
          margin-top: 10px;
          font-weight: 600;
          color: #333;
          font-size: 14px;
          margin-bottom: 10px;
        }
        > .item_box {
          display: flex;
          justify-content: space-between;
          flex-wrap: wrap;
          line-height: 30px;
          color: #666;
          > .item {
            min-width: 210px;
          }
        }
      }
    }
    .return-info {
      margin-bottom: 10px;
    }
    /deep/ .tableClss th {
      background-color: #f5f5f5;
      border: none;
      height: 36px;
      font-weight: bold;
      color: #000;
      padding: 8px 10px;
    }

    /deep/ .el-table {
      margin: 10px 0;
    }
  }
}
</style>
