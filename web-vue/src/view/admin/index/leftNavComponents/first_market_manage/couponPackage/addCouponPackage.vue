<template>
  <div class="content">
    <div class="main">
      <div class="coupon_settings">
        <div class="left_preview">
          <div class="left_top">
            优惠券礼包
          </div>
          <div class="left_content">
            <div class="content_bg">
              <img :src="$imageHost+'/image/admin/cou_package_bg.jpeg'">
            </div>
          </div>
        </div>
        <div class="right_settings"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style lang="scss" scoped>
.main {
  padding: 10px;
  background-color: #fff;
  .coupon_settings {
    max-width: 940px;
    margin: 0 auto;
    display: flex;
    .left_preview {
      width: 325px;
      .left_top {
        width: 100%;
        height: 55px;
        color: #000;
        background: url(../../../../../../../static/image/admin/cou_top_bg.png)
          no-repeat 100%/100%;
        text-align: center;
        line-height: 75px;
      }
      .left_content {
        width: 100%;
        min-height: 450px;
        background: linear-gradient(left, #ff5666, #e6558e);
        position: relative;
      }
    }
    .right_settings {
      flex: 1;
      margin-left: 15px;
    }
  }
}
</style>
