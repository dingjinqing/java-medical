<template>
  <div class="content">
    <div class="main">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          label="全部优惠券"
          name="first"
        >
          <div class="wrapper">
            <span>优惠券名称：</span>
            <el-input
              size="medium"
              placeholder="请输入优惠券名称"
              class='search_content'
            >
            </el-input>
            <el-button
              type="primary"
              size="medium"
              @click="addCoupon()"
              class="barginBtn"
            >添加优惠券</el-button>
          </div>
        </el-tab-pane>
        <el-tab-pane
          label="进行中"
          name="second"
        >
          <div class="wrapper">
            <span>优惠券名称：</span>
            <el-input
              size="medium"
              placeholder="请输入优惠券名称"
              class='search_content'
            >
            </el-input>
            <el-button
              type="primary"
              size="medium"
              @click="addCoupon()"
              class="barginBtn"
            >添加优惠券</el-button>
          </div>
        </el-tab-pane>
        <el-tab-pane
          label="未开始"
          name="third"
        >
          <div class="wrapper">
            <span>优惠券名称：</span>
            <el-input
              size="medium"
              placeholder="请输入优惠券名称"
              class='search_content'
            >
            </el-input>
            <el-button
              type="primary"
              size="medium"
              @click="addCoupon()"
              class="barginBtn"
            >添加优惠券</el-button>
          </div>
        </el-tab-pane>
        <el-tab-pane
          label="已过期"
          name="fourth"
        >
          <div class="wrapper">
            <span>优惠券名称：</span>
            <el-input
              size="medium"
              placeholder="请输入优惠券名称"
              class='search_content'
            >
            </el-input>
            <el-button
              type="primary"
              size="medium"
              @click="addCoupon()"
              class="barginBtn"
            >添加优惠券</el-button>
          </div>
        </el-tab-pane>
        <el-tab-pane
          label="已停用"
          name="fifth"
        >
          <div class="wrapper">
            <span>优惠券名称：</span>
            <el-input
              size="medium"
              placeholder="请输入优惠券名称"
              class='search_content'
            >
            </el-input>
            <el-button
              type="primary"
              size="medium"
              @click="addCoupon()"
              class="barginBtn"
            >添加优惠券</el-button>
          </div>
        </el-tab-pane>
      </el-tabs>
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
          prop="remainAmount"
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
              <span>领取明细</span>
              <span @click="delCoupon(scope.row.id)">删除</span>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="footer">
        <span>当前页面1/1，总记录4条</span>
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :page-size="20"
          layout="prev, pager, next, jumper"
          :total="4"
        >
        </el-pagination>
      </div>
    </div>
  </div>

</template>
<script>
import { couponList, pauseCoupon, deleteCoupon } from '@/api/admin/marketManage/couponList.js'
export default {
  data () {
    return {
      tableData: [],
      activeName: 'second',
      currentPage: 1
    }
  },
  mounted () {
    // 初始化数据
    this.handleClick()
  },
  methods: {

    handleClick (tab) {
      alert(tab.index)
      let obj = {
        'nav': parseInt(tab.index) + 1,
        'currentPage': 1,
        'pageRows': 1
      }

      couponList(obj).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.handleData(res.content.dataList)
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
      console.log(id)
      pauseCoupon(id).then(res => {
        if (res.error === 0) {
          alert('停用成功！')
        }
        console.log(res)
      })
    },
    // 删除优惠券
    delCoupon (id) {
      deleteCoupon(id).then(res => {
        if (res.error === 0) {
          alert('删除成功！')
          this.seacherCouponList()
        }
      })
    },
    addCoupon () {
      this.$router.push({
        name: 'add_coupon'
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
  width: 15%;
  margin-left: -70%;
}
.opt {
  text-align: left;
  color: #5a8bff;
  span {
    cursor: pointer;
  }
}
</style>
