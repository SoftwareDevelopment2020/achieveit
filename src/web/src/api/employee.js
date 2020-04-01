/**
 * 项目相关接口
 */
import request from '@/utils/request'

/**
 * 发送get请求
 */
export function get(url) {
  return request({
    url: '/employee' + url,
    method: 'get'
  })
}

/**
 * 发送post请求
 */
export function post(url, data) {
  return request({
    url: '/employee' + url,
    method: 'post',
    data
  })
}

/**
 * 获取项目人员信息
 */
export function getProjectEmployees(data) {
  return post('/project_employee_vo', data)
}

/**
 * 添加项目人员
 */
export function addProjectEmployee(data) {

}

/**
 * 删除项目人员
 */
export function deleteProjectEmployee(data) {

}

/**
 * 设置角色
 */
export function setProjectEmployeeRole(data) {

}

/**
 * 设置权限
 */
export function setProjectEmployeePermission(data) {

}
