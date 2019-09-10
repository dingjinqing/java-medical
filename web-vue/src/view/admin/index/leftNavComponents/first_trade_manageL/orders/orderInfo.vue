<template>
  <div class="main">
    <div class="since-info">
      <div class="since-info-top">
        <span>订单号：P201909051016068533</span>
        <span>订单状态：待发货</span>
      </div>
      <div class="since-info-path">
        <el-steps
          :active="2"
          align-center
        >
          <el-step
            title="买家已下单"
            description="2019-09-05 10:16:06"
          ></el-step>
          <el-step
            title="买家已付款"
            description="2019-09-05 10:16:06"
          ></el-step>
          <el-step title="卖家已发货"></el-step>
          <el-step title="订单完成"></el-step>
        </el-steps>
      </div>
      <div class="since-info-detail">
        <div class="order_info">
          <div class="title">订单信息</div>
          <div class="item_box">
            <div class="item">订单状态： 已发货</div>
            <div class="item">订单金额： 0.00元</div>
            <div class="item">下单时间：2019-09-05 10:16:06</div>
            <div class="item">配送方式：快递</div>
            <div class="item">支付方式：</div>
            <div class="item">订单号：P201909051016068533</div>
            <div class="item">下单人昵称：分隔符</div>
            <div class="item">下单人手机号：</div>
            <div class="item">发货时间：2019-09-05 10:16:17</div>
          </div>

        </div>
        <div class="user_info">
          <div class="title">用户信息</div>
          <div class="item_box">
            <div class="item">收货客户：XXXX</div>
            <div class="item">手机号码：XXXXXXXXXXX</div>
            <div class="item">收货地址：北京市北京市海淀区伊泰大厦(海淀区西直门北大街54号)</div>
            <div class="item">买家留言：</div>
          </div>
        </div>
      </div>
      <div class="order-remark">
        <i class="icon el-icon-edit-outline"></i>
        <span>买家备注：</span>
      </div>
    </div>
    <div class="order-list-table">
      <div class="title">快递信息</div>
      <template v-for="orderInfo in dataList.childOrders">
        <div
          class="table_box"
          :key="orderInfo.orderId"
        >
          <table width="100%">
            <thead>
              <tr>
                <th width="300px">商品名称</th>
                <th width="100px">规格</th>
                <th width="100px">发货数量</th>
                <th>物流公司</th>
                <th>物流单号</th>
                <th>发货时间</th>
                <th>确认收货时间</th>
              </tr>

            </thead>
            <template v-for="(item,index1) in orderInfo.shippingList">
              <tbody :key="index1">
                <tr
                  v-for="(shipGoods , index) in item.goods"
                  :key="shipGoods.recId"
                >
                  <td>{{shipGoods.goodsName}}</td>
                  <td>{{shipGoods.goodsAttr}}</td>
                  <td>{{shipGoods.sendNumber}}</td>
                  <td
                    v-if="index === 0"
                    :rowspan="item.goods.length"
                  >{{item.shippingName}}</td>
                  <td
                    v-if="index === 0"
                    :rowspan="item.goods.length"
                  >{{item.shippingNo}}</td>
                  <td
                    v-if="index === 0"
                    :rowspan="item.goods.length"
                  >{{item.shippingTime}}</td>
                  <td
                    v-if="index === 0"
                    :rowspan="item.goods.length"
                  >{{item.confirmTime}}</td>
                </tr>
              </tbody>
            </template>

          </table>
        </div>
      </template>
    </div>
    <div class="order-list-table">
      <div class="title">退货/退款信息</div>
    </div>
    <div class="order-list-table">
      <div class="table_box">
        <table width="100%">
          <thead>
            <thead>
              <tr>
                <th width="270px">商品</th>
                <th width="100px">货号</th>
                <th width="100px">单价</th>
                <th width="100px">数量</th>
                <th>收货人信息</th>
                <th>下单时间</th>
                <th>订单状态</th>
                <th>支付金额</th>
              </tr>
              <tr class="jiange">
                <th colspan="8"></th>
              </tr>
            </thead>
          <tbody>
            <template v-for="childrenOrder in dataList.childOrders">
              <tr
                class="order-tb-head"
                :key="childrenOrder.orderId"
              >
                <td colspan="8">
                  <div class="tb-head_box">
                    <div class="left">
                      <span>订单号：{{childrenOrder.orderSn}}</span>
                      <span>支付方式：<i class="el-icon-shopping-cart-full"></i></span>
                      <span>配送方式：快递</span>
                    </div>
                    <div class="right">
                      <span class="icon_collect"><i class="el-icon-star-off"></i></span>
                      <span>添加备注</span>
                      <span>手动退款退货</span>
                      <span>查看评价</span>
                    </div>
                  </div>
                </td>
              </tr>
              <tr
                class="order-tb-body"
                v-for="(goodsInfo,index) in childrenOrder.goods"
                :key="goodsInfo.recId"
              >
                <td>
                  <div class="goods_info">
                    <img
                      :src="$imageHost+'/image/admin/icon_jia.png'"
                      alt=""
                    >
                    <div class="right_info">
                      <div class="goods_name">{{goodsInfo.goodsName}}</div>
                      <div class="goods_spec">{{goodsInfo.goodsAttr}}</div>
                    </div>
                  </div>
                </td>
                <td>{{goodsInfo.goodsSn}}</td>
                <td>{{goodsInfo.goodsPrice}}</td>
                <td>{{goodsInfo.goodsNumber}}</td>
                <td
                  v-if="index == 0"
                  :rowspan="childrenOrder.goods.length"
                >
                  <p>{{childrenOrder.consignee}}</p>
                  <p>{{childrenOrder.mobile}}</p>
                  <p><span>收货地址</span></p>
                </td>
                <td
                  v-if="index == 0"
                  :rowspan="childrenOrder.goods.length"
                >{{childrenOrder.createTime}}</td>
                <td
                  v-if="index == 0"
                  :rowspan="childrenOrder.goods.length"
                >{{childrenOrder.orderStatus}}</td>
                <td
                  v-if="index == 0"
                  :rowspan="childrenOrder.goods.length"
                >123123123</td>
              </tr>
            </template>
            <tr>
              <td
                colspan="8"
                class="total_box"
              >
                <p>
                  商品金额：<span class="text-amount"></span>
                </p>
                <p>运费：</p>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="order-list-table">
      <div class="title">返利详情</div>
      <div class="table_box">
        <table width="100%">
          <thead>
            <tr>
              <th>分销员手机号</th>
              <th>分销员微信昵称</th>
              <th>分销员真实姓名</th>
              <th>商品名称</th>
              <th>返利商品总金额</th>
              <th>成本价</th>
              <th>商品可返利金额</th>
              <th>返利关系</th>
              <th>返利比例</th>
              <th>返利佣金金额</th>
              <th>返利状态</th>
              <th>返利日期</th>
            </tr>
          </thead>
          <tbody>
            <template>
              <tr></tr>
            </template>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      dataList: null
    }
  },
  created () {
    this.initData()
  },
  methods: {
    initData () {
      this.dataList = {
        'orderId': 1002,
        'orderSn': 'P201900000000000002',
        'mainOrderSn': 'P201900000000000002',
        'goodsType': '0',
        'childOrders': [
          {
            'orderId': 1004,
            'orderSn': 'P201900000000000004',
            'mainOrderSn': 'P201900000000000002',
            'goodsType': '0',
            'childOrders': null,
            'goods': [
              {
                'recId': 1008,
                'mainRecId': 1004,
                'orderId': 1004,
                'orderSn': 'P201900000000000004',
                'goodsId': 694,
                'goodsSn': 'G101010694',
                'goodsName': '首单限时优化-CJ111',
                'goodsNumber': 1,
                'goodsPrice': 55,
                'goodsAttr': '颜色:白色',
                'productId': 4726,
                'goodsImg': 'http://mpdevimg2.weipubao.cn/upload/0/image/20190807/crop_0mf3fRQPXxNt1Kot.jpeg',
                'sendNumber': null,
                'discountedGoodsPrice': null
              }
            ],
            'orderStatus': 3,
            'consignee': '测试520',
            'mobile': '12345678910',
            'payCode': 'balance',
            'deliverType': 0,
            'createTime': '2019-08-26 14:02:46',
            'shippingFee': 22,
            'moneyPaid': 0,
            'partShipFlag': 1,
            'refundStatus': 4,
            'payTime': null,
            'shippingTime': '2019-09-04 18:46:30',
            'finishedTim': null,
            'closedTime': null,
            'cancelledTime': null,
            'confirmTime': null,
            'username': null,
            'completeAddress': '北京市北京市海淀区西直门北大街54号 伊泰大厦',
            'addMessage': '',
            'shippingList': [
              {
                'orderSn': 'P201900000000000004',
                'shippingNo': '11rrkkkkkkrrrr1',
                'shippingName': '',
                'shippingTime': '2019-09-04 18:46:29',
                'confirmTime': null,
                'goods': [
                  {
                    'recId': 50,
                    'goodsName': '首单限时优化-CJ111',
                    'goodsAttr': '颜色:白色',
                    'sendNumber': '1'
                  }
                ],
                'batchNo': 'P201900000000000004_20190904184629'
              }
            ],
            'refundList': null
          },
          {
            'orderId': 1005,
            'orderSn': 'P201900000000000005',
            'mainOrderSn': 'P201900000000000002',
            'goodsType': '0',
            'childOrders': null,
            'goods': [
              {
                'recId': 1010,
                'mainRecId': 1004,
                'orderId': 1005,
                'orderSn': 'P201900000000000005',
                'goodsId': 694,
                'goodsSn': 'G101010694',
                'goodsName': '首单限时优化-CJ111',
                'goodsNumber': 1,
                'goodsPrice': 55,
                'goodsAttr': '颜色:白色',
                'productId': 4726,
                'goodsImg': 'http://mpdevimg2.weipubao.cn/upload/0/image/20190807/crop_0mf3fRQPXxNt1Kot.jpeg',
                'sendNumber': null,
                'discountedGoodsPrice': null
              },
              {
                'recId': 1011,
                'mainRecId': 1005,
                'orderId': 1005,
                'orderSn': 'P201900000000000005',
                'goodsId': 694,
                'goodsSn': 'G101010694',
                'goodsName': '首单限时优化-CJ111',
                'goodsNumber': 1,
                'goodsPrice': 55,
                'goodsAttr': '颜色:白色',
                'productId': 4726,
                'goodsImg': 'http://mpdevimg2.weipubao.cn/upload/0/image/20190807/crop_0mf3fRQPXxNt1Kot.jpeg',
                'sendNumber': null,
                'discountedGoodsPrice': null
              }
            ],
            'orderStatus': 4,
            'consignee': '测试520',
            'mobile': '12345678910',
            'payCode': 'balance',
            'deliverType': 0,
            'createTime': '2019-08-26 14:02:46',
            'shippingFee': 22,
            'moneyPaid': 0,
            'partShipFlag': 0,
            'refundStatus': 5,
            'payTime': null,
            'shippingTime': '2019-09-04 18:48:53',
            'finishedTim': null,
            'closedTime': null,
            'cancelledTime': null,
            'confirmTime': null,
            'username': null,
            'completeAddress': '北京市北京市海淀区西直门北大街54号 伊泰大厦',
            'addMessage': '',
            'shippingList': [
              {
                'orderSn': 'P201900000000000005',
                'shippingNo': '11rrkkkkkkrrrr1',
                'shippingName': '',
                'shippingTime': '2019-09-04 18:48:52',
                'confirmTime': null,
                'goods': [
                  {
                    'recId': 52,
                    'goodsName': '首单限时优化-CJ111',
                    'goodsAttr': '颜色:白色',
                    'sendNumber': '1'
                  },
                  {
                    'recId': 51,
                    'goodsName': '首单限时优化-CJ111',
                    'goodsAttr': '颜色:白色',
                    'sendNumber': '1'
                  }
                ],
                'batchNo': 'P201900000000000005_20190904184852'
              }
            ],
            'refundList': [
              {
                'retId': 83,
                'orderId': 1005,
                'orderSn': 'P201900000000000005',
                'returnOrderSn': 'R201909041906318788',
                'returnType': '0',
                'refundStatus': 5,
                'money': 10,
                'apply': null,
                'success': null,
                'orderReturnGoodsVo': [
                  {
                    'recId': 1010,
                    'mainRecId': null,
                    'orderId': null,
                    'orderSn': 'P201900000000000005',
                    'goodsId': 694,
                    'goodsSn': null,
                    'goodsName': '首单限时优化-CJ111',
                    'goodsNumber': 1,
                    'goodsPrice': 55,
                    'goodsAttr': '颜色:白色',
                    'productId': 4726,
                    'goodsImg': 'http://mpdevimg2.weipubao.cn/upload/0/image/20190807/crop_0mf3fRQPXxNt1Kot.jpeg',
                    'sendNumber': 1,
                    'discountedGoodsPrice': 55,
                    'retId': 83,
                    'success': 2
                  }
                ]
              }
            ]
          }
        ],
        'goods': [],
        'orderStatus': 3,
        'consignee': '测试520',
        'mobile': '12345678910',
        'payCode': 'balance',
        'deliverType': 0,
        'createTime': '2019-08-26 14:02:46',
        'shippingFee': 22,
        'moneyPaid': 0,
        'partShipFlag': 0,
        'refundStatus': 4,
        'payTime': null,
        'shippingTime': null,
        'finishedTim': null,
        'closedTime': null,
        'cancelledTime': null,
        'confirmTime': null,
        'username': null,
        'completeAddress': '北京市北京市海淀区西直门北大街54号 伊泰大厦',
        'addMessage': '',
        'shippingList': null,
        'refundList': null
      }
      this.rebate_list = []
      console.log(this.dataList)
    },
    shipTableMethod ({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        return {
          colspan: 3,
          rowspan: 1
        }
      }
    },
    columnStyle ({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        return 'no_padding'
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.main {
  padding: 10px;
  > div {
    margin-bottom: 10px;
  }
  .since-info {
    background-color: #fff;
    padding: 0 25px;
    font-size: 14px;
    overflow: hidden;
    .since-info-top {
      display: flex;
      line-height: 44px;
      color: #333;
      > span {
        margin-right: 60px;
      }
    }
    .since-info-path {
      margin-bottom: 10px;
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
    .order-remark {
      font-size: 14px;
      color: #333;
      line-height: 50px;
      > .icon {
        font-size: 20px;
        color: #f8a039;
        vertical-align: middle;
        cursor: pointer;
      }
    }
  }
  .order-list-table {
    padding: 10px 25px 0;
    background-color: #fff;
    overflow: hidden;
    > .table_box {
      margin-bottom: 10px;
      > table {
        > thead tr {
          background: #f5f5f5;
          th {
            padding: 8px 10px;
            color: #333;
            font-size: 14px;
          }
        }
        > tbody {
          margin-bottom: 15px;
          border: 1px solid #eee;
          td {
            border: 1px solid #eee;
            color: #666;
            padding: 8px 10px;
            color: #666;
            font-size: 14px;
            text-align: center;
            vertical-align: middle;
          }
          .total_box {
            text-align: right;
            > p {
              line-height: 30px;
            }
          }
          .order-tb-head {
            background-color: #f5f5f5;
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
          .order-tb-body {
            .goods_info {
              display: flex;
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
          }
        }
        .jiange {
          background-color: #fff;
        }
      }
    }
  }
  .title {
    font-weight: 600;
    color: #333;
    font-size: 14px;
    margin-bottom: 10px;
  }
  .no_padding {
    padding: 0 !important;
  }
}
</style>
