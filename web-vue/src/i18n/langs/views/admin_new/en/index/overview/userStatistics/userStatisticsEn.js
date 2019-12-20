export const en = {
  userOverview: 'User Overview',
  modelAnalysis: 'RFM Model Analysis',

  // 用户概况及趋势
  userOverviewAndTrend: 'Customer Overview and Trends',
  year: 'Year',
  month: 'Month',
  day: 'Day',
  compareLastMonth: 'Last Month',
  timeRange: [
    { value: 1, label: 'last 1 day' },
    { value: 7, label: 'last 7 days' },
    { value: 30, label: 'last 30 days' }
  ],
  visitNumber: 'Visitors',
  userNumber: 'Accumulated users',
  userTradeNumber: 'User Trades',
  content1: 'The number of deduplicated visitors who visited all pages of the store during the screening period. One visit multiple times during the screening period is counted as one',
  content2: 'As of the last day of the screening time, the cumulative number of members in the store',
  content3: 'The number of deduplicated customers who paid successfully in the store during the screening time, and one person pays multiple times during the screening time only as one',

  // 用户活跃
  userActive: 'User Active',
  rate: 'Proportion',
  visitMemberNumber: 'Visit Member Number',
  visitTips: 'The number of users who visited the store during the screening period, and multiple visits are counted as one',
  visitMemberRate: 'Proportion of Visiting Members',
  visiCoumpute: 'Visit users / Cumulative users during the filtering period',

  getCouponNumber: 'Number of Coupon Members',
  getCouponTips: 'The number of users who have received the coupon within the screening period, and one person receives the coupon multiple times as one person',
  getCouponRate: 'Proportion of coupon members',
  getCouponCoumpute: 'Number of coupon users / visit users during the filter period',

  addBuyMember: 'Add purchase members',
  addBuyMemberTips: 'The number of users who add items to the shopping cart within the filter time. One person adds the shopping cart multiple times as one person',
  addBuyMemberRate: 'Proportion of added members',
  addBuyMemberCoumpute: 'Add users / visit users during the filter period',

  tradeSuccessNumber: 'Number of Traded Members',
  tradeSuccessNumberTips: 'The number of users who have made a successful payment within the screening period. One person who makes multiple payments is counted as one',
  tradeSuccessNumberRate: 'Proportion of traded members',
  tradeSuccessNumberCoumpute: 'Number of traded users / visited users during the filtering period',

  // 会员统计
  memberStatistics: 'Member Statistics',
  compareLastDay: 'Last Day',
  memberTipsList: [
    {
      title: 'Accumulated Members',
      content: 'As of the last day of the screening time, the cumulative number of members in the store'
    },
    {
      title: 'New Members',
      content: 'Number of new customers who have become members by collecting membership cards within the screening period'
    },
    {
      title: 'Number of Upgraded Members',
      content: 'The number of members who have been upgraded through the membership rules during the screening period'
    },
    {
      title: 'Number of stored value members',
      content: 'The number of members who have stored the value during the screening period'
    }
  ],
  cumulateMemberNumber: 'Cumulative Member Number',
  addMemberNumber: 'Add Member Number',
  updateMemberNumber: 'Upgrade members',
  chargeMemberNumber: 'Number of stored value members',

  // 用户成交分析
  userAnalysis: 'User Deal Analysis',
  customerType: 'Customer Type',
  customerNumber: 'Number of customers',
  customerNumberRate: 'Percentage of customers',
  singlePrice: "Customer's price",
  payMoney: 'Payment Amount',
  visitToPayRate: 'Visit-Pay Conversion Rate',
  visitToPayRates: 'visit payment conversion rate',
  allTradeUser: 'All Trade Users',
  tradeIndex: 'Selection of trend indicator',
  newCustomer: 'Number of new customers',
  oldCustomer: 'Number of Old Customers',
  newSinglePrice: 'New Dealing Unit Price',
  oldSinglePrice: 'Old Dealer Unit Price',
  newPayMoney: 'New transaction payment amount',
  oldPayMoney: 'Old transaction payment amount',
  newTransformRate: 'New Deal Access-Payment Conversion Rate',
  oldTransformRate: 'Old Deal Access-Payment Conversion Rate',
  total: 'Total',

  // 用户复购趋势
  userBuyAgainTrend: 'User Repurchase Trend',
  naturalWeek: 'Natural Week',
  selectWeek: 'Select Week',
  userBuyAgainRate: 'Customer Repurchase Rate',

  // RFM模型分析
  RFMInstructions: "By analyzing the user's recent consumption time (R), consumption frequency (F), and consumption amount (M), users can be effectively stratified. Customers who consume more frequently and spend more money have higher customer life value (LTV). Through marketing activities, membership systems and other operating methods, user contributions can be effectively enhanced. ",
  lastPayTime: 'Last payment time is at',
  selectDte: 'Select Date',
  beforeTradeData: 'Customer data of previous deals',
  queryIndex: 'Query index:',
  search: 'Query',
  consumeFrequency: 'Consumption Frequency (F)',
  lastCustomeTime: 'Last Consumed Time (R)',
  summation: 'Total',
  queryList: [
    { name: 'Number of users / proportion', content: 'Number of users / proportion that have been traded in various types within the query time' },
    { name: 'Cumulative payment amount (yuan)', content: 'Cumulative payment amount of various users within the query time' },
    { name: 'Customer unit price', content: 'Customer unit price in each interval within the query time' }
  ]
}
