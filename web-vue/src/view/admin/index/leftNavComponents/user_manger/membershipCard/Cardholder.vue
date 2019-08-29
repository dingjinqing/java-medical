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
                v-for="item in selectOptions"
                :key="item.value"
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
          prop="userID"
          align="center"
          label="会员ID"
          width="120"
        >
        </el-table-column>
        <el-table-column
          label="昵称"
          align="center"
        >
          <template slot-scope="scope">
            <span
              @click="handleToUserDetail(scope.row)"
              style="cursor:pointer;color:#5a8bff"
            >
              {{scope.row.sickName}}
            </span>
          </template>
        </el-table-column>
        <el-table-column
          prop="phoneNum"
          label="手机号"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="inviter"
          label="邀请人"
          width="120"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="date"
          label="领卡时间"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="cardNum"
          label="会员卡号"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="status"
          label="卡状态"
          width="120"
          align="center"
        >
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
              >{{item}}</span>
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
export default {
  components: { Pagination: () => import('@/components/admin/pagination/pagination') },
  data () {
    return {
      pageParams: {
        totalRows: 10,
        currentPage: 1,
        pageRows: 10
      },
      carIdInput: '',
      carNameInput: '',
      phoneInput: '',
      cardNuberInput: '',
      selectOptions: [{
        value: '0',
        label: '全部'
      }, {
        value: '1',
        label: '正常'
      }, {
        value: '2',
        label: '已废除'
      },
      {
        value: '3',
        label: '已过期'
      }],
      statusValue: '0',
      dateValue: '',
      tableData: [
        {
          userID: '51',
          phoneNum: '18811309193',
          sickName: '啦啦啦',
          inviter: '帅飞',
          date: '20190828 14:40:44',
          cardNum: '2342342334235',
          status: '正常'
        },
        {
          userID: '12',
          sickName: '啦啦啦',
          phoneNum: '18811309193',
          inviter: '帅飞',
          date: '20190828 14:40:44',
          cardNum: '2342342334235',
          status: '正常'
        },
        {
          userID: '43',
          sickName: '啦啦啦',
          phoneNum: '18811309193',
          inviter: '帅飞',
          date: '20190828 14:40:44',
          cardNum: '2342342334235',
          status: '正常'
        }
      ],
      operation: ['充值明细', '消费明细', '废除']
    }
  },
  mounted () {
    // 初始化数据
    this.defaultData()
  },
  methods: {
    defaultData () {

    },
    // 操作部分点击
    handleToOperation (row, index) {
      console.log(row, index)
    },
    search (data) {
      console.log(data)
    },
    // 跳转到会员详情页
    handleToUserDetail (row) {
      this.$router.push({
        name: 'membershipInformation'
      })
    },
    // 顶部按钮系列点击
    handleTobtn (index) {
      switch (index) {
        case 0:
          break
        case 1:
          this.carIdInput = ''
          this.carNameInput = ''
          this.phoneInput = ''
          this.cardNuberInput = ''
          this.statusValue = '0'
          this.dateValue = ''
          break
        case 2:
      }
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
