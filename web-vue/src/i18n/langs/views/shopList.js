export const cn = {
  title: {
    experienceVersion: '体验版',
    payVersion: '付费版',
    newShop: '店铺添加'
  },

  // 体验版和付费版页面共同字段，二者基本相同
  info: {
    account_info1: '账号ID、公司',
    account_info2: '店铺ID、账户名称、手机号',
    account_info3: '选择店铺状态',
    account_info4: '选择店铺标识',
    account_info5: '选择禁用状态',
    account_info6: '底部导航',
    account_info7: '店铺到期时间',
    account_info8: '请选择时间',
    account_info9: '至',
    account_info10: '搜索',
    shop_type: '店铺类型' // 付费版特有字段
  },
  state_option: {
    isUsing: '使用中',
    coming: '即将过期',
    expired: '已过期'
  },
  id_option: {
    name1: '店+',
    name2: '欧派',
    name3: '寺库'
  },
  disabled_state: {
    disabled: '已禁用',
    undisabled: '未禁用'
  },
  bototm_nav: {
    display: '显示',
    hidden: '隐藏'
  },
  time: '店铺到期时间',
  select_time: '请选择时间',
  to: '至',
  search: '搜索',
  table: {
    ID: '账号ID',
    shopID: '店铺ID(店铺类型)',
    shopName: '店铺名称',
    wechatName: '小程序名称(公司)',
    mobile: '手机号',
    createTime: '创建时间',
    endTime: '到期时间(店铺状态)',
    isDisabled: '是否禁用',
    permission: '小程序授权',
    account: '所属账号',
    money: '续费总金额',
    shopFlag: '店铺标识',
    bottom: '底部导航',
    special: '特殊配置',
    operating: '操作'
  },

  // 店铺添加
  addInfo: {
    sysId: '所属账号',
    dbConfigId: '数据库',
    mobile: '手机号',
    shopType: '店铺类型',
    endTime: '到期时间',
    receiveMobile: '接收短信手机号',
    shopName: '店铺名称',
    shopPhone: '店铺客服电话',
    shopNotice: '店铺公告',
    shopWx: '店铺微信',
    shopEmail: '店铺邮箱',
    isDisabled: '店铺禁用',
    shopQq: '店铺客服QQ',
    shopFlag: '店铺标记',
    memberKey: '欧派店铺标识',
    tenancyName: '欧派大屏租户名称',
    userName: '欧派大屏用户名',
    password: '欧派大屏密码',
    hidBottom: '隐藏底部',
    needSelect: '请选择',
    currency: '币种',
    shopLanguage: '店铺语言'
  },
  selectDb: '请选择数据库',
  db_version: [{
    value: '',
    label: '选择数据库'
  },
  {
    value: '1',
    label: 'vpu_user(172.21.0.3)'
  }
  ],
  selectType: '请选择店铺类型',
  shop_version: [{
    value: '',
    label: '选择店铺类型'
  },
  {
    value: 'v1',
    label: '体验版'
  },
  {
    value: 'v2',
    label: '基础版'
  },
  {
    value: 'v3',
    label: '高级版'
  },
  {
    value: 'v4',
    label: '旗舰版'
  }
  ],
  selectData: '请选择日期时间',
  selectFlag: '请选择店铺标记',
  flag_type: [{
    value: '',
    label: '选择店铺标记'
  },
  {
    value: '1',
    label: '欧派'
  },
  {
    value: '2',
    label: '寺库'
  }
  ],
  shopDisabled: '禁用',
  hideFooter: '隐藏',
  save: '保存',
  prompt: '添加新店铺，会创建此店铺的数据库。添加店铺只可以禁用，不能删除！谨慎添加',
  language_type: [{
    value: '',
    label: '选择语言'
  },
  {
    value: 'zh_CN',
    label: '中文'
  },
  {
    value: 'zh_HK',
    label: '中文(香港)'
  },
  {
    value: 'zh_TW',
    label: '中文(台湾)'
  },
  {
    value: 'zh_SG',
    label: '中文(新加坡)'
  },
  {
    value: 'en_US',
    label: 'English (US)'
  }
  ],
  currency_type: [{
    value: '',
    label: '选择币种',
    sign: '¤'
  },
  {
    value: 'CNY',
    label: '人民币',
    sign: '¥'
  },
  {
    value: 'HKD',
    label: '港元',
    sign: 'HK$'
  },
  {
    value: 'TWD',
    label: '台币',
    sign: 'NT'
  },
  {
    value: 'USD',
    label: '美元',
    sign: '$'
  },
  {
    value: 'EUR',
    label: '欧元',
    sign: '€'
  },
  {
    value: 'JPY',
    label: '日元',
    sign: '￥'
  },
  {
    value: 'GBP',
    label: '英镑 ',
    sign: '￡'
  }
  ]
}

