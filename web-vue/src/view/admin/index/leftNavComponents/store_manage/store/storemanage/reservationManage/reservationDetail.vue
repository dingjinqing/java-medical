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
                    <el-col :span="10"  class="col_style"> {{$t('reservationManage.orderStatus')}}： {{this.detailData.orderStatusName}}</el-col>
                    <el-col :span="10" class="col_style">{{$t('reservationManage.orderAmount')}}： {{this.detailData.orderAmount}}</el-col>
                  </el-row>
                  <el-row  :gutter="20" class="row_style">
                    <el-col :span="10" class="col_style"> {{$t('reservationManage.serviceDate')}}： {{this.detailData.serviceDate}} {{this.detailData.servicePeriod}}</el-col>
                    <el-col :span="10" class="col_style">{{$t('reservationManage.store')}}： {{this.detailData.orderAmount}}</el-col>
                  </el-row>
                  <el-row  :gutter="20" class="row_style">
                    <el-col :span="10" class="col_style"> {{$t('reservationManage.orderSn')}}： {{this.detailData.orderSn}}</el-col>
                    <el-col :span="10" class="col_style">{{$t('reservationManage.technician')}}： {{this.detailData.technicianName}}</el-col>
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
                <el-tooltip>
                  <span><img :src="row.serviceImg" style="cursor: pointer" alt=""> </span>
                    <span>{{row.serviceName}} </span>
                </el-tooltip>
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
          <el-row style="margin-top: 10px; text-align: right">
            <el-col>{{$t('reservationManage.moneyPaid')}}： <span style="color: #cc0000"> ￥ {{this.detailData.moneyPaid}}</span>
              <el-button
                type="primary"
                size="small"
                @click="confirmDone"
                v-if="detailData.orderStatus === 1"
              >{{$t('reservationManage.confirmDone')}}</el-button>
            </el-col>
          </el-row>
        </div>
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
  </div>
</template>

<script>
import { detail } from '@/api/admin/storeManage/storemanage/reservationManage'
export default {
  data () {
    return {
      orderSn: '',
      tableData: [],
      detailData: {},
      stepActive: 0,
      imgs: []
    }
  },
  created () {
    this.orderSn = this.$route.params.orderSn
    this.langDefault()
    this.getDetail()
  },
  methods: {
    getDetail () {
      detail(this.orderSn).then(res => {
        if (res.error === 0) {
          this.detailData = res.content
          this.imgs = JSON.parse(res.content.serviceImg)
          this.detailData.serviceImg = this.imgs[0]
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
    },
    confirmDone () {

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
  .col_style {
    font-size: 14px;
    color: #666;
  }
  .main {
    padding: 10px;
    .navBox {
      background-color: #fff;
      padding: 15px;
    }
    .btn {
      margin-left: 5px;
    }
    .table_box {
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
