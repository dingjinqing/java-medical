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
  }
]

export default routes
