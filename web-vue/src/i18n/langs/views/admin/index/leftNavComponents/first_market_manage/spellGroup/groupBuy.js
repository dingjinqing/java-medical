export const cn = {
  // 多人拼团活动效果
  number: '数量',
  legendData: [
    '活动实付总金额',
    '活动优惠总金额',
    '费效比',
    '新成交用户数',
    '老成家用户数'
  ],
  oldNumberUsers: '老成交用户数',
  oldNumberUsersComment: '在店铺有过付款订单，参与该活动的用户数',
  numberNewTransactions: '新成交用户数',
  numberNewTransactionsComment: '活动带来的首次在店铺下单的用户数',
  costBenefitRatio: '费效比',
  costBenefitRatioComment: '活动优惠总金额/活动实付总金额',
  totalDiscountAmount: '活动优惠总金额(元)',
  totalDiscountAmountComment: '活动优惠总金额',
  totalAmountPaid: '活动实付总金额(元)',
  totalAmountPaidComment: '活动订单实际付款总金额(包括账户余额、会员卡余额及微信支付，不包含退款部分)',
  screeningDates: '筛选日期',
  selectTime: '选择开始时间',
  // 退款失败订单
  orderSource: '订单来源',
  // 拼团订单列表
  paymentAmount: '支付金额',
  buyerName: '下单人姓名',
  shippingAddress: '收货地址',
  orderTime: '下单时间',
  consigneeInfo: '收货人信息',
  consigneeMobile: '收件人手机号',
  consigneeName: '收件人姓名',
  orderStatus: '订单状态',
  orderStatusArr: {
    null: '全部订单',
    1: '待付款',
    2: '订单取消',
    3: '订单关闭',
    4: '代发货/待核销',
    5: '已发货',
    6: '已收货/已自提',
    7: '订单完成',
    8: '退货中',
    9: '退货完成',
    10: '退款中',
    11: '退款完成',
    12: '送礼完成'
  },
  // 添加拼团活动
  groupBuyActivity: '拼团活动',
  groupBuyActivityComment: '开关默认关闭，开启开关，则用户可以申请为店铺分销员，分销员邀请用户注册产生订单，购买者邀请人可获得佣金奖励。关闭开关，手机端个人中心”分销中心“菜单隐藏，用户下单，邀请人不再产生佣金奖励，系统分销机制关闭，邀请不再记录邀请关系。 ',
  selectGoods: '选择商品',
  commanderDiscounts: '团长优惠',
  commanderDiscountsComment1: '开启团长(开团人)优惠后，团长将享受更优惠价格，有助于提高开团率和成团率。 ',
  commanderDiscountsComment2: '默认成团的团长也能享受团长优惠，为避免不必要的损失，请谨慎设置  ',
  discountsOption: '优惠设置',
  goodsNmaeProduct: '商品规格',
  originalPrice: '原价',
  groupBuyPrice: '拼团价',
  commanderPrice: '团长价',
  originalStock: '原库存',
  groupBuyStock: '拼团库存',
  batchOption: '批量设置',
  limitAmountComment: '不可小于2人,保存后不可编辑',
  orderGoodsNum: '下单商品数量',
  orderGoodsNumComment1: '单次下单购买拼团商品数量最小',
  orderGoodsNumComment2: '单次下单购买拼团商品数量最大',
  orderGoodsNumComment3: '请填写正整数，不填或为0表示不限制数量',
  jian: '件',
  jionLimit: '参团限制',
  joinLimitComment1: '每人最多参加',
  joinLimitComment2: '次新团 ',
  joinLimitComment3: '默认为0，0表示不限制数量。仅限制参与其他用户所开的团的数量',
  openLimit: '开团限制',
  openLimitComment1: '每人最多开启',
  openLimitComment2: '默认为0，0表示不限制数量。仅限制同一用户的开团数量',
  openIsDefault: '开启默认成团',
  openIsDefaultComment: '开启默认成团后，24小时内人数未满的团，系统将会模拟“匿名买家”凑满人数，使该团成团。 你只需要对已付款参团的真实买家发货。建议合理开启，以提高成团率 ',
  shippingOption: '运费设置',
  freeShipping: '免运费',
  shippingOptionComment: '使用原商品运费模板',
  consolationPrize: '鼓励奖',
  addCoupon: '添加优惠卷',
  consolationPrizeComment1: '买家拼团失败后给予一定奖励，可提升买家复购',
  consolationPrizeComment2: '最多添加5张优惠券，已过期和已停用的优惠券不能添加',
  lastWeek: '最近一周',
  lastmonth: '最近一月',
  lastThreeMonths: '最近三月',
  // 拼团订单
  // 拼团活动
  activityName: '活动名称',
  activityType: '活动类型',
  addActivity: '添加活动',
  editActivity: '编辑活动',
  goodsName: '商品名称',
  validDate: '有效期',
  startDate: '开始日期',
  endDate: '结束日期',
  activityStatus: '活动状态',
  limitAmount: '成团人数',
  grouponOrderNum: '成团订单数',
  grouponType: [
    {
      value: 1,
      label: '普通拼团'
    }, {
      value: 2,
      label: '老带新拼团'
    }
  ],
  edit: '编辑',
  share: '分享',
  disable: '停用',
  enabled: '启用',
  delete: '删除',
  grouponDetailList: '参团明细',
  grouponOrderlist: '拼团订单',
  returnFailOrder: '退款失败',
  activityInfo: '活动详情分析',
  newUserList: '新用户列表',
  returnOrder: '退款失败订单',
  activityEchert: '活动效果',
  tabInfo: [{
    title: '全部拼团活动',
    name: '1'
  }, {
    title: '进行中',
    name: '2'
  }, {
    title: '未开始',
    name: '3'
  }, {
    title: '已过期',
    name: '4'
  }, {
    title: '已停用',
    name: '5'
  }],
  // 拼团详情-成团状态
  userMobileNumber: '用户手机号',
  mobileNumber: '手机号',
  userNickname: '用户昵称',
  nickname: '昵称',
  grouponState: '成团状态',
  commanderName: '团长名称',
  commanderMobile: '团长手机号',
  username: '参团用户名称',
  mobile: '参团用户手机号',
  isDefault: '默认成团',
  orderSn: '订单号',
  startTime: '参团时间',
  endTime: '成团时间',
  searchDataText: '查询',
  stateOptions: [{
    value: 0,
    label: '全部'
  }, {
    value: 1,
    label: '成团中'
  }, {
    value: 2,
    label: '已成团'
  }, {
    value: 3,
    label: '未成团'
  }]
}

