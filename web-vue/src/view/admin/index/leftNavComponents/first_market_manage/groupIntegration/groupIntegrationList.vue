<template>
  <div class="content">
    <div class="main">
      <div class="p_top_right">
        <div class="topRightDiv s_btn">
          <el-button
            @click="addGroupIntegration()"
            type="primary"
            size="small"
          >添加瓜分积分活动</el-button>
        </div>
      </div>
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
          prop="name"
          label="活动名称"
          align="center"
        >

        </el-table-column>
        <el-table-column
          prop="content"
          label="活动内容"
          align="center"
        >
        </el-table-column>
        <el-table-column
          label="活动积分总量"
          align="center"
        >
          <template slot-scope="scope">
            <div>
              <span>{{scope.row.totalIntegration}}</span>
              <span>{{scope.row.leftIntegration}}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="actDate"
          label="有效期"
          align="center"
        >

        </el-table-column>
        <el-table-column
          prop="expire"
          label="活动状态"
          align="center"
          width="80"
        >

        </el-table-column>
        <el-table-column
          prop="inteUserSum"
          label="参与人数"
          align="center"
        >

        </el-table-column>
        <el-table-column
          prop="inteGroupSum"
          label="团数量"
          align="center"
        >

        </el-table-column>
        <el-table-column
          prop="useIntegration"
          label="消耗积分"
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
              <span @click="puaseGroupIntegration(scope.row.id)">停用</span>
              <span @click="upGroupIntegration(scope.row.id)">启用</span>
              <span>参团明细</span>
              <span>成团明细</span>
              <span @click="delGroupIntegration(scope.row.id)">删除</span>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>

</template>
<script>
import { groupIntegrationList, changeGroupIntegrationStatus, delGroupIntegration } from '@/api/admin/marketManage/groupIntegrationList.js'
export default {
  data () {
    return {
      tableData: []
    }
  },
  mounted () {
    // 初始化数据
    this.seacherGroupIntegrationList()
  },
  methods: {

    seacherGroupIntegrationList () {
      let obj = {
        'currentPage ': 0,
        'pageRows ': 20,
        'type': 0
      }

      groupIntegrationList(obj).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.handleData(res.content.dataList)
        }
      })
    },
    // 对过期状态值设置对应显示
    getExpireString (expire) {
      if (expire === 1) {
        return '进行中'
      } else if (expire === 2) {
        return '未开始'
      } else if (expire === 3) {
        return '已过期'
      } else if (expire === 4) {
        return '已停用'
      }
    },
    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        item.content = `${item.limitAmount}人瓜分${item.inteGroup}`
        item.totalIntegration = `${item.inteTotal}积分`
        item.leftIntegration = `剩余：${item.inteRemain}积分`
        item.actDate = `${item.startTime}至${item.endTime}`
        item.expire = this.getExpireString(item.expire)
      })
      this.tableData = data
    },

    // 停用瓜分积分活动
    puaseGroupIntegration (id) {
      let data = {
        'id': id,
        'status': 0
      }
      changeGroupIntegrationStatus(data).then(res => {
        if (res.error === 0) {
          alert('停用成功！')
        }
        console.log(res)
      })
    },
    // 停用瓜分积分活动
    upGroupIntegration (id) {
      let data = {
        'id': id,
        'status': 1
      }
      changeGroupIntegrationStatus(data).then(res => {
        if (res.error === 0) {
          alert('启用成功！')
        }
        console.log(res)
      })
    },
    // 删除瓜分积分活动
    delGroupIntegration (id) {
      delGroupIntegration(id).then(res => {
        if (res.error === 0) {
          alert('删除成功！')
          this.seacherCouponList()
        }
      })
    },
    // 增加瓜分积分活动
    addGroupIntegration () {
      this.$router.push({
        name: 'group_integration_add'
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
