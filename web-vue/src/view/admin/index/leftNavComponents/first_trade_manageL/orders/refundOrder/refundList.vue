<template>
  <div class="content">
    <div class="main">
      <div class="search_box">
        <div class="filters">
          <div class="filters_item">
            <span>{{$t('order.orderSn')}}：</span>
            <el-input
              v-model="searchParams.orderSn"
              placeholder="订单编号"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>{{$t('order.returnSn')}}：</span>
            <el-input
              v-model="searchParams.refundSn"
              :placeholder="$t('order.returnSn')"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>{{$t('order.returnStatus')}}：</span>
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
            <span>{{$t('order.returntype')}}：</span>
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
            <span>{{$t('order.applyTime')}}：</span>
            <el-date-picker
              v-model="applyTime"
              type="daterange"
              :range-separator="$t('membershipIntroduction.to')"
              :start-placeholder="$t('membershipIntroduction.Starttime')"
              :end-placeholder="$t('membershipIntroduction.Endtime')"
              value-format="yyyy-MM-dd HH:mm:ss"
              :default-time="['00:00:00','23:59:59']"
              size="small"
              :picker-options="pickerOptions"
            >
            </el-date-picker>
          </div>
          <div class="filters_item">
            <el-button
              type="primary"
              size="small"
              @click="search"
            >{{$t('order.filter')}}</el-button>
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
              <th width="300px">{{$t('order.goodsName')}}</th>
              <th>{{$t('order.goodsNum')}}</th>
              <th>{{$t('order.goodsPrice')}}</th>
              <th>{{$t('order.returntype')}}</th>
              <th>{{$t('order.returnReasonText')}}</th>
              <th>{{$t('order.returnStatus')}}</th>
              <th width="130px">{{$t('order.operate')}}</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td colspan="8"></td>
            </tr>
          </tbody>
          <template v-for="orderItem in refundOrderList">
            <tbody
              :key="orderItem.retId"
              class="hasborder"
            >
              <tr class="order-tb-head">
                <td colspan="8">
                  <div class="tb-head_box">
                    <div class="left">
                      <span>{{$t('order.returnSn') + '：'+ orderItem.returnOrderSn}}</span>
                      <span>{{$t('order.orderSn') + '：'+orderItem.orderSn}}</span>
                      <span>
                        {{
                          $t('order.applyTime') + '：'+
                          (orderItem.returnType != 1 ?
                          orderItem. shippingOrRefundTime:
                          orderItem.applyTime)
                        }}
                      </span>
                      <span>{{$t('order.returnMoney') + '：'+ currencyPool[orderItem.currency][lang][1] + orderItem.money.toFixed(2)}}</span>
                      <span>{{$t('order.returnShipping') + '：'+ currencyPool[orderItem.currency][lang][1] + orderItem.shippingFee.toFixed(2)}}</span>
                    </div>
                  </div>
                </td>
              </tr>
              <template v-if="orderItem.goods != null">
                <template v-for="(goodsItem,index) in orderItem.goods">
                  <tr
                    class="order-tb-body"
                    :key="index"
                  >
                    <td>
                      <div class="goods_info">
                        <img
                          :src="$imageHost+'/'+goodsItem.goodsImg"
                          alt=""
                        >
                        <div class="right_info">
                          <div class="goods_name">{{goodsItem.goodsName}}</div>
                          <div class="goods_spec">{{goodsItem.goodsAttr}}</div>
                        </div>
                      </div>
                    </td>
                    <td>
                      {{goodsItem.goodsNumber}}
                    </td>
                    <td>
                      {{goodsItem.goodsPrice.toFixed(2)}}
                    </td>
                    <td
                      v-if="index === 0"
                      :rowspan="orderItem.goods.length"
                    >
                      {{returnTypeMap.get(orderItem.returnType)}}
                    </td>
                    <td
                      v-if="index === 0"
                      :rowspan="orderItem.goods.length"
                    >
                      {{$t('order.reasonTypeList')[orderItem.reasonType]}}
                    </td>
                    <td
                      v-if="index === 0"
                      :rowspan="orderItem.goods.length"
                    >
                      {{
                      returnStatusToShowMapping[orderItem.refundStatus + ''] == null ?
                      returnStatusToShowMapping[orderItem.refundStatus + '-' + orderItem.returnType] :
                      returnStatusToShowMapping[orderItem.refundStatus + '']
                    }}
                    </td>
                    <td
                      v-if="index === 0"
                      :rowspan="orderItem.goods.length"
                    >
                      <el-button
                        :type="(orderItem.refundStatus === 1 || orderItem.refundStatus === 2 || orderItem.refundStatus === 4) ? 'primary' : 'default'"
                        size="small"
                        @click="checkDetail(orderItem.returnOrderSn)"
                      >{{(orderItem.refundStatus === 1 || orderItem.refundStatus === 2 || orderItem.refundStatus === 4) ? '处理退款':'查看详情'}}</el-button>
                    </td>
                  </tr>
                </template>
              </template>
              <template v-else>
                <tr class="order-tb-body">
                  <td>
                    {{$t('order.nullValue')}}
                  </td>
                  <td>
                    {{$t('order.nullValue')}}
                  </td>
                  <td>
                    {{$t('order.nullValue')}}
                  </td>
                  <td>
                    {{returnTypeMap.get(orderItem.returnType)}}
                  </td>
                  <td>
                    {{$t('order.reasonTypeList')[orderItem.reasonType]}}
                  </td>
                  <td>
                    {{
                      returnStatusToShowMapping[orderItem.refundStatus + ''] == null ?
                      returnStatusToShowMapping[orderItem.refundStatus + '-' + orderItem.returnType] :
                      returnStatusToShowMapping[orderItem.refundStatus + '']
                    }}
                  </td>
                  <td>
                    <el-button
                      :type="orderItem.refundStatus === 1 ? 'primary' : 'default'"
                      size="small"
                      @click="checkDetail(orderItem.returnOrderSn)"
                    >{{orderItem.refundStatus === 1 ? '处理退款':'查看详情'}}</el-button>
                  </td>
                </tr>
              </template>
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
import {
  list
} from '@/api/admin/orderManage/order.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      applyTime: null,
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
        refundSn: null,
        refundStatus: null,
        returnType: null,
        returnStart: null,
        returnEnd: null,
        currentPage: null,
        pageRows: null,
        tabIndex: null
      },
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
      tabList: [
        { value: '-1', label: '全部' },
        { value: '1', label: '商家待处理' },
        { value: '2', label: '买家待处理' },
        { value: '3', label: '已完成' }
      ],
      refundOrderList: []
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
    applyTime (val) {
      this.searchParams.returnStart = val ? val[0] : null
      this.searchParams.returnEnd = val ? val[1] : null
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
        name: 'orderRefundInfo',
        query: {
          returnOrderSn: returnOrderSn
        }
      })
    },
    search () {
      this.refundOrderList = null
      this.searchParams.currentPage = this.pageParams.currentPage
      this.searchParams.pageRows = this.pageParams.pageRows
      list(this.searchParams).then(res => {
        console.log(res)
        this.pageParams = res.content.list.page
        this.refundOrderList = res.content.list.dataList
      }).catch(() => {
      })
    },
    arrayToMap () {
      this.returnTypeMap = new Map(this.$t('order.returnTypeList'))
      this.returnStatusMap = new Map(this.$t('order.returnStatusList'))
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
