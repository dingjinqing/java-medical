<template>
  <div class="content">
      <div class="main">
        <div class="navBox">
            <el-row  :gutter="20">
              <el-col :span="5">{{$t('reservationManage.orderSn')}}：{{this.detailData.orderSn}}</el-col>
              <el-col :span="10"> {{$t('reservationManage.orderStatus')}}： {{this.detailData.orderStatusName}}</el-col>
            </el-row>
            <el-steps :active="stepActive" finish-status="success" simple style="margin-top: 20px; width: 50%">
              <el-step :title="$t('reservationManage.alreadyReservation')" ></el-step>
              <el-step :title="$t('reservationManage.orderComplete')" ></el-step>
            </el-steps>
          <div style="margin-top: 10px">
            <el-row>
              <el-col :span="8">
                <div class="boder_style">
                  <el-row  :gutter="20" class="row_style">
                    <el-col :span="10" style="font: 16px solid;">{{$t('reservationManage.orderInfo')}}</el-col>
                  </el-row>
                  <el-row  :gutter="20" class="row_style">
                    <el-col :span="12"  class="col_style"> {{$t('reservationManage.orderStatus')}}： {{this.detailData.orderStatusName}}</el-col>
                    <el-col :span="8" class="col_style">{{$t('reservationManage.orderAmount')}}： {{this.detailData.orderAmount}}</el-col>
                  </el-row>
                  <el-row  :gutter="20" class="row_style">
                    <el-col :span="12" class="col_style"> {{$t('reservationManage.serviceDate')}}： {{this.detailData.serviceDate}} {{this.detailData.servicePeriod}}</el-col>
                    <el-col :span="8" class="col_style">{{$t('reservationManage.store')}}： {{this.detailData.storeName}}</el-col>
                  </el-row>
                  <el-row  :gutter="20" class="row_style">
                    <el-col :span="12" class="col_style"> {{$t('reservationManage.orderSn')}}： {{this.detailData.orderSn}}</el-col>
                    <el-col :span="8" class="col_style">{{$t('reservationManage.technician')}}： {{this.detailData.technicianName}}</el-col>
                  </el-row>
                </div>
              </el-col>
              <el-col :span="8" :offset="1">
                <div class="boder_style">
                  <el-row  :gutter="20" class="row_style">
                    <el-col :span="10">{{$t('reservationManage.userInfo')}}</el-col>
                  </el-row>
                  <el-row  :gutter="20" class="row_style">
                    <el-col :span="10" class="col_style"> {{$t('reservationManage.subscriber')}}： {{this.detailData.subscriber}}</el-col>
                    <el-col :span="10" class="col_style"> {{$t('reservationManage.mobile')}}： {{this.detailData.mobile}}</el-col>
                  </el-row>
                  <el-row  :gutter="20" class="row_style">
                    <el-col :span="10" class="col_style"> {{$t('reservationManage.message')}}： {{this.detailData.addMessage}}</el-col>
                  </el-row>
                </div>
              </el-col>
            </el-row>
            <div style="margin-top: 20px">
              <el-row :gutter="6">
                <el-col :span="2"><img src="http://mpdevimg2.weipubao.cn/image/admin/since-edit.png" class="add_text" style="cursor: pointer" alt="">  {{$t('reservationManage.adminMessage')}}：</el-col>
                <el-col :span="4" class="col_style">{{this.detailData.adminMessage}}</el-col>
              </el-row>
            </div>
          </div>
        </div>
        <div class="table_box">
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
              :label="$t('reservationManage.serviceName')"
              prop="serviceName"
            >
              <template slot-scope="{ row }">
                <el-row :gutter=15>
                  <el-col :span="5" v-if="row.serviceImg">
                    <img :src="row.serviceImg" style="width: 48px; height: 48px">
                  </el-col>
                  <el-col :span="10">
                    <label style="font-size: 14px;">{{row.serviceName}}</label>
                  </el-col>
                </el-row>
              </template>
            </el-table-column>
            <el-table-column
              :label="$t('reservationManage.serviceFee')"
              prop="servicePrice"
            >
            </el-table-column>
            <el-table-column
              :label="$t('reservationManage.orderStatus')"
              prop="orderStatusName"
            ></el-table-column>
          </el-table>
          <el-row style="margin-top: 10px;">
            <!--支付金额明细-->
            <div class="pay_detail">
              <div class="pd_title">
                <span>{{$t('order.payInfo')}}</span>
                <span class="refund"><el-button
                  type="primary"
                  size="small"
                  @click="showMess2(detailData.orderId, detailData.orderSn, detailData.userId)"
                  v-if="detailData.orderStatus === 1"
                >{{$t('reservationManage.confirmDone')}}</el-button></span>
              </div>
              <div class="list_item">
                <span>{{$t('order.discountInfo')}}：</span>
                <div class="preference_list">
                  <div><span>券抵扣金额</span> - {{this.detailData.discount.toFixed(2)}} 元</div>
                  <div><span>{{$t('order.memberCardDiscount')}}</span> - {{this.detailData.memberCardBalance.toFixed(2)}} 元</div>
                  <div><span class="w2">{{$t('order.balance')}}</span> - {{this.detailData.useAccount.toFixed(2)}} 元</div>
                </div>
              </div>
              <div class="list_item">
                <span>{{$t('order.totalPrice')}}：</span>
                <div>{{this.detailData.orderAmount.toFixed(2)}} 元</div>
              </div>
              <div class="list_item">
                <span>{{$t('order.amountSum')}}：</span>
                <div class="m_color">{{this.detailData.moneyPaid.toFixed(2)}} 元</div>
              </div>
            </div>
          </el-row>
        </div>
        <!--核销表格信息-->
        <div class="table_box">
          <el-table
            ref="technicianTable"
            :data="tableData"
            v-if="detailData.orderStatus === 3"
            class="tableClass"
            max-height="500"
            border
            :header-cell-style="{
              'background-color':'#f5f5f5',
              'border':'none'
            }"
          >
            <el-table-column
              :label="$t('reservationManage.chargeCode')"
              prop="verifyCode"
            >
            </el-table-column>
            <el-table-column
              :label="$t('reservationManage.verifyAdmin')"
              prop="verifyAdmin"
            >
            </el-table-column>
            <el-table-column
              :label="$t('reservationManage.verifyDate')"
              prop="finishedTime"
            ></el-table-column>
            <el-table-column
              :label="$t('reservationManage.chargeType')"
              prop="verifyPay"
            ></el-table-column>
          </el-table>
        </div>
      </div>
    <!-- 核销/确认完成弹窗 -->
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
            v-model="chargeParam.verifyCode">
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
                  <el-select v-model="chargeParam.cardId" clearable :placeholder="$t('reservationManage.memberCard')">
                    <el-option
                      v-for="item in availableCard"
                      :key="item.cardId"
                      :label="item.cardName"
                      :value="item.cardId">
                    </el-option>
                  </el-select>
                </template>
                <el-input
                  v-if="chargeParam.verifyPay === 1"
                  style="width: 20%"
                  :placeholder="$t('reservationManage.reduceOrLimit')"
                  v-model="chargeParam.reduce">
                </el-input>
                <el-input
                  v-if="chargeParam.verifyPay === 1"
                  style="width: 20%"
                  :placeholder="$t('reservationManage.season')"
                  v-model="chargeParam.reason">
                </el-input>
              </div>
              <div style="margin-top: 20px">
                <el-radio :label="2">{{$t('reservationManage.balance')}}</el-radio>
                <el-input
                  v-if="chargeParam.verifyPay === 2"
                  style="width: 30%"
                  placeholder="99999999999999"
                  v-model.number="chargeParam.balance">
                </el-input>
                <el-input
                  v-if="chargeParam.verifyPay === 2"
                  style="width: 30%"
                  :placeholder="$t('reservationManage.season')"
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
import { detail, availableCard, charge } from '@/api/admin/storeManage/storemanage/reservationManage'

