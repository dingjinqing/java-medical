<template>
  <div class="content">
    <div class="main">
      <div class="search_box">
        <div class="filters">
          <div class="filters_item">
            <span>{{ $t('order.orderSn') }}：</span>
            <el-input
              v-model="searchParams.orderSn"
              placeholder="订单编号"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>{{ $t('order.returnSn') }}：</span>
            <el-input
              v-model="searchParams.returnOrderSn"
              :placeholder="$t('order.returnSn')"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>{{ $t('order.buyer') }}：</span>
            <el-input
              v-model="searchParams.orderUserNameOrMobile"
              :placeholder="
                $t('order.userNameText') + '/' + $t('order.userMobileText')
              "
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>{{ $t('order.returnStatus') }}：</span>
            <el-select
              v-model="searchParams.refundStatus"
              size="small"
              class="default_input"
              filterable
            >
              <el-option
                v-for="item in $t('order.returnStatusList')"
                :key="item[0]"
                :label="item[1]"
                :value="item[0]"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <span>{{ $t('order.returntype') }}：</span>
            <el-select
              v-model="searchParams.returnType"
              size="small"
              class="default_input"
              filterable
              multiple
            >
              <el-option
                v-for="item in $t('order.returnTypeList')"
                :key="item[0]"
                :label="item[1]"
                :value="item[0]"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <span>{{ $t('order.returnWays') }}：</span>
            <el-select
              v-model="returnWay"
              size="small"
              class="default_input"
              filterable
            >
              <el-option
                v-for="item in $t('order.returnWaysList')"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <span>{{ $t('order.applyTime') }}：</span>
            <el-date-picker
              v-model="applyTime.startTime"
              type="datetime"
              placeholder="开始时间"
              value-format="yyyy-MM-dd HH:mm:ss"
              class="default_input"
              @change="datePickerChange(true, applyTime)"
              size="small"
            />
             至
            <el-date-picker
              v-model="applyTime.endTime"
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
            <el-button type="primary" size="small" @click="search">{{
              $t('order.filter')
            }}</el-button>
          </div>
        </div>
      </div>
      <div class="table_box">
        <el-tabs v-model="tabIndex" @tab-click="handleClick">
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
              <th width="300px">{{ $t('order.goodsName') }}</th>
              <th>{{ $t('order.goodsNum') }}</th>
              <th>{{ $t('order.goodsPrice') }}</th>
              <th>{{ $t('order.returntype') }}</th>
              <th>{{ $t('order.orderUserInfo') }}</th>
              <th>{{ $t('order.returnReasonText') }}</th>
              <th>{{ $t('order.returnStatus') }}</th>
              <th width="130px">{{ $t('order.operate') }}</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td colspan="8"></td>
            </tr>
          </tbody>
          <template v-for="(orderItem, orderIndex) in refundOrderList">
            <tbody :key="orderItem.retId" class="hasborder">
              <tr class="order-tb-head">
                <td colspan="8">
                  <div class="tb-head_box">
                    <div class="left">
                      <span>{{
                        $t('order.returnSn') + '：' + orderItem.returnOrderSn
                      }}</span>
                      <el-popover placement="right" trigger="hover">
                        <div
                          class="order-brief"
                          v-loading="orderBriefLoading"
                          @click="handleViewOrder(orderItem.orderSn)"
                        >
                          <div class="title">订单信息</div>
                          <div class="brief-info">
                            <div>订单号：{{ orderItem.orderSn }}</div>
                            <div>
                              订单状态：{{
                                orderItem.refundStatus === 5
                                  ? returnStatusToShowMapping['5']
                                  : orderBriefData.orderStatusName
                              }}
                            </div>
                            <div>支付方式：{{ orderBriefData.payName }}</div>
                            <div>
                              配送方式：{{
                                orderBriefData.deliverType === 0
                                  ? '快递'
                                  : '自提'
                              }}
                            </div>
                            <div>
                              订单类型：{{ orderBriefData.goodsTypeName }}
                            </div>
                            <div>下单时间：{{ orderBriefData.createTime }}</div>
                            <div>
                              下单人：{{ orderBriefData.username }}
                              {{ orderBriefData.userMobile }}
                            </div>
                            <div>
                              收件人：{{ orderBriefData.consignee }}
                              {{ orderBriefData.mobile }}
                            </div>
                            <div>
                              收货地址：{{ orderBriefData.completeAddress }}
                            </div>
                          </div>
                        </div>
                        <span slot="reference"
                          >{{ $t('order.orderSn') }}：<span
                            class="high-light"
                            @click="handleViewOrder(orderItem.orderSn)"
                            @mouseenter="
                              requestOrderInfo(orderItem.orderSn, orderIndex)
                            "
                            >{{ orderItem.orderSn }}</span
                          ></span
                        >
                      </el-popover>
                      <span>
                        {{
                          $t('order.applyTime') +
                          '：' +
                          (orderItem.returnType != 1
                            ? orderItem.shippingOrRefundTime
                            : orderItem.applyTime)
                        }}
                      </span>
                      <span>{{
                        $t('order.returnMoney') +
                        '：' +
                        currencyPool[orderItem.currency][lang][1] +
                        orderItem.money.toFixed(2)
                      }}</span>
                      <span>{{
                        $t('order.returnShipping') +
                        '：' +
                        currencyPool[orderItem.currency][lang][1] +
                        orderItem.shippingFee.toFixed(2)
                      }}</span>
                    </div>
                  </div>
                </td>
              </tr>
              <template v-if="orderItem.goods != null">
                <template v-for="(goodsItem, index) in orderItem.goods">
                  <tr class="order-tb-body" :key="index">
                    <td>
                      <div class="goods_info">
                        <img
                          :src="$imageHost + '/' + goodsItem.goodsImg"
                          alt=""
                        />
                        <div class="right_info">
                          <div class="goods_name">
                            {{ goodsItem.goodsName }}
                          </div>
                          <div class="goods_spec">
                            {{ goodsItem.goodsAttr }}
                          </div>
                        </div>
                      </div>
                    </td>
                    <td>
                      {{ goodsItem.goodsNumber }}
                    </td>
                    <td>
                      {{ goodsItem.goodsPrice.toFixed(2) }}
                    </td>
                    <td v-if="index === 0" :rowspan="orderItem.goods.length">
                      {{ returnTypeMap.get(orderItem.returnType) }}
                      <br />
                      ({{
                        $t(`order.returnWaysList`).find(
                          (item) => item.value === orderItem.returnSource
                        ).label
                      }})
                    </td>
                    <td v-if="index === 0" :rowspan="orderItem.goods.length">
                      <div
                        class="high-light"
                        @click="viewUserCenter(orderItem.userId)"
                      >
                        {{ orderItem.orderUserName }}
                        <br />
                        {{ orderItem.orderMobile }}
                      </div>
                    </td>
                    <td v-if="index === 0" :rowspan="orderItem.goods.length">
                      {{ $t('order.reasonTypeList')[orderItem.reasonType] }}
                    </td>
                    <td v-if="index === 0" :rowspan="orderItem.goods.length">
                      {{
                        returnStatusToShowMapping[
                          orderItem.refundStatus + ''
                        ] == null
                          ? returnStatusToShowMapping[
                              orderItem.refundStatus +
                                '-' +
                                orderItem.returnType
                            ]
                          : returnStatusToShowMapping[
                              orderItem.refundStatus + ''
                            ]
                      }}
                    </td>
                    <td v-if="index === 0" :rowspan="orderItem.goods.length">
                      <el-button
                        :type="
                          orderItem.refundStatus === 1 ||
                          orderItem.refundStatus === 2 ||
                          orderItem.refundStatus === 4
                            ? 'primary'
                            : 'default'
                        "
                        size="small"
                        @click="checkDetail(orderItem.returnOrderSn)"
                        >{{
                          orderItem.refundStatus === 1 ||
                          orderItem.refundStatus === 2 ||
                          orderItem.refundStatus === 4
                            ? '处理退款'
                            : '查看详情'
                        }}</el-button
                      >
                    </td>
                  </tr>
                </template>
              </template>
              <template v-else>
                <tr class="order-tb-body">
                  <td>
                    {{ $t('order.nullValue') }}
                  </td>
                  <td>
                    {{ $t('order.nullValue') }}
                  </td>
                  <td>
                    {{ $t('order.nullValue') }}
                  </td>
                  <td>
                    {{ returnTypeMap.get(orderItem.returnType) }}
                    <br />
                    ({{
                      $t(`order.returnWaysList`).find(
                        (item) => item.value === orderItem.returnSource
                      ).label
                    }})
                  </td>
                  <td>
                    <div
                      class="high-light"
                      @click="viewUserCenter(orderItem.userId)"
                    >
                      {{ orderItem.orderUserName }}
                      <br />
                      {{ orderItem.orderMobile }}
                    </div>
                  </td>
                  <td>
                    {{ $t('order.reasonTypeList')[orderItem.reasonType] }}
                  </td>
                  <td>
                    {{
                      returnStatusToShowMapping[orderItem.refundStatus + ''] ==
                      null
                        ? returnStatusToShowMapping[
                            orderItem.refundStatus + '-' + orderItem.returnType
                          ]
                        : returnStatusToShowMapping[orderItem.refundStatus + '']
                    }}
                  </td>
                  <td>
                    <el-button
                      :type="
                        orderItem.refundStatus === 1 ? 'primary' : 'default'
                      "
                      size="small"
                      @click="checkDetail(orderItem.returnOrderSn)"
                      >{{
                        orderItem.refundStatus === 1 ? '处理退款' : '查看详情'
                      }}</el-button
                    >
                  </td>
                </tr>
              </template>
            </tbody>
          </template>
        </table>
        <pagination :page-params.sync="pageParams" @pagination="initDataList" />
      </div>
    </div>
  </div>
