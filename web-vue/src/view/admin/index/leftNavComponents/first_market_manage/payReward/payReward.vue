<!--
***  支付有礼列表页面
-->
<template>
  <div class="payReward">
    <div class="mainContent">
      <!-- tab切换导航 -->
      <statusTab
        v-model="param.navType"
        :activityName="activityName"
        :standard="true"
        @click="searchList()"
      />

      <!-- 添加支付有礼活动 -->
      <div class="wrapper">
        <el-button
          type="primary"
          size="small"
          @click="addActivity"
        >{{$t('payReward.addActivity')}}</el-button>
        <div class="right">{{$t('payReward.activityTips')}}</div>
      </div>
    </div>

    <!-- tab表格内容部分 -->
    <div class="table_list">
      <el-table
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="activityNames"
          :label="$t('payReward.actName')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="name"
          :label="$t('payReward.triggerCondition')"
          align="center"
        >
          <template slot-scope="scope">
            {{scope.row.goodsAreaType === 1? '部分商品':'全部商品'}}
            <br>
            {{$t('payReward.full')}}{{scope.row.minPayMoney===null?'0.00':scope.row.minPayMoney+".00"}}{{$t('payReward.yuan')}}
          </template>
        </el-table-column>

        <el-table-column
          prop="vaildDate"
          :label="$t('payReward.validDate')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="awardContentList"
          :label="$t('payReward.activityReward')"
          align="center"
        >
          <template slot-scope="scope">
            <div
              v-for="(item, index) in scope.row.awardContentList"
              :key="index"
            >
              {{item.giftType | formatGiftType(item.giftType)}}
            </div>
          </template>
        </el-table-column>

        <el-table-column
          prop="actFirst"
          :label="$t('payReward.priority')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="statusText"
          :label="$t('payReward.activityStatus')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          :label="$t('payReward.operate')"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <el-tooltip
                :content="$t('payReward.edit')"
                placement="top"
                v-if="scope.row.currentState === 1 || scope.row.currentState === 2"
              >
                <span
                  class="el-icon-edit-outline"
                  @click="handleEdit(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('payReward.enableUse')"
                placement="top"
                v-if=" scope.row.currentState === 4"
              >
                <span
                  class="el-icon-circle-check"
                  @click="openSwitch(scope.row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('payReward.disableUse')"
                placement="top"
                v-if="scope.row.currentState === 1 || scope.row.currentState === 2"
              >
                <span
                  class="el-icon-circle-close"
                  @click="closeSwitch(scope.row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('payReward.activityDetail')"
                placement="top"
                v-if="scope.row.currentState !== 2"
              >
                <span
                  class="el-icon-tickets"
                  @click="actDetails(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('payReward.delete')"
                placement="top"
                v-if="scope.row.currentState === 3 || scope.row.currentState === 4"
              >
                <span
                  class="el-icon-delete"
                  @click="delPayRewardAct(scope.row.id)"
                ></span>
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="footer">
        <pagination
          :page-params.sync="param"
          @pagination="searchList"
        />
      </div>
    </div>

  </div>
</template>

<script>
import { payRewardList, delPayRewardAct, actSwitch } from '@/api/admin/marketManage/payReward.js'
import statusTab from '@/components/admin/marketManage/status/statusTab'
import pagination from '@/components/admin/pagination/pagination.vue'

export default {
  components: { statusTab, pagination },
  mounted () {
    this.langDefault()
  },
  watch: {
    'param.navType' (n, o) {
      this.searchList()
    },
    lang () {
      this.activityName = this.$t('payReward.payReward')
    }
  },
  created () {
    this.searchList()
  },
  data () {
    return {
      activityName: this.$t('payReward.payReward'),
      tableData: [],
      param: {
        'navType': 0,
        'currentPage': 1,
        'pageRows': 20
      },
      startTime: '',
      endTime: ''
    }
  },

  methods: {
    // 跳转到添加支付有礼活动
    addActivity () {
      this.$router.push({
        name: 'addPayRewardAct'
      })
    },

    // 点击编辑按钮跳转到编辑支付有礼活动
    handleEdit (id) {
      this.$router.push({
        name: 'addPayRewardAct',
        query: {
          id: id
        }
      })
    },

    // 点击活动明细跳转到活动明细页面
    actDetails (id) {
      this.$router.push({
        name: 'activityDetails',
        query: {
          id: id
        }
      })
    },

    // tab对应分类查询数据
    searchList () {
      payRewardList(this.param).then((res) => {
        const { error } = res
        if (error === 0) {
          this.handleData(res.content)
        }
      }).catch(err => console.log(err))
    },
    // 处理列表数据
    handleData (data) {
      this.param = Object.assign(data.page, this.param)
      this.tableData = data.dataList
      data.dataList.map((item, index) => {
        if (item.timeType === 1) {
          item.vaildDate = this.$t('marketCommon.permanent')
        } else {
          item.vaildDate = `${item.startTime} ` + this.$t('marketCommon.to') + ` ${item.endTime}`
        }
      })
      this.tableData.map((item, index) => {
        item.statusText = this.getActStatusString(item.currentState)
      })
    },

    // 删除支付有礼活动
    delPayRewardAct (id) {
      this.$confirm(this.$t('payReward.confirmDelete'), {
        confirmButtonText: this.$t('payReward.confirm'),
        cancelButtonText: this.$t('payReward.cancel'),
        type: 'warning'
      }).then(() => {
        delPayRewardAct({ id: id }).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('payReward.deleteSucess') })
            this.searchList()
          }
        })
      }).catch(() => {
        this.$message.info({ message: this.$t('payReward.cancelDelete') })
      })
    },

    // 停用
    closeSwitch (row) {
      this.$confirm(this.$t('payReward.confirmStop'), {
        confirmButtonText: this.$t('payReward.confirm'),
        cancelButtonText: this.$t('payReward.cancel'),
        type: 'warning'
      }).then(() => {
        actSwitch({
          id: row.id
        }).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('payReward.stopSuccess') })
            this.searchList()
          }
        })
      }).catch(() => {
        this.$message.success({ message: this.$t('payReward.cancelStop') })
      })
    },

    // 启用
    openSwitch (row) {
      this.$confirm(this.$t('payReward.confirmEnable'), {
        confirmButtonText: this.$t('payReward.confirm'),
        cancelButtonText: this.$t('payReward.cancel'),
        type: 'warning'
      }).then(() => {
        actSwitch({
          id: row.id
        }).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('payReward.enableSuccess') })
            this.searchList()
          }
        })
      }).catch(() => {
        this.$message.success({ message: this.$t('payReward.cancelEnable') })
      })
    }
  },

  // 活动奖励数字对应的类型
  filters: {
    formatGiftType (giftType) {
      console.log(giftType)
      if (giftType === 7) {
        return '自定义'
      } else if (giftType === 6) {
        return '积分'
      } else if (giftType === 5) {
        return '商品'
      } else if (giftType === 4) {
        return '余额'
      } else if (giftType === 3) {
        return '幸运大抽奖'
      } else if (giftType === 2) {
        return '分裂优惠券'
      } else if (giftType === 1) {
        return '普通优惠券'
      } else {
        return '无奖品'
      }
    }
  }

}

</script>
<style lang="scss" scoped>
.payReward {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .mainContent {
    position: relative;
    background-color: #fff;
    padding: 15px;
    .wrapper {
      display: flex;
      justify-content: space-between;
      .right {
        color: #999;
        margin-top: 10px;
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
    .opt {
      text-align: center;
      color: #5a8bff;
      span {
        cursor: pointer;
        font-size: 22px;
      }
    }
  }
}
</style>
