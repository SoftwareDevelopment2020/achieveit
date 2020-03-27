const state = {
  projectId: null,
  project: null
}

const mutations = {
  SET_PROJECTID: (state, projectId) => {
    state.projectId = projectId
  },
  SET_PROJECT: (state, project) => {
    state.project = project
  }
}

const actions = {
  setProject({commit}, project) {
    return new Promise((resolve, reject) => {
      commit('SET_PROJECTID', project.projectId)
      commit('SET_PROJECT', project)
      resolve
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
