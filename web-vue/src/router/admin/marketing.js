const routes = [
  // 营销管理系列子路由
  {
    path: '/admin/home/main/first_market_manage',
    name: 'first_market_manage',
    meta: {
      crumbTitle: 'router.market_manage',
      meta: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/first_market/firstMarketManage'
      )
  },
  // 分享有礼活动
  {
    path: '/admin/home/main/sharePoliteList',
    name: 'share_polite',
    meta: {
      crumbTitle: 'router.share_polite',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/sharePolite/sharePoliteList'
      )
  },
  // 分享有礼活动-添加
  {
    path: '/admin/home/main/sharePolite/add',
    name: 'share_polite_add',
    meta: {
      crumbTitle: 'router.share_polite_add',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/sharePolite/addSharePolite'
      )
  },
  // 分享有礼活动-领取明细
  {
    path: '/admin/home/main/sharePoliteDetail',
    name: 'share_polite_detail',
    meta: {
      crumbTitle: 'router.share_polite_detail',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/sharePolite/sharePoliteDetail'
      )
  },
  // 砍价
  {
    path: '/admin/home/main/bargain',
    name: 'bargain',
    meta: {
      crumbTitle: 'router.bargain',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/bargain/bargainIndex'
      )
  },
  // 添加砍价活动
  {
    path: '/admin/home/main/bargainActivity',
    name: 'bargain_activity',
    meta: {
      crumbTitle: 'router.bargain_activity',
      meta: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/bargain/addBargainAct'
      )
  },
  // 多人拼团
  {
    path: '/admin/home/main/spellGroup',
    name: 'pin_group',
    meta: {
      crumbTitle: 'router.pin_group',
      meta: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/spellGroup/spellGroup'
      )
  },
  // 分销
  {
    path: '/admin/home/main/distribution',
    name: 'distribution_info',
    meta: {
      crumbTitle: 'router.distribution_info',
      meta: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/distribution/distribution'
      )
  },
  // // 组团瓜分积分
  // {
  //   path: '/admin/home/main/divideIntegral',
  //   name: 'pin_integration',
  //   meta: {
  //     crumbTitle: 'router.pin_integration',
  //     meta: 'first_market_manage'
  //   },
  //   component: () =>
  //     import(
  //       '@/view/admin/index/leftNavComponents/first_market_manage/divideIntegral/divideIntegral'
  //     )
  // },
  // 普通优惠券
  {
    path: '/admin/home/main/ordinaryCoupon',
    name: 'ordinary_coupon',
    meta: {
      crumbTitle: 'router.ordinary_coupon',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/first_market/ordinaryCoupon'
      )
  },
  // 优惠券礼包
  {
    path: '/admin/home/main/couponPackage',
    name: 'coupon_package',
    meta: {
      crumbTitle: 'router.coupon_Package',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/couponPackage/couponPackage'
      )
  },
  // 添加优惠券礼包
  {
    path: '/admin/home/main/couponPackage/add',
    name: 'coupon_Package_add',
    meta: {
      crumbTitle: 'router.coupon_Package_add',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/couponPackage/addCouponPackage'
      )
  },
  // 优惠券礼包-领取明细
  {
    path: '/admin/home/main/couponPackage/receiveDetails',
    name: 'coupon_Package_receive_details',
    meta: {
      crumbTitle: 'router.coupon_Package_receive_details',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/couponPackage/couponPackageReceiveDetails'
      )
  },
  // 优惠券礼包-订单明细
  {
    path: '/admin/home/main/couponPackage/orderDetails',
    name: 'coupon_Package_order_details',
    meta: {
      crumbTitle: 'router.coupon_Package_order_details',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/couponPackage/couponPackageOrderDetails'
      )
  },
  // 限时降价
  {
    path: '/admin/home/main/reduce',
    name: 'reduce',
    meta: {
      crumbTitle: 'router.reduce',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/reducePrice/reduce'
      )
  },
  // 添加限时降价
  {
    path: '/admin/home/main/reduce/add',
    name: 'reduce_add_view',
    meta: {
      crumbTitle: 'router.reduce',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/reducePrice/addReduce'
      )
  },
  // 限时降价订单列表
  {
    path: '/admin/home/main/reduce/orderList',
    name: 'reduce_order_list',
    meta: {
      crumbTitle: 'router.reduce_order_list',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/reducePrice/reduceOrderList'
      )
  },
  // 好友助力
  {
    path: '/admin/home/main/friendHelp',
    name: 'promote',
    meta: {
      crumbTitle: 'router.promote',
      meta: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/friendHelp/friendHelp'
      )
  },
  // 添加好友助力活动
  {
    path: '/admin/home/main/addHelpAct',
    name: 'promote_activity',
    meta: {
      crumbTitle: 'router.promote_activity',
      meta: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/friendHelp/addHelpAct'
      )
  },
  // 添加优惠券活动
  {
    path: '/admin/home/main/addyCoupon',
    name: 'add_coupon',
    meta: {
      crumbTitle: 'router.ordinary_coupon',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/first_market/addCoupon'
      )
  },
  // 拼团抽奖活动
  {
    path: '/admin/home/main/lotteryDraw',
    name: 'group_draw',
    meta: {
      crumbTitle: 'router.group_draw',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/lotteryDraw/lotteryDraw'
      )
  },
  // 幸运大抽奖活动
  {
    path: '/admin/home/main/luckyDraw',
    name: 'lottery_activity',
    meta: {
      crumbTitle: 'router.lottery_activity',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/luckyDraw/luckyDraw'
      )
  },
  // 好友代付
  {
    path: '/admin/home/main/friendPay',
    name: 'friend_pay',
    meta: {
      crumbTitle: 'router.friend_pay',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/first_market/friendPay'
      )
  },
  // 赠品
  {
    path: '/admin/home/main/gift',
    name: 'gift_view',
    meta: {
      crumbTitle: 'router.gift',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/gift/gift'
      )
  },
  // 创建赠品活动
  {
    path: '/admin/home/main/gift/add',
    name: 'gift_add_view',
    meta: {
      crumbTitle: 'router.gift_add',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/gift/addGift'
      )
  },
  // 修改赠品活动
  {
    path: '/admin/home/main/gift/add/:id',
    name: 'gift_edit_view',
    meta: {
      crumbTitle: 'router.gift_edit',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/gift/addGift'
      )
  },
  // 赠送明细
  {
    path: '/admin/home/main/gift/giftDetail/:id',
    name: 'gift_detail_view',
    meta: {
      crumbTitle: 'router.gift_detail',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/gift/giftDetail.vue'
      )
  },
  // 瓜分积分活动
  {
    path: '/admin/home/main/integration/list',
    name: 'pin_integration',
    meta: {
      crumbTitle: 'router.pin_integration',
      meta: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/groupIntegration/groupIntegrationList'
      )
  },
  // 创建瓜分积分活动
  {
    path: '/admin/home/main/integration/add',
    name: 'group_integration_add',
    meta: {
      crumbTitle: 'router.pin_integration_add',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/groupIntegration/groupIntegrationAdd'
      )
  },
  // 编辑瓜分积分活动
  {
    path: '/admin/home/main/integration/edit/:id',
    name: 'group_integration_edit',
    meta: {
      crumbTitle: 'router.pin_integration_edit',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/groupIntegration/groupIntegrationAdd'
      )
  },
  // 瓜分积分活动--参与用户明细
  {
    path: '/admin/home/main/integration/detail/:id',
    name: 'group_integration_detail',
    meta: {
      crumbTitle: 'router.pin_integration_detail',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/groupIntegration/groupIntegrationDetail'
      )
  },
  // 瓜分积分活动--成团明细
  {
    path: '/admin/home/main/integration/success/:id',
    name: 'group_integration_success',
    meta: {
      crumbTitle: 'router.pin_integration_success',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/groupIntegration/groupIntegrationSuccess'
      )
  },
  // 打包一口价活动
  {
    path: '/admin/home/main/packsale/list',
    name: 'package_price',
    meta: {
      crumbTitle: 'router.package_price',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/packagePrice/packagePriceList'
      )
  },
  // 打包一口价活动参与明细
  {
    path: '/admin/home/main/packsale/detail/:id',
    name: 'package_price_detail',
    meta: {
      crumbTitle: 'router.package_price_detail',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/packagePrice/packagePriceDetail'
      )
  },
  // 定金膨胀
  {
    path: '/admin/home/main/presale',
    name: 'presale_view',
    meta: {
      crumbTitle: 'router.presale',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/preSale/preSale'
      )
  },
  // 定金膨胀 - 创建活动
  {
    path: '/admin/home/main/presale/add',
    name: 'presale_add_view',
    meta: {
      crumbTitle: 'router.presale_add',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/preSale/preSaleAdd'
      )
  },
  // 定金膨胀 - 修改活动
  {
    path: '/admin/home/main/presale/edit/:id',
    name: 'presale_edit_view',
    meta: {
      crumbTitle: 'router.presale_edit',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/preSale/preSaleAdd'
      )
  },
  // 定金膨胀 - 订单明细
  {
    path: '/admin/home/main/presale/order_detail/:id',
    name: 'presale_order_detail_view',
    meta: {
      crumbTitle: 'router.presale_order_detail',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/preSale/preSaleOrderDetail'
      )
  },
  // 定金膨胀 - 活动明细
  {
    path: '/admin/home/main/presale/detail/:id',
    name: 'presale_detail_view',
    meta: {
      crumbTitle: 'router.presale_detail',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/preSale/preSaleDetail'
      )
  },
  // 加价购活动
  {
    path: '/api/admin/market/addPriceBuy/list',
    name: 'increase_purchase',
    meta: {
      crumbTitle: 'router.increase_purchase',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/increasePurchase/purchaseList'
      )
  },
  // 加价购活动 换购订单
  {
    path: '/api/admin/market/addPriceBuy/redemptionOrder',
    name: 'purchase_redemption_order',
    meta: {
      crumbTitle: 'router.purchase_redemption_order',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/increasePurchase/redemptionOrder'
      )
  },
  // 加价购活动 换购明细
  {
    path: '/api/admin/market/addPriceBuy/redemptionDetail',
    name: 'purchase_redemption_detail',
    meta: {
      crumbTitle: 'router.purchase_redemption_detail',
      meta: 'first_market_manage',
      category: 'first_market_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_market_manage/increasePurchase/redemptionDetail'
      )
  }
]
export default routes
