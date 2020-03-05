export const cn = {

  // -------------------------tab-------------------
  distributionCfg: '分销配置',
  distributorLevelCfg: '分销员等级配置',
  rebateStrategyCfg: '返利策略配置',
  distributorList: '分销员列表',
  distributorGroup: '分销员分组',
  commissionStatistics: '佣金统计',
  rebateGoodsStatistics: '商品返利统计',
  withdrawAudit: '返利提现审核',
  distributorAudit: '分销员审核',
  advertisement: '分销推广语',

  // --------------------通用---------------------
  createTime: '创建时间',
  advertisementContent: '推广语内容',
  mobile: '手机号',
  realName: '真实姓名',
  withdrawSn: '提现单号',
  applyTime: '申请时间',
  withdrawMoney: '提现金额',
  opt: '操作',
  disposeStatus: '处理状态',
  applyWithdrawMoney: '申请提现金额',
  disposePayType: '出账类型',
  screen: '筛选',
  export: '导出',
  rebateStatus: '返利状态',
  serialNumber: '流水号',
  withdrawNo: '提现序号',
  canWithdrawMoney: '可提现金额',
  chooseDate: '选择日期',
  to: '至',
  search: '查询',
  registTime: '注册时间',
  username: '用户昵称',
  rebateVailDate: '返利有效期',
  distributorMobile: '分销员手机号',

  // -----------------------分销配置-----------------
  switch: '分销开关：',
  switchTip: '开关默认关闭，开启开关，则用户可以申请为店铺分销员，分销员邀请用户注册产生订单，购买者邀请人可获得佣金奖励。关闭开关，手机端个人中心”分销中心“菜单隐藏，用户下单，邀请人不再产生佣金奖励，系统分销机制关闭，邀请不再记录邀请关系。',
  reviewed: '分销员审核：',
  reviewedTip1: '若开启审核，您需要配置推广文案内容',
  reviewedTip2: '推广文案配置',
  reviewedTip3: '开启分销员审核功能后，普通用户申请成为分销员时需要经过商家审核。关闭则成为店铺分销员不需要申请审核，全部用户均默认为店铺分销员。',
  reviewedInvitation: '系统校验邀请码，验证后自动通过审核',
  invitationTip1: '勾选后，系统自动生成分销员邀请码。',
  invitationTip2: '注：店铺中至少有一个分销员时，可开启此功能。',
  invitationTip3: '如需修改邀请码，请到',
  invitationTip4: '分销员列表',
  invitationTip5: '中进行设置',
  reviewedInfo: '开启后用户申请成为分销员时，需要提交个人信息',
  checkedList: ['真实姓名', '手机号', '身份证号码', '性别', '生日', '婚姻状况', '教育程度', '所在行业', '所在地', '备注', '分销员分组', '图片上传', '邀请码'],
  ranking: '分销员排名：',
  rankingTip: '开关默认关闭，开启开关，且拥有返利数据的分销员数大于等于3位时分销员中心显示分销员佣金排名。关闭则不显示分销员佣金排名页面。',
  validity: '返利有效期：',
  validityDay: '天',
  validityForever: '永久',
  validityTip: '用户被分销员邀请注册开始计算，在该天数限制内该用户购买分销商品给分销员计算佣金返利，一旦超过该天数，则不再给分销员佣金返利，默认为空，为空表示不限制。',
  protection: '分销员保护期：',
  protectionTip: '在保护期内，分销员发展的客户不会变更绑定关系，保护期过后可通过分享链接重新绑定邀请关系。 超过保护期若未重新建立邀请关系，则原绑定关系仍然有效，可依据返利配置条件返利。若保护期设置为0天，则用户可随时通过他人分享进入小程序实现其邀请人的变更。',
  pageName: '分销中心页面名称：',
  recommendShop: '推荐商品：',
  recommendRadio1: '不显示',
  recommendRadio2: '默认',
  recommendRadio3: '自定义',
  recommendTip: '将从已选商品中随机抽取10个展示在小程序端“分销中心”，引导分销员推广商品',
  chooseCommodity: '选择商品',
  commodityName: '商品名称',
  commodityPrice: '价格',
  commodityStock: '库存',
  commodityOption: '操作',
  commodityDelete: '删除',
  customContent: '自定义内容：',
  selectTemplate: '选择模版',
  refresh: '刷新',
  addTemplate: '添加模版',

  rebateSettings: '返利提现设置',
  rebateSettingsSwitch: '返利提现开关：',
  rebateSettingsTip1: '注：开启提现功能开关前，请阅读',
  rebateSettingsTip2: '《返利提现配置操作说明》',
  rebateSettingsTip3: '开关开启，分销员推广返利获得的佣金可提现到微信钱包，分销员在小程序发起返利申请，需后台审核通过才可提现到账',
  rebateRadio1: '小程序',
  rebateRadioTip1: '注意：使用返利提现功能，请确保小程序已开通微信支付，否则不可提现',
  rebateRadioTip2: '去配置',
  rebateRadio2: '公众号',
  rebateRadioTip3: '注意：使用返利提现功能，请确保小程序已绑定认证服务号并配置相关支付信息，否则不可提现，未关注公众号的用户将会提现失败',
  minRebate: '返利最小提现金额：',
  minUnit: '元',
  minRebateTip: '分销员发起返利提现，单次申请最小提现金额。为防止分销员提现过于频繁，请设置单次最小提现金额。',
  rebateBg: '分销中心推广海报背景图',
  rebateNickname: '昵称',
  rebateCode: '二维码区域',
  rebateWriting: '邀请文案：',
  rebateImg: '海报背景图：',
  customSelect: '默认背景选择：',
  uploadSelect: '上传背景图片：',
  imgTip: '图片尺寸640px*640px',
  rebateSave: '保存',
  rebateSaveSuccess: '保存成功!',

  // 分销推广文案
  contentTip1: `
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; color: #e53333; font-size: 18px; font-weight: bold;">以下说明模版，请根据情况自行修改，仅供参考。</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">&nbsp;</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">小伙伴，欢迎加入我们</p>
  `,
  contentTip2: `
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; color: #e53333; font-size: 18px; font-weight: bold;"><span style="font-size: 12px; font-weight: normal;">小伙伴，欢迎加入我们</span></p>
  `,
  contentText: `
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">&nbsp;</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">我们是XXX运营团队，竭诚为你的销售工作提供完善的支持。</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">&nbsp;</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">我们诚挚邀请你加入我们的分销员推广计划，无任何成本即可成为XXX的分销员，一起分享收获的喜悦。你只需将高品质商品分享推荐给他人，收获他人的感谢的同时，挣得属于自己的利润。</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">&nbsp;</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">1. 返利说明</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">1）买家购买返利商品，下单支付成功，则邀请该用户注册的分销员可获得佣金返利；佣金值=商品实际支付金额*佣金比例。</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">2）邀请新用户注册，或者分享给已经注册的但没有邀请人的用户都算作该分销员邀请的用户。</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">3）订单支付成功做返利佣金为待返利状态，交易完成则该佣金返利成功，自动提现到分销员的用户余额中。交易完成前发生退款的订单，相应的分销员返利佣金为已退款返利失败状态。</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">4）仅在线支付的订单算作业绩，即微信支付、余额支付（会员卡余额、用户余额）、货到付款的订单计算返利佣金。例如买家购买返利商品A价格为100元，该买家使用了20元的优惠券，其余金额使用微信支付，返利比例为10%，则该邀请该买家的分销员可获得返利佣金为（100-20）*10%=8元。</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">5）买家仅返利商品，该买家的邀请人可获得返利佣金，购买普通商品不返利。</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">&nbsp;</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">2. 结算说明</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">1）返利佣金比例为X%。</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">2）返利有效期X50，（用户被分销员邀请注册开始计算，在该天数限制内该用户购买分销商品给分销员计算佣金返利，一旦超过该天数，则不再给分销员佣金返利)。</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">&nbsp;</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">3. 其他说明</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">1）分享前，请确定商品页面有【&middot;&middot;&middot;】按钮；</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">2）销售过程中有任何疑问，请直接联系商家；</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">3）已售出商品的任何售后问题，由本商城处理；</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">4）不传播或者扩散有关于政治、色情等任何违法的信息，一经发现，则立即封号，如果触犯任何法律相关问题，商城不负任何责任；</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">5）以上内容解释权归本商城所有。</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">4. 联系方式</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">1）联络人</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">2）手机：1234567889</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">3）分销员QQ交流群：12345678</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">4）邮箱：xxx@xxx.com</p>
    <p style="overflow-wrap: break-word; margin: 5px 0px; font-family: 'sans serif', tahoma, verdana, helvetica; font-size: 12px;">无论是销售、对账，还是结算问题，请通过以上方式咨询。</p>
  `,
  pageTip: '若系统开启分销员审核功能，则该文案展示在用户申请页面。或分销员可在分销中心邀请其他用户注册为分销员，此时普通用户通过邀请链接点击也是查看该推广文案。',
  shareAddress: '分享地址',
  writingTitle: '页面标题',
  writingTitleTip: '请填写页面标题',
  writingCopy: '复制',
  writingShare: '立即分享',
  writingContent: '页面内容',
  writingTemplate: '使用模板文案',

  // -----------------分销员等级配置---------------
  levelTip: '提示：每次修改分销员等级，将会有大量分销员受到影响，请谨慎操作',
  levelText: '升级规则',
  dialogTitle: '提醒',
  dialogText1: '累计邀请用户数：分销员累积邀请的用户数。',
  dialogText2: '累积推广金：分销员推广商品的订单累计金额。',
  dialogText3: '累积消费金：分销员在店铺累积消费金额。',
  dialogSure: '确 定',
  // 表格
  level: '等级',
  levelName: '等级名称',
  level1: '成为分销员后，默认即是该等级',
  levelRadio1: '自动升级',
  levelRadio2: '手动升级',
  levelTip1: '累计邀请用户数达',
  levelTip2: '个',
  levelTip3: '或',
  levelTip4: '累计推广金达',
  levelTip5: '元',
  levelTip6: '累积推广金与消费金总和达',
  distributorsNum: '分销员数量',
  option: '操作',
  levelAlready: '已启动',
  levelStart1: '启用',
  levelStart2: '已启用',
  levelStop1: '停用',
  levelStop2: '已停用',

  // -------------------返利策略配置---------------
  addRebateStrategy: '添加返利策略',
  editRebateStrategy: '编辑返利策略',
  policyTabInfo: [{
    title: '全部策略',
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
  AddRebateStrategy: '添加返利策略',
  strategyName: '返利策略名称',
  strategyValidity: '有效期',
  ratioRate: '返利比例',
  strategyCreateTime: '创建时间',
  strategyLevel: '优先级',
  strategyStatus: '状态',
  strategyOption: '操作',

  // 表格
  strategyTip1: '请输入返利策略名称',
  ratioLevel: '返利策略优先级',
  strategyTip2: '请输入返利策略优先级',
  strategyTip3: '当一个商品被添加到多个策略时，执行优先级最高的，可填写1到100间的整数。允许优先级重复，若重复则返利商品执行最新创建的返利策略。',
  selfPurchase: '分销员自购返利',
  purchaseOpen: '开启',
  purchaseClose: '关闭',
  strategyTip4: '开启后，分销员购买商品也会获得返利，返利比例为分销员当前等级的直接邀请返利比例。',
  strategyTip5: '注：当自购返利开关开启，若下单人是分销员，则该下单人的间接邀请人不会获得返利，其直接邀请人可获得返利，返利比例为直接邀请人所在等级的间接邀请返利比例',
  costProtection: '成本价保护：',
  costProtectionTip1: '当单件商品实付金额-成本价大于0时，按分销比例分配差额',
  costProtectionTip2: '当单件商品实付金额-成本价小于等于0时，返利为0',
  costProtectionTip3: '注：',
  costProtectionTip4: '未设置成本价的商品无效',
  Invitation: '邀请新用户下首单返利配置：',
  InvitationTip: '开启后，分销员邀请新用户并引导其在店铺下首单（直接邀请关系），可单独设置返利比例。帮助店铺快速拉新，提高新用户成单率。',
  proportion: '返利佣金比例',
  proportionTip1: '直接邀请返利比例：分销员成功推广后获取的佣金',
  proportionTip2: '间接邀请返利比例：B是A发展的分销员，B成功推广后，A可获得邀请奖励佣金',
  proportionTip3: '直接邀请返利比例',
  proportionTip4: '间接邀请返利比例',
  proportionTip5: '当前等级分销员可获返利金额为下单商品金额的0%-0%',
  proportionTip6: '直接邀请新用户下首单返利比例',
  proportionTip7: '该策略配置商品都按当前比例结算佣金，佣金值=商品实际支付金额*佣金比例，例如，分销商品价格100元，返利佣金比例20%，那么用户购买一件分销商品，邀请该用户分销员获得20元佣金。订单支付完成佣金返利到分销员分销中心的余额账户中，但是该佣金为待返利状态，订单完成后，佣金返利，分销员可以直接使用该佣金购物。限制小数点后一位数字。',
  authority: '分销员权限：',
  authorityTip1: '推广赠送优惠券',
  authorityTip2: '允许分销员分销商品时赠送优惠券',
  distributionGoods: '分销商品：',
  goodsRadio1: '全部商品',
  goodsRadio2: '指定商品',
  goodsTip1: '已选',
  goodsTip2: '件商品',
  goodsTip3: '个商家',
  goodsTip4: '个平台',

  storeArr: [{
    name: '添加商品',
    value: '1'
  },
  {
    name: '添加商品分类',
    value: '2'
  },
  {
    name: '添加平台分类',
    value: '3'
  }],

  addSuccess: '添加成功!',
  addFail: '添加失败!',
  editSuccess: '编辑成功!',
  editFail: '编辑失败!',

  // ---------------------分销员列表----------------
  wxName: '微信昵称',
  invitedUserMobile: '被邀请用户手机号',
  invitedUserName: '被邀请用户昵称',
  distributorLevel: '分销员等级',
  distributorListfont: '注：',
  distributorListDesc: '未开启分销员审核时，列表只展示有下级用户的分销员',
  id: 'ID',
  nextUserNum: '下级用户数',
  indirectInviteUserNum: '间接邀请用户数',
  rebateGoodsAmount: '累积返利商品总额',
  rebateFanliAmount: '累积获得佣金金额',
  waitFanliAmount: '待返利佣金金额',
  showHasInviteUsers: '产看已邀请用户',
  showFanliDetail: '查看返利佣金明细',
  clear: '清除',
  userMobile: '用户手机号',
  rebateOrderNum: '累计返利订单数',
  orderGoodsRebateAmount: '累积订单商品返利总金额',
  inviteProtectDate: '邀请保护日期',

  // -------------------分销员分组-----------------
  distributionGroup: '',
  switchDesc: '分组是否展示在小程序端',
  show: '展示',
  notShow: '不展示',
  save: '保存',
  addDistributorGroup: '添加分销员分组',
  groupName: '分组名称',
  includeDistributorNum: '包含分销员数量',
  isDefaultGroup: '是否为默认分组',
  addDistributor: '添加分销员',
  distributorUsername: '分销员昵称',
  distributorId: '分销员ID',
  currentLevel: '当前分组',
  reset: '重置',
  deny: '否',
  setDefault: '设置默认',

  // --------------------佣金统计------------------

  orderTime: '下单时间',
  rebateOrderSn: '返利订单号',

  // -------------------商品返利统计---------------
  goodsName: '商品名称',
  goodsPrice: '商品价格',
  cate: '商品所属分类',
  salesNum: '商品总销量',
  hasRebateAmount: '已返利总数量',
  hasRebateMoney: '已返利总佣金',
  showParticulars: '查看明细',
  // 查看明细
  rebateSn: '返利订单号',
  orderUsername: '下单用户昵称',
  orderUsermobile: '下单用户手机号',
  distributorName: '分销员昵称',
  distributorRealName: '分销员真实姓名',
  rebateTime: '返利时间',
  rebateRelation: '返利关系',
  goodsNumber: '商品数量',
  rebateGoodsMoney: '返利商品金额',
  goodsOrderSn: '商品订单号',
  rebteRatio: '返利比例',
  rebateDate: '返利日期',

  // -------------------返利提现审核----------------
  // 列表搜索条件
  applyName: '申请人昵称',
  // 返利提现审核列表
  applayer: '申请人',
  optTime: '操作时间',
  refuseReason: '驳回原因',
  disposeDesc: '处理备注',
  showDetail: '查看详情',
  // 提现审核详情
  withdrawDetail: '提现申请详情',
  // 按钮
  confirmDisposePay: '确认出账',
  refuseapply: '驳回提现申请',
  addDesc: '添加备注',
  // 基本信息
  applyMoney: '申请金额',
  userId: '用户ID',
  dsecInfo: '备注信息',
  refuseApplyReason: '驳回申请原因',
  // 转账明细信息
  transferDetail: '转账明细信息',
  // 当前用户提现记录
  currentUserWithdrawRecord: '当前用户提现记录',

  // ---------------------分销推广语----------------
  // 列表搜索框
  endModifyTime: '最后修改时间',
  // 添加推广语按钮
  addAdvertisement: '添加推广语',
  // 推广语列表
  advertisementTag: '推广语标签',
  inputAdvertisement: '请输入推广语内容',
  updateTime: '更新时间',
  status: '状态',
  edit: '编辑',
  pause: '停用',
  open: '启用',
  delete: '删除',
  // 添加推广语弹窗
  title: '标题',
  addNote: '请输入分销推广语，将帮助分销员朋友圈推广：',
  cancleBtn: '取消',
  confirmBt: '确定',

  // ---------------------分销员审核----------------
  reviewMobile: '手机号',
  reviewName: '昵称',
  reviewTime: '申请时间',
  reviewSelect: '选择日期',
  reviewTo: '至',
  reviewSearch: '查询',
  reviewBy: '待审核分销员',
  reviewPass: '审核通过',
  reviewNoPass: '未通过',
  reviewGroup: '分销员分组',
  reviewDate: '审核时间',
  reviewStatus: '审核状态',
  reviewOption: '操作',
  noPassReason: '未通过原因',
  reviewTip: '无需提交个人信息',
  reviewRealName: '真实姓名',
  reviewId: '身份证号',
  reviewSet: '设置',
  passBtn: '通过',
  noPassBtn: '不通过',
  reviewSex: '性别',
  reviewBirthday: '生日',
  reviewMarital: '婚姻状况',
  reviewEducation: '教育程度',
  reviewIndustry: '所在行业',
  reviewAddress: '所在地',
  reviewNote: '备注',
  reviewImg: '图片',
  reviewNo: '无',
  reviewTitle1: '设置分销员分组',
  selectGroup: '选择分组',
  reviewTitle2: '审核不通过原因',
  reviewTitleTip: '原因提交后不可修改，请谨慎提交'

}
