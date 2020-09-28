
<template>
  <el-dialog
    title="关联订单列表"
    :visible.sync="showDialog"
    :close-on-click-modal="false"
    width="50%"
    center
  >
    <el-table
      class="version-manage-table"
      header-row-class-name="tableClss"
      :data="tableData"
      border
      style="width: 100%"
    >
      <template slot="empty">
        <tableEmpty />
      </template>
      <el-table-column
        label="订单号"
        align="center"
      >
        <template slot-scope="scope">
          <span
            class="fontStyle"
            @click="orderHandler(scope.row.orderSn)"
          >{{scope.row.orderSn}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop=""
        label="返利到账时间"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop=""
        label="返利金额"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop=""
        label="提现金额"
        align="center"
      >
      </el-table-column>
    </el-table>

    <pagination
      :page-params.sync="pageParams"
      @pagination="initData()"
    />
  </el-dialog>

</template>

<script>
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  props: {
    tuneUp: Boolean
  },
  data () {
    return {
      showDialog: false,
      tableData: [],
      pageParams: {}
    }
  },
  watch: {
    tuneUp (newval) {
      this.showDialog = true
      this.initData()
    }
  },
  mounted () {
    this.initData()
  },
  methods: {
    // 初始化
    initData () {

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
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.fontStyle {
  color: #5a8bff;
  cursor: pointer;
}
</style>
