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
        >{{$t('bargainList.addBargain')}}</el-button>

        <div class="rightContent">
          <span>{{$t('bargainList.cutTimesSetTip1')}}</span>
          <el-input-number
            v-model="dailyCutTimes"
            controls-position="right"
            style="width: 100px;margin: 0 5px;"
            size="small"
            :precision="0"
            :min="0"
          ></el-input-number>
          <span>{{$t('bargainList.times')}}</span>
          <span>{{$t('bargainList.cutTimesSetTip2')}}</span>
          <el-button
            @click="updateDailyCutTimes"
            type="primary"
            size="small"
          >{{$t('bargainList.settingSave')}}</el-button>
        </div>
      </div>
    </div>
    <div class="table_list">
      <el-table
        v-loading="loading"
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="bargainName"
          :label="$t('bargainList.bargainName')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="bargainType"
          :label="$t('bargainList.bargainType')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="vaildDate"
          :label="$t('bargainList.vaildDate')"
          align="center"
          width="170px"
        >
          <template slot-scope="scope">
            {{scope.row.startTime}}<br>至<br>{{scope.row.endTime}}
          </template>
        </el-table-column>

        <el-table-column
          prop="statusName"
          :label="$t('bargainList.statusName')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="goodsName"
          :label="$t('bargainList.goodsName')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="goodsNumber"
          :label="$t('bargainList.goodsNumber')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="stock"
          :label="$t('bargainList.stock')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="successNumber"
          :label="$t('bargainList.successNumber')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="bargainUserNumber"
          :label="$t('bargainList.bargainUserNumber')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          :label="$t('marketCommon.operate')"
          align="center"
        >
          <template slot-scope="scope">
            <div class="operation">
              <el-tooltip
                class="item"
                effect="dark"
                :content="$t('marketCommon.edit')"
                placement="top"
                v-if="scope.row.status === 1"
              >
                <i
                  class="el-icon-edit-outline"
                  @click="edit(scope.row.id)"
                ></i>
              </el-tooltip>
              <el-tooltip
                class="item"
                effect="dark"
                :content="$t('marketCommon.delete')"
                placement="top"
                v-else
              >
                <i
                  @click="delBargain(scope.row.id)"
                  class="el-icon-delete"
                ></i>
              </el-tooltip>
              <el-tooltip
                v-if="scope.row.status === 1"
                class="item"
                effect="dark"
                :content="$t('marketCommon.disable')"
                placement="top"
              >
                <i
                  @click="puaseBargain(scope.row.id)"
                  class="el-icon-circle-close"
                ></i>
              </el-tooltip>
              <el-tooltip
                v-else
                class="item"
                effect="dark"
                :content="$t('marketCommon.enabled')"
                placement="top"
              >
                <i
                  @click="enableBargain(scope.row.id)"
                  class="el-icon-check"
                ></i>
              </el-tooltip>
              <el-tooltip
                class="item"
                effect="dark"
                :content="$t('marketCommon.share')"
                placement="top"
              >
                <i
                  @click="shareBargain(scope.row.id)"
                  class="el-icon-share"
                ></i>
              </el-tooltip>
              <el-tooltip
                class="item"
                effect="dark"
                :content="$t('bargainList.getBargainOrders')"
                placement="top"
              >
                <i
                  class="el-icon-tickets"
                  @click="checkOrderList(scope.row.id)"
                ></i>
              </el-tooltip>
              <el-tooltip
                class="item"
                effect="dark"
                :content="$t('marketCommon.getSourceUserList')"
                placement="top"
              >
                <i
                  class="el-icon-user-solid"
                  @click="getNewUserDetail(scope.row.id)"
                ></i>
              </el-tooltip>
              <el-tooltip
                class="item"
                effect="dark"
                :content="$t('bargainList.bargainRecordList')"
                placement="top"
              >
                <i
                  class="el-icon-s-unfold"
                  @click="bargainingUser(scope.row.id)"
                ></i>
              </el-tooltip>
              <el-tooltip
                class="item"
                effect="dark"
                :content="$t('marketCommon.activityEffectData')"
                placement="top"
              >
                <i
                  class="el-icon-s-data"
                  @click="effectData(scope.row.id,scope.row.startTime,scope.row.endTime)"
                ></i>
              </el-tooltip>
            </div>
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
import statusTab from '@/components/admin/marketManage/status/statusTab'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination, statusTab },
  data () {
    return {
      activityName: this.$t('bargainList.bargain'),
      // 默认显示进行中的活动
      tabIndex: 1,
      currentPage: 1,
      tableData: [],
      pageParams: {},
      loading: false,
      dailyCutTimes: 0,

      // 表格原始数据
      originalData: []
    }
  },
  watch: {
    'tabIndex' (n, o) {
      this.initDataList()
    },

    // data内变量国际化
    lang () {
      this.activityName = this.$t('bargainList.bargain')

      // 重新渲染表格数据
      let originalData = JSON.parse(JSON.stringify(this.originalData))
      this.handleData(originalData)
    }
  },
  mounted () {
    this.langDefault()
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
      this.loading = true
      this.pageParams.state = [parseInt(this.tabIndex)]
      bargainList(this.pageParams).then((res) => {
        if (res.error === 0) {
          this.originalData = res.content.dataList
          let originalData = JSON.parse(JSON.stringify(this.originalData))
          this.handleData(originalData)
          this.pageParams = res.content.page
          this.loading = false
        } else {
          this.loading = false
          this.$message.error(res.message)
        }
      })
    },

    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        item.bargainType = item.bargainType === 0 ? this.$t('bargainList.bargainType0') : this.$t('bargainList.bargainType1')
        // item.vaildDate = `${item.startTime} ` + this.$t('marketCommon.to') + ` ${item.endTime}`
        item.statusName = this.getActStatusString(item.currentState)
      })
      this.tableData = data
    },

    // 停用砍价
    puaseBargain (id) {
      let param = {
        'id': id,
        'status': 0
      }
      this.$confirm(this.$t('marketCommon.actDisableConfirmTip'), this.$t('marketCommon.tip'), {
        confirmButtonText: this.$t('marketCommon.ok'),
        cancelButtonText: this.$t('marketCommon.cancel'),
        type: 'warning'
      }).then(() => {
        updateBargain(param).then((res) => {
          if (res.error === 0) {
            this.$message.success(this.$t('marketCommon.successfulOperation'))
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
      this.$confirm(this.$t('marketCommon.actEnabledConfirmTip'), this.$t('marketCommon.tip'), {
        confirmButtonText: this.$t('marketCommon.ok'),
        cancelButtonText: this.$t('marketCommon.cancel'),
        type: 'warning'
      }).then(() => {
        updateBargain(param).then((res) => {
          if (res.error === 0) {
            this.$message.success(this.$t('marketCommon.successfulOperation'))
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
      this.$confirm(this.$t('marketCommon.actDeleteConfirmTip'), this.$t('marketCommon.tip'), {
        confirmButtonText: this.$t('marketCommon.ok'),
        cancelButtonText: this.$t('marketCommon.cancel'),
        type: 'warning'
      }).then(() => {
        deleteBargain(param).then((res) => {
          if (res.error === 0) {
            this.$message.success(this.$t('marketCommon.successfulOperation'))
            this.initDataList()
          }
        })
      })
    },
    // 设置砍价取单日可帮助砍价的次数
    updateDailyCutTimes () {
      setDailyCutTimes(this.dailyCutTimes).then((res) => {
        if (res.error === 0) {
          this.$message.success(this.$t('marketCommon.successfulOperation'))
        } else {
          this.$message.error(this.$t('marketCommon.failOperation'))
        }
      })
    },

    // 取活动分享二维码
    shareBargain (id) {
      getBargainShareCode(id).then((res) => {
        console.log(res)
      })
    },

    // 编辑点击事件
    edit (id) {
      this.$router.push({
        path: '/admin/home/main/bargain/add',
        query: {
          id: id
        }
      })
    },

    // 跳转砍价订单页
    checkOrderList (id) {
      this.$router.push({
        path: '/admin/home/main/bargain/orderList',
        query: {
          id: id
        }
      })
    },

    // 跳转到获取新用户明细页面
    getNewUserDetail (id) {
      this.$router.push({
        path: '/admin/home/main/bargain/getNewUserList',
        query: {
          id: id
        }
      })
    },

    // 跳转到查看发起砍价用户页面
    bargainingUser (id) {
      this.$router.push({
        path: '/admin/home/main/bargain/bargainingUser',
        query: {
          id: id
        }
      })
    },

    // 跳转到活动效果数据页面
    effectData (id, startTime, endTime) {
      let d = new Date()
      if (d < new Date(endTime)) {
        endTime = this.moment(d).format('YYYY-MM-DD HH:mm:ss')
      }
      this.$router.push({
        path: '/admin/home/main/bargain/effectData',
        query: {
          id: id,
          startTime: startTime,
          endTime: endTime
        }
      })
    },

    // 跳转到添加页
    addActivity () {
      this.$router.push({
        name: 'bargain_activity'
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
.operation {
  display: flex;
  flex-wrap: wrap;
  margin-left: -5px;
  > .item {
    font-size: 22px;
    color: #66b1ff;
    cursor: pointer;
    margin-left: 5px;
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
