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
            prop=""
            label="活动名称"
            align="center"
          ></el-table-column>
          <el-table-column
            label="订单号"
            align="center"
          >
            <template slot-scope="scope">
              <span class="itemColor">{{ scope.row.orderSn }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="商品名称"
            align="center"
            width="250px"
          >
            <!-- <template slot-scope="scope">
              <div class="fl">
                <img
                  :src="$imageHost+'/image/admin/icon_jia.png'"
                  alt=""
                  style="width: 100%; height: 100%;"
                >
              </div>
              <div class="fr">
                <p><span class="tips">限时降价</span>商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述商品描述</p>
                <p>规格scale:大规;规格tast:甜</p>
                <div></div>
              </div>
            </template> -->
          </el-table-column>
          <el-table-column
            prop=""
            label="单价"
            align="center"
          ></el-table-column>
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

        <!-- <el-table
          v-loading="loading"
          :data="tableData"
          style="width:100%;"
          border
          :header-cell-style="{
            'background-color':'#f5f5f5',
            'text-align':'center',
            'border':'none'
          }"
          :cell-style="{
            'text-align':'center'
          }"
          :cell-class-name="goodsInfo"
        >
          <template v-for="item in tableLabel">

            <el-table-column
              :prop="item.prop"
              :label="item.label"
              :key="item.index"
              v-if="item.index === 2"
              width="400"
            >
              <el-table-column
                :label="$t('marketCommon.goodsName')"
                width="300"
                cell-style="{
                  'padding':0
                }"
              >
                <template slot-scope="scope">
                  <div
                    v-for="goodsItem in scope.row.goods"
                    :key="goodsItem.goodsId"
                    class="goods_info"
                  >
                    <img
                      :src="$imageHost+'/image/admin/icon_jia.png'"
                      alt=""
                      class="goods_img"
                    >
                    <span class="goods_name">{{goodsItem.goodsName}}</span>
                  </div>
                </template>
              </el-table-column>
              <el-table-column
                :label="$t('marketCommon.price')"
                width="100"
              >
                <template slot-scope="scope">
                  <div
                    class="goods_price"
                    v-for="goodsItem in scope.row.goods"
                    :key="goodsItem.goodsId"
                  >
                    {{goodsItem.goodsPrice}}
                  </div>
                </template>
              </el-table-column>
            </el-table-column>
            <el-table-column
              :prop="item.prop"
              :label="item.label"
              :key="item.index"
              v-else-if="item.index === 4"
            >
              <template
                slot-scope="scope"
                @click="jumpUserInfo(scope.row.userId)"
              >
                <span>{{scope.row.username}}</span><br><span>{{scope.row.userMobile}}</span>
              </template>
            </el-table-column>
            <el-table-column
              :prop="item.prop"
              :label="item.label"
              :key="item.index"
              v-else-if="item.index === 5"
            >
              <template slot-scope="scope">
                <span>{{scope.row.consignee}}</span><br><span>{{scope.row.mobile}}</span>
              </template>
            </el-table-column>
            <el-table-column
              :prop="item.prop"
              :label="item.label"
              :key="item.index"
              v-else-if="item.index === 6"
            >
              <template slot-scope="scope">
                <span>￥{{scope.row.moneyPaid}}</span><br><span>({{$t('reducePriceList.expressDelivery')}}￥{{scope.row.shippingFee}})</span>
              </template>
            </el-table-column>
            <el-table-column
              :prop="item.prop"
              :label="item.label"
              :key="item.index"
              v-else
            ></el-table-column>
          </template>
        </el-table> -->
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { getReducePriceOrderList } from '@/api/admin/marketManage/reducePrice.js'
import marketOrderSearchTab from '@/components/admin/marketManage/marketOrderSearchTab.vue'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination'), marketOrderSearchTab
  },
  mounted () {
    this.langDefault()
    if (this.$route.query.id > 0) {
      this.actId = this.$route.query.id
      this.initDataList()
    }
  },
  data () {
    return {
      loading: false,
      pageParams: {},
      requestParams: {},
      tableData: [],
      tableLabel: [
        { index: 1, prop: 'orderSn', label: this.$t('marketCommon.orderSn') },
        { index: 2, prop: '', label: this.$t('reducePriceList.goodsInfo') },
        { index: 3, prop: 'createTime', label: this.$t('marketCommon.orderTime') },
        { index: 4, prop: '', label: this.$t('marketCommon.orderUserInfo') },
        { index: 5, prop: '', label: this.$t('marketCommon.consigneeInfo') },
        { index: 6, prop: 'moneyPaid', label: this.$t('marketCommon.moneyPaid') },
        { index: 7, prop: 'orderStatus', label: this.$t('marketCommon.orderStatus') }
      ],

      actId: null
    }
  },
  watch: {
    // 国际化
    lang () {
      this.tableLabel = [
        { index: 1, prop: 'orderSn', label: this.$t('marketCommon.orderSn') },
        { index: 2, prop: '', label: this.$t('reducePriceList.goodsInfo') },
        { index: 3, prop: 'createTime', label: this.$t('marketCommon.orderTime') },
        { index: 4, prop: '', label: this.$t('marketCommon.orderUserInfo') },
        { index: 5, prop: '', label: this.$t('marketCommon.consigneeInfo') },
        { index: 6, prop: 'moneyPaid', label: this.$t('marketCommon.moneyPaid') },
        { index: 7, prop: 'orderStatus', label: this.$t('marketCommon.orderStatus') }
      ]
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.requestParams.activityId = this.actId
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
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
      // this.tableData = data
    },
    goodsInfo (data) {
      if (data.columnIndex === 1 || data.columnIndex === 2) {
        return 'no_padding'
      } else {
        return ''
      }
    },
    handleAreaData (data) {
      this.provinceCode = data.province
      this.cityCode = data.city
      this.districtCode = data.district
    },
    exportDataList () {
      alert(11)
    },

    // 下单人信息
    userHandler (id) {

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
}
</style>
