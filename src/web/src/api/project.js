/**
 * 项目相关接口
 */
import request from '@/utils/request'

/**
 * 发送get请求
 */
export function get (url) {
  return request({
    url: '/project' + url,
    method: 'get'
  })
}

/**
 * 发送post请求
 */
export function post (url, data) {
  return request({
    url: '/project' + url,
    method: 'post',
    data
  })
}

/**
 * 获取项目
 */
export function getProjects (data) {
  return post('/search_projects', data)
}
