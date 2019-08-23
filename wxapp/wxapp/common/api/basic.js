import { request } from "../util/api"

/**
 * 登陆
 */
export function login(data) {
  return request('/api/wxapp/login', data)
}


/**
 * 获取全局配置信息
 */
export function getSetting() {
  return request('/api/wxapp/cfg/bottom')
}

