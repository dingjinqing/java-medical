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
      },
      {
        path: '/index/home/news',
        name: 'indexNews',
        component: r => require.ensure([], () => r(require('@/components/index/news')), 'indexNews'),
        children: [
          {
            path: '/index/home/news/newslist',
            name: 'newsList',
            component: r => require.ensure([], () => r(require('@/components/index/news/newsindex')), 'newsList')
          },
          {
            path: '/index/home/news/newsdetail',
            name: 'newsDetail',
            component: r => require.ensure([], () => r(require('@/components/index/news/newsDetail')), 'newsDetail')
          }
        ]
      },
      {
        path: '/index/home/aboutus',
        name: 'aboutUs',
        component: r => require.ensure([], () => r(require('@/components/index/aboutUs')), 'aboutUs')
      }
    ]
  }
]
export default routes
