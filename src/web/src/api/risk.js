import request from "@/utils/request";

export function getRisks(projectId, data) {
  return request({
    url: '/risk/search_risks',
    method: 'post',
    params: {
      'projectId': projectId,
    },
    data: data
  })
}

export function addRisk(projectId, risk) {
  return request({
    url: '/risk/save_risk',
    method: 'post',
    data: risk,
    params: {
      'projectId': projectId
    }
  })
}
