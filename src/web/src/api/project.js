/**
 * 项目相关接口
 */
import request from '@/utils/request'

/**
 * 发送get请求
 */
export function get(url) {
  return request({
    url: '/project' + url,
    method: 'get'
  })
}

/**
 * 发送post请求
 */
export function post(url, data) {
  return request({
    url: '/project' + url,
    method: 'post',
    data
  })
}

/**
 * 获取项目
 */
export function getProjects(data) {
  return post('/search_projects', data)
}

/**
 * 更新项目基本信息
 * @param projectBasics
 * @returns {AxiosPromise}
 */
export function updateProject(projectBasics) {
  // return request({
  //   url: '/project/update_project',
  //   method: post,
  //   data: projectBasics
  // })
  return post('/update_project', projectBasics)
}

/**
 * 添加项目
 */
export function addProject(data) {
  return post('/new_project', data)
}
