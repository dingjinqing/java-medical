<template>
  <div class="content">
    <div class="main">
      <el-tabs
        v-model="tabSwitch"
        @tab-click="initDataList"
      >
        <el-tab-pane
          v-for="(item) in tabInfo"
          :key="item.name"
          :label="item.title"
          :name="item.name"
        >
          <el-button
            v-if="tableListView"
            type="primary"
            @click="addActivity()"
          >添加活动</el-button>
        </el-tab-pane>
      </el-tabs>

    </div>
    <addGroupBuy v-if="tableListView===false" />
    <div
      class="table_list"
      v-if="tableListView"
    >
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="name"
          label="活动名称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="activityType"
          label="活动类型"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="goodsName"
          label="商品名称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="vaildDate"
          label="有效期"
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
          prop="limitAmount"
          label="成团人数"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="groupOrderNum"
          label="成团订单数"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <span>编辑</span>
              <span>分享</span>
              <span
                @click="changeStatus(scope.row.id)"
                v-if="scope.row.status==0"
              > 停用</span>
              <span
                @click="changeStatus(scope.row.id)"
                v-if="scope.row.status==1"
              > 启用</span>
              <span>领取明细</span>
              <span @click="deleteGroupBuy(scope.row.id)">删除</span>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="footer">
      </div>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />
    </div>
  </div>

</template>
<script>

import addGroupBuy from './addGroupBuy.vue'
import pagination from '@/components/admin/pagination/pagination.vue'
import {
  groupBuyList,
  changeStatusActivity,
  deleteGroupBuyActivity
} from '@/api/admin/marketManage/spellGroup.js'

export default {
  components: {
    addGroupBuy,
    pagination
  },
  data () {
    return {
      pageParams: {},
      tableData: [],
      tabSwitch: '2',
      tabInfo: [{
        title: '全部砍价活动',
        name: '1'
      }, {
        title: '进行中',
        name: '2'
      }, {
        title: '未开始',
        name: '3'
      }, {
        title: '已过期',
        name: '4'
      }, {
        title: '已停用',
        name: '5'
      }],
      tabIndex: 3,
      currentPage: 0,
      pageRows: 1,
      totalRows: 0,
      pageCount: undefined,
      tableListView: true
    }
  },
  mounted () {
    console.log('mounted-----------------------')
    // 初始化数据
    this.initDataList()
  },
  methods: {
    initDataList () {
      let obj = {
        'type': this.tabSwitch,
        'currentPage': this.pageParams.currentPage,
        'pageRows': this.pageParams.pageRows
      }
      console.log(this.tabInfo.length)
      // 新增标签
      if (this.tabSwitch === '6') {
        return
      }
      // 不是新增
      if (this.tabInfo.length > 5) {
        console.log('新增活动')
        this.tableListView = true
        this.tabInfo = this.tabInfo.filter(tab => tab.name !== '6')
      }
      groupBuyList(obj).then(res => {
        console.log(res.content.dataList)
        console.log(res)
        console.log(res.content.page)
        console.log(res.content.page.pageRows)
        this.pageParams = res.content.page
        this.currentPage = res.content.page.currentPage
        this.pageRows = res.content.page.pageRows
        this.pageCount = res.content.page.pageCount
        this.totalRows = res.content.page.totalRows

        this.handleData(res.content.dataList)
      }).catch(() => {
        this.$message.error('保存失败')
      })
    },
    handleData (tabData) {
      tabData.map((item, index) => {
        item.vaildDate = `${item.startTime}至${item.endTime}`
        item.statusText = this.getActStatusString(item.status, item.startTime, item.endTime)
      })
      this.tableData = tabData
    },
    changeStatus (id) {
      let obj = {
        'id': id
      }
      changeStatusActivity(obj).then(res => {
        console.log('change=>res = ' + res)
        this.initDataList()
      })
    },
    deleteGroupBuy (data) {
      let obj = {
        'id': data
      }
      deleteGroupBuyActivity(obj).then(res => {
        console.log(res)
      })
    },
    // 添加新活动
    addActivity () {
      console.log('添加拼团活动--------------' + this.tabInfo.length)
      if (this.tabInfo.length > 5) {
        return
      }
      console.log(typeof this.tabInfo)
      this.tabInfo.push({
        title: '添加拼团活动',
        name: '6',
        content: 'New Tab content'
      })
      this.tableListView = false
      this.tabSwitch = '6'
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
  padding: 10px 20px 10px 20px;
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
.footer {
  padding: 20px 0 20px 20px;
  display: flex;
  justify-content: flex-end;
  span {
    display: block;
    height: 32px;
    line-height: 32px;
  }
}
</style>
