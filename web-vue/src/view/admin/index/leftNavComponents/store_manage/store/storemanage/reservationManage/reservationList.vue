<template>
  <!--预约管理-->
  <div>
    <div class="technician_list_page">
      <!--条件搜索-->
      <div class="list_info">
        <label style="font-size: 14px;">
          预约手机号
          <el-input
            size="small"
            class="filter_input"
            :placeholder="$t('technicianList.technicianName')"
            v-model="queryParams.mobile"
          ></el-input>
        </label>
        <label style="font-size: 14px;">
          预约起始时间
          <el-date-picker
            v-model="queryParams.serviceDateStart"
            type="date"
            size="small"
            placeholder="选择日期">
          </el-date-picker>
        </label>
        <label style="font-size: 14px;">
          预约结束时间
          <el-date-picker
            v-model="queryParams.serviceDateEnd"
            type="date"
            size="small"
            placeholder="选择日期">
          </el-date-picker>
        </label>
        <label style="font-size: 14px;">
          技师
          <el-input
            size="small"
            class="filter_input"
            placeholder="技师"
            v-model="queryParams.technicianName"
          ></el-input>
        </label>
        <el-input
          type="tel"
          placeholder="请输入预约人姓名服务名查询"
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
        <el-button
          type="primary"
          size="small"
          @click="showMess3"
        >新建预约活动</el-button>
      </div>
      <div>
        <el-tabs
          v-model="tabSwitch"
          @tab-click="handleClick"
        >
          <el-tab-pane
            v-for="(item) in tabInfo"
            :key="item.name"
            :label="item.title"
            :name="item.name"
          >
          </el-tab-pane>
        </el-tabs>
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
              label="预约人"
              prop="subscriber"
            >
            </el-table-column>
            <el-table-column
              label="服务名称"
              prop="serviceName"
            >
            </el-table-column>
            <el-table-column
              label="预约手机号"
              prop="mobile"
            ></el-table-column>
            <el-table-column
              label="预约到店时间"
              prop="serviceDate"
            ></el-table-column>
            <el-table-column
              label="技师"
              prop="technicianName"
            ></el-table-column>
            <el-table-column
              label="预约支付金额"
              prop="serviceSubsist"
            ></el-table-column>
            <el-table-column
              label="留言"
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
                      @click="click2Detail(row.orderSn)"
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
                      @click="showMess2(row.orderId, row.orderSn, row.userId)"
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
                  v-model.number="chargeParam.balance">
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
    <!-- 后台添加预约弹窗 -->
    <el-dialog
      title="新增预约"
      :visible.sync="showReservation"
      :close-on-click-modal='false'
      width=50%
    >
      <div class="table_list">
        <div>
          * 预约人：
          <el-input
            style="width: 40%"
            placeholder="选择会员"
            v-model="userRowData.userName">
          </el-input>
          <el-button
            type="primary"
            size="small"
            @click="hanldeModifyPerson()"
          >选择会员</el-button>
        </div>
        <div>
          * 手机号：
          <el-input
            style="width: 40%"
            placeholder="请填写预约人手机号"
            v-model="reservation.mobile">
          </el-input>
        </div>
        <div>
          * 预约到店时间：
          <el-date-picker
            v-model="dateTime"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="datetime"
            placeholder="选择到店日期时间"
            align="right"
            :picker-options="pickerOptions">
          </el-date-picker>
        </div>
        <div>
          * 预约服务：
          <template>
            <el-select v-model="reservation.serviceId" clearable placeholder="请选择服务"
                       @change="changeEvent()">
              <el-option
                v-for="item in reservationService"
                :key="item.id"
                :label="item.serviceName"
                :value="item.id">
              </el-option>
            </el-select>
          </template>
        </div>
        <div>
          * 预约技师：
          <template>
            <el-select v-model="reservation.technicianId" clearable placeholder="请选择技师">
              <el-option
                v-for="item in reservationTech"
                :key="item.id"
                :label="item.technicianName"
                :value="item.id">
              </el-option>
            </el-select>
          </template>
        </div>
        <div>
          备注：
          <el-input
            style="width: 40%"
            placeholder="备注不超过200字"
            v-model="reservation.adminMessage">
          </el-input>
        </div>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          size="small"
          @click="add"
        >{{$t('tradeConfiguration.save')}}</el-button>
        <el-button
          size="small"
          @click="closeWin3"
        >{{$t('tradeConfiguration.cancel')}}</el-button>
      </span>
    </el-dialog>
    <!--选择会员弹窗组件-->
    <ChooseUser
      :dialogVisible.sync="modifypersonDialogVisible"
      @rowData="dealRowData"
    />
  </div>
</template>

