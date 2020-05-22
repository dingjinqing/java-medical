import service from '@/util/request.js'

// 平台分类-根据父ID获取下级分类
export function selectPlatformClassification (parentId) {
  return service({
    url: `/api/admin/decorate/cate/child?parentId=${parentId}`,
    method: 'get'
  })
}

// 平台分类-根据ID查询当前节点的所有祖先级节点
export function selectParentPlatformClassification (catId) {
  return service({
    url: `/api/admin/goods/getSysCatParents?catId=${catId}`,
    method: 'get'
  })
}

// 查询商品新增时店铺设置的通用配置项
export function selectGoodsCommonConfig () {
  return service({
    url: `/api/admin/goods/common/cfg`,
    method: 'get'
  })
}

// 开启店铺通用配置-重量配置
export function openGoodsWeightCfg () {
  return service({
    url: `/api/admin/config/shop/common/open/goods/weight/cfg`,
    method: 'get'
  })
}

// 商品新增，发送请求分为有sku和无sku两个示例
export function addGoodsApi (data) {
  return service({
    url: `/api/admin/goods/add`,
    method: 'post',
    data
  })
}
// 商品修改
export function updateGoodsApi (data) {
  return service({
    url: `/api/admin/goods/update`,
    method: 'post',
    data
  })
}
// 商品查询详细信息，
export function selectGoodsApi (data) {
  return service({
    url: `/api/admin/goods/select`,
    method: 'post',
    data
  })
}
// 商品名、商品编码、商品规格编码是否已存在
export function isGoodsColumnValueExist (data) {
  return service({
    url: `/api/admin/goods/columns/exist`,
    method: 'post',
    data
  })
}
// 获取所有等级会员卡
export function getLevelCardList () {
  return service({
    url: `/api/admin/member/card/list`,
    method: 'post',
    data: {
      cardType: 2
    }
  })
}

// 获取所有可设置为会员专享的卡，普通卡和等级卡
export function getExclusiveCardList () {
  return service({
    url: `/api/admin/member/card/exclusive/get`,
    method: 'post'
  })
}
// 获取商品对应的小程序展示页面
export function getGoodsQrCode (goodsId) {
  return service({
    url: `/api/admin/goods/qrCode/get?goodsId=${goodsId}`,
    method: 'get'
  })
}
