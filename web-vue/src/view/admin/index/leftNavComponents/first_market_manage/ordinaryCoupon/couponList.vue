<template>
  <div class="content">
    <!-- tabs -->
    <div class="main">
      <statusTab
        v-model="nav"
        :activityName="activityName"
        :standard="true"
      />
      <div class="wrapper">
        <span>{{$t('ordinaryCouponList.couponName')}}：</span>
        <el-input
          size="small"
          v-model="actName"
          clearable
          :placeholder="$t('ordinaryCouponList.inputPlaceholder')"
          class='search_content'
        >
        </el-input>
        <el-button
          type="primary"
          size="small"
          @click="handleClick"
          class="btn"
        >{{$t('ordinaryCouponList.search')}}</el-button>
        <el-button
          type="primary"
          size="small"
          @click="addCoupon()"
          class="barginBtn"
        >{{$t('ordinaryCouponList.addCoupon')}}</el-button>
      </div>
    </div>

    <!-- 表格 -->
    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="actName"
          :label="$t('ordinaryCouponList.couponName')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop=""
          :label="$t('ordinaryCouponList.type')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="scoreNumber"
          :label="$t('ordinaryCouponList.pointsExchange')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="denomination"
          :label="$t('ordinaryCouponList.value')"
          align="center"
          width="80"
        >
        </el-table-column>
        <el-table-column
          prop="leastConsume"
          :label="$t('ordinaryCouponList.minConsume')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="surplus"
          :label="$t('ordinaryCouponList.inventory')"
          align="center"
          width="180"
        >
        </el-table-column>
        <el-table-column
          prop="receivePerPerson"
          :label="$t('ordinaryCouponList.receiveLimit')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="vaildDate"
          :label="$t('ordinaryCouponList.validityDay')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="receivePerson"
          :label="$t('ordinaryCouponList.receiveTimes')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="giveOutPerson"
          :label="$t('ordinaryCouponList.sendTimes')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="used"
          :label="$t('ordinaryCouponList.use')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          :label="$t('ordinaryCouponList.operate')"
          align="center"
          width="130"
        >
          <template slot-scope="scope">
            <div class="opt">
              <el-tooltip
                :content="$t('ordinaryCouponList.edit')"
                placement="top"
                v-if="scope.row.currentState === 2 || scope.row.currentState === 1"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-edit-outline"
                  @click="updateCoupon(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('ordinaryCouponList.share')"
                placement="top"
                v-if="scope.row.currentState === 2 || scope.row.currentState === 1"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-share"
                  @click="shareCoupon(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('ordinaryCouponList.disableUse')"
                placement="top"
                v-if="scope.row.currentState === 2 || scope.row.currentState === 1"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-circle-close"
                  @click="puaseCoupon(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('ordinaryCouponList.enableUse')"
                placement="top"
                v-if="scope.row.currentState === 4"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-circle-check"
                  @click="startCoupon(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('ordinaryCouponList.reveiveDetails')"
                placement="top"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-tickets"
                  @click="receiveDetails(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('ordinaryCouponList.delete')"
                placement="top"
                v-if="scope.row.currentState === 3 || scope.row.currentState === 4"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-delete"
                  @click="delCoupon(scope.row.id)"
                ></span>
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="handleClick"
      />
    </div>

    <!-- 分享 -->
    <el-dialog
      :title="$t('seckill.shareTitle')"
      :visible.sync="shareDialog"
      width="350px"
      center
      :close-on-click-modal="false"
    >
      <div style="width: 100%; text-align: center; margin-bottom: 15px; border-bottom: 1px solid #ccc;">
        <div>
          <img
            :src="shareImg"
            alt=""
            style="width:160px;height:160px;"
          >
        </div>
        <div style="margin: 20px; 0">
          <a
            v-if="shareImg !== null"
            :href="shareImg"
            download
            style="color: #999;text-decoration: none;"
          >{{ $t('seckill.downLoad') }}</a>
          <a
            v-if="shareImg === null"
            href="javaScript:void(0);"
            style="color: #999;text-decoration: none;"
          >{{ $t('seckill.downLoadFail') }}</a>
        </div>
      </div>
      <div>
        <el-input v-model="sharePath">
          <el-button
            slot="append"
            v-clipboard:copy="sharePath"
            v-clipboard:success="copyHandler"
          >{{ $t('seckill.copy') }}</el-button>
        </el-input>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="shareDialog = false">{{ $t('seckill.cancel') }}</el-button>
        <el-button
          type="primary"
          @click="shareDialog = false"
        >{{ $t('seckill.sure') }}</el-button>
      </span>
    </el-dialog>

  </div>

