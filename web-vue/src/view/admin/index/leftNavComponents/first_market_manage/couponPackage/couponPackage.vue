<template>
  <div class="content">
    <div class="main">
      <div class="navBox">
        <statusTab
          v-model="tabIndex"
          :activityName="activityName"
          :standard="true"
        />
        <el-button
          type="primary"
          @click="addCouponPackage()"
        >{{$t('couponPackage.addCouponPackage')}}</el-button>
      </div>
      <div class="table_box">
        <div class="filters">
          <div class="filters_item"><span>{{$t('marketCommon.actName')}}：</span>
            <el-input
              v-model="actName"
              :placeholder="$t('marketCommon.actNamePlaceholder')"
              size="small"
            ></el-input>
          </div>
          <div class="filters_item"><span>{{$t('couponPackage.packName')}}：</span>
            <el-input
              v-model="packName"
              :placeholder="$t('couponPackage.packNamePlaceholder')"
              size="small"
            ></el-input>
          </div>
          <div class="filters_item"><span>{{$t('couponPackage.accessMode')}}：</span>
            <el-select
              v-model="accessMode"
              size="small"
            >
              <el-option
                v-for="item in get_type_option"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <el-button
              @click="initDataList"
              type="primary"
              size="small"
            >{{$t('marketCommon.filter')}}</el-button>
          </div>
        </div>
        <el-table
          v-loading="loading"
          :data="tableData"
          style="width:100%;"
          border
          :header-cell-style="{
            'background-color':'#f5f5f5',
            'text-align':'center',
            'border':'none'
          }"
          :cell-style="{
            'text-align':'center'
          }"
        >
          <el-table-column
            prop="actName"
            :label="$t('marketCommon.actName')"
          ></el-table-column>
          <el-table-column
            prop="packName"
            :label="$t('couponPackage.packName')"
          ></el-table-column>
          <el-table-column
            prop="vaildDate"
            :label="$t('marketCommon.validDate')"
            width="160"
          >
            <template slot-scope="scope">
              <span v-html="scope.row.vaildDate"></span>
            </template>
          </el-table-column>
          <el-table-column
            prop="voucherKindsNumber"
            :label="$t('couponPackage.voucherKindsNumber')"
          ></el-table-column>
          <el-table-column
            prop="voucherNumber"
            :label="$t('couponPackage.voucherNumber')"
          ></el-table-column>
          <el-table-column
            prop="totalAmount"
            :label="$t('couponPackage.totalAmount')"
          ></el-table-column>
          <el-table-column
            prop="accessMode"
            :label="$t('couponPackage.accessMode')"
          ></el-table-column>
          <el-table-column
            prop="accessCost"
            :label="$t('couponPackage.accessCost')"
            width="80"
          ></el-table-column>
          <el-table-column
            prop="issueAmount"
            :label="$t('couponPackage.issueAmount')"
          ></el-table-column>
          <el-table-column
            prop="statusName"
            :label="$t('marketCommon.activityStatus')"
            width="80"
          ></el-table-column>
          <el-table-column
            :label="$t('marketCommon.operate')"
            width="130"
          >
            <template slot-scope="scope">
              <div class="operation">
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="$t('marketCommon.edit')"
                  placement="top"
                  v-if="scope.row.status === 1"
                >
                  <i
                    class="el-icon-edit-outline"
                    @click="edit(scope.row.id)"
                  ></i>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="$t('marketCommon.delete')"
                  placement="top"
                  v-else
                >
                  <i
                    @click="delCouponPackage(scope.row.id)"
                    class="el-icon-delete"
                  ></i>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="$t('marketCommon.share')"
                  placement="top"
                >
                  <i
                    class="el-icon-share"
                    @click="shareCouponPackage(scope.row.id)"
                  ></i>
                </el-tooltip>
                <el-tooltip
                  v-if="scope.row.status === 1"
                  class="item"
                  effect="dark"
                  :content="$t('marketCommon.disable')"
                  placement="top"
                >
                  <i
                    @click="puaseCouponPackage(scope.row.id)"
                    class="el-icon-remove-outline"
                  ></i>
                </el-tooltip>
                <el-tooltip
                  v-else
                  class="item"
                  effect="dark"
                  :content="$t('marketCommon.enabled')"
                  placement="top"
                >
                  <i
                    @click="enableCouponPackage(scope.row.id)"
                    class="el-icon-check"
                  ></i>
                </el-tooltip>

                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="$t('couponPackage.checkOrders')"
                  placement="top"
                >
                  <i
                    class="el-icon-s-order"
                    @click="orderDetails(scope.row.id)"
                  ></i>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="$t('couponPackage.receiveDetails')"
                  placement="top"
                >
                  <i
                    class="el-icon-document"
                    @click="receiveDetails(scope.row.id)"
                  ></i>
                </el-tooltip>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { couponPackageList, updateCouponPackage, deleteCouponPackage, getCouponPackShareCode } from '@/api/admin/marketManage/couponPackage.js'
