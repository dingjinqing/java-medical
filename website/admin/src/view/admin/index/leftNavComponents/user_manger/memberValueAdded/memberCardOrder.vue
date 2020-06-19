<template>
  <div>
    <div class="search_box">
      <div class="filters">
        <div class="filters_item">
          <span>{{$t('orderCommon.orderUserInfo')}}：</span>
          <el-input
            v-model="searchParams.userInfo"
            :placeholder="$t('orderCommon.orderUserInfoPlaceholder')"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>{{$t('orderCommon.orderSn')}}：</span>
          <el-input
            v-model="searchParams.orderSn"
            :placeholder="$t('orderCommon.orderSnPlaceholder')"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>{{$t('memberCardOrder.memberCardSn')}}：</span>
          <el-input
            v-model="searchParams.cardNo"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>支付方式：</span>
          <el-select
            v-model="searchParams.payCode"
            :placeholder="$t('orderCommon.selectPlaceholder')"
            size="small"
            class="default_input"
          >
            <el-option
              label="全部"
              :value="null"
            ></el-option>
            <el-option
              label="微信支付"
              value="wxpay"
            ></el-option>
            <el-option
              label="余额支付"
              value="balance"
            ></el-option>
            <el-option
              label="积分支付支付"
              value="score"
            ></el-option>
          </el-select>
        </div>
        <div
          class="filters_item"
          style="max-width: 500px;"
        >
          <span>{{$t('orderCommon.orderTime')}}：</span>
          <el-date-picker
            v-model="applicationTime0"
            type="date"
            size="small"
            value-format="yyyy-MM-dd 00:00:00"
            :placeholder="$t('actionRecord.startTime')"
          >
          </el-date-picker>
          &nbsp;至&nbsp;
          <el-date-picker
            v-model="applicationTime1"
            type="date"
            size="small"
            value-format="yyyy-MM-dd 23:59:59"
            :placeholder="$t('actionRecord.endTime')"
          >
          </el-date-picker>
        </div>
        <div class="filters_item">
          <span>{{$t('memberCardOrder.memberCardType')}}：</span>
          <el-select
            v-model="searchParams.cardType"
            :placeholder="$t('orderCommon.selectPlaceholder')"
            size="small"
            class="default_input"
            filterable
          >
            <el-option
              label="请选择会员卡类型"
              :value="null"
            ></el-option>
            <el-option
              :label="$t('memberCardOrder.normalMemberCard')"
              :value="0"
            ></el-option>
            <el-option
              :label="$t('memberCardOrder.limitNumMemberCard')"
              :value="1"
            ></el-option>
          </el-select>
        </div>
        <div class="filters_item">
          <el-button
            @click="initDataList"
            type="primary"
            size="small"
          >{{$t('orderCommon.filter')}}</el-button>
          <el-button
            type="default"
            size="small"
            @click="exportDataList"
          >{{$t('orderCommon.exportTable')}}</el-button>
        </div>
      </div>
    </div>
    <el-tabs
      @tab-click="handleClick"
      v-model="searchParams.refundType"
    >
      <el-tab-pane
        :label="$t('orderCommon.all')"
        name="0"
      >
      </el-tab-pane>
      <el-tab-pane
        :label="$t('orderCommon.refundOrder')"
        name="1"
      ></el-tab-pane>
    </el-tabs>
    <div class="table_box">
      <table>
        <thead v-loading="loading">
          <tr style="height:47px;">
            <th width="150px">{{$t('orderCommon.goods')}}</th>
            <th>{{$t('memberCardOrder.memberCardSn')}}</th>
            <th>{{$t('orderCommon.price')}}</th>
            <th>{{$t('orderCommon.orderUserInfo')}}</th>
            <th>{{$t('orderCommon.orderTime')}}</th>
            <th>{{$t('orderCommon.orderStatus')}}</th>
            <th>{{$t('orderCommon.moneyPaid')}}</th>
          </tr>
        </thead>
        <tbody v-if="memberCardOrderList.length">
          <tr>
            <td colspan="7">
            </td>
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
                    <span>{{$t('orderCommon.orderCoding')}}:{{orderItem.orderSn}}</span>
                    <div class="payType">支付方式：
                      <el-tooltip
                        class="item"
                        effect="dark"
                        content="微信支付"
                        placement="top"
                      >
                        <img
                          :src="$imageHost+'/image/admin/wxpay.png'"
                          alt=""
                          v-if="orderItem.moneyPaid"
                        >
                      </el-tooltip>
                      <el-tooltip
                        class="item"
                        effect="dark"
                        content="余额支付"
                        placement="top"
                      >
                        <img
                          :src="$imageHost+'/image/admin/account.png'"
                          alt=""
                          v-if="orderItem.useAccount"
                        >
                      </el-tooltip>
                      <el-tooltip
                        class="item"
                        effect="dark"
                        content="积分支付"
                        placement="top"
                      >
                        <img
                          :src="$imageHost+'/image/admin/rewards_points.png'"
                          alt=""
                          v-if="orderItem.useScore"
                        >
                      </el-tooltip>

                    </div>
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
                >{{orderItem.cardType === 1 ? $t('memberCardOrder.limitNumMemberCard'):$t('memberCardOrder.normalMemberCard')}}</el-tag>{{orderItem.cardName}}
              </td>
              <td>{{orderItem.cardNo}}</td>
              <td>{{orderItem.price}}</td>
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
              <td>{{orderItem.newMoneyPaid}}</td>
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
      <!--无数据-->
      <div
        v-if="!memberCardOrderList.length"
        class="el-table__empty-block"
      ><span class="el-table__empty-text">暂无数据</span></div>
    </div>
    <ManualRefund
      :dataInfo="refundInfo"
      :show.sync="showRefund"
    />
    <!--导出数据弹窗-->
    <memberOrderExportDialog
      :show.sync="showExportConfirm"
      :param="this.searchParams"
    />
  </div>
