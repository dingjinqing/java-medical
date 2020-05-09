<template>
  <div
    class="content"
    v-loading="loading"
  >
    <div class="main">
      <el-tabs
        v-model="tabSwitch"
        @tab-click="handleTabClick"
      >
        <el-tab-pane
          v-for="(item) in tabInfo"
          :key="item.name"
          :label="item.title"
          :name="item.name"
        >
          <el-button
            v-if="!isShowAddFlag"
            type="primary"
            @click="addActivity"
            class="barginBtn"
            size="small"
          >{{$t('luckyDraw.addActivity')}}
          </el-button>
        </el-tab-pane>
      </el-tabs>
    </div>
    <!-- 添加抽奖活动的路由出口 -->
    <div v-if="isShowAddFlag">
      <component
        v-bind:is="currentComponent"
        :id="lotteryId"
        :isEdite="isEdite"
      ></component>
    </div>
    <div
      v-if="!isShowAddFlag"
      class="table_list"
    >
      <el-table
        :data="tableData"
        class="version-manage-table"
        header-row-class-name="tableClss"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="lotteryName"
          :label="$t('luckyDraw.activityName')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          :label="$t('luckyDraw.dateValidity')"
          align="center"
        >
          <template slot-scope="{row}">
            <div style="line-height:1;">
              <div>{{row.startTime}}</div>
              <div>{{$t('luckyDraw.to')}}</div>
              <div>{{row.endTime}}</div>
            </div>
          </template>
        </el-table-column>

        <el-table-column
          prop="statusText"
          :label="$t('luckyDraw.activityStatus')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="joinNumber"
          :label="$t('luckyDraw.joinNumber')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="awardNumber"
          :label="$t('luckyDraw.awardNumber')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          :label="$t('luckyDraw.option')"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <!-- 设置 -->
              <el-tooltip
                v-if="scope.row.currentState == 1 || scope.row.currentState == 2"
                :content="$t('luckyDraw.edit')"
                placement="top"
              >
                <span
                  class="el-icon-edit-outline"
                  @click="editActivity(scope.row.id)"
                ></span>
              </el-tooltip>
              <!-- 分享 -->
              <el-tooltip
                v-if="scope.row.currentState == 1 || scope.row.currentState == 2"
                :content="$t('luckyDraw.share')"
                placement="top"
              >
                <span
                  class="el-icon-share"
                  @click="shareActivity(scope.row.id)"
                ></span>
              </el-tooltip>
              <!-- 停用 -->
              <el-tooltip
                v-if="scope.row.currentState == 1 || scope.row.currentState == 2"
                :content="$t('luckyDraw.disable')"
                placement="top"
              >
                <span
                  class="el-icon-circle-close"
                  @click="changeStatus(scope.row.id, 'stop')"
                  v-if="scope.row.status==1"
                ></span>
              </el-tooltip>
              <!-- 启用 -->
              <el-tooltip
                v-if="scope.row.currentState == 4"
                :content="$t('luckyDraw.enabled')"
                placement="top"
              >
                <span
                  class="el-icon-circle-check"
                  @click="changeStatus(scope.row.id, 'start')"
                  v-if="scope.row.status==0"
                ></span>
              </el-tooltip>
              <!-- 删除 -->
              <el-tooltip
                v-if="scope.row.currentState == 3 || scope.row.currentState == 4"
                :content="$t('luckyDraw.delete')"
                placement="top"
              >
                <span
                  class="el-icon-delete"
                  @click="deleteluckyDraw(scope.row.id)"
                ></span>
              </el-tooltip>
              <!-- 抽奖明细 -->
              <el-tooltip
                v-if="scope.row.currentState != 2"
                :content="$t('luckyDraw.detailList')"
                placement="top"
              >
                <span
                  class="el-icon-tickets"
                  @click="luckyDrawDetailList(scope.row.id)"
                ></span>
              </el-tooltip>
              <!-- 获取新用户明细 -->
              <el-tooltip
                v-if="scope.row.currentState != 2"
                :content="$t('luckyDraw.newUserList')"
                placement="top"
              >
                <span
                  class="el-icon-user-solid"
                  @click="newUserDetail(scope.row.id,scope.row.lotteryName)"
                ></span>
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="footer">
        <pagination
          :page-params.sync="pageParams"
          @pagination="handlePagination"
        />
      </div>
    </div>
    <!-- 分享活动弹窗 -->
    <el-dialog
      title="扫一扫，分享给好友吧~"
      :visible.sync="shareDialogVisible"
      width="320px"
      custom-class="share-dialog"
    >
      <el-image
        :src="shareInfo.imageUrl"
        style="width:160px;height:160px;margin:0 auto;"
        fit="fill"
      ></el-image>
      <a
        class="share-dialog-a"
        :href="shareInfo.imageUrl"
        download
      >下载二维码</a>
      <div class="share-dialog-footer">
        <el-input
          size="small"
          v-model="shareInfo.pagePath"
        ></el-input>
        <el-button
          type="text"
          style="margin-left:10px;"
          v-clipboard:copy="shareInfo.pagePath"
          v-clipboard:success="onCopySuccess"
          v-clipboard:error="onCopyError"
        >复制</el-button>
      </div>
    </el-dialog>
  </div>

