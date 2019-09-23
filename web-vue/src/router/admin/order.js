const routes = [
  //   全部订单
  {
    path: '/admin/home/main/orders/all',
    name: 'order',
    meta: {
      crumbTitle: 'router.order',
      meta: 'first_trade_manage',
      category: 'order'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_trade_manageL/orders/orderList'
      )
  },
  //   订单详情
  {
    path: '/admin/home/main/orders/info',
    name: 'orderInfo',
    meta: {
      crumbTitle: 'router.orderInfo',
      meta: 'first_trade_manage',
      category: 'order'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_trade_manageL/orders/orderInfo'
      )
  },
  // 退款退货订单
  {
    path: '/admin/home/main/orders/refund/list',
    name: 'order_return',
    meta: {
      crumbTitle: 'router.refundList',
      meta: 'first_trade_manage',
      category: 'order_return'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_trade_manageL/orders/refund_order/refundList'
      )
  },
  // 退款申请详情
  {
    path: '/admin/home/main/orders/refund/info',
    name: 'orderRefundInfo',
    meta: {
      crumbTitle: 'router.orderRefundInfo',
      meta: 'first_trade_manage',
      category: 'order_return'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_trade_manageL/orders/refund_order/orderRefundInfo'
      )
  },
  // 手动退货退款
  {
    path: '/admin/home/main/orders/refund/manual',
    name: 'manualRefund',
    meta: {
      crumbTitle: 'router.manualRefund',
      meta: 'first_trade_manage',
      category: 'order'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_trade_manageL/orders/refund_order/manualRefundInfo'
      )
  },
  // 买单订单列表
  {
    path: '/admin/home/main/orders/check/list',
    name: 'check_order',
    meta: {
      crumbTitle: 'router.check_order',
      meta: 'first_trade_manage',
      category: 'check_order'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_trade_manageL/orders/store_order/storeOrderList'
      )
  },
  // 买单订单详情
  {
    path: '/admin/home/main/orders/check/info',
    name: 'storeOrderInfo',
    meta: {
      crumbTitle: 'router.storeOrderInfo',
      meta: 'first_trade_manage',
      category: 'check_order'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_trade_manageL/orders/store_order/storeOrderInfo'
      )
  },
  // 虚拟商品订单-会员卡
  {
    path: '/admin/home/main/orders/virtual/order',
    name: 'member_card_order',
    meta: {
      crumbTitle: 'router.memberCardOrder',
      meta: 'first_trade_manage',
      category: 'member_card_order'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_trade_manageL/orders/virtual_goods_order/virtualGoodsOrder'
      )
  },
  // 待发货订单
  {
    path: '/admin/home/main/orders/waiting',
    name: 'order_wait',
    meta: {
      crumbTitle: 'router.order',
      meta: 'first_trade_manage',
      category: 'order_wait'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_trade_manageL/orders/orderList'
      )
  }
]

export default routes
