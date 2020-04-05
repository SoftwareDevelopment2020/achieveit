import request from "@/utils/request";

export function getBugs(projectId, currentPage, size) {
  return request({
    url: '/bug/bugs_by_project_id',
    method: 'get',
    params: {
      'projectId': projectId,
      'current': currentPage,
      'size': size
    }
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
