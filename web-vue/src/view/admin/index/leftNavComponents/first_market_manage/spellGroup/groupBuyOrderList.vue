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
        header-row-class-name="tableHeader"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="orderSn"
          :label="$t('groupBuy.orderSn')"
          align="center"
        > </el-table-column>
        <el-table-column
          :label="$t('groupBuy.goodsName')"
          align="center"
          width="200px"
        >
          <template slot-scope="scope">
            <el-table
              :data="scope.row.goods"
              :show-header=false
            >
              <el-table-column
                prop="goodsName"
                align="center"
              >
              </el-table-column>
              <el-table-column
                prop="goodsPrice"
                align="center"
              > </el-table-column>
            </el-table>
          </template>
        </el-table-column>
        <el-table-column
          prop="createTime"
          :label="$t('groupBuy.orderTime')"
          align="center"
        > </el-table-column>
        <el-table-column
          prop="username"
          :label="$t('groupBuy.buyerName')"
          align="center"
        > </el-table-column>
        <el-table-column
          prop="consignee"
          :label="$t('groupBuy.consigneeInfo')"
          align="center"
        > </el-table-column>
        <el-table-column
          prop="moneyPaid"
          :label="$t('groupBuy.paymentAmount')"
          align="center"
        > </el-table-column>
        <el-table-column
          prop="orderStatusText"
          :label="$t('groupBuy.orderStatus')"
          align="center"
        > </el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />
    </div>
  </div>
</template>

<script>
import pagination from '@/components/admin/pagination/pagination.vue'
import marketOrderSearchTab from '@/components/admin/marketManage/marketOrderSearchTab.vue'
import { groupBuyOrderList } from '@/api/admin/marketManage/spellGroup.js'

export default {
  components: {
    pagination,
    marketOrderSearchTab
  },
  mounted () {
    if (this.$route.query.id > 0) {
      this.actId = this.$route.query.id
      this.initDataList()
      // 初始化语言
      this.langDefault()
    }
  },
  data () {
    return {
      loading: false,
      pageParams: {},
      requestParams: {},
      tableData: [],
      orderStatusArr: this.$t('groupBuy.orderStatusArr')
    }
  },
  watch: {
    lang () {
      this.orderStatusArr = this.$t('groupBuy.orderStatusArr')
      this.initDataList()
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.requestParams.activityId = this.$route.query.id
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      groupBuyOrderList(this.requestParams).then((res) => {
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
        item.orderStatusText = this.orderStatusArr[item.orderStatus]
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
