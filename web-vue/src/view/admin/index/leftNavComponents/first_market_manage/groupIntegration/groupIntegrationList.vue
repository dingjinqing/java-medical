<template>
  <div class="content">
    <div class="main">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          label="全部瓜分活动"
          name="first"
        >
          <el-button
            type="primary"
            @click="addActivity"
          >添加瓜分积分活动</el-button>
        </el-tab-pane>
        <el-tab-pane
          label="进行中"
          name="second"
        >
          <el-button
            type="primary"
            @click="addActivity"
          >添加瓜分积分活动</el-button>
        </el-tab-pane>
        <el-tab-pane
          label="未开始"
          name="third"
        >
          <el-button
            type="primary"
            @click="addActivity"
          >添加瓜分积分活动</el-button>
        </el-tab-pane>
        <el-tab-pane
          label="已过期"
          name="fourth"
        >
          <el-button
            type="primary"
            @click="addActivity"
          >添加瓜分积分活动</el-button>
        </el-tab-pane>
        <el-tab-pane
          label="已停用"
          name="fifth"
        >
          <el-button
            type="primary"
            @click="addActivity"
          >添加瓜分积分活动</el-button>
        </el-tab-pane>
        <el-tab-pane
          :label="sixTitle"
          name="sixth"
          v-if="showSix"
        >
          <integrationAdd
          :isEditId="isEditId"
           @backHome="backHome"/>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div class="table_list" v-if="!showSix">
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
          prop="content"
          label="活动内容"
          align="center"
        >
        </el-table-column>
        <el-table-column
          label="活动积分总量"
          align="center"
        >
          <template slot-scope="scope">
            <div>
              <span>{{scope.row.totalIntegration}}</span>
              <br>
              <span>{{scope.row.leftIntegration}}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="actDate"
          label="有效期"
          align="center"
        >
          <template slot-scope="scope">
            {{scope.row.startTime}}<br>至<br>{{scope.row.endTime}}
          </template>
        </el-table-column>
        <el-table-column
          prop="expire"
          label="活动状态"
          align="center"
          width="80"
          :formatter="formatter"
        >

        </el-table-column>
        <el-table-column
          prop="inteUserSum"
          label="参与人数"
          align="center"
        >

        </el-table-column>
        <el-table-column
          prop="inteGroupSum"
          label="团数量"
          align="center"
        >

        </el-table-column>
        <el-table-column
          prop="useIntegration"
          label="消耗积分"
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
                content="编辑"
                placement="top"
                v-if="scope.row.expire===1||scope.row.expire===2"
              >
                <span
                  class="el-icon-edit-outline iconSpan"
                  @click="gotoEdit(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="分享"
                placement="top"
              >
                <span
                  class="el-icon-share iconSpan"
                  @click="shareHandle(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="停用"
                placement="top"
                 v-if="scope.row.expire===1||scope.row.expire===2"
              >
                <span
                  @click="puaseGroupIntegration(scope.row.id)"
                  class="el-icon-circle-close iconSpan"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="启用"
                placement="top"
                 v-if="scope.row.expire===4"
              >
                <span
                  @click="upGroupIntegration(scope.row.id)"
                  class="el-icon-circle-check iconSpan"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="参与明细"
                placement="top"
              >
                <span
                  @click="gotoDetail(scope.row.id)"
                  class="el-icon-present iconSpan"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="成团明细"
                placement="top"
              >
                <span
                  @click="gotoSuccess(scope.row.id)"
                  class="el-icon-user iconSpan"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="删除"
                placement="top"
                v-if="scope.row.expire===4||scope.row.expire===3"
              >
                <span
                  @click="delGroupIntegration(scope.row.id)"
                  class="el-icon-delete iconSpan"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="活动效果数据"
                placement="top"
              >
                <span
                  @click="gotoAnalysis(scope.row.id)"
                  class="el-icon-data-line iconSpan"
                ></span>
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="seacherGroupIntegrationList"
      />
    </div>
    <shareDialog
      :imgPath="shareImgPath"
      :pagePath="sharePagePath"
      :show="shareDialogShow"
      @close="shareDialogShow=false"
    />
  </div>
