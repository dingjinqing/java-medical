<template>
  <div class="content">
    <div class="main">
      <statusTab
        v-model="param.status"
        :activityName="activityName"
        :standard="true"
      />
      <div class="wrapper">
        <!-- 添加分享有礼活动按钮 -->
        <el-button
          type="primary"
          size="small"
          @click="addActivity"
        >{{$t('sharePolite.addActivity')}}</el-button>
        <div class="rightContent">
          <el-tooltip
            effect="light"
            placement="top"
          >
            <div
              slot="content"
              style="width:300px;line-height:1.5;"
            >若设置为3，即用户每天最多分享ABC3个商品，可获得活动奖励超过3个时，点击分享商品D，不再显示开屏有礼活动</div>
            <div class="tooltips">
              <img
                :src="$imageHost+'/image/admin/analysis_tishi.png'"
                alt=""
              >
            </div>
          </el-tooltip>
          <span>{{$t('sharePolite.userDaily')}}</span>
          <el-input
            v-model="dailyLimit"
            style="width: 80px;margin:0 5px"
            size="small"
          ></el-input>
          <span style="margin-right:10px;">{{$t('sharePolite.sharePoliteActivity')}}</span>
          <span>{{$t('sharePolite.unlimited')}}</span>
          <el-button
            type="primary"
            size="small"
            @click="saveDailyLimit()"
          >{{$t('sharePolite.save')}}</el-button>
        </div>
      </div>
    </div>
    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="name"
          :label="$t('sharePolite.activityName')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="condition"
          :label="$t('sharePolite.condition')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="validityPeriod"
          :label="$t('sharePolite.validityPeriod')"
          align="center"
        >
          <template slot-scope="scope">
            <div v-if="scope.row.validityPeriod === '1'"> {{$t('marketCommon.permanent')}}</div>
            <div v-else>
              {{scope.row.startTime}}<br />{{$t('marketCommon.to')}}<br />{{scope.row.endTime}}
            </div>
          </template>
        </el-table-column>

        <el-table-column
          prop="rewardType"
          :label="$t('sharePolite.rewardType')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="priority"
          :label="$t('sharePolite.priority')"
          align="center"
          width="140px"
        >
        </el-table-column>

        <el-table-column
          prop="pageStatus"
          :label="$t('sharePolite.activityStatus')"
          align="center"
          width="140px"
        >
        </el-table-column>

        <el-table-column
          prop="shareNum"
          :label="$t('sharePolite.shareNum')"
          align="center"
          width="140px;"
        >
        </el-table-column>

        <el-table-column
          prop="inviteNum"
          :label="$t('sharePolite.inviteNum')"
          align="center"
          width="140px"
        >
        </el-table-column>

        <el-table-column
          :label="$t('sharePolite.option')"
          align="center"
        >
          <template slot-scope="scope">
            <div class="operation">
              <el-tooltip
                :content="$t('sharePolite.edit')"
                placement="top"
                v-if="scope.row.pageStatus != '已停用'"
              >
                <span
                  class="el-icon-edit-outline iconSpn"
                  @click="updateActivity(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('sharePolite.terminate')"
                placement="top"
                v-if="scope.row.status === 0"
              >
                <span
                  class="el-icon-circle-close iconSpn"
                  @click="shutdown(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('sharePolite.open')"
                placement="top"
                v-if="scope.row.status === 1"
              >
                <span
                  class="el-icon-circle-check iconSpn"
                  @click="open(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('sharePolite.delete')"
                placement="top"
              >
                <span
                  @click="del(scope.row.id)"
                  class="el-icon-delete iconSpn"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('sharePolite.detail')"
                placement="top"
              >
                <span
                  @click="jumptoReceiveDetail(scope.row.id)"
                  class="el-icon-s-cooperation iconSpn"
                ></span>
              </el-tooltip>
              <!--              <el-tooltip
                :content="$t('sharePolite.share')"
                placement="top"
              >
                <span class="el-icon-share iconSpn"></span>
              </el-tooltip>-->
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="footer">
        <pagination
          :page-params.sync="pageParams"
          @pagination="seacherList"
        />
      </div>
    </div>
  </div>
