<template>
  <div class="content">
    <div class="main">
      <div class="search_box">
        <div class="filters">
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
            <span>{{$t('order.payTime')}}：</span>
            <el-date-picker
              v-model="payTime"
              type="daterange"
              :range-separator="$t('membershipIntroduction.to')"
              :start-placeholder="$t('membershipIntroduction.Starttime')"
              :end-placeholder="$t('membershipIntroduction.Endtime')"
              value-format="yyyy-MM-dd HH:mm:ss"
              :default-time="['00:00:00','23:59:59']"
              size="small"
            >
            </el-date-picker>
          </div>
          <div class="filters_item">
            <span>{{$t('order.payerName')}}：</span>
            <el-input
              v-model="searchParams.userName"
              :placeholder="$t('order.payerName')"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>{{$t('order.store')}}：</span>
            <el-select
              v-model="searchParams.storeId"
              size="small"
              class="default_input"
              filterable
              clearable
            >
              <el-option
                v-for="item in storeList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <span>{{$t('order.orderStatusText')}}：</span>
            <el-select
              v-model="searchParams.orderStatus"
              size="small"
              class="default_input"
              filterable
              clearable
            >
              <el-option
                v-for="item in $t('order.storeStatusList')"
                :key="item[0]"
                :label="item[1]"
                :value="item[0]"
              ></el-option>
            </el-select>
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
        <table>
          <thead>
            <tr>
              <th width="300px">{{$t('order.storeNameText')}}</th>
              <th>{{$t('order.payerName')}}</th>
              <th>{{$t('order.storeTime')}}</th>
              <th>{{$t('order.orderStatusText')}}</th>
              <th>{{$t('order.storeMoneyPaid')}}</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td colspan="5"></td>
            </tr>
          </tbody>
          <template v-for="orderItem in storeOrderList">
            <tbody
              :key="orderItem.orderId"
              class="hasborder"
            >
              <tr class="order-tb-head">
                <td colspan="8">
                  <div class="tb-head_box">
                    <div class="left">
                      <span>{{$t('order.storeOrderSn')}}：{{orderItem.orderSn}}</span>
                      <span>{{$t('order.paymentType')}}：{{$t('order.payTypeObj')[orderItem.payCode]}}</span>
                    </div>
                    <div class="right">
                      <span class="icon_collect"><i
                          :class="{'el-icon-star-off':!orderItem.starFlag,'el-icon-star-on':orderItem.starFlag}"
                          @click="toggleStar(orderItem.orderSn,orderItem.starFlag)"
                        ></i></span>
                      <span @click="addNodes(orderItem.orderSn)">添加备注</span>
                      <span @click="seeDetails(orderItem.orderSn)">{{$t('order.details')}}</span>
                    </div>
                  </div>
                </td>
              </tr>
              <tr class="order-tb-body">
                <td>{{storeMap.get(orderItem.storeId)}}</td>
                <td>{{orderItem.username}}</td>
                <td>{{orderItem.payTime}}</td>
                <td>{{$t('order.storeStatusList')[orderItem.orderStatus][1]}}</td>
                <td>{{orderItem.moneyPaid.toFixed(2)}}</td>
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
    <nodesDialog
      :show.sync="showNodes"
      :orderSn="notesOrderSn"
    />
  </div>
</template>

<script>
import { store,
  star
} from '@/api/admin/orderManage/order.js'
import { allSourceRequest } from '@/api/admin/membershipList.js'

export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination'),
    nodesDialog: () => import('../addNotes')
  },
  data () {
    return {
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
      payTime: null,
      searchParams: {
        orderSn: null,
        userName: null,
        storeId: null,
        orderStatus: null,
        payTimeStart: null,
        payTimeEnd: null
      },
      storeOrderList: [],
      showNodes: false,
      defaultStore: null,
      storeList: [
        {
          value: null,
          label: this.defaultStore
        }
      ],
      storeMap: new Map(),
      notesOrderSn: null
    }
  },
  mounted () {
    console.log('mounted-----------------------')
    // 初始化数据
    this.langDefault()
    this.initDataList()
  },
  watch: {
    lang () {
      this.langDefault()
      this.defaultStore = [
        {
          value: null,
          label: this.$t('order.defaultSelect')
        }
      ]
    },
    payTime (val) {
      this.searchParams.payTimeStart = val ? val[0] : null
      this.searchParams.payTimeEnd = val ? val[1] : null
    }
  },
  methods: {
    initDataList () {
      this.getAllStore()
      this.search()
    },
    search () {
      this.storeOrderList = null
      this.searchParams.currentPage = this.pageParams.currentPage
      this.searchParams.pageRows = this.pageParams.pageRows
      store(this.searchParams).then(res => {
        console.log(res)
        this.pageParams = res.content.page
        this.storeOrderList = res.content.dataList
      }).catch(() => {
      })
    },
    seeDetails (orderSn) {
      this.$router.push({
        name: 'storeOrderInfo',
        query: {
          orderSn: orderSn
        }
      })
    },
    addNodes (orderSn) {
      this.showNodes = true
      this.notesOrderSn = orderSn
    },
    // 获取来源
    getAllStore () {
      allSourceRequest().then(res => {
        this.storeList = this.storeList.concat(res.content)
        res.content.forEach(store => {
          this.storeMap.set(store.value, store.label)
        })
      })
    },
    toggleStar (orderSn, starFlag) {
      let obj = {
        orderSn: [orderSn],
        type: 0,
        starFlag: starFlag === 1 ? 0 : 1
      }
      star(obj).then(res => {
        if (res.error === 0) {
          this.$message.success(starFlag === 1 ? '取消标星成功' : '标星成功')
          this.search()
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
