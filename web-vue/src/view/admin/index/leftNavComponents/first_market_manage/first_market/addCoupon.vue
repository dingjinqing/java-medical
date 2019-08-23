<template>
  <div class="content">
    <div class="main">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          label="全部优惠券"
          name="first"
        ></el-tab-pane>
        <el-tab-pane
          label="进行中"
          name="second"
        ></el-tab-pane>
        <el-tab-pane
          label="未开始"
          name="third"
        ></el-tab-pane>
        <el-tab-pane
          label="已过期"
          name="fourth"
        ></el-tab-pane>
        <el-tab-pane
          label="已停用"
          name="fifth"
        ></el-tab-pane>
      </el-tabs>
      <div class="add_coupon_content">
        <div class="fl content_left">
          <div class="fl_title">
            <div>优惠券</div>
          </div>
          <div class="info">
            <div class="info_top">
              <div
                class="vip_exclusive"
                style="display:none;"
              >会员专享</div>
              <div class="coupon_name">优惠券名称</div>
              <div class="coupon_vou">
                <span>0.00</span>
              </div>
              <div class="coupon_dis"></div>
            </div>
          </div>
          <div class="info_mid">
            <div class="clearfix">
              <span class="sub_title">有效日期</span>
              <span class=date>
                <span>xxxx-xx-xx xx:xx:xx</span>
                <span>xxxx-xx-xx xx:xx:xx</span>
              </span>
              <span class="day">
                领取日期
                <span class="days">x天</span>
                <span class="hour">x小时</span>
                <span class="minute">x分钟</span>
                内有效
              </span>
            </div>
            <div>
              <span class="sub_title">使用限制</span>
              <span
                class="least"
                style="display:none;"
              >订单满<span>X</span>元可用</span>
              <span class="all">无限制</span>
              <span class="part">部分商品可用</span>
            </div>
          </div>
          <div class="info_bot">
            <div class="code">请输入领取码</div>
            <div class="use">立即使用</div>
            <div>
              <span class="sub_title">使用说明</span>
              <div class="instruction">暂无使用说明</div>
            </div>
          </div>
        </div>
        <div class="content_right">
          <div class="coupon_info">
            <div class="coupon_info_title">优惠券基础信息</div>
            <ul>
              <li class="content_right_li clearfix">
                <div class=ft>
                  <em>*</em>优惠券名称
                </div>
                <div class="ft">
                  <el-input
                    size="small"
                    class="coupon_name_input"
                    placeholder="最多输入10个字"
                    v-model="coupon_name_input"
                  ></el-input>
                </div>
              </li>
              <li class="content_right_li clearfix">
                <div class="to_choose">

                </div>
              </li>
              <li class="content_right_li clearfix"></li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      tableData: [],
      activeName: 'second',
      currentPage: 1
    }
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
.main_coupon {
  padding: 0 20px 20px;
  background: #fff;
  width: 100%;
}
.add_coupon_content .content_left {
  width: 323px;
  height: 510px;
  border: 1px solid #ccc;
  background: #eee;
  margin-top: 10px;
  margin-left: 20%;
}
.fl_title {
  height: 55px;
  background: url(../../../../../../assets/adminImg/phone_tops.png) no-repeat;
  color: white;
  text-align: center;
}
.fl_title div {
  padding-top: 25px;
  font-size: 15px;
}
.info {
  width: 95%;
  margin: 10px auto 0px auto;
  background: white;
}
.info_mid,
.info_bot {
  background: white;
  margin: 0px 10px;
  color: #666;
  font-size: 13px;
}
.info_bot {
  padding: 10px;
}
.vip_exclusive {
  position: absolute;
  top: 10px;
  left: 10px;
  width: 70px;
  line-height: 5px;
  color: #fff;
  border-radius: 15px;
  background-color: rgba(0, 0, 0, 0.1);
  font-size: 12px;
}
.info_top {
  padding: 10px;
  color: white;
  background: url(../../../../../../assets/adminImg/coupon_bg.png) left top
    no-repeat;
  height: 100px;
  background-size: 100% 100%;
  text-align: center;
  font-size: 15px;
}
.info_top div {
  padding: 10px;
}
.info_mid,
.info_bot {
  background: white;
  margin: 0px 10px;
  color: #666;
  font-size: 13px;
}
.info_mid div {
  border-bottom: 1px solid #ddd;
  padding: 15px;
}
.sub_title {
  color: #aaa;
}
.date {
  width: 75%;
  float: right;
}
.day,
.part,
.coupon_dis {
  display: none;
}
.info_bot {
  padding: 10px;
}
.code {
  width: 60%;
  margin: 0 auto;
  text-align: center;
  background: #f6f6f6;
  border-radius: 5px;
  padding: 6px;
  color: #aaa;
  display: none;
}
.use {
  border: 1px solid #ff6666;
  border-radius: 5px;
  padding: 5px;
  width: 30%;
  text-align: center;
  margin: 15px auto;
  color: #ff6666;
}
.instruction {
  background-color: #f6f6f6;
  min-height: 50px;
  margin: 10px auto;
  border-radius: 5px;
  padding: 5px;
  font-size: 12px;
}
.add_coupon_content .content_right {
  float: left;
  margin-left: 45%;
  margin-top: -36.4%;
  width: 560px;
}
.coupon_info_title {
  border-bottom: 1px solid #e5e5e5;
  line-height: 40px;
}
.coupon_info {
  border: 1px solid #e5e5e5;
  background: #f8f8f8;
  padding: 0 12px 22px;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  border-radius: 3px;
}
.content_right_li {
  padding: 10px 0 0 0;
}
.content_right_li em {
  color: #f66;
}
.content_right_li .fl:first-child {
  width: 110px;
  text-align: right;
}
.ft {
  width: 110px;
  float: left;
  line-height: 30px;
  margin-right: 15px;
  text-align: right;
}
.coupon_name_input {
  width: 160px;
}
</style>
