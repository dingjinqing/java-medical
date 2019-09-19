<template>
  <div class="content">
    <div class="main">
      <div class="title">提现申请详情</div>
      <div class='lists'>
        <div class="lis">
          <div class="list">
            <div class="list_title">申请提现金额</div>
            <div class="list_v">{{tableData.withdrawCash}}</div>
          </div>
          <div class="list">
            <div class="list_title">出账类型</div>
            <div class="list_v">小程序账户出账</div>
          </div>
          <div class="list_last">
            <div class="list_title">处理状态</div>
            <div class="list_v">出账失败</div>
          </div>
        </div>
      </div>
      <div class="but">
        <el-button
          type="primary"
          size="small"
        >确认出账</el-button>
        <el-button size="small">驳回提现申请</el-button>
        <el-button size="small">添加备注</el-button>
      </div>
      <div class="title">提现申请基本信息</div>
      <div class="table_info">
        <table>
          <tr>
            <td>提现单号：{{tableData.orderSn}}</td>
            <td>申请时间：{{tableData.createTime}}</td>
          </tr>
          <tr>
            <td>出账类型：小程序账户出账</td>
            <td>申请金额：{{tableData.withdrawCash}}</td>
          </tr>
          <tr>
            <td>用户ID：{{tableData.withdrawCash}}</td>
            <td>注册时间：{{tableData.orderSn}}</td>
          </tr>
          <tr>
            <td>用户昵称：{{tableData.username}}</td>
            <td>真实姓名：{{tableData.realName}}</td>
          </tr>
          <tr>
            <td>手机号：{{tableData.mobile}}</td>
            <td>处理状态：{{tableData.status}}</td>
          </tr>
          <tr>
            <td colspan="2">备注信息：（{{tableData.desc}}）</td>
          </tr>
          <tr>
            <td colspan="2">驳回申请原因：（{{tableData.refuseDesc}}）</td>
          </tr>
        </table>
      </div>
      <div class="title">转账明细信息</div>
      <div class="table_info">
        <el-table
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="tableData"
          border
          style="width: 80%"
        >
          <el-table-column
            prop="orderSn"
            label="提现单号"
            width="180"
          >
          </el-table-column>
          <el-table-column
            prop="name"
            label="流水号"
            width="180"
          >
          </el-table-column>
          <el-table-column
            prop="address"
            label="用户提现序号"
          >
          </el-table-column>
          <el-table-column
            prop="date"
            label="出账类型"
            width="180"
          >
          </el-table-column>
          <el-table-column
            prop="name"
            label="可提现金额"
            width="180"
          >
          </el-table-column>
          <el-table-column
            prop="address"
            label="申请提现金额"
          >
          </el-table-column>
          <el-table-column
            prop="name"
            label="操作时间"
            width="180"
          >
          </el-table-column>
          <el-table-column
            prop="address"
            label="操作"
          >
          </el-table-column>
        </el-table>
      </div>
      <div class="title">当前用户提现记录</div>
      <div class="table_info">
        <el-table
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="tableData"
          border
          style="width: 100%"
        >
          <el-table-column
            prop="date"
            label="提现单号"
            width="180"
          >
          </el-table-column>
          <el-table-column
            prop="name"
            label="流水号"
            width="180"
          >
          </el-table-column>
          <el-table-column
            prop="address"
            label="提现序号"
          >
          </el-table-column>
          <el-table-column
            prop="date"
            label="出账类型"
            width="180"
          >
          </el-table-column>
          <el-table-column
            prop="name"
            label="申请提现金额"
            width="180"
          >
          </el-table-column>
          <el-table-column
            prop="address"
            label="申请时间"
          >
          </el-table-column>
          <el-table-column
            prop="date"
            label="处理状态"
            width="180"
          >
          </el-table-column>
          <el-table-column
            prop="name"
            label="处理备注"
            width="180"
          >
          </el-table-column>
          <el-table-column
            prop="address"
            label="操作"
          >
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>
<script>
import { withdrawDetail } from '@/api/admin/marketManage/distribution.js'
// 引入分页
// import pagination from '@/components/admin/pagination/pagination'
export default {
  data () {
    return {
      tableData: [],
      id: ''
    }
  },
  mounted () {
    if (this.$route.query.id > 0) {
      this.id = this.$route.query.id
    }
    this.detail()
  },
  methods: {
    detail () {
      withdrawDetail(this.id).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.tableData = res.content
        }
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
    padding-left: 10px;
  }
  .title {
    width: 100%;
    font-weight: bold;
    font-size: 17px;
    padding: 20px;
    margin-top: 10px;
  }
  .lists {
    margin-left: 10px;
    width: 80%;
    border: 1px solid #ebeef5;
    height: 120px;
    padding: 10px;
  }
  .lis {
    margin-top: 5px;
  }
  .list {
    position: flex;
    float: left;
    border-right: 1px solid #f5f5f5;
    width: 33.33%;
    height: 90px;
  }
  .list_last {
    position: flex;
    float: left;
    width: 33.33%;
    height: 90px;
  }
  .list_title {
    text-align: center;
    font-size: 15px;
    margin-top: 20px;
  }
  .list_v {
    text-align: center;
    font-size: 20px;
    margin-top: 21px;
    font-weight: bold;
  }
  .but {
    padding: 10px;
    margin-top: 10px;
    margin-bottom: 10px;
  }
  .table_info {
    padding: 10px;
  }
  tr {
    height: 20px;
    line-height: 20px;
    border: 1px solid #ebeef5;
  }
  td {
    padding: 10px;
    width: 650px;
    border: 1px solid #ebeef5;
  }
  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    font-weight: bold;
    color: #000;
    padding: 8px 10px;
  }
}
</style>
