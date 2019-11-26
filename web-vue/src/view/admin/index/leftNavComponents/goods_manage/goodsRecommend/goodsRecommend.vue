<template>
  <div class="goodsRecommend">
    <div class="filters">
      <div class="filter_left">
        <el-input
          v-model="recommendTitle"
          :placeholder="$t('recommend.filterPlaceholder')"
          class="default_input"
          size="small"
        ></el-input>
        <el-button
          type="primary"
          size="small"
          @click="initDataList"
        >{{$t('recommend.filter')}}</el-button>
      </div>
      <el-button
        type="primary"
        size="small"
        @click="addRecommend"
      >{{$t('recommend.new')}}</el-button>
    </div>
    <div class="table_box">
      <el-table
        v-loading="loading"
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="dataList"
        style="width:100%;"
        border
      >
        <el-table-column
          prop="recommendName"
          :label="$t('recommend.templateName')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="updateTime"
          :label="$t('recommend.updateTime')"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('recommend.applicationPage')"
          align="center"
        >
          <template slot-scope="scope">
            <div class="used_page">
              <div
                class="page_left"
                v-if="scope.row.recommendUsePage.length"
              >
                <p
                  v-for="(item,index) in scope.row.recommendUsePage"
                  :key="index"
                >{{$t('recommend.pageList')[item]}}</p>
              </div>
              <div class="page_right">
                <el-tooltip
                  :content="$t('recommend.setting')"
                  placement="top"
                >
                  <span
                    class="el-icon-edit-outline operateSpan"
                    @click="editItem(scope.row)"
                  ></span>
                </el-tooltip>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('recommend.templateStatus')"
          align="center"
        >
          <template slot-scope="scope">
            {{scope.row.status === 0 ? $t('recommend.activated') : $t('recommend.terminated')}}
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('recommend.operating')"
          align="center"
        >
          <template slot-scope="scope">
            <el-tooltip
              :content="$t('recommend.edit')"
              placement="top"
            >
              <span
                class="el-icon-edit-outline operateSpan"
                @click="edit(scope.row.id)"
              ></span>
            </el-tooltip>
            <el-tooltip
              :content="scope.row.status === 0 ? $t('recommend.disable') : $t('recommend.enable')"
              placement="top"
            >
              <span
                class="el-icon-circle-close operateSpan"
                @click="editStatus(scope.row)"
              ></span>
            </el-tooltip>
            <el-tooltip
              :content="$t('recommend.delete')"
              placement="top"
            >
              <span
                class="el-icon-delete operateSpan"
                @click="del(scope.row.id)"
              ></span>
            </el-tooltip>
            <!-- <div class="operation">
              <p @click="edit(scope.row.id)">{{$t('recommend.edit')}}</p>
              <p @click="editStatus(scope.row)">{{scope.row.status === 0 ? $t('recommend.disable') : $t('recommend.enable')}}</p>
              <p @click="del(scope.row.id)">{{$t('recommend.delete')}}</p>
            </div> -->
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
      pageParams: {}
    }
  },
  mounted () {
    this.initDataList()
    this.langDefault()
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
    edit (id) {
      this.$router.push({
        name: 'addRecommend',
        query: {
          id: id
        }
      })
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
.operateSpan {
  font-size: 22px;
  color: #5a8bff;
  cursor: pointer !important;
}
</style>
