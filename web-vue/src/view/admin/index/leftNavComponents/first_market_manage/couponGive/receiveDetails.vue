<template>
  <div class="content">
    <!-- 筛选条件 -->
    <wrapper>
      <div class="main">
        <el-form :inline="true">
          <el-form-item
            size="small"
            :label="$t('couponGive.username')"
          >
            <el-input
              v-model="username"
              :placeholder="$t('couponGive.usernamePlaceholder')"
            />
          </el-form-item>
          <el-form-item
            size="small"
            :label="$t('couponGive.mobile')"
          >
            <el-input
              v-model="mobile"
              :placeholder="$t('couponGive.mobilePlaceholder')"
            />
          </el-form-item>
          <el-form-item
            size="small"
            :label="$t('couponGive.useStatus')"
          >
            <el-select v-model="isUsed">
              <el-option
                :label="$t('couponGive.all')"
                value="-1"
              ></el-option>
              <el-option
                :label="$t('couponGive.notUsed')"
                value="0"
              ></el-option>
              <el-option
                :label="$t('couponGive.alreadyUsed')"
                value="1"
              ></el-option>
              <el-option
                :label="$t('couponGive.expired')"
                value="2"
              ></el-option>
              <el-option
                :label="$t('couponGive.repealed')"
                value="3"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-button
            size="small"
            type="primary"
            @click="onSubmit"
          >{{$t('couponGive.filter')}}</el-button>
          <el-button size="small">{{$t('couponGive.export')}}</el-button>
        </el-form>
      </div>
    </wrapper>
    <!-- 表格数据 -->
    <wrapper>
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
            label="用户昵称"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="mobile"
            label="手机号码"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="couponName"
            label="优惠券名称"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="accessMode"
            label="获取方式"
            align="center"
            :formatter="accessModeName"
          ></el-table-column>
          <el-table-column
            prop="isUsed"
            label="是否使用"
            align="center"
            :formatter="isUsedName"
          ></el-table-column>
          <el-table-column
            prop="orderSn"
            label="使用订单号"
            align="center"
          ></el-table-column>
          <el-table-column
            label="有效期"
            align="center"
          >
            <template slot-scope="scope">
              <span v-html="scope.row.validDate"></span>
            </template>
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="领取时间"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="usedTime"
            label="使用时间"
            align="center"
          ></el-table-column>
          <el-table-column
            prop=""
            label="操作"
            align="center"
          >
            <template slot-scope="scope">

              <el-button
                v-if="scope.row.isUsed === '未使用'"
                @click="stopCoupon(scope.row.id)"
                type="text"
              >废除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageParams"
          @pagination="loadData"
        />

      </div>
    </wrapper>
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
      isUsed: '-1',
      accessId: '',
      username: '',
      mobile: '',
      actId: '',
      tableData: [],
      pageParams: {
        currentPage: 1,
        pageRows: 20
      }
    }
  },
  methods: {
    // 加载数据
    loadData () {
      this.pageParams.accessId = this.accessId
      this.pageParams.actId = this.actId
      this.pageParams.mobile = this.mobile
      this.pageParams.username = this.username
      this.pageParams.isUsed = this.isUsed
      console.log('pageParams:', this.pageParams)
      receiveDetails(this.pageParams).then(res => {
        console.log('pageInfo:', res.content.dataList)
        this.handData(res.content.dataList)
        this.tableData = res.content.dataList
        this.pageParams = res.content.page
      })
    },
    // 筛选
    onSubmit () {
      this.pageParams.currentPage = 1
      this.loadData()
    },
    // 表格数据处理
    handData (data) {
      data.map((item, index) => {
        item.validDate = `${item.startTime}<br/>${this.$t('promoteList.to')}<br/>${item.endTime}`
      })
      this.tableData = data
    },
    // 获取方式 标识转文字
    accessModeName (row, column) {
      switch (row.accessMode) {
        case 0: row.accessMode = '发放'
          break
        case 1: row.accessMode = '领取'
          break
      }
      return row.accessMode
    },
    // 是否使用 标识转文字
    isUsedName (row, column) {
      switch (row.isUsed) {
        case 0: row.isUsed = '未使用'
          break
        case 1: row.isUsed = '已使用'
          break
        case 2: row.isUsed = '已使用'
          break
        case 3: row.isUsed = '已废除'
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
  height: 100%;
  .main {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 10px 20px;
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
  padding: 10px 20px 0 20px;
}
</style>
