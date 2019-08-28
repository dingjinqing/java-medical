<template>
  <div class="content">
    <div class="main">
      <statusTab
        v-model="param.status"
        :activityName="activityName"
        :standard="true"
      />
      <div class="wrapper">
        <el-button
          type="primary"
          @click="addActivity"
        >添加分享有礼活动</el-button>
        <div class="rightContent">
          <span>用户每天可参与</span>
          <el-input
            v-model="input"
            style="width: 80px"
            size="small"
            :value="dailyLimit"
          ></el-input>
          <span>次 分享有礼活动</span>
          <span>填写0表示不限制</span>
          <el-button
            type="primary"
            @click="saveDailyLimit()"
          >保存设置</el-button>
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
          label="活动名称"
          align="center"
        >

        </el-table-column>
        <el-table-column
          prop="condition"
          label="触发条件"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="validityPeriod"
          label="有效期"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="rewardType"
          label="活动奖励"
          align="center"
        >

        </el-table-column>
        <el-table-column
          prop="priority"
          label="优先级"
          align="center"
        >

        </el-table-column>
        <el-table-column
          prop="pageStatus"
          label="活动状态"
          align="center"
        >

        </el-table-column>
        <el-table-column
          prop="shareNum"
          label="分享人数"
          align="center"
        >

        </el-table-column>
        <el-table-column
          prop="inviteNum"
          label="邀请人数"
          align="center"
        >

        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <div class="operation">
              <el-tooltip
                content="编辑"
                placement="top"
              >
                <span
                  class="el-icon-edit-outline iconSpn"
                  @click="todoItem()"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="停用"
                placement="top"
                v-if="status != '1'"
              >
                <span
                  class="el-icon-circle-close iconSpn"
                  @click="shutdown(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="启用"
                placement="top"
                v-if="status === '1'"
              >
                <span
                  class="el-icon-circle-check iconSpn"
                  @click="open(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="删除"
                placement="top"
              >
                <span
                  @click="del(scope.row.id)"
                  class="el-icon-delete iconSpn"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="领取明细"
                placement="top"
              >
                <span
                  @click="jumptoReceiveDetail(scope.row.id)"
                  class="el-icon-s-cooperation iconSpn"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="分享"
                placement="top"
              >
                <span
                  class="el-icon-share iconSpn"
                  @click="todoItem()"
                ></span>
              </el-tooltip>
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
import statusTab from '@/components/admin/status/statusTab'
export default {
  components: {
    pagination,
    statusTab
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
    // 待完成方法
    todoItem () {

    },
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
      switch (this.param.status) {
        case 0:
          this.param.category = 0
          break
        case 1:
          this.param.category = 8
          break
        case 2:
          this.param.category = 4
          break
        case 3:
          this.param.category = 2
          break
        case 4:
          this.param.category = 1
      }
      this.param.currentPage = this.pageParams.currentPage
      this.param.pageRows = this.pageParams.pageRows
      getList(this.param).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.handleData(res.content)
          this.input = res.content.dailyShareAward
          this.pageParams = res.content.pageResult.page
          this.param.currentPage = res.content.pageResult.page.currentPage
          this.param.pageRows = res.content.pageResult.page.pageRows
        }
      })
    },
    // 表格数据处理
    handleData (data) {
      data.pageResult.dataList.map((item, index) => {
        // 有效期
        if (item.validityPeriod === 1) {
          item.validityPeriod = '永久有效'
        } else {
          item.validityPeriod = `${item.startTime}至${item.endTime}`
        }
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
          case 1:
            item.pageStatus = '已停用'
            break
          case 2:
            item.pageStatus = '已过期'
            break
          case 4:
            item.pageStatus = '未开始'
            break
          case 8:
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
      updateDailyLimit(this.input).then(res => {
        if (res.error === 0) {
          alert('更新成功！')
          this.seacherList()
        }
      })
    },
    addActivity () { },
    // 停用分享有礼活动
    shutdown (shareId) {
      let obj = {
        'shareId': shareId,
        'status': 1
      }
      changeActivity(obj).then(res => {
        if (res.error === 0) {
          alert('停用成功！')
        }
      })
    },
    // 启用分享有礼活动
    open (shareId) {
      let obj = {
        'shareId': shareId,
        'status': 0
      }
      changeActivity(obj).then(res => {
        if (res.error === 0) {
          alert('启用成功！')
        }
      })
    },
    // 删除分享有礼活动
    del (shareId) {
      let obj = {
        'shareId': shareId,
        'status': 2
      }
      changeActivity(obj).then(res => {
        if (res.error === 0) {
          alert('删除成功！')
          this.seacherList()
        }
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
  padding: 10px 20px 0 20px;
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
