export const cn = {
  // 添加新活动
  actTypeFirstServed: '先到先得',
  actTypeTimingOpen: '定时开奖',
  actTypeDirectGiving: '直接送礼',
  requiredValidDate: '请选择有效期',
  activityTypeVaild: '最少选择一种活动玩法',
  required: '必填',
  save: '保存',
  choosingGoods: '选择商品',
  choosingGoodsTips: '已选择商品:{0}件商品',
  editActicity: '编辑我要送礼活动',
  commodity: '适用商品',
  commodityTips1: '1、送礼商品默认不收取运费',
  commodityTips2: '2、当用户选择先到先得、定时开奖玩法时，无论订单是否满足赠品条件均不赠送赠品。 ',
  tutorial: '查看教程',
  customService: '关于客服',
  customServiceTips1: '(1)礼品订单在前端不支持申请退款，客户如果有咨询、维权需求，可在“礼物记录”中联系客服，店家可在后端“订单管理”中手动退款',
  customServiceTips2: '(2)配置送礼功能前，请在微信后台设置小程序客服',
  actTypeTips1: '先到先得：送礼人分享礼单给多位好友，或者将礼单海报发送到朋友圈，好友拼手速领取礼物，每人随机获得一份，先到先得送完即止（适用于一对多送礼）\n',
  actTypeTips2: '定时开奖：送礼人设定开奖时间，到达开奖是时间后，自动从领取礼物的用户中随机抽出中奖用户送出礼物（适用于一对多送礼）\n',
  actTypeTips3: '直接送礼：送礼人挑选礼物，付款并分享给收礼人，收礼人点击，并填写收货地址即可收礼。（适用于一对一送礼）\n',
  priorityTips: '用于区分不同我要送礼活动的优先级，请填写正整数，数值越大优先级越高',
  startDate: '开始日期',
  endDate: '结束日期',
  everTime: '永久时间',
  fixedTime: '固定时间',
  MyCenter: '个人中心',
  MyCenterJump: '前往',
  addGiveGiftTips: '提示：配置"我要送礼"活动后请在"小程序管理" "',
  addGiveGiftTips1: '" 开启礼物记录查看入口',
  // 收礼明细
  giveGiftTypeArr: [
    [1, '直接送礼'],
    [2, '先到先得'],
    [3, '定时开奖']
  ],
  childOrderSn: '子订单号',
  receiveTime: '领取时间',
  receivePoeple: '收礼人',
  receiveUserName: '收礼人姓名',
  receiveMobile: '收礼人手机号',
  giftStatusList1: [
    {
      value: null,
      label: '全部'
    },
    {
      value: 3,
      label: '未提交地址'
    },
    {
      value: 13,
      label: '待开奖'
    },
    {
      value: 13,
      label: '未中奖'
    },
    {
      value: 13,
      label: '待发货'
    },
    {
      value: 13,
      label: '已发货'
    },
    {
      value: 13,
      label: '已完成'
    },
    {
      value: 13,
      label: '已关闭'
    }
  ],
  // 送礼明细
  giftStatus: '礼单状态',
  returnMoneyNum: '退款数量',
  activityType: '活动玩法',
  payTime: '支付时间',
  giftGoods: '礼物',
  givePeople: '送礼人',
  mainOrderSn: '主订单号',
  filter: '筛选',
  givePeopleName: '送礼人姓名',
  givePeopleMobile: '送礼人手机号',
  goodsName: '商品名称',
  goodsSn: '商品货号',
  giveCartStatus: '礼单状态',
  giftStatusList: [
    {
      value: null,
      label: '全部'
    },
    {
      value: 3,
      label: '进行中'
    },
    {
      value: 13,
      label: '已完成'
    }
  ],
  // 我要送礼活动
  changeStatusComment: '此操作将启用该活动, 是否继续?',
  deleteComment: '此操作将删除该活动, 是否继续?',
  confirm: '确定',
  cancel: '取消',
  cancelMessage: '已取消',
  to: '至',
  receiveDetail: '收礼明细',
  giveGiftDetail: '送礼明细',
  edit: '编辑',
  share: '分享',
  disable: '停用',
  enabled: '启用',
  delete: '删除',
  option: '操作',
  receivePoepleNum: '收礼人数',
  givePeopleNum: '送礼人数',
  priority: '优先级',
  validDate: '有效期',
  activityStatus: '活动状态',
  activityName: '活动名称',
  addActivity: '添加我要送礼',
  tabInfo: [
    {
      title: '全部我要送礼活动',
      name: 'all',
      index: 0
    }, {
      title: '进行中',
      name: 'inProgress',
      index: 1
    }, {
      title: '未开始',
      name: 'notStarted',
      index: 2
    }, {
      title: '已过期',
      name: 'expired',
      index: 3
    }, {
      title: '已停用',
      name: 'disabled',
      index: 4
    }]
}
