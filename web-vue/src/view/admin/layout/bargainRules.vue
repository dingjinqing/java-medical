<template>
  <!-- 小程序-砍价规则页面 -->
  <div class="bargainRules">
    <p>1、用户发起砍价，可以优先为自己砍价，分享给好友则默认再次进行一次砍价，金额和用户首次砍价金额相同;</p>
    <p>2、邀请好友一起砍价，砍到底价即可领取商品；</p>
    <p>3、对于同一个砍价，您只能帮助砍价一次;</p>
    <p>4、每次砍价金额随机，参与好友越多越容易成功;</p>
    <p>5、砍价成功，需要完善收货信息，商家才可进行发货;</p>
    <p>6、商品数量有限，先到先得，当前砍价商品全部砍完，所有未砍到底价的用户均视为砍价失败，用户不可再发起该砍价，好友也不可再帮助用户砍价;</p>
    <p>7、主办方可以根据本活动的实际举办情况对活动规则进行变动或者调整，相关调整解释权归商家所有，依法生效。</p>
  </div>
</template>

<script>
export default {
  mounted () {
    document.title = '砍价规则'
  }
}
</script>

<style scoped>
.bargainRules {
  height: 100%;
  line-height: 1.4;
  padding: 0 0.27rem;
  background: #fff;
}
.bargainRules p {
  color: #ccc;
  font-size: 0.29rem;
}
</style>
