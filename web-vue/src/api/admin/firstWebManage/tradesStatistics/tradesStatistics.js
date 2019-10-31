/****
  交易统计相关接口
 ***/

import service from '@/util/request.js'

// 交易地域分布
export function tradeAreaApi (data) {
  return service({
    url: '/api/admin/transactionstatistics/geographical',
    method: 'post',
    data: data
  })
}

//  标签成交分析
export function labelAnalysisApi (data) {
  return service({
    url: '/api/admin/transactionstatistics/labelanalysis',
    method: 'post',
    data: data
  })
}
