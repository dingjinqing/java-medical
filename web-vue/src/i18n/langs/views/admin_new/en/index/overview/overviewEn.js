export const en = {
  overviewName: 'Mall Overview',
  bindButtonNull: 'Follow',
  bindButtonNullMessage:
    'Follow the public number, receive notifications in real time',
  bindButtonBind: 'debinding',
  bindButtonBindMessage:
    'Unbound public number can not receive message notification in real time',
  bindButtonDelBind: 'Unbind',
  bindButtonDelBindMessage:
    'has been concerned about the public number, receiving notifications in real time',
  bindButtonDelMsg: 'current account',
  imageMessage:
    'Use your mobile phone to scan the QR code below to pay attention to the public number and receive new order reminders in time',
  imageTitle: 'Follow the public number',

  // 头部
  enUserTip: 'Do business',
  unUserTip: 'Un business',
  userExpired: 'Expired',
  experienceVersion: 'Experience Version',
  basicEdition: 'Basic Edition',
  advancedVersion: 'Advanced Version',
  Ultimate: 'Ultimate',
  title: 'WeiPuBo Electricity supplier operation',
  edition: 'Ultimate',
  editionTip1: 'The current version is',
  editionTip2: 'Valid until',
  renew: 'Want to renew',
  upgrade: 'Version upgrade',
  shareShop: 'Sharing',
  shareDefault: 'Failed to get QR code',
  shareTitle: 'Scan, Share with friends~',
  shareDownload: 'Download QR code',
  copy: 'Copy',

  // 代办事项
  agencyTitle: 'Agency Matters',
  agencyCustom: 'Custom',
  agencyTip: '5 to do items to be selected',
  checkData: [
    'To be shipped',
    'Pending disposal',
    'Sold out',
    'To be audited',
    'Waiting for delivery'
  ],
  checkList: [
    {
      value: 1,
      label: 'To be shipped',
      isCheck: false,
      link: '/admin/home/main/orders/waiting?orderStatus=3'
    },
    {
      value: 2,
      label: 'Pending disposal',
      isCheck: false,
      link: '/admin/home/main/orders/refund/list'
    },
    {
      value: 3,
      label: 'Sold out',
      isCheck: false,
      link: '/admin/home/main/goodsManage/goodsForSaleOut'
    },
    {
      value: 4,
      label: 'To be audited',
      isCheck: false,
      link: '/admin/home/main/goodsManage/evaluationManagement?activeName=second'
    },
    {
      value: 5,
      label: 'Waiting for delivery',
      isCheck: false,
      link: '/admin/home/main/orders/self'
    },
    {
      value: 6,
      label: 'Reviewed by distributor',
      isCheck: false,
      link: '/admin/home/main/distributorCheck'
    },
    {
      value: 7,
      label: 'Membership activation',
      isCheck: false,
      link: ''
    },
    {
      value: 8,
      label: 'Cash withdrawal',
      isCheck: false,
      link: '/admin/home/main/withdrawDepositCheck'
    },
    {
      value: 9,
      label: 'Service evaluation',
      isCheck: false,
      link: ''
    }
  ],

  // 数据展示
  dataTitle: 'Data Display',
  dataTip1:
    'Number of visitors: during the statistical time, the number of de duplication visitors to all pages of the store (including the home page of the store, single product page, member home page, etc.) is recorded as one for multiple visits by one person within the statistical time range.',
  dataTip2:
    'Number of orders placed: the number of orders placed successfully within the statistics time. An order corresponds to a unique order number.',
  dataTip3:
    'Number of orders: count the number of customers who have successfully placed an order within the time, and record one person for multiple orders (refund order will not be excluded)',
  dataTip4:
    'Payment order: count the number of orders that have been successfully paid within the time period. An order corresponds to a unique order number (the payment order is included when the group is formed, and the payment on delivery is included in the payment order when the shipment is made, and the refund order is not excluded).',
  dataTip5:
    'Payment amount (yuan): sum of all payment orders (including wechat payment, balance, points, membership card, payment amount will be included when the group is formed, payment amount will be included when the goods are delivered, and refund amount will not be excluded)',
  dataTip6:
    'Visit order conversion rate: number of orders / visitors within the statistical time',
  dataTip7:
    'Order payment conversion rate: number of payers / number of orders within the statistical time',
  dataTip8:
    'Visit - payment conversion rate: number of paid / number of visitors within the statistical time',
  userVisitNum: 'Number of visitors',
  orderNum: 'Single order',
  orderUserNum: 'Order number',
  paidOrderNum: 'Payment number',
  totalPaidSum: 'Payment amount',
  paidUserNum: 'Number of payments',
  uv2paid: 'Visit payment conversion rate',
  uv2order: 'Visit - order conversion rate',
  order2paid: 'Order payment conversion rate',
  options: [
    {
      value: 1,
      label: 'Today'
    },
    {
      value: 2,
      label: 'Yesterday'
    },
    {
      value: 7,
      label: 'Nearly a week'
    },
    {
      value: 30,
      label: 'Nearly a month'
    },
    {
      value: 90,
      label: 'Nearly three months'
    }
  ],

  // 功能推荐
  functionTitle: 'Function Recommendation',
  functionList: [
    {
      icon: '/image/admin/new_ov/drpt.png',
      title: 'Multiplayer group',
      link: '/admin/home/main/spellGroup'
    },
    {
      icon: '/image/admin/new_ov/fx.png',
      title: 'Retail',
      link: '/admin/home/main/distribution'
    },
    {
      icon: '/image/admin/new_ov/hyzl.png',
      title: 'Friend help',
      link: '/admin/home/main/friendHelp'
    },
    {
      icon: '/image/admin/new_ov/hdyl.png',
      title: 'Kaiping is polite',
      link: '/admin/home/main/openScreen/list'
    },
    {
      icon: '/image/admin/new_ov/kj.png',
      title: 'Bargain',
      link: '/admin/home/main/bargain'
    },
    {
      icon: '/image/admin/new_ov/ptcj.png',
      title: 'Assemble',
      link: '/admin/home/main/lotteryDraw'
    },
    {
      icon: '/image/admin/new_ov/yhqlb.png',
      title: 'Coupon pack',
      link: '/admin/home/main/couponPackage'
    },
    {
      icon: '/image/admin/new_ov/zfyl.png',
      title: 'Payment of courtesy',
      link: '/admin/home/main/payReward'
    }
  ],

  // 店铺助手
  storeTitle: 'Shop Assistant',
  storeRefresh: 'Refresh',
  storeItems: 'items',
  storePending: 'Pending',
  storeTabs: [
    {
      label: 'All',
      name: '1'
    },
    {
      label: 'Shop',
      name: '2'
    },
    {
      label: 'Goods',
      name: '3'
    },
    {
      label: 'Order',
      name: '4'
    },
    {
      label: 'Marketing',
      name: '5'
    }
  ],
  storeMore: 'View more',
  storeTip: 'Tip',
  storeGo: 'Go',
  storeCourse: 'View tutorial',
  storeTask: 'Task',
  storeRecommend: 'Recommend',
  // 待完成
  unShopTip1: 'Suggestions on improving wechat configuration',
  unShopTip2: 'It is recommended to complete the sub account setting',
  unShopTip3: 'Proposal to authorize public number',
  unShopTip4: 'It is suggested to finish the shop decoration',
  unShopTip5: 'It is recommended to open the circle',
  unShopTip6: 'Suggest opening customer service',
  unStoreTip1: 'Freight template not set',
  unStoreTip2: 'Item not added',
  unStoreTip3: 'Low inventory',
  unStoreTip4: 'Unsalable goods',
  unStoreTip5: 'Product evaluation has not been reviewed for more than 3 days',
  unStoreTip6: 'Recommendation not configured',
  unStoreTip7: 'Merchant classification is not configured',
  unOrderTip1: 'Order is not delivered for more than 3 days',
  unOrderTip2:
    'Order refund application has not been processed for more than 3 days',
  unMarketTip1:
    'Distributor application has not been processed for more than 3 days',
  unMarketTip2:
    'Membership card activation application has not been processed for more than 2 days',
  unMarketTip3: 'Coupon',
  unMarketTip4: 'Soon to be completed',
  unMarketTip5: 'cards have',
  // 已完成
  tabInfo: [
    {
      title: 'To be completed',
      name: '1'
    },
    {
      title: 'Completed',
      name: '2'
    }
  ],
  shop: 'Shop',
  commodity: 'Commodity',
  order: 'Order',
  market: 'Market',
  enShopTip1: 'WeChat configuration has been improved',
  enShopTip2: 'Sub account setting completed',
  enShopTip3: 'Authorized public address',
  enShopTip4: 'Shop decoration completed',
  enShopTip5: 'The circle has been opened',
  enShopTip6: 'Customer service enabled',
  enStoreTip1: 'Freight template set',
  enStoreTip2: 'Item added',
  enStoreTip3: 'Abundant commodity inventory',
  enStoreTip4: 'Good sales',
  enStoreTip5: 'Good progress of commodity evaluation and audit',
  enStoreTip6: 'Item configured',
  enStoreTip7: 'Merchant classification configured',
  enOrderTip1: 'Good order delivery progress',
  enOrderTip2: 'Refund processing progress is good',
  enMarketTip1:
    'Distributor application has not been processed for more than 3 days',
  enMarketTip2: 'Membership card activation audit progress is good',
  enMarketTip3: 'No coupon with small stock (less than the specified value)',
  enMarketTip4: 'Good progress of distributor audit',

  // 公告
  noticeTitle: 'Notice',
  noticeMore: 'more',

  // 营销日历
  calendarTitle: 'Marketing Calendar',

  // 更多服务
  serveTitle: 'More Services',
  serveList: [
    {
      icon: '/image/admin/new_ov/wangdian.png',
      title: '旺店通ERP',
      link: 'http://www.wangdian.cn/pc/erpCompany.html'
    },
    {
      icon: '/image/admin/new_ov/pos.png',
      title: '微铺宝POS',
      link: 'http://pos.wangdian.cn/'
    },
    {
      icon: '/image/admin/new_ov/ekuai.png',
      title: 'E快帮ERP',
      link: 'http://www.ekbyun.com/'
    },
    {
      icon: '/image/admin/new_ov/dashuju.png',
      title: '大数据',
      link: 'http://www.wangdian.cn/pc/data.html'
    },
    {
      icon: '/image/admin/new_ov/020.png',
      title: 'O2O',
      link: 'http://www.wangdian.cn/pc/o2o.html'
    },
    {
      icon: '/image/admin/new_ov/wms.png',
      title: '旺店通WMS',
      link: 'http://www.wangdian.cn/pc/wms.html'
    }
  ]
}
