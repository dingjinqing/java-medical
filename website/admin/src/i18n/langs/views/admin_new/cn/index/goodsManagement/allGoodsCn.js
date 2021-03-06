// 中文
export const cn = {
  // 头部导航部分数据和按钮数据
  allGoodsRouterHeader: {
    saleOn: '出售中',
    saleOut: '已售罄',
    inStock: '仓库中',
    searchBtn: '查询',
    resetBtn: '重置',
    exportGoods: '导出商品',
    addGoods: '添加商品'
  },
  allGoodsHeaderData: {
    goodsName: '商品名称',
    searchGoods: '搜索商品',
    searchGoodsSaleOut: '商品名称/货号/规格编码',
    category: '平台分类',
    chooseCategory: '请选择平台分类',
    sort: '商家分类',
    chooseSort: '请选择商家分类',
    goodsLabel: '商品标签',
    chooseGoodsLabel: '请选择商品标签',
    saleTime: '上架时间',
    chooseSaleTime: '选择时间',
    goodsBrand: '商品品牌',
    chooseGoodsBrand: '请选择品牌',
    goodsSource: '商品来源',
    goodsSourceOptions: ['请选择商品来源', '自营', '非自营'],
    goodsType: '活动类型',
    goodsTypeOptions: [
      '请选择活动类型',
      '拼团',
      '砍价',
      '秒杀',
      '限时降价',
      '加价购',
      '打包一口价',
      '定金膨胀'
    ],
    shopPrice: '商品价格',
    inputShopPrice: '输入价格'
  },
  allGoodsData: {
    goodsName: '名称',
    shopPrice: '价格',
    goodsSn: '商品货号',
    prdSn: '货号/规格编码',
    noPrdSn: '无规格',
    cat: '平台分类',
    sort: '商家分类',
    goodsBrand: '品牌',
    goodsNumber: '库存',
    saleNumber: '销量',
    goodsLabel: '商品标签',
    setting: '设置',
    operate: '操作',
    edit: '编辑',
    copy: '复制',
    share: '分享',
    underCarriage: '下架',
    upCarriage: '上架',
    shareGoodsTitle: '扫一扫，分享给好友吧~',
    setLabelTitle: '设置标签',
    setLabelTip: '可以在这里编辑商品标签信息,添加或删除标签',
    chooseCategory: '请选择标签',
    selected: '已选',
    delete: '删除',
    tip: '提示',
    deleteTipMsg: '确认要删除吗？',
    confirm: '确认',
    cancel: '取消',
    deleteOk: '删除成功!',
    underCarriageTipMsg: '确认下架该商品吗？',
    upCarriageTipMsg: '确认上架该商品吗？',
    underCarriageOk: '下架成功!',
    upCarriageOk: '上架成功!',
    shopPriceRequired: '请输入正确价格',
    goodsNumberRequired: '请输入正确商品数量',
    setSuccess: '设置成功',
    goodsType: ['拼团', '砍价', '秒杀', '限时降价', '预售'],
    no: '无'
  },
  allGoodsHeaderInputLabel: {
    goodsName: '商品名称',
    catName: '平台分类',
    sortName: '商家分类',
    labelName: '商品标签',
    saleTimeStart: '上架起始时间',
    saleTimeEnd: '上架结束时间',
    brandName: '商品品牌',
    sourceName: '商品来源',
    typeName: '活动类型',
    lowShopPrice: '商品价格下限',
    highShopPrice: '商品价格上限'
  },
  exportDialog: {
    basicInformation: '基础信息',
    createTime: '添加时间',
    platformClass: '平台分类',
    sortNameFirst: '商家一级分类',
    sortNameSecond: '商家二级分类',
    brand: '品牌',
    goodsSn: '商品货号',
    goodsName: '商品名称',
    prdDesc: '规格名称',
    prdSn: '规格编码',
    goodsAd: '商品广告',
    prdNumber: '库存',
    prdCostPrice: '成本价',
    marketPrice: '市场价',
    shopPrice: '零售价',
    isOnSale: '上架状态',
    limitBuyNum: '最小限购数量',
    weight: '重量',
    unit: '单位量词',
    goodsImg: '商品主图',
    detailImg: '商品详情图',
    barcode: '商品条码',
    deliverPlace: '发货地',
    gradeCardPrice: '会员价'
  },
  bottomOptions: {
    allCheck: '全选',
    lowerShelf: '下架',
    delete: '删除',
    batchSetup: '批量设置',
    batchExportOptions: [{
      value: '0',
      label: '批量导出'
    }, {
      value: '1',
      label: '批量导出筛选的件商品'
    }, {
      value: '2',
      label: '批量导出勾选结果'
    }],
    batchFiltered: '批量导出筛选的',
    commodity: '件商品',
    downloadCode: '下载二维码',
    selectpProduct: '请选择商品',
    noItemSelected: '未选择任何商品'
  },
  batchDialog: {
    dialogTitle: '商品管理/出售中/批量设置',
    isCheck: '已选',
    rightTitleTip: '最多可批量设置20件商品',
    goodsPriceTips1: '批量设置',
    goodsPriceTips2: '折扣',
    goodsPriceTips3: '涨/降价',
    goodsPriceTips4: '改价金额',
    goodsPriceTips5: '填写"负值"即降价',
    commoditySpecification: '商品规格',
    originalPrice: '原价',
    discount: '折扣',
    fracture: '折',
    element: '元',
    freightTemplate: '运费模板',
    refresh: '刷新',
    newTemplate: '新建模板',
    templateManagement: '模板管理',
    unifiedFreight: '店铺统一运费：0元',
    distributableArea: '除可配送区域外，不可配送',
    regionsOfTheCountry: '全国其他区域运费',
    inPiece: '件内',
    perIncrease: '元，每增加',
    partsPlus: '件，加',
    viewDetails: '查看详情',
    specify: '指定可配送区域运费',
    freightDelivery: '指定条件包邮可配送区域运费:',
    full: '满',
    packageMail: '件包邮',
    yuanBaoPost: '元包邮',
    piece: '件',
    minimumLimit: '最小限购数量：',
    purchaseQuantity: '0或不填表示不限制购买数量',
    maximum: '最大限购数量：',
    lowerFrames: '上下架：',
    noModification: '不修改',
    marketImmediately: '立即上架售卖',
    customListing: '自定义上架售卖',
    selectDateTime: '选择日期时间',
    timeOfListing: '选择上架售卖时间',
    putIntoWarehouse: '暂不售卖，放入仓库',
    templateLocation: '模板位置：',
    customContentOn: '自定义内容在上',
    detailsArOn: '商品详情在上',
    customContent: '自定义内容：',
    brand: '商品品牌：',
    brandTips: '设置商品详情页显示的自定义内容',
    selectTemplate: '选择模板',
    chooseBrand: '选择品牌',
    addTemplate: '添加模板',
    newBrand: '新建品牌',
    managementTemplate: '管理模板',
    managementBrand: '管理品牌',
    pageName: '页面名称',
    brandName: '品牌名称',
    enterPageName: '请输入页面名称',
    enterBrandName: '请输入品牌名称',
    pageClassification: '页面分类',
    brandClassification: '品牌分类',
    search: '搜索',
    creationTime: '创建时间',
    homePage: '是否首页',
    yes: '是',
    no: '否',
    goodsLabel: '商品标签：',
    newProductLabel: '新建商品标签',
    manageProductLabels: '管理商品标签',
    selected: '已选：',
    memberExclusive: '会员专享商品：',
    memberExclusiveTips: '将已选商品设置为会员专享商品',
    memberExclusiveTipsBottom: '将已选会员专享商品设置为普通商品',
    membershipCard: '设置会员卡(非必选)',
    membershipCardTips: '用户持有指定会员卡才可以购买已选商品',
    newMembershipCard: '新建会员卡',
    manageMembership: '管理会员卡',
    manageMembershipTip: '注：设置会员卡只增加可购买已选商品的会员卡数量，不会修改商品已设置的会员卡。',
    placeOfShipment: '发货地：',
    upWords: '最多设置15字',
    remind: '提醒',
    exitEditing: '确定要退出编辑吗？',
    cancel: '取 消',
    sure: '确定',
    save: '保存',
    liData: ['商品价格', '商家分类', '运费模板', '限购数量', '上架时间', '商品详情', '商品标签', '商品品牌', '会员专属', '发货地'],
    pleaseSelectCard: '请选择会员卡',
    selectProductLabel: '请选择商品标签',
    selectClassification: '请选择分类',
    pleaseChoose: '请选择',
    defaultTemplate: '店铺默认运费模板',
    saveSuccessfully: '保存成功',
    saveFailed: '保存失败'
  }
}
