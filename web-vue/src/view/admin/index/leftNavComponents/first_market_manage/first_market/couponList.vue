<template>
  <div class="content">
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
          prop="useScore"
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
          prop="useConsumeRestrict"
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
              <span>编辑</span>
              <span>分享</span>
              <span @click="puaseCoupon(scope.row.id)">停用</span>
              <span @click="receiveDetails(scope.row.id)">领取明细</span>
              <span @click="delCoupon(scope.row.id)">删除</span>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="handleClick"
      />
    </div>

  </div>

</template>
<script>
// 引入分页
import pagination from '@/components/admin/pagination/pagination'
import statusTab from '@/components/admin/status/statusTab'
import { couponList, pauseCoupon, deleteCoupon } from '@/api/admin/marketManage/couponList.js'
export default {
  components: {
    statusTab, pagination
  },
  data () {
    return {
      actName: null,
      activityName: '优惠券',
      tableData: [],
      activeName: 'second',
      currentPage: 1,
      nav: 0,
      pageParams: {}
    }
  },
  mounted () {
    // 初始化数据
    this.handleClick()
  },
  methods: {
    // 当前页发生变化
    handleCurrentChange () {
      console.log(this.currentPage)
    },

    handleClick () {
      this.pageParams.nav = this.nav
      this.pageParams.actName = this.actName
      couponList(this.pageParams).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          this.pageParams = res.content.page
        }
      })
    },
    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        //   if (item.surplus === 0) {
        //     item.surplus = 'asd'
        //   }
        //   item.surplus =
        item.vaildDate = `${item.startTime}至${item.endTime}`
        item.receivePerson = `${item.receivePerson}/${item.receiveAmount}`
        item.giveOutPerson = `${item.giveoutPerson}/${item.giveoutAmount}`
      })
      this.tableData = data
    },
    // 停用优惠券
    puaseCoupon (id) {
      this.$confirm('此操作将永久删除该优惠券活动, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        pauseCoupon(id).then(res => {
          if (res.error === 0) {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.couponList()
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
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
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.seacherCouponList()
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    addCoupon () {
      this.$router.push({
        name: 'add_coupon'
      })
    },
    receiveDetails () {
      this.$router.push({
        name: 'ordinary_coupon_receive_details'
      })
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
  width: 150px;
}
.opt {
  text-align: left;
  color: #5a8bff;
  span {
    cursor: pointer;
  }
}
</style>
