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
          :label="this.$t('seckill.activityName')"
          prop="name"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="this.$t('seckill.orderSn')"
          prop="orderSn"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="this.$t('seckill.goodsName')"
          prop="goodsName"
          align="center"
        >
        </el-table-column>
        <el-table-column
          :label="this.$t('seckill.goodsPrice')"
          prop="goodsPrice"
          align="center"
        >
        </el-table-column>
        <el-table-column
          :label="this.$t('seckill.createTime')"
          prop="createTime"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="this.$t('seckill.username')"
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
          :label="this.$t('seckill.consignee')"
          align="center"
        >
          <template slot-scope="scope">
            <span>{{scope.row.consignee}}</span><br><span>{{scope.row.mobile}}</span>
          </template></el-table-column>
        <el-table-column
          :label="this.$t('seckill.moneyPaid')"
          prop="moneyPaid"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="this.$t('seckill.orderStatusText')"
          prop="orderStatusText"
          align="center"
        >
        </el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />
    </div>
    <!-- 导出数据确认弹窗 -->
    <exportForm
      :show.sync="showExportConfirm"
      :param="this.requestParams"
    />
  </div>
</template>
<script>
// 引入组件
import marketOrderSearchTab from '@/components/admin/marketManage/marketOrderSearchTab.vue'
import pagination from '@/components/admin/pagination/pagination.vue'
import areaLinkage from '@/components/admin/areaLinkage/areaLinkage.vue'
import { orderSeckillList } from '@/api/admin/marketManage/seckill.js'
export default {

  components: {
    marketOrderSearchTab,
    pagination,
    areaLinkage,
    exportForm: () => import('./seckillOrderExportConfirmDialog.vue')
  },
  data () {
    return {
      loading: false,
      pageParams: {},
      requestParams: {},
      tableData: [],
      orderStatusMap: {},
      createTime: '', // 创建时间
      showExportConfirm: false // 是否展示导出数据弹窗
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
      this.orderStatusMap = new Map(this.$t('order.orderStatusList'))
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.requestParams.activityId = this.$route.query.id
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
      console.log(this.requestParams)
      orderSeckillList(this.requestParams).then((res) => {
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
        item.orderStatusText = this.orderStatusMap.get(item.orderStatus)
        item.name = this.$route.query.name
        item.goods.forEach(val => {
          item.goodsPrice = val.goodsPrice
          item.goodsName = val.goodsName
        })
      })
      this.tableData = data
    },

    getOrderStatusText (index) {
      this.orderStatus.forEach(item => {
        if (item.value === index) {
          return item.label
        }
      })
    },

    // 导出数据
    exportDataList () {
      this.showExportConfirm = true
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
/deep/ .areaLinkage {
  .el-select {
    margin-left: 10px;
    &:first-of-type {
      margin-left: 0;
    }
  }
}
</style>