export default {
  data () {
    return {
      orderSn: '',
      tableData: [],
      detailData: {},
      stepActive: 0,
      imgs: [],
      // 确认完成
      showCharge: false,
      // 核销支付方式 0门店买单 1会员卡 2余额
      verifyPay: '',
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
      // 可用会员卡下拉
      availableCard: []
    }
  },
  created () {
    this.orderSn = this.$route.params.orderSn
    this.langDefault()
    this.getDetail()
  },
  methods: {
    // 预约详情页跳转/核销完成后刷新本页面
    click2Detail (orderSn) {
      this.$router.push({
        name: 'store_storemanage_reservation_detail',
        params: {
          orderSn: orderSn,
          flag: true
        }
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
          this.click2Detail(this.chargeParam.orderStatus)
        }
        this.$message.error('核销失败')
        this.showCharge = false
      })
    },
    getDetail () {
      detail(this.orderSn).then(res => {
        if (res.error === 0) {
          this.detailData = res.content
          // this.imgs = JSON.parse(res.content.serviceImg)
          this.detailData.serviceImg = res.content.serviceImg
          switch (this.detailData.verifyPay) {
            case 0:
              this.detailData.verifyPay = this.$t('reservationManage.storeBuy')
              break
            case 1:
              this.detailData.verifyPay = this.$t('reservationManage.memberCard')
              break
            case 2:
              this.detailData.verifyPay = this.$t('reservationManage.balance')
              break
          }
          this.tableData.push(this.detailData)
          switch (this.detailData.orderStatus) {
            case 0:
              this.stepActive = 0
              break
            case 1:
              this.stepActive = 1
              break
            case 2:
              this.stepActive = 0
              break
            case 3:
              this.stepActive = 2
              break
          }
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .boder_style {
    height: 250px;
    border: 1px solid #ccc;
    padding-left: 50px;
    padding-top: 10px;
    .row_style {
      margin-top: 30px;
    }
    .col_style {
      font-size: 14px;
      color: #666;
    }
  }
  .m_color {
    color: #ff1c1a;
  }
  .col_style {
    font-size: 14px;
    color: #666;
  }
  .pay_detail {
    display: flex;
    flex-direction: column;
    color: #333;
    .pd_title {
      display: flex;
      justify-content: space-between;
      line-height: 40px;
      background: #f9f9f9;
      padding: 0 10px;
      .refund {
        cursor: pointer;
      }
    }
    .list_item {
      display: flex;
      line-height: 40px;
      padding-left: 10px;
      > span {
        width: 100px;
        text-align: right;
      }
      > div {
        flex: 1;
        &.preference_list {
          display: flex;
          flex-direction: column;
          .w2 {
            letter-spacing: 43px;
            margin-right: -43px;
          }
        }
      }
    }
  }
  .main {
    padding: 10px;
    .navBox {
      background-color: #fff;
      padding: 15px;
      min-width: 1800px;
    }
    .btn {
      margin-left: 5px;
    }
    .table_box {
      min-width: 1800px;
      margin-top: 10px;
      background-color: #fff;
      padding: 15px;
      .filters {
        display: flex;
        line-height: 32px;
        margin-left: -15px;
        margin-bottom: 10px;
        .filters_item {
          max-width: 350px;
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
          font-size: 14px;
          color: #66b1ff;
          cursor: pointer;
          margin-right: 8px;
        }
      }
      .businessStateOperate {
        font-size: 14px;
        color: #66b1ff;
        cursor: pointer;
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
