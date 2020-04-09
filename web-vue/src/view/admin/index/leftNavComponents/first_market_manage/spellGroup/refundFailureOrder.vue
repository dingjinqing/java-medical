<!--
* 拼团退款失败订单页面
*
* @author:赵鑫
-->
<template>
  <div class="refundFailureOrder">
    <wrapper class="refundFailureOrder_main">
      <ul>
        <li class="liItem">
          <div class="liNav">
            <span>{{$t('order.goodsName')}}</span>
            <el-input
              v-model="searchParams.goodsName"
              :placeholder="$t('order.goodsName')"
              size="small"
              class="inputWidth"
            ></el-input>
          </div>
          <div class="liNav">
            <span>{{$t('order.orderSn')}}</span>
            <el-input
              v-model="searchParams.orderSn"
              :placeholder="$t('order.orderSn')"
              size="small"
              class="inputWidth"
            ></el-input>
          </div>
          <div class="liNav">
            <span>{{$t('order.orderStatusText')}}</span>
            <el-select
              size="small"
              v-model="searchParams.orderStatus"
              :placeholder="$t('order.defaultSelect')"
              class="inputWidth"
              filterable
              multiple
            >
              <el-option
                v-for="item in $t('order.orderStatusList')"
                :key="item[0]"
                :label="item[1]"
                :value="item[0]"
              >
              </el-option>
            </el-select>
          </div>
        </li>
        <li class="liItem">
          <div class="liNav">
            <span>{{$t('order.consigneeName')}}</span>
            <el-input
              v-model="searchParams.consignee"
              :placeholder="$t('order.consigneeName')"
              size="small"
              lass="inputWidth"
            ></el-input>
          </div>
          <div class="liNav">
            <span>{{$t('order.mobile')}}</span>
            <el-input
              v-model="searchParams.mobile"
              :placeholder="$t('order.mobile')"
              size="small"
              class="inputWidth"
            ></el-input>
          </div>
        </li>
        <li class="liItem">
          <div class="liNav date">
            <span>{{$t('order.orderTime')}}</span>
            <el-date-picker
              v-model="orderTime"
              class="pickerWidth"
              size="small"
              type="datetimerange"
              :range-separator="$t('marketCommon.to')"
              :start-placeholder="$t('marketCommon.startTime')"
              :end-placeholder="$t('marketCommon.endTime')"
            >
            </el-date-picker>
          </div>
          <div
            class="liNav"
            style="margin-left:180px"
          >
            <span>{{$t('order.deliverTypeText')}}</span>
            <el-select
              v-model="searchParams.deliverType"
              :placeholder="$t('order.defaultSelect')"
              size="small"
              class="inputWidth"
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
        </li>
      </ul>
      <ul
        class="uls"
        v-if='!arrorFlag'
      >
        <li class="liItem">
          <div class="liNav">
            <span>{{$t('order.userName')}}</span>
            <el-input
              v-model="searchParams.userName"
              :placeholder="$t('order.userName')"
              size="small"
              class="inputWidth"
            ></el-input>
          </div>
          <div class="liNav">
            <span>{{$t('order.userSource')}}</span>
            <el-select
              v-model="searchParams.source"
              size="small"
              :placeholder="$t('order.defaultSelect')"
              class="inputWidth"
            >
              <el-option
                v-for="item in sourceList"
                :key="item.value"
                :value="item.value"
                :label="item.label"
              >
              </el-option>
            </el-select>
          </div>
          <div class="liNav">
            <span>{{$t('order.verifyCode')}}</span>
            <el-input
              v-model="searchParams.verifyCode"
              :placeholder="$t('order.verifyCode')"
              size="small"
              class="inputWidth"
            ></el-input>
          </div>
        </li>
        <li class="liItem">
          <div class="liNav">
            <span>{{$t('order.store')}}</span>
            <el-select
              size="small"
              v-model="searchParams.storeId"
              :placeholder="$t('order.defaultSelect')"
              class="inputWidth"
            >
              <el-option
                v-for="item in storeList"
                :key="item.value"
                :value="item.value"
                :label="item.label"
              >
              </el-option>
            </el-select>
          </div>
          <div class="liNav">
            <span>{{$t('order.tag')}}</span>
            <el-select
              v-model="searchParams.tagIds"
              multiple
              filterable
              allow-create
              default-first-option
              size="small"
              :multiple-limit=3
              :placeholder="$t('order.tagDescribe')"
              class="inputWidth"
            >
              <el-option
                v-for="item in tagList"
                :key="item.value"
                :value="item.value"
                :label="item.label"
              >
              </el-option>
            </el-select>
          </div>
        </li>
        <li class="liItem">
          <div class="liNav date">
            <span>{{$t('order.completeTime')}}</span>
            <el-date-picker
              v-model="completeTime"
              class="pickerWidth"
              size="small"
              type="datetimerange"
              :range-separator="$t('membershipIntroduction.to')"
              :start-placeholder="$t('membershipIntroduction.Starttime')"
              :end-placeholder="$t('membershipIntroduction.Endtime')"
            >
            </el-date-picker>
          </div>
          <div class="liNav address">
            <span>{{$t('order.shippingAddress')}}</span>
            <areaLinkage
              @areaData="handleAreaData"
              style="width:365px;"
            />
          </div>
        </li>
      </ul>
      <ul>
        <li class="liItem">
          <div class="liNav">
            <span>{{$t('order.paymentType')}}</span>
            <el-select
              size="small"
              v-model="searchParams.paymentType"
              :placeholder="$t('order.defaultSelect')"
              class="inputWidth"
            >
              <el-option
                v-for="item in $t('order.paymentTypeList')"
                :key="item[0]"
                :label="item[1]"
                :value="item[0]"
              >
              </el-option>
            </el-select>
          </div>
          <div class="liNav">
            <span>{{$t('order.specCode')}}</span>
            <el-input
              v-model="searchParams.specCode"
              :placeholder="$t('order.specCode')"
              size="small"
              class="inputWidth"
            ></el-input>
          </div>
        </li>
        <li class="liItem">
          <div class="liNav">
            <span>{{$t('groupBuy.orderSource')}}</span>

          </div>
          <div class="liNav">
            <el-button
              type="primary"
              size="small"
              @click="initDataList"
            >{{$t('order.filter')}}
            </el-button>
            <el-button size="small  ">{{$t('order.export')}}</el-button>
          </div>
        </li>
      </ul>

      <ul class="arrowUl">
        <li>
          <div @click="handleToChangeArror()">
            <div
              v-if="arrorFlag"
              style="color:rgb(90, 139, 255);cursor:pointer"
            >{{$t('membershipIntroduction.more')}}&nbsp;<img :src="ArrowArr[0].img_1"></div>
            <div
              v-if="!arrorFlag"
              style="color:rgb(90, 139, 255);cursor:pointer"
            >{{$t('membershipIntroduction.retract')}}&nbsp;<img :src="ArrowArr[1].img_2"></div>
          </div>
        </li>
      </ul>
    </wrapper>

    <wrapper>
      <div class="table_box">
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
                        :src="$imageHost+'/image/admin/icon_jia.png'"
                        alt=""
                      >
                      <div class="right_info">
                        <div class="goods_name">{{goodsItem.goodsName}}</div>
                        <div class="goods_spec">{{goodsItem.goodsAttr}}</div>
                      </div>
                    </div>
                  </td>
                  <td>{{goodsItem.goodsSn}}</td>
                  <td>{{goodsItem.goodsPrice}}</td>
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
                    {{orderItem.orderStatus}}
                  </td>
                  <td
                    v-if="index === 0"
                    :rowspan="orderItem.goods.length"
                  >
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

    </wrapper>
  </div>
