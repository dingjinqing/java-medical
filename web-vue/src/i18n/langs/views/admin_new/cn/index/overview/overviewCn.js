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
  userTip: '营业',
  title: '微铺宝电商运营',
  edition: '旗舰版',
  editionTip1: '当前版本为',
  editionTip2: '有效期至',
  renew: '我要续费',
  upgrade: '版本升级',
  shareShop: '分享店铺',
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
      isCheck: false
    },
    {
      value: 2,
      label: '待处理退款退货',
      isCheck: false
    },
    {
      value: 3,
      label: '已售罄商品',
      isCheck: false
    },
    {
      value: 4,
      label: '商品评价待审核',
      isCheck: false
    },
    {
      value: 5,
      label: '待提货订单',
      isCheck: false
    },
    {
      value: 6,
      label: '分销员待审核',
      isCheck: false
    },
    {
      value: 7,
      label: '会员卡激活待审核',
      isCheck: false
    },
    {
      value: 8,
      label: '分销提现待审核',
      isCheck: false
    },
    {
      value: 9,
      label: '服务评价待审核',
      isCheck: false
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
      value: 1,
      label: '今日'
    },
    {
      value: 2,
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
      link: ''
    },
    {
      icon: '/image/admin/new_ov/kj.png',
      title: '砍价',
      link: ''
    },
    {
      icon: '/image/admin/new_ov/ptcj.png',
      title: '拼团抽奖',
      link: '/admin/home/main/lotteryDraw'
    },
    {
      icon: '/image/admin/new_ov/yhqlb.png',
      title: '优惠券礼包',
      link: ''
    },
    {
      icon: '/image/admin/new_ov/zfyl.png',
      title: '支付有礼',
      link: '/admin/home/main/admin/home/main/payReward'
    }
  ],

  // 店铺助手
  storeTitle: '店铺助手',

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
