<template>
  <div class="content">
    <div class="main">
      <div class="title">提现申请详情</div>
      <div class="lists">
        <div class="lis">
          <div class="list">
            <div class="list_title">申请提现金额</div>
            <div class="list_v">{{ tableData[0].withdrawCash }}</div>
          </div>
          <div class="list">
            <div class="list_title">出账类型</div>
            <div class="list_v">{{ tableData.type }}</div>
          </div>
          <div class="list_last">
            <div class="list_title">处理状态</div>
            <div class="list_v">
              {{ tableData[0].status | getWithdrawStatus }}
            </div>
          </div>
        </div>
      </div>
      <div class="but">
        <el-button
          type="primary"
          size="small"
          v-if="tableData[0].status === 3"
          @click="changeStatus(tableData[0], 'pass')"
          >通过</el-button
        >
        <el-button
          type="primary"
          size="small"
          v-if="tableData[0].status === 1"
          @click="changeStatus(tableData[0], 'chargeOff')"
          >确认出账</el-button
        >
        <el-button
          size="small"
          v-if="tableData[0].status === 1 || tableData[0].status === 3"
          @click="changeStatus(tableData[0], 'reject')"
          >驳回提现申请</el-button
        >
        <el-button size="small" @click="addRemarks">添加备注</el-button>
      </div>
      <div class="title">提现申请基本信息</div>
      <div class="table_info">
        <table width="100%">
          <tr>
            <td>提现单号：{{ tableData[0].orderSn }}</td>
            <td>申请时间：{{ tableData[0].createTime }}</td>
          </tr>
          <tr>
            <td>出账类型：小程序账户出账</td>
            <td>申请金额：{{ tableData[0].withdrawCash }}</td>
          </tr>
          <tr>
            <td>用户ID：{{ tableData[0].withdrawCash }}</td>
            <td>注册时间：{{ tableData[0].orderSn }}</td>
          </tr>
          <tr>
            <td>用户昵称：{{ tableData[0].username }}</td>
            <td>真实姓名：{{ tableData[0].realName }}</td>
          </tr>
          <tr>
            <td>手机号：{{ tableData[0].mobile }}</td>
            <td>处理状态：{{ tableData[0].status | getWithdrawStatus }}</td>
          </tr>
          <tr>
            <td colspan="2">
              备注信息：{{
                tableData[0].desc ? '(' + tableData[0].desc + ')' : ''
              }}
            </td>
          </tr>
          <tr>
            <td colspan="2">
              驳回申请原因：{{
                tableData[0].refuseDesc
                  ? '(' + tableData[0].refuseDesc + ')'
                  : ''
              }}
            </td>
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
          style="width: 100%"
        >
          <el-table-column
            prop="orderSn"
            label="提现单号"
            width="180"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="withdrawNum"
            label="流水号"
            width="180"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="withdrawUserNum"
            label="用户提现序号"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="type"
            label="出账类型"
            width="180"
            align="center"
          >
            <template slot-scope="scope">
              <div>
                {{ scope.row.type === 1 ? '公众号出账' : '小程序出账' }}
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="withdraw"
            label="可提现金额"
            width="180"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="withdrawCash"
            label="申请提现金额"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="updateTime"
            label="操作时间"
            width="180"
            align="center"
          >
          </el-table-column>
        </el-table>
      </div>
      <div class="title">当前用户提现记录</div>
      <div class="table_info">
        <el-table
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="otherRecord"
          border
          style="width: 100%"
        >
          <el-table-column
            prop="orderSn"
            label="提现单号"
            width="180"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="withdrawNum"
            label="流水号"
            width="180"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="withdrawUserNum"
            label="提现序号"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="type"
            label="出账类型"
            width="180"
            align="center"
          >
            <template slot-scope="scope">
              <div>
                {{ scope.row.type === 1 ? '公众号出账' : '小程序出账' }}
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="withdrawCash"
            label="申请提现金额"
            width="180"
            align="center"
          >
          </el-table-column>
          <el-table-column prop="createTime" label="申请时间" align="center">
          </el-table-column>
          <el-table-column
            prop="status"
            label="处理状态"
            width="180"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="desc"
            label="处理备注"
            width="180"
            align="center"
          >
          </el-table-column>
        </el-table>
      </div>
    </div>
    <el-dialog title="提示" :visible.sync="remarksShow" width="30%">
      <el-input
        type="textarea"
        rows="4"
        resize="none"
        placeholder="请输入内容"
        v-model="remarksDetail"
      >
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="remarksShow = false">取 消</el-button>
        <el-button type="primary" @click="confirmRemarks">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { getWithdrawDetail, changeWithdrawStatus, addDesc } from '@/api/admin/basicConfiguration/doctorWithDraw'
// 引入分页
// import pagination from '@/components/admin/pagination/pagination'
export default {
  data () {
    return {
      tableData: [],
      otherRecord: [],
      id: '',
      remarksShow: false,
      remarksDetail: ''
    }
  },
  mounted () {
    if (this.$route.query.id > 0) {
      this.id = this.$route.query.id
      console.log(111)
    }
    this.detail()
  },
  methods: {
    detail () {
      getWithdrawDetail(this.id).then(res => {
        console.log(res)
        let data = res.content
        if (res.error === 0) {
          let arr = []
          arr.push(data)
          this.tableData = arr

          if (this.tableData.type === 1) {
            this.tableData.type = '公众号出账'
          } else {
            this.tableData.type = '小程序出账'
          }

          // 当前用户提现记录
          this.otherRecord = res.content.withdrawList.dataList
          this.otherRecord.forEach(item => {
            if (item.status === 1) {
              item.status = '待审核'
            } else if (item.status === 2) {
              item.status = '拒绝'
            } else if (item.status === 3) {
              item.status = '已审核待出账'
            } else if (item.status === 4) {
              item.status = '出账成功'
            } else {
              item.status = '失败'
            }
          })
        }
        console.log(this.otherRecord)
      })
    },
    changeStatus ({ row: data }, actions) {
      switch (actions) {
        case 'pass':
          this.$confirm('确认通过提现审核吗？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            changeWithdrawStatus({ checkStatus: 3, orderSn: data.orderSn }).then(res => {
              this.$message.success({
                message: '已通过'
              })
              this.detail()
            })
          })
          break
        case 'chargeOff':
          this.$confirm('确认出账吗？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            changeWithdrawStatus({ checkStatus: 4, orderSn: data.orderSn }).then(res => {
              this.$message.success({
                message: '已出账'
              })
              this.detail()
            })
          })
          break

        default:
          this.$prompt('请输入驳回理由', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消'
          }).then(({ value }) => {
            changeWithdrawStatus({ checkStatus: 2, orderSn: data.orderSn, refuseDesc: value }).then(res => {
              this.$message.success({
                message: '已驳回请求'
              })
              this.detail()
            })
          }).catch(() => {
          })

          break
      }
    },
    addRemarks () {
      this.remarksShow = true
      this.remarksDetail = this.tableData[0].desc
    },
    confirmRemarks () {
      addDesc({ id: this.id, desc: this.remarksDetail }).then(res => {
        if (res.error === 0) {
          this.remarksShow = false
          this.$message.success({ message: '添加备注成功' })
          this.detail()
        } else {
          this.$message.error({ message: '添加失败' })
        }
      })
    }
  },
  filters: {
    getWithdrawStatus (status) {
      let statusName = {
        'default': '失败',
        1: '待审核',
        2: '拒绝',
        3: '已审核待出账',
        4: '出账成功'
      }
      return statusName[status] || statusName['default']
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
    width: 100%;
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
