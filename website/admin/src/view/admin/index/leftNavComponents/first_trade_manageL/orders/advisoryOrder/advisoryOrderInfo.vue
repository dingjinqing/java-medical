<template>
   <div class="main1">
      <div class="since-info">
         <div class="since-info-top">
            <div class="order_mes">
               <span>订单号：202020202020202020202</span>
               <span>订单状态：订单完成</span>
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
                  <div class="item">订单状态：</div>
                  <div class="item">订单金额：</div>
                  <div class="item">下单时间：</div>
                  <div class="item">用户：</div>
                  <div class="item">订单号：</div>
                  <div class="item">下单人手机：</div>
               </div>
            </div>
            <div class="user_info">
               <div class="title">患者信息</div>
               <div class="item_box">
                  <div class="item">姓名：</div>
                  <div class="item">性别：</div>
                  <div class="item">生日：</div>
                  <div class="item">证件类型：</div>
                  <div class="item">证件号码：</div>
                  <div class="item">手机号：</div>
               </div>
            </div>
         </div>
      </div>
   </div>
</template>

<script>
export default {

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
