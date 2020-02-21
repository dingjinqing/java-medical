export const cn = {
  overviewName: '商城概览',
  bindButtonNull: '关注',
  bindButtonNullMessage: '关注公众号，实时接收消息通知',
  bindButtonBind: '去绑定',
  bindButtonBindMessage: '当前账号已关注公众号，但未绑定，不能实时接收消息通知',
  bindButtonDelBind: '解除绑定',
  bindButtonDelBindMessage: '已关注公众号，实时接收消息通知',
  bindButtonDelMsg: '当前账号',
  imageMessage: '用手机扫下方二维码关注公众号，及时接收新订单提醒',
  imageTitle: '关注公众号',

  // 头部
  enUserTip: '营业',
  unUserTip: '未营业',
  userExpired: '已过期',
  experienceVersion: '体验版',
  basicEdition: '基础版',
  advancedVersion: '高级版',
  Ultimate: '旗舰版',
  title: '微铺宝电商运营',
  edition: '旗舰版',
  editionTip1: '当前版本为',
  editionTip2: '有效期至',
  renew: '我要续费',
  upgrade: '版本升级',
  shareShop: '分享店铺',
  shareDefault: '请授权小程序',
  shareTitle: '扫一扫，分享给好友吧~',
  shareDownload: '下载二维码',
  copy: '复制',

  // 代办事项
  agencyTitle: '待办事项',
  agencyCustom: '自定义',
  agencyTip: '需选择5个待办事项',
  checkData: [
    '待发货订单',
    '待处理退款退货',
    '已售罄商品',
    '商品评价待审核',
    '待提货订单'
  ],
  checkList: [
    {
      value: 1,
      label: '待发货订单',
      isCheck: false,
      link: '/admin/home/main/orders/waiting?orderStatus=3'
    },
    {
      value: 2,
      label: '待处理退款退货',
      isCheck: false,
      link: '/admin/home/main/orders/refund/list'
    },
    {
      value: 3,
      label: '已售罄商品',
      isCheck: false,
      link: '/admin/home/main/goodsManage/goodsForSaleOut'
    },
    {
      value: 4,
      label: '商品评价待审核',
      isCheck: false,
      link: '/admin/home/main/goodsManage/evaluationManagement?activeName=second'
    },
    {
      value: 5,
      label: '待提货订单',
      isCheck: false,
      link: '/admin/home/main/orders/self'
    },
    {
      value: 6,
      label: '分销员待审核',
      isCheck: false,
      link: '/admin/home/main/distributorCheck'
    },
    {
      value: 7,
      label: '会员卡激活待审核',
      isCheck: false,
      link: ''
    },
    {
      value: 8,
      label: '分销提现待审核',
      isCheck: false,
      link: '/admin/home/main/withdrawDepositCheck'
    },
    {
      value: 9,
      label: '服务评价待审核',
      isCheck: false,
      link: ''
    }
  ],

  // 数据展示
  dataTitle: '数据展示',
  dataTip1:
    '访问人数：统计时间内，店铺所有页面（包括店铺主页、单品页、会员主页等）被访问的去重人数，一个人在统计时间范围内访问多次只记为一个',
  dataTip2:
    '下单笔数：统计时间内，下单成功的订单数，一个订单对应唯一一个订单号',
  dataTip3:
    '下单人数：统计时间内，成功下单的客户数，一人多次下单记为一人（不剔除退款订单）',
  dataTip4:
    '支付订单：统计时间内，成功付款的订单数，一个订单对应唯一一个订单号（拼团在成团时计入付款订单，货到付款在发货时计入付款订单，不剔除退款订单）',
  dataTip5:
    '支付金额(元)：统计时间内，所有付款订单金额之和（包括微信支付、余额、积分、会员卡，拼团在成团时计入付款金额，货到付款在发货时计入付款金额，不剔除退款金额）',
  dataTip6: '访问-下单转化率：统计时间内，下单人数/访客数',
  dataTip7: '下单-支付转化率：统计时间内，付款人数/下单人数',
  dataTip8: '访问-支付转化率：统计时间内，付款人数/访客数',
  userVisitNum: '访问人数',
  orderNum: '单笔下单',
  orderUserNum: '下单人数',
  paidOrderNum: '支付单数(笔数)',
  totalPaidSum: '支付金额(元)',
  paidUserNum: '付款人数',
  uv2paid: '访问-支付转化率',
  uv2order: '访问-下单转化率',
  order2paid: '下单-支付转化率',
  options: [
    {
      value: 0,
      label: '今日'
    },
    {
      value: 1,
      label: '昨日'
    },
    {
      value: 7,
      label: '近一周'
    },
    {
      value: 30,
      label: '近一个月'
    },
    {
      value: 90,
      label: '近三个月'
    }
  ],

  // 功能推荐
  functionTitle: '功能推荐',
  functionList: [
    {
      icon: '/image/admin/new_ov/drpt.png',
      title: '多人拼团',
      link: '/admin/home/main/spellGroup'
    },
    {
      icon: '/image/admin/new_ov/fx.png',
      title: '分销',
      link: '/admin/home/main/distribution'
    },
    {
      icon: '/image/admin/new_ov/hyzl.png',
      title: '好友助力',
      link: '/admin/home/main/friendHelp'
    },
    {
      icon: '/image/admin/new_ov/hdyl.png',
      title: '开屏有礼',
      link: '/admin/home/main/openScreen/list'
    },
    {
      icon: '/image/admin/new_ov/kj.png',
      title: '砍价',
      link: '/admin/home/main/bargain'
    },
    {
      icon: '/image/admin/new_ov/ptcj.png',
      title: '拼团抽奖',
      link: '/admin/home/main/lotteryDraw'
    },
    {
      icon: '/image/admin/new_ov/yhqlb.png',
      title: '优惠券礼包',
      link: '/admin/home/main/couponPackage'
    },
    {
      icon: '/image/admin/new_ov/zfyl.png',
      title: '支付有礼',
      link: '/admin/home/main/payReward'
    }
  ],

  // 店铺助手
  storeTitle: '店铺助手',
  storeRefresh: '刷新',
  storeItems: '项',
  storePending: '待处理',
  storeTabs: [
    {
      label: '全部',
      name: '1'
    },
    {
      label: '店铺',
      name: '2'
    },
    {
      label: '商品',
      name: '3'
    },
    {
      label: '订单',
      name: '4'
    },
    {
      label: '营销',
      name: '5'
    }
  ],
  storeMore: '查看更多',
  storeTip: '提醒',
  storeGo: '前往',
  storeCourse: '查看教程',
  storeIntroduction: '功能介绍',
  storeTask: '任务',
  storeConfig: '微信配置',
  storeRecommend: '推荐',
  // 待完成
  unShopTip1: '建议完善微信配置',
  unShopTipSet1: '未注册小程序',
  unShopTipSet2: '未配置小程序客服',
  unShopTipSet3: '未授权小程序',
  unShopTipSet4: '未开通微信支付',
  unShopTipSet5: '未配置微信支付',
  unShopTip2: '建议完成子账号设置',
  unShopTip3: '建议授权公众号',
  unShopTip4: '建议完成店铺首页装修',
  unShopTip5: '建议开启好物圈',
  unShopTip6: '建议开启客服',
  unStoreTip1: '运费模板未设置',
  unStoreTip2: '未添加商品',
  unStoreTip3: '个商品库存偏少',
  unStoreTip4: '个商品滞销',
  unStoreTip5: '个商品评价超过3天未审核',
  unStoreTip6: '未配置推荐商品',
  unStoreTip7: '未配置商家分类',
  unOrderTip1: '个订单超过3天未发货',
  unOrderTip2: '个订单退款申请超过3天未处理',
  unMarketTip1: '个分销员申请超过3天未处理',
  unMarketTip2: '个会员卡激活申请超过2天未处理',
  unMarketTip3: '优惠券',
  unMarketTip4: '即将发完',
  unMarketTip5: '类会员卡有',
  // 已完成
  tabInfo: [
    {
      title: '待完成',
      name: '1'
    },
    {
      title: '已完成',
      name: '2'
    }
  ],
  shop: '店铺',
  commodity: '商品',
  order: '订单',
  market: '营销',
  enShopTip1: '已完善微信配置',
  enShopTipSet1: '已注册小程序',
  enShopTipSet2: '已配置小程序客服',
  enShopTipSet3: '已授权小程序',
  enShopTipSet4: '已开通微信支付',
  enShopTipSet5: '已配置微信支付',
  enShopTip2: '已完成子账号设置',
  enShopTip3: '已授权公众号',
  enShopTip4: '已完成店铺首页装修',
  enShopTip5: '已开启好物圈',
  enShopTip6: '已开启客服',
  enStoreTip1: '已设置运费模板',
  enStoreTip2: '已添加商品',
  enStoreTip3: '商品库存充裕',
  enStoreTip4: '商品销售状况良好',
  enStoreTip5: '商品评价审核进度良好',
  enStoreTip6: '已配置推荐商品',
  enStoreTip7: '已配置商家分类',
  enOrderTip1: '订单发货进度良好',
  enOrderTip2: '退款处理进度良好',
  enMarketTip1: '个分销员申请超过3天未处理',
  enMarketTip2: '会员卡激活审核进度良好',
  enMarketTip3: '没有库存偏小(小于指定值)的优惠券',
  enMarketTip4: '分销员审核进度良好',

  // 公告
  noticeTitle: '公告',
  noticeMore: '更多',

  // 营销日历
  calendarTitle: '营销日历',

  // 更多服务
  serveTitle: '更多服务',
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
