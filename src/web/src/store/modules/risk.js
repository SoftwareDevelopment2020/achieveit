import {getRisks, addRisk} from "@/api/risk";
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
  getRisks({commit}) {
    return new Promise((resolve, reject) => {
      getRisks(store.getters.projectId).then(response => {
        console.log("后端获取风险信息")
        commit('SET_RISKS', response.data)
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

  }
}
export default {
  namespaced: true,
  state,
  mutations,
  actions
}
