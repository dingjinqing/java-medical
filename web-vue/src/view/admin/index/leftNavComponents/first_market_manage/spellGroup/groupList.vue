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
          <el-button type="primary">添加好友助力活动</el-button>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
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
          prop=""
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
          prop=""
          label="成团人数"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="成团订单数"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="操作"
          align="center"
        >
          <template>
            <div class="opt">
              <span>编辑</span>
              <span>分享</span>
              <span>停用</span>
              <span>领取明细</span>
              <span>删除</span>
            </div>
          </template>
        </el-table-column>

      </el-table>
    </div>
  </div>

</template>
<script>
import { groupBuyList } from '@/api/admin/marketManage/spellGroup.js'

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
      tabIndex: 3,
      currentPage: 1
    }
  },
  mounted () {
  },
  methods: {
    handleClick (tab) {
      console.log(tab)
      let obj = {
        'type': parseInt(tab.index) + 1,
        'currentPage': 1,
        'pageRows': 1
      }
      groupBuyList(obj).then(res => {
        console.log(res)
      }).catch(() => {
        this.$message.error('保存失败')
      })
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
</style>
