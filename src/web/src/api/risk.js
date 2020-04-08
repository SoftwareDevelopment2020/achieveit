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

export function importRisk() {
  return request({
    method: 'get',
    url: '/risk/global_risks'
  })
}

export function updateRisk(projectId, data) {
  return request({
    url: '/risk/update_risk',
    method: 'post',
    params: {
      'projectId': projectId
    },
    data: data
  })
}
