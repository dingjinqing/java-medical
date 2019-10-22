import service from '@/util/request.js'

// 搜索配置-查询搜索配置接口
export function querySearchConfig () {
  return service({
    // url: '/api/get/searchcfg',
    url: '/api/admin/get/searchcfg',
    method: 'get'
  })
}

// 搜索配置-修改搜索配置接口
export function modifySearchConfig (data) {
  return service({
    url: '/api/admin/shop/cfg/update/searchcfg',
    method: 'post',
    data: data
  })
}
