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
 * 获取当前项目信息
 */
export function getProjectById(data) {
  return get('/select_project_by_id?id=' + (data.id === null || data.id === 'null' ? '' : data.id))
}

/**
 * 更新项目基本信息
 */
export function updateProject(projectBasics) {
  return post('/update_project', projectBasics)
}

/**
 * 添加项目
 */
export function addProject(data) {
  return post('/new_project', data)
}

//审批或否决项目
export function examineProject(projectId, approved) {
  return request({
    method: 'post',
    url: '/project/examine_project',
    params: {
      'approved': approved,
      'project_id': projectId
    }
  })
}

//分配EPG,QA,建立配置库
export function allot(projectId) {
  return request({
    url: '/project/init_project',
    method: 'post',
    params: {
      'project_id': projectId
    }
  })
}
