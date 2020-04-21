<template>
  <div class="content">
    <!-- 筛选条件 -->
    <div class="main">
      <el-form :inline="true">
        <el-form-item
          size="small"
          :label="$t('couponGive.username') + '：'"
        >
          <el-input
            v-model="username"
            :placeholder="$t('couponGive.usernamePlaceholder')"
          />
        </el-form-item>
        <el-form-item
          size="small"
          :label="$t('couponGive.mobile') + '：'"
        >
          <el-input
            v-model="mobile"
            :placeholder="$t('couponGive.mobilePlaceholder')"
          />
        </el-form-item>
        <el-form-item
          size="small"
          :label="$t('couponGive.useStatus') + '：'"
        >
          <el-select v-model="isUsed">
            <el-option
              v-for="item in statusList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          size="small"
          label="领取方式："
        >
          <el-select v-model="isReceive">
            <el-option
              v-for="item in receiveList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-button
          size="small"
          type="primary"
          @click="onSubmit"
          style="margin-left: 10px;"
        >{{$t('couponGive.filter')}}</el-button>
        <!-- <el-button size="small">{{$t('couponGive.export')}}</el-button> -->
      </el-form>
    </div>
    <!-- 表格数据 -->
    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="username"
          :label="$t('couponGive.username')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="mobile"
          :label="$t('couponGive.mobile')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="couponName"
          :label="$t('couponGive.couponName')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="accessMode"
          :label="$t('couponGive.accessMode')"
          align="center"
          :formatter="accessModeName"
        ></el-table-column>
        <el-table-column
          prop="isUsed"
          :label="$t('couponGive.isUsed')"
          align="center"
          :formatter="isUsedName"
        ></el-table-column>
        <el-table-column
          prop="orderSn"
          :label="$t('couponGive.orderSn')"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('couponGive.validityPeriod')"
          align="center"
          width="160px"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.startTime }}<br />{{ $t('promoteList.to') }}<br />{{ scope.row.endTime }}</span>
            <!-- <span v-html="scope.row.validDate"></span> -->
          </template>
        </el-table-column>
        <el-table-column
          prop="createTime"
          :label="$t('couponGive.recTime')"
          align="center"
          width="160px"
        ></el-table-column>
        <el-table-column
          prop="usedTime"
          :label="$t('couponGive.useTime')"
          align="center"
          width="160px"
        ></el-table-column>
        <el-table-column
          prop=""
          :label="$t('couponGive.operate')"
          align="center"
        >
          <template slot-scope="scope">

            <el-button
              v-if="scope.row.isUsed === `未使用`"
              @click="stopCoupon(scope.row.id)"
              type="text"
            >{{$t('couponGive.abolish')}}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页组件 -->
      <pagination
        :page-params.sync="pageParams"
        @pagination="loadData"
      />
    </div>
  </div>
</template>
<script>
import { receiveDetails, stopCoupon } from '@/api/admin/marketManage/couponGive.js'
import wrapper from '@/components/admin/wrapper/wrapper'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: {
    pagination, wrapper
  },
  data: function () {
    return {
      getSource: 9,

      accessId: '',
      username: '',
      mobile: '',
      actId: '',
      isUsed: '0', // 使用状态值
      // 使用状态
      statusList: [{
        label: this.$t('couponGive.all'),
        value: '0'
      }, {
        label: this.$t('couponGive.notUsed'),
        value: '1'
      }, {
        label: this.$t('couponGive.alreadyUsed'),
        value: '2'
      }, {
        label: this.$t('couponGive.expired'),
        value: '3'
      }, {
        label: this.$t('couponGive.repealed'),
        value: '4'
      }],
      isReceive: '0', // 领取状态值
      // 领取方式
      receiveList: [{
        label: '全部',
        value: '0'
      }],
      tableData: [], // 表格数据
      pageParams: {}, // 分页
      requestParam: {}
    }
  },
  methods: {
    // 加载数据
    loadData () {
      this.requestParam.accessId = this.accessId
      this.requestParam.actId = this.actId
      this.requestParam.mobile = this.mobile
      this.requestParam.username = this.username
      this.requestParam.isUsed = this.isUsed
      this.requestParam.currentPage = this.pageParams.currentPage
      this.requestParam.pageRows = this.pageParams.pageRows
      receiveDetails(this.requestParam).then(res => {
        this.tableData = res.content.dataList
        this.pageParams = res.content.page
      })
    },
    // 筛选
    onSubmit () {
      this.pageParams.currentPage = 1
      this.loadData()
    },
    // 获取方式 标识转文字
    accessModeName (row, column) {
      switch (row.accessMode) {
        case 0: row.accessMode = `${this.$t('couponGive.grant')}`
          break
        case 1: row.accessMode = `${this.$t('couponGive.receive')}`
          break
      }
      return row.accessMode
    },
    // 是否使用 标识转文字
    isUsedName (row, column) {
      switch (row.isUsed) {
        case 0: row.isUsed = `${this.$t('couponGive.notUsed')}`
          break
        case 1: row.isUsed = `${this.$t('couponGive.alreadyUsed')}`
          break
        case 2: row.isUsed = `${this.$t('couponGive.expired')}`
          break
        case 3: row.isUsed = `${this.$t('couponGive.repealed')}`
          break
      }
      return row.isUsed
    },
    // 废除优惠券
    stopCoupon (id) {
      let delParam = {
        'id': id
      }
      stopCoupon(delParam).then(res => {
        if (res.error === 0) {
          this.loadData()
        }
      })
    }
  },
  // 初始化加载项
  mounted () {
    this.accessId = this.$route.params.id
    this.actId = this.$route.params.couponId
    this.loadData()
  }
}
</script>
<style lang="scss" scoped>
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  .main {
    background-color: #fff;
    min-height: 50px;
    line-height: 50px;
    .el-input {
      line-height: 50px;
    }
    .el-select {
      line-height: 50px;
    }
    .el-form-item {
      margin-bottom: 0;
      margin-right: 0;
      margin-left: 10px;
    }
  }
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  color: #000;
  padding: 8px 10px;
}
.table_list {
  position: relative;
  margin-top: 10px;
  background-color: #fff;
  padding: 10px;
}
</style>
