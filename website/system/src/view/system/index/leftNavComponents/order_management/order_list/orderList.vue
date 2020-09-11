<template>
  <div class="content">
    <div class="main">
      <div class="search_box">
        <div class="filters">
          <div class="filters_item">
            <span>买家昵称：</span>
            <el-input
              v-model="params.userName"
              placeholder="请输入"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>下单日期：</span>
            <el-date-picker
              v-model="payTime"
              type="daterange"
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              value-format="yyyy-MM-dd HH:mm:ss"
              :default-time="['00:00:00','23:59:59']"
              size="small"
            >
            </el-date-picker>
          </div>
          <div class="filters_item">
            <span class="fil_span">订单来源：</span>
            <el-select
              v-model="params.shopId"
              size="small"
              @change="getShop"
              class="timeSelect"
              filterable
              clearable
            >
              <el-option
                v-for="item in shopList"
                :key="item.shopId"
                :label="item.shopName"
                :value="item.shopId"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <span>配送方式：</span>
            <el-select
              v-model="params.deliverType"
              size="small"
              class="default_input"
              filterable
              clearable
            >
              <el-option
                v-for="(item,index) in deliver"
                :key="index"
                :label="item"
                :value="index"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item" style="margin-left:40px;">
            <el-button
              type="primary"
              size="small"
              @click="initDataList"
            >查询</el-button>
          </div>
        </div>
      </div>
      <div class="table_box">
        <el-tabs
          v-model="params.orderStatus2"
          @tab-click="handleClick"
        >
          <template v-for="item in tabsOrderStatus">
            <!-- <el-tab-pane
              :label="item.label"
              :name="item.value"
              :key="item.value"
              v-if="item.value === '3'"
            >
              <span slot="label">
                <span>待发货<span class="wait_num">{{count['1']}}</span></span>/<span>待核销<span class="wait_num">{{count['2']}}</span></span>
              </span>
            </el-tab-pane> -->
            <el-tab-pane
              :label="item.label"
              :name="item.value"
              :key="item.value"
            >
            </el-tab-pane>
          </template>
        </el-tabs>
        <el-table
          :data='tableData'
          style="width:100%"
          border
          :header-cell-style="{
            'background-color':'#f5f5f5',
            'text-align':'center',
            'border':'none',
            'color': '#000'
          }"
          :cell-style="{
            'text-align':'center'
          }"
        >
          <el-table-column
            prop='orderSn'
            label='订单编号'
          ></el-table-column>
          <el-table-column label='订单状态'>
            <template v-slot='scope'>
              <span>{{ scope.row.orderStatus | status }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop='username'
            label='买家昵称'
          ></el-table-column>
          <el-table-column
            prop='consignee'
            label='收货人'
          ></el-table-column>
          <el-table-column label='配送方式'>
            <template v-slot='scope'>
              <span>{{scope.row.deliverType == 1 ? '自提' : '快递'}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop='shopName'
            label='订单来源'
          ></el-table-column>
          <el-table-column
            prop='createTime'
            label='下单时间'
          ></el-table-column>
          <el-table-column label='订单金额'>
            <template v-slot='scope'>
              <span>￥{{scope.row.moneyPaid}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop='createTime'
            label='备注'
          ></el-table-column>
          <el-table-column label='操作'>
            <template slot-scope="scope">
              <div class="operation">
                <a
                  href="javaScript:void(0);"
                  @click='toDetail(scope.row.orderId)'
                >详情</a>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { getShopList } from '@/api/admin/orderManage/salesReport.js'
import { getMedicalOrder } from '@/api/admin/orderManage/order.js'
export default {
  components: {
    pagination: () => import('@/components/system/pagination/pagination')
  },
  data () {
    return {
      that: this,
      payTime: null,
      params: {
        orderSn: null,
        shopId: null,
        orderStatus: [],
        createTimeStart: null,
        createTimeEnd: null,
        searchType: 0,
        pinStatus: '',
        orderStatus2: '-1',
        goodsType: [],
        userName: ''
      },
      tabsOrderStatus: [
        { value: '-1', label: '全部' },
        { value: '0', label: '待付款' },
        { value: '14', label: '待审核' },
        { value: '3', label: '待发货' },
        { value: '4', label: '已发货' },
        { value: '6', label: '已完成' },
        { value: '10', label: '退款' }
      ],
      deliver: ['快递', '自提'],
      tableData: null,
      filterOrderStatus: null,
      notesOrderSn: null,
      count: {},
      shopList: null,
      pageParams: {
        currentPage: 1,
        pageRows: 20,
        totalRows: null
      }
    }
  },
  mounted () {
    this.getShop()
    this.initDataList()
  },
  watch: {
    lang () {
      this.defaultStore = [
        {
          value: null,
          label: this.$t('order.defaultSelect')
        }
      ]
    },
    payTime (val) {
      this.params.createTimeStart = val ? val[0] : null
      this.params.createTimeEnd = val ? val[1] : null
    },
    'filterOrderStatus': {
      handler (val) {
        if ([null, 0, 3, 4, 6, 10, 14].includes(val)) {
          this.params.orderStatus2 = val === null ? '-1' : String(val)
        } else {
          this.params.orderStatus2 = null
        }
      },
      immediate: true
    }
  },
  methods: {
    handleClick (data) {
      this.filterOrderStatus = Number(data.name)
      if (data.name === '-1' || data.name === '30') this.filterOrderStatus = null
      this.initDataList()
    },
    initDataList () {
      this.params.orderStatus = this.filterOrderStatus !== null ? [this.filterOrderStatus] : []
      let params = Object.assign({}, this.params, this.pageParams)
      getMedicalOrder(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.tableData = res.content.list.dataList
          this.pageParams = res.content.list.page
        }
      }).catch(err => console.log(err))
    },
    getShop () {
      getShopList(this.params.shopId).then(res => {
        if (res.error !== 0) {
          this.$message.error({ message: res.message })
          return
        }
        this.shopList = res.content
        this.initDataList()
      })
    }
  },
  filters: {
    status: function (val) {
      if (!val) return
      let allStatus = [
        { value: '5', label: '已收货/已自提' },
        { value: '7', label: '售后中' },
        { value: '8', label: '售后完成' },
        { value: '2', label: '已关闭' },
        { value: '30', label: '追星订单' },
        { value: '-1', label: '全部' },
        { value: '0', label: '待付款' },
        { value: '14', label: '待审核' },
        { value: '15', label: '已审核' },
        { value: '3', label: '待发货' },
        { value: '4', label: '已发货' },
        { value: '6', label: '已完成' },
        { value: '1', label: '客户已取消' },
        { value: '10', label: '退款' }
      ]
      let news = allStatus.filter(item =>
        Number(item.value) === val
      )
      if (news[0]) return news[0].label
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
