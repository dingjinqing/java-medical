<template>
  <div class="content">
      <div class="main">
        <div class="navBox">
            <el-row  :gutter="20">
              <el-col :span="5">订单号：{{this.detailData.orderSn}}</el-col>
              <el-col :span="10"> 订单状态： {{this.detailData.orderStatusName}}</el-col>
            </el-row>
            <el-steps :active="stepActive" finish-status="success" simple style="margin-top: 20px; width: 50%">
              <el-step title="买家已预约" ></el-step>
              <el-step title="订单完成" ></el-step>
            </el-steps>
          <div style="margin-top: 10px">
            <el-row>
              <el-col :span="8">
                <div class="boder_style">
                  <el-row  :gutter="20" class="row_style">
                    <el-col :span="10" style="font: 16px solid;">订单信息</el-col>
                  </el-row>
                  <el-row  :gutter="20" class="row_style">
                    <el-col :span="10"  class="col_style"> 订单状态： {{this.detailData.orderStatusName}}</el-col>
                    <el-col :span="10" class="col_style">订单金额： {{this.detailData.orderAmount}}</el-col>
                  </el-row>
                  <el-row  :gutter="20" class="row_style">
                    <el-col :span="10" class="col_style"> 预约到店时间： {{this.detailData.serviceDate}} {{this.detailData.servicePeriod}}</el-col>
                    <el-col :span="10" class="col_style">门店： {{this.detailData.orderAmount}}</el-col>
                  </el-row>
                  <el-row  :gutter="20" class="row_style">
                    <el-col :span="10" class="col_style"> 订单号： {{this.detailData.orderSn}}</el-col>
                    <el-col :span="10" class="col_style">预约技师： {{this.detailData.technicianName}}</el-col>
                  </el-row>
                </div>
              </el-col>
              <el-col :span="8" :offset="1">
                <div class="boder_style">
                  <el-row  :gutter="20" class="row_style">
                    <el-col :span="10">用户信息</el-col>
                  </el-row>
                  <el-row  :gutter="20" class="row_style">
                    <el-col :span="10" class="col_style"> 预约人： {{this.detailData.subscriber}}</el-col>
                    <el-col :span="10" class="col_style">手机号： {{this.detailData.mobile}}</el-col>
                  </el-row>
                  <el-row  :gutter="20" class="row_style">
                    <el-col :span="10" class="col_style"> 买家留言： {{this.detailData.addMessage}}</el-col>
                  </el-row>
                </div>
              </el-col>
            </el-row>
            <div style="margin-top: 20px">
              <el-row :gutter="6">
                <el-col :span="2"><img src="http://mpdevimg2.weipubao.cn/image/admin/since-edit.png" class="add_text" style="cursor: pointer" alt="">  卖家备注：</el-col>
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
              label="服务"
              prop="serviceName"
            >
            </el-table-column>
            <el-table-column
              label="总价"
              prop="servicePrice"
            >
            </el-table-column>
            <el-table-column
              label="订单状态"
              prop="orderStatusName"
            ></el-table-column>
          </el-table>
          <el-row style="margin-top: 10px; text-align: right">
            <el-col>实收款： <span style="color: #cc0000"> ￥ {{this.detailData.moneyPaid}}</span>
              <el-button
                type="primary"
                size="small"
                @click="confirmDone"
                v-if="detailData.orderStatus === 1"
              >确认完成</el-button>
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
              label="核销码"
              prop="verifyCode"
            >
            </el-table-column>
            <el-table-column
              label="核销人"
              prop="verifyAdmin"
            >
            </el-table-column>
            <el-table-column
              label="核销时间"
              prop="finishedTime"
            ></el-table-column>
            <el-table-column
              label="核销方式"
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
      stepActive: 0
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
          switch (this.detailData.verifyPay) {
            case 0:
              this.detailData.verifyPay = '门店买单'
              break
            case 1:
              this.detailData.verifyPay = '会员卡'
              break
            case 2:
              this.detailData.verifyPay = '余额'
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
