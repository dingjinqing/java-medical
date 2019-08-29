<template>
  <div class="content">
    <div class="main">
      <div class="market_nav_title">
        <span class="title_one">{{$t('marketManage.title1')}}</span>
        <span class="title_two">{{$t('marketManage.title1Desc')}}</span>
      </div>
      <div class="box">
        <!-- 多人拼团 -->
        <div
          class="voucher box_style"
          @click="spellGroup()"
        >
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/drpt.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.manyPeopleGroup')}}</p>
            <span>{{$t('marketManage.manyPeopleGroupDesc')}}</span>
          </div>
        </div>
        <!-- 砍价 -->
        <div
          class="voucher box_style"
          @click="bargain()"
        >
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/kj.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.bargain')}}</p>
            <span>{{$t('marketManage.bargainDesc')}}</span>
          </div>
        </div>
        <!-- 分销 -->
        <div
          class="voucher box_style"
          @click="distribution()"
        >
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/fx.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.distribution')}}</p>
            <span>{{$t('marketManage.distributionDesc')}}</span>
          </div>
        </div>
      </div>

      <div class="box">
        <!-- 拼团抽奖 -->
        <div
          class="voucher box_style"
          @click="lotteryDraw()"
        >
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/ptcj.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.groupDraw')}}</p>
            <span>{{$t('marketManage.groupDrawDesc')}}</span>
          </div>
        </div>
        <!-- 组团瓜分积分 -->
        <div
          class="voucher box_style"
          @click="divideIntegral()"
        >
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/gfjf.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.divideIntegral')}}</p>
            <span>{{$t('marketManage.divideIntegralDesc')}}</span>
          </div>
        </div>
        <!-- 幸运大抽奖 -->
        <div
          class="voucher box_style"
          @click="luckyDraw()"
        >
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/xxdcj.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.luckyDraw')}}</p>
            <span>{{$t('marketManage.luckyDrawDesc')}}</span>
          </div>
        </div>
      </div>

      <div class="box">
        <!-- 好友代付 -->
        <div
          class="voucher box_style"
          @click="friendPay"
        >
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/instead_pay.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.friendPaid')}}</p>
            <span>{{$t('marketManage.friendPaidDesc')}}</span>
          </div>
        </div>
        <!-- 好友助力 -->
        <div
          class="voucher box_style"
          @click="friendHelp"
        >
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/friend_promote_cion.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.friendHelp')}}</p>
            <span>{{$t('marketManage.friendHelpDesc')}}</span>
          </div>
        </div>
        <!-- 我要送礼 -->
        <div class="voucher box_style">
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/wysl.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.giveGifts')}}</p>
            <span>{{$t('marketManage.giveGiftsDesc')}}</span>
          </div>
        </div>
      </div>

      <div class="box">
        <!-- 分享有礼 -->
        <div
          @click="sharePolite()"
          class="voucher box_style"
        >
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/fxyl.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.sharePolite')}}</p>
            <span>{{$t('marketManage.sharePolite')}}</span>
          </div>
        </div>
      </div>
      <div class="market_nav_title">
        <span class="title_one">{{$t('marketManage.title2')}}</span>
        <span class="title_two">{{$t('marketManage.title2Desc')}}</span>
      </div>
      <div class="box1">
        <!-- 普通优惠券 -->
        <div
          @click="coupon()"
          class="voucher box_style"
        >
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/ptyhq.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.coupon')}}</p>
            <span>{{$t('marketManage.couponDesc')}}</span>
          </div>
        </div>
        <!-- 优惠券礼包 -->
        <div
          class="voucher box_style"
          @click="couponPackage()"
        >
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/icon_coupon_bag.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.couponPackage')}}</p>
            <span>{{$t('marketManage.couponPackageDesc')}}</span>
          </div>
        </div>
        <!-- 限时降价 -->
        <div
          class="voucher box_style"
          @click="reduce()"
        >
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/xsjj.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.reducePrice')}}</p>
            <span>{{$t('marketManage.reducePriceDesc')}}</span>
          </div>
        </div>
      </div>
      <div class="box1">
        <!-- 支付有礼 -->
        <div class="voucher box_style">
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/zfyl.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.paymentPolite')}}</p>
            <span>{{$t('marketManage.paymentPoliteDesc')}}</span>
          </div>
        </div>
        <!-- 首单特惠 -->
        <div class="voucher box_style">
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/sdth.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.firstPreferential')}}</p>
            <span>{{$t('marketManage.firstPreferentialDesc')}}</span>
          </div>
        </div>
        <!-- 定金膨胀 -->
        <div class="voucher box_style" @click="preSale">
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/djpz.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.depositExpansion')}}</p>
            <span>{{$t('marketManage.depositExpansionDesc')}}</span>
          </div>
        </div>
      </div>
      <div class="box1">
        <!-- 秒杀 -->
        <div class="voucher box_style">
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/ms.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.secondsKill')}}</p>
            <span>{{$t('marketManage.secondsKillDesc')}}</span>
          </div>
        </div>
        <!-- 赠品 -->
        <div
          class="voucher box_style"
          @click="gift()"
        >
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/zp.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.gifts')}}</p>
            <span>{{$t('marketManage.giftsDesc')}}</span>
          </div>
        </div>
        <!-- 直播 -->
        <div class="voucher box_style">
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/zb.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.live')}}</p>
            <span>{{$t('marketManage.liveDesc')}}</span>
          </div>
        </div>
      </div>

      <div class="market_nav_title">
        <span class="title_one">{{$t('marketManage.title3')}}</span>
        <span class="title_two">{{$t('marketManage.title3Desc')}}</span>
      </div>

      <div class="box2">
        <!-- 满折满减 -->
        <div class="voucher box_style">
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/mzmj.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.discount')}}</p>
            <span>{{$t('marketManage.discountDesc')}}</span>
          </div>
        </div>
        <!-- 打包一口价 -->
        <div
          class="voucher box_style"
          @click="packagePrice"
        >
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/dbykj.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.packagePrice')}}</p>
            <span>{{$t('marketManage.packagePriceDesc')}}</span>
          </div>
        </div>
        <!-- 加价购 -->
        <div class="voucher box_style">
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/jjg.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.addPriceBuy')}}</p>
            <span>{{$t('marketManage.addPriceBuyDesc')}}</span>
          </div>
        </div>
      </div>
      <div class="box2">
        <!-- 满包邮 -->
        <div class="voucher box_style">
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/mby.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.freeMail')}}</p>
            <span>{{$t('marketManage.freeMailDesc')}}</span>
          </div>
        </div>
      </div>

      <div class="market_nav_title">
        <span class="title_one">{{$t('marketManage.title4')}}</span>
        <span class="title_two">{{$t('marketManage.title4Desc')}}</span>
      </div>

      <div class="box3">
        <!-- 积分兑换 -->
        <div class="voucher box_style">
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/jfdh.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.integralExchange')}}</p>
            <span>{{$t('marketManage.integralExchangeDesc')}}</span>
          </div>
        </div>
        <!-- 开屏有礼 -->
        <div class="voucher box_style">
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/hdyl.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.tailPolite')}}</p>
            <span>{{$t('marketManage.tailPoliteDesc')}}</span>
          </div>
        </div>
        <!-- 评价有礼 -->
        <div class="voucher box_style">
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/pjyl.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.evaluationPolite')}}</p>
            <span>{{$t('marketManage.evaluationPoliteDesc')}}</span>
          </div>
        </div>
      </div>
      <div class="box3">
        <!-- 收藏有礼 -->
        <div class="voucher box_style">
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/scyl.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.collectionPolite')}}</p>
            <span>{{$t('marketManage.collectionPoliteDesc')}}</span>
          </div>
        </div>
        <!-- 表单统计 -->
        <div class="voucher box_style">
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/bdtj.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.formStatistical')}}</p>
            <span>{{$t('marketManage.formStatisticalDesc')}}</span>
          </div>
        </div>
        <!-- 测评 -->
        <div class="voucher box_style">
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/cp.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.measurement')}}</p>
            <span>{{$t('marketManage.measurementDesc')}}</span>
          </div>
        </div>
      </div>
      <div class="box3">
        <!-- 定向发券 -->
        <div class="voucher box_style">
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/dxfq.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.directionalSendCoupon')}}</p>
            <span>{{$t('marketManage.directionalSendCouponDesc')}}</span>
          </div>
        </div>
        <!-- 消息推送 -->
        <div class="voucher box_style">
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/xxts.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.sendMessage')}}</p>
            <span>{{$t('marketManage.sendMessageDesc')}}</span>
          </div>
        </div>
        <!-- 渠道页面分析 -->
        <div class="voucher box_style">
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/qdymfx.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.pageAnalysis')}}</p>
            <span>{{$t('marketManage.pageAnalysisDesc')}}</span>
          </div>
        </div>
      </div>
      <div class="box3">
        <!-- 微信好物圈 -->
        <div class="voucher box_style box_bottom">
          <div class="activity_img"><img
              :src="$imageHost + '/image/admin/new_market/goods_circle.png'"
              alt=""
            ></div>
          <div class="activity_title">
            <p>{{$t('marketManage.wechateGoodsCicle')}}</p>
            <span>{{$t('marketManage.wechateGoodsCicleDesc')}}</span>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>
