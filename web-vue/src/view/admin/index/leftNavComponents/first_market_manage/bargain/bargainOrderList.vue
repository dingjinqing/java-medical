<template>
  <div class="content">
    <div class="main">
      <div class="filters">
        <div class="filters_item"><span>商品名称：</span>
          <el-input
            v-model="requestParams.goodsName"
            placeholder="商品名称"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item"><span>订单号：</span>
          <el-input
            v-model="requestParams.orderSn"
            placeholder="订单号"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item"><span>订单状态：</span>
          <el-select
            v-model="requestParams.orderStatus"
            placeholder="请选择"
            size="small"
            class="default_input"
          >
            <el-option
              v-for="item in orderStatus"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </div>
        <div class="filters_item"><span>收货人姓名：</span>
          <el-input
            v-model="requestParams.consignee"
            placeholder="收货人姓名"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item"><span>收货人手机号：</span>
          <el-input
            v-model="requestParams.mobile"
            placeholder="收货人手机号"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item"><span>下单时间：</span>
          <el-date-picker
            v-model="requestParams.createTimeStart"
            type="datetime"
            placeholder="下单时间"
            size="small"
            class="date_picker"
          ></el-date-picker>
        </div>
        <div class="filters_item"><span>收货地址：</span>
          <areaLinkage
            @areaData="handleAreaData"
            style="width:365px;"
          />
        </div>
        <div class="filters_item">
          <el-button
            @click="initDataList()"
            type="primary"
            size="small"
          >筛选</el-button>
          <el-button
            type="default"
            size="small"
          >导出表格</el-button>
        </div>
      </div>
      <div class="table_box">

        <el-table
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
            >
              <template slot-scope="scope">
                <div
                  v-for="goodsItem in scope.row.goods.slice(0, 1)"
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
              :prop="item.prop"
              :label="item.label"
              :key="item.index"
              v-else-if="item.index === 3"
            >
              <template slot-scope="scope">
                <div
                  v-for="goodsItem in scope.row.goods.slice(0, 1)"
                  :key="goodsItem.goodsId"
                  class="goods_info"
                >
                  <span class="goods_price">{{goodsItem.goodsPrice}}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              :prop="item.prop"
              :label="item.label"
              :key="item.index"
              v-else-if="item.index === 5"
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
              v-else-if="item.index === 6"
            >
              <template slot-scope="scope">
                <span>{{scope.row.consignee}}</span><br><span>{{scope.row.mobile}}</span>
              </template>
            </el-table-column>
            <el-table-column
              :prop="item.prop"
              :label="item.label"
              :key="item.index"
              v-else-if="item.index === 7"
            >
              <template slot-scope="scope">
                <span>￥{{scope.row.moneyPaid}}</span><br><span>(含快递￥{{scope.row.shippingFee}})</span>
              </template>
            </el-table-column>
            <el-table-column
              :prop="item.prop"
              :label="item.label"
              :key="item.index"
              v-else
            ></el-table-column>
          </template>
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
import { getBargainOrderList } from '@/api/admin/marketManage/bargain.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination'),
    areaLinkage: () => import('@/components/admin/areaLinkage/areaLinkage.vue')
  },
  mounted () {
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
        { index: 1, prop: 'orderSn', label: '订单编号' },
        { index: 2, prop: '', label: '砍价商品' },
        { index: 3, prop: 'goodsPrice', label: '单价' },
        { index: 4, prop: 'createTime', label: '下单时间' },
        { index: 5, prop: '', label: '下单人信息' },
        { index: 6, prop: '', label: '收货人信息' },
        { index: 7, prop: 'moneyPaid', label: '支付金额' },
        { index: 8, prop: 'orderStatus', label: '订单状态' }
      ],
      orderStatus: [
        { value: -1, label: '全部订单' },
        { value: 1, label: '待付款' },
        { value: 2, label: '订单取消' },
        { value: 3, label: '订单关闭' },
        { value: 4, label: '代发货/待核销' },
        { value: 5, label: '已发货' },
        { value: 6, label: '已收货/已自提' },
        { value: 7, label: '订单完成' },
        { value: 8, label: '退货中' },
        { value: 9, label: '退货完成' },
        { value: 10, label: '退款中' },
        { value: 11, label: '退款完成' },
        { value: 12, label: '送礼完成' }
      ],
      actId: null
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.requestParams.activityId = this.actId
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      getBargainOrderList(this.requestParams).then((res) => {
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          this.pageParams = res.content.page
          this.loading = false
        }
      })
    },
    // 表格数据处理
    handleData (data) {
      this.tableData = data
    },
    goodsInfo (data) {
      if (data.columnIndex === 2) {
        return 'no_padding'
      } else {
        return ''
      }
    },
    handleAreaData (data) {
      this.provinceCode = data.province
      this.cityCode = data.city
      this.districtCode = data.district
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
</style>