export const en = {
  title: {
    experienceVersion: 'Trial Version',
    payVersion: 'Paid version'
  },

  // 体验版和付费版共同的字段，二者基本相同
  info: {
    account_info1: 'Account ID、Company',
    account_info2: 'Store ID、account name、phone number',
    account_info3: 'Select store status',
    account_info4: 'Select store ID',
    account_info5: 'Select disabled state',
    account_info6: 'Bottom navigation',
    account_info7: 'Expire date',
    account_info8: 'Please select time',
    account_info9: 'to',
    account_info10: 'search',
    shop_type: 'Store type' // 付费版特有字段
  },
  state_option: {
    isUsing: 'Using',
    coming: 'Coming soon',
    expired: 'expried'
  },
  id_option: {
    name1: 'store+',
    name2: 'European',
    name3: 'Temple library'
  },
  disabled_state: {
    disabled: 'disabled',
    undisabled: 'Not disabled'
  },
  bototm_nav: {
    display: 'display',
    hidden: 'hidden'
  },
  time: 'Store expiration time',
  select_time: 'Please select time',
  to: 'to',
  search: 'search',
  table: {
    ID: 'Account ID',
    shopID: 'Store ID(Store type)',
    shopName: 'Store name',
    wechatName: 'Applet name(company)',
    mobile: 'Phone number',
    createTime: 'Create time',
    endTime: 'Expire date(Store status)',
    isDisabled: 'Whether to disable',
    permission: 'Applet authorization',
    account: 'Account',
    money: 'Total renewal amount',
    shopFlag: 'Store identification',
    bottom: 'Bottom navigation',
    special: 'Special configuration',
    operating: 'operating'
  },

  // 店铺添加页面
  addInfo: {
    sysId: 'account',
    dbConfigId: 'database',
    mobile: 'phone number',
    shopType: 'store type',
    endTime: 'Expire date',
    receiveMobile: 'Receive SMS phone number',
    shopName: 'store name',
    shopPhone: 'Shop customer service phone',
    shopNotice: 'Shop announcement',
    shopWx: 'Shop WeCha',
    shopEmail: 'Shop Mailbox ',
    isDisabled: 'Store disabled',
    shopQq: 'Shop customer service QQ',
    shopFlag: 'Shop mark',
    memberKey: 'European shop logo',
    tenancyName: 'Ou Pai big screen tenant name',
    userName: 'Ou Pai big screen username',
    password: 'European big screen password',
    hidBottom: 'Hide bottom'
  },
  selectDb: 'Please select a database',
  selectType: 'Please select the store type',
  versionName: {
    exp: 'Trial Version',
    base: 'Basic version',
    high: 'Advanced version',
    unique: 'Ultimate'
  },
  selectData: 'Please select a date and time',
  selectFlag: 'Please select a store tag',
  flag_type: {
    type1: 'European',
    type2: 'Temple library'
  },
  shopDisabled: 'Disable',
  hideFooter: 'hide',
  save: 'save',
  prompt: 'Add a new store and create a database for this store. Adding a store can only be disabled and cannot be deleted! Cautiously add '
}
