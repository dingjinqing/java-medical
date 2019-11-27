<template>
  <div class="content">
    <div class="main">
      <div class="navBox">
        <statusTab
          v-model="tabIndex"
          :activityName="$t('reducePriceList.reducePrice')"
          :standard="true"
        />
        <el-button
          type="primary"
          @click="addReduce()"
        >{{$t('reducePriceList.addReducePrice')}}</el-button>
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
        >

          <el-table-column
            prop="name"
            :label="$t('marketCommon.actName')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="goodsAmount"
            :label="$t('reducePriceList.goodsAmount')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="validDate"
            :label="$t('marketCommon.validDate')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="statusName"
            :label="$t('marketCommon.activityStatus')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="orderAmount"
            :label="$t('reducePriceList.paidOrderNumber')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="userAmount"
            :label="$t('reducePriceList.paidUserNumber')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="paymentTotalAmount"
            :label="$t('reducePriceList.totalMoneyPaid')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            :label="$t('marketCommon.operate')"
            align="center"
          >
            <template slot-scope="scope">
              <div class="operation">
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="$t('marketCommon.edit')"
                  placement="top"
                  v-if="scope.row.status === 1"
                >
                  <i
                    class="el-icon-edit-outline"
                    @click="edit(scope.row.id)"
                  ></i>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="$t('marketCommon.delete')"
                  placement="top"
                  v-else
                >
                  <i
                    @click="del(scope.row.id)"
                    class="el-icon-delete"
                  ></i>
                </el-tooltip>
                <el-tooltip
                  v-if="scope.row.status === 1"
                  class="item"
                  effect="dark"
                  :content="$t('marketCommon.disable')"
                  placement="top"
                >
                  <i
                    @click="disable(scope.row.id)"
                    class="el-icon-remove-outline"
                  ></i>
                </el-tooltip>
                <el-tooltip
                  v-else
                  class="item"
                  effect="dark"
                  :content="$t('marketCommon.enabled')"
                  placement="top"
                >
                  <i
                    @click="enable(scope.row.id)"
                    class="el-icon-check"
                  ></i>
                </el-tooltip>

                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="$t('reducePriceList.orderDetails')"
                  placement="top"
                >
                  <i
                    class="el-icon-s-order"
                    @click="checkOrderList(scope.row.id)"
                  ></i>
                </el-tooltip>
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
import { reducePriceList, updateReducePrice, deleteReducePrice } from '@/api/admin/marketManage/reducePrice.js'
export default {
  components: {
    statusTab: () => import('@/components/admin/marketManage/status/statusTab'),
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      // 默认显示进行中的活动
      tabIndex: 1,
      currentPage: 1,
      pageParams: {},
      tableData: [],
      loading: false
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      let param = {
        'state': parseInt(this.tabIndex),
        'currentPage': 1
      }
      reducePriceList(param).then((res) => {
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          this.pageParams = res.content.page
          this.loading = false
        }
      })
    },
    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        item.validDate = `${item.startTime} ${this.$t('marketCommon.to')} ${item.endTime}`
        item.statusName = this.getActStatusString(item.currentState)
      })
      this.tableData = data
    },
    addReduce () {
      this.$router.push({
        name: 'reduce_add_view'
      })
    },
    edit (id) {
      this.$router.push({
        path: '/admin/home/main/reduce/add',
        query: {
          id: id
        }
      })
    },
    del (id) {
      let param = {
        'id': id
      }
      this.$confirm(this.$t('marketCommon.actDeleteConfirmTip'), this.$t('marketCommon.tip'), {
        confirmButtonText: this.$t('marketCommon.ok'),
        cancelButtonText: this.$t('marketCommon.cancel'),
        type: 'warning'
      }).then(() => {
        deleteReducePrice(param).then((res) => {
          if (res.error === 0) {
            this.$message({
              type: 'success',
              message: this.$t('marketCommon.successfulOperation')
            })
            this.initDataList()
          }
        })
      })
    },
    disable (id) {
      let param = {
        'id': id,
        'status': 0
      }
      this.$confirm(this.$t('marketCommon.actDisableConfirmTip'), this.$t('marketCommon.tip'), {
        confirmButtonText: this.$t('marketCommon.ok'),
        cancelButtonText: this.$t('marketCommon.cancel'),
        type: 'warning'
      }).then(() => {
        updateReducePrice(param).then((res) => {
          if (res.error === 0) {
            this.$message({
              type: 'success',
              message: this.$t('marketCommon.successfulOperation')
            })
            this.initDataList()
          }
        })
      })
    },
    enable (id) {
      let param = {
        'id': id,
        'status': 1
      }
      this.$confirm(this.$t('marketCommon.actEnabledConfirmTip'), this.$t('marketCommon.tip'), {
        confirmButtonText: this.$t('marketCommon.ok'),
        cancelButtonText: this.$t('marketCommon.cancel'),
        type: 'warning'
      }).then(() => {
        updateReducePrice(param).then((res) => {
          if (res.error === 0) {
            this.$message({
              type: 'success',
              message: this.$t('marketCommon.successfulOperation')
            })
            this.initDataList()
          }
        })
      })
    },
    checkOrderList (id) {
      this.$router.push({
        path: '/admin/home/main/reduce/orderList',
        query: {
          id: id
        }
      })
    }
  },
  watch: {
    'tabIndex' (n, o) {
      this.initDataList()
    }
  },
  mounted () {
    this.initDataList()
  }
}
</script>

<style lang="scss" scoped>
.content {
  padding: 10px;
  .main {
    .navBox {
      background-color: #fff;
      padding: 10px;
      margin-bottom: 10px;
    }
    .table_box {
      background-color: #fff;
      padding: 15px;
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
  }
}
</style>
