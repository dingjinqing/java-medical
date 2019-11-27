export const en = {
  // tabs
  tabInfo: [
    {
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
  search: 'Search',
  searchTip: 'Please enter the activity name',
  addGift: 'Add Gift',
  // 表格
  activityName: 'activityName',
  validDate: 'ValidDate',
  level: 'Level',
  giftTimes: 'giftTimes',
  activityStatus: 'activityStatus',
  option: 'Option',
  // 操作
  edit: 'Edit',
  stop: 'disabled',
  start: 'enabled',
  detail: 'Detail Gift',
  delete: 'Delete',

  // 添加页面
  steps: ['Set activity rules', 'Setting gifts'],
  // stepsUp: 'Basic configuration',
  stepsUp: 'Basic config',
  stepsDown: 'Gift strategy',
  activityLevel: 'Level',
  activityTime: 'ValidDate',
  startTime: 'startTime',
  endTime: 'endTime',
  to: 'to',
  commodity: 'Commodity',
  addCommodity: 'Add Commodity',
  viewCommodity: 'View Commodity',
  selected: 'Selected',
  selectedNUm: 'Commodity',
  giftConditions: 'Conditions',
  select: 'select',
  conditions1: 'Full amount gift：',
  conditions2: 'Full quantity gift：',
  conditions3: 'Membership tag：',
  conditions4: 'Membership card：',
  conditions5: 'Payment ranking：',
  conditions6: 'Purchased times：',
  conditions7: 'Payment time：',
  conditions8: 'User category：',
  conditionsTip1: 'full',
  conditionsTip2: 'element',
  conditionsTip3: ', Gift giving',
  conditionsTip4: 'piece',
  conditionsTip5: 'Front',
  conditionsTip6: 'Several payment users',
  conditionsTip7: 'second',
  ruleDescription: 'Description',
  nameTip: 'Only used as merchant record, users will not see the name',
  levelTip:
    'Used to distinguish the priority of different complimentary activities. Please fill in a positive integer. The higher the value, the higher the priority',
  goodsRanges: ['All merchandise', 'Designated commodity'],
  conditionsTip:
    'You can get gifts if one of the following conditions is met. You can select up to 3 types',
  textareaTip: `This prompt will be displayed in the front end of the applet. Please carefully write the complimentary rule description according to the configured complimentary policy. You can fill in up to 200 words.\nFor example: the top 100 payment users can get free gifts, which will end when they are finished.`,
  forExample: 'For Example',
  userAction: [
    {
      id: 1,
      name: 'New User'
    },
    {
      id: 2,
      name: 'Old User'
    }
  ],
  rules: [
    {
      label: 'Full amount gift',
      keys: ['fullPrice']
    },
    {
      label: 'Full gift',
      keys: ['fullNumber']
    },
    {
      label: 'Membership tag',
      keys: ['tagId']
    },
    {
      label: 'Membership card',
      keys: ['cardId']
    },
    {
      label: 'Payment ranking',
      keys: ['payTop']
    },
    {
      label: 'Purchased times',
      keys: ['minPayNum', 'maxPayNum']
    },
    {
      label: 'Payment time',
      keys: ['payStartTime', 'payEndTime']
    },
    {
      label: 'User category',
      keys: ['userAction']
    }
  ],
  // step2
  addFreebies: 'Add Freebies',
  addTip:
    'Note: please set the gift inventory reasonably, and the activity will be stopped in advance after all the gifts are distributed. Up to 20 complimentary items can be added',
  goodsName: 'Goods Name',
  catName: 'Specifications',
  goodsPrice: 'Goods Price',
  goodsNumber: 'Goods Number',
  productNumber: 'Product Number',
  // 底部
  lastStep: 'Last Step',
  nextStep: 'Next Step',
  save: 'Save',
  saveSuccess: 'Save Success',
  saveDefault: 'Save Default',
  editSuccess: 'Edit Success',
  editDefault: 'Edit Default',

  // 赠送明细
  mobile: 'Mobile',
  username: 'Username',
  givingTime: 'Giving Time',
  orderSn: 'Order number',
  userId: 'User ID',
  giftAmount: 'Gift Amount'
}
