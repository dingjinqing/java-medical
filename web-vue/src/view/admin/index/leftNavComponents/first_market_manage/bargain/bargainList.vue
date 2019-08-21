<template>
  <div class="content">
    <div class="main">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          label="全部砍价活动"
          name="first"
        >
          <el-button
            type="primary"
            @click="addActivity"
          >添加砍价活动</el-button>
        </el-tab-pane>
        <el-tab-pane
          label="进行中"
          name="second"
        >
          <el-button
            type="primary"
            @click="addActivity"
          >添加砍价活动</el-button>
          <div class="rightContent">
            <span>砍价设置：每个被邀请的用户，单日可帮助砍价 </span>
            <input type="text">
            <span>次</span>
            <span>设置为空时，不限制帮助砍价次数</span>
            <el-button type="primary">保存设置</el-button>
          </div>
        </el-tab-pane>
        <el-tab-pane
          label="未开始"
          name="third"
        >
          <el-button
            type="primary"
            @click="addActivity"
          >添加砍价活动</el-button>
        </el-tab-pane>
        <el-tab-pane
          label="已过期"
          name="fourth"
        >
          <el-button
            type="primary"
            @click="addActivity"
          >添加砍价活动</el-button>
        </el-tab-pane>
        <el-tab-pane
          label="已停用"
          name="fifth"
        >
          <el-button
            type="primary"
            @click="addActivity"
          >添加砍价活动</el-button>
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
          prop=""
          label="砍价活动名称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="活动类型"
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
          prop=""
          label="活动状态"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="商品名称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="商品库存"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="砍价库存"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="成功数量"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="发起砍价人数"
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
// import { } from '@/api/admin/marketManage/spellGroup.js'

export default {
  data () {
    return {
      tableData: [],
      activeName: 'second'
    }
  },
  mounted () {
  },
  methods: {
    handleClick (tab) {
      console.log(tab)
      // let obj = {
      //   'type': parseInt(tab.index) + 1,
      //   'currentPage': 1,
      //   'pageRows': 1
      // }
      // groupBuyList(obj).then(res => {
      //   console.log(res)
      // }).catch(() => {
      //   this.$message.error('保存失败')
      // })
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
