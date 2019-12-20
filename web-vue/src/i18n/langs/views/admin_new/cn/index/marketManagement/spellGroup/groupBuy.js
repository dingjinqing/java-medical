export const cn = {

  // 校验
  activityNameRequiredRules: '请输入活动名称',
  goodsIdRequireRules: '请选择活动商品',
  validityDateRules: '请选择时间',
  limitAmountRequireRules: '请填写成团人数',
  joinLimitRequireRules: '请填写参团限制',
  openLimitRequireRules: '请填写开团限制',
  lengthMax20: '长度在 1 到 20 个字符',
  // 新用户明细
  invitePeople: '邀请人',
  newUserId: '新用户id',
  newUserNickname: '新用户昵称',
  newUserMobile: '新用户手机号',
  registrationTime: '注册时间',
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
  totalDiscountAmount: '活动优惠总金额({0})',
  totalDiscountAmountComment: '活动优惠总金额',
  totalAmountPaid: '活动实付总金额({0})',
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
  groupBuyActivityComment: '所有用户都可以开团，但只有新用户才能参团，保存后不可编辑 ',
  selectGoods: '选择商品',
  commanderDiscounts: '团长优惠价',
  commanderDiscountsComment1: '开启团长(开团人)优惠后，团长将享受更优惠价格，有助于提高开团率和成团率。 ',
  commanderDiscountsComment2: '默认成团的团长也能享受团长优惠，为避免不必要的损失，请谨慎设置  ',
  discountsOption: '优惠设置',
  goodsNmaeProduct: '商品规格',
  originalPrice: '原价',
  groupBuyPrice: '拼团价',
  commanderPrice: '团长价',
  originalStock: '原库存',
  groupBuyStock: '拼团库存',
  noData: '暂无数据',
  moreSettings: '更多设置：',
  activitySharing: '活动分享',
  batchOption: '批量设置',
  limitAmountComment: '不可小于2人,保存后不可编辑',
  orderGoodsNum: '下单商品数量',
  orderGoodsNumComment1: '单次下单购买拼团商品数量最小',
  orderGoodsNumComment2: '单次下单购买拼团商品数量最大',
  orderGoodsNumComment3: '请填写正整数，不填或为0表示不限制数量',
  jian: '件',
  joinLimit: '参团限制',
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
  addCoupon: '添加优惠券',
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
  option: '操作',
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
    name: '0'
  }, {
    title: '进行中',
    name: '1'
  }, {
    title: '未开始',
    name: '2'
  }, {
    title: '已过期',
    name: '3'
  }, {
    title: '已停用',
    name: '4'
  }],
  changeStatusComment: '此操作将启用该活动, 是否继续?',
  deleteComment: '此操作将删除该活动, 是否继续?',
  confirm: '确定',
  cancel: '取消',
  cancelMessage: '已取消',
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
  }],
  moreConfigure: '展开更多配置',
  packUpConfigure: '收起更多配置'
}
