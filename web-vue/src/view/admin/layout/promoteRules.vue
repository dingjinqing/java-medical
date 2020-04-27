<template>
  <!-- 小程序-好友助力规则页面 -->
  <div
    class="promoteRules"
    v-if="isUseDefault === 0"
  >
    <div v-html="promoteRules"></div>
  </div>

  <div
    class="promoteRules"
    v-else
  >
    <p class="title">参与步骤</p>
    <p>1、通过活动海报或好友分享,进入活动页面,通过活动发起按钮,发起助力活动,按页面提示分享给好友帮忙助力;</p>
    <p>2、好友通过小程序落地页查看活动现状,帮忙助力,获得助力值;</p>
    <p>3、在活动有效期内,助力值满,则发起人可获得奖励;</p>
    <p>4、在奖励有效期内,获奖用户领取奖励并完成下单(或查看商品),即可等待商家发货;</p>
    <p class="title">参与规则</p>
    <p>1、同一时间,同一用户只能发起一个助力活动;</p>
    <p>2、发起助力后24小时助力值未满的,则助力失败,不可获得奖励;</p>
  </div>
</template>

<script>
import { promoteRules } from '@/api/admin/util.js'
export default {
  data () {
    return {
      isUseDefault: 1, // 使用默认规则
      promoteRules: '' // 自定义规则
    }
  },
  mounted () {
    // 初始化数据
    this.initData()
  },
  methods: {
    initData () {
      // 获取传参
      promoteRules({
        actCode: this.$route.query.actCode
      }).then(res => {
        if (res.error === 0 && res.content !== null) {
          this.isUseDefault = res.content.isUseDefault
          this.promoteRules = res.content.document
        }
      })
    }
  }
}
</script>

<style scoped>
.promoteRules {
  width: 100%;
  height: 100%;
  line-height: 3;
  padding: 0 10px;
  background: #fff;
}
.promoteRules p {
  color: #666;
  font-size: 14px;
  /* font-size: 0.75rem; */
}
.promoteRules .title {
  color: #000;
  font-weight: bold;
}
</style>
