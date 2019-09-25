const routes = [
  //   门店列表
  {
    path: '/admin/home/main/store/list',
    name: 'store_list',
    meta: {
      crumbTitle: 'router.store_list',
      meta: 'store_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/store_manage/store/storeList'
      )
  },
  //   门店分组列表
  {
    path: '/admin/home/main/store/group/list',
    name: 'group_manage',
    meta: {
      crumbTitle: 'router.store_group_list',
      meta: 'store_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/store_manage/store/storeGroupList'
      )
  }
]

export default routes
