const routes = [
  //   门店列表
  {
    path: '/admin/home/main/store/all',
    name: 'store_list',
    meta: {
      crumbTitle: 'router.store_list',
      meta: 'store_manage',
      category: 'store'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/store_manage/store/storeList'
      )
  }
]

export default routes
