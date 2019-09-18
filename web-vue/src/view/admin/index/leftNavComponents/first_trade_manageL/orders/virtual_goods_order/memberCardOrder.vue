<template>
  <div>
    <div class="search_box">
      <div class="filters">
        <div class="filters_item">
          <span>下单用户信息：</span>
          <el-input
            v-model="searchParams.userInfo"
            placeholder="请输入姓名/手机号"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>订单号：</span>
          <el-input
            v-model="searchParams.orderSn"
            placeholder="请输入订单号"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>会员卡号：</span>
          <el-input
            v-model="searchParams.cardNo"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>会员卡类型：</span>
          <el-select
            v-model="searchParams.cardType"
            size="small"
            class="default_input"
            filterable
          >
            <el-option
              v-for="item in cardTypeList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </div>
        <div class="filters_item">
          <span>下单时间：</span>
          <el-date-picker
            v-model="applicationTime"
            type="datetimerange"
            range-separator="至"
            value-format="yyyy-MM-dd HH:mm:ss"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            size="small"
          >
          </el-date-picker>
        </div>
        <div class="filters_item">
          <el-button
            @click="initDataList"
            type="primary"
            size="small"
          >筛选</el-button>
          <el-button
            type="default"
            size="small"
          >导出表格</el-button>
        </div>
      </div>
    </div>
    <el-tabs
      @tab-click="handleClick"
      v-model="searchParams.refundType"
    >
      <el-tab-pane
        label="全部"
        name="0"
      >
      </el-tab-pane>
      <el-tab-pane
        label="退款订单"
        name="1"
      ></el-tab-pane>
    </el-tabs>
    <div class="table_box">
      <table>
        <thead v-loading="loading">
          <tr>
            <th width="150px">商品</th>
            <th>会员卡号</th>
            <th>单价</th>
            <th>下单用户信息</th>
            <th>下单时间</th>
            <th>订单状态</th>
            <th>支付金额</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td colspan="7"></td>
          </tr>
        </tbody>
        <template v-for="(orderItem, index) in memberCardOrderList">
          <tbody
            :key="orderItem.orderId"
            class="hasborder"
          >
            <tr class="order-tb-head">
              <td colspan="8">
                <div class="tb-head_box">
                  <div class="left">
                    <span>订单编号：{{orderItem.orderSn}}</span>
                  </div>
                </div>
              </td>
            </tr>
            <tr class="order-tb-body">
              <td class="card_name">
                <el-tag
                  size="mini"
                  effect="plain"
                  :type="orderItem.cardType === 1 ? 'warning':'danger'"
                >{{orderItem.cardType === 1 ? '限次会员卡':'普通会员卡'}}</el-tag>{{orderItem.cardName}}
              </td>
              <td>{{orderItem.cardNo}}</td>
              <td>{{orderItem.moneyPaid}}</td>
              <td> <a
                  class="user_info"
                  @click="viewUserDetail(orderItem.userId)"
                >{{orderItem.username}}<br />{{orderItem.mobile}}</a> </td>
              <td>{{orderItem.payTime}}</td>
              <td
                v-html="returnFlagType(orderItem.orderSn)"
                @click="processRefunds(orderItem.orderSn,$event)"
                class="refund_status"
              ></td>
              <td>{{orderItem.moneyPaid}}</td>
            </tr>
          </tbody>
          <tbody
            :key="index - 1"
            v-if="index != memberCardOrderList.length - 1"
          >
            <tr>
              <td colspan="8"></td>
            </tr>
          </tbody>
        </template>
      </table>
      <Pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />
    </div>
    <ManualRefund
      :dataInfo="refundInfo"
      :show.sync="showRefund"
    />
  </div>
</template>

