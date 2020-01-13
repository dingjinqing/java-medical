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
          prop="name"
          :label="$t('shipping.name')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="startTime"
          :label="$t('shipping.startTime')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="typeText"
          :label="$t('shipping.typeText')"
          align="center"
        >
          <template slot-scope="scope">
            <div v-if="scope.row.type === 1">{{ $t('shipping.typeAll') }}</div>
            <div v-if="scope.row.type === 0">{{ $t('shipping.typeNum') }}</div>
          </template>
        </el-table-column>
        <el-table-column
          prop="ruleText"
          :label="$t('shipping.ruleText')"
          align="center"
        >
          <template slot-scope="scope">
            <div
              v-for="(item, index) in scope.row.ruleList"
              :key="index"
            >
              <div v-if="item.conType === 0">{{ $t('shipping.ruleTip1') }} {{ item.money }} {{ $t('shipping.ruleTip2') }}{{ $t('shipping.ruleTip4') }}；<br></div>
              <div v-if="item.conType === 1">{{ $t('shipping.ruleTip1') }} {{ item.num }} {{ $t('shipping.ruleTip3') }}{{ $t('shipping.ruleTip4') }}；<br></div>
              <div v-if="item.conType === 2">{{ $t('shipping.ruleTip1') }} {{ item.money }} {{ $t('shipping.ruleTip2') }}{{ $t('shipping.ruleTip4') }}，{{ $t('shipping.ruleTip1') }} {{ item.num }} {{ $t('shipping.ruleTip3') }}{{ $t('shipping.ruleTip4') }}；<br></div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="expireTypeText"
          :label="$t('shipping.expireTypeText')"
          align="center"
        >
          <template slot-scope="scope">
            <div v-if="scope.row.expireType === 0">{{scope.row.startTime}}<br>{{ $t('shipping.to') }}<br>{{scope.row.endTime}}</div>
            <div v-if="scope.row.expireType === 1">{{ $t('shipping.expireTypeAll') }}</div>
          </template>
        </el-table-column>
        <el-table-column
          prop="level"
          :label="$t('shipping.level')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="statusText"
          :label="$t('shipping.statusText')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          :label="$t('shipping.option')"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <el-tooltip
                :content="$t('seckill.edit')"
                placement="top"
                v-if="scope.row.currentStatus === 2 || scope.row.currentStatus === 1"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-edit-outline"
                  @click="editHandler(scope.row.id, scope.row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.share')"
                placement="top"
                v-if="scope.row.currentStatus === 2 || scope.row.currentStatus === 1"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-share"
                  @click="shareHandler(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.stop')"
                placement="top"
                v-if="scope.row.currentStatus === 2 || scope.row.currentStatus === 1"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-circle-close"
                  @click="stopHandler(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.start')"
                placement="top"
                v-if="scope.row.currentStatus === 4"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-circle-check"
                  @click="startHandler(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.delete')"
                placement="top"
                v-if="scope.row.currentStatus === 3 || scope.row.currentStatus === 4"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-delete"
                  @click="deleteHandler(scope.row.id)"
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

    <!-- 分享弹窗 -->
    <shareDialog
      :show="shareDialog"
      :imgPath="shareImg"
      :pagePath="sharePath"
      @close="shareDialog=false"
    />

  </div>
</template>
<script>
// 引入组件
import pagination from '@/components/admin/pagination/pagination'
import shareDialog from '@/components/admin/shareDialog'
import addShipping from './shippingAdd.vue'
import { getShippingList, deleteShipping, changeShipping, shareShipping } from '@/api/admin/marketManage/shipping.js'
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
      shareDialog: false, // 分享弹窗
      shareImg: '',
      sharePath: '',
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
      this.requestParams.navType = this.tabSwitch
      this.requestParams.page = {
        currentPage: this.pageParams.currentPage,
        pageRows: this.pageParams.pageRows
      }
      this.closeTabAddGroup()
      getShippingList(this.requestParams).then((res) => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
          this.tableData.map((item, index) => {
            item.statusText = this.getActStatusString(item.currentStatus)
          })
        }
      })
    },

    // 添加
    addHandler () {
      this.isEdite = false
      this.showTabAddGroup(this.$t('shipping.addShipping'))
    },

    // 编辑
    editHandler (id, row) {
      this.editId = id
      this.isEdite = true
      this.showTabAddGroup(this.$t('shipping.editShipping'))
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
        deleteShipping({ id: id }).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('seckill.deleteSuccess') })
            this.initDataList()
          }
        })
      }).catch(() => {
        this.$message.info({ message: this.$t('seckill.deleteFail') })
      })
    },

    // 分享
    shareHandler (id) {
      this.shareDialog = !this.shareDialog
      shareShipping({ id: id }).then((res) => {
        if (res.error === 0) {
          this.shareImg = res.content.imageUrl
          this.sharePath = res.content.pagePath
        }
      })
    },

    // 停用
    stopHandler (id) {
      this.$confirm(this.$t('seckill.stopTip'), {
        confirmButtonText: this.$t('seckill.sure'),
        cancelButtonText: this.$t('seckill.cancel'),
        type: 'warning'
      }).then(() => {
        changeShipping({
          id: id
          // status: 0
        }).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('seckill.stopSuccess') })
            this.initDataList()
          }
        })
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
        changeShipping({
          id: id
          // status: 1
        }).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('seckill.startSuccess') })
            this.initDataList()
          }
        })
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
</style>
