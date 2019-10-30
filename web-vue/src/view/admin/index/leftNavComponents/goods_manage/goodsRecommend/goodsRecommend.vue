<template>
  <div class="goodsRecommend">
    <div class="filters">
      <div class="filter_left">
        <el-input
          v-model="recommendTitle"
          placeholder="请输入模板名称"
          class="default_input"
          size="small"
        ></el-input>
        <el-button
          type="primary"
          size="small"
          @click="initDataList"
        >筛选</el-button>
      </div>
      <el-button
        type="primary"
        size="small"
        @click="addRecommend"
      >新建商品推荐模板</el-button>
    </div>
    <div class="table_box">
      <el-table
        v-loading="loading"
        :data="dataList"
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
          prop="recommendName"
          label="模板名称"
        ></el-table-column>
        <el-table-column
          prop="updateTime"
          label="更新时间"
        ></el-table-column>
        <el-table-column label="应用页面">
          <template slot-scope="scope">
            <div class="used_page">
              <div
                class="page_left"
                v-if="scope.row.recommendUsePage.length"
              >
                <p
                  v-for="(item,index) in scope.row.recommendUsePage"
                  :key="index"
                >{{usedPage[item]}}</p>
              </div>
              <div class="page_right">
                <span @click="editItem(scope.row)">设置</span>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="模板状态">
          <template slot-scope="scope">
            {{scope.row.status | statusFilter}}
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <div class="operation">
              <p @click="edit(scope.row.id)">编辑</p>
              <p @click="editStatus(scope.row)">{{scope.row.status === 0 ? '停用' : '启用'}}</p>
              <p @click="del(scope.row.id)">删除</p>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination
      :page-params.sync="pageParams"
      @pagination="initDataList"
    />
    <editRecommendDialog
      :show.sync="showDialog"
      :editData="editData"
      @refresh="initDataList"
    />
  </div>
</template>
<script>
import { getRecommendList, delRecommend, updateRecommend } from '@/api/admin/goodsManage/goodsRecommend/goodsRecommend'
export default {
  components: {
    editRecommendDialog: () => import('./editRecommendDialog'),
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      recommendTitle: null,
      loading: false,
      showDialog: false,
      editData: null,
      dataList: null,
      pageParams: {},
      usedPage: {
        cart: '购物车页',
        orderlist: '订单列表页',
        bargainitem: '砍价活动页',
        groupbuyitem: '参团活动页',
        search: '商品列表页',
        payment: '支付成功页',
        order_complete: '订单完成页',
        new_search: '商品搜索页',
        item: '商品详情页'
      }
    }
  },
  mounted () {
    this.initDataList()
  },
  methods: {
    initDataList () {
      // this.dataList = [
      //   { recommendTitle: '智能推荐', updateTime: '2018-10-23 11:11:11', isStart: true, usedPage: ['item', 'search', 'new_search'] },
      //   { recommendTitle: '智能推荐', updateTime: '2018-10-23 11:11:11', isStart: true, usedPage: ['item', 'search', 'new_search'] },
      //   { recommendTitle: '智能推荐', updateTime: '2018-10-23 11:11:11', isStart: false, usedPage: ['item', 'order_complete', 'new_search'] },
      //   { recommendTitle: '智能推荐', updateTime: '2018-10-23 11:11:11', isStart: false, usedPage: ['item', 'search', 'new_search'] },
      //   { recommendTitle: '智能推荐', updateTime: '2018-10-23 11:11:11', isStart: true, usedPage: ['item', 'groupbuyitem', 'new_search'] },
      //   { recommendTitle: '智能推荐', updateTime: '2018-10-23 11:11:11', isStart: true, usedPage: ['item', 'search', 'new_search'] },
      //   { recommendTitle: '智能推荐', updateTime: '2018-10-23 11:11:11', isStart: true, usedPage: ['item', 'search', 'new_search'] },
      //   { recommendTitle: '智能推荐', updateTime: '2018-10-23 11:11:11', isStart: false, usedPage: ['item', 'search', 'new_search'] },
      //   { recommendTitle: '智能推荐', updateTime: '2018-10-23 11:11:11', isStart: true },
      //   { recommendTitle: '智能推荐', updateTime: '2018-10-23 11:11:11', isStart: true }
      // ]
      this.loading = true
      let obj = {
        recommendName: this.recommendTitle,
        ...this.pageParams
      }
      getRecommendList(obj).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.pageParams = res.content.page
          this.dataList = res.content.dataList
          this.loading = false
        }
      })
    },
    addRecommend () {
      this.$router.push({
        name: 'addRecommend'
      })
    },
    edit () {

    },
    del (id) {
      this.$confirm('确认删除此条商品推荐吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        delRecommend(id).then(res => {
          console.log(res)
          if (res.error === 0) {
            this.dataList = this.dataList.filter(item => { return item.id !== id })
            this.pageParams.totalRows = this.dataList.length
          }
        })
      }).catch(() => {
      })
    },
    editItem (rowData) {
      this.editData = rowData
      this.showDialog = true
    },
    editStatus (rowData) {
      let msg = rowData.status === 1 ? '是否启用？' : '是否停用'
      this.$confirm(msg, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        let obj = {
          ...rowData,
          status: rowData.status === 1 ? 0 : 1
        }
        updateRecommend(obj).then(res => {
          console.log(res)
          if (res.error === 0) {
            let idx = this.dataList.findIndex(item => { return item.id === rowData.id })
            this.dataList[idx].status = rowData.status === 1 ? 0 : 1
            let msg = this.dataList[idx].status === 0 ? '启用成功' : '停用成功'
            this.$message.success({
              message: msg,
              duration: 2000
            })
          }
        })
      })
    }
  },
  filters: {
    statusFilter (value) {
      return value === 0 ? '已启用' : '已停用'
    }
  }
}
</script>
<style lang="scss" scoped>
.goodsRecommend {
  padding: 10px;
}
.filters {
  display: flex;
  padding: 10px;
  background-color: #fff;
  .filter_left {
    flex: 1;
  }
}
.table_box {
  background-color: #fff;
  padding: 10px;
  margin-top: 10px;
}
.used_page {
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  .page_left {
    display: flex;
    flex-direction: column;
  }
  .page_right {
    color: #409eff;
    cursor: pointer;
  }
}
.operation {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  > p {
    cursor: pointer;
    color: #409eff;
  }
}
.default_input {
  width: 180px;
}
</style>
