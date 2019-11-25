<template>
  <div class="content">
    <div class="main">
      <marketOrderSearchTab
        :requestParams="requestParams"
        @filter="initDataList"
        @export="exportDataList"
      />

      <div class="table_box">
        <el-table
          v-loading="loading"
          :data="tableData"
          style="width:100%;"
          border
          :header-cell-style="{
            'background-color':'#f5f5f5',
            'color': 'none',
            'text-align':'center',
            'border':'none'
          }"
          :cell-style="{
            'text-align':'center'
          }"
          :cell-class-name="goodsInfo"
        >
          <el-table-column
            prop="orderSn"
            :label="$t('marketCommon.orderSn')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            :label="$t('firstSpecial.firstSpecialGoods')"
            align="center"
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
            :label="$t('marketCommon.price')"
            align="center"
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
            prop="createTime"
            :label="$t('marketCommon.price')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            :label="$t('marketCommon.orderUserInfo')"
            align="center"
          >
            <template
              slot-scope="scope"
              @click="jumpUserInfo(scope.row.userId)"
            >
              <span>{{scope.row.username}}</span><br><span>{{scope.row.userMobile}}</span>
            </template>
          </el-table-column>
          <el-table-column
            :label="$t('marketCommon.consigneeInfo')"
            align="center"
          >
            <template slot-scope="scope">
              <span>{{scope.row.consignee}}</span><br><span>{{scope.row.mobile}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="moneyPaid"
            :label="$t('marketCommon.moneyPaid')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="orderStatus"
            :label="$t('marketCommon.orderStatus')"
            align="center"
          >
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
import { getFirstSpecialOrderList } from '@/api/admin/marketManage/firstSpecial.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination'),
    marketOrderSearchTab: () => import('@/components/admin/marketManage/marketOrderSearchTab.vue')
  },
  mounted () {
    this.langDefault()
    if (this.$route.query.id > 0) {
      this.actId = this.$route.query.id
      this.orderStatusMap = new Map(this.$t('order.orderStatusList'))
      this.initDataList()
    }
  },
  data () {
    return {
      loading: false,
      pageParams: {},
      requestParams: {
        goodsName: null,
        orderSn: null,
        selectedOrderStatus: null,
        consignee: null,
        mobile: null,
        createTimeStart: ''
      },
      tableData: [],
      orderStatusMap: {},
      actId: null,

      // 表格原始数据
      originalData: []
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.requestParams.activityId = this.actId
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows

      let orderStatus = []
      if (this.requestParams.selectedOrderStatus != null) {
        orderStatus.push(this.requestParams.selectedOrderStatus)
      }
      this.requestParams.orderStatus = orderStatus
      getFirstSpecialOrderList(this.requestParams).then((res) => {
        if (res.error === 0) {
          this.originalData = res.content.dataList
          let originalData = JSON.parse(JSON.stringify(this.originalData))
          this.handleData(originalData)
          this.pageParams = res.content.page
          this.loading = false
        }
      })
    },
    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        item.orderStatus = this.orderStatusMap.get(item.orderStatus)
      })
      this.tableData = data
    },
    goodsInfo (data) {
      if (data.columnIndex === 2) {
        return 'no_padding'
      } else {
        return ''
      }
    },
    exportDataList () {
      alert(11)
    }
  },
  watch: {
    // data内变量国际化
    lang () {
      // 重新渲染表格数据
      let originalData = JSON.parse(JSON.stringify(this.originalData))
      this.handleData(originalData)
    }
  }
}
</script>

<style lang="scss" scoped>
.main {
  padding: 10px;
  .table_box {
    background-color: #fff;
    padding: 15px;
    margin-top: 10px;
    font-weight: bold;
    color: #000;
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
    width: 175px;
  }
  .date_picker {
    width: 175px;
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