<script>
export default {
  mounted () {
    this.langDefault()
  },
  methods: {
    // 多人拼团点击
    spellGroup () {
      this.$router.push({
        name: 'pin_group'
      })
    },
    // 砍价点击
    bargain () {
      this.$router.push({
        name: 'bargain'
      })
    },
    // 好友助力点击
    friendHelp () {
      this.$router.push({
        name: 'promote'
      })
    },
    // 拼团抽奖点击
    lotteryDraw () {
      this.$router.push({
        name: 'group_draw'
      })
    },
    // 分销点击
    distribution () {
      this.$router.push({
        name: 'distribution_info'
      })
    },
    // 瓜分积分点击
    divideIntegral () {
      this.$router.push({
        name: 'pin_integration'
      })
    },
    // 普通优惠卷点击
    coupon () {
      this.$router.push({
        name: 'ordinary_coupon'
      })
    },
    // 赠品点击
    gift () {
      this.$router.push({
        name: 'gift_view'
      })
    },
    // 幸运大抽奖点击
    luckyDraw () {
      this.$router.push({
        name: 'lottery_activity'
      })
    },
    // 好友代付点击
    friendPay () {
      this.$router.push({
        name: 'friend_pay'
      })
    },
    // 优惠券礼包
    couponPackage () {
      this.$router.push({
        name: 'coupon_package'
      })
    },
    // 限时降价点击
    reduce () {
      this.$router.push({
        name: 'reduce'
      })
    },
    // 分享有礼活动点击
    sharePolite () {
      this.$router.push({
        name: 'share_polite'
      })
    },
    // 打包一口价
    packagePrice () {
      this.$router.push({
        name: 'package_price'
      })
    },
    // 定金膨胀
    preSale () {
      this.$router.push({
        name: 'presale_view'
      })
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
    padding: 10px 20px 0 20px;
    .box {
      display: flex;
      .box_style {
        display: flex;
        width: 31%;
        height: 60px;
        background-color: #f2f6ff;
        margin-top: 10px;
        margin-right: 20px;
        cursor: pointer;
        border: 1px solid #d9e4ff;
      }
    }
  }
  .activity_img {
    width: 25%;
    height: 60px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .activity_title {
    width: 75%;
    height: 60px;
    position: relative;
    p {
      margin-top: 14px;
      font-size: 14px;
      color: #333;
      padding-bottom: 3px;
    }
    span {
      font-size: 12px;
      color: #999;
    }
  }

  .market_nav_title {
    margin-top: 15px;
    width: 100%;
    height: 20px;
    .title_one {
      font-size: 16px;
      color: #333;
    }
    .title_two {
      font-size: 12px;
      color: #999;
      padding-left: 5px;
    }
  }
}
.box1 {
  display: flex;
  .box_style {
    display: flex;
    width: 31%;
    height: 60px;
    background-color: #fffcf6;
    margin-top: 10px;
    margin-right: 20px;
    cursor: pointer;
    border: 1px solid #ffe8c1;
  }
}

.box2 {
  display: flex;
  .box_style {
    display: flex;
    width: 31%;
    height: 60px;
    background-color: #f3fff9;
    margin-top: 10px;
    margin-right: 20px;
    cursor: pointer;
    border: 1px solid #d6ffec;
  }
}

.box3 {
  display: flex;
  .box_style {
    display: flex;
    width: 31%;
    height: 60px;
    background-color: #fff6f6;
    margin-top: 10px;
    margin-right: 20px;
    cursor: pointer;
    border: 1px solid #ffe2e2;
  }
}
.box_bottom {
  margin-bottom: 30px;
}
</style>