<script>
import { getList, detail, addMessage, add, charge, cancel, techList } from '@/api/admin/storeManage/storemanage/reservationManage'
import { getAllService } from '@/api/admin/storeManage/storemanage/serviceManage'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: {
    pagination,
    ChooseUser: () => import('@/components/admin/chooseUser')
  },
  data () {
    return {
      userRowData: {},
      // 日期时间
      pickerOptions: {
        shortcuts: [{
          text: '今天',
          onClick (picker) {
            picker.$emit('pick', new Date())
          }
        }, {
          text: '昨天',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24)
            picker.$emit('pick', date)
          }
        }, {
          text: '一周前',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', date)
          }
        }]
      },
      dateTime: '',
      // 门店服务下拉
      reservationService: [{
        serviceId: 3,
        serviceName: '服务3'
      },
      {
        serviceId: 2,
        serviceName: '服务2'
      }],
      // 门店技师下拉
      reservationTech: [{
        technicianId: 3,
        technicianName: '服务'
      }],
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
      // 新增预约服务
      showReservation: false,
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
        orderId: 0,
        userId: 0,
        verifyCode: '',
        verifyPay: 0,
        cardId: null,
        cardNo: null,
        reduce: null,
        balance: null,
        reason: '',
        account: null,
        countDis: null
      },
      // 新建预约入参
      reservation: {
        storeId: 0,
        userId: 0,
        subscriber: '',
        mobile: '',
        serviceId: 0,
        technicianId: 0,
        technicianName: '',
        serviceDate: '',
        servicePeriod: '',
        adminMessage: ''
      },
      tableData: [],
      pageParams: {
        currentPage: 0,
        pageRows: 10
      },
      tabSwitch: '-1',
      tabInfo: [{
        title: '所有预约',
        name: '-1'
      }, {
        title: '待支付',
        name: '0'
      }, {
        title: '带服务',
        name: '1'
      }, {
        title: '已取消',
        name: '2'
      }, {
        title: '已完成',
        name: '3'
      }],
      storeId: 0,
      serviceId: 0,
      modifypersonDialogVisible: false
    }
  },
  created () {
    this.queryParams.storeId = this.$route.query.id
    this.storeId = this.$route.query.id
    this.langDefault()
    this.initDataList()
    this.getStoreService()
    // this.getTechList()
  },
  methods: {
    // 服务下拉
    getStoreService () {
      let obj = {
        storeId: this.storeId
      }
      getAllService(obj).then(res => {
        if (res.error === 0) {
          this.reservationService = res.content
        }
      })
    },
    changeEvent () {
      this.serviceId = this.reservation.serviceId
      this.getTechList()
    },
    // 技师下拉
    getTechList () {
      let obj = {
        storeId: this.storeId,
        serviceId: this.serviceId
      }
      techList(obj).then(res => {
        if (res.error === 0) {
          this.reservationTech = res.content
        }
      })
    },
    // 可用会员卡下拉
    getMemberCardList () {
      let obj = {
        userId: 0,
        storeId: this.storeId,
        serviceId: this.serviceId
      }
      techList(obj).then(res => {
        if (res.error === 0) {
          this.reservationTech = res.content
        }
      })
    },
    // 状态标签页切换
    handleClick () {
      this.queryParams.orderStatus = this.tabSwitch
      // 切换状态重新加载列表
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
      }).catch(() => {
        this.$message.error('操作失败')
      })
    },
    // 预约详情页跳转
    click2Detail (orderSn) {
      this.$router.push({
        name: 'store_storemanage_reservation_detail',
        params: {
          orderSn: orderSn,
          flag: true
        }
      })
    },
    // 添加预约弹窗-点击触发弹窗
    showMess3 (orderSn) {
      this.orderSn = orderSn
      this.showReservation = true
    },
    // 关闭添加预约弹窗
    closeWin3 () {
      this.showReservation = false
    },
    // 添加预约
    add () {
      this.reservation.technicianName = this.reservationTech.find((item) => {
        return item.id === this.reservation.technicianId
      }).technicianName
      this.reservation.serviceDate = this.dateTime.split(' ')[0]
      this.reservation.servicePeriod = this.dateTime.split(' ')[1]
      this.reservation.subscriber = this.userRowData.userName
      this.reservation.userId = this.userRowData.userId
      this.reservation.storeId = this.storeId
      add(this.reservation).then(res => {
        if (res.error === 0) {
          this.$message.success('添加成功')
          this.initDataList()
          this.showReservation = false
        }
        this.$message.error('添加失败')
        this.showReservation = false
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
    showMess2 (orderId, orderSn, userId) {
      this.chargeParam.orderId = orderId
      this.chargeParam.orderSn = orderSn
      this.chargeParam.userId = parseInt(userId)
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
    },
    // 吊起选择会员组件
    hanldeModifyPerson () {
      this.modifypersonDialogVisible = true
    },
    // 会员组件 返回数据处理
    dealRowData (data) {
      this.userRowData = data
    }
  }
}
</script>

<style lang="scss" scoped>
  .table_list {
    position: relative;
    .table_footer {
      background: #666;
    }
  }
  .content {
    margin-top: 10px;
  }
  .la
  .modifypersonDivTop .el-input__inner {
    width: 140px !important;
  }
  .modifypersonDivTop,
  .modifypersonDivTop > div {
    display: flex;
  }
  .modifypersonDivTop > div > span {
    line-height: 32px;
    height: 32px;
    display: block;
    width: 56px;
  }
  .baseInfo .el-dialog__body {
    padding-bottom: 0 !important;
  }
  .baseInfo .el-dialog__footer {
    border-top: 1px solid #eee;
  }
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
