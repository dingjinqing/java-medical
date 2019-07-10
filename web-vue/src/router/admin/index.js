const routes = [
  {
    path: '/admin/home',
    name: 'adminHome',
    component: r => require.ensure([], () => r(require('@/view/admin/home')), 'adminHome'),
    children: [
      {
        path: '/admin/home/shop_main',
        name: 'shopMain',
        component: r => require.ensure([], () => r(require('@/view/admin/shop_main')), 'shopMain')
      },
      {
        path: '/admin/home/mian',
        name: 'adminMain',
        component: r => require.ensure([], () => r(require('@/view/admin/index/main')), 'adminMain')
      }
    ]
  },
  {
    path: '/admin/selectlinks',
    name: 'selectLinks',
    component: r => require.ensure([], () => r(require('@/components/admin/selectLinks')), 'selectLinks')
  }

]
export default routes
