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
          <div class="wrapper">
            <el-button
              type="primary"
              @click="addActivity"
            >添加砍价活动</el-button>

            <div class="rightContent">
              <span>砍价设置：每个被邀请的用户，单日可帮助砍价 </span>
              <el-input
                style="width: 80px"
                size="small"
              ></el-input>
              <span>次</span>
              <span>设置为空时，不限制帮助砍价次数</span>
              <el-button
                type="primary"
                size="small"
              >保存设置</el-button>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="bargainName"
          label="砍价活动名称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="bargainType"
          label="活动类型"
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
          prop="status"
          label="活动状态"
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
          prop="goodsNumber"
          label="商品库存"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="stock"
          label="砍价库存"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="successNumber"
          label="成功数量"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="bargainUserNumber"
          label="发起砍价人数"
          align="center"
        >
        </el-table-column>

        <el-table-column
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <span>编辑</span>
              <span>分享</span>
              <span @click="puaseBargain(scope.row.id)">停用</span>
              <span>查看砍价订单</span>
              <span>获取新用户明细</span>
              <span>查看发起砍价用户</span>
              <span>活动效果数据</span>
              <span @click="delBargain(scope.row.id)">删除</span>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="footer">
        <span>当前页面1/1，总记录4条</span>
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :page-size="20"
          layout="prev, pager, next, jumper"
          :total="4"
        >
        </el-pagination>
      </div>
    </div>
  </div>

</template>
<script>
import { bargainList, updateBargain, deleteBargain } from '@/api/admin/marketManage/bargain.js'
export default {
  data () {
    return {
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
      tabIndex: 2,
      currentPage: 1,
      tableData: []
    }
  },
  mounted () {
    // 初始化数据
    this.initDataList()
  },
  methods: {
    initDataList (tab) {
      let obj = {
        'state': parseInt(tab.index),
        'currentPage': 1
      }

      bargainList(obj).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.handleData(res.content.dataList)
        }
      })
    },

    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        item.bargainType = item.bargainType === 0 ? '定人' : '任意价'
        item.vaildDate = `${item.startTime}至${item.endTime}`
        item.status = this.getActStatusString(item.status, item.startTime, item.endTime)
      })
      this.tableData = data
    },

    // 停用砍价
    puaseBargain (id) {
      let obj = {
        'id': id,
        'status': 0
      }

      updateBargain(obj).then((res) => {
        if (res.error === 0) {
          alert('停用成功')
        }
      })
    },

    // 当前页发生变化
    handleCurrentChange () {
      console.log(this.currentPage)
    },
    handleClick (tab) {
      this.initDataList(tab)
    },
    addActivity () {
      console.log(111)
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
    .wrapper {
      display: flex;
      justify-content: space-between;
      .rightContent {
        .el-button {
          margin-left: 5px;
        }
        span {
          height: 30px;
          line-height: 30px;
        }
        :nth-of-type(3) {
          color: #999;
        }
      }
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