</template>

<script>
import { refundFailOrderList } from '@/api/admin/marketManage/spellGroup.js'
import wrapper from '@/components/admin/wrapper/wrapper'
import areaLinkage from '@/components/admin/areaLinkage/areaLinkage.vue'

export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination'),
    wrapper,
    areaLinkage
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
        totalRows: 1,
        currentPage: 1,
        firstPage: 1,
        prePage: 1,
        nextPage: 1,
        lastPage: 1,
        pageRows: 20,
        pageCount: 1
      },
      searchParams: {
        activityId: this.$route.query.id,
        pinStatus: [2],
        searchType: 0,
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
        { value: '1', label: '未完成' },
        { value: '2', label: '未完成1' },
        { value: '3', label: '未完成2' },
        { value: '4', label: '未完成3' }
      ],
      storeList: [
        { value: '1', label: '未完成' },
        { value: '2', label: '未完成1' },
        { value: '3', label: '未完成2' },
        { value: '4', label: '未完成3' }
      ],
      tagList: [
        { value: '1', label: '未完成' },
        { value: '2', label: '未完成1' },
        { value: '3', label: '未完成2' },
        { value: '4', label: '未完成3' }
      ],
      orderList: [],
      arrorFlag: true,
      ArrowArr: [
        {
          img_1: this.$imageHost + '/image/admin/show_more.png'
        },
        {
          img_2: this.$imageHost + '/image/admin/hid_some.png'
        }
      ]
    }
  },
  watch: {
    lang () {
      this.arrayToMap()
    }
  },
  mounted () {
    // 初始化
    this.langDefault()
    this.initDataList()
    // if (this.$route) {
    //   console.log(this.$route, 'gets')
    // }
    console.log('init-data')
  },
  methods: {
    // 改变箭头事件
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    },
    initDataList (data) {
      this.search()
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
      this.searchParams.pageRows = this.pageParams.pageRows
      this.searchParams.currentPage = this.pageParams.currentPage
      refundFailOrderList(this.searchParams).then(res => {
        this.pageParams = res.content.page
        this.searchParams.pageRows = this.pageParams.pageRows
        this.searchParams.currentPage = this.pageParams.currentPage
        this.orderList = res.content.dataList
      }).catch(() => {
      })
    },
    handleAreaData (data) {
      this.searchParams.provinceCode = data.province
      this.searchParams.cityCode = data.city
      this.searchParams.districtCode = data.district
    },
    seeDetails (orderSn) {
      console.log(orderSn)
      this.$router.push({
        name: 'orderInfo',
        query: {
          orderSn: orderSn
        }
      })
    },
    arrayToMap () {
      this.orderStatusMap = new Map(this.$t('order.orderStatusList'))
      this.goodsTypeMap = new Map(this.$t('order.goodsTypeList'))
      this.deliverTypeMap = new Map(this.$t('order.deliverTypeList'))
      this.paymentTypeMap = new Map(this.$t('order.paymentTypeList'))
    }

  }
}

</script>
<style lang="scss" scoped>
.refundFailureOrder {
  font-size: 14px;

  .refundFailureOrder_main {
    padding: 10px 25px;

    ul {
      .liItem {
        display: flex;

        .liNav {
          display: flex;
          width: 340px;
          margin-bottom: 20px;

          span {
            display: block;
            width: 85px;
            line-height: 30px;
            height: 30px;
            text-align: right;
            color: #333;
            margin-right: 25px;
          }

          .inputWidth {
            width: 150px;
          }
        }

        .date {
          width: 500px;
        }

        .address {
          width: 500px;
          margin-left: 100px;

          span {
            width: 85px;
          }

          .areaLinkage {
            width: 415px;

            /deep/ .el-select {
              margin-right: 5px;
            }
          }
        }
      }
    }

    .arrowUl {
      margin-top: 15px;
      display: flex;
      justify-content: center;
    }
  }

  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    color: #000;
    padding: 8px 10px;
  }

  .table_list {
    position: relative;
  }
}

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
</style>
