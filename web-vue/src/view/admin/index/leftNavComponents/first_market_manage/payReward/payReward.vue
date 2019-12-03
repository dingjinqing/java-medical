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
        >添加支付有礼活动</el-button>
        <div class="right">注：同一时间段仅会开展一个优先级最高的支付有礼活动</div>
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
          label="活动名称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="name"
          label="触发条件"
          align="center"
        >
          <template slot-scope="scope">
            {{scope.row.goodsAreaType === 1? '部分商品':'全部商品'}}
            <br>
            满{{scope.row.minPayMoney===null?'0.00':scope.row.minPayMoney+".00"}}元
          </template>
        </el-table-column>

        <el-table-column
          prop="vaildDate"
          label="活动有效期"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="awardContentList"
          label="活动奖励"
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
          label="优先级"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="statusText"
          label="活动状态"
          align="center"
        >
        </el-table-column>

        <el-table-column
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <el-tooltip
                content="编辑"
                placement="top"
                v-if="scope.row.currentState === 1 || scope.row.currentState === 2"
              >
                <span
                  class="el-icon-edit-outline"
                  @click="handleEdit(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="启用"
                placement="top"
                v-if=" scope.row.currentState === 4"
              >
                <span
                  class="el-icon-circle-check"
                  @click="openSwitch(scope.row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="停用"
                placement="top"
                v-if="scope.row.currentState === 1 || scope.row.currentState === 2"
              >
                <span
                  class="el-icon-circle-close"
                  @click="closeSwitch(scope.row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="活动明细"
                placement="top"
                v-if="scope.row.currentState !== 2"
              >
                <span
                  class="el-icon-tickets"
                  @click="actDetails(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="删除"
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
    }
  },
  created () {
    this.searchList()
  },
  data () {
    return {
      activityName: '支付有礼',
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
      this.$confirm('确认要删除吗？', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delPayRewardAct({ id: id }).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: '删除成功' })
            this.searchList()
          }
        })
      }).catch(() => {
        this.$message.info({ message: '已取消删除' })
      })
    },

    // 停用
    closeSwitch (row) {
      this.$confirm('确认要停用吗？', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        actSwitch({
          id: row.id
        }).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: '停用成功' })
            this.searchList()
          }
        })
      }).catch(() => {
      })
    },

    // 启用
    openSwitch (row) {
      this.$confirm('确认要启用吗？', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        actSwitch({
          id: row.id
        }).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: '启用成功' })
            this.searchList()
          }
        })
      }).catch(() => {
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
