<!--
* 定金膨胀活动列表
*
* @author 郑保乐
-->
<template>
  <div>
    <wrapper>
      <statusTab
        v-model="param.status"
        :activityName="activityName"
        :standard="false"
      />
      <el-row :gutter="20">
        <el-col :span="4">
          <el-input
            v-model="param.name"
            placeholder="活动名称"
          ></el-input>
        </el-col>
        <el-col
          :span="4"
          :offset="13"
        >
          <el-button
            type="primary"
            @click="loadData"
          >筛选</el-button>
          <el-button
            type="primary"
            style="float:right;"
            @click="gotoAdd"
          >
            添加定金膨胀活动
          </el-button>
        </el-col>
      </el-row>
      <el-row>
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
            prop="presaleName"
            label="活动名称"
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
      </el-row>
      <el-row>
        <el-col
          :offset="14"
          :span="10"
        >
          <!-- pagination -->
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
    </wrapper>
  </div>
</template>
<script>
import wrapper from '@/components/admin/wrapper/wrapper'
import statusTab from '@/components/admin/marketManage/status/statusTab'
import { couldEdit, couldStop, couldStart, couldDelete, getNameById } from '@/components/admin/marketManage/status/status'
import { getPageList, deletePreSale, disablePreSale, enablePreSale, sharePreSale } from '@/api/admin/marketManage/preSale'

export default {

  components: {
    wrapper,
    statusTab
  },
  data () {
    return {
      name: '',
      activityName: '定金膨胀',
      param: {
        status: 0,
        startTime: null,
        endTime: null,
        preStartTime: null,
        preEndTime: null,
        // pagination
        currentPage: 1,
        pageRows: 20
      },
      page: {
        totalRows: 0
      },
      tableData: [{
        id: 1,
        presaleName: '活动名称',
        preStartTime: '2018-12-26 16:29:53',
        preEndTime: '2019-01-05 16:30:27',
        startTime: '2019-01-12 16:30:29',
        endTime: '2019-02-08 16:30:31',
        boughtGoodsQuantity: 1,
        orderQuantity: 1,
        bargainPaidOrderQuantity: 1,
        tailPaidOrderQuantity: 0,
        orderUserQuantity: 1,
        status: 0
      }]
    }
  },
  methods: {
    // 列表查询
    loadData () {
      const { param } = this
      getPageList(param).then(res => {
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
</style>
