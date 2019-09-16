<template>
  <div class="content">
    <div class="main">
      <div class="search_box">
        <div class="filters">
          <div class="filters_item">
            <span>订单编号：</span>
            <el-input
              v-model="searchParams.orderSn"
              placeholder="订单编号"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>退款编号：</span>
            <el-input
              v-model="searchParams.refundSn"
              placeholder="退款编号"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>退款状态：</span>
            <el-select
              v-model="searchParams.refundStatus"
              size="small"
              class="default_input"
              filterable
            >
              <el-option
                v-for="item in refundStatus"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <span>退款类型：</span>
            <el-select
              v-model="searchParams.refundType"
              size="small"
              class="default_input"
              filterable
            >
              <el-option
                v-for="item in refundType"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <span>申请时间：</span>
            <el-date-picker
              v-model="searchParams.applicationTime"
              type="daterange"
              range-separator="至"
              start-placeholder="选择时间"
              end-placeholder="选择时间"
              value-format="yyyy-MM-dd"
              size="small"
              :picker-options="pickerOptions"
            >
            </el-date-picker>
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
        <el-tabs
          v-model="searchParams.tabIndex"
          @tab-click="handleClick"
        >
          <el-tab-pane
            v-for="item in tabList"
            :label="item.label"
            :name="item.value"
            :key="item.value"
          >
          </el-tab-pane>
        </el-tabs>
        <table>
          <thead>
            <tr>
              <th width="300px">商品名称</th>
              <th>退款类型</th>
              <th>商品数量</th>
              <th>商品价格</th>
              <th>退款原因</th>
              <th>退款状态</th>
              <th width="130px">操作</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td colspan="8"></td>
            </tr>
          </tbody>
          <template v-for="orderItem in refundOrderList">
            <tbody
              :key="orderItem.orderId"
              class="hasborder"
            >
              <tr class="order-tb-head">
                <td colspan="8">
                  <div class="tb-head_box">
                    <div class="left">
                      <span>退款编号：{{orderItem.returnOrderSn}}</span>
                      <span>订单编号：{{orderItem.orderSn}}</span>
                      <span>申请时间：{{orderItem.createTime}}</span>
                      <span>退款金额：10</span>
                      <span>运费退款金额：100</span>
                    </div>
                  </div>
                </td>
              </tr>
              <template v-for="(goodsItem,index) in orderItem.goods">
                <tr
                  class="order-tb-body"
                  :key="index"
                >
                  <td>
                    <div class="goods_info">
                      <img
                        :src="$imageHost+'/image/admin/icon_jia.png'"
                        alt=""
                      >
                      <div class="right_info">
                        <div class="goods_name">{{goodsItem.goodsName}}</div>
                        <div class="goods_spec">{{goodsItem.goodsAttr}}</div>
                      </div>
                    </div>
                  </td>
                  <td>
                    {{orderItem.returnType}}
                  </td>
                  <td>
                    {{goodsItem.goodsNumber}}
                  </td>
                  <td>
                    {{goodsItem.goodsPrice}}
                  </td>
                  <td
                    v-if="index === 0"
                    :rowspan="orderItem.goods.length"
                  >
                    {{orderItem.refundStatus}}
                  </td>
                  <td
                    v-if="index === 0"
                    :rowspan="orderItem.goods.length"
                  >
                    {{orderItem.deliverType}}
                  </td>
                  <td
                    v-if="index === 0"
                    :rowspan="orderItem.goods.length"
                  >
                    <el-button
                      :type="orderItem.refundStatus === 1 ? 'primary' : 'default'"
                      size="small"
                      @click="checkDetail(orderItem.returnOrderSn)"
                    >{{orderItem.refundStatus === 1 ? '处理退款':'查看详情'}}</el-button>
                  </td>
                </tr>
              </template>
            </tbody>
            <tbody :key="orderItem.orderId">
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
        orderSn: '',
        refundSn: '',
        refundStatus: -1,
        refundType: -1,
        applicationTime: null,
        tabIndex: '-1'
      },
      refundStatus: [
        { value: -1, label: '全部' },
        { value: 1, label: '退款申请等待商家确认' },
        { value: 2, label: '商家拒绝退款申请' },
        { value: 3, label: '商家同意退货退款，等待买家退货' },
        { value: 4, label: '买家已退货，等待商家确认收货' },
        { value: 5, label: '商家未收货，拒绝退款' },
        { value: 6, label: '退款撤销' },
        { value: 7, label: '退款成功' }
      ],
      refundType: [
        { value: -1, label: '全部' },
        { value: 1, label: '仅退款' },
        { value: 2, label: '退货退款' },
        { value: 3, label: '仅退运费' },
        { value: 4, label: '手动退款' }
      ],
      pickerOptions: {
        shortcuts: [
          {
            text: '最近一周',
            onClick (picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '最近一个月',
            onClick (picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
              picker.$emit('pick', [start, end])
            }
          }
        ]
      },
      tabList: [
        { value: '-1', label: '全部' },
        { value: '1', label: '商家待处理' },
        { value: '2', label: '买家待处理' },
        { value: '3', label: '已完成' }
      ],
      refundOrderList: [
        {
          'orderId': 6751,
          'orderSn': 'P201908261402467301',
          'mainOrderSn': null,
          'goodsType': null,
          'childOrders': null,
          'goods': [
            {
              'recId': 8313,
              'mainRecId': null,
              'orderId': null,
              'orderSn': 'P201908261402467301',
              'goodsId': 694,
              'goodsSn': null,
              'goodsName': '首单限时优化-CJ111',
              'goodsNumber': 1,
              'goodsPrice': 55,
              'goodsAttr': '颜色:白色',
              'productId': 4726,
              'goodsImg': 'http://mpdevimg2.weipubao.cn/upload/0/image/20190807/crop_0mf3fRQPXxNt1Kot.jpeg',
              'sendNumber': 0,
              'discountedGoodsPrice': 55,
              'retId': 78,
              'success': 2
            }
          ],
          'orderStatus': null,
          'consignee': '',
          'mobile': null,
          'payCode': null,
          'deliverType': null,
          'createTime': '2019-09-02 18:12:11',
          'shippingFee': 0,
          'moneyPaid': null,
          'partShipFlag': null,
          'refundStatus': 1,
          'returnOrderSn': 'R201909021812112881',
          'applyTime': null,
          'money': 10,
          'returnType': 0,
          'reason': null
        },
        {
          'orderId': 1000,
          'orderSn': 'P201900000000000000',
          'mainOrderSn': null,
          'goodsType': null,
          'childOrders': null,
          'goods': [
            {
              'recId': 1000,
              'mainRecId': null,
              'orderId': null,
              'orderSn': 'P201900000000000000',
              'goodsId': 694,
              'goodsSn': null,
              'goodsName': '首单限时优化-CJ111',
              'goodsNumber': 1,
              'goodsPrice': 55,
              'goodsAttr': '颜色:白色',
              'productId': 4726,
              'goodsImg': 'http://mpdevimg2.weipubao.cn/upload/0/image/20190807/crop_0mf3fRQPXxNt1Kot.jpeg',
              'sendNumber': 0,
              'discountedGoodsPrice': 55,
              'retId': 79,
              'success': 2
            },
            {
              'recId': 1001,
              'mainRecId': null,
              'orderId': null,
              'orderSn': 'P201900000000000000',
              'goodsId': 695,
              'goodsSn': null,
              'goodsName': '首单限时优化-CJ222',
              'goodsNumber': 1,
              'goodsPrice': 55,
              'goodsAttr': '颜色:黑色',
              'productId': 4727,
              'goodsImg': 'http://mpdevimg2.weipubao.cn/upload/0/image/20190807/crop_0mf3fRQPXxNt1Kot.jpeg',
              'sendNumber': 0,
              'discountedGoodsPrice': 55,
              'retId': 79,
              'success': 2
            }
          ],
          'orderStatus': null,
          'consignee': '',
          'mobile': null,
          'payCode': null,
          'deliverType': null,
          'createTime': '2019-09-03 17:31:19',
          'shippingFee': 0,
          'moneyPaid': null,
          'partShipFlag': null,
          'refundStatus': 2,
          'returnOrderSn': 'R201909031731185091',
          'applyTime': null,
          'money': 10.01,
          'returnType': 0,
          'reason': null
        },
        {
          'orderId': 1001,
          'orderSn': 'P201900000000000001',
          'mainOrderSn': null,
          'goodsType': null,
          'childOrders': null,
          'goods': [
            {
              'recId': 1002,
              'mainRecId': null,
              'orderId': null,
              'orderSn': 'P201900000000000001',
              'goodsId': 694,
              'goodsSn': null,
              'goodsName': '首单限时优化-CJ111',
              'goodsNumber': 1,
              'goodsPrice': 55,
              'goodsAttr': '颜色:白色',
              'productId': 4726,
              'goodsImg': 'http://mpdevimg2.weipubao.cn/upload/0/image/20190807/crop_0mf3fRQPXxNt1Kot.jpeg',
              'sendNumber': 0,
              'discountedGoodsPrice': 55,
              'retId': 81,
              'success': 2
            },
            {
              'recId': 1003,
              'mainRecId': null,
              'orderId': null,
              'orderSn': 'P201900000000000001',
              'goodsId': 695,
              'goodsSn': null,
              'goodsName': '首单限时优化-CJ222',
              'goodsNumber': 1,
              'goodsPrice': 55,
              'goodsAttr': '颜色:黑色',
              'productId': 4727,
              'goodsImg': 'http://mpdevimg2.weipubao.cn/upload/0/image/20190807/crop_0mf3fRQPXxNt1Kot.jpeg',
              'sendNumber': 0,
              'discountedGoodsPrice': 55,
              'retId': 81,
              'success': 2
            }
          ],
          'orderStatus': null,
          'consignee': '',
          'mobile': null,
          'payCode': null,
          'deliverType': null,
          'createTime': '2019-09-03 19:22:30',
          'shippingFee': 22,
          'moneyPaid': null,
          'partShipFlag': null,
          'refundStatus': 1,
          'returnOrderSn': 'R201909031922307382',
          'applyTime': null,
          'money': 110,
          'returnType': 0,
          'reason': null
        },
        {
          'orderId': 1005,
          'orderSn': 'P201900000000000005',
          'mainOrderSn': null,
          'goodsType': null,
          'childOrders': null,
          'goods': [
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
          ],
          'orderStatus': null,
          'consignee': '',
          'mobile': null,
          'payCode': null,
          'deliverType': null,
          'createTime': '2019-09-04 19:06:31',
          'shippingFee': 0,
          'moneyPaid': null,
          'partShipFlag': null,
          'refundStatus': 5,
          'returnOrderSn': 'R201909041906318788',
          'applyTime': null,
          'money': 10,
          'returnType': 0,
          'reason': null
        }
      ]
    }
  },
  methods: {
    initDataList () {

    },
    handleClick (index) {
      console.log(index)
    },
    checkDetail (refundSn) {
      this.$router.push({
        name: 'orderRefundInfo',
        query: {
          refundSn: refundSn
        }
      })
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
    .default_input {
      width: 180px;
    }
  }
}
</style>