import statusTab from '@/components/admin/marketManage/status/statusTab'
// 引入分页
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination, statusTab },
  data () {
    return {
      loading: false,
      activityName: '',
      // 默认显示进行中的活动
      tabIndex: 1,

      pageParams: {},

      actName: '',
      packName: '',
      accessMode: -1,
      get_type_option: [],
      tableData: [],
      totalRows: null,

      // 表格原始数据
      originalData: []
    }
  },
  methods: {
    initDataList (page) {
      this.loading = true
      this.pageParams.state = parseInt(this.tabIndex)
      this.pageParams.accessMode = parseInt(this.accessMode)
      this.pageParams.actName = this.actName
      this.pageParams.packName = this.packName

      couponPackageList(this.pageParams).then((res) => {
        if (res.error === 0) {
          this.originalData = res.content.dataList
          let originalData = JSON.parse(JSON.stringify(this.originalData))
          this.handleData(originalData)
          this.pageParams = res.content.page
          this.loading = false
        }
      })
    },

    // 表格数据处理/渲染
    handleData (data) {
      data.map((item, index) => {
        switch (item.accessMode) {
          case 0:
            item.accessMode = this.$t('couponPackage.accessModeCash')
            break
          case 1:
            item.accessMode = this.$t('couponPackage.accessModeTntegral')
            break
          case 2:
            item.accessMode = this.$t('couponPackage.accessModeFree')
            break
        }
        item.vaildDate = `${item.startTime}<br/>${this.$t('marketCommon.to')}<br/>${item.endTime}`
        item.statusName = this.getActStatusString(item.currentState)
      })
      this.tableData = data
    },

    // 停用优惠券礼包活动
    puaseCouponPackage (id) {
      let param = {
        'id': id,
        'status': 0
      }
      this.$confirm(this.$t('marketCommon.actDisableConfirmTip'), this.$t('marketCommon.tip'), {
        confirmButtonText: this.$t('marketCommon.ok'),
        cancelButtonText: this.$t('marketCommon.cancel'),
        type: 'warning'
      }).then(() => {
        updateCouponPackage(param).then((res) => {
          if (res.error === 0) {
            this.$message({
              type: 'success',
              message: this.$t('marketCommon.successfulOperation')
            })
            this.initDataList()
          }
        })
      })
    },

    // 启用优惠券礼包活动
    enableCouponPackage (id) {
      let param = {
        'id': id,
        'status': 1
      }
      this.$confirm(this.$t('marketCommon.actEnabledConfirmTip'), this.$t('marketCommon.tip'), {
        confirmButtonText: this.$t('marketCommon.ok'),
        cancelButtonText: this.$t('marketCommon.cancel'),
        type: 'warning'
      }).then(() => {
        updateCouponPackage(param).then((res) => {
          if (res.error === 0) {
            this.$message({
              type: 'success',
              message: this.$t('marketCommon.successfulOperation')
            })
            this.initDataList()
          }
        })
      })
    },

    // 删除优惠券礼包活动
    delCouponPackage (id) {
      let param = {
        'id': id
      }
      this.$confirm(this.$t('marketCommon.actDeleteConfirmTip'), this.$t('marketCommon.tip'), {
        confirmButtonText: this.$t('marketCommon.ok'),
        cancelButtonText: this.$t('marketCommon.cancel'),
        type: 'warning'
      }).then(() => {
        deleteCouponPackage(param).then((res) => {
          if (res.error === 0) {
            this.$message({
              type: 'success',
              message: this.$t('marketCommon.successfulOperation')
            })
            this.initDataList()
          }
        })
      })
    },
    // 领取明细
    receiveDetails (id) {
      this.$router.push({
        path: '/admin/home/main/couponPackage/receiveDetails',
        query: {
          id: id
        }
      })
    },
    // 查看订单
    orderDetails (id) {
      this.$router.push({
        path: '/admin/home/main/couponPackage/orderDetails',
        query: {
          id: id
        }
      })
    },
    // 取活动分享二维码
    shareCouponPackage (id) {
      getCouponPackShareCode(id).then((res) => {
        console.log(res)
      })
    },

    edit (id) {
      this.$router.push({
        path: '/admin/home/main/couponPackage/add',
        query: {
          id: id
        }
      })
    },
    addCouponPackage () {
      this.$router.push({
        name: 'coupon_Package_add'
      })
    }
  },
  watch: {
    'tabIndex' (n, o) {
      this.initDataList()
    },

    // data内变量国际化
    lang () {
      this.get_type_option = [
        { value: -1, label: this.$t('couponPackage.accessModeAll') },
        { value: 0, label: this.$t('couponPackage.accessModeCash') },
        { value: 1, label: this.$t('couponPackage.accessModeTntegral') },
        { value: 2, label: this.$t('couponPackage.accessModeFree') }
      ]
      this.activityName = this.$t('couponPackage.couponPackage')

      // 重新渲染表格数据
      let originalData = JSON.parse(JSON.stringify(this.originalData))
      this.handleData(originalData)
    }
  },
  mounted () {
    this.langDefault()
    this.initDataList()
  }

}
</script>

<style lang="scss" scoped>
.main {
  padding: 10px;
  .navBox {
    background-color: #fff;
    padding: 0 15px 14px;
    margin-bottom: 10px;
  }
  .table_box {
    background-color: #fff;
    padding: 15px;
    .filters {
      display: flex;
      line-height: 32px;
      margin-left: -15px;
      margin-bottom: 10px;
      .filters_item {
        max-width: 250px;
        display: flex;
        margin-left: 15px;
        > span {
          min-width: 80px;
          font-size: 14px;
        }
      }
    }
    .operation {
      display: flex;
      flex-wrap: wrap;
      margin-left: -5px;
      > .item {
        font-size: 22px;
        color: #66b1ff;
        cursor: pointer;
        margin-left: 5px;
      }
    }
    .tapOneblock {
      display: flex;
      justify-content: flex-end;
      margin-top: 10px;
      > span {
        height: 32px;
        line-height: 32px;
      }
    }
  }
}
</style>
