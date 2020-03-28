import {Message} from 'element-ui'
import {getFeatures, uploadFeatures} from "@/api/feature";
import store from "@/store";

const state = {
  features: null,
}
const mutations = {
  SET_FEATURES: (state, features) => {
    state.features = features
  },

}

const actions = {
  setFeatures({commit}, features) {
    return new Promise((resolve, reject) => {
      commit('SET_FEATURES', features)
      resolve()
    })
  },
  uploadFeatures({dispatch, commit}, excel) {
    return new Promise((resolve, reject) => {
      uploadFeatures(excel).then(response => {

      }).catch(error => {

      })
    })
  },
  getFeatures({commit}) {
    return new Promise((resolve, reject) => {
      getFeatures(store.getters.projectId).then(response => {
        console.log("后端获取")
        commit('SET_FEATURES', response.data)
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  },

}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
