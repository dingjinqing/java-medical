<template>
  <div class="Cardholder">
    <div class="CardholderMain">
      <div class="CardholderTop">
        <div class="topDiv">
          <div>
            <span>会员ID</span>
            <el-input
              v-model="carIdInput"
              placeholder="请输入内容"
              size="small"
            ></el-input>
          </div>
          <div>
            <span>昵称</span>
            <el-input
              v-model="carNameInput"
              placeholder="请输入内容"
              size="small"
            ></el-input>
          </div>
          <div>
            <span>手机号</span>
            <el-input
              v-model="phoneInput"
              placeholder="请输入内容"
              size="small"
            ></el-input>
          </div>
          <div>
            <span>会员卡号</span>
            <el-input
              v-model="cardNuberInput"
              placeholder="请输入内容"
              size="small"
            ></el-input>
          </div>
        </div>
        <div class="topDiv">
          <div>
            <span>卡状态</span>
            <el-select
              v-model="statusValue"
              size="small"
            >
              <el-option
                v-for="(item,index) in selectOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div>
            <span>领卡时间</span>
            <el-date-picker
              v-model="dateValue"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd HH:mm:ss"
              :default-time="['00:00:00','23:59:59']"
              size="small"
            >
            </el-date-picker>
          </div>
        </div>
        <div class="bottomDiv">
          <el-button
            type="primary"
            size="small"
            @click="handleTobtn(0)"
          >筛选</el-button>
          <el-button
            type="info"
            plain
            size="small"
            @click="handleTobtn(1)"
          >重置</el-button>
          <el-button
            type="info"
            plain
            size="small"
            @click="handleTobtn(2)"
          >会员导出</el-button>
        </div>
      </div>
    </div>
    <div class="tableMain">
      <el-table
        class="version-manage-table"
        :data="tableData"
        header-row-class-name="tableClss"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="userId"
          align="center"
          label="会员ID"
          width="90"
        >
        </el-table-column>
        <el-table-column
          label="昵称"
          width="120"
          align="center"
        >
          <template slot-scope="scope">
            <span
              @click="handleToUserDetail(scope.row)"
              style="cursor:pointer;color:#5a8bff"
            >
              {{scope.row.username}}
            </span>
          </template>
        </el-table-column>
        <el-table-column
          prop="mobile"
          label="手机号"
          width="120"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="invitedName"
          label="邀请人"
          width="120"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="领卡时间"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="cardNo"
          label="会员卡号"
          align="center"
        >
        </el-table-column>
        <el-table-column
          label="卡状态"
          align="center"
        >
          <template slot-scope="scope">
            <span v-if="scope.row.flag === 0"> 正常 </span>
            <span v-else-if="scope.row.flag === 1"> 已废除({{scope.row.updateTime}}) </span>
            <span v-else-if="scope.row.flag === 2"> 已过期 </span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <div class="operation">
              <span
                v-for="(item,index) in operation"
                :key="index"
                @click="handleToOperation(scope.row,index)"
              >
              <span v-if="item.flag !== 1 && index !== 2">{{item}}</span>
              </span>
            </div>
          </template>
        </el-table-column>

      </el-table>
      <Pagination
        :page-params.sync="pageParams"
        @pagination="search"
      />
    </div>
  </div>
</template>
<script>
import { getAllCardHolders } from '@/api/admin/memberManage/memberCard.js'
export default {
  components: { Pagination: () => import('@/components/admin/pagination/pagination') },

  data () {
    return {
      pageParams: {
        totalRows: null,
        currentPage: 1,
        pageRows: 20
      },
      cardId: null, // 会员卡id
      carIdInput: '',
      carNameInput: '',
      phoneInput: '',
      cardNuberInput: '',
      selectOptions: [{
        value: -1,
        label: '全部'
      }, {
        value: 0,
        label: '正常'
      }, {
        value: 1,
        label: '已废除'
      },
      {
        value: 2,
        label: '已过期'
      }],
      statusValue: -1, // 卡状态默认值
      dateValue: null, // 领取时间
      tableData: [],
      operation: ['充值明细', '消费明细', '废除']
    }
  },
  created () {
    this.cardId = this.$route.query.cardId
  },
  mounted () {
    // 初始化数据
    this.defaultData()
  },
  methods: {
    // 1- 加载默认的数据
    defaultData () {
      let obj = {
        'pageRows': this.pageParams.pageRows,
        'currentPage': this.pageParams.currentPage,
        'cardId': this.cardId,
        'userId': this.carIdInput,
        'username': this.carNameInput,
        'mobile': this.phoneInput,
        'cardNo': this.cardNuberInput,
        'flag': this.statusValue,
        'firstDateTime': this.dateValue ? this.dateValue[0] : null,
        'secondDateTime': this.dateValue ? this.dateValue[1] : null
      }
      console.log(obj)
      // 获取api
      getAllCardHolders(obj).then(res => {
        if (res.error === 0) {
          // 成功
          this.tableData = res.content.dataList
          // 分页信息
          this.pageParams = res.content.page
        }
      })
    },
    // 2- 顶部按钮系列点击
    handleTobtn (index) {
      switch (index) {
        case 0:
          // 筛选
          this.defaultData()
          break
        case 1:
          // 重置
          this.carIdInput = ''
          this.carNameInput = ''
          this.phoneInput = ''
          this.cardNuberInput = ''
          this.statusValue = -1
          this.dateValue = null
          break
        case 2:
      }
    },
    // 3- 处理分页信息传递出来的分页数据
    search (data) {
      console.log(data)
      this.defaultData()
    },
    // 操作部分点击
    handleToOperation (row, index) {
      console.log(row, index)
    },

    // 跳转到会员详情页
    handleToUserDetail (row) {
      this.$router.push({
        name: 'membershipInformation'
      })
    }

  }
}
</script>
<style lang="scss" scoped>
.Cardholder {
  padding: 10px;
  padding-bottom: 68px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .CardholderMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px;
    .topDiv {
      display: flex;
      margin-bottom: 20px;
      div {
        /deep/ .el-input {
          width: 140px;
        }
        span {
          white-space: nowrap;
          display: inline-block;
          width: 80px;
          text-align: right;
          margin-right: 20px;
        }
        display: flex;
        align-items: center;
      }
    }
    .bottomDiv {
      display: flex;
      /deep/ .el-button {
        width: 85px;
      }
    }
  }
  .tableMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px;
    margin-top: 10px;
    /deep/ .tableClss th {
      background-color: #f5f5f5;
      border: none;
      height: 36px;
      font-weight: bold;
      color: #000;
      padding: 8px 10px;
      .el-checkbox {
        margin-left: -4px;
      }
    }
    .operation {
      display: flex;
      justify-content: space-around;
      span {
        cursor: pointer;
        color: #5a8bff;
      }
    }
  }
}
</style>
