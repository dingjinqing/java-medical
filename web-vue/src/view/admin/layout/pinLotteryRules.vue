<template>
  <!-- 小程序-拼团抽奖规则页面 -->
  <div
    class="pinLotteryRules"
    v-if="isUseDefault === '0'"
  >
    <div v-html="pinLotteryRules"></div>
  </div>

  <div
    class="pinLotteryRules"
    v-else
  >
    <p class="title">参与步骤</p>
    <p>1、在低价抽奖商品列表页,点击商品进入商品详情页,通过下单开团入口进入订单结算页,付款成功后,按页面提示分享给微信好友;</p>
    <p>2、好友通过小程序落地页查看活动现状,完成支付,参与拼团;</p>
    <p>3、支付人数在有效期内达到门槛值,则团内所有用户都获得抽奖资格,等待公布中奖结果;</p>
    <p>4、中奖结果在活动结束时公布,所有中奖订单进入发货流程,未中奖用户及未成团用户将全额退款至原支付账户;</p>
    <p class="title">参与规则</p>
    <p>1、活动期间,同一账户每个拼团商品仅可购买一单;</p>
    <p>2、拼团抽奖商品库存有限,如因库存不足导致抢购失败或发货失败,订单将全额退款至原支付账户;</p>
  </div>
</template>

<script>
import { pinLotteryRules } from '@/api/admin/util.js'
export default {
  data () {
    return {
      isUseDefault: '1', // 使用默认规则
      pinLotteryRules: '' // 自定义规则
    }
  },
  mounted () {
    // 初始化数据
    this.initData()
  },
  methods: {
    initData () {
      // 获取传参
      let obj = {
        shop_id: this.$route.query.shop_id,
        gid: this.$route.query.gid
      }
      pinLotteryRules(obj).then(res => {
        if (res.error === 0 && res.content !== null) {
          this.isUseDefault = res.content.isUseDefault
          this.pinLotteryRules = res.content.document
        }
      })
    }
  }
}
</script>

<style scoped>
.pinLotteryRules {
  height: 100%;
  line-height: 3;
  padding: 0 10px;
  background: #fff;
}
.pinLotteryRules p {
  color: #666;
  font-size: 14px;
  /* font-size: 0.75rem; */
}
.pinLotteryRules .title {
  color: #000;
  font-weight: bold;
}
</style>
