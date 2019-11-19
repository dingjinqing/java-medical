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
        <span>优惠券名称：</span>
        <el-input
          size="medium"
          v-model="actName"
          clearable
          placeholder="请输入优惠券名称"
          class='search_content'
        >
        </el-input>
        <el-button
          type="primary"
          size="medium"
          @click="handleClick"
        >查询</el-button>
        <el-button
          type="primary"
          size="medium"
          @click="addCoupon()"
          class="barginBtn"
        >添加优惠券</el-button>
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
          label="优惠券名称"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="scoreNumber"
          label="积分兑换"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="denomination"
          label="价值"
          align="center"
          width="80"
        >
        </el-table-column>
        <el-table-column
          prop="leastConsume"
          label="最低消费"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="surplus"
          label="库存"
          align="center"
          width="80"
        >
        </el-table-column>
        <el-table-column
          prop="receivePerPerson"
          label="领取限制"
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
          prop="receivePerson"
          label="领取人/次"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="giveOutPerson"
          label="发放人/次"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="used"
          label="已使用"
          align="center"
        >
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          width="130"
        >
          <template slot-scope="scope">
            <div class="opt">
              <el-tooltip
                content="编辑"
                placement="top"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-edit-outline"
                  @click="updateCoupon(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="分享"
                placement="top"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-share"
                  @click="shareCoupon(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="停用"
                placement="top"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-circle-close"
                  @click="puaseCoupon(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="启用"
                placement="top"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-circle-check"
                  @click="startCoupon(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="领取明细"
                placement="top"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-tickets"
                  @click="receiveDetails(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="删除"
                placement="top"
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
import { couponList, pauseCoupon, deleteCoupon } from '@/api/admin/marketManage/couponList.js'
export default {
  components: {
    statusTab, pagination
  },
  data () {
    return {
      nav: 0,
      actName: null,
      activityName: '优惠券',
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
          item.vaildDate = `${item.startTime} 至${item.endTime} `
        }
        item.receivePerson = `${item.receivePerson} /${item.receiveAmount}`
        item.giveOutPerson = `${item.giveoutPerson}/${item.giveoutAmount}`
      })
      this.tableData = data
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
      this.$confirm('此操作将永久删除该优惠券活动, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteCoupon(id).then(res => {
          if (res.error === 0) {
            this.$message.success({ message: '删除成功!' })
            this.handleClick()
          }
        })
      }).catch(() => {
        this.$message.info({ message: '已取消删除' })
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
      this.$confirm('此操作将停用该优惠券活动, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        pauseCoupon(id).then(res => {
          if (res.error === 0) {
            this.$message.success({ message: '停用成功!' })
            this.handleClick()
          }
        })
      }).catch(() => {
        this.$message.info({ message: '已取消停用' })
      })
    },

    // 启用优惠券
    startCoupon (id) {
      this.$confirm('此操作将启用该优惠券活动, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then((res) => {
        this.$message.success({ message: '启用成功!' })
      }).catch(() => {
        this.$message.info({ message: '已取消启用' })
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
    padding: 10px 20px 10px 20px;
    .wrapper {
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
      .barginBtn {
        float: right;
      }
    }
    span {
      line-height: 40px;
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
