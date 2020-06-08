export const en = {
  noData: 'No relevant data available',
  ExchangeFrequency: 'Frequency of Commodity Exchange (Frequency)',
  freeshiptimes: 'Freeship times',
  servicestimes: 'Number of store services (times)',
  Balanceyuan: 'Balance (yuan)',
  state: 'state',
  type: 'type',
  Member: 'Member',
  memberName: 'memberName',
  CardNumber: 'Membership Card Number',
  membershipcard: 'Type',
  Collectiontime: 'Time',
  placeNameNum: 'Please enter a nickname',
  distributionData: [
    {
      title: 'Quantity of returned orders',
      content: 'Not yet'
    },
    {
      title: 'Total Amount of Return Commodities (RMB)',
      content: '0.00'
    },
    {
      title: 'Total amount of rebate Commission (RMB)',
      content: '0.00'
    },
    {
      title: 'Total commission amount has been proposed (RMB)',
      content: '0.00'
    },
    {
      title: 'Number of subordinate users',
      content: '0'
    },
    {
      title: 'Distributor rank',
      content: 'Distributor Testing'
    },
    {
      title: 'Distributor grouping',
      content: '/'
    }
  ],
  transactionData1: [
    {
      title: 'Last order time',
      content: 'No order yet',
      tip: 'Last time the user placed an order (including refunded orders)',
      value: 'lastOrderTime',
      linkName: ''
    },
    {
      title: 'Customer unit price',
      content: '0',
      tip: 'Average consumption amount per order (including refunded orders)',
      value: 'unitPrice',
      linkName: ''
    },
    {
      title: 'Accumulated consumption amount',
      content: '0',
      tip: 'Accumulated consumption amount of users in the mall (paid part, including refunded order amount)',
      value: 'totalMoneyPaid',
      linkName: ''
    },
    {
      title: 'Cumulative consumption orders',
      content: '0',
      tip: 'Cumulative consumption orders of users in the mall (paid part, including refunded orders)',
      value: 'orderNum',
      linkName: ''
    },
    {
      title: 'Accumulated refund amount',
      content: '0',
      tip: 'Accumulated refund amount of users in the mall',
      value: 'returnOrderMoney',
      linkName: ''
    },
    {
      title: 'Cumulative refund orders',
      content: '0',
      tip: 'Cumulative number of refund orders of users in the mall (orders with refund behavior)',
      value: 'returnOrderNum',
      linkName: ''
    }
  ],
  transactionData2: [
    {
      title: 'Last order time',
      content: 'No order yet',
      tip: 'The last time the user places an order for physical goods (including refunded orders and pending orders)',
      value: 'lastOrderTime',
      linkName: 'order'
    },
    {
      title: 'Customer unit price',
      content: '0',
      tip: 'Average order consumption amount of physical goods purchased by users (including refunded orders)',
      value: 'unitPrice',
      linkName: 'order'
    },
    {
      title: 'Accumulated consumption amount',
      content: '0',
      tip: 'Accumulated consumption amount of physical goods purchased by users in the mall (paid part, including refunded order amount)',
      value: 'totalMoneyPaid',
      linkName: 'order'
    },
    {
      title: 'Cumulative consumption orders',
      content: '0',
      tip: 'Cumulative consumption orders of physical goods purchased by users in the mall (paid orders, including refunded orders)',
      value: 'orderNum',
      linkName: 'order'
    },
    {
      title: 'Accumulated refund amount',
      content: '0',
      tip: 'Amount refunded after the user purchases physical goods in the mall',
      value: 'returnOrderMoney',
      linkName: 'order'
    },
    {
      title: 'Cumulative refund orders',
      content: '0',
      tip: 'Total number of orders refunded by users after purchasing physical goods in the mall',
      value: 'returnOrderNum',
      linkName: 'order'
    }
  ],
  // transactionData3: [
  //   {
  //     title: 'Last order time',
  //     content: 'No order yet',
  //     tip: 'The last time a user places an order for a non physical product (including refunded order and pending order)',
  //     value: 'lastOrderTime',
  //     linkName: 'order'
  //   },
  //   {
  //     title: 'Customer unit price',
  //     content: '0',
  //     tip: 'Average consumption amount of each purchase of non physical goods (including refunded orders)',
  //     value: 'unitPrice',
  //     linkName: 'order'
  //   },
  //   {
  //     title: 'Accumulated consumption amount',
  //     content: '0',
  //     tip: 'Accumulated consumption amount of non physical goods purchased by users in the mall (paid part, including refunded order amount)',
  //     value: 'totalMoneyPaid',
  //     linkName: 'order'
  //   },
  //   {
  //     title: 'Cumulative consumption orders',
  //     content: '0',
  //     tip: 'Cumulative consumption orders of non physical goods purchased by users in the mall (paid orders, including refunded orders)',
  //     value: 'orderNum',
  //     linkName: 'order'
  //   },
  //   {
  //     title: 'Accumulated refund amount',
  //     content: '0',
  //     tip: 'Amount refunded by users after purchasing non physical goods in the mall',
  //     value: 'returnOrderMoney',
  //     linkName: 'order'
  //   },
  //   {
  //     title: 'Cumulative refund orders',
  //     content: '0',
  //     tip: 'Total number of orders refunded by users after purchasing non physical goods in the mall',
  //     value: 'returnOrderNum',
  //     linkName: 'order'
  //   }
  // ],
  transactionData3: [
    {
      title: 'Number of purchase orders for membership card',
      content: '0',
      tip: 'Number of orders (including refunded orders) that users purchase membership cards in the mall',
      value: 'memberCardPurchaseOrderNum',
      linkName: ''
    },
    {
      title: '会员卡续费单数',
      content: '0',
      tip: 'Renewal order of membership card',
      value: 'memberCardRenewOrderNum',
      linkName: ''
    },
    {
      title: '会员卡充值单数',
      content: '0',
      tip: 'Top up order of membership card',
      value: 'memberCardChargeOrderNum',
      linkName: ''
    },
    {
      title: '优惠券礼包购买订单数',
      content: '0',
      tip: 'Number of purchase orders for coupon package',
      value: 'couponPackPurchaseOrderNum',
      linkName: ''
    }
  ],
  transactionData4: [
    {
      title: 'Last order time',
      content: 'No order yet',
      tip: 'The last time the user places an order for service reservation (including refunded order, to be served, to be paid order)',
      value: 'lastOrderTime',
      linkName: ''
    },
    {
      title: 'Number of orders successfully paid',
      content: '0',
      tip: 'The number of paid orders (paid orders, including refunded orders) that users make service appointments in the mall',
      value: 'orderNum',
      linkName: ''
    },
    {
      title: 'Accumulated consumption amount',
      content: '0',
      tip: 'Accumulated consumption amount of user is reservation service in the mall (paid part, including refunded order amount)',
      value: 'totalMoneyPaid',
      linkName: ''
    },
    {
      title: 'Customer unit price',
      content: '0',
      tip: 'Average order consumption amount of each reservation service (including refunded orders)',
      value: 'unitPrice',
      linkName: ''
    },
    {
      title: 'Accumulated refund amount',
      content: '0',
      tip: 'The amount of refund after the user makes a service appointment in the mall',
      value: 'returnOrderMoney',
      linkName: ''
    },
    {
      title: 'Cumulative refund orders',
      content: '0',
      tip: 'Total number of orders refunded by users after service appointment in the mall',
      value: 'returnOrderNum',
      linkName: ''
    }
  ],
  transactionData5: [
    {
      title: 'Last order time',
      content: 'No order yet',
      tip: 'Time when the user last placed an order for the store to pay the bill',
      value: 'lastOrderTime',
      linkName: 'check_order'
    },
    {
      title: 'Number of purchase orders of stores',
      content: '0',
      tip: 'The number of orders (paid orders, including refunded orders) that the user purchases from stores in the mall',
      value: 'orderNum',
      linkName: 'check_order'
    },
    {
      title: 'Accumulated consumption amount',
      content: '0',
      tip: 'Accumulated consumption amount of customers paying for the store in the mall',
      value: 'totalMoneyPaid',
      linkName: 'check_order'
    },
    {
      title: 'Customer unit price',
      content: '0',
      tip: 'Average consumption amount of each store purchase order',
      value: 'unitPrice',
      linkName: 'check_order'
    }
  ],
  OrderList: 'Order List',
  Transactionstatistics: 'Transaction statistics',
  assetsData: [
    'Ordinary Card',
    'Limit Card',
    'Grade Card',
    'Reserve balance',
    'Available Integral',
    'Number of coupons available'
  ],
  AssetInformation: 'Asset Information',
  Labelinformation: 'Label information',
  Seemore: 'See more',
  Gender: 'Gender',
  monthlyincome: 'monthly income',
  Maritalstatus: 'Marital status',
  indursty: 'indursty',
  ID: 'ID',
  PermanentResidence: 'Permanent Residence',
  Educationlevel: 'Education level',
  Birthday: 'Birthday',
  Sourcechannel: 'Source channel',
  address: 'address',
  Cumulativeamount: 'Cumulative consumption amount',
  Accumulateintegrals: 'Accumulate integrals',
  Recentbrowsing: 'Recent browsing',
  Becomeacustomer: 'Become a customer',
  Modifycontacts: 'Modify contacts',
  Realname: 'Real name',
  Towrite: 'To write',
  Essentialinformation: 'Essential information',
  Currentpage: 'Current page',
  Revisioninviters: 'Revision of inviters',
  Deleteinviter: 'Delete the inviter',
  ModifiedIntegral: 'Modified Integral',
  Hairpin: 'Hairpin',
  people: 'people',
  Forscreened: 'For screened',
  setup: 'Set up',
  choiseGoods: 'Choosing goods',
  designatedgoods: 'Cumulative designated goods',
  purchasetimes: 'Accumulated purchase times',
  enddate: 'End date',
  chooseDate: 'Choose Date',
  startdata: 'Start date',
  Pleasecontent: 'Please enter the content',
  PassengerUnitPrice: 'Passenger Unit Price',
  coverName: 'Name of the invitee',
  to: 'to',
  Endtime: 'End time',
  Starttime: 'Start date',
  phoneNum: 'Mobile',
  wechatNickname: 'Wechat nickname',
  source: 'source',
  membershipCard: 'Card',
  label: 'Label',
  registrationTime: 'Registration time',
  inviter: 'Inviter',
  integral: 'Integral',
  Balance: 'Balance',
  banLogin: 'Landing has been banned',
  alreadyPhone: 'HasPhone',
  alreadyScore: 'HasScore',
  alreadyBalance: 'HasMoney',
  alreadyCard: 'HasCard',
  importMembers: 'Imported Members',
  screen: 'screen',
  membershipExport: 'Membership Export',
  more: 'More',
  retract: 'Retract',
  reacord: 'Logged in at a specified time',
  passengerUnitPrice: 'Passenger Unit Price',
  behavior: 'Additional purchases within a specified period of time',
  buyNum: 'Accumulated purchase times',
  transaction: 'Transaction within a specified period of time',
  designatedCommodities: 'Purchase of designated goods',
  nickname: 'Nickname',
  operation: 'operation',
  Balancedetails: 'Balance details',
  Integraldetails: 'Integral details',
  Nolanding: 'No landing',
  NoLandingPrompt:
    'Members are not allowed to log in after logging in, and it is forbidden to log in?',
  ResumeLogin: 'Resume Login',
  ResumeLoginPrompt:
    "Are you sure you want to restore the member's login feature?",
  Labeling: 'Labeling',
  Seedetails: 'See details',
  Allelection: 'All election',
  options_one: [
    {
      value: '0',
      label: 'Batch prohibition of landing'
    },
    {
      value: '1',
      label: 'No landing for selected persons'
    },
    {
      value: '2',
      label: ''
    }
  ],
  options_two: [
    {
      value: '0',
      label: 'Batch labeling'
    },
    {
      value: '1',
      label: 'Label the selected person'
    },
    {
      value: '2',
      label: ''
    }
  ],
  options_three: [
    {
      value: '0',
      label: 'Mass issuance of membership cards'
    },
    {
      value: '1',
      label: 'Hair Cards for Selected Persons'
    },
    {
      value: '2',
      label: ''
    }
  ],
  options_four: [
    {
      value: '0',
      label: 'Batch Modified Integral'
    },
    {
      value: '1',
      label: 'Modify the points for the selected person'
    },
    {
      value: '2',
      label: ''
    }
  ],
  options_five: [
    {
      value: '0',
      label: 'Bulk revision inviter'
    },
    {
      value: '1',
      label: 'Modify the inviter for the selected person'
    },
    {
      value: '2',
      label: ''
    },
    {
      value: '3',
      label: 'Delete the inviter from the selected person'
    },
    {
      value: '4',
      label: ''
    }
  ],
  currentPage: 'Current page',
  TotalRecords: 'Total Records',
  strip: 'strip',
  placePhoneNum: 'Please enter your cell phone number',
  placeWXNameNum: 'Please enter your Weixin nickname',
  placeChoise: 'Please choose',
  placeinpuLabel: 'Please enter the label',
  cardOptions: [{ id: 0, cardName: 'ALL' }],
  userFromSource: [{
    value: '0',
    label: 'ALL'
  }, {
    value: '-1',
    label: 'Search'
  }, {
    value: '-2',
    label: 'Share'
  }, {
    value: '-3',
    label: 'ScanQrCode'
  }, {
    value: '-4',
    label: 'Not acquired'
  }],
  allSource: 'ALL',
  notAcquired: 'Not acquired',
  backStage: 'Back stage',
  scanQrCode: 'ScanQrCode',
  filter: 'filter',
  orderSn: 'orderSn',
  placeHolderOrderSn: 'please input orderSn',
  time: 'time',
  score: 'score',
  remark: 'remark',
  expiredTime: 'expired time',
  tagError: 'A user can own tag at most five',
  cancel: 'cancel',
  centain: 'centain',
  prompt: 'prompt',
  success: 'success',
  error: 'error',
  accountCertain: 'account certain',
  scoreCertain: 'score certain',
  unknown: 'unknown',
  notAddYet: 'not add yet',
  balanceDialogData: [
    {
      title: 'Modified amount',
      presentText: 'Current amount',
      persentMoney: '',
      addText: 'Increase amount',
      tips: '（*When balance is positive,increase,else reduce*）',
      bzText: 'Add Remark',
      index: 0
    },
    {
      title: 'Modify Ex Times',
      presentText: 'Times',
      persentMoney: '',
      addText: 'Add-times',
      tips: '（*When times is positive,increase,else reduce*）',
      bzText: 'Add Remark',
      index: 0
    },
    {
      title: 'Modify Times',
      presentText: 'Times',
      persentMoney: '',
      addText: 'Add-times',
      tips: '（*When times is positive,increase,else reduce*）',
      bzText: 'Add Remark',
      index: 0
    }
  ],
  integralDialogData: [
    {
      title: 'Modified score',
      presentText: 'Current score',
      persentMoney: '',
      addText: 'Increase score',
      tips: '（*When score is positive,increase,else reduce*）',
      bzText: 'Add Remark',
      index: 1
    }
  ],
  maritalStatus: ['UnMarried', 'Married', 'Secret'],
  MarriageValueOptions: [
    {
      value: '2',
      label: 'Married'
    },
    {
      value: '1',
      label: 'UnMarried'
    },
    {
      value: '3',
      label: 'Secret'
    }
  ],
  notExists: 'Not Exists',
  sex: [['f', 'woman'], ['m', 'man']],
  day: 'day(s)',
  month: 'month(s)',
  year: 'year(s)',
  distributionStatistic: 'Distribution statistics',
  chooseTag: 'please choose tag',
  chooseUser: 'choose user',
  search: 'search',
  userId: 'USER ID',
  GenderValueOptions: [
    {
      value: 'm',
      label: 'man'
    },
    {
      value: 'f',
      label: 'woman'
    }
  ],
  incomeValueOptions: [
    {
      value: '1',
      label: 'Under 2000'
    },
    {
      value: '2',
      label: '2000-3999'
    },
    {
      value: '3',
      label: '4000-5999'
    },
    {
      value: '4',
      label: '6000-7999'
    },
    {
      value: '5',
      label: '8000 or more'
    }
  ],
  educationValueOptions: [
    {
      value: '1',
      label: 'JUNIOR'
    },
    {
      value: '2',
      label: 'HIGH'
    },
    {
      value: '3',
      label: 'SECONDARY'
    },
    {
      value: '4',
      label: 'COLLEGE'
    },
    {
      value: '5',
      label: 'UNDERGRADUATE'
    },
    {
      value: '6',
      label: 'MASTER'
    },
    {
      value: '7',
      label: 'DOCTOR'
    },
    {
      value: '8',
      label: 'OTHER'
    }
  ],
  industry: 'industry',
  localtion: 'localtion',
  disCount: 'discount;',
  memberCount: 'Member discount',
  powerPayOwnGood: 'pay own Good;',
  powerCard: 'recharge reward;',
  powerScore: 'score reward;',
  times: 'time(s);',
  storeExchange: 'store exchange',
  deleteCardSuccess: 'delete success',
  goodsExchange: 'goods exchange',
  normalCard: 'Ordinary Card',
  limiteCard: 'Limited Card',
  rankCard: 'Grade Card',
  using: 'USING',
  abolished: 'DEPRECATED',
  expired: 'EXPIRED',
  cardTypeArray: [
    {
      value: null,
      label: 'All Card'
    },
    {
      value: 0,
      label: 'Ordinary Card'
    },
    {
      value: 1,
      label: 'Limited  Card'
    },
    {
      value: 2,
      label: 'Grade    Card'
    }
  ],
  allCard: {
    id: null,
    cardName: 'All Card'
  },
  chargeDetail: 'charge',
  consumeDetail: 'consume',
  deleteCard: 'del',

  add: 'Add',
  normalCardFull: 'NormalCard',
  limiteCardFull: 'LimiteCard',
  gradeCard: 'GradeCard',
  addCardTips: 'Note: the card that needs to be activated is issued directly to the user',

  // 导出弹窗
  exportTable: 'Export table',
  filterOnFollowing: 'Filter based on the following conditions',
  articleData: 'Article data',
  ayExport: 'Are you sure to export?',
  filterCirteria: 'Filter criteria',
  numExports: 'Number of exports (maximum export at a time',
  articleDatas: 'Article data',
  basicInformation: 'basic information',
  userId2: 'User id',
  nickname2: 'nickname',
  phone: 'phone number',
  balance: 'Balance',
  integral2: 'integral',
  userSource: 'User Source',
  registrationTime2: 'Registration time',
  memberShip: 'membership card',
  address2: 'address',
  cumulativeSpending: 'Cumulative spending amount',
  cumulativeConsumption: 'Cumulative Consumption Orders',
  cumulativeRefund: 'Cumulative refund amount',
  cumulativeOrder: 'Cumulative refund orders',
  remarks: 'Remarks',
  distributionIntro: 'Distribution Information',
  invitePe: 'Invite people',
  inviteMobile: "Inviter's mobile number",
  invitedDistribut: 'Invited by distributors',
  numRebate: 'Number of rebate orders',
  rebateProducts: 'Total amount of rebate products',
  totalRebate: 'Total Rebate Order Commission',
  totalCommission: 'Total Commission Withdrawn',
  numSubordinate: 'Number of subordinate users',
  distributorLevel: 'Distributor Level',
  distributorGroup: 'Distributor Group',
  determine: 'determine',
  cancel2: 'cancel',
  cardStatus: 'Card status',
  cardStatusOpt: [
    {
      value: -1,
      label: 'All'
    },
    {
      value: 0,
      label: 'Normal'
    },
    {
      value: 1,
      label: 'Delete'
    },
    {
      value: 2,
      label: 'Expired'
    },
    {
      value: 3,
      label: 'Gived'
    }
  ],
  cardGived: 'Gived'
}
