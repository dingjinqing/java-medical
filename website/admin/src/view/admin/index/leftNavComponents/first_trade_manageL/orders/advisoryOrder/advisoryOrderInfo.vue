<template>
   <div class="main1">
      <div class="since-info">
         <div class="since-info-top">
            <div class="order_mes">
               <span>订单号：{{orderContent.orderSn}}</span>
               <span>订单状态：{{orderContent.orderStatusName}}</span>
            </div>
            <el-button
               type='primary'
               size='small'
            >手动退款</el-button>
         </div>
         <div class="since-info-detail">
            <div class="order_info">
               <div class="title">订单信息</div>
               <div class="item_box">
                  <div class="item">订单状态：{{orderContent.orderStatusName}}</div>
                  <div class="item">订单金额：{{orderContent.orderAmount}}</div>
                  <div class="item">下单时间：{{orderContent.payTime}}</div>
                  <div class="item">用户：{{orderContent.orderStatusName}}</div>
                  <div class="item">订单号：{{orderContent.orderSn}}</div>
                  <div class="item">下单人手机：{{orderContent.orderStatusName}}</div>
               </div>
            </div>
            <div class="user_info">
               <div class="title">患者信息</div>
               <div class="item_box">
                  <div class="item">姓名：{{orderContent.patientName}}</div>
                  <div class="item">性别：{{orderContent.patientSexName}}</div>
                  <div class="item">生日：{{orderContent.orderStatusName}}</div>
                  <div class="item">证件类型：{{orderContent.patientIdentityName}}</div>
                  <div class="item">证件号码：{{orderContent.patientIdentityCode}}</div>
                  <div class="item">手机号：{{orderContent.patientMobile}}</div>
               </div>
            </div>
         </div>
      </div>
   </div>
</template>

<script>
import { advisoryOrderInfo } from '@/api/admin/orderManage/order.js'
export default {
  mounted () {
   if (this.$route.query.orderId) {
     this.id = this.$route.query.orderId
     this.initOrderInfo(this.id)
   }
  },
  watch: {
   '$route.query.orderId': function (newVal) {
     if (newVal) {
      this.id = this.$route.query.orderId
      this.initOrderInfo(this.id)
     }
   }
  },
  data () {
   return {
     orderContent: {}
   }
  },
  methods: {
   initOrderInfo (id) {
     advisoryOrderInfo({orderId:id}).then(res => {
       if (res.error === 0) {
         res.content.payTime = res.content.payTime.substr(0, 10)
         res.content.orderStatusName = this.getStatusName(res.content.orderStatus)
         res.content.patientSexName = this.getPatientSex(res.content.patientSex)
         res.content.patientIdentityName = this.getIdentityName(res.content.patientIdentityType)
         this.orderContent = res.content
         console.log(this.orderContent)
       } else {
         this.$message.error({
           message: '获取失败',
           showClose: true
         })
       }
     })
   },
   getStatusName (data) {
      switch (data) {
        case 0:
          return '待付款'
        case 1:
          return '待接诊'
        case 2:
          return '接诊中'
        case 3:
          return '已完成'
        case 4:
          return '已退款'
        case 5:
          return '已取消'
      }
   },
   getPatientSex (data) {
      switch (data) {
        case 0:
          return '未知'
        case 1:
          return '男'
        case 2:
          return '女'
      }
   },
   getIdentityName (data) {
      switch (data) {
        case 1:
          return '身份证'
        case 2:
          return '军人证'
        case 3:
          return '护照'
        case 4:
          return '社保卡'
      }
   }
  }
}
</script>

<style lang='scss'>
.main1{
   margin: 10px;
   min-width: auto !important;
   .since-info {
    background-color: #fff;
    padding: 0 25px;
    font-size: 14px;
    overflow: hidden;
      .since-info-top {
         display: flex;
         align-items: center;
         justify-content: space-between;
         height: 60px;
         color: #333;
         .order_mes span {
            margin-right: 60px;
         }
      }
      .since-info-detail {
         display: flex;
         margin-left: -30px;
         margin-bottom: 10px;
         > div {
            margin-left: 30px;
            border: 1px solid #cfd6ff;
            flex: 1;
            padding: 0 30px;
            > .title {
               margin-top: 10px;
               font-weight: 600;
               color: #333;
               font-size: 14px;
               margin-bottom: 10px;
            }
            > .item_box {
               display: flex;
               justify-content: space-between;
               flex-wrap: wrap;
               line-height: 30px;
               color: #666;
               > .item {
                  min-width: 210px;
               }
            }
         }
      }
   }
}
</style>
