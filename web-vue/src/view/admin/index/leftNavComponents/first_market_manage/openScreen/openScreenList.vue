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
        >{{$t('openScreen.addEvent')}}</el-button>
        <p class="tips">{{$t('openScreen.note')}}</p>
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
          :label="$t('openScreen.eventName')"
          prop="name"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('openScreen.triggerCondition')"
          prop="action"
          align="center"
        >
          <template slot-scope="{row}">
            <div>
              {{row.action|filterAction}}
            </div>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('openScreen.activityTime')"
          align="center"
        >
          <template slot-scope="{row}">
            <div style="text-align:center;line-height:1;">
              <p>{{row.startDate}}</p>
              <p>{{$t('openScreen.to')}}</p>
              <p>{{row.endDate}}</p>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('openScreen.activityType')"
          prop="activityAction"
          align="center"
        >
          <template slot-scope="{row}">
            <div>
              {{row.activityAction|filterType(row)}}
            </div>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('openScreen.priority')"
          prop="first"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('openScreen.activeStatus')"
          prop="status"
          align="center"
        >
          <template slot-scope="{row}">
            <div>{{row.status|filterStatus}}</div>
          </template>
        </el-table-column>
        <el-table-column
          prop="operate"
          :label="$t('openScreen.operate')"
          width="180"
          align="center"
        >
          <template slot-scope="{row}">
            <div class="iconWrap">
              <el-tooltip
                v-show="row.currentState === 1 || row.currentState === 2"
                :content="$t('openScreen.edit')"
                placement="top"
              >
                <span
                  class="el-icon-edit-outline iconSpan"
                  @click="edit('edit', row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('openScreen.eventDetails')"
                placement="top"
              >
                <span
                  class="iconfont iconmingxi1 iconSpan"
                  @click="edit('detail', row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                v-show="row.currentState === 1 || row.currentState === 2"
                :content="$t('openScreen.disabled')"
                placement="top"
              >
                <span
                  class="iconfont icontingyong iconSpan"
                  @click="edit('stop', row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                v-show="row.currentState === 4"
                :content="$t('openScreen.enabled')"
                placement="top"
              >
                <span
                  class="iconfont iconqiyong iconSpan"
                  @click="edit('start', row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                v-show="row.currentState === 3 || row.currentState === 4"
                :content="$t('openScreen.delete')"
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
import vm from '@/main'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      tabPanes: [
        { id: 'first', label: this.$t('openScreen.allEvents') },
        { id: 'second', label: this.$t('openScreen.processing') },
        { id: 'third', label: this.$t('openScreen.notStart') },
        { id: 'fourth', label: this.$t('openScreen.expired') },
        { id: 'fifth', label: this.$t('openScreen.terminated') }
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
          text = vm.$t('openScreen.newUser')
          break
        case 2:
          text = vm.$t('openScreen.allUser')
          break
        case 3:
          text = vm.$t('openScreen.notPaidUser')
          break
      }
      return text
    },
    filterStatus (status) {
      let text = ''
      switch (status) {
        case 1:
          text = vm.$t('openScreen.processing')
          break
        case 2:
          text = vm.$t('openScreen.notStart')
          break
        case 3:
          text = vm.$t('openScreen.expired')
          break
        case 4:
          text = vm.$t('openScreen.terminated')
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
          text = vm.$t('openScreen.eventCoupon')
          break
        case 2:
          text = vm.$t('openScreen.luckyDraw')
          break
        case 3:
          text = vm.$t('openScreen.customEvent')
          break
        case 4:
          text = vm.$t('openScreen.integral')
          break
        case 5:
          text = vm.$t('openScreen.balance')
          break
        case 6:
          text = vm.$t('openScreen.splitCoupon')
          break
      }
      return text
    }
  },
  mounted () {
    this.initDataList()
    this.langDefault()
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
            item.statusText = this.getActStatusString(item.currentState)
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
          this.$confirm(this.$t('openScreen.ayDisable'), this.$t('openScreen.prompt')
          ).then(() => {
            this.stopActivity(id)
          })
          break
        case 'start':
          this.$confirm(this.$t('openScreen.ayEnable'), this.$t('openScreen.prompt')
          ).then(() => {
            this.enableActivity(id)
          })
          break
        case 'delete':
          this.$confirm(this.$t('openScreen.ayDelete'), this.$t('openScreen.prompt')
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
