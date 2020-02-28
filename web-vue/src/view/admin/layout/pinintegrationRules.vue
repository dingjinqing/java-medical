<template>
  <!-- 小程序-瓜分积分规则页面 -->
  <div class="pinintegrationRules">
    <p>1、用户开团瓜分积分，按页面提示分享给好友。</p>
    <p>2、当参与拼团人数达到5人时，立即开奖，与好友一起瓜分50积分。</p>
    <p>3、邀请新用户参加，将有机会瓜分双份积分。</p>
    <p>4、用户可参加1次拼团。</p>
    <p>5、活动积分预计在24小时内到账。</p>
    <p>6、本活动的最终解释权归“积分发放方”所有。</p>
  </div>
</template>

<script>
export default {
  mounted () {
    document.title = '组团瓜分规则'
  }
}
</script>

<style scoped>
.pinintegrationRules {
  height: 100%;
  line-height: 3;
  padding: 0 10px;
  background: #fff;
}
.pinintegrationRules p {
  color: #666;
  font-size: 14px;
  /* font-size: 0.75rem; */
}
</style>
