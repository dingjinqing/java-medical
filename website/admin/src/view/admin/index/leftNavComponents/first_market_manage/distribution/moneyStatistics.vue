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
            :placeholder="$t('distribution.chooseDate')"
          ></el-date-picker>
          {{$t('distribution.to')}}
          <el-date-picker
            v-model="searchParam.endCreateTime"
            type="datetime"
            size="small"
            align="right"
            class="selectWidth"
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
            :placeholder="$t('distribution.chooseDate')"
          ></el-date-picker>
          {{$t('distribution.to')}}
          <el-date-picker
            v-model="searchParam.endRebateTime"
            type="datetime"
            size="small"
            align="right"
            class="selectWidth"
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
          <el-button size="small">{{$t('distribution.export')}}</el-button>
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
      <el-table-column
        :label="$t('distribution.distributorName')"
        align="center"
      >
        <template slot-scope="scope">
          <span
            class="linkStyle"
            @click="userNameHandler(scope.row.userId)"
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
        align="enter"
      >
      </el-table-column>
      <el-table-column
        prop="orderUserName"
        :label="$t('distribution.orderName')"
        align="enter"
      >
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
  </div>
</template>

<script>
import { brokerageList, distributorGroupList } from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  props: {
    userId: {
      type: Number,
      default: () => 0
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
        settlementFlag: '',
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
        label: '不返利',
        value: 1
      }, {
        label: '已返利',
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
      }]
    }
  },
  watch: {
    userId () {
      this.initData()
    }
  },
  mounted () {
    this.initData()
    this.getGroupList() // 分销员分组
  },
  methods: {
    // 佣金统计列表
    initData () {
      var paramsData = {}
      paramsData = this.searchParam
      paramsData.userId = this.userId
      paramsData.currentPage = this.pageParams.currentPage
      paramsData.pageRows = this.pageParams.pageRows
      brokerageList(paramsData).then(res => {
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          this.pageParams = res.content.page
        }
      })
    },

    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        if (item.settlementFlag === 0) {
          item.settlementFlag = '待返利'
        } else if (item.settlementFlag === 1) {
          item.settlementFlag = '不返利'
        } else if (item.settlementFlag === 2) {
          item.settlementFlag = '已返利'
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

    // 获取分销员分组
    getGroupList () {
      distributorGroupList().then(res => {
        if (res.error === 0) {
          this.groupList = res.content
        }
      })
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
