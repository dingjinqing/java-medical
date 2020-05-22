<template>
  <div class="userStatistics">
    <div class="userStatistics_content">
      <el-tabs
        v-model="assetManage"
        @tab-click="handleClick"
      >
        <el-tab-pane
          :label="this.$t('assetsManage.cashManage')"
          name="first"
        >
        </el-tab-pane>
        <el-tab-pane
          :label="this.$t('assetsManage.scoreManage')"
          name="second"
        >
        </el-tab-pane>
      </el-tabs>
      <div>
        <!--        筛选条件-->
        <div>
          <el-row
            :gutter="20"
            class="row_style"
          >
            <el-col :span="1.5">{{$t('assetsManage.number')}}：</el-col>
            <el-col :span="4">
              <el-input
                size="small"
                v-model="param.tradeSn"
              ></el-input>
            </el-col>
            <el-col :span="1.5">{{$t('assetsManage.hour')}}：</el-col>
            <el-col :span="8">
              <el-date-picker
                size="small"
                v-model="param.startTime"
                type="datetime"
                style="width:200px "
                value-format="yyyy-MM-dd HH:mm:ss"
              >
              </el-date-picker>
              <span>{{$t('promoteList.to')}}</span>
              <el-date-picker
                size="small"
                v-model="param.endTime"
                type="datetime"
                style="width: 200px"
                value-format="yyyy-MM-dd HH:mm:ss"
              >
              </el-date-picker>
            </el-col>
            <el-col :span="1.5">{{$t('assetsManage.flows')}}：</el-col>
            <el-col :span="5">
              <el-select
                v-model="param.tradeFlow"
                size="small"
              >
                <el-option
                  :label="$t('promoteList.all')"
                  value="-1"
                ></el-option>
                <el-option
                  label="收入"
                  value="0"
                ></el-option>
                <el-option
                  label="支出"
                  value="1"
                ></el-option>
              </el-select>
            </el-col>
          </el-row>
          <el-row
            :gutter="20"
            class="row_style"
          >
            <el-col :span="1.5">{{$t('assetsManage.Nickname')}}：</el-col>
            <el-col :span="4">
              <el-input
                size="small"
                v-model="param.username"
              ></el-input>
            </el-col>
            <el-col :span="1.5">{{$t('assetsManage.amount')}}：</el-col>
            <el-col :span="8">
              <el-row :gutter="8">
                <el-col :span="4">
                  <el-input
                    size="small"
                    v-model="param.lowerLimit"
                  ></el-input>
                </el-col>
                <el-col :span="1"><span>{{$t('promoteList.to')}}</span></el-col>
                <el-col :span="4">
                  <el-input
                    size="small"
                    v-model="param.upperLimit"
                  ></el-input>
                </el-col>
              </el-row>
            </el-col>
            <el-col :span="1.5">{{$t('assetsManage.Type')}}：</el-col>
            <el-col :span="5">
              <template>
                <el-select
                  size="small"
                  v-model="param.tradeType"
                  :placeholder="$t('promoteList.all')"
                >
                  <el-option
                    v-for="item in tradeTypeOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </template>
            </el-col>
          </el-row>
          <el-row
            :gutter="20"
            class="row_style"
          >
            <el-col :span="1.5">{{$t('assetsManage.name')}}：</el-col>
            <el-col :span="4">
              <el-input
                size="small"
                v-model="param.realName"
              ></el-input>
            </el-col>
            <el-col :span="1.5">{{$t('assetsManage.phone')}}：</el-col>
            <el-col :span="4">
              <el-input
                size="small"
                v-model="param.mobile"
              ></el-input>
            </el-col>
            <el-col :span="1">
              <el-button
                type="primary"
                size="small"
                @click="initData"
              >{{$t('promoteList.filter')}}</el-button>
            </el-col>
            <el-col :span="1">
              <el-button
                type="primary"
                size="small"
                @click="resetParam"
              >{{$t('assetsManage.Reset')}}</el-button>
            </el-col>
            <el-col :span="1">
              <el-button
                type="primary"
                size="small"
                @click="showDownload"
              >{{$t('assetsManage.Export')}}</el-button>
            </el-col>
          </el-row>
        </div>
        <div class="table_list">
          <!--          表单数据-->
          <el-table
            class="version-manage-table"
            :data="tableData"
            border
            style="width: 100%"
          >
            <el-table-column
              prop="tradeTime"
              :label="$t('assetsManage.hour')"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="tradeSn"
              :label="$t('assetsManage.number')"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="tradeNum"
              :label="$t('assetsManage.amount')"
              align="center"
              v-if="this.flag === 0"
            >
            </el-table-column>
            <el-table-column
              prop="tradeNum"
              :label="$t('assetsManage.points')"
              align="center"
              v-if="this.flag === 1"
            >
            </el-table-column>
            <el-table-column
              prop="username"
              :label="$t('assetsManage.Nickname')"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="realName"
              :label="$t('assetsManage.name')"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="mobile"
              :label="$t('assetsManage.phone')"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="tradeType"
              :label="$t('assetsManage.Type')"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="tradeFlow"
              :label="$t('assetsManage.flows')"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="tradeStatus"
              :label="$t('assetsManage.status')"
              align="center"
            >
            </el-table-column>
          </el-table>

          <pagination
            :page-params.sync="pageParams"
            @pagination="handleClick"
          />

        </div>
      </div>
    </div>
    <!--    数据导出/下载弹窗-->
    <assetsExportDialog
      :show.sync="showDialog"
      :param="this.param"
      :totalRows="this.totalRows"
    />
  </div>
