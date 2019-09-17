<template>
  <div class="content">
    <div class="main">
      <div class="search_box">
        <div class="filters">
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
            <span>支付时间：</span>
            <el-date-picker
              v-model="searchParams.applicationTime"
              type="daterange"
              range-separator="至"
              start-placeholder="支付时间"
              end-placeholder="支付时间"
              value-format="yyyy-MM-dd"
              size="small"
            >
            </el-date-picker>
          </div>
          <div class="filters_item">
            <span>买单人：</span>
            <el-input
              v-model="searchParams.userName"
              placeholder="请输入买单人昵称"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>门店：</span>
            <el-select
              v-model="searchParams.storeId"
              size="small"
              class="default_input"
              filterable
              clearable
            >
              <el-option
                v-for="item in storeList"
                :key="item.storeId"
                :label="item.name"
                :value="item.storeId"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <span>订单状态：</span>
            <el-select
              v-model="searchParams.orderStatus"
              size="small"
              class="default_input"
              filterable
              clearable
            >
              <el-option
                label="已支付"
                value="1"
              ></el-option>
              <el-option
                label="已退款"
                value="2"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <el-button
              type="primary"
              size="small"
            >筛选</el-button>
          </div>
        </div>
      </div>
      <div class="table_box">
        <table>
          <thead>
            <tr>
              <th width="300px">买单门店</th>
              <th>买单人</th>
              <th>买单时间</th>
              <th>订单状态</th>
              <th>买单收银金额</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td colspan="5"></td>
            </tr>
          </tbody>
          <template v-for="orderItem in checkOrderList">
            <tbody
              :key="orderItem.orderId"
              class="hasborder"
            >
              <tr class="order-tb-head">
                <td colspan="8">
                  <div class="tb-head_box">
                    <div class="left">
                      <span>支付单号：{{orderItem.OrderSn}}</span>
                      <span>支付方式：{{orderItem.payType}}</span>
                    </div>
                    <div class="right">
                      <span class="icon_collect"><i class="el-icon-star-off"></i></span>
                      <span @click="addNodes">添加备注</span>
                      <span @click="seeDetails(orderItem.OrderSn)">查看详情</span>
                    </div>
                  </div>
                </td>
              </tr>
              <tr class="order-tb-body">
                <td>{{orderItem.storeName}}</td>
                <td>{{orderItem.userName}}</td>
                <td>{{orderItem.createTime}}</td>
                <td>{{orderItem.orderStatus}}</td>
                <td>{{orderItem.moneypaid}}</td>
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
    <nodesDialog :show.sync="showNodes" />
  </div>
</template>

<script>
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination'),
    nodesDialog: () => import('../addNotes')
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
        orderSn: null,
        userName: null,
        storeId: null,
        orderStatus: null,
        payTimeStart: '',
        payTimeEnd: '',
        applicationTime: null
      },
      storeList: [
        { storeId: 1, name: '牡丹园门店' },
        { storeId: 2, name: '西直门门店' },
        { storeId: 3, name: '东直门门店' },
        { storeId: 4, name: '天安门门店' }
      ],
      checkOrderList: [
        { orderId: 1, OrderSn: '1231231313', payType: '微信支付', storeName: '牡丹园门店', userName: '奔跑的蜗牛', createTime: '2019-08-12 11:11:11', orderStatus: '已支付', moneypaid: '0' },
        { orderId: 2, OrderSn: '1231231313', payType: '微信支付', storeName: '牡丹园门店', userName: '奔跑的蜗牛', createTime: '2019-08-12 11:11:11', orderStatus: '已支付', moneypaid: '0' },
        { orderId: 3, OrderSn: '1231231313', payType: '微信支付', storeName: '牡丹园门店', userName: '奔跑的蜗牛', createTime: '2019-08-12 11:11:11', orderStatus: '已支付', moneypaid: '0' },
        { orderId: 4, OrderSn: '1231231313', payType: '微信支付', storeName: '牡丹园门店', userName: '奔跑的蜗牛', createTime: '2019-08-12 11:11:11', orderStatus: '已支付', moneypaid: '0' },
        { orderId: 5, OrderSn: '1231231313', payType: '微信支付', storeName: '牡丹园门店', userName: '奔跑的蜗牛', createTime: '2019-08-12 11:11:11', orderStatus: '已支付', moneypaid: '0' },
        { orderId: 6, OrderSn: '1231231313', payType: '微信支付', storeName: '牡丹园门店', userName: '奔跑的蜗牛', createTime: '2019-08-12 11:11:11', orderStatus: '已支付', moneypaid: '0' }
      ],
      showNodes: false
    }
  },
  methods: {
    seeDetails (OrderSn) {
      this.$router.push({
        name: 'checkOrderInfo',
        query: {
          OrderSn: OrderSn
        }
      })
    },
    addNodes () {
      console.log(123)
      this.showNodes = true
    }
  }
}
</script>

<style lang="scss" scoped>
.content {
  padding: 10px;
  .main {
    .search_box {
      background-color: #fff;
      padding: 10px 10px 0 0;
      .filters {
        display: flex;
        line-height: 32px;
        flex-wrap: wrap;
        // max-width: 1226px;
        .filters_item {
          display: flex;
          max-width: 480px;
          min-width: 320px;
          margin-left: 15px;
          margin-bottom: 10px;
          /deep/ .areaLinkage {
            .el-select {
              margin-left: 10px;
              &:first-of-type {
                margin-left: 0;
              }
            }
          }
          > span {
            min-width: 100px;
            font-size: 14px;
            text-align: right;
          }
        }
      }
      .default_input {
        width: 150px;
      }
    }
    .table_box {
      margin-top: 10px;
      background-color: #fff;
      padding: 10px;
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
            .goods_info {
              display: flex;
              padding: 8px 10px;
              border-bottom: 1px solid #eee;
              &:last-of-type {
                border-bottom: 0;
              }
              > img {
                width: 60px;
                height: 60px;
                margin-right: 5px;
              }
              > .right_info {
                flex: 1;
                display: flex;
                flex-direction: column;
                text-align: left;
                justify-content: space-between;
                .goods_name {
                  overflow: hidden;
                  text-overflow: ellipsis;
                  display: -webkit-box;
                  -webkit-line-clamp: 2;
                  overflow: hidden;
                  /*! autoprefixer: off */
                  -webkit-box-orient: vertical;
                  text-align: left;
                }
              }
            }
            .goods_sn,
            .goods_number,
            .goods_price {
              display: block;
              border-bottom: 1px solid #eee;
              word-break: break-all;
              height: 77px;
              position: relative;
              > span {
                overflow: hidden;
                text-overflow: ellipsis;
                display: -webkit-box;
                -webkit-line-clamp: 4;
                overflow: hidden;
                /*! autoprefixer: off */
                -webkit-box-orient: vertical;
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
              }
              &:last-of-type {
                border-bottom: 0;
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
  }
}
</style>
