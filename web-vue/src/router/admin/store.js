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
  // 门店列表 商品管理
  {
    path: '/admin/home/main/store/goods/list',
    name: 'store_goods_list',
    meta: {
      crumbTitle: 'router.storeGoodsList',
      meta: 'store_manage',
      category: 'store_list'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/store_manage/store/storeGoodsList'
      )
  },
  // 门店列表 核销员管理
  {
    path: '/admin/home/main/store/verification/list',
    name: 'store_verification_list',
    meta: {
      crumbTitle: 'router.verifierManage',
      meta: 'store_manage',
      category: 'store_list'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/store_manage/store/verifierManage'
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
  },
  //   新增门店
  {
    path: '/admin/home/main/store/addStore',
    name: 'add_store',
    meta: {
      crumbTitle: 'router.add_store',
      meta: 'store_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/store_manage/store/addStoreGroup'
      )
  }
]

export default routes
