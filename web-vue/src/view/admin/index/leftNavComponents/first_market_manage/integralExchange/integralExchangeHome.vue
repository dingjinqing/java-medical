<template>
  <div class="content">
    <div class="main">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          label="全部积分兑换活动"
          name="first"
        >
        </el-tab-pane>
        <el-tab-pane
          label="进行中"
          name="second"
        >
        </el-tab-pane>
        <el-tab-pane
          label="未开始"
          name="third"
        >
        </el-tab-pane>
        <el-tab-pane
          label="已过期"
          name="fourth"
        >
        </el-tab-pane>
        <el-tab-pane
          label="已停用"
          name="fifth"
        >

        </el-tab-pane>
      </el-tabs>
      <el-button
        type="primary"
        @click="addActivity"
      >添加瓜分积分活动</el-button>
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
          prop="content"
          label="商品名称"
          align="center"
        >
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
          label="兑换现金(元)"
          align="center"
          width="200"
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
          prop="expire"
          label="兑换积分数"
          align="center"
          :formatter="formatter"
        >

        </el-table-column>
        <el-table-column
          prop="inteUserSum"
          label="商品库存"
          align="center"
          width="90"
        >

        </el-table-column>
        <el-table-column
          prop="inteGroupSum"
          label="积分兑换库存"
          align="center"
          width="90"
        >

        </el-table-column>
        <el-table-column
          prop="useIntegration"
          label="已兑换数量"
          align="center"
          width="90"
        >

        </el-table-column>
        <el-table-column
          prop="useIntegration"
          label="兑换用户数"
          align="center"
          width="90"
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
export default {
  data () {
    return {
      activeName: 'second',
      shareImgPath: '',
      sharePagePath: '',
      shareDialogShow: false,
      tableData: [],
      pageParams: {
      },
      isEditId: 0,
      sixTitle: '添加瓜分积分活动'
    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    let params = {
      'index': 1
    }
    this.handleClick(params)
    // this.seacherGroupIntegrationList()
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

    },
    // 对过期状态值设置对应显示
    formatter (row, column) {

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

    },
    // 启用瓜分积分活动
    upGroupIntegration (id) {

    },
    // 删除瓜分积分活动
    delGroupIntegration (id) {

    },
    // 增加瓜分积分活动
    addActivity () {

    },
    // 分享活动
    shareHandle (id) {

    },
    // 编辑活动
    gotoEdit (id) {

    },

    // 前往参与瓜分积分活动的用户明细页面
    gotoDetail (id) {

    },
    // 前往成团明细页面
    gotoSuccess (id) {

    },
    gotoAnalysis (id) {

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
  padding: 10px 20px 20px 20px;
  min-height: 380px;
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