</template>

<script>
import pagination from '@/components/admin/pagination/pagination.vue'
import assetsExportDialog from './assetsExportDialog'
import { cashDetail } from '@/api/admin/firstWebManage/assetsManage/assetsManage.js'
export default {
  components: { pagination, assetsExportDialog },
  created () {
    if (this.$route.params.flag === 0) {
      this.assetManage = 'first'
      this.tradeTypeOptions = this.cashOptions
    } else if (this.$route.params.flag === 1) {
      this.assetManage = 'second'
      this.tradeTypeOptions = this.scoreOptions
    }
    this.initData()
  },
  data () {
    return {
      // 下载/导出弹窗
      showDialog: false,
      totalRows: 0,
      tradeTypeOptions: [{
        value: '0',
        label: '全部'
      }],
      scoreOptions: this.$t('assetsManage.scoreOptions'),
      cashOptions: this.$t('assetsManage.cashOptions'),
      assetManage: 'first',
      // 标示是积分还是现金
      flag: this.$route.params.flag,
      tableData: [],
      pageParams: {
        currentPage: 0,
        pageRows: 20
      },
      param: {
        startTime: '',
        endTime: '',
        lowerLimit: '',
        upperLimit: '',
        tradeSn: '',
        tradeType: '',
        tradeFlow: '',
        tradeContent: this.$route.params.flag,
        username: '',
        realName: '',
        mobile: '',
        currentPage: '',
        pageRows: '',
        exportRowStart: 1,
        exportRowEnd: 5000
      }
    }
  },
  methods: {
    showDownload () {
      this.showDialog = true
    },
    // 重置筛选条件
    resetParam () {
      if (this.assetManage === 'first') {
        this.param = {}
        this.flag = 0
        this.param.tradeContent = 0
        this.tradeTypeOptions = this.cashOptions
      } else if (this.assetManage === 'second') {
        this.param = {}
        this.flag = 1
        this.param.tradeContent = 1
        this.tradeTypeOptions = this.scoreOptions
      }
      this.initData()
    },
    handleClick (tab, event) {
      this.assetManage = tab.name
      this.resetParam()
    },
    // 初始化表单数据
    initData () {
      this.param.currentPage = this.pageParams.currentPage
      this.param.pageRows = this.pageParams.pageRows
      cashDetail(this.param).then(res => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
          this.handleData(this.tableData)
          this.totalRows = this.pageParams.totalRows
          this.exportRowEnd = this.totalRows > 5000 ? 5000 : this.totalRows
        }
      }).catch(err => console.log(err))
    },
    handleData (data) {
      data.map((item, index) => {
        switch (item.tradeFlow) {
          case 0:
            item.tradeFlow = '收入'
            break
          case 1:
            item.tradeFlow = '支出'
            break
          case 2:
            item.tradeFlow = '待确认收入'
            break
        }
        switch (item.tradeStatus) {
          case 0:
            item.tradeStatus = '已入账'
            break
          case 1:
            item.tradeStatus = '已出账'
            break
        }
        if (this.flag === 0) {
          item.tradeType = this.cashTradeTypeChange(item.tradeType)
        } else if (this.flag === 1) {
          item.tradeType = this.scoreTradeTypeChange(item.tradeType)
        }
      })
    },
    cashTradeTypeChange (data) {
      switch (data) {
        case 1:
          return '微信支付'
        case 2:
          return '余额支付'
        case 3:
          return '会员卡支付'
        case 4:
          return '现金退款'
        case 5:
          return '用户余额退款'
        case 6:
          return '用户会员卡余额退款'
        case 7:
          return '返利'
        case 8:
          return '抽奖获得余额'
        case 9:
          return '用户余额充值'
        case 10:
          return '用户会员卡余额充值'
        case 11:
          return '支付有礼'
        case 12:
          return ''
      }
    },
    scoreTradeTypeChange (data) {
      switch (data) {
        case 1:
          return '积分支付'
        case 2:
          return '积分兑换'
        case 3:
          return '幸运大抽奖消耗积分'
        case 4:
          return '积分充值'
        case 5:
          return '用户登录送积分'
        case 6:
          return '用户签到送积分'
        case 7:
          return '开卡赠送积分'
        case 8:
          return '买单送积分'
        case 9:
          return '交易退货退积分'
        case 10:
          return '组团瓜分积分'
        case 11:
          return '抽奖获得积分'
        case 12:
          return '支付有礼'
      }
    }
  }
}

</script>
<style lang="scss" scoped>
.userStatistics {
  padding: 10px;
  .userStatistics_content {
    background: #fff;
    padding: 15px 0 0 20px;
  }
}
.tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.row_style {
  margin-top: 15px;
  margin-left: 15px;
}
.table_list {
  position: relative;
  margin-top: 20px;
  background-color: #fff;
  padding: 10px 20px 10px 20px;
  .select_info {
    /*display: flex;*/
    margin: 10px 0px 20px;
    /*span {*/
    /*  white-space: nowrap;*/
    /*  height: 32px;*/
    /*  line-height: 32px;*/
    /*}*/
    /deep/ .el-input__inner {
      width: 200px;
      display: inline-block;
    }
  }
  .footer_right {
    padding: 20px 0 20px 20px;
    display: flex;
    justify-content: flex-end;
    span {
      display: block;
      height: 32px;
      line-height: 32px;
    }
  }
}
</style>
