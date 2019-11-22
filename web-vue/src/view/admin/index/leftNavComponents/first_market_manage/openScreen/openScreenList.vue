<template>
  <div class="container">
    <div class="filter-list">
      <el-tabs
        v-model="activeName"
        @tab-click="tabClickHandle"
      >
        <el-tab-pane
          v-for="item in tabPanes"
          :key="item.id"
          :label="item.label"
          :name="item.id"
        ></el-tab-pane>
      </el-tabs>
      <div class="clearfix">
        <el-button
          type="primary"
          size="small"
          @click="addOpenScreen"
        >添加开屏有礼活动</el-button>
        <p class="tips">注：同一时间仅会开展一个优先级最高的开屏有礼活动</p>
      </div>
    </div>
    <div class="table-list">
      <el-table
        :data="tableData"
        border
        :header-cell-style="{
          'background-color':'#f5f5f5',
          'border':'none'
        }"
      >
        <el-table-column
          label="活动名称"
          prop="name"
        ></el-table-column>
        <el-table-column
          label="触发条件"
          prop="action"
        >
          <template slot-scope="{row}">
            <div>
              {{row.action|filterAction}}
            </div>
          </template>
        </el-table-column>
        <el-table-column label="活动时间">
          <template slot-scope="{row}">
            <div>
              <span>{{row.startDate}}</span>
              <span>至</span>
              <span>{{row.endDate}}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="活动类型"
          prop="activityAction"
        ></el-table-column>
        <el-table-column label="优先级"></el-table-column>
        <el-table-column
          label="活动状态"
          prop="status"
        >
          <template slot-scope="{row}">
            <div>{{row.status|filterStatus}}</div>
          </template>
        </el-table-column>
        <el-table-column
          prop="operate"
          label="操作"
          width="180"
        >
          <template slot-scope="{row}">
            <div>
              <el-tooltip content="编辑">
                <span
                  class="el-icon-edit iconSpan"
                  @click="edit('edit', row)"
                ></span>
              </el-tooltip>
              <el-tooltip content="删除">
                <span
                  class="el-icon-delete iconSpan"
                  @click="edit('delete', row)"
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
  </div>
</template>

<script>
import { getOpenScreenList } from '@/api/admin/marketManage/openScreen.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      tabPanes: [
        { id: 'first', label: '全部开屏有礼活动' },
        { id: 'second', label: '进行中' },
        { id: 'third', label: '未开始' },
        { id: 'fourth', label: '已过期' },
        { id: 'fifth', label: '已停用' }
      ],
      activeName: 'second',
      tableData: [],
      queryParams: {
        status: 2
      },
      pageParams: {}
    }
  },
  filters: {
    filterAction (val) {
      let text = ''
      switch (val) {
        case 1:
          text = '新用户'
          break
        case 2:
          text = '全部用户'
          break
        case 3:
          text = '未在店内支付过的用户'
          break
      }
      return text
    },
    filterStatus (status) {
      let text = ''
      switch (status) {
        case 0:
          text = '进行中'
          break
        case 1:
          text = '未开始'
          break
        case 2:
          text = '已过期'
          break
        case 3:
          text = '已停用'
          break
      }
      return text
    }
  },
  mounted () {
    this.langDefault()
    this.initDataList()
  },
  methods: {
    tabClickHandle (tab) {
      let status = ''
      switch (tab.name) {
        case 'first':
          status = ''
          break
        case 'second':
          status = 0
          break
        case 'third':
          status = 1
          break
        case 'fourth':
          status = 2
          break
        case 'fifth':
          status = 3
          break
      }
      this.$set(this.queryParams, 'status', status)
      this.initDataList()
    },
    initDataList () {
      let params = Object.assign({}, this.queryParams, this.pageParams)
      getOpenScreenList(params).then(res => {
        if (res.error === 0) {
          console.log(res)
          this.tableData = [...res.content.dataList]
          this.pageParams = Object.assign({}, res.content.page)
        }
      })
    },
    edit (opera, row) {
      console.log(row)
      switch (opera) {
        case 'edit':
          break
        case 'delete':
          break
      }
    },
    addOpenScreen () {
      this.$router.push({
        name: 'open_screen_add'
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  padding: 10px;
  width: 100%;
  height: 100%;
  font-size: 14px;
  color: #333;
  .filter-list {
    padding: 15px;
    background-color: #fff;
    .clearfix {
      &::after {
        content: "";
        clear: both;
        zoom: 1;
      }
      .tips {
        float: right;
        line-height: 32px;
      }
    }
  }
  .table-list {
    padding: 15px;
    margin-top: 10px;
    background-color: #fff;
  }
}
</style>
