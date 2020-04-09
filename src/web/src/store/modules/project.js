import {updateProject, getProjects, examineProject, allot} from "@/api/project";
import {Message} from 'element-ui'
import {getProjectById} from "../../api/project";
import store from "../index";

const state = {
  id: null,
  projectId: null,
  project: null
}
const mutations = {
  SET_ID: (state, id) => {
    state.id = id
  },
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
        commit('SET_ID', project.id)
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
  getCurrentProject({dispatch, state}, id) {
    return new Promise((resolve, reject) => {
      getProjectById({id: id}).then(async response => {
        const project = response.data
        if (id) {
          await dispatch('setProject', project)
        }
        // 设定角色信息
        const {userDetail, roles} = project
        console.log('getCurrentProject')
        console.log(project)
        await store.dispatch('user/setInfo', {userDetail, roles}, {root: true})

        resolve(project)
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
  examineProject({dispatch}, approved) {
    return new Promise((resolve, reject) => {
      examineProject(store.getters.projectId, approved).then(response => {
        dispatch('setProject', response.data).then(() => {
          console.log('更新项目信息成功')
          resolve()
        })
      }).catch(error => {
        reject(error)
      })
    })
  },
  allot({dispatch}) {
    return new Promise((resolve, reject) => {
      allot(store.getters.projectId).then(response => {
        dispatch('setProject', response.data).then(() => {
          console.log('更新项目信息成功')
          resolve()
        })
      }).catch(error => {
        reject(error)
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
