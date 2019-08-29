<template>
  <div class="refillDetails">
    <div class="refillDetailsMain">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          label="充值明细"
          name="first"
        ></el-tab-pane>
        <el-tab-pane
          label="消费明细"
          name="second"
        ></el-tab-pane>
      </el-tabs>
      <!--template-->
      <div class="refillDetailsTop">
        <div>
          <span>昵称</span>
          <el-input
            v-model="nackNameInput"
            placeholder="请输入昵称"
            size="small"
          ></el-input>
        </div>
        <div>
          <span>手机号码</span>
          <el-input
            v-model="phoneNumInput"
            placeholder="请输入手机号码"
            size="small"
          ></el-input>
        </div>
        <div>
          <span>余额变动时间</span>
          <el-date-picker
            size="small"
            v-model="dateValue"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          >
          </el-date-picker>
        </div>
        <div>
          <el-button
            type="primary"
            size="small"
          >筛选</el-button>
        </div>
      </div>
      <div class="bottom">
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
            label="会员昵称"
            width="120"
          >
          </el-table-column>
          <el-table-column
            prop="phoneNum"
            label="手机号"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="inviter"
            label="余额变动明细"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="date"
            label="余额变动原因"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="cardNum"
            label="余额变动时间"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="status"
            label="备注"
            align="center"
          >
          </el-table-column>
        </el-table>
        <Pagination
          :page-params.sync="pageParams"
          @pagination="search"
        />
      </div>
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
      activeName: 'first',
      nackNameInput: '',
      phoneNumInput: '',
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
      ]

    }
  },
  watch: {
    activeName (newData) {
      let name = ''
      if (newData === 'first') {
        name = 'refillDetails'
      } else {
        name = 'refillDetailsItem'
      }
      this.$router.push({
        name: name
      })
    }
  },
  methods: {
    // taps切换
    handleClick (tab, event) {
      console.log(tab, event)
      if (tab.index === '0') {

      } else {

      }
    },
    search (data) {
      console.log(data)
    }
  }
}
</script>
<style lang="scss" scoped>
.refillDetails {
  padding: 10px;
  padding-bottom: 68px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .refillDetailsMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px;

    .refillDetailsTop {
      display: flex;
      margin-bottom: 20px;
      div {
        display: flex;
        align-items: center;
        /deep/ .el-input {
          width: 150px;
        }
        span {
          display: inline-block;
          width: 100px;
          white-space: nowrap;
          text-align: right;
          margin-right: 10px;
        }
        /deep/ .el-button {
          width: 85px;
        }
      }
      & div:nth-of-type(2) {
        margin: 0 100px;
      }
      & div:nth-of-type(3) {
        margin-right: 20px;
      }
    }
    .bottom {
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
    }
  }
}
</style>