</template>

<script>
import { getMemberCardOrderList } from '@/api/admin/orderManage/virtualGoodsOrder.js'
export default {
  components: {
    Pagination: () => import('@/components/admin/pagination/pagination'),
    ManualRefund: () => import('./refundDialog'),
    MemberOrderExportDialog: () => import('./memberOrderExportDialog') // 会员卡购买页面数据导出弹窗
  },
  data () {
    return {
      userId: null,
      loading: false,
      showRefund: false,
      refundInfo: null,
      pageParams: {},
      searchParams: {},
      memberCardOrderList: [],
      applicationTime: '',
      applicationTime0: '',
      applicationTime1: '',
      showExportConfirm: false, // 是否展示导出数据弹窗
      // 原始表格数据
      originalData: []
    }
  },
  watch: {
    lang () {
      // 重新渲染表格数据
      let originalData = JSON.parse(JSON.stringify(this.originalData))
      this.handleData(originalData)
    }
  },
  mounted () {
    if (this.$route.query.userId) {
      this.userId = this.$route.query.userId
    }
    this.langDefault()
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
      this.searchParams.startTime = this.applicationTime0
      this.searchParams.endTime = this.applicationTime1
      this.searchParams.currentPage = this.pageParams.currentPage
      this.searchParams.pageRows = this.pageParams.pageRows
      if (this.userId) {
        this.searchParams.userId = this.userId
      }
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
        if (item.useScore > 0) {
          item.price = item.useScore + this.$t('orderCommon.integral')
        } else {
          item.price = item.orderAmount + this.currencyPool[item.currency][this.lang][0]
        }
        item.newMoneyPaid = item.moneyPaid + this.currencyPool[item.currency][this.lang][0]
      })
      this.memberCardOrderList = data
    },
    viewUserDetail (userId) {
      console.log(userId)
      this.$router.push({
        name: 'membershipInformation',
        query: {
          userId: userId
        }
      })
    },
    returnFlagType (orderSn) {
      // else if (orderInfo.returnFlag === 1 && (orderInfo.moneyPaid + orderInfo.useAccount + orderInfo.useScore > orderInfo.returnScore + orderInfo.returnAccount + orderInfo.returnMoney)) {
      //   return `<div><a class="refund">${this.$t('orderCommon.manualRefund')}</a><br/><a class="view">${this.$t('orderCommon.checkRefund')}</a></div>`
      // }
      let orderInfo = this.memberCardOrderList.find(item => {
        return item.orderSn === orderSn
      })
      console.log(orderInfo.moneyPaid + orderInfo.useAccount + orderInfo.useScore)
      if (orderInfo.returnFlag === 0 && (orderInfo.moneyPaid + orderInfo.useAccount + orderInfo.useScore > 0)) {
        let statusStr = `<div>${this.$t('orderCommon.orderFinished')}`
        // if (orderInfo.canReturn === 1) statusStr += `<br/><a class="refund" >${this.$t('orderCommon.manualRefund')}</a>`
        statusStr += '</div>'
        return statusStr
      } else if (orderInfo.returnFlag === 0) {
        return `<div>${this.$t('orderCommon.orderFinished')}<div/>`
      } else if (orderInfo.returnFlag === 1) {
        return `<div>${this.$t('orderCommon.refundFailed')}</div>`
      } else {
        return `<div>${this.$t('orderCommon.refundCompleted')}<br/> <a class="view">${this.$t('orderCommon.checkRefund')}</a></div>`
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
    },
    exportDataList () {
      this.searchParams.startTime = this.applicationTime0
      this.searchParams.endTime = this.applicationTime1
      this.showExportConfirm = true
    }
  },
  filters: {
    returnFlagType (val) {
      switch (val) {
        case 0:
          return this.$t('orderCommon.orderFinished')
        case 1:
          return this.$t('orderCommon.refundFailed')
        case 2:
          return this.$t('orderCommon.refundCompleted')
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.search_box {
  background-color: #fff;
  .filters {
    display: flex;
    line-height: 32px;
    flex-wrap: wrap;
    margin-left: -15px;
    // max-width: 1226px;
    .filters_item {
      display: flex;
      max-width: 480px;
      min-width: 280px;
      margin-left: 15px;
      margin-bottom: 10px;
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
}
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
          padding: 15px 0;
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
              // justify-content: space-between;
              .payType {
                margin-left: 40px;
                display: flex;
                align-items: center;
                img {
                  margin-right: 5px;
                }
              }
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
.el-table__empty-block {
  width: 100%;
  display: flex;
  justify-content: center;
  border: 1px solid #ebeef5;
  .el-table__empty-text {
    line-height: 60px;
    width: 50%;
    color: #909399;
  }
}
</style>
