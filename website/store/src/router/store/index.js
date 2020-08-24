
const routes = [
  {
    path: '/admin/home',
    name: 'adminHome',
    component: () => import('@/view/admin/home'),
    children: [
      {
        path: '/admin/home/main',
        name: 'adminMain',
        component: () => import('@/view/admin/index/main'),
        children: [
          // 门店概况
          {
            path: '/admin/store/shopView',
            name: 'shopView',
            meta: {
              crumbTitle: '',
              meta: 'overView'
            },
            component: () => import('@/view/admin/index/leftNavComponents/store/overview/shopView')
          },
          {
            path: '/admin/store/list',
            name: 'storeList',
            meta: {
              crumbTitle: '',
              meta: 'storeList'
            },
            component: () => import('@/view/admin/index/leftNavComponents/store/overview/shopView')
          },
          {
            path: '/admin/store/goods',
            name: 'storeGoods',
            meta: {
              crumbTitle: '',
              meta: 'storeGoods'
            },
            component: () => import('@/view/admin/index/leftNavComponents/store/overview/shopView')
          },
          {
            path: '/admin/store/order',
            name: 'storeOrder',
            meta: {
              crumbTitle: '',
              meta: 'storeOrder'
            },
            component: () => import('@/view/admin/index/leftNavComponents/store/overview/shopView')
          },
          {
            path: '/admin/store/member',
            name: 'storeMember',
            meta: {
              crumbTitle: '',
              meta: 'storeMember'
            },
            component: () => import('@/view/admin/index/leftNavComponents/store/overview/shopView')
          }
        ]
      }
    ]
  }
]
export default routes