<script>
import { getMemberCardOrderList } from '@/api/admin/orderManage/virtualGoodsOrder.js'
export default {
  components: {
    Pagination: () => import('@/components/admin/pagination/pagination'),
    ManualRefund: () => import('./refundDialog')
  },
  data () {
    return {
      loading: false,
      showRefund: false,
      refundInfo: null,
      pageParams: {},
      searchParams: {},
      cardTypeList: [
        { value: null, label: '请选择会员卡类型' },
        { value: 1, label: '普通会员卡' },
        { value: 2, label: '限次会员卡' }
      ],
      memberCardOrderList: [],
      applicationTime: '',

      // 原始表格数据
      originalData: []
    }
  },
  mounted () {
    this.initDataList()
  },
  methods: {
    handleClick (el) {
      if (el.name === '0') {
        this.searchParams.refund = false
      } else {
        this.searchParams.refund = true
      }
      this.initDataList()
    },
    initDataList () {
      this.loading = true
      this.searchParams.startTime = this.applicationTime[0]
      this.searchParams.endTime = this.applicationTime[1]
      this.searchParams.currentPage = this.pageParams.currentPage
      this.searchParams.pageRows = this.pageParams.pageRows
      getMemberCardOrderList(this.searchParams).then((res) => {
        if (res.error === 0) {
          this.originalData = res.content.dataList
          let originalData = JSON.parse(JSON.stringify(this.originalData))
          this.handleData(originalData)
          this.pageParams = res.content.page
          this.loading = false
        }
      })
    },
    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        // this.$set(this.memberCardOrderList, index, item)
      })
      this.memberCardOrderList = data
    },
    viewUserDetail (userId) {
      this.$router.push({
        name: 'membershipInformation',
        query: {
          userId: userId
        }
      })
    },
    returnFlagType (orderSn) {
      let orderInfo = this.memberCardOrderList.find(item => {
        return item.orderSn === orderSn
      })
      if (orderInfo.returnFlag === 0 && (orderInfo.moneyPaid + orderInfo.useAccount + orderInfo.useScore > 0)) {
        return `<div>订单完成<br/><a class="refund" >手动退款</a></div>`
      } else if (orderInfo.returnFlag === 0) {
        return `<div>订单完成<div/>`
      } else if (orderInfo.returnFlag === 1 && (orderInfo.moneyPaid + orderInfo.useAccount + orderInfo.useScore > orderInfo.returnScore + orderInfo.returnAccount + orderInfo.returnMoney)) {
        return `<div><a class="refund">手动退款</a><br/><a class="view">查看退款</a></div>`
      } else if (orderInfo.returnFlag === 1) {
        return `<div>退款失败</div>`
      } else {
        return `<div>退款完成<br/> <a class="view">查看退款</a></div>`
      }
    },
    processRefunds (orderSn, event) {
      this.refundInfo = this.memberCardOrderList.find(item => {
        return item.orderSn === orderSn
      })
      this.$set(this.refundInfo, 'viewOrderType', 'card')
      if (event.target.className === 'view') {
        this.$set(this.refundInfo, 'action', 'view')
        this.showRefund = true
      } else if (event.target.className === 'refund') {
        this.$set(this.refundInfo, 'action', 'refund')
        this.showRefund = true
      }
    }
  },
  filters: {
    returnFlagType (val) {
      switch (val) {
        case 0:
          return `订单完成`
        case 1:
          return `退款失败`
        case 2:
          return `退款成功`
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.table_box {
  background-color: #fff;
  padding-right: 1px;
  table {
    width: 100%;
    margin-top: 15px;
    &:first-of-type {
      margin-top: 0;
    }
    > thead {
      > tr {
        background: #f5f5f5;
        > th {
          text-align: center;
          padding: 8px 0;
          font-size: 14px;
          color: #333;
          font-weight: 600;
        }
      }
    }
    > tbody {
      .order-tb-head {
        background: #f5f5f5;
      }
      > tr {
        > td {
          text-align: center;
          font-size: 14px;
          padding: 8px 10px;
          word-break: break-all;
          .tb-head_box {
            display: flex;
            line-height: 24px;
            padding: 0 10px;
            .left {
              flex: 1;
              display: flex;
              font-size: 14px;
              color: #666;
              justify-content: space-between;
            }
            .right {
              width: 265px;
              margin-left: 200px;
              display: flex;
              justify-content: space-between;
              color: #409eff;
              font-size: 14px;
              > span {
                cursor: pointer;
              }
              .icon_collect {
                font-size: 20px;
                .el-icon-star-off {
                  color: #666;
                }
                .el-icon-star-on {
                  color: red;
                }
              }
            }
          }
        }
      }
      .order-tb-body {
        td {
          vertical-align: middle;
          color: #666;
          line-height: 24px;
          &.card_name {
            text-align: left;
            /deep/ .el-tag {
              margin-right: 5px;
            }
          }
          .user_info {
            color: #409eff;
            cursor: pointer;
          }
          /deep/ &.refund_status {
            .view,
            .refund {
              color: #409eff;
              cursor: pointer;
            }
          }
        }
      }
    }
    .hasborder {
      border: 1px solid #eee;
      td {
        border: 1px solid #eee;
      }
    }
  }
}
</style>