</template>

<script>
import {
  getOrderList,
  getOrderBrief
} from '@/api/store/order'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      applyTime: {
        startTime: null,
        endTime: null
      },
      paymentTypeMap: {},
      goodsTypeMap: {},
      pageParams: {
        'totalRows': null,
        'currentPage': null,
        'firstPage': null,
        'prePage': null,
        'nextPage': null,
        'lastPage': null,
        'pageRows': null,
        'pageCount': null
      },
      searchParams: {
        searchType: 1,
        orderSn: null,
        returnOrderSn: null,
        refundStatus: null,
        returnType: null,
        returnStart: null,
        returnEnd: null,
        currentPage: null,
        pageRows: null,
        stateCollection: null,
        orderUserNameOrMobile: null,
        returnSource: null
      },
      returnWay: -1,
      returnTypeMap: null,
      returnStatusMap: null,
      returnStatusToShowMapping: null,
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
      tabIndex: '-1',
      tabList: [
        { value: '-1', label: '全部' },
        { value: '1', label: '商家待处理' },
        { value: '2', label: '买家待处理' },
        { value: '3', label: '已完成' }
      ],
      refundOrderList: [],
      orderBriefData: {},
      applyEndTime: {
        disabledDate: time => {
          return time.getTime() < new Date(this.applyTime.startTime).getTime()
        }
      },
      orderBriefLoading: false
    }
  },
  mounted () {
    console.log('mounted-----------------------')
    // 初始化数据
    if (this.$route.query.orderSn) {
      this.$set(this.searchParams, 'orderSn', this.$route.query.orderSn)
    }
    this.langDefault()
    this.initDataList()
  },
  watch: {
    lang () {
      this.langDefault()
      this.arrayToMap()
    },
    tabIndex (val) {
      if (val === -1) {
        this.searchParams.stateCollection = null
      } else {
        this.searchParams.stateCollection = val
      }
      this.initDataList()
    },
    returnWay: {
      handler: function (val) {
        console.log(val)
        if (val === -1) {
          this.searchParams.returnSource = null
        } else {
          this.searchParams.returnSource = [val]
        }
      }
    }
  },
  methods: {
    initDataList () {
      this.search()
    },
    handleClick (index) {
      console.log(index)
    },
    checkDetail (returnOrderSn) {
      this.$router.push({
        name: 'afterSaleInfo',
        query: {
          returnOrderSn: returnOrderSn
        }
      })
    },
    search () {
      this.refundOrderList = null
      this.searchParams.currentPage = this.pageParams.currentPage
      this.searchParams.pageRows = this.pageParams.pageRows
      this.searchParams.returnStart = this.applyTime.startTime
      this.searchParams.returnEnd = this.applyTime.endTime
      getOrderList(this.searchParams).then(res => {
        console.log(res)
        this.pageParams = res.content.list.page
        this.refundOrderList = res.content.list.dataList
      }).catch(() => {
      })
    },
    arrayToMap () {
      this.returnTypeMap = new Map(this.$t('order.returnTypeList'))
      this.returnStatusMap = new Map(this.$t('order.returnStatusList'))
      this.paymentTypeMap = new Map(this.$t('order.paymentTypeList'))
      this.goodsTypeMap = new Map(this.$t('order.goodsTypeList'))
      this.returnStatusToShowMapping = {
        '1': this.$t('order.returnStatusMapping_1'),
        '2': this.$t('order.returnStatusList')[3][1],
        '3': this.$t('order.returnStatusList')[2][1],
        '4-0': this.$t('order.returnStatusMapping_4_0'),
        '4-1': this.$t('order.returnStatusList')[4][1],
        '6-0': this.$t('order.returnStatusList')[6][1],
        '6-1': this.$t('order.returnStatusList')[5][1],
        '7': this.$t('order.returnStatusList')[7][1],
        '5': this.$t('order.returnStatusList')[8][1]
      }
    },
    requestOrderInfo (orderSn, targetIndex) {
      this.orderBriefLoading = true
      getOrderBrief({ orderSn }).then(res => {
        console.log(res)
        if (res.error === 0 && res.content) {
          this.orderBriefData = res.content
          this.$set(this.orderBriefData, 'orderStatusName', this.getOrderStatus(res.content))
          this.$set(this.orderBriefData, 'payName', this.getOrderPayName(res.content))
          this.$set(this.orderBriefData, 'goodsTypeName', this.getGoodsTypeName(res.content))
          this.orderBriefLoading = false
        }
      }).catch(() => {
      })
    },
    getOrderPayName ({ payCodeList }) {
      let nameArray = []
      payCodeList.forEach(item => nameArray.push(this.paymentTypeMap.get(item)))
      return nameArray.join(',')
    },
    getOrderStatus (orderData) {
      const orderStatusList = [
        [null, '全部订单'],
        [0, '待付款'],
        [1, '订单取消'],
        [2, '订单关闭'],
        [3, '待发货/待核销'],
        [4, '已发货'],
        [5, '已收货/已自提'],
        [6, '订单完成'],
        [7, '退货中'],
        [8, '退货完成'],
        [9, '退款中'],
        [10, '退款完成'],
        [11, '拼团中'],
        [12, '已成团'],
        [13, '送礼完成']
      ]
      let typeArray = orderData.goodsType.substring(1, orderData.goodsType.length - 1).split('][')
      console.log(typeArray)
      if (orderData.orderStatus !== 3 && orderData.orderStatus !== 5) {
        if (orderData.orderStatus === 0 && typeArray.indexOf(10) !== -1) {
          if (orderData.bkOrderPaid === 0) {
            return '待付定金'
          } else {
            return '待付尾款'
          }
        } else {
          let orderStatusMap = new Map(orderStatusList)
          return orderStatusMap.get(orderData.orderStatus)
        }
      } else {
        if (orderData.deliverType === 1 && orderData.orderStatus === 3) {
          return '待核销'
        } else if (orderData.deliverType === 0 && orderData.orderStatus === 3) {
          return '待发货'
        } else if (orderData.deliverType === 1 && orderData.orderStatus === 5) {
          return '已自提'
        } else if (orderData.deliverType === 0 && orderData.orderStatus === 5) {
          return '已收货'
        }
      }
      return '待发货'
    },
    getGoodsTypeName ({ goodsType }) {
      let typeArray = goodsType.substring(1, goodsType.length - 1).split('][')
      let nameArray = []
      typeArray.forEach(item => nameArray.push(this.goodsTypeMap.get(Number(item))))
      return nameArray.join(',')
    },
    handleViewOrder (orderSn) {
      this.$router.push({
        name: 'orderInfo',
        query: {
          orderSn: orderSn
        }
      })
    },
    /* 验证输入的时间范围是否合法 */
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
    viewUserCenter (userId) {
      this.$router.push({
        name: 'membershipInformation',
        query: {
          userId
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
              line-height: 20px;
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
.order-brief {
  display: flex;
  width: 250px;
  flex-direction: column;
  border-radius: 6px 6px 0px 0px;
  overflow: hidden;
  cursor: pointer;
  > .title {
    text-align: center;
    line-height: 30px;
    color: white;
    background: #5a8bff;
  }
  > .brief-info {
    display: flex;
    flex-direction: column;
    padding: 10px 15px 15px;
    background: rgba(90, 139, 255, 0.1);
    position: relative;
    > div {
      color: #333;
      & + div {
        margin-top: 5px;
      }
    }
    > .bottom-bg {
      position: absolute;
      bottom: 0;
      left: 10px;
    }
  }
}
.high-light {
  color: #409eff;
  cursor: pointer;
}
</style>
