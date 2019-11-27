<!--
* 我要送礼--活动列表
*
* @author 孔德成
-->
<template>
  <div class="content">
    <div class="main">
      <el-row>
        <el-col :span='24'>
          <el-tabs
            v-model="tabSwitch"
            @tab-click="chooseTab"
          >
            <el-tab-pane
              v-for="(item) in tabInfo"
              :key="item.name"
              :label="item.title"
              :name="item.name"
            >
              <el-button
                v-if="tableListView"
                type="primary"
                @click="addActivity"
              >
                {{$t('giveGift.addActivity')}}
              </el-button>
            </el-tab-pane>
          </el-tabs>

        </el-col>
      </el-row>
    </div>
    <div
      class="table_list"
      v-if="tableListView"
    >
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        v-loading="loading"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="actName"
          :label="$t('giveGift.activityName')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="validDate"
          :label="$t('giveGift.validDate')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="level"
          :label="$t('giveGift.priority')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="sendOrderNumber"
          :label="$t('giveGift.givePeopleNum')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="getOrderMunber"
          :label="$t('giveGift.receivePoepleNum')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="statusText"
          :label="$t('giveGift.activityStatus')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          :label="$t('giveGift.option')"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <el-tooltip
                :content="$t('giveGift.edit')"
                placement="top"
              >
                <span
                  class="el-icon-edit-outline"
                  @click="editActivity(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('giveGift.share')"
                placement="top"
              >
                <span class="el-icon-share"></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('giveGift.disable')"
                placement="top"
              >
                <span
                  class="el-icon-circle-close"
                  @click="changeStatus(scope.row.id)"
                  v-if="scope.row.status==1"
                > </span>
              </el-tooltip>
              <el-tooltip :content="$t('giveGift.enabled')">
                <span
                  class="el-icon-circle-check"
                  @click="changeStatus(scope.row.id)"
                  v-if="scope.row.status==0"
                > </span>
              </el-tooltip>
              <el-tooltip
                :content="$t('giveGift.delete')"
                placement="top"
              >
                <span
                  class="el-icon-delete"
                  @click="deleteGiveGiftActivity(scope.row.id)"
                >
                </span>
              </el-tooltip>
              <el-tooltip
                :content="$t('giveGift.giveGiftDetail')"
                placement="top"
              >
                <span
                  class="el-icon-tickets"
                  @click="giveGiftDetail(scope.row.id,scope.row.actName)"
                >
                </span>
              </el-tooltip>
              <el-tooltip
                :content="$t('giveGift.receiveDetail')"
                placement="top"
              >
                <span
                  class="el-icon-s-unfold"
                  @click="receiveDetail(scope.row.id)"
                ></span>
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="footer">
      </div>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />
    </div>
    <addGiveGift
      v-if="!tableListView"
      @submitFormJump="submitFormJump()"
    ></addGiveGift>
  </div>
</template>

<script>
import addGiveGift from './addGiveGift.vue'
import pagination from '@/components/admin/pagination/pagination.vue'

import {
  giveGiftList,
  changeGiveGift,
  deleteGiveGift
} from '@/api/admin/marketManage/giveGift'

