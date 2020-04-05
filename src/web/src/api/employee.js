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
 * 发送delete请求
 */
export function deleteMethod(url) {
  return request({
    url: '/employee' + url,
    method: 'delete'
  })
}

/**
 * 获取项目人员信息
 */
export function getProjectEmployees(data) {
  return post('/project_employee_vo', data)
}

/**
 * 获取所有项目人员基本信息
 */
export function getAllProjectEmployeeBasics(data) {
  return get('/get_project_employee_basics?id=' + data.id)
}

/**
 * 添加项目人员
 */
export function addProjectEmployee(data) {
  return post('/add_project_employee', data)
}

/**
 * 删除项目人员
 */
export function deleteProjectEmployee(data) {
  console.info(data)
  return deleteMethod('/delete_project_employee?id=' + data.projectEmployeeId)
}

/**
 * 设置角色
 */
export function setRole(data) {
  return post('/set_role', data)
}

/**
 * 设置权限
 */
export function setPermission(data) {
  return post('/set_permission', data)
}

export function getEmployeesByProjectId(projectId) {
  return request({
    url: '/employee/employees_by_project_id',
    method: 'get',
    params: {
      'projectId': projectId
    }
  })
}
