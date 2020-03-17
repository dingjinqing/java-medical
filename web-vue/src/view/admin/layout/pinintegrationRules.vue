<template>
  <!-- 小程序-瓜分积分规则页面 -->
  <div
    class="pinintegrationRules"
    v-if="isUseDefault === '0'"
  >
    <div v-html="integrationRules"></div>
  </div>

  <div
    class="pinintegrationRules"
    v-else
  >
    <div v-if="divideType !== 1">
      <p>1、用户开团瓜分积分，按页面提示分享给好友。</p>
      <p>2、当参与拼团人数达到<span>{{ limitAmount }}</span>人时，立即开奖，与好友一起瓜分积分。</p>
      <p>3、邀请新用户参加，将有机会瓜分双份积分。</p>
      <p v-if="joinLimit !== 0">4、用户可参加<span>{{ joinLimit }}</span>次拼团。</p>
      <p v-if="joinLimit === 0">4、用户参团次数不限。</p>
      <p>5、活动积分预计在24小时内到账。</p>
      <p>6、本活动的最终解释权归“积分发放方”所有。</p>
    </div>
    <div v-if="divideType === 1">
      <p>1、用户开团瓜分积分，按页面提示分享给好友。</p>
      <p>2、当参与拼团人数达到<span>{{ limitAmount }}</span>人时，立即开奖，与好友一起瓜分<span>{{ inteGroup }}</span>积分。</p>
      <p>3、当拼团人数少于<span>{{ limitAmount }}</span>人时，将于开团24小时后开奖，与好友一起瓜分奖池累计积分。</p>
      <p>4、邀请新用户参加，将有机会瓜分双份积分。</p>
      <p v-if="joinLimit !== 0">5、用户可参加<span>{{ joinLimit }}</span>次拼团。</p>
      <p v-if="joinLimit === 0">5、用户参团次数不限。</p>
      <p>6、活动积分预计在24小时内到账。</p>
      <p>7、本活动的最终解释权归“积分发放方”所有。</p>
    </div>

  </div>

</template>

<script>
import { integrationRules } from '@/api/admin/util.js'
export default {
  data () {
    return {
      isUseDefault: '1', // 使用默认规则
      integrationRules: '', // 自定义规则
      limitAmount: null, // 活动人数
      inteGroup: null, // 瓜分积分数
      divideType: 1, // 瓜分方式
      joinLimit: null // 参团限制
    }
  },
  mounted () {
    // 初始化数据
    this.initData()
  },
  methods: {
    initData () {
      // 获取传参
      let part = window.location.href.split('?')[1].split('&')
      let shopId = Number(part[0].split('=')[1])
      let pId = Number(part[2].split('=')[1])
      let obj = {
        shop_id: shopId,
        pid: pId
      }
      integrationRules(obj).then(res => {
        if (res.error === 0) {
          this.isUseDefault = res.content.activityInfo.is_use_default
          if (res.content.activityInfo.is_use_default === '1') {
            // 默认规则
            this.limitAmount = res.content.limitAmount
            this.inteGroup = res.content.inteGroup
            this.divideType = res.content.divideType
            this.joinLimit = res.content.joinLimit
          } else {
            // 自定义规则
            this.integrationRules = res.content.activityInfo.document
          }
        }
      })
    }
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
