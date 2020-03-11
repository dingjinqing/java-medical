<!--
*** 打包一口价-查看活动订单
*
*** @user:zhaoxin
-->
<template>
  <div class="content">
    <div class="search-condition">
      <marketOrderSearchTab
        :requestParams="requestParams"
        @filter="initDataList"
        @export="exportDataList"
      />
    </div>
    <div class="main">
      <div class="table_box">
        <el-table
          :data="tableData"
          style="width:100%;"
          border
          header-row-class-name="tableClss"
        >
          <el-table-column
            prop="orderSn"
            :label="$t('marketCommon.orderSn')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            :label="$t('bargainList.bargainGoods')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            :label="$t('marketCommon.price')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="createTime"
            :label="$t('marketCommon.orderTime')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            :label="$t('marketCommon.orderUserInfo')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            :label="$t('marketCommon.consigneeInfo')"
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

    <!-- 导出数据确认弹窗 -->
    <!-- <exportForm
      :show.sync="showExportConfirm"
      :param="this.requestParams"
    /> -->
  </div>
</template>

<script>
import { activityOrder } from '@/api/admin/marketManage/packagePrice.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination'),
    marketOrderSearchTab: () => import('@/components/admin/marketManage/marketOrderSearchTab.vue')
    // exportForm: () => import('./bargainExportConfirmDialog.vue')
  },
  mounted () {
    this.langDefault()
    if (this.$route.query.id > 0) {
      this.actId = this.$route.query.id
      console.log(this.actId, 'get-id')
      this.orderStatusMap = new Map(this.$t('order.orderStatusList'))
      this.initDataList()
    }
  },
  data () {
    return {
      pageParams: {},
      requestParams: {
        goodsName: null,
        orderSn: null,
        orderStatus: null,
        consignee: null,
        mobile: null,
        createTimeStart: ''
        // areaCode: '',
        // cityCode: '',
        // districtCode: ''
      },
      tableData: [],
      orderStatusMap: {},
      actId: null,

      // 表格原始数据
      originalData: [],
      showExportConfirm: false // 是否展示导出数据弹窗
    }
  },
  methods: {
    initDataList () {
      // if (this.requestParams.createTimeStart) {
      //   let end = this.requestParams.createTimeStart.split(' ', 1)
      //   let endTime = end[0] + ' 23:59:59'
      //   this.requestParams.createTimeEnd = endTime
      // } else {
      //   this.requestParams.createTimeEnd = ''
      // }

      // this.requestParams.activityId = this.actId
      // this.requestParams.currentPage = this.pageParams.currentPage
      // this.requestParams.pageRows = this.pageParams.pageRows

      // let orderStatus = []
      // if (this.requestParams.selectedOrderStatus != null) {
      //   orderStatus.push(this.requestParams.selectedOrderStatus)
      // }
      // this.requestParams.orderStatus = orderStatus
      // if (this.requestParams.createTimeStart) {
      //   console.log(this.requestParams.createTimeStart, 'get data')
      // }
      let obj = {
        activityId: this.actId
      }
      console.log(obj, 'get obj')
      activityOrder(obj).then((res) => {
        if (res.error === 0) {
          console.log(res, 'list-data')
          // this.originalData = res.content.dataList
          // let originalData = JSON.parse(JSON.stringify(this.originalData))
          // this.handleData(originalData)
          // this.pageParams = res.content.page
          // this.loading = false
        }
      })
    },
    // 表格数据处理
    // handleData (data) {
    //   data.map((item, index) => {
    //     item.orderStatus = this.orderStatusMap.get(item.orderStatus)
    //   })
    //   this.tableData = data
    // },
    exportDataList () {
      this.showExportConfirm = true
    }
  },
  watch: {
    // data内变量国际化
    lang () {
      // 重新渲染表格数据
      // let originalData = JSON.parse(JSON.stringify(this.originalData))
      // this.handleData(originalData)
    }
  }
}
</script>

<style lang="scss" scoped>
.search-condition {
  padding: 10px 10px 0;
}
.main {
  padding: 10px;
  .table_box {
    position: relative;
    padding: 15px;
    background-color: #fff;
  }
  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    font-weight: bold;
    color: #000;
    padding: 8px 10px;
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
