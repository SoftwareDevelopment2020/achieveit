/**
 * 设备信息相关接口
 */

import request from '@/utils/request'

/**
 * 发送post请求
 */
export function post(url, data) {
  return request({
    url: '/device' + url,
    method: 'post',
    data
  })
}

/**
 * 查询设备信息
 */
export function getDevices(data) {
  return post('/search_properties', data)
}

/**
 * 添加设备信息
 */
export function addDevice(data) {
  return post('/add_property', data)
}

/**
 * 更新设备信息
 */
export function updateDevice(data) {
  return post('/update_property', data)
}
