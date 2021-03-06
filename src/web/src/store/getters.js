const getters = {
  sidebar: state => state.app.sidebar,
  size: state => state.app.size,
  device: state => state.app.device,
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
  token: state => state.user.token,
  name: state => state.user.name,
  roles: state => state.user.roles,
  permission_routes: state => state.permission.routes,
  errorLogs: state => state.errorLog.logs,
  projectKey: state => state.project.id,
  projectId: state => state.project.projectId,
  project: state => state.project.project,
  features: state => state.feature.features,
  risks: state => state.risk.risks
}
export default getters
