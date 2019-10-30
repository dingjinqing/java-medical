/****
  交易统计相关接口
 ***/

import service from '@/util/request.js'

//  标签成交分析
export function labelAnalysisApi (data) {
  return service({
    url: '/api/admin/transactionstatistics/labelanalysis',
    method: 'post',
    data: data
  })
}
