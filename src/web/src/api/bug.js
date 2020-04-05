import request from "@/utils/request";

export function getBugs(projectId, data) {
  return request({
    url: '/bug/bugs_by_page',
    method: 'post',
    params: {
      'projectId': projectId,
    },
    data: data
  })
}

export function addBug(projectId, bug) {
  return request({
    url: '/bug/save_bug',
    method: 'post',
    params: {
      'projectId': projectId
    },
    data: bug
  })

}

export function updateBug(projectId, bug) {
  return request({
    url: '/bug/update_bug',
    method: 'post',
    params: {'projectId': projectId},
    data: bug
  })
}
