const routes = [
  // 会员卡模块路由
  {
    path: '/admin/home/main/membershipList',
    name: 'user_list',
    meta: {
      crumbTitle: 'router.membershipList',
      meta: 'user_manger'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/user_manger/membershipList/membershipList'
      )
  },
  {
    path: '/admin/home/main/membershipInformation',
    name: 'membershipInformation',
    meta: {
      crumbTitle: 'router.memberEditorList',
      meta: 'user_manger',
      category: 'user_list'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/user_manger/membershipList/membershipInformation'
      )
  },
  {
    path: '/admin/home/main/receiveDetail',
    name: 'receiveDetail',
    meta: {
      crumbTitle: 'router.receiveDetail',
      meta: 'user_manger',
      category: 'user_list'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/user_manger/membershipList/receiveDetail'
      )
  },
  {
    path: '/admin/home/main/balanceDetail',
    name: 'balanceDetail',
    meta: {
      crumbTitle: 'router.balanceDetail',
      meta: 'user_manger',
      category: 'user_list'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/user_manger/membershipList/balanceDetail'
      )
  },
  {
    path: '/admin/home/main/integralDetail',
    name: 'integralDetail',
    meta: {
      crumbTitle: 'router.integralDetail',
      meta: 'user_manger',
      category: 'user_list'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/user_manger/membershipList/integralDetail'
      )
  },
  {
    path: '/admin/home/main/membershipIntroduction',
    name: 'user_import',
    meta: {
      crumbTitle: 'router.membershipIntroduction',
      meta: 'user_manger'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/user_manger/membershipIntroduction/membershipIntroduction'
      )
  },
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
  // 普通会员卡详情页
  {
    path: '/admin/home/main/normalCardDetail',
    name: 'normalCardDetail',
    meta: {
      crumbTitle: 'router.userCard',
      meta: 'user_manger',
      category: 'user_card'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/user_manger/membershipCard/normalCardDetail'
      )
  },
  // 限次会员卡详情页
  {
    path: '/admin/home/main/limitCardDetail',
    name: 'limitCardDetail',
    meta: {
      crumbTitle: 'router.limitTimes',
      meta: 'user_manger',
      category: 'user_card'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/user_manger/membershipCard/limitCardDetail'
      )
  },
  // 等级会员卡详情页
  {
    path: '/admin/home/main/gradeCardDetail',
    name: 'gradeCardDetail',
    meta: {
      crumbTitle: 'router.GradeCard',
      meta: 'user_manger',
      category: 'user_card'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/user_manger/membershipCard/gradeCardDetail'
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
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/user_manger/membershipCard/Cardholder'
      )
  },
  // 充值明细
  {
    path: '/admin/home/main/refillDetails',
    name: 'refillDetails',
    meta: {
      crumbTitle: 'router.Cardholder',
      meta: 'user_manger',
      category: 'user_list'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/user_manger/membershipCard/refillDetails'
      )
  },
  // 充值明细子路由
  {
    path: '/admin/home/main/refillDetailsItem',
    name: 'refillDetailsItem',
    meta: {
      crumbTitle: 'router.refillDetailsItem',
      meta: 'user_manger',
      category: 'user_list'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/user_manger/membershipCard/refillDetails'
      )
  },
  // 领取详情
  {
    path: '/admin/home/main/receivingDetails',
    name: 'receivingDetails',
    meta: {
      crumbTitle: 'router.Cardholder',
      meta: 'user_manger',
      category: 'user_card'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/user_manger/membershipCard/receivingDetails'
      )
  },
  // 激活审核
  {
    path: '/admin/home/main/activateAudit',
    name: 'activateAudit',
    meta: {
      crumbTitle: 'router.Cardholder',
      meta: 'user_manger',
      category: 'user_card'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/user_manger/membershipCard/activateAudit'
      )
  },
  // 查看订单
  {
    path: '/admin/home/main/viewOrders',
    name: 'viewOrders',
    meta: {
      crumbTitle: 'router.Cardholder',
      meta: 'user_manger',
      category: 'user_card'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/user_manger/membershipCard/viewOrders'
      )
  },
  // 标签管理
  {
    path: '/admin/home/main/labelManagement',
    name: 'user_tag',
    meta: {
      crumbTitle: 'router.labelManagement',
      meta: 'user_manger'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/user_manger/labelManagement/labelManagement'
      )
  },
  // 积分管理
  {
    path: '/admin/home/main/integralManagement',
    name: 'score',
    meta: {
      crumbTitle: 'router.integralManagement',
      meta: 'user_manger'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/user_manger/integralManagement/integralManagement'
      )
  },
  // 查看签到会员
  {
    path: '/admin/home/main/viewSigninMembers',
    name: 'viewSigninMembers',
    meta: {
      crumbTitle: 'router.viewSigninMembers',
      meta: 'user_manger',
      category: 'user_list'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/user_manger/integralManagement/viewSigninMembers'
      )
  },
  // 积分说明
  {
    path: '/admin/home/main/integralDescription',
    name: 'integralDescription',
    meta: {
      crumbTitle: 'router.integralDescription',
      meta: 'user_manger',
      category: 'score'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/user_manger/integralManagement/integralDescription'
      )
  }
]

export default routes
