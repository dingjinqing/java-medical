<template>
  <div class="content">
    <div class="main">
      <el-tabs
        v-model="tabSwitch"
        @tab-click="handleClick"
      >
        <el-tab-pane
          v-for="(item) in tabInfo"
          :key="item.name"
          :label="item.title"
          :name="item.name"
        >
          <el-button
            type="primary"
            class="barginBtn"
            @click="addActive"
          >添加好友助力活动</el-button>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div class="table_list">
      <div class="select_info">
        <div class="leftarea">
          <span>活动名称：</span>
          <el-input
            size="small"
            v-model="actName"
          ></el-input>
        </div>
        <div class="midarea">
          <span class="demonstration">活动日期：</span>
          <el-date-picker
            size="small"
            v-model="startTime"
            type="datetime"
            placeholder="开始时间："
            style="weight :100px "
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
          <span>至 </span>
          <el-date-picker
            size="small"
            v-model="endTime"
            type="datetime"
            placeholder="结束时间："
            style="width: 200px"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </div>
        <div class="rightarea">
          <span>奖励类型：</span>
          <el-select
            v-model="options.value"
            size="small"
            placeholder="请选择"
          >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
          <el-button
            type="primary"
            size="small"
            @click="onSubmit"
          >筛选</el-button>
        </div>
      </div>
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="actName"
          label="活动名称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="validDate"
          label="活动有效期"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="rewardType"
          label="奖励类型"
          align="center"
          :formatter="rewardTypeName"
        >
        </el-table-column>

        <el-table-column
          prop="marketStore"
          label="奖励库存"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="recNum"
          label="已领取奖励数量"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="actState"
          label="活动状态"
          align="center"
          :formatter="actStateName"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <span class="el-icon-edit-outline"></span>
              <span class="el-icon-share"></span>
              <span @click="receiveDetails(scope.row.id)">领取明细</span>
              <span @click="launchDetails(scope.row.id)">发起明细</span>
              <span @click="participateDetails(scope.row.id)">参与明细</span>
              <span
                class="el-icon-circle-check"
                @click="startOrBlock(scope)"
              ></span>
              <span
                class="el-icon-circle-close"
                @click="startOrBlock(scope)"
              ></span>
              <span
                class="el-icon-delete"
                @click="delAct(scope)"
              ></span>
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
import { friendHelpList, deleteActive, switchAct } from '@/api/admin/marketManage/friendHelp.js'
import pagination from '@/components/admin/pagination/pagination'
import addHelpAct from './addHelpAct'
export default {
  components: {
    pagination,
    addHelpAct
  },
  data () {
    return {
      startTime: '',
      endTime: '',
      actName: '',
      tabSwitch: '1',
      tabInfo: [{
        title: '全部助力活动',
        name: '0'
      }, {
        title: '进行中',
        name: '1'
      }, {
        title: '未开始',
        name: '2'
      }, {
        title: '已过期',
        name: '3'
      }, {
        title: '已停用',
        name: '4'
      }],
      tabIndex: 2,
      currentPage: 1,
      tableData: [],
      options: [
        {
          value: '-1',
          label: '全部'
        }, {
          value: '0',
          label: '赠送商品'
        }, {
          value: '1',
          label: '折扣商品'
        }, {
          value: '2',
          label: '赠送优惠券'
        }],
      pageParams: {

      }
    }
  },
  created () {
    this.handleClick()
  },

  methods: {
    // 当前页发生变化
    handleCurrentChange () {
      console.log(this.currentPage)
    },

    handleClick () {
      this.pageParams.rewardType = this.options.value
      this.pageParams.startTime = this.startTime
      this.pageParams.endTime = this.endTime
      this.pageParams.actName = this.actName
      this.pageParams.actState = this.tabSwitch
      friendHelpList(this.pageParams).then(res => {
        console.log(res)

        if (res.error === 0) {
          this.handleData(res.content.dataList)
          this.pageParams = res.content.page
        }
      }).catch(() => {
        this.$message.error('操作失败')
      })
    },
    onSubmit () {
      this.pageParams.currentPage = 1
      this.handleClick()
    },
    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        item.validDate = `${item.startTime}至${item.endTime}`
        var jsonObject = JSON.parse(item.rewardContent)
        item.marketStore = jsonObject[0].market_store
      })
      this.tableData = data
    },
    // 删除优惠券
    delAct (scope) {
      let delParam = {
        'id': scope.row.id
      }
      deleteActive(delParam).then(res => {
        if (res.error === 0) {
          alert('删除成功！')
          this.handleClick()
        }
      })
    },
    // 停用启用优惠券
    startOrBlock (scope) {
      let switchParam = {
        'id': scope.row.id
      }
      switchAct(switchParam).then(res => {
        if (res.error === 0) {
          alert('修改成功！')
          this.handleClick()
        }
      })
    },

    // 奖励类型转化为文字
    rewardTypeName (row, column) {
      switch (row.rewardType) {
        case 0: row.rewardType = '赠送商品'
          break
        case 1: row.rewardType = '折扣商品'
          break
        case 2: row.rewardType = '赠送优惠券'
          break
      }
      return row.rewardType
    },
    // 活动状态转化为文字
    actStateName (row, column) {
      switch (row.actState) {
        case 1: row.actState = '进行中'
          break
        case 2: row.actState = '未开始'
          break
        case 3: row.actState = '已过期'
          break
        case 4: row.actState = '已停用'
          break
      }
      return row.actState
    },
    // 添加好友助力活动
    addActive () {
      this.$router.push({
        name: 'promote_activity'
      })
    },
    // 领取明细
    receiveDetails (id) {
      this.$router.push(`/admin/home/main/friendHelp/receiveDetails/${id}`)
    },
    // 发起明细
    launchDetails (id) {
      this.$router.push(`/admin/home/main/friendHelp/launchDetails/${id}`)
    },
    // 参与明细
    participateDetails (id) {
      this.$router.push({
        path: `/admin/home/main/friendHelp/participateDetails/${id}/${null}`
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
  .select_info {
    display: flex;
    margin: 10px 0px;
    .leftarea {
      display: flex;
      margin-right: 30px;
    }
    .rightarea {
      display: flex;
      :nth-of-type(1) {
        margin-right: 5px;
      }
    }
    .midarea {
      display: flex;
      margin-right: 30px;
      :nth-of-type(2) {
        margin: 0 0 0 10px;
      }
    }
    span {
      white-space: nowrap;
      height: 32px;
      line-height: 32px;
    }
    /deep/ .el-input__inner {
      width: 200px;
      display: inline-block;
    }
  }
  .footer_right {
    padding: 20px 0 20px 20px;
    display: flex;
    justify-content: flex-end;
    span {
      display: block;
      height: 32px;
      line-height: 32px;
    }
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
.opt {
  text-align: left;
  color: #5a8bff;
  span {
    cursor: pointer;
  }
}
</style>
