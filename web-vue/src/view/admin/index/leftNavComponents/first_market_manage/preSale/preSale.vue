<!--
* 定金膨胀活动列表
*
* @author 郑保乐
-->
<template>
  <div class="container">
    <section class="tab_switch">
      <statusTab
        v-model="param.status"
        :activityName="activityName"
        :standard="false"
      />
      <section>
        <div class="tab_info1">
          <div>
            活动名称：
            <el-input
              v-model="param.presaleName"
              placeholder="活动名称"
              style="width:180px"
              size="small"
            ></el-input>
          </div>
          <div class="money_paytime">
            定金支付时间：
            <el-date-picker
              v-model="param.preStartTime"
              type="datetime"
              placeholder="选择开始日期"
              size="small"
              style="width:185px"
            >
            </el-date-picker>
            <span style="margin: 0 5px">至</span>
            <el-date-picker
              v-model="param.preEndTime"
              type="datetime"
              placeholder="选择结束日期"
              size="small"
              style="width:185px"
            >
            </el-date-picker>
          </div>
        </div>
        <div class="tab_info2">
          <span>尾款支付时间：</span>
          <el-date-picker
            v-model="param.startTime"
            type="datetime"
            placeholder="选择开始日期"
            size="small"
            style="width:185px"
          >
          </el-date-picker>
          <span style="margin: 0 5px">至</span>
          <el-date-picker
            v-model="param.endTime"
            type="datetime"
            placeholder="选择结束日期"
            size="small"
            style="width:185px"
          >
          </el-date-picker>
          <el-button
            type='primary'
            size="small"
            class="choose"
            @click="loadData"
          >
            筛选
          </el-button>
        </div>
        <el-button
          size="small"
          type="primary"
          @click="gotoAdd"
          class="add_activity"
        >添加定金膨胀活动</el-button>
      </section>
    </section>

    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableHeader"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="presaleName"
          label="活动名称"
          align="center"
        > </el-table-column>
        <el-table-column
          prop=""
          label="定金支付时间"
          align="center"
        > </el-table-column>
        <el-table-column
          prop=""
          label="尾款支付时间"
          align="center"
        > </el-table-column>
        <el-table-column
          prop="boughtGoodsQuantity"
          label="已购商品数量"
          align="center"
        > </el-table-column>
        <el-table-column
          prop="orderQuantity"
          label="订单数量"
          align="center"
        > </el-table-column>
        <el-table-column
          prop="bargainPaidOrderQuantity"
          label="已付定金订单数"
          align="center"
        > </el-table-column>
        <el-table-column
          prop="tailPaidOrderQuantity"
          label="已付尾款订单数"
          align="center"
        > </el-table-column>
        <el-table-column
          prop="orderUserQuantity"
          label="下单用户数"
          align="center"
        > </el-table-column>
        <el-table-column
          prop="status"
          label="活动状态"
          align="center"
        > </el-table-column>
        <el-table-column
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <el-row>
              <el-button
                size="mini"
                @click="disable(scope.row.id)"
                v-show="couldStop(scope.row)"
              >停用</el-button>
              <el-button
                size="mini"
                @click="enable(scope.row.id)"
                v-show="couldStart(scope.row)"
              >启用</el-button>
              <el-button
                size="mini"
                @click="gotoEdit(scope.row.id)"
                v-show="couldEdit(scope.row)"
              >编辑</el-button>
              <el-button
                size="mini"
                @click="gotoOrderDetail(scope.row.id)"
              >订单明细</el-button>
              <el-button
                size="mini"
                @click="gotoDetail(scope.row.id)"
              >活动明细</el-button>
              <el-button
                size="mini"
                @click="deleteGift(scope.row.id)"
                v-show="couldDelete(scope.row)"
              >删除</el-button>
              <el-button
                size="mini"
                @click="share(scope.row.id)"
              >分享</el-button>
            </el-row>
          </template>
        </el-table-column>
      </el-table>
      <el-row>
        <el-col
          :offset="14"
          :span="10"
        >
          <el-pagination
            @size-change="loadData"
            @current-change="loadData"
            :current-page.sync="param.currentPage"
            :page-size="param.pageRows"
            :total="page.pageRows"
            layout="total, sizes, prev, pager, next, jumper"
          >
          </el-pagination>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import statusTab from '@/components/admin/marketManage/status/statusTab'
import { couldEdit, couldStop, couldStart, couldDelete, getNameById } from '@/components/admin/marketManage/status/status'
import { getPageList, deletePreSale, disablePreSale, enablePreSale, sharePreSale } from '@/api/admin/marketManage/preSale'

export default {

  components: {
    statusTab
  },
  data () {
    return {
      name: '',
      activityName: '定金膨胀',
      startTime: '',
      param: {
        status: 0,
        presaleName: '',
        startTime: null,
        endTime: null,
        preStartTime: null,
        preEndTime: null,
        currentPage: 1,
        pageRows: 20
      },
      page: {
        totalRows: 0
      },
      tableData: []
    }
  },
  methods: {
    // 列表查询
    loadData () {
      const { param } = this
      getPageList(param).then(res => {
        console.log(res, 'query res')
        const { content: { page, dataList } } = res
        this.tableData = dataList
        this.page = page
      })
    },
    // 删除活动
    delete (id) {
      deletePreSale(id).then(r => {
        this.loadData()
        this.success('删除成功')
      })
    },
    // 停用活动
    disable (id) {
      disablePreSale(id).then(r => {
        this.loadData()
        this.success('停用成功')
      })
    },
    // 启用活动
    enable (id) {
      enablePreSale(id).then(r => {
        this.loadData()
        this.success('启用成功')
      })
    },
    gotoAdd () {
      this.$router.push('/admin/home/main/presale/add')
    },
    gotoEdit (id) {
      this.$router.push(`/admin/home/main/presale/edit/${id}`)
    },
    gotoOrderDetail (id) {
      this.$router.push(`/admin/home/main/presale/order_detail/${id}`)
    },
    gotoDetail (id) {
      this.$router.push(`/admin/home/main/presale/detail/${id}`)
    },
    share (id) {
      sharePreSale(id).then(r => {
        // todo share
      })
    },
    getStatus (v) {
      return getNameById(v).name
    },
    success (message) {
      this.$message({
        message,
        type: 'success'
      })
    },
    couldEdit (row) {
      return couldEdit(row)
    },
    couldStop (row) {
      return couldStop(row)
    },
    couldStart (row) {
      return couldStart(row)
    },
    couldDelete (row) {
      return couldDelete(row)
    }
  },
  watch: {
    'param.status' (n, o) {
      this.loadData()
    }
  },
  mounted () {
    this.loadData()
  }
}
</script>
<style lang="scss" scoped>
.container {
  .tab_switch {
    margin: 10px;
    padding: 20px;
    background: #fff;
    font-size: 14px;
    .tab_info1 {
      display: flex;
      margin: 10px 0;
      .money_paytime {
        margin-left: 70px;
      }
    }
    .tab_info2 {
      display: inline-block;
      margin: 10px 0;
      .choose {
        margin-left: 20px;
      }
    }
    .add_activity {
      display: block;
      margin-top: 10px;
    }
  }
  .table_list {
    margin: 0 10px;
    padding: 15px;
    background: #fff;
  }
  /deep/ .tableHeader th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    color: #000;
    padding: 8px 10px;
  }
}
</style>
