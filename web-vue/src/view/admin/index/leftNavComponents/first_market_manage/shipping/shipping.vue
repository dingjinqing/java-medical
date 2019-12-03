<template>
  <div class="content">

    <!-- tab -->
    <div class="main">
      <el-tabs
        v-model="tabSwitch"
        @tab-click="initDataList"
        :lazy="true"
      >
        <el-tab-pane
          v-for="(item, index) in tabInfo"
          :key="index"
          :label="item.title"
          :name="item.name"
        >
          <el-button
            type="primary"
            size="small"
            v-if="tableListView"
            @click="addHandler"
          >{{$t('shipping.addShipping')}}</el-button>
        </el-tab-pane>
      </el-tabs>
    </div>

    <!-- 添加 / 编辑 -->
    <addShipping
      :isEdite="isEdite"
      :editId="editId"
      @addShippingSubmit="addShippingSubmit"
      v-if="tableListView===false"
    />

    <!-- 表格 -->
    <div
      class="table_list"
      v-if="tableListView"
    >
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          label="活动名称"
          align="center"
        >
        </el-table-column>
        <el-table-column
          label="创建时间"
          align="center"
        >
        </el-table-column>
        <el-table-column
          label="活动商品"
          align="center"
        >
        </el-table-column>
        <el-table-column
          label="包邮规则"
          align="center"
        >
        </el-table-column>
        <el-table-column
          label="有效期"
          align="center"
        >
        </el-table-column>
        <el-table-column
          label="优先级"
          align="center"
        >
        </el-table-column>
        <el-table-column
          label="活动状态"
          align="center"
        >
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <el-tooltip
                :content="$t('seckill.edit')"
                placement="top"
                v-if="scope.row.currentState !== 4"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-edit-outline"
                  @click="editHandler(scope.row.skId, scope.row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.share')"
                placement="top"
                v-if="scope.row.currentState === 2 || scope.row.currentState === 1"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-share"
                  @click="shareHandler(scope.row.skId)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.stop')"
                placement="top"
                v-if="scope.row.currentState === 2 || scope.row.currentState === 3"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-circle-close"
                  @click="stopHandler(scope.row.skId)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.start')"
                placement="top"
                v-if="scope.row.currentState === 4"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-circle-check"
                  @click="startHandler(scope.row.skId)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.delete')"
                placement="top"
                v-if="scope.row.currentState === 3 || scope.row.currentState === 4"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-delete"
                  @click="deleteHandler(scope.row.skId)"
                ></span>
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
</template>
<script>
// 引入组件
import pagination from '@/components/admin/pagination/pagination'
import shareDialog from '@/components/admin/shareDialog'
import addShipping from './shippingAdd.vue'
// import { getShippingList } from '@/api/admin/marketManage/shipping.js'
export default {
  components: {
    pagination,
    shareDialog,
    addShipping
  },
  data () {
    return {
      tabSwitch: '1',
      tableListView: true, // tab显示隐藏
      tabInfo: [],
      tableData: [], // 表格数据
      pageParams: {}, // 分页
      requestParams: {},
      editId: '', // 编辑的活动id
      isEdite: true // 编辑状态
    }
  },
  watch: {
    lang () {
      this.tabInfo = this.$t('shipping.tabInfo')
      this.initDataList()
    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    this.initDataList()
  },
  methods: {
    // 满包邮列表
    initDataList () {
      this.requestParams.state = [this.tabSwitch]
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      this.closeTabAddGroup()
      // getShippingList(this.requestParams).then((res) => {
      //   if (res.error === 0) {

      //   }
      // })
    },

    // 添加
    addHandler () {
      this.isEdite = false
      this.showTabAddGroup('添加满包邮活动')
    },

    // 编辑
    editHandler (id, row) {
      this.editId = id
      this.isEdite = true
      this.showTabAddGroup('编辑满包邮活动')
    },

    showTabAddGroup (title) {
      if (this.tabSwitch === '6' || this.tabInfo.length > 5) {
        this.closeTabAddGroup()
      }
      this.tabInfo.push({
        title: title,
        name: '6'
      })
      this.tabSwitch = '6'
      this.tableListView = false
    },

    closeTabAddGroup () {
      // 新增标签
      if (this.tabSwitch === '6') {
        return
      }
      // 不是新增
      if (this.tabInfo.length > 5) {
        this.tableListView = true
        this.tabInfo.pop({
          title: this.$t('shipping.editShipping'),
          name: '6'
        })
        console.log('closeTabAddGroup', this.tabInfo)
      }
      return this.tabInfo
    },

    // 保存
    addShippingSubmit () {
      this.tabSwitch = '1'
      this.initDataList()
    },

    // 删除
    deleteHandler (id) {
      this.$confirm(this.$t('seckill.deleteTip'), {
        confirmButtonText: this.$t('seckill.sure'),
        cancelButtonText: this.$t('seckill.cancel'),
        type: 'warning'
      }).then(() => {
        // deleteSeckillList({ skId: id }).then((res) => {
        //   if (res.error === 0) {
        //     this.$message.success({ message: this.$t('seckill.deleteSuccess') })
        //     this.initDataList()
        //   }
        // })
      }).catch(() => {
        this.$message.info({ message: this.$t('seckill.deleteFail') })
      })
    },

    // 分享
    shareHandler (id) {
      this.shareDialog = !this.shareDialog
      // shareSeckillList(id).then((res) => {
      //   if (res.error === 0) {
      //     this.shareImg = res.content.imageUrl
      //     this.sharePath = res.content.pagePath
      //   }
      // })
    },

    // 停用
    stopHandler (id) {
      this.$confirm(this.$t('seckill.stopTip'), {
        confirmButtonText: this.$t('seckill.sure'),
        cancelButtonText: this.$t('seckill.cancel'),
        type: 'warning'
      }).then(() => {
        // updateSeckillList({
        //   skId: id,
        //   status: 0
        // }).then((res) => {
        //   if (res.error === 0) {
        //     this.$message.success({ message: this.$t('seckill.stopSuccess') })
        //     this.initDataList()
        //   }
        // })
      }).catch(() => {
        this.$message.info({ message: this.$t('seckill.stopFail') })
      })
    },

    // 启用
    startHandler (id) {
      this.$confirm(this.$t('seckill.startTip'), {
        confirmButtonText: this.$t('seckill.sure'),
        cancelButtonText: this.$t('seckill.cancel'),
        type: 'warning'
      }).then(() => {
        // updateSeckillList({
        //   skId: id,
        //   status: 1
        // }).then((res) => {
        //   if (res.error === 0) {
        //     this.$message.success({ message: this.$t('seckill.startSuccess') })
        //     this.initDataList()
        //   }
        // })
      }).catch(() => {
        this.$message.info({ message: this.$t('seckill.startFail') })
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
.balanceDialo .el-dialog__body {
  padding-bottom: 0 !important;
}
.balanceDialo .el-dialog__footer {
  border-top: 1px solid #eee;
}
.setUpDialog .el-dialog__body {
  padding-top: 10px !important;
}
.add_coupon {
  float: left;
  margin-left: 65%;
}
.footer {
  padding: 20px 0 20px 20px;
  display: flex;
  justify-content: flex-end;
  span {
    display: block;
    height: 32px;
    line-height: 32px;
    float: left;
  }
}
.search_content {
  width: 220px;
}
.opt {
  text-align: center;
  color: #5a8bff;
  span {
    cursor: pointer;
  }
}
// .shareBox {
//   width: 100%;
//   text-align: center;
//   margin-bottom: 15px;
//   border-bottom: 1px solid #ccc;
// }
</style>
