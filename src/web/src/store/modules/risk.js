import {getRisks, addRisk, importRisk, updateRisk} from "@/api/risk";
import store from "@/store";

const state = {
  risks: null
}

const mutations = {
  SET_RISKS: function (state, risks) {
    state.risks = risks
  }
}
const actions = {
  getRisks({commit}, data) {
    return new Promise((resolve, reject) => {
      getRisks(store.getters.projectId, data).then(response => {

        // commit('SET_RISKS', response.data)
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  },
  addRisk({}, newRisk) {
    const data = {
      'risk': newRisk,
      'projectId': store.getters.projectId
    }
    newRisk.related = newRisk.related.join(',')
    console.log(newRisk)
    return new Promise((resolve, reject) => {
      addRisk(store.getters.projectId, newRisk).then(response => {
        resolve(response)
      }).catch(error => {
        reject(error)
      })
    })

  },
  setRisks({commit}, data) {
    return new Promise(resolve => {
      commit('SET_RISKS', data)
      resolve()
    })
  },
  importRisk({}) {
    return new Promise((resolve, reject) => {
      importRisk().then(response => {
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  },
  updateRisk({}, data) {
    data.related=data.related.join(',')
    return new Promise((resolve, reject) => {
      updateRisk(store.getters.projectId, data).then(res => {
        resolve(res)
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
