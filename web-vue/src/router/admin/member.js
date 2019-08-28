const routes = [
  // 会员卡模块路由
  {
    path: '/admin/home/main/user_card',
    name: 'user_card',
    meta: {
      crumbTitle: 'router.userCard',
      meta: 'user_manger'
    },
    component: () =>
            import(
              '@/view/admin/index/leftNavComponents/user_manger/membershipCard/membershipCard'
            )
  },
  {
    path: '/admin/home/main/limitTimes',
    name: 'limitTimes',
    meta: {
      crumbTitle: 'router.limitTimes',
      meta: 'user_manger',
      category: 'user_card'
    },
    component: () =>
            import(
              '@/view/admin/index/leftNavComponents/user_manger/membershipCard/membershipCard'
            )
  },
  {
    path: '/admin/home/main/GradeCard',
    name: 'GradeCard',
    meta: {
      crumbTitle: 'router.GradeCard',
      meta: 'user_manger',
      category: 'user_card'
    },
    component: () =>
            import(
              '@/view/admin/index/leftNavComponents/user_manger/membershipCard/membershipCard'
            )
  },
  // 会员卡详情页
  {
    path: '/admin/home/main/membershipCardDetail',
    name: 'membershipCardDetail',
    meta: {
      crumbTitle: 'router.userCard',
      meta: 'user_manger',
      category: 'user_card'
    },
    component: () =>
            import(
              '@/view/admin/index/leftNavComponents/user_manger/membershipCard/membershipCardDetail'
            )
  },
  // 持卡会员
  {
    path: '/admin/home/main/Cardholder',
    name: 'Cardholder',
    meta: {
      crumbTitle: 'router.Cardholder',
      meta: 'user_manger',
      category: 'user_card'
    },
    component: () => import('@/view/admin/index/leftNavComponents/user_manger/membershipCard/cardholder')
  }
]

export default routes
