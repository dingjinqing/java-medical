<template>
  <div class="content">

    <div class="main">
      <marketOrderSearchTab
        :requestParams="requestParams"
        @filter="initDataList"
        @export="exportDataList"
      />
    </div>

    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          :label="$t('lotteryDraw.orderSn')"
          align="center"
        >
          <template slot-scope="scope">
            <span
              class="linkStyle"
              @click="orderHandler(scope.row.orderSn)"
            >{{scope.row.orderSn}}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.goodsId')"
          align="center"
        >
          <template slot-scope="scope">
            <div class="goodImge">
              <div>
                <img :src="$imageHost+'/'+scope.row.goodsImg">
              </div>
              <div class="name">{{scope.row.goodsName}}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.isGroup')"
          align="center"
        >
          <template slot-scope="scope">
            <span v-if="scope.row.grouped === true">{{ $t('lotteryDraw.isYes') }}</span>
            <span v-if="scope.row.grouped === false">{{ $t('lotteryDraw.isNo') }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.consignee')"
          prop="consigneeRealName"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.isWinDraw')"
          align="center"
        >
          <template slot-scope="scope">
            <span v-if="scope.row.isWinDraw === true">{{ $t('lotteryDraw.isYes') }}</span>
            <span v-if="scope.row.isWinDraw === false">{{ $t('lotteryDraw.isNo') }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.createTime')"
          prop="createTime"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.codeCount')"
          prop="codeCount"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.orderStatusName')"
          prop="orderStatusName"
          align="center"
        ></el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />
    </div>

  </div>
</template>
<script>
// 引入组件
import marketOrderSearchTab from '@/components/admin/marketManage/marketOrderSearchTab.vue'
import pagination from '@/components/admin/pagination/pagination.vue'
import { orderLotteryList } from '@/api/admin/marketManage/lotteryDraw.js'
export default {

  components: {
    marketOrderSearchTab,
    pagination
  },
  data () {
    return {
      loading: false,
      pageParams: {},
      requestParams: {},
      tableData: [],
      // 订单状态
      orderStatusArr: {
        null: '全部订单',
        0: '待付款',
        1: '订单取消',
        2: '订单关闭',
        3: '待发货/待核销',
        4: '已发货',
        5: '已收货/已自提',
        6: '订单完成',
        7: '售后中',
        8: '售后完成',
        9: '待接单',
        10: '已接单-取件中',
        11: '已取件-配送中'
      }
    }
  },
  watch: {
    lang () {
      this.initDataList()
    }
  },
  mounted () {
    if (this.$route.query.id > 0) {
      this.initDataList()
    }
  },
  methods: {
    // 筛选
    initDataList () {
      this.loading = true
      var obj = {}
      obj.groupDrawId = this.$route.query.id
      obj.currentPage = this.pageParams.currentPage
      obj.pageRows = this.pageParams.pageRows
      console.log(this.requestParams)
      if (this.requestParams.goodsName) {
        obj.goodsName = this.requestParams.goodsName
      }
      if (this.requestParams.orderSn) {
        obj.orderSn = this.requestParams.orderSn
      }
      if (this.requestParams.selectedOrderStatus !== undefined) {
        // obj.orderStatusName = this.requestParams.selectedOrderStatus
        obj.orderStatusName = this.orderStatusArr[this.requestParams.selectedOrderStatus]
      }
      if (this.requestParams.consignee) {
        obj.consigneeName = this.requestParams.consignee
      }
      if (this.requestParams.mobile) {
        obj.mobile = this.requestParams.mobile
      }
      if (this.requestParams.createTimeStart) {
        obj.createTime = this.requestParams.createTimeStart
      }
      if (this.requestParams.provinceCode) {
        obj.provinceCode = this.requestParams.provinceCode
      }
      if (this.requestParams.cityCode) {
        obj.cityCode = this.requestParams.cityCode
      }
      if (this.requestParams.districtCode) {
        obj.districtCode = this.requestParams.districtCode
      }
      console.log(obj)
      orderLotteryList(obj).then((res) => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
          this.loading = false
        }
      })
    },

    // 导出数据
    exportDataList () {
      this.$confirm('此操作将导出数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message.success({ message: '导出成功' })
      }).catch(() => {
        this.$message.info({ message: '已取消导出' })
      })
    },

    // 跳转订单详情
    orderHandler (orderSn) {
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
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    background-color: #fff;
    padding: 15px;
    .wrapper {
      .el-button {
        margin-left: 5px;
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
.table_list {
  position: relative;
  margin-top: 10px;
  background-color: #fff;
  padding: 15px;
}
.inputWidth {
  width: 175px;
}
.el-form-item {
  margin-bottom: 1px;
}
.el-row {
  margin-bottom: 14px !important;
}
.el-main {
  padding: inherit;
}
.goodImge {
  display: flex;
}
.goodImge img {
  width: 50px;
  height: 50px;
  line-height: 50px;
  border: 1px solid #ccc;
}
.goodImge .name {
  width: 115px;
  height: 40px;
  text-overflow: ellipsis;
  overflow: hidden;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  display: -webkit-box;
  margin-left: 12px;
  text-align: left;
}
.linkStyle {
  color: #5a8bff;
  cursor: pointer;
}
</style>
