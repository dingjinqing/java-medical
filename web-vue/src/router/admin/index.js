const routes = [
  {
    path: '/admin/home',
    name: 'adminHome',
    component: r => require.ensure([], () => r(require('@/view/admin/home')), 'adminHome')
    // children: [

    // ]
  }
]
export default routes
