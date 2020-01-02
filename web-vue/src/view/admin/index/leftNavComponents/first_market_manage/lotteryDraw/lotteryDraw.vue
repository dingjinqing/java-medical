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
        ></el-tab-pane>
      </el-tabs>

      <div v-if="tableListView">
        <el-form label-width="100px">
          <el-form-item
            label="活动名称："
            class="item"
          >
            <el-input
              size="small"
              placeholder="请输入活动名称"
              v-model="requestParams.activityName"
              class="inputWidth"
              clearabl
            ></el-input>
          </el-form-item>
          <el-form-item
            label="活动有效期："
            class="item"
          >
            <el-date-picker
              type="datetime"
              :placeholder="$t('gift.startTime')"
              size="small"
              v-model="requestParams.startTime"
              value-format="yyyy-MM-dd HH:mm:ss"
              class="date_picker inputWidth"
            ></el-date-picker>
            {{ $t('gift.to') }}
            <el-date-picker
              type="datetime"
              :placeholder="$t('gift.endTime')"
              size="small"
              v-model="requestParams.endTime"
              value-format="yyyy-MM-dd HH:mm:ss"
              class="date_picker inputWidth"
            ></el-date-picker>
          </el-form-item>
          <el-button
            type="primary"
            size="small"
            class="item"
            style="margin-left: 10px;"
          >筛选</el-button>
        </el-form>
      </div>

      <div>
        <el-button
          type="primary"
          size="small"
          v-if="tableListView"
          @click="addLotteryDraw"
        >添加拼团抽奖活动</el-button>
      </div>
    </div>

    <!-- 添加 / 编辑 -->
    <addLotteryDraw
      :isEdite="isEdite"
      :editId="editId"
      @addLotterySubmit="addLotterySubmit"
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
          label="活动名称"
          align="center"
        ></el-table-column>
        <el-table-column
          label="有效期"
          align="center"
          width="160"
        >
          <template slot-scope="scope">
            {{scope.row.startTime}}<br>至<br>{{scope.row.endTime}}
          </template>
        </el-table-column>
        <el-table-column
          prop="statusText"
          label="状态"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="goodsCount"
          label="有效商品数"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="limitAmount"
          label="最少成团人数"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="minJoinNum"
          label="开奖所需最少人数"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="joinUserCount"
          label="参与人数"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="groupUserCount"
          label="成团人数"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="groupCount"
          label="开团数"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="drawUserCount"
          label="中奖用户"
          align="center"
        ></el-table-column>
        <el-table-column
          label="操作"
          align="center"
          width="200"
        >
          <template slot-scope="scope">
            <div class="opt">
              <el-tooltip
                :content="$t('seckill.edit')"
                placement="top"
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
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-circle-check"
                  @click="startHandler(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.order')"
                placement="top"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-tickets"
                  @click="orderHanlder(scope.row.id, scope.row.name)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.detail')"
                placement="top"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-user-solid"
                  @click="detailHanlder(scope.row.id, scope.row.name)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="开团明细"
                placement="top"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-wind-power"
                  @click="groupHandler(scope.row.id, scope.row.name)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.delete')"
                placement="top"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-delete"
                  @click="deleteHandler(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.effect')"
                placement="top"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-s-data"
                  @click="effectHandler(scope.row.id)"
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

    <!-- 分享 -->
    <shareDialog
      :show="shareDialog"
      :imgPath="shareImg"
      :pagePath="sharePath"
      @close="shareDialog=false"
    />

  </div>
</template>

<script>
import addLotteryDraw from './addLotteryDraw.vue'
import shareDialog from '@/components/admin/shareDialog'
import pagination from '@/components/admin/pagination/pagination'
import { lotteryDrawList, shareLotteryDraw } from '@/api/admin/marketManage/lotteryDraw.js'
// deleteLotteryDraw, updateStatus,
export default {
  components: {
    addLotteryDraw,
    shareDialog,
    pagination
  },
  data () {
    return {
      tabSwitch: '1',
      tableListView: true, // tab显示隐藏
      tabInfo: this.$t('seckill.tabInfo'),
      tableData: [], // 表格数据
      // 分页
      pageParams: {
        currentPage: 1,
        pageRows: 20
      },
      requestParams: {
        activityName: '',
        startTime: '',
        endTime: ''
      },
      editId: '', // 编辑的活动id
      isEdite: true, // 编辑状态,

      shareDialog: false, // 分享弹窗
      shareImg: '',
      sharePath: ''

    }
  },
  watch: {
    lang () {
      this.tabInfo = this.$t('seckill.tabInfo')
      // this.initDataList()
    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    this.initDataList()
  },
  methods: {
    // 拼团抽奖列表
    initDataList () {
      this.requestParams.status = this.tabSwitch
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      this.closeTabAddGroup()
      lotteryDrawList(this.requestParams).then((res) => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
          this.tableData.map((item, index) => {
            item.statusText = this.getActStatusString(item.currentState)
          })
        }
      })
    },

    // 添加
    addLotteryDraw () {
      this.isEdite = false
      this.showTabAddGroup('添加拼团抽奖活动')
    },

    // 编辑
    editHandler (id, row) {
      this.editId = id
      this.isEdite = true
      this.showTabAddGroup('编辑拼团抽奖活动')
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
          title: '编辑拼团抽奖活动',
          name: '6'
        })
        console.log('closeTabAddGroup', this.tabInfo)
      }
      return this.tabInfo
    },

    // 保存
    addLotterySubmit () {
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
        // deleteLotteryDraw({ id: id }).then((res) => {
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
      shareLotteryDraw(id).then((res) => {
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
        // updateStatus({
        //   id: id,
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
        // updateStatus({
        //   id: id,
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
    },

    // 查看活动订单
    orderHanlder (id, name) {
      this.$router.push({ name: 'lottery_order_view', query: { id: id, name: name } })
    },

    // 获取新用户明细
    detailHanlder (id, name) {
      this.$router.push({ name: 'lottery_detail_view', query: { id: id, name: name } })
    },

    // 开团明细
    groupHandler (id, name) {
      this.$router.push({ name: 'lottery_group_view', query: { id: id, name: name } })
    },

    // 查看活动数据效果
    effectHandler (id) {
      this.$router.push({ name: 'lottery_effect_view', query: { id: id } })
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
  }
}
.table_list {
  position: relative;
  margin-top: 10px;
  background-color: #fff;
  padding: 15px;
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.inputWidth {
  width: 170px;
}
.item {
  display: inline-block;
  margin-right: 10px;
}
.opt {
  text-align: center;
  color: #5a8bff;
  span {
    cursor: pointer;
  }
}
</style>
