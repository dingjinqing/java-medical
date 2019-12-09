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
      >添加评价有礼活动</el-button>
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
            label="活动名称"
            prop="name"
            align="center"
          ></el-table-column>
          <el-table-column
            label="触发条件"
            prop="commentType"
            align="center"
            :formatter="commentTypeFmt"
          ></el-table-column>
          <el-table-column
            label="活动有效期"
            align="center"
          >
            <template slot-scope="{row}">
              <div v-if="row.isForever == 0">
                {{row.startTime}} 至 {{row.endTime}}
              </div>
              <div v-else>永久有效</div>
            </template>
          </el-table-column>
          <el-table-column
            label="活动奖励"
            prop="awardType"
            align="center"
            :formatter="awardTypeFmt"
          ></el-table-column>
          <el-table-column
            label="优先级"
            prop="level"
            align="center"
          ></el-table-column>
          <el-table-column
            label="活动状态"
            prop="status"
            align="center"
            :formatter="statusFmt"
          ></el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="136"
          >
            <template slot-scope="{row, index}">
              <div class="operate-wrap">
                <el-tooltip
                  content="停用"
                  v-show="row.status == 1|| row.status == 2"
                >
                  <i
                    class="el-icon-circle-close iconSpan"
                    @click="edit('stop', row)"
                  ></i>
                </el-tooltip>
                <el-tooltip
                  content="启用"
                  v-show="row.status == 4"
                >
                  <i
                    class="el-icon-circle-check iconSpan"
                    @click="edit('action', row)"
                  ></i>
                </el-tooltip>
                <el-tooltip
                  content="编辑"
                  v-show="row.status == 1 || row.status == 2"
                >
                  <i
                    class="el-icon-edit-outline iconSpan"
                    @click="edit('edit', row)"
                  ></i>
                </el-tooltip>
                <el-tooltip
                  content="删除"
                  v-show="row.status == 3||row.status == 4"
                >
                  <i
                    class="el-icon-delete iconSpan"
                    @click="edit('delete', row, index)"
                  ></i>
                </el-tooltip>
                <el-tooltip
                  content="活动明细"
                  v-show="row.stauts != 2"
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
      <addEvaluationGift v-if="activeTab === '5'"></addEvaluationGift>
    </div>
  </div>
</template>

<script>
import { getEvaluationGiftList } from '@/api/admin/marketManage/evaluationGift.js'
export default {
  components: {
    addEvaluationGift: () => import('./evaluationGiftAdd'),
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      tabInfo: [{
        title: '全部评价有礼活动',
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
          this.closeAddTab()
          this.initDataList()
        }
      },
      immediate: true
    }
  },
  mounted () {
    this.langDefault()
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
        return '评价即送'
      } else if (commentType === 2) {
        return '自定义'
      } else {
        return '未处理'
      }
    },
    awardTypeFmt (row, column) {
      let awardType = row.awardType
      switch (awardType) {
        case 1:
          return '积分'
        case 2:
          return '优惠券'
        case 3:
          return '余额'
        case 4:
          return '幸运大抽奖'
        case 5:
          return '自定义'
      }
    },
    statusFmt (row, column) {
      let status = row.status
      let text = ''
      switch (status) {
        case 1:
          text = '进行中'
          break
        case 2:
          text = '未开始'
          break
        case 3:
          text = '已过期'
          break
        case 4:
          text = '已停用'
          break
      }
      return text
    },
    addTabHandle () {
      if (this.tabInfo.length === 5) {
        this.tabInfo.push({
          title: '添加评价有礼活动',
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
      switch (operate) {
        case 'stop':
          break
        case 'action':
          break
        case 'edit':
          break
        case 'delete':
          break
        case 'detail':
          that.$router.push({
            name: 'comment',
            query: {
              award_activity_id: row.id
            }
          })
          break
      }
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
