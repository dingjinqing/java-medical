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
            v-model="searchParam.mobile"
            size="small"
            class="inputWidth"
            :placeholder="$t('distribution.contentTip')"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('distribution.wxName') + '：'">
          <el-input
            v-model="searchParam.username"
            size="small"
            class="inputWidth"
            :placeholder="$t('distribution.contentTip')"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('distribution.orderMobile') + '：'">
          <el-input
            v-model="searchParam.orderMobile"
            size="small"
            class="inputWidth"
            :placeholder="$t('distribution.contentTip')"
          ></el-input>
        </el-form-item>
      </div>
      <div>
        <el-form-item :label="$t('distribution.orderName') + '：'">
          <el-input
            v-model="searchParam.orderName"
            size="small"
            class="inputWidth"
            :placeholder="$t('distribution.contentTip')"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('distribution.startOrderTime') + '：'">
          <el-date-picker
            v-model="searchParam.startOrderTime"
            type="datetime"
            size="small"
            align="right"
            class="selectWidth"
            :placeholder="$t('distribution.chooseDate')"
          ></el-date-picker>
          {{$t('distribution.to')}}
          <el-date-picker
            v-model="searchParam.endOrderTime"
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
            :placeholder="$t('distribution.contentTip')"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('distribution.startReturnTime') + '：'">
          <el-date-picker
            v-model="searchParam.startReturnTime"
            type="datetime"
            size="small"
            align="right"
            class="selectWidth"
            :placeholder="$t('distribution.chooseDate')"
          ></el-date-picker>
          {{$t('distribution.to')}}
          <el-date-picker
            v-model="searchParam.endReturnTime"
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
            v-model="searchParam.status"
            size="small"
            class="inputWidth"
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
            v-model="searchParam.group"
            size="small"
            class="inputWidth"
            :placeholder="$t('distribution.selectTip')"
          >
            <el-option
              v-for="item in groupList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('distribution.relationship') + '：'">
          <el-select
            v-model="searchParam.relationship"
            size="small"
            class="inputWidth"
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
        prop="distributorName"
        :label="$t('distribution.distributorName')"
        align="center"
      >
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
        prop="orderSn"
        :label="$t('distribution.rebateOrderSn')"
        align="center"
      >
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
        prop=""
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
        prop=""
        :label="$t('distribution.rebateStatus')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop=""
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
// import { brokerageList } from '@/api/admin/marketManage/distribution.js'
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
        mobile: '',
        username: '',
        orderMobile: '',
        orderName: '',
        startOrderTime: '',
        endOrderTime: '',
        orderSn: '',
        startReturnTime: '',
        endReturnTime: '',
        status: '',
        group: '',
        relationship: ''
      },
      tableData: [], // 表格
      pageParams: {}, // 分页
      requestParam: {},
      statusList: [{
        label: '',
        value: 0
      }], // 返利状态列表
      groupList: [{
        label: '',
        value: 0
      }], // 分销员分组列表
      relationshipList: [{
        label: '',
        value: 0
      }] // 返利关系列表
    }
  },
  watch: {
    userId () {
      console.log(this.userId)
      this.initData()
    }
  },
  mounted () {
    this.initData()
  },
  methods: {
    // 佣金统计列表
    initData () {
      this.requestParam = this.searchParam
      this.requestParam.userId = this.userId
      // this.requestParams.currentPage = this.pageParams.currentPage
      // this.requestParams.pageRows = this.pageParams.pageRows
      console.log(this.requestParam)
      // brokerageList(this.requestParam).then(res => {
      //   if (res.error === 0) {
      //     this.tableData = res.content.dataList
      //     this.pageParams = res.content.page
      //     this.tableData.map(item => {
      //       if (item.rebateLevel === 0) {
      //         item.rebateLevel = '自购返利'
      //       }
      //       if (item.rebateLevel === 1) {
      //         item.rebateLevel = '一级返利'
      //       }
      //       if (item.rebateLevel === 2) {
      //         item.rebateLevel = '二级返利'
      //       }
      //     })
      //   }
      // })
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
</style>
