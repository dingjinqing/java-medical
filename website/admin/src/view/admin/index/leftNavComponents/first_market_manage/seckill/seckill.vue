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
            @click="addSeckill"
          >{{$t('seckill.addSeckill')}}</el-button>
        </el-tab-pane>
      </el-tabs>
    </div>

    <!-- 添加 / 编辑 -->
    <addSeckill
      :isEdite="isEdite"
      :editId="editId"
      :isGoing="isGoing"
      @addSeckillSubmit="addSeckillSubmit"
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
          :label="$t('seckill.activityName')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="goods"
          :label="$t('seckill.goodsName')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="first"
          :label="$t('seckill.first')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="validity"
          :label="$t('seckill.validDate')"
          align="center"
          width="160"
        >
          <template slot-scope="scope">
            {{scope.row.startTime}}<br>至<br>{{scope.row.endTime}}
          </template>
        </el-table-column>
        <el-table-column
          prop="statusText"
          :label="$t('seckill.activityStatus')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="saleNum"
          :label="$t('seckill.saleNum')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="limitAmount"
          :label="$t('seckill.limitAmount')"
          align="center"
        >
        </el-table-column>
        <!-- 操作 -->
        <el-table-column
          :label="$t('seckill.option')"
          align="center"
          width="200"
          style="margin: 0 auto;"
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
                  class="iconfont iconbianji"
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
                  class="iconfont iconfenxiang1"
                  @click="shareHandler(scope.row.skId)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.stop')"
                placement="top"
                v-if="scope.row.currentState === 1 || scope.row.currentState === 2 || scope.row.currentState === 3"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont icontingyong"
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
                  class="iconfont iconqiyong"
                  @click="startHandler(scope.row.skId)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.order')"
                placement="top"
                v-if="scope.row.currentState !== 2"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont icondingdan"
                  @click="seckillOrderHanlder(scope.row.skId, scope.row.name)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.detail')"
                placement="top"
                v-if="scope.row.currentState !== 2"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont iconxinyonghumingxi"
                  @click="seckillDetailHanlder(scope.row.skId, scope.row.name)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.user')"
                placement="top"
                v-if="scope.row.currentState !== 2"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont iconmingxi1"
                  @click="seckillUserHanlder(scope.row.skId, scope.row.name)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.delete')"
                placement="top"
                v-if="scope.row.currentState === 3 || scope.row.currentState === 4"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont iconshanchu2"
                  @click="deleteHandler(scope.row.skId)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.effect')"
                placement="top"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont iconfankuitongji"
                  @click="seckillEffectHandler(scope.row.skId, scope.row.startTime, scope.row.endTime)"
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
// 引入组件
import statusTab from '@/components/admin/marketManage/status/statusTab'
import pagination from '@/components/admin/pagination/pagination'
import shareDialog from '@/components/admin/shareDialog'
import addSeckill from './seckillAdd.vue'
import { seckillList, deleteSeckillList, shareSeckillList, updateSeckillList } from '@/api/admin/marketManage/seckill.js'

export default {
  components: {
    statusTab,
    pagination,
    addSeckill,
    shareDialog
  },
  data () {
    return {
      tabSwitch: '1',
      tableListView: true, // tab显示隐藏
      tabInfo: this.$t('seckill.tabInfo'),
      tableData: [], // 表格数据
      pageParams: {}, // 分页
      requestParams: {},
      shareDialog: false, // 分享弹窗
      shareImg: '',
      sharePath: '',
      editId: '', // 编辑的活动id
      isEdite: true, // 编辑状态
      isGoing: false // 是否是进行中的活动
    }
  },
  watch: {
    lang () {
      this.tabInfo = this.$t('seckill.tabInfo')
      this.initDataList()
    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    this.initDataList()
    if (this.$route.params.calenderAdd) {
      this.addSeckill()
    } else if (this.$route.params.calenderEdit) {
      this.editHandler(this.$route.params.id)
    }
  },
  methods: {
    // 秒杀列表
    initDataList () {
      this.requestParams.state = [this.tabSwitch]
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      this.closeTabAddGroup()
      seckillList(this.requestParams).then((res) => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
          this.tableData.map((item, index) => {
            item.statusText = this.getActStatusString(item.currentState)
          })
        } else {
          this.$message.error(res.message)
        }
      })
    },

    // 添加
    addSeckill () {
      this.isEdite = false
      this.isGoing = false
      this.showTabAddGroup(this.$t('seckill.addSeckill'))
    },

    // 编辑
    editHandler (id, row) {
      if (row.currentState === 1) {
        this.isEdite = true
      }
      if (row.currentState === 2) {
        this.isEdite = false
      }
      this.editId = id
      this.isGoing = true
      this.showTabAddGroup(this.$t('seckill.editSeckill'))
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
          title: this.$t('seckill.editSeckill'),
          name: '6'
        })
        console.log('closeTabAddGroup', this.tabInfo)
      }
      return this.tabInfo
    },

    // 保存
    addSeckillSubmit () {
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
        deleteSeckillList({ skId: id }).then((res) => {
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
      shareSeckillList(id).then((res) => {
        if (res.error === 0) {
          this.shareImg = res.content.imageUrl
          this.sharePath = res.content.pagePath
        }
      })
    },

    // 复制
    copyHandler (e) {
      this.$message.success({ message: this.$t('seckill.copySuccess') })
    },

    // 停用
    stopHandler (id) {
      this.$confirm(this.$t('seckill.stopTip'), {
        confirmButtonText: this.$t('seckill.sure'),
        cancelButtonText: this.$t('seckill.cancel'),
        type: 'warning'
      }).then(() => {
        updateSeckillList({
          skId: id,
          status: 0
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
        updateSeckillList({
          skId: id,
          status: 1
        }).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('seckill.startSuccess') })
            this.initDataList()
          }
        })
      }).catch(() => {
        this.$message.info({ message: this.$t('seckill.startFail') })
      })
    },

    // 查看秒杀订单
    seckillOrderHanlder (id, name) {
      this.$router.push({ name: 'sec_kill_order', query: { id: id, name: name } })
    },

    // 查看用户明细
    seckillDetailHanlder (id, name) {
      this.$router.push({ name: 'sec_kill_detail', query: { id: id, name: name } })
    },

    // 查看秒杀用户
    seckillUserHanlder (id, name) {
      this.$router.push({ name: 'sec_kill_user', query: { id: id, name: name } })
    },

    // 查看活动数据效果
    seckillEffectHandler (id, startTime, endTime) {
      this.$router.push({ name: 'sec_kill_effect', query: { id: id, startTime: startTime, endTime: endTime } })
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
  padding: 20px;
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
