<template>
  <div class="refillDetails">
    <div class="refillDetailsMain">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          :label="$t('memberCard.chargeDetail')"
          name="first"
        ></el-tab-pane>
        <el-tab-pane
          :label="$t('memberCard.consumeDetail')"
          name="second"
        ></el-tab-pane>
      </el-tabs>
      <!--template-->
      <div class="refillDetailsTop">
        <div>
          <span>{{$t('memberCard.username')}}</span>
          <el-input
            v-model="nackNameInput"
            :placeholder="$t('memberCard.pleaseInputUsername')"
            size="small"
          ></el-input>
        </div>
        <div>
          <span>{{$t('memberCard.mobile')}}</span>
          <el-input
            v-model="phoneNumInput"
            :placeholder="$t('memberCard.pleaseInputMobile')"
            size="small"
          ></el-input>
        </div>
        <div>
          <span>{{ $t('memberCard.balanceChangeTime') }}</span>
          <el-date-picker
            size="small"
            v-model="dateValue"
            type="daterange"
            :range-separator="$t('memberCard.to')"
            :start-placeholder="$t('memberCard.startDate')"
            :end-placeholder="$t('memberCard.overDate')"
            value-format="yyyy-MM-dd HH:mm:ss"
            :default-time="['00:00:00','23:59:59']"
          >
          </el-date-picker>
        </div>
        <div>
          <el-button
            type="primary"
            size="small"
            @click="search"
          >{{$t('memberCard.filter')}}</el-button>
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
            prop="username"
            align="center"
            :label="$t('memberCard.memberName')"
            width="120"
          >
          </el-table-column>
          <el-table-column
            prop="mobile"
            :label="$t('memberCard.mobile')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="money"
            :label="$t('memberCard.balanceChangeDetail')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="reason"
            :label="$t('memberCard.balanceChangeReason')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="createTime"
            :label="$t('memberCard.balanceChangeTime')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="message"
            :label="$t('memberCard.message')"
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
import { getConsumeListRequest, getChargeListRequest } from '@/api/admin/memberManage/memberCard.js'
export default {
  components: { Pagination: () => import('@/components/admin/pagination/pagination') },
  data () {
    return {
      pageParams: {
        totalRows: 10,
        currentPage: 1,
        pageRows: 20
      },
      cardId: null,
      cardNo: null,
      username: null,
      userId: null,
      activeName: 'first',
      nackNameInput: '',
      phoneNumInput: '',
      dateValue: '',
      tableData: [],
      chargeData: [], // 充值明细数据
      consumeData: [] // 消费明细数据

    }
  },
  created () {
    this.cardId = this.$route.query.cardId
    this.cardNo = this.$route.query.cardNo
    this.cardType = this.$route.query.cardType
    this.nackNameInput = this.$route.query.username
    this.userId = this.$route.query.userId
    let flag = this.$route.query.activeName
    this.activeName = flag === 1 ? 'first' : 'second'
    this.loadDefaultData()
  },
  watch: {
    lang () {

    },
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
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  methods: {
    // 1- 加载默认数据
    loadDefaultData () {
      let obj = {
        'pageRows': this.pageParams.pageRows,
        'currentPage': this.pageParams.currentPage,
        'cardId': this.cardId,
        'cardNo': this.cardNo,
        'cardType': this.cardType,
        'username': this.nackNameInput,
        'userId': this.userId,
        'mobile': this.phoneNumInput,
        'firstTime': this.dateValue ? this.dateValue[0] : null,
        'endTime': this.dateValue ? this.dateValue[1] : null
      }
      console.log(obj)
      if (this.activeName === 'first') {
        this.getChargeList(obj)
      } else if (this.activeName === 'second') {
        this.getConsumeList(obj)
      }
    },

    // 2- 请求后台充值明细
    getChargeList (data) {
      getChargeListRequest(data).then(res => {
        if (res.error === 0) {
          // success
          // set table data
          this.tableData = res.content.dataList
          // set pagination
          this.pageParams = res.content.page
          console.log(this.tableData)
        }
      })
    },
    // 3- 请求后台消费明细
    getConsumeList (data) {
      getConsumeListRequest(data).then(res => {
        if (res.error === 0) {
          // success
          // set table data
          this.tableData = res.content.dataList
          // set pagination
          this.pageParams = res.content.page
          console.log(this.tableData)
        }
      })
    },
    // 4- taps切换
    handleClick (tab, event) {
      console.log(tab, event)
      // this.clearInputData()
      this.loadDefaultData()
    },
    // 5- clear input data
    clearInputData () {
      this.nackNameInput = null
      this.phoneNumInput = null
      this.dateValue = null
    },
    search (data) {
      console.log(data)
      this.loadDefaultData()
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
