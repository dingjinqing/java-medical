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
            v-model="searchParams.applicationTime"
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
      v-model="refundType"
      @tab-click="handleClick"
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
        <thead>
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
              <td>{{orderItem.cardName}}</td>
              <td>{{orderItem.cardNo}}</td>
              <td>{{orderItem.moneyPaid}}</td>
              <td>{{orderItem.username}}<br />{{orderItem.mobile}}</td>
              <td>{{orderItem.payTime}}</td>
              <td>{{orderItem.returnFlag | returnFlagType}}</td>
              <td>{{orderItem.moneyPaid}}</td>
            </tr>
          </tbody>
          <tbody
            :key="orderItem.orderId"
            v-if="index != memberCardOrderList.length - 1"
          >
            <tr>
              <td colspan="8"></td>
            </tr>
          </tbody>
        </template>
      </table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />
    </div>
  </div>
</template>

<script>
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      pageParams: {
        'totalRows': 4,
        'currentPage': 1,
        'firstPage': 1,
        'prePage': 1,
        'nextPage': 1,
        'lastPage': 1,
        'pageRows': 20,
        'pageCount': 1
      },
      searchParams: {
        userInfo: null,
        orderSn: null,
        cardNo: null,
        cardType: null,
        applicationTime: null,
        refundType: '0',
        refund: false
      },
      cardTypeList: [
        { value: null, label: '请选择会员卡类型' },
        { value: 1, label: '普通会员卡' },
        { value: 2, label: '限次会员卡' }
      ],
      memberCardOrderList: [
        {
          'orderId': 1,
          'orderSn': 'M201908011052262504',
          'cardType': 0,
          'cardNo': '111222333',
          'cardName': '测试用会员卡',
          'moneyPaid': 10,
          'useAccount': 0,
          'useScore': 0,
          'payFee': 0,
          'payTime': '2019-08-01 05:29:01',
          'payType': 0,
          'username': '测试虚拟商品用户',
          'mobile': '15901408256',
          'returnFlag': 1,
          'returnTime': '2018-12-28 14:55:28'
        },
        {
          'orderId': 2,
          'orderSn': 'M201908011052262504',
          'cardType': 0,
          'cardNo': '111222333',
          'cardName': '测试用会员卡',
          'moneyPaid': 10,
          'useAccount': 0,
          'useScore': 0,
          'payFee': 0,
          'payTime': '2019-08-01 05:29:01',
          'payType': 0,
          'username': '测试虚拟商品用户',
          'mobile': '15901408256',
          'returnFlag': 2,
          'returnTime': '2018-12-28 14:55:28'
        },
        {
          'orderId': 3,
          'orderSn': 'M201908011052262504',
          'cardType': 0,
          'cardNo': '111222333',
          'cardName': '测试用会员卡',
          'moneyPaid': 10,
          'useAccount': 0,
          'useScore': 0,
          'payFee': 0,
          'payTime': '2019-08-01 05:29:01',
          'payType': 0,
          'username': '测试虚拟商品用户',
          'mobile': '15901408256',
          'returnFlag': 0,
          'returnTime': '2018-12-28 14:55:28'
        }
      ]
    }
  },
  methods: {
    handleClick (el) {
      if (el.name === '0') {
        this.searchParams.refund = false
      } else {
        this.searchParams.refund = true
      }
    },
    initDataList () {

    }
  },
  filters: {
    returnFlagType (val) {
      switch (val) {
        case 0:
          return `订单完成`
        case 1:
          return `已退款`
        case 2:
          return `退款失败`
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
