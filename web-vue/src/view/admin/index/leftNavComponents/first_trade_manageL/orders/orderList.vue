<template>
  <div class="content">
    <div class="main">
      <div class="search_box">
        <div class="filters">
          <div class="filters_item">
            <span>{{$t('order.goodsName')}}：</span>
            <el-input
              v-model="searchParams.goodsName"
              :placeholder="$t('order.goodsName')"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>{{$t('order.orderSn')}}：</span>
            <el-input
              v-model="searchParams.orderSn"
              :placeholder="$t('order.orderSn')"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>{{$t('order.orderStatusText')}}：</span>
            <el-select
              v-model="searchParams.orderStatus"
              :placeholder="$t('order.defaultSelect')"
              size="small"
              class="default_input"
              filterable
              multiple
            >
              <el-option
                v-for="item in $t('order.orderStatusList')"
                :key="item[0]"
                :label="item[1]"
                :value="item[0]"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <span>{{$t('order.goodsTypeText')}}：</span>
            <el-select
              v-model="searchParams.goodsType"
              :placeholder="$t('order.defaultSelect')"
              size="small"
              class="default_input"
              filterable
            >
              <el-option
                v-for="item in $t('order.goodsTypeList')"
                :key="item[0]"
                :label="item[1]"
                :value="item[0]"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <span>{{$t('order.consigneeName')}}：</span>
            <el-input
              v-model="searchParams.consignee"
              :placeholder="$t('order.consigneeName')"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>{{$t('order.mobile')}}：</span>
            <el-input
              v-model="searchParams.mobile"
              :placeholder="$t('order.mobile')"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>{{$t('order.deliverTypeText')}}：</span>
            <el-select
              v-model="searchParams.deliverType"
              :placeholder="$t('order.defaultSelect')"
              size="small"
              class="default_input"
              filterable
            >
              <el-option
                v-for="item in $t('order.deliverTypeList')"
                :key="item[0]"
                :label="item[1]"
                :value="item[0]"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <span>{{$t('order.userName')}}：</span>
            <el-input
              v-model="searchParams.userName"
              :placeholder="$t('order.userName')"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>{{$t('order.userSource')}}：</span>
            <el-select
              v-model="searchParams.source"
              :placeholder="$t('order.defaultSelect')"
              size="small"
              class="default_input"
              filterable
            >
              <el-option
                v-for="item in sourceList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </div>
          <div
            class="filters_item"
            v-show="moreFilters"
          >
            <span>{{$t('order.verifyCode')}}：</span>
            <el-input
              v-model="searchParams.verifyCode"
              :placeholder="$t('order.verifyCode')"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div
            class="filters_item"
            v-show="moreFilters"
          >
            <span>{{$t('order.store')}}：</span>
            <el-select
              v-model="searchParams.storeId"
              :placeholder="$t('order.defaultSelect')"
              size="small"
              class="default_input"
              filterable
            >
              <el-option
                v-for="item in storeList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </div>
          <div
            class="filters_item"
            v-show="moreFilters"
          >
            <span>{{$t('order.paymentType')}}：</span>
            <el-select
              v-model="searchParams.paymentType"
              :placeholder="$t('order.defaultSelect')"
              size="small"
              class="default_input"
              filterable
            >
              <el-option
                v-for="item in $t('order.paymentTypeList')"
                :key="item[0]"
                :label="item[1]"
                :value="item[0]"
              ></el-option>
            </el-select>
          </div>
          <div
            class="filters_item"
            v-show="moreFilters"
          >
            <span>{{$t('order.tag')}}：</span>
            <el-select
              v-model="searchParams.tagIds"
              multiple
              filterable
              allow-create
              default-first-option
              :placeholder="$t('order.tagDescribe')"
              :multiple-limit="3"
              size="small"
              style="width:auto"
            >
              <el-option
                v-for="item in tagList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div
            class="filters_item"
            v-show="moreFilters"
          >
            <span>{{$t('order.ShippingAddress')}}：</span>
            <areaLinkage
              @areaData="handleAreaData"
              style="width:365px;"
            />
          </div>
          <div
            class="filters_item"
            v-show="moreFilters"
          >
            <span>{{$t('order.specCode')}}：</span>
            <el-input
              v-model="searchParams.specCode"
              :placeholder="$t('order.specCode')"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div
            class="filters_item"
            v-show="moreFilters"
          >
            <span>{{$t('order.orderTime')}}：</span>
            <el-date-picker
              v-model="orderTime"
              type="daterange"
              :range-separator="$t('membershipIntroduction.to')"
              :start-placeholder="$t('membershipIntroduction.Starttime')"
              :end-placeholder="$t('membershipIntroduction.Endtime')"
              value-format="yyyy-MM-dd"
              size="small"
            >
            </el-date-picker>
          </div>
          <div
            class="filters_item"
            v-show="moreFilters"
          >
            <span>{{$t('order.completeTime')}}：</span>
            <el-date-picker
              v-model="completeTime"
              type="daterange"
              :range-separator="$t('membershipIntroduction.to')"
              :start-placeholder="$t('membershipIntroduction.Starttime')"
              :end-placeholder="$t('membershipIntroduction.Endtime')"
              value-format="yyyy-MM-dd"
              size="small"
            >
            </el-date-picker>
          </div>
        </div>
      </div>

      <div class="search_button_box">
        <span @click="moreFilters = !moreFilters">{{moreFilters ? $t('order.collapse') : $t('order.more')}}</span>
        <div class="button_box">
          <el-button
            type="primary"
            size="small"
            @click="search"
          >{{$t('order.filter')}}</el-button>
          <el-button
            type="default"
            size="small"
          >{{$t('order.export')}}</el-button>
        </div>
      </div>
      <div class="table_box">
        <el-tabs
          v-model="searchParams.orderStatus"
          @tab-click="handleClick"
        >
          <template v-for="item in tabsOrderStatus">
            <el-tab-pane
              :label="item.label"
              :name="item.value"
              :key="item.value"
              v-if="item.value === '4'"
            >
              <span slot="label">
                <span>待发货<span class="wait_num">0</span></span>/<span>待核销<span class="wait_num">0</span></span>
              </span>
            </el-tab-pane>
            <el-tab-pane
              :label="item.label"
              :name="item.value"
              :key="item.value"
              v-else-if="item.value === '8'"
            >
              <span slot="label">
                <span>退货/退款中<span class="wait_num">0</span></span>
              </span>
            </el-tab-pane>
            <el-tab-pane
              :label="item.label"
              :name="item.value"
              :key="item.value"
              v-else
            >
            </el-tab-pane>
          </template>

        </el-tabs>
        <table>
          <thead>
            <tr>
              <th width="300px">{{$t('order.goods')}}</th>
              <th width="10%">{{$t('order.goodsSn')}}</th>
              <th width="10%">{{$t('order.goodsPrice')}}</th>
              <th width="10%">{{$t('order.goodsNumber')}}</th>
              <th width="10%">{{$t('order.consignee')}}</th>
              <th>{{$t('order.orderTime')}}</th>
              <th width="10%">{{$t('order.orderStatusText')}}</th>
              <th width="10%">{{$t('order.moneyPaid')}}</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td colspan="8"></td>
            </tr>
          </tbody>
          <template v-for="orderItem in orderList">
            <tbody
              :key="orderItem.orderId"
              class="hasborder"
            >
              <tr class="order-tb-head">
                <td colspan="8">
                  <div class="tb-head_box">
                    <div class="left">
                      <span>{{$t('order.orderSn')}}：{{orderItem.orderSn}}</span>
                      <span>{{$t('order.paymentType')}}：
                        <span
                          v-for="(payCode,index) in orderItem.payCodeList"
                          :key="index"
                        >
                          <i :class="payCodeIconClassMap[payCode]"></i>
                        </span>
                      </span>
                      <span>{{$t('order.deliverTypeText')}}：{{deliverTypeMap.get(orderItem.deliverType)}}</span>
                      <span>{{$t('order.goodsTypeText')}}：
                        <span
                          v-for="(goodsType,index) in orderItem.goodsType.split(',')"
                          :key="index"
                        >
                          <template v-if="index != 0">,</template>
                          {{goodsTypeMap.get(Number(goodsType))}}
                        </span>
                      </span>
                    </div>
                    <div class="right">
                      <span class="icon_collect"><i class="el-icon-star-off"></i></span>
                      <span>{{$t('order.remark')}}</span>
                      <span @click="seeDetails(orderItem.orderSn)">{{$t('order.details')}}</span>
                      <span>{{$t('order.comment')}}</span>
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
                        :src="$imageHost+goodsItem.goodsImg"
                        alt=""
                      >
                      <div class="right_info">
                        <div class="goods_name">{{goodsItem.goodsName}}</div>
                        <div class="goods_spec">{{goodsItem.goodsAttr}}</div>
                      </div>
                    </div>
                  </td>
                  <td>{{goodsItem.goodsSn}}</td>
                  <td>{{goodsItem.goodsPrice.toFixed(2)}}</td>
                  <td>{{goodsItem.goodsNumber}}</td>
                  <td
                    v-if="index === 0"
                    :rowspan="orderItem.goods.length"
                  >
                    <p>{{orderItem.consignee}}</p>
                    <p>{{orderItem.mobile}}</p>
                  </td>
                  <td
                    v-if="index === 0"
                    :rowspan="orderItem.goods.length"
                  >
                    {{orderItem.createTime}}
                  </td>
                  <td
                    v-if="index === 0"
                    :rowspan="orderItem.goods.length"
                  >
                    {{orderStatusMap.get(orderItem.orderStatus)}}
                  </td>
                  <td
                    v-if="index === 0"
                    :rowspan="orderItem.goods.length"
                  >
                    <span>
                      {{orderItem.moneyPaid.toFixed(2)}}
                    </span>
                    <span>
                      ({{
                        $t('order.includeExpress')
                      }}
                      {{orderItem.shippingFee.toFixed(2)}}
                      )
                    </span>

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

