/**
 * 工时相关接口
 */
import request from '@/utils/request'

/**
 * 发送get请求
 */
export function get(url) {
  return request({
    url: '/manhour' + url,
    method: 'get'
  })
}

/**
 * 发送post请求
 */
export function post(url, data) {
  return request({
    url: '/manhour' + url,
    method: 'post',
    data
  })
}

/**
 * 获取活动列表
 */
export function getActivities(url) {
  return get('/activities')
}

/**
 * 添加工时信息
 */
export function addWorkHour(data) {
  return post('/add_manhour', data)
}
