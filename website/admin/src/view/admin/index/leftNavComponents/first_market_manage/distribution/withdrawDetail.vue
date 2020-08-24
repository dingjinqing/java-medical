<template>
  <div class="content">
    <div class="main">
      <div class="title">提现申请详情</div>
      <div class='lists'>
        <div class="lis">
          <div class="list">
            <div class="list_title">申请提现金额</div>
            <div class="list_v">￥{{tableData[0].withdrawCash ? tableData[0].withdrawCash : '0.00'}}</div>
          </div>
          <div class="list">
            <div class="list_title">出账类型</div>
            <div class="list_v">{{tableData[0].type}}</div>
          </div>
          <div class="list_last">
            <div class="list_title">处理状态</div>
            <div class="list_v">{{tableData[0].statusName}}</div>
          </div>
        </div>
      </div>
      <div class="but">
        <el-button
          type="primary"
          size="small"
          v-if="tableData[0].status === 1"
          @click="withdrawCheckHandler()"
        >提现信息审核完成</el-button>
        <el-button
          type="primary"
          size="small"
          v-if="tableData[0].status === 3 || tableData[0].status === 5"
          @click="withdrawHandler()"
        >确认出账</el-button>
        <el-button
          size="small"
          v-if="tableData[0].status === 1 || tableData[0].status === 3 || tableData[0].status === 5"
          @click="clickHandler(1)"
        >驳回提现申请</el-button>
        <el-button
          size="small"
          @click="clickHandler(2)"
        >添加备注</el-button>
      </div>
      <div class="title">提现申请基本信息</div>
      <div class="table_info">
        <table v-if="tableData && tableData.length > 0">
          <tr>
            <td>提现单号：{{tableData[0].orderSn}}</td>
            <td>申请时间：{{tableData[0].createTime}}</td>
          </tr>
          <tr>
            <td>出账类型：{{tableData[0].type}}</td>
            <td>申请金额：￥{{tableData[0].withdrawCash}}</td>
          </tr>
          <tr>
            <td>用户ID：{{tableData[0].userId}}</td>
            <td>注册时间：{{tableData[0].userCreateTime}}</td>
          </tr>
          <tr>
            <td>用户昵称：<span
                class="opt"
                @click="userNameHandler(tableData[0].userId)"
              >{{tableData[0].username}}</span></td>
            <td>真实姓名：{{tableData[0].realName}}</td>
          </tr>
          <tr>
            <td>手机号：{{tableData[0].mobile}}</td>
            <td>处理状态：{{tableData[0].statusName}}</td>
          </tr>
          <tr>
            <td colspan="2">备注信息：{{tableData[0].desc ? tableData[0].desc : '（不通过核实，需填写备注信息）'}}</td>
          </tr>
          <tr>
            <td colspan="2">驳回申请原因：{{tableData[0].refuseDesc ? tableData[0].refuseDesc : '（驳回申请，需填写备注信息）'}}</td>
          </tr>
        </table>
        <template v-if="!tableData || tableData.length === 0">
          <tableEmpty />
        </template>
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
          <template slot="empty">
            <tableEmpty />
          </template>
          <el-table-column
            prop="orderSn"
            label="提现单号"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="withdrawNum"
            label="流水号"
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
            align="center"
          >
          </el-table-column>
          <el-table-column
            label="可提现金额"
            align="center"
          >
            <template slot-scope="scope">
              <span>￥{{scope.row.withdraw ? scope.row.withdraw : '0.00'}}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="申请提现金额"
            align="center"
          >
            <template slot-scope="scope">
              <span>￥{{scope.row.withdrawCash ? scope.row.withdrawCash : '0.00'}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="checkTime"
            label="操作时间"
            align="center"
            width="100px"
          >
          </el-table-column>
          <!-- <el-table-column
            label="操作"
            align="center"
          >
            <template slot-scope="scope">
              <div class="opt">
                <p @click="relationHandler(scope.row.id)">查看关联订单</p>
              </div>
            </template>
          </el-table-column> -->
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
          <template slot="empty">
            <tableEmpty />
          </template>
          <el-table-column
            prop="orderSn"
            label="提现单号"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="withdrawNum"
            label="流水号"
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
            align="center"
          >
          </el-table-column>
          <el-table-column
            label="申请提现金额"
            align="center"
          >
            <template slot-scope="scope">
              <span>￥{{scope.row.withdrawCash ? scope.row.withdrawCash : '0.00'}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="申请时间"
            align="center"
            width="100px"
          >
          </el-table-column>
          <el-table-column
            prop="statusName"
            label="处理状态"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="desc"
            label="处理备注"
            align="center"
          >
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
          >
            <template slot-scope="scope">
              <div class="opt">
                <p @click="withdrawDetailHandler(scope.row.id)">查看详情</p>
              </div>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          :page-params.sync="pageParams"
          @pagination="initData()"
        />
      </div>
    </div>

    <!-- 关联订单弹窗 -->
    <relationDialog :tuneUp="relationDialog" />

    <!-- 添加备注/驳回申请弹窗 -->
    <el-dialog
      :title="typeTip"
      :visible.sync="typeDialog"
      :close-on-click-modal="false"
      width="30%"
      center
    >
      <div>
        <p>{{typeTitle}}</p>
        <p style="margin-top: 10px;">
          <el-input
            v-model="typeValue"
            :autosize="{ minRows: 5}"
            style="width:100%;"
            size="small"
            type="textarea"
          ></el-input>
        </p>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          size="small"
          @click="cancelDialog()"
        >取 消</el-button>
        <el-button
          type="primary"
          size="small"
          @click="sureDialog()"
        >确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script>
import { withdrawDetail, withdrawResult, withdrawAddRemark } from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination'),
    relationDialog: () => import('./relationOrderDialog')
  },
  inject: ['reload'],
  data () {
    return {
      id: '',
      tableData: [], // 提现详情
      otherRecord: [], // 提现记录
      pageParams: {},
      // 提现处理状态
      statusList: [{
        value: 1,
        label: '待审核'
      },
      {
        value: 2,
        label: '已驳回申请'
      },
      {
        value: 3,
        label: '已审核, 待出账'
      },
      {
        value: 4,
        label: '出账成功'
      },
      {
        value: 5,
        label: '出账失败'
      },
      {
        value: 6,
        label: '红包待领取'
      }],

      relationDialog: false, // 关联订单弹窗
      type: 1, // 弹窗类型 (1: 驳回, 2: 备注)
      typeDialog: false, // 弹窗
      typeTip: '', // 弹窗标题
      typeTitle: '', // 内容标题
      typeValue: '' // 内容
    }
  },
  mounted () {
    if (this.$route.query.id > 0) {
      this.id = this.$route.query.id
    }
    this.initData()
  },
  methods: {
    // 获取提现详情
    initData () {
      withdrawDetail(this.id).then(res => {
        if (res.error === 0) {
          this.tableData = []
          this.tableData.push(res.content)
          // 详情信息处理
          this.tableData.forEach(item => {
            if (item.type === 1) {
              item.type = '公众号出账'
            } else {
              item.type = '小程序出账'
            }
            this.statusList.forEach(val => {
              if (item.status === val.value) {
                item.statusName = val.label
              }
            })
          })

          // 当前用户提现记录
          this.otherRecord = res.content.userWithdrawList.dataList
          this.pageParams = res.content.userWithdrawList.page
          this.otherRecord.forEach(item => {
            if (item.type === 1) {
              item.type = '公众号出账'
            } else {
              item.type = '小程序出账'
            }
            this.statusList.forEach(val => {
              if (item.status === val.value) {
                item.statusName = val.label
              }
            })
          })
        } else {
          this.$message.warning(res.message)
        }
      })
    },

    // 提现审核完成
    withdrawCheckHandler () {
      this.$confirm('点击核实提现信息完成操作前请确认已经对提取金额、提取申请账号确实进行了核对并核实无误，确定要核实完成吗？', '确认核实提现信息', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        // 更改用户状态
        withdrawResult({
          isPass: 1,
          orderSn: this.tableData[0].orderSn,
          refuseDesc: ''
        }).then(res => {
          if (res.error === 0) {
            this.$message.success('核实完成')
            this.initData()
          } else {
            this.$message.warning(res.message)
          }
        })
      })
    },

    // 确认出账
    withdrawHandler () {
      this.$confirm('确认要出账吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        withdrawResult({
          isPass: 1,
          orderSn: this.tableData[0].orderSn,
          refuseDesc: ''
        }).then(res => {
          if (res.error === 0) {
            this.$message.success('出账成功')
            // 更改用户状态
            this.initData()
          } else {
            this.$message.warning(res.message)
          }
        })
      })
    },

    // 备注/驳回弹窗
    clickHandler (type) {
      this.type = type
      if (this.type === 1) {
        this.typeTip = '驳回申请原因'
        this.typeTitle = '请填写驳回提现申请的原因'
        this.typeValue = ''
      } else {
        this.typeTip = '添加备注'
        this.typeTitle = '请填写备注信息'
        this.typeValue = this.tableData[0].desc ? this.tableData[0].desc : ''
      }
      this.typeDialog = !this.typeDialog
    },

    // 取消驳回/备注弹窗
    cancelDialog () {
      this.typeDialog = false
      this.typeValue = ''
    },

    // 确定驳回/备注弹窗
    sureDialog () {
      if (this.type === 1) {
        if (this.typeValue === '') {
          this.$message.warning('请填写驳回原因')
          return false
        }
        // 驳回申请
        withdrawResult({
          isPass: 0,
          orderSn: this.tableData[0].orderSn,
          refuseDesc: this.typeValue
        }).then(res => {
          if (res.error === 0) {
            this.$message.success('驳回成功')
            // 更改用户状态
            this.typeDialog = false
            this.typeValue = ''
            this.initData()
          } else {
            this.$message.warning(res.message)
          }
        })
      } else {
        if (this.typeValue !== '') {
          // 添加备注
          withdrawAddRemark({
            id: this.id,
            desc: this.typeValue
          }).then(res => {
            if (res.error === 0) {
              this.$message.success('添加成功')
              this.typeDialog = false
              this.typeValue = ''
              this.initData()
            } else {
              this.$message.warning(res.message)
            }
          })
        }
      }
    },

    // 查看关联订单
    relationHandler () {
      this.relationDialog = !this.relationDialog
    },

    // 跳转用户详情
    userNameHandler (id) {
      this.$router.push({
        path: '/admin/home/main/membershipInformation',
        query: {
          userId: id
        }
      })
    },

    // 查看提现详情
    withdrawDetailHandler (id) {
      this.$router.push({
        path: '/admin/home/main/distribution/withdraw/detail',
        query: {
          id: id
        }
      })
      this.reload()
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
  .opt {
    color: #5a8bff;
    cursor: pointer;
  }
}
</style>
