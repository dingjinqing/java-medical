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
          >{{$t('promoteList.addAct')}}</el-button>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div class="table_list">
      <div class="select_info">
        <div class="leftarea">
          <span>{{$t('promoteList.actName')}}：&nbsp;</span>
          <el-input
            :placeholder="$t('promoteList.actNamePlaceholder')"
            size="small"
            v-model="actName"
          ></el-input>
        </div>
        <div class="midarea">
          <span class="demonstration">{{$t('promoteList.actDate')}}：&nbsp;</span>
          <div>
            <el-date-picker
              size="small"
              v-model="startTime"
              type="datetime"
              style="width:200px "
              value-format="yyyy-MM-dd HH:mm:ss"
            >
            </el-date-picker>
          </div>
          <span>{{$t('promoteList.to')}}</span>
          <div>
            <el-date-picker
              size="small"
              v-model="endTime"
              type="datetime"
              style="width: 200px"
              value-format="yyyy-MM-dd HH:mm:ss"
            >
            </el-date-picker>
          </div>
        </div>
        <div class="rightarea">
          <span>{{$t('promoteList.rewardType')}}：&nbsp;</span>
          <el-select
            v-model="rewardType"
            size="small"
          >
            <el-option
              :label="$t('promoteList.all')"
              value="-1"
            ></el-option>
            <el-option
              :label="$t('promoteList.giftGoods')"
              value="0"
            ></el-option>
            <el-option
              :label="$t('promoteList.discountGoods')"
              value="1"
            ></el-option>
            <el-option
              :label="$t('promoteList.giftCoupons')"
              value="2"
            ></el-option>
          </el-select>
          <el-button
            type="primary"
            size="small"
            @click="onSubmit"
          >{{$t('promoteList.filter')}}</el-button>
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
          :label="$t('promoteList.actName')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          :label="$t('promoteList.actValidityPeriod')"
          align="center"
        >
          <template slot-scope="scope">
            <span v-html="scope.row.validDate"></span>
          </template>
        </el-table-column>

        <el-table-column
          prop="rewardType"
          :label="$t('promoteList.rewardType')"
          align="center"
          :formatter="rewardTypeName"
        >
        </el-table-column>

        <el-table-column
          prop="marketStore"
          :label="$t('promoteList.rewardStore')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="recNum"
          :label="$t('promoteList.recNumber')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="actState"
          :label="$t('promoteList.activityStatus')"
          align="center"
          :formatter="actStateName"
        >
        </el-table-column>

        <el-table-column
          prop=""
          :label="$t('promoteList.operate')"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <el-tooltip
                class="item"
                effect="dark"
                :content="$t('promoteList.edit')"
                placement="top"
              >
                <i
                  class="el-icon-edit-outline"
                  @click="updateActive(scope.row.id)"
                ></i>
              </el-tooltip>
              <el-tooltip
                class="item"
                effect="dark"
                :content="$t('promoteList.share')"
                placement="top"
              >
                <i class="el-icon-share"></i>
              </el-tooltip>
              <el-tooltip
                class="item"
                effect="dark"
                :content="$t('promoteList.receiveDetails')"
                placement="top"
              >
                <i
                  class="el-icon-present"
                  @click="receiveDetails(scope.row.id)"
                ></i>
              </el-tooltip>
              <el-tooltip
                class="item"
                effect="dark"
                :content="$t('promoteList.launchDetails')"
                placement="top"
              >
                <i
                  class="el-icon-news"
                  @click="launchDetails(scope.row.id)"
                ></i>
              </el-tooltip>
              <el-tooltip
                class="item"
                effect="dark"
                :content="$t('promoteList.participateDetails')"
                placement="top"
              >
                <i
                  class="el-icon-set-up"
                  @click="participateDetails(scope.row.id)"
                ></i>
              </el-tooltip>

              <el-tooltip
                class="item"
                effect="dark"
                :content="$t('promoteList.enabled')"
                placement="top"
                v-if="scope.row.isBlock === 1"
              >
                <i
                  class="el-icon-circle-check"
                  @click="startOrBlock(scope.row.id)"
                ></i>
              </el-tooltip>
              <el-tooltip
                class="item"
                effect="dark"
                :content="$t('promoteList.disable')"
                placement="top"
                v-else
              >
                <i
                  class="el-icon-circle-close"
                  @click="startOrBlock(scope.row.id)"
                ></i>
              </el-tooltip>
              <el-tooltip
                class="item"
                effect="dark"
                :content="$t('promoteList.delete')"
                placement="top"
              >
                <i
                  class="el-icon-delete"
                  @click="delAct(scope.row.id)"
                ></i>
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
        title: this.$t('promoteList.allActivity'),
        name: '0'
      }, {
        title: this.$t('promoteList.doingActivity'),
        name: '1'
      }, {
        title: this.$t('promoteList.notBeginActivity'),
        name: '2'
      }, {
        title: this.$t('promoteList.expiredActivity'),
        name: '3'
      }, {
        title: this.$t('promoteList.disabledActivity'),
        name: '4'
      }, {
        title: '添加好友助力活动',
        name: '5'
      }],
      currentPage: 1,
      tableData: [],
      rewardType: '-1',
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
      console.log('this.tabSwitch：', this.tabSwitch)
      this.pageParams.rewardType = this.rewardType
      this.pageParams.startTime = this.startTime
      this.pageParams.endTime = this.endTime
      this.pageParams.actName = this.actName
      this.pageParams.actState = this.tabSwitch
      friendHelpList(this.pageParams).then(res => {
        console.log('表格数据：', res)

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
        item.validDate = `${item.startTime}<br/>${this.$t('promoteList.to')}<br/>${item.endTime}`
        // var jsonObject = JSON.parse(item.rewardContent)
        console.log('item是啥？', item)
        item.marketStore = item.fpRewardContent.market_store
      })
      this.tableData = data
    },
    // 删除优惠券
    delAct (id) {
      let delParam = {
        'id': id
      }
      deleteActive(delParam).then(res => {
        if (res.error === 0) {
          alert('删除成功！')
          this.handleClick()
        }
      })
    },
    // 停用启用优惠券
    startOrBlock (id) {
      let switchParam = {
        'id': id
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
        case 0: row.rewardType = this.$t('promoteList.giftGoods')
          break
        case 1: row.rewardType = this.$t('promoteList.discountGoods')
          break
        case 2: row.rewardType = this.$t('promoteList.giftCoupons')
          break
      }
      return row.rewardType
    },
    // 活动状态转化为文字
    actStateName (row, column) {
      switch (row.actState) {
        case 1: row.actState = this.$t('promoteList.doingActivity')
          break
        case 2: row.actState = this.$t('promoteList.notBeginActivity')
          break
        case 3: row.actState = this.$t('promoteList.expiredActivity')
          break
        case 4: row.actState = this.$t('promoteList.disabledActivity')
          break
      }
      return row.actState
    },
    // 添加好友助力活动
    addActive () {
      this.$router.push({
        path: `/admin/home/main/addHelpAct/${null}`
      })
    },
    // 编辑好友助力活动
    updateActive (id) {
      this.$router.push({
        path: `/admin/home/main/addHelpAct/${id}`
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
    margin: 10px 0px 20px;
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
  display: flex;
  justify-content: space-around;
  > .item {
    font-size: 22px;
    color: #66b1ff;
    cursor: pointer;
  }
}
</style>
