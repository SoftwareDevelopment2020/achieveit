import {updateProject, getProjects} from "@/api/project";
import {Message} from 'element-ui'

const state = {
  projectId: null,
  project: null
}
const mutations = {
  SET_PROJECT_ID: (state, projectId) => {
    state.projectId = projectId
  },
  SET_PROJECT: (state, project) => {
    state.project = project
  }
}

const actions = {
  setProject({commit, dispatch}, project) {
    return new Promise((resolve, reject) => {
      if (project !== null) {
        commit('SET_PROJECT_ID', project.projectId)
        commit('SET_PROJECT', project)
      }
      //重新选择项目后，清空项目features功能列表
      dispatch('feature/setFeatures', null, {root: true})
      resolve()
    })
  },
  updateProject({dispatch, commit}, projectBasics) {
    return new Promise((resolve, reject) => {
      updateProject(projectBasics).then(response => {
        //更新成功则保存项目信息
        dispatch('setProject', projectBasics).then(() => {
          console.log('更新项目信息成功')
          Message({
            message: response.data,
            type: 'success',
            duration: 3 * 1000
          })
          resolve()
        })

      }).catch(error => {
        reject(error)
      })
    })
  },
  getCurrentProject({commit}, projectId) {
    return new Promise((resolve, reject) => {
      getProjects({searchCondition: {projectId: projectId}}).then(response => {
        console.log('成功获取当前选择项目信息')
        console.log(response.data.records[0])
        resolve(response.data.records[0])
      }).catch(error => {
        reject(error)
      })
    })
  },
  removeCurrentProject({commit}) {
    //清除当前项目
    commit('SET_PROJECT_ID', null)
    commit('SET_PROJECT', null)
    console.log('clear')
  },

}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
