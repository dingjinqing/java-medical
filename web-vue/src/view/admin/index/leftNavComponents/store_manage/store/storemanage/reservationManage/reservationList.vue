<template>
  <!--预约管理-->
  <div>
    <div class="technician_list_page">
      <!--条件搜索-->
      <div class="list_info">
        <label style="font-size: 14px;">
          {{$t('reservationManage.mobile')}}
          <el-input
            size="small"
            class="filter_input"
            :placeholder="$t('reservationManage.mobile')"
            v-model="queryParams.mobile"
          ></el-input>
        </label>
        <label style="font-size: 14px;">
          {{$t('reservationManage.reservationStartTime')}}
          <el-date-picker
            v-model="queryParams.serviceDateStart"
            type="date"
            size="small"
            :placeholder="$t('reservationManage.reservationStartTime')"
          >
          </el-date-picker>
        </label>
        <label style="font-size: 14px;">
          {{$t('reservationManage.reservationEndTime')}}
          <el-date-picker
            v-model="queryParams.serviceDateEnd"
            type="date"
            size="small"
            :placeholder="$t('reservationManage.reservationEndTime')"
          >
          </el-date-picker>
        </label>
        <label style="font-size: 14px;">
          {{$t('reservationManage.technician')}}
          <el-input
            size="small"
            class="filter_input"
            :placeholder="$t('reservationManage.technician')"
            v-model="queryParams.technicianName"
          ></el-input>
        </label>
        <el-input
          type="tel"
          :placeholder="$t('reservationManage.keywords')"
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
        >{{$t('reservationManage.newActive')}}</el-button>
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
            <span slot="label">
              {{item.title}}<span
                class="wait_num"
                v-if="item.name === '-1'"
              >{{countingData.all}}</span>
              <span
                class="wait_num"
                v-if="item.name === '0'"
              >{{countingData.waitPay}}</span>
              <span
                class="wait_num"
                v-if="item.name === '1'"
              >{{countingData.waitService}}</span>
              <span
                class="wait_num"
                v-if="item.name === '2'"
              >{{countingData.canceled}}</span>
              <span
                class="wait_num"
                v-if="item.name === '3'"
              >{{countingData.finished}}</span>
            </span>
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
              :label="$t('reservationManage.subscriber')"
              prop="subscriber"
            >
            </el-table-column>
            <el-table-column
              :label="$t('reservationManage.serviceName')"
              prop="serviceName"
            >
            </el-table-column>
            <el-table-column
              :label="$t('reservationManage.mobile')"
              prop="mobile"
            ></el-table-column>
            <el-table-column
              :label="$t('reservationManage.serviceDate')"
              prop="serviceDate"
            ></el-table-column>
            <el-table-column
              :label="$t('reservationManage.technician')"
              prop="technicianName"
            ></el-table-column>
            <el-table-column
              :label="$t('reservationManage.serviceSubsist')"
              prop="serviceSubsist"
            ></el-table-column>
            <el-table-column
              :label="$t('reservationManage.message')"
              prop="addMessage"
            ></el-table-column>
            <el-table-column
              :label="$t('reservationManage.operate')"
              prop="operate"
              align="center"
            >
              <template slot-scope="{ row }">
                <div style="margin-top:10px;">
                  <el-tooltip :content="$t('reservationManage.addMessage')">
                    <span
                      class="iconSpan"
                      @click="showMess(row.orderSn)"
                    >{{$t('reservationManage.addMessage')}} </span>
                  </el-tooltip>
                  <el-tooltip :content="$t('reservationManage.seeDetails')">
                    <span
                      class="iconSpan"
                      @click="click2Detail(row.orderSn)"
                    >{{$t('reservationManage.seeDetails')}}</span>
                  </el-tooltip>
                  <el-tooltip :content="$t('reservationManage.seeEvluation')">
                    <span
                      class="iconSpan"
                      @click="click2Evluation()"
                    >{{$t('reservationManage.seeEvluation')}}</span>
                  </el-tooltip>
                  <el-tooltip
                    :content="$t('reservationManage.cancel')"
                    v-if="row.orderStatus == 1"
                  >
                    <span
                      class="iconSpan"
                      @click="showMess1(row.orderId, row.orderSn)"
                    >{{$t('reservationManage.cancel')}}</span>
                  </el-tooltip>
                  <el-tooltip
                    :content="$t('reservationManage.charge')"
                    v-if="row.orderStatus == 1"
                  >
                    <span
                      class="iconSpan"
                      @click="showMess2(row.orderId, row.orderSn, row.userId)"
                    >{{$t('reservationManage.charge')}}</span>
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
      :title="$t('reservationManage.addMessage')"
      :visible.sync="showMessage"
      :close-on-click-modal='false'
      width=50%
    >
      <div class="table_list">
        <el-input
          type="textarea"
          :rows="2"
          :placeholder="$t('reservationManage.message')"
          v-model="adminMessage"
        >
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
      :title="$t('reservationManage.cancelReservation')"
      :visible.sync="showCancel"
      :close-on-click-modal='false'
      width=50%
    >
      <div class="table_list">
        <el-input
          type="textarea"
          :rows="2"
          :placeholder="$t('reservationManage.cancelReason')"
          v-model="cancelReason"
        >
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
      :title="$t('reservationManage.charge')"
      :visible.sync="showCharge"
      :close-on-click-modal='false'
      width=40%
    >
      <div class="table_list">
        <div>
          {{$t('reservationManage.chargeCode')}}：
          <el-input
            style="width: 40%"
            :placeholder="$t('reservationManage.chargeCode')"
            v-model="chargeParam.verifyCode"
          >
          </el-input>
        </div>
        <br>
        <div style="margin-top: 20px">
          <el-row>
            <el-col>{{$t('reservationManage.chargeType')}}：</el-col>
          </el-row>
          <template>
            <el-radio-group v-model="chargeParam.verifyPay">
              <div style="margin-top: 20px">
                <el-radio :label="0">{{$t('reservationManage.storeBuy')}}</el-radio>
              </div>
              <div style="margin-top: 20px">
                <el-radio :label="1">{{$t('reservationManage.memberCard')}}</el-radio>
                <template v-if="chargeParam.verifyPay === 1">
                  <el-select
                    v-model="chargeParam.cardId"
                    clearable
                    :placeholder="$t('reservationManage.memberCard')"
                  >
                    <el-option
                      v-for="item in availableCard"
                      :key="item.cardId"
                      :label="item.cardName"
                      :value="item.cardId"
                    >
                    </el-option>
                  </el-select>
                </template>
                <el-input
                  v-if="chargeParam.verifyPay === 1"
                  style="width: 20%"
                  :placeholder="$t('reservationManage.reduceOrLimit')"
                  v-model="chargeParam.reduce"
                >
                </el-input>
                <el-input
                  v-if="chargeParam.verifyPay === 1"
                  style="width: 20%"
                  :placeholder="$t('reservationManage.season')"
                  v-model="chargeParam.reason"
                >
                </el-input>
              </div>
              <div style="margin-top: 20px">
                <el-radio :label="2">{{$t('reservationManage.balance')}}</el-radio>
                <el-input
                  v-if="chargeParam.verifyPay === 2"
                  style="width: 30%"
                  :placeholder="userAccount"
                  v-model.number="chargeParam.balance"
                >
                </el-input>
                <el-input
                  v-if="chargeParam.verifyPay === 2"
                  style="width: 30%"
                  :placeholder="$t('reservationManage.season')"
                  v-model="chargeParam.reason"
                >
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
      :title="$t('reservationManage.newActive')"
      :visible.sync="showReservation"
      :close-on-click-modal='false'
      width=30%
    >
      <div class="table_list">
        <div>
          <el-row
            :gutter="15"
            class="row_style"
          >
            <el-col :span="5">
              <span class="span_asterisk">*</span> {{$t('reservationManage.subscriber')}}：
            </el-col>
            <el-col :span="10">
              <el-input
                :placeholder="$t('reservationManage.chooseUser')"
                v-model="userRowData.userName"
              >
              </el-input>
              <el-button
                type="primary"
                @click="hanldeModifyPerson()"
              >{{$t('reservationManage.chooseUser')}}</el-button>
            </el-col>
          </el-row>
        </div>
        <div>
          <el-row
            :gutter="15"
            class="row_style"
          >
            <el-col :span="5">
              <span class="span_asterisk">*</span> {{$t('reservationManage.mobile')}}：
            </el-col>
            <el-col :span="10">
              <el-input
                :placeholder="$t('reservationManage.mobile')"
                v-model="reservation.mobile"
              >
              </el-input>
            </el-col>
          </el-row>
        </div>
        <div>
          <el-row
            :gutter="15"
            class="row_style"
          >
            <el-col :span="5">
              <span class="span_asterisk">*</span> {{$t('reservationManage.serviceDate')}}：
            </el-col>
            <el-col :span="10">
              <el-date-picker
                v-model="dateTime"
                value-format="yyyy-MM-dd HH:mm:ss"
                type="datetime"
                :placeholder="$t('reservationManage.serviceDate')"
                align="right"
                :picker-options="pickerOptions"
              >
              </el-date-picker>
            </el-col>
          </el-row>
        </div>
        <div>
          <el-row
            :gutter="15"
            class="row_style"
          >
            <el-col :span="5">
              <span class="span_asterisk">*</span> {{$t('reservationManage.serviceName')}}：
            </el-col>
            <el-col :span="10">
              <template>
                <el-select
                  v-model="reservation.serviceId"
                  clearable
                  :placeholder="$t('reservationManage.serviceName')"
                  @change="changeEvent()"
                >
                  <el-option
                    v-for="item in reservationService"
                    :key="item.id"
                    :label="item.serviceName"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
              </template>
            </el-col>
          </el-row>
        </div>
        <div>
          <el-row
            :gutter="15"
            class="row_style"
          >
            <el-col :span="5">
              <span class="span_asterisk"></span> {{$t('reservationManage.technician')}}：
            </el-col>
            <el-col :span="10">
              <template>
                <el-select
                  v-model="reservation.technicianId"
                  clearable
                  :placeholder="$t('reservationManage.technician')"
                >
                  <el-option
                    v-for="item in reservationTech"
                    :key="item.id"
                    :label="item.technicianName"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
              </template>
            </el-col>
          </el-row>
        </div>
        <div>
          <el-row
            :gutter="15"
            class="row_style"
          >
            <el-col :span="5">
              {{$t('reservationManage.message')}}：
            </el-col>
            <el-col :span="10">
              <el-input
                :placeholder="$t('reservationManage.messageLimit')"
                v-model="reservation.adminMessage"
              >
              </el-input>
            </el-col>
          </el-row>
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
import { getList, availableCard, getChargeAccount, addMessage, add, charge, cancel, techList } from '@/api/admin/storeManage/storemanage/reservationManage'
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
      reservationTech: [],
      // 被核销用户余额
      userAccount: 0.0,
      // 可用会员卡下拉
      availableCard: [],
      // 备注
      adminMessage: '',
      orderSn: '',
      orderId: 0,
      userId: 0,
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
        canceled: 0,
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
        reduce: '',
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
        title: this.$t('reservationManage.alReservation'),
        name: '-1'
      }, {
        title: this.$t('reservationManage.waitPay'),
        name: '0'
      }, {
        title: this.$t('reservationManage.waitService'),
        name: '1'
      }, {
        title: this.$t('reservationManage.canceled'),
        name: '2'
      }, {
        title: this.$t('reservationManage.finished'),
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
    // 被核销用户余额
    getChargeUserAccount (userId) {
      getChargeAccount(userId).then(res => {
        if (res.error === 0) {
          this.userAccount = res.content
        }
      })
    },
    // 可用会员卡下拉
    getMemberCardList () {
      let obj = {
        userId: this.userId,
        storeId: this.storeId
      }
      availableCard(obj).then(res => {
        if (res.error === 0) {
          this.availableCard = res.content
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
          this.countingData = res.content.countingData
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
    // 查看评价跳转
    click2Evluation () {
      this.$router.push({
        name: 'store_storemanage_comment',
        params: {
          flag: true
        }
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
      // 必填项校验
      if (this.userRowData === {}) {
        this.$message.info('必填项不可为空！')
      } else if (this.mobile === '') {
        this.$message.info('必填项不可为空！')
      } else if (this.dateTime === '') {
        this.$message.info('必填项不可为空！')
      } else if (this.reservation.serviceId === 0) {
        this.$message.info('必填项不可为空！')
      } else {
        console.log('技师列表：' + this.reservationTech)
        if (!this.reservationTech) {
          this.reservation.technicianName = this.reservationTech.find((item) => {
            return item.id === this.reservation.technicianId
          }).technicianName
        }
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
      }
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
    // 查看评价
    evaluation () {

    },
    // 取消弹窗-点击触发弹窗
    showMess1 (orderId, orderSn) {
      this.orderId = orderId
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
        'orderId': this.orderId,
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
      this.userId = parseInt(userId)
      this.getMemberCardList()
      this.showCharge = true
      this.getChargeUserAccount(this.userId)
    },
    // 关闭核销弹窗
    closeWin2 () {
      this.showCharge = false
    },
    // 核销
    charge () {
      switch (this.chargeParam.verifyPay) {
        case 0:
          break
        case 1:
          if (this.availableCard === null) {
            this.$message.error('无可用会员卡')
            break
          }
          this.chargeParam.cardNo = this.availableCard.find((item) => {
            return item.cardId === this.chargeParam.cardId
          }).cardNo
          break
        case 2:
          break
      }
      charge(this.chargeParam).then(res => {
        if (res.error === 0) {
          this.$message.success('核销成功')
          this.showCharge = false
          this.initDataList()
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
          this.countingData = res.content.countingData
          console.log('统计数据：' + JSON.stringify(this.countingData))
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
  margin-left: 20px;
  position: relative;
  .table_footer {
    background: #666;
  }
  .span_asterisk {
    color: #cc0000;
  }
  .span_text {
    text-align: right;
  }
  .row_style {
    margin-top: 10px;
  }
}
.content {
  margin-top: 10px;
}
.la .modifypersonDivTop .el-input__inner {
  width: 140px !important;
}
.modifypersonDivTop,
.modifypersonDivTop > div {
  display: flex;
}
.wait_num {
  position: relative;
  top: -7px;
  right: 0;
  border-radius: 10px;
  background: #ff9d0e;
  color: #fff;
  line-height: 1;
  font-size: 11px;
  text-align: center;
  padding: 2px 5px;
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
    margin-top: 20px;
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
