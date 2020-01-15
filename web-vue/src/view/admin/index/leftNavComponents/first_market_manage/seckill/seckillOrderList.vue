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
          prop="username"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="this.$t('seckill.consignee')"
          prop="consignee"
          align="center"
        ></el-table-column>
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
    areaLinkage
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
        1: '待付款',
        2: '订单取消',
        3: '订单关闭',
        4: '代发货/待核销',
        5: '已发货',
        6: '已收货/已自提',
        7: '订单完成',
        8: '退货中',
        9: '退货完成',
        10: '退款中',
        11: '退款完成',
        12: '送礼完成'
      },
      orderStatusMap: {},
      createTime: '' // 创建时间
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
      if (this.requestParams.selectedOrderStatus) {
        this.requestParams.orderStatus = []
        this.requestParams.orderStatus.push(this.requestParams.selectedOrderStatus)
      }
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
      console.log('订单状态', this.orderStatusArr)

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
      this.$confirm('此操作将导出数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message.success({ message: '导出成功' })
      }).catch(() => {
        this.$message.info({ message: '已取消导出' })
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
/deep/ .areaLinkage {
  .el-select {
    margin-left: 10px;
    &:first-of-type {
      margin-left: 0;
    }
  }
}
</style>