</template>
<script>
import {
  getLotteryList,
  changeStatus,
  deleteLottery,
  shareLottery
} from '@/api/admin/marketManage/luckyDraw.js'
import pagination from '@/components/admin/pagination/pagination.vue'
// 引入添加抽奖活动界面
import luckyDrawAdd from './luckyDrawAdd'
export default {
  components: {
    pagination,
    luckyDrawAdd
  },
  data () {
    return {
      tableData: [],
      tabSwitch: '1',
      tabInfo: this.$t('luckyDraw.tabInfo'),
      pageParams: {},
      tabIndex: 1,
      /**
       * 添加抽奖相关数据
       */
      isShowAddFlag: false,
      // 动态组件
      currentComponent: null,
      lotteryId: null,
      isEdite: false,
      shareDialogVisible: false,
      loading: false,
      shareInfo: {}
    }
  },
  watch: {
    lang () {
      this.tabInfo = this.$t('luckyDraw.tabInfo')
    },
    '$route.query': function (val) {
      if (val === 0 || val === '0') {
        this.tabSwitch = '0'
      }
    }
  },
  created () {

  },
  mounted: function () {
    // 初始化国际化
    this.langDefault()
    // 初始化页面数据
    this.initPageData()
    this.closeTabAddGroup()
    if (this.$route.query.add) {
      this.addActivity()
    }
    if (this.tabSwitch === `6`) {
      console.log(1111111111)
    } else {
      console.log(222222222)
    }
  },

  methods: {
    initPageData () {
      let obj = {
        //  0全部，1进行中，2未开始，3已过期，4已停用
        state: this.tabSwitch,
        currentPage: this.pageParams.currentPage,
        pageRows: this.pageParams.pageRows
      }
      getLotteryList(obj).then(res => {
        console.log(res)
        this.pageParams = res.content.page
        this.resDataFilter(res.content.dataList)
        this.tableData = res.content.dataList
      }).catch(() => {
        this.$message.error(this.$t('luckyDraw.dataLoadFail'))
      })
    },
    resDataFilter (data) {
      data.map((item, index) => {
        item.statusText = this.getActStatusString(item.currentState)
      })
    },
    handlePagination () {
      this.initPageData()
    },
    handleTabClick (tab) {
      if (this.closeTabAddGroup()) {
        this.initPageData()
      }
    },
    // 添加抽奖活动
    addActivity () {
      console.log('addActivity', 'lottery')
      // 改变flag
      this.isEdite = false
      this.isShowAddFlag = true
      // 增加tab页--添加抽奖活动
      this.tabInfo.push({
        title: this.$t('luckyDraw.addSweepstakes'),
        name: '6'
      })
      this.tabSwitch = `6`
      // 跳转到路由添加抽奖界面
      this.currentComponent = luckyDrawAdd
    },
    // 编辑
    editActivity (id) {
      console.log('editActivity', 'lottery', id)
      this.lotteryId = id
      this.isEdite = true
      // 改变flag
      this.isShowAddFlag = true
      // 增加tab页--添加抽奖活动
      this.tabInfo.push({
        title: this.$t('luckyDraw.addSweepstakes'),
        name: '6'
      })
      this.tabSwitch = `6`
      // 跳转到路由添加抽奖界面
      this.currentComponent = luckyDrawAdd
    },
    shareActivity (id) {
      console.log(id)
      this.loading = true
      shareLottery({
        id: id
      }).then(res => {
        this.loading = false
        if (res.error === 0) {
          this.shareInfo = res.content
          this.shareDialogVisible = true
        } else {
          this.$message.error(res.message)
        }
      })
    },
    changeStatus (id, operate) {
      let that = this
      let message = ''
      if (operate === 'stop') {
        message = this.$t('luckyDraw.stopStatusComment')
      } else {
        message = this.$t('luckyDraw.changeStatusComment')
      }
      this.$confirm(message, {
        confirmButtonText: this.$t('luckyDraw.confirm'),
        cancelButtonText: this.$t('luckyDraw.cancel'),
        type: 'warning'
      }).then(() => {
        changeStatus({ id: id }).then(res => {
          console.log('res', res)
          if (res.error === 0) {
            this.$message.success(res.message)
            this.initPageData()
          } else {
            this.$message.warning(res.message)
          }
        })
      }).catch(() => {
        that.$message.warning(that.$t('luckyDraw.cancelMessage'))
      })
    },
    deleteluckyDraw (id) {
      console.log('deleteluckyDraw', id)
      this.$confirm(this.$t('luckyDraw.deleteLuckDrawComment'), {
        confirmButtonText: this.$t('luckyDraw.confirm'),
        cancelButtonText: this.$t('luckyDraw.cancel'),
        type: 'warning'
      }).then(() => {
        deleteLottery({ id: id }).then(res => {
          console.log('res', res)
          if (res.error === 0) {
            this.$message.success(res.message)
            this.initPageData()
          } else {
            this.$message.warning(res.message)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: this.$t('luckyDraw.cancelMessage')
        })
      })
    },
    luckyDrawDetailList (id) {
      console.log('跳转到抽奖详情页面 id = ', id)
      this.$router.push({ path: '/admin/home/main/luckyDraw/detailList', query: { id: id } })
    },
    newUserDetail (id, activityName) {
      console.log('跳转到获取新用户列表页面 id = ', id, 'activityName: ', activityName)
      this.$router.push({ path: '/admin/home/main/luckyDraw/newUserList', query: { id: id, activityName: activityName } })
    },
    // 关闭新增幸运大抽奖标签页
    closeTabAddGroup () {
      // 新增标签
      if (this.tabSwitch === '6') {
        return false
      }
      // 不是新增
      for (; this.tabInfo.length > 5;) {
        this.currentComponent = luckyDrawAdd

        this.isShowAddFlag = false
        this.tabInfo.pop()
      }
      return true
    },
    closeAddGroupTab () {
      // 不是新增
      for (; this.tabInfo.length > 5;) {
        this.currentComponent = luckyDrawAdd
        this.isShowAddFlag = false
        this.tabSwitch = '1'
        this.tabInfo.pop()
      }
      this.initPageData()
    },
    onCopySuccess () {
      this.$message.success('已复制')
    },
    onCopyError () {
      this.$message.error('复制失败')
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
    padding: 10px 20px 10px 20px;

    .wrapper {
      display: flex;
      justify-content: space-between;

      .rightContent {
        .el-button {
          margin-left: 5px;
        }

        span {
          height: 30px;
          line-height: 30px;
        }

        :nth-of-type(3) {
          color: #999;
        }
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
  padding: 10px 20px 10px 20px;
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

.opt {
  text-align: center;
  color: #5a8bff;

  span {
    cursor: pointer;
    font-size: 22px;
  }
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
  }
}
.share-dialog {
  .el-dialog__header {
    background: #fff;
  }
  .el-dialog__body {
    padding-top: 10px;
    padding-bottom: 10px;
  }
  .el-image {
    display: block;
    width: 100%;
  }
  .share-dialog-a {
    display: inline-block;
    width: 100%;
    text-decoration: none;
    margin: 10px auto;
    text-align: center;
    color: #999;
    font-size: 14px;
    cursor: pointer;
  }
  .share-dialog-footer {
    display: flex;
  }
}
</style>
