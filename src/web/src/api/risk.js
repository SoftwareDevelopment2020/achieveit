import request from "@/utils/request";

export function getRisks(projectId) {
  return request({
    url: '/risk/risks_by_project_id',
    method: 'get',
    params: {
      'projectId': projectId
    }
  })
}

export function addRisk(projectId, risk) {
  return request({
    url: '/risk/save_risk',
    method: 'post',
    data:risk,
    params: {
      'projectId': projectId
    }
  })
}
