<template>
  <!--预约管理-->
  <div>
    <div class="technician_list_page">
      <!--条件搜索-->
      <div class="list_info">
        <label style="font-size: 14px;">
          预约手机号：
          <el-input
            size="small"
            class="filter_input"
            :placeholder="$t('technicianList.technicianName')"
            v-model="queryParams.mobile"
          ></el-input>
        </label>
        <label style="font-size: 14px;">
          预约起始时间：
          <el-input
            size="small"
            class="filter_input"
            :placeholder="$t('technicianList.technicianName')"
            v-model="queryParams.serviceDateStart"
          ></el-input>
        </label>
        <label style="font-size: 14px;">
          预约结束时间：
          <el-input
            size="small"
            class="filter_input"
            :placeholder="$t('technicianList.technicianName')"
            v-model="queryParams.serviceDateEnd"
          ></el-input>
        </label>
        <label style="font-size: 14px;">
          技师：
          <el-input
            size="small"
            class="filter_input"
            :placeholder="技师"
            v-model="queryParams.technicianName"
          ></el-input>
        </label>
        <el-input
          type="tel"
          :placeholder="请输入预约人姓名服务名查询"
          style="width: 170px;"
          size="small"
          v-model="queryParams.keywords"
        >
        </el-input>
        <el-button
          type="primary"
          size="small"
          @click="initDataList"
        >{{$t('technicianList.inquire')}}</el-button>
      </div>
      <!--列表展示-->
      <div class="list_table">
        <el-table
          ref="technicianTable"
          :data="tableData"
          class="tableClass"
          max-height="500"
          border
          :header-cell-style="{
            'background-color':'#f5f5f5',
            'border':'none'
          }"
        >
          <el-table-column
            :label="预约人"
            prop="subscriber"
          >
          </el-table-column>
          <el-table-column
            :label="服务名称"
            prop="serviceName"
          >
          </el-table-column>
          <el-table-column
            :label="预约手机号"
            prop="mobile"
          ></el-table-column>
          <el-table-column
            :label="预约到店时间"
            prop="serviceDate"
          ></el-table-column>
          <el-table-column
            :label="技师"
            prop="technicianName"
          ></el-table-column>
          <el-table-column
            :label="预约支付金额"
            prop="serviceSubsist"
          ></el-table-column>
          <el-table-column
            :label="留言"
            prop="addMessage"
          ></el-table-column>
          <el-table-column
            label="操作"
            prop="operate"
            align="center"
          >
            <template slot-scope="{ row }">
              <div style="margin-top:10px;">
                <el-tooltip :content="$t('technicianList.shiftManagement')">
                  <span
                    class="iconSpan"
                    @click="showMess(row.orderSn)"
                  >添加备注 </span>
                </el-tooltip>
                <el-tooltip :content="$t('technicianList.edit')">
                  <span
                    class="iconSpan"
                    @click="edit('edit', row)"
                  >查看详情</span>
                </el-tooltip>
                <el-tooltip :content="$t('technicianList.edit')">
                  <span
                    class="iconSpan"
                    @click="edit('edit', row)"
                  >查看评价</span>
                </el-tooltip>
                <el-tooltip :content="$t('technicianList.edit')">
                  <span
                    class="iconSpan"
                    @click="showMess1(row.orderSn)"
                  >取消</span>
                </el-tooltip>
                <el-tooltip :content="$t('technicianList.edit')">
                  <span
                    class="iconSpan"
                    @click="showMess2(row.orderSn)"
                  >核销</span>
                </el-tooltip>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <div class="table-page">
          <pagination
            :page-params.sync="pageParams"
            @pagination="initDataList"
          ></pagination>
        </div>
      </div>
    </div>
    <!-- 添加备注弹窗 -->
    <el-dialog
      title="添加备注"
      :visible.sync="showMessage"
      :close-on-click-modal='false'
      width=50%
    >
      <div class="table_list">
        <el-input
          type="textarea"
          :rows="2"
          placeholder="请输入备注"
          v-model="adminMessage">
        </el-input>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          size="small"
          @click="addMessage"
        >{{$t('tradeConfiguration.save')}}</el-button>
        <el-button
          size="small"
          @click="closeWin"
        >{{$t('tradeConfiguration.cancel')}}</el-button>
      </span>
    </el-dialog>
    <!-- 取消弹窗 -->
    <el-dialog
      title="取消预约"
      :visible.sync="showCancel"
      :close-on-click-modal='false'
      width=50%
    >
      <div class="table_list">
        <el-input
          type="textarea"
          :rows="2"
          placeholder="请输入取消原因"
          v-model="cancelReason">
        </el-input>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          size="small"
          @click="cancel"
        >{{$t('tradeConfiguration.save')}}</el-button>
        <el-button
          size="small"
          @click="closeWin1"
        >{{$t('tradeConfiguration.cancel')}}</el-button>
      </span>
    </el-dialog>
    <!-- 核销弹窗 -->
    <el-dialog
      title="核销预约"
      :visible.sync="showCharge"
      :close-on-click-modal='false'
      width=50%
    >
      <div class="table_list">
        <div>
          请输入核销码：
          <el-input
            style="width: 40%"
            placeholder="请输入核销码"
            v-model="chargeParam.verifyCode">
          </el-input>
        </div>
        <br>
        <div>
          <el-row>
            <el-col>请选择核销方式：</el-col>
          </el-row>
          <template>
            <el-radio-group v-model="chargeParam.verifyPay">
              <div>
              <el-radio :label="0">门店买单</el-radio>
              </div>
              <div>
              <el-radio :label="1">会员卡</el-radio>
                <el-input
                  v-if="chargeParam.verifyPay === 1"
                  style="width: 30%"
                  size="small"
                  placeholder="可用会员卡下拉列表"
                  v-model="chargeParam.cardId">
                </el-input>
                <el-input
                  v-if="chargeParam.verifyPay === 1"
                  style="width: 30%"
                  size="small"
                  placeholder="请输入金额或次数"
                  v-model="chargeParam.reduce">
                </el-input>
                <el-input
                  v-if="chargeParam.verifyPay === 1"
                  style="width: 30%"
                  size="small"
                  placeholder="请输入扣除原因"
                  v-model="chargeParam.reason">
                </el-input>
              </div>
              <div>
              <el-radio :label="2">账户余额</el-radio>
                <el-input
                  v-if="chargeParam.verifyPay === 2"
                  style="width: 30%"
                  size="small"
                  placeholder="99999999999999"
                  v-model="chargeParam.balance">
                </el-input>
                <el-input
                  v-if="chargeParam.verifyPay === 2"
                  style="width: 30%"
                  size="small"
                  placeholder="请输入扣除原因"
                  v-model="chargeParam.reason">
                </el-input>
              </div>
            </el-radio-group>
          </template>
        </div>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          size="small"
          @click="charge"
        >{{$t('tradeConfiguration.save')}}</el-button>
        <el-button
          size="small"
          @click="closeWin2"
        >{{$t('tradeConfiguration.cancel')}}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getList, detail, addMessage, add, charge, cancel } from '@/api/admin/storeManage/storemanage/reservationManage'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination },
  data () {
    return {
      // 备注
      adminMessage: '',
      orderSn: '',
      showMessage: false,
      // 取消预约原因
      cancelReason: '',
      showCancel: false,
      // 核销码
      showCharge: false,
      // 核销支付方式 0门店买单 1会员卡 2余额
      verifyPay: '',
      // 列表请求入参
      queryParams: {
        storeId: '',
        orderStatus: -1,
        mobile: '',
        serviceDateStart: '',
        serviceDateEnd: '',
        technicianName: '',
        // 预约人姓名或服务名
        keywords: '',
        currentPage: 0,
        pageRows: 20
      },
      // 统计数据
      countingData: {
        all: 0,
        waitPay: 0,
        waitService: 0,
        cancelled: 0,
        finished: 0
      },
      // 核销入参
      chargeParam: {
        orderSn: '',
        orderId: '',
        userId: '',
        verifyCode: '',
        verifyPay: '',
        cardId: '',
        cardNo: '',
        reduce: '',
        balance: '',
        reason: '',
        account: '',
        countDis: ''
      },
      tableData: [],
      pageParams: {}
    }
  },
  created () {
    this.queryParams.storeId = this.$route.query.id
    this.langDefault()
    this.initDataList()
  },
  methods: {
    // 添加预约
    add (message) {
      add().then(res => {
        if (res.error === 0) {

        }
      })
    },
    // 添加留言弹窗-点击触发弹窗
    showMess (orderSn) {
      this.orderSn = orderSn
      this.showMessage = true
    },
    // 关闭留言弹窗
    closeWin () {
      this.showMessage = false
    },
    // 添加留言
    addMessage () {
      let obj = {
        'orderSn': this.orderSn,
        'adminMessage': this.adminMessage
      }
      addMessage(obj).then(res => {
        if (res.error === 0) {
          this.$message.success('添加成功')
        }
        this.$message.error('添加失败')
        this.showMessage = false
      })
    },
    // 查看详情
    toDetail (param) {
      detail().then(res => {
        if (res.error === 0) {

        }
      })
    },
    // 查看评价
    evaluation () {

    },
    // 取消弹窗-点击触发弹窗
    showMess1 (orderSn) {
      this.orderSn = orderSn
      this.showCancel = true
    },
    // 关闭取消弹窗
    closeWin1 () {
      this.showCancel = false
    },
    // 取消
    cancel () {
      let obj = {
        'orderSn': this.orderSn,
        'orderId': 0,
        'cancelReason': this.cancelReason
      }
      cancel(obj).then(res => {
        if (res.error === 0) {
          this.$message.success('取消成功')
        }
        this.$message.error('取消失败')
        this.showCancel = false
      })
    },
    // 核销弹窗-点击触发弹窗
    showMess2 (orderSn) {
      this.orderSn = orderSn
      this.showCharge = true
    },
    // 关闭核销弹窗
    closeWin2 () {
      this.showCharge = false
    },
    // 核销
    charge () {
      charge(this.chargeParam).then(res => {
        if (res.error === 0) {
          this.$message.success('核销成功')
        }
        this.$message.error('核销失败')
        this.showCancel = false
      })
    },
    edit (operate, row) {
      switch (operate) {
        case 'scheduling':
          this.$router.push({
            name: 'schedule_setting',
            query: {
              id: this.queryParams.storeId,
              businessHours: this.$route.query.businessHours,
              businessType: this.$route.query.businessType,
              technicianId: row.id,
              technicianName: row.technicianName
            }
          })
          break
        case 'edit':
          this.$router.push({
            name: 'store_storemanage_technician_add',
            query: {
              id: this.queryParams.storeId,
              technicianId: row.id,
              businessHours: this.$route.query.businessHours,
              businessType: this.$route.query.businessType
            }
          })
          break
      }
    },
    initDataList () {
      let params = Object.assign({}, this.queryParams, this.pageParams)
      getList(params).then(res => {
        if (res.error === 0) {
          this.tableData = [...res.content.pageList.dataList]
          this.countingData = [...res.content.countingData]
          this.pageParams = Object.assign({}, res.content.pageList.page)
          // 合并时期时间段
          this.tableData.map((item, index) => {
            item.serviceDate = item.serviceDate + ' ' + item.servicePeriod
          })
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .technician_list_page {
    margin: 0 25px;
    .list_info {
      padding-bottom: 10px;
      .filter_input {
        width: 170px;
      }
      .technician_list_img {
        display: inline-block;
        width: 60px;
        height: 60px;
      }
    }
    .list_table {
      .iconSpan {
        color: #5a8bff;
        text-decoration: none;
        cursor: pointer !important;
      }
    }
  }
</style>
