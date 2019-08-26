<template>
  <div class="content">
    <div class="main">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick(activeName)"
      >
        <el-tab-pane
          label="全部分享有礼活动"
          name="first"
        >
        </el-tab-pane>
        <el-tab-pane
          label="进行中"
          name="second"
        >
        </el-tab-pane>
        <el-tab-pane
          label="未开始"
          name="third"
        >
        </el-tab-pane>
        <el-tab-pane
          label="已过期"
          name="fourth"
        >
        </el-tab-pane>
        <el-tab-pane
          label="已停用"
          name="fifth"
        >
        </el-tab-pane>
        <el-tab-pane
          label="添加分享有礼活动"
          name="sixth"
        >
        </el-tab-pane>
      </el-tabs>
    </div>
    <div class="table_list">
    </div>
  </div>

</template>
<script>
import { getList, changeActivity, updateDailyLimit } from '@/api/admin/marketManage/sharePolite.js'
export default {
  mounted () {
    this.langDefault()
    // 页面加载时调用接口初始化数据，初始化加载进行中模块
    this.seacherList(8)
  },
  data () {
    return {
      tableData: []
    }
  },
  methods: {
    // 分享有礼活动领取明细点击跳转
    jumptoReceiveDetail (shareId) {
      this.$router.push({
        name: 'share_polite_detail',
        params: {
          id: shareId,
          flag: true
        }
      })
    },
    // 分模块查询数据列表
    seacherList (category) {
      let obj = {
        'currentPage ': 0,
        'pageRows ': 20,
        'category': category
      }
      getList(obj).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.handleData(res.content)
        }
      })
    },
    // 表格数据处理
    handleData (data) {
      this.tableData = data.pageResult.dataList
    },
    // 标签页点击事件
    handleClick (activeName) {
      switch (activeName) {
        case 'first':
          this.seacherList(0)
          break
        case 'second':
          this.seacherList(8)
          break
        case 'third':
          this.seacherList(4)
          break
        case 'fourth':
          this.seacherList(2)
          break
        case 'fifth':
          this.seacherList(1)
          break
        default:
          this.seacherList(8)
          break
      }
    },
    // 更新每日用户可分享次数上限参数
    saveDailyLimit (num) {
      updateDailyLimit(num).then(res => {
        if (res.error === 0) {
          alert('更新成功！')
        }
        console.log(res)
      })
    },
    addActivity () { },
    // 停用分享有礼活动
    shutdown (shareId) {
      let obj = {
        'shareId': shareId,
        'status': 1
      }
      changeActivity(obj).then(res => {
        if (res.error === 0) {
          alert('停用成功！')
        }
        console.log(res)
      })
    },
    // 启用分享有礼活动
    open (shareId) {
      let obj = {
        'shareId': shareId,
        'status': 0
      }
      changeActivity(obj).then(res => {
        if (res.error === 0) {
          alert('启用成功！')
        }
        console.log(res)
      })
    },
    // 删除分享有礼活动
    del (shareId) {
      let obj = {
        'shareId': shareId,
        'status': 2
      }
      changeActivity(obj).then(res => {
        if (res.error === 0) {
          alert('删除成功！')
          this.seacherSharePoliteList()
        }
        console.log(res)
      })
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
.condition {
  position: relative;
  background-color: #fff;
  padding: 10px 20px 0 20px;
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
  padding: 10px 20px 0 20px;
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
