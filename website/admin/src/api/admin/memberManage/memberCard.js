import service from '@/util/request.js'

// 获取所有会员卡以分类的形式返回
export function getAllMemberCardByClassRequest () {
  return service({
    url: '/api/admin/member/card/all/list',
    method: 'post',
    data: {}
  })
}

// 为会员设置会员卡
export function setCardForMemberRequest (data) {
  return service({
    url: '/api/admin/member/card/all/add',
    method: 'post',
    data: data
  })
}

// 获取会员持有的所有可用会员 卡
export function getAllAvailableMemberCardRequest (userId) {
  return service({
    url: `/api/admin/member/card/all/get/${userId}`,
    method: 'post',
    data: null
  })
}

// 获取会员卡信息
export function getMemberCard (data) {
  return service({
    url: '/api/admin/member/card/list',
    method: 'post',
    data: data
  })
}

// 获取会员持有会员卡详细信息
export function getAllMemberCardDetailRequest (data) {
  return service({
    url: '/api/admin/member/card/detail/list',
    method: 'post',
    data: data
  })
}

// 获取系统中指定类型的所有可用会员卡
export function getAllMemberCardRequest (data) {
  return service({
    url: '/api/admin/member/card/list',
    method: 'post',
    data: data
  })
}

// 获取持卡会员
export function getAllCardHolders (data) {
  return service({
    url: '/api/admin/member/cardholder',
    method: 'post',
    data: data
  })
}

// 获取会员卡领取批次
export function getgetCardBatchListRequest (cardId) {
  return service({
    url: `/api/admin/member/card/batch/get/${cardId}`,
    method: 'post',
    data: null
  })
}

// 会卡领取详情-查询
export function getReceiveListRequest (data) {
  return service({
    url: '/api/admin/member/code/receivelist',
    method: 'post',
    data: data
  })
}

// 废除会员卡指定批次
export function deleteCardBatchRequest (id) {
  return service({
    url: `/api/admin/member/card/batch/delete/${id}`,
    method: 'post',
    data: null
  })
}

// 充值明细
export function getChargeListRequest (data) {
  return service({
    url: '/api/admin/member/card/charge/list',
    method: 'post',
    data: data
  })
}

// 消费明细
export function getConsumeListRequest (data) {
  return service({
    url: '/api/admin/member/card/consume/list',
    method: 'post',
    data: data
  })
}

// 获取激活列表信息
export function getActivateAuditListRequest (data) {
  return service({
    url: '/api/admin/member/activateAudit/list',
    method: 'post',
    data: data
  })
}

// 审核用户激活通过
export function passActivateAuditRequest (data) {
  return service({
    url: '/api/admin/member/activateAudit/activate',
    method: 'post',
    data: data
  })
}

// 拒绝通过审核
export function rejectActivateAudit (data) {
  return service({
    url: '/api/admin/member/activateAudit/reject',
    method: 'post',
    data: data
  })
}

//  会员卡订单-查询
export function getCardConsumeOrderList (data) {
  return service({
    url: '/api/admin/member/card/order/list',
    method: 'post',
    data: data
  })
}

// 会员卡创建
export function createMemberCardRequest (data) {
  return service({
    url: '/api/admin/member/card/add',
    method: 'post',
    data: data
  })
}

// 获取单张会员卡详细信息
export function getCardDetailInfoRequest (data) {
  return service({
    url: '/api/admin/member/card/get',
    method: 'post',
    data: data
  })
}

// 更新会员卡信息数据
export function updateCardRequest (data) {
  return service({
    url: '/api/admin/member/card/update',
    method: 'post',
    data
  })
}

// 停止或启动会员卡状态
export function changeCardStatueRequest (data) {
  return service({
    url: '/api/admin/member/card/power',
    method: 'post',
    data
  })
}

// 删除会员卡
export function deleteCardRequest (data) {
  return service({
    url: '/api/admin/member/card/delete',
    method: 'post',
    data
  })
}

// 添加领取批次
export function createReceiveBatchRequest (data) {
  return service({
    url: '/api/admin/member/card/generatecode',
    method: 'post',
    data
  })
}

// 添加领取批次
export function getReceiveBatchRequest (id) {
  return service({
    url: `/api/admin/member/card/code/${id}`,
    method: 'post',
    data: null
  })
}

// 废除会员卡
export function deleteUserCardRequest (data) {
  return service({
    url: '/api/admin/user/card/delete',
    method: 'post',
    data
  })
}

// 废除会员卡
export function getAllNoDeleteGradeCard () {
  return service({
    url: '/api/admin/member/card/all/grade',
    method: 'post',
    data: null
  })
}

// 增加减少会员卡余额和次数
export function chargeConsume (data) {
  return service({
    url: '/api/admin/user/card/consume',
    method: 'post',
    data
  })
}

// 持卡会员导出
export function exportExcel (data) {
  return service({
    url: '/api/admin/member/cardholder/import/export',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}
//  会员卡审核导出
export function exportExcelForCardExamine (data) {
  return service({
    url: '/api/admin/member/activateAudit/list/export',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}

// 获取导入领取码模板
export function getExportExcel () {
  return service({
    url: `/api/admin/member/card/code/getTemplate`,
    method: 'get',
    responseType: 'blob'
  })
}

// 获取导入卡号+密码模板
export function getPwdExportExcel () {
  return service({
    url: `/api/admin/member/card/codePwd/getTemplate`,
    method: 'get',
    responseType: 'blob'
  })
}
// 导入模板
export function importInsertExcel (data) {
  return service({
    url: '/api/admin/member/card/code/import/insert',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
//  获得生成/导入记录
export function getExcelList (data) {
  return service({
    url: `/api/admin/member/card/code/importlist/${data}`,
    method: 'get',
    data: data
  })
}

// 下载成功数据
export function getSuccessExcel (data) {
  return service({
    url: '/api/admin/member/card/code/import/success',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}

// 下载失败数据
export function getFailExcel (data) {
  return service({
    url: '/api/admin/member/card/code/import/fail',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}

export function getShareCode (cardId) {
  return service({
    url: `/api/admin/member/card/getqrcode/${cardId}`,
    method: 'post'
  })
}

export function getAllValidGradeCard () {
  return service({
    url: '/api/admin/member/valid/grade/card/list',
    method: 'post'
  })
}

// 下载激活数据
export function exportInfo (data) {
  return service({
    url: '/api/admin/member/code/receivelist/export',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}
