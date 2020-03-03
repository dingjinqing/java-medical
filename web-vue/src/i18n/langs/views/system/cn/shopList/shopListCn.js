export const cn = {
  title: {
    experienceVersion: '体验版',
    payVersion: '付费版',
    newShop: '店铺添加',
    editShop: '店铺编辑'
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
    currency: '币种',
    language: '语言',
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
    value: 0,
    label: '选择店铺标记'
  },
  {
    value: 1,
    label: '欧派'
  },
  {
    value: 2,
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
  ],
  renew_Type: [{
    value: 1,
    label: '续费'
  },
  {
    value: 2,
    label: '试用'
  },
  {
    value: 3,
    label: '赠送'
  },
  {
    value: 4,
    label: '退款'
  },
  {
    value: 5,
    label: '初次付费'
  },
  {
    value: 6,
    label: '试用转付费'
  }
  ]
}
