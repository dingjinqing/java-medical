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
  }
]

export default routes
