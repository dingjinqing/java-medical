export const cn = {
  userOverview: '用户概览',
  modelAnalysis: 'RFM模型分析',

  // 客户概况及趋势
  userOverviewAndTrend: '客户概况及趋势',
  year: '年',
  month: '月',
  day: '日',
  compareLastMonth: '较前一月',
  timeRange: [
    { value: 1, label: '最新1天' },
    { value: 7, label: '最新7天' },
    { value: 30, label: '最新30天' }
  ],
  visitNumber: '访客数',
  userNumber: '积累用户数',
  userTradeNumber: '用户成交数',
  content1: '筛选时间内，店铺所有页面被访问的去重人数，一个人在筛选时间范围内访问多次只记为一个',
  content2: '截至到筛选时间的最后一天，店铺的会员累积人数',
  content3: '筛选时间内，在店铺中付款成功的去重客户数，一个人在筛选时间范围内付款多次只记为一个',

  // 用户活跃
  userActive: '用户活跃',
  rate: '占比',
  visitMemberNumber: '访问会员数',
  visitTips: '筛选时间内，访问过店铺的用户数量,一人多次访问记为一人',
  visitMemberRate: '访问会员数占比',
  visiCoumpute: '筛选时间内，访问用户数 / 累积用户数',

  getCouponNumber: '领券会员数',
  getCouponTips: '筛选时间内，领取了优惠券的用户数，一人多次领券记为一人',
  getCouponRate: '领券会员数占比',
  getCouponCoumpute: '筛选时间内，领券用户数/ 访问用户数',

  addBuyMember: '加购会员数',
  addBuyMemberTips: '筛选时间内，将商品添加购物车的用户数，一人多次添加购物车记为一人',
  addBuyMemberRate: '加购会员数占比',
  addBuyMemberCoumpute: '筛选时间内，加购用户数/ 访问用户数',

  tradeSuccessNumber: '成交会员数',
  tradeSuccessNumberTips: '筛选时间内，付款成功的用户数，一人多次付款成功记为一人',
  tradeSuccessNumberRate: '成交会员数占比',
  tradeSuccessNumberCoumpute: '筛选时间内，成交用户数/ 访问用户数',

  // 会员统计
  memberStatistics: '会员统计',
  compareLastDay: '较前一日',
  memberTipsList: [
    {
      title: '累积会员数',
      content: '截至到筛选时间的最后一天，店铺的会员累积人数'
    },
    {
      title: '新增会员数',
      content: '筛选时间内，通过领取会员卡，新成为会员的客户数量'
    },
    {
      title: '升级会员数',
      content: '筛选时间内，通过会员规则升级的会员数量，一人多次升级记为一人'
    },
    {
      title: '储值会员数',
      content: '筛选时间内，进行储值的会员数量，一人多次储值记为一人'
    }
  ],
  cumulateMemberNumber: '累积会员数',
  addMemberNumber: '新增会员数',
  updateMemberNumber: '升级会员数',
  chargeMemberNumber: '储值会员数',

  // 用户成交分析
  userAnalysis: '用户成交分析',
  customerType: '客户类型',
  customerNumber: '客户数',
  customerNumberRate: '客户数占比',
  singlePrice: '客单价',
  payMoney: '付款金额',
  visitToPayRate: '访问-付款转化率',
  visitToPayRates: '访问付款转化率',
  allTradeUser: '全部成交用户',
  tradeIndex: '趋势指标选择',
  newCustomer: '新成交客户数',
  oldCustomer: '老成交客户数',
  newSinglePrice: '新成交客单价',
  oldSinglePrice: '老成交客单价',
  newPayMoney: '新成交付款金额',
  oldPayMoney: '老成交付款金额',
  newTransformRate: '新成交访问-付款转化率',
  oldTransformRate: '老成交访问-付款转化率',
  total: '总量',

  // 用户复购趋势
  userBuyAgainTrend: '用户复购趋势',
  naturalWeek: '自然周',
  selectWeek: '选择周',
  userBuyAgainRate: '客户复购率',

  // RFM模型分析
  RFMInstructions: '通过分析用户的最近消费时间(R)、消费频次(F)、消费金额(M)，可有效将用户分层。消费频次越大、消费金额越高的客户，其客户生命价值(LTV)越高。通过营销活动、会员体系等运营 方式，可有效提升用户贡献。',
  lastPayTime: '最近付款时间在 ',
  selectDte: '选择日期',
  beforeTradeData: '之前的成交客户数据',
  queryIndex: '查询指标：',
  search: '查询',
  consumeFrequency: '消费频次(F)',
  lastCustomeTime: '最近消费时间(R)',
  summation: '合计',
  queryList: [
    { name: '用户数/占比', content: '在查询时间内各类已成交的用户数/占比' },
    { name: '累计支付金额(元) ', content: '在查询时间内各类用户累计支付金额' },
    { name: '客单价', content: '在查询时间内各区间的客单价' }
  ]

}
