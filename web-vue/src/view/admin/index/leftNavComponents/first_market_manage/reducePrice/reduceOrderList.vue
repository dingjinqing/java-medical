<template>
  <div class="content">
    <div class="main">
      <div class="filters">
        <marketOrderSearchTab
          :requestParams="requestParams"
          @filter="initDataList"
          @export="exportDataList"
        />
      </div>

      <div class="table_box">
        <el-table
          v-loading="loading"
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="tableData"
          border
          style="width: 100%"
        >
          <el-table-column
            label="活动名称"
            align="center"
          >
            <template>
              <span>{{ this.actName }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="订单号"
            align="center"
          >
            <template slot-scope="scope">
              <span
                class="itemColor"
                @click="orderHandler(scope.row.orderSn)"
              >{{ scope.row.orderSn }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="商品名称"
            align="center"
            width="250px"
          >
            <template slot-scope="scope">
              <div class="fl">
                <img
                  :src="scope.row.goods[0].goodsImg"
                  alt=""
                  style="width: 100%; height: 100%;"
                >
              </div>
              <div class="fr">
                <p><span class="tips">限时降价</span><span>{{scope.row.goods[0].goodsName}}</span></p>
                <p>{{scope.row.goods[0].goodsAttr}}</p>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="单价"
            align="center"
          >
            <template slot-scope="scope">
              <span>{{scope.row.goods[0].goodsPrice}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="下单时间"
            align="center"
            width="160px"
          ></el-table-column>
          <el-table-column
            label="下单人信息"
            align="center"
          >
            <template slot-scope="scope">
              <div @click="userHandler(scope.row.userId)">
                <p class="itemColor">{{ scope.row.username }}</p>
                <p class="itemColor">{{ scope.row.userMobile }}</p>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="收货人信息"
            align="center"
          >
            <template slot-scope="scope">
              <div>
                <p class="itemColor">{{ scope.row.consignee }}</p>
                <p class="itemColor">{{ scope.row.mobile }}</p>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="支付金额"
            align="center"
          >
            <template slot-scope="scope">
              <p style="color: #c09853;">￥{{ scope.row.moneyPaid }}</p>
              <p>(含快递费<span style="color: #c09853;">￥{{ scope.row.shippingFee }}</span>)</p>
              <span></span>
            </template>
          </el-table-column>
          <el-table-column
            prop="orderStatus"
            label="订单状态"
            align="center"
          ></el-table-column>
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
import { download } from '@/util/excelUtil.js'
import { getReducePriceOrderList, reducePriceOrderListExport } from '@/api/admin/marketManage/reducePrice.js'
import marketOrderSearchTab from '@/components/admin/marketManage/marketOrderSearchTab.vue'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination'), marketOrderSearchTab
  },
  mounted () {
    this.langDefault()
    if (this.$route.query.id > 0) {
      this.actId = this.$route.query.id
      this.actName = this.$route.query.actName
      this.initDataList()
    }
  },
  data () {
    return {
      loading: false,
      pageParams: {},
      requestParams: {},
      tableData: [],
      actName: '', // 活动名称
      actId: null,
      orderList: this.$t('order.orderStatusList') // 订单状态
    }
  },
  watch: {
    // 国际化
    lang () { }
  },
  methods: {
    // 初始化数据
    initDataList () {
      this.loading = true
      this.requestParams.activityId = this.actId
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      // 订单状态
      var arr = []
      if (this.requestParams.selectedOrderStatus || this.requestParams.selectedOrderStatus === 0) {
        arr[0] = this.requestParams.selectedOrderStatus
      }
      if (arr !== []) {
        this.requestParams.orderStatus = arr
      }
      // 下单时间
      if (this.requestParams.createTimeStart) {
        this.requestParams.createTimeEnd = this.requestParams.createTimeStart.replace('00:00:00', '23:59:59')
      } else {
        this.requestParams.createTimeEnd = null
      }
      getReducePriceOrderList(this.requestParams).then((res) => {
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          this.pageParams = res.content.page
          this.loading = false
        }
      })
    },

    // 表格数据处理
    handleData (data) {
      data.forEach(item => {
        this.orderList.forEach(val => {
          if (item.orderStatus === val[0]) {
            item.orderStatus = val[1]
          }
        })
      })
      this.tableData = data
    },

    // 导出数据
    exportDataList () {
      reducePriceOrderListExport(this.requestParams).then(res => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : '限时降价订单导出.xlsx'
        download(res, decodeURIComponent(fileName))
      })
    },

    // 下单人信息
    userHandler (id) {
      this.$router.push({
        path: '/admin/home/main/membershipInformation',
        query: {
          userId: id
        }
      })
    },

    // 跳转订单详情
    orderHandler (orderSn) {
      this.$router.push({
        path: '/admin/home/main/orders/info',
        query: {
          orderSn: orderSn
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.main {
  padding: 10px;
  .filters {
    display: flex;
    line-height: 32px;
    margin-bottom: 10px;
    background-color: #fff;
    padding: 10px 15px 0 0;
    flex-wrap: wrap;
    .filters_item {
      max-width: 465px;
      display: flex;
      margin-left: 15px;
      margin-bottom: 10px;
      > span {
        min-width: 100px;
        font-size: 14px;
      }
    }
  }
  .table_box {
    background-color: #fff;
    padding: 15px;
    .goods_price {
      border-bottom: 1px solid #ebeef5;
      line-height: 62px;
      &:last-of-type {
        border-bottom: 0;
      }
    }
    .goods_info {
      display: flex;
      border-bottom: 1px solid #ebeef5;
      padding: 8px;
      &:last-of-type {
        border-bottom: 0;
      }
      > img {
        width: 45px;
        height: 45px;
        margin-right: 5px;
      }
      > .goods_name {
        flex: 1;
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
    .operation {
      display: flex;
      justify-content: space-around;
      > .item {
        font-size: 22px;
        color: #66b1ff;
        cursor: pointer;
      }
    }
  }
  .default_input {
    width: 150px;
  }
  .date_picker {
    width: 185px;
  }
  .address_choose {
    margin-left: 10px;
  }
  /deep/ .no_padding {
    padding: 0;
    .cell {
      padding: 0;
    }
  }
  /deep/ .areaLinkage {
    .el-select {
      margin-left: 10px;
      &:first-of-type {
        margin-left: 0;
      }
    }
  }
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.itemColor {
  color: #5a8bff;
  cursor: pointer;
}
.fl {
  float: left;
  width: 32%;
  height: 80px;
  text-align: center;
  line-height: 50px;
  border: 1px solid #ccc;
  font-size: 0;
}
.fr {
  float: left;
  margin-left: 3%;
  width: 65%;
  height: 80px;
  position: relative;
  // margin-left: 12px;
  font-size: 12px;
  // text-align: left;
}
.fr p {
  width: 100%;
  display: -webkit-box;
  -ms-text-overflow: ellipsis;
  text-overflow: ellipsis;
  overflow: hidden;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  margin: 0;
  text-align: left;
}
.fr p .tips {
  display: inline-block;
  border: 1px #ef8115 solid;
  padding: 0px 3px;
  color: #ef8115;
  border-radius: 2px;
  font-size: 12px;
  margin-right: 3px;
}
</style>
