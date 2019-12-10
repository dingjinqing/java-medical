<template>
  <!-- 评价有礼 -->
  <div class="container">
    <div class="top">
      <el-tabs
        v-model="activeTab"
        :lazy="true"
      >
        <el-tab-pane
          v-for="(item, index) in tabInfo"
          :key="index"
          :label="item.title"
          :name="item.name"
        ></el-tab-pane>
      </el-tabs>
      <el-button
        type="primary"
        size="small"
        @click="addTabHandle"
        v-show="activeTab !== '5'"
      >{{$t('evaluationGiftList.addEvaluationAct')}}</el-button>
    </div>
    <div class="center">
      <div
        class="table_list"
        v-show="activeTab !== '5'"
      >
        <el-table
          :data="tableData"
          border
          header-row-class-name="tableClss"
        >
          <el-table-column
            :label="$t('evaluationGiftList.eventName')"
            prop="name"
            align="center"
          ></el-table-column>
          <el-table-column
            :label="$t('evaluationGiftList.triggerCondition')"
            prop="commentType"
            align="center"
            :formatter="commentTypeFmt"
          ></el-table-column>
          <el-table-column
            :label="$t('evaluationGiftList.activityPeriod')"
            align="center"
          >
            <template slot-scope="{row}">
              <div v-if="row.isForever == 0">
                {{row.startTime}} <p>{{$t('evaluationGiftList.to')}}</p> {{row.endTime}}
              </div>
              <div v-else>{{$t('evaluationGiftList.permanent')}}</div>
            </template>
          </el-table-column>
          <el-table-column
            :label="$t('evaluationGiftList.activityReward')"
            prop="awardType"
            align="center"
            :formatter="awardTypeFmt"
          ></el-table-column>
          <el-table-column
            :label="$t('evaluationGiftList.priority')"
            prop="level"
            align="center"
          ></el-table-column>
          <el-table-column
            :label="$t('evaluationGiftList.status')"
            prop="currentStatus"
            align="center"
            :formatter="currentStatusFmt"
          ></el-table-column>
          <el-table-column
            :label="$t('evaluationGiftList.operate')"
            align="center"
            width="136"
          >
            <template slot-scope="{row, index}">
              <div class="operate-wrap">
                <el-tooltip
                  :content="$t('evaluationGiftList.disabled')"
                  placement="top"
                  v-if="row.currentStatus == 1|| row.currentStatus == 2"
                >
                  <i
                    class="el-icon-circle-close iconSpan"
                    @click="edit('stop', row)"
                  ></i>
                </el-tooltip>
                <el-tooltip
                  :content="$t('evaluationGiftList.enabled')"
                  placement="top"
                  v-if="row.currentStatus == 4"
                >
                  <i
                    class="el-icon-circle-check iconSpan"
                    @click="edit('action', row)"
                  ></i>
                </el-tooltip>
                <el-tooltip
                  :content="$t('evaluationGiftList.edit')"
                  placement="top"
                  v-if="row.currentStatus == 1 || row.currentStatus == 2"
                >
                  <i
                    class="el-icon-edit-outline iconSpan"
                    @click="edit('edit', row)"
                  ></i>
                </el-tooltip>
                <el-tooltip
                  :content="$t('evaluationGiftList.delete')"
                  placement="top"
                  v-if="row.currentStatus == 3 || row.currentStatus == 4"
                >
                  <i
                    class="el-icon-delete iconSpan"
                    @click="edit('delete', row, index)"
                  ></i>
                </el-tooltip>
                <el-tooltip
                  :content="$t('evaluationGiftList.eventDetails')"
                  placement="top"
                  v-if="row.stauts != 2"
                >
                  <span
                    class="iconfont iconmingxi1 iconSpan"
                    @click="edit('detail', row)"
                  ></span>
                </el-tooltip>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        >
        </pagination>
      </div>
      <addEvaluationGift
        v-if="activeTab === '5'"
        @changeTabAct="changeTabActHandle"
      ></addEvaluationGift>
    </div>
  </div>
</template>

