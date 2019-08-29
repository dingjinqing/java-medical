<template>
  <div class="content">
    <div class="main">
      <div class="navBox">
        <statusTab
          v-model="tabIndex"
          :activityName="activityName"
          :standard="true"
        />
        <el-button
          type="primary"
          @click="addReduce()"
        >添加限时降价</el-button>
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
          <template v-for="(item,index) in tableItem">
            <el-table-column
              :prop="item.prop"
              :label="item.label"
              :key="index"
              v-if="item.label === '有效期'"
              width="170"
            >
              <template slot-scope="scope">
                <span v-html="scope.row.startTime+'<br/>至<br/>'+scope.row.endTime"></span>
              </template>
            </el-table-column>
            <el-table-column
              :prop="item.prop"
              :label="item.label"
              :key="index"
              v-else-if="item.label === '操作'"
              width="130"
            >
              <template slot-scope="scope">
                <div class="operation">
                  <el-tooltip
                    class="item"
                    effect="dark"
                    content="编辑"
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
                    content="删除"
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
                    content="停用"
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
                    content="启用"
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
                    content="订单明细"
                    placement="top"
                  >
                    <i class="el-icon-s-order"></i>
                  </el-tooltip>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              :prop="item.prop"
              :label="item.label"
              :key="index"
              v-else
            >
            </el-table-column>
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
import { reducePriceList, updateReducePrice, deleteReducePrice } from '@/api/admin/marketManage/reducePrice.js'
export default {
  components: {
    statusTab: () => import('@/components/admin/status/statusTab'),
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      activityName: '限时降价',
      // 默认显示进行中的活动
      tabIndex: 1,
      currentPage: 1,
      pageParams: {},
      tableData: [],
      loading: false,
      tableItem: [
        { prop: 'name', label: '活动名称' },
        { prop: 'goodsAmount', label: '商品数量' },
        { prop: 'vaildDate', label: '有效期' },
        { prop: 'statusName', label: '活动状态' },
        { prop: 'orderAmount', label: '付款订单数' },
        { prop: 'userAmount', label: '付款用户数' },
        { prop: 'paymentTotalAmount', label: '付款总金额' },
        { prop: '', label: '操作' }
      ]
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
        // TODO: 国际化
        item.vaildDate = `${item.startTime}至${item.endTime}`
        item.statusName = this.getActStatusString(item.status, item.startTime, item.endTime)
      })
      this.tableData = data
    },
    addReduce () {
      this.$router.push({
        name: 'reduce_add_view'
      })
    },
    edit (id) { },
    del (id) {
      let param = {
        'id': id
      }
      this.$confirm('确定删除该限时降价活动?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteReducePrice(param).then((res) => {
          if (res.error === 0) {
            this.$message({
              type: 'success',
              message: '删除成功!'
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
      this.$confirm('确定停用该限时降价活动?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updateReducePrice(param).then((res) => {
          if (res.error === 0) {
            this.$message({
              type: 'success',
              message: '停用成功!'
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
      this.$confirm('确定启用该限时降价活动?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updateReducePrice(param).then((res) => {
          if (res.error === 0) {
            this.$message({
              type: 'success',
              message: '启用成功!'
            })
            this.initDataList()
          }
        })
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
