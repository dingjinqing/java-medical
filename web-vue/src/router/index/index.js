const routes = [
  {
    path: '/index/home',
    name: 'indexHome',
    component: () => import('@/view/index/home'),

    children: [
      {
        path: '/index/home/main',
        name: 'indexHomeMain',
        component: () => import('@/view/index/main')
      },
      {
        path: '/index/home/ontrial',
        name: 'indexHomeOntrial',
        component: () => import('@/view/index/onTrial')
      },

      {
        path: '/index/home/news',
        name: 'indexNews',
        component: () => import('@/view/index/news'),
        children: [
          {
            path: '/index/home/news/newslist',
            name: 'newsList',
            component: () => import('@/view/index/news/newsindex')
          },
          {
            path: '/index/home/news/newsdetail',
            name: 'newsDetail',
            component: () => import('@/view/index/news/newsDetail')
          }
        ]
      },
      {
        path: '/index/home/aboutus',
        name: 'aboutUs',
        component: () => import('@/view/index/aboutUs')
      }
    ]
  },
  { // 移动端打开官网试用
    path: '/index/home/applyMobile',
    name: 'applyMobile',
    component: () => import('@/view/index/ontrialComponents/applyMobile')
  },
  { // 移动端打开官网首页
    path: '/index/home/indexMobile',
    name: 'indexMobile',
    component: () => import('@/view/index/indexMobile')
  }
]
export default routes
