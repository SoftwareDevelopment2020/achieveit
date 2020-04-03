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
