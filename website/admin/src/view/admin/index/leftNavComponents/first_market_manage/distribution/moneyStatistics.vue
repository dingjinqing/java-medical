<template>
  <div class="distributorListContent">
    <el-form
      :model="searchParam"
      label-width="130px"
      label-position="right"
      :inline="true"
    >
      <div>
        <el-form-item :label="$t('distribution.mobile') + '：'">
          <el-input
            v-model="searchParam.distributorMobile"
            size="small"
            class="inputWidth"
            clearable
            :placeholder="$t('distribution.contentTip')"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('distribution.wxName') + '：'">
          <el-input
            v-model="searchParam.distributorName"
            size="small"
            class="inputWidth"
            clearable
            :placeholder="$t('distribution.contentTip')"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('distribution.orderMobile') + '：'">
          <el-input
            v-model="searchParam.mobile"
            size="small"
            class="inputWidth"
            clearable
            :placeholder="$t('distribution.contentTip')"
          ></el-input>
        </el-form-item>
      </div>
      <div>
        <el-form-item :label="$t('distribution.orderName') + '：'">
          <el-input
            v-model="searchParam.username"
            size="small"
            class="inputWidth"
            clearable
            :placeholder="$t('distribution.contentTip')"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('distribution.startOrderTime') + '：'">
          <el-date-picker
            v-model="searchParam.startCreateTime"
            type="datetime"
            size="small"
            align="right"
            class="selectWidth"
            value-format="yyyy-MM-dd HH:mm:ss"
            :placeholder="$t('distribution.chooseDate')"
          ></el-date-picker>
          {{$t('distribution.to')}}
          <el-date-picker
            v-model="searchParam.endCreateTime"
            type="datetime"
            size="small"
            align="right"
            class="selectWidth"
            value-format="yyyy-MM-dd HH:mm:ss"
            default-time="23:59:59"
            :placeholder="$t('distribution.chooseDate')"
          ></el-date-picker>
        </el-form-item>
      </div>
      <div>
        <el-form-item :label="$t('distribution.rebateOrderSn') + '：'">
          <el-input
            v-model="searchParam.orderSn"
            size="small"
            class="inputWidth"
            clearable
            :placeholder="$t('distribution.contentTip')"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('distribution.startReturnTime') + '：'">
          <el-date-picker
            v-model="searchParam.startRebateTime"
            type="datetime"
            size="small"
            align="right"
            class="selectWidth"
            value-format="yyyy-MM-dd HH:mm:ss"
            :placeholder="$t('distribution.chooseDate')"
          ></el-date-picker>
          {{$t('distribution.to')}}
          <el-date-picker
            v-model="searchParam.endRebateTime"
            type="datetime"
            size="small"
            align="right"
            class="selectWidth"
            value-format="yyyy-MM-dd HH:mm:ss"
            default-time="23:59:59"
            :placeholder="$t('distribution.chooseDate')"
          ></el-date-picker>
        </el-form-item>
      </div>
      <div>
        <el-form-item :label="$t('distribution.returnStatus') + '：'">
          <el-select
            v-model="searchParam.settlementFlag"
            size="small"
            class="inputWidth"
            clearable
            multiple
            :placeholder="$t('distribution.selectTip')"
          >
            <el-option
              v-for="item in statusList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('distribution.distributorGroup') + '：'">
          <el-select
            v-model="searchParam.distributorGroup"
            size="small"
            class="inputWidth"
            clearable
            :placeholder="$t('distribution.selectTip')"
          >
            <el-option
              v-for="item in groupList"
              :key="item.id"
              :label="item.groupName"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('distribution.relationship') + '：'">
          <el-select
            v-model="searchParam.rebateLevel"
            size="small"
            class="inputWidth"
            clearable
            :placeholder="$t('distribution.selectTip')"
          >
            <el-option
              v-for="item in relationshipList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button
            @click="initData"
            type="primary"
            size="small"
          >{{$t('distribution.screen')}}</el-button>
          <el-button
            size="small"
            @click="exportDataList"
          >{{$t('distribution.export')}}</el-button>
        </el-form-item>
      </div>
    </el-form>

    <el-table
      class="version-manage-table"
      header-row-class-name="tableClss"
      :data="tableData"
      border
      style="width: 100%; margin-top: 10px;"
    >
      <template slot="empty">
        <tableEmpty />
      </template>
      <el-table-column
        :label="$t('distribution.distributorName')"
        align="center"
      >
        <template slot-scope="scope">
          <span
            class="linkStyle"
            @click="userNameHandler(scope.row.distributorId)"
          >{{scope.row.distributorName}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="distributorMobile"
        :label="$t('distribution.mobile')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="realName"
        :label="$t('distribution.realName')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="groupName"
        :label="$t('distribution.distributorGroup')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        :label="$t('distribution.rebateOrderSn')"
        align="center"
      >
        <template slot-scope="scope">
          <span
            class="linkStyle"
            @click="orderHandler(scope.row.orderSn)"
          >{{scope.row.orderSn}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="orderAmount"
        :label="$t('distribution.orderAmount')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="userMobile"
        :label="$t('distribution.orderMobile')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        :label="$t('distribution.orderName')"
        align="center"
      >
        <template slot-scope="scope">
          <span
            class="linkStyle"
            @click="userNameHandler(scope.row.userId)"
          >{{scope.row.orderUserName}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="rebateLevel"
        :label="$t('distribution.relationship')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="totalRebateMoney"
        :label="$t('distribution.totalRebateMoney')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="realRebateMoney"
        :label="$t('distribution.realRebateMoney')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="createTime"
        :label="$t('distribution.startOrderTime')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="settlementFlag"
        :label="$t('distribution.rebateStatus')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="rebateTime"
        :label="$t('distribution.startReturnTime')"
        align="center"
      >
      </el-table-column>
    </el-table>
    <pagination
      :page-params.sync="pageParams"
      @pagination="initData"
    />

    <!-- 导出数据弹窗 -->
    <exportDialog
      :tuneUp="exportDialog"
      :param="this.searchParam"
      :totalRows="totalRows"
      :type="2"
      @export="exportHandler"
    />
  </div>
</template>

<script>
import { download } from '@/util/excelUtil.js'
import { brokerageList, distributorAllGroup, brokerageExport } from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination'),
    exportDialog: () => import('./moneyExportDialog')
  },
  props: {
    userId: {
      type: Number,
      default: () => null
    },
    username: {
      type: String,
      default: () => ''
    },
    mobile: {
      type: String,
      default: () => ''
    },
    settlementFlag: {
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      // 搜索
      searchParam: {
        distributorMobile: '',
        distributorName: '',
        mobile: '',
        username: '',
        startCreateTime: '',
        endCreateTime: '',
        orderSn: '',
        startRebateTime: '',
        endRebateTime: '',
        settlementFlag: [],
        distributorGroup: '',
        rebateLevel: ''
      },
      tableData: [], // 表格
      // 分页
      pageParams: {
        currentPage: 1,
        pageRows: 10
      },
      // 返利状态列表
      statusList: [{
        label: '待返利',
        value: 0
      }, {
        label: '已返利',
        value: 1
      }, {
        label: '不返利',
        value: 2
      }],
      // 分销员分组列表
      groupList: [],
      // 返利关系列表
      relationshipList: [{
        label: '自购返利',
        value: 0
      }, {
        label: '直接返利',
        value: 1
      }, {
        label: '间接返利',
        value: 2
      }],

      exportDialog: false, // 导出数据弹窗
      totalRows: 0 // 筛选个数
    }
  },
  watch: {
    userId () {
      this.getAllData()
    }
  },
  mounted () {
    this.getAllData()
  },
  methods: {
    async getAllData () {
      // 获取分销员分组
      await distributorAllGroup().then(res => {
        this.groupList = res.content
      })
      this.searchParam.distributorName = this.username
      this.searchParam.distributorMobile = this.mobile
      this.searchParam.settlementFlag = this.settlementFlag
      this.initData()
    },

    // 佣金统计列表
    initData () {
      return new Promise((resolve, reject) => {
        var paramsData = {}
        paramsData = JSON.parse(JSON.stringify(this.searchParam))
        paramsData.settlementFlag = paramsData.settlementFlag.toString()
        paramsData.userId = this.userId
        paramsData.currentPage = this.pageParams.currentPage
        paramsData.pageRows = this.pageParams.pageRows
        brokerageList(paramsData).then(res => {
          if (res.error === 0) {
            this.handleData(res.content.dataList)
            this.pageParams = res.content.page
            resolve(this.pageParams)
          }
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        if (item.settlementFlag === 0) {
          item.settlementFlag = '待返利'
        } else if (item.settlementFlag === 1) {
          item.settlementFlag = '已返利'
        } else if (item.settlementFlag === 2) {
          item.settlementFlag = '不返利'
        }
        if (item.rebateLevel === 0) {
          item.rebateLevel = '自购返利'
        } else if (item.rebateLevel === 1) {
          item.rebateLevel = '直接返利'
        } else if (item.rebateLevel === 2) {
          item.rebateLevel = '间接返利'
        }
      })
      this.tableData = data
    },

    // 用户昵称跳转
    userNameHandler (id) {
      this.$router.push({
        path: '/admin/home/main/membershipInformation',
        query: {
          userId: id
        }
      })
    },

    // 跳转订单详情
    orderHandler (orderSn) {
      this.$router.push({
        path: '/admin/home/main/orders/info',
        query: {
          orderSn: orderSn
        }
      })
    },

    // 导出数据
    exportDataList () {
      this.initData().then(() => {
        this.totalRows = this.pageParams.totalRows
        this.exportDialog = !this.exportDialog
      })
    },

    // 导出数据弹窗回调函数
    exportHandler (data) {
      // 搜索条件
      var obj = {}
      for (var i in data) {
        if (i === 'startNum' || i === 'endNum') {
          obj[i] = data[i]
        } else if (data[i]) {
          obj[i] = data[i]
        }
      }
      brokerageExport(obj).then(res => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : '佣金统计列表导出.xlsx'
        download(res, decodeURIComponent(fileName))
      })
    }

  }
}

</script>
<style lang="scss" scoped>
.distributorListContent {
  padding: 8px;
  padding-bottom: 38px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.inputWidth {
  width: 170px;
}
.selectWidth {
  width: 200px;
}
.linkStyle {
  color: #5a8bff;
  cursor: pointer;
}
</style>