import {
  list
} from '@/api/admin/orderManage/order.js'

export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination'),
    areaLinkage: () => import('@/components/admin/areaLinkage/areaLinkage.vue')
  },
  data () {
    return {
      orderStatusMap: {},
      goodsTypeMap: {},
      deliverTypeMap: {},
      paymentTypeMap: {},
      payCodeIconClassMap: {
        1: 'el-icon-shopping-cart-full',
        2: 'el-icon-shopping-cart-1',
        3: 'el-icon-shopping-cart-1',
        4: 'el-icon-shopping-cart-full',
        5: 'el-icon-shopping-cart-full',
        6: 'el-icon-shopping-cart-full'
      },
      moreFilters: false,
      pageParams: {
        'totalRows': 1,
        'currentPage': 1,
        'firstPage': 1,
        'prePage': 1,
        'nextPage': 1,
        'lastPage': 1,
        'pageRows': 20,
        'pageCount': 1
      },
      searchParams: {
        searchType: 0,
        pinStatus: [],
        goodsName: '',
        orderSn: '',
        orderStatus: [],
        goodsType: null,
        consignee: '',
        mobile: '',
        createTimeStart: null,
        createTimeEnd: null,
        deliverType: null,
        paymentType: null,
        userName: '',
        source: null,
        tagIds: [],
        storeId: null,
        verifyCode: '',
        specCode: '',
        finishedTimeStart: null,
        finishedTimeEnd: null,
        countryCode: null,
        provinceCode: null,
        cityCode: null,
        districtCode: null
      },
      orderTime: null,
      completeTime: null,
      sourceList: [
      ],
      storeList: [
      ],
      tagList: [
      ],
      tabsOrderStatus: [
        { value: null, label: '全部' },
        { value: '1', label: '待付款' },
        { value: '4', label: '待发货/待核销' },
        { value: '5', label: '已发货' },
        { value: '6', label: '已收货/已核销' },
        { value: '7', label: '已完成' },
        { value: '8', label: '退货退款中' },
        { value: '3', label: '已关闭' },
        { value: '16', label: '追星订单' }
      ],
      orderList: [
      ]
    }
  },
  mounted () {
    console.log('mounted-----------------------')
    // 初始化数据
    this.langDefault()
    this.arrayToMap()
    this.initDataList()
  },
  watch: {
    lang () {
      this.langDefault()
      this.arrayToMap()
    }
  },
  methods: {
    handleClick (data) {
      console.log(data)
    },
    handleAreaData (data) {
      this.searchParams.provinceCode = data.province
      this.searchParams.cityCode = data.city
      this.searchParams.districtCode = data.district
    },
    arrayToMap () {
      this.orderStatusMap = new Map(this.$t('order.orderStatusList'))
      this.goodsTypeMap = new Map(this.$t('order.goodsTypeList'))
      this.deliverTypeMap = new Map(this.$t('order.deliverTypeList'))
      this.paymentTypeMap = new Map(this.$t('order.paymentTypeList'))
    },
    search () {
      if (this.completeTime) {
        this.searchParams.finishedTimeStart = this.completeTime[0] + ' 00:00:00'
        this.searchParams.finishedTimeEnd = this.completeTime[1] + ' 23:59:59'
      }
      if (this.orderTime) {
        this.searchParams.createTimeStart = this.orderTime[0] + ' 00:00:00'
        this.searchParams.createTimeEnd = this.orderTime[1] + ' 23:59:59'
      }
      this.orderList = null
      list(this.searchParams).then(res => {
        this.pageParams = res.content.page
        this.currentPage = res.content.page.currentPage
        this.pageRows = res.content.page.pageRows
        this.pageCount = res.content.page.pageCount
        this.totalRows = res.content.page.totalRows
        this.orderList = res.content.dataList
      }).catch(() => {
      })
    },
    initDataList () {
      this.search()
    },

    seeDetails (orderSn) {
      console.log(orderSn)
      this.$router.push({
        name: 'orderInfo',
        query: {
          orderSn: orderSn
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
        justify-content: space-between;
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
      .wait_num {
        position: relative;
        top: -7px;
        right: 0;
        border-radius: 10px;
        background: #ff9d0e;
        color: #fff;
        line-height: 1;
        font-size: 11px;
        text-align: center;
        padding: 2px 5px;
      }
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
    .search_button_box {
      background-color: #fff;
      text-align: center;
      line-height: 55px;
      > span {
        color: #409eff;
        font-size: 14px;
        margin-left: 150px;
        cursor: pointer;
      }
      > .button_box {
        float: right;
        margin-right: 10px;
      }
    }
  }
  .default_input {
    width: 180px;
  }
}
</style>
