const routes = [
  {
    path: '/admin/home',
    name: 'adminHome',
    component: r => require.ensure([], () => r(require('@/view/admin/home')), 'adminHome'),
    children: [
      {
        path: '/admin/home/shop_main',
        name: 'shopMain',
        component: r => require.ensure([], () => r(require('@/components/admin/shop_main')), 'shopMain')
      }
    ]
  }
]
export default routes