export default {
  components: {
    addGiveGift,
    pagination
  },
  data: function () {
    return {
      tableData: [],
      pageParams: { id: null },
      tabInfo: this.$t('giveGift.tabInfo'),
      tabSwitch: this.$route.params.tabSwitch,
      tableListView: true,
      loading: false
    }
  },
  mounted () {
    console.log('mounted', this.$route)
    this.tabSwitch = 'inProgress'
    // 初始化数据
    this.langDefault()
    this.initDataList()
  },
  watch: {
    lang () {
      this.tabInfo = this.$t('giveGift.tabInfo')
      this.tabSwitch = this.$route.params.tabSwitch
    },
    $route (to) {
      console.log('to', to, to.path.split('/')[5])
      console.log('$route', this.$route, this.$route.params.tabSwitch)
      if (this.tabSwitch !== this.$route.params.tabSwitch) {
        this.tabSwitch = to.path.split('/')[5]
        this.initDataList()
      }
    }
  },
  methods: {
    // 初始化
    initDataList () {
      console.log(' this.tabSwitch', this.tabSwitch)
      // 判断是不是添加活动页面
      if (this.tabSwitch === 'add') {
        this.addActivity()
        return
      }
      if (this.tabSwitch === 'edit') {
        this.editActivity(this.$route.query.id)
        return
      }
      if (this.tabInfo.length > 5) {
        this.chooseTab()
      }
      let obj = {
        currentPage: this.pageParams.currentPage,
        pageRows: this.pageParams.pageRows
      }
      this.loading = true
      obj.navType = this.tabInfo.filter(item => {
        return item.name === this.tabSwitch
      })[0].index
      console.log('forEach', obj)
      giveGiftList(obj).then(res => {
        if (res.error === 0) {
          this.$message.success(res.message)
          this.resDataFilter(res.content.dataList)
          this.pageParams = res.content.page
        } else {
          this.$message.warning(res.error, res.message)
        }
        this.loading = false
      }).catch(e => {
        this.$message.warning(e.toString())
        this.loading = false
      })
    },
    // 列表数据格式化
    resDataFilter (data) {
      data.forEach(item => {
        item.validDate = item.startTime + this.$t('giveGift.to') + item.endTime
        item.statusText = this.getActStatusString(item.currentState)
      })
      this.tableData = data
    },
    // 添加
    addActivity () {
      console.log('添加活动 ')
      this.addNewTab('add')
    },
    // 编辑
    editActivity (id) {
      console.log('编辑活动,id= ', id)
      this.addNewTab('edit', { id: id })
    },
    // 切换tab
    chooseTab () {
      console.log('chooseTab', this.tabSwitch)
      if (this.tabSwitch === 'add') {
        this.addActivity()
        return
      }
      if (this.tabSwitch === 'edit') {
        this.editActivity(this.$route.query.id)
        return
      }
      this.closeNewTab()
      this.$router.push({
        path: this.tabSwitch
      })
      this.initDataList()
    },
    // 改变状态
    changeStatus (id) {
      this.$confirm(this.$t('giveGift.changeStatusComment'), {
        confirmButtonText: this.$t('giveGift.confirm'),
        cancelButtonText: this.$t('giveGift.cancel'),
        type: 'warning'
      }).then(() => {
        changeGiveGift({ 'id': id }).then(res => {
          console.log('change=>res = ' + res)
          if (res.error === 0) {
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
          this.initDataList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: this.$t('giveGift.cancelMessage')
        })
      })
    },
    // 删除活动
    deleteGiveGiftActivity (id) {
      this.$confirm(this.$t('giveGift.deleteComment'), {
        confirmButtonText: this.$t('giveGift.confirm'),
        cancelButtonText: this.$t('giveGift.cancel'),
        type: 'warning'
      }).then(() => {
        deleteGiveGift({ 'id': id }).then(res => {
          console.log(res)
          if (res.error === 0) {
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
          this.initDataList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: this.$t('giveGift.cancelMessage')
        })
      })
    },
    // 收礼详情
    receiveDetail (id) {
      // 收礼明细
      this.$router.push({
        name: 'giveGift_receive',
        params: {
          id: id
        }
      })
    },
    // 送礼详情
    giveGiftDetail (id, activityName) {
      // 送礼明细
      console.log('跳转到送礼明细列表页面 id = ', id, 'activityName: ', activityName)
      this.$router.push({
        name: 'giveGift_details',
        params: {
          id: id,
          activityName: activityName
        }
      })
    },
    // 添加tab
    addNewTab (index, params = null) {
      if (this.tabInfo.length > 5) {
        this.tabSwitch = index
        this.tableListView = false
        this.add = false
        return
      }
      // 该变路由
      this.$router.push({ path: index, query: params })
      // 添加tab
      this.tabInfo.push({
        title: index === 'add' ? this.$t('giveGift.addActivity') : this.$t('giveGift.editActicity'),
        name: index
      })
      this.tableListView = false
      this.tabSwitch = index
      this.add = false
    },
    // 关闭tab
    closeNewTab () {
      // 新增标签
      if (this.tabSwitch === 'add' || this.tabSwitch === 'edit') {
        return
      }
      // 不是新增
      if (this.tabInfo.length > 5) {
        this.tableListView = true
        this.tabInfo.pop({
          title: this.$t('groupBuy.editActivity'),
          name: 'add'
        })
        console.log('closeTabAddGroup', this.tabInfo)
      }
      return this.tabInfo
    },
    // 添加或编辑后
    submitFormJump () {
      console.log('submitFormJump')
      this.tabSwitch = 'inProgress'
      this.chooseTab()
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
  }
}

.p_top_right {
  display: flex;

  span {
    white-space: nowrap;
    height: 32px;
    line-height: 32px;
    margin-right: 10px;
  }

  .topRightDiv {
    &:nth-of-type(2) {
      margin: 0 10px 0 30px;
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

.opt {
  text-align: left;
  color: #5a8bff;

  span {
    cursor: pointer;
  }
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
  }
}
</style>
