export const cn = {
  // tabs
  tabInfo: [
    {
      title: '全部秒杀活动',
      name: '0'
    },
    {
      title: '进行中',
      name: '1'
    },
    {
      title: '未开始',
      name: '2'
    },
    {
      title: '已过期',
      name: '3'
    },
    {
      title: '已停用',
      name: '4'
    }
  ],
  // 搜索
  search: '查询',
  searchTip: '请输入活动名称',
  addGift: '添加赠品活动',
  // 表格
  activityName: '活动名称',
  validDate: '有效期',
  level: '优先级',
  giftTimes: '赠送次数',
  activityStatus: '活动状态',
  option: '操作',
  // 操作
  edit: '编辑',
  stop: '停用',
  start: '启用',
  detail: '赠送明细',
  delete: '删除',
  // 添加页面
  // step1
  steps: ['设置活动规则', '设置赠品'],
  stepsUp: '基础配置',
  stepsDown: '赠品策略',
  activityLevel: '活动优先级',
  activityTime: '活动时间',
  startTime: '开始日期',
  endTime: '结束日期',
  to: '至',
  commodity: '活动商品',
  giftConditions: '赠品条件',
  ruleDescription: '赠品规则说明',
  nameTip: '只作为商家记录使用，用户不会看到这个名称',
  levelTip: '用于区分不同赠品活动的优先级，请填写正整数，数值越大优先级越高',
  goodsRanges: ['全部商品', '指定商品'],
  conditionsTip: '以下条件满足其一即可获得赠品，最多可选择 3 类',
  textareaTip: `此提示将在小程序前端展示，请根据配置的赠品策略谨慎编写赠品规则说明，最多可填写200字。\n例：前100名付款用户可获得赠品，送完即止。`,
  forExample: '查看示例',
  userAction: [
    {
      id: 1,
      name: '新用户'
    },
    {
      id: 2,
      name: '老用户'
    }
  ],
  rules: [
    {
      label: '满金额赠送',
      keys: ['fullPrice']
    },
    {
      label: '满件数赠送',
      keys: ['fullNumber']
    },
    {
      label: '会员标签',
      keys: ['tagId']
    },
    {
      label: '会员卡',
      keys: ['cardId']
    },
    {
      label: '付款排名',
      keys: ['payTop']
    },
    {
      label: '已购买次数',
      keys: ['minPayNum', 'maxPayNum']
    },
    {
      label: '付款时间',
      keys: ['payStartTime', 'payEndTime']
    },
    {
      label: '用户类别',
      keys: ['userAction']
    }
  ],
  // step2
  addFreebies: '添加赠品商品',
  addTip:
    '注：请合理设置赠品库存，赠品全部发完活动将提前停止。最多可添加20件赠品',
  goodsName: '商品名称',
  catName: '规格',
  goodsPrice: '商品原价',
  goodsNumber: '商品库存',
  productNumber: '赠品库存 (当前库存/初始库存)',
  // 底部
  lastStep: '上一步',
  nextStep: '下一步',
  save: '保存',
  saveSuccess: '保存成功',
  saveDefault: '保存失败',
  deitSuccess: '修改成功',
  deitDefault: '修改失败'
}