<script>
import { getEvaluationGiftList, toggleEvaluationGift, deleteEvaluationGift } from '@/api/admin/marketManage/evaluationGift.js'
export default {
  components: {
    addEvaluationGift: () => import('./evaluationGiftAdd'),
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      tabInfo: [{
        title: this.$t('evaluationGiftList.allEvaluation'),
        name: '0'
      }, {
        title: this.$t('evaluationGiftList.processing'),
        name: '1'
      }, {
        title: this.$t('evaluationGiftList.notStarted'),
        name: '2'
      }, {
        title: this.$t('evaluationGiftList.expired'),
        name: '3'
      }, {
        title: this.$t('evaluationGiftList.terminated'),
        name: '4'
      }],
      activeTab: '1',
      tableData: [],
      queryParams: {
        navType: 1
      },
      pageParams: {}
    }
  },
  watch: {
    activeTab: {
      handler: function (newVal) {
        if (newVal && newVal !== '5') {
          this.$set(this.queryParams, 'navType', Number(newVal))
          this.$router.replace({ query: {} })
          this.closeAddTab()
          this.initDataList()
        }
      },
      immediate: false
    },
    '$route.query.id': {
      handler: function (newVal) {
        if (newVal) {
          if (this.tabInfo.length === 5) {
            this.tabInfo.push({
              title: this.$t('evaluationGiftList.editEvaluation'),
              name: '5'
            })
          }
          this.activeTab = '5'
        }
      },
      immediate: true
    },
    lang (val) {
      if (this.tabInfo.length === 5) {
        this.tabInfo = [{
          title: this.$t('evaluationGiftList.allEvaluation'),
          name: '0'
        }, {
          title: this.$t('evaluationGiftList.processing'),
          name: '1'
        }, {
          title: this.$t('evaluationGiftList.notStarted'),
          name: '2'
        }, {
          title: this.$t('evaluationGiftList.expired'),
          name: '3'
        }, {
          title: this.$t('evaluationGiftList.terminated'),
          name: '4'
        }]
      } else {
        this.tabInfo = [{
          title: this.$t('evaluationGiftList.allEvaluation'),
          name: '0'
        }, {
          title: this.$t('evaluationGiftList.processing'),
          name: '1'
        }, {
          title: this.$t('evaluationGiftList.notStarted'),
          name: '2'
        }, {
          title: this.$t('evaluationGiftList.expired'),
          name: '3'
        }, {
          title: this.$t('evaluationGiftList.terminated'),
          name: '4'
        }, {
          title: this.$t('evaluationGiftList.addEvaluationAct'),
          name: '5'
        }]
      }
    }
  },
  mounted () {
    this.langDefault()
    this.initDataList()
  },
  methods: {
    initDataList () {
      let params = Object.assign({}, this.queryParams, this.pageParams)
      console.log(params)
      getEvaluationGiftList(params).then(res => {
        if (res.error === 0) {
          this.tableData = [...res.content.dataList]
          this.pageParams = Object.assign({}, res.content.page)
        }
      })
    },
    commentTypeFmt (row, column) {
      let commentType = row.commentType
      if (commentType === 1) {
        return this.$t('evaluationGiftList.evaluateSend')
      } else if (commentType === 2) {
        return this.$t('evaluationGiftList.custom')
      } else {
        return this.$t('evaluationGiftList.unprocess')
      }
    },
    awardTypeFmt (row, column) {
      let awardType = row.awardType
      switch (awardType) {
        case 1:
          return this.$t('evaluationGiftList.integral')
        case 2:
          return this.$t('evaluationGiftList.coupon')
        case 3:
          return this.$t('evaluationGiftList.balance')
        case 4:
          return this.$t('evaluationGiftList.luckyDraw')
        case 5:
          return this.$t('evaluationGiftList.custom')
      }
    },
    currentStatusFmt (row, column) {
      let currentStatus = row.currentStatus
      let text = ''
      switch (currentStatus) {
        case 1:
          text = this.$t('evaluationGiftList.processing')
          break
        case 2:
          text = this.$t('evaluationGiftList.notStarted')
          break
        case 3:
          text = this.$t('evaluationGiftList.expired')
          break
        case 4:
          text = this.$t('evaluationGiftList.terminated')
          break
      }
      return text
    },
    addTabHandle () {
      if (this.tabInfo.length === 5) {
        this.tabInfo.push({
          title: this.$t('evaluationGiftList.addEvaluationAct'),
          name: '5'
        })
        this.activeTab = '5'
      }
    },
    closeAddTab () {
      if (this.tabInfo.length > 5) {
        this.tabInfo.pop()
      }
    },
    edit (operate, row) {
      let that = this
      let id = row.id
      switch (operate) {
        case 'stop':
          that.$confirm(that.$t('evaluationGiftList.ayDisable'), that.$t('evaluationGiftList.remind')).then(() => {
            that.toggleEvaluationGiftcurrentStatus(id)
          })
          break
        case 'action':
          that.$confirm(that.$t('evaluationGiftList.ayEnable'), that.$t('evaluationGiftList.remind')).then(() => {
            that.toggleEvaluationGiftcurrentStatus(id)
          })
          break
        case 'edit':
          that.$router.push({
            query: {
              id: id
            }
          })
          break
        case 'delete':
          that.$confirm(that.$t('evaluationGiftList.ayDelete'), that.$t('evaluationGiftList.remind')).then(() => {
            that.deleteEvaluationGiftHandle(id)
          })
          break
        case 'detail':
          that.$router.push({
            name: 'comment',
            query: {
              award_activity_id: id
            }
          })
          break
      }
    },
    // 在新增编辑页更新tab
    changeTabActHandle (data) {
      this.activeTab = String(data)
    },
    toggleEvaluationGiftcurrentStatus (id) {
      let that = this
      let params = {
        id: id
      }
      toggleEvaluationGift(params).then(res => {
        if (res.error === 0) {
          that.$message.success(res.message)
          that.initDataList()
        } else {
          that.$message.error(res.message)
        }
      })
    },
    deleteEvaluationGiftHandle (id) {
      let that = this
      let params = {
        id: id
      }
      deleteEvaluationGift(params).then(res => {
        if (res.error === 0) {
          that.$message.success(res.message)
          that.initDataList()
        } else {
          that.$message.error(res.message)
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
.container {
  padding: 10px;
  font-size: 14px;
  height: 100%;
  position: relative;
  .top {
    background-color: #fff;
    padding: 15px;
  }
  .center {
    background-color: #fff;
    margin-top: 10px;
    padding: 15px;
    /deep/ .tableClss th {
      background-color: #f5f5f5;
      border: none;
      height: 36px;
      font-weight: bold;
      color: #000;
      padding: 8px 10px;
    }
    .iconSpan {
      font-size: 22px;
      color: #5a8bff;
      cursor: pointer;
    }
    .operate-wrap {
      width: 136px;
      overflow: hidden;
      .el-tooltip {
        float: left;
        margin-right: 8px;
        &:not(:first-child) {
          margin-left: 15px;
        }
      }
    }
  }
}
</style>
