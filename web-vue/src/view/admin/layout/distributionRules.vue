<template>
  <!-- 小程序-分销中心规则页面 -->
  <div class="distributionRules">
    <el-tabs
      v-model="activeName"
      @tab-click="handleClick"
      :stretch="true"
    >
      <el-tab-pane
        label="返利说明"
        name="first"
      >
        <p class="title">1. 返利说明</p>
        <p>(1) 买家购买返利商品,下单支付成功,则邀请该用户注册的分销员可获得佣金返利;佣金制=商品实际支付金额*佣金比例。</p>
        <p>(2) 邀请新用户注册,或者分享给已经注册的但没有邀请人的用户都算作该分销员的用户。</p>
        <p>(3) 订单支付成功则返利佣金为待返利状态,交易完成则该佣金返利完成,自动提现到分销员的用户余额中。交易完成前发生退款的订单,相应的分校元返利佣金为已退款返利失败状态。</p>
        <p>(4) 仅在线支付的订单算作业绩,即微信支付、余额支付(会员卡余额、用户余额)、货到付款的订单计算返利佣金。例如买家购买返利商品A价格为100元,该买家使用了20元的优惠券,其余金额使用微信支付,返利比例为10%,则邀请该买家的分销员可获得返利佣金为(100-20)*10%=8元。</p>
        <p>(5) 买家仅购买返利商品,该买家的邀请人可获得返利佣金,购买普通商品不返利。</p>
        <p class="title">2. 结算说明</p>
        <p>(1) 返利佣金比例为10%。</p>
        <p>(2) 返利有效期为10天(用户被分销员邀请注册开始计算,在该天数限制内该用户购买分销商品给分销员计算佣金返利,一旦超过改天数,则不再给分销员佣金返利)。</p>
        <p class="title">3. 其他说明</p>
        <p>(1) 分享前,请确定商品页面有【...】按钮。</p>
        <p>(2) 销售过程中有任何疑问,请直接联系商家。</p>
        <p>(3) 已售出商品的任何售后问题,由本商城处理。</p>
        <p>(4) 不传播或者扩散有关政治、色情等任何违法的信息,一经发现,则立即封号,如果触犯任何法律相关问题,商城不负任何责任。</p>
        <p>(5) 以上内容解释权归本商城所有。</p>
      </el-tab-pane>
      <el-tab-pane
        label="分销FAQ"
        name="second"
      >
        <p>Q: 怎样进行"推广赚钱"?</p>
        <p>A: 分享店铺给好友,吸引好友点击注册,10天内在该店铺中购买所有带有<span class="content">分销</span>图标的返利商品,都会按照一定的计算方法算作你的"分销返利佣金"收益。</p>
        <p>Q: 如何计算"分销返利佣金"收益?</p>
        <p>A: 每个带有<span class="content">分销</span>图标的商品都有一定的返利比例,没有该标记的表示该商品不参与"分销返利"。你邀请的用户购物付款后,将根据订单中每个商品的提成比例分别计算提成(不参与返利的商品不返利),最后累加成为该笔订单的返利佣金。返利佣金计算中会扣除运费、退款、满减等金额,以最终成交价为准。</p>
        <p>Q: "分销返利佣金"何时到账?</p>
        <p>A: 你邀请的用户购买分销商品后,你可在分销员中心的返利订单明细中看到这笔订单,这笔佣金将自动转入你的个人余额中。</p>
        <p>Q: 分销员自己购买商品会给自己返利吗?</p>
        <p>A: 不会,分销员自己购买商品不会给自己返利。</p>
        <p>Q: 为什么我按上述规则分享,没有拿到返利佣金?</p>
        <p>A: (1) 好友可能并未 建立邀请注册关系。 (2) 好友购物日期已经超过返利有效期。</p>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  data () {
    return {
      activeName: 'first'
    }
  },
  mounted () {
    document.title = '分销中心规则'
  }
}
</script>

<style scoped>
.distributionRules {
  width: 100%;
  height: 100%;
  line-height: 3;
  padding: 0 10px;
  background: #fff;
}
.el-tabs__nav {
  width: 100% !important;
}
.el-tabs__item {
  width: 50% !important;
  text-align: center !important;
}
.distributionRules p {
  color: #888;
}
.distributionRules .title {
  color: #000;
}
.distributionRules .content {
  margin: 0 3px;
  padding: 3px;
  color: red;
  border: 1px solid red;
}
</style>
