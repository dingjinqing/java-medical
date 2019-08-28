<template>
  <div class="content">
    <div class="main">
      <statusTab
        v-model="tabIndex"
        :activityName="activityName"
        :standard="true"
      />
      <div class="wrapper">
        <el-button
          type="primary"
          @click="addActivity"
        >添加砍价活动</el-button>

        <div class="rightContent">
          <span>砍价设置：每个被邀请的用户，单日可帮助砍价 </span>
          <el-input
            v-model="dailyCutTimes"
            style="width: 80px"
            size="small"
          ></el-input>
          <span>次</span>
          <span>设置为空时，不限制帮助砍价次数</span>
          <el-button
            @click="updateDailyCutTimes"
            type="primary"
            size="small"
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
          prop="bargainName"
          label="砍价活动名称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="bargainType"
          label="活动类型"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="vaildDate"
          label="有效期"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="statusName"
          label="活动状态"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="goodsName"
          label="商品名称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="goodsNumber"
          label="商品库存"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="stock"
          label="砍价库存"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="successNumber"
          label="成功数量"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="bargainUserNumber"
          label="发起砍价人数"
          align="center"
        >
        </el-table-column>

        <el-table-column
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <el-tooltip
              content="编辑"
              placement="top"
            >
              <i
                class="el-icon-edit-outline"
                style="color:#409EFF;fontSize:16px"
              ></i>
            </el-tooltip>
            <el-tooltip
              v-if="scope.row.status === 1"
              content="停用"
              placement="top"
            >
              <i
                @click="puaseBargain(scope.row.id)"
                class="el-icon-remove-outline"
                style="color:#409EFF;fontSize:16px"
              ></i>
            </el-tooltip>
            <el-tooltip
              v-else
              content="启用"
              placement="top"
            >
              <i
                @click="enableBargain(scope.row.id)"
                class="el-icon-check"
                style="color:#409EFF;fontSize:16px"
              ></i>
            </el-tooltip>
            <el-tooltip
              content="分享"
              placement="top"
            >
              <i
                @click="shareBargain(scope.row.id)"
                class="el-icon-share"
                style="color:#409EFF;fontSize:16px"
              ></i>
            </el-tooltip>
            <el-tooltip
              content="删除"
              placement="top"
            >
              <i
                @click="delBargain(scope.row.id)"
                class="el-icon-delete"
                style="color:#409EFF;fontSize:16px"
              ></i>
            </el-tooltip>

          </template>
        </el-table-column>
      </el-table>
      <div class="footer">
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        />
      </div>
    </div>
  </div>

</template>
<script>
import { bargainList, updateBargain, deleteBargain, getDailyCutTimes, setDailyCutTimes, getBargainShareCode } from '@/api/admin/marketManage/bargain.js'
import statusTab from '@/components/admin/status/statusTab'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination, statusTab },
  data () {
    return {
      activityName: '砍价',
      // 默认显示进行中的活动
      tabIndex: 1,
      currentPage: 1,
      tableData: [],
      pageParams: {},
      dailyCutTimes: 0
    }
  },
  mounted () {
    // 初始列表化数据
    this.initDataList()
    // 取单日可帮助砍价的次数
    getDailyCutTimes().then((res) => {
      if (res.error === 0) {
        this.dailyCutTimes = res.content
      }
    })
  },
  methods: {
    initDataList () {
      let param = {
        'state': parseInt(this.tabIndex),
        'currentPage': 1
      }

      bargainList(param).then((res) => {
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          this.pageParams = res.content.page
        }
      })
    },

    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        item.bargainType = item.bargainType === 0 ? '定人' : '任意价'
        item.vaildDate = `${item.startTime}至${item.endTime}`
        item.statusName = this.getActStatusString(item.status, item.startTime, item.endTime)
      })
      this.tableData = data
    },

    // 停用砍价
    puaseBargain (id) {
      let param = {
        'id': id,
        'status': 0
      }
      this.$confirm('确定停用该砍价活动?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updateBargain(param).then((res) => {
          if (res.error === 0) {
            this.$message({
              type: 'success',
              message: '停用成功!'
            })
            this.initDataList()
          }
        })
      })
    },

    // 启用砍价
    enableBargain (id) {
      let param = {
        'id': id,
        'status': 1
      }
      this.$confirm('确定启用该砍价活动?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updateBargain(param).then((res) => {
          if (res.error === 0) {
            this.$message({
              type: 'success',
              message: '启用成功!'
            })
            this.initDataList()
          }
        })
      })
    },

    // 删除砍价
    delBargain (id) {
      let param = {
        'id': id
      }
      this.$confirm('确定删除该砍价活动?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteBargain(param).then((res) => {
          if (res.error === 0) {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.initDataList()
          }
        })
      })
    },
    // 设置砍价取单日可帮助砍价的次数
    updateDailyCutTimes () {
      setDailyCutTimes(this.dailyCutTimes).then((res) => {
        if (res.error === 0) {
          this.$message({
            type: 'success',
            message: '设置成功!'
          })
        } else {
          this.$message({
            type: 'fail',
            message: '设置失败!'
          })
        }
      })
    },

    // 取活动分享二维码
    shareBargain (id) {
      getBargainShareCode(id).then((res) => {
        console.log(res)
      })
    },

    addActivity () {
      this.$router.push({
        name: 'bargain_activity'
      })
    }
  },
  watch: {
    'tabIndex' (n, o) {
      this.initDataList()
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
