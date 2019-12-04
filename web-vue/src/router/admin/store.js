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
  // 门店列表 门店管理
  {
    path: '/admin/home/main/store/storemanage',
    redirect: '/admin/home/main/store/storemanage/reserve',
    name: 'store_storemanage_list',
    meta: {
      crumbTitle: 'router.storeManage',
      meta: 'store_manage',
      category: 'store_list'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/store_manage/store/storeManage'
      ),
    children: [
      // 门店列表 门店管理 预约管理
      {
        path: 'reserve',
        name: 'store_storemanage_reservation',
        meta: {
          crumbTitle: 'router.storeReservationManage',
          meta: 'store_manage',
          category: 'store_list'
        },
        component: () => import('@/view/admin/index/leftNavComponents/store_manage/store/storemanage/reservationManage/reservationList')
      },
      // 门店列表 门店管理 服务管理
      {
        path: 'service',
        name: 'store_storemanage_service',
        meta: {
          crumbTitle: 'router.storeServiceManage',
          meta: 'store_manage',
          category: 'store_list'
        },
        component: () => import('@/view/admin/index/leftNavComponents/store_manage/store/storemanage/serviceManage'),
        children: [
          {
            path: 'list',
            name: 'store_storemanage_service_list',
            meta: {
              crumbTitle: 'router.storeServiceManage',
              meta: 'store_manage',
              category: 'store_list'
            },
            component: () => import('@/view/admin/index/leftNavComponents/store_manage/store/storemanage/serviceManage/serviceList')
          },
          {
            path: 'classify',
            name: 'store_storemanage_service_classify',
            meta: {
              crumbTitle: 'router.storeServiceManage',
              meta: 'store_manage',
              category: 'store_list'
            },
            component: () => import('@/view/admin/index/leftNavComponents/store_manage/store/storemanage/serviceManage/serviceClassify')
          },
          {
            path: 'add',
            name: 'store_storemanage_service_add',
            meta: {
              crumbTitle: 'router.storeServiceManage',
              meta: 'store_manage',
              category: 'store_list'
            },
            component: () => import('@/view/admin/index/leftNavComponents/store_manage/store/storemanage/serviceManage/serviceAdd')
          }
        ]
      },
      // 门店列表 门店管理 技师管理
      {
        path: 'technician',
        name: 'store_storemanage_technician',
        meta: {
          crumbTitle: 'router.storeTechnicianManage',
          meta: 'store_manage',
          category: 'store_list'
        },
        component: () => import('@/view/admin/index/leftNavComponents/store_manage/store/storemanage/technicianManage'),
        children: [
          {
            path: 'list',
            name: 'store_storemanage_technician_list',
            meta: {
              crumbTitle: 'router.storeServiceManage',
              meta: 'store_manage',
              category: 'store_list'
            },
            component: () => import('@/view/admin/index/leftNavComponents/store_manage/store/storemanage/technicianManage/technicianList')
          },
          {
            path: 'classify',
            name: 'store_storemanage_technician_classify',
            meta: {
              crumbTitle: 'router.storeServiceManage',
              meta: 'store_manage',
              category: 'store_list'
            },
            component: () => import('@/view/admin/index/leftNavComponents/store_manage/store/storemanage/technicianManage/technicianClassify')
          },
          {
            path: 'add',
            name: 'store_storemanage_technician_add',
            meta: {
              crumbTitle: 'router.storeServiceManage',
              meta: 'store_manage',
              category: 'store_list'
            },
            component: () => import('@/view/admin/index/leftNavComponents/store_manage/store/storemanage/technicianManage/technicianAdd')
          }
        ]
      },
      // 门店列表 门店管理 评价管理
      {
        path: 'comment',
        name: 'store_storemanage_comment',
        meta: {
          crumbTitle: 'router.storeEvaluationManage',
          meta: 'store_manage',
          category: 'store_list'
        },
        component: () => import('@/view/admin/index/leftNavComponents/store_manage/store/storemanage/evaluationManage'),
        children: [
          {
            path: 'record',
            name: 'store_storemanage_reservation_record',
            meta: {
              crumbTitle: 'router.storeEvaluationManage',
              meta: 'store_manage',
              category: 'store_list'
            },
            component: () => import('@/view/admin/index/leftNavComponents/store_manage/store/storemanage/evaluationManage/evaluationRecord')
          },
          {
            path: 'review',
            name: 'store_storemanage_reservation_review',
            meta: {
              crumbTitle: 'router.storeEvaluationManage',
              meta: 'store_manage',
              category: 'store_list'
            },
            component: () => import('@/view/admin/index/leftNavComponents/store_manage/store/storemanage/evaluationManage/evaluationReview')
          }
        ]
      }
    ]
  },
  // 门店列表 技师管理 排班配置
  {
    path: '/admin/home/main/store/storemanage/schedule/list',
    name: 'schedule_setting',
    meta: {
      crumbTitle: 'router.schedule_setting',
      meta: 'store_manage',
      category: 'store_list'
    },
    component: () => import('@/view/admin/index/leftNavComponents/store_manage/store/schedulingManage')
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
        '@/view/admin/index/leftNavComponents/store_manage/store/addStore'
      )
  },
  //   门店服务配置
  {
    path: '/admin/home/main/store/serviceConfig',
    name: 'store_service_config',
    meta: {
      crumbTitle: 'router.store_service_config',
      meta: 'store_manage'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/store_manage/store/serviceConfig'
      )
  }
]

export default routes