</template>
<script>
import { groupIntegrationList, changeGroupIntegrationStatus, delGroupIntegration, shareActivity } from '@/api/admin/marketManage/groupIntegrationList.js'
import shareDialog from '@/components/admin/shareDialog'
import pagination from '@/components/admin/pagination/pagination'
import integrationAdd from './groupIntegrationInfo'
export default {
  components: {
    shareDialog, pagination, integrationAdd
  },
  data () {
    return {
      type: 0,
      activeName: 'first',
      shareImgPath: '',
      sharePagePath: '',
      shareDialogShow: false,
      tableData: [],
      pageParams: {
      },
      showSix: false,
      isEditId: 0,
      sixTitle: '添加瓜分积分活动'
    }
  },
  mounted () {
    // 初始化数据
    this.seacherGroupIntegrationList()
  },
  methods: {

    handleClick (e) {
      this.type = parseInt(e.index)
      this.pageParams.currentPage = 1
      if (this.activeName === 'sixth') {
        this.showSix = true
      } else {
        this.showSix = false
      }
      this.seacherGroupIntegrationList()
    },
    seacherGroupIntegrationList () {
      this.pageParams.type = this.type
      groupIntegrationList(this.pageParams).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          this.pageParams = res.content.page
        }
      })
    },
    // 对过期状态值设置对应显示
    formatter (row, column) {
      let expire = row.expire
      if (expire === 1) {
        return '进行中'
      } else if (expire === 2) {
        return '未开始'
      } else if (expire === 3) {
        return '已过期'
      } else if (expire === 4) {
        return '已停用'
      }
    },
    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        item.content = `${item.limitAmount}人瓜分${item.inteGroup}`
        item.totalIntegration = `${item.inteTotal}积分`
        item.leftIntegration = `剩余：${item.inteRemain}积分`
        // item.actDate = `${item.startTime}至${item.endTime}`
        // item.expire = this.getExpireString(item.expire)
      })
      this.tableData = data
    },

    // 停用瓜分积分活动
    puaseGroupIntegration (id) {
      this.$alert('确定要停用吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning' }).then(() => {
        let data = {
          'id': id,
          'status': 0
        }
        changeGroupIntegrationStatus(data).then(res => {
          console.log(res)
          if (res.error === 0) {
            this.$message.success('停用成功')
            this.seacherGroupIntegrationList()
          } else {
            this.$message.error('停用失败')
          }
        })
      })
    },
    // 启用瓜分积分活动
    upGroupIntegration (id) {
      this.$alert('确定要启用吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning' }).then(() => {
        let data = {
          'id': id,
          'status': 1
        }
        changeGroupIntegrationStatus(data).then(res => {
          console.log(res)
          if (res.error === 0) {
            this.$message.success('启用成功')
            this.seacherGroupIntegrationList()
          } else {
            this.$message.error('启用失败')
          }
        })
      })
    },
    // 删除瓜分积分活动
    delGroupIntegration (id) {
      this.$alert('确定要删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning' }).then(() => {
        delGroupIntegration(id).then(res => {
          if (res.error === 0) {
            this.$message.success('删除成功')
            this.seacherGroupIntegrationList()
          } else {
            this.$message.error('删除失败')
          }
        })
      })
    },
    gotoAnalysis (id) {
      console.log('活动效果数据' + id)
    },
    // 增加瓜分积分活动
    addActivity () {
      // this.$router.push({
      //   name: 'group_integration_add'
      // })
      this.isEditId = 0
      this.showSix = true
      this.activeName = 'sixth'
    },
    // 分享活动
    shareHandle (id) {
      shareActivity(id).then(res => {
        if (res.error === 0) {
          this.shareImgPath = res.content.imgUrl
          this.sharePagePath = res.content.pageUrl
          this.shareDialogShow = true
        }
      })
    },
    // 编辑活动
    gotoEdit (id) {
      // this.$router.push(`/admin/home/main/integration/edit/${id}`)
      this.sixTitle = '编辑瓜分积分活动'
      this.showSix = true
      this.isEditId = id
      this.activeName = 'sixth'
    },

    // 前往参与瓜分积分活动的用户明细页面
    gotoDetail (id) {
      this.$router.push(`/admin/home/main/integration/detail/${id}`)
    },
    // 前往成团明细页面
    gotoSuccess (id) {
      this.$router.push(`/admin/home/main/integration/success/${id}`)
    },
    backHome (data) {
      if (data.flag === 6) {
        this.showSix = false
        this.activeName = 'first'
        this.type = 0
        this.seacherGroupIntegrationList()
      }
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
.paginationfooter {
  padding: 20px 0 20px 20px;
  display: flex;
  justify-content: flex-end;
  span {
    display: block;
    height: 32px;
    line-height: 32px;
  }
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
.iconSpan {
  font-size: 22px;
  color: #5a8bff;
  cursor: pointer !important;
  margin-top: 5px;
}
</style>
