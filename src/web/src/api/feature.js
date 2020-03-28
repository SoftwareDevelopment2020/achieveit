import request from '@/utils/request'

export function getFeatures(projectId) {
  return request({
    url: '/feature/features_by_project_id',
    method: 'get',
    params: {"projectId": projectId}
  })
}


export function uploadFeatures(formData) {
  return request({
    headers: {"Content-Type": "multipart/form-data"},
    url: '/feature/upload_features',
    method: 'post',
    formData
  })
}
