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
            :label="$t('lotteryDraw.activityName') + '：'"
            class="item"
          >
            <el-input
              size="small"
              :placeholder="$t('lotteryDraw.activityNameTip')"
              v-model="requestParams.activityName"
              class="inputWidth"
              clearabl
            ></el-input>
          </el-form-item>
          <el-form-item
            :label="$t('lotteryDraw.validDate') + '：'"
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
              default-time="23:59:59"
              class="date_picker inputWidth"
            ></el-date-picker>
          </el-form-item>
          <el-button
            type="primary"
            size="small"
            class="item"
            @click="initDataList"
            style="margin-left: 10px;"
          >{{ $t('lotteryDraw.select') }}</el-button>
        </el-form>
        <el-button
          type="primary"
          size="small"
          @click="addLotteryDraw"
        >{{ $t('lotteryDraw.addLotteryDraw') }}</el-button>
      </div>
    </div>

    <!-- 添加 / 编辑 -->
    <addLotteryDraw
      :isEdite="isEdite"
      :editId="editId"
      :isGoing="isGoing"
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
          :label="$t('lotteryDraw.activityName')"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.validity')"
          align="center"
          width="160"
        >
          <template slot-scope="scope">
            {{scope.row.startTime}}<br>{{ $t('lotteryDraw.to') }}<br>{{scope.row.endTime}}
          </template>
        </el-table-column>
        <el-table-column
          prop="statusText"
          :label="$t('lotteryDraw.status')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="goodsCount"
          :label="$t('lotteryDraw.goodsCount')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="limitAmount"
          :label="$t('lotteryDraw.limitAmount')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="minJoinNum"
          :label="$t('lotteryDraw.minJoinNum')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="joinUserCount"
          :label="$t('lotteryDraw.joinUserCount')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="groupUserCount"
          :label="$t('lotteryDraw.groupUserCount')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="groupCount"
          :label="$t('lotteryDraw.groupCount')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="drawUserCount"
          :label="$t('lotteryDraw.drawUserCount')"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.option')"
          align="center"
          width="150"
        >
          <template slot-scope="scope">
            <div class="opt">
              <el-tooltip
                :content="$t('lotteryDraw.edit')"
                placement="top"
                v-if="scope.row.status === 1 || scope.row.status === 2"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont iconbianji  "
                  @click="editHandler(scope.row.id, scope.row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('lotteryDraw.share')"
                placement="top"
                v-if="scope.row.status === 1 || scope.row.status === 2"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont iconfenxiang1"
                  @click="shareHandler(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('lotteryDraw.stop')"
                placement="top"
                v-if="scope.row.status === 1 || scope.row.status === 2"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont icontingyong"
                  @click="stopHandler(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('lotteryDraw.start')"
                placement="top"
                v-if="scope.row.status === 4"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont iconqiyong"
                  @click="startHandler(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('lotteryDraw.order')"
                placement="top"
                v-if="scope.row.status !== 2"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont icondingdan"
                  @click="orderHanlder(scope.row.id, scope.row.name)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('lotteryDraw.user')"
                placement="top"
                v-if="scope.row.status !== 2"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont iconmingxi1"
                  @click="userHanlder(scope.row.id, scope.row.name)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('lotteryDraw.userDetail')"
                placement="top"
                v-if="scope.row.status !== 2"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont iconxinyonghumingxi"
                  @click="detailHanlder(scope.row.id, scope.row.name)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('lotteryDraw.groupDetail')"
                placement="top"
                v-if="scope.row.status !== 2"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont iconkaituanmingxi"
                  @click="groupHandler(scope.row.id, scope.row.name)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('lotteryDraw.delete')"
                placement="top"
                v-if="scope.row.status === 3 || scope.row.status === 4"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont iconshanchu2"
                  @click="deleteHandler(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('lotteryDraw.effect')"
                placement="top"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont iconfankuitongji"
                  @click="effectHandler(scope.row.id, scope.row.startTime, scope.row.endTime)"
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
import { lotteryDrawList, deleteLotteryDraw, shareLotteryDraw, stopStatus, startStatus } from '@/api/admin/marketManage/lotteryDraw.js'
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
      tabInfo: this.$t('lotteryDraw.tabInfo'),
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
      isGoing: false, // 是否是进行中活动

      shareDialog: false, // 分享弹窗
      shareImg: '',
      sharePath: ''

    }
  },
  watch: {
    lang () {
      this.tabInfo = this.$t('lotteryDraw.tabInfo')
      this.initDataList()
    }
  },
  mounted () {
    if (this.$route.params.calenderAdd) {
      this.addLotteryDraw()
    } else if (this.$route.params.calenderEdit) {
      this.editHandler(this.$route.params.id)
    }
    // 初始化数据
    this.langDefault()
    this.initDataList()
  },
  methods: {
    // 拼团抽奖列表
    initDataList () {
      if (this.tabSwitch === '0') {
        this.requestParams.status = null
      } else {
        this.requestParams.status = Number(this.tabSwitch)
      }
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      this.closeTabAddGroup()
      lotteryDrawList(this.requestParams).then((res) => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
          this.tableData.map((item, index) => {
            item.statusText = this.getActStatusString(item.status)
          })
        }
      })
    },

    // 添加
    addLotteryDraw () {
      this.isEdite = false
      this.isGoing = false
      this.showTabAddGroup(this.$t('lotteryDraw.addLotteryDraw'))
    },

    // 编辑
    editHandler (id, val) {
      if (val.status === 1) {
        this.isEdite = true
      }
      if (val.status === 2) {
        this.isEdite = false
      }
      this.editId = id
      this.isGoing = true
      this.showTabAddGroup(this.$t('lotteryDraw.editLotteryDraw'))
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
          title: this.$t('lotteryDraw.editLotteryDraw'),
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
        deleteLotteryDraw(id).then((res) => {
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
      shareLotteryDraw({ groupDrawId: id }).then((res) => {
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
        stopStatus(id).then((res) => {
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
        startStatus(id).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('seckill.startSuccess') })
            this.initDataList()
          }
        })
      }).catch(() => {
        this.$message.info({ message: this.$t('seckill.startFail') })
      })
    },

    // 查看活动订单
    orderHanlder (id, name) {
      this.toPush('group_draw_order', id, name)
    },

    // 获取新用户明细
    detailHanlder (id, name) {
      this.toPush('group_draw_detail', id, name)
    },

    // 参与用户
    userHanlder (id, name) {
      this.toPush('group_draw_user', id, name)
    },

    // 开团明细
    groupHandler (id, name) {
      this.toPush('group_draw_group', id, name)
    },

    // 查看活动数据效果
    effectHandler (id, startTime, endTime) {
      let routeData = this.$router.resolve({ name: 'group_draw_effect', query: { id: id, startTime: startTime, endTime: endTime } })
      window.open(routeData.href, '_blank')
    },

    toPush (url, id, name) {
      let routeData = this.$router.resolve({ name: url, query: { id: id, name: name } })
      window.open(routeData.href, '_blank')
    }
  }

}

</script>
<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
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
  text-align: left;
  color: #5a8bff;
  span {
    cursor: pointer;
  }
}
</style>