</template>
<script>
import { getList, changeActivity, updateDailyLimit } from '@/api/admin/marketManage/sharePolite.js'
import pagination from '@/components/admin/pagination/pagination.vue'
import statusTab from '@/components/admin/marketManage/status/statusTab'
import wrapper from '@/components/admin/wrapper/wrapper'
export default {
  components: {
    pagination,
    statusTab,
    wrapper
  },
  mounted () {
    this.langDefault()
  },
  watch: {
    'param.status' (n, o) {
      this.seacherList()
    }
  },
  created () {
    // 页面加载时调用接口初始化数据，初始化加载进行中模块
    this.seacherList()
  },
  data () {
    return {
      activityName: '分享有礼',
      tableData: [],
      status: null,
      input: 0,
      dailyLimit: 0,
      pageParams: {},
      param: {
        status: 0,
        category: 0,
        // 分页
        currentPage: 0,
        pageRows: 20
      }
    }
  },
  methods: {
    // 分享有礼活动领取明细点击跳转
    jumptoReceiveDetail (shareId) {
      this.$router.push({
        name: 'share_polite_detail',
        params: {
          id: shareId,
          flag: true
        }
      })
    },
    // 分模块查询数据列表
    seacherList () {
      this.param.category = this.param.status
      if (this.pageParams != null) {
        this.param.currentPage = this.pageParams.currentPage
        this.param.pageRows = this.pageParams.pageRows
      }
      console.log(JSON.parse(JSON.stringify(this.param)))
      getList(this.param).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.handleData(res.content)
          this.dailyLimit = res.content.dailyShareAward
          this.pageParams = res.content.pageResult.page
          this.param.currentPage = res.content.pageResult.page.currentPage
          this.param.pageRows = res.content.pageResult.page.pageRows
        }
      })
    },
    // 更新活动数据跳转
    updateActivity (shareId) {
      this.$router.push({
        name: 'share_polite_add',
        params: {
          id: shareId,
          flag: true
        }
      })
    },
    // 表格数据处理
    handleData (data) {
      data.pageResult.dataList.map((item, index) => {
        // 触发条件
        switch (item.condition) {
          case 1:
            item.condition = '全部商品'
            break
          case 2:
            item.condition = '指定商品'
            break
          case 3:
            item.condition = `访问量少于${item.goodsPv}的商品`
            break
          default:
            item.condition = ''
            break
        }
        // 活动状态
        switch (item.pageStatus) {
          case 4:
            item.pageStatus = '已停用'
            break
          case 3:
            item.pageStatus = '已过期'
            break
          case 2:
            item.pageStatus = '未开始'
            break
          case 1:
            item.pageStatus = '进行中'
            break
        }
        // 活动奖励类型
        item.rewardType.forEach((itemC, indexC) => {
          switch (itemC) {
            case 1:
              item.rewardType[indexC] = '积分、'
              break
            case 2:
              item.rewardType[indexC] = '优惠券、'
              break
            case 3:
              item.rewardType[indexC] = '幸运大转盘'
          }
        })
      })
      this.tableData = data.pageResult.dataList
    },
    // 更新每日用户可分享次数上限参数
    saveDailyLimit () {
      this.$confirm('此操作将更新每日用户可分享次数上限, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updateDailyLimit(this.dailyLimit).then(res => {
          if (res.error === 0) {
            this.$message.success('更新成功！')
            this.seacherList()
          }
        })
      }).catch(() => {
        this.$message.info('已取消删除！')
      })
    },
    // 添加分享有礼活动
    addActivity () {
      this.$router.push({
        name: 'share_polite_add'
      })
    },
    // 停用分享有礼活动
    shutdown (shareId) {
      let obj = {
        'shareId': shareId,
        'status': 1
      }
      this.$confirm('此操作将停用该加价购活动, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        changeActivity(obj).then(res => {
          if (res.error === 0) {
            this.$message.success('停用成功！')
            this.seacherList()
          }
        })
      }).catch(() => {
        this.$message.info('已取消停用！')
      })
    },
    // 启用分享有礼活动
    open (shareId) {
      let obj = {
        'shareId': shareId,
        'status': 0
      }
      this.$confirm('此操作将启用该加价购活动, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        changeActivity(obj).then(res => {
          if (res.error === 0) {
            this.$message.success('启用成功！')
            this.seacherList()
          }
        })
      }).catch(() => {
        this.$message.info('已取消启用！')
      })
    },
    // 删除分享有礼活动
    del (shareId) {
      let obj = {
        'shareId': shareId,
        'status': 2
      }
      this.$confirm('此操作将永久删除该加价购活动, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        changeActivity(obj).then(res => {
          if (res.error === 0) {
            this.$message.success('删除成功！')
            this.seacherList()
          }
        })
      }).catch(() => {
        this.$message.info('已取消删除！')
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
      display: flex;
      justify-content: space-between;
      .rightContent {
        display: flex;
        .tooltips {
          margin: 6px 8px 0 0;
          cursor: pointer;
        }
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
.condition {
  position: relative;
  background-color: #fff;
  padding: 10px 20px 0 20px;
}
.p_top_right {
  display: flex;
  /deep/ .el-button {
    padding: none;
    height: 32px;
  }
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
.operation {
  display: flex;
  justify-content: space-around;
  .iconSpn {
    display: block;
    font-size: 20px;
    color: #5a8bff;
    cursor: pointer;
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
  padding: 15px 15px 15px;
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
</style>
