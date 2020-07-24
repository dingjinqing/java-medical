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
        '@/view/admin/index/leftNavComponents/first_trade_manageL/orders/refundOrder/refundList'
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
        '@/view/admin/index/leftNavComponents/first_trade_manageL/orders/refundOrder/orderRefundInfo'
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
        '@/view/admin/index/leftNavComponents/first_trade_manageL/orders/refundOrder/manualRefundInfo'
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
        '@/view/admin/index/leftNavComponents/first_trade_manageL/orders/storeOrder/storeOrderList'
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
        '@/view/admin/index/leftNavComponents/first_trade_manageL/orders/storeOrder/storeOrderInfo'
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
        '@/view/admin/index/leftNavComponents/first_trade_manageL/orders/virtualGoodsOrder/virtualGoodsOrder'
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
  },
  // 自提订单
  {
    path: '/admin/home/main/orders/self',
    name: 'shop_setting',
    meta: {
      crumbTitle: 'router.self_order',
      meta: 'first_trade_manage',
      category: 'shop_setting'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_trade_manageL/orders/orderList'
      )
  },
  // 拼团退款失败
  {
    path: '/admin/home/main/orders/pinGroup/fail',
    name: 'pin_group_fail',
    meta: {
      crumbTitle: 'router.pin_group_fail',
      meta: 'first_trade_manage',
      category: 'pin_group_fail'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/first_trade_manageL/orders/orderList'
      )
  },
  // 批量发货
  {
    path: '/admin/home/main/orders/bulkShipment/list',
    name: 'bulk_shipment',
    meta: {
      crumbTitle: 'router.bulk_shipment',
      meta: 'first_trade_manage',
      category: 'bulk_shipment'
    },
    component: () => import('@/view/admin/index/leftNavComponents/first_trade_manageL/orders/bulkShipment/bulkShipmentList')
  },
  // 咨询订单
  {
    path: '/admin/home/main/orders/advisoryOrder/list',
    name: 'advisory_order',
    meta: {
      crumbTitle: 'router.advisory_order',
      meta: 'first_trade_manage',
      category: 'advisory_order'
    },
    component: () => import('@/view/admin/index/leftNavComponents/first_trade_manageL/orders/advisoryOrder/advisoryOrderList')
  },
  {
    path: '/admin/home/main/orders/advisoryOrder/info',
    name: 'advisory_order_info',
    meta: {
      crumbTitle: 'router.advisory_order',
      meta: 'first_trade_manage',
      category: 'advisory_order'
    },
    component: () => import('@/view/admin/index/leftNavComponents/first_trade_manageL/orders/advisoryOrder/advisoryOrderInfo')
  }
]

export default routes
