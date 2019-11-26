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
            <div style="text-align:center;line-height:1;">
              <p>{{row.startDate}}</p>
              <p>至</p>
              <p>{{row.endDate}}</p>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="活动类型"
          prop="activityAction"
        >
          <template slot-scope="{row}">
            <div>
              {{row.activityAction|filterType(row)}}
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="优先级"
          prop="first"
        ></el-table-column>
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
            <div class="iconWrap">
              <el-tooltip
                v-show="row.status === 1 || row.status === 2"
                content="编辑"
                placement="top"
              >
                <span
                  class="el-icon-edit-outline iconSpan"
                  @click="edit('edit', row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="活动明细"
                placement="top"
              >
                <span
                  class="iconfont iconmingxi1 iconSpan"
                  @click="edit('detail', row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                v-show="row.status === 1 || row.status === 2"
                content="停用"
                placement="top"
              >
                <span
                  class="iconfont icontingyong iconSpan"
                  @click="edit('stop', row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                v-show="row.status === 4"
                content="启用"
                placement="top"
              >
                <span
                  class="iconfont iconqiyong iconSpan"
                  @click="edit('start', row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                v-show="row.status === 3 || row.status === 4"
                content="删除"
                placement="top"
              >
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
import { getOpenScreenList, disableOpenScreenApi, enableOpenScreenApi, deleteOpenScreenApi } from '@/api/admin/marketManage/openScreen.js'
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
        nvaType: 1
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
        default:
          text = ''
          break
      }
      return text
    },
    filterType (type, row) {
      let text = ''
      switch (type) {
        case 1:
          text = '活动送券'
          break
        case 2:
          text = '幸运大抽奖'
          break
        case 3:
          text = '自定义活动'
          break
        case 4:
          text = '积分'
          break
        case 5:
          text = '余额'
          break
        case 6:
          text = '分裂优惠券'
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
          status = 0
          break
        case 'second':
          status = 1
          break
        case 'third':
          status = 2
          break
        case 'fourth':
          status = 3
          break
        case 'fifth':
          status = 4
          break
      }
      this.$set(this.queryParams, 'nvaType', status)
      this.initDataList()
    },
    initDataList () {
      let params = Object.assign({}, this.queryParams, this.pageParams)
      getOpenScreenList(params).then(res => {
        if (res.error === 0) {
          console.log(res)
          this.tableData = res.content.dataList.map(item => {
            if (item.endDate > new Date()) {
              item.expired = true
            }
            item.statusText = this.getActStatusString(item.status, item.startTime, item.endTime)
            return item
          })
          this.pageParams = Object.assign({}, res.content.page)
        }
      })
    },
    edit (opera, row) {
      let id = row.id
      switch (opera) {
        case 'edit':
          this.$router.push({
            name: 'open_screen_add',
            query: {
              id: id
            }
          })
          break
        case 'detail':
          this.$router.push({
            name: 'open_screen_detail',
            query: {
              id: id
            }
          })
          break
        case 'stop':
          this.$confirm('确认要停用该活动吗?', '提示'
          ).then(() => {
            this.stopActivity(id)
          })
          break
        case 'start':
          this.$confirm('确认要启用该活动吗?', '提示'
          ).then(() => {
            this.enableActivity(id)
          })
          break
        case 'delete':
          this.$confirm('确认要删除该活动吗?', '提示'
          ).then(() => {
            this.deleteActivity(id)
          })
          break
      }
    },
    addOpenScreen () {
      this.$router.push({
        name: 'open_screen_add'
      })
    },
    stopActivity (id) {
      let params = {
        id: id
      }
      disableOpenScreenApi(params).then(res => {
        if (res.error === 0) {
          this.$message.success(res.message)
          this.initDataList()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    enableActivity (id) {
      let params = {
        id: id
      }
      enableOpenScreenApi(params).then(res => {
        if (res.error === 0) {
          this.$message.success(res.message)
          this.initDataList()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    deleteActivity (id) {
      let params = {
        id: id
      }
      deleteOpenScreenApi(params).then(res => {
        if (res.error === 0) {
          this.$message.success(res.message)
          this.initDataList()
        } else {
          this.$message.error(res.message)
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
  width: 100%;
  height: 100%;
  font-size: 14px;
  color: #333;
  .iconWrap {
    display: flex;
    justify-content: space-around;
  }
  .iconSpan {
    font-size: 22px;
    color: #5a8bff;
    cursor: pointer;
  }
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
