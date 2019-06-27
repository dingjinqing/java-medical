const routes = [
  {
    path: '/index/home',
    name: 'indexHome',
    component: r => require.ensure([], () => r(require('@/view/index/home')), 'indexHome'),
    children: [
      {
        path: '/index/home/main',
        name: 'indexHomeMain',
        component: r => require.ensure([], () => r(require('@/components/index/main')), 'indexHomeMain')
      },
      {
        path: '/index/home/ontrial',
        name: 'indexHomeOntrial',
        component: r => require.ensure([], () => r(require('@/components/index/onTrial')), 'indexHomeOntrial')
      }
    ]
  }
]
export default routes