</template>
<script>
// 引入分页
import pagination from '@/components/admin/pagination/pagination'
import statusTab from '@/components/admin/marketManage/status/statusTab'
import { couponList, pauseCoupon, deleteCoupon, startCoupon } from '@/api/admin/marketManage/couponList.js'
export default {
  components: {
    statusTab, pagination
  },
  data () {
    return {
      nav: 0,
      actName: null, // 搜索条件
      activityName: this.$t('ordinaryCouponList.coupon'),
      tableData: [],
      pageParams: {}, // 分页
      requestParams: {},
      shareDialog: false, // 分享弹窗
      shareImg: '',
      sharePath: ''
    }
  },
  mounted () {
    // 初始化数据
    this.handleClick()
  },
  methods: {
    // 优惠券列表
    handleClick () {
      this.requestParams.nav = this.nav
      if (this.actName === '') {
        this.actName = null
      }
      this.requestParams.actName = this.actName
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      couponList(this.requestParams).then((res) => {
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          this.pageParams = res.content.page
        }
      })
    },

    // 表格数据处理
    handleData (data) {
      console.log(data)
      data.map((item, index) => {
        if (item.receivePerPerson === 0) {
          item.receivePerPerson = '不限制'
        } else {
          item.receivePerPerson = `限领${item.receivePerPerson} 张`
        }
        if (item.scoreNumber === 0) {
          item.scoreNumber = '不需要兑换'
        } else {
          item.scoreNumber = `${item.scoreNumber}积分兑换`
        }
        if (item.useConsumeRestrict === 0) {
          item.leastConsume = '无门槛'
        }
        if (item.actCode === 'discount') {
          item.denomination = `打${item.denomination}折`
        }
        if (item.validityType === 1) {
          item.vaildDate = `领取开始${item.validity}天${item.validityHour}小时${item.validityMinute}分内有效`
        } else {
          item.vaildDate = `${item.startTime}至${item.endTime} `
        }
        item.receivePerson = `${item.receivePerson} /${item.receiveAmount}`
        item.giveOutPerson = `${item.giveoutPerson}/${item.giveoutAmount}`
      })
      this.tableData = data
      console.log(this.tableData)
    },

    // 编辑优惠券
    updateCoupon (id) {
      this.$router.push({
        path: '/admin/home/main/addyCoupon',
        query: {
          id: id
        }
      })
    },

    // 添加优惠券
    addCoupon () {
      this.$router.push({
        name: 'add_coupon'
      })
    },

    // 删除优惠券
    delCoupon (id) {
      this.$confirm(this.$t('ordinaryCouponList.couponDelTips'), this.$t('ordinaryCouponList.tips'), {
        confirmButtonText: this.$t('ordinaryCouponList.confirm'),
        cancelButtonText: this.$t('ordinaryCouponList.cancle'),
        type: 'warning'
      }).then(() => {
        deleteCoupon(id).then(res => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('ordinaryCouponList.delSuccess') })
            this.handleClick()
          }
        })
      }).catch(() => {
        this.$message.info({ message: this.$t('ordinaryCouponList.cancleDel') })
      })
    },

    // 领取明细
    receiveDetails (id) {
      this.$router.push({
        path: '/admin/home/main/ordinaryCoupon/receiveDetails',
        query: {
          id: id
        }
      })
    },

    // 停用优惠券
    puaseCoupon (id) {
      this.$confirm(this.$t('ordinaryCouponList.couponActStopTip'), this.$t('ordinaryCouponList.tips'), {
        confirmButtonText: this.$t('ordinaryCouponList.confirm'),
        cancelButtonText: this.$t('ordinaryCouponList.cancle'),
        type: 'warning'
      }).then(() => {
        pauseCoupon(id).then(res => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('ordinaryCouponList.stopSuccess') })
            this.handleClick()
          }
        })
      }).catch(() => {
        this.$message.info({ message: this.$t('ordinaryCouponList.cancleStop') })
      })
    },

    // 启用优惠券
    startCoupon (id) {
      this.$confirm(this.$t('ordinaryCouponList.couponActStartTip'), this.$t('ordinaryCouponList.tips'), {
        confirmButtonText: this.$t('ordinaryCouponList.confirm'),
        cancelButtonText: this.$t('ordinaryCouponList.cancle'),
        type: 'warning'
      }).then((res) => {
        startCoupon(id).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('ordinaryCouponList.startSuccess') })
            this.handleClick()
          }
        })
      }).catch(() => {
        this.$message.info({ message: this.$t('ordinaryCouponList.cancleStart') })
      })
    },

    // 分享优惠券
    shareCoupon (id) {
      this.shareDialog = true
    },

    // 复制
    copyHandler (e) {
      this.$message.success({ message: this.$t('seckill.copySuccess') })
    }
  },
  watch: {
    'nav' (n, o) {
      this.handleClick()
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
    span {
      line-height: 30px;
    }
    .btn {
      margin-left: 10px;
    }
    .barginBtn {
      float: right;
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
  padding: 15px;
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
  text-align: left;
  color: #5a8bff;
  span {
    cursor: pointer;
  }
}
</style>
