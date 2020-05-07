export const en = {
  // tabs
  addLotteryDraw: 'Add LotteryDraw',
  editLotteryDraw: 'Edit LotteryDraw',
  tabInfo: [{
    title: 'All',
    name: '0'
  },
  {
    title: 'In Progress',
    name: '1'
  },
  {
    title: 'Not Started',
    name: '2'
  },
  {
    title: 'Overdue',
    name: '3'
  },
  {
    title: 'Disable',
    name: '4'
  }
  ],

  // 搜索
  activityName: 'activityName',
  activityNameTip: 'Please enter the activity name',
  validDate: 'Period of validity',
  select: 'Search',

  // 表格
  validity: 'validity',
  to: 'to',
  status: 'status',
  goodsCount: 'Number of valid products',
  limitAmount: 'Minimum group size',
  minJoinNum: 'Minimum number of people required for prize',
  joinUserCount: 'Number of participants',
  groupUserCount: 'Regiment size',
  groupCount: 'Number of clusters',
  drawUserCount: 'Winning user',
  option: 'options',
  edit: 'Edit',
  share: 'Share',
  stop: 'Stop',
  start: 'Start',
  order: 'View active orders',
  user: 'Participating users',
  userDetail: 'Get new user details',
  groupDetail: 'Opening details',
  delete: 'Delete',
  effect: 'Activity effect data',

  // 添加
  validDateTip: 'Award at the end of the event',
  joinNum: 'Minimum number of people in the prize pool',
  joinNumTip: 'If the minimum number of members required for each prize pool is less than this number, all participating users will not win the prize. Each product corresponds to a prize pool',
  payMoney: 'Commodity amount',
  money: 'yuan',
  payMoneyTip: 'The amount of goods to be paid for placing an order and participating in the group lottery',
  maxJinNum: 'Maximum number of participants',
  maxJinNumTip: 'The maximum number of lottery groups that each user can participate in during the activity time',
  openLimit: 'Maximum opening quantity',
  openLimitTip: 'During the activity time, each user can open the maximum number of lottery groups',
  limitAmountTip: 'The minimum number of members of each lottery group. After the group is formed, all participating users in the group can participate in the lottery',
  toNumShow: 'Minimum number of exhibitors',
  toNumShowTip: 'During the activity time, when the number of participating users reaches the set number, the front end of the applet displays the number of participating users',
  example: 'View examples',
  reward: 'Encouragement Award',
  rewardTip1: 'Surplus',
  rewardTip2: 'num',
  rewardTip3: 'Unlimited inventory',
  rewardTip4: 'Receive',
  rewardTip5: 'Point exchange',
  goodsId: 'Active commodities',
  selectGood: 'Choose commodity',
  goodsTip1: 'Add up to 20 items, selected',
  goodsTip2: 'commodity',
  goodsName: 'Commodity name',
  goodsPrice: 'Original price of commodity',
  goodsNumber: 'Commodity inventory',
  addSuccess: 'Add Success',
  editSuccess: 'Edit Success',
  templateData: `
  <div style="line-height: 1.5;">
    <p>Participation steps</p>
    <p>1.On the list page of low price lottery products, click the product to enter the product details page, and enter the order settlement page through the opening group entrance of the order. After the payment is successful, share it to wechat friends according to the page prompts；</p>
    <p>2.Friends check the activity status through the landing page of the app, complete the payment and participate in the group；</p>
    <p>3.If the number of payers reaches the threshold within the validity period, all users in the group will be eligible for the lottery and wait for the result of winning the prize to be announced；</p>
    <p>4.The winning results will be announced at the end of the activity. All winning orders will enter the delivery process. Users who fail to win the prize and those who fail to form a group will be fully refunded to the original payment account。</p>
    <p>Participation rules</p>
    <p>1.During the activity, only one order can be purchased for each group commodity in the same account；</p>
    <p>2.The inventory of the lottery goods is limited. If the rush purchase fails or the delivery fails due to insufficient inventory, the order will be fully refunded to the original payment account。</p>
  </div>
`,

  // 活动订单
  orderSn: 'Order number',
  isGroup: 'Is it a group',
  isYes: 'yes',
  isNo: 'no',
  consignee: 'Consignee information',
  isWinDraw: 'Is it a prize',
  createTime: 'Order time',
  codeCount: 'Number of lottery codes',
  orderStatusName: 'Order status',

  // 参与用户
  nickName: 'User nickname',
  nickNameTip: 'Please enter user nickname',
  startTime: 'Participation time',
  orderSnTip: 'Please enter the order number',
  mobile: 'mobile',
  mobileTip: 'Please enter your mobile number',
  minInviteUserCount: 'Number of invited users',
  groupId: 'group ID',
  groupIdTip: 'Please enter group Id',
  grouped: 'Agglomeration state',
  groupedTip: 'select',
  isGrouper: 'is Grouper',
  resetSelect: 'Reset filter',
  username: 'nickName',
  groupTime: 'group Time',

  // 用户明细
  query: 'query',

  // 成团明细
  groupName: 'Colonel nickname',
  groupNameTip: 'Please enter the leader is nickname',
  openGroupTime: 'Opening time',
  grouperMobile: 'Head mobile number',

  // 活动数据效果
  legendData: [
    'Number of payment orders',
    'Number of participating users',
    'Group users',
    'Number of new users'
  ],
  statusList: [{
    value: null,
    label: 'Please select'
  },
  {
    value: true,
    label: 'Has formed a group'
  },
  {
    value: false,
    label: 'Unformed'
  }
  ],
  groupList: [{
    value: '',
    label: 'Please select'
  }, {
    value: true,
    label: 'Yes'
  }, {
    value: false,
    label: 'No'
  }]
}
