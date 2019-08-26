<template>
  <div class="content">
    <div class="main">
      <el-tabs v-model="tabActive">
        <el-tabs>
          <div class="rightContent">
            <span>手机号</span>
            <input type="text">
            <span>用户昵称</span>
            <input type="text">
            <span>商品名称</span>
            <input type="text">
            <span>奖励等级</span>
            <input type="text">
            <el-button
              type="primary"
              @click="searchByCondition"
            >查询</el-button>
          </div>
        </el-tabs>
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
          prop="userId"
          label="用户id"
          align="center"
        >

        </el-table-column>
        <el-table-column
          prop="username"
          label="用户昵称"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="mobile"
          label="手机号"
          align="center"
          width="80"
        >
        </el-table-column>
        <el-table-column
          prop="goodsName"
          label="分享商品"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="awardLevel"
          label="奖励级别"
          align="center"
          width="80"
        >

        </el-table-column>
        <el-table-column
          prop="inviteUserNum"
          label="邀请用户总数"
          align="center"
        >

        </el-table-column>
        <el-table-column
          prop="inviteNewUserNum"
          label="邀请新用户数"
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
          prop="createTime"
          label="领取时间"
          align="center"
        >
        </el-table-column>
      </el-table>
    </div>
  </div>

</template>
<script>
import { getReceiveDetail } from '@/api/admin/marketManage/sharePolite.js'
export default {
  data () {
    return {
      tableData: []
    }
  },
  mounted () {
    this.langDefault()
    // 页面加载时调用接口初始化数据
    this.defaultSeach()
  },
  methods: {
    defaultSeach () {
      let obj = {
        'shareId': this.$route.params.id,
        'currentPage ': 0,
        'pageRows ': 20
      }
      console.log(obj)
      getReceiveDetail(obj).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.handleData(res.content.dataList)
        }
      })
    },
    created () {
      console.log(this.$router.params)
      if (this.$router.params.flag === true) {
        this.shareId = this.$router.params.id
      }
    },
    // 条件查询
    searchByCondition () {
      let obj = {
        'currentPage ': 0,
        'pageRows ': 20
      }

      getReceiveDetail(obj).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.handleData(res.content.dataList)
        }
      })
    },
    // 表格数据处理
    handleData (data) {
      //   数组遍历
      data.map((item, index) => {
        switch (item.rewardType) {
          case 1:
            item.rewardType = '积分'
            break
          case 2:
            item.rewardType = '优惠券'
            break
          case 3:
            item.rewardType = '幸运大转盘'
            break
          default:
            item.rewardType = ''
            break
        }
        //   item.surplus =
        // item.vaildDate = `${item.startTime}至${item.endTime}`
        // item.receivePerson = `${item.receivePerson}/${item.receiveAmount}`
        // item.giveOutPerson = `${item.giveoutPerson}/${item.giveoutAmount}`
      })
      this.tableData = data
    },
    watch: {
      '$router': 'revice'
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
</style>