export const en = {
  // 活动效果数据
  number: 'number',
  legendData: [
    'total amount paid for activities ',
    'total discount amount ',
    'cost-effectiveness ratio ',
    'number of new transactions ',
    'number of experienced households'
  ],
  oldNumberUsers: 'oldNumberUsers ',
  oldNumberUsersComment: 'number of users who have paid in the store,',
  numberNewTransactions: 'number of new transactions ',
  numberNewTransactionsComment: 'activities in order for the first time the number of users',
  costBenefitRatio: 'cost-benefit ratio',
  costBenefitRatioComment: 'the benefit total amount/the benefit actual amount of the activity',
  totalDiscountAmount: 'total amount of event discount (yuan)',
  totalDiscountAmountComment: 'activities discount total amount',
  totalAmountPaid: 'totalAmountPaid for activity ',
  totalAmountPaidComment: 'total amount of actual payment for activity order (including account balance, membership card balance and WeChat payment, excluding refund part)',
  screeningDates: 'screeningDates ',
  selectTime: 'select start time ',

  // 订单详情
  paymentAmount: 'Pay amount',
  buyerName: 'Next single name',
  shippingAddress: 'receiving address',
  orderTime: 'Time to place an order',
  consigneeInfo: 'Receipt Information',
  consigneeMobile: 'Recipient phone number',
  consigneeName: 'Recipient Name',
  orderStatus: 'Order Status',
  orderStatusArr: {
    null: 'All orders',
    1: 'To be paid',
    2: 'Order Cancellation',
    3: 'Order Close',
    4: 'Shipping/Pending Write-off',
    5: 'Shipped',
    6: 'Received/Self-Lifted',
    7: 'Order Complete',
    8: 'In return',
    9: 'Return Complete',
    10: 'In refund',
    11: 'Refund Completed',
    12: 'Gift Done' },
  // 添加拼团活动
  groupBuyActivity: 'groupon Activity',
  groupBuyActivityComment: 'The switch is turned off by default. When the switch is turned on, the user can apply for a store distributor. The distributor invites the user to register to generate an order, and the purchaser invites a commission reward. When the switch is turned off, the mobile terminal personal center "distribution center" menu is hidden, the user places an order, the inviter no longer generates a commission reward, the system distribution mechanism is closed, and the invitation no longer records the invitation relationship.',
  selectGoods: 'Select goods',
  commanderDiscounts: 'commander Discounts',
  commanderDiscountsComment1: 'After opening the group leader (opening group) discount, the head will enjoy more favorable prices, which will help increase the opening rate and group rate.',
  commanderDiscountsComment2: ' The head of the group can also enjoy the group leader\'s discount. To avoid unnecessary losses, please set it carefully.',
  discountsOption: 'Offer settings',
  goodsNmaeProduct: 'Product specifications',
  originalPrice: 'Original price',
  groupBuyPrice: 'Fighting price',
  commanderPrice: 'commander Price',
  originalStock: 'Original stock',
  groupBuyStock: 'groupon Stock',
  batchOption: 'Batch setting',
  orderGoodsNum: 'Order quantity',
  orderGoodsNumComment1: 'The minimum number of purchases for a single order',
  orderGoodsNumComment2: 'The largest number of purchase orders for a single order',
  orderGoodsNumComment3: 'Please fill in a positive integer, do not fill in or 0 to indicate no limit',
  jian: 'Piece',
  jionLimit: 'jion Limit',
  joinLimitComment1: 'Maximum participation per person',
  joinLimitComment2: 'Secondary group ',
  joinLimitComment3: 'The default is 0, 0 means no limit. Limit only the number of groups that participate in other users',
  openLimit: 'Open group limit',
  openLimitComment1: 'Maximize per person',
  openLimitComment2: 'The default is 0, 0 means no limit. Limit only the number of open groups for the same user',
  openIsDefault: 'Open the default group',
  openIsDefaultComment: 'After opening the default group, the number of people who are not full within 24 hours, the system will simulate the "anonymous buyer" to fill the number, so that the group will be a group. You only need to ship the real buyer who has paid for the group. Proposed to open reasonably to increase the group rate',
  shippingOption: 'Shipping setting',
  freeShipping: 'free shipping',
  shippingOptionComment: 'Use original product shipping template',
  consolationPrize: 'Encouragement Award',
  consolationPrizeComment1: 'After the buyer fails to fight, the group will give a certain reward, which can enhance the buyer\'s repurchase.',
  consolationPrizeComment2: 'Add up to 5 coupons, expired and disabled coupons cannot be added',
  addCoupon: 'add Coupon',
  // 拼团活动
  activityName: 'activityName',
  activityType: 'activityType',
  addActivity: 'addActivity',
  editActivity: 'editActivity',
  goodsName: 'goodsName',
  validDate: 'validDate',
  startDate: 'start Date',
  endDate: 'end Date',
  activityStatus: 'activityStatus',
  limitAmount: 'limitAmount',
  grouponOrderNum: 'grouponOrderNum',
  grouponType: [
    {
      value: 1,
      label: 'generalGroupon'
    }, {
      value: 2,
      label: 'OldUserGroupon'
    }
  ],
  edit: 'edit',
  share: 'share',
  disable: 'disable',
  enabled: 'enabled',
  delete: 'delete',
  grouponDetailList: 'grouponDetailList',
  grouponOrderlist: 'grouponOrderlist',
  newUserList: 'newUserList',
  returnFailOrder: 'returnFailOrder',
  activityInfo: 'activityInfo',
  tabInfo: [{
    title: 'all',
    name: '1'
  }, {
    title: 'in Progress',
    name: '2'
  }, {
    title: 'NotStarted',
    name: '3'
  }, {
    title: 'overdue',
    name: '4'
  }, {
    title: 'Disable',
    name: '5'
  }],
  userMobileNumber: 'user mobile',
  mobileNumber: 'mobile number',
  userNickname: 'user nickname',
  nickname: 'nickname',
  grouponState: 'groupon status',
  commanderName: 'commander name',
  commanderMobile: 'commander mobile',
  username: 'user name',
  mobile: 'user  mobile',
  isDefault: 'default finish',
  orderSn: 'orderSn',
  startTime: 'startTime',
  endTime: 'endTime',
  searchDataText: 'search',
  stateOptions: [{
    value: 0,
    label: 'all'
  }, {
    value: 1,
    label: 'Groupon proceed'
  }, {
    value: 2,
    label: 'Groupon end'
  }, {
    value: 3,
    label: 'Groupon failure'
  }]
}
